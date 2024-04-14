package got.common.entity.animal;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRegistry;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.westeros.GOTBiomeNeck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityMidges extends EntityLiving implements GOTAmbientCreature {
	private final Midge[] midges;

	private ChunkCoordinates currentFlightTarget;
	private EntityPlayer playerTarget;

	private GOTEntityMidges(World world) {
		super(world);
		setSize(2.0f, 2.0f);
		renderDistanceWeight = 0.5;
		midges = new Midge[3 + rand.nextInt(6)];
		for (int l = 0; l < midges.length; ++l) {
			midges[l] = new Midge();
		}
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void collideWithEntity(Entity entity) {
	}

	@Override
	public void collideWithNearbyEntities() {
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);
		return j >= 62 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock && super.getCanSpawnHere();
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		int id = GOTEntityRegistry.getEntityID(this);
		if (id > 0 && GOTEntityRegistry.SPAWN_EGGS.containsKey(id)) {
			return new ItemStack(GOTItems.spawnEgg, 1, id);
		}
		return null;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		Entity attacker;
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource instanceof EntityDamageSourceIndirect && (attacker = damagesource.getEntity()) instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) attacker;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
				EntityPlayer entityplayer = npc.hiredNPCInfo.getHiringPlayer();
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.shootDownMidges);
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		motionY *= 0.6;
		for (Midge midge : midges) {
			midge.update();
		}
		if (rand.nextInt(5) == 0) {
			playSound("got:midges.swarm", getSoundVolume(), getSoundPitch());
		}
		if (!worldObj.isRemote && isEntityAlive()) {
			boolean inMidgewater = GOTCrashHandler.getBiomeGenForCoords(worldObj, MathHelper.floor_double(posX), MathHelper.floor_double(posZ)) instanceof GOTBiomeNeck;
			int chance = inMidgewater ? 100 : 500;
			if (rand.nextInt(chance) == 0) {
				double range = inMidgewater ? 16.0 : 24.0;
				int threshold = inMidgewater ? 6 : 5;
				List<GOTEntityMidges> list = worldObj.getEntitiesWithinAABB(GOTEntityMidges.class, boundingBox.expand(range, range, range));
				if (list.size() < threshold) {
					GOTEntityMidges moreMidges = new GOTEntityMidges(worldObj);
					moreMidges.setLocationAndAngles(posX, posY, posZ, rand.nextFloat() * 360.0f, 0.0f);
					moreMidges.onSpawnWithEgg(null);
					worldObj.spawnEntityInWorld(moreMidges);
				}
			}
		}
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (currentFlightTarget != null && !worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ)) {
			currentFlightTarget = null;
		}
		if (playerTarget != null && (!playerTarget.isEntityAlive() || getDistanceSqToEntity(playerTarget) > 256.0)) {
			playerTarget = null;
		}
		if (playerTarget != null) {
			if (rand.nextInt(400) == 0) {
				playerTarget = null;
			} else {
				currentFlightTarget = new ChunkCoordinates((int) playerTarget.posX, (int) playerTarget.posY + 3, (int) playerTarget.posZ);
			}
		} else if (rand.nextInt(100) == 0) {
			EntityPlayer closestPlayer = worldObj.getClosestPlayerToEntity(this, 12.0);
			if (closestPlayer != null && rand.nextInt(7) == 0) {
				playerTarget = closestPlayer;
			} else {
				int i = (int) posX + rand.nextInt(7) - rand.nextInt(7);
				int j = (int) posY + rand.nextInt(4) - rand.nextInt(3);
				int k = (int) posZ + rand.nextInt(7) - rand.nextInt(7);
				if (j < 1) {
					j = 1;
				}
				int height = worldObj.getTopSolidOrLiquidBlock(i, k);
				if (j > height + 8) {
					j = height + 8;
				}
				currentFlightTarget = new ChunkCoordinates(i, j, k);
			}
		}
		if (currentFlightTarget != null) {
			double dx = currentFlightTarget.posX + 0.5 - posX;
			double dy = currentFlightTarget.posY + 0.5 - posY;
			double dz = currentFlightTarget.posZ + 0.5 - posZ;
			motionX += (Math.signum(dx) * 0.5 - motionX) * 0.1;
			motionY += (Math.signum(dy) * 0.7 - motionY) * 0.1;
			motionZ += (Math.signum(dz) * 0.5 - motionZ) * 0.1;
			moveForward = 0.2f;
		} else {
			motionZ = 0.0;
			motionY = 0.0;
			motionX = 0.0;
		}
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

	public Midge[] getMidges() {
		return midges;
	}

	public class Midge {
		private static final int MAX_MIDGE_TICK = 80;
		private final float midgeInitialPosY;
		private final float midgeRotation;
		private final float midgePosX;
		private final float midgePosZ;

		private float midgePrevPosX;
		private float midgePrevPosY;
		private float midgePrevPosZ;
		private float midgePosY;
		private int midgeTick;

		protected Midge() {
			midgePosX = -1.0F + rand.nextFloat() * 2.0F;
			midgePosY = rand.nextFloat() * 2.0F;
			midgePosZ = -1.0F + rand.nextFloat() * 2.0F;
			midgeInitialPosY = midgePosY = rand.nextFloat() * 2.0f;
			midgeRotation = rand.nextFloat() * 360.0f;
			midgeTick = rand.nextInt(MAX_MIDGE_TICK);
		}

		protected void update() {
			midgePrevPosX = midgePosX;
			midgePrevPosY = midgePosY;
			midgePrevPosZ = midgePosZ;
			++midgeTick;
			if (midgeTick > MAX_MIDGE_TICK) {
				midgeTick = 0;
			}
			midgePosY = midgeInitialPosY + 0.5f * MathHelper.sin(midgeTick / 6.2831855f);
		}

		public float getMidgePosX() {
			return midgePosX;
		}

		public float getMidgePosY() {
			return midgePosY;
		}

		public float getMidgePosZ() {
			return midgePosZ;
		}

		public float getMidgePrevPosX() {
			return midgePrevPosX;
		}

		public float getMidgePrevPosY() {
			return midgePrevPosY;
		}

		public float getMidgePrevPosZ() {
			return midgePrevPosZ;
		}

		public float getMidgeRotation() {
			return midgeRotation;
		}
	}
}