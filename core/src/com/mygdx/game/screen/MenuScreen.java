package com.mygdx.game.screen;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.*;
import com.mygdx.game.sprite.UI.ButtonExit;
import com.mygdx.game.sprite.UI.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private final Game game;
    private Texture img;
    private Texture bg;
    private Background background;
    private Logo logo;
    private TextureAtlas atlas;
    private TextureAtlas starAtlas;
    private com.mygdx.game.sprite.UI.ButtonExit buttonExit;
    private com.mygdx.game.sprite.UI.ButtonPlay buttonPlay;
    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("cyan gradient.png");
        logo = new Logo(img);
        background = new Background(bg);
        starAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        atlas = new TextureAtlas(Gdx.files.internal("textures/Ui.atlas"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(starAtlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);

        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    private void update(float delta) {
        buttonPlay.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
//        logo.draw(batch);
//        logo.move();
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }
}
