package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.UI.GameOver;
import com.mygdx.game.sprite.UI.StartNewGame;
import com.mygdx.game.sprite.ship.EnemyShip;
import com.mygdx.game.sprite.ship.Explosion;
import com.mygdx.game.sprite.ship.MainShip;
import com.mygdx.game.sprite.Star;
import com.mygdx.game.sprite.ship.Ship;
import com.mygdx.game.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Ship mainShip;
    private TextureAtlas atlas;
    private TextureAtlas mainGameAtlas;
    private TextureAtlas uiAtlas;
    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private List<EnemyShip> enemyList;
    private List<Bullet> bulletList;
    private State state;
    private GameOver gameOver;
    private StartNewGame startNewGame;
    private Game game;

//    private HPStatusBar hpStatusBar;

    public GameScreen(Game game) {
        this.game = game;
    }

    enum State {GAME, GAME_OVER}
    @Override
    public void show() {
        super.show();
        bg = new Texture("cyan gradient.png");
        background = new Background(bg);
        uiAtlas = new TextureAtlas(Gdx.files.internal("textures/Ui.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        mainGameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        gameOver = new GameOver(uiAtlas);
        startNewGame = new StartNewGame(uiAtlas, game);
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(mainGameAtlas);
        mainShip = new MainShip(mainGameAtlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(mainGameAtlas, enemyPool);
        state = State.GAME;
        stars = new Star[50];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    private void checkTargets() {
        if (state != State.GAME) {
            return;
        }
        enemyList = enemyPool.getActiveObjects();
        bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            if (mainShip.pos.dst(enemy.pos) < enemy.getHalfWidth() + mainShip.getHalfWidth()) {
                enemy.destroy();
                mainShip.destroy();
                state = State.GAME_OVER;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip ||  bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.checkBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.checkBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
        checkTargets();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        startNewGame.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        mainGameAtlas.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp();
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        startNewGame.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        startNewGame.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.GAME) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
        startNewGame.update(delta);
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars)
            star.draw(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        if (state == State.GAME) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            startNewGame.draw(batch);
        }
        batch.end();
    }
}