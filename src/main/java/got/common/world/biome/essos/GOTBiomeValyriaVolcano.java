package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.feature.GOTWorldGenStreams;
import got.common.world.feature.GOTWorldGenVolcanoCrater;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class GOTBiomeValyriaVolcano extends GOTBiomeValyria {
	public GOTBiomeValyriaVolcano(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.stone;
		fillerBlock = Blocks.stone;
		decorator.setTreesPerChunk(0);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreValyrian, 3), 2.0f, 0, 16);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int i12;
		int j1;
		int l;
		super.decorate(world, random, i, k);
		GOTWorldGenStreams lavaGen = new GOTWorldGenStreams(Blocks.flowing_lava);
		for (l = 0; l < 250; ++l) {
			i12 = i + random.nextInt(16) + 8;
			j1 = 40 + random.nextInt(120);
			int k13 = k + random.nextInt(16) + 8;
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
		return GOTAchievement.enterValyriaVolcano;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}