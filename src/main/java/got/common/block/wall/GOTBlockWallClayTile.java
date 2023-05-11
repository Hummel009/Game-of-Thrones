package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.util.IIcon;

public class GOTBlockWallClayTile extends GOTBlockWallBase {
	public GOTBlockWallClayTile() {
		super(GOTRegistry.clayTile, 1);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.clayTile.getIcon(i, j);
	}
}
