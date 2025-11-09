package com.vish.gdx.breakout.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import static com.vish.gdx.breakout.utils.Constants.*;

public class AssetCreator {

	public Texture createBackground() {
		Pixmap pixmap;
		pixmap = new Pixmap((int) GAME_WIDTH, (int) GAME_HEIGHT, Format.RGBA8888);
		pixmap.setColor(BACKGROUND_COLOUR);
		pixmap.fillRectangle(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT);
		pixmap.setColor(WHITE_COLOUR);
		pixmap.fillRectangle(0, (int) UPPER_MARGIN_SIZE - BORDER, (int) GAME_WIDTH, BORDER);
		pixmap.fillRectangle(0, (int) (GAME_HEIGHT - LOWER_MARGIN_SIZE), (int) GAME_WIDTH, BORDER);
		Texture pixmaptex = new Texture(pixmap);
		return pixmaptex;
	}

	public Texture createDottedLine() {
		Pixmap pixmap;
		pixmap = new Pixmap(DOTTED_LINE_LENGTH, DOTTED_LINE_THICKNESS, Format.RGBA8888);

		pixmap.setColor(DOTTED_LINE_TRANSPARENT);
		pixmap.fillRectangle(0, 0, (int) DOTTED_LINE_LENGTH, DOTTED_LINE_THICKNESS);

		pixmap.setColor(DOTTED_LINE_OPAQUE);

		for (int i = 0; i < (DOTTED_LINE_LENGTH / (DOTS_GAP)); i++) {
			int x = i * (DOTTED_LINE_LENGTH / (DOTS_GAP));
			pixmap.fillCircle(x, (int) DOTTED_LINE_THICKNESS / 2, 5);

		}
		Texture pixmaptex = new Texture(pixmap);
		return pixmaptex;
	}

	public Texture getCover() {
		Pixmap pixmap;
		pixmap = new Pixmap((int) GAME_WIDTH, (int) BLOCK_SIZE, Format.RGBA8888);
		pixmap.setColor(BACKGROUND_COLOUR);
		pixmap.fillRectangle(0, 0, (int) GAME_WIDTH, (int) BLOCK_SIZE);
		pixmap.setColor(Color.WHITE);
		pixmap.fillRectangle(0, (int) (BLOCK_SIZE - BORDER), (int) GAME_WIDTH, BLOCK_SIZE);
		pixmap.fillRectangle(0, (int) (GAME_HEIGHT - UPPER_MARGIN - PLAYZONE_HIEGHT), (int) GAME_WIDTH, BORDER);
		Texture pixmaptex = new Texture(pixmap);
		return pixmaptex;
	}

	public Texture getTexture(float width, float height, Color color) {
		return getTexture((int) width, (int) height, color);
	}

	public Texture getTexture(int width, int height, Color color) {
		Pixmap pixmap;
		pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		Texture pixmaptex = new Texture(pixmap);
		return pixmaptex;
	}
}
