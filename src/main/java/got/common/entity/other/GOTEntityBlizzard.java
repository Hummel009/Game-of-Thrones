package got.common.entity.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTConfig;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.utils.IceUtils;
import got.common.faction.GOTFaction;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityBlizzard extends GOTEntityNPC implements GOTBiome.ImmuneToFrost {
	private final EntityAIBase rangedAttackAI = new GOTEntityAIRangedAttack(this, 1.25, 20, 40, 20.0f);

	{
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBlizzard(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, rangedAttackAI);
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		isImmuneToFire = true;
	}

	@Override
	public boolean isSpawnsInDarkness() {
		return true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killBlizzard;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		npcSnowballAttack(target);
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			tasks.removeTask(rangedAttackAI);
		}
		if (mode == AttackMode.RANGED) {
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
		}
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return IceUtils.attackWithFrost(entity, super.attackEntityAsMob(entity));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, GOTConfig.walkerFireDamage);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		if (rand.nextFloat() <= 0.525f) {
			dropItem(GOTItems.iceShard, rand.nextInt(2) + 1);
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		IceUtils.createNewWight(this, entity, worldObj);
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public float getBrightness(float f) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "mob.blaze.death";
	}

	@Override
	public String getHurtSound() {
		return "mob.blaze.hit";
	}

	@Override
	public String getLivingSound() {
		return "mob.blaze.breathe";
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (motionX * motionX + motionY * motionY + motionZ * motionZ >= 0.01) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			double d1 = boundingBox.minY + MathHelper.randomFloatClamp(rand, 0.2f, 0.7f) * height;
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			GOT.proxy.spawnParticle("chill", d, d1, d2, -motionX * 0.5, 0.0, -motionZ * 0.5);
		}
	}
}