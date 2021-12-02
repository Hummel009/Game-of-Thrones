package got.common.database;

import java.util.*;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class GOTMaterial {
	public static float[] protectionBase = { 0.14f, 0.4f, 0.32f, 0.14f };
	public static float maxProtection = 25.0f;
	public static List<GOTMaterial> allGOTMaterials = new ArrayList<>();
	public static ToolMaterial LOG = EnumHelper.addToolMaterial("LOG", 0, 59, 2.0F, 0.0F, 15).setRepairItem(new ItemStack(Blocks.planks));
	public static ToolMaterial ROCK = EnumHelper.addToolMaterial("ROCK", 1, 131, 4.0F, 1.0F, 5).setRepairItem(new ItemStack(Blocks.stone));
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 2, 250, 6.0F, 2.0F, 14).setRepairItem(new ItemStack(Items.iron_ingot));

	public static GOTMaterial ANONYMOUS = new GOTMaterial("ANONYMOUS").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
	public static GOTMaterial HAND = new GOTMaterial("HAND").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
	public static GOTMaterial COBALT = new GOTMaterial("COBALT").setUses(800).setDamage(3.0f).setProtection(0.7f).setHarvestLevel(4).setSpeed(8.5f).setEnchantability(10);
	public static GOTMaterial VALYRIAN = new GOTMaterial("VALYRIAN").setUses(2400).setDamage(5.0f).setProtection(0.8f).setHarvestLevel(4).setSpeed(9.0f).setEnchantability(8);

	public static GOTMaterial DOTHRAKI = new GOTMaterial("DOTHRAKI").setIsWeak(Item.getItemFromBlock(GOTRegistry.driedReeds));
	public static GOTMaterial BONE = new GOTMaterial("BONE").setIsWeak(Items.bone);
	public static GOTMaterial BRONZE = new GOTMaterial("BRONZE").setIsWeak(GOTRegistry.bronzeIngot);
	public static GOTMaterial FUR = new GOTMaterial("FUR").setIsWeak(GOTRegistry.fur);
	public static GOTMaterial GEMSBOK = new GOTMaterial("GEMSBOK").setIsWeak(GOTRegistry.gemsbokHide);
	public static GOTMaterial KAFTAN = new GOTMaterial("KAFTAN").setIsWeak(Item.getItemFromBlock(Blocks.wool));
	public static GOTMaterial LHAZAR = new GOTMaterial("LHAZAR").setIsWeak(GOTRegistry.gemsbokHide);
	public static GOTMaterial HORN = new GOTMaterial("HORN").setIsWeak(GOTRegistry.rhinoHorn);
	public static GOTMaterial LHAZAR_LION = new GOTMaterial("LHAZAR_LION").setIsWeak(GOTRegistry.lionFur);
	public static GOTMaterial ROBES = new GOTMaterial("ROBES").setIsWeak(Item.getItemFromBlock(Blocks.wool));
	public static GOTMaterial OBSIDIAN = new GOTMaterial("OBSIDIAN").setIsWeak(GOTRegistry.obsidianShard);
	public static GOTMaterial SOTHORYOS_GOLD = new GOTMaterial("SOTHORYOS_GOLD").setIsWeak(Items.gold_ingot);
	public static GOTMaterial FLINT = new GOTMaterial("FLINT").setIsWeak(Items.flint);

	public static GOTMaterial IBBEN = new GOTMaterial("IBBEN").setIsStandart();
	public static GOTMaterial ARRYN = new GOTMaterial("ARRYN").setIsStandart();
	public static GOTMaterial ASSHAI = new GOTMaterial("ASSHAI").setIsStandart();
	public static GOTMaterial BRAAVOS = new GOTMaterial("BRAAVOS").setIsStandart();
	public static GOTMaterial CROWNLANDS = new GOTMaterial("CROWNLANDS").setIsStandart();
	public static GOTMaterial DORNE = new GOTMaterial("DORNE").setIsStandart();
	public static GOTMaterial JOGOS = new GOTMaterial("JOGOS").setIsStandart();
	public static GOTMaterial DRAGONSTONE = new GOTMaterial("DRAGONSTONE").setIsStandart();
	public static GOTMaterial GHISCAR = new GOTMaterial("GHISCAR").setIsStandart();
	public static GOTMaterial GIFT = new GOTMaterial("GIFT").setIsStandart();
	public static GOTMaterial GOLDENCOMPANY = new GOTMaterial("GOLDENCOMPANY").setIsStandart();
	public static GOTMaterial HILLMEN = new GOTMaterial("HILLMEN").setIsStandart();
	public static GOTMaterial IRONBORN = new GOTMaterial("IRONBORN").setIsStandart();
	public static GOTMaterial LORATH = new GOTMaterial("LORATH").setIsStandart();
	public static GOTMaterial LYS = new GOTMaterial("LYS").setIsStandart();
	public static GOTMaterial MOSSOVY = new GOTMaterial("MOSSOVY").setIsStandart();
	public static GOTMaterial MYR = new GOTMaterial("MYR").setIsStandart();
	public static GOTMaterial NORTH = new GOTMaterial("NORTH").setIsStandart();
	public static GOTMaterial NORVOS = new GOTMaterial("NORVOS").setIsStandart();
	public static GOTMaterial PENTOS = new GOTMaterial("PENTOS").setIsStandart();
	public static GOTMaterial QARTH = new GOTMaterial("QARTH").setIsStandart();
	public static GOTMaterial QOHOR = new GOTMaterial("QOHOR").setIsStandart();
	public static GOTMaterial REACH = new GOTMaterial("REACH").setIsStandart();
	public static GOTMaterial RIVERLANDS = new GOTMaterial("RIVERLANDS").setIsStandart();
	public static GOTMaterial SOTHORYOS = new GOTMaterial("SOTHORYOS").setIsStandart();
	public static GOTMaterial STORMLANDS = new GOTMaterial("STORMLANDS").setIsStandart();
	public static GOTMaterial SUMMER = new GOTMaterial("SUMMER").setIsStandart();
	public static GOTMaterial TYROSH = new GOTMaterial("TYROSH").setIsStandart();
	public static GOTMaterial VOLANTIS = new GOTMaterial("VOLANTIS").setIsStandart();
	public static GOTMaterial WESTERLANDS = new GOTMaterial("WESTERLANDS").setIsStandart();
	public static GOTMaterial YITI = new GOTMaterial("YITI").setIsStandart();

	public static GOTMaterial ROYCE = new GOTMaterial("ROYCE").setIsStrong(GOTRegistry.bronzeIngot);
	public static GOTMaterial REDKING = new GOTMaterial("REDKING").setIsStrong(Items.iron_ingot);
	public static GOTMaterial REACHGUARD = new GOTMaterial("REACHGUARD").setIsStrong(Items.iron_ingot);
	public static GOTMaterial NORTHGUARD = new GOTMaterial("NORTHGUARD").setIsStrong(Items.iron_ingot);
	public static GOTMaterial KINGSGUARD = new GOTMaterial("KINGSGUARD").setIsStrong(Items.iron_ingot);
	public static GOTMaterial ARRYNGUARD = new GOTMaterial("ARRYNGUARD").setIsStrong(Items.iron_ingot);
	public static GOTMaterial BLACKFYRE = new GOTMaterial("BLACKFYRE").setIsStrong(Items.iron_ingot);
	public static GOTMaterial ICE = new GOTMaterial("ICE").setIsStrong(GOTRegistry.iceShard);
	public static GOTMaterial HELMET = new GOTMaterial("HELMET").setIsStrong(Items.iron_ingot);
	public static GOTMaterial YITI_FRONTIER = new GOTMaterial("YITI_FRONTIER").setIsStrong(GOTRegistry.yitiSteelIngot);
	public static GOTMaterial YITI_SAMURAI = new GOTMaterial("YITI_SAMURAI").setIsStrong(GOTRegistry.yitiSteelIngot);
	public static GOTMaterial WESTERLANDSGUARD = new GOTMaterial("WESTERLANDSGUARD").setIsStrong(Items.iron_ingot);
	public static GOTMaterial WESTKING = new GOTMaterial("WESTKING").setIsStrong(Items.iron_ingot);
	public static GOTMaterial UNSULLIED = new GOTMaterial("UNSULLIED").setIsStrong(Items.iron_ingot);
	public static GOTMaterial TARGARYEN = new GOTMaterial("TARGARYEN").setIsStrong(Items.iron_ingot);
	public static GOTMaterial BLACKSKIN = new GOTMaterial("BLACKSKIN").setIsStrong(Items.iron_ingot);
	public static GOTMaterial SILVER = new GOTMaterial("SILVER").setIsStrong(GOTRegistry.silverIngot);
	public static GOTMaterial RENLY = new GOTMaterial("RENLY").setIsStrong(Items.iron_ingot);

	public String materialName;

	public boolean undamageable = false;

	public int uses;

	public float damage;

	public int[] protection;
	public int harvestLevel;
	public float speed;
	public int enchantability;
	public boolean canHarvestManFlesh = false;
	public Item.ToolMaterial toolMaterial;
	public ItemArmor.ArmorMaterial armorMaterial;

	public GOTMaterial(String name) {
		materialName = "GOT_" + name;
		allGOTMaterials.add(this);
	}

	public boolean canHarvestManFlesh() {
		return canHarvestManFlesh;
	}

	public boolean isDamageable() {
		return !undamageable;
	}

	public void setCraftingItem(Item item) {
		setCraftingItems(item, item);
	}

	public void setCraftingItems(Item toolItem, Item armorItem) {
		toToolMaterial().setRepairItem(new ItemStack(toolItem));
		toArmorMaterial().customCraftingMaterial = armorItem;
	}

	public GOTMaterial setDamage(float f) {
		damage = f;
		return this;
	}

	public GOTMaterial setEnchantability(int i) {
		enchantability = i;
		return this;
	}

	public GOTMaterial setHarvestLevel(int i) {
		harvestLevel = i;
		return this;
	}

	public GOTMaterial setIsStandart() {
		setUses(300).setDamage(2.5f).setProtection(0.5f).setSpeed(2.0f).setHarvestLevel(6).setEnchantability(10).setCraftingItem(Items.iron_ingot);
		return this;
	}

	public GOTMaterial setIsStrong(Item item) {
		setUses(500).setDamage(3.0f).setProtection(0.6f).setSpeed(6.0f).setHarvestLevel(2).setEnchantability(10).setCraftingItem(item);
		return this;
	}

	public GOTMaterial setIsWeak(Item item) {
		setUses(200).setDamage(0.0f).setProtection(0.4f).setHarvestLevel(0).setSpeed(0.0f).setEnchantability(8).setCraftingItem(item);
		return this;
	}

	public GOTMaterial setManFlesh() {
		canHarvestManFlesh = true;
		return this;
	}

	public GOTMaterial setProtection(float f) {
		protection = new int[protectionBase.length];
		for (int i = 0; i < protection.length; ++i) {
			protection[i] = Math.round(protectionBase[i] * f * maxProtection);
		}
		return this;
	}

	public GOTMaterial setSpeed(float f) {
		speed = f;
		return this;
	}

	public GOTMaterial setUndamageable() {
		undamageable = true;
		return this;
	}

	public GOTMaterial setUses(int i) {
		uses = i;
		return this;
	}

	public ItemArmor.ArmorMaterial toArmorMaterial() {
		if (armorMaterial == null) {
			armorMaterial = EnumHelper.addArmorMaterial(materialName, Math.round(uses * 0.06f), protection, enchantability);
		}
		return armorMaterial;
	}

	public Item.ToolMaterial toToolMaterial() {
		if (toolMaterial == null) {
			toolMaterial = EnumHelper.addToolMaterial(materialName, harvestLevel, uses, speed, damage, enchantability);
		}
		return toolMaterial;
	}

	public static ItemArmor.ArmorMaterial getArmorMaterialByName(String name) {
		return ItemArmor.ArmorMaterial.valueOf(name);
	}

	public static Item.ToolMaterial getToolMaterialByName(String name) {
		return Item.ToolMaterial.valueOf(name);
	}

	public static void onInit() {
		COBALT.setCraftingItem(GOTRegistry.cobaltIngot);
		VALYRIAN.setCraftingItem(GOTRegistry.valyrianIngot);
	}
}
