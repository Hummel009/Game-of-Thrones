package got.common.entity.essos;

import got.common.database.GOTAchievement;
import got.common.entity.ai.*;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.*;
import got.common.entity.westeros.*;
import got.common.entity.westeros.legendary.trader.GOTEntityGendryBaratheon;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.faction.GOTFaction;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
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
		addTargetTasks();
		spawnsInDarkness = true;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killShryke;
	}

	public void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGoldenMan.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityWesterosBandit.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityWesterosScrapTrader.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGendryBaratheon.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityBronn.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGeroldDayne.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityThreeEyedRaven.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityMaester.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityProstitute.class, 0, true));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(5.0);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getDeathSound() {
		return "got:crocodile.death";
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLivingBase) {
				GOTItemSword.applyStandardPoison((EntityLivingBase)entity);
			}
			return true;
		}
		return false;
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
	public String getLivingSound() {
		return "got:crocodile.say";
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && this.getClass() == familyInfo.marriageEntityClass && rand.nextInt(7) == 0) {
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
