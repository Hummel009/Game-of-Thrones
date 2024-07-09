package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeLhazarForest extends GOTBiomeEssosBase {
	public GOTBiomeLhazarForest(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.setTreesPerChunk(8);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.JOGOS_NHAI;
		biomeAchievement = GOTAchievement.enterJogosNhai;
		banditEntityClass = GOTEntityDarkSkinBandit.class;
	}
}