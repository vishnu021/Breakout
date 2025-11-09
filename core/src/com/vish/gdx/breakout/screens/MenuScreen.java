package com.vish.gdx.breakout.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.vish.gdx.breakout.actors.InteractiveBackground;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class MenuScreen extends Stage implements Screen {

	Game thisGame;
	private Skin skinLibgdx;
	private int maxScore;
	Stack stack;
	Group menuGroup;
	final ImageActor soundImage = new ImageActor(Assets.INSTANCE.getTexture("sound_on"));

	public MenuScreen(Game g) {
		super(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
//		this.setDebugAll(true);

		thisGame = g;
		skinLibgdx = Assets.INSTANCE.getSkinLibgdx();
		maxScore = Assets.INSTANCE.getPreferences().getMaxScore();
		Gdx.input.setInputProcessor(this);
		this.clear();
		stack = new Stack();
		this.addActor(stack);
		stack.setSize(GAME_WIDTH, GAME_HEIGHT);
		stack.addActor(new InteractiveBackground());
		stack.addActor(
				Assets.INSTANCE.getDynamicAssets().createRectangleImage(GAME_WIDTH, GAME_HEIGHT, Color.BLACK, 0.5f));
		menuGroup = buildMenuGroup();
		stack.addActor(menuGroup);
		Assets.INSTANCE.getPreferences().clearSavedGame();
	}

	private Group buildMenuGroup() {
		Group group = new Group();
		group.setHeight(GAME_HEIGHT);
		group.setWidth(GAME_WIDTH);

		Label bestLabel = new Label("Best", skinLibgdx, "ttf-font-micro", Color.WHITE);
		Label scoreLabel = new Label(String.valueOf(maxScore), skinLibgdx, "ttf-font-micro", Color.WHITE);
		float width = bestLabel.getWidth() > scoreLabel.getWidth() ? bestLabel.getWidth() : scoreLabel.getWidth();

		bestLabel.setPosition((width - bestLabel.getWidth()) / 2 + 10, GAME_HEIGHT - bestLabel.getHeight() - 10);
		scoreLabel.setPosition((width - scoreLabel.getWidth()) / 2 + 10,
				GAME_HEIGHT - bestLabel.getHeight() - scoreLabel.getHeight() - 10);
		group.addActor(bestLabel);
		group.addActor(scoreLabel);

		Label lbl = new Label("Break Out", skinLibgdx, "ttf-font", Color.WHITE);
		lbl.setPosition((GAME_WIDTH - lbl.getWidth()) / 2, (GAME_HEIGHT * 0.7f));
		group.addActor(lbl);

		ImageButton playButton = new ImageButton(skinLibgdx, "playButton");
		playButton.setColor(Color.WHITE);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("clicked", "->");
				thisGame.setScreen(new GameScreen(thisGame));
			}
		});

		playButton.setPosition((GAME_WIDTH - playButton.getWidth()) / 2, (GAME_HEIGHT - playButton.getHeight()) / 2);
		group.addActor(playButton);

		Label playLabel = new Label("Play", skinLibgdx, "ttf-font-mid", Color.WHITE);
		playLabel.setPosition((GAME_WIDTH - playLabel.getWidth()) / 2, (GAME_HEIGHT * 0.38f));
		group.addActor(playLabel);

		ImageActor achievements = new ImageActor(Assets.INSTANCE.getTexture("cup"));
		achievements.setSize(50, 50);
		achievements.setPosition(50, 50);
		group.addActor(achievements);

		ImageActor leaderBoard = new ImageActor(Assets.INSTANCE.getTexture("stats"));
		leaderBoard.setSize(50, 50);
		leaderBoard.setPosition((GAME_WIDTH - leaderBoard.getHeight()) / 2, 50);
		group.addActor(leaderBoard);
		soundImage.setPosition((GAME_WIDTH - soundImage.getWidth() - 50), 50);
		if (Assets.INSTANCE.getPreferences().sound) {
			soundImage.setDrawable(skinLibgdx, "sound_on");
		} else {
			soundImage.setDrawable(skinLibgdx, "sound_off");
		}
		soundImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (Assets.INSTANCE.getPreferences().sound) {
					soundImage.setDrawable(skinLibgdx, "sound_off");
					Assets.INSTANCE.getPreferences().sound = false;
				} else {
					soundImage.setDrawable(skinLibgdx, "sound_on");
					Assets.INSTANCE.getPreferences().sound = true;
				}
				Assets.INSTANCE.getPreferences().save();
			}
		});
		group.addActor(soundImage);
		return group;
	}

	@Override
	public void show() {
		Gdx.app.log("Menu", "show");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		act(Gdx.graphics.getDeltaTime());
		draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
