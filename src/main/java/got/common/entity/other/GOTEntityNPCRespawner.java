package got.common.entity.other;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTItems;
import got.common.faction.GOTFaction;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketNPCRespawner;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class GOTEntityNPCRespawner extends Entity {
	private Class<? extends Entity> spawnClass1;
	private Class<? extends Entity> spawnClass2;

	private boolean setHomePosFromSpawn;

	private float prevSpawnerSpin;
	private float spawnerSpin;

	private int blockEnemySpawns;
	private int checkHorizontalRange = 8;
	private int checkVerticalMax = 4;
	private int checkVerticalMin = -4;
	private int homeRange = -1;
	private int mountSetting;
	private int noPlayerRange = 24;
	private int spawnCap = 4;
	private int spawnHorizontalRange = 4;
	private int spawnInterval = 3600;
	private int spawnVerticalMax = 2;
	private int spawnVerticalMin = -2;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNPCRespawner(World world) {
		super(world);
		setSize(1.0f, 1.0f);
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	public static boolean isSpawnBlocked(Entity entity, GOTFaction spawnFaction) {
		int k;
		int j;
		int range;
		World world = entity.worldObj;
		int i = MathHelper.floor_double(entity.posX);
		AxisAlignedBB originBB = AxisAlignedBB.getBoundingBox(i, j = MathHelper.floor_double(entity.boundingBox.minY), k = MathHelper.floor_double(entity.posZ), i + 1, j + 1, k + 1);
		AxisAlignedBB searchBB = originBB.expand(range = 64, range, range);
		List<GOTEntityNPCRespawner> spawners = world.getEntitiesWithinAABB(GOTEntityNPCRespawner.class, searchBB);
		if (!spawners.isEmpty()) {
			for (GOTEntityNPCRespawner obj : spawners) {
				AxisAlignedBB spawnBlockBB;
				if (obj.blockEnemySpawns <= 0 || !(spawnBlockBB = obj.createSpawnBlockRegion()).intersectsWith(searchBB) || !spawnBlockBB.intersectsWith(originBB) || !obj.isEnemySpawnBlocked(spawnFaction)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean isSpawnBlocked(GOTEntityNPC npc) {
		return isSpawnBlocked(npc, npc.getFaction());
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	@Override
	public boolean canBeCollidedWith() {
		if (!worldObj.isRemote) {
			return false;
		}
		EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
		return entityplayer != null && entityplayer.capabilities.isCreativeMode;
	}

	private AxisAlignedBB createSpawnBlockRegion() {
		if (blockEnemySpawns <= 0) {
			return null;
		}
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		int range = blockEnemySpawns;
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
	}

	@Override
	public void entityInit() {
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(GOTItems.npcRespawner);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int l = 0; l < 16; ++l) {
				double d = posX + (rand.nextDouble() - 0.5) * width;
				double d1 = posY + rand.nextDouble() * height;
				double d2 = posZ + (rand.nextDouble() - 0.5) * width;
				worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(GOTItems.npcRespawner), d, d1, d2, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				IMessage packet = new GOTPacketNPCRespawner(this);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
			return true;
		}
		return false;
	}

	private boolean isEnemySpawnBlocked(GOTFaction spawnFaction) {
		GOTFaction faction1;
		GOTFaction faction2;
		return spawnClass1 != null && (faction1 = ((GOTEntityNPC) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(spawnClass1), worldObj)).getFaction()) != null && faction1.isBadRelation(spawnFaction) || spawnClass2 != null && (faction2 = ((GOTEntityNPC) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(spawnClass2), worldObj)).getFaction()) != null && faction2.isBadRelation(spawnFaction);
	}

	@Override
	public boolean isInvisible() {
		if (!worldObj.isRemote) {
			return super.isInvisible();
		}
		EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
		return entityplayer == null || !entityplayer.capabilities.isCreativeMode;
	}

	public void onBreak() {
		worldObj.playSoundAtEntity(this, Blocks.glass.stepSound.getBreakSound(), (Blocks.glass.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.glass.stepSound.getPitch() * 0.8f);
		worldObj.setEntityState(this, (byte) 16);
		setDead();
	}

	@Override
	public void onUpdate() {
		int maxY;
		int maxX;
		int i;
		int minX;
		int k;
		int j;
		int minZ;
		int minY;
		int entities;
		int maxZ;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevSpawnerSpin = spawnerSpin;
		spawnerSpin += 6.0f;
		prevSpawnerSpin = MathHelper.wrapAngleTo180_float(prevSpawnerSpin);
		spawnerSpin = MathHelper.wrapAngleTo180_float(spawnerSpin);
		motionX = 0.0;
		motionY = 0.0;
		motionZ = 0.0;
		moveEntity(motionX, motionY, motionZ);
		if (!worldObj.isRemote && ticksExisted % spawnInterval == 0 && (spawnClass1 != null || spawnClass2 != null) && worldObj.checkChunksExist(minX = (i = MathHelper.floor_double(posX)) - checkHorizontalRange, minY = (j = MathHelper.floor_double(boundingBox.minY)) + checkVerticalMin, minZ = (k = MathHelper.floor_double(posZ)) - checkHorizontalRange, maxX = i + checkHorizontalRange, maxY = j + checkVerticalMax, maxZ = k + checkHorizontalRange) && worldObj.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, noPlayerRange) == null && (entities = worldObj.selectEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1), new EntitySelectorImpl(this)).size()) < spawnCap) {
			int attempts = 16;
			for (int l = 0; l < attempts; ++l) {
				int spawnX = i + MathHelper.getRandomIntegerInRange(rand, -spawnHorizontalRange, spawnHorizontalRange);
				int spawnY = j + MathHelper.getRandomIntegerInRange(rand, spawnVerticalMin, spawnVerticalMax);
				int spawnZ = k + MathHelper.getRandomIntegerInRange(rand, -spawnHorizontalRange, spawnHorizontalRange);
				Block belowBlock = worldObj.getBlock(spawnX, spawnY - 1, spawnZ);
				worldObj.getBlockMetadata(spawnX, spawnY - 1, spawnZ);
				boolean belowSolid = belowBlock.isSideSolid(worldObj, spawnX, spawnY - 1, spawnZ, ForgeDirection.UP);
				if (!belowSolid || worldObj.getBlock(spawnX, spawnY, spawnZ).isNormalCube() || worldObj.getBlock(spawnX, spawnY + 1, spawnZ).isNormalCube()) {
					continue;
				}
				Class<? extends Entity> entityClass = null;
				if (spawnClass1 != null && spawnClass2 != null) {
					entityClass = rand.nextInt(3) == 0 ? spawnClass2 : spawnClass1;
				} else if (spawnClass1 != null) {
					entityClass = spawnClass1;
				} else if (spawnClass2 != null) {
					entityClass = spawnClass2;
				}
				String entityName = GOTEntityRegistry.getStringFromClass(entityClass);
				GOTEntityNPC entity = (GOTEntityNPC) EntityList.createEntityByName(entityName, worldObj);
				entity.setLocationAndAngles(spawnX + 0.5, spawnY, spawnZ + 0.5, rand.nextFloat() * 360.0f, 0.0f);
				entity.setNPCPersistent(true);
				entity.setLiftSpawnRestrictions(true);
				if (!entity.getCanSpawnHere()) {
					continue;
				}
				entity.setLiftSpawnRestrictions(false);
				worldObj.spawnEntityInWorld(entity);
				if (mountSetting == 0) {
					entity.setSpawnRidingHorse(false);
				} else if (mountSetting == 1) {
					entity.setSpawnRidingHorse(true);
				}
				entity.onSpawnWithEgg(null);
				if (homeRange >= 0) {
					if (setHomePosFromSpawn) {
						entity.setHomeArea(spawnX, spawnY, spawnZ, homeRange);
					} else {
						entity.setHomeArea(i, j, k, homeRange);
					}
				} else {
					entity.detachHome();
				}
				entities++;
				if (entities >= spawnCap) {
					break;
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		readSpawnerDataFromNBT(nbt);
	}

	public void readSpawnerDataFromNBT(NBTTagCompound nbt) {
		spawnInterval = nbt.getInteger("SpawnInterval");
		if (spawnInterval <= 0) {
			spawnInterval = 3600;
		}
		noPlayerRange = nbt.getByte("NoPlayerRange");
		spawnClass1 = GOTEntityRegistry.getClassFromString(nbt.getString("SpawnClass1"));
		spawnClass2 = GOTEntityRegistry.getClassFromString(nbt.getString("SpawnClass2"));
		if (spawnClass1 != null && !GOTEntityNPC.class.isAssignableFrom(spawnClass1)) {
			spawnClass1 = null;
		}
		if (spawnClass2 != null && !GOTEntityNPC.class.isAssignableFrom(spawnClass2)) {
			spawnClass2 = null;
		}
		checkHorizontalRange = nbt.getByte("CheckHorizontal");
		checkVerticalMin = nbt.getByte("CheckVerticalMin");
		checkVerticalMax = nbt.getByte("CheckVerticalMax");
		spawnCap = nbt.getByte("SpawnCap");
		spawnHorizontalRange = nbt.getByte("SpawnHorizontal");
		spawnVerticalMin = nbt.getByte("SpawnVerticalMin");
		spawnVerticalMax = nbt.getByte("SpawnVerticalMax");
		homeRange = nbt.getByte("HomeRange");
		setHomePosFromSpawn = nbt.getBoolean("HomeSpawn");
		mountSetting = nbt.getByte("MountSetting");
		blockEnemySpawns = nbt.getByte("BlockEnemy");
	}

	public void toggleMountSetting() {
		mountSetting = mountSetting == 0 ? 1 : mountSetting == 1 ? 2 : 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		writeSpawnerDataToNBT(nbt);
	}

	public void writeSpawnerDataToNBT(NBTTagCompound nbt) {
		nbt.setInteger("SpawnInterval", spawnInterval);
		nbt.setByte("NoPlayerRange", (byte) noPlayerRange);
		String class1String = "";
		String class2String = "";
		if (spawnClass1 != null) {
			class1String = GOTEntityRegistry.getStringFromClass(spawnClass1);
		}
		if (spawnClass2 != null) {
			class2String = GOTEntityRegistry.getStringFromClass(spawnClass2);
		}
		nbt.setString("SpawnClass1", class1String == null ? "" : class1String);
		nbt.setString("SpawnClass2", class2String == null ? "" : class2String);
		nbt.setByte("CheckHorizontal", (byte) checkHorizontalRange);
		nbt.setByte("CheckVerticalMin", (byte) checkVerticalMin);
		nbt.setByte("CheckVerticalMax", (byte) checkVerticalMax);
		nbt.setByte("SpawnCap", (byte) spawnCap);
		nbt.setByte("SpawnHorizontal", (byte) spawnHorizontalRange);
		nbt.setByte("SpawnVerticalMin", (byte) spawnVerticalMin);
		nbt.setByte("SpawnVerticalMax", (byte) spawnVerticalMax);
		nbt.setByte("HomeRange", (byte) homeRange);
		nbt.setBoolean("HomeSpawn", setHomePosFromSpawn);
		nbt.setByte("MountSetting", (byte) mountSetting);
		nbt.setByte("BlockEnemy", (byte) blockEnemySpawns);
	}

	public void setCheckRanges(int xz, int y, int y1, int l) {
		checkHorizontalRange = xz;
		checkVerticalMin = y;
		checkVerticalMax = y1;
		spawnCap = l;
	}

	public void setSpawnRanges(int xz, int y, int y1, int h) {
		spawnHorizontalRange = xz;
		spawnVerticalMin = y;
		spawnVerticalMax = y1;
		homeRange = h;
	}

	public Class<? extends Entity> getSpawnClass1() {
		return spawnClass1;
	}

	public void setSpawnClass1(Class<? extends Entity> spawnClass1) {
		this.spawnClass1 = spawnClass1;
	}

	public Class<? extends Entity> getSpawnClass2() {
		return spawnClass2;
	}

	public void setSpawnClass2(Class<? extends Entity> spawnClass2) {
		this.spawnClass2 = spawnClass2;
	}

	public float getPrevSpawnerSpin() {
		return prevSpawnerSpin;
	}

	public float getSpawnerSpin() {
		return spawnerSpin;
	}

	public int getBlockEnemySpawns() {
		return blockEnemySpawns;
	}

	public void setBlockEnemySpawns(int blockEnemySpawns) {
		this.blockEnemySpawns = Math.min(blockEnemySpawns, 64);
	}

	public int getCheckHorizontalRange() {
		return checkHorizontalRange;
	}

	public void setCheckHorizontalRange(int checkHorizontalRange) {
		this.checkHorizontalRange = checkHorizontalRange;
	}

	public int getCheckVerticalMax() {
		return checkVerticalMax;
	}

	public void setCheckVerticalMax(int checkVerticalMax) {
		this.checkVerticalMax = checkVerticalMax;
	}

	public int getCheckVerticalMin() {
		return checkVerticalMin;
	}

	public void setCheckVerticalMin(int checkVerticalMin) {
		this.checkVerticalMin = checkVerticalMin;
	}

	public int getHomeRange() {
		return homeRange;
	}

	public void setHomeRange(int homeRange) {
		this.homeRange = homeRange;
	}

	public int getMountSetting() {
		return mountSetting;
	}

	public int getNoPlayerRange() {
		return noPlayerRange;
	}

	public void setNoPlayerRange(int noPlayerRange) {
		this.noPlayerRange = noPlayerRange;
	}

	public int getSpawnCap() {
		return spawnCap;
	}

	public void setSpawnCap(int spawnCap) {
		this.spawnCap = spawnCap;
	}

	public int getSpawnHorizontalRange() {
		return spawnHorizontalRange;
	}

	public void setSpawnHorizontalRange(int spawnHorizontalRange) {
		this.spawnHorizontalRange = spawnHorizontalRange;
	}

	public int getSpawnInterval() {
		return spawnInterval;
	}

	public void setSpawnInterval(int spawnInterval) {
		this.spawnInterval = spawnInterval;
	}

	public int getSpawnVerticalMax() {
		return spawnVerticalMax;
	}

	public void setSpawnVerticalMax(int spawnVerticalMax) {
		this.spawnVerticalMax = spawnVerticalMax;
	}

	public int getSpawnVerticalMin() {
		return spawnVerticalMin;
	}

	public void setSpawnVerticalMin(int spawnVerticalMin) {
		this.spawnVerticalMin = spawnVerticalMin;
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTEntityNPCRespawner respawner;

		private EntitySelectorImpl(GOTEntityNPCRespawner respawner) {
			this.respawner = respawner;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			if (!entity.isEntityAlive()) {
				return false;
			}
			Class<?> entityClass = entity.getClass();
			return respawner.spawnClass1 != null && respawner.spawnClass1.isAssignableFrom(entityClass) || respawner.spawnClass2 != null && respawner.spawnClass2.isAssignableFrom(entityClass);
		}
	}
}