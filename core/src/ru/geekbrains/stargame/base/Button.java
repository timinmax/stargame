package ru.geekbrains.stargame.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.math.Rect;

public class Button extends Sprite  {

    private final static float BUTTON_SCALE = 0.1f;
    private final float PRESSED_SCALE = 0.7f;
    private boolean isPressed = false;
    private int pointer = 0;
    protected TextureAtlas atlas;
    private Sound mouseOverSound, clickSound;
    boolean mouseOver = false;

    public Button(StarGame theGame, TextureAtlas atlas) {
        super(theGame);
        this.atlas = atlas;
        mouseOverSound = Gdx.audio.newSound(Gdx.files.internal("mouseOverSound.mp3"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("clickSound.mp3"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(BUTTON_SCALE);
    }

    public void mouseMoved(Vector2 tmpVector){
        if (isMe(tmpVector)) {
            if (!mouseOver) {
                mouseOverSound.play();
            }
            mouseOver = true;
            setFrame(1);
        }else {
            mouseOver = false;
            setFrame(0);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (isPressed || !isMe(touch)){
            return false;
        }
        this.pointer = pointer;
        isPressed = true;
        scale = PRESSED_SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !isPressed){
            return false;
        }
        if (isMe(touch)) {
            clickSound.play();
            setFrame(2);
            clickAction();
        }
        isPressed = false;
        scale = 1f;

        return false;
    }

    protected void clickAction(){

    }
}
