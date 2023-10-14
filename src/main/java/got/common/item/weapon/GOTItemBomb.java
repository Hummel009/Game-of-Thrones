package got.common.item.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.other.GOTBlockBomb;
import got.common.dispense.GOTDispenseBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemBomb extends ItemBlock {
	public GOTItemBomb(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseBomb());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int meta = itemstack.getItemDamage();
		int strength = GOTBlockBomb.getBombStrengthLevel(meta);
		if (strength == 1) {
			list.add(StatCollector.translateToLocal("tile.got.bomb.double_strength"));
		}
		if (strength == 2) {
			list.add(StatCollector.translateToLocal("tile.got.bomb.triple_strength"));
		}
		if (GOTBlockBomb.isFireBomb(meta)) {
			list.add(StatCollector.translateToLocal("tile.got.bomb.fire"));
		}
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}
