package got.common.entity.other;

import net.minecraft.item.ItemStack;

public interface GOTNPCMount {
	boolean getBelongsToNPC();

	String getMountArmorTexture();

	float getStepHeightWhileRiddenByPlayer();

	boolean isMountArmorValid(ItemStack var1);

	boolean isMountSaddled();

	void setBelongsToNPC(boolean var1);

	void super_moveEntityWithHeading(float var1, float var2);

}
