package got.common.database;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.*;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.other.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class GOTChestContents {
	public static ArrayList<WeightedRandomChestContent> NORD_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> SUD_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> NOMAD_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> SOTHORYOS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> YI_TI_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> MOSSOVY_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> GIFT_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> TREASURE_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> ASSHAI_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> BEYOND_WALL_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> IBBEN_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> HILLMEN_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> SUMMER_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> LHAZAR_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> ARRYN_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> BRAAVOS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> CROWNLANDS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> DORNE_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> DRAGONSTONE_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> GHISCAR_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> GOLDEN_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> IRONBORN_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> LORATH_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> LYS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> MYR_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> NORTH_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> NORVOS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> PENTOS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> QARTH_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> QOHOR_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> REACH_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> RIVERLANDS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> STORMLANDS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> TYROSH_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> VOLANTIS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> WESTERLANDS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> JOGOS_L = new ArrayList<>();
	public static ArrayList<WeightedRandomChestContent> DOTHRAKI_L = new ArrayList<>();

	static {
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 1, 4, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 1, 6, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lemon), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lemonCakeItem), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.orange), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lime), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.olive), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.almond), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipan), 1, 3, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipanChocolate), 1, 3, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCactusLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugOrangeJuice), 1, 1, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLemonade), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLemonLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLimeLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.obsidianShard), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 6, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.reeds), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.driedReeds), 2, 6, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mango), 1, 3, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.banana), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.melonSoup), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bananaBread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornBread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.corn), 1, 4, 100));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornCooked), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornStalk), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 5, 100));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMelonLiqueur), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugBananaBeer), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCornLiquor), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugChocolate), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCocoa), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMangoJuice), 1, 1, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 3), 1, 8, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosDagger), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosDaggerPoisoned), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosAxe), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.sarbacane), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.sarbacaneTrap), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dartPoisoned), 1, 4, 25));

		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSteelIngot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.olive), 1, 5, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.raisins), 1, 4, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pomegranate), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSword), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBattleaxe), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSpear), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiHelmet), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiChestplate), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiLeggings), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBoots), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugSourMilk), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.milk_bucket), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPomegranateWine), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPomegranateJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugRedWine), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugWhiteWine), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugRedGrapeJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugWhiteGrapeJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBow), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 50));

		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyChestplate), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyLeggings), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyBoots), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovySword), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leekSoup), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mushroomPie), 1, 2, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.mushroom_stew), 1, 1, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHelmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeChestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeLeggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBoots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAppleJuice), 1, 1, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftHelmet), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftChestplate), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftLeggings), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftBoots), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 0), 1, 20, 200));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 1), 1, 8, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 2), 1, 2, 5));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 5, 100));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.gold_ingot), 1, 4, 75));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverNugget), 1, 5, 100));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverIngot), 1, 4, 75));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 25));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSword), 1, 1, 25));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHelmet), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeChestplate), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeLeggings), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBoots), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 25));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletGold), 1, 3, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletSilver), 1, 3, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 20));

		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 3, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 8, 16, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.fur), 1, 4, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.dragon_egg), 1, 1, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.skullCup), 1, 3, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiDagger), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiDaggerPoisoned), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiSword), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiBattleaxe), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiSpear), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiHammer), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiStaff), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bottlePoison), 1, 1, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.saddle), 1, 1, 50));

		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 75));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerRaw), 1, 3, 100));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 25));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironBattleaxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDaggerPoisoned), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironPike), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironSpear), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSword), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSpear), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 50));

		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.horn), 1, 2, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.flintDagger), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenSword), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenHarpoon), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenChestplate), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenLeggings), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenBoots), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadSword), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadSpear), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadBattleaxe), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadBow), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 200));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCactusLiqueur), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.fur), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 2, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerRaw), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.stoneSpear), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironSpear), 1, 1, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironBattleaxe), 1, 1, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenHelmet), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenChestplate), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenLeggings), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenBoots), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 75));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 5, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.skullCup), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75));

		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerSword), 1, 1, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerDagger), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerDaggerPoisoned), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerPike), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerHelmet), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerChestplate), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerLeggings), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerBoots), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 200));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarSword), 1, 1, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarDagger), 1, 1, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarDaggerPoisoned), 1, 1, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarClub), 1, 1, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarHelmet), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarChestplate), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarLeggings), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarBoots), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarSpear), 1, 1, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 2, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lionCooked), 1, 2, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.zebraCooked), 1, 2, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.saltedFlesh), 1, 3, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 200));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		ARRYN_L.addAll(NORD_L);
		BRAAVOS_L.addAll(NORD_L);
		CROWNLANDS_L.addAll(NORD_L);
		DRAGONSTONE_L.addAll(NORD_L);
		IRONBORN_L.addAll(NORD_L);
		LORATH_L.addAll(NORD_L);
		NORTH_L.addAll(NORD_L);
		NORVOS_L.addAll(NORD_L);
		QOHOR_L.addAll(NORD_L);
		REACH_L.addAll(NORD_L);
		RIVERLANDS_L.addAll(NORD_L);
		STORMLANDS_L.addAll(NORD_L);
		WESTERLANDS_L.addAll(NORD_L);

		DORNE_L.addAll(SUD_L);
		GHISCAR_L.addAll(SUD_L);
		GOLDEN_L.addAll(SUD_L);
		LYS_L.addAll(SUD_L);
		MYR_L.addAll(SUD_L);
		PENTOS_L.addAll(SUD_L);
		QARTH_L.addAll(SUD_L);
		TYROSH_L.addAll(SUD_L);
		VOLANTIS_L.addAll(SUD_L);

		JOGOS_L.addAll(NOMAD_L);
		DOTHRAKI_L.addAll(NOMAD_L);

		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiHelmet), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiChestplate), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiLeggings), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiBoots), 1, 1, 10));

		JOGOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosHelmet), 1, 1, 25));
		JOGOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosChestplate), 1, 1, 25));
		JOGOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosLeggings), 1, 1, 25));
		JOGOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosBoots), 1, 1, 25));

		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynHelmet), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynChestplate), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynLeggings), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynBoots), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsHelmet), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsChestplate), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsLeggings), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsBoots), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneHelmet), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneChestplate), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneLeggings), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneBoots), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneHelmet), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneChestplate), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneLeggings), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneBoots), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornHelmet), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornChestplate), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornLeggings), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornBoots), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.northHelmet), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.northChestplate), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.northLeggings), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.northBoots), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachHelmet), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachChestplate), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachLeggings), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachBoots), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsHelmet), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsChestplate), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsLeggings), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsBoots), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsHelmet), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsChestplate), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsLeggings), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsBoots), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsHelmet), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsChestplate), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsLeggings), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsBoots), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10));

		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosHelmet), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosChestplate), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosLeggings), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosBoots), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarHelmet), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarChestplate), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarLeggings), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarBoots), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathHelmet), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathChestplate), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathLeggings), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathBoots), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysHelmet), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysChestplate), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysLeggings), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysBoots), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrHelmet), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrChestplate), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrLeggings), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrBoots), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosHelmet), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosChestplate), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosLeggings), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosBoots), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosHelmet), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosChestplate), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosLeggings), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosBoots), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthHelmet), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthChestplate), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthLeggings), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthBoots), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorHelmet), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorChestplate), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorLeggings), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorBoots), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshHelmet), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshChestplate), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshLeggings), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshBoots), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisHelmet), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisChestplate), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisLeggings), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisBoots), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));

		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldHelmet), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldChestplate), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldLeggings), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldBoots), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50));
	}

	public static GOTChestContents ARRYN = new GOTChestContents(4, 6, ARRYN_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents ASSHAI = new GOTChestContents(4, 6, ASSHAI_L).enablePouches().setLore(20, GOTLore.LoreCategory.ASSHAI);
	public static GOTChestContents BEYOND_WALL = new GOTChestContents(4, 6, BEYOND_WALL_L).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
	public static GOTChestContents BRAAVOS = new GOTChestContents(4, 6, BRAAVOS_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents CROWNLANDS = new GOTChestContents(4, 6, CROWNLANDS_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents DORNE = new GOTChestContents(4, 6, DORNE_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents DOTHRAKI = new GOTChestContents(4, 6, DOTHRAKI_L).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);
	public static GOTChestContents DRAGONSTONE = new GOTChestContents(4, 6, DRAGONSTONE_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents GHISCAR = new GOTChestContents(4, 6, GHISCAR_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents GIFT = new GOTChestContents(4, 6, GIFT_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents GOLDEN = new GOTChestContents(4, 6, GOLDEN_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents HILLMEN = new GOTChestContents(4, 6, HILLMEN_L).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
	public static GOTChestContents IBBEN = new GOTChestContents(4, 6, IBBEN_L).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
	public static GOTChestContents IRONBORN = new GOTChestContents(4, 6, IRONBORN_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents JOGOS = new GOTChestContents(4, 6, JOGOS_L).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);
	public static GOTChestContents LHAZAR = new GOTChestContents(4, 6, LHAZAR_L).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);
	public static GOTChestContents LORATH = new GOTChestContents(4, 6, LORATH_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents LYS = new GOTChestContents(4, 6, LYS_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents MOSSOVY = new GOTChestContents(4, 6, MOSSOVY_L).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK).setLore(20, GOTLore.LoreCategory.MOSSOVY);
	public static GOTChestContents MYR = new GOTChestContents(4, 6, MYR_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents NORTH = new GOTChestContents(4, 6, NORTH_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents NORVOS = new GOTChestContents(4, 6, NORVOS_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents PENTOS = new GOTChestContents(4, 6, PENTOS_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents QARTH = new GOTChestContents(4, 6, QARTH_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents QOHOR = new GOTChestContents(4, 6, QOHOR_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents REACH = new GOTChestContents(4, 6, REACH_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents RIVERLANDS = new GOTChestContents(4, 6, RIVERLANDS_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents SOTHORYOS = new GOTChestContents(4, 6, SOTHORYOS_L).enablePouches().setDrinkVessels(GOTFoods.SOTHORYOS_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);
	public static GOTChestContents STORMLANDS = new GOTChestContents(4, 6, STORMLANDS_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents SUMMER = new GOTChestContents(4, 6, SUMMER_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);
	public static GOTChestContents TREASURE = new GOTChestContents(4, 6, TREASURE_L).enablePouches();
	public static GOTChestContents TYROSH = new GOTChestContents(4, 6, TYROSH_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents VOLANTIS = new GOTChestContents(4, 6, VOLANTIS_L).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
	public static GOTChestContents WESTERLANDS = new GOTChestContents(4, 6, WESTERLANDS_L).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
	public static GOTChestContents YI_TI = new GOTChestContents(4, 6, YI_TI_L).enablePouches().setDrinkVessels(GOTFoods.RICH_DRINK).setLore(20, GOTLore.LoreCategory.YITI);

	public WeightedRandomChestContent[] items;
	public int minItems;
	public int maxItems;
	public boolean pouches = false;
	public GOTItemMug.Vessel[] vesselTypes;
	public List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	public int loreChance = 10;

	public GOTChestContents(int i, int j, ArrayList<WeightedRandomChestContent> list) {
		WeightedRandomChestContent[] w = new WeightedRandomChestContent[list.size()];
		w = list.toArray(w);
		minItems = i;
		maxItems = j;
		items = w;
	}

	public GOTChestContents enablePouches() {
		pouches = true;
		return this;
	}

	public ItemStack getOneItem(Random random, boolean isNPCDrop) {
		InventoryBasic drops = new InventoryBasic("oneItem", false, 1);
		GOTChestContents.fillInventory(drops, random, this, 1, isNPCDrop);
		ItemStack item = drops.getStackInSlot(0);
		item.stackSize = 1;
		return item;
	}

	public GOTChestContents setDrinkVessels(GOTFoods foods) {
		return this.setDrinkVessels(foods.getDrinkVessels());
	}

	public GOTChestContents setDrinkVessels(GOTItemMug.Vessel... v) {
		vesselTypes = v;
		return this;
	}

	public GOTChestContents setLore(int chance, GOTLore.LoreCategory... categories) {
		loreCategories = Arrays.asList(categories);
		loreChance = chance;
		return this;
	}

	public static void fillChest(World world, Random random, int i, int j, int k, GOTChestContents itemPool) {
		GOTChestContents.fillChest(world, random, i, j, k, itemPool, -1);
	}

	public static void fillChest(World world, Random random, int i, int j, int k, GOTChestContents itemPool, int amount) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity == null || !(tileentity instanceof IInventory)) {
			if (j >= 0 && j < 256) {
				FMLLog.warning("Warning: GOTChestContents attempted to fill a chest at " + i + ", " + j + ", " + k + " which does not exist");
			}
			return;
		}
		GOTChestContents.fillInventory((IInventory) tileentity, random, itemPool, amount);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount) {
		GOTChestContents.fillInventory(inventory, random, itemPool, amount, false);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount, boolean isNPCDrop) {
		if (amount == -1) {
			amount = GOTChestContents.getRandomItemAmount(itemPool, random);
		} else if (amount <= 0) {
			throw new IllegalArgumentException("GOTChestContents tried to fill a chest with " + amount + " items");
		}
		for (int i = 0; i < amount; ++i) {
			WeightedRandomChestContent wrcc = (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, itemPool.items);
			for (ItemStack itemstack : ChestGenHooks.generateStacks(random, wrcc.theItemId, wrcc.theMinimumChanceToGenerateItem, wrcc.theMaximumChanceToGenerateItem)) {
				Item item;
				if (!isNPCDrop && itemPool.pouches) {
					if (random.nextInt(50) == 0) {
						itemstack = new ItemStack(GOTRegistry.pouch, 1, GOTItemPouch.getRandomPouchSize(random));
					} else if (random.nextInt(50) == 0) {
						itemstack = GOTItemModifierTemplate.getRandomCommonTemplate(random);
					}
				}
				if (!itemPool.loreCategories.isEmpty()) {
					GOTLore lore;
					int loreChance = itemPool.loreChance;
					int minDropLoreChance = 8;
					if (isNPCDrop && loreChance > minDropLoreChance) {
						loreChance = (int) (loreChance * 0.75f);
						loreChance = Math.max(loreChance, minDropLoreChance);
					}
					if (random.nextInt(loreChance = Math.max(loreChance, 1)) == 0 && (lore = GOTLore.getMultiRandomLore(itemPool.loreCategories, random, false)) != null) {
						itemstack = lore.createLoreBook(random);
					}
				}
				if (itemstack.isItemStackDamageable() && !itemstack.getHasSubtypes()) {
					itemstack.setItemDamage(MathHelper.floor_double(itemstack.getMaxDamage() * MathHelper.randomFloatClamp(random, 0.0f, 0.75f)));
				}
				if (itemstack.stackSize > itemstack.getMaxStackSize()) {
					itemstack.stackSize = itemstack.getMaxStackSize();
				}
				if (GOTConfig.enchantingGOT) {
					boolean skilful = !isNPCDrop && random.nextInt(5) == 0;
					GOTEnchantmentHelper.applyRandomEnchantments(itemstack, random, skilful, false);
				}
				if ((item = itemstack.getItem()) instanceof GOTItemMug) {
					GOTItemMug.Vessel[] vessels;
					if (((GOTItemMug) item).isBrewable) {
						GOTItemMug.setStrengthMeta(itemstack, 1 + random.nextInt(3));
					}
					if (GOTItemMug.isItemFullDrink(itemstack) && (vessels = itemPool.vesselTypes) != null) {
						GOTItemMug.Vessel v = vessels[random.nextInt(vessels.length)];
						GOTItemMug.setVessel(itemstack, v, true);
					}
				}
				inventory.setInventorySlotContents(random.nextInt(inventory.getSizeInventory()), itemstack);
			}
		}
	}

	public static int getRandomItemAmount(GOTChestContents itemPool, Random random) {
		return MathHelper.getRandomIntegerInRange(random, itemPool.minItems, itemPool.maxItems);
	}
}
