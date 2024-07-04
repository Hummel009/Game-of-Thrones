package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTEventSpawner;

import java.awt.*;

public class GOTBiomeBleedingSea extends GOTBiomeJogosNhai {
	public GOTBiomeBleedingSea(int i, boolean major) {
		super(i, major);
		decorator.clearSettlements();
		biomeColors.setWater(new Color(0x640a0a));
		npcSpawnList.clear();
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YI_TI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterBleedingSea;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}