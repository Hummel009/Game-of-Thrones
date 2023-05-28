package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.util.IIcon;

public class GOTBlockWallClayTile extends GOTBlockWallBase {
	public GOTBlockWallClayTile() {
		super(GOTBlocks.clayTile, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTBlocks.clayTile.getIcon(i, j);
	}
}
