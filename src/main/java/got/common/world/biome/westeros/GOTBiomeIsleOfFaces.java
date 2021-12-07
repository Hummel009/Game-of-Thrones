package got.common.world.biome.westeros;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.*;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeIsleOfFaces extends GOTBiomeWesteros {
	public GOTBiomeIsleOfFaces(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		invasionSpawns.clearInvasions();
		clearBiomeVariants();
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 8, 1, 2));
		enablePodzol = false;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WEIRWOOD, 10);
		decorator.treesPerChunk = 1;
		decorator.flowersPerChunk = 20;
		decorator.doubleFlowersPerChunk = 12;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 3;
		addFlower(Blocks.red_flower, 0, 80);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_FACES;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("isleOfFaces");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.RIVERLANDS;
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