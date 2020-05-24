package ru.geekbrains.stargame.sprite;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Background extends Sprite {

    public Background(TextureRegion texture) {
        super(texture);
    }
    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}