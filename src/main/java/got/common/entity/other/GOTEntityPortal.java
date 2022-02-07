package got.common.entity.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.GOTDimension;
import got.common.world.GOTTeleporter;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class GOTEntityPortal extends Entity {
	public static int MAX_SCALE = 120;
	public float prevPortalRotation;
	public float portalRotation;

	public GOTEntityPortal(World world) {
		super(world);
		setSize(3.0f, 1.5f);
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				Block.SoundType sound = Blocks.glass.stepSound;
				worldObj.playSoundAtEntity(this, sound.getBreakSound(), (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
				worldObj.setEntityState(this, (byte) 16);
				setDead();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(10, (short) 0);
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(Items.iron_sword);
	}

	public float getPortalRotation(float f) {
		return prevPortalRotation + (portalRotation - prevPortalRotation) * f;
	}

	public int getScale() {
		return dataWatcher.getWatchableObjectShort(10);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int l = 0; l < 16; ++l) {
				worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(Items.iron_sword), posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		if (entity instanceof EntityPlayer) {
			return attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity), 0.0f);
		}
		return false;
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public void onUpdate() {
		prevPortalRotation = portalRotation;
		portalRotation += 4.0f;
		while (portalRotation - prevPortalRotation < -180.0f) {
			prevPortalRotation -= 360.0f;
		}
		while (portalRotation - prevPortalRotation >= 180.0f) {
			prevPortalRotation += 360.0f;
		}
		if (!worldObj.isRemote && dimension != 0 && dimension != GOTDimension.GAME_OF_THRONES.dimensionID) {
			setDead();
		}
		if (!worldObj.isRemote && getScale() < MAX_SCALE) {
			setScale(getScale() + 1);
		}
		if (getScale() >= MAX_SCALE) {
			int i;
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(8.0, 8.0, 8.0));
			for (Object player : players) {
				EntityPlayer entityplayer = (EntityPlayer) player;
				if (!boundingBox.intersectsWith(entityplayer.boundingBox) || entityplayer.ridingEntity != null || entityplayer.riddenByEntity != null) {
					continue;
				}
				GOT.getProxy().setInPortal(entityplayer);
			}
			List entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8.0, 8.0, 8.0));
			for (i = 0; i < entities.size(); ++i) {
				Entity entity = (Entity) entities.get(i);
				if (entity instanceof EntityPlayer || !boundingBox.intersectsWith(entity.boundingBox) || entity.ridingEntity != null || entity.riddenByEntity != null || entity.timeUntilPortal != 0) {
					continue;
				}
				transferEntity(entity);
			}
			if (rand.nextInt(50) == 0) {
				worldObj.playSoundAtEntity(this, "portal.portal", 0.5f, rand.nextFloat() * 0.4f + 0.8f);
			}
			for (i = 0; i < 2; ++i) {
				double d = posX - 3.0 + rand.nextFloat() * 6.0f;
				double d1 = posY - 0.75 + rand.nextFloat() * 3.0f;
				double d2 = posZ - 3.0 + rand.nextFloat() * 6.0f;
				double d3 = (posX - d) / 8.0;
				double d4 = (posY + 1.5 - d1) / 8.0;
				double d5 = (posZ - d2) / 8.0;
				double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				double d7 = 1.0 - d6;
				double d8 = 0.0;
				double d9 = 0.0;
				double d10 = 0.0;
				if (d7 > 0.0) {
					d7 *= d7;
					d8 += d3 / d6 * d7 * 0.2;
					d9 += d4 / d6 * d7 * 0.2;
					d10 += d5 / d6 * d7 * 0.2;
				}
				worldObj.spawnParticle("flame", d, d1, d2, d8, d9, d10);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setScale(nbt.getInteger("Scale"));
	}

	public void setScale(int i) {
		dataWatcher.updateObject(10, (short) i);
	}

	public void transferEntity(Entity entity) {
		if (!worldObj.isRemote) {
			int dimension = 0;
			if (entity.dimension == 0) {
				dimension = GOTDimension.GAME_OF_THRONES.dimensionID;
			} else if (entity.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
				dimension = 0;
			}
			GOT.transferEntityToDimension(entity, dimension, new GOTTeleporter(DimensionManager.getWorld(dimension), true));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Scale", getScale());
	}
}
