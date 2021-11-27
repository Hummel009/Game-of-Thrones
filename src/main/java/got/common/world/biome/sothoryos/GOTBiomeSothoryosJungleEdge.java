package got.common.world.biome.sothoryos;

import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;

public class GOTBiomeSothoryosJungleEdge extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosJungleEdge(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 2;
		decorator.affix(new GOTStructureSothoryosVillage(this, 1.0f));
	}
}
