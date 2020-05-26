package com.mygdx.game.sprite.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.Sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.base.ScaledButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private static final float MARGIN = 0.05f;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("play"));
        this.game = game;
        this.deltaSize = 0.6f;
    }

    @Override
    public void update(float delta) {
        playAnimation();
        super.update(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setTop(worldBounds.getTop() - MARGIN * 8);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
