package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.util.IIcon;

public class GOTBlockWallBone extends GOTBlockWallBase {
	public GOTBlockWallBone() {
		super(GOTBlocks.boneBlock, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 0) {
			return GOTBlocks.boneBlock.getIcon(i, 0);
		}
		return super.getIcon(i, j);
	}
}
