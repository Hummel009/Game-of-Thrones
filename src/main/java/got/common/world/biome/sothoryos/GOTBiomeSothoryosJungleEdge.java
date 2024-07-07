package got.common.world.biome.sothoryos;

public class GOTBiomeSothoryosJungleEdge extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosJungleEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
	}
}