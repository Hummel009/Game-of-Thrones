package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeEssosMarshes extends GOTBiomeEssosBase implements GOTBiome.Marshes {
	public GOTBiomeEssosMarshes(int i, boolean major) {
		super(i, major);
		preseter.setupMarshesView();
		preseter.setupMarshesFlora();
		preseter.setupMarshesFauna();
		preseter.setupSouthernTrees(true);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}
}