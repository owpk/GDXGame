package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Align;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.base.Font;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.sprite.UI.GameOver;
import com.mygdx.game.sprite.UI.StartNewGame;
import com.mygdx.game.sprite.ship.EnemyShip;
import com.mygdx.game.sprite.ship.MainShip;
import com.mygdx.game.sprite.Star;
import com.mygdx.game.sprite.ship.Ship;
import com.mygdx.game.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private Background background;
    private Ship mainShip;
    private TextureAtlas atlas;
    private TextureAtlas mainGameAtlas;
    private TextureAtlas uiAtlas;
    private TextureAtlas shipAtlas;
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
    private Music music;
    private static final float RELOAD_STAGE_TIMER = 1f;
    private float reloadStageTimer;
    boolean stageOver;

    private Game game;

    private int frags;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    enum State {GAME, GAME_OVER}

    public GameScreen(Game game) {
        this.game = game;
    }

    public void startNewGame() {
        frags = 0;
        mainShip.setDefaults();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        state = State.GAME;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("cyan gradient.png");
        background = new Background(bg);
        uiAtlas = new TextureAtlas(Gdx.files.internal("textures/Ui.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        shipAtlas = new TextureAtlas(Gdx.files.internal("textures/m_ship/shipp.atlas"));
        mainGameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        gameOver = new GameOver(uiAtlas);
        startNewGame = new StartNewGame(uiAtlas, this);
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(mainGameAtlas);
        mainShip = new MainShip(shipAtlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(mainGameAtlas, enemyPool);
        state = State.GAME;
        stars = new Star[50];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        font = new Font("fonts/font.fnt", "fonts/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    private void checkTargets() {
        if (state != State.GAME) {
            return;
        }
        enemyList = enemyPool.getActiveObjects();
        bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();
                enemy.playExplosionAnimation();
                mainShip.destroy();
                mainShip.playExplosionAnimation();
                mainShip.setHp(0);
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip ||  bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.checkBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        enemy.playExplosionAnimation();
                        frags += 1;
                    }
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
            mainShip.playExplosionAnimation();
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
        font.setSize(FONT_SIZE);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        mainShip.dispose();
        font.dispose();
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

    private void checkStage(float delta) {
        if (frags < 10)
            switchStage(1, delta);
        if (frags >= 10 && frags <= 20)
            switchStage(2, delta);
        if (frags >= 20)
            switchStage(3, delta);
    }

    private void switchStage(int i, float delta) {
        switch (i) {
            case 1:
                enemyEmitter.generateSmallGroup(delta);
                break;
            case 2:
                enemyEmitter.setLevel(2);
                enemyEmitter.generateMedium(delta);
                break;
            case 3:
                enemyEmitter.setLevel(3);
                enemyEmitter.setFlag(frags >= 20 && frags <= 30);
                enemyEmitter.generateBig(delta);
                break;
        }

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
            checkStage(delta);
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
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
        //font.draw(batch, sbHp, mainShip.pos.x, mainShip.getBottom() - TEXT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN, Align.right);
    }
}