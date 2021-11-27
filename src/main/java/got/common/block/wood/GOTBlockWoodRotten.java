package got.common.block.wood;

import net.minecraft.block.Block;

public class GOTBlockWoodRotten extends GOTBlockWoodBase {
	public GOTBlockWoodRotten() {
		setWoodNames("rotten");
	}

	public static boolean isRottenWood(Block block) {
		return block instanceof GOTBlockWoodRotten;
	}
}
