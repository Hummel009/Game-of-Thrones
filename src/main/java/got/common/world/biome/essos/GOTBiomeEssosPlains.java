package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public abstract class GOTBiomeEssosPlains extends GOTBiomeEssos {
	protected GOTBiomeEssosPlains(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 0.5f);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.grassPerChunk = 256;
	}
}