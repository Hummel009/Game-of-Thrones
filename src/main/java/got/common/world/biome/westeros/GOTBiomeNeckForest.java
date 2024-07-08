package got.common.world.biome.westeros;

public class GOTBiomeNeckForest extends GOTBiomeNeck {
	public GOTBiomeNeckForest(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(5);
	}
}