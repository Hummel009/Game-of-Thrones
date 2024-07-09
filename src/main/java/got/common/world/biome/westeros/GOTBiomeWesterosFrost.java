package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeWesterosFrost extends GOTBiomeWesterosBase {
	public GOTBiomeWesterosFrost(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupPolarTrees(false);

		biomeWaypoints = GOTWaypoint.Region.BEYOND_WALL;
		biomeAchievement = GOTAchievement.enterWesterosFrost;
		enableRiver = false;
		chanceToSpawnAnimals = 0.1f;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		roadBlock = GOTBezierType.PATH_SNOWY;
	}
}