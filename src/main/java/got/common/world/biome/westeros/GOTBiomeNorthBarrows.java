package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.common.GOTStructureWesterosObelisk;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;

public class GOTBiomeNorthBarrows extends GOTBiomeNorth {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new GOTStructureBarrow(false), 20);
		decorator.addRandomStructure(new GOTStructureWesterosObelisk(false), 1000);
		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.STONE(1, 4), 400);

		GOTStructureNorthCity stown = new GOTStructureNorthCity(this, 0.0f).setIsSmallTown();
		stown.affix(GOTWaypoint.Barrowtown, 0, 1, 2);
		decorator.affix(stown);
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