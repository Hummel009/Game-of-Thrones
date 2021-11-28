package got.common.entity.essos.legendary.warrior;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGreyWorm extends GOTEntityHumanBase implements GOTMercenary {
	public GOTEntityGreyWorm(World world) {
		super(world);
		canBeMarried = false;
		npcShield = GOTShields.UNSULLIED;
		addTargetTasks(true);
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.ESSOS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.ESSOS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(npcAttackDamageExtra).setBaseValue(14.0);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTRegistry.unsulliedBoots, 1);
		dropItem(GOTRegistry.unsulliedLeggings, 1);
		dropItem(GOTRegistry.unsulliedChestplate, 1);
		dropItem(GOTRegistry.unsulliedHelmet, 1);
	}

	@Override
	public float getAlignmentBonus() {
		return 0.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.UNALIGNED;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_UNSULLIED;
	}

	@Override
	public float getMercAlignmentRequired() {
		return 0.0f;
	}

	@Override
	public int getMercBaseCost() {
		return 5000;
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.unsulliedBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.unsulliedLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.unsulliedChestplate));
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.TRADE);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}