package got.common.block.other;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.item.other.*;
import got.common.tileentity.GOTTileEntityMug;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GOTBlockMug extends BlockContainer {
	public GOTBlockMug() {
		this(3.0f, 8.0f);
	}

	public GOTBlockMug(float f, float f1) {
		super(Material.circuits);
		f /= 16.0f;
		f1 /= 16.0f;
		setBlockBounds(0.5f - (f *= 0.75f), 0.0f, 0.5f - f, 0.5f + f, f1 *= 0.75f, 0.5f + f);
		setHardness(0.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j - 1, k);
		return block.canPlaceTorchOnTop(world, i, j - 1, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityMug();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if ((meta & 4) == 0) {
			ItemStack itemstack = GOTBlockMug.getMugItem(world, i, j, k);
			GOTTileEntityMug mug = (GOTTileEntityMug) world.getTileEntity(i, j, k);
			if (mug != null) {
				drops.add(itemstack);
			}
		}
		return drops;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		return GOTBlockMug.getMugItem(world, i, j, k);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityMug) {
			GOTTileEntityMug mug = (GOTTileEntityMug) tileentity;
			ItemStack mugItem = mug.getMugItem();
			if (!mug.isEmpty() && GOTItemMug.isItemEmptyDrink(itemstack)) {
				ItemStack takenDrink = mugItem.copy();
				GOTItemMug.Vessel v = GOTItemMug.getVessel(itemstack);
				GOTItemMug.setVessel(takenDrink, v, true);
				if (entityplayer.capabilities.isCreativeMode) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
				} else {
					--itemstack.stackSize;
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
					} else if (!entityplayer.inventory.addItemStackToInventory(takenDrink)) {
						entityplayer.dropPlayerItemWithRandomChoice(takenDrink, false);
					}
				}
				mug.setEmpty();
				world.playSoundAtEntity(entityplayer, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				return true;
			}
			if (mug.isEmpty() && GOTItemMug.isItemFullDrink(itemstack)) {
				ItemStack emptyMug = GOTItemMug.getVessel(itemstack).getEmptyVessel();
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
				ItemStack mugFill = itemstack.copy();
				mugFill.stackSize = 1;
				mug.setMugItem(mugFill);
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				return true;
			}
			if (!mug.isEmpty()) {
				if (itemstack != null && itemstack.getItem() instanceof GOTItemBottlePoison && mug.canPoisonMug()) {
					if (!world.isRemote) {
						mug.poisonMug(entityplayer);
						if (!entityplayer.capabilities.isCreativeMode) {
							ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
						}
						entityplayer.openContainer.detectAndSendChanges();
						((EntityPlayerMP) entityplayer).sendContainerToPlayer(entityplayer.openContainer);
					}
					return true;
				}
				ItemStack equivalentDrink = GOTItemMug.getEquivalentDrink(mugItem);
				Item eqItem = equivalentDrink.getItem();
				boolean canDrink = false;
				if (eqItem instanceof GOTItemMug) {
					canDrink = ((GOTItemMug) eqItem).canPlayerDrink(entityplayer);
				}
				if (canDrink) {
					ItemStack mugItemResult = mugItem.onFoodEaten(world, entityplayer);
					mugItemResult = ForgeEventFactory.onItemUseFinish(entityplayer, mugItem, mugItem.getMaxItemUseDuration(), mugItemResult);
					mug.setEmpty();
					world.markBlockForUpdate(i, j, k);
					world.playSoundAtEntity(entityplayer, "random.drink", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 4, 4);
		}
		this.dropBlockAsItem(world, i, j, k, meta, 0);
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			this.dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	private static ItemStack getMugItem(World world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityMug) {
			GOTTileEntityMug mug = (GOTTileEntityMug) tileentity;
			return mug.getMugItem();
		}
		return new ItemStack(GOTRegistry.mug);
	}

	public static void setMugItem(World world, int i, int j, int k, ItemStack itemstack, GOTItemMug.Vessel vessel) {
		TileEntity te = world.getTileEntity(i, j, k);
		if (te instanceof GOTTileEntityMug) {
			GOTTileEntityMug mug = (GOTTileEntityMug) te;
			mug.setMugItem(itemstack);
			mug.setVessel(vessel);
		}
	}
}
