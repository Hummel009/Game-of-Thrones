package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.essos.jogos.GOTStructureJogosNhaiSettlement;

public class GOTBiomeJogosNhai extends GOTBiomeEssosPlains {
	public GOTBiomeJogosNhai(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureJogosNhaiSettlement(this, 1.0f));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterJogosNhai;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.JOGOS_NHAI;
	}
}