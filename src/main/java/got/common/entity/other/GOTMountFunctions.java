package got.common.entity.other;

import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMountControl;
import got.common.network.GOTPacketMountControlServerEnforce;
import got.coremod.GOTReplacedMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTMountFunctions {
	public static boolean canRiderControl(Entity entity) {
		Entity rider = entity.riddenByEntity;
		if (rider instanceof EntityPlayer) {
			return ((EntityPlayer) rider).isClientWorld();
		}
		return !entity.worldObj.isRemote;
	}

	public static boolean interact(GOTNPCMount mount, EntityPlayer entityplayer) {
		EntityLiving entity = (EntityLiving) mount;
		if (mount.getBelongsToNPC() && entity.riddenByEntity == null) {
			if (!entity.worldObj.isRemote) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.mountOwnedByNPC"));
			}
			return true;
		}
		return false;
	}

	public static boolean isMountControllable(Entity mount) {
		if (mount instanceof EntityHorse && ((EntityHorse) mount).isTame()) {
			return true;
		}
		return mount instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) mount).isNPCTamed();
	}

	public static boolean isPlayerControlledMount(Entity mount) {
		if (mount != null && mount.riddenByEntity instanceof EntityPlayer && isMountControllable(mount)) {
			return canRiderControl(mount);
		}
		return false;
	}

	public static void move(GOTNPCMount mount, float strafe, float forward) {
		EntityLiving entity = (EntityLiving) mount;
		Entity rider = entity.riddenByEntity;
		if (rider instanceof EntityPlayer && mount.isMountSaddled()) {
			entity.prevRotationYaw = entity.rotationYaw = rider.rotationYaw;
			entity.rotationPitch = rider.rotationPitch * 0.5f;
			entity.rotationYaw %= 360.0f;
			entity.rotationPitch %= 360.0f;
			entity.rotationYawHead = entity.renderYawOffset = entity.rotationYaw;
			strafe = ((EntityLivingBase) rider).moveStrafing * 0.5f;
			forward = ((EntityLivingBase) rider).moveForward;
			if (forward <= 0.0f) {
				forward *= 0.25f;
			}
			entity.stepHeight = 1.0f;
			entity.jumpMovementFactor = entity.getAIMoveSpeed() * 0.1f;
			if (GOTReplacedMethods.MountFunctions.canRiderControl_elseNoMotion(entity)) {
				entity.setAIMoveSpeed((float) entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				mount.super_moveEntityWithHeading(strafe, forward);
			}
			entity.prevLimbSwingAmount = entity.limbSwingAmount;
			double d0 = entity.posX - entity.prevPosX;
			double d1 = entity.posZ - entity.prevPosZ;
			float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0f;
			if (f4 > 1.0f) {
				f4 = 1.0f;
			}
			entity.limbSwingAmount += (f4 - entity.limbSwingAmount) * 0.4f;
			entity.limbSwing += entity.limbSwingAmount;
		} else {
			entity.stepHeight = 0.5f;
			entity.jumpMovementFactor = 0.02f;
			mount.super_moveEntityWithHeading(strafe, forward);
		}
	}

	public static void sendControlToServer(EntityPlayer clientPlayer) {
		sendControlToServer(clientPlayer, null);
	}

	public static void sendControlToServer(EntityPlayer clientPlayer, GOTPacketMountControlServerEnforce pktSet) {
		Entity mount = clientPlayer.ridingEntity;
		if (isPlayerControlledMount(mount)) {
			if (pktSet != null) {
				mount.setPositionAndRotation(pktSet.posX, pktSet.posY, pktSet.posZ, pktSet.rotationYaw, pktSet.rotationPitch);
				mount.updateRiderPosition();
			}
			GOTPacketMountControl pkt = new GOTPacketMountControl(mount);
			GOTPacketHandler.networkWrapper.sendToServer(pkt);
		}
	}

	public static void setNavigatorRangeFromNPC(GOTNPCMount mount, GOTEntityNPC npc) {
		EntityLiving entity = (EntityLiving) mount;
		double d = npc.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
		entity.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(d);
	}

	public static void update(GOTNPCMount mount) {
		EntityLiving entity = (EntityLiving) mount;
		World world = entity.worldObj;
		Random rand = entity.getRNG();
		if (!world.isRemote) {
			if (rand.nextInt(900) == 0 && entity.isEntityAlive()) {
				entity.heal(1.0f);
			}
			if (!(entity instanceof GOTEntityNPC)) {
				EntityLivingBase target;
				if (entity.getAttackTarget() != null && (!(target = entity.getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
					entity.setAttackTarget(null);
				}
				if (entity.riddenByEntity instanceof EntityLiving) {
					target = ((EntityLiving) entity.riddenByEntity).getAttackTarget();
					entity.setAttackTarget(target);
				} else if (entity.riddenByEntity instanceof EntityPlayer) {
					entity.setAttackTarget(null);
				}
			}
		}
	}
}