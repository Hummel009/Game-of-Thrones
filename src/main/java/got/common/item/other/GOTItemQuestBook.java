package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTGuiId;
import got.common.quest.GOTMiniQuestEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemQuestBook extends Item {
	@SideOnly(Side.CLIENT)
	public static IIcon questOfferIcon;

	public GOTItemQuestBook() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		int activeQuests = playerData.getActiveMiniQuests().size();
		list.add(StatCollector.translateToLocalFormatted("item.got.mqbook.activeQuests", activeQuests));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.openGui(GOT.instance, GOTGuiId.QUEST_BOOK.ordinal(), world, 0, 0, 0);
		if (!world.isRemote) {
			GOTLevelData.getData(entityplayer).distributeMQEvent(new GOTMiniQuestEvent.OpenRedBook());
		}
		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		questOfferIcon = iconregister.registerIcon("got:quest_offer");
	}
}
