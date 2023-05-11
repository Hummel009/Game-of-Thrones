package got.common.world.structure.westeros.common;

import got.common.database.GOTChestContents;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.*;

public abstract class GOTStructureWesterosBase extends GOTStructureBase {
	public static final Map<Kingdom, BannerType> BANNERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Block> TABLES = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> ARCHERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> BARTENDERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> CAPTAINS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> FARMERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> FARMHANDS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> MEN = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> SMITHS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends Entity>> SOLDIERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, Class<? extends WorldGenerator>> TOWERS = new EnumMap<>(Kingdom.class);
	public static final Map<Kingdom, GOTChestContents> CHESTS = new EnumMap<>(Kingdom.class);
	public static final Set<Kingdom> KINGDOMS_WITH_MAESTERS = EnumSet.of(Kingdom.ARRYN, Kingdom.CROWNLANDS, Kingdom.REACH, Kingdom.RIVERLANDS, Kingdom.STORMLANDS, Kingdom.WESTERLANDS, Kingdom.NORTH);
	public static final Set<Kingdom> KINGDOMS_WITH_SEPTONS = EnumSet.of(Kingdom.ARRYN, Kingdom.CROWNLANDS, Kingdom.RIVERLANDS, Kingdom.STORMLANDS, Kingdom.WESTERLANDS);

