package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.item.other.*;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTBlockBarrel extends BlockContainer {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] barrelIcons;

	public GOTBlockBarrel() {
		super(Material.wood);
		setCreativeTab(GOTCreativeTabs.tabFood);
		setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.8125f, 0.875f);
		setHardness(3.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) world.getTileEntity(i, j, k);
		if (barrel != null) {
			ItemStack brewing = barrel.getStackInSlot(9);
			barrel.setInventorySlotContents(9, null);
			GOT.dropContainerItems(barrel, world, i, j, k);
			for (int slot = 0; slot < barrel.getSizeInventory(); ++slot) {
				barrel.setInventorySlotContents(slot, null);
			}
			barrel.setInventorySlotContents(9, brewing);
			if (!world.isRemote && (meta & 8) == 0) {
				this.dropBlockAsItem(world, i, j, k, getBarrelDrop(world, i, j, k, meta));
			}
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityBarrel();
	}

	private ItemStack getBarrelDrop(World world, int i, int j, int k, int metadata) {
		ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
		GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) world.getTileEntity(i, j, k);
		if (barrel != null && barrel.getBarrelMode() != 0) {
			GOTItemBarrel.setBarrelDataFromTE(itemstack, barrel);
		}
		return itemstack;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == -1) {
			return barrelIcons[2];
		}
		if (i < 2) {
			return barrelIcons[1];
		}
		return barrelIcons[0];
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		world.markBlockForUpdate(i, j, k);
		return getBarrelDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getBarrelRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		Item item;
		GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) world.getTileEntity(i, j, k);
		ItemStack barrelDrink = barrel.getBrewedDrink();
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		item = itemstack == null ? null : itemstack.getItem();
		if (side == world.getBlockMetadata(i, j, k)) {
			if (barrelDrink != null && GOTItemMug.isItemEmptyDrink(itemstack)) {
				ItemStack playerDrink = barrelDrink.copy();
				playerDrink.stackSize = 1;
				GOTItemMug.Vessel v = GOTItemMug.getVessel(itemstack);
				GOTItemMug.setVessel(playerDrink, v, true);
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, playerDrink);
				} else if (!entityplayer.inventory.addItemStackToInventory(playerDrink)) {
					entityplayer.dropPlayerItemWithRandomChoice(playerDrink, false);
				}
				barrel.consumeMugRefill();
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				return true;
			}
			if (itemstack != null && item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
				boolean match = false;
				if (barrel.getBarrelMode() == 0) {
					match = true;
				} else if (barrelDrink != null && barrelDrink.stackSize < GOTRecipeBrewing.getBarrelCapacity()) {
					match = barrelDrink.getItem() == itemstack.getItem() && GOTItemMug.getStrength(barrelDrink) == GOTItemMug.getStrength(itemstack);
				}
				if (match) {
					if (barrelDrink == null) {
						ItemStack barrelFill = itemstack.copy();
						barrelFill.stackSize = 1;
						GOTItemMug.setVessel(barrelFill, GOTItemMug.Vessel.MUG, false);
						barrel.setInventorySlotContents(9, barrelFill);
					} else {
						++barrelDrink.stackSize;
						barrel.setInventorySlotContents(9, barrelDrink);
					}
					barrel.setBarrelMode(2);
					if (!entityplayer.capabilities.isCreativeMode) {
						GOTItemMug.Vessel v = GOTItemMug.getVessel(itemstack);
						ItemStack emptyMug = v.getEmptyVessel();
						--itemstack.stackSize;
						if (itemstack.stackSize <= 0) {
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
						} else if (!entityplayer.inventory.addItemStackToInventory(emptyMug)) {
							entityplayer.dropPlayerItemWithRandomChoice(emptyMug, false);
						}
					}
					world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
					return true;
				}
			}
		}
		if (itemstack != null && item instanceof GOTItemBottlePoison && barrel.canPoisonBarrel()) {
			if (!world.isRemote) {
				barrel.poisonBarrel(entityplayer);
				if (!entityplayer.capabilities.isCreativeMode) {
					ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
				}
				entityplayer.openContainer.detectAndSendChanges();
				((EntityPlayerMP) entityplayer).sendContainerToPlayer(entityplayer.openContainer);
			}
			return true;
		}
		if (!world.isRemote) {
			entityplayer.openGui(GOT.getInstance(), 16, world, i, j, k);
		}
		return true;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
		}
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
		int meta = 0;
		switch (rotation) {
		case 0:
			meta = 2;
			break;
		case 1:
			meta = 5;
			break;
		case 2:
			meta = 3;
			break;
		case 3:
			meta = 4;
			break;
		default:
			break;
		}
		world.setBlockMetadataWithNotify(i, j, k, meta, 2);
		if (itemstack.hasDisplayName()) {
			((GOTTileEntityBarrel) world.getTileEntity(i, j, k)).setBarrelName(itemstack.getDisplayName());
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		barrelIcons = new IIcon[3];
		barrelIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		barrelIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
		barrelIcons[2] = iconregister.registerIcon(getTextureName() + "_tap");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
