package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.render.other.GOTDrinkIcons;
import got.common.GOTLevelData;
import got.common.block.other.GOTBlockMug;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
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
import java.util.Collection;
import java.util.List;

public class GOTItemMug extends Item {
	private static final String[] STRENGTH_NAMES = {"weak", "light", "moderate", "strong", "potent"};
	private static final float[] STRENGTHS = {0.25f, 0.5f, 1.0f, 2.0f, 3.0f};
	private static final float[] FOOD_STRENGTHS = {0.5f, 0.75f, 1.0f, 1.25f, 1.5f};
	private static final int VESSEL_META = 100;

	@SideOnly(Side.CLIENT)
	private static IIcon barrelGuiEmptyBucketSlotIcon;

	@SideOnly(Side.CLIENT)
	private static IIcon barrelGuiEmptyMugSlotIcon;

	private final boolean isFoodDrink;
	private final boolean isFullMug;
	private final boolean isBrewable;
	private final float alcoholicity;
	private final Collection<PotionEffect> potionEffects = new ArrayList<>();

	@SideOnly(Side.CLIENT)
	private IIcon[] drinkIcons;

	@SideOnly(Side.CLIENT)
	private IIcon liquidIcon;

	private boolean curesEffects;
	private float foodSaturationAmount;
	private int foodHealAmount;
	private int damageAmount;

	public GOTItemMug(float alc) {
		this(true, false, true, alc);
	}

	public GOTItemMug(boolean full, boolean food) {
		this(full, food, false, 0.0f);
	}

	private GOTItemMug(boolean full, boolean food, boolean brew, float alc) {
		if (full) {
			setMaxStackSize(1);
			setHasSubtypes(true);
			setMaxDamage(0);
		} else {
			setMaxStackSize(64);
		}
		setCreativeTab(GOTCreativeTabs.TAB_FOOD);
		isFullMug = full;
		isFoodDrink = food;
		isBrewable = brew;
		alcoholicity = alc;
	}

