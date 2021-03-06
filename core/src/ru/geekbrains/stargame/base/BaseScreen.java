package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.math.MatrixUtils;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Background;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    protected static StarGame game;
    protected TextureRegion txBckGrnd;
    protected Background background;

    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;


    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;
    private Vector2 mouseMove;
    private Music bgMusic;
    protected TextureAtlas atlas;

    public BaseScreen(StarGame theGame, FileHandle bgMusic) {
        game = theGame;
        this.bgMusic = Gdx.audio.newMusic(bgMusic);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
        mouseMove = new Vector2();

        bgMusic.setLooping(true);
        bgMusic.setVolume(0.2f);
        bgMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);

        resize(worldBounds);
    }
    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {
        bgMusic.stop();
    }

    @Override
    public void resume() {
        bgMusic.play();
    }

    @Override
    public void hide() {
        bgMusic.stop();
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bgMusic.dispose();
        atlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY ).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseMove.set(screenX, screenBounds.getHeight() - screenY ).mul(screenToWorld);
        mouseMoved(mouseMove);
        return false;
    }

    public boolean mouseMoved(Vector2 mouseMove) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Rect getWorldBounds() {
        return worldBounds;
    }
}
