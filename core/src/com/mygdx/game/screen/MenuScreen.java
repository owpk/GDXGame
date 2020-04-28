package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture sprite;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 g;
    private Vector2 n;
    private Vector2 touch;



    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(v);
        g.y -= 0.02f;
        pos.add(g);
        v.add(n);
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
        n = new Vector2(0.01f,0);
        g = new Vector2(0, 0);
        touch = new Vector2();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    private boolean checkCollision(int x, int y) {
        return (x > pos.x && x < pos.x + sprite.getWidth()) &&
                (y > pos.y && y < pos.y + sprite.getHeight());
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenY);
        System.out.println("mouse x:"+screenX+" y:"+screenY);
        System.out.println("pos.x:"+(pos.x+sprite.getWidth())+" pos.y:"+(pos.y + sprite.getHeight()));
        if (checkCollision(screenX,screenY)) {
            v.set(0,0);
            g.set(0,0);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
