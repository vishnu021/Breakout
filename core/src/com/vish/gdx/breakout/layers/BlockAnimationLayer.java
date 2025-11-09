package com.vish.gdx.breakout.layers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.vish.gdx.breakout.core.actors.ImageActor;
import com.vish.gdx.breakout.core.assets.Assets;
import static com.vish.gdx.breakout.utils.Constants.*;

public class BlockAnimationLayer extends Group {
	private List<Actor> horizontalBeam;
	private List<Actor> verticalBeam;

	public BlockAnimationLayer() {
		super();
		horizontalBeam = new ArrayList<Actor>();
		verticalBeam = new ArrayList<Actor>();
	}

	public Actor getHorizontalBeamfromPool() {
		if (horizontalBeam.size() > 0) {
			Actor existingBeam = horizontalBeam.get(0);
			horizontalBeam.remove(existingBeam);
			existingBeam.setVisible(true);
//			this.addActor(existingBeam);
			return existingBeam;
		} else {
			Actor newBeam = new ImageActor(
					Assets.INSTANCE.getDynamicAssets().getTexture(GAME_WIDTH, BORDER, Color.SKY));
			this.addActor(newBeam);
			return newBeam;
		}
	}

	public void returnHorizontalBeamtoPool(Actor actor) {
		actor.setVisible(false);
		this.horizontalBeam.add(actor);
	}

	public Actor getVerticalBeamfromPool() {
		if (verticalBeam.size() > 0) {
			Actor existingBeam = verticalBeam.get(0);
			verticalBeam.remove(existingBeam);
			existingBeam.setVisible(true);
			return existingBeam;
		} else {
			Actor newBeam = new ImageActor(
					Assets.INSTANCE.getDynamicAssets().getTexture(BORDER, GAME_HEIGHT, Color.SKY));
			this.addActor(newBeam);
			return newBeam;
		}
	}

	public void returnVerticalBeam(Actor actor) {
		actor.setVisible(false);
		this.verticalBeam.add(actor);
	}

}
