package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyStarShip;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.PlayButton;
import ru.geekbrains.stargame.sprite.RestartButton;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.StarShip;
import ru.geekbrains.stargame.utils.EnemyEmitter;

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
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private enum State {PLAYING, GAME_OVER}
    private State state;
    private GameOver gameOver;
    private RestartButton restartButton;

    private TextureAtlas myAtlas = new TextureAtlas("game.pack");

        public GameScreen(StarGame theGame) {
        super(theGame, Gdx.files.internal("gameSound.mp3"));
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("mainAtlas.pack");
        background = new Background(new Texture("bg.png"));
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool);
        gameOver = new GameOver(atlas);
        restartButton = new RestartButton(game,myAtlas);

        starShip = new StarShip(myAtlas.findRegion("StarShipIRML"),
                myAtlas.findRegion("playerBullet"),bulletPool,explosionPool);

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(myAtlas);
        }
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        starShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        restartButton.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkBulletHit();
        free();
        draw();
    }

    private void checkBulletHit() {
        List<EnemyStarShip> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }

            if (bullet.getOwner() == starShip) {
                for (EnemyStarShip enemy : enemyList) {
                    if (enemy.isMe(bullet.pos)) {
                        enemy.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                }
            } else if (starShip.isMe(bullet.pos)) {
                starShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (starShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    public void startNewGame() {

        starShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        state = State.PLAYING;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            starShip.update(delta, keysPressed);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }else if (state == State.GAME_OVER) {
            restartButton.update(delta);
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            starShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            restartButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            starShip.touchDragged(touch, pointer);
        }
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.touchDown(touch, pointer, button);
        }else if (state == State.GAME_OVER){
            restartButton.touchDown(touch,pointer,button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.setDestination(touch);
        }else if(state == State.GAME_OVER){
            restartButton.touchUp(touch,pointer,button);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(Vector2 mouseMove) {
            if(state==State.GAME_OVER){
            restartButton.mouseMoved(mouseMove);
            }
        return super.mouseMoved(mouseMove);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            updateKeysPressed(keycode, false);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            updateKeysPressed(keycode, true);
        }
        return false;
    }

    private void updateKeysPressed(int keycode, boolean state){
        switch (keycode){
            case EXIT_CODE:
                game.switchScreen(0);
                break;
            case LArr_CODE:
                keysPressed[0] = state;
                break;
            case RArr_CODE:
                keysPressed[1] = state;
                break;
            case UArr_CODE:
                keysPressed[2] = state;
                break;
            case ABut_CODE:
                keysPressed[0] = state;
                break;
            case WBut_CODE:
                keysPressed[2] = state;
                break;
            case DBut_CODE:
                keysPressed[1] = state;
                break;
            case SPACE_CODE:
                keysPressed[3] = state;
                break;
            default:
        }
    }

    @Override
    public void dispose() {
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        super.dispose();
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }
}
