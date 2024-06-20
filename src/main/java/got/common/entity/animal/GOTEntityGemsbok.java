package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.GOTEntityRegistry;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityGemsbok extends EntityAnimal implements GOTBiome.ImmuneToHeat {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGemsbok(World world) {
		super(world);
		setSize(0.9f, 1.4f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.3));
		tasks.addTask(2, new EntityAIMate(this, 1.0));
		tasks.addTask(3, new EntityAITempt(this, 1.2, Items.wheat, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
		tasks.addTask(7, new EntityAILookIdle(this));
	}


	@Override
	public void dropFewItems(boolean flag, int i) {
		int j = 1 + rand.nextInt(4) + rand.nextInt(1 + i);
		for (int k = 0; k < j; ++k) {
			dropItem(GOTItems.gemsbokHide, 1);
		}
		if (rand.nextBoolean()) {
			dropItem(GOTItems.gemsbokHorn, 1);
		}
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new GOTEntityGemsbok(worldObj);
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
		return "got:deer.death";
	}

	protected float getGemsbokSoundPitch() {
		return 0.8f;
	}

	@Override
	public String getHurtSound() {
		return "got:deer.hurt";
	}

	@Override
	public String getLivingSound() {
		return "got:deer.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public float getSoundPitch() {
		return super.getSoundPitch() * getGemsbokSoundPitch();
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}
}