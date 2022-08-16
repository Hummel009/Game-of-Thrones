package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
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

	@Override
	public boolean disableNoise() {
		return true;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}