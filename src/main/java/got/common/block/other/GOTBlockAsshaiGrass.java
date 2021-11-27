package got.common.block.other;

import java.util.*;

import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

public class GOTBlockAsshaiGrass extends BlockBush implements IShearable {
	public GOTBlockAsshaiGrass() {
		super(Material.vine);
		setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setHardness(0.0f);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		if (j >= 0 && j < 256) {
			return GOTBiomeShadowLand.isBlackSurface(world, i, j - 1, k);
		}
		return false;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getGrassRenderID();
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
		return list;
	}
}
