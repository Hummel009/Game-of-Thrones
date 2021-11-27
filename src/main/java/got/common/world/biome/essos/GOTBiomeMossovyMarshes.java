package got.common.world.biome.essos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.fixed.GOTStructureMossovyRampart;
import got.common.world.map.GOTWaypoint;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class GOTBiomeMossovyMarshes extends GOTBiome {
	public GOTBiomeMossovyMarshes(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		clearBiomeVariants();
		variantChance = 1.0f;
		addBiomeVariantSet(GOTBiomeVariant.SET_SWAMP);
		decorator.sandPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 5;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK_DEAD, 100);
		flowers.clear();
		addFlower(GOTRegistry.deadMarshPlant, 0, 10);
		GOTStructureMossovyRampart rampart = new GOTStructureMossovyRampart(this, 0.0f);
		rampart.affix(GOTWaypoint.EastPass);
		rampart.affix(GOTWaypoint.WestPass);
		rampart.affix(GOTWaypoint.NorthPass);
		rampart.affix(GOTWaypoint.SouthPass);
		decorator.affix(rampart);
	}

	@Override
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
			new WorldGenFlowers(GOTRegistry.deadMarshPlant).generate(world, random, i1, j1, k1);
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
		return GOTAchievement.VISIT_MOSSOVY_MARSHES;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("mossovyMarshes");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.MOSSOVY;
	}
}