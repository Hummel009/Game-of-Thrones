package got.common.entity.animal;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityWyvern extends GOTEntityNPC implements GOTBiome.ImmuneToHeat {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWyvern(World world) {
		super(world);
		setSize(2.85f, 2.55f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		getNavigator().setCanSwim(false);
		addTargetTasks(true);
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		spawnsInDarkness = true;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killWyvern);
		}
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(6.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (!worldObj.isRemote) {
				setSnapTime(20);
			}
			worldObj.playSoundAtEntity(this, "got:crocodile.snap", getSoundVolume(), getSoundPitch());
			float attackDamage = (float) getEntityAttribute(NPC_ATTACK_DAMAGE).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int count = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int j = 0; j < count; ++j) {
			int drop = rand.nextInt(5);
			switch (drop) {
				case 0:
					dropItem(Items.bone, 1);
					continue;
				case 1:
					dropItem(Items.fish, 1);
					continue;
				case 2:
					dropItem(Items.leather, 1);
					continue;
				case 3:
					dropItem(GOTItems.zebraRaw, 1);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, 0);
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
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
	public String getLivingSound() {
		return "got:crocodile.say";
	}

	private int getSnapTime() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	private void setSnapTime(int i) {
		dataWatcher.updateObject(20, i);
	}

	@Override
	public void onLivingUpdate() {
		int i;
		super.onLivingUpdate();
		if (!worldObj.isRemote && (i = getSnapTime()) > 0) {
			setSnapTime(i - 1);
		}
	}
}