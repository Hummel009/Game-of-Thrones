package got.common.database;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
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
	private ItemStack theIcon;

	public GOTCreativeTabs(String label) {
		super(label);
	}

	@Override
	public ItemStack getIconItemStack() {
		return theIcon;
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public Item getTabIconItem() {
		return theIcon.getItem();
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public String getTranslatedTabLabel() {
		return StatCollector.translateToLocal("got.tab." + getTabLabel());
	}

	public static void onInit() {
		GOTCreativeTabs.tabBlock.theIcon = new ItemStack(GOTRegistry.brick1, 1, 1);
		GOTCreativeTabs.tabUtil.theIcon = new ItemStack(GOTRegistry.unsmeltery);
		GOTCreativeTabs.tabDeco.theIcon = new ItemStack(GOTRegistry.chandelier, 1, 3);
		GOTCreativeTabs.tabFood.theIcon = new ItemStack(GOTRegistry.mugVodka);
		GOTCreativeTabs.tabMaterials.theIcon = new ItemStack(GOTRegistry.valyrianIngot);
		GOTCreativeTabs.tabMisc.theIcon = new ItemStack(GOTRegistry.goldRing);
		GOTCreativeTabs.tabTools.theIcon = new ItemStack(GOTRegistry.wildlingAxe);
		GOTCreativeTabs.tabCombat.theIcon = new ItemStack(GOTRegistry.hillmenHelmet);
		GOTCreativeTabs.tabStory.theIcon = new ItemStack(GOTRegistry.gregorCleganeSword);
		GOTCreativeTabs.tabSpawn.theIcon = new ItemStack(GOTRegistry.spawnEgg, 1, 600);
		GOTCreativeTabs.tabBanner.theIcon = new ItemStack(GOTRegistry.bannerTab);
	}
}
