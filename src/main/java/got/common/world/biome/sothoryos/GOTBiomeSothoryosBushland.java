package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeSothoryosBushland extends GOTBiomeSothoryosBase {
	private static final WorldGenerator BOULDER_GEN = new GOTWorldGenBoulder(Blocks.stone, 0, 1, 3);
	private static final GOTWorldGenBoulder TERMITE_GEN = new GOTWorldGenBoulder(GOTBlocks.termiteMound, 0, 1, 4);

	public GOTBiomeSothoryosBushland(int i, boolean major) {
		super(i, major);
		preseter.setupBushlandView();
		preseter.setupBushlandFlora();
		preseter.setupBushlandFauna();
		preseter.setupSouthernTrees(true);

		decorator.addSettlement(new GOTStructureSothoryosSettlement(this, 1.0f));

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosBushland;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (random.nextInt(32) == 0) {
			int boulders = 1 + random.nextInt(4);
			for (int l = 0; l < boulders; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				int j1 = world.getHeightValue(i1, k1);
				BOULDER_GEN.generate(world, random, i1, j1, k1);
			}
		}
		if (random.nextInt(16) == 0) {
			int termites = 1 + random.nextInt(4);
			for (int l = 0; l < termites; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				int j1 = world.getHeightValue(i1, k1);
				TERMITE_GEN.generate(world, random, i1, j1, k1);
				for (int x = 0; x < 5; ++x) {
					int k2 = k1 - random.nextInt(5) + random.nextInt(5);
					int i2 = i1 - random.nextInt(5) + random.nextInt(5);
					int j2 = world.getHeightValue(i2, k2);
					if (world.getBlock(i2, j2 - 1, k1).isOpaqueCube()) {
						int j3 = j2 + random.nextInt(4);
						for (int j4 = j2; j4 <= j3; ++j4) {
							world.setBlock(i2, j4, k2, GOTBlocks.termiteMound);
							world.getBlock(i2, j4 - 1, k2).onPlantGrow(world, i2, j4 - 1, k2, i2, j4 - 1, k2);
						}
					}
				}
			}
		}
	}
}