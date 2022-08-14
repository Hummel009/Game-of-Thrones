package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.jogos.GOTStructureJogosVillage;

public class GOTBiomeJogosNhai extends GOTBiome {
	public GOTBiomeJogosNhai(int i, boolean major) {
		super(i, major);
		setupExoticFauna();
		addBiomeVariant(GOTBiomeVariant.FLOWERS);
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
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

		decorator.addVillage(new GOTStructureJogosVillage(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.YI_TI, GOTEventSpawner.EventChance.UNCOMMON);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterJogos;
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