package got.common.world.biome.sothoryos;

import net.minecraft.init.Blocks;

public class GOTBiomeSothoryosJungleEdge extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosJungleEdge(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.grass;
		fillerBlock = Blocks.dirt;

		decorator.setTreesPerChunk(2);
	}
}