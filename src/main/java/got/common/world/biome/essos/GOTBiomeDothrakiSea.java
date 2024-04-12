package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiSettlement;

public class GOTBiomeDothrakiSea extends GOTBiomeEssosPlains {
	public GOTBiomeDothrakiSea(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureDothrakiSettlement(this, 1.0f));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDothrakiSea;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.DOTHRAKI;
	}
}