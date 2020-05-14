package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.StarShip;

public class GameScreen extends BaseScreen {
    private StarShip starShip;
    private static final int EXIT_CODE = 131;

    public GameScreen(StarGame theGame) {
        super(theGame);
    }

    @Override
    public void show() {
        super.show();
        txBckGrnd = game.txAtlas.findRegion("bkgrnd2");
        background = new Background(txBckGrnd);

        TextureRegion[] txHero = new TextureRegion[2];
        txHero[0] = game.txAtlas.findRegion("starShip");
        txHero[1] = game.txAtlas.findRegion("starShipIsMoving");
        starShip = new StarShip(txHero);
        starShip.setScale(0.3f);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        starShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        starShip.update(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        starShip.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starShip.setDestination(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==EXIT_CODE){
            game.switchScreen(0);
        }
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
