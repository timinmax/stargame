package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;


public class StarShip extends Sprite {
    Vector2 dstVector = new Vector2();
    Vector2 tmpVector = new Vector2();

    private static final int ROTATE_LEFT_IDX = 0;
    private static final int ROTATE_RIGHT_IDX = 1;
    private static final int ACCELERATE_IDX = 2;
    private static final int FIRE_IDX = 3;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Rect worldBounds;

    private float shootTimer;
    private float shootInterval = 0.5f;

    float velocity = 0.01f;

    public StarShip(TextureRegion starShipIRML,TextureRegion bulletRegion, BulletPool bulletPool) {
        super(
                starShipIRML.split(206,starShipIRML.getRegionHeight())[0]
        );
        this.bulletPool = bulletPool;
        this.bulletRegion = bulletRegion;
        bulletV = new Vector2(0, 0.5f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(scale);
        this.worldBounds = worldBounds;
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    public void update(float delta, boolean[] keyPressed) {
        shootTimer += delta;
        if (shootTimer >= shootInterval) {
            shootTimer = 0;
            shoot();
        }
        setFrame(0);
        if(!this.pos.equals(dstVector)){
            tmpVector.set(dstVector);
            tmpVector.sub(this.pos);
            if (tmpVector.len() <= velocity){
                this.pos.set(dstVector);
            }else{
                tmpVector.nor().scl(velocity);
                setAngle(tmpVector.angle() - 90);
                this.pos.add(tmpVector);
                setFrame(1);
            }
        }
        if(keyPressed[ROTATE_LEFT_IDX]){
            rotateLeft();
        }else if(keyPressed[ROTATE_RIGHT_IDX]){
            rotateRight();
        };
        if (keyPressed[ACCELERATE_IDX]){
            accelerate(worldBounds);
        }
        if (keyPressed[FIRE_IDX]){
            shoot();
        }
    }
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pointer == 1){
            shoot();//second pointer 2 shoot
        }
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        if (pointer == 0) {
            setDestination(touch);//first pointer 2 drag
        }
        return false;
    }

    public void setDestination(Vector2 touch){
        dstVector.set(touch);
    }

    public void rotateLeft() {
        rotation(1);
        setFrame(3);
    }

    public void rotateRight() {
        rotation(-1);
        setFrame(2);
    }

    public void accelerate(Rect worldBounds) {
        tmpVector.set(0 - (float)Math.sin(Math.toRadians(getAngle())),
                (float)Math.cos(Math.toRadians(getAngle())));
        tmpVector.sub(this.pos);
        tmpVector.nor().scl(velocity);
        this.pos.add(tmpVector);

        if (this.pos.x>worldBounds.getRight()){
            this.pos.x = worldBounds.getLeft();
        }else if (this.pos.x < worldBounds.getLeft()){
            this.pos.x = worldBounds.getRight();
        }

        if (this.pos.y>worldBounds.getTop()){
            this.pos.y = worldBounds.getBottom();
        }else if (this.pos.y < worldBounds.getBottom()){
            this.pos.y = worldBounds.getTop();
        }

        dstVector.set(this.pos);
        setFrame(1);
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletV.set(0 - (float)Math.sin(Math.toRadians(getAngle())),
                (float)Math.cos(Math.toRadians(getAngle())));

        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1,getAngle());


    }

    private void rotation(int mult){
        float newAngle = this.getAngle()+5*mult;
        if (newAngle>=360f){
            newAngle = 0f;
        }else if (newAngle <= -360f){
            newAngle = 0f;
        }
        setAngle(newAngle);

    }
}