package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeGiftOld extends GOTBiomeGiftNew {
	public GOTBiomeGiftOld(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.treesPerChunk = -1;
		decorator.clearVillages();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		invasionSpawns.addInvasion(GOTInvasions.THENN, GOTEventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(GOTInvasions.WILDLING, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.GIANT, GOTEventSpawner.EventChance.RARE);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterGiftOld;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("giftOld");
	}

	@Override
	public int getWallTop() {
		return 150;
	}
}