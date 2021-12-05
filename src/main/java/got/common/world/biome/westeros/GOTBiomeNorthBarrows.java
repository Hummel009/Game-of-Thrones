package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.structure.other.GOTStructureBarrow;

public class GOTBiomeNorthBarrows extends GOTBiomeNorth {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		decorator.addRandomStructure(new GOTStructureBarrow(false), 50);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_NORTH_BARROWS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("northBarrows");
	}
}