package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseTermite;
import got.common.entity.other.GOTEntityThrownTermite;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTItemTermite extends Item {
	public GOTItemTermite() {
		setMaxStackSize(16);
		setCreativeTab(GOTCreativeTabs.tabMisc);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseTermite());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new GOTEntityThrownTermite(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}
}
