package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeSothoryosJungle extends GOTBiomeSothoryosBushland {
	public GOTBiomeSothoryosJungle(int i, boolean major) {
		super(i, major);
		setupJungleFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.MOUNTAIN, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		topBlock = GOTBlocks.mudGrass;
		fillerBlock = GOTBlocks.mud;
		decorator.setTreesPerChunk(40);
		decorator.setFlowersPerChunk(4);
		decorator.setDoubleFlowersPerChunk(4);
		decorator.setGrassPerChunk(15);
		decorator.setDoubleGrassPerChunk(10);
		decorator.setCanePerChunk(5);
		decorator.setCornPerChunk(10);
		decorator.setLogsPerChunk(0);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		decorator.addGem(new WorldGenMinable(GOTBlocks.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
	}

	@Override
	public boolean enableTermite() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosJungle;
	}
}