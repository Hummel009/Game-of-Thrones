package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.util.GOTVec3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityCargocart extends GOTEntityCart {
	public int load;

	public GOTEntityCargocart(World worldIn) {
		super(worldIn);
	}

	public GOTEntityCargocart(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!isDead && !worldObj.isRemote) {
			setBeenAttacked();
			worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
			boolean drop = !(damagesource.getEntity() instanceof EntityPlayer) || !((EntityPlayer) damagesource.getEntity()).capabilities.isCreativeMode;
			dropAsItem(drop);
		}
		return true;
	}

	public void dropAsItem(boolean drop) {
		setDead();
		if (drop) {
			entityDropItem(new ItemStack(GOTItems.cargocart), 0.0f);
		}
	}

	public int getLoad() {
		return load;
	}

	@Override
	public double getMountedYOffset() {
		return 0.7;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		if (!worldObj.isRemote) {
			player.mountEntity(this);
		}
		return true;
	}

	public void setLoad(int loadIn) {
		load = loadIn;
	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			GOTVec3d gOTVec3d = new GOTVec3d(-0.68, 0.0, 0.0).rotateYaw(-rotationYaw * 0.017453292f - 1.5707964f);
			riddenByEntity.setPosition(posX + gOTVec3d.xCoord, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + gOTVec3d.zCoord);
		}
	}
}
