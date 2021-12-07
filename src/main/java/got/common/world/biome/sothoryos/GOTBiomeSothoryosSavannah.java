package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeSothoryosSavannah extends GOTBiome {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 2, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 2, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 1, 2));
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.STEPPE);
		this.addBiomeVariant(GOTBiomeVariant.STEPPE_BARREN);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
		decorator.grassPerChunk = 256;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.KANUKA, 100);
		registerExoticFlowers();

		SpawnListContainer[] c = new SpawnListContainer[2];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		c[1] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);

		decorator.affix(new GOTStructureSothoryosVillage(this, 1.0f));
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SOTHORYOS_SAVANNAH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosSavannah");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.SOTHORYOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
