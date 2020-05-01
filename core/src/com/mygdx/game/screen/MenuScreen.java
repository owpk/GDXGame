package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture sprite;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;
    private static float V_LEN = 0.005f;

    private float y;
    private float gravity = 0.02f;



    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(v);
        pos.add(0, y -= gravity);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(sprite, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        img = new Texture("cyan gradient.png");
        sprite = new Texture("badlogic.jpg");
        pos = new Vector2(0, 0);
        v = new Vector2(2, 2);
        touch = new Vector2();

    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX - (float) sprite.getWidth()/2, Gdx.graphics.getHeight() - screenY - (float) sprite.getHeight()/2);
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN * touch.cpy().sub(pos).len());
        pos.set(touch);
        y = 0;
        return super.touchDown(screenX, screenY, pointer, button);
    }
    
}
