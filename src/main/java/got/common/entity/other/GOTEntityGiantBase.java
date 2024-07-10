package got.common.entity.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTNames;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.inanimate.GOTEntityThrownRock;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class GOTEntityGiantBase extends GOTEntityNPC implements GOTBiome.ImmuneToFrost {
	private static final IAttribute THROWN_ROCK_DAMAGE = new RangedAttribute("got.thrownRockDamage", 10.0, 0.0, 200.0).setDescription("GOT Thrown Rock Damage");

	private final EntityAIBase rangedAttackAI = new GOTEntityAIRangedAttack(this, 1.2, 30, 60, 25.0f);
	private final EntityAIBase meleeAttackAI = new GOTEntityAIAttackOnCollide(this, 1.4, false);

	protected GOTEntityGiantBase(World world) {
		super(world);
		setSize(1.6f * 1.6f, 3.2f * 1.6f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(4, meleeAttackAI);
		tasks.addTask(5, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(6, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(7.0);
		getAttributeMap().registerAttribute(THROWN_ROCK_DAMAGE);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		EntityArrow template = new EntityArrow(worldObj, this, target, f * 1.5f, 0.5f);
		GOTEntityThrownRock rock = getThrownRock();
		rock.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
		rock.motionX = template.motionX;
		rock.motionY = template.motionY + 0.6;
		rock.motionZ = template.motionZ;
		worldObj.spawnEntityInWorld(rock);
		playSound(getLivingSound(), getSoundVolume(), getSoundPitch() * 0.75f);
		swingItem();
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(21, (byte) 0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			float attackDamage = (float) getEntityAttribute(NPC_ATTACK_DAMAGE).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("got:giant.step", 0.75f, getSoundPitch());
	}

	@Override
	public String getDeathSound() {
		return "got:giant.say";
	}

	@Override
	public String getHurtSound() {
		return "got:giant.say";
	}

	@Override
	public String getLivingSound() {
		return "got:giant.say";
	}

	@Override
	public float getSoundVolume() {
		return 1.5f;
	}

	@Override
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 2.0;
		motionY /= 2.0;
		motionZ /= 2.0;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getGiantName(rand));
	}

	@Override
	public double getMeleeRange() {
		return 12.0;
	}

	private GOTEntityThrownRock getThrownRock() {
		GOTEntityThrownRock rock = new GOTEntityThrownRock(worldObj, this);
		rock.setDamage((float) getEntityAttribute(THROWN_ROCK_DAMAGE).getAttributeValue());
		return rock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			super.handleHealthUpdate(b);
			for (int l = 0; l < 64; ++l) {
				GOT.proxy.spawnParticle("largeStone", posX + rand.nextGaussian() * width * 0.5, posY + rand.nextDouble() * height, posZ + rand.nextGaussian() * width * 0.5, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	public boolean isThrowingRocks() {
		return dataWatcher.getWatchableObjectByte(21) == 1;
	}

	private void setThrowingRocks(boolean flag) {
		dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setThrowingRocks(false);
		}
		if (mode == GOTEntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, meleeAttackAI);
			setThrowingRocks(false);
		}
		if (mode == GOTEntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(3, rangedAttackAI);
			setThrowingRocks(true);
		}
	}
}