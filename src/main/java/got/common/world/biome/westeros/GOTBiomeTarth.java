package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeTarth extends GOTBiomeStormlands {
	public GOTBiomeTarth(int i, boolean major) {
		super(i, major);
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 5;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGem, 2), 10.0f, 0, 50);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_TARTH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("tarth");
	}
}