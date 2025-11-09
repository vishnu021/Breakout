package com.vish.gdx.breakout.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vish.gdx.breakout.utils.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class FontActor extends Actor {
	private Matrix4 matrix = new Matrix4();
	private BitmapFontCache bitmapFontCache;
	public  GlyphLayout glplayout;

	public FontActor(float posX, float posY, String fontText, fontSize size) {
		BitmapFont fnt = Assets.instance.getfont(size);
		bitmapFontCache = new BitmapFontCache(fnt);
		glplayout = bitmapFontCache.setText(fontText, 0, 0);
		setPosition(posX, posY);
		setOrigin(glplayout.width / 2, -glplayout.height / 2);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		Color color = getColor();
		bitmapFontCache.setColor(color.r, color.g, color.b, color.a * alpha);
		matrix.idt();
		matrix.translate(getX(), getY(), 0);
		matrix.rotate(0, 0, 1, getRotation());
		matrix.scale(getScaleX(), getScaleY(), 1);
		matrix.translate(-getOriginX(), -getOriginY(), 0);
		batch.setTransformMatrix(matrix);
		bitmapFontCache.draw(batch);

	}

	public void setAlpha(int a) {
		Color color = getColor();
		setColor(color.r, color.g, color.b, a);
	}

	public void setText(String newFontText) {
		glplayout = bitmapFontCache.setText(newFontText, 0, 0);
		setOrigin(glplayout.width / 2, -glplayout.height / 2);

	}

	@Override
	public String toString() {
		return "FontActor [position : (" + getX() + "," + getY() + "), glplayout=" + glplayout
				+ "]";
	}

}