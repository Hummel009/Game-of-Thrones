package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeSothoryosJungle extends GOTBiomeSothoryosBushland {
	public GOTBiomeSothoryosJungle(int i, boolean major) {
		super(i, major);
		setupJungleFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		topBlock = GOTRegistry.mudGrass;
		fillerBlock = GOTRegistry.mud;
		decorator.treesPerChunk = 40;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
		decorator.canePerChunk = 5;
		decorator.cornPerChunk = 10;
		decorator.logsPerChunk = 0;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		decorator.addGem(new WorldGenMinable(GOTRegistry.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
	}

	@Override
	public boolean enableTermite() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosJungle;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosJungle");
	}
}