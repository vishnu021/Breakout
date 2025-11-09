package com.vish.gdx.breakout.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.vish.gdx.breakout.utils.Constants.*;

public class RotatingImageActor extends Image {
	private static final String TAG = RotatingImageActor.class.getName();

	float rotation = 1f;

	public RotatingImageActor(TextureRegion texture) {
		super(texture);
		this.scaleBy(0.5f);
		this.setColor(this.getColor().r, this.getColor().g, this.getColor().b, 0.3f);
		this.setPosition((GAME_WIDTH - getWidth()) / 2, (GAME_HEIGHT - getHeight()) / 2);
		this.setOrigin(getWidth() / 2, getHeight() / 2);
		Gdx.app.debug(TAG, "origin : " + this.getOriginX() + " " + this.getOriginY());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public void next() {
		rotation += 0.2f;
		this.setRotation(rotation);
	}

}
