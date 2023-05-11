package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.render.other.GOTDrinkIcons;
import got.common.GOTLevelData;
import got.common.block.other.GOTBlockMug;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GOTItemMug extends Item {
	public static String[] strengthNames = {"weak", "light", "moderate", "strong", "potent"};
	public static float[] strengths = {0.25f, 0.5f, 1.0f, 2.0f, 3.0f};
	public static float[] foodStrengths = {0.5f, 0.75f, 1.0f, 1.25f, 1.5f};
	public static int vesselMeta = 100;
	@SideOnly(value = Side.CLIENT)
	public static IIcon barrelGui_emptyBucketSlotIcon;
	@SideOnly(value = Side.CLIENT)
	public static IIcon barrelGui_emptyMugSlotIcon;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] drinkIcons;
	@SideOnly(value = Side.CLIENT)
	public IIcon liquidIcon;
	public boolean isFullMug;
	public boolean isFoodDrink;
	public boolean isBrewable;
	public float alcoholicity;
	public int foodHealAmount;
	public float foodSaturationAmount;
	public List<PotionEffect> potionEffects = new ArrayList<>();
	public int damageAmount;
	public boolean curesEffects;

	public GOTItemMug(boolean full, boolean food) {
		this(full, food, false, 0.0f);
	}

	public GOTItemMug(boolean full, boolean food, boolean brew, float alc) {
		if (full) {
			setMaxStackSize(1);
			setHasSubtypes(true);
			setMaxDamage(0);
		} else {
			setMaxStackSize(64);
		}
		setCreativeTab(GOTCreativeTabs.tabFood);
		isFullMug = full;
		isFoodDrink = food;
		isBrewable = brew;
		alcoholicity = alc;
	}

	public GOTItemMug(float alc) {
		this(true, false, true, alc);
	}

	public static void addPotionEffectsToTooltip(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag, List itemEffects) {
		if (!itemEffects.isEmpty()) {
			ItemStack potionEquivalent = new ItemStack(Items.potionitem);
			potionEquivalent.setItemDamage(69);
			NBTTagList effectsData = new NBTTagList();
			for (Object itemEffect : itemEffects) {
				PotionEffect effect = (PotionEffect) itemEffect;
				NBTTagCompound nbt = new NBTTagCompound();
				effect.writeCustomPotionEffectToNBT(nbt);
				effectsData.appendTag(nbt);
			}
			potionEquivalent.setTagCompound(new NBTTagCompound());
			potionEquivalent.getTagCompound().setTag("CustomPotionEffects", effectsData);
			ArrayList effectTooltips = new ArrayList<>();
			potionEquivalent.getItem().addInformation(potionEquivalent, entityplayer, effectTooltips, flag);
			list.addAll(effectTooltips);
		}
	}

	public static ItemStack getEquivalentDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemMug) {
				return itemstack;
			}
			if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
				ItemStack water = itemstack.copy();
				water.func_150996_a(GOTRegistry.mugWater);
				GOTItemMug.setVessel(water, Vessel.BOTTLE, false);
				return water;
			}
		}
		return itemstack;
	}

	public static float getFoodStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = GOTItemMug.getStrengthMeta(itemstack);
			return foodStrengths[i];
		}
		return 1.0f;
	}

	public static ItemStack getRealDrink(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() == GOTRegistry.mugWater && GOTItemMug.getVessel(itemstack) == Vessel.BOTTLE) {
			ItemStack water = itemstack.copy();
			water.func_150996_a(Items.potionitem);
			water.setItemDamage(0);
			return water;
		}
		return itemstack;
	}

	public static float getStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = GOTItemMug.getStrengthMeta(itemstack);
			return strengths[i];
		}
		return 1.0f;
	}

	public static int getStrengthMeta(int damage) {
		int i = damage % vesselMeta;
		if (i < 0 || i >= strengths.length) {
			i = 0;
		}
		return i;
	}

	public static int getStrengthMeta(ItemStack itemstack) {
		return GOTItemMug.getStrengthMeta(itemstack.getItemDamage());
	}

	public static String getStrengthSubtitle(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = GOTItemMug.getStrengthMeta(itemstack);
			return StatCollector.translateToLocal("item.got.drink." + strengthNames[i]);
		}
		return null;
	}

	public static Vessel getVessel(int damage) {
		int i = damage / vesselMeta;
		return Vessel.forMeta(i);
	}

	public static Vessel getVessel(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug) {
			GOTItemMug itemMug = (GOTItemMug) item;
			if (itemMug.isFullMug) {
				return GOTItemMug.getVessel(itemstack.getItemDamage());
			}
			return itemMug.getEmptyVesselType();
		}
		if (item == Items.glass_bottle || item == Items.potionitem && itemstack.getItemDamage() == 0) {
			return Vessel.BOTTLE;
		}
		return null;
	}

	public static boolean isItemEmptyDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemMug) {
				return !((GOTItemMug) item).isFullMug;
			}
			if (item == Items.glass_bottle) {
				return true;
			}
		}
		return false;
	}

	public static boolean isItemFullDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemMug) {
				return ((GOTItemMug) item).isFullMug;
			}
			if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
				return true;
			}
		}
		return false;
	}

	public static void setStrengthMeta(ItemStack itemstack, int i) {
		Vessel v = GOTItemMug.getVessel(itemstack);
		itemstack.setItemDamage(i);
		GOTItemMug.setVessel(itemstack, v, true);
	}

	public static void setVessel(ItemStack itemstack, Vessel v, boolean correctItem) {
		if (correctItem && itemstack.getItem() == Items.potionitem && itemstack.getItemDamage() == 0) {
			itemstack.func_150996_a(GOTRegistry.mugWater);
			itemstack.setItemDamage(0);
		}
		int i = itemstack.getItemDamage();
		itemstack.setItemDamage(v.id * vesselMeta + (i %= vesselMeta));
		if (correctItem && itemstack.getItem() == GOTRegistry.mugWater && v == Vessel.BOTTLE) {
			itemstack.func_150996_a(Items.potionitem);
			itemstack.setItemDamage(0);
		}
	}

	public static boolean tryPlaceMug(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side) {
		Vessel vessel = GOTItemMug.getVessel(itemstack);
		if (vessel == null || !vessel.canPlace) {
			return false;
		}
		Block mugBlock = vessel.getBlock();
		Block block = world.getBlock(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]);
		if (block != null && !block.isReplaceable(world, i, j, k) || block.getMaterial() == Material.water) {
			return false;
		}
		if (entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
			if (!mugBlock.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
			world.setBlock(i, j, k, mugBlock, l, 3);
			ItemStack mugFill = itemstack.copy();
			mugFill.stackSize = 1;
			GOTBlockMug.setMugItem(world, i, j, k, mugFill, vessel);
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, mugBlock.stepSound.func_150496_b(), (mugBlock.stepSound.getVolume() + 1.0f) / 2.0f, mugBlock.stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isBrewable) {
			float strength = GOTItemMug.getStrength(itemstack);
			list.add(GOTItemMug.getStrengthSubtitle(itemstack));
			if (alcoholicity > 0.0f) {
				EnumChatFormatting c;
				float f = alcoholicity * strength * 10.0f;
				c = f < 2.0f ? EnumChatFormatting.GREEN : f < 5.0f ? EnumChatFormatting.YELLOW : f < 10.0f ? EnumChatFormatting.GOLD : f < 20.0f ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED;
				list.add(c + StatCollector.translateToLocal("item.got.drink.alcoholicity") + ": " + String.format("%.2f", Float.valueOf(f)) + "%");
			}
			GOTItemMug.addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, convertPotionEffectsForStrength(strength));
		}
	}

	public GOTItemMug addPotionEffect(int i, int j) {
		potionEffects.add(new PotionEffect(i, j * 20));
		return this;
	}

	public void applyToNPC(GOTEntityNPC npc, ItemStack itemstack) {
		float strength = GOTItemMug.getStrength(itemstack);
		npc.heal(foodHealAmount * strength);
		List<PotionEffect> effects = convertPotionEffectsForStrength(strength);
		for (PotionEffect effect : effects) {
			npc.addPotionEffect(effect);
		}
		if (damageAmount > 0) {
			npc.attackEntityFrom(DamageSource.magic, damageAmount * strength);
		}
		if (curesEffects) {
			npc.curePotionEffects(new ItemStack(Items.milk_bucket));
		}
	}

	public boolean canPlayerDrink(EntityPlayer entityplayer) {
		if (!isFullMug) {
			return false;
		}
		return !isFoodDrink || entityplayer.canEat(false);
	}

	public List<PotionEffect> convertPotionEffectsForStrength(float strength) {
		ArrayList<PotionEffect> list = new ArrayList<>();
		for (PotionEffect base : potionEffects) {
			PotionEffect modified = new PotionEffect(base.getPotionID(), (int) (base.getDuration() * strength));
			list.add(modified);
		}
		return list;
	}

	public Vessel getEmptyVesselType() {
		for (Vessel v : Vessel.values()) {
			if (v.getEmptyVesselItem() != this) {
				continue;
			}
			return v;
		}
		return Vessel.MUG;
	}

	@Override
	public IIcon getIconFromDamage(int i) {
		if (isFullMug) {
			if (i == -1) {
				return liquidIcon;
			}
			int vessel = GOTItemMug.getVessel(i).id;
			return drinkIcons[vessel];
		}
		return super.getIconFromDamage(i);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {

		return super.getItemStackDisplayName(itemstack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		if (isFullMug) {
			Vessel[] vesselTypes = {Vessel.MUG};
			if (tab == null || tab.hasSearchBar()) {
				vesselTypes = Vessel.values();
			}
			for (Vessel v : vesselTypes) {
				if (isBrewable) {
					for (int str = 0; str < strengths.length; ++str) {
						ItemStack drink = new ItemStack(item);
						GOTItemMug.setStrengthMeta(drink, str);
						GOTItemMug.setVessel(drink, v, true);
						if (drink.getItem() != item) {
							continue;
						}
						list.add(drink);
					}
					continue;
				}
				ItemStack drink = new ItemStack(item);
				GOTItemMug.setVessel(drink, v, true);
				if (drink.getItem() != item) {
					continue;
				}
				list.add(drink);
			}
		} else {
			super.getSubItems(item, tab, list);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		Vessel vessel = GOTItemMug.getVessel(itemstack);
		float strength = GOTItemMug.getStrength(itemstack);
		float foodStrength = GOTItemMug.getFoodStrength(itemstack);
		if (entityplayer.canEat(false)) {
			entityplayer.getFoodStats().addStats(Math.round(foodHealAmount * foodStrength), foodSaturationAmount * foodStrength);
		}
		if (alcoholicity > 0.0f) {
			int duration;
			float alcoholPower = alcoholicity * strength;
			int tolerance = GOTLevelData.getData(entityplayer).getAlcoholTolerance();
			if (tolerance > 0) {
				float f = (float) Math.pow(0.99, tolerance);
				alcoholPower *= f;
			}
			if (!world.isRemote && itemRand.nextFloat() < alcoholPower && (duration = (int) (60.0f * (1.0f + itemRand.nextFloat() * 0.5f) * alcoholPower)) >= 1) {
				int durationTicks = duration * 20;
				entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, durationTicks));
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.getDrunk);
				int toleranceAdd = Math.round(duration / 20.0f);
				GOTLevelData.getData(entityplayer).setAlcoholTolerance(tolerance += toleranceAdd);
			}
		}
		if (!world.isRemote && shouldApplyPotionEffects(itemstack, entityplayer)) {
			List<PotionEffect> effects = convertPotionEffectsForStrength(strength);
			for (PotionEffect effect : effects) {
				entityplayer.addPotionEffect(effect);
			}
		}
		if (damageAmount > 0) {
			entityplayer.attackEntityFrom(DamageSource.magic, damageAmount * strength);
		}
		if (!world.isRemote && curesEffects) {
			entityplayer.curePotionEffects(new ItemStack(Items.milk_bucket));
		}
		if (vessel == Vessel.SKULL) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkSkull);
		}
		if (!world.isRemote && this == GOTRegistry.mugPlantainBrew) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkPlantainBrew);
			for (Potion potion : Potion.potionTypes) {
				if (potion == null || !GOTReflection.isBadEffect(potion)) {
					continue;
				}
				entityplayer.removePotionEffect(potion.id);
			}
		}
		return !entityplayer.capabilities.isCreativeMode ? new ItemStack(vessel.getEmptyVesselItem()) : itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!isFullMug) {
			ItemStack filled = new ItemStack(GOTRegistry.mugWater);
			GOTItemMug.setVessel(filled, getEmptyVesselType(), true);
			MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);
			if (m == null) {
				return itemstack;
			}
			if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = m.blockX;
				int j = m.blockY;
				int k = m.blockZ;
				if (!world.canMineBlock(entityplayer, i, j, k) || !entityplayer.canPlayerEdit(i, j, k, m.sideHit, itemstack)) {
					return itemstack;
				}
				if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0) {
					--itemstack.stackSize;
					if (itemstack.stackSize <= 0) {
						world.playSoundAtEntity(entityplayer, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
						return filled.copy();
					}
					if (!entityplayer.inventory.addItemStackToInventory(filled.copy())) {
						entityplayer.dropPlayerItemWithRandomChoice(filled.copy(), false);
					}
					world.playSoundAtEntity(entityplayer, "got:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				}
			}
			return itemstack;
		}
		if (canPlayerDrink(entityplayer)) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		return GOTItemMug.tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
	}

	@Override
	public void registerIcons(IIconRegister iconregister) {
		if (isFullMug) {
			drinkIcons = new IIcon[Vessel.values().length];
			for (int i = 0; i < Vessel.values().length; ++i) {
				drinkIcons[i] = GOTDrinkIcons.registerDrinkIcon(iconregister, this, getIconString(), Vessel.values()[i].name);
			}
			liquidIcon = GOTDrinkIcons.registerLiquidIcon(iconregister, this, getIconString());
			barrelGui_emptyBucketSlotIcon = iconregister.registerIcon("got:barrel_empty_bucket_slot");
			barrelGui_emptyMugSlotIcon = iconregister.registerIcon("got:barrel_empty_mug_slot");
		} else {
			super.registerIcons(iconregister);
		}
	}

	public GOTItemMug setCuresEffects() {
		curesEffects = true;
		return this;
	}

	public GOTItemMug setDamageAmount(int i) {
		damageAmount = i;
		return this;
	}

	public GOTItemMug setDrinkStats(int i, float f) {
		foodHealAmount = i;
		foodSaturationAmount = f;
		return this;
	}

	public boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
		return true;
	}

	public enum Vessel {
		MUG(0, "mug", true, 0), MUG_CLAY(1, "clay", true, 1), GOBLET_GOLD(2, "goblet_gold", true, 10), GOBLET_SILVER(3, "goblet_silver", true, 8), GOBLET_COPPER(4, "goblet_copper", true, 5), GOBLET_WOOD(5, "goblet_wood", true, 0), SKULL(6, "skull", true, 3), GLASS(7, "glass", true, 3), BOTTLE(8, "bottle", true, 2), SKIN(9, "skin", false, 0), HORN(10, "horn", true, 5), HORN_GOLD(11, "horn_gold", true, 8);

		public String name;
		public int id;
		public boolean canPlace;
		public int extraPrice;

		Vessel(int i, String s, boolean flag, int p) {
			id = i;
			name = s;
			canPlace = flag;
			extraPrice = p;
		}

		public static Vessel forMeta(int i) {
			for (Vessel v : Vessel.values()) {
				if (v.id != i) {
					continue;
				}
				return v;
			}
			return MUG;
		}

		public Block getBlock() {
			if (this == MUG) {
				return GOTRegistry.mugBlock;
			}
			if (this == MUG_CLAY) {
				return GOTRegistry.ceramicMugBlock;
			}
			if (this == GOBLET_GOLD) {
				return GOTRegistry.gobletGoldBlock;
			}
			if (this == GOBLET_SILVER) {
				return GOTRegistry.gobletSilverBlock;
			}
			if (this == GOBLET_COPPER) {
				return GOTRegistry.gobletCopperBlock;
			}
			if (this == GOBLET_WOOD) {
				return GOTRegistry.gobletWoodBlock;
			}
			if (this == SKULL) {
				return GOTRegistry.skullCupBlock;
			}
			if (this == GLASS) {
				return GOTRegistry.wineGlassBlock;
			}
			if (this == BOTTLE) {
				return GOTRegistry.glassBottleBlock;
			}
			if (this == SKIN) {
				return null;
			}
			if (this == HORN) {
				return GOTRegistry.aleHornBlock;
			}
			if (this == HORN_GOLD) {
				return GOTRegistry.aleHornGoldBlock;
			}
			return GOTRegistry.mugBlock;
		}

		public ItemStack getEmptyVessel() {
			return new ItemStack(getEmptyVesselItem());
		}

		public Item getEmptyVesselItem() {
			if (this == MUG) {
				return GOTRegistry.mug;
			}
			if (this == MUG_CLAY) {
				return GOTRegistry.ceramicMug;
			}
			if (this == GOBLET_GOLD) {
				return GOTRegistry.gobletGold;
			}
			if (this == GOBLET_SILVER) {
				return GOTRegistry.gobletSilver;
			}
			if (this == GOBLET_COPPER) {
				return GOTRegistry.gobletCopper;
			}
			if (this == GOBLET_WOOD) {
				return GOTRegistry.gobletWood;
			}
			if (this == SKULL) {
				return GOTRegistry.skullCup;
			}
			if (this == GLASS) {
				return GOTRegistry.wineGlass;
			}
			if (this == BOTTLE) {
				return Items.glass_bottle;
			}
			if (this == SKIN) {
				return GOTRegistry.waterskin;
			}
			if (this == HORN) {
				return GOTRegistry.aleHorn;
			}
			if (this == HORN_GOLD) {
				return GOTRegistry.aleHornGold;
			}
			return GOTRegistry.mug;
		}
	}

}
