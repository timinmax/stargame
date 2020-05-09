package ru.geekbrains.stargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Button;
import ru.geekbrains.stargame.math.Rect;



public class PlayButton extends Button {


    public PlayButton(StarGame theGame) {
        super(theGame);
        regions = new TextureRegion[3];
        regions[0] = game.txAtlas.findRegion("buttonPlaySimple");
        regions[1] = game.txAtlas.findRegion("buttonPlayMouseOver");
        regions[2] = game.txAtlas.findRegion("buttonPlayClick");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.pos.set(0f,0.4f);
    }

    @Override
    protected void clickAction() {
        game.switchScreen(1);
    }
}