package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.jogos.GOTStructureJogosSettlement;

public class GOTBiomeJogosNhai extends GOTBiomeEssosPlains {
	public GOTBiomeJogosNhai(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureJogosSettlement(this, 1.0f));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterJogosNhai;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.JOGOS;
	}
}