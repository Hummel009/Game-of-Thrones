package got.common.item.other;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.dispense.GOTDispenseWildFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTItemWildFireJar extends ItemBlock {
	public GOTItemWildFireJar(Block block) {
		super(block);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseWildFireJar());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		list.add(StatCollector.translateToLocal("tile.got.wild_fire.warning"));
	}
}
