package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosTaiga extends GOTBiome {
	public GOTBiomeSothoryosTaiga(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		topBlock = Blocks.grass;
		fillerBlock = Blocks.snow;
		decorator.treesPerChunk = 10;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosTaiga;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("taiga");
	}
}