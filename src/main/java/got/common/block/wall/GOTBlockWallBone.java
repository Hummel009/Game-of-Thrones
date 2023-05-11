package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.util.IIcon;

public class GOTBlockWallBone extends GOTBlockWallBase {
	public GOTBlockWallBone() {
		super(GOTRegistry.boneBlock, 1);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 0) {
			return GOTRegistry.boneBlock.getIcon(i, 0);
		}
		return super.getIcon(i, j);
	}
}
