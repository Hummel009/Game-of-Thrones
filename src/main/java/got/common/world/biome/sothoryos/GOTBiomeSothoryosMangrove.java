package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.entity.animal.GOTEntityCrocodile;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeSothoryosMangrove extends GOTBiomeSothoryosBase implements GOTBiome.Marshes {
	public GOTBiomeSothoryosMangrove(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.mudGrass;
		fillerBlock = GOTBlocks.mud;

		preseter.setupJungleView();
		preseter.setupJungleFlora();
		preseter.setupJungleFauna();
		preseter.removeAllEntities();

		variantChance = 1.0f;
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);

		addSpawnableMonster(GOTEntityCrocodile.class, 5, 1, 1);

		decorator.addTree(GOTTreeType.MANGROVE, 500);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosMangrove;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}