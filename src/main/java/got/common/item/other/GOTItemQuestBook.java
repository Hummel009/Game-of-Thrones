package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.*;
import got.common.database.GOTCreativeTabs;
import got.common.quest.GOTMiniQuestEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemQuestBook extends Item {
	@SideOnly(value = Side.CLIENT)
	public static IIcon questOfferIcon;

	public GOTItemQuestBook() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		int activeQuests = playerData.getActiveMiniQuests().size();
		list.add(StatCollector.translateToLocalFormatted("item.got.mqbook.activeQuests", activeQuests));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.openGui(GOT.getInstance(), 32, world, 0, 0, 0);
		if (!world.isRemote) {
			GOTLevelData.getData(entityplayer).distributeMQEvent(new GOTMiniQuestEvent.OpenRedBook());
		}
		return itemstack;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		questOfferIcon = iconregister.registerIcon("got:questOffer");
	}
}
