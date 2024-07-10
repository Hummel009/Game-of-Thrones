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
		preseter.setupDomesticFaunaOverride();

		biomeVariants.clear();

		decorator.setTreesPerChunk(1);
		decorator.setFlowersPerChunk(20);
		decorator.setDoubleFlowersPerChunk(12);
		addFlower(Blocks.red_flower, 0, 80);

		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 80));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterReachFireField;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_PAVING.setHasFlowers(true);
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
}