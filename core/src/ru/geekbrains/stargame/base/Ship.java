package ru.geekbrains.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.Explosion;

public class Ship extends Sprite {
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    protected Rect worldBounds;
    protected float reloadTimer;
    protected float reloadInterval = 0.5f;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected Vector2 bulletPos = new Vector2();

    protected float bulletHeight;
    protected int damage;
    protected int hp;

    private float damageAnimateTimer;

    protected Sound sound;

    protected final Vector2 v0 = new Vector2();;
    protected final Vector2 v = new Vector2();;

    public Ship(TextureRegion[] regions){
        super(regions);
    }
    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    protected void autoShoot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0f;
        }
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        sound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }


    public void damage(int damage) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }
}
