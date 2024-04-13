package got.common.item.weapon;

import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityDart;
import got.common.item.other.GOTItemDart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemSarbacane extends Item {
	public GOTItemSarbacane(Item.ToolMaterial material) {
		setMaxStackSize(1);
		setMaxDamage(material.getMaxUses());
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		setFull3D();
	}

	public static void applySarbacaneModifiers(GOTEntityDart dart, ItemStack itemstack) {
		int punch = GOTEnchantmentHelper.calcRangedKnockback(itemstack);
		if (punch > 0) {
			dart.knockbackStrength = punch;
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + GOTEnchantmentHelper.calcFireAspect(itemstack) > 0) {
			dart.setFire(100);
		}
		for (GOTEnchantment ench : GOTEnchantment.CONTENT) {
			if (!ench.getApplyToProjectile() || !GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			GOTEnchantmentHelper.setProjectileEnchantment(dart, ench);
		}
	}

	public static float getSarbacaneLaunchSpeedFactor(ItemStack itemstack) {
		float f = 1.0f;
		if (itemstack != null) {
			f *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
		}
		return f;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == getItemFromBlock(GOTBlocks.reeds);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	public int getMaxDrawTime() {
		return 5;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		boolean anyDart = false;
		for (ItemStack invItem : entityplayer.inventory.mainInventory) {
			if (invItem == null || !(invItem.getItem() instanceof GOTItemDart)) {
				continue;
			}
			anyDart = true;
			break;
		}
		if (anyDart || entityplayer.capabilities.isCreativeMode) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		ItemStack dartItem = null;
		int dartSlot = -1;
		for (int l = 0; l < entityplayer.inventory.mainInventory.length; ++l) {
			ItemStack invItem = entityplayer.inventory.mainInventory[l];
			if (invItem == null || !(invItem.getItem() instanceof GOTItemDart)) {
				continue;
			}
			dartItem = invItem;
			dartSlot = l;
			break;
		}
		if (dartItem == null && entityplayer.capabilities.isCreativeMode) {
			dartItem = new ItemStack(GOTItems.dart);
		}
		if (dartItem != null) {
			int useTick = getMaxItemUseDuration(itemstack) - i;
			float charge = (float) useTick / getMaxDrawTime();
			if (charge < 0.65f) {
				return;
			}
			charge = charge * (charge + 2.0f) / 3.0f;
			charge = Math.min(charge, 1.0f);
			itemstack.damageItem(1, entityplayer);
			if (!entityplayer.capabilities.isCreativeMode && dartSlot >= 0) {
				--dartItem.stackSize;
				if (dartItem.stackSize <= 0) {
					entityplayer.inventory.mainInventory[dartSlot] = null;
				}
			}
			world.playSoundAtEntity(entityplayer, "got:item.dart", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
			if (!world.isRemote) {
				ItemStack shotDart = dartItem.copy();
				shotDart.stackSize = 1;
				GOTEntityDart dart = ((GOTItemDart) shotDart.getItem()).createDart(world, entityplayer, shotDart, charge * 2.0f * getSarbacaneLaunchSpeedFactor(itemstack));
				if (dart.dartDamageFactor < 1.0f) {
					dart.dartDamageFactor = 1.0f;
				}
				if (charge >= 1.0f) {
					dart.setIsCritical(true);
				}
				applySarbacaneModifiers(dart, itemstack);
				if (entityplayer.capabilities.isCreativeMode) {
					dart.canBePickedUp = 2;
				}
				world.spawnEntityInWorld(dart);
			}
		}
	}
}