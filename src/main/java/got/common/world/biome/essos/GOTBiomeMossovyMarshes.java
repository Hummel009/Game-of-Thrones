package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenMarshLights;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;

import java.util.Random;

public class GOTBiomeMossovyMarshes extends GOTBiomeMossovy implements GOTBiome.Marshes {
	public GOTBiomeMossovyMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		variantChance = 1.0f;
		decorator.setSandPerChunk(0);
		decorator.setQuagmirePerChunk(1);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(3);
		decorator.setFlowersPerChunk(0);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(8);
		decorator.setCanePerChunk(10);
		decorator.setReedPerChunk(5);
		decorator.clearSettlements();
		decorator.clearStructures();
		npcSpawnList.clear();
	}

	@Override
	@SuppressWarnings("StatementWithEmptyBody")
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int i1;
		int j1;
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 6; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			new WorldGenFlowers(GOTBlocks.deadMarshPlant).generate(world, random, i1, j1, k1);
		}
		for (l = 0; l < 4; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			for (j1 = 128; j1 > 0 && world.isAirBlock(i1, j1 - 1, k1); --j1) {
			}
			new GOTWorldGenMarshLights().generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterMossovyMarshes;
	}
}