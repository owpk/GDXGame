package com.mygdx.game.sprite.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.ScaledButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.sprite.Bullet;

public class StartNewGame extends ScaledButton {
    private float animInterval = 0.10f;
    private float animTimer;
    private float pi;

    public StartNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
    }

    @Override
    public void update(float delta) {
        //анимация пульсации
        animTimer += 0.1f;
        if (animTimer >= animInterval) {
            pi += 0.1f;
            float deltaScale = (float) Math.cos(pi);
            if (deltaScale < 0.9 && deltaScale > 0.6)
                scale = deltaScale + 0.1f;
            animTimer = 0;
        }
        super.update(delta);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        setTop(worldBounds.getTop() - 0.55f);
    }
}
