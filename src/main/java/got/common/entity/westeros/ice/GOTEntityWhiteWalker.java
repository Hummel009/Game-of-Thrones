package got.common.entity.westeros.ice;

import got.GOT;
import got.common.GOTConfig;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.IceUtils;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityWhiteWalker extends GOTEntityNPC implements GOTBiome.ImmuneToFrost {
	private static final ItemStack[] WEAPONS = {new ItemStack(GOTItems.iceSword), new ItemStack(GOTItems.iceHeavySword), new ItemStack(GOTItems.iceSpear)};

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWhiteWalker(World world) {
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
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		spawnsInDarkness = true;
		isImmuneToFire = true;
		faction = GOTFaction.WHITE_WALKER;
		alignmentBonus = 5.0f;
		killAchievement = GOTAchievement.killWhiteWalker;
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
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
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, GOTConfig.walkerFireDamage);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		if (rand.nextFloat() <= 0.525f) {
			dropItem(GOTItems.iceShard, rand.nextInt(2) + 1);
		}
	}

	@Override
	public String getDeathSound() {
		return "got:walker.death";
	}

	@Override
	public String getHurtSound() {
		return "got:walker.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:walker.say";
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (motionX * motionX + motionY * motionY + motionZ * motionZ >= 0.01) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			double d1 = boundingBox.minY + MathHelper.randomFloatClamp(rand, 0.2f, 0.7f) * height;
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			GOT.proxy.spawnParticle("chill", d, d1, d2, -motionX * 0.5, 0.0, -motionZ * 0.5);
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
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		if (rand.nextInt(6) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(GOTItems.whiteWalkersBoots));
			setCurrentItemOrArmor(2, new ItemStack(GOTItems.whiteWalkersLeggings));
			setCurrentItemOrArmor(3, new ItemStack(GOTItems.whiteWalkersChestplate));
		}

		return livingData;
	}
}