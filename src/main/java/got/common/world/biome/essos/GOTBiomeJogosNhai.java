package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
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
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 1, 2));
		SpawnListContainer[] container1 = new SpawnListContainer[3];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		container1[1] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_FRONTIER, 4).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		container1[2] = GOTBiomeSpawnList.entry(GOTSpawnList.YITI_SAMURAI, 2).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		decorator.grassPerChunk = 256;
		decorator.clearTrees();
		invasionSpawns.addInvasion(GOTInvasions.YI_TI, GOTEventSpawner.EventChance.UNCOMMON);
		GOTStructureJogosVillage camp = new GOTStructureJogosVillage(this, 1.0f);
		camp.affix(GOTWaypoint.Hojdbaatar);
		decorator.affix(camp);
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