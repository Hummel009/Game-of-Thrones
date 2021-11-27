package got.common.block.other;

import net.minecraft.world.World;

public class GOTBlockReedDry extends GOTBlockReed {
	@Override
	public boolean canReedGrow(World world, int i, int j, int k) {
		return false;
	}
}
