package got.common.entity.other;

import got.GOT;
import got.common.database.GOTRegistry;
import got.common.util.GOTVec3d;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityCargocart extends GOTEntityCart {
	public int load;
	public int lasthit;
	public int hitcount;

	public GOTEntityCargocart(World worldIn) {
		super(worldIn);
	}

	public GOTEntityCargocart(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!isDead) {
			if (source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode) {
				setDead();
			}
			if (source.isFireDamage()) {
				setDead();
			} else {
				lasthit = ticksExisted;
				hitcount = lasthit >= ticksExisted - 20 ? ++hitcount : 0;
				if (hitcount == 10) {
					setDead();
					if (!worldObj.isRemote) {
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + 1.0, posZ, new ItemStack(GOTRegistry.cargocart)));
					}
				}
			}
		}
		return true;
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
		if (player.isSneaking()) {
			player.openGui(GOT.instance, 83, worldObj, getEntityId(), 0, 0);
		} else if (!worldObj.isRemote) {
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
