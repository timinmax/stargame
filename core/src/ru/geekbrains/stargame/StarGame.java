package ru.geekbrains.stargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.screen.GameScreen;
import ru.geekbrains.stargame.screen.StartScreen;

public class StarGame extends Game {
	public static TextureAtlas txAtlas;
	private Screen[] gameScreens = new Screen[2];


	@Override
	public void create() {
		txAtlas = new TextureAtlas("game.pack");
		gameScreens[0] = new StartScreen(this);
		gameScreens[1] = new GameScreen(this);
		switchScreen(0);
	}

	@Override
	public void dispose() {
		super.dispose();
		txAtlas.dispose();
	}

	public void switchScreen(int idScreen){
		setScreen(gameScreens[idScreen]);
	}
}
