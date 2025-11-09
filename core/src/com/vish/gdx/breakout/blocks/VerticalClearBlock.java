package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.vish.gdx.breakout.actors.BlockGroup;

public class VerticalClearBlock extends AbstractBlock {
	private static final String TAG = VerticalClearBlock.class.getName();

	public boolean touched = false;
	private Actor effectActor;

	public VerticalClearBlock(BlockGroup blockGroup, float x, float y, World world, Actor blockImage) {
		super(BlockType.VERTICAL_CLEARER, blockGroup, x, y, world, blockImage);
		Gdx.app.debug(TAG, "Constructor called");
	}

	public VerticalClearBlock() {
		super();
		Gdx.app.debug(TAG, "Default Constructor called");
	}

	public void decreaseCount() {
		if (!touched) {
			effectActor = this.blockGroup.gameStage.animationLayer.getVerticalBeamfromPool();
			effectActor.setPosition(this.getX() + (this.blockImage.getWidth() - effectActor.getWidth()) / 2, 0);
			touched = true;
		}

		SequenceAction sequenceAction = new SequenceAction();
		Action dimAction = Actions.alpha(0, 0.02f);
		Action brightAction = Actions.alpha(1, 0.02f);

		sequenceAction.addAction(brightAction);
		sequenceAction.addAction(dimAction);

		if (this.effectActor.getActions().size == 0) {
			effectActor.addAction(sequenceAction);
		}

		for (Actor act : this.blockGroup.getChildren()) {
			if (act instanceof PhysicalBlock) {
				if ((act.getX() < this.getX()) && (act.getX() + BLOCK_SIZE) > this.getX()) {
					((PhysicalBlock) act).decreaseCount();
				}
			}
		}

	}

	@Override
	public void step() {
		super.step();
		if (touched) {
			blockGroup.deleteBlock.add(this);
			this.blockGroup.gameStage.animationLayer.returnVerticalBeam(effectActor);

		}
	}

	@Override
	public void addPhysics(float x, float y) {
		x += blockImage.getWidth() / 2;
		y += blockImage.getHeight() / 2;
		super.addPhysics(x, y);
	}

	@Override
	public void addFixture() {
		CircleShape circle = new CircleShape();
		circle.setRadius(blockImage.getHeight() / (2 * PIXELS_TO_METERS));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.filter.categoryBits = WORLD_ENTITY;
		fixtureDef.filter.maskBits = PHYSICS_ENTITY;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		circle.dispose();
	}

	@Override
	public void destroyEffect() {
		this.remove();
	}

}
