package com.vish.gdx.breakout.core.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vish.gdx.breakout.actors.ThreeeDBlock;
import com.vish.gdx.breakout.core.actors.ImageActor;
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

	// public static Texture createBlock(BlockColor blockColor) {
	// Color a = null;
	// Color b = null;
	// Color c = null;
	// switch (blockColor) {
	// case white:
	// a = GREEN_COLOUR;
	// b = GREEN_COLOUR_X;
	// c = GREEN_COLOUR_Y;
	// break;
	// case green:
	// a = GREEN_COLOUR;
	// b = GREEN_COLOUR_X;
	// c = GREEN_COLOUR_Y;
	// break;
	// case red:
	// a = GREEN_COLOUR;
	// b = GREEN_COLOUR_X;
	// c = GREEN_COLOUR_Y;
	// break;
	// case blue:
	// a = GREEN_COLOUR;
	// b = GREEN_COLOUR_X;
	// c = GREEN_COLOUR_Y;
	// break;
	// }
	// return create3DPixmap(a, b, c);
	// }

	public static Texture create3DImage() {
		Group grp = new Group();
		grp.setSize(BLOCK_SIZE, BLOCK_SIZE);
		int blockSide = BLOCK_SIZE - BLOCK_MARGIN;
		// int BackBlockSide = BLOCK_SIZE;
		int triangleHeight = BLOCK_MARGIN;

		Pixmap pixmap = new Pixmap(blockSide, blockSide, Format.RGBA8888);
		Color white = new Color((float) 248 / 255, (float) 248 / 255, (float) 248 / 255, 1f);
		pixmap.setColor(white);
		pixmap.fillRectangle(0, 0, blockSide, blockSide);

		return new Texture(pixmap);
	}

	public ThreeeDBlock create3DBlockGroup() {
		int blockSide = BLOCK_SIZE;
		int triangleHeight = BLOCK_MARGIN;

		Pixmap pixmap = createSquarePixmap(blockSide,
				new Color((float) 248 / 255, (float) 248 / 255, (float) 248 / 255, 1f));
		ImageActor image = new ImageActor(new Texture(pixmap));

		Pixmap pixmap2 = createSquarePixmap(blockSide,
				new Color((float) 150 / 255, (float) 150 / 255, (float) 150 / 255, 1f));

		ImageActor image2 = new ImageActor(new Texture(pixmap2));
		// int deltaX = (int) ((2 * BLOCK_MARGIN) * (GAME_WIDTH / 2 - positionVector2.x)
		// / GAME_WIDTH);
		ThreeeDBlock grp = new ThreeeDBlock(image, image2);
		grp.setSize(BLOCK_SIZE + 2 * BLOCK_MARGIN, BLOCK_SIZE + 2 * BLOCK_MARGIN);
		return grp;
	}

	public Pixmap createSquarePixmap(int blockSide, Color color) {
		Pixmap pixmap = new Pixmap(blockSide, blockSide, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, blockSide, blockSide);
		return pixmap;
	}

	public Pixmap createIsosecelesTrianglePixmap(int base, Color color) {
		Pixmap pixmap = new Pixmap(base, base / 2, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, base, base);
		pixmap.setColor(Color.BLUE);
		pixmap.fillTriangle(0, 0, base, 0, base / 2, base / 2);
		return pixmap;
	}

	public Pixmap createRectanglePixmap(float x, float y, Color color, float alpha) {
		Pixmap pixmap = new Pixmap((int) x, (int) y, Format.RGBA8888);
		pixmap.setColor(color.r, color.g, color.b, alpha);
		pixmap.fillRectangle(0, 0, (int) x, (int) y);
		return pixmap;
	}

	public ImageActor createRectangleImage(float x, float y, Color color, float alpha) {
		return new ImageActor(new Texture(createRectanglePixmap(x, y, color, alpha)));
	}

	// private static Texture create3DPixmap(Color color, Color xColor, Color
	// yColor) {
	// int front_face = BLOCK_SIZE - BLOCK_MARGIN;
	// int side = BLOCK_SIZE - BLOCK_GAP;
	// Pixmap pixmap = new Pixmap(side, side, Format.RGBA8888);
	// // starting block creation
	// pixmap.setColor(color);
	// pixmap.fillRectangle(0, 0, front_face, front_face);
	// pixmap.setColor(xColor);
	// for (int i = 0; i < front_face; i++) {
	// pixmap.drawLine(i, front_face, i + (side - front_face), side);
	// }
	// pixmap.setColor(yColor);
	//
	// for (int i = 0; i < front_face; i++) {
	// pixmap.drawLine(front_face, i, side, i + (side - front_face));
	// }
	//
	// Texture pixmaptex = new Texture(pixmap);
	// return pixmaptex;
	// }

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

	public Texture getTextureWithBorder(float width, float height, Color color, float borderHeight, Color borderColor,
			boolean borderAtTop) {
		return getTextureWithBorder((int) width, (int) height, color, (int) borderHeight, borderColor, borderAtTop);
	}

	public Texture getTextureWithBorder(int width, int height, Color color, int borderHeight, Color borderColor,
			boolean borderAtTop) {
		// border height inclusive of height
		Pixmap pixmap;
		pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		if (borderAtTop) {
			pixmap.fillRectangle(0, 0, width, height - borderHeight);
			pixmap.setColor(borderColor);
			pixmap.fillRectangle(0, height - borderHeight, width, height);
		} else {
			pixmap.fillRectangle(0, borderHeight, width, height);
			pixmap.setColor(borderColor);
			pixmap.fillRectangle(0, 0, width, borderHeight);
		}
		Texture pixmaptex = new Texture(pixmap);
		return pixmaptex;
	}
}
