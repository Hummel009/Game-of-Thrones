package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemCracker extends Item {
	public static int emptyMeta = 4096;
	public static int CUSTOM_CAPACITY = 3;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] crackerIcons;
	public String[] crackerNames = { "red", "blue", "green", "silver", "gold" };

	public GOTItemCracker() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (!GOTItemCracker.isEmpty(itemstack)) {
			String name = GOTItemCracker.getSealingPlayerName(itemstack);
			if (name == null) {
				name = StatCollector.translateToLocal("item.got.cracker.sealedByDale");
			}
			list.add(StatCollector.translateToLocalFormatted("item.got.cracker.sealedBy", name));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		i = GOTItemCracker.getBaseCrackerMetadata(i);
		if (i >= crackerIcons.length) {
			i = 0;
		}
		return crackerIcons[i];
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (GOTItemCracker.isEmpty(itemstack)) {
			String name = super.getItemStackDisplayName(itemstack);
			return StatCollector.translateToLocalFormatted("item.got.cracker.empty", name);
		}
		return super.getItemStackDisplayName(itemstack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < crackerNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
			list.add(GOTItemCracker.setEmpty(new ItemStack(item, 1, i), true));
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!GOTItemCracker.isEmpty(itemstack)) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}
			}
			world.playSoundAtEntity(entityplayer, "fireworks.blast", 1.0f, 0.9f + world.rand.nextFloat() * 0.1f);
			if (!world.isRemote) {
				IInventory crackerItems = null;
				IInventory customItems = GOTItemCracker.loadCustomCrackerContents(itemstack);
				if (customItems != null) {
					crackerItems = customItems;
				} else {
					int amount = 1;
					if (world.rand.nextInt(3) == 0) {
						++amount;
					}
					crackerItems = new InventoryBasic("cracker", true, amount);
					GOTChestContents.fillInventory(crackerItems, world.rand, GOTChestContents.WESTEROS, amount);
				}
				for (int l = 0; l < crackerItems.getSizeInventory(); ++l) {
					ItemStack loot = crackerItems.getStackInSlot(l);
					if (entityplayer.inventory.addItemStackToInventory(loot)) {
						continue;
					}
					entityplayer.dropPlayerItemWithRandomChoice(loot, false);
				}
				return entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem);
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!GOTItemCracker.isEmpty(itemstack)) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		} else {
			entityplayer.openGui(GOT.getInstance(), 48, world, 0, 0, 0);
		}
		return itemstack;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		crackerIcons = new IIcon[crackerNames.length];
		for (int i = 0; i < crackerNames.length; ++i) {
			crackerIcons[i] = iconregister.registerIcon(getIconString() + "_" + crackerNames[i]);
		}
	}

	public static int getBaseCrackerMetadata(int i) {
		return i & ~emptyMeta;
	}

	public static int getBaseCrackerMetadata(ItemStack itemstack) {
		return GOTItemCracker.getBaseCrackerMetadata(itemstack.getItemDamage());
	}

	public static String getSealingPlayerName(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SealingPlayer")) {
			return itemstack.getTagCompound().getString("SealingPlayer");
		}
		return null;
	}

	public static boolean isEmpty(ItemStack itemstack) {
		return (itemstack.getItemDamage() & emptyMeta) == emptyMeta;
	}

	public static IInventory loadCustomCrackerContents(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("CustomCracker")) {
			NBTTagCompound invData = itemstack.getTagCompound().getCompoundTag("CustomCracker");
			int size = invData.getInteger("Size");
			InventoryBasic inv = new InventoryBasic("cracker", false, size);
			NBTTagList items = invData.getTagList("Items", 10);
			for (int i = 0; i < items.tagCount(); ++i) {
				NBTTagCompound itemData = items.getCompoundTagAt(i);
				byte slot = itemData.getByte("Slot");
				if (slot < 0 || slot >= inv.getSizeInventory()) {
					continue;
				}
				inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
			}
			return inv;
		}
		return null;
	}

	public static void setCustomCrackerContents(ItemStack itemstack, IInventory inv) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (inv == null) {
			itemstack.getTagCompound().removeTag("CustomCracker");
		} else {
			NBTTagCompound invData = new NBTTagCompound();
			int size = inv.getSizeInventory();
			invData.setInteger("Size", size);
			NBTTagList items = new NBTTagList();
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack invItem = inv.getStackInSlot(i);
				if (invItem == null) {
					continue;
				}
				NBTTagCompound itemData = new NBTTagCompound();
				itemData.setByte("Slot", (byte) i);
				invItem.writeToNBT(itemData);
				items.appendTag(itemData);
			}
			invData.setTag("Items", items);
			itemstack.getTagCompound().setTag("CustomCracker", invData);
		}
	}

	public static ItemStack setEmpty(ItemStack itemstack, boolean flag) {
		int i = itemstack.getItemDamage();
		i = flag ? (i |= emptyMeta) : (i &= ~emptyMeta);
		itemstack.setItemDamage(i);
		return itemstack;
	}

	public static void setSealingPlayerName(ItemStack itemstack, String name) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (name == null) {
			itemstack.getTagCompound().removeTag("SealingPlayer");
		} else {
			itemstack.getTagCompound().setString("SealingPlayer", name);
		}
	}
}
