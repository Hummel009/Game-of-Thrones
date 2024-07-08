package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeWesterosFrost extends GOTBiomeWesterosBase {
	public GOTBiomeWesterosFrost(int i, boolean major) {
		super(i, major);
		banditChance = GOTEventSpawner.EventChance.NEVER;

		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupFrostTrees(false);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterWesterosFrost;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BEYOND_WALL;
	}
}