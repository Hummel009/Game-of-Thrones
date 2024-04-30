package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeReachFireField extends GOTBiomeReach {
	public GOTBiomeReachFireField(int i, boolean major) {
		super(i, major);
		setupStandardDomesticFauna();
		biomeVariants.clear();
		decorator.setTreesPerChunk(0);
		decorator.setFlowersPerChunk(20);
		decorator.setDoubleFlowersPerChunk(12);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(3);
		addFlower(Blocks.red_flower, 0, 80);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterReachFireField;
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		if (random.nextInt(5) > 0) {
			WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
			doubleFlowerGen.func_150548_a(4);
			return doubleFlowerGen;
		}
		return super.getRandomWorldGenForDoubleFlower(random);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_PAVING.setHasFlowers(true);
	}
}