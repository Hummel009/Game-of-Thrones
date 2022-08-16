package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;

public class GOTBiomeLongSummer extends GOTBiomeEssos {
	public GOTBiomeLongSummer(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.CHARRED, 1000);
		decorator.addTree(GOTTreeType.OAK_DEAD, 1000);
	}

	@Override
	public boolean disableNoise() {
		return true;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLongSummer;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("longSummer");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.VALYRIA;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.VALYRIA;
	}
}
