package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenStreams;
import got.common.world.feature.GOTWorldGenVolcanoCrater;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeThennLand extends GOTBiomeHauntedForest {
	public GOTBiomeThennLand(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.FOTINIA, 20);
		decorator.addSettlement(new GOTStructureWildlingSettlement(this, 1.0f).type(GOTStructureWildlingSettlement.Type.THENN, 6));
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_THENN, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i12;
		int j1;
		int l;
		int k13;
		super.decorate(world, random, i, k);
		GOTWorldGenStreams lavaGen = new GOTWorldGenStreams(Blocks.flowing_lava);
		for (l = 0; l < 250; ++l) {
			i12 = i + random.nextInt(16) + 8;
			k13 = k + random.nextInt(16) + 8;
			j1 = world.getHeightValue(i12, k13);
			lavaGen.generate(world, random, i12, j1, k13);
		}
		random.nextInt(1);
		int i1 = i + random.nextInt(16) + 8;
		int k1 = k + random.nextInt(16) + 8;
		j1 = world.getHeightValue(i1, k1);
		new GOTWorldGenVolcanoCrater().generate(world, random, i1, j1, k1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterThennLand;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}