package got.common.world.biome.westeros;

public class GOTBiomeIronIslandsHills extends GOTBiomeIronIslands {
	public GOTBiomeIronIslandsHills(int i, boolean major) {
		super(i, major);
		decorator.setBiomeOreFactor(2.0f);
	}
}