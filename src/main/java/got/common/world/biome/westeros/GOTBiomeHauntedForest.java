package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.entity.animal.GOTEntityDirewolf;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeHauntedForest extends GOTBiomeWesteros {
	public GOTBiomeHauntedForest(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		fillerBlock = Blocks.snow;
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDirewolf.class, 20, 1, 2));
		decorator.setTreesPerChunk(10);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		decorator.addSettlement(new GOTStructureWildlingSettlement(this, 1.0f));
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 9).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(9).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(1).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_GUARDIAN, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		Collection<GOTSpawnListContainer> c5 = new ArrayList<>();
		c5.add(GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c5);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterHauntedForest;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BEYOND_WALL;
	}

	@Override
	public int getWallTop() {
		return 150;
	}
}