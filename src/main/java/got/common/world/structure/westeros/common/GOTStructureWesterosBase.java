package got.common.world.structure.westeros.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
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
		TABLES.put(Kingdom.ARRYN, GOTBlocks.tableArryn);
		TABLES.put(Kingdom.CROWNLANDS, GOTBlocks.tableCrownlands);
		TABLES.put(Kingdom.CROWNLANDS_RED, GOTBlocks.tableCrownlands);
		TABLES.put(Kingdom.DORNE, GOTBlocks.tableDorne);
		TABLES.put(Kingdom.DRAGONSTONE, GOTBlocks.tableDragonstone);
		TABLES.put(Kingdom.IRONBORN, GOTBlocks.tableIronborn);
		TABLES.put(Kingdom.NORTH, GOTBlocks.tableNorth);
		TABLES.put(Kingdom.REACH, GOTBlocks.tableReach);
		TABLES.put(Kingdom.RIVERLANDS, GOTBlocks.tableRiverlands);
		TABLES.put(Kingdom.STORMLANDS, GOTBlocks.tableStormlands);
		TABLES.put(Kingdom.WESTERLANDS, GOTBlocks.tableWesterlands);
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
		ItemStack[] items = {new ItemStack(GOTItems.westerosDagger), new ItemStack(GOTItems.westerosSpear), new ItemStack(GOTItems.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.ironPike), new ItemStack(GOTItems.ironCrossbow), new ItemStack(GOTItems.goldRing), new ItemStack(GOTItems.silverRing), new ItemStack(GOTItems.copperRing), new ItemStack(GOTItems.bronzeRing)};
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

	public boolean hasSandstone() {
		return kingdom == Kingdom.DORNE;
	}

	public boolean hasGranite() {
		return kingdom == Kingdom.CROWNLANDS_RED;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		barsBlock = Blocks.iron_bars;
		bedBlock = GOTBlocks.strawBed;
		brick2Block = GOTBlocks.brick2;
		brick2Meta = 11;
		brick2SlabBlock = GOTBlocks.slabSingle5;
		brick2SlabMeta = 3;
		brick2StairBlock = GOTBlocks.stairsBasaltWesterosBrick;
		brick2WallBlock = GOTBlocks.wallStone2;
		brick2WallMeta = 10;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		gateBlock = GOTBlocks.gateWesteros;
		pillar2Block = GOTBlocks.pillar1;
		pillar2Meta = 9;
		pillarBlock = GOTBlocks.pillar1;
		plateBlock = GOTBlocks.ceramicPlateBlock;
		rockWallBlock = GOTBlocks.wallStone1;
		rockWallMeta = 2;
		roofBlock = GOTBlocks.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTBlocks.stairsThatch;
		bannerType = getBanner();
		tableBlock = getTable();
		if (hasSandstone()) {
			brickBlock = GOTBlocks.brick1;
			brickCarved = GOTBlocks.brick3;
			brickCarvedMeta = 8;
			brickCrackedBlock = GOTBlocks.brick1;
			brickCrackedMeta = 15;
			brickCrackedSlabBlock = GOTBlocks.slabSingle4;
			brickCrackedSlabMeta = 0;
			brickCrackedStairBlock = GOTBlocks.stairsSandstoneBrick;
			brickCrackedWallBlock = GOTBlocks.wallStone1;
			brickCrackedWallMeta = 15;
			brickMeta = 15;
			brickMossyBlock = GOTBlocks.brick1;
			brickMossyMeta = 15;
			brickMossySlabBlock = GOTBlocks.slabSingle4;
			brickMossySlabMeta = 0;
			brickMossyStairBlock = GOTBlocks.stairsSandstoneBrick;
			brickMossyWallBlock = GOTBlocks.wallStone1;
			brickMossyWallMeta = 15;
			brickSlabBlock = GOTBlocks.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = GOTBlocks.stairsAndesiteBrick;
			brickWallBlock = GOTBlocks.wallStone1;
			brickWallMeta = 3;
			pillarMeta = 5;
			rockBlock = Blocks.sandstone;
			rockMeta = 0;
			rockSlabBlock = GOTBlocks.slabSingle4;
			rockSlabDoubleBlock = GOTBlocks.brick1;
			rockSlabDoubleMeta = 15;
			rockSlabMeta = 0;
			rockStairBlock = GOTBlocks.stairsSandstoneBrickRed;
		} else if (hasGranite()) {
			brickBlock = GOTBlocks.brick2;
			brickCarved = GOTBlocks.brick3;
			brickCarvedMeta = 1;
			brickCrackedBlock = GOTBlocks.brick1;
			brickCrackedMeta = 3;
			brickCrackedSlabBlock = GOTBlocks.slabSingle1;
			brickCrackedSlabMeta = 5;
			brickCrackedStairBlock = GOTBlocks.stairsAndesiteBrickCracked;
			brickCrackedWallBlock = GOTBlocks.wallStone1;
			brickCrackedWallMeta = 5;
			brickMeta = 2;
			brickMossyBlock = GOTBlocks.brick1;
			brickMossyMeta = 2;
			brickMossySlabBlock = GOTBlocks.slabSingle1;
			brickMossySlabMeta = 4;
			brickMossyStairBlock = GOTBlocks.stairsAndesiteBrickMossy;
			brickMossyWallBlock = GOTBlocks.wallStone1;
			brickMossyWallMeta = 4;
			brickSlabBlock = GOTBlocks.slabSingle3;
			brickSlabMeta = 6;
			brickStairBlock = GOTBlocks.stairsGraniteBrick;
			brickWallBlock = GOTBlocks.wallStone2;
			brickWallMeta = 3;
			pillarMeta = 4;
			rockBlock = GOTBlocks.rock;
			rockMeta = 4;
			rockSlabBlock = GOTBlocks.slabSingle3;
			rockSlabDoubleBlock = GOTBlocks.smoothStone;
			rockSlabDoubleMeta = 4;
			rockSlabMeta = 6;
			rockStairBlock = GOTBlocks.stairsAndesite;
		} else {
			brickBlock = GOTBlocks.brick1;
			brickCarved = GOTBlocks.brick1;
			brickCarvedMeta = 5;
			brickCrackedBlock = GOTBlocks.brick1;
			brickCrackedMeta = 3;
			brickCrackedSlabBlock = GOTBlocks.slabSingle1;
			brickCrackedSlabMeta = 5;
			brickCrackedStairBlock = GOTBlocks.stairsAndesiteBrickCracked;
			brickCrackedWallBlock = GOTBlocks.wallStone1;
			brickCrackedWallMeta = 5;
			brickMeta = 1;
			brickMossyBlock = GOTBlocks.brick1;
			brickMossyMeta = 2;
			brickMossySlabBlock = GOTBlocks.slabSingle1;
			brickMossySlabMeta = 4;
			brickMossyStairBlock = GOTBlocks.stairsAndesiteBrickMossy;
			brickMossyWallBlock = GOTBlocks.wallStone1;
			brickMossyWallMeta = 4;
			brickSlabBlock = GOTBlocks.slabSingle1;
			brickSlabMeta = 3;
			brickStairBlock = GOTBlocks.stairsAndesiteBrick;
			brickWallBlock = GOTBlocks.wallStone1;
			brickWallMeta = 3;
			pillarMeta = 6;
			rockBlock = GOTBlocks.rock;
			rockMeta = 1;
			rockSlabBlock = GOTBlocks.slabSingle1;
			rockSlabDoubleBlock = GOTBlocks.slabDouble1;
			rockSlabDoubleMeta = 2;
			rockSlabMeta = 2;
			rockStairBlock = GOTBlocks.stairsAndesite;
		}
		switch (random.nextInt(7)) {
			case 0:
				plankBlock = GOTBlocks.planks1;
				plankMeta = 9;
				plankSlabBlock = GOTBlocks.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = GOTBlocks.stairsBeech;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 9;
				fenceGateBlock = GOTBlocks.fenceGateBeech;
				woodBeamBlock = GOTBlocks.woodBeam2;
				woodBeamMeta = 1;
				doorBlock = GOTBlocks.doorBeech;
				break;
			case 1:
				plankBlock = GOTBlocks.planks2;
				plankMeta = 2;
				plankSlabBlock = GOTBlocks.woodSlabSingle3;
				plankSlabMeta = 2;
				plankStairBlock = GOTBlocks.stairsCedar;
				fenceBlock = GOTBlocks.fence2;
				fenceMeta = 2;
				fenceGateBlock = GOTBlocks.fenceGateCedar;
				woodBeamBlock = GOTBlocks.woodBeam4;
				woodBeamMeta = 2;
				doorBlock = GOTBlocks.doorCedar;
				break;
			case 2:
				plankBlock = GOTBlocks.planks1;
				plankMeta = 8;
				plankSlabBlock = GOTBlocks.woodSlabSingle2;
				plankSlabMeta = 0;
				plankStairBlock = GOTBlocks.stairsAramant;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 8;
				fenceGateBlock = GOTBlocks.fenceGateAramant;
				woodBeamBlock = GOTBlocks.woodBeam2;
				woodBeamMeta = 0;
				doorBlock = GOTBlocks.doorAramant;
				break;
			case 3:
				plankBlock = Blocks.planks;
				plankMeta = 2;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 2;
				plankStairBlock = Blocks.birch_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 2;
				fenceGateBlock = GOTBlocks.fenceGateBirch;
				woodBeamBlock = GOTBlocks.woodBeamV1;
				woodBeamMeta = 2;
				doorBlock = GOTBlocks.doorBirch;
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
				woodBeamBlock = GOTBlocks.woodBeamV1;
				woodBeamMeta = 0;
				doorBlock = Blocks.wooden_door;
				break;
		}
		switch (random.nextInt(11)) {
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
				cropBlock = GOTBlocks.lettuceCrop;
				cropMeta = 7;
				seedItem = GOTItems.lettuce;
				break;
			case 3:
				cropBlock = GOTBlocks.cornStalk;
				cropMeta = 0;
				seedItem = Item.getItemFromBlock(GOTBlocks.cornStalk);
				break;
			case 4:
				cropBlock = GOTBlocks.leekCrop;
				cropMeta = 7;
				seedItem = GOTItems.leek;
				break;
			case 5:
				cropBlock = GOTBlocks.turnipCrop;
				cropMeta = 7;
				seedItem = GOTItems.turnip;
				break;
			default:
				cropBlock = Blocks.wheat;
				cropMeta = 7;
				seedItem = Items.wheat_seeds;
				break;
		}
		if (random.nextBoolean()) {
			wallBlock = GOTBlocks.daub;
			wallMeta = 0;
		} else {
			wallBlock = plankBlock;
			wallMeta = plankMeta;
		}
	}

	public enum Kingdom {
		ARRYN, DORNE, CROWNLANDS, CROWNLANDS_RED, DRAGONSTONE, IRONBORN, NORTH, REACH, RIVERLANDS, STORMLANDS, WESTERLANDS
	}
}