package got.common.entity.other;

import java.util.List;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.network.*;
import got.common.util.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTEntityGrapplingArrow extends EntityThrowable implements IEntityAdditionalSpawnData {
	public Entity shootingEntity = null;
	public int shootingEntityID;
	public boolean firstattach = false;
	public GOTVec thispos;
	public boolean righthand = true;
	public boolean attached = false;
	public double taut = 1;
	public boolean ignoreFrustumCheck = true;

	public int RenderBoundingBoxSize = 999;

	public GOTEntityGrapplingArrow(World worldIn) {
		super(worldIn);
	}

	public GOTEntityGrapplingArrow(World worldIn, EntityLivingBase shooter, boolean righthand) {
		super(worldIn, shooter);
		shootingEntity = shooter;
		shootingEntityID = shootingEntity.getEntityId();
		GOTGrappleHelper.updateMaxLen(worldIn);
		GOTGrappleHelper.updateGrapplingBlocks(worldIn);
		this.righthand = righthand;
	}

	public void clientAttach(double x, double y, double z) {
		setAttachPos(x, y, z);

		if (shootingEntity instanceof EntityPlayer) {
			GOT.proxy.resetlaunchertime(shootingEntityID);
		}
	}

	@Override
	public float func_70182_d() {
		return 5F;
	}

	public int getControlId() {
		return GOTGrappleHelper.GRAPPLEID;
	}

	@Override
	public float getGravityVelocity() {
		return 0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (shootingEntityID == 0) {
			kill();
		}
		if (firstattach) {
			motionX = 0;
			motionY = 0;
			motionZ = 0;
			firstattach = false;
			super.setPosition(thispos.x, thispos.y, thispos.z);
		}
		if (toofaraway()) {
			removeServer();
		}
	}

	@Override
	public void onImpact(MovingObjectPosition movingobjectposition) {
		if (!worldObj.isRemote && (shootingEntityID != 0)) {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {

				Entity entityhit = movingobjectposition.entityHit;
				if (entityhit == shootingEntity) {
					return;
				}
				GOTVec playerpos = GOTVec.positionvec(shootingEntity);
				GOTVec entitypos = GOTVec.positionvec(entityhit);
				GOTVec yank = playerpos.sub(entitypos).mult(0.4);
				entityhit.addVelocity(yank.x, Math.min(yank.y, 2), yank.z);
				removeServer();
				return;
			}
			GOTBlockPos blockpos = null;
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				blockpos = new GOTBlockPos(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
			}
			GOTVec vec3 = null;
			if (movingobjectposition != null) {
				vec3 = new GOTVec(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			serverAttach(blockpos, vec3, movingobjectposition.sideHit);
		}
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		shootingEntityID = data.readInt();
		shootingEntity = worldObj.getEntityByID(shootingEntityID);
		righthand = data.readBoolean();
	}

	public void remove() {
		kill();
	}

	public void removeServer() {
		kill();
		shootingEntityID = 0;

	}

	public void serverAttach(GOTBlockPos blockpos, GOTVec pos, int sideHit) {
		if (attached) {
			return;
		}
		attached = true;
		if ((blockpos != null) && !GOTGrappleHelper.anyblocks) {
			Block block = worldObj.getBlock(blockpos.x, blockpos.y, blockpos.z);

			if ((!GOTGrappleHelper.removeblocks && !GOTGrappleHelper.grapplingblocks.contains(block)) || (GOTGrappleHelper.removeblocks && GOTGrappleHelper.grapplingblocks.contains(block))) {
				removeServer();
				return;
			}
		}
		GOTVec vec3 = GOTVec.positionvec(this);
		vec3.add_ip(GOTVec.motionvec(this));
		if (pos != null) {
			vec3 = pos;

			setPosition(vec3.x, vec3.y, vec3.z);
		}

		switch (sideHit) {
		case 0:
			posY -= 0.3;
			break;
		case 4:
			posX -= 0.05;
			break;
		case 2:
			posZ -= 0.05;
			break;
		default:
			break;
		}

		if (toofaraway()) {
			return;
		}

		motionX = 0;
		motionY = 0;
		motionZ = 0;

		thispos = GOTVec.positionvec(this);
		firstattach = true;
		GOTGrappleHelper.attached.add(shootingEntityID);

		GOTGrappleHelper.sendtocorrectclient(new GOTPacketGrappleAttach(getEntityId(), posX, posY, posZ, getControlId(), shootingEntityID, GOTGrappleHelper.grapplingLength, blockpos), shootingEntityID, worldObj);
		if (shootingEntity instanceof EntityPlayerMP) {
			EntityPlayerMP sender = (EntityPlayerMP) shootingEntity;
			int dimension = sender.dimension;
			MinecraftServer minecraftServer = sender.mcServer;
			for (EntityPlayerMP player : (List<EntityPlayerMP>) minecraftServer.getConfigurationManager().playerEntityList) {
				GOTPacketGrappleAttachPos msg = new GOTPacketGrappleAttachPos(getEntityId(), posX, posY, posZ);
				if (dimension == player.dimension) {
					GOTGrappleHelper.sendtocorrectclient(msg, player.getEntityId(), player.worldObj);
				}
			}
		}
	}

	public void setAttachPos(double x, double y, double z) {
		setPosition(x, y, z);

		motionX = 0;
		motionY = 0;
		motionZ = 0;
		firstattach = true;
		attached = true;
		thispos = new GOTVec(x, y, z);
	}

	public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
		float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		setThrowableHeading(f, f1, f2, velocity, inaccuracy);
		motionX += entityThrower.motionX;
		motionZ += entityThrower.motionZ;

		if (!entityThrower.onGround) {
			motionY += entityThrower.motionY;
		}
	}

	@Override
	public void setPosition(double x, double y, double z) {
		if (thispos != null) {
			x = thispos.x;
			y = thispos.y;
			z = thispos.z;
		}
		super.setPosition(x, y, z);
	}

	public boolean toofaraway() {
		if (shootingEntity == null) {
			return false;
		}
		if ((!worldObj.isRemote && !GOTGrappleHelper.attached.contains(shootingEntityID)) && (GOTGrappleHelper.grapplingLength != 0)) {
			double d = GOTVec.positionvec(this).sub(GOTVec.positionvec(shootingEntity)).length();
			if (d > GOTGrappleHelper.grapplingLength) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + String.valueOf(System.identityHashCode(this)) + "]";
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : 0);
		data.writeBoolean(righthand);
	}
}
