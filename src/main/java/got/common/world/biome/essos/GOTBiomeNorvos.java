package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.entity.animal.GOTEntityBear;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.norvos.GOTStructureNorvosFortress;
import got.common.world.structure.essos.norvos.GOTStructureNorvosSettlement;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeNorvos extends GOTBiomeEssos {
	public WorldGenerator boulderGen = new GOTWorldGenBoulder(Blocks.stone, 0, 2, 4);

	public GOTBiomeNorvos(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 15, 1, 1));
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.BEECH_PARTY, 2);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 400);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		decorator.addSettlement(new GOTStructureNorvosSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureNorvosFortress(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.VOLANTIS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.DOTHRAKI, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORVOS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (random.nextInt(6) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		double d2;
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
		d2 = biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.5) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
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
		return GOTAchievement.enterNorvos;
	}

}