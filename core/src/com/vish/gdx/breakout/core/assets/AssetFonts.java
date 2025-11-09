package com.vish.gdx.breakout.core.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class AssetFonts {
	public BitmapFont defaultMicro;
	public BitmapFont defaultSmall;
	public BitmapFont defaultMiddle;
	public BitmapFont defaultBig;

	public AssetFonts(FileHandle fileHandle) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fileHandle);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		defaultMicro = getBitmapFont(generator, parameter, 18);
		defaultSmall = getBitmapFont(generator, parameter, 18);
		defaultMiddle = getBitmapFont(generator, parameter, 35);
		defaultBig = getBitmapFont(generator, parameter, 55);
	}

	private BitmapFont getBitmapFont(FreeTypeFontGenerator generator, FreeTypeFontParameter parameter, int size) {
		parameter.size = size; // font size
		BitmapFont bitmapFont = generator.generateFont(parameter);
		bitmapFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return bitmapFont;
	}

	public BitmapFont getfont(fontSize large) {
		switch (large) {
		case micro:
			return defaultMicro;
		case small:
			return defaultSmall;
		case medium:
			return defaultMiddle;
		case large:
			return defaultBig;
		default:
			return null;
		}
	}

	public static enum fontSize {
		micro,small, medium, large
	}


	public BitmapFont getfont(String size) {
		switch (size) {
		case "small":
			return defaultSmall;
		case "medium":
			return defaultMiddle;
		case "large":
			return defaultBig;
		default:
			return null;
		}
	}

	public void dispose() {
		if (defaultMicro != null) {
			defaultMicro.dispose();
		}
		if (defaultSmall != null) {
			defaultSmall.dispose();
		}
		if (defaultMiddle != null) {
			defaultMiddle.dispose();
		}
		if (defaultBig != null) {
			defaultBig.dispose();
		}
	}
}
