package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.entity.other.inanimate.GOTEntityPlowcart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class GOTItemPlowcart extends Item {
	public GOTItemPlowcart() {
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		Vec3 vec3d = Vec3.createVectorHelper(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
		MovingObjectPosition result = worldIn.rayTraceBlocks(vec3d, Vec3.createVectorHelper(playerIn.getLookVec().xCoord * 5.0 + vec3d.xCoord, playerIn.getLookVec().yCoord * 5.0 + vec3d.yCoord, playerIn.getLookVec().zCoord * 5.0 + vec3d.zCoord), false);
		if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !worldIn.isRemote) {
			GOTEntityPlowcart cart = new GOTEntityPlowcart(worldIn, result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord);
			cart.rotationYaw = (playerIn.rotationYaw + 180.0f) % 360.0f;
			worldIn.spawnEntityInWorld(cart);
			if (!playerIn.capabilities.isCreativeMode) {
				--itemStackIn.stackSize;
			}
		}
		return itemStackIn;
	}
}