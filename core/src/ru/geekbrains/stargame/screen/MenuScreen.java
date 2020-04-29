package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bckGrnd;

    private Vector2 pos,
                    touch,
                    dstPos;

    private static final float PRECISION = 10f;
    private static final float VELOCITY = 5f;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        bckGrnd = new Texture("bkgrnd2.jpg");

        touch = new Vector2();
        pos = new Vector2();
        dstPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!pos.equals(dstPos)){
            Vector2 v = dstPos.cpy().sub(pos);
            if (v.len() < PRECISION) {
                //if close enought then set equal//System.out.println("Almost arrived");
                pos.set(dstPos);
            } else {
                //move to destination//System.out.printf("Moving (%f, %f) -> (%f, %f) V(%f, %f)\n", pos.x, pos.y, dstPos.x, dstPos.y, v.x, v.y);
                pos.add(v.nor().scl(VELOCITY));
            }
        }

        batch.begin();
        batch.draw(bckGrnd, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY );
        dstPos.set(touch);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bckGrnd.dispose();
    }
}
