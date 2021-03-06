package ru.geekbrains.stargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.Button;
import ru.geekbrains.stargame.math.Rect;



public class PlayButton extends Button {


    public PlayButton(StarGame theGame, TextureAtlas atlas) {
        super(theGame,atlas);
        regions = new TextureRegion[3];
        regions[0] = atlas.findRegion("buttonPlaySimple");
        regions[1] = atlas.findRegion("buttonPlayMouseOver");
        regions[2] = atlas.findRegion("buttonPlayClick");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.pos.set(0f,0.01f);
    }

    @Override
    protected void clickAction() {
        game.switchScreen(1);
    }
}