package got.common.entity.animal;

import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;

public interface GOTAnimalSpawnConditions {
	boolean canWorldGenSpawnAt(int var1, int var2, int var3, GOTBiome var4, GOTBiomeVariant var5);
}
