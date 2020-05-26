package com.mygdx.game.sprite.UI;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameOver"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        setTop(0.1f);
    }
}
