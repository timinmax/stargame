package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.sprite.EnemyStarShip;

public class EnemyPool extends SpritesPool<EnemyStarShip> {
    private TextureRegion enemySprites;

    public EnemyPool(TextureRegion enemySprites) {
        super();
        this.enemySprites = enemySprites;
    }

    @Override
    protected EnemyStarShip newObject() {
        return new EnemyStarShip(enemySprites);
    }
}