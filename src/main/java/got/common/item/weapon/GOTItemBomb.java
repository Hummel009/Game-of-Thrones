package got.common.item.weapon;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.block.other.GOTBlockBomb;
import got.common.dispense.GOTDispenseBomb;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class GOTItemBomb extends ItemBlock {
	public GOTItemBomb(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseBomb());
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int meta = itemstack.getItemDamage();
		int strength = GOTBlockBomb.getBombStrengthLevel(meta);
		if (strength == 1) {
			list.add(StatCollector.translateToLocal("tile.got.bomb.doubleStrength"));
		}
		if (strength == 2) {
			list.add(StatCollector.translateToLocal("tile.got.bomb.tripleStrength"));
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
