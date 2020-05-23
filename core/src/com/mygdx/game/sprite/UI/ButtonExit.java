package com.mygdx.game.sprite.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.base.ScaledButton;
import com.mygdx.game.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float MARGIN = 0.05f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setTop(worldBounds.getTop() - MARGIN * 11);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}