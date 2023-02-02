package got.common.world.structure.westeros.common;

import java.util.*;
import java.util.Map.Entry;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.crownlands.*;
import got.common.entity.westeros.dorne.*;
import got.common.entity.westeros.dragonstone.*;
import got.common.entity.westeros.ironborn.*;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.item.other.GOTItemBanner;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.arryn.GOTStructureArrynTower;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsTower;
import got.common.world.structure.westeros.dorne.GOTStructureDorneTower;
import got.common.world.structure.westeros.dragonstone.GOTStructureDragonstoneTower;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornTower;
import got.common.world.structure.westeros.north.GOTStructureNorthTower;
import got.common.world.structure.westeros.reach.GOTStructureReachTower;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsTower;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsTower;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsTower;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class GOTStructureWesterosBase extends GOTStructureBase {
	public Block rockBlock;
	public int rockMeta;
	public Block rockSlabBlock;
	public int rockSlabMeta;
	public Block rockSlabDoubleBlock;
	public int rockSlabDoubleMeta;
	public Block rockStairBlock;
	public Block rockWallBlock;
	public int rockWallMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickMossyBlock;
	public int brickMossyMeta;
	public Block brickMossySlabBlock;
	public int brickMossySlabMeta;
	public Block brickMossyStairBlock;
	public Block brickMossyWallBlock;
	public int brickMossyWallMeta;
	public Block brickCrackedBlock;
	public int brickCrackedMeta;
	public Block brickCrackedSlabBlock;
	public int brickCrackedSlabMeta;
	public Block brickCrackedStairBlock;
	public Block brickCrackedWallBlock;
	public int brickCrackedWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block pillar2Block;
	public int pillar2Meta;
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block cobbleSlabBlock;
	public int cobbleSlabMeta;
	public Block cobbleStairBlock;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block doorBlock;
	public Block wallBlock;
	public int wallMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block bedBlock;
	public Block gateBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public Block tableBlock;
	public Block brickCarved;
	public int brickCarvedMeta;
	public BannerType banner;
	public boolean isArryn = false;
	public boolean isDorne = false;
	public boolean isCrownlands = false;
	public boolean isCrownlandsRed = false;
	public boolean isDragonstone = false;
	public boolean isIronborn = false;
	public boolean isNorth = false;
	public boolean isReach = false;
	public boolean isRiverlands = false;
	public boolean isStormlands = false;
	public boolean isWesterlands = false;

	public GOTStructureWesterosBase(boolean flag) {
		super(flag);
	}

	public BannerType getBanner() {
		Map<Boolean, BannerType> map = new HashMap<>();
		map.put(isArryn, GOTItemBanner.BannerType.ARRYN);
		map.put(isCrownlands, GOTItemBanner.BannerType.ROBERT);
		map.put(isCrownlandsRed, GOTItemBanner.BannerType.JOFFREY);
		map.put(isDorne, GOTItemBanner.BannerType.MARTELL);
		map.put(isDragonstone, GOTItemBanner.BannerType.STANNIS);
		map.put(isIronborn, GOTItemBanner.BannerType.GREYJOY);
		map.put(isNorth, GOTItemBanner.BannerType.ROBB);
		map.put(isReach, GOTItemBanner.BannerType.TYRELL);
		map.put(isRiverlands, GOTItemBanner.BannerType.TULLY);
		map.put(isStormlands, GOTItemBanner.BannerType.RENLY);
		map.put(isWesterlands, GOTItemBanner.BannerType.LANNISTER);

		for (Entry<Boolean, BannerType> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBartender(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynBartender(world));
		map.put(isCrownlands, new GOTEntityCrownlandsBartender(world));
		map.put(isCrownlandsRed, new GOTEntityCrownlandsBartender(world));
		map.put(isDorne, new GOTEntityDorneBartender(world));
		map.put(isDragonstone, new GOTEntityDragonstoneBartender(world));
		map.put(isIronborn, new GOTEntityIronbornBartender(world));
		map.put(isNorth, new GOTEntityNorthBartender(world));
		map.put(isReach, new GOTEntityReachBartender(world));
		map.put(isRiverlands, new GOTEntityRiverlandsBartender(world));
		map.put(isStormlands, new GOTEntityStormlandsBartender(world));
		map.put(isWesterlands, new GOTEntityWesterlandsBartender(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBlacksmith(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynBlacksmith(world));
		map.put(isCrownlands, new GOTEntityCrownlandsBlacksmith(world));
		map.put(isCrownlandsRed, new GOTEntityCrownlandsBlacksmith(world));
		map.put(isDorne, new GOTEntityDorneBlacksmith(world));
		map.put(isDragonstone, new GOTEntityDragonstoneBlacksmith(world));
		map.put(isIronborn, new GOTEntityIronbornBlacksmith(world));
		map.put(isNorth, new GOTEntityNorthBlacksmith(world));
		map.put(isReach, new GOTEntityReachBlacksmith(world));
		map.put(isRiverlands, new GOTEntityRiverlandsBlacksmith(world));
		map.put(isStormlands, new GOTEntityStormlandsBlacksmith(world));
		map.put(isWesterlands, new GOTEntityWesterlandsBlacksmith(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getCaptain(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynCaptain(world));
		map.put(isCrownlands, new GOTEntityCrownlandsCaptain(world));
		map.put(isDorne, new GOTEntityDorneCaptain(world));
		map.put(isDragonstone, new GOTEntityDragonstoneCaptain(world));
		map.put(isIronborn, new GOTEntityIronbornCaptain(world));
		map.put(isNorth, new GOTEntityNorthCaptain(world));
		map.put(isReach, new GOTEntityReachCaptain(world));
		map.put(isRiverlands, new GOTEntityRiverlandsCaptain(world));
		map.put(isStormlands, new GOTEntityStormlandsCaptain(world));
		map.put(isWesterlands, new GOTEntityWesterlandsCaptain(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTChestContents getChestContents() {
		Map<Boolean, GOTChestContents> map = new HashMap<>();
		map.put(isArryn, GOTChestContents.ARRYN);
		map.put(isCrownlands, GOTChestContents.CROWNLANDS);
		map.put(isCrownlandsRed, GOTChestContents.CROWNLANDS);
		map.put(isDorne, GOTChestContents.DORNE);
		map.put(isDragonstone, GOTChestContents.DRAGONSTONE);
		map.put(isIronborn, GOTChestContents.IRONBORN);
		map.put(isNorth, GOTChestContents.NORTH);
		map.put(isReach, GOTChestContents.REACH);
		map.put(isRiverlands, GOTChestContents.RIVERLANDS);
		map.put(isStormlands, GOTChestContents.STORMLANDS);
		map.put(isWesterlands, GOTChestContents.WESTERLANDS);

		for (Entry<Boolean, GOTChestContents> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmer(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynFarmer(world));
		map.put(isCrownlands, new GOTEntityCrownlandsFarmer(world));
		map.put(isCrownlandsRed, new GOTEntityCrownlandsFarmer(world));
		map.put(isDorne, new GOTEntityDorneFarmer(world));
		map.put(isDragonstone, new GOTEntityDragonstoneFarmer(world));
		map.put(isIronborn, new GOTEntityIronbornFarmer(world));
		map.put(isNorth, new GOTEntityNorthFarmer(world));
		map.put(isReach, new GOTEntityReachFarmer(world));
		map.put(isRiverlands, new GOTEntityRiverlandsFarmer(world));
		map.put(isStormlands, new GOTEntityStormlandsFarmer(world));
		map.put(isWesterlands, new GOTEntityWesterlandsFarmer(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmhand(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynFarmhand(world));
		map.put(isCrownlands, new GOTEntityCrownlandsFarmhand(world));
		map.put(isCrownlandsRed, new GOTEntityCrownlandsFarmhand(world));
		map.put(isDorne, new GOTEntityDorneFarmhand(world));
		map.put(isDragonstone, new GOTEntityDragonstoneFarmhand(world));
		map.put(isIronborn, new GOTEntityIronbornFarmhand(world));
		map.put(isNorth, new GOTEntityNorthFarmhand(world));
		map.put(isReach, new GOTEntityReachFarmhand(world));
		map.put(isRiverlands, new GOTEntityRiverlandsFarmhand(world));
		map.put(isStormlands, new GOTEntityStormlandsFarmhand(world));
		map.put(isWesterlands, new GOTEntityWesterlandsFarmhand(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.westerosDagger), new ItemStack(GOTRegistry.westerosSpear), new ItemStack(GOTRegistry.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing) };
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getMan(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynMan(world));
		map.put(isCrownlands, new GOTEntityCrownlandsMan(world));
		map.put(isCrownlandsRed, new GOTEntityCrownlandsMan(world));
		map.put(isDorne, new GOTEntityDorneMan(world));
		map.put(isDragonstone, new GOTEntityDragonstoneMan(world));
		map.put(isIronborn, new GOTEntityIronbornMan(world));
		map.put(isNorth, new GOTEntityNorthMan(world));
		map.put(isReach, new GOTEntityReachMan(world));
		map.put(isRiverlands, new GOTEntityRiverlandsMan(world));
		map.put(isStormlands, new GOTEntityStormlandsMan(world));
		map.put(isWesterlands, new GOTEntityWesterlandsMan(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldier(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynSoldier(world));
		map.put(isCrownlands, new GOTEntityCrownlandsLevyman(world));
		map.put(isCrownlandsRed, new GOTEntityKingsguard(world));
		map.put(isDorne, new GOTEntityDorneSoldier(world));
		map.put(isDragonstone, new GOTEntityDragonstoneSoldier(world));
		map.put(isIronborn, new GOTEntityIronbornSoldier(world));
		map.put(isNorth, new GOTEntityNorthSoldier(world));
		map.put(isReach, new GOTEntityReachSoldier(world));
		map.put(isRiverlands, new GOTEntityRiverlandsSoldier(world));
		map.put(isStormlands, new GOTEntityStormlandsSoldier(world));
		map.put(isWesterlands, new GOTEntityWesterlandsSoldier(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isArryn, new GOTEntityArrynSoldierArcher(world));
		map.put(isCrownlands, new GOTEntityCrownlandsLevymanArcher(world));
		map.put(isCrownlandsRed, new GOTEntityKingsguard(world));
		map.put(isDorne, new GOTEntityDorneSoldierArcher(world));
		map.put(isDragonstone, new GOTEntityDragonstoneSoldierArcher(world));
		map.put(isIronborn, new GOTEntityIronbornSoldierArcher(world));
		map.put(isNorth, new GOTEntityNorthSoldierArcher(world));
		map.put(isReach, new GOTEntityReachSoldierArcher(world));
		map.put(isRiverlands, new GOTEntityRiverlandsSoldierArcher(world));
		map.put(isStormlands, new GOTEntityStormlandsSoldierArcher(world));
		map.put(isWesterlands, new GOTEntityWesterlandsSoldierArcher(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public Block getTable() {
		Map<Boolean, Block> map = new HashMap<>();
		map.put(isArryn, GOTRegistry.tableArryn);
		map.put(isCrownlands, GOTRegistry.tableCrownlands);
		map.put(isCrownlandsRed, GOTRegistry.tableCrownlands);
		map.put(isDorne, GOTRegistry.tableDorne);
		map.put(isDragonstone, GOTRegistry.tableDragonstone);
		map.put(isIronborn, GOTRegistry.tableIronborn);
		map.put(isNorth, GOTRegistry.tableNorth);
		map.put(isReach, GOTRegistry.tableReach);
		map.put(isRiverlands, GOTRegistry.tableRiverlands);
		map.put(isStormlands, GOTRegistry.tableStormlands);
		map.put(isWesterlands, GOTRegistry.tableWesterlands);

		for (Entry<Boolean, Block> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTStructureWesterosTower getTower(boolean notifyChanges) {
		Map<Boolean, GOTStructureWesterosTower> map = new HashMap<>();
		map.put(isArryn, new GOTStructureArrynTower(notifyChanges));
		map.put(isCrownlands, new GOTStructureCrownlandsTower(notifyChanges));
		map.put(isCrownlandsRed, new GOTStructureCrownlandsTower(notifyChanges));
		map.put(isDorne, new GOTStructureDorneTower(notifyChanges));
		map.put(isDragonstone, new GOTStructureDragonstoneTower(notifyChanges));
		map.put(isIronborn, new GOTStructureIronbornTower(notifyChanges));
		map.put(isNorth, new GOTStructureNorthTower(notifyChanges));
		map.put(isReach, new GOTStructureReachTower(notifyChanges));
		map.put(isRiverlands, new GOTStructureRiverlandsTower(notifyChanges));
		map.put(isStormlands, new GOTStructureStormlandsTower(notifyChanges));
		map.put(isWesterlands, new GOTStructureWesterlandsTower(notifyChanges));

		for (Entry<Boolean, GOTStructureWesterosTower> tow : map.entrySet()) {
			if (Boolean.TRUE.equals(tow.getKey())) {
				return tow.getValue();
			}
		}
		return null;
	}

	public boolean hasMaester() {
		return isArryn || isCrownlands || isReach || isRiverlands || isStormlands || isWesterlands || isNorth;
	}

	public boolean hasSepton() {
		return isArryn || isCrownlands || isRiverlands || isStormlands || isWesterlands;
	}

	public GOTStructureBase setGranite() {
		isCrownlands = false;
		isCrownlandsRed = true;
		return this;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		banner = getBanner();
		tableBlock = getTable();
		brickBlock = GOTRegistry.brick1;
		brickMeta = 1;
		brickSlabBlock = GOTRegistry.slabSingle1;
		brickSlabMeta = 3;
		brickStairBlock = GOTRegistry.stairsAndesiteBrick;
		brickWallBlock = GOTRegistry.wallStone1;
		pillarMeta = 6;
		rockMeta = 1;
		rockSlabBlock = GOTRegistry.slabSingle1;
		rockSlabDoubleBlock = GOTRegistry.slabDouble1;
		rockSlabDoubleMeta = 2;
		rockSlabMeta = 2;
		rockWallBlock = GOTRegistry.wallStone1;
		rockBlock = GOTRegistry.rock;
		rockStairBlock = GOTRegistry.stairsAndesite;
		rockWallMeta = 2;
		brickWallMeta = 3;
		brickMossyBlock = GOTRegistry.brick1;
		brickMossyMeta = 2;
		brickMossySlabBlock = GOTRegistry.slabSingle1;
		brickMossySlabMeta = 4;
		brickMossyStairBlock = GOTRegistry.stairsAndesiteBrickMossy;
		brickMossyWallBlock = GOTRegistry.wallStone1;
		brickMossyWallMeta = 4;
		brickCrackedBlock = GOTRegistry.brick1;
		brickCrackedMeta = 3;
		brickCrackedSlabBlock = GOTRegistry.slabSingle1;
		brickCrackedSlabMeta = 5;
		brickCrackedStairBlock = GOTRegistry.stairsAndesiteBrickCracked;
		brickCrackedWallBlock = GOTRegistry.wallStone1;
		brickCrackedWallMeta = 5;
		pillarBlock = GOTRegistry.pillar1;
		brick2Block = GOTRegistry.brick2;
		brick2Meta = 11;
		brick2SlabBlock = GOTRegistry.slabSingle5;
		brick2SlabMeta = 3;
		brick2StairBlock = GOTRegistry.stairsBasaltWesterosBrick;
		brick2WallBlock = GOTRegistry.wallStone2;
		brick2WallMeta = 10;
		pillar2Block = GOTRegistry.pillar1;
		pillar2Meta = 9;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		brickCarved = GOTRegistry.brick1;
		brickCarvedMeta = 5;
		if (isCrownlandsRed) {
			brickBlock = GOTRegistry.brick2;
			brickMeta = 2;
			brickSlabBlock = GOTRegistry.slabSingle3;
			brickSlabMeta = 6;
			brickStairBlock = GOTRegistry.stairsGraniteBrick;
			brickWallBlock = GOTRegistry.wallStone2;
			pillarMeta = 4;
			rockMeta = 4;
			rockSlabBlock = GOTRegistry.slabSingle3;
			rockSlabDoubleBlock = GOTRegistry.smoothStone;
			rockSlabDoubleMeta = 4;
			rockSlabMeta = 6;
			rockWallBlock = GOTRegistry.wallStone2;
			brickCarved = GOTRegistry.brick3;
			brickCarvedMeta = 1;
		}
		if (isDorne) {
			brickCrackedMeta = 15;
			brickCrackedSlabBlock = GOTRegistry.slabSingle4;
			brickCrackedSlabMeta = 0;
			brickCrackedStairBlock = GOTRegistry.stairsSandstoneBrick;
			brickCrackedWallMeta = 15;
			brickMeta = 15;
			brickMossyMeta = 15;
			brickMossySlabBlock = GOTRegistry.slabSingle4;
			brickMossySlabMeta = 0;
			brickMossyStairBlock = GOTRegistry.stairsSandstoneBrick;
			brickMossyWallMeta = 15;
			brickSlabBlock = GOTRegistry.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = GOTRegistry.stairsSandstoneBrick;
			brickWallMeta = 15;
			cobbleBlock = Blocks.sandstone;
			cobbleSlabBlock = GOTRegistry.slabSingle7;
			cobbleSlabMeta = 2;
			cobbleStairBlock = Blocks.sandstone_stairs;
			pillarMeta = 5;
			rockBlock = Blocks.sandstone;
			rockMeta = 0;
			rockSlabBlock = GOTRegistry.slabSingle4;
			rockSlabDoubleBlock = GOTRegistry.brick1;
			rockSlabDoubleMeta = 15;
			rockSlabMeta = 0;
			rockStairBlock = GOTRegistry.stairsSandstoneBrickRed;
			rockWallBlock = GOTRegistry.wallStoneV;
			rockWallMeta = 4;
			brickCarved = GOTRegistry.brick3;
			brickCarvedMeta = 8;
		}
		int randomWood = random.nextInt(7);
		switch (randomWood) {
		case 0:
			plankBlock = GOTRegistry.planks1;
			plankMeta = 9;
			plankSlabBlock = GOTRegistry.woodSlabSingle2;
			plankSlabMeta = 1;
			plankStairBlock = GOTRegistry.stairsBeech;
			fenceBlock = GOTRegistry.fence;
			fenceMeta = 9;
			fenceGateBlock = GOTRegistry.fenceGateBeech;
			woodBeamBlock = GOTRegistry.woodBeam2;
			woodBeamMeta = 1;
			doorBlock = GOTRegistry.doorBeech;
			break;
		case 1:
			plankBlock = GOTRegistry.planks2;
			plankMeta = 2;
			plankSlabBlock = GOTRegistry.woodSlabSingle3;
			plankSlabMeta = 2;
			plankStairBlock = GOTRegistry.stairsCedar;
			fenceBlock = GOTRegistry.fence2;
			fenceMeta = 2;
			fenceGateBlock = GOTRegistry.fenceGateCedar;
			woodBeamBlock = GOTRegistry.woodBeam4;
			woodBeamMeta = 2;
			doorBlock = GOTRegistry.doorCedar;
			break;
		case 2:
			plankBlock = GOTRegistry.planks1;
			plankMeta = 8;
			plankSlabBlock = GOTRegistry.woodSlabSingle2;
			plankSlabMeta = 0;
			plankStairBlock = GOTRegistry.stairsAramant;
			fenceBlock = GOTRegistry.fence;
			fenceMeta = 8;
			fenceGateBlock = GOTRegistry.fenceGateAramant;
			woodBeamBlock = GOTRegistry.woodBeam2;
			woodBeamMeta = 0;
			doorBlock = GOTRegistry.doorAramant;
			break;
		case 3:
			plankBlock = Blocks.planks;
			plankMeta = 2;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 2;
			plankStairBlock = Blocks.birch_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 2;
			fenceGateBlock = GOTRegistry.fenceGateBirch;
			woodBeamBlock = GOTRegistry.woodBeamV1;
			woodBeamMeta = 2;
			doorBlock = GOTRegistry.doorBirch;
			break;
		default:
			plankBlock = Blocks.planks;
			plankMeta = 0;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 0;
			plankStairBlock = Blocks.oak_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 0;
			fenceGateBlock = Blocks.fence_gate;
			woodBeamBlock = GOTRegistry.woodBeamV1;
			woodBeamMeta = 0;
			doorBlock = Blocks.wooden_door;
			break;
		}
		if (random.nextBoolean()) {
			wallBlock = GOTRegistry.daub;
			wallMeta = 0;
		} else {
			wallBlock = plankBlock;
			wallMeta = plankMeta;
		}
		roofBlock = GOTRegistry.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTRegistry.stairsThatch;
		barsBlock = Blocks.iron_bars;
		bedBlock = GOTRegistry.strawBed;
		gateBlock = GOTRegistry.gateWesteros;
		plateBlock = GOTRegistry.ceramicPlateBlock;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
			cropMeta = 7;
			seedItem = Items.wheat_seeds;
		} else {
			int randomCrop = random.nextInt(6);
			switch (randomCrop) {
			case 0:
				cropBlock = Blocks.carrots;
				cropMeta = 7;
				seedItem = Items.carrot;
				break;
			case 1:
				cropBlock = Blocks.potatoes;
				cropMeta = 7;
				seedItem = Items.potato;
				break;
			case 2:
				cropBlock = GOTRegistry.lettuceCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.lettuce;
				break;
			case 3:
				cropBlock = GOTRegistry.cornStalk;
				cropMeta = 0;
				seedItem = Item.getItemFromBlock(GOTRegistry.cornStalk);
				break;
			case 4:
				cropBlock = GOTRegistry.leekCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.leek;
				break;
			case 5:
				cropBlock = GOTRegistry.turnipCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.turnip;
				break;
			}
		}
	}
}