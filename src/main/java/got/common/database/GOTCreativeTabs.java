package got.common.database;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTCreativeTabs extends CreativeTabs {
	public static GOTCreativeTabs tabBlock = new GOTCreativeTabs("blocks");
	public static GOTCreativeTabs tabUtil = new GOTCreativeTabs("util");
	public static GOTCreativeTabs tabDeco = new GOTCreativeTabs("decorations");
	public static GOTCreativeTabs tabFood = new GOTCreativeTabs("food");
	public static GOTCreativeTabs tabMaterials = new GOTCreativeTabs("materials");
	public static GOTCreativeTabs tabMisc = new GOTCreativeTabs("misc");
	public static GOTCreativeTabs tabTools = new GOTCreativeTabs("tools");
	public static GOTCreativeTabs tabCombat = new GOTCreativeTabs("combat");
	public static GOTCreativeTabs tabStory = new GOTCreativeTabs("story");
	public static GOTCreativeTabs tabSpawn = new GOTCreativeTabs("spawning");
	public static GOTCreativeTabs tabBanner = new GOTCreativeTabs("banner");
	public ItemStack theIcon;

	public GOTCreativeTabs(String label) {
		super(label);
	}

	public static void onInit() {
		GOTCreativeTabs.tabBlock.theIcon = new ItemStack(GOTRegistry.brick1, 1, 1);
		GOTCreativeTabs.tabUtil.theIcon = new ItemStack(GOTRegistry.unsmeltery);
		GOTCreativeTabs.tabDeco.theIcon = new ItemStack(GOTRegistry.chandelier, 1, 3);
		GOTCreativeTabs.tabFood.theIcon = new ItemStack(GOTRegistry.mugVodka);
		GOTCreativeTabs.tabMaterials.theIcon = new ItemStack(GOTRegistry.valyrianIngot);
		GOTCreativeTabs.tabMisc.theIcon = new ItemStack(GOTRegistry.coin, 1, 6);
		GOTCreativeTabs.tabTools.theIcon = new ItemStack(GOTRegistry.wildlingAxe);
		GOTCreativeTabs.tabCombat.theIcon = new ItemStack(GOTRegistry.ironbornHelmet);
		GOTCreativeTabs.tabStory.theIcon = new ItemStack(GOTRegistry.bane);
		GOTCreativeTabs.tabSpawn.theIcon = new ItemStack(GOTRegistry.spawnEgg, 1, 248);
		GOTCreativeTabs.tabBanner.theIcon = new ItemStack(GOTRegistry.bannerTab);
	}

	@Override
	public ItemStack getIconItemStack() {
		return theIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return theIcon.getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return StatCollector.translateToLocal("got.tab." + getTabLabel());
	}
}
