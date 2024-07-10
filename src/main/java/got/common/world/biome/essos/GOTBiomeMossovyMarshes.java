package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTWorldGenMarshLights;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;

import java.util.Random;

public class GOTBiomeMossovyMarshes extends GOTBiomeEssosBase implements GOTBiome.Marshes {
	private static final WorldGenFlowers WORLD_GEN_FLOWERS = new WorldGenFlowers(GOTBlocks.deadMarshPlant);
	private static final GOTWorldGenMarshLights WORLD_GEN_MARSH_LIGHTS = new GOTWorldGenMarshLights();

	public GOTBiomeMossovyMarshes(int i, boolean major) {
		super(i, major);
		preseter.setupMarshesView();
		preseter.setupMarshesFlora();
		preseter.setupMarshesFauna();
		preseter.setupNorthernTrees(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.MOSSOVY;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterMossovyMarshes;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
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
			WORLD_GEN_FLOWERS.generate(world, random, i1, j1, k1);
		}
		for (l = 0; l < 4; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			for (j1 = 128; j1 > 0 && world.isAirBlock(i1, j1 - 1, k1); --j1) {
			}
			WORLD_GEN_MARSH_LIGHTS.generate(world, random, i1, j1, k1);
		}
	}
}