package com.vish.gdx.breakout.blocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.vish.gdx.breakout.actors.BlockGroup;
import static com.vish.gdx.breakout.utils.Constants.*;

public abstract class AbstractBlock extends Group implements Serializable {

	public Actor blockImage;
	protected boolean destroy = false;
	public BlockGroup blockGroup;
	// Vector2 position;
	float x;
	float y;
	public BlockType blockType;

	public enum BlockType {
		EMPTY_BLOCK, BONUS_BLOCK, PHYSICAL_BLOCK, HORIZONTAL_CLEARER, VERTICAL_CLEARER, DOUBLE_CLEAR_BLOCK
	}

	public transient World world;
	public transient Body body;

	public abstract void decreaseCount();

	public AbstractBlock(BlockType blockType, BlockGroup blockGroup, float x, float y, World world, Actor blockImage) {
		this.blockType = blockType;
		this.blockGroup = blockGroup;
		this.x = x;
		this.y = y;
		this.world = world;
		this.blockImage = blockImage;
		this.x = this.x + (BLOCK_SIZE_WITH_MARGIN - this.blockImage.getWidth()) / 2;
		this.y = this.y + (BLOCK_SIZE_WITH_MARGIN - this.blockImage.getHeight()) / 2;
		initialise();
	}

	public AbstractBlock() {
		super();
	}

	private void initialise() {
		addActor(blockImage);
		this.setPosition(this.x, this.y);
		addPhysics(this.x, this.y);
	}

	public void load(World world, Actor blockImage) {
		this.world = world;
		this.blockImage = blockImage;
		initialise();
	}

	@Override
	public void write(Json json) {
		json.writeValue("x", x);
		json.writeValue("y", y);
		json.writeValue("blockType", blockType);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		x = json.readValue("x", int.class, jsonData);
		y = json.readValue("y", int.class, jsonData);
		blockType = json.readValue("blockType", BlockType.class, jsonData);
	}

	public void addPhysics(float x, float y) {
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.StaticBody;
		def.position.set(x / (PIXELS_TO_METERS), y / (PIXELS_TO_METERS));
		body = world.createBody(def);
		addFixture();
		body.setUserData(this);
	}

	public abstract void addFixture();

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (this.getActions().size == 0) {
			this.x = this.getX();
			this.y = this.getY();
		}
	}

	public void step() {
		this.addAction(Actions.moveTo(getX(), getY() - BLOCK_SIZE_WITH_MARGIN, (float) 0.5));
		body.setTransform(
				new Vector2(body.getPosition().x, body.getPosition().y - BLOCK_SIZE_WITH_MARGIN / PIXELS_TO_METERS), 0);
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public BlockGroup getBlockGroup() {
		return blockGroup;
	}

	public void setBlockGroup(BlockGroup blockGroup) {
		this.blockGroup = blockGroup;
	}

	public abstract void destroyEffect();

}
