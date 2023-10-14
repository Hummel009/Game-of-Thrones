package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.entity.other.GOTEntityNPCRespawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemNPCRespawner extends Item {
	public GOTItemNPCRespawner() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode && !world.isRemote) {
			Vec3 eyePos = Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
			Vec3 look = entityplayer.getLook(1.0f);
			double range = ((EntityPlayerMP) entityplayer).theItemInWorldManager.getBlockReachDistance();
			double d = eyePos.xCoord + look.xCoord * range;
			double d1 = eyePos.yCoord + look.yCoord * range;
			double d2 = eyePos.zCoord + look.zCoord * range;
			int i = MathHelper.floor_double(d);
			int j = MathHelper.floor_double(d1);
			int k = MathHelper.floor_double(d2);
			placeSpawnerAt(world, i, j, k);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (entityplayer.capabilities.isCreativeMode) {
			if (!world.isRemote) {
				placeSpawnerAt(world, i + Facing.offsetsXForSide[side], j + Facing.offsetsYForSide[side], k + Facing.offsetsZForSide[side]);
			}
			return true;
		}
		return false;
	}

	public void placeSpawnerAt(World world, int i, int j, int k) {
		GOTEntityNPCRespawner spawner = new GOTEntityNPCRespawner(world);
		double f = 0.1;
		double f1 = 1.0 - f;
		List<GOTEntityNPCRespawner> entities = world.getEntitiesWithinAABB(GOTEntityNPCRespawner.class, AxisAlignedBB.getBoundingBox(i + f, j + f, k + f, i + f1, j + f1, k + f1));
		if (entities.isEmpty()) {
			spawner.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
			double c = 0.01;
			if (world.getCollidingBoundingBoxes(spawner, spawner.boundingBox.contract(c, c, c)).isEmpty()) {
				world.spawnEntityInWorld(spawner);
			}
		}
	}
}
