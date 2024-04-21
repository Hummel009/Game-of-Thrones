package got.common.entity.other;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class GOTEntityRugBase extends Entity implements GOTBannerProtectable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	protected GOTEntityRugBase(World world) {
		super(world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && !isDead) {
			Block.SoundType blockSound = Blocks.wool.stepSound;
			worldObj.playSoundAtEntity(this, blockSound.getBreakSound(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
			Entity attacker = damagesource.getEntity();
			boolean creative = attacker instanceof EntityPlayer && ((EntityPlayer) attacker).capabilities.isCreativeMode;
			if (!creative) {
				entityDropItem(getRugItem(), 0.0f);
			}
			setDead();
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	@SuppressWarnings("NoopMethodInAbstractClass")
	public void entityInit() {
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getRugItem();
	}

	protected abstract ItemStack getRugItem();

	private int getTimeUntilGrowl() {
		return (60 + rand.nextInt(150)) * 20;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.04;
		func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98f;
		if (onGround) {
			f = 0.588f;
			Block i = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
			if (i.getMaterial() != Material.air) {
				f = i.slipperiness * 0.98f;
			}
		}
		motionX *= f;
		motionY *= 0.98;
		motionZ *= f;
		if (onGround) {
			motionY *= -0.5;
		}
	}

	@Override
	@SuppressWarnings("NoopMethodInAbstractClass")
	public void readEntityFromNBT(NBTTagCompound nbt) {
	}

	@Override
	@SuppressWarnings("NoopMethodInAbstractClass")
	public void writeEntityToNBT(NBTTagCompound nbt) {
	}
}