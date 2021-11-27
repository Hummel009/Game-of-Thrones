package got.common.world.biome.variant;

public class GOTBiomeVariantDenseForest extends GOTBiomeVariant {
	public GOTBiomeVariantDenseForest(int i, String s) {
		super(i, s, GOTBiomeVariant.VariantScale.LARGE);
		setHeight(0.5f, 2.0f);
		setTrees(8.0f);
		setGrass(2.0f);
		disableVillages();
	}
}
