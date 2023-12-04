package got.common.item.other;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
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
	@SideOnly(Side.CLIENT)
	public IIcon[] coinIcons;

	public GOTItemCoin() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(GOTCreativeTabs.tabMaterials);
	}

	public static int getContainerValue(IInventory inv, boolean allowStolen) {
		if (inv instanceof InventoryPlayer) {
			return getInventoryValue(((InventoryPlayer) inv).player, allowStolen);
		}
		int coins = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			coins += getStackValue(itemstack, allowStolen);
		}
		return coins;
	}

	public static int getInventoryValue(EntityPlayer entityplayer, boolean allowStolen) {
		int coins = 0;
		for (ItemStack itemstack : entityplayer.inventory.mainInventory) {
			coins += getStackValue(itemstack, allowStolen);
		}
		return coins + getStackValue(entityplayer.inventory.getItemStack(), allowStolen);
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
		return getSingleItemValue(itemstack, allowStolen) * itemstack.stackSize;
	}

	public static void giveCoins(int coins, EntityPlayer entityplayer) {
		int coins1 = coins;
		InventoryPlayer inv = entityplayer.inventory;
		if (coins1 <= 0) {
			FMLLog.warning("Attempted to give a non-positive value of coins " + coins1 + " to player " + entityplayer.getCommandSenderName());
		}
		ItemStack coin;
		int value;
		int i;
		for (i = values.length - 1; i >= 0; --i) {
			value = values[i];
			coin = new ItemStack(GOTItems.coin, 1, i);
			while (coins1 >= value && inv.addItemStackToInventory(coin.copy())) {
				coins1 -= value;
			}
		}
		if (coins1 > 0) {
			for (i = values.length - 1; i >= 0; --i) {
				value = values[i];
				coin = new ItemStack(GOTItems.coin, 1, i);
				while (coins1 >= value) {
					entityplayer.dropPlayerItemWithRandomChoice(coin.copy(), false);
					coins1 -= value;
				}
			}
		}
	}

	public static void takeCoins(int coins, EntityPlayer entityplayer) {
		int coins1 = coins;
		InventoryPlayer inv = entityplayer.inventory;
		int invValue = getInventoryValue(entityplayer, false);
		if (invValue < coins1) {
			FMLLog.warning("Attempted to take " + coins1 + " coins from player " + entityplayer.getCommandSenderName() + " who has only " + invValue);
		}
		int initCoins = coins1;
		int value;
		ItemStack itemstack;
		ItemStack is;
		ItemStack coin;
		int i;
		int slot;
		block0:
		for (i = values.length - 1; i >= 0; --i) {
			value = values[i];
			if (value > initCoins) {
				continue;
			}
			coin = new ItemStack(GOTItems.coin, 1, i);
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
					coins1 -= value;
					if (coins1 >= value) {
						continue;
					}
					continue block0;
				}
			}
		}
		if (coins1 > 0) {
			for (i = 0; i < values.length; ++i) {
				if (i == 0) {
					continue;
				}
				value = values[i];
				coin = new ItemStack(GOTItems.coin, 1, i);
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
						coins1 -= value;
						if (coins1 >= 0) {
							continue;
						}
						break block4;
					}
				}
				if (coins1 < 0) {
					break;
				}
			}
		}
		if (coins1 < 0) {
			giveCoins(-coins1, entityplayer);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		int i = itemstack.getItemDamage();
		if (i >= values.length) {
			i = 0;
		}
		list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("item.got:coin.nominal.name") + ": " + values[i]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int i) {
		int i1 = i;
		if (i1 >= coinIcons.length) {
			i1 = 0;
		}
		return coinIcons[i1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
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
		return getUnlocalizedName() + '.' + values[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		coinIcons = new IIcon[values.length];
		for (int i = 0; i < values.length; ++i) {
			coinIcons[i] = iconregister.registerIcon(getIconString() + '_' + values[i]);
		}
	}
}