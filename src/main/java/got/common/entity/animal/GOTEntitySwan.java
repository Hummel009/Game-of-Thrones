package got.common.entity.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.other.iface.GOTAmbientCreature;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTEntitySwan extends EntityCreature implements GOTAmbientCreature, GOTBiome.ImmuneToFrost {
	private static final Random VIOLENCE_RAND = new Random();

	private final EntityAIBase attackAI = new GOTEntityAIAttackOnCollide(this, 1.4, true);
	private final EntityAIBase fleeAI = new EntityAIPanic(this, 1.8);

	private float flapPhase;
	private float flapPower;
	private float prevFlapPower;
	private float prevFlapPhase;
	private float flapAccel = 1.0f;

	private int peckTime;
	private int peckLength;
	private int timeUntilHiss;

	private boolean assignedAttackOrFlee;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySwan(World world) {
		super(world);
		setSize(0.5f, 0.7f);
		getNavigator().setAvoidsWater(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, attackAI);
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityLivingBase.class, 10.0f, 0.05f));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		IEntitySelector swanAttackRange = new EntitySelectorImpl(this);
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, swanAttackRange));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		float f = (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();

		if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), f)) {
			worldObj.setEntityState(this, (byte) 20);

			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		damagesource.getEntity();

		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int feathers = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < feathers; ++l) {
			dropItem(GOTItems.swanFeather, 1);
		}
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		return worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock ? 10.0f : worldObj.getLightBrightness(i, j, k) - 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 1 + worldObj.rand.nextInt(2);
	}

	public float getPeckAngle(float tick) {
		if (peckLength == 0) {
			return 0.0f;
		}
		float peck = (peckTime + tick) / peckLength;
		float cutoff = 0.2f;
		if (peck < cutoff) {
			return peck / cutoff;
		}
		if (peck < 1.0f - cutoff) {
			return 1.0f;
		}
		return (1.0f - peck) / cutoff;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 20) {
			peckLength = 10;
		} else if (b == 21) {
			peckLength = 40;
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	private boolean isViolentSwan() {
		long seed = getUniqueID().getLeastSignificantBits();
		VIOLENCE_RAND.setSeed(seed);
		return VIOLENCE_RAND.nextBoolean();
	}

	@Override
	public void onLivingUpdate() {
		EntityLivingBase target;
		super.onLivingUpdate();
		prevFlapPhase = flapPhase;
		prevFlapPower = flapPower;
		flapPower += onGround ? -0.02f : 0.05f;
		flapPower = Math.max(0.0f, Math.min(flapPower, 1.0f));
		if (!onGround) {
			flapAccel = 0.6f;
		}
		flapPhase += flapAccel;
		flapAccel *= 0.95f;
		if (!onGround && motionY < 0.0) {
			motionY *= 0.6;
		}
		if (inWater && motionY < 0.0) {
			motionY *= 0.01;
		}
		if (!worldObj.isRemote && getAttackTarget() != null && (!(target = getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
			setAttackTarget(null);
		}
		if (peckLength > 0) {
			++peckTime;
			if (peckTime >= peckLength) {
				peckTime = 0;
				peckLength = 0;
			}
		} else {
			peckTime = 0;
		}
	}

	@Override
	public void updateAITasks() {
		if (!assignedAttackOrFlee) {
			tasks.removeTask(attackAI);
			tasks.removeTask(fleeAI);
			boolean violent = isViolentSwan();
			if (violent) {
				tasks.addTask(1, attackAI);
			} else {
				tasks.addTask(1, fleeAI);
			}
			assignedAttackOrFlee = true;
		}
		super.updateAITasks();
		if (timeUntilHiss <= 0) {
			List<EntityPlayer> nearbyPlayers;
			double range;
			if (getAttackTarget() == null && rand.nextInt(3) == 0 && !(nearbyPlayers = worldObj.selectEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(range = 8.0, range, range), GOT.selectNonCreativePlayers())).isEmpty()) {
				EntityPlayer entityplayer = nearbyPlayers.get(rand.nextInt(nearbyPlayers.size()));
				getNavigator().clearPathEntity();
				float hissLook = (float) Math.toDegrees(Math.atan2(entityplayer.posZ - posZ, entityplayer.posX - posX));
				rotationYaw = rotationYawHead = hissLook - 90.0f;
				worldObj.setEntityState(this, (byte) 21);
				playSound("got:swan.hiss", getSoundVolume(), getSoundPitch());
				timeUntilHiss = 80 + rand.nextInt(80);
			}
		} else {
			--timeUntilHiss;
		}
	}

	public float getFlapPhase() {
		return flapPhase;
	}

	public float getFlapPower() {
		return flapPower;
	}

	public float getPrevFlapPower() {
		return prevFlapPower;
	}

	public float getPrevFlapPhase() {
		return prevFlapPhase;
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTEntitySwan swan;

		private EntitySelectorImpl(GOTEntitySwan swan) {
			this.swan = swan;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return entity instanceof EntityLivingBase && entity.isEntityAlive() && swan.getDistanceSqToEntity(entity) < 16.0;
		}
	}
}