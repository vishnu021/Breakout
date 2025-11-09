package com.vish.gdx.breakout.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vish.gdx.breakout.GameStage;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class GameDetailsGroup extends Group {
	private static final String TAG = GameDetailsGroup.class.getName();

	Skin skinLibgdx;
	final ImageActor pauseImage = new ImageActor(Assets.INSTANCE.getTexture("pause"));
	final GameStage gameStage;
	private Label stepCounter;
	GlyphLayout glyphLayout;
	private Label multiplier;
	private Label bestLabel;
	private Label scoreLabel;
	ImageActor fastForward;
	public boolean accelerated = false;

	public GameDetailsGroup(final GameStage gameStage) {
		super();
		this.gameStage = gameStage;
		skinLibgdx = Assets.INSTANCE.getSkinLibgdx();
		glyphLayout = new GlyphLayout();
		// creating this

		// Game Action is going to have the following Actors ***************
		// 1. >> UpperMargin
		// 2. >> LowerMargin
		// 3. >> BestScore
		// 4. >> Nth Step
		// 5. >> Pause/Play
		// 6. >> Multiplier
		// 7. >> FastForward

		// this = new GameDetailsGroup();
		this.setSize(GAME_WIDTH, GAME_HEIGHT);

		// UpperMargin
		ImageActor headCover = new ImageActor(Assets.INSTANCE.getDynamicAssets().getTextureWithBorder(GAME_WIDTH,
				UPPER_MARGIN_SIZE + BORDER, BACKGROUND_COLOUR, BORDER, Color.WHITE, true));
		headCover.setPosition(0, GAME_HEIGHT - UPPER_MARGIN_SIZE - BORDER);
		this.addActor(headCover);
		// LowerMargin

		ImageActor footCover = new ImageActor(Assets.INSTANCE.getDynamicAssets().getTextureWithBorder(GAME_WIDTH,
				LOWER_MARGIN_SIZE, BACKGROUND_COLOUR, BORDER, Color.WHITE, false));
		footCover.setPosition(0, 0);
		this.addActor(footCover);

		// BEST SCORE
		bestLabel = new Label("Best", skinLibgdx, "ttf-font-micro", Color.WHITE);
		scoreLabel = new Label(String.valueOf(Assets.INSTANCE.getPreferences().getMaxScore()), skinLibgdx,
				"ttf-font-micro", Color.WHITE);

		updateBestScore();
		this.addActor(bestLabel);
		this.addActor(scoreLabel);

		// Nth Step
		stepCounter = new Label("1", skinLibgdx, "ttf-font", Color.WHITE);
		stepCounter.setPosition((GAME_WIDTH - stepCounter.getWidth()) / 2,
				UPPER_MARGIN + UPPER_MARGIN_SIZE / 2 - stepCounter.getHeight() / 2);
		this.addActor(stepCounter);

		// PAUSE/PLAY
		this.addActor(pauseImage);
		pauseImage.setName("pause");
		pauseImage.setSize(30, 30);
		pauseImage.setPosition(20, UPPER_MARGIN + UPPER_MARGIN_SIZE / 2 - pauseImage.getHeight() / 2);
		pauseImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.debug(TAG, "paused");
				super.clicked(event, x, y);
				gameStage.gameState = State.PAUSE;
				gameStage.pauseCover.setVisible(true);
			}
		});
		pauseImage.setTouchable(Touchable.enabled);

		// Multiplier
		multiplier = new Label("x1", skinLibgdx, "ttf-font-micro", Color.WHITE);
		multiplier.setPosition((GAME_WIDTH - multiplier.getWidth()) / 2, LOWER_MARGIN / 2);
		this.addActor(multiplier);

		// FAST FORWARD
		fastForward = new ImageActor(Assets.INSTANCE.getTexture("play_saved"));
		fastForward.setSize(30, 30);
		fastForward.setPosition(GAME_WIDTH * 0.9f - fastForward.getWidth(), 20);
		fastForward.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				accelerated = true;
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				accelerated = false;
				super.touchUp(event, x, y, pointer, button);
			}

		});
		this.addActor(fastForward);

	}

	public void updateBestScore() {
		scoreLabel.setText(String.valueOf(Assets.INSTANCE.getPreferences().getMaxScore()));
		glyphLayout.setText(skinLibgdx.getFont("ttf-font-micro"), scoreLabel.getText());
		float width = bestLabel.getWidth() > glyphLayout.width ? bestLabel.getWidth() : glyphLayout.width;
		bestLabel.setPosition(GAME_WIDTH - (width - bestLabel.getWidth()) / 2 - bestLabel.getWidth() - 50,
				UPPER_MARGIN + UPPER_MARGIN_SIZE / 2 + 5);
		scoreLabel.setPosition(GAME_WIDTH - (width - glyphLayout.width) / 2 - glyphLayout.width - 50,
				UPPER_MARGIN + UPPER_MARGIN_SIZE / 2 - scoreLabel.getHeight() / 2 - 5);
	}

	public void setStepCounterValue(int step) {
		this.stepCounter.setText(String.valueOf(step));
		glyphLayout.setText(skinLibgdx.getFont("ttf-font"), stepCounter.getText());
		float textWidth = glyphLayout.width;
		this.stepCounter.setX((GAME_WIDTH - textWidth) / 2);
	}

	public void setMultiplierValue(int multiplierValue) {
		if (multiplierValue > 0) {
			multiplier.setText("x" + String.valueOf(multiplierValue));
			glyphLayout.setText(skinLibgdx.getFont("ttf-font-micro"), multiplier.getText());
			multiplier.setX((GAME_WIDTH - glyphLayout.width) / 2);
		} else {
			multiplier.setText("");
		}
	}

	public void setBestLabelValue(Label bestLabel) {
		this.bestLabel = bestLabel;
	}

	public void setScoreLabelValue(Label scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	public void setFastForwardValue(ImageActor fastForward) {
		this.fastForward = fastForward;
	}

}
