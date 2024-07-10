package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiSettlement;

public class GOTBiomeDothrakiSea extends GOTBiomeEssosBase {
	public GOTBiomeDothrakiSea(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.addSettlement(new GOTStructureDothrakiSettlement(this, 1.0f));
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.DOTHRAKI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDothrakiSea;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_IBBEN;
	}
}