package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarSettlement;
import got.common.world.structure.other.GOTStructureStoneRuin;

public class GOTBiomeLhazar extends GOTBiomeEssosPlains {
	public GOTBiomeLhazar(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureLhazarSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLhazar;
	}


	@Override
	public Region getBiomeWaypoints() {
		return Region.LHAZAR;
	}
}