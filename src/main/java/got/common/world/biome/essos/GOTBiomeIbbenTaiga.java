package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeIbbenTaiga extends GOTBiomeEssosBase {
	public GOTBiomeIbbenTaiga(int i, boolean major) {
		super(i, major);
		preseter.setupTaigaView();
		preseter.setupTaigaFlora();
		preseter.setupTaigaFauna();
		preseter.setupNorthernTrees(true);

		biomeWaypoints = GOTWaypoint.Region.IBBEN;
		biomeAchievement = GOTAchievement.enterIbbenTaiga;
		banditChance = GOTEventSpawner.EventChance.COMMON;
	}
}