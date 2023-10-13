package got.common.block.leaves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GOTBlockLeavesBase extends BlockLeaves {
	public static Collection<Block> allLeafBlocks = new ArrayList<>();
	@SideOnly(Side.CLIENT)
	public IIcon[][] leafIcons;
	public String[] leafNames;
	public boolean[] seasonal;
	public String vanillaTextureName;

	public GOTBlockLeavesBase() {
		this(false, null);
	}

	public GOTBlockLeavesBase(boolean vanilla, String vname) {
		if (vanilla) {
			setCreativeTab(CreativeTabs.tabDecorations);
			vanillaTextureName = vname;
		} else {
			setCreativeTab(GOTCreativeTabs.tabDeco);
		}
		allLeafBlocks.add(this);
	}

	public void addSpecialLeafDrops(List<ItemStack> drops, World world, int i, int j, int k, int meta, int fortune) {
	}

	public int calcFortuneModifiedDropChance(int baseChance, int fortune) {
		int chance = baseChance;
		if (fortune > 0) {
			chance -= 2 << fortune;
			chance = Math.max(chance, baseChance / 2);
			chance = Math.max(chance, 1);
		}
		return chance;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@Override
	public String[] func_150125_e() {
		return leafNames;
	}

	public String[] getAllLeafNames() {
		return leafNames;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		int saplingChanceBase = getSaplingChance(meta & 3);
		int saplingChance = calcFortuneModifiedDropChance(saplingChanceBase, fortune);
		if (world.rand.nextInt(saplingChance) == 0) {
			drops.add(new ItemStack(getItemDropped(meta, world.rand, fortune), 1, damageDropped(meta)));
		}
		addSpecialLeafDrops(drops, world, i, j, k, meta, fortune);
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int meta = j & 3;
		if (meta >= leafNames.length) {
			meta = 0;
		}
		return leafIcons[meta][field_150121_P ? 0 : 1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		return 16777215;
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getLeavesRenderID();
	}

	public int getSaplingChance(int meta) {
		return 20;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < leafNames.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getTextureName() {
		if (vanillaTextureName != null) {
			return vanillaTextureName;
		}
		return super.getTextureName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		leafIcons = new IIcon[leafNames.length][2];
		for (int i = 0; i < leafNames.length; ++i) {
			IIcon fancy = iconregister.registerIcon(getTextureName() + "_" + leafNames[i] + "_fancy");
			IIcon fast = iconregister.registerIcon(getTextureName() + "_" + leafNames[i] + "_fast");
			leafIcons[i][0] = fancy;
			leafIcons[i][1] = fast;
		}
	}

	public void setLeafNames(String... s) {
		leafNames = s;
		setSeasonal(new boolean[s.length]);
	}

	public void setSeasonal(boolean... b) {
		if (b.length != leafNames.length) {
			throw new IllegalArgumentException("Leaf seasons length must match number of types");
		}
		seasonal = b;
	}

	public static int getBiomeLeafColor(IBlockAccess world, int i, int j, int k) {
		int r = 0;
		int g = 0;
		int b = 0;
		int count = 0;
		int range = 1;
		for (int i1 = -range; i1 <= range; ++i1) {
			for (int k1 = -range; k1 <= range; ++k1) {
				int biomeColor = world.getBiomeGenForCoords(i + i1, k + k1).getBiomeFoliageColor(i + i1, j, k + k1);
				r += (biomeColor & 0xFF0000) >> 16;
				g += (biomeColor & 0xFF00) >> 8;
				b += biomeColor & 0xFF;
				++count;
			}
		}
		return (r / count & 0xFF) << 16 | (g / count & 0xFF) << 8 | b / count & 0xFF;
	}

	public static void setAllGraphicsLevels(boolean flag) {
		for (Object allLeafBlock : allLeafBlocks) {
			((BlockLeaves) allLeafBlock).setGraphicsLevel(flag);
		}
	}

}