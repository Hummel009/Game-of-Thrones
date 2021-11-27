package got.common.item.other;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface GOTItemClick {
	void onLeftClick(ItemStack stack, EntityPlayer player);

	void onLeftClickRelease(ItemStack stack, EntityPlayer player);
}
