package ru.geekbrains.stargame.base;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.math.Rect;

public class Button extends Sprite  {

    private final static float BUTTON_SCALE = 0.3f;

    public Button(StarGame theGame) {
        super(theGame);
        setScale(BUTTON_SCALE);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(scale);
    }

    public void mouseMoved(Vector2 tmpVector){
        if (isMe(tmpVector)) {
            setFrame(1);
        }else {
            setFrame(0);
        }
    }

    public void touchDown(Vector2 touch) {
        if (isMe(touch)) {
            setFrame(2);
            clickAction();
        }
    }

    protected void clickAction(){

    }
}
