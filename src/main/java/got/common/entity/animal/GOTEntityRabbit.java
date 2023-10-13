package got.common.entity.animal;

import java.util.List;
import java.util.UUID;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAvoidWithChance;
import got.common.entity.ai.GOTEntityAIFlee;
import got.common.entity.ai.GOTEntityAIRabbitEatCrops;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRegistry;
import got.common.entity.other.GOTFarmhand;
import got.common.entity.other.GOTRandomSkinEntity;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityRabbit extends EntityCreature implements GOTAmbientCreature, GOTRandomSkinEntity, GOTBiome.ImmuneToFrost {
	public static String fleeSound = "got:rabbit.flee";

	public GOTEntityRabbit(World world) {
		super(world);
		setSize(0.5f, 0.5f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIFlee(this, 2.0));
		tasks.addTask(2, new GOTEntityAIAvoidWithChance(this, EntityPlayer.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
		tasks.addTask(2, new GOTEntityAIAvoidWithChance(this, GOTEntityNPC.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
		tasks.addTask(3, new GOTEntityAIRabbitEatCrops(this, 1.2));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0f, 0.05f));
		tasks.addTask(6, new EntityAILookIdle(this));
	}

	public boolean anyFarmhandsNearby(int i, int j, int k) {
		int range = 16;
		List<? extends GOTFarmhand> farmhands = worldObj.getEntitiesWithinAABB(GOTFarmhand.class, AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range));
		return !farmhands.isEmpty();
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int meat = rand.nextInt(3) + rand.nextInt(1 + i);
		for (int l = 0; l < meat; ++l) {
			if (isBurning()) {
				dropItem(GOTItems.rabbitCooked, 1);
				continue;
			}
			dropItem(GOTItems.rabbitRaw, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		Block block = worldObj.getBlock(i, j - 1, k);
		if (block == Blocks.grass) {
			return 10.0f;
		}
		return worldObj.getLightBrightness(i, j, k) - 0.5f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "got:rabbit.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 1 + rand.nextInt(2);
	}

	@Override
	public String getHurtSound() {
		return "got:rabbit.hurt";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public int getTalkInterval() {
		return 200;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isRabbitEating() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setRabbitEating(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}
}
