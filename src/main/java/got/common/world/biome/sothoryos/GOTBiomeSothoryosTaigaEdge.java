package got.common.world.biome.sothoryos;

public class GOTBiomeSothoryosTaigaEdge extends GOTBiomeSothoryosTaiga {
	public GOTBiomeSothoryosTaigaEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
	}
}