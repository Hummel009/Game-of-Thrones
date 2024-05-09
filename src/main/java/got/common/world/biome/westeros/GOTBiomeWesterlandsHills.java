package got.common.world.biome.westeros;

import got.common.entity.animal.GOTEntityLion;
import got.common.entity.animal.GOTEntityLioness;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeWesterlandsHills extends GOTBiomeWesterlands {
	public GOTBiomeWesterlandsHills(int i, boolean major) {
		super(i, major);
		decorator.setBiomeOreFactor(2.0f);
		decorator.setBiomeGemFactor(2.0f);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 10, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 10, 1, 1));
	}
}