package got.common.world.biome.variant;

public class GOTBiomeVariantForest extends GOTBiomeVariant {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTBiomeVariantForest(int i, String s) {
		super(i, s);
		setTrees(8.0f);
		setGrass(2.0f);
		disableSettlements();
	}
}