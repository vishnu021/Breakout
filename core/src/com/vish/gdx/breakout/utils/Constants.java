package com.vish.gdx.breakout.utils;

import com.badlogic.gdx.graphics.Color;

public final class Constants {

	private Constants() {
		// Prevent instantiation
	}

	public static final String DATA_FILE = "breakout.dat";
	public static final String TEXTURE_ATLAS_OBJECTS = "BreakOut.pack";
	public static final String BALL_TEXTURE = "tennis";
	public static final String BLOCK_TEXTURE = "block";
	public static final float GAME_WIDTH = 480;
	public static final float GAME_HEIGHT = 800;

	public static final int BORDER = 10;
	public static final int BALL_SIZE = 22;

	public final static short PHYSICS_ENTITY = 0x1; // 0001
	public final static short WORLD_ENTITY = 0x1 << 1; // 0010 or 0x2 in hex

	public static final int DOTTED_LINE_THICKNESS = 2;
	public static final int DOTS_GAP = 25;
	public static final int DOTTED_LINE_LENGTH = (int) GAME_HEIGHT;

	public static final float PIXELS_TO_METERS = 100f;

	public static final float UPPER_MARGIN_SIZE = (float) (GAME_HEIGHT * 0.13);// including border
	public static final float LOWER_MARGIN_SIZE = (float) (GAME_HEIGHT * 0.1);// including border

	public static final int NUMBER_OF_BLOCKS = 8;
	public static final int BLOCK_SIZE_WITH_MARGIN = (int) GAME_WIDTH / NUMBER_OF_BLOCKS;
	public static final int BLOCK_MARGIN = (int) (BLOCK_SIZE_WITH_MARGIN * 0.18);
	public static final int BLOCK_SIZE = BLOCK_SIZE_WITH_MARGIN - BLOCK_MARGIN;
	public static final int BLOCK_MARGIN_WITHOUT_OFFSET = (int) (BLOCK_MARGIN * 0.75f);

	public static final float SLIDER_Y_CORRECTION = (float) (8);

	public static final float UPPER_MARGIN = GAME_HEIGHT - UPPER_MARGIN_SIZE;
	public static final float LOWER_MARGIN = LOWER_MARGIN_SIZE;

	public static final float BALL_ENTRY_GAP_SECONDS = (float) 0.15;

	public static final float PLAYZONE_WIDTH = GAME_WIDTH;
	public static final float PLAYZONE_HIEGHT = GAME_HEIGHT - UPPER_MARGIN - LOWER_MARGIN;

	public static final float BALL_VELOCITY = 7f;
	public static final float sqrt_2 = (float) 1.4142;
	public static final float SLIDER_SENSIVITY = 1;

	public static final String Jellee_Roman = "font/GetVoIP Grotesque Italic.otf";

	// LAUNCH ANGLE CONSTRAINTS
	public static final float MIN_LAUNCH_ANGLE = 10f;
	public static final float MAX_LAUNCH_ANGLE = 170f;

	// BLOCK GENERATION PROBABILITIES
	public static final int SPECIAL_BLOCK_CHANCE_DENOMINATOR = 3;
	public static final int DOUBLE_VALUE_THRESHOLD = 7;
	public static final int DOUBLE_VALUE_DENOMINATOR = 9;
	public static final int EMPTY_BLOCK_THRESHOLD = 6;
	public static final int EMPTY_BLOCK_DENOMINATOR = 10;

	// ACTOR NAMES
	public static final String SLIDER = "textActor";
	public static final String BALL = "buttonActor";
	public static final String MAX_SCORE_PREFERENCE = "maximumScoreValue";
	public static final String MAX_SCORE_VALUE = "maximumScore";

	// BACKGROUND VARIABLES
	public static final Color WHITE_COLOUR = new Color(1, 1, 1, 1.0f);
	public static final Color GREEN_COLOUR = new Color((float) 148 / 255, (float) 217 / 255, (float) 2 / 255, 1.0f);
	public static final Color GREEN_COLOUR_X = new Color((float) 80 / 255, (float) 134 / 255, (float) 0, 1.0f);
	public static final Color GREEN_COLOUR_Y = new Color((float) 119 / 255, (float) 192 / 255, (float) 0, 1.0f);
	public static final Color RED_COLOUR = new Color(1, 0, 0, 1.0f);
	public static final Color BLUE_COLOUR = new Color(0, 0, 1, 1.0f);
	// public static final Color BACKGROUND_COLOUR = new Color((float) 58 / 255,
	// (float) 3 / 255, (float) 175 / 255, 1.0f);
	public static final Color BACKGROUND_COLOUR = new Color(0f, 0f, 0f, 1.0f);
	public static final Color DOTTED_LINE_OPAQUE = new Color((float) 180 / 255, (float) 180 / 255, (float) 180 / 255,
			1.0f);
	public static final Color DOTTED_LINE_TRANSPARENT = new Color((float) 180 / 255, (float) 180 / 255,
			(float) 180 / 255, 0.0f);

	public static final String TEXTURE_ATLAS_LIBGDX_UI = "uiskin2.atlas";
	// Location of description file for skins
	public static final String SKIN_LIBGDX_UI = "uiskin2.json";

	public static enum BlockColor {
		white, green, red, blue
	};

	public static enum fontSize {
		small, medium, large
	};

	public enum State {
		PAUSE, RUN, RESUME, STOPPED
	}

	// public enum ImageType {
	// PAUSE, RUN, RESUME, STOPPED
	// }

}
