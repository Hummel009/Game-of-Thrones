package got.common.entity.other;

import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityGiantBase extends GOTEntityNPC {
	public GOTEntityGiantBase(World world) {
		super(world);
		canBeMarried = false;
		setSize(1.6f * 1.6f, 3.2f * 1.6f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(4, getGiantAttackAI());
		tasks.addTask(5, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(6, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
		isImmuneToFrost = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(npcAttackDamage).setBaseValue(5.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			float attackDamage = (float) getEntityAttribute(GOTEntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(Items.beef, 10);
		dropItem(GOTItems.fur, 10);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("got:giant.step", 0.75f, getSoundPitch());
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public String getDeathSound() {
		return "got:giant.say";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WILDLING;
	}

	public EntityAIBase getGiantAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public String getHurtSound() {
		return "got:giant.say";
	}

	@Override
	public String getLivingSound() {
		return "got:giant.say";
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public float getSoundVolume() {
		return 1.5f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standard/special/giant_friendly";
		}
		return "standard/special/giant_hostile";
	}

	@Override
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 2.0;
		motionY /= 2.0;
		motionZ /= 2.0;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getGiantName(rand));
	}
}