package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityBlizzard;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeUlthosFrost extends GOTBiomeUlthosTaiga {
	public GOTBiomeUlthosFrost(int i, boolean major) {
		super(i, major);
		setupFrostFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		topBlock = Blocks.snow;
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBlizzard.class, 40, 3, 3));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosFrost;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosFrost");
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}