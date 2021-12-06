package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.jogos.GOTStructureJogosVillage;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeJogosNhai extends GOTBiome {
	public GOTBiomeJogosNhai(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 8, 1, 2));
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
		decorator.addTree(GOTTreeType.CEDAR, 300);
		decorator.addTree(GOTTreeType.CYPRESS, 500);
		decorator.addTree(GOTTreeType.CYPRESS_LARGE, 50);
		decorator.addTree(GOTTreeType.DATE_PALM, 5);
		decorator.addTree(GOTTreeType.LEMON, 2);
		decorator.addTree(GOTTreeType.LIME, 2);
		decorator.addTree(GOTTreeType.OAK_DESERT, 1000);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 5);
		decorator.addTree(GOTTreeType.ORANGE, 2);
		decorator.addTree(GOTTreeType.PALM, 100);
		decorator.addTree(GOTTreeType.PLUM, 2);
		decorator.addTree(GOTTreeType.CEDAR_LARGE, 150);
		decorator.addTree(GOTTreeType.ACACIA, 50);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		registerExoticFlowers();

		decorator.affix(new GOTStructureJogosVillage(this, 1.0f));

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		GOTStructureJogosVillage camp = new GOTStructureJogosVillage(this, 0.0f).setIsBig();
		camp.affix(GOTWaypoint.Hojdbaatar);
		decorator.affix(camp);

		invasionSpawns.addInvasion(GOTInvasions.YI_TI, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_JOGOS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("jogosNhai");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.JOGOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.2f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}