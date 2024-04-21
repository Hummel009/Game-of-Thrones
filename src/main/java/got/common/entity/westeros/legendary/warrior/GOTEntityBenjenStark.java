package got.common.entity.westeros.legendary.warrior;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
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
		addTargetTasks(true);
		setupLegendaryNPC(true);
		setSize(0.6f, 1.8f);
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
		cape = GOTCapes.NIGHT;
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
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && (itemstack.getItem() == GOTItems.valyrianSword || itemstack.getItem() == GOTItems.iceSword || itemstack.getItem() == GOTItems.iceHeavySword || itemstack.getItem() == GOTItems.sothoryosAxe || itemstack.getItem() == GOTItems.sothoryosBattleaxe || itemstack.getItem() == GOTItems.sothoryosDagger || itemstack.getItem() == GOTItems.sothoryosDaggerPoisoned || itemstack.getItem() == GOTItems.sothoryosHammer || itemstack.getItem() == GOTItems.sothoryosHoe || itemstack.getItem() == GOTItems.sothoryosPickaxe || itemstack.getItem() == GOTItems.sothoryosPike || itemstack.getItem() == GOTItems.sothoryosShovel || itemstack.getItem() == GOTItems.sothoryosSpear || itemstack.getItem() == GOTItems.sothoryosSword || itemstack.getItem() == GOTItems.valyrianAxe || itemstack.getItem() == GOTItems.valyrianBattleaxe || itemstack.getItem() == GOTItems.valyrianDagger || itemstack.getItem() == GOTItems.valyrianDaggerPoisoned || itemstack.getItem() == GOTItems.valyrianHalberd || itemstack.getItem() == GOTItems.valyrianHammer || itemstack.getItem() == GOTItems.valyrianMattock || itemstack.getItem() == GOTItems.valyrianPickaxe || itemstack.getItem() == GOTItems.valyrianShovel || itemstack.getItem() == GOTItems.valyrianSpear || itemstack.getItem() == GOTItems.valyrianHoe || itemstack.getItem() == GOTItems.gregorCleganeSword || itemstack.getItem() == GOTItems.baelishDagger || itemstack.getItem() == GOTItems.blackfyre || itemstack.getItem() == GOTItems.boltonDagger || itemstack.getItem() == GOTItems.brightroar || itemstack.getItem() == GOTItems.celtigarAxe || itemstack.getItem() == GOTItems.darkSister || itemstack.getItem() == GOTItems.euronDagger || itemstack.getItem() == GOTItems.heartsbane || itemstack.getItem() == GOTItems.ice || itemstack.getItem() == GOTItems.justMaid || itemstack.getItem() == GOTItems.ladyForlorn || itemstack.getItem() == GOTItems.lamentation || itemstack.getItem() == GOTItems.lightbringer || itemstack.getItem() == GOTItems.longclaw || itemstack.getItem() == GOTItems.oathkeeper || itemstack.getItem() == GOTItems.nightfall || itemstack.getItem() == GOTItems.orphanMaker || itemstack.getItem() == GOTItems.redRain || itemstack.getItem() == GOTItems.truth || itemstack.getItem() == GOTItems.vigilance || itemstack.getItem() == GOTItems.widowWail || itemstack.getItem() == GOTItems.blackArakh || itemstack.getItem() == GOTItems.crowbar)) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.valyrianSword, 1);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.NIGHT_WATCH;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killBenjenStark;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/benjen_friendly";
		}
		return "standard/civilized/usual_hostile";
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.valyrianSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	public GOTEntityNPC setIsRider(boolean is) {
		spawnRidingHorse = is;
		return this;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}