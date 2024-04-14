package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityRegistry;
import got.common.util.GOTCrashHandler;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityFlamingo extends EntityAnimal {
	private float destPos;
	private float coef1;
	private float coef2;
	private float coef3;

	private float coef4 = 5.0f;

	@SuppressWarnings("WeakerAccess")
	public GOTEntityFlamingo(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.3));
		tasks.addTask(2, new EntityAIMate(this, 1.0));
		tasks.addTask(3, new EntityAITempt(this, 1.2, Items.fish, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.2));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float f) {
		boolean flag = super.attackEntityFrom(source, f);
		if (flag && !worldObj.isRemote && getFishingTickCur() > 20) {
			setFishingTick(20, 20);
		}
		return flag;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new GOTEntityFlamingo(worldObj);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
	}

	@Override
	public void fall(float f) {
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
		return "got:flamingo.death";
	}

	@Override
	public Item getDropItem() {
		return Items.feather;
	}

	private int getFishingTick() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public int getFishingTickCur() {
		return getFishingTick() & 0xFFFF;
	}

	public int getFishingTickPre() {
		return getFishingTick() >> 16;
	}

	@Override
	public String getHurtSound() {
		return "got:flamingo.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:flamingo.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.getItem() == Items.fish;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		coef1 = coef2;
		coef3 = destPos;
		destPos = (float) (destPos + (onGround || inWater ? -1 : 4) * 0.3);
		if (destPos < 0.0f) {
			destPos = 0.0f;
		}
		if (destPos > 1.0f) {
			destPos = 1.0f;
		}
		if (!onGround && !inWater && coef4 < 1.0f) {
			coef4 = 1.0f;
		}
		coef4 = (float) (coef4 * 0.9);
		if (!onGround && !inWater && motionY < 0.0) {
			motionY *= 0.6;
		}
		coef2 += coef4 * 2.0f;
		if (!worldObj.isRemote && !isChild() && !isInLove() && getFishingTickCur() == 0 && rand.nextInt(600) == 0 && worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY), MathHelper.floor_double(posZ)) == Blocks.water) {
			setFishingTick(200, 200);
		}
		if (getFishingTickCur() > 0) {
			if (worldObj.isRemote) {
				for (int i = 0; i < 3; ++i) {
					double d = posX + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					double d1 = boundingBox.minY + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					double d2 = posZ + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					worldObj.spawnParticle("bubble", d, d1, d2, 0.0, 0.0, 0.0);
				}
			} else {
				int cur = getFishingTickCur();
				setFishingTick(cur, cur - 1);
			}
		}
		if (!worldObj.isRemote && isInLove() && getFishingTickCur() > 20) {
			setFishingTick(20, 20);
		}
	}

	private void setFishingTick(int pre, int cur) {
		int i = pre << 16 | cur & 0xFFFF;
		dataWatcher.updateObject(16, i);
	}

	public float getCoef1() {
		return coef1;
	}

	public float getCoef2() {
		return coef2;
	}

	public float getCoef3() {
		return coef3;
	}

	public float getDestPos() {
		return destPos;
	}
}