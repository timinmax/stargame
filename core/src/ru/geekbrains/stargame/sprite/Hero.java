package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;


public class Hero extends Sprite {
    Vector2 dstVector, tmpVector;
    float velocity;
    public Hero(Texture texture) {
        super(new TextureRegion(texture));
        dstVector = new Vector2();
        tmpVector = new Vector2();
        velocity = 0.01f;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(scale);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        move();
        super.draw(batch);
    }


    public void setDestination(Vector2 touch){
        dstVector.set(touch);
    }

    private void move(){
        if(!this.pos.equals(dstVector)){
            tmpVector.set(dstVector);
            tmpVector.sub(this.pos);
            if (tmpVector.len() <= velocity){
                this.pos.set(dstVector);
            }else{
                this.pos.add(tmpVector.nor().scl(velocity));
            }
        }
    }

}