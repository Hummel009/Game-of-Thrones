package got.common.item.other;

import java.util.List;

import got.GOT;
import got.common.GOTCommonProxy;
import got.common.entity.other.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemMotorGrapplingHook extends GOTItemGrapplingHook {
	public GOTItemMotorGrapplingHook() {
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Pulls player towards hook");
		list.add("");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " - Throw grappling hook");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " again - Release");
		list.add("Double-" + GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindUseItem) + " - Release and throw again");
		list.add(GOT.proxy.getkeyname(GOTCommonProxy.keys.keyBindJump) + " - Release and jump");
	}

	@Override
	public GOTEntityGrapplingArrow createarrow(ItemStack satack, World worldIn, EntityLivingBase playerIn, boolean righthand) {
		return new GOTEntityMotorGrapplingArrow(worldIn, playerIn, righthand);
	}
}
