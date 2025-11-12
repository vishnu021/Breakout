package com.vish.gdx.breakout;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vish.gdx.breakout.actors.BallGroup;
import com.vish.gdx.breakout.actors.BlockGroup;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.actors.RotatingImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import com.vish.gdx.breakout.layers.BlockAnimationLayer;
import com.vish.gdx.breakout.layers.GameDetailsGroup;
import com.vish.gdx.breakout.layers.GameOverLayer;
import static com.vish.gdx.breakout.utils.Constants.*;
import com.vish.gdx.breakout.utils.WorldContactListener;

public class GameStage extends Stage implements GestureListener, Serializable {
	private static final String TAG = GameStage.class.getName();

	// debug
	boolean debug = false;
	Matrix4 debugMatrix;
	Box2DDebugRenderer debugRenderer;

	public ImageActor slider;
	public ImageActor dottedLine;
	public ImageActor pauseCover;

	public BallGroup ballGroup;
	public RotatingImageActor background;
	public BlockGroup blockGroup;
	private GameOverLayer gameOverTable;
	Skin skinLibgdx;
	boolean paused = false;
	public State gameState;

	// public int maxScore;
	Body worldBoundary;
	ArrayList<Body> bodies;
	public GameDetailsGroup gameDetailsGroup;
	public BlockAnimationLayer animationLayer;

	float initialX = 0;
	float initialY = 0;

	// physics objects
	World world;
	private static final float TIME_STEP = 1 / 60f;
	private static final int VELOCITY_ITERATIONS = 6;
	private static final int POSITION_ITERATIONS = 2;
	private float accumulator = 0;

	InputMultiplexer multiplexer;

	public GameStage(Viewport viewPort) {
		super(viewPort);
		initialise();
		blockGroup = new BlockGroup(world, Assets.INSTANCE.getPreferences().getMaxScore(), this);
		gameDetailsGroup.setMultiplierValue(blockGroup.ballCount);
		blockGroup.nextStep();
	}

	public GameStage() {
		super(new StretchViewport(GAME_WIDTH, GAME_HEIGHT));
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		initialise();
		Gdx.app.debug(TAG, "Reading GameStage");
		blockGroup = json.readValue("blockGroup", BlockGroup.class, jsonData);
		blockGroup.gameStage = this;
		blockGroup.world = this.world;
		blockGroup.load();
		gameDetailsGroup.setStepCounterValue(blockGroup.step);
		gameDetailsGroup.setMultiplierValue(blockGroup.ballCount);
		blockGroup.updateBlocks();
	}

	private void initialise() {
		// this.setDebugAll(true);
		skinLibgdx = Assets.INSTANCE.getSkinLibgdx();
		world = new World(new Vector2(0, 0f), true);
		world.setContactListener(new WorldContactListener());
		if (debug)
			debugRenderer = new Box2DDebugRenderer();

		initialiseActors();

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);

		GestureDetector gestureDetector = new GestureDetector(this);
		multiplexer.addProcessor(gestureDetector);

