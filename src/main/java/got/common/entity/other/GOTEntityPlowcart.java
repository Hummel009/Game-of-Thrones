package got.common.entity.other;

import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityPlowcart extends GOTEntityCart {
	public boolean plowing;
	public int lasthit;
	public int hitcount;
	public double bladeOffset = 1.2;

	public GOTEntityPlowcart(World worldIn) {
		super(worldIn);
	}

	public GOTEntityPlowcart(World worldIn, double x, double y, double z) {
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
				if (lasthit >= ticksExisted - 20) {
					++hitcount;
				} else {
					hitcount = 0;
				}
				if (hitcount == 10) {
					setDead();
					if (!worldObj.isRemote) {
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + 1.0, posZ, new ItemStack(GOTRegistry.plowcart)));
					}
				}
			}
		}
		return true;
	}

	public boolean getPlowing() {
		return plowing;
	}

	public void setPlowing(boolean plowingIn) {
		plowing = plowingIn;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		plowing = !plowing;
		return true;
	}

	@Override
	public void onUpdate() {
		Block targetblock;
		super.onUpdate();
		if (getPulling() != null && (targetblock = worldObj.getBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) (posY - 1.0), (int) (posZ - getLookVec().zCoord * bladeOffset))) != null && worldObj.isAirBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) posY, (int) (posZ - getLookVec().zCoord * bladeOffset)) && (targetblock == Blocks.dirt || targetblock == Blocks.grass) && plowing) {
			worldObj.setBlock((int) (posX - getLookVec().xCoord * bladeOffset), (int) (posY - 1.0), (int) (posZ - getLookVec().zCoord * bladeOffset), Blocks.farmland, 7, 2);
		}
	}
}