	static {
		ARCHERS.put(Kingdom.ARRYN, GOTEntityArrynSoldierArcher.class);
		ARCHERS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsLevymanArcher.class);
		ARCHERS.put(Kingdom.CROWNLANDS_RED, GOTEntityKingsguard.class);
		ARCHERS.put(Kingdom.DORNE, GOTEntityDorneSoldierArcher.class);
		ARCHERS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneSoldierArcher.class);
		ARCHERS.put(Kingdom.IRONBORN, GOTEntityIronbornSoldierArcher.class);
		ARCHERS.put(Kingdom.NORTH, GOTEntityNorthSoldierArcher.class);
		ARCHERS.put(Kingdom.REACH, GOTEntityReachSoldierArcher.class);
		ARCHERS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsSoldierArcher.class);
		ARCHERS.put(Kingdom.STORMLANDS, GOTEntityStormlandsSoldierArcher.class);
		ARCHERS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsSoldierArcher.class);
		BANNERS.put(Kingdom.ARRYN, GOTItemBanner.BannerType.ARRYN);
		BANNERS.put(Kingdom.CROWNLANDS, GOTItemBanner.BannerType.ROBERT);
		BANNERS.put(Kingdom.CROWNLANDS_RED, GOTItemBanner.BannerType.JOFFREY);
		BANNERS.put(Kingdom.DORNE, GOTItemBanner.BannerType.MARTELL);
		BANNERS.put(Kingdom.DRAGONSTONE, GOTItemBanner.BannerType.STANNIS);
		BANNERS.put(Kingdom.IRONBORN, GOTItemBanner.BannerType.GREYJOY);
		BANNERS.put(Kingdom.NORTH, GOTItemBanner.BannerType.ROBB);
		BANNERS.put(Kingdom.REACH, GOTItemBanner.BannerType.TYRELL);
		BANNERS.put(Kingdom.RIVERLANDS, GOTItemBanner.BannerType.TULLY);
		BANNERS.put(Kingdom.STORMLANDS, GOTItemBanner.BannerType.RENLY);
		BANNERS.put(Kingdom.WESTERLANDS, GOTItemBanner.BannerType.LANNISTER);
		BARTENDERS.put(Kingdom.ARRYN, GOTEntityArrynBartender.class);
		BARTENDERS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsBartender.class);
		BARTENDERS.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsBartender.class);
		BARTENDERS.put(Kingdom.DORNE, GOTEntityDorneBartender.class);
		BARTENDERS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneBartender.class);
		BARTENDERS.put(Kingdom.IRONBORN, GOTEntityIronbornBartender.class);
		BARTENDERS.put(Kingdom.NORTH, GOTEntityNorthBartender.class);
		BARTENDERS.put(Kingdom.REACH, GOTEntityReachBartender.class);
		BARTENDERS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsBartender.class);
		BARTENDERS.put(Kingdom.STORMLANDS, GOTEntityStormlandsBartender.class);
		BARTENDERS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsBartender.class);
		CAPTAINS.put(Kingdom.ARRYN, GOTEntityArrynCaptain.class);
		CAPTAINS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsCaptain.class);
		CAPTAINS.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsCaptain.class);
		CAPTAINS.put(Kingdom.DORNE, GOTEntityDorneCaptain.class);
		CAPTAINS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneCaptain.class);
		CAPTAINS.put(Kingdom.IRONBORN, GOTEntityIronbornCaptain.class);
		CAPTAINS.put(Kingdom.NORTH, GOTEntityNorthCaptain.class);
		CAPTAINS.put(Kingdom.REACH, GOTEntityReachCaptain.class);
		CAPTAINS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsCaptain.class);
		CAPTAINS.put(Kingdom.STORMLANDS, GOTEntityStormlandsCaptain.class);
		CAPTAINS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsCaptain.class);
		CHESTS.put(Kingdom.ARRYN, GOTChestContents.ARRYN);
		CHESTS.put(Kingdom.CROWNLANDS, GOTChestContents.CROWNLANDS);
		CHESTS.put(Kingdom.CROWNLANDS_RED, GOTChestContents.CROWNLANDS);
		CHESTS.put(Kingdom.DORNE, GOTChestContents.DORNE);
		CHESTS.put(Kingdom.DRAGONSTONE, GOTChestContents.DRAGONSTONE);
		CHESTS.put(Kingdom.IRONBORN, GOTChestContents.IRONBORN);
		CHESTS.put(Kingdom.NORTH, GOTChestContents.NORTH);
		CHESTS.put(Kingdom.REACH, GOTChestContents.REACH);
		CHESTS.put(Kingdom.RIVERLANDS, GOTChestContents.RIVERLANDS);
		CHESTS.put(Kingdom.STORMLANDS, GOTChestContents.STORMLANDS);
		CHESTS.put(Kingdom.WESTERLANDS, GOTChestContents.WESTERLANDS);
		FARMERS.put(Kingdom.ARRYN, GOTEntityArrynFarmer.class);
		FARMERS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsFarmer.class);
		FARMERS.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsFarmer.class);
		FARMERS.put(Kingdom.DORNE, GOTEntityDorneFarmer.class);
		FARMERS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneFarmer.class);
		FARMERS.put(Kingdom.IRONBORN, GOTEntityIronbornFarmer.class);
		FARMERS.put(Kingdom.NORTH, GOTEntityNorthFarmer.class);
		FARMERS.put(Kingdom.REACH, GOTEntityReachFarmer.class);
		FARMERS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsFarmer.class);
		FARMERS.put(Kingdom.STORMLANDS, GOTEntityStormlandsFarmer.class);
		FARMERS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsFarmer.class);
		FARMHANDS.put(Kingdom.ARRYN, GOTEntityArrynFarmhand.class);
		FARMHANDS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsFarmhand.class);
		FARMHANDS.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsFarmhand.class);
		FARMHANDS.put(Kingdom.DORNE, GOTEntityDorneFarmhand.class);
		FARMHANDS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneFarmhand.class);
		FARMHANDS.put(Kingdom.IRONBORN, GOTEntityIronbornFarmhand.class);
		FARMHANDS.put(Kingdom.NORTH, GOTEntityNorthFarmhand.class);
		FARMHANDS.put(Kingdom.REACH, GOTEntityReachFarmhand.class);
		FARMHANDS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsFarmhand.class);
		FARMHANDS.put(Kingdom.STORMLANDS, GOTEntityStormlandsFarmhand.class);
		FARMHANDS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsFarmhand.class);
		MEN.put(Kingdom.ARRYN, GOTEntityArrynMan.class);
		MEN.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsMan.class);
		MEN.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsMan.class);
		MEN.put(Kingdom.DORNE, GOTEntityDorneMan.class);
		MEN.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneMan.class);
		MEN.put(Kingdom.IRONBORN, GOTEntityIronbornMan.class);
		MEN.put(Kingdom.NORTH, GOTEntityNorthMan.class);
		MEN.put(Kingdom.REACH, GOTEntityReachMan.class);
		MEN.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsMan.class);
		MEN.put(Kingdom.STORMLANDS, GOTEntityStormlandsMan.class);
		MEN.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsMan.class);
		SMITHS.put(Kingdom.ARRYN, GOTEntityArrynBlacksmith.class);
		SMITHS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsBlacksmith.class);
		SMITHS.put(Kingdom.CROWNLANDS_RED, GOTEntityCrownlandsBlacksmith.class);
		SMITHS.put(Kingdom.DORNE, GOTEntityDorneBlacksmith.class);
		SMITHS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneBlacksmith.class);
		SMITHS.put(Kingdom.IRONBORN, GOTEntityIronbornBlacksmith.class);
		SMITHS.put(Kingdom.NORTH, GOTEntityNorthBlacksmith.class);
		SMITHS.put(Kingdom.REACH, GOTEntityReachBlacksmith.class);
		SMITHS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsBlacksmith.class);
		SMITHS.put(Kingdom.STORMLANDS, GOTEntityStormlandsBlacksmith.class);
		SMITHS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsBlacksmith.class);
		SOLDIERS.put(Kingdom.ARRYN, GOTEntityArrynSoldier.class);
		SOLDIERS.put(Kingdom.CROWNLANDS, GOTEntityCrownlandsLevyman.class);
		SOLDIERS.put(Kingdom.CROWNLANDS_RED, GOTEntityKingsguard.class);
		SOLDIERS.put(Kingdom.DORNE, GOTEntityDorneSoldier.class);
		SOLDIERS.put(Kingdom.DRAGONSTONE, GOTEntityDragonstoneSoldier.class);
		SOLDIERS.put(Kingdom.IRONBORN, GOTEntityIronbornSoldier.class);
		SOLDIERS.put(Kingdom.NORTH, GOTEntityNorthSoldier.class);
		SOLDIERS.put(Kingdom.REACH, GOTEntityReachSoldier.class);
		SOLDIERS.put(Kingdom.RIVERLANDS, GOTEntityRiverlandsSoldier.class);
		SOLDIERS.put(Kingdom.STORMLANDS, GOTEntityStormlandsSoldier.class);
		SOLDIERS.put(Kingdom.WESTERLANDS, GOTEntityWesterlandsSoldier.class);
		TABLES.put(Kingdom.ARRYN, GOTRegistry.tableArryn);
		TABLES.put(Kingdom.CROWNLANDS, GOTRegistry.tableCrownlands);
		TABLES.put(Kingdom.CROWNLANDS_RED, GOTRegistry.tableCrownlands);
		TABLES.put(Kingdom.DORNE, GOTRegistry.tableDorne);
		TABLES.put(Kingdom.DRAGONSTONE, GOTRegistry.tableDragonstone);
		TABLES.put(Kingdom.IRONBORN, GOTRegistry.tableIronborn);
		TABLES.put(Kingdom.NORTH, GOTRegistry.tableNorth);
		TABLES.put(Kingdom.REACH, GOTRegistry.tableReach);
		TABLES.put(Kingdom.RIVERLANDS, GOTRegistry.tableRiverlands);
		TABLES.put(Kingdom.STORMLANDS, GOTRegistry.tableStormlands);
		TABLES.put(Kingdom.WESTERLANDS, GOTRegistry.tableWesterlands);
		TOWERS.put(Kingdom.ARRYN, GOTStructureArrynTower.class);
		TOWERS.put(Kingdom.CROWNLANDS, GOTStructureCrownlandsTower.class);
		TOWERS.put(Kingdom.CROWNLANDS_RED, GOTStructureCrownlandsTower.class);
		TOWERS.put(Kingdom.DORNE, GOTStructureDorneTower.class);
		TOWERS.put(Kingdom.DRAGONSTONE, GOTStructureDragonstoneTower.class);
		TOWERS.put(Kingdom.IRONBORN, GOTStructureIronbornTower.class);
		TOWERS.put(Kingdom.NORTH, GOTStructureNorthTower.class);
		TOWERS.put(Kingdom.REACH, GOTStructureReachTower.class);
		TOWERS.put(Kingdom.RIVERLANDS, GOTStructureRiverlandsTower.class);
		TOWERS.put(Kingdom.STORMLANDS, GOTStructureStormlandsTower.class);
		TOWERS.put(Kingdom.WESTERLANDS, GOTStructureWesterlandsTower.class);
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

	protected GOTStructureWesterosBase(boolean flag) {
		super(flag);
	}

	public BannerType getBanner() {
		return BANNERS.get(kingdom);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(BARTENDERS.get(kingdom), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SMITHS.get(kingdom), world);
	}

	public GOTEntityNPC getCaptain(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(CAPTAINS.get(kingdom), world);
	}

	public GOTChestContents getChestContents() {
		return CHESTS.get(kingdom);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMERS.get(kingdom), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMHANDS.get(kingdom), world);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.westerosDagger), new ItemStack(GOTRegistry.westerosSpear), new ItemStack(GOTRegistry.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing)};
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(MEN.get(kingdom), world);
	}

	public GOTEntityNPC getSoldier(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SOLDIERS.get(kingdom), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(ARCHERS.get(kingdom), world);
	}

	public Block getTable() {
		return TABLES.get(kingdom);
	}

	public GOTStructureWesterosTower getTower(boolean notifyChanges) {
		return (GOTStructureWesterosTower) GOTReflection.newStructure(TOWERS.get(kingdom), notifyChanges);
	}

	public boolean hasMaester() {
		return KINGDOMS_WITH_MAESTERS.contains(kingdom);
	}

	public boolean hasSepton() {
		return KINGDOMS_WITH_SEPTONS.contains(kingdom);
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
		ARRYN, DORNE, CROWNLANDS, CROWNLANDS_RED, DRAGONSTONE, IRONBORN, NORTH, REACH, RIVERLANDS, STORMLANDS, WESTERLANDS
	}
}