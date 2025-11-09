package com.vish.gdx.breakout.core.assets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import static com.vish.gdx.breakout.utils.Constants.*;
import com.vish.gdx.breakout.utils.SkinLoader;
import com.vish.gdx.breakout.utils.Theme;

public class Assets implements Disposable, AssetErrorListener {

	private static final String TAG = Assets.class.getName();

	public static final Assets INSTANCE = new Assets();

	private AssetFonts fonts;
	@SuppressWarnings("unused")
	private AssetManager assetManager;
	private AssetCreator dynamicAssets;
	private AssetBackgroundImages backgroundImages;
	private Skin skinLibgdx;
	private GamePreferences preferences;
	Map<String, TextureRegion> textures;
	Theme theme;
	SoundManager sounds;

	public AssetManager getAssetManager() {
		return assetManager;
	}

	private Assets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Color getColorWithAlpha(Color color, float alpha) {
		return new Color(color.r, color.g, color.b, alpha);
	}

	public void init(AssetManager assetManager) {
		preferences = new GamePreferences();
		theme = new Theme();
		preferences.load();
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);

		assetManager.load(TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.finishLoading();

//		Gdx.app.debug(TAG, "# of assets loaded " + assetManager.getAssetNames().size);
//		for (String a : assetManager.getAssetNames())
//			Gdx.app.debug(TAG, " asset : " + a);

		TextureAtlas atlas = assetManager.get(TEXTURE_ATLAS_OBJECTS);
		for (Texture t : atlas.getTextures()) {
			t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		}

		backgroundImages = new AssetBackgroundImages(atlas);
		textures = new HashMap<String, TextureRegion>();
		for (AtlasRegion atlasRegion : atlas.getRegions()) {
			Gdx.app.debug(TAG, " atlasRegion : " + atlasRegion.name);
			textures.put(atlasRegion.name, atlasRegion);
		}
		fonts = new AssetFonts(Gdx.files.internal(Jellee_Roman));
		dynamicAssets = new AssetCreator();
		skinLibgdx = SkinLoader.loadSkin(textures);
		theme.init(textures);
		sounds = new SoundManager(preferences);
	}

	public GamePreferences getPreferences() {
		return preferences;
	}

	public SoundManager getSounds() {
		return sounds;
	}

	public void setSounds(SoundManager sounds) {
		this.sounds = sounds;
	}

	public TextureRegion getTexture(String name) {
		return textures.get(name);
	}

	public Skin getSkinLibgdx() {
		return skinLibgdx;
	}

	public void setSkinLibgdx(Skin skinLibgdx) {
		this.skinLibgdx = skinLibgdx;
	}

	public AssetFonts getFonts() {
		return fonts;
	}

	public void setFonts(AssetFonts fonts) {
		this.fonts = fonts;
	}

	public AssetCreator getDynamicAssets() {
		return dynamicAssets;
	}

	public void setDynamicAssets(AssetCreator dynamicAssets) {
		this.dynamicAssets = dynamicAssets;
	}

	public static Assets getInstance() {
		return INSTANCE;
	}

	public TextureRegion getBackground() {
		return backgroundImages.spiral;
	}

	public class AssetBackgroundImages {
		public AtlasRegion spiral;

		public AssetBackgroundImages(TextureAtlas atlas) {
			spiral = atlas.findRegion("spiral");
		}
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {

	}

	@Override
	public void dispose() {
		if (assetManager != null) {
			assetManager.dispose();
		}
		if (skinLibgdx != null) {
			skinLibgdx.dispose();
		}
		if (fonts != null) {
			fonts.dispose();
		}
		if (sounds != null) {
			sounds.dispose();
		}
	}

}
