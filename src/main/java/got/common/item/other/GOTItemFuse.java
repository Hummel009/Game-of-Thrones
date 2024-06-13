package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemFuse extends GOTItemDoubleTorch {
	public GOTItemFuse() {
		super(GOTBlocks.fuse);
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("item.got.fuse"));
	}
}