	private static void addPotionEffectsToTooltip(EntityPlayer entityplayer, Collection<String> list, boolean flag, Collection<PotionEffect> itemEffects) {
		if (!itemEffects.isEmpty()) {
			ItemStack potionEquivalent = new ItemStack(Items.potionitem);
			potionEquivalent.setItemDamage(69);
			NBTTagList effectsData = new NBTTagList();
			for (PotionEffect itemEffect : itemEffects) {
				NBTTagCompound nbt = new NBTTagCompound();
				itemEffect.writeCustomPotionEffectToNBT(nbt);
				effectsData.appendTag(nbt);
			}
			potionEquivalent.setTagCompound(new NBTTagCompound());
			potionEquivalent.getTagCompound().setTag("CustomPotionEffects", effectsData);
			List<String> effectTooltips = new ArrayList<>();
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
				water.func_150996_a(GOTItems.mugWater);
				setVessel(water, Vessel.BOTTLE, false);
				return water;
			}
		}
		return itemstack;
	}

	public static float getFoodStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return FOOD_STRENGTHS[i];
		}
		return 1.0f;
	}

	public static float getStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return STRENGTHS[i];
		}
		return 1.0f;
	}

	private static int getStrengthMeta(int damage) {
		int i = damage % VESSEL_META;
		if (i < 0 || i >= STRENGTHS.length) {
			return 0;
		}
		return i;
	}

	private static int getStrengthMeta(ItemStack itemstack) {
		return getStrengthMeta(itemstack.getItemDamage());
	}

	public static String getStrengthSubtitle(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof GOTItemMug && ((GOTItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return StatCollector.translateToLocal("item.got.drink." + STRENGTH_NAMES[i]);
		}
		return null;
	}

	private static Vessel getVessel(int damage) {
		int i = damage / VESSEL_META;
		return Vessel.forMeta(i);
	}

	public static Vessel getVessel(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug) {
			GOTItemMug itemMug = (GOTItemMug) item;
			if (itemMug.isFullMug) {
				return getVessel(itemstack.getItemDamage());
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
			return item == Items.glass_bottle;
		}
		return false;
	}

	public static boolean isItemFullDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemMug) {
				return ((GOTItemMug) item).isFullMug;
			}
			return item == Items.potionitem && itemstack.getItemDamage() == 0;
		}
		return false;
	}

	public static void setStrengthMeta(ItemStack itemstack, int i) {
		Vessel v = getVessel(itemstack);
		itemstack.setItemDamage(i);
		setVessel(itemstack, v, true);
	}

	public static void setVessel(ItemStack itemstack, Vessel v, boolean correctItem) {
		if (correctItem && itemstack.getItem() == Items.potionitem && itemstack.getItemDamage() == 0) {
			itemstack.func_150996_a(GOTItems.mugWater);
			itemstack.setItemDamage(0);
		}
		int i = itemstack.getItemDamage();
		itemstack.setItemDamage(v.getId() * VESSEL_META + i % VESSEL_META);
		if (correctItem && itemstack.getItem() == GOTItems.mugWater && v == Vessel.BOTTLE) {
			itemstack.func_150996_a(Items.potionitem);
			itemstack.setItemDamage(0);
		}
	}

	public static boolean tryPlaceMug(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side) {
		int i1 = i;
		int j1 = j;
		int k1 = k;
		Vessel vessel = getVessel(itemstack);
		if (vessel == null || !vessel.isCanPlace()) {
			return false;
		}
		Block mugBlock = vessel.getBlock();
		Block block = world.getBlock(i1 += Facing.offsetsXForSide[side], j1 += Facing.offsetsYForSide[side], k1 += Facing.offsetsZForSide[side]);
		if (block != null && !block.isReplaceable(world, i1, j1, k1) || block != null && block.getMaterial() == Material.water) {
			return false;
		}
		if (entityplayer.canPlayerEdit(i1, j1, k1, side, itemstack)) {
			if (!mugBlock.canPlaceBlockAt(world, i1, j1, k1)) {
				return false;
			}
			int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
			world.setBlock(i1, j1, k1, mugBlock, l, 3);
			ItemStack mugFill = itemstack.copy();
			mugFill.stackSize = 1;
			GOTBlockMug.setMugItem(world, i1, j1, k1, mugFill, vessel);
			world.playSoundEffect(i1 + 0.5, j1 + 0.5, k1 + 0.5, mugBlock.stepSound.func_150496_b(), (mugBlock.stepSound.getVolume() + 1.0f) / 2.0f, mugBlock.stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getBarrelGuiEmptyMugSlotIcon() {
		return barrelGuiEmptyMugSlotIcon;
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getBarrelGuiEmptyBucketSlotIcon() {
		return barrelGuiEmptyBucketSlotIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isBrewable) {
			float strength = getStrength(itemstack);
			list.add(getStrengthSubtitle(itemstack));
			if (alcoholicity > 0.0f) {
				float f = alcoholicity * strength * 10.0f;
				EnumChatFormatting c = f < 2.0f ? EnumChatFormatting.GREEN : f < 5.0f ? EnumChatFormatting.YELLOW : f < 10.0f ? EnumChatFormatting.GOLD : f < 20.0f ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED;
				list.add(c + StatCollector.translateToLocal("item.got.drink.alcoholicity") + ": " + String.format("%.2f", f) + '%');
			}
			addPotionEffectsToTooltip(entityplayer, list, flag, convertPotionEffectsForStrength(strength));
		}
	}

	public GOTItemMug addPotionEffect(int i, int j) {
		potionEffects.add(new PotionEffect(i, j * 20));
		return this;
	}

	public void applyToNPC(GOTEntityNPC npc, ItemStack itemstack) {
		float strength = getStrength(itemstack);
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
		return isFullMug && (!isFoodDrink || entityplayer.canEat(false));
	}

	private List<PotionEffect> convertPotionEffectsForStrength(float strength) {
		List<PotionEffect> list = new ArrayList<>();
		for (PotionEffect base : potionEffects) {
			PotionEffect modified = new PotionEffect(base.getPotionID(), (int) (base.getDuration() * strength));
			list.add(modified);
		}
		return list;
	}

	private Vessel getEmptyVesselType() {
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
			int vessel = getVessel(i).getId();
			return drinkIcons[vessel];
		}
		return super.getIconFromDamage(i);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		if (isFullMug) {
			Vessel[] vesselTypes = {Vessel.MUG};
			if (tab == null || tab.hasSearchBar()) {
				vesselTypes = Vessel.values();
			}
			for (Vessel v : vesselTypes) {
				if (isBrewable) {
					for (int str = 0; str < STRENGTHS.length; ++str) {
						ItemStack drink = new ItemStack(item);
						setStrengthMeta(drink, str);
						setVessel(drink, v, true);
						if (drink.getItem() != item) {
							continue;
						}
						list.add(drink);
					}
					continue;
				}
				ItemStack drink = new ItemStack(item);
				setVessel(drink, v, true);
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
		Vessel vessel = getVessel(itemstack);
		float strength = getStrength(itemstack);
		float foodStrength = getFoodStrength(itemstack);
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
				GOTLevelData.getData(entityplayer).setAlcoholTolerance(tolerance + toleranceAdd);
			}
		}
		if (!world.isRemote && shouldApplyPotionEffects(entityplayer)) {
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
		if (!world.isRemote && this == GOTItems.mugPlantainBrew) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkPlantainBrew);
			for (Potion potion : Potion.potionTypes) {
				if (potion == null || !GOTReflection.isBadEffect(potion)) {
					continue;
				}
				entityplayer.removePotionEffect(potion.id);
			}
		}
		if (!world.isRemote && this == GOTItems.mugUnsulliedTonic) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.drinkUnsulliedTonic);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			return new ItemStack(vessel.getEmptyVesselItem());
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!isFullMug) {
			ItemStack filled = new ItemStack(GOTItems.mugWater);
			setVessel(filled, getEmptyVesselType(), true);
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
		return tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
	}

	@Override
	public void registerIcons(IIconRegister iconregister) {
		if (isFullMug) {
			drinkIcons = new IIcon[Vessel.values().length];
			for (int i = 0; i < Vessel.values().length; ++i) {
				drinkIcons[i] = GOTDrinkIcons.registerDrinkIcon(iconregister, this, getIconString(), Vessel.values()[i].getName());
			}
			liquidIcon = GOTDrinkIcons.registerLiquidIcon(iconregister, getIconString());
			barrelGuiEmptyBucketSlotIcon = iconregister.registerIcon("got:barrel_empty_bucket_slot");
			barrelGuiEmptyMugSlotIcon = iconregister.registerIcon("got:barrel_empty_mug_slot");
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

	protected boolean shouldApplyPotionEffects(EntityPlayer entityplayer) {
		return true;
	}

	public float getAlcoholicity() {
		return alcoholicity;
	}

	public boolean isBrewable() {
		return isBrewable;
	}

	public boolean isFullMug() {
		return isFullMug;
	}

	public enum Vessel {
		MUG(0, "mug", true, 0),
		MUG_CLAY(1, "clay", true, 1),
		GOBLET_GOLD(2, "goblet_gold", true, 10),
		GOBLET_SILVER(3, "goblet_silver", true, 8),
		GOBLET_COPPER(4, "goblet_copper", true, 5),
		GOBLET_WOOD(5, "goblet_wood", true, 0),
		SKULL(6, "skull", true, 3),
		GLASS(7, "glass", true, 3),
		BOTTLE(8, "bottle", true, 2),
		SKIN(9, "skin", false, 0),
		HORN(10, "horn", true, 5),
		HORN_GOLD(11, "horn_gold", true, 8),
		GOBLET_BRONZE(12, "goblet_bronze", true, 5),
		GOBLET_VALYRIAN(13, "goblet_valyrian", true, 15);

		private final int id;
		private final int extraPrice;
		private final boolean canPlace;
		private final String name;

		Vessel(int i, String s, boolean flag, int p) {
			id = i;
			name = s;
			canPlace = flag;
			extraPrice = p;
		}

		public static Vessel forMeta(int i) {
			for (Vessel v : values()) {
				if (v.id != i) {
					continue;
				}
				return v;
			}
			return MUG;
		}

		public Block getBlock() {
			switch (this) {
				case MUG_CLAY:
					return GOTBlocks.ceramicMug;
				case GOBLET_GOLD:
					return GOTBlocks.gobletGold;
				case GOBLET_SILVER:
					return GOTBlocks.gobletSilver;
				case GOBLET_COPPER:
					return GOTBlocks.gobletCopper;
				case GOBLET_VALYRIAN:
					return GOTBlocks.gobletValyrian;
				case GOBLET_BRONZE:
					return GOTBlocks.gobletBronze;
				case GOBLET_WOOD:
					return GOTBlocks.gobletWood;
				case SKULL:
					return GOTBlocks.skullCup;
				case GLASS:
					return GOTBlocks.wineGlass;
				case BOTTLE:
					return GOTBlocks.glassBottle;
				case HORN:
					return GOTBlocks.aleHorn;
				case HORN_GOLD:
					return GOTBlocks.aleHornGold;
				case SKIN:
					return null;
				default:
					return GOTBlocks.mug;
			}
		}

		public ItemStack getEmptyVessel() {
			return new ItemStack(getEmptyVesselItem());
		}

		public Item getEmptyVesselItem() {
			switch (this) {
				case MUG_CLAY:
					return GOTItems.ceramicMug;
				case GOBLET_GOLD:
					return GOTItems.gobletGold;
				case GOBLET_SILVER:
					return GOTItems.gobletSilver;
				case GOBLET_VALYRIAN:
					return GOTItems.gobletValyrian;
				case GOBLET_BRONZE:
					return GOTItems.gobletBronze;
				case GOBLET_COPPER:
					return GOTItems.gobletCopper;
				case GOBLET_WOOD:
					return GOTItems.gobletWood;
				case SKULL:
					return GOTItems.skullCup;
				case GLASS:
					return GOTItems.wineGlass;
				case BOTTLE:
					return Items.glass_bottle;
				case SKIN:
					return GOTItems.waterskin;
				case HORN:
					return GOTItems.aleHorn;
				case HORN_GOLD:
					return GOTItems.aleHornGold;
				default:
					return GOTItems.mug;
			}
		}

		public int getId() {
			return id;
		}

		public boolean isCanPlace() {
			return canPlace;
		}

		public int getExtraPrice() {
			return extraPrice;
		}

		private String getName() {
			return name;
		}
	}
}