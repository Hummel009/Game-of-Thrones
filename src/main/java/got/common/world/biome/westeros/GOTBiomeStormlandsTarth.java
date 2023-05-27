package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeStormlandsTarth extends GOTBiomeStormlands {
	public GOTBiomeStormlandsTarth(int i, boolean major) {
		super(i, major);
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 5;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGem, 2), 10.0f, 0, 50);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterStormlandsTarth;
	}

}