package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.inanimate.GOTEntityPlowcart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemPlowcart extends Item {
	public GOTItemPlowcart() {
		setCreativeTab(GOTCreativeTabs.TAB_UTIL);
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

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		list.add(StatCollector.translateToLocal("item.got.plowcart1"));
		list.add(StatCollector.translateToLocal("item.got.plowcart2"));
	}
}