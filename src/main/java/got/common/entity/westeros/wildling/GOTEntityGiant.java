package got.common.entity.westeros.wildling;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.GOTEntityGiantBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityThrownRock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class GOTEntityGiant extends GOTEntityGiantBase {
	public static IAttribute thrownRockDamage = new RangedAttribute("got.thrownRockDamage", 10.0, 0.0, 200.0).setDescription("GOT Thrown Rock Damage");
	public EntityAIBase rangedAttackAI = getGiantRangedAttackAI();
	public EntityAIBase meleeAttackAI;

	public GOTEntityGiant(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.253);
		getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
		getAttributeMap().registerAttribute(thrownRockDamage);
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
	public EntityAIBase getGiantAttackAI() {
		meleeAttackAI = new GOTEntityAIAttackOnCollide(this, 1.4, false);
		return meleeAttackAI;
	}

	public EntityAIBase getGiantRangedAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.2, 30, 60, 25.0f);
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killGiant;
	}

	@Override
	public double getMeleeRange() {
		return 12.0;
	}

	public GOTEntityThrownRock getThrownRock() {
		GOTEntityThrownRock rock = new GOTEntityThrownRock(worldObj, this);
		rock.setDamage((float) getEntityAttribute(thrownRockDamage).getAttributeValue());
		return rock;
	}

	@SideOnly(value = Side.CLIENT)
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

	public void setThrowingRocks(boolean flag) {
		dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
	}
}