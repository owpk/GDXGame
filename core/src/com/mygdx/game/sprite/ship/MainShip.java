package com.mygdx.game.sprite.ship;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.screen.KeySet;


public class MainShip extends Ship {
    private int leftPointer;
    private int rightPointer;
    private static final int INVALID_POINTER = -1;

    private final Vector2 v0;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2, bulletPool);
        this.bulletPool = bulletPool;

        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);

        v0 = new Vector2(0.5f, 0);

        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
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
        super.update(delta);
    }

    private void moveRight() {
        v1.set(v0);
    }

    private void moveLeft() {
        v1.set(v0).rotate(180);
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

}

