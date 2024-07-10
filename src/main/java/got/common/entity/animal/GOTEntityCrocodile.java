package got.common.entity.animal;

import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class GOTEntityCrocodile extends GOTEntityNPC implements GOTBiome.ImmuneToFrost, IMob {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrocodile(World world) {
		super(world);
		setSize(2.1f, 0.7f);
		addTargetTasks(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	@Override
	public boolean isSpawnsInDarkness() {
		return true;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(4.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (!worldObj.isRemote) {
				setSnapTime(20);
			}
			worldObj.playSoundAtEntity(this, "got:crocodile.snap", getSoundVolume(), getSoundPitch());
			return true;
		}
		return false;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int count = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int j = 0; j < count; ++j) {
			int drop = rand.nextInt(3);
			switch (drop) {
				case 0:
					dropItem(Items.bone, 1);
					continue;
				case 1:
					dropItem(Items.fish, 1);
					continue;
				case 2:
					dropItem(Items.leather, 1);
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, 0);
	}

	@Override
	public String getDeathSound() {
		return "got:crocodile.death";
	}

	@Override
	public String getLivingSound() {
		return "got:crocodile.say";
	}

	public int getSnapTime() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	private void setSnapTime(int i) {
		dataWatcher.updateObject(20, i);
	}

	@Override
	public void moveEntityWithHeading(float f, float f1) {
		if (!worldObj.isRemote && isInWater() && getAttackTarget() != null) {
			moveFlying(f, f1, 0.1f);
		}
		super.moveEntityWithHeading(f, f1);
	}

	@Override
	public void onLivingUpdate() {
		int i;
		super.onLivingUpdate();
		if (!worldObj.isRemote && isInWater()) {
			motionY += 0.02;
		}
		if (!worldObj.isRemote && (i = getSnapTime()) > 0) {
			setSnapTime(i - 1);
		}
	}
}