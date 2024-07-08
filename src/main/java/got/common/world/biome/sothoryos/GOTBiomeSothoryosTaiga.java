package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosTaiga extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosTaiga(int i, boolean major) {
		super(i, major);
		fillerBlock = Blocks.snow;

		preseter.setupTaigaView();
		preseter.setupTaigaFlora();
		preseter.setupTaigaFauna();
		preseter.setupTaigaTrees(false);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosTaiga;
	}
}