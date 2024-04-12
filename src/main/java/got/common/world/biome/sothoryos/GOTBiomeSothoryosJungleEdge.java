package got.common.world.biome.sothoryos;

import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;

public class GOTBiomeSothoryosJungleEdge extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosJungleEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
		decorator.addSettlement(new GOTStructureSothoryosSettlement(this, 1.0f));
	}
}