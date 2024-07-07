package got.common.world.biome;

import got.common.database.GOTBlocks;
import got.common.entity.animal.*;
import got.common.world.biome.essos.GOTBiomeYiTi;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeWesteros;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomePreseter {
	private final GOTBiome biome;

	public GOTBiomePreseter(GOTBiome biome) {
		this.biome = biome;
	}

	public GOTBiome getBiome() {
		return biome;
	}

	public void setupDesertView() {
		biome.setVariantChance(0.2f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getDecorator().setGrassPerChunk(5);
		biome.getDecorator().setDoubleGrassPerChunk(0);
		biome.getDecorator().setCactiPerChunk(2);
		biome.getDecorator().setDeadBushPerChunk(2);
	}

	public void setupDesertColdViewOverride() {
		biome.getDecorator().setGrassPerChunk(0);
		biome.getDecorator().setCactiPerChunk(0);
		biome.getDecorator().setDeadBushPerChunk(0);
	}

	public void setupDesertFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.southernFlower, 0, 10);
		biome.addFlower(GOTBlocks.southernFlower, 1, 10);
		biome.addFlower(GOTBlocks.southernFlower, 2, 5);
		biome.addFlower(GOTBlocks.southernFlower, 3, 5);
	}

	public void setupDesertColdFloraOverride() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
	}

	public void setupDesertFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityCamel.class, 100, 1, 2));
		biome.getSpawnableGOTAmbientList().clear();
	}

	public void setupDesertColdFaunaOverride() {
		biome.getSpawnableCreatureList().clear();
	}

	public void setupDesertTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.OAK_DEAD, 800);
		biome.getDecorator().addTree(GOTTreeType.OAK_DESERT, 200);
	}

	public void setupForestView() {
		biome.setVariantChance(0.2f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FLOWERS, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.CLEARING, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.8f);
		biome.getDecorator().setTreesPerChunk(10);
		biome.getDecorator().setFlowersPerChunk(4);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
		biome.getDecorator().setGrassPerChunk(10);
		biome.getDecorator().setDoubleGrassPerChunk(4);
	}

	public void setupForestFlora() {
		biome.getFlowers().clear();
		biome.addFlower(Blocks.yellow_flower, 0, 20);
		biome.addFlower(Blocks.red_flower, 0, 10);
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
		biome.addFlower(GOTBlocks.marigold, 0, 10);
		if (biome instanceof GOTBiomeYiTi) {
			biome.addFlower(GOTBlocks.marigold, 0, 10);
			biome.addFlower(GOTBlocks.chrysanthemum, 0, 10);
			biome.addFlower(GOTBlocks.chrysanthemum, 1, 10);
			biome.addFlower(GOTBlocks.chrysanthemum, 2, 10);
			biome.addFlower(GOTBlocks.chrysanthemum, 3, 10);
			biome.addFlower(GOTBlocks.chrysanthemum, 4, 10);
		}
	}

	public void setupForestFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 20, 2, 3));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 20, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 10, 1, 1));
		biome.getSpawnableGOTAmbientList().clear();
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 5, 2, 3));
		if (biome instanceof GOTBiomeWesteros) {
			biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 20, 1, 2));
		} else {
			biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteBison.class, 20, 1, 2));
		}
	}

	public void setupFrostView() {
		biome.setVariantChance(0.2f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getDecorator().setFlowersPerChunk(0);
		biome.getDecorator().setDoubleFlowersPerChunk(0);
		biome.getDecorator().setGrassPerChunk(0);
		biome.getDecorator().setDoubleGrassPerChunk(0);
	}

	public void setupFrostFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
	}

	public void setupFrostFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntitySnowBear.class, 60, 1, 1));
		biome.getSpawnableWaterCreatureList().clear();
		biome.getSpawnableCaveCreatureList().clear();
		biome.getSpawnableGOTAmbientList().clear();
	}

	public void setupFrostTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.PINE, 20);
	}

	public void setupTaigaView() {
		biome.setVariantChance(0.2f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FLOWERS, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.CLEARING, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.8f);
		biome.getDecorator().setTreesPerChunk(10);
		biome.getDecorator().setFlowersPerChunk(1);
		biome.getDecorator().setDoubleFlowersPerChunk(0);
		biome.getDecorator().setGrassPerChunk(2);
		biome.getDecorator().setDoubleGrassPerChunk(0);
	}

	public void setupTaigaFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
	}

	public void setupTaigaFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 20, 2, 3));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 15, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 15, 1, 1));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityWoolyRhino.class, 10, 1, 1));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityMammoth.class, 10, 1, 1));
		biome.getSpawnableWaterCreatureList().clear();
		biome.getSpawnableCaveCreatureList().clear();
		biome.getSpawnableGOTAmbientList().clear();
	}

	public void setupTaigaTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.PINE, 20);
	}

	public void setupMountainsView() {
		biome.setVariantChance(0.3f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getDecorator().setTreesPerChunk(3);
		biome.getDecorator().setGrassPerChunk(6);
		biome.getDecorator().setDoubleGrassPerChunk(1);
		biome.getDecorator().setFlowersPerChunk(3);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
		biome.getDecorator().setBiomeGemFactor(biome.getDecorator().getBiomeGemFactor() * 2.0f);
		biome.getDecorator().setBiomeOreFactor(biome.getDecorator().getBiomeOreFactor() * 2.0f);
		biome.getDecorator().addOre(new WorldGenMinable(GOTBlocks.oreGlowstone, 4), 8.0f, 0, 48);
		biome.getDecorator().addOre(new WorldGenMinable(GOTBlocks.oreCobalt, 5), 5.0f, 0, 32);
	}

	public void setupMountainsFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
		biome.addFlower(GOTBlocks.marigold, 0, 10);
	}

	public void setupMountainsFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 20, 2, 3));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 20, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 10, 1, 1));
		biome.getSpawnableGOTAmbientList().clear();
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 5, 2, 3));
	}

	public void setupMarshesView() {
		biome.setVariantChance(1.0f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		biome.getDecorator().setTreesPerChunk(3);
		biome.getDecorator().setSandPerChunk(0);
		biome.getDecorator().setQuagmirePerChunk(1);
		biome.getDecorator().setTreesPerChunk(0);
		biome.getDecorator().setLogsPerChunk(2);
		biome.getDecorator().setGrassPerChunk(8);
		biome.getDecorator().setDoubleGrassPerChunk(8);
		biome.getDecorator().setFlowersPerChunk(0);
		biome.getDecorator().setReedPerChunk(2);
		biome.getDecorator().setDryReedChance(1.0f);
	}

	public void setupMarshesFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.deadMarshPlant, 0, 10);
	}

	public void setupMarshesFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBeaver.class, 50, 1, 1));
		biome.getSpawnableWaterCreatureList().clear();
		biome.getSpawnableGOTAmbientList().clear();
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityMidges.class, 95, 4, 4));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntitySwan.class, 5, 1, 2));
	}

	public void setupBushlandView() {
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FLOWERS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biome.getDecorator().setTreesPerChunk(0);
		biome.getDecorator().setLogsPerChunk(1);
		biome.getDecorator().setGrassPerChunk(16);
		biome.getDecorator().setDoubleGrassPerChunk(10);
		biome.getDecorator().setCornPerChunk(4);
	}

	public void setupBushlandFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.southernFlower, 0, 10);
		biome.addFlower(GOTBlocks.southernFlower, 1, 10);
		biome.addFlower(GOTBlocks.southernFlower, 2, 5);
		biome.addFlower(GOTBlocks.southernFlower, 3, 5);
	}

	public void setupBushlandFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 15, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 15, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteOryx.class, 15, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 15, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 10, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 10, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 5, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 5, 1, 2));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 5, 1, 1));
		biome.getSpawnableCreatureList().add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 5, 1, 1));
		biome.getSpawnableGOTAmbientList().clear();
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		biome.getSpawnableGOTAmbientList().add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 20, 2, 3));
	}
}