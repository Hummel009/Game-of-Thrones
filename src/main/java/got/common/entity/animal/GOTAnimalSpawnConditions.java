package got.common.entity.animal;

import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;

public interface GOTAnimalSpawnConditions {
	boolean canWorldGenSpawnAt(GOTBiome var4, GOTBiomeVariant var5);
}
