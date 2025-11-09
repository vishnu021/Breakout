package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.vish.gdx.breakout.actors.BlockGroup;
import com.vish.gdx.breakout.blocks.AbstractBlock.BlockType;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class BlockCreatorFactory {
	private static final String TAG = BlockCreatorFactory.class.getName();

	public AbstractBlock createBlock() {
		return null;
	}

	public AbstractBlock createBlock(BlockType blockType, World world, int blockValue, BlockGroup blockGroup, int i) {
		switch (blockType) {
		case EMPTY_BLOCK:
			break;
		case BONUS_BLOCK:
			return new BonusBallBlock(blockGroup, i * BLOCK_SIZE_WITH_MARGIN, UPPER_MARGIN - BLOCK_SIZE_WITH_MARGIN,
					world, createBlockImage(blockType));
		case PHYSICAL_BLOCK:
			return new PhysicalBlock(world, i * BLOCK_SIZE_WITH_MARGIN, UPPER_MARGIN - BLOCK_SIZE_WITH_MARGIN,
					blockValue, blockGroup, createBlockImage(blockType));
		case HORIZONTAL_CLEARER:
			return new HorizontalClearBlock(blockGroup, i * BLOCK_SIZE_WITH_MARGIN,
					UPPER_MARGIN - BLOCK_SIZE_WITH_MARGIN, world, createBlockImage(blockType));
		case DOUBLE_CLEAR_BLOCK:
			return new DoubleClearBlock(blockGroup, i * BLOCK_SIZE_WITH_MARGIN, UPPER_MARGIN - BLOCK_SIZE_WITH_MARGIN,
					world, createBlockImage(blockType));
		case VERTICAL_CLEARER:
			return new VerticalClearBlock(blockGroup, i * BLOCK_SIZE_WITH_MARGIN, UPPER_MARGIN - BLOCK_SIZE_WITH_MARGIN,
					world, createBlockImage(blockType));
		default:
			break;
		}
		return null;
	}

	public Actor createBlockImage(BlockType blockType) {
		if (blockType == null) {
			Gdx.app.error(TAG, "Null type for : ");
			return null;
		}
		switch (blockType) {
		case BONUS_BLOCK:
			return getResizedActor("bonus");
		case PHYSICAL_BLOCK:
			return Assets.INSTANCE.getDynamicAssets().create3DBlockGroup();
		case HORIZONTAL_CLEARER:
			return getResizedActor("horizontalArrow");
		case DOUBLE_CLEAR_BLOCK:
			return getResizedActor("doubleArrow");
		case VERTICAL_CLEARER:
			return getResizedActor("verticalArrow");
		case EMPTY_BLOCK:
			return null;
		default:
			Gdx.app.error(TAG, "Default type for : ");
			break;
		}
		return null;
	}

	private ImageActor getResizedActor(String texture) {
		ImageActor imgact = new ImageActor(Assets.INSTANCE.getTexture(texture));
		imgact.setSize(30f, 30f);
		return imgact;
	}

}
