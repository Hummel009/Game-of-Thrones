package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityEdmureTully extends GOTEntityHumanBase {
	public EntityAIBase rangedAttackAI = createRangedAttackAI();
	public EntityAIBase meleeAttackAI;

	public GOTEntityEdmureTully(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, createMeleeAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.WILD, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.WILD_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		isImmuneToFrost = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
	}

	public EntityAIBase createMeleeAttackAI() {
		meleeAttackAI = new GOTEntityAIAttackOnCollide(this, 1.4, true);
		return meleeAttackAI;
	}

	public EntityAIBase createRangedAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.25, 20, 40, 40.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.arrowFire, 64);
	}

	@Override
	public float getAlignmentBonus() {
		return 200.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public float getFireArrowChance() {
		return 1.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killEdmureTully;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		}
		if (mode == GOTEntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == GOTEntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosDaggerPoisoned));
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.westerosBow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}