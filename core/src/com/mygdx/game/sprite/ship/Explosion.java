package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.utils.Regions;

public class Explosion extends Sprite {
    private static TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");
    private  Music music;
    private  boolean isPlaying;
    private float animationInterval = 0.4f;

    public float getAnimationInterval() {
        return animationInterval;
    }

    public void setAnimationInterval(float animationInterval) {
        this.animationInterval = animationInterval;
    }

    {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/8BitExplosion.mp3"));
    }

    public static TextureRegion[] getTextureRegions() {
        return Regions.split(atlas.findRegion("explosion"), 9, 9, 74);
    }
    public void playExplosionSFX() {
        music.play();
        isPlaying = true;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
