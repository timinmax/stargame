package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.EnemyStarShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.StarShip;

public class GameScreen extends BaseScreen {
    private StarShip starShip;
    private static final int EXIT_CODE = 131;
    private static final int LArr_CODE = 21;
    private static final int RArr_CODE = 22;
    private static final int UArr_CODE = 19;
    private static final int ABut_CODE = 29;
    private static final int WBut_CODE = 51;
    private static final int DBut_CODE = 32;
    private static final int SPACE_CODE = 62;
    private boolean[] keysPressed = new boolean[4];//ArrowL,ArrowRight,ArrowUp,a,d,w,space
    private Star stars[] = new Star[150];
    private BulletPool bulletPool;
    private EnemyPool enemyPool;


    private float enemyTimer;
    private float enemyInterval;

    public GameScreen(StarGame theGame) {
        super(theGame, Gdx.files.internal("gameSound.mp3"));
    }

    @Override
    public void show() {
        super.show();
        txBckGrnd = game.txAtlas.findRegion("bkgrnd2");
        background = new Background(txBckGrnd);
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(game.txAtlas.findRegion("enemyStarShips"));
        starShip = new StarShip(game.txAtlas.findRegion("StarShipIRML"),
                game.txAtlas.findRegion("playerBullet"),bulletPool);
        starShip.setScale(0.3f);

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(game.txAtlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        starShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        enemyInterval = Rnd.nextFloat(0.5f, 1.5f);
        update(delta);
        free();
        draw();
    }

    private void update(float delta) {
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        starShip.update(delta,keysPressed);
        for (Star star : stars) {
            star.update(delta);
        }

        enemyTimer += delta;
        if (enemyTimer >= enemyInterval) {
            EnemyStarShip enemyStarShip = enemyPool.obtain();
            enemyStarShip.set(getWorldBounds());
            enemyTimer = 0f;
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        starShip.draw(batch);
        batch.end();


    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        starShip.touchDragged(touch,pointer);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starShip.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        starShip.setDestination(touch);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case LArr_CODE:
                keysPressed[0] = false;
                break;
            case RArr_CODE:
                keysPressed[1] = false;
                break;
            case UArr_CODE:
                keysPressed[2] = false;
                break;
            case ABut_CODE:
                keysPressed[0] = false;
                break;
            case WBut_CODE:
                keysPressed[2] = false;
                break;
            case DBut_CODE:
                keysPressed[1] = false;
                break;
            case SPACE_CODE:
                keysPressed[3] = false;
                break;
            default:
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case EXIT_CODE:
                game.switchScreen(0);
                break;
            case LArr_CODE:
                keysPressed[0] = true;
                break;
            case RArr_CODE:
                keysPressed[1] = true;
                break;
            case UArr_CODE:
                keysPressed[2] = true;
                break;
            case ABut_CODE:
                keysPressed[0] = true;
                break;
            case WBut_CODE:
                keysPressed[2] = true;
                break;
            case DBut_CODE:
                keysPressed[1] = true;
                break;
            case SPACE_CODE:
                keysPressed[3] = true;
                break;
            default:
        }

        return false;
    }

    @Override
    public void dispose() {
        bulletPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }
}
