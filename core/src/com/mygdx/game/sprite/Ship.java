package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.screen.KeySet;

public class Ship extends Sprite {

    private float animateTimer;
    private float animateInterval = 0.7f;

    private static final float SIZE = 0.15f;
    private static final float MARGIN = 0.05f;

    private float position;
    private boolean pressed;

    private int leftPointer;
    private int rightPointer;

    private int keycode;



    private static final int INVALID_POINTER = -1;

    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;

    private final Vector2 v0;
    private final Vector2 v1;

    public Ship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;

        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);

        v0 = new Vector2(0.5f, 0);
        v1 = new Vector2();

        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
    }

    public void setBulletV(float speed) {
        v.y = speed;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
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

    @Override
    public void update(float delta) {
        if (!pressed)
        keyListener(keycode);

        shoot();

        pos.mulAdd(v1, delta);
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
        }
        if (getTop() > worldBounds.getTop()) {
            stop();
            setTop(worldBounds.getTop());
        }
        if (getBottom() < worldBounds.getBottom()) {
            stop();
            setBottom(worldBounds.getBottom());
        }
    }

    private void moveRight() {
        v1.set(v0);
    }

    private void moveLeft() {
        v1.set(v0).rotate(180);
    }

    private void stop() {
        v1.setZero();
    }

    public void keyDown(int keycode) {
        this.keycode = keycode;
        pressed = false;
    }

    public void keyUp() {
        pressed = true;
        position = 0;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    private void shoot() {
        animateTimer += 0.1f;
        if (animateTimer >= animateInterval) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
            animateTimer = 0f;
        }
    }

}

