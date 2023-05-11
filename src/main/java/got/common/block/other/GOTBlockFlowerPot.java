package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTRegistry;
import got.common.tileentity.GOTTileEntityFlowerPot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class GOTBlockFlowerPot extends BlockFlowerPot implements ITileEntityProvider {
	public static boolean canAcceptPlant(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock) {
			Block block = ((ItemBlock) item).field_150939_a;
			return block instanceof GOTBlockFlower;
		}
		return false;
	}

	public static ItemStack getPlant(IBlockAccess world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityFlowerPot) {
			GOTTileEntityFlowerPot flowerPot = (GOTTileEntityFlowerPot) tileentity;
			if (flowerPot.item == null) {
				return null;
			}
			return new ItemStack(flowerPot.item, 1, flowerPot.meta);
		}
		return null;
	}

	public static void setPlant(World world, int i, int j, int k, ItemStack itemstack) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityFlowerPot) {
			GOTTileEntityFlowerPot flowerPot = (GOTTileEntityFlowerPot) tileentity;
			flowerPot.item = itemstack.getItem();
			flowerPot.meta = itemstack.getItemDamage();
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityFlowerPot();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ItemStack itemstack;
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(Items.flower_pot));
		if ((meta & 8) == 0 && (itemstack = GOTBlockFlowerPot.getPlant(world, i, j, k)) != null && (GOTTileEntityFlowerPot) world.getTileEntity(i, j, k) != null) {
			drops.add(itemstack);
		}
		return drops;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.flower_pot.getIcon(i, j);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		return GOTBlockFlowerPot.getPlant(world, i, j, k);
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getFlowerPotRenderID();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
		}
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (GOTBlockFlowerPot.getPlant(world, i, j, k) != null && GOTBlockFlowerPot.getPlant(world, i, j, k).getItem() == Item.getItemFromBlock(GOTRegistry.pipeweedPlant)) {
			double d = i + 0.2 + random.nextFloat() * 0.6f;
			double d1 = j + 0.625 + random.nextFloat() * 0.1875f;
			double d2 = k + 0.2 + random.nextFloat() * 0.6f;
			world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
