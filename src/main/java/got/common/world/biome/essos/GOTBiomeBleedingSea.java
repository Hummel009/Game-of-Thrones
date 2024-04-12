package got.common.world.biome.essos;

import got.common.database.GOTAchievement;

public class GOTBiomeBleedingSea extends GOTBiomeJogosNhai {
	public GOTBiomeBleedingSea(int i, boolean major) {
		super(i, major);
		decorator.clearSettlements();
		biomeColors.setWater(0x640a0a);
		npcSpawnList.clear();
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