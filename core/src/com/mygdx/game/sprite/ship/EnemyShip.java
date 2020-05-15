package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.BulletPool;


public class EnemyShip extends Ship {
    private float bias;

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("enemy0"),1,2,2, bulletPool);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        bulletV = new Vector2(0,-0.2f);
        animateInterval = 2.5f;
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
        setBottom(worldBounds.getTop() - 0.2f - MARGIN);
    }

    @Override
    public void update(float delta) {
        moveDown();
        super.update(delta);
    }

    private void moveRandom() {
        this.pos.add(Rnd.nextFloat(-0.005f,0.005f),Rnd.nextFloat(-0.005f,0.005f));
    }
    private void moveDown() {
        this.pos.add(0,-0.0015f).add(bias+=0.00001f,0);
    }
}
