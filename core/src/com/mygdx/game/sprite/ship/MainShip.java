package com.mygdx.game.sprite.ship;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.math.Rect;
import com.mygdx.game.pool.BulletPool;
import com.mygdx.game.pool.ExplosionPool;
import com.mygdx.game.screen.KeySet;


public class MainShip extends Ship {
    private int leftPointer;
    private int rightPointer;
    private static final int INVALID_POINTER = -1;
    private static final int HP = 100;
    private final Vector2 v0;

    private float vPosX;
    private float vPosY;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;

        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);

        v0 = new Vector2(0.5f, 0);

        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;

        reloadInterval = 0.25f;
        reloadTimer = reloadInterval;
        hp = HP;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));

        reloadInterval = 1.5f;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    private float posXAdd() {
        vPosX += 0.0014f;
        return vPosX;
    }

    private float posYAdd() {
        vPosY += 0.0014f;
        return vPosY;
    }

    private float posXSub() {
        vPosX -= 0.0014f;
        return vPosX;
    }
    private float posYSub() {
        vPosY -= 0.0014f;
        return vPosY;
    }

    public void keyListener(int keycode) {
        if (KeySet.UP.contain(keycode)) {
            pos.add(0, posYAdd());
        } else if (KeySet.DOWN.contain(keycode)) {
            pos.add(0, posYSub());
        } else if (KeySet.LEFT.contain(keycode)) {
            pos.add(posXSub(), 0);
        } else if (KeySet.RIGHT.contain(keycode)) {
            pos.add(posXAdd(), 0);
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
    protected void playSound() {
        sound.play(0.1f);
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
    public void keyUp() {
        pressed = true;
        position = 0;
        vPosX = 0;
        vPosY = 0;
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

