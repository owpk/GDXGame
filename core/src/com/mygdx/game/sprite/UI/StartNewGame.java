package com.mygdx.game.sprite.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.ScaledButton;
import com.mygdx.game.math.Rect;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.sprite.Bullet;

public class StartNewGame extends ScaledButton {

    public StartNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("newGame"));
        this.game = game;
    }

    @Override
    public void update(float delta) {
        //анимация пульсации
        playAnimation();
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
