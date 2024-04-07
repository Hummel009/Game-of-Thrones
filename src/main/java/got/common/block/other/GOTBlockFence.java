package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GOTBlockFence extends BlockFence {
	private final Block plankBlock;

	public GOTBlockFence(Block planks) {
		super("", Material.wood);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(soundTypeWood);
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

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return plankBlock.getIcon(i, j);
	}

	@Override
	public int getRenderType() {
		if (GOT.proxy.isClient()) {
			return GOT.proxy.getFenceRenderID();
		}
		return super.getRenderType();
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		List<ItemStack> plankTypes = new ArrayList<>();
		plankBlock.getSubBlocks(Item.getItemFromBlock(plankBlock), plankBlock.getCreativeTabToDisplayOn(), plankTypes);
		for (ItemStack plankType : plankTypes) {
			int meta = plankType.getItemDamage();
			list.add(new ItemStack(this, 1, meta));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
