package com.mygdx.game.sprite.ship;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.utils.Regions;

public class Explosion extends Sprite {
    private static TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");

    public static TextureRegion[] getTextureRegions() {
        return Regions.split(atlas.findRegion("explosion"), 9, 9, 9);
    }

}
