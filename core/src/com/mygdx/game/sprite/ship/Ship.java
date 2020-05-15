package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.sprite.Bullet;
import com.mygdx.game.utils.Regions;

public class Ship extends Sprite {
    protected float SIZE = 0.15f;
    protected float MARGIN = 0.05f;
    protected float animateTimer;
    protected float animateInterval = 0.7f;
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected int damage = 1;
    protected int keycode;
    protected boolean pressed;
    protected float position;
    protected final Vector2 v1;
    protected final Explosion explosion;


    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        v1 = new Vector2();
        explosion = new Explosion();
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
        if (getTop() > worldBounds.getTop()) {
            stop();
            setTop(worldBounds.getTop());
            return true;
        }
        if (getBottom() < worldBounds.getBottom()) {
            stop();
            setBottom(worldBounds.getBottom());
            return true;
        }
        return false;
    }

    protected void playDestroyAnimation() {
        regions = Explosion.getTextureRegions();
        animateTimer += 0.1f;
        if (animateTimer >= explosion.getAnimationInterval()) {
            if (frame < regions.length - 1)
                frame++;
            animateTimer = 0f;
        }
    }


    @Override
    public void update(float delta) {
        pos.mulAdd(v1, delta);
        if(!destroyed)
            shoot();
        if(checkCollision()) {
            destroyed = true;
        }
        if(destroyed) {
            if (!explosion.isPlaying())
                explosion.playExplosionSFX();
            playDestroyAnimation();
        }
    }

    protected  void shoot() {
        animateTimer += 0.1f;
        if (animateTimer >= animateInterval) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, damage);
            animateTimer = 0f;
        }
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
