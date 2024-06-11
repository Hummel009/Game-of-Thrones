package got.common.item.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.inanimate.GOTEntityCrossbowBolt;
import got.common.recipe.GOTRecipe;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemCrossbow extends ItemBow {
	private final Item.ToolMaterial crossbowMaterial;
	private final double boltDamageFactor;

	@SideOnly(Side.CLIENT)
	private IIcon[] crossbowPullIcons;

	public GOTItemCrossbow(Item.ToolMaterial material) {
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		crossbowMaterial = material;
		setMaxDamage((int) (crossbowMaterial.getMaxUses() * 1.25f));
		setMaxStackSize(1);
		boltDamageFactor = 1.0f + Math.max(0.0f, (crossbowMaterial.getDamageVsEntity() - 2.0f) * 0.1f);
	}

	public static void applyCrossbowModifiers(GOTEntityCrossbowBolt bolt, ItemStack itemstack) {
		int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
		if (power > 0) {
			bolt.setBoltDamageFactor(bolt.getBoltDamageFactor() + power * 0.5 + 0.5);
		}
		int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
		punch += GOTEnchantmentHelper.calcRangedKnockback(itemstack);
		if (punch > 0) {
			bolt.setKnockbackStrength(punch);
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + GOTEnchantmentHelper.calcFireAspect(itemstack) > 0) {
			bolt.setFire(100);
		}
		for (GOTEnchantment ench : GOTEnchantment.CONTENT) {
			if (!ench.getApplyToProjectile() || !GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
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

	private static ItemStack getLoaded(ItemStack itemstack) {
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
				return new ItemStack(GOTItems.crossbowBolt);
			}
		}
		return null;
	}

	public static boolean isLoaded(ItemStack itemstack) {
		return getLoaded(itemstack) != null;
	}

	private static int getInvBoltSlot(EntityPlayer entityplayer) {
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			ItemStack invItem = entityplayer.inventory.mainInventory[slot];
			if (invItem == null || !(invItem.getItem() instanceof GOTItemCrossbowBolt)) {
				continue;
			}
			return slot;
		}
		return -1;
	}

	private static void setLoaded(ItemStack itemstack, ItemStack ammo) {
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

	private static boolean shouldConsumeBolt(ItemStack itemstack, EntityPlayer entityplayer) {
		return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		ItemStack ammo = getLoaded(itemstack);
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

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
		if (isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		if (usingItem != null && usingItem.getItem() == this) {
			int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
			double useAmount = (double) ticksInUse / 50;
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

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		if (isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		return itemIcon;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return GOTRecipe.checkItemEquals(crossbowMaterial.getRepairItemStack(), repairItem) || super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public int getItemEnchantability() {
		return 1 + crossbowMaterial.getEnchantability() / 5;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String name = super.getItemStackDisplayName(itemstack);
		if (isLoaded(itemstack)) {
			return StatCollector.translateToLocalFormatted("item.got.crossbow.loaded", name);
		}
		return name;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (isLoaded(itemstack)) {
			ItemStack boltItem = getLoaded(itemstack);
			if (boltItem != null) {
				ItemStack shotBolt = boltItem.copy();
				shotBolt.stackSize = 1;
				GOTEntityCrossbowBolt bolt = new GOTEntityCrossbowBolt(world, entityplayer, shotBolt, 2.0f * getCrossbowLaunchSpeedFactor(itemstack));
				if (bolt.getBoltDamageFactor() < 1.0) {
					bolt.setBoltDamageFactor(1.0);
				}
				bolt.setIsCritical(true);
				applyCrossbowModifiers(bolt, itemstack);
				if (!shouldConsumeBolt(itemstack, entityplayer)) {
					bolt.setCanBePickedUp(2);
				}
				if (!world.isRemote) {
					world.spawnEntityInWorld(bolt);

					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useCrossbow);
				}
				world.playSoundAtEntity(entityplayer, "got:item.crossbow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.5f);
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
		if (ticksInUse >= 50 && !isLoaded(itemstack)) {
			ItemStack boltItem = null;
			int boltSlot = getInvBoltSlot(entityplayer);
			if (boltSlot >= 0) {
				boltItem = entityplayer.inventory.mainInventory[boltSlot];
			}
			boolean shouldConsume = shouldConsumeBolt(itemstack, entityplayer);
			if (boltItem == null && !shouldConsume) {
				boltItem = new ItemStack(GOTItems.crossbowBolt);
			}
			if (boltItem != null) {
				if (shouldConsume) {
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
		if (!world.isRemote && !isLoaded(itemstack) && getMaxItemUseDuration(itemstack) - count == 50) {
			world.playSoundAtEntity(entityplayer, "got:item.crossbowLoad", 1.0f, 1.5f + world.rand.nextFloat() * 0.2f);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		itemIcon = iconregister.registerIcon(getIconString());
		crossbowPullIcons = new IIcon[3];
		crossbowPullIcons[0] = iconregister.registerIcon(getIconString() + '_' + GOTItemBow.BowState.PULL_0.getIconName());
		crossbowPullIcons[1] = iconregister.registerIcon(getIconString() + '_' + GOTItemBow.BowState.PULL_1.getIconName());
		crossbowPullIcons[2] = iconregister.registerIcon(getIconString() + '_' + GOTItemBow.BowState.PULL_2.getIconName());
	}

	public double getBoltDamageFactor() {
		return boltDamageFactor;
	}
}