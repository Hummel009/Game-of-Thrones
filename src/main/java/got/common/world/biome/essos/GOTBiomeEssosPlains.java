package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public abstract class GOTBiomeEssosPlains extends GOTBiomeEssos {
	protected GOTBiomeEssosPlains(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.SAVANNAH_BAOBAB, 0.5f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setGrassPerChunk(256);
	}
}