package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.KeySet;

public class Ship extends Sprite {
    private float position;
    private boolean pressed;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("ship_2"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + 0.02f);
    }

    private void posAdd() {
        position += 0.0014f;
    }

    private void posSub() {
        position -= 0.0014f;
    }

    public void keyListener(int keycode) {
        if (KeySet.UP.contain(keycode)) {
            posAdd();
            pos.add(0, position);
        } else if (KeySet.DOWN.contain(keycode)) {
            posSub();
            pos.add(0, position);
        } else if (KeySet.LEFT.contain(keycode)) {
            posSub();
            pos.add(position, 0);
        } else if (KeySet.RIGHT.contain(keycode)) {
            posAdd();
            pos.add(position, 0);
        }
    }

    public void keyDown() {
        pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void keyUp() {
        pressed = true;
        position = 0;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        pos.set(touch);
        return super.touchUp(touch, pointer, button);
    }


}

