package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.util.IIcon;

public class GOTBlockWallClayTileDyed extends GOTBlockWallBase {
	public GOTBlockWallClayTileDyed() {
		super(GOTRegistry.clayTileDyed, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.clayTileDyed.getIcon(i, j);
	}
}
