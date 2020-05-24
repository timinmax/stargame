package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.EnemyStarShip;

public class EnemyPool extends SpritesPool<EnemyStarShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    protected EnemyStarShip newObject() {
        return new EnemyStarShip(bulletPool, explosionPool, worldBounds, sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}