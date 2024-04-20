package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseConker;
import got.common.entity.other.GOTEntityConker;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemConker extends Item {
	public GOTItemConker() {
		setMaxStackSize(16);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseConker());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new GOTEntityConker(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}
}