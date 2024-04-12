package got.common.world.biome.ulthos;

public class GOTBiomeUlthosMarshesForest extends GOTBiomeUlthosMarshes {
	public GOTBiomeUlthosMarshesForest(int i, boolean major) {
		super(i, major);
		decorator.setTreesPerChunk(5);
	}
}