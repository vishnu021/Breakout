package com.vish.gdx.breakout.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.vish.gdx.breakout.blocks.AbstractBlock.BlockType;

public class SoundManager {

	Sound blockHitSound;
	GamePreferences preferences;

	// public SoundManager() {
	// super();
	// blockHitSound =
	// Gdx.audio.newSound(Gdx.files.internal("Sounds/blockHitSound.mp3"));
	// }

	public SoundManager(GamePreferences preferences) {
		this.preferences = preferences;
		blockHitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/blockHitSound.mp3"));
	}

	public Sound getBlockHitSound() {
		return blockHitSound;
	}

	public void setBlockHitSound(Sound blockHitSound) {
		this.blockHitSound = blockHitSound;
	}

	public void play(BlockType blockType) {
		if (preferences.sound) {
			switch (blockType) {
			case PHYSICAL_BLOCK:
				blockHitSound.play();
				break;
			case BONUS_BLOCK:
				break;
			case EMPTY_BLOCK:
				break;
			default:
				break;
			}
		}
	}

	public void dispose() {
		if (blockHitSound != null) {
			blockHitSound.dispose();
		}
	}
}
