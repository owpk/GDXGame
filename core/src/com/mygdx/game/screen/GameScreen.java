package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.ship.EnemyShip;
import com.mygdx.game.sprite.ship.MainShip;
import com.mygdx.game.sprite.Star;
import com.mygdx.game.sprite.ship.Ship;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Ship mainShip;
    private Ship enemyShip;
    private TextureAtlas atlas;
    private TextureAtlas mainGameAtlas;
    private Star[] stars;
    private BulletPool bulletPool;


    @Override
    public void show() {
        super.show();
        bg = new Texture("cyan gradient.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        mainGameAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        mainShip = new MainShip(mainGameAtlas, bulletPool);
        enemyShip = new EnemyShip(mainGameAtlas,bulletPool);
        stars = new Star[50];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        enemyShip.resize(worldBounds);
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
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyShip.update(delta);
    }

    private void free() {
        bulletPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        enemyShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}