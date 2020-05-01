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

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        v.set(touch.cpy().sub(pos));
        v.setLength(V_LEN * touch.cpy().sub(pos).len());
        g.y = 0;
        pos.set(touch);
        return super.touchDown(touch, pointer, button);
    }

    //
//        return super.touchDown(touch, pointer, button);

}
