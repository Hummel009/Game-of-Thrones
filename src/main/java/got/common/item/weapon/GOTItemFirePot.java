package got.common.item.weapon;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseFirePot;
import got.common.entity.other.inanimate.GOTEntityFirePot;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemFirePot extends Item {
	public GOTItemFirePot() {
		setMaxStackSize(4);
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseFirePot());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			world.spawnEntityInWorld(new GOTEntityFirePot(world, entityplayer));
			world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}

			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useFirePot);
		}
		return itemstack;
	}
}