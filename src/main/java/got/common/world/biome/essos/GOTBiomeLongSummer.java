package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeLongSummer extends GOTBiomeEssosPlains {
	public GOTBiomeLongSummer(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableWaterCreatureList.clear();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.FOREST, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(2);
		decorator.setFlowersPerChunk(2);
		decorator.setDoubleFlowersPerChunk(0);
		decorator.setGrassPerChunk(6);
		decorator.setDoubleGrassPerChunk(6);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALLER, 100);
		decorator.addTree(GOTTreeType.OAK_GIANT, 1);
		decorator.addTree(GOTTreeType.OAK_PARTY, 100);
		decorator.addTree(GOTTreeType.OAK_LARGE, 1000);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = BIOME_TERRAIN_NOISE.func_151601_a(i * 0.09, k * 0.09);
		double d2 = BIOME_TERRAIN_NOISE.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.3) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLongSummer;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VALYRIA;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_COBBLE;
	}
}