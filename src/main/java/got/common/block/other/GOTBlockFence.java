package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GOTBlockFence extends BlockFence {
	private Block plankBlock;

	public GOTBlockFence(Block planks) {
		super("", Material.wood);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(GOTCreativeTabs.tabDeco);
		plankBlock = planks;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return plankBlock.getIcon(i, j);
	}

	@Override
	public int getRenderType() {
		if (GOT.getProxy().isClient()) {
			return GOT.getProxy().getFenceRenderID();
		}
		return super.getRenderType();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		ArrayList plankTypes = new ArrayList();
		plankBlock.getSubBlocks(Item.getItemFromBlock(plankBlock), plankBlock.getCreativeTabToDisplayOn(), plankTypes);
		for (Object plankType : plankTypes) {
			Object obj = plankType;
			if (!(obj instanceof ItemStack)) {
				continue;
			}
			int meta = ((ItemStack) obj).getItemDamage();
			list.add(new ItemStack(this, 1, meta));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
