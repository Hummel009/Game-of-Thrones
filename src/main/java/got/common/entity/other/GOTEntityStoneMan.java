package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.faction.GOTFaction;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityStoneMan extends GOTEntityNPC {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityStoneMan(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(5.0);
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
		return 2.0f;
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
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killStoneMan;
	}

	@Override
	public String getLivingSound() {
		return "mob.zombie.say";
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		if (entity instanceof GOTEntityHumanBase) {
			GOTEntityHumanBase target = (GOTEntityHumanBase) entity;
			GOTEntityStoneMan stoneMan = new GOTEntityStoneMan(worldObj);
			stoneMan.setCape(target.getCape());
			stoneMan.getFamilyInfo().setAge(target.getFamilyInfo().getAge());
			stoneMan.getFamilyInfo().setMale(target.getFamilyInfo().isMale());
			stoneMan.getNpcItemsInv().setMeleeWeapon(target.getNpcItemsInv().getMeleeWeapon());
			stoneMan.getNpcItemsInv().setIdleItem(target.getNpcItemsInv().getMeleeWeapon());
			stoneMan.setCurrentItemOrArmor(1, target.getEquipmentInSlot(1));
			stoneMan.setCurrentItemOrArmor(2, target.getEquipmentInSlot(2));
			stoneMan.setCurrentItemOrArmor(3, target.getEquipmentInSlot(3));
			stoneMan.setCurrentItemOrArmor(4, target.getEquipmentInSlot(4));
			worldObj.removeEntity(entity);
			worldObj.spawnEntityInWorld(stoneMan);
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}