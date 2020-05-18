package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class EnemyStarShip extends Sprite {
    private Rect worldBounds;
    private Vector2 v = new Vector2();

    public EnemyStarShip(TextureRegion enemySprites){
        super(
                enemySprites.split(90,enemySprites.getRegionHeight())[0]
        );
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }


    public void set(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setScale(0.3f);
        setHeightProportion(0.3f);

        setFrame(Rnd.nextInt(0,3));
        this.pos.set( Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight()), worldBounds.getTop());
        this.v.set(0,-0.2f);
    }
}
