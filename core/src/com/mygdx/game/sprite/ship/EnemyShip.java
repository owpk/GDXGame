package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Bullet;


public class EnemyShip extends Ship {
    private float bias;
    private static final float V_Y = -0.3f;
    private float pi;
    private float animTimer;
    private static final float ANIM_INTERVAL = 0.4f;
    private float deltaPos;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool, explosionPool, worldBounds, sound);
        bulletV = new Vector2(0, -0.2f);
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
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        v1.set(0, V_Y);
    }


    @Override
    protected boolean checkYCol() {
        if (getTop() < worldBounds.getBottom()) {
            destroy();
            setBottom(worldBounds.getBottom());
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if (getTop() < worldBounds.getTop())
            v1.set(v0);
        if (Rnd.nextFloat(0, 0.5f) > 0.48f)
            moveDown();
        if (checkCollision()) {
            destroy();
        }
        super.update(delta);
        flyStage();
        checkCollision();
        setDamageFrame();
    }

    @Override
    public boolean checkBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    private void moveDown() {
        this.v1.add(0, -0.00015f).add(bias += Rnd.nextFloat(-0.0005f, 0.0005f), 0);
    }

    public void flyStage() {
        animTimer += 0.1f;
        if (animTimer >= ANIM_INTERVAL) {
            pi += 0.1f;
            deltaPos = (float) Math.cos(pi);
            animTimer = 0;
        }
        pos.add(deltaPos * 0.01f,-0.001f);
    }
}
