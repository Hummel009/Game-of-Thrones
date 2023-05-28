package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockWallV extends GOTBlockWallBase {
	public GOTBlockWallV() {
		super(Blocks.stonebrick, 9);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return Blocks.stone.getIcon(i, 0);
			case 1:
				return Blocks.stonebrick.getIcon(i, 0);
			case 2:
				return Blocks.stonebrick.getIcon(i, 1);
			case 3:
				return Blocks.stonebrick.getIcon(i, 2);
			case 4:
				return Blocks.sandstone.getIcon(i, 0);
			case 5:
				return GOTBlocks.redSandstone.getIcon(i, 0);
			case 6:
				return Blocks.brick_block.getIcon(i, 0);
			case 7:
				return GOTBlocks.redBrick.getIcon(i, 0);
			case 8:
				return GOTBlocks.redBrick.getIcon(i, 1);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
