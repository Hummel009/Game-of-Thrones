package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosFrost extends GOTBiomeSothoryosTaiga {
	public GOTBiomeSothoryosFrost(int i, boolean major) {
		super(i, major);
		setupFrostFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		topBlock = Blocks.snow;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosFrost;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}