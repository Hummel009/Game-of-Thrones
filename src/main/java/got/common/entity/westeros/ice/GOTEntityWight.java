package got.common.entity.westeros.ice;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityWight extends GOTEntityHumanBase implements GOTBiome.ImmuneToFrost {
	private static final ItemStack[] WEAPONS = {new ItemStack(GOTItems.wildlingAxe), new ItemStack(GOTItems.wildlingBattleaxe), new ItemStack(GOTItems.wildlingDagger), new ItemStack(GOTItems.wildlingDaggerPoisoned), new ItemStack(GOTItems.wildlingHammer), new ItemStack(GOTItems.wildlingPolearm), new ItemStack(GOTItems.wildlingSword), new ItemStack(GOTItems.wildlingSword), new ItemStack(GOTItems.wildlingSword), new ItemStack(GOTItems.wildlingSword)};
	private static final ItemStack[] SPEARS = {new ItemStack(GOTItems.wildlingSpear)};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWight(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityWhiteWalker.class, 5.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, GOTEntityWight.class, 5.0f, 0.02f));
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return IceUtils.attackWithFrost(entity, super.attackEntityAsMob(entity));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, true);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public String getDeathSound() {
		return "mob.zombie.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public String getHurtSound() {
		return "mob.zombie.hurt";
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killWight;
	}

	@Override
	public String getLivingSound() {
		return "mob.zombie.say";
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		IceUtils.createNewWight(this, entity, worldObj);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData livingData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(WEAPONS.length);
		npcItemsInv.setMeleeWeapon(WEAPONS[i].copy());
		if (rand.nextInt(8) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			i = rand.nextInt(SPEARS.length);
			npcItemsInv.setMeleeWeapon(SPEARS[i].copy());
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.furChestplate));
		setCurrentItemOrArmor(4, null);
		return livingData;
	}
}