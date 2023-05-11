package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.wall.GOTBlockWallBase;
import got.common.database.GOTRegistry;
import net.minecraft.util.IIcon;

public class GOTBlockScorchedWall extends GOTBlockWallBase {
	public GOTBlockScorchedWall() {
		super(GOTRegistry.scorchedStone, 1);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.scorchedStone.getIcon(i, j);
	}
}
