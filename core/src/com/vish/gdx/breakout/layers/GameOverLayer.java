package com.vish.gdx.breakout.layers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class GameOverLayer extends Group {
	private static final String TAG = GameOverLayer.class.getName();

	Skin skinLibgdx;

	public GameOverLayer(int currentScore) {
		super();
		skinLibgdx = Assets.INSTANCE.getSkinLibgdx();
		Label gameOverLabel = new Label("GAME OVER", skinLibgdx, "ttf-font", Color.WHITE);
		Gdx.app.debug(TAG, "Getting maxScroe : " + Assets.INSTANCE.getPreferences().getMaxScore());
		Label highestLabel = new Label("Highest : " + String.valueOf(Assets.INSTANCE.getPreferences().getMaxScore()),
				skinLibgdx, "ttf-font-mid", Color.WHITE);
		Label score = new Label("Your Score : " + String.valueOf(currentScore), skinLibgdx, "ttf-font-mid", Color.WHITE);
		gameOverLabel.setPosition((GAME_WIDTH - gameOverLabel.getWidth()) / 2, GAME_HEIGHT * 0.6f);
		highestLabel.setPosition((GAME_WIDTH - highestLabel.getWidth()) / 2, GAME_HEIGHT * 0.5f);
		score.setPosition((GAME_WIDTH - score.getWidth()) / 2, GAME_HEIGHT * 0.4f);
		this.addActor(
				Assets.INSTANCE.getDynamicAssets().createRectangleImage(GAME_WIDTH, GAME_HEIGHT, Color.BLUE, 0.7f));
		this.addActor(gameOverLabel);
		this.addActor(highestLabel);
		this.addActor(score);
		// this.setColor(Color.BLUE);

	}

}
