package com.vish.gdx.breakout;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.screens.GameScreen;
import com.vish.gdx.breakout.screens.MenuScreen;

public class BreakoutGame extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log("Game", "App created");

		Assets.INSTANCE.init(new AssetManager());
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		// Dispose current screen (triggers pool cleanup)
		if (getScreen() != null) {
			getScreen().dispose();
		}

		// Dispose global assets
		Assets.INSTANCE.dispose();

		Gdx.app.log("Game", "App disposed - all pools cleared, GC can run");
	}
}
