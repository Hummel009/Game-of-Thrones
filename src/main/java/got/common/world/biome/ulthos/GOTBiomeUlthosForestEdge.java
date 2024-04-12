package got.common.world.biome.ulthos;

public class GOTBiomeUlthosForestEdge extends GOTBiomeUlthosForest {
	public GOTBiomeUlthosForestEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
	}
}