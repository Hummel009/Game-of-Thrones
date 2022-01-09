package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;
import got.common.world.structure.other.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeYiTi extends GOTBiome {

	public GOTBiomeYiTi(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 5, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteBison.class, 6, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 4, 1, 1));
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.SHRUBLAND_OAK);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND_SAND);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND_SAND);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.generatePipeweed = true;
		decorator.cornPerChunk = 4;
		decorator.addTree(GOTTreeType.CHERRY, 500);
		decorator.addTree(GOTTreeType.LEMON, 500);
		decorator.addTree(GOTTreeType.LIME, 500);
		decorator.addTree(GOTTreeType.POMEGRANATE, 500);
		registerYiTiPlainsFlowers();

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c2);

		decorator.addVillage(new GOTStructureYiTiCity(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.JOGOS, GOTEventSpawner.EventChance.UNCOMMON);

		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.YITI(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_YI_TI;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("yiTi");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.YITI;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PAVING;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.COBBLEBRICK;
	}

	@Override
	public int getWallTop() {
		return 90;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
