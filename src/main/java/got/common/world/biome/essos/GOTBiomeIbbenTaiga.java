package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeIbbenTaiga extends GOTBiomeIbben {
	public GOTBiomeIbbenTaiga(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.treesPerChunk = 10;
		decorator.clearSettlements();
	}

}