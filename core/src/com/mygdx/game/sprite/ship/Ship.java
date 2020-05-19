package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.sprite.Bullet;

public class Ship extends Sprite {
    protected float SIZE = 0.15f;
    protected float MARGIN = 0.05f;
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage = 1;
    protected int keycode;
    protected boolean pressed;
    protected float position;
    protected final Vector2 v1;
    protected Sound sound;
    protected ExplosionPool explosionPool;
    protected float reloadInterval;
    protected float reloadTimer;
    protected int hp;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v1 = new Vector2();
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        v1 = new Vector2();
        bulletV = new Vector2();
    }

    protected void stop() {
        v1.setZero();
    }

    protected boolean checkCollision() {
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
            return true;
        }
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
            return true;
        }
        if (getBottom() < worldBounds.getBottom()) {
            stop();
            setBottom(worldBounds.getBottom());
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v1, delta);
        if (!destroyed) {
            shoot();
        }
    }

    protected void shoot() {
        reloadTimer += 0.1f;
        if (reloadTimer >= reloadInterval) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, damage);
            reloadTimer = 0f;
            playSound();
        }
    }

    protected void playSound() {}

    private void playExplosionAnimation() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    @Override
    public void destroy() {
        super.destroy();
        playExplosionAnimation();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
    }

    public void keyDown(int keycode) {
        this.keycode = keycode;
        pressed = false;
    }

    public void keyUp() {
        pressed = true;
        position = 0;
    }

    public void setBulletV(float speed) {
        v.y = speed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSIZE(float SIZE) {
        this.SIZE = SIZE;
    }

    public void setMARGIN(float MARGIN) {
        this.MARGIN = MARGIN;
    }
}
