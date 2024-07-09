package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.jogos.GOTStructureJogosNhaiSettlement;

public class GOTBiomeJogosNhai extends GOTBiomeEssosBase {
	public GOTBiomeJogosNhai(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.addSettlement(new GOTStructureJogosNhaiSettlement(this, 1.0f));

		biomeWaypoints = GOTWaypoint.Region.JOGOS_NHAI;
		biomeAchievement = GOTAchievement.enterJogosNhai;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		wallBlock = GOTBezierType.WALL_YI_TI;
	}
}