package got.common.world.biome.essos;

import got.common.database.GOTAchievement;

public class GOTBiomeRedSea extends GOTBiomeJogosNhai {
	public GOTBiomeRedSea(int i, boolean major) {
		super(i, major);
		decorator.clearSettlements();
		biomeColors.setWater(0x640a0a);
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterRedSea;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

}
