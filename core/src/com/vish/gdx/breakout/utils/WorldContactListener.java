package com.vish.gdx.breakout.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.vish.gdx.breakout.blocks.AbstractBlock;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA.getBody().getUserData() != null && fixtureA.getBody().getUserData() instanceof AbstractBlock) {
			((AbstractBlock) fixtureA.getBody().getUserData()).decreaseCount();
		} else if (fixtureB.getBody().getUserData() != null
				&& fixtureB.getBody().getUserData() instanceof AbstractBlock) {
			((AbstractBlock) fixtureB.getBody().getUserData()).decreaseCount();
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