		resetInputProcessor();
	}

	public void resetInputProcessor() {
		Gdx.input.setInputProcessor(multiplexer);
		Gdx.graphics.setContinuousRendering(true);
		pauseCover.setVisible(false);
	}

	public void addActorsToStage() {
		// Actor 1 >> Background
		addActor(background);
		// Actor 2 >> Slider
		addActor(slider);
		// // // Actor 3 >> BlockGroup
		addActor(blockGroup);
		// // // Actor 4 >> Dotted Lines
		addActor(dottedLine);
		// // // Actor 5 >> Upper/LowerMargin/BallfontActor
		this.addActor(animationLayer);
		addActor(gameDetailsGroup);
		addActor(ballGroup);
		addActor(pauseCover);

	}

	private void initialiseActors() {
		addBoundary();
		// creating actors
		slider = new ImageActor(Assets.INSTANCE.getTexture(BALL_TEXTURE));
		slider.setName(SLIDER);
		slider.setSize(BALL_SIZE, BALL_SIZE);
		slider.setX((GAME_WIDTH - slider.getWidth()) / 2);
		slider.setY(LOWER_MARGIN);

		gameDetailsGroup = new GameDetailsGroup(this);
		ballGroup = new BallGroup(world, gameDetailsGroup);

		background = new RotatingImageActor(Assets.INSTANCE.getBackground());

		dottedLine = new ImageActor(Assets.INSTANCE.getDynamicAssets().createDottedLine());
		dottedLine.setVisible(false);

		pauseCover = Assets.INSTANCE.getDynamicAssets().createRectangleImage(GAME_WIDTH, GAME_HEIGHT, Color.BLUE, 0.7f);
		pauseCover.setVisible(false);

		animationLayer = new BlockAnimationLayer();
	}

	private void addBoundary() {
		bodies = new ArrayList<Body>();
		// Right Boundary : (480,0) <-> (480,800)
		bodies.add(getEdge(GAME_WIDTH / PIXELS_TO_METERS, 0f, GAME_WIDTH / PIXELS_TO_METERS,
				GAME_HEIGHT / PIXELS_TO_METERS));
		// Left boundary : (480,0) <-> (480,800)
		bodies.add(getEdge(0f, 0f, 0f, GAME_HEIGHT / PIXELS_TO_METERS));
		// Top boundary : (0,UPPER_MARGIN) <-> (GAME_WIDTH,UPPER_MARGIN)
		bodies.add(getEdge(0f, UPPER_MARGIN / PIXELS_TO_METERS, GAME_WIDTH / PIXELS_TO_METERS,
				UPPER_MARGIN / PIXELS_TO_METERS));
		// Lower boundary : (0,0) <-> (GAME_WIDTH,UPPER_MARGIN)
		// bodies.add(getEdge(0f, (LOWER_MARGIN - BORDER) / PIXELS_TO_METERS, GAME_WIDTH
		// / PIXELS_TO_METERS,
		// (LOWER_MARGIN - BORDER) / PIXELS_TO_METERS));
	}

	private Body getEdge(float v1X, float v1Y, float v2X, float v2Y) {
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(def);

		// creating shape
		EdgeShape edge = new EdgeShape();
		edge.set(v1X, v1Y, v2X, v2Y);

		// Creating Fixture for shape Edge
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;

		fixtureDef.filter.categoryBits = WORLD_ENTITY;
		fixtureDef.filter.maskBits = PHYSICS_ENTITY;

		body.createFixture(fixtureDef);
		edge.dispose();

		return body;
	}

	@Override
	public void draw() {
		super.draw();
		if (debug) {
			debugMatrix = this.getBatch().getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
			debugRenderer.render(world, debugMatrix);
		}
	}

	@Override
	public void act(float delta) {
		if (gameState == State.RUN) {
			super.act(delta);

			// Fixed timestep physics simulation
			float frameTime = Math.min(delta, 0.25f);
			accumulator += frameTime;

			int stepMultiplier = gameDetailsGroup.accelerated ? 3 : 1;

			while (accumulator >= TIME_STEP) {
				for (int i = 0; i < stepMultiplier; i++) {
					world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
					background.next();
				}
				accumulator -= TIME_STEP;
			}

			// Clean up destroyed physics bodies
			blockGroup.clearPhysics();

			// Game state updates
			if (blockGroup.gameOver) {
				if (gameOverTable == null) {
					gameOverTable = new GameOverLayer(blockGroup.step - 1);
					gameOverTable.setSize(GAME_WIDTH, GAME_HEIGHT);
					this.addActor(gameOverTable);
				}
				slider.setVisible(false);
				ballGroup.setVisible(false);
				dottedLine.setVisible(false);
				gameDetailsGroup.setStepCounterValue(1);
				gameDetailsGroup.updateBestScore();
			} else if (ballGroup.launchComplete && !ballGroup.hasChildren()) {
				Gdx.app.log("GameStage", "Ball action complete");
				gameDetailsGroup.setStepCounterValue(++blockGroup.step);
				gameDetailsGroup.setMultiplierValue(blockGroup.ballCount);
				blockGroup.nextStep();
				ballGroup.launchComplete = false;
			}
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) { // System.out.println("touch down");
		if (blockGroup.gameOver) {
			gameOverTable.remove();
			gameOverTable = null;
			addBoundary();
			blockGroup.step = 1;
			blockGroup.ballCount = 1;
			blockGroup.gameOver = false;
			blockGroup.setVisible(true);
			ballGroup.setVisible(true);
			slider.setVisible(true);
			gameDetailsGroup.setMultiplierValue(blockGroup.ballCount);
			blockGroup.nextStep();
		}
		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (!blockGroup.hasActions() && !ballGroup.hasChildren()) {
			if (initialX == 0) {
				initialX = x;
				initialY = y;
			}
			float theta = MathUtils.atan2(initialY - y, initialX - x) * MathUtils.radiansToDegrees;
			theta = Math.abs(theta);
			if (theta > MIN_LAUNCH_ANGLE && theta < MAX_LAUNCH_ANGLE) {
				dottedLine.setVisible(true);
				dottedLine.setX(slider.getX() + slider.getWidth() / 2);
				dottedLine.setY(slider.getY() + slider.getHeight() / 2);
				dottedLine.setRotation(180 - theta);
			} else {
				dottedLine.setVisible(false);
			}
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (!blockGroup.hasActions() && !ballGroup.hasChildren() && initialX != 0 && dottedLine.isVisible()) {
			float theta = dottedLine.getRotation() * (MathUtils.PI / 180);
			if (Math.abs(theta) < (15 * MathUtils.PI / 180)) {
				theta = 15 * (MathUtils.PI / 180);
			}
			ballGroup.theta = theta;
			ballGroup.addBallActors(slider.getX(), slider.getY(), blockGroup.ballCount);
		}
		dottedLine.setPosition(1000, 1000);
		dottedLine.setRotation(0);
		initialX = 0;
		initialY = 0;
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {
	}

	@Override
	public void write(Json json) {
		json.writeValue("blockGroup", blockGroup);
	}

	/**
	 * Dispose resources - ONLY called when app closes or screen is permanently disposed.
	 * NOT called during normal gameplay or game over.
	 * This is where GC is allowed to happen.
	 */
	@Override
	public void dispose() {
		Gdx.app.debug(TAG, "Disposing GameStage - clearing pools and freeing resources");

		// Dispose ball pool - allows GC to reclaim memory
		if (ballGroup != null) {
			ballGroup.dispose();
		}

		// Dispose physics world
		if (world != null) {
			world.dispose();
		}

		// Dispose debug renderer if used
		if (debugRenderer != null) {
			debugRenderer.dispose();
		}

		super.dispose();
	}

	@Override
	public String toString() {
		return "GameStage [slider=" + slider + ", dottedLine=" + dottedLine + ", pauseCover=" + pauseCover
				+ ", ballGroup=" + ballGroup + ", background=" + background + ", blockGroup=" + blockGroup
				+ ", gameOverTable=" + gameOverTable + ", paused=" + paused + ", gameState=" + gameState
				+ ",  initialX=" + initialX + ", initialY=" + initialY + "]";
	}

}
