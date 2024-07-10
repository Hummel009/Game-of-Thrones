package got.common.world.biome.other;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeLake extends GOTBiome {
	public GOTBiomeLake(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.OCEAN.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return null;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return null;
	}
}