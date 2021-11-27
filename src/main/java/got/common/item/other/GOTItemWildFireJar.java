package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.dispense.GOTDispenseWildFireJar;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class GOTItemWildFireJar extends ItemBlock {
	public GOTItemWildFireJar(Block block) {
		super(block);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseWildFireJar());
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		list.add(StatCollector.translateToLocal("tile.got.wildFire.warning"));
	}
}
