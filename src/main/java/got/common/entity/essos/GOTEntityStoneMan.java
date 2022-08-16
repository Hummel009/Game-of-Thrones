package got.common.entity.essos;

import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.faction.GOTFaction;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityStoneMan extends GOTEntityNPC {
	public GOTEntityStoneMan(World world) {
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
				GOTItemSword.applyStandardWither((EntityLivingBase) entity);
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
		return 5.0f;
	}

	@Override
	public String getDeathSound() {
		return "mob.zombie.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public String getHurtSound() {
		return "mob.zombie.hurt";
	}

	@Override
	public String getLivingSound() {
		return "mob.zombie.say";
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
	public void onKillEntity(EntityLivingBase entity) {
		GOTEntityStoneMan infected = new GOTEntityStoneMan(worldObj);
		if (entity instanceof GOTEntityBericDondarrion || entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityLancelLannister || entity instanceof GOTEntityTheonGreyjoy) {
			super.onKillEntity(entity);
		} else if (entity instanceof GOTEntityHumanBase) {
			super.onKillEntity(entity);
			infected.familyInfo.setAge(((GOTEntityHumanBase) entity).familyInfo.getAge());
			infected.copyLocationAndAnglesFrom(entity);
			infected.npcItemsInv.setMeleeWeapon(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			infected.npcItemsInv.setIdleItem(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			infected.setCurrentItemOrArmor(1, ((GOTEntityHumanBase) entity).getEquipmentInSlot(1));
			infected.setCurrentItemOrArmor(2, ((GOTEntityHumanBase) entity).getEquipmentInSlot(2));
			infected.setCurrentItemOrArmor(3, ((GOTEntityHumanBase) entity).getEquipmentInSlot(3));
			infected.setCurrentItemOrArmor(4, ((GOTEntityHumanBase) entity).getEquipmentInSlot(4));
			infected.familyInfo.setMale(((GOTEntityHumanBase) entity).familyInfo.male);
			worldObj.removeEntity(entity);
			worldObj.spawnEntityInWorld(infected);
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}
