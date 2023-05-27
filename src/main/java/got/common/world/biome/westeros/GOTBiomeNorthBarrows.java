package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.structure.other.GOTStructureBarrow;

public class GOTBiomeNorthBarrows extends GOTBiomeNorth {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.addStructure(new GOTStructureBarrow(false), 20);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorthBarrows;
	}

}