package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bckGrnd;

    private Vector2 pos,v,gravity,touch;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bckGrnd = new Texture("bkgrnd2.jpg");

        pos = new Vector2();
        touch = new Vector2();
        v = new Vector2(1,1);
        gravity = new Vector2(0,-0.005f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(v);
        v.add(gravity);
        batch.begin();
        batch.draw(bckGrnd, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY );
        pos.set(touch);
        v.set(1,1);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bckGrnd.dispose();
    }
}
