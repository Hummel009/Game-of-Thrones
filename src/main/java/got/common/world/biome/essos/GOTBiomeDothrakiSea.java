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

		biomeWaypoints = GOTWaypoint.Region.DOTHRAKI;
		biomeAchievement = GOTAchievement.enterDothrakiSea;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		wallBlock = GOTBezierType.WALL_IBBEN;
	}
}