package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class Logo extends Sprite {


    public Logo(Texture texture) {
        super(new TextureRegion(texture));

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
        this.pos.set(worldBounds.pos);
    }

    public void set(Vector2 touch) {

    }
//
//        return super.touchDown(touch, pointer, button);

}
