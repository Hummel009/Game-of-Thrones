package got.common.database;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class GOTMaterial {
	public static ArmorMaterial ANONYMOUS = EnumHelper.addArmorMaterial("GOT_ANONYMOUS", 33, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial ARRYN = EnumHelper.addArmorMaterial("GOT_ARRYN", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial ARRYNGUARD = EnumHelper.addArmorMaterial("GOT_ARRYNGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial ASSHAI = EnumHelper.addArmorMaterial("GOT_ASSHAI", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial BLACKFYRE = EnumHelper.addArmorMaterial("GOT_BLACKFYRE", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial BLACKSKIN = EnumHelper.addArmorMaterial("GOT_BLACKSKIN", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial BONE = EnumHelper.addArmorMaterial("GOT_BONE", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial BRAAVOS = EnumHelper.addArmorMaterial("GOT_BRAAVOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial BRONZE_CHAINMAIL = EnumHelper.addArmorMaterial("GOT_BRONZE_CHAINMAIL", 15, new int[]{2, 4, 3, 1}, 12);
	public static ArmorMaterial BRONZE = EnumHelper.addArmorMaterial("GOT_BRONZE", 15, new int[]{2, 5, 4, 1}, 9);
	public static ArmorMaterial CROWNLANDS = EnumHelper.addArmorMaterial("GOT_CROWNLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial DORNE = EnumHelper.addArmorMaterial("GOT_DORNE", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial DOTHRAKI = EnumHelper.addArmorMaterial("GOT_DOTHRAKI", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial DRAGONSTONE = EnumHelper.addArmorMaterial("GOT_DRAGONSTONE", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial FUR = EnumHelper.addArmorMaterial("GOT_FUR", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial GEMSBOK = EnumHelper.addArmorMaterial("GOT_GEMSBOK", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial GHISCAR = EnumHelper.addArmorMaterial("GOT_GHISCAR", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial GIFT = EnumHelper.addArmorMaterial("GOT_GIFT", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial GOLDENCOMPANY = EnumHelper.addArmorMaterial("GOT_GOLDENCOMPANY", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial HAND = EnumHelper.addArmorMaterial("GOT_HAND", 25, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial HELMET = EnumHelper.addArmorMaterial("GOT_HELMET", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial HILLMEN = EnumHelper.addArmorMaterial("GOT_HILLMEN", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial IBBEN = EnumHelper.addArmorMaterial("GOT_IBBEN", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial ICE = EnumHelper.addArmorMaterial("GOT_ICE", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial IRONBORN = EnumHelper.addArmorMaterial("GOT_IRONBORN", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial JOGOS = EnumHelper.addArmorMaterial("GOT_JOGOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial KAFTAN = EnumHelper.addArmorMaterial("GOT_KAFTAN", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial KINGSGUARD = EnumHelper.addArmorMaterial("GOT_KINGSGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial LHAZAR = EnumHelper.addArmorMaterial("GOT_LHAZAR", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial LHAZAR_LION = EnumHelper.addArmorMaterial("GOT_LHAZAR_LION", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial LORATH = EnumHelper.addArmorMaterial("GOT_LORATH", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial LYS = EnumHelper.addArmorMaterial("GOT_LYS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial MOSSOVY = EnumHelper.addArmorMaterial("GOT_MOSSOVY", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial MYR = EnumHelper.addArmorMaterial("GOT_MYR", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial NORTH = EnumHelper.addArmorMaterial("GOT_NORTH", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial NORTHGUARD = EnumHelper.addArmorMaterial("GOT_NORTHGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial NORVOS = EnumHelper.addArmorMaterial("GOT_NORVOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial PENTOS = EnumHelper.addArmorMaterial("GOT_PENTOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial QARTH = EnumHelper.addArmorMaterial("GOT_QARTH", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial QOHOR = EnumHelper.addArmorMaterial("GOT_QOHOR", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial REACH = EnumHelper.addArmorMaterial("GOT_REACH", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial REACHGUARD = EnumHelper.addArmorMaterial("GOT_REACHGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial REDKING = EnumHelper.addArmorMaterial("GOT_REDKING", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial RENLY = EnumHelper.addArmorMaterial("GOT_RENLY", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial RIVERLANDS = EnumHelper.addArmorMaterial("GOT_RIVERLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial ROBES = EnumHelper.addArmorMaterial("GOT_ROBES", 10, new int[]{1, 3, 2, 1}, 15);
	public static ArmorMaterial ROYCE = EnumHelper.addArmorMaterial("GOT_ROYCE", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial SOTHORYOS = EnumHelper.addArmorMaterial("GOT_SOTHORYOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial SOTHORYOS_GOLD = EnumHelper.addArmorMaterial("GOT_SOTHORYOS_GOLD", 13, new int[]{2, 5, 3, 1}, 25);
	public static ArmorMaterial STORMLANDS = EnumHelper.addArmorMaterial("GOT_STORMLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial SUMMER = EnumHelper.addArmorMaterial("GOT_SUMMER", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial TARGARYEN = EnumHelper.addArmorMaterial("GOT_TARGARYEN", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial TYROSH = EnumHelper.addArmorMaterial("GOT_TYROSH", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial UNSULLIED = EnumHelper.addArmorMaterial("GOT_UNSULLIED", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial VALYRIAN_CHAINMAIL = EnumHelper.addArmorMaterial("GOT_VALYRIAN_CHAINMAIL", 33, new int[]{3, 7, 5, 2}, 13);
	public static ArmorMaterial VALYRIAN = EnumHelper.addArmorMaterial("GOT_VALYRIAN", 33, new int[]{3, 8, 6, 3}, 10);
	public static ArmorMaterial VOLANTIS = EnumHelper.addArmorMaterial("GOT_VOLANTIS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial WESTERLANDS = EnumHelper.addArmorMaterial("GOT_WESTERLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial WESTERLANDSGUARD = EnumHelper.addArmorMaterial("GOT_WESTERLANDSGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial WESTKING = EnumHelper.addArmorMaterial("GOT_WESTKING", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial YITI = EnumHelper.addArmorMaterial("GOT_YITI", 20, new int[]{2, 6, 5, 2}, 9);
	public static ArmorMaterial YITI_FRONTIER = EnumHelper.addArmorMaterial("GOT_YITI_FRONTIER", 25, new int[]{2, 7, 6, 3}, 9);
	public static ArmorMaterial YITI_SAMURAI = EnumHelper.addArmorMaterial("GOT_YITI_SAMURAI", 25, new int[]{2, 7, 6, 3}, 9);

	public static ToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("GOT_BRONZE_TOOL", 2, 250, 5.0F, 1.5F, 10);
	public static ToolMaterial COBALT_TOOL = EnumHelper.addToolMaterial("GOT_COBALT_TOOL", 3, 1500, 8.0F, 3.0F, 10);
	public static ToolMaterial FLINT_TOOL = EnumHelper.addToolMaterial("GOT_FLINT_TOOL", 1, 200, 4.0F, 1.0F, 5);
	public static ToolMaterial HORN_TOOL = EnumHelper.addToolMaterial("GOT_HORN_TOOL", 1, 200, 4.0F, 1.0F, 5);
	public static ToolMaterial ICE_TOOL = EnumHelper.addToolMaterial("GOT_ICE_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static ToolMaterial OBSIDIAN_TOOL = EnumHelper.addToolMaterial("GOT_OBSIDIAN_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static ToolMaterial SILVER_TOOL = EnumHelper.addToolMaterial("GOT_SILVER_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static ToolMaterial VALYRIAN_TOOL = EnumHelper.addToolMaterial("GOT_VALYRIAN_TOOL", 4, 2500, 9.0F, 5.0F, 8);
	public static ToolMaterial ASSHAI_TOOL = EnumHelper.addToolMaterial("GOT_ASSHAI_TOOL", 2, 450, 6.0F, 2.0F, 14);

	public static ItemArmor.ArmorMaterial getArmorMaterialByName(String name) {
		try {
			return ItemArmor.ArmorMaterial.valueOf(name);
		} catch (Exception e) {
			return ItemArmor.ArmorMaterial.IRON;
		}
	}

	public static Item.ToolMaterial getToolMaterialByName(String name) {
		try {
			return Item.ToolMaterial.valueOf(name);
		} catch (Exception e) {
			return Item.ToolMaterial.IRON;
		}
	}

	public static void onInit() {
		ANONYMOUS.customCraftingMaterial = Items.paper;
		ARRYN.customCraftingMaterial = Items.iron_ingot;
		ARRYNGUARD.customCraftingMaterial = Items.iron_ingot;
		ASSHAI.customCraftingMaterial = Items.iron_ingot;
		BLACKFYRE.customCraftingMaterial = Items.iron_ingot;
		BLACKSKIN.customCraftingMaterial = Items.iron_ingot;
		BONE.customCraftingMaterial = Items.bone;
		BRAAVOS.customCraftingMaterial = Items.iron_ingot;
		BRONZE.customCraftingMaterial = GOTRegistry.bronzeIngot;
		BRONZE_CHAINMAIL.customCraftingMaterial = GOTRegistry.bronzeIngot;
		CROWNLANDS.customCraftingMaterial = Items.iron_ingot;
		DORNE.customCraftingMaterial = Items.iron_ingot;
		DOTHRAKI.customCraftingMaterial = Item.getItemFromBlock(GOTRegistry.driedReeds);
		DRAGONSTONE.customCraftingMaterial = Items.iron_ingot;
		FUR.customCraftingMaterial = GOTRegistry.fur;
		GEMSBOK.customCraftingMaterial = GOTRegistry.gemsbokHide;
		GHISCAR.customCraftingMaterial = Items.iron_ingot;
		GIFT.customCraftingMaterial = Items.iron_ingot;
		GOLDENCOMPANY.customCraftingMaterial = Items.iron_ingot;
		HAND.customCraftingMaterial = Items.string;
		HELMET.customCraftingMaterial = Items.iron_ingot;
		HILLMEN.customCraftingMaterial = Items.iron_ingot;
		IBBEN.customCraftingMaterial = Items.iron_ingot;
		ICE.customCraftingMaterial = Items.iron_ingot;
		IRONBORN.customCraftingMaterial = Items.iron_ingot;
		JOGOS.customCraftingMaterial = Items.iron_ingot;
		KAFTAN.customCraftingMaterial = Item.getItemFromBlock(Blocks.wool);
		KINGSGUARD.customCraftingMaterial = Items.iron_ingot;
		LHAZAR.customCraftingMaterial = GOTRegistry.gemsbokHide;
		LHAZAR_LION.customCraftingMaterial = GOTRegistry.lionFur;
		LORATH.customCraftingMaterial = Items.iron_ingot;
		LYS.customCraftingMaterial = Items.iron_ingot;
		MOSSOVY.customCraftingMaterial = Items.iron_ingot;
		MYR.customCraftingMaterial = Items.iron_ingot;
		NORTH.customCraftingMaterial = Items.iron_ingot;
		NORTHGUARD.customCraftingMaterial = Items.iron_ingot;
		NORVOS.customCraftingMaterial = Items.iron_ingot;
		PENTOS.customCraftingMaterial = Items.iron_ingot;
		QARTH.customCraftingMaterial = Items.iron_ingot;
		QOHOR.customCraftingMaterial = Items.iron_ingot;
		REACH.customCraftingMaterial = Items.iron_ingot;
		REACHGUARD.customCraftingMaterial = Items.iron_ingot;
		REDKING.customCraftingMaterial = Items.iron_ingot;
		RENLY.customCraftingMaterial = Items.iron_ingot;
		RIVERLANDS.customCraftingMaterial = Items.iron_ingot;
		ROBES.customCraftingMaterial = Item.getItemFromBlock(Blocks.wool);
		ROYCE.customCraftingMaterial = GOTRegistry.bronzeIngot;
		SOTHORYOS.customCraftingMaterial = Items.iron_ingot;
		SOTHORYOS_GOLD.customCraftingMaterial = Items.gold_ingot;
		STORMLANDS.customCraftingMaterial = Items.iron_ingot;
		SUMMER.customCraftingMaterial = Items.iron_ingot;
		TARGARYEN.customCraftingMaterial = Items.iron_ingot;
		TYROSH.customCraftingMaterial = Items.iron_ingot;
		UNSULLIED.customCraftingMaterial = Items.iron_ingot;
		VALYRIAN_CHAINMAIL.customCraftingMaterial = GOTRegistry.valyrianIngot;
		VALYRIAN.customCraftingMaterial = GOTRegistry.valyrianIngot;
		VOLANTIS.customCraftingMaterial = Items.iron_ingot;
		WESTERLANDS.customCraftingMaterial = Items.iron_ingot;
		WESTERLANDSGUARD.customCraftingMaterial = Items.iron_ingot;
		WESTKING.customCraftingMaterial = Items.iron_ingot;
		YITI.customCraftingMaterial = Items.iron_ingot;
		YITI_FRONTIER.customCraftingMaterial = Items.iron_ingot;
		YITI_SAMURAI.customCraftingMaterial = Items.iron_ingot;

		BRONZE_TOOL.setRepairItem(new ItemStack(GOTRegistry.bronzeIngot));
		COBALT_TOOL.setRepairItem(new ItemStack(GOTRegistry.alloySteelIngot));
		FLINT_TOOL.setRepairItem(new ItemStack(Items.flint));
		HORN_TOOL.setRepairItem(new ItemStack(GOTRegistry.horn));
		ICE_TOOL.setRepairItem(new ItemStack(GOTRegistry.iceShard));
		OBSIDIAN_TOOL.setRepairItem(new ItemStack(GOTRegistry.obsidianShard));
		SILVER_TOOL.setRepairItem(new ItemStack(GOTRegistry.silverIngot));
		VALYRIAN_TOOL.setRepairItem(new ItemStack(GOTRegistry.valyrianIngot));
		ASSHAI_TOOL.setRepairItem(new ItemStack(Items.iron_ingot));
	}
}
