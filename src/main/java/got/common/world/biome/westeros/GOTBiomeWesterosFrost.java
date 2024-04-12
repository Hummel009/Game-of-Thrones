package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeWesterosFrost extends GOTBiomeWesteros {
	public GOTBiomeWesterosFrost(int i, boolean major) {
		super(i, major);
		setupFrostFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
		decorator.clearTrees();
		decorator.clearStructures();
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSnowyWasteland;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ICE;
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
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
	}

	@Override
	public int getWallTop() {
		return 150;
	}
}