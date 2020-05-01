package com.mygdx.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Background background;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("cyan gradient.png");
        background = new Background(bg);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

}
