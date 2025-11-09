package com.vish.gdx.breakout.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.layers.GameDetailsGroup;
import static com.vish.gdx.breakout.utils.Constants.*;

public class BallGroup extends Group {

	public float theta = (MathUtils.PI / 180) * 45;
	public boolean launchComplete = false;
	Task task;
	World world;
	int actionBallCount;
	GameDetailsGroup gameDetailsGroup;
	private final java.util.ArrayList<Ball> ballsToRemove = new java.util.ArrayList<Ball>();

	public BallGroup(World world, GameDetailsGroup gameDetailsGroup) {
		this.world = world;
		this.gameDetailsGroup = gameDetailsGroup;
	}

	public void addBallActors(final float x, final float y, final int actionBallCount) {
		if (actionBallCount <= 0) {
			com.badlogic.gdx.Gdx.app.error("BallGroup", "Invalid ball count: " + actionBallCount);
			return;
		}

		this.actionBallCount = actionBallCount - 1;
		task = Timer.schedule(new Task() {
			@Override
			public void run() {
				timerFunc(x, y);
			}
		}, 0, BALL_ENTRY_GAP_SECONDS, actionBallCount - 1);
	}

	private void timerFunc(float x, float y) {
		Ball ball = new Ball(world, Assets.INSTANCE.getTexture(BALL_TEXTURE), new Vector2(x, y), theta);
		this.addActor(ball);

		if (actionBallCount > 0) {
			gameDetailsGroup.setMultiplierValue(actionBallCount--);
		} else {
			gameDetailsGroup.setMultiplierValue(0);
		}

		if (!task.isScheduled()) {
			launchComplete = true;
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		cleanupBalls();
	}

	private void cleanupBalls() {
		for (Ball ball : ballsToRemove) {
			if (ball.body != null) {
				world.destroyBody(ball.body);
				ball.body = null;
			}
			ball.remove();
		}
		ballsToRemove.clear();
	}

	public void markBallForRemoval(Ball ball) {
		if (!ballsToRemove.contains(ball)) {
			ballsToRemove.add(ball);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public static class Ball extends Image {
		private World world;
		private Body body;
		private float theta;

		public Ball(World world, TextureRegion texture, Vector2 position, float theta) {
			super(texture);
			this.world = world;
			this.setSize(BALL_SIZE, BALL_SIZE);
			this.setScale(1f);
			this.theta = theta;
			addPhysics(position.x + BALL_SIZE / 2, position.y + BALL_SIZE / 2);
			this.setPosition(position.x, position.y);
			// System.out.println("creating new ball : " + this);
		}

		private void addPhysics(float x, float y) {
			BodyDef def = new BodyDef();
			def.type = BodyDef.BodyType.DynamicBody;
			def.position.set(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);
			body = world.createBody(def);
			addFixture(x, y);
		}

		private void addFixture(float x, float y) {
			CircleShape circle = new CircleShape();
			circle.setRadius(BALL_SIZE / (2 * PIXELS_TO_METERS));
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = circle;
			fixtureDef.density = 0.1f;
			fixtureDef.restitution = 1f;
			fixtureDef.friction = 0f;

			fixtureDef.filter.categoryBits = PHYSICS_ENTITY;
			fixtureDef.filter.maskBits = WORLD_ENTITY;

			body.createFixture(fixtureDef);
			body.setLinearVelocity(BALL_VELOCITY * MathUtils.cos(theta), BALL_VELOCITY * MathUtils.sin(theta));

			circle.dispose();
		}

		public void changeSpeed(float ballVelocity) {
			Vector2 vel = body.getLinearVelocity();
			body.setLinearVelocity(vel.x * ballVelocity, vel.y * ballVelocity);
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			if (body != null) {
				if ((body.getPosition().y < (LOWER_MARGIN - BORDER + this.getHeight()) / PIXELS_TO_METERS)
						&& (body.getLinearVelocity().y < 0)) {
					((BallGroup) getParent()).markBallForRemoval(this);
					return;
				}
				setPosition(body.getPosition().x * PIXELS_TO_METERS - getWidth() / 2,
						body.getPosition().y * PIXELS_TO_METERS - getHeight() / 2);

				if (Math.abs(body.getLinearVelocity().y) < 1f) {
					body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y
							+ (body.getLinearVelocity().y) / Math.abs(body.getLinearVelocity().y) * 0.05f);
				}
			}
		}

		@Override
		public String toString() {
			return "Ball [world=" + world + ", body=" + body + ", theta=" + theta + "," + " velocity : "
					+ body.getLinearVelocity() + "]";
		}

	}
}
