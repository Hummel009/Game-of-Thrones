package got.common.world.biome.other;

import got.common.world.biome.GOTBiome;

public class GOTBiomeLake extends GOTBiome {
	public GOTBiomeLake(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		enableRiver = false;
	}
}