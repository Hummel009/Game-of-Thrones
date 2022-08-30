package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public abstract class GOTBiomeEssosPlains extends GOTBiomeEssos {
	public GOTBiomeEssosPlains(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.grassPerChunk = 256;
	}
}