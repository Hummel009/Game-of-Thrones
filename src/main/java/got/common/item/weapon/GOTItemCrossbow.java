package got.common.item.weapon;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import got.common.enchant.*;
import got.common.entity.other.GOTEntityCrossbowBolt;
import got.common.recipe.GOTRecipe;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemCrossbow extends ItemBow {
	public double boltDamageFactor;
	public Item.ToolMaterial crossbowMaterial;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] crossbowPullIcons;

	public GOTItemCrossbow(GOTMaterial material) {
		this(material.toToolMaterial());
	}

	public GOTItemCrossbow(Item.ToolMaterial material) {
		setCreativeTab(GOTCreativeTabs.tabCombat);
		crossbowMaterial = material;
		setMaxDamage((int) (crossbowMaterial.getMaxUses() * 1.25f));
		setMaxStackSize(1);
		boltDamageFactor = 1.0f + Math.max(0.0f, (crossbowMaterial.getDamageVsEntity() - 2.0f) * 0.1f);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		ItemStack ammo = GOTItemCrossbow.getLoaded(itemstack);
		if (ammo != null) {
			String ammoName = ammo.getDisplayName();
			list.add(StatCollector.translateToLocalFormatted("item.got.crossbow.loadedItem", ammoName));
		}
	}

	public Item.ToolMaterial getCrossbowMaterial() {
		return crossbowMaterial;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		return getIconIndex(itemstack);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		if (usingItem != null && usingItem.getItem() == this) {
			int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
			double useAmount = (double) ticksInUse / (double) getMaxDrawTime();
			if (useAmount >= 1.0) {
				return crossbowPullIcons[2];
			}
			if (useAmount > 0.5) {
				return crossbowPullIcons[1];
			}
			if (useAmount > 0.0) {
				return crossbowPullIcons[0];
			}
		}
		return itemIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		return itemIcon;
	}

	public int getInvBoltSlot(EntityPlayer entityplayer) {
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			ItemStack invItem = entityplayer.inventory.mainInventory[slot];
			if (invItem == null || !(invItem.getItem() instanceof GOTItemCrossbowBolt)) {
				continue;
			}
			return slot;
		}
		return -1;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		if (GOTRecipe.checkItemEquals(crossbowMaterial.getRepairItemStack(), repairItem)) {
			return true;
		}
		return super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public int getItemEnchantability() {
		return 1 + crossbowMaterial.getEnchantability() / 5;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String name = super.getItemStackDisplayName(itemstack);
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			name = StatCollector.translateToLocalFormatted("item.got.crossbow.loaded", name);
		}
		return name;
	}

	public int getMaxDrawTime() {
		return 50;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (GOTItemCrossbow.isLoaded(itemstack)) {
			ItemStack boltItem = GOTItemCrossbow.getLoaded(itemstack);
			if (boltItem != null) {
				float charge = 1.0f;
				ItemStack shotBolt = boltItem.copy();
				shotBolt.stackSize = 1;
				GOTEntityCrossbowBolt bolt = new GOTEntityCrossbowBolt(world, entityplayer, shotBolt, charge * 2.0f * GOTItemCrossbow.getCrossbowLaunchSpeedFactor(itemstack));
				if (bolt.boltDamageFactor < 1.0) {
					bolt.boltDamageFactor = 1.0;
				}
				if (charge >= 1.0f) {
					bolt.setIsCritical(true);
				}
				GOTItemCrossbow.applyCrossbowModifiers(bolt, itemstack);
				if (!shouldConsumeBolt(itemstack, entityplayer)) {
					bolt.canBePickedUp = 2;
				}
				if (!world.isRemote) {
					world.spawnEntityInWorld(bolt);
				}
				world.playSoundAtEntity(entityplayer, "got:item.crossbow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
				itemstack.damageItem(1, entityplayer);
				if (!world.isRemote) {
					setLoaded(itemstack, null);
				}
			}
		} else if (!shouldConsumeBolt(itemstack, entityplayer) || getInvBoltSlot(entityplayer) >= 0) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick) {
		int ticksInUse = getMaxItemUseDuration(itemstack) - useTick;
		if (ticksInUse >= getMaxDrawTime() && !GOTItemCrossbow.isLoaded(itemstack)) {
			ItemStack boltItem = null;
			int boltSlot = getInvBoltSlot(entityplayer);
			if (boltSlot >= 0) {
				boltItem = entityplayer.inventory.mainInventory[boltSlot];
			}
			boolean shouldConsume = shouldConsumeBolt(itemstack, entityplayer);
			if (boltItem == null && !shouldConsume) {
				boltItem = new ItemStack(GOTRegistry.crossbowBolt);
			}
			if (boltItem != null) {
				if (shouldConsume && boltSlot >= 0) {
					--boltItem.stackSize;
					if (boltItem.stackSize <= 0) {
						entityplayer.inventory.mainInventory[boltSlot] = null;
					}
				}
				if (!world.isRemote) {
					setLoaded(itemstack, boltItem.copy());
				}
			}
			entityplayer.clearItemInUse();
		}
	}

	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
		World world = entityplayer.worldObj;
		if (!world.isRemote && !GOTItemCrossbow.isLoaded(itemstack) && getMaxItemUseDuration(itemstack) - count == getMaxDrawTime()) {
			world.playSoundAtEntity(entityplayer, "got:item.crossbowLoad", 1.0f, 1.5f + world.rand.nextFloat() * 0.2f);
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		itemIcon = iconregister.registerIcon(getIconString());
		crossbowPullIcons = new IIcon[3];
		crossbowPullIcons[0] = iconregister.registerIcon(getIconString() + "_" + GOTItemBow.BowState.PULL_0.iconName);
		crossbowPullIcons[1] = iconregister.registerIcon(getIconString() + "_" + GOTItemBow.BowState.PULL_1.iconName);
		crossbowPullIcons[2] = iconregister.registerIcon(getIconString() + "_" + GOTItemBow.BowState.PULL_2.iconName);
	}

	public void setLoaded(ItemStack itemstack, ItemStack ammo) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemCrossbow) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
				itemstack.setTagCompound(nbt);
			}
			if (ammo != null) {
				NBTTagCompound ammoData = new NBTTagCompound();
				ammo.writeToNBT(ammoData);
				nbt.setTag("GOTCrossbowAmmo", ammoData);
			} else {
				nbt.removeTag("GOTCrossbowAmmo");
			}
			if (nbt.hasKey("GOTCrossbowLoaded")) {
				nbt.removeTag("GOTCrossbowLoaded");
			}
		}
	}

	public boolean shouldConsumeBolt(ItemStack itemstack, EntityPlayer entityplayer) {
		return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
	}

	public static void applyCrossbowModifiers(GOTEntityCrossbowBolt bolt, ItemStack itemstack) {
		int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
		if (power > 0) {
			bolt.boltDamageFactor += power * 0.5 + 0.5;
		}
		int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
		punch += GOTEnchantmentHelper.calcRangedKnockback(itemstack);
		if (punch > 0) {
			bolt.knockbackStrength = punch;
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + GOTEnchantmentHelper.calcFireAspect(itemstack) > 0) {
			bolt.setFire(100);
		}
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			GOTEnchantmentHelper.setProjectileEnchantment(bolt, ench);
		}
	}

	public static float getCrossbowLaunchSpeedFactor(ItemStack itemstack) {
		float f = 1.0f;
		if (itemstack != null) {
			if (itemstack.getItem() instanceof GOTItemCrossbow) {
				f = (float) (f * ((GOTItemCrossbow) itemstack.getItem()).boltDamageFactor);
			}
			f *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
		}
		return f;
	}

	public static ItemStack getLoaded(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemCrossbow) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt == null) {
				return null;
			}
			if (nbt.hasKey("GOTCrossbowAmmo")) {
				NBTTagCompound ammoData = nbt.getCompoundTag("GOTCrossbowAmmo");
				return ItemStack.loadItemStackFromNBT(ammoData);
			}
			if (nbt.hasKey("GOTCrossbowLoaded")) {
				return new ItemStack(GOTRegistry.crossbowBolt);
			}
		}
		return null;
	}

	public static boolean isLoaded(ItemStack itemstack) {
		return GOTItemCrossbow.getLoaded(itemstack) != null;
	}
}
