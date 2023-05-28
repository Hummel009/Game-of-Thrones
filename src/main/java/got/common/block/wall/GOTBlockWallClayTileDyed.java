package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.util.IIcon;

public class GOTBlockWallClayTileDyed extends GOTBlockWallBase {
	public GOTBlockWallClayTileDyed() {
		super(GOTBlocks.clayTileDyed, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTBlocks.clayTileDyed.getIcon(i, j);
	}
}
