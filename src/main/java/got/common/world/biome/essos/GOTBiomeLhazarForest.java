package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.entity.other.GOTEntityNPC;
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
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.JOGOS_NHAI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterJogosNhai;
	}

	@Override
	public Class<? extends GOTEntityNPC> getBanditEntityClass() {
		return GOTEntityDarkSkinBandit.class;
	}
}