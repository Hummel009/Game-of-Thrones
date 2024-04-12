package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeIsleOfFaces extends GOTBiomeWesteros {
	public GOTBiomeIsleOfFaces(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FLOWERS, 1.0f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WEIRWOOD, 10);
		decorator.setTreesPerChunk(1);
		decorator.setFlowersPerChunk(20);
		decorator.setDoubleFlowersPerChunk(12);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(3);
		addFlower(Blocks.red_flower, 0, 80);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIsleOfFaces;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.RIVERLANDS;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
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