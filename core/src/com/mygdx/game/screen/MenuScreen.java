package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture sprite;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;

    private float gravity = 0.02f;



    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(v);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(sprite, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        batch.getProjectionMatrix().idt();
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
    
}
