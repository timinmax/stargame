package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;


public class Hero extends Sprite {
    Vector2 dstVector = new Vector2();
    Vector2 tmpVector = new Vector2();
    float velocity = 0.01f;

    public Hero(TextureRegion texture) {
        super(texture);
    }

    public Hero(TextureRegion[] texture) {
        super(texture);
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
                setFrame(0);
            }else{
                tmpVector.nor().scl(velocity);
                setAngle(tmpVector.angle() - 90);
                this.pos.add(tmpVector);
                setFrame(1);
            }
        }
    }

}