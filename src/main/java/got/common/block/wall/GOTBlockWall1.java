package got.common.block.wall;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockWall1 extends GOTBlockWallBase {
	public GOTBlockWall1() {
		super(GOTRegistry.brick1, 16);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
		case 0:
			return GOTRegistry.rock.getIcon(i, 0);
		case 1:
			return GOTRegistry.brick1.getIcon(i, 0);
		case 2:
			return GOTRegistry.rock.getIcon(i, 1);
		case 3:
			return GOTRegistry.brick1.getIcon(i, 1);
		case 4:
			return GOTRegistry.brick1.getIcon(i, 2);
		case 5:
			return GOTRegistry.brick1.getIcon(i, 3);
		case 6:
			return GOTRegistry.brick1.getIcon(i, 4);
		case 7:
			return GOTRegistry.brick1.getIcon(i, 6);
		case 8:
			return GOTRegistry.rock.getIcon(i, 2);
		case 9:
			return GOTRegistry.brick1.getIcon(i, 7);
		case 10:
			return GOTRegistry.brick1.getIcon(i, 11);
		case 11:
			return GOTRegistry.brick1.getIcon(i, 12);
		case 12:
			return GOTRegistry.brick1.getIcon(i, 13);
		case 13:
			return GOTRegistry.rock.getIcon(i, 3);
		case 14:
			return GOTRegistry.brick1.getIcon(i, 14);
		case 15:
			return GOTRegistry.brick1.getIcon(i, 15);
		default:
			break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
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
