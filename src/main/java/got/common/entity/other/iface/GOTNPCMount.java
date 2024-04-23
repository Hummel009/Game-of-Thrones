package got.common.entity.other.iface;

import net.minecraft.item.ItemStack;

public interface GOTNPCMount {
	boolean getBelongsToNPC();

	void setBelongsToNPC(boolean var1);

	String getMountArmorTexture();

	@SuppressWarnings("unused")
	boolean isMountArmorValid(ItemStack var1);

	boolean isMountSaddled();

	void super_moveEntityWithHeading(float var1, float var2);
}