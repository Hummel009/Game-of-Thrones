package got.common.world.structure.westeros.basis;

import java.util.*;
import java.util.Map.Entry;

import got.common.GOTConfig;
import got.common.database.GOTRegistry;
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
import got.common.world.structure.westeros.storage.GOTStructureArryn.GOTStructureArrynTower;
import got.common.world.structure.westeros.storage.GOTStructureCrownlands.GOTStructureCrownlandsTower;
import got.common.world.structure.westeros.storage.GOTStructureDorne.GOTStructureDorneTower;
import got.common.world.structure.westeros.storage.GOTStructureDragonstone.GOTStructureDragonstoneTower;
import got.common.world.structure.westeros.storage.GOTStructureIronborn.GOTStructureIronbornTower;
import got.common.world.structure.westeros.storage.GOTStructureNorth.GOTStructureNorthTower;
import got.common.world.structure.westeros.storage.GOTStructureReach.GOTStructureReachTower;
import got.common.world.structure.westeros.storage.GOTStructureRiverlands.GOTStructureRiverlandsTower;
import got.common.world.structure.westeros.storage.GOTStructureStormlands.GOTStructureStormlandsTower;
import got.common.world.structure.westeros.storage.GOTStructureWesterlands.GOTStructureWesterlandsTower;
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
	public Block brickCarved;
	public int brickCarvedMeta;
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
	public BannerType banner;
	public boolean isArryn = false;
	public boolean isCrownlands = false;
	public boolean isCrownlandsRed = false;
	public boolean isDorne = false;
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
		Map<Boolean, BannerType> theBanner = new HashMap<>();
		theBanner.put(isArryn, GOTItemBanner.BannerType.ARRYN);
		theBanner.put(isCrownlands, GOTItemBanner.BannerType.ROBERT);
		theBanner.put(isCrownlandsRed, GOTItemBanner.BannerType.JOFFREY);
		theBanner.put(isDragonstone, GOTItemBanner.BannerType.STANNIS);
		theBanner.put(isDorne, GOTItemBanner.BannerType.MARTELL);
		theBanner.put(isIronborn, GOTItemBanner.BannerType.GREYJOY);
		theBanner.put(isNorth, GOTItemBanner.BannerType.ROBB);
		theBanner.put(isReach, GOTItemBanner.BannerType.TYRELL);
		theBanner.put(isRiverlands, GOTItemBanner.BannerType.TULLY);
		theBanner.put(isStormlands, GOTItemBanner.BannerType.RENLY);
		theBanner.put(isWesterlands, GOTItemBanner.BannerType.LANNISTER);

		for (Entry<Boolean, BannerType> npc : theBanner.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBartender(World world) {
		Map<Boolean, GOTEntityNPC> theBartender = new HashMap<>();
		theBartender.put(isArryn, new GOTEntityArrynBartender(world));
		theBartender.put(isCrownlands, new GOTEntityCrownlandsBartender(world));
		theBartender.put(isCrownlandsRed, new GOTEntityCrownlandsBartender(world));
		theBartender.put(isDragonstone, new GOTEntityDragonstoneBartender(world));
		theBartender.put(isDorne, new GOTEntityDorneBartender(world));
		theBartender.put(isIronborn, new GOTEntityIronbornBartender(world));
		theBartender.put(isNorth, new GOTEntityNorthBartender(world));
		theBartender.put(isReach, new GOTEntityReachBartender(world));
		theBartender.put(isRiverlands, new GOTEntityRiverlandsBartender(world));
		theBartender.put(isStormlands, new GOTEntityStormlandsBartender(world));
		theBartender.put(isWesterlands, new GOTEntityWesterlandsBartender(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theBartender.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBlacksmith(World world) {
		Map<Boolean, GOTEntityNPC> theBlacksmith = new HashMap<>();
		theBlacksmith.put(isArryn, new GOTEntityArrynBlacksmith(world));
		theBlacksmith.put(isCrownlands, new GOTEntityCrownlandsBlacksmith(world));
		theBlacksmith.put(isCrownlandsRed, new GOTEntityCrownlandsBlacksmith(world));
		theBlacksmith.put(isDragonstone, new GOTEntityDragonstoneBlacksmith(world));
		theBlacksmith.put(isDorne, new GOTEntityDorneBlacksmith(world));
		theBlacksmith.put(isIronborn, new GOTEntityIronbornBlacksmith(world));
		theBlacksmith.put(isNorth, new GOTEntityNorthBlacksmith(world));
		theBlacksmith.put(isReach, new GOTEntityReachBlacksmith(world));
		theBlacksmith.put(isRiverlands, new GOTEntityRiverlandsBlacksmith(world));
		theBlacksmith.put(isStormlands, new GOTEntityStormlandsBlacksmith(world));
		theBlacksmith.put(isWesterlands, new GOTEntityWesterlandsBlacksmith(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theBlacksmith.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getCaptain(World world) {
		Map<Boolean, GOTEntityNPC> theCaptain = new HashMap<>();
		theCaptain.put(isArryn, new GOTEntityArrynCaptain(world));
		theCaptain.put(isCrownlands, new GOTEntityCrownlandsCaptain(world));
		theCaptain.put(isCrownlandsRed, new GOTEntityCrownlandsCaptain(world));
		theCaptain.put(isDragonstone, new GOTEntityDragonstoneCaptain(world));
		theCaptain.put(isDorne, new GOTEntityDorneCaptain(world));
		theCaptain.put(isIronborn, new GOTEntityIronbornCaptain(world));
		theCaptain.put(isNorth, new GOTEntityNorthCaptain(world));
		theCaptain.put(isReach, new GOTEntityReachCaptain(world));
		theCaptain.put(isRiverlands, new GOTEntityRiverlandsCaptain(world));
		theCaptain.put(isStormlands, new GOTEntityStormlandsCaptain(world));
		theCaptain.put(isWesterlands, new GOTEntityWesterlandsCaptain(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theCaptain.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmer(World world) {
		Map<Boolean, GOTEntityNPC> theFarmer = new HashMap<>();
		theFarmer.put(isArryn, new GOTEntityArrynFarmer(world));
		theFarmer.put(isCrownlands, new GOTEntityCrownlandsFarmer(world));
		theFarmer.put(isCrownlandsRed, new GOTEntityCrownlandsFarmer(world));
		theFarmer.put(isDragonstone, new GOTEntityDragonstoneFarmer(world));
		theFarmer.put(isDorne, new GOTEntityDorneFarmer(world));
		theFarmer.put(isIronborn, new GOTEntityIronbornFarmer(world));
		theFarmer.put(isNorth, new GOTEntityNorthFarmer(world));
		theFarmer.put(isReach, new GOTEntityReachFarmer(world));
		theFarmer.put(isRiverlands, new GOTEntityRiverlandsFarmer(world));
		theFarmer.put(isStormlands, new GOTEntityStormlandsFarmer(world));
		theFarmer.put(isWesterlands, new GOTEntityWesterlandsFarmer(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theFarmer.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmhand(World world) {
		Map<Boolean, GOTEntityNPC> theFarmhand = new HashMap<>();
		theFarmhand.put(isArryn, new GOTEntityArrynFarmhand(world));
		theFarmhand.put(isCrownlands, new GOTEntityCrownlandsFarmhand(world));
		theFarmhand.put(isCrownlandsRed, new GOTEntityCrownlandsFarmhand(world));
		theFarmhand.put(isDragonstone, new GOTEntityDragonstoneFarmhand(world));
		theFarmhand.put(isDorne, new GOTEntityDorneFarmhand(world));
		theFarmhand.put(isIronborn, new GOTEntityIronbornFarmhand(world));
		theFarmhand.put(isNorth, new GOTEntityNorthFarmhand(world));
		theFarmhand.put(isReach, new GOTEntityReachFarmhand(world));
		theFarmhand.put(isRiverlands, new GOTEntityRiverlandsFarmhand(world));
		theFarmhand.put(isStormlands, new GOTEntityStormlandsFarmhand(world));
		theFarmhand.put(isWesterlands, new GOTEntityWesterlandsFarmhand(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theFarmhand.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.westerosDagger), new ItemStack(GOTRegistry.westerosSpear), new ItemStack(GOTRegistry.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing) };
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getMan(World world) {
		Map<Boolean, GOTEntityNPC> theMan = new HashMap<>();
		theMan.put(isArryn, new GOTEntityArrynMan(world));
		theMan.put(isCrownlands, new GOTEntityCrownlandsMan(world));
		theMan.put(isCrownlandsRed, new GOTEntityCrownlandsMan(world));
		theMan.put(isDragonstone, new GOTEntityDragonstoneMan(world));
		theMan.put(isDorne, new GOTEntityDorneMan(world));
		theMan.put(isIronborn, new GOTEntityIronbornMan(world));
		theMan.put(isNorth, new GOTEntityNorthMan(world));
		theMan.put(isReach, new GOTEntityReachMan(world));
		theMan.put(isRiverlands, new GOTEntityRiverlandsMan(world));
		theMan.put(isStormlands, new GOTEntityStormlandsMan(world));
		theMan.put(isWesterlands, new GOTEntityWesterlandsMan(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theMan.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldier(World world) {
		Map<Boolean, GOTEntityNPC> theSoldier = new HashMap<>();
		theSoldier.put(isArryn, new GOTEntityArrynSoldier(world));
		theSoldier.put(isCrownlands, new GOTEntityCrownlandsLevyman(world));
		theSoldier.put(isCrownlandsRed, new GOTEntityKingsguard(world));
		theSoldier.put(isDragonstone, new GOTEntityDragonstoneSoldier(world));
		theSoldier.put(isDorne, new GOTEntityDorneSoldier(world));
		theSoldier.put(isIronborn, new GOTEntityIronbornSoldier(world));
		theSoldier.put(isNorth, new GOTEntityNorthSoldier(world));
		theSoldier.put(isReach, new GOTEntityReachSoldier(world));
		theSoldier.put(isRiverlands, new GOTEntityRiverlandsSoldier(world));
		theSoldier.put(isStormlands, new GOTEntityStormlandsSoldier(world));
		theSoldier.put(isWesterlands, new GOTEntityWesterlandsSoldier(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theSoldier.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		Map<Boolean, GOTEntityNPC> theSoldierArcher = new HashMap<>();
		theSoldierArcher.put(isArryn, new GOTEntityArrynSoldierArcher(world));
		theSoldierArcher.put(isCrownlands, new GOTEntityCrownlandsLevymanArcher(world));
		theSoldierArcher.put(isCrownlandsRed, new GOTEntityKingsguard(world));
		theSoldierArcher.put(isDragonstone, new GOTEntityDragonstoneSoldierArcher(world));
		theSoldierArcher.put(isDorne, new GOTEntityDorneSoldierArcher(world));
		theSoldierArcher.put(isIronborn, new GOTEntityIronbornSoldierArcher(world));
		theSoldierArcher.put(isNorth, new GOTEntityNorthSoldierArcher(world));
		theSoldierArcher.put(isReach, new GOTEntityReachSoldierArcher(world));
		theSoldierArcher.put(isRiverlands, new GOTEntityRiverlandsSoldierArcher(world));
		theSoldierArcher.put(isStormlands, new GOTEntityStormlandsSoldierArcher(world));
		theSoldierArcher.put(isWesterlands, new GOTEntityWesterlandsSoldierArcher(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theSoldierArcher.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public Block getTable() {
		Map<Boolean, Block> theTable = new HashMap<>();
		theTable.put(isArryn, GOTRegistry.tableArryn);
		theTable.put(isCrownlands, GOTRegistry.tableCrownlands);
		theTable.put(isCrownlandsRed, GOTRegistry.tableCrownlands);
		theTable.put(isDragonstone, GOTRegistry.tableDragonstone);
		theTable.put(isDorne, GOTRegistry.tableDorne);
		theTable.put(isIronborn, GOTRegistry.tableIronborn);
		theTable.put(isNorth, GOTRegistry.tableNorth);
		theTable.put(isReach, GOTRegistry.tableReach);
		theTable.put(isRiverlands, GOTRegistry.tableRiverlands);
		theTable.put(isStormlands, GOTRegistry.tableStormlands);
		theTable.put(isWesterlands, GOTRegistry.tableWesterlands);

		for (Entry<Boolean, Block> npc : theTable.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTStructureWesterosTower getTower(boolean notifyChanges) {
		Map<Boolean, GOTStructureWesterosTower> theTower = new HashMap<>();
		theTower.put(isArryn, new GOTStructureArrynTower(notifyChanges));
		theTower.put(isCrownlands, new GOTStructureCrownlandsTower(notifyChanges));
		theTower.put(isCrownlandsRed, new GOTStructureCrownlandsTower(notifyChanges));
		theTower.put(isDragonstone, new GOTStructureDragonstoneTower(notifyChanges));
		theTower.put(isDorne, new GOTStructureDorneTower(notifyChanges));
		theTower.put(isIronborn, new GOTStructureIronbornTower(notifyChanges));
		theTower.put(isNorth, new GOTStructureNorthTower(notifyChanges));
		theTower.put(isReach, new GOTStructureReachTower(notifyChanges));
		theTower.put(isRiverlands, new GOTStructureRiverlandsTower(notifyChanges));
		theTower.put(isStormlands, new GOTStructureStormlandsTower(notifyChanges));
		theTower.put(isWesterlands, new GOTStructureWesterlandsTower(notifyChanges));

		for (Entry<Boolean, GOTStructureWesterosTower> tow : theTower.entrySet()) {
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
		/*if (GOTConfig.enableColouredRoofs) {
			overrideRoofs();
		}*/
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

	public void overrideRoofs() {
		if (isDorne) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 1;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle1;
            this.brick2SlabMeta = 1;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedOrange;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 1;
		} else if (isStormlands) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 13;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedGreen;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 13;
		} else if (isReach) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 5;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle1;
            this.brick2SlabMeta = 5;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedLime;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 5;
		} else if (isWesterlands) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 14;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 6;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedRed;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 14;
		} else if (isRiverlands) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 3;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle1;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedLightBlue;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 3;
		} else if (isArryn) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 11;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 3;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedBlue;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 11;
		} else if (isCrownlands) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 12;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 4;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedBrown;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 12;
		} else if (isDragonstone) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 7;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle1;
            this.brick2SlabMeta = 7;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedGray;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 7;
		} else if (isNorth) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 0;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle1;
            this.brick2SlabMeta = 0;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedWhite;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 0;
		} else if (isIronborn) {
            this.brick2Block = GOTRegistry.clayTileDyed;
            this.brick2Meta = 15;
            this.brick2SlabBlock = GOTRegistry.slabClayTileDyedSingle2;
            this.brick2SlabMeta = 7;
            this.brick2StairBlock = GOTRegistry.stairsClayTileDyedBlack;
            this.brick2WallBlock = GOTRegistry.wallClayTileDyed;
            this.brick2WallMeta = 15;
		}
	}
}