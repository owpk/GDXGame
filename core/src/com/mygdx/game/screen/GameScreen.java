package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Ship;
import com.mygdx.game.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private Ship ship;
    private TextureAtlas mainGameAtlas;
    private TextureAtlas atlas;
    private Star[] stars;
    private int keycode;

    @Override
    public void show() {
        super.show();
        bg = new Texture("cyan gradient.png");
        background = new Background(bg);
        mainGameAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        ship = new Ship(mainGameAtlas);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        mainGameAtlas.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        this.keycode = keycode;
        ship.keyDown();
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp();
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        ship.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        ship.draw(batch);
        if (!ship.isPressed())
            ship.keyListener(keycode);
        batch.end();
    }
}