package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseMysteryWeb;
import got.common.entity.other.GOTEntityMysteryWeb;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemMysteryWeb extends Item {
	public GOTItemMysteryWeb() {
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseMysteryWeb());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
		if (!world.isRemote) {
			world.spawnEntityInWorld(new GOTEntityMysteryWeb(world, entityplayer));
		}
		return itemstack;
	}
}