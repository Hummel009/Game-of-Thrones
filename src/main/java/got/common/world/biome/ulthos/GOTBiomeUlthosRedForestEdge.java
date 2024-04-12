package got.common.world.biome.ulthos;

public class GOTBiomeUlthosRedForestEdge extends GOTBiomeUlthosRedForest {
	public GOTBiomeUlthosRedForestEdge(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(2);
	}
}