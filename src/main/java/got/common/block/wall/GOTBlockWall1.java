package got.common.block.wall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockWall1 extends GOTBlockWallBase {
	public GOTBlockWall1() {
		super(GOTBlocks.brick1, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return GOTBlocks.rock.getIcon(i, 0);
			case 1:
				return GOTBlocks.brick1.getIcon(i, 0);
			case 2:
				return GOTBlocks.rock.getIcon(i, 1);
			case 3:
				return GOTBlocks.brick1.getIcon(i, 1);
			case 4:
				return GOTBlocks.brick1.getIcon(i, 2);
			case 5:
				return GOTBlocks.brick1.getIcon(i, 3);
			case 6:
				return GOTBlocks.brick1.getIcon(i, 4);
			case 7:
				return GOTBlocks.brick1.getIcon(i, 6);
			case 8:
				return GOTBlocks.rock.getIcon(i, 2);
			case 9:
				return GOTBlocks.brick1.getIcon(i, 7);
			case 10:
				return GOTBlocks.brick1.getIcon(i, 11);
			case 11:
				return GOTBlocks.brick1.getIcon(i, 12);
			case 12:
				return GOTBlocks.brick1.getIcon(i, 13);
			case 13:
				return GOTBlocks.rock.getIcon(i, 3);
			case 14:
				return GOTBlocks.brick1.getIcon(i, 14);
			case 15:
				return GOTBlocks.brick1.getIcon(i, 15);
			default:
				break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 6));
		list.add(new ItemStack(item, 1, 8));
		list.add(new ItemStack(item, 1, 9));
		list.add(new ItemStack(item, 1, 13));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}
}
