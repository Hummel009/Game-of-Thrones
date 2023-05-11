package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemMatch extends Item {
	public GOTItemMatch() {
		setFull3D();
		setCreativeTab(GOTCreativeTabs.tabTools);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (Items.flint_and_steel.onItemUse(new ItemStack(Items.flint_and_steel), entityplayer, world, i, j, k, side, f, f1, f2)) {
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
