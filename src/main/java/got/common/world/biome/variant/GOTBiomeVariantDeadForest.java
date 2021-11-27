package got.common.world.biome.variant;

public class GOTBiomeVariantDeadForest extends GOTBiomeVariant {
	public GOTBiomeVariantDeadForest(int i, String s) {
		super(i, s, GOTBiomeVariant.VariantScale.SMALL);
		setTrees(3.0f);
		setGrass(0.5f);
		disableVillages();
	}
}
