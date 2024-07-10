package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityJungleScorpion;
import got.common.world.biome.essos.GOTBiomeEssosBase;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.other.GOTStructureBurntHouse;
import got.common.world.structure.other.GOTStructureRottenHouse;
import got.common.world.structure.other.GOTStructureRuinedHouse;

public class GOTBiomeSummerIslandsTropicalForest extends GOTBiomeEssosBase {
	public GOTBiomeSummerIslandsTropicalForest(int i, boolean major) {
		super(i, major);
		preseter.setupJungleView();
		preseter.setupJungleFlora();
		preseter.setupJungleFauna();
		preseter.setupJungleTrees();

		addSpawnableMonster(GOTEntityJungleScorpion.class, 5, 1, 1);

		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SUMMER;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSummerIslands;
	}
}