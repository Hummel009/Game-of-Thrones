package got.common.item.other;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTRegistry;
import got.common.quest.IPickpocketable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemCoin extends Item {
	public static int[] values = {1, 4, 16, 64, 256, 1024, 4096, 16384};
	@SideOnly(value = Side.CLIENT)
	public IIcon[] coinIcons;

	public GOTItemCoin() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(GOTCreativeTabs.tabMaterials);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		int i = itemstack.getItemDamage();
		if (i >= values.length) {
			i = 0;
		}
		list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("item.got:coin.nominal.name") + ": " + String.valueOf(values[i]));
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIconFromDamage(int i) {
		if (i >= coinIcons.length) {
			i = 0;
		}
		return coinIcons[i];
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < values.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int i = itemstack.getItemDamage();
		if (i >= values.length) {
			i = 0;
		}
		return super.getUnlocalizedName() + "." + values[i];
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		coinIcons = new IIcon[values.length];
		for (int i = 0; i < values.length; ++i) {
			coinIcons[i] = iconregister.registerIcon(getIconString() + "_" + values[i]);
		}
	}

	public static int getContainerValue(IInventory inv, boolean allowStolen) {
		if (inv instanceof InventoryPlayer) {
			return GOTItemCoin.getInventoryValue(((InventoryPlayer) inv).player, allowStolen);
		}
		int coins = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			coins += GOTItemCoin.getStackValue(itemstack, allowStolen);
		}
		return coins;
	}

	public static int getInventoryValue(EntityPlayer entityplayer, boolean allowStolen) {
		int coins = 0;
		for (ItemStack itemstack : entityplayer.inventory.mainInventory) {
			coins += GOTItemCoin.getStackValue(itemstack, allowStolen);
		}
		return coins += GOTItemCoin.getStackValue(entityplayer.inventory.getItemStack(), allowStolen);
	}

	public static int getSingleItemValue(ItemStack itemstack, boolean allowStolen) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemCoin) {
			if (!allowStolen && IPickpocketable.Helper.isPickpocketed(itemstack)) {
				return 0;
			}
			int i = itemstack.getItemDamage();
			if (i >= values.length) {
				i = 0;
			}
			return values[i];
		}
		return 0;
	}

	public static int getStackValue(ItemStack itemstack, boolean allowStolen) {
		if (itemstack == null) {
			return 0;
		}
		return GOTItemCoin.getSingleItemValue(itemstack, allowStolen) * itemstack.stackSize;
	}

	public static void giveCoins(int coins, EntityPlayer entityplayer) {
		int i;
		int value;
		ItemStack coin;
		InventoryPlayer inv = entityplayer.inventory;
		if (coins <= 0) {
			FMLLog.warning("Attempted to give a non-positive value of coins " + coins + " to player " + entityplayer.getCommandSenderName());
		}
		for (i = values.length - 1; i >= 0; --i) {
			value = values[i];
			coin = new ItemStack(GOTRegistry.coin, 1, i);
			while (coins >= value && inv.addItemStackToInventory(coin.copy())) {
				coins -= value;
			}
		}
		if (coins > 0) {
			for (i = values.length - 1; i >= 0; --i) {
				value = values[i];
				coin = new ItemStack(GOTRegistry.coin, 1, i);
				while (coins >= value) {
					entityplayer.dropPlayerItemWithRandomChoice(coin.copy(), false);
					coins -= value;
				}
			}
		}
	}

	public static void takeCoins(int coins, EntityPlayer entityplayer) {
		int slot;
		int i;
		ItemStack coin;
		ItemStack is;
		ItemStack itemstack;
		int value;
		InventoryPlayer inv = entityplayer.inventory;
		int invValue = GOTItemCoin.getInventoryValue(entityplayer, false);
		if (invValue < coins) {
			FMLLog.warning("Attempted to take " + coins + " coins from player " + entityplayer.getCommandSenderName() + " who has only " + invValue);
		}
		int initCoins = coins;
		block0:
		for (i = values.length - 1; i >= 0; --i) {
			value = values[i];
			if (value > initCoins) {
				continue;
			}
			coin = new ItemStack(GOTRegistry.coin, 1, i);
			for (slot = -1; slot < inv.mainInventory.length; ++slot) {
				while ((itemstack = slot == -1 ? inv.getItemStack() : inv.mainInventory[slot]) != null && itemstack.isItemEqual(coin)) {
					if (slot == -1) {
						is = inv.getItemStack();
						if (is != null) {
							--is.stackSize;
							if (is.stackSize <= 0) {
								inv.setItemStack(null);
							}
						}
					} else {
						inv.decrStackSize(slot, 1);
					}
					coins -= value;
					if (coins >= value) {
						continue;
					}
					continue block0;
				}
			}
		}
		if (coins > 0) {
			for (i = 0; i < values.length; ++i) {
				if (i == 0) {
					continue;
				}
				value = values[i];
				coin = new ItemStack(GOTRegistry.coin, 1, i);
				block4:
				for (slot = -1; slot < inv.mainInventory.length; ++slot) {
					while ((itemstack = slot == -1 ? inv.getItemStack() : inv.mainInventory[slot]) != null && itemstack.isItemEqual(coin)) {
						if (slot == -1) {
							is = inv.getItemStack();
							if (is != null) {
								--is.stackSize;
								if (is.stackSize <= 0) {
									inv.setItemStack(null);
								}
							}
						} else {
							inv.decrStackSize(slot, 1);
						}
						coins -= value;
						if (coins >= 0) {
							continue;
						}
						break block4;
					}
				}
				if (coins < 0) {
					break;
				}
			}
		}
		if (coins < 0) {
			GOTItemCoin.giveCoins(-coins, entityplayer);
		}
	}
}