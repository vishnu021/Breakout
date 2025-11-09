package com.vish.gdx.breakout.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.vish.gdx.breakout.GameStage;
import com.vish.gdx.breakout.actors.BlockGroup;
import com.vish.gdx.breakout.utils.Constants;

public class GamePreferences {
	private static final String TAG = GamePreferences.class.getName();

	public static final GamePreferences instance = new GamePreferences();
	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	public int charSkin;
	public boolean showFpsCounter;
	private Preferences prefs;
	public Integer maxScore;

	public void load() {
		Gdx.app.log(TAG, "Loading preferences.");
		prefs = Gdx.app.getPreferences(Constants.MAX_SCORE_PREFERENCE);
		sound = prefs.getBoolean("sound", true);
		maxScore = prefs.getInteger(Constants.MAX_SCORE_VALUE, 0);
		Gdx.app.debug(TAG, "sound : " + sound + " maxScore : " + maxScore);
	}

	public void save() {
		prefs.putBoolean("sound", sound);
		prefs.putInteger(Constants.MAX_SCORE_VALUE, maxScore);
		prefs.flush();
		Gdx.app.debug(TAG, "flushed...");
		Gdx.app.debug(TAG, "sound : " + sound + " maxScore : " + maxScore);

	}

	public void saveGame(GameStage gameStage) {
		if (gameStage == null) {
			Gdx.app.error(TAG, "Cannot save null game state");
			return;
		}

		Gdx.app.log(TAG, "Saving game");
		try {
			new Json(OutputType.json).toJson(gameStage, GameStage.class, Gdx.files.local(Constants.DATA_FILE));
			Gdx.app.debug(TAG, "Game saved successfully");
		} catch (Exception e) {
			Gdx.app.error(TAG, "Failed to serialize game state: " + e.getMessage(), e);
		}
	}

	public GameStage loadGame() {
		try {
			final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
			if (!handle.exists()) {
				Gdx.app.debug(TAG, "No saved game found");
				return null;
			}

			GameStage gameStage = new Json(OutputType.json).fromJson(GameStage.class, handle);
			Gdx.app.debug(TAG, "Game loaded successfully");
			return gameStage;
		} catch (Exception e) {
			Gdx.app.error(TAG, "Failed to load game: " + e.getMessage(), e);
			return null;
		}
	}

	public void deleteGame() {
		try {
			final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
			if (handle.exists()) {
				handle.delete();
				Gdx.app.debug(TAG, "Saved game deleted successfully");
			} else {
				Gdx.app.debug(TAG, "No saved game to delete");
			}
		} catch (Exception e) {
			Gdx.app.error(TAG, "Failed to delete saved game: " + e.getMessage(), e);
		}
	}

	public void clearSavedGame() {

	}

	public static GamePreferences getInstance() {
		return instance;
	}

	public boolean isSound() {
		return sound;
	}

	public boolean isMusic() {
		return music;
	}

	public float getVolSound() {
		return volSound;
	}

	public float getVolMusic() {
		return volMusic;
	}

	public int getCharSkin() {
		return charSkin;
	}

	public boolean isShowFpsCounter() {
		return showFpsCounter;
	}

	public Preferences getPrefs() {
		return prefs;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	static boolean hasSavedData() {
		return Gdx.files.local(Constants.DATA_FILE).exists();
	}

	private void deleteSave() {
		final FileHandle handle = Gdx.files.local(Constants.DATA_FILE);
		if (handle.exists())
			handle.delete();
	}
}
