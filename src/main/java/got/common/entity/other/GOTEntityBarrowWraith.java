package got.common.entity.other;

import got.GOT;
import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.entity.other.utils.UndeadUtils;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityBarrowWraith extends GOTEntityNPC implements GOTBiome.ImmuneToFrost {
	private static final Potion[] ATTACK_EFFECTS = {Potion.moveSlowdown, Potion.digSlowdown, Potion.wither};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBarrowWraith(World world) {
		super(world);
		setSize(0.8f, 2.5f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, getWightAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true);
		isImmuneToFire = true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killBarrowWight;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(5.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				for (Potion effect : ATTACK_EFFECTS) {
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(effect.id, duration * 20, 0));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, -1);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		boolean causeDamage = UndeadUtils.calculateDamage(this, damageSource, true);
		return super.attackEntityFrom(damageSource, causeDamage ? amount : 0.0f);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public String getDeathSound() {
		return "got:wight.death";
	}

	@Override
	public String getHurtSound() {
		return "got:wight.hurt";
	}

	public int getTargetEntityID() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	private void setTargetEntityID(Entity entity) {
		dataWatcher.updateObject(16, entity == null ? -1 : entity.getEntityId());
	}

	private EntityAIBase getWightAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			for (int l = 0; l < 1; ++l) {
				double d = posX + width * MathHelper.getRandomDoubleInRange(rand, -0.5, 0.5);
				double d1 = posY + height * MathHelper.getRandomDoubleInRange(rand, 0.4, 0.8);
				double d2 = posZ + width * MathHelper.getRandomDoubleInRange(rand, -0.5, 0.5);
				double d3 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				double d4 = MathHelper.getRandomDoubleInRange(rand, -0.2, -0.05);
				double d5 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				if (rand.nextBoolean()) {
					GOT.proxy.spawnParticle("asshaiTorch", d, d1, d2, d3, d4, d5);
					continue;
				}
				worldObj.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
			}
		}
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (!worldObj.isRemote) {
			setTargetEntityID(target);
		}
	}
}