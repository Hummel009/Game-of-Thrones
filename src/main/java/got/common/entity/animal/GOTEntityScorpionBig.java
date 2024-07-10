package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public abstract class GOTEntityScorpionBig extends GOTEntityNPC implements GOTBiome.ImmuneToHeat, IMob {
	private float scorpionWidth = -1.0f;
	private float scorpionHeight;

	protected GOTEntityScorpionBig(World world) {
		super(world);
		setSize(1.2f, 0.9f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		getNavigator().setCanSwim(false);
		addTargetTasks(true);
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
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
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + getScorpionScale() * 6.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - getScorpionScale() * 0.05);
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(2.0 + getScorpionScale());
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
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public String getDeathSound() {
		return "mob.spider.death";
	}

	@Override
	public String getHurtSound() {
		return "mob.spider.say";
	}

	@Override
	public String getLivingSound() {
		return "mob.spider.say";
	}

	private int getRandomScorpionScale() {
		return rand.nextInt(3);
	}

	private int getScorpionScale() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	private void setScorpionScale(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	public float getScorpionScaleAmount() {
		return 0.5f + getScorpionScale() / 2.0f;
	}

	public int getStrikeTime() {
		return dataWatcher.getWatchableObjectInt(19);
	}

	private void setStrikeTime(int i) {
		dataWatcher.updateObject(19, i);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.glass_bottle) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(GOTItems.bottlePoison));
			} else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(GOTItems.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
				entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(GOTItems.bottlePoison), false);
			}
			return true;
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotionID() != Potion.poison.id && super.isPotionApplicable(effect);
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
		getEntityAttribute(NPC_ATTACK_DAMAGE).setBaseValue(2.0 + getScorpionScale());
	}

	private void rescaleScorpion(float f) {
		super.setSize(scorpionWidth * f, scorpionHeight * f);
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

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("ScorpionScale", (byte) getScorpionScale());
	}
}