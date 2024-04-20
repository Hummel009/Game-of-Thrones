package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.wall.GOTBlockWallBase;
import got.common.database.GOTBlocks;
import net.minecraft.util.IIcon;

public class GOTBlockScorchedWall extends GOTBlockWallBase {
	public GOTBlockScorchedWall() {
		super(GOTBlocks.scorchedStone, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTBlocks.scorchedStone.getIcon(i, j);
	}
}