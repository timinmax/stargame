package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Hero;

public class MenuScreen extends BaseScreen {
    private TextureRegion txBckGrnd;
    private TextureRegion[] txHero;

    private Background background;
    private Hero hero;

    @Override
    public void show() {
        super.show();
        txHero = new TextureRegion[2];

        txHero[0] = txAtlas.findRegion("starShip");
        txHero[1] = txAtlas.findRegion("starShipIsMoving");
        txBckGrnd = txAtlas.findRegion("bkgrnd2");

        background = new Background(txBckGrnd);
        hero = new Hero(txHero);
        hero.setScale(0.3f);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        hero.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        background.draw(batch);
        hero.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        hero.setDestination(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
