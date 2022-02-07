package got.common.database;

import java.util.*;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class GOTMaterial {
	private static float[] protectionBase = { 0.14f, 0.4f, 0.32f, 0.14f };
	private static float maxProtection = 25.0f;
	private static List<GOTMaterial> allGOTMaterials = new ArrayList<>();
	public static ToolMaterial LOG = EnumHelper.addToolMaterial("LOG", 0, 59, 2.0F, 0.0F, 15).setRepairItem(new ItemStack(Blocks.planks));
	public static ToolMaterial ROCK = EnumHelper.addToolMaterial("ROCK", 1, 131, 4.0F, 1.0F, 5).setRepairItem(new ItemStack(Blocks.stone));
	public static ToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 2, 250, 6.0F, 2.0F, 14).setRepairItem(new ItemStack(Items.iron_ingot));

	public static GOTMaterial ANONYMOUS = new GOTMaterial("ANONYMOUS").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
	public static GOTMaterial HAND = new GOTMaterial("HAND").setUndamageable().setUses(0).setDamage(0.0f).setProtection(0.0f).setEnchantability(0);
	public static GOTMaterial VALYRIAN = new GOTMaterial("VALYRIAN").setUses(2400).setDamage(5.0f).setProtection(0.8f).setHarvestLevel(4).setSpeed(9.0f).setEnchantability(8);

	public static GOTMaterial DOTHRAKI = new GOTMaterial("DOTHRAKI").setIsWeak();
	public static GOTMaterial BONE = new GOTMaterial("BONE").setIsWeak();
	public static GOTMaterial BRONZE = new GOTMaterial("BRONZE").setIsWeak();
	public static GOTMaterial FUR = new GOTMaterial("FUR").setIsWeak();
	public static GOTMaterial GEMSBOK = new GOTMaterial("GEMSBOK").setIsWeak();
	public static GOTMaterial KAFTAN = new GOTMaterial("KAFTAN").setIsWeak();
	public static GOTMaterial LHAZAR = new GOTMaterial("LHAZAR").setIsWeak();
	public static GOTMaterial HORN = new GOTMaterial("HORN").setIsWeak();
	public static GOTMaterial LHAZAR_LION = new GOTMaterial("LHAZAR_LION").setIsWeak();
	public static GOTMaterial ROBES = new GOTMaterial("ROBES").setIsWeak();
	public static GOTMaterial OBSIDIAN = new GOTMaterial("OBSIDIAN").setIsWeak();
	public static GOTMaterial SOTHORYOS_GOLD = new GOTMaterial("SOTHORYOS_GOLD").setIsWeak();
	public static GOTMaterial FLINT = new GOTMaterial("FLINT").setIsWeak();

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

	public static GOTMaterial ALLOY_STEEL = new GOTMaterial("COBALT").setIsStrong();
	public static GOTMaterial ROYCE = new GOTMaterial("ROYCE").setIsStrong();
	public static GOTMaterial REDKING = new GOTMaterial("REDKING").setIsStrong();
	public static GOTMaterial REACHGUARD = new GOTMaterial("REACHGUARD").setIsStrong();
	public static GOTMaterial NORTHGUARD = new GOTMaterial("NORTHGUARD").setIsStrong();
	public static GOTMaterial KINGSGUARD = new GOTMaterial("KINGSGUARD").setIsStrong();
	public static GOTMaterial ARRYNGUARD = new GOTMaterial("ARRYNGUARD").setIsStrong();
	public static GOTMaterial BLACKFYRE = new GOTMaterial("BLACKFYRE").setIsStrong();
	public static GOTMaterial ICE = new GOTMaterial("ICE").setIsStrong();
	public static GOTMaterial HELMET = new GOTMaterial("HELMET").setIsStrong();
	public static GOTMaterial YITI_FRONTIER = new GOTMaterial("YITI_FRONTIER").setIsStrong();
	public static GOTMaterial YITI_SAMURAI = new GOTMaterial("YITI_SAMURAI").setIsStrong();
	public static GOTMaterial WESTERLANDSGUARD = new GOTMaterial("WESTERLANDSGUARD").setIsStrong();
	public static GOTMaterial WESTKING = new GOTMaterial("WESTKING").setIsStrong();
	public static GOTMaterial UNSULLIED = new GOTMaterial("UNSULLIED").setIsStrong();
	public static GOTMaterial TARGARYEN = new GOTMaterial("TARGARYEN").setIsStrong();
	public static GOTMaterial BLACKSKIN = new GOTMaterial("BLACKSKIN").setIsStrong();
	public static GOTMaterial SILVER = new GOTMaterial("SILVER").setIsStrong();
	public static GOTMaterial RENLY = new GOTMaterial("RENLY").setIsStrong();

	private String materialName;
	private boolean undamageable;
	private int uses;
	private float damage;
	private int[] protection;
	private int harvestLevel;
	private float speed;
	private int enchantability;
	private Item.ToolMaterial toolMaterial;
	private ItemArmor.ArmorMaterial armorMaterial;

	private GOTMaterial(String name) {
		materialName = "GOT_" + name;
		allGOTMaterials.add(this);
	}

	public boolean isDamageable() {
		return !undamageable;
	}

	private void setCraftingItem(Item item) {
		setCraftingItems(item, item);
	}

	private void setCraftingItems(Item toolItem, Item armorItem) {
		toToolMaterial().setRepairItem(new ItemStack(toolItem));
		toArmorMaterial().customCraftingMaterial = armorItem;
	}

	private GOTMaterial setDamage(float f) {
		damage = f;
		return this;
	}

	private GOTMaterial setEnchantability(int i) {
		enchantability = i;
		return this;
	}

	private GOTMaterial setHarvestLevel(int i) {
		harvestLevel = i;
		return this;
	}

	private GOTMaterial setIsStandart() {
		setUses(350).setDamage(2.5f).setProtection(0.5f).setSpeed(5.5f).setHarvestLevel(6).setEnchantability(8).setCraftingItem(Items.iron_ingot);
		return this;
	}

	private GOTMaterial setIsStrong() {
		setUses(500).setDamage(3.0f).setProtection(0.6f).setSpeed(6.0f).setHarvestLevel(10).setEnchantability(10);
		return this;
	}

	private GOTMaterial setIsWeak() {
		setUses(200).setDamage(2.0f).setProtection(0.4f).setSpeed(5.0f).setHarvestLevel(2).setEnchantability(6);
		return this;
	}

	private GOTMaterial setProtection(float f) {
		protection = new int[protectionBase.length];
		for (int i = 0; i < protection.length; ++i) {
			protection[i] = Math.round(protectionBase[i] * f * maxProtection);
		}
		return this;
	}

	private GOTMaterial setSpeed(float f) {
		speed = f;
		return this;
	}

	private GOTMaterial setUndamageable() {
		undamageable = true;
		return this;
	}

	private GOTMaterial setUses(int i) {
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

	public static Item.ToolMaterial getToolMaterialByName(String name) {
		return Item.ToolMaterial.valueOf(name);
	}

	public static void onInit() {
		HAND.setCraftingItem(GOTRegistry.diamond);
		ANONYMOUS.setCraftingItem(Items.paper);
		VALYRIAN.setCraftingItem(GOTRegistry.valyrianIngot);
		DOTHRAKI.setCraftingItem(Item.getItemFromBlock(GOTRegistry.driedReeds));
		BONE.setCraftingItem(Items.bone);
		BRONZE.setCraftingItem(GOTRegistry.bronzeIngot);
		FUR.setCraftingItem(GOTRegistry.fur);
		GEMSBOK.setCraftingItem(GOTRegistry.gemsbokHide);
		KAFTAN.setCraftingItem(Item.getItemFromBlock(Blocks.wool));
		LHAZAR.setCraftingItem(GOTRegistry.gemsbokHide);
		HORN.setCraftingItem(GOTRegistry.rhinoHorn);
		LHAZAR_LION.setCraftingItem(GOTRegistry.lionFur);
		ROBES.setCraftingItem(Item.getItemFromBlock(Blocks.wool));
		OBSIDIAN.setCraftingItem(GOTRegistry.obsidianShard);
		SOTHORYOS_GOLD.setCraftingItem(Items.gold_ingot);
		FLINT.setCraftingItem(Items.flint);
		ICE.setCraftingItem(GOTRegistry.iceShard);
		SILVER.setCraftingItem(GOTRegistry.silverIngot);
		YITI_FRONTIER.setCraftingItem(GOTRegistry.yitiSteelIngot);
		YITI_SAMURAI.setCraftingItem(GOTRegistry.yitiSteelIngot);
		ALLOY_STEEL.setCraftingItem(GOTRegistry.alloySteelIgnot);
		REACHGUARD.setCraftingItem(GOTRegistry.alloySteelIgnot);
		NORTHGUARD.setCraftingItem(GOTRegistry.alloySteelIgnot);
		KINGSGUARD.setCraftingItem(GOTRegistry.alloySteelIgnot);
		ARRYNGUARD.setCraftingItem(GOTRegistry.alloySteelIgnot);
		WESTERLANDSGUARD.setCraftingItem(GOTRegistry.alloySteelIgnot);
		UNSULLIED.setCraftingItem(GOTRegistry.alloySteelIgnot);
		HELMET.setCraftingItem(GOTRegistry.alloySteelIgnot);
		TARGARYEN.setCraftingItem(GOTRegistry.alloySteelIgnot);
		BLACKSKIN.setCraftingItem(GOTRegistry.alloySteelIgnot);
		RENLY.setCraftingItem(GOTRegistry.alloySteelIgnot);
		REDKING.setCraftingItem(GOTRegistry.alloySteelIgnot);
		BLACKFYRE.setCraftingItem(GOTRegistry.alloySteelIgnot);
		WESTKING.setCraftingItem(GOTRegistry.alloySteelIgnot);
		ROYCE.setCraftingItem(GOTRegistry.bronzeIngot);
	}
}
