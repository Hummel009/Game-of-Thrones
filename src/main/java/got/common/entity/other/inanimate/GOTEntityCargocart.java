package got.common.entity.other.inanimate;

import got.common.database.GOTItems;
import got.common.util.GOTVertex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityCargocart extends GOTEntityCart {
	private int load;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCargocart(World worldIn) {
		super(worldIn);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
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

	private void dropAsItem(boolean drop) {
		setDead();
		if (drop) {
			entityDropItem(new ItemStack(GOTItems.cargocart), 0.0f);
		}
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int loadIn) {
		load = loadIn;
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

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			GOTVertex vertex = new GOTVertex(-0.68, 0.0, 0.0).rotateYaw(-rotationYaw * 0.017453292f - 1.5707964f);
			riddenByEntity.setPosition(posX + vertex.getX(), posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + vertex.getZ());
		}
	}
}