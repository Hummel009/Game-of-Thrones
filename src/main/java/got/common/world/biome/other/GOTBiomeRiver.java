package got.common.world.biome.other;

import got.common.world.biome.GOTBiome;

public class GOTBiomeRiver extends GOTBiome {
	public GOTBiomeRiver(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		enableRiver = false;
	}
}