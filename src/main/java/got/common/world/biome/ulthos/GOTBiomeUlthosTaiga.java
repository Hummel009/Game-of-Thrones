package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityBlizzard;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeUlthosTaiga extends GOTBiomeUlthosForest {
	public GOTBiomeUlthosTaiga(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		fillerBlock = Blocks.snow;
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBlizzard.class, 20, 3, 3));
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosTaiga;
	}
}