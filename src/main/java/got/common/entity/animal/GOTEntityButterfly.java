package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityRegistry;
import got.common.entity.other.GOTRandomSkinEntity;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeQohorForest;
import got.common.world.biome.essos.GOTBiomeVolantisOrangeForest;
import got.common.world.biome.sothoryos.GOTBiomeSummerIslands;
import got.common.world.biome.ulthos.GOTBiomeUlthosForest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

public class GOTEntityButterfly extends EntityLiving implements GOTAmbientCreature, GOTRandomSkinEntity, GOTBiome.ImmuneToFrost {
	public ChunkCoordinates currentFlightTarget;
	public int flapTime;

	public GOTEntityButterfly(World world) {
		super(world);
		setSize(0.5f, 0.5f);
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(rand, 0.08, 0.12));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean flag = super.attackEntityFrom(damagesource, f);
		if (flag && !worldObj.isRemote && isButterflyStill()) {
			setButterflyStill(false);
		}
		return flag;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void collideWithEntity(Entity entity) {
	}

	@Override
	public void collideWithNearbyEntities() {
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void fall(float f) {
	}

	public ButterflyType getButterflyType() {
		byte i = dataWatcher.getWatchableObjectByte(16);
		if (i < 0 || i >= ButterflyType.values().length) {
			i = 0;
		}
		return ButterflyType.values()[i];
	}

	public void setButterflyType(ButterflyType type) {
		setButterflyType(type.ordinal());
	}

	public void setButterflyType(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && GOTAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.spawnEgg, 1, GOTEntityRegistry.getEntityID(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	public boolean isButterflyStill() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setButterflyStill(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(posX);
		int k = MathHelper.floor_double(posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiomeUlthosForest) {
			setButterflyType(ButterflyType.ULTHOS);
		} else if (biome instanceof GOTBiomeQohorForest || biome instanceof GOTBiomeVolantisOrangeForest) {
			setButterflyType(ButterflyType.QOHOR);
		} else if (biome instanceof GOTBiomeSummerIslands) {
			setButterflyType(ButterflyType.SOTHORYOS);
		} else {
			setButterflyType(ButterflyType.COMMON);
		}
		return data1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isButterflyStill()) {
			motionZ = 0.0;
			motionY = 0.0;
			motionX = 0.0;
			posY = MathHelper.floor_double(posY);
			if (worldObj.isRemote) {
				if (rand.nextInt(200) == 0) {
					flapTime = 40;
				}
				if (flapTime > 0) {
					--flapTime;
				}
			}
		} else {
			motionY *= 0.6;
			if (worldObj.isRemote) {
				flapTime = 0;
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setButterflyType(nbt.getInteger("ButterflyType"));
		setButterflyStill(nbt.getBoolean("ButterflyStill"));
	}

	@Override
	public void setUniqueID(UUID uuid) {
		entityUniqueID = uuid;
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (isButterflyStill()) {
			int k;
			int j;
			int i = MathHelper.floor_double(posX);
			if (!worldObj.getBlock(i, j = (int) posY - 1, k = MathHelper.floor_double(posZ)).isSideSolid(worldObj, i, j, k, ForgeDirection.UP) || rand.nextInt(400) == 0 || worldObj.getClosestPlayerToEntity(this, 3.0) != null) {
				setButterflyStill(false);
			}
		} else {
			if (currentFlightTarget != null && (!worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ) || currentFlightTarget.posY < 1)) {
				currentFlightTarget = null;
			}
			if (currentFlightTarget == null || rand.nextInt(30) == 0 || currentFlightTarget.getDistanceSquared((int) posX, (int) posY, (int) posZ) < 4.0f) {
				currentFlightTarget = new ChunkCoordinates((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
			}
			double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
			double d0 = currentFlightTarget.posX + 0.5 - posX;
			double d1 = currentFlightTarget.posY + 0.5 - posY;
			double d2 = currentFlightTarget.posZ + 0.5 - posZ;
			motionX += (Math.signum(d0) * 0.5 - motionX) * speed;
			motionY += (Math.signum(d1) * 0.7 - motionY) * speed;
			motionZ += (Math.signum(d2) * 0.5 - motionZ) * speed;
			float f = (float) (Math.atan2(motionZ, motionX) * 180.0 / 3.141592653589793) - 90.0f;
			float f1 = MathHelper.wrapAngleTo180_float(f - rotationYaw);
			moveForward = 0.5f;
			rotationYaw += f1;
			if (rand.nextInt(150) == 0 && worldObj.getBlock(MathHelper.floor_double(posX), (int) posY - 1, MathHelper.floor_double(posZ)).isNormalCube()) {
				setButterflyStill(true);
			}
		}
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("ButterflyType", getButterflyType().ordinal());
		nbt.setBoolean("ButterflyStill", isButterflyStill());
	}

	public enum ButterflyType {
		ULTHOS("ulthos"), QOHOR("qohor"), COMMON("common"), SOTHORYOS("sothoryos");

		public String textureDir;

		ButterflyType(String s) {
			textureDir = s;
		}
	}

}
