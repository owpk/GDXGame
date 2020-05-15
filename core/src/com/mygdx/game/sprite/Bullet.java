package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class Bullet extends Sprite {


    private Rect worldBounds;
    private int damage;
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(
            Sprite owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
