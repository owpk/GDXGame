package com.mygdx.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture bg;
    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bg = new Texture("cyan gradient.png");
        logo = new Logo(img);
        background = new Background(bg);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        logo.pos.add(logo.getV());
        logo.pos.add(logo.getG());
        logo.setGravity();
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("TOUCH");
        logo.getV().set(touch.cpy().sub(logo.pos));
        logo.getV().setLength(Sprite.V_LEN * touch.cpy().sub(logo.pos).len());
        logo.getG().y = 0;
        logo.pos.set(touch);
        return super.touchDown(touch, pointer, button);
    }
}
