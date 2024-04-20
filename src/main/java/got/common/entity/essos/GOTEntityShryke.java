package got.common.entity.essos;

import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityShryke extends GOTEntityNPC {
	public GOTEntityShryke(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		addTargetTasks(true);
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(5.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLivingBase) {
				GOTItemSword.applyStandardPoison((EntityLivingBase) entity);
			}
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getDeathSound() {
		return "got:crocodile.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public String getHurtSound() {
		return "got:crocodile.say";
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killShryke;
	}

	@Override
	public String getLivingSound() {
		return "got:crocodile.say";
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && getClass() == familyInfo.marriageEntityClass && rand.nextInt(7) == 0) {
			familyInfo.setChild();
		}
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getHealth() < getMaxHealth() && ticksExisted % 10 == 0) {
			heal(1.0f);
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}