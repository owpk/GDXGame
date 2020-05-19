package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;


public class EnemyShip extends Ship {
    private float bias;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool, explosionPool, worldBounds, sound);
        bulletV = new Vector2(0,-0.2f);
        animateInterval = 1f;
    }

    @Override
    protected void shoot() {
        if (worldBounds.getTop() > this.getHalfHeight() + pos.y)
        super.shoot();
    }

    @Override
    protected void playSound() {
        sound.play(1f, 0.2f, 0f);
    }


    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.v1.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v1.set(v0);
    }

    @Override
    public void update(float delta) {
        if (Rnd.nextFloat(0,0.5f) > 0.48f)
            moveDown();
        if (checkCollision()) {
            destroy();
        }
        super.update(delta);
    }

    private void moveDown() {
        this.v1.add(0,-0.00015f).add(bias += Rnd.nextFloat(-0.0005f, 0.0005f),0);
    }
}
