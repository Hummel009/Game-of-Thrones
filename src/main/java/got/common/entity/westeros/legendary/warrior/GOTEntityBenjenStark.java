package got.common.entity.westeros.legendary.warrior;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityBenjenStark extends GOTEntityHumanBase {
	public GOTEntityBenjenStark(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		isImmuneToFrost = true;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		spawnRidingHorse = true;
		npcCape = GOTCapes.NIGHT;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		ItemStack itemstack;
		Entity entity = damagesource.getEntity();
		if (damagesource.isFireDamage()) {
			return super.attackEntityFrom(damagesource, f);
		}
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && (itemstack.getItem() == GOTRegistry.valyrianSword || itemstack.getItem() == GOTRegistry.iceSword || itemstack.getItem() == GOTRegistry.iceHeavySword || itemstack.getItem() == GOTRegistry.sothoryosAxe || itemstack.getItem() == GOTRegistry.sothoryosBattleaxe || itemstack.getItem() == GOTRegistry.sothoryosDagger || itemstack.getItem() == GOTRegistry.sothoryosDaggerPoisoned || itemstack.getItem() == GOTRegistry.sothoryosHammer || itemstack.getItem() == GOTRegistry.sothoryosHoe || itemstack.getItem() == GOTRegistry.sothoryosPickaxe || itemstack.getItem() == GOTRegistry.sothoryosPike || itemstack.getItem() == GOTRegistry.sothoryosShovel || itemstack.getItem() == GOTRegistry.sothoryosSpear || itemstack.getItem() == GOTRegistry.sothoryosSword || itemstack.getItem() == GOTRegistry.valyrianAxe || itemstack.getItem() == GOTRegistry.valyrianBattleaxe || itemstack.getItem() == GOTRegistry.valyrianDagger || itemstack.getItem() == GOTRegistry.valyrianDaggerPoisoned || itemstack.getItem() == GOTRegistry.valyrianHalberd || itemstack.getItem() == GOTRegistry.valyrianHammer || itemstack.getItem() == GOTRegistry.valyrianMattock || itemstack.getItem() == GOTRegistry.valyrianPickaxe || itemstack.getItem() == GOTRegistry.valyrianShovel || itemstack.getItem() == GOTRegistry.valyrianSpear || itemstack.getItem() == GOTRegistry.valyrianHoe || itemstack.getItem() == GOTRegistry.gregorCleganeSword || itemstack.getItem() == GOTRegistry.baelishDagger || itemstack.getItem() == GOTRegistry.blackfyreSword || itemstack.getItem() == GOTRegistry.boltonDagger || itemstack.getItem() == GOTRegistry.brightroar || itemstack.getItem() == GOTRegistry.celtigarAxe || itemstack.getItem() == GOTRegistry.darkSister || itemstack.getItem() == GOTRegistry.euronDagger || itemstack.getItem() == GOTRegistry.heartsbane || itemstack.getItem() == GOTRegistry.ice || itemstack.getItem() == GOTRegistry.justMaid || itemstack.getItem() == GOTRegistry.ladyForlorn || itemstack.getItem() == GOTRegistry.lamentation || itemstack.getItem() == GOTRegistry.lightbringer || itemstack.getItem() == GOTRegistry.longclaw || itemstack.getItem() == GOTRegistry.oathkeeper || itemstack.getItem() == GOTRegistry.nightfall || itemstack.getItem() == GOTRegistry.orphanMaker || itemstack.getItem() == GOTRegistry.redRain || itemstack.getItem() == GOTRegistry.truth || itemstack.getItem() == GOTRegistry.vigilance || itemstack.getItem() == GOTRegistry.widowWail || itemstack.getItem() == GOTRegistry.blackArakh || itemstack.getItem() == GOTRegistry.crowbar)) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTRegistry.valyrianSword, 1);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.NIGHT_WATCH;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_BENJEN_STARK;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/benjen_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 18;
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.valyrianSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
