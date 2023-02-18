package got.common.world.structure.westeros.common;

import java.util.*;

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
import got.common.util.GOTReflection;
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
import net.minecraft.entity.Entity;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class GOTStructureWesterosBase extends GOTStructureBase {
	public static Map<Kingdom, BannerType> banners = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> bartenders = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> blacksmiths = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> captains = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, GOTChestContents> chests = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> farmers = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> farmhands = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> men = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> soldiers = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends Entity>> archers = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Block> tables = new EnumMap<>(Kingdom.class);
	public static Map<Kingdom, Class<? extends WorldGenerator>> towers = new EnumMap<>(Kingdom.class);
	static {
		archers.put(Kingdom.ARRYN, GOTEntityArrynSoldierArcher.class);
		archers.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsLevymanArcher.class);
		archers.put(Kingdom.CROWNLANDS_RED, GOTEntityKingsguard.class);
		archers.put(Kingdom.DORNE, GOTEntityDorneSoldierArcher.class);
		archers.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneSoldierArcher.class);
		archers.put(Kingdom.IRONBORN, GOTEntityIronbornSoldierArcher.class);
		archers.put(Kingdom.NORTH, GOTEntityNorthSoldierArcher.class);
		archers.put(Kingdom.REACH, GOTEntityReachSoldierArcher.class);
		archers.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsSoldierArcher.class);
		archers.put(Kingdom.STORMLANDS, GOTEntityStormlandsSoldierArcher.class);
		archers.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsSoldierArcher.class);
		banners.put(Kingdom.ARRYN, GOTItemBanner.BannerType.ARRYN);
		banners.put(Kingdom.CROWNLANDS, GOTItemBanner.BannerType.ROBERT);
		banners.put(Kingdom.CROWNLANDS_RED, GOTItemBanner.BannerType.JOFFREY);
		banners.put(Kingdom.DORNE, GOTItemBanner.BannerType.MARTELL);
		banners.put(Kingdom.DRAGONSTONE, GOTItemBanner.BannerType.STANNIS);
		banners.put(Kingdom.IRONBORN, GOTItemBanner.BannerType.GREYJOY);
		banners.put(Kingdom.NORTH, GOTItemBanner.BannerType.ROBB);
		banners.put(Kingdom.REACH, GOTItemBanner.BannerType.TYRELL);
		banners.put(Kingdom.RIVERLANDS, GOTItemBanner.BannerType.TULLY);
		banners.put(Kingdom.STORMLANDS, GOTItemBanner.BannerType.RENLY);
		banners.put(Kingdom.WESTERLANDS, GOTItemBanner.BannerType.LANNISTER);
		bartenders.put(Kingdom.ARRYN, GOTEntityArrynBartender.class);
		bartenders.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsBartender.class);
		bartenders.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsBartender.class);
		bartenders.put(Kingdom.DORNE, GOTEntityDorneBartender.class);
		bartenders.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneBartender.class);
		bartenders.put(Kingdom.IRONBORN, GOTEntityIronbornBartender.class);
		bartenders.put(Kingdom.NORTH, GOTEntityNorthBartender.class);
		bartenders.put(Kingdom.REACH, GOTEntityReachBartender.class);
		bartenders.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsBartender.class);
		bartenders.put(Kingdom.STORMLANDS, GOTEntityStormlandsBartender.class);
		bartenders.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsBartender.class);
		blacksmiths.put(Kingdom.ARRYN, GOTEntityArrynBlacksmith.class);
		blacksmiths.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsBlacksmith.class);
		blacksmiths.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsBlacksmith.class);
		blacksmiths.put(Kingdom.DORNE, GOTEntityDorneBlacksmith.class);
		blacksmiths.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneBlacksmith.class);
		blacksmiths.put(Kingdom.IRONBORN, GOTEntityIronbornBlacksmith.class);
		blacksmiths.put(Kingdom.NORTH, GOTEntityNorthBlacksmith.class);
		blacksmiths.put(Kingdom.REACH, GOTEntityReachBlacksmith.class);
		blacksmiths.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsBlacksmith.class);
		blacksmiths.put(Kingdom.STORMLANDS, GOTEntityStormlandsBlacksmith.class);
		blacksmiths.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsBlacksmith.class);
		captains.put(Kingdom.ARRYN, GOTEntityArrynCaptain.class);
		captains.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsCaptain.class);
		captains.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsCaptain.class);
		captains.put(Kingdom.DORNE, GOTEntityDorneCaptain.class);
		captains.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneCaptain.class);
		captains.put(Kingdom.IRONBORN, GOTEntityIronbornCaptain.class);
		captains.put(Kingdom.NORTH, GOTEntityNorthCaptain.class);
		captains.put(Kingdom.REACH, GOTEntityReachCaptain.class);
		captains.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsCaptain.class);
		captains.put(Kingdom.STORMLANDS, GOTEntityStormlandsCaptain.class);
		captains.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsCaptain.class);
		chests.put(Kingdom.ARRYN, GOTChestContents.ARRYN);
		chests.put(Kingdom.CROWNLANDS, GOTChestContents.CROWNLANDS);
		chests.put(Kingdom.CROWNLANDS_RED, GOTChestContents.CROWNLANDS);
		chests.put(Kingdom.DORNE, GOTChestContents.DORNE);
		chests.put(Kingdom.DRAGONSTONE, GOTChestContents.DRAGONSTONE);
		chests.put(Kingdom.IRONBORN, GOTChestContents.IRONBORN);
		chests.put(Kingdom.NORTH, GOTChestContents.NORTH);
		chests.put(Kingdom.REACH, GOTChestContents.REACH);
		chests.put(Kingdom.RIVERLANDS, GOTChestContents.RIVERLANDS);
		chests.put(Kingdom.STORMLANDS, GOTChestContents.STORMLANDS);
		chests.put(Kingdom.WESTERLANDS, GOTChestContents.WESTERLANDS);
		farmers.put(Kingdom.ARRYN, GOTEntityArrynFarmer.class);
		farmers.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsFarmer.class);
		farmers.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsFarmer.class);
		farmers.put(Kingdom.DORNE, GOTEntityDorneFarmer.class);
		farmers.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneFarmer.class);
		farmers.put(Kingdom.IRONBORN, GOTEntityIronbornFarmer.class);
		farmers.put(Kingdom.NORTH, GOTEntityNorthFarmer.class);
		farmers.put(Kingdom.REACH, GOTEntityReachFarmer.class);
		farmers.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsFarmer.class);
		farmers.put(Kingdom.STORMLANDS, GOTEntityStormlandsFarmer.class);
		farmers.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsFarmer.class);
		farmhands.put(Kingdom.ARRYN, GOTEntityArrynFarmhand.class);
		farmhands.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsFarmhand.class);
		farmhands.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsFarmhand.class);
		farmhands.put(Kingdom.DORNE, GOTEntityDorneFarmhand.class);
		farmhands.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneFarmhand.class);
		farmhands.put(Kingdom.IRONBORN, GOTEntityIronbornFarmhand.class);
		farmhands.put(Kingdom.NORTH, GOTEntityNorthFarmhand.class);
		farmhands.put(Kingdom.REACH, GOTEntityReachFarmhand.class);
		farmhands.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsFarmhand.class);
		farmhands.put(Kingdom.STORMLANDS, GOTEntityStormlandsFarmhand.class);
		farmhands.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsFarmhand.class);
		men.put(Kingdom.ARRYN, GOTEntityArrynMan.class);
		men.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsMan.class);
		men.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsMan.class);
		men.put(Kingdom.DORNE, GOTEntityDorneMan.class);
		men.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneMan.class);
		men.put(Kingdom.IRONBORN, GOTEntityIronbornMan.class);
		men.put(Kingdom.NORTH, GOTEntityNorthMan.class);
		men.put(Kingdom.REACH, GOTEntityReachMan.class);
		men.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsMan.class);
		men.put(Kingdom.STORMLANDS, GOTEntityStormlandsMan.class);
		men.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsMan.class);
		soldiers.put(Kingdom.ARRYN, GOTEntityArrynSoldier.class);
		soldiers.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsLevyman.class);
		soldiers.put(Kingdom.CROWNLANDS_RED, GOTEntityKingsguard.class);
		soldiers.put(Kingdom.DORNE, GOTEntityDorneSoldier.class);
		soldiers.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneSoldier.class);
		soldiers.put(Kingdom.IRONBORN, GOTEntityIronbornSoldier.class);
		soldiers.put(Kingdom.NORTH, GOTEntityNorthSoldier.class);
		soldiers.put(Kingdom.REACH, GOTEntityReachSoldier.class);
		soldiers.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsSoldier.class);
		soldiers.put(Kingdom.STORMLANDS, GOTEntityStormlandsSoldier.class);
		soldiers.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsSoldier.class);
		tables.put(Kingdom.ARRYN, GOTRegistry.tableArryn);
		tables.put(Kingdom.CROWNLANDS, GOTRegistry.tableCrownlands);
		tables.put(Kingdom.CROWNLANDS_RED, GOTRegistry.tableCrownlands);
		tables.put(Kingdom.DORNE, GOTRegistry.tableDorne);
		tables.put(Kingdom.DRAGONSTONE, GOTRegistry.tableDragonstone);
		tables.put(Kingdom.IRONBORN, GOTRegistry.tableIronborn);
		tables.put(Kingdom.NORTH, GOTRegistry.tableNorth);
		tables.put(Kingdom.REACH, GOTRegistry.tableReach);
		tables.put(Kingdom.RIVERLANDS, GOTRegistry.tableRiverlands);
		tables.put(Kingdom.STORMLANDS, GOTRegistry.tableStormlands);
		tables.put(Kingdom.WESTERLANDS, GOTRegistry.tableWesterlands);
		towers.put(Kingdom.ARRYN, GOTStructureArrynTower.class);
		towers.put(Kingdom.CROWNLANDS, GOTStructureCrownlandsTower.class);
		towers.put(Kingdom.CROWNLANDS_RED, GOTStructureCrownlandsTower.class);
		towers.put(Kingdom.DORNE, GOTStructureDorneTower.class);
		towers.put(Kingdom.DRAGONSTONE, GOTStructureDragonstoneTower.class);
		towers.put(Kingdom.IRONBORN, GOTStructureIronbornTower.class);
		towers.put(Kingdom.NORTH, GOTStructureNorthTower.class);
		towers.put(Kingdom.REACH, GOTStructureReachTower.class);
		towers.put(Kingdom.RIVERLANDS, GOTStructureRiverlandsTower.class);
		towers.put(Kingdom.STORMLANDS, GOTStructureStormlandsTower.class);
		towers.put(Kingdom.WESTERLANDS, GOTStructureWesterlandsTower.class);
	}
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
	public BannerType bannerType;
	public Kingdom kingdom;

	public GOTStructureWesterosBase(boolean flag) {
		super(flag);
	}

	public BannerType getBanner() {
		return banners.get(kingdom);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(bartenders.get(kingdom), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(blacksmiths.get(kingdom), world);
	}

	public GOTEntityNPC getCaptain(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(captains.get(kingdom), world);
	}

	public GOTChestContents getChestContents() {
		return chests.get(kingdom);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmers.get(kingdom), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmhands.get(kingdom), world);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.westerosDagger), new ItemStack(GOTRegistry.westerosSpear), new ItemStack(GOTRegistry.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing) };
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(men.get(kingdom), world);
	}

	public GOTEntityNPC getSoldier(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(soldiers.get(kingdom), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(archers.get(kingdom), world);
	}

	public Block getTable() {
		return tables.get(kingdom);
	}

	public GOTStructureWesterosTower getTower(boolean notifyChanges) {
		return (GOTStructureWesterosTower) GOTReflection.newStructure(towers.get(kingdom), notifyChanges);
	}

	public boolean hasMaester() {
		return kingdom == Kingdom.ARRYN || kingdom == Kingdom.CROWNLANDS || kingdom == Kingdom.REACH || kingdom == Kingdom.RIVERLANDS || kingdom == Kingdom.STORMLANDS || kingdom == Kingdom.WESTERLANDS || kingdom == Kingdom.NORTH;
	}

	public boolean hasSepton() {
		return kingdom == Kingdom.ARRYN || kingdom == Kingdom.CROWNLANDS || kingdom == Kingdom.RIVERLANDS || kingdom == Kingdom.STORMLANDS || kingdom == Kingdom.WESTERLANDS;
	}

	public GOTStructureBase setGranite() {
		kingdom = Kingdom.CROWNLANDS_RED;
		return this;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		bannerType = getBanner();
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
		if (kingdom == Kingdom.CROWNLANDS_RED) {
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
		if (kingdom == Kingdom.DORNE) {
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

	public enum Kingdom {
		ARRYN, DORNE, CROWNLANDS, CROWNLANDS_RED, DRAGONSTONE, IRONBORN, NORTH, REACH, RIVERLANDS, STORMLANDS, WESTERLANDS;
	}
}