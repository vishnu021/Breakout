package com.vish.gdx.breakout.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class InteractiveBackground extends Group {
	Random random = new Random();
	Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BROWN, Color.ORANGE, Color.GOLD,
			Color.SKY, Color.SALMON };
	Integer[] speeds;

	public InteractiveBackground() {
		super();
		speeds = new Integer[NUMBER_OF_BLOCKS];

		for (int i = 0; i < NUMBER_OF_BLOCKS; i++)
			speeds[i] = 3 + i % 3;

		List<Integer> speedList = Arrays.asList(speeds);
		Collections.shuffle(speedList);

		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < GAME_HEIGHT / (BLOCK_SIZE_WITH_MARGIN); i++)
			numbers.add(i % NUMBER_OF_BLOCKS);
		Collections.shuffle(numbers);

		for (int i = 0; i < GAME_HEIGHT / (BLOCK_SIZE_WITH_MARGIN); i++) {
			int blockNum = numbers.get(i);
			ThreeeDBlock img = Assets.INSTANCE.getDynamicAssets().create3DBlockGroup();
			img.setPosition(blockNum * (BLOCK_SIZE_WITH_MARGIN), GAME_HEIGHT + i * (BLOCK_SIZE_WITH_MARGIN));
			// img.setDeltaX((int) ((2 * BLOCK_MARGIN) * (GAME_WIDTH / 2 - blockNum *
			// (BLOCK_SIZE)) / GAME_WIDTH));
			img.setColor(colors[random.nextInt(colors.length)]);
			this.addActor(img);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		for (Actor actor : this.getChildren()) {
			if (actor.getY() + actor.getHeight() < 0) {
				actor.setY(GAME_HEIGHT);
			} else {
				((ThreeeDBlock) actor).setPosition(actor.getX(),
						actor.getY() - speeds[(int) ((actor.getX() * NUMBER_OF_BLOCKS) / GAME_WIDTH)]);
			}
		}
	}

}
