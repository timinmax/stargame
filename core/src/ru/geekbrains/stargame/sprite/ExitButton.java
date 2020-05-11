package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Button;
import ru.geekbrains.stargame.math.Rect;


public class ExitButton extends Button {


    public ExitButton(StarGame theGame) {
        super(theGame);
        regions = new TextureRegion[3];
        regions[0] = game.txAtlas.findRegion("buttonExitSimple");
        regions[1] = game.txAtlas.findRegion("buttonExitMouseOver");
        regions[2] = game.txAtlas.findRegion("buttonExitClick");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.pos.set(0f,-0.1f);
    }

    @Override
    public void mouseMoved(Vector2 tmpVector) {
        super.mouseMoved(tmpVector);
    }

    @Override
    protected void clickAction() {
        Gdx.app.exit();
    }
}