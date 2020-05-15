package com.mygdx.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.math.Rect;
import com.mygdx.game.utils.Regions;

import javax.swing.plaf.synth.Region;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame = 0;

    protected Vector2 v;
    protected Vector2 g;
    protected float speed = 0.003f;
    protected float gravity = 0.0002f;
    protected boolean destroyed;

    public static float V_LEN = 0.005f;

    public Sprite(TextureRegion region) {
        v = new Vector2(speed, speed);
        g = new Vector2(0, gravity);
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite() {

    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        regions = Regions.split(region, rows, cols, frames);
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public Vector2 getV() {
        return v;
    }

    public Vector2 getG() {
        return g;
    }

    public void resize(Rect worldBounds) {
    }

    public float setGravity() {
        return g.y -= gravity;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }

    public void flushDestroy() {
        destroyed = false;
    }
}
