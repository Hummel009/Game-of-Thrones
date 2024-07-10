package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeVolantisMarshes extends GOTBiomeEssosBase implements GOTBiome.Marshes {
	public GOTBiomeVolantisMarshes(int i, boolean major) {
		super(i, major);
		preseter.setupMarshesView();
		preseter.setupMarshesFlora();
		preseter.setupMarshesFauna();
		preseter.setupSouthernTrees(true);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VOLANTIS;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterVolantis;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}