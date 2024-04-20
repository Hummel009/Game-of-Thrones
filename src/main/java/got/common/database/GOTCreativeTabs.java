package got.common.database;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTCreativeTabs extends CreativeTabs {
	public static final GOTCreativeTabs TAB_BLOCK = new GOTCreativeTabs("blocks");
	public static final GOTCreativeTabs TAB_UTIL = new GOTCreativeTabs("util");
	public static final GOTCreativeTabs TAB_DECO = new GOTCreativeTabs("decorations");
	public static final GOTCreativeTabs TAB_FOOD = new GOTCreativeTabs("food");
	public static final GOTCreativeTabs TAB_MATERIALS = new GOTCreativeTabs("materials");
	public static final GOTCreativeTabs TAB_MISC = new GOTCreativeTabs("misc");
	public static final GOTCreativeTabs TAB_TOOLS = new GOTCreativeTabs("tools");
	public static final GOTCreativeTabs TAB_COMBAT = new GOTCreativeTabs("combat");
	public static final GOTCreativeTabs TAB_STORY = new GOTCreativeTabs("story");
	public static final GOTCreativeTabs TAB_SPAWN = new GOTCreativeTabs("spawning");
	public static final GOTCreativeTabs TAB_BANNER = new GOTCreativeTabs("banner");

	private ItemStack theIcon;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTCreativeTabs(String label) {
		super(label);
	}

	public static void onInit() {
		TAB_BLOCK.theIcon = new ItemStack(GOTBlocks.brick1, 1, 1);
		TAB_UTIL.theIcon = new ItemStack(GOTBlocks.unsmeltery);
		TAB_DECO.theIcon = new ItemStack(GOTBlocks.chandelier, 1, 3);
		TAB_FOOD.theIcon = new ItemStack(GOTItems.mugVodka);
		TAB_MATERIALS.theIcon = new ItemStack(GOTItems.valyrianIngot);
		TAB_MISC.theIcon = new ItemStack(GOTItems.coin, 1, 6);
		TAB_TOOLS.theIcon = new ItemStack(GOTItems.wildlingAxe);
		TAB_COMBAT.theIcon = new ItemStack(GOTItems.ironbornHelmet);
		TAB_STORY.theIcon = new ItemStack(GOTItems.bane);
		TAB_SPAWN.theIcon = new ItemStack(GOTItems.spawnEgg, 1, 248);
		TAB_BANNER.theIcon = new ItemStack(GOTItems.bannerTab);
	}

	@Override
	public ItemStack getIconItemStack() {
		return theIcon;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public void setIconItemStack(ItemStack theIcon) {
		this.theIcon = theIcon;
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