package got.common.world.biome.essos;

import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeEssosUnhabited extends GOTBiomeEssosBase {
	public GOTBiomeEssosUnhabited(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(true);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(true);
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.VALYRIA;
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}
}