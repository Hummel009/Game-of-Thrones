package got.common.world.biome.westeros;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.map.*;
import got.common.world.structure.westeros.reach.GOTStructureReachCity;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeFireField extends GOTBiomeReach {
	public GOTBiomeFireField(int i, boolean major) {
		super(i, major);
		decorator.clearVillages();

		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 20;
		decorator.doubleFlowersPerChunk = 12;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 3;
		addFlower(Blocks.red_flower, 0, 80);

		GOTStructureReachCity castle = new GOTStructureReachCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Highgarden, 0, -1);
		decorator.affix(castle);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_FIRE_FIELD;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("fireField");
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
		return GOTBezierType.PAVING.setHasFlowers(true);
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}