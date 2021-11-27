package got.common.entity.animal;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class GOTEntityScorpionBig extends GOTEntityNPC implements GOTBiome.ImmuneToHeat {
	public float scorpionWidth = -1.0f;
	public float scorpionHeight;

	public GOTEntityScorpionBig(World world) {
		super(world);
		canBeMarried = false;
		setSize(1.2f, 0.9f);
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
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + getScorpionScale() * 6.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - getScorpionScale() * 0.05);
		getEntityAttribute(npcAttackDamage).setBaseValue(2.0 + getScorpionScale());
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (!worldObj.isRemote) {
				setStrikeTime(20);
			}
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int k = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int j = 0; j < k; ++j) {
			dropItem(Items.rotten_flesh, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) getRandomScorpionScale());
		dataWatcher.addObject(19, 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("mob.spider.step", 0.15f, 1.0f);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			if (j > 62 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock) {
				return true;
			}
		}
		return false;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public String getDeathSound() {
		return "mob.spider.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public String getHurtSound() {
		return "mob.spider.say";
	}

	@Override
	public String getLivingSound() {
		return "mob.spider.say";
	}

	public int getRandomScorpionScale() {
		return rand.nextInt(3);
	}

	public int getScorpionScale() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public float getScorpionScaleAmount() {
		return 0.5f + getScorpionScale() / 2.0f;
	}

	public int getStrikeTime() {
		return dataWatcher.getWatchableObjectInt(19);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.glass_bottle) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(GOTRegistry.bottlePoison));
			} else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(GOTRegistry.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
				entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(GOTRegistry.bottlePoison), false);
			}
			return true;
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		if (effect.getPotionID() == Potion.poison.id) {
			return false;
		}
		return super.isPotionApplicable(effect);
	}

	@Override
	public void onLivingUpdate() {
		int i;
		super.onLivingUpdate();
		rescaleScorpion(getScorpionScaleAmount());
		if (!worldObj.isRemote && (i = getStrikeTime()) > 0) {
			setStrikeTime(i - 1);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setScorpionScale(nbt.getByte("ScorpionScale"));
		getEntityAttribute(npcAttackDamage).setBaseValue(2.0 + getScorpionScale());
	}

	public void rescaleScorpion(float f) {
		super.setSize(scorpionWidth * f, scorpionHeight * f);
	}

	public void setScorpionScale(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	@Override
	public void setSize(float f, float f1) {
		boolean flag = scorpionWidth > 0.0f;
		scorpionWidth = f;
		scorpionHeight = f1;
		if (!flag) {
			rescaleScorpion(1.0f);
		}
	}

	public void setStrikeTime(int i) {
		dataWatcher.updateObject(19, i);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("ScorpionScale", (byte) getScorpionScale());
	}

}
