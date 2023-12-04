package got.common.entity.other;

import got.common.database.GOTItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityPlowcart extends GOTEntityCart {
	public boolean plowing;
	public double bladeOffset = 1.2;

	public GOTEntityPlowcart(World worldIn) {
		super(worldIn);
	}

	public GOTEntityPlowcart(World worldIn, double x, double y, double z) {
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
			entityDropItem(new ItemStack(GOTItems.plowcart), 0.0f);
		}
	}

	public boolean getPlowing() {
		return plowing;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		plowing = !plowing;
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		Block targetblock;
		if (getPulling() != null && (targetblock = worldObj.getBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) (posY - 1.0), (int) (posZ - getLookVec().zCoord * bladeOffset))) != null && worldObj.isAirBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) posY, (int) (posZ - getLookVec().zCoord * bladeOffset)) && (targetblock == Blocks.dirt || targetblock == Blocks.grass) && plowing) {
			worldObj.setBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) (posY - 1.0), (int) (posZ - getLookVec().zCoord * bladeOffset), Blocks.farmland, 7, 2);
		}
	}
}
