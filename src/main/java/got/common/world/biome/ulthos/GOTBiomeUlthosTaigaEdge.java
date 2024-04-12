package got.common.world.biome.ulthos;

public class GOTBiomeUlthosTaigaEdge extends GOTBiomeUlthosTaiga {
	public GOTBiomeUlthosTaigaEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
	}
}