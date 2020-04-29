package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bckGrnd;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bckGrnd = new Texture("bkgrnd2.jpg");

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        batch.draw(bckGrnd, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bckGrnd.dispose();
    }
}
