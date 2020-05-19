package com.mygdx.game.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.base.SpritesPool;
import com.mygdx.game.sprite.ship.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/8BitExplosion.mp3"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
