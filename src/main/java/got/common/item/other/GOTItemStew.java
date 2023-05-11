package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemStew extends ItemFood {
	public GOTItemStew(int j, float f, boolean flag) {
		super(j, f, flag);
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabFood);
		setContainerItem(Items.bowl);
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		super.onEaten(itemstack, world, entityplayer);
		return new ItemStack(Items.bowl);
	}
}
