package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.ExitButton;
import ru.geekbrains.stargame.sprite.PlayButton;

public class StartScreen extends BaseScreen {
    private PlayButton startBtn;
    private ExitButton exitBtn;


    public StartScreen(StarGame theGame) {
        super(theGame);
    }

    @Override
    public void show() {
        super.show();
        txBckGrnd = game.txAtlas.findRegion("kosmodrom");
        background = new Background(txBckGrnd);
        startBtn = new PlayButton(game);
        exitBtn = new ExitButton(game);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        startBtn.resize(worldBounds);
        exitBtn.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        startBtn.draw(batch);
        exitBtn.draw(batch);
        batch.end();
    }

    @Override
    public boolean mouseMoved(Vector2 mouseMove) {
        startBtn.mouseMoved(mouseMove);
        exitBtn.mouseMoved(mouseMove);
        return super.mouseMoved(mouseMove);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        startBtn.touchDown( touch,  pointer,  button);
        exitBtn.touchDown( touch,  pointer,  button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        startBtn.mouseMoved(touch);
        exitBtn.mouseMoved(touch);
        startBtn.touchUp( touch,  pointer,  button);
        exitBtn.touchUp( touch,  pointer,  button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
