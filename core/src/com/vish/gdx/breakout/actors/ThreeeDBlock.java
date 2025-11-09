package com.vish.gdx.breakout.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vish.gdx.breakout.core.actors.ImageActor;
import static com.vish.gdx.breakout.utils.Constants.*;

public class ThreeeDBlock extends Group {
	public ImageActor frontBlock;
	public ImageActor backBlock;

	public ThreeeDBlock(ImageActor frontBlock, ImageActor backBlock) {
		super();
		this.frontBlock = frontBlock;
		this.backBlock = backBlock;
		this.addActor(this.backBlock);
		this.addActor(this.frontBlock);
		frontBlock.setPosition(BLOCK_MARGIN, BLOCK_MARGIN);
	}

	@Override
	public void setPosition(float x, float y) {
		backBlock.setY(BLOCK_MARGIN + BLOCK_MARGIN_WITHOUT_OFFSET
				- 2 * (y / (GAME_HEIGHT - BLOCK_SIZE_WITH_MARGIN)) * BLOCK_MARGIN_WITHOUT_OFFSET);
		backBlock.setX(BLOCK_MARGIN + BLOCK_MARGIN_WITHOUT_OFFSET
				- 2 * (x / (GAME_WIDTH - BLOCK_SIZE_WITH_MARGIN)) * BLOCK_MARGIN_WITHOUT_OFFSET);
		super.setPosition(x, y);
	}

	@Override
	public void setColor(Color color) {
		frontBlock.setColor(color);
		backBlock.setColor(color);
		super.setColor(color);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public void updateY(float x, float y) {
		backBlock.setY(BLOCK_MARGIN + BLOCK_MARGIN_WITHOUT_OFFSET
				- 2 * (y / (GAME_HEIGHT - BLOCK_SIZE_WITH_MARGIN)) * BLOCK_MARGIN_WITHOUT_OFFSET);
		backBlock.setX((BLOCK_MARGIN + BLOCK_MARGIN_WITHOUT_OFFSET
				- 2 * (x / (GAME_WIDTH - BLOCK_SIZE_WITH_MARGIN)) * BLOCK_MARGIN_WITHOUT_OFFSET) );
	}

	public void parentPosition(float x, float y) {

	}

	public ImageActor getFrontBlock() {
		return frontBlock;
	}

	public void setFrontBlock(ImageActor frontBlock) {
		this.frontBlock = frontBlock;
	}

	public ImageActor getBackBlock() {
		return backBlock;
	}

	public void setBackBlock(ImageActor backBlock) {
		this.backBlock = backBlock;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

}
