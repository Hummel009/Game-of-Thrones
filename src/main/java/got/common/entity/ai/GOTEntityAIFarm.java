package got.common.entity.ai;

import com.mojang.authlib.GameProfile;
import got.common.block.other.GOTBlockCorn;
import got.common.block.other.GOTBlockGrapevine;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTFarmhand;
import got.common.util.GOTReflection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class GOTEntityAIFarm extends EntityAIBase {
	public static int DEPOSIT_THRESHOLD = 16;
	public static int COLLECT_THRESHOLD = 16;
	public static int MIN_CHEST_RANGE = 24;
	public GOTEntityNPC theEntity;
	public GOTFarmhand theEntityFarmer;
	public World theWorld;
	public double moveSpeed;
	public float farmingEfficiency;
	public Action action;
	public ChunkCoordinates actionTarget;
	public ChunkCoordinates pathTarget;
	public int pathingTick;
	public int rePathDelay;
	public boolean harvestingSolidBlock;
	public FakePlayer fakePlayer;

	public GOTEntityAIFarm(GOTFarmhand npc, double d, float f) {
		theEntity = (GOTEntityNPC) npc;
		theEntityFarmer = npc;
		theWorld = theEntity.worldObj;
		moveSpeed = d;
		setMutexBits(1);
		if (theWorld instanceof WorldServer) {
			fakePlayer = FakePlayerFactory.get((WorldServer) theWorld, new GameProfile(null, "GOTFarming"));
		}
		farmingEfficiency = f;
	}

	public boolean canDoBonemealing() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack invBmeal = getInventoryBonemeal();
			return invBmeal != null;
		}
		return false;
	}

	public boolean canDoCollecting() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack seeds = getInventorySeeds();
			if (seeds != null && seeds.stackSize <= 16) {
				return true;
			}
			ItemStack bonemeal = getInventoryBonemeal();
			return bonemeal == null || bonemeal.stackSize <= 16;
		}
		return false;
	}

	public boolean canDoDepositing() {
		if (theEntity.hiredNPCInfo.isActive) {
			for (int l = 1; l <= 2; ++l) {
				ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (itemstack == null || itemstack.stackSize < 16) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public boolean canDoHarvesting() {
		return theEntity.hiredNPCInfo.isActive && getInventorySeeds() != null && hasSpaceForCrops() && getCropForSeed(getSeedsToPlant()) != null;
	}

	public boolean canDoHoeing() {
		return true;
	}

	public boolean canDoPlanting() {
		if (theEntity.hiredNPCInfo.isActive) {
			ItemStack invSeeds = getInventorySeeds();
			return invSeeds != null && invSeeds.stackSize > 1;
		}
		return true;
	}

	@Override
	public boolean continueExecuting() {
		if (theEntity.hiredNPCInfo.isActive && !theEntity.hiredNPCInfo.isGuardMode() || theEntity.getNavigator().noPath()) {
			return false;
		}
		if (pathingTick < 200) {
			switch (action) {
				case BONEMEALING:
					return canDoBonemealing() && isSuitableForBonemealing(actionTarget);
				case COLLECTING:
					return canDoCollecting() && isSuitableForCollecting(actionTarget);
				case DEPOSITING:
					return canDoDepositing() && isSuitableForDepositing(actionTarget);
				case HARVESTING:
					return canDoHarvesting() && isSuitableForHarvesting(actionTarget);
				case HOEING:
					return canDoHoeing() && isSuitableForHoeing(actionTarget);
				case PLANTING:
					return canDoPlanting() && isSuitableForPlanting(actionTarget);
			}
		}
		return false;
	}

	public TargetPair findTarget(Action targetAction) {
		setAppropriateHomeRange(targetAction);
		Random rand = theEntity.getRNG();
		boolean isChestAction = targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING;
		List<TileEntityChest> chests = new ArrayList<>();
		if (isChestAction) {
			chests = gatherNearbyChests();
		}
		for (int l = 0; l < 32; ++l) {
			int i = 0;
			int j = 0;
			int k = 0;
			boolean suitable = false;
			if (isChestAction) {
				if (!chests.isEmpty()) {
					TileEntityChest chest = chests.get(rand.nextInt(chests.size()));
					i = chest.xCoord;
					j = chest.yCoord;
					k = chest.zCoord;
					switch (targetAction) {
						case COLLECTING:
							suitable = isSuitableForCollecting(i, j, k);
							break;
						case DEPOSITING:
							suitable = isSuitableForDepositing(i, j, k);
							break;
						default:
							break;
					}
				}
			} else {
				i = MathHelper.floor_double(theEntity.posX) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
				j = MathHelper.floor_double(theEntity.boundingBox.minY) + MathHelper.getRandomIntegerInRange(rand, -4, 4);
				k = MathHelper.floor_double(theEntity.posZ) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
				switch (targetAction) {
					case BONEMEALING:
						suitable = isSuitableForBonemealing(i, j, k);
						break;
					case HARVESTING:
						suitable = isSuitableForHarvesting(i, j, k);
						break;
					case HOEING:
						suitable = isSuitableForHoeing(i, j, k);
						break;
					case PLANTING:
						suitable = isSuitableForPlanting(i, j, k);
						break;
					default:
						break;
				}
			}
			if (!suitable || !theEntity.isWithinHomeDistance(i, j, k)) {
				continue;
			}
			ChunkCoordinates target = new ChunkCoordinates(i, j, k);
			ChunkCoordinates path = getPathTarget(i, j, k, targetAction);
			PathEntity pathCheck = theEntity.getNavigator().getPathToXYZ(path.posX, path.posY, path.posZ);
			if (pathCheck == null) {
				continue;
			}
			return new TargetPair(target, path);
		}
		return null;
	}

	public List<TileEntityChest> gatherNearbyChests() {
		int x = MathHelper.floor_double(theEntity.posX);
		int z = MathHelper.floor_double(theEntity.posZ);
		int searchRange = (int) theEntity.func_110174_bM();
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		int chunkRange = (searchRange >> 4) + 1;
		List<TileEntityChest> nearbyChests = new ArrayList<>();
		for (int i = -chunkRange; i <= chunkRange; ++i) {
			for (int k = -chunkRange; k <= chunkRange; ++k) {
				int nearChunkX = chunkX + i;
				int nearChunkZ = chunkZ + k;
				if (!theWorld.getChunkProvider().chunkExists(nearChunkX, nearChunkZ)) {
					continue;
				}
				Chunk chunk = theWorld.getChunkFromChunkCoords(nearChunkX, nearChunkZ);
				for (Object obj : chunk.chunkTileEntityMap.values()) {
					TileEntity te = (TileEntity) obj;
					if (!(te instanceof TileEntityChest) || te.isInvalid()) {
						continue;
					}
					TileEntityChest chest = (TileEntityChest) te;
					if (!theEntity.isWithinHomeDistance(chest.xCoord, chest.yCoord, chest.zCoord)) {
						continue;
					}
					nearbyChests.add(chest);
				}
			}
		}
		return nearbyChests;
	}

	public ChunkCoordinates getAdjacentSolidOpenWalkTarget(int i, int j, int k) {
		ArrayList<ChunkCoordinates> possibleCoords = new ArrayList<>();
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int k1 = -1; k1 <= 1; ++k1) {
				int i2 = i + i1;
				int k2 = k + k1;
				for (int j1 = 1; j1 >= -1; --j1) {
					int j2 = j + j1;
					if (!isSolidOpenWalkTarget(i2, j2, k2)) {
						continue;
					}
					possibleCoords.add(new ChunkCoordinates(i2, j2, k2));
				}
			}
		}
		if (!possibleCoords.isEmpty()) {
			return possibleCoords.get(0);
		}
		return new ChunkCoordinates(i, j, k);
	}

	public ItemStack getCropForSeed(IPlantable seed) {
		Block block = seed.getPlant(theWorld, -1, -1, -1);
		if (block instanceof BlockCrops) {
			return new ItemStack(GOTReflection.getCropItem((BlockCrops) block));
		}
		if (block instanceof BlockStem) {
			return new ItemStack(GOTReflection.getStemFruitBlock((BlockStem) block).getItemDropped(0, theWorld.rand, 0), 1, 0);
		}
		if (block instanceof GOTBlockCorn) {
			return new ItemStack(GOTItems.corn);
		}
		if (block instanceof GOTBlockGrapevine) {
			return new ItemStack(((GOTBlockGrapevine) block).getGrapeItem());
		}
		return null;
	}

	public ItemStack getInventoryBonemeal() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return null;
		}
		ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(3);
		if (itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
			return itemstack;
		}
		return null;
	}

	public ItemStack getInventorySeeds() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return null;
		}
		ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof IPlantable && ((IPlantable) item).getPlantType(theWorld, -1, -1, -1) == EnumPlantType.Crop) {
			return itemstack;
		}
		return null;
	}

	public ChunkCoordinates getPathTarget(int i, int j, int k, Action targetAction) {
		if (targetAction == Action.HOEING) {
			if (isReplaceable(i, j + 1, k)) {
				return new ChunkCoordinates(i, j + 1, k);
			}
			return getAdjacentSolidOpenWalkTarget(i, j + 1, k);
		}
		if (targetAction == Action.PLANTING || targetAction == Action.HARVESTING || targetAction == Action.BONEMEALING) {
			if (harvestingSolidBlock) {
				return new ChunkCoordinates(i, j + 1, k);
			}
			if (isFarmingGrapes()) {
				int groundY = j;
				for (int j1 = 1; j1 <= 2; ++j1) {
					if (theWorld.getBlock(i, j - j1 - 1, k) instanceof GOTBlockGrapevine) {
						continue;
					}
					groundY = j - j1 - 1;
					break;
				}
				return getAdjacentSolidOpenWalkTarget(i, groundY + 1, k);
			}
			return new ChunkCoordinates(i, j, k);
		}
		if (targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING) {
			return getAdjacentSolidOpenWalkTarget(i, j, k);
		}
		return new ChunkCoordinates(i, j, k);
	}

	public IPlantable getSeedsToPlant() {
		ItemStack invSeeds;
		if (theEntity.hiredNPCInfo.isActive && (invSeeds = getInventorySeeds()) != null) {
			return (IPlantable) invSeeds.getItem();
		}
		return theEntityFarmer.getUnhiredSeeds();
	}

	public TileEntityChest getSuitableChest(int i, int j, int k) {
		TileEntity te;
		Block block = theWorld.getBlock(i, j, k);
		int meta = theWorld.getBlockMetadata(i, j, k);
		if (block.hasTileEntity(meta) && (te = theWorld.getTileEntity(i, j, k)) instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) te;
			if (chest.adjacentChestXPos != null && isFarmhandMarked(chest.adjacentChestXPos) || chest.adjacentChestZNeg != null && isFarmhandMarked(chest.adjacentChestZNeg) || chest.adjacentChestZPos != null && isFarmhandMarked(chest.adjacentChestZPos) || isFarmhandMarked(chest) || chest.adjacentChestXNeg != null && isFarmhandMarked(chest.adjacentChestXNeg)) {
				return chest;
			}
		}
		return null;
	}

	public boolean hasSpaceForCrops() {
		if (theEntity.hiredNPCInfo.getHiredInventory() == null) {
			return false;
		}
		for (int l = 1; l <= 2; ++l) {
			ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
			if (itemstack != null && (itemstack.stackSize >= itemstack.getMaxStackSize() || !itemstack.isItemEqual(getCropForSeed(getSeedsToPlant())))) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean isFarmhandMarked(TileEntityChest chest) {
		int i = chest.xCoord;
		int j = chest.yCoord;
		int k = chest.zCoord;
		AxisAlignedBB chestBB = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
		List<EntityItemFrame> entities = theWorld.getEntitiesWithinAABB(EntityItemFrame.class, chestBB.expand(2.0, 2.0, 2.0));
		for (EntityItemFrame entity : entities) {
			ItemStack frameItem;
			if (entity.field_146063_b != i || entity.field_146064_c != j || entity.field_146062_d != k || (frameItem = entity.getDisplayedItem()) == null || !(frameItem.getItem() instanceof ItemHoe)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean isFarmingGrapes() {
		IPlantable seed = getSeedsToPlant();
		return seed.getPlant(theWorld, -1, -1, -1) instanceof GOTBlockGrapevine;
	}

	public boolean isReplaceable(int i, int j, int k) {
		Block block = theWorld.getBlock(i, j, k);
		return !block.getMaterial().isLiquid() && block.isReplaceable(theWorld, i, j, k);
	}

	public boolean isSolidOpenWalkTarget(int i, int j, int k) {
		Block below = theWorld.getBlock(i, j - 1, k);
		if (below.isOpaqueCube() || below.canSustainPlant(theWorld, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.wheat)) {
			List<AxisAlignedBB> bounds = new ArrayList<>();
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 2, k + 1);
			for (int j1 = j; j1 <= j + 1; ++j1) {
				theWorld.getBlock(i, j1, k).addCollisionBoxesToList(theWorld, i, j1, k, aabb, bounds, theEntity);
			}
			if (bounds.isEmpty()) {
				return theEntity.getNavigator().getPathToXYZ(i, j, k) != null;
			}
		}
		return false;
	}

	public boolean isSuitableForBonemealing(ChunkCoordinates pos) {
		return isSuitableForBonemealing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForBonemealing(int i, int j, int k) {
		harvestingSolidBlock = false;
		IPlantable seed = getSeedsToPlant();
		Block plantBlock = seed.getPlant(theWorld, i, j, k);
		if (plantBlock instanceof IGrowable && theWorld.getBlock(i, j, k) == plantBlock && ((IGrowable) plantBlock).func_149851_a(theWorld, i, j, k, theWorld.isRemote)) {
			harvestingSolidBlock = plantBlock.isOpaqueCube();
			return true;
		}
		return false;
	}

	public boolean isSuitableForCollecting(ChunkCoordinates pos) {
		return isSuitableForCollecting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForCollecting(int i, int j, int k) {
		harvestingSolidBlock = false;
		TileEntityChest chest = getSuitableChest(i, j, k);
		if (chest != null) {
			for (int l : new int[]{0, 3}) {
				ItemStack collectMatch = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (collectMatch == null && l == 3) {
					collectMatch = new ItemStack(Items.dye, 0, 15);
				}
				if (collectMatch == null || collectMatch.stackSize > 16) {
					continue;
				}
				for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
					ItemStack chestItem = chest.getStackInSlot(slot);
					if (chestItem == null || !chestItem.isItemEqual(collectMatch) || !ItemStack.areItemStackTagsEqual(chestItem, collectMatch) || chestItem.stackSize <= 0) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSuitableForDepositing(ChunkCoordinates pos) {
		return isSuitableForDepositing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForDepositing(int i, int j, int k) {
		harvestingSolidBlock = false;
		TileEntityChest chest = getSuitableChest(i, j, k);
		if (chest != null) {
			for (int l = 1; l <= 2; ++l) {
				ItemStack depositItem = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
				if (depositItem == null) {
					continue;
				}
				for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
					ItemStack chestItem = chest.getStackInSlot(slot);
					if (chestItem != null && (!chestItem.isItemEqual(depositItem) || !ItemStack.areItemStackTagsEqual(chestItem, depositItem) || chestItem.stackSize >= chestItem.getMaxStackSize())) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSuitableForHarvesting(ChunkCoordinates pos) {
		return isSuitableForHarvesting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForHarvesting(int i, int j, int k) {
		harvestingSolidBlock = false;
		IPlantable seed = getSeedsToPlant();
		Block plantBlock = seed.getPlant(theWorld, i, j, k);
		if (plantBlock instanceof BlockCrops) {
			harvestingSolidBlock = false;
			return theWorld.getBlock(i, j, k) == plantBlock && theWorld.getBlockMetadata(i, j, k) >= 7;
		}
		if (plantBlock instanceof BlockStem) {
			harvestingSolidBlock = true;
			return theWorld.getBlock(i, j, k) == GOTReflection.getStemFruitBlock((BlockStem) plantBlock);
		}
		if (plantBlock instanceof GOTBlockCorn) {
			harvestingSolidBlock = false;
			if (theWorld.getBlock(i, j, k) == plantBlock) {
				for (int j1 = 0; j1 <= GOTBlockCorn.MAX_GROW_HEIGHT - 1; ++j1) {
					int j2 = j + j1;
					if (theWorld.getBlock(i, j2, k) != plantBlock || !GOTBlockCorn.hasCorn(theWorld, i, j2, k)) {
						continue;
					}
					return true;
				}
			}
		} else if (plantBlock instanceof GOTBlockGrapevine) {
			harvestingSolidBlock = false;
			return theWorld.getBlock(i, j, k) == seed.getPlant(theWorld, i, j, k) && theWorld.getBlockMetadata(i, j, k) >= 7;
		}
		return false;
	}

	public boolean isSuitableForHoeing(ChunkCoordinates pos) {
		return isSuitableForHoeing(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForHoeing(int i, int j, int k) {
		harvestingSolidBlock = false;
		Block block = theWorld.getBlock(i, j, k);
		boolean isGrassDirt = block.canSustainPlant(theWorld, i, j, k, ForgeDirection.UP, Blocks.tallgrass);
		boolean isFarmland = block.canSustainPlant(theWorld, i, j, k, ForgeDirection.UP, (IPlantable) Blocks.wheat);
		if (isGrassDirt && !isFarmland && (isReplaceable(i, j + 1, k) || theWorld.getBlock(i, j + 1, k) == GOTBlocks.grapevine)) {
			Block below = theWorld.getBlock(i, j - 1, k);
			if (below == Blocks.sand) {
				return false;
			}
			int range = 4;
			for (int i1 = i - range; i1 <= i + range; ++i1) {
				for (int k1 = k - range; k1 <= k + range; ++k1) {
					if (theWorld.getBlock(i1, j, k1).getMaterial() != Material.water) {
						continue;
					}
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean isSuitableForPlanting(ChunkCoordinates pos) {
		return isSuitableForPlanting(pos.posX, pos.posY, pos.posZ);
	}

	public boolean isSuitableForPlanting(int i, int j, int k) {
		harvestingSolidBlock = false;
		if (isFarmingGrapes()) {
			return theWorld.getBlock(i, j, k) == GOTBlocks.grapevine && GOTBlockGrapevine.canPlantGrapesAt(theWorld, i, j, k, getSeedsToPlant());
		}
		return theWorld.getBlock(i, j - 1, k).isFertile(theWorld, i, j - 1, k) && isReplaceable(i, j, k);
	}

	@Override
	public void resetTask() {
		action = null;
		setAppropriateHomeRange(null);
		actionTarget = null;
		pathTarget = null;
		pathingTick = 0;
		rePathDelay = 0;
		harvestingSolidBlock = false;
	}

	public void setAppropriateHomeRange(Action targetAction) {
		if (theEntity.hiredNPCInfo.isActive) {
			int hRange = theEntity.hiredNPCInfo.getGuardRange();
			ChunkCoordinates home = theEntity.getHomePosition();
			if ((targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING) && hRange < 24) {
				hRange = 24;
			}
			theEntity.setHomeArea(home.posX, home.posY, home.posZ, hRange);
		}
	}

	@Override
	public boolean shouldExecute() {
		return shouldFarmhandExecute();
	}

	public boolean shouldFarmhandExecute() {
		if (theEntity.hiredNPCInfo.isActive && !theEntity.hiredNPCInfo.isGuardMode()) {
			return false;
		}
		setAppropriateHomeRange(null);
		if (theEntity.hasHome() && !theEntity.isWithinHomeDistanceCurrentPosition()) {
			return false;
		}
		if (theEntity.getRNG().nextFloat() < farmingEfficiency * 0.1f) {
			Map<Action, Boolean> map = new EnumMap<>(Action.class);
			map.put(Action.DEPOSITING, canDoDepositing());
			map.put(Action.HOEING, canDoHoeing());
			map.put(Action.PLANTING, canDoPlanting());
			map.put(Action.HARVESTING, canDoHarvesting());
			map.put(Action.BONEMEALING, canDoBonemealing());
			map.put(Action.COLLECTING, canDoCollecting());
			for (Action act : Action.values()) {
				TargetPair depositTarget;
				if (Boolean.TRUE.equals(map.get(act)) && (depositTarget = findTarget(act)) != null) {
					actionTarget = depositTarget.actionTarget;
					pathTarget = depositTarget.pathTarget;
					action = act;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		setAppropriateHomeRange(action);
	}

	@Override
	public void updateTask() {
		boolean canDoAction;
		double distSq = theEntity.getDistanceSq(pathTarget.posX + 0.5, pathTarget.posY, pathTarget.posZ + 0.5);
		if (action == Action.HOEING || action == Action.PLANTING) {
			int i = MathHelper.floor_double(theEntity.posX);
			int j = MathHelper.floor_double(theEntity.boundingBox.minY);
			int k = MathHelper.floor_double(theEntity.posZ);
			canDoAction = i == pathTarget.posX && j == pathTarget.posY && k == pathTarget.posZ;
		} else {
			canDoAction = distSq < 9.0;
		}
		if (canDoAction) {
			boolean canCollect;
			switch (action) {
				case BONEMEALING:
					boolean canBonemeal = isSuitableForBonemealing(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					if (canBonemeal) {
						theEntity.swingItem();
						ItemStack bonemeal = getInventoryBonemeal();
						if (ItemDye.applyBonemeal(getInventoryBonemeal(), theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, fakePlayer)) {
							theWorld.playAuxSFX(2005, actionTarget.posX, actionTarget.posY, actionTarget.posZ, 0);
						}
						if (bonemeal.stackSize <= 0) {
							bonemeal = null;
						}
						theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(3, bonemeal);
					}
					break;
				case COLLECTING:
					canCollect = isSuitableForCollecting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					if (canCollect) {
						theEntity.swingItem();
						TileEntity te = theWorld.getTileEntity(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						if (te instanceof TileEntityChest) {
							IInventory chest = (IInventory) te;
							int[] invSlots = {0, 3};
							for (int l : invSlots) {
								ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
								if (itemstack == null && l == 3) {
									itemstack = new ItemStack(Items.dye, 0, 15);
								}
								if (itemstack != null) {
									for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
										ItemStack chestItem = chest.getStackInSlot(slot);
										if (chestItem != null && chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize > 0) {
											while (itemstack.stackSize < itemstack.getMaxStackSize() && chestItem.stackSize > 0) {
												chestItem.stackSize--;
												itemstack.stackSize++;
											}
											if (itemstack.stackSize <= 0) {
												itemstack = null;
											}
											if (chestItem.stackSize <= 0) {
												chestItem = null;
											}
											theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack);
											chest.setInventorySlotContents(slot, chestItem);
											if (itemstack.stackSize >= itemstack.getMaxStackSize()) {
												break;
											}
										}
									}
								}
							}
							theWorld.playSoundEffect(actionTarget.posX + 0.5, actionTarget.posY + 0.5, actionTarget.posZ + 0.5, "random.chestopen", 0.5f, theWorld.rand.nextFloat() * 0.1f + 0.9f);
						}
					}
					break;
				case DEPOSITING:
					boolean canDeposit = isSuitableForDepositing(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					if (canDeposit) {
						theEntity.swingItem();
						TileEntity te = theWorld.getTileEntity(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						if (te instanceof TileEntityChest) {
							IInventory chest = (IInventory) te;
							block5:
							for (int l = 1; l <= 2; ++l) {
								ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
								if (itemstack == null) {
									continue;
								}
								for (int slot = 0; slot < chest.getSizeInventory(); ++slot) {
									ItemStack chestItem = chest.getStackInSlot(slot);
									if (chestItem != null && (!chestItem.isItemEqual(itemstack) || !ItemStack.areItemStackTagsEqual(chestItem, itemstack) || chestItem.stackSize >= chestItem.getMaxStackSize())) {
										continue;
									}
									if (chestItem == null) {
										chestItem = itemstack.copy();
										chestItem.stackSize = 0;
									}
									while (itemstack.stackSize > 0 && chestItem.stackSize < chestItem.getMaxStackSize()) {
										++chestItem.stackSize;
										--itemstack.stackSize;
									}
									chest.setInventorySlotContents(slot, chestItem);
									if (itemstack.stackSize > 0) {
										continue;
									}
									theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, null);
									continue block5;
								}
							}
							theWorld.playSoundEffect(actionTarget.posX + 0.5, actionTarget.posY + 0.5, actionTarget.posZ + 0.5, "random.chestclosed", 0.5f, theWorld.rand.nextFloat() * 0.1f + 0.9f);
						}
					}
					break;
				case HARVESTING:
					boolean canHarvest = isSuitableForHarvesting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					if (canHarvest) {
						theEntity.swingItem();
						Block block = theWorld.getBlock(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						Collection<ItemStack> drops = new ArrayList<>();
						int meta;
						if (block instanceof GOTBlockCorn) {
							int x = actionTarget.posX;
							int z = actionTarget.posZ;
							for (int j1 = 0; j1 <= GOTBlockCorn.MAX_GROW_HEIGHT - 1; ++j1) {
								int y = actionTarget.posY + j1;
								if (theWorld.getBlock(x, y, z) != block || !GOTBlockCorn.hasCorn(theWorld, x, y, z)) {
									continue;
								}
								int meta2 = theWorld.getBlockMetadata(x, y, z);
								drops.addAll(((GOTBlockCorn) block).getCornDrops(theWorld, x, y, z, meta2));
								GOTBlockCorn.setHasCorn(theWorld, x, y, z, false);
							}
						} else if (block instanceof GOTBlockGrapevine) {
							meta = theWorld.getBlockMetadata(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
							drops.addAll(block.getDrops(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, meta, 0));
							block.removedByPlayer(theWorld, fakePlayer, actionTarget.posX, actionTarget.posY, actionTarget.posZ, true);
						} else {
							meta = theWorld.getBlockMetadata(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
							drops.addAll(block.getDrops(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ, meta, 0));
							theWorld.setBlockToAir(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						}
						Block.SoundType cropSound = block.stepSound;
						theWorld.playSoundEffect(actionTarget.posX + 0.5, actionTarget.posY + 0.5, actionTarget.posZ + 0.5, cropSound.getBreakSound(), (cropSound.getVolume() + 1.0f) / 2.0f, cropSound.getPitch() * 0.8f);
						ItemStack seedItem = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
						ItemStack cropItem = getCropForSeed(getSeedsToPlant());
						boolean addedOneCropSeed = false;
						block3:
						for (ItemStack drop : drops) {
							ItemStack itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
							if (drop.isItemEqual(cropItem)) {
								if (drop.isItemEqual(seedItem) && !addedOneCropSeed) {
									addedOneCropSeed = true;
									itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
									if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
										++itemstack.stackSize;
										theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
										continue;
									}
								}
								for (int l = 1; l <= 2; ++l) {
									ItemStack itemstack2 = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
									if (itemstack2 == null) {
										theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, drop);
										continue block3;
									}
									if (itemstack2.stackSize + drop.stackSize > itemstack2.getMaxStackSize() || !itemstack2.isItemEqual(cropItem)) {
										continue;
									}
									++itemstack2.stackSize;
									theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack2);
									continue block3;
								}
								continue;
							}
							if (!drop.isItemEqual(seedItem) || itemstack.stackSize + drop.stackSize > (itemstack = theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0)).getMaxStackSize()) {
								continue;
							}
							++itemstack.stackSize;
							theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
						}
					}
					break;
				case HOEING:
					boolean canHoe = isSuitableForHoeing(actionTarget);
					if (canHoe) {
						theEntity.swingItem();
						ItemStack proxyHoe = new ItemStack(Items.iron_hoe);
						int hoeRange = 1;
						for (int i1 = -hoeRange; i1 <= hoeRange; ++i1) {
							for (int k1 = -hoeRange; k1 <= hoeRange; ++k1) {
								if (Math.abs(i1) + Math.abs(k1) > hoeRange) {
									continue;
								}
								int x = actionTarget.posX + i1;
								int z = actionTarget.posZ + k1;
								int y = actionTarget.posY;
								boolean alreadyChecked = i1 == 0 && k1 == 0;
								if (!alreadyChecked && !isSuitableForHoeing(x, y, z)) {
									continue;
								}
								if (isReplaceable(x, y + 1, z)) {
									theWorld.setBlockToAir(x, y + 1, z);
								}
								proxyHoe.tryPlaceItemIntoWorld(fakePlayer, theWorld, x, y, z, 1, 0.5f, 0.5f, 0.5f);
							}
						}
					}
					break;
				case PLANTING:
					boolean canPlant = isSuitableForPlanting(actionTarget.posX, actionTarget.posY, actionTarget.posZ);
					if (canPlant) {
						theEntity.swingItem();
						IPlantable seed = getSeedsToPlant();
						Block plant = seed.getPlant(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						int meta = seed.getPlantMetadata(theWorld, actionTarget.posX, actionTarget.posY, actionTarget.posZ);
						theWorld.setBlock(actionTarget.posX, actionTarget.posY, actionTarget.posZ, plant, meta, 3);
						if (theEntity.hiredNPCInfo.isActive) {
							theEntity.hiredNPCInfo.getHiredInventory().decrStackSize(0, 1);
						}
					}
					break;
			}
		} else {
			theEntity.getLookHelper().setLookPosition(actionTarget.posX + 0.5, actionTarget.posY + 0.5, actionTarget.posZ + 0.5, 10.0f, theEntity.getVerticalFaceSpeed());
			--rePathDelay;
			if (rePathDelay <= 0) {
				rePathDelay = 10;
				theEntity.getNavigator().tryMoveToXYZ(pathTarget.posX + 0.5, pathTarget.posY, pathTarget.posZ + 0.5, moveSpeed);
			}
			++pathingTick;
		}
	}

	public enum Action {
		HOEING, PLANTING, HARVESTING, DEPOSITING, BONEMEALING, COLLECTING
	}

	public static class TargetPair {
		public ChunkCoordinates actionTarget;
		public ChunkCoordinates pathTarget;

		public TargetPair(ChunkCoordinates action, ChunkCoordinates path) {
			actionTarget = action;
			pathTarget = path;
		}
	}
}