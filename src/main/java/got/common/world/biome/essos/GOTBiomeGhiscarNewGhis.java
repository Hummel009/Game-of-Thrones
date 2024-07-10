package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarFightingPit;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeGhiscarNewGhis extends GOTBiomeGhiscar {
	public GOTBiomeGhiscarNewGhis(int i, boolean major) {
		super(i, major);
		decorator.addStructure(new GOTStructureGhiscarFightingPit(false), 150);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_MILITARY, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterGhiscarNewGhis;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}