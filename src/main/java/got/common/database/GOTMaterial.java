package got.common.database;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class GOTMaterial {
	public static final ItemArmor.ArmorMaterial ANONYMOUS = EnumHelper.addArmorMaterial("GOT_ANONYMOUS", 33, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial ARRYN = EnumHelper.addArmorMaterial("GOT_ARRYN", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial ARRYNGUARD = EnumHelper.addArmorMaterial("GOT_ARRYNGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial ASSHAI = EnumHelper.addArmorMaterial("GOT_ASSHAI", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial BLACKFYRE = EnumHelper.addArmorMaterial("GOT_BLACKFYRE", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial BLACKSKIN = EnumHelper.addArmorMaterial("GOT_BLACKSKIN", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial BONE = EnumHelper.addArmorMaterial("GOT_BONE", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial BRAAVOS = EnumHelper.addArmorMaterial("GOT_BRAAVOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial BRONZE_CHAINMAIL = EnumHelper.addArmorMaterial("GOT_BRONZE_CHAINMAIL", 15, new int[]{2, 4, 3, 1}, 12);
	public static final ItemArmor.ArmorMaterial BRONZE = EnumHelper.addArmorMaterial("GOT_BRONZE", 15, new int[]{2, 5, 4, 1}, 9);
	public static final ItemArmor.ArmorMaterial CROWNLANDS = EnumHelper.addArmorMaterial("GOT_CROWNLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial DORNE = EnumHelper.addArmorMaterial("GOT_DORNE", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial DOTHRAKI = EnumHelper.addArmorMaterial("GOT_DOTHRAKI", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial DRAGONSTONE = EnumHelper.addArmorMaterial("GOT_DRAGONSTONE", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial FUR = EnumHelper.addArmorMaterial("GOT_FUR", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial GEMSBOK = EnumHelper.addArmorMaterial("GOT_GEMSBOK", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial GHISCAR = EnumHelper.addArmorMaterial("GOT_GHISCAR", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial GIFT = EnumHelper.addArmorMaterial("GOT_GIFT", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial GOLDENCOMPANY = EnumHelper.addArmorMaterial("GOT_GOLDENCOMPANY", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial HAND = EnumHelper.addArmorMaterial("GOT_HAND", 25, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial HELMET = EnumHelper.addArmorMaterial("GOT_HELMET", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial HILLMEN = EnumHelper.addArmorMaterial("GOT_HILLMEN", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial IBBEN = EnumHelper.addArmorMaterial("GOT_IBBEN", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial ICE = EnumHelper.addArmorMaterial("GOT_ICE", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial IRONBORN = EnumHelper.addArmorMaterial("GOT_IRONBORN", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial JOGOS = EnumHelper.addArmorMaterial("GOT_JOGOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial KAFTAN = EnumHelper.addArmorMaterial("GOT_KAFTAN", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial KINGSGUARD = EnumHelper.addArmorMaterial("GOT_KINGSGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial LHAZAR = EnumHelper.addArmorMaterial("GOT_LHAZAR", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial LORATH = EnumHelper.addArmorMaterial("GOT_LORATH", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial LYS = EnumHelper.addArmorMaterial("GOT_LYS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial MOSSOVY = EnumHelper.addArmorMaterial("GOT_MOSSOVY", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial MYR = EnumHelper.addArmorMaterial("GOT_MYR", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial NORTH = EnumHelper.addArmorMaterial("GOT_NORTH", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial NORTHGUARD = EnumHelper.addArmorMaterial("GOT_NORTHGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial NORVOS = EnumHelper.addArmorMaterial("GOT_NORVOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial PENTOS = EnumHelper.addArmorMaterial("GOT_PENTOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial QARTH = EnumHelper.addArmorMaterial("GOT_QARTH", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial QOHOR = EnumHelper.addArmorMaterial("GOT_QOHOR", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial REACH = EnumHelper.addArmorMaterial("GOT_REACH", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial REACHGUARD = EnumHelper.addArmorMaterial("GOT_REACHGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial REDKING = EnumHelper.addArmorMaterial("GOT_REDKING", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial RENLY = EnumHelper.addArmorMaterial("GOT_RENLY", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial RIVERLANDS = EnumHelper.addArmorMaterial("GOT_RIVERLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial ROBES = EnumHelper.addArmorMaterial("GOT_ROBES", 10, new int[]{1, 3, 2, 1}, 15);
	public static final ItemArmor.ArmorMaterial ROYCE = EnumHelper.addArmorMaterial("GOT_ROYCE", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial SOTHORYOS = EnumHelper.addArmorMaterial("GOT_SOTHORYOS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial SOTHORYOS_GOLD = EnumHelper.addArmorMaterial("GOT_SOTHORYOS_GOLD", 13, new int[]{2, 5, 3, 1}, 25);
	public static final ItemArmor.ArmorMaterial STORMLANDS = EnumHelper.addArmorMaterial("GOT_STORMLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial SUMMER = EnumHelper.addArmorMaterial("GOT_SUMMER", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial TARGARYEN = EnumHelper.addArmorMaterial("GOT_TARGARYEN", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial TYROSH = EnumHelper.addArmorMaterial("GOT_TYROSH", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial UNSULLIED = EnumHelper.addArmorMaterial("GOT_UNSULLIED", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial VALYRIAN_CHAINMAIL = EnumHelper.addArmorMaterial("GOT_VALYRIAN_CHAINMAIL", 33, new int[]{3, 7, 5, 2}, 13);
	public static final ItemArmor.ArmorMaterial VALYRIAN = EnumHelper.addArmorMaterial("GOT_VALYRIAN", 33, new int[]{3, 8, 6, 3}, 10);
	public static final ItemArmor.ArmorMaterial VOLANTIS = EnumHelper.addArmorMaterial("GOT_VOLANTIS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial WESTERLANDS = EnumHelper.addArmorMaterial("GOT_WESTERLANDS", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial WESTERLANDSGUARD = EnumHelper.addArmorMaterial("GOT_WESTERLANDSGUARD", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial WESTKING = EnumHelper.addArmorMaterial("GOT_WESTKING", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial YITI = EnumHelper.addArmorMaterial("GOT_YITI", 20, new int[]{2, 6, 5, 2}, 9);
	public static final ItemArmor.ArmorMaterial YITI_FRONTIER = EnumHelper.addArmorMaterial("GOT_YITI_FRONTIER", 25, new int[]{2, 7, 6, 3}, 9);
	public static final ItemArmor.ArmorMaterial YITI_SAMURAI = EnumHelper.addArmorMaterial("GOT_YITI_SAMURAI", 25, new int[]{2, 7, 6, 3}, 9);

	public static final Item.ToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("GOT_BRONZE_TOOL", 2, 250, 5.0F, 1.5F, 10);
	public static final Item.ToolMaterial COBALT_TOOL = EnumHelper.addToolMaterial("GOT_COBALT_TOOL", 3, 1500, 8.0F, 3.0F, 10);
	public static final Item.ToolMaterial ICE_TOOL = EnumHelper.addToolMaterial("GOT_ICE_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static final Item.ToolMaterial OBSIDIAN_TOOL = EnumHelper.addToolMaterial("GOT_OBSIDIAN_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static final Item.ToolMaterial SILVER_TOOL = EnumHelper.addToolMaterial("GOT_SILVER_TOOL", 2, 450, 6.0F, 2.0F, 14);
	public static final Item.ToolMaterial VALYRIAN_TOOL = EnumHelper.addToolMaterial("GOT_VALYRIAN_TOOL", 4, 2500, 9.0F, 5.0F, 8);
	public static final Item.ToolMaterial ASSHAI_TOOL = EnumHelper.addToolMaterial("GOT_ASSHAI_TOOL", 2, 450, 6.0F, 2.0F, 14);

	public static final Item.ToolMaterial SAPPHIRE = EnumHelper.addToolMaterial("GOT_SAPPHIRE", 2, 450, 6.0F, 2.0F, 14);
	public static final Item.ToolMaterial RUBY = EnumHelper.addToolMaterial("GOT_RUBY", 2, 450, 6.0F, 2.0F, 14);

	private GOTMaterial() {
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
		BRONZE.customCraftingMaterial = GOTItems.bronzeIngot;
		BRONZE_CHAINMAIL.customCraftingMaterial = GOTItems.bronzeIngot;
		CROWNLANDS.customCraftingMaterial = Items.iron_ingot;
		DORNE.customCraftingMaterial = Items.iron_ingot;
		DOTHRAKI.customCraftingMaterial = Item.getItemFromBlock(GOTBlocks.driedReeds);
		DRAGONSTONE.customCraftingMaterial = Items.iron_ingot;
		FUR.customCraftingMaterial = GOTItems.fur;
		GEMSBOK.customCraftingMaterial = GOTItems.gemsbokHide;
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
		LHAZAR.customCraftingMaterial = GOTItems.gemsbokHide;
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
		ROYCE.customCraftingMaterial = GOTItems.bronzeIngot;
		SOTHORYOS.customCraftingMaterial = Items.iron_ingot;
		SOTHORYOS_GOLD.customCraftingMaterial = Items.gold_ingot;
		STORMLANDS.customCraftingMaterial = Items.iron_ingot;
		SUMMER.customCraftingMaterial = Items.iron_ingot;
		TARGARYEN.customCraftingMaterial = Items.iron_ingot;
		TYROSH.customCraftingMaterial = Items.iron_ingot;
		UNSULLIED.customCraftingMaterial = Items.iron_ingot;
		VALYRIAN_CHAINMAIL.customCraftingMaterial = GOTItems.valyrianIngot;
		VALYRIAN.customCraftingMaterial = GOTItems.valyrianIngot;
		VOLANTIS.customCraftingMaterial = Items.iron_ingot;
		WESTERLANDS.customCraftingMaterial = Items.iron_ingot;
		WESTERLANDSGUARD.customCraftingMaterial = Items.iron_ingot;
		WESTKING.customCraftingMaterial = Items.iron_ingot;
		YITI.customCraftingMaterial = Items.iron_ingot;
		YITI_FRONTIER.customCraftingMaterial = Items.iron_ingot;
		YITI_SAMURAI.customCraftingMaterial = Items.iron_ingot;

		BRONZE_TOOL.setRepairItem(new ItemStack(GOTItems.bronzeIngot));
		COBALT_TOOL.setRepairItem(new ItemStack(GOTItems.alloySteelIngot));
		ICE_TOOL.setRepairItem(new ItemStack(GOTItems.iceShard));
		OBSIDIAN_TOOL.setRepairItem(new ItemStack(GOTItems.obsidianShard));
		SILVER_TOOL.setRepairItem(new ItemStack(GOTItems.silverIngot));
		VALYRIAN_TOOL.setRepairItem(new ItemStack(GOTItems.valyrianIngot));
		ASSHAI_TOOL.setRepairItem(new ItemStack(Items.iron_ingot));

		SAPPHIRE.setRepairItem(new ItemStack(GOTItems.sapphire));
		RUBY.setRepairItem(new ItemStack(GOTItems.ruby));
	}
}