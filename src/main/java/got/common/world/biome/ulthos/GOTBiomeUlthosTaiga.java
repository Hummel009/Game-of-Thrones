package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
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
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
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

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosTaiga");
	}
}