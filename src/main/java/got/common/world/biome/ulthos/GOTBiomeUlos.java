package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.other.GOTStructureRuinsBig;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeUlos extends GOTBiome {
	public GOTBiomeUlos(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 10, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 4, 4, 4));
		decorator.treesPerChunk = 12;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 6;
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK, 1000);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK, 15);
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK_LARGE, 10);
		decorator.addTree(GOTTreeType.OAK, 50);
		decorator.addTree(GOTTreeType.OAK_LARGE, 100);
		decorator.addTree(GOTTreeType.ULTHOS_OAK, 50);
		decorator.addTree(GOTTreeType.SPRUCE, 100);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 50);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 20);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.CHESTNUT, 20);
		decorator.addTree(GOTTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.FIR, 200);
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.ASPEN, 50);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 10);
		registerForestFlowers();
		GOTStructureRuinsBig colossal = new GOTStructureRuinsBig(this, 0.0f);
		colossal.affix(GOTWaypoint.Ulos);
		decorator.affix(colossal);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_ULOS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulos");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ULTHOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}
