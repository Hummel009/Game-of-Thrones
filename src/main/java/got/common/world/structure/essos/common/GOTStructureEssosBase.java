package got.common.world.structure.essos.common;

import got.common.database.GOTChestContents;
import got.common.database.GOTRegistry;
import got.common.entity.essos.braavos.*;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.lorath.*;
import got.common.entity.essos.lys.*;
import got.common.entity.essos.myr.*;
import got.common.entity.essos.norvos.*;
import got.common.entity.essos.pentos.*;
import got.common.entity.essos.qarth.*;
import got.common.entity.essos.qohor.*;
import got.common.entity.essos.tyrosh.*;
import got.common.entity.essos.volantis.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemBanner;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.util.GOTReflection;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public abstract class GOTStructureEssosBase extends GOTStructureBase {
	public static final Map<City, BannerType> BANNERS = new EnumMap<>(City.class);
	public static final Map<City, Block> TABLES = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> ARCHERS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> BARTENDERS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> CAPTAINS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> FARMERS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> FARMHANDS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> MEN = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> SMITHS = new EnumMap<>(City.class);
	public static final Map<City, Class<? extends Entity>> SOLDIERS = new EnumMap<>(City.class);
	public static final Map<City, GOTChestContents> CHESTS = new EnumMap<>(City.class);
	public static final Set<City> NORTHERN_TREE_CITIES = EnumSet.of(City.BRAAVOS, City.LORATH, City.NORVOS, City.QOHOR);
	public static final Set<City> SANDSTONE_CITIES = EnumSet.of(City.GHISCAR, City.QARTH, City.PENTOS);

	static {
		ARCHERS.put(City.BRAAVOS, GOTEntityBraavosSoldierArcher.class);
		ARCHERS.put(City.GHISCAR, GOTEntityGhiscarCorsairArcher.class);
		ARCHERS.put(City.LORATH, GOTEntityLorathSoldierArcher.class);
		ARCHERS.put(City.LYS, GOTEntityLysSoldierArcher.class);
		ARCHERS.put(City.MYR, GOTEntityMyrSoldierArcher.class);
		ARCHERS.put(City.NORVOS, GOTEntityNorvosLevymanArcher.class);
		ARCHERS.put(City.PENTOS, GOTEntityPentosLevymanArcher.class);
		ARCHERS.put(City.QARTH, GOTEntityQarthLevymanArcher.class);
		ARCHERS.put(City.QOHOR, GOTEntityQohorLevymanArcher.class);
		ARCHERS.put(City.TYROSH, GOTEntityTyroshSoldierArcher.class);
		ARCHERS.put(City.VOLANTIS, GOTEntityVolantisSoldierArcher.class);
		BANNERS.put(City.BRAAVOS, GOTItemBanner.BannerType.BRAAVOS);
		BANNERS.put(City.GHISCAR, GOTItemBanner.BannerType.GHISCAR);
		BANNERS.put(City.LORATH, GOTItemBanner.BannerType.LORATH);
		BANNERS.put(City.LYS, GOTItemBanner.BannerType.LYS);
		BANNERS.put(City.MYR, GOTItemBanner.BannerType.MYR);
		BANNERS.put(City.NORVOS, GOTItemBanner.BannerType.NORVOS);
		BANNERS.put(City.PENTOS, GOTItemBanner.BannerType.PENTOS);
		BANNERS.put(City.QARTH, GOTItemBanner.BannerType.QARTH);
		BANNERS.put(City.QOHOR, GOTItemBanner.BannerType.QOHOR);
		BANNERS.put(City.TYROSH, GOTItemBanner.BannerType.TYROSH);
		BANNERS.put(City.VOLANTIS, GOTItemBanner.BannerType.VOLANTIS);
		BARTENDERS.put(City.BRAAVOS, GOTEntityBraavosBartender.class);
		BARTENDERS.put(City.GHISCAR, GOTEntityGhiscarBartender.class);
		BARTENDERS.put(City.LORATH, GOTEntityLorathBartender.class);
		BARTENDERS.put(City.LYS, GOTEntityLysBartender.class);
		BARTENDERS.put(City.MYR, GOTEntityMyrBartender.class);
		BARTENDERS.put(City.NORVOS, GOTEntityNorvosBartender.class);
		BARTENDERS.put(City.PENTOS, GOTEntityPentosBartender.class);
		BARTENDERS.put(City.QARTH, GOTEntityQarthBartender.class);
		BARTENDERS.put(City.QOHOR, GOTEntityQohorBartender.class);
		BARTENDERS.put(City.TYROSH, GOTEntityTyroshBartender.class);
		BARTENDERS.put(City.VOLANTIS, GOTEntityVolantisBartender.class);
		CAPTAINS.put(City.BRAAVOS, GOTEntityBraavosGeneral.class);
		CAPTAINS.put(City.GHISCAR, GOTEntityGhiscarAdmiral.class);
		CAPTAINS.put(City.LORATH, GOTEntityLorathGeneral.class);
		CAPTAINS.put(City.LYS, GOTEntityLysGeneral.class);
		CAPTAINS.put(City.MYR, GOTEntityMyrGeneral.class);
		CAPTAINS.put(City.NORVOS, GOTEntityNorvosGuardCaptain.class);
		CAPTAINS.put(City.PENTOS, GOTEntityPentosGuardCaptain.class);
		CAPTAINS.put(City.QARTH, GOTEntityQarthGuardCaptain.class);
		CAPTAINS.put(City.QOHOR, GOTEntityQohorGuardCaptain.class);
		CAPTAINS.put(City.TYROSH, GOTEntityTyroshGeneral.class);
		CAPTAINS.put(City.VOLANTIS, GOTEntityVolantisGeneral.class);
		CHESTS.put(City.BRAAVOS, GOTChestContents.BRAAVOS);
		CHESTS.put(City.GHISCAR, GOTChestContents.GHISCAR);
		CHESTS.put(City.LORATH, GOTChestContents.LORATH);
		CHESTS.put(City.LYS, GOTChestContents.LYS);
		CHESTS.put(City.MYR, GOTChestContents.MYR);
		CHESTS.put(City.NORVOS, GOTChestContents.NORVOS);
		CHESTS.put(City.PENTOS, GOTChestContents.PENTOS);
		CHESTS.put(City.QARTH, GOTChestContents.QARTH);
		CHESTS.put(City.QOHOR, GOTChestContents.QOHOR);
		CHESTS.put(City.TYROSH, GOTChestContents.TYROSH);
		CHESTS.put(City.VOLANTIS, GOTChestContents.VOLANTIS);
		FARMERS.put(City.BRAAVOS, GOTEntityBraavosFarmer.class);
		FARMERS.put(City.GHISCAR, GOTEntityGhiscarSlaver.class);
		FARMERS.put(City.LORATH, GOTEntityLorathFarmer.class);
		FARMERS.put(City.LYS, GOTEntityLysSlaver.class);
		FARMERS.put(City.MYR, GOTEntityMyrSlaver.class);
		FARMERS.put(City.NORVOS, GOTEntityNorvosFarmer.class);
		FARMERS.put(City.PENTOS, GOTEntityPentosFarmer.class);
		FARMERS.put(City.QARTH, GOTEntityQarthFarmer.class);
		FARMERS.put(City.QOHOR, GOTEntityQohorFarmer.class);
		FARMERS.put(City.TYROSH, GOTEntityTyroshSlaver.class);
		FARMERS.put(City.VOLANTIS, GOTEntityVolantisSlaver.class);
		FARMHANDS.put(City.BRAAVOS, GOTEntityBraavosFarmhand.class);
		FARMHANDS.put(City.GHISCAR, GOTEntityGhiscarSlave.class);
		FARMHANDS.put(City.LORATH, GOTEntityLorathFarmhand.class);
		FARMHANDS.put(City.LYS, GOTEntityLysSlave.class);
		FARMHANDS.put(City.MYR, GOTEntityMyrSlave.class);
		FARMHANDS.put(City.NORVOS, GOTEntityNorvosFarmhand.class);
		FARMHANDS.put(City.PENTOS, GOTEntityPentosFarmhand.class);
		FARMHANDS.put(City.QARTH, GOTEntityQarthFarmhand.class);
		FARMHANDS.put(City.QOHOR, GOTEntityQohorFarmhand.class);
		FARMHANDS.put(City.TYROSH, GOTEntityTyroshSlave.class);
		FARMHANDS.put(City.VOLANTIS, GOTEntityVolantisSlave.class);
		MEN.put(City.BRAAVOS, GOTEntityBraavosMan.class);
		MEN.put(City.GHISCAR, GOTEntityGhiscarMan.class);
		MEN.put(City.LORATH, GOTEntityLorathMan.class);
		MEN.put(City.LYS, GOTEntityLysMan.class);
		MEN.put(City.MYR, GOTEntityMyrMan.class);
		MEN.put(City.NORVOS, GOTEntityNorvosMan.class);
		MEN.put(City.PENTOS, GOTEntityPentosMan.class);
		MEN.put(City.QARTH, GOTEntityQarthMan.class);
		MEN.put(City.QOHOR, GOTEntityQohorMan.class);
		MEN.put(City.TYROSH, GOTEntityTyroshMan.class);
		MEN.put(City.VOLANTIS, GOTEntityVolantisMan.class);
		SMITHS.put(City.BRAAVOS, GOTEntityBraavosBlacksmith.class);
		SMITHS.put(City.GHISCAR, GOTEntityGhiscarBlacksmith.class);
		SMITHS.put(City.LORATH, GOTEntityLorathBlacksmith.class);
		SMITHS.put(City.LYS, GOTEntityLysBlacksmith.class);
		SMITHS.put(City.MYR, GOTEntityMyrBlacksmith.class);
		SMITHS.put(City.NORVOS, GOTEntityNorvosBlacksmith.class);
		SMITHS.put(City.PENTOS, GOTEntityPentosBlacksmith.class);
		SMITHS.put(City.QARTH, GOTEntityQarthBlacksmith.class);
		SMITHS.put(City.QOHOR, GOTEntityQohorBlacksmith.class);
		SMITHS.put(City.TYROSH, GOTEntityTyroshBlacksmith.class);
		SMITHS.put(City.VOLANTIS, GOTEntityVolantisBlacksmith.class);
		SOLDIERS.put(City.BRAAVOS, GOTEntityBraavosSoldier.class);
		SOLDIERS.put(City.GHISCAR, GOTEntityGhiscarCorsair.class);
		SOLDIERS.put(City.LORATH, GOTEntityLorathSoldier.class);
		SOLDIERS.put(City.LYS, GOTEntityLysSoldier.class);
		SOLDIERS.put(City.MYR, GOTEntityMyrSoldier.class);
		SOLDIERS.put(City.NORVOS, GOTEntityNorvosLevyman.class);
		SOLDIERS.put(City.PENTOS, GOTEntityPentosLevyman.class);
		SOLDIERS.put(City.QARTH, GOTEntityQarthLevyman.class);
		SOLDIERS.put(City.QOHOR, GOTEntityQohorLevyman.class);
		SOLDIERS.put(City.TYROSH, GOTEntityTyroshSoldier.class);
		SOLDIERS.put(City.VOLANTIS, GOTEntityVolantisSoldier.class);
		TABLES.put(City.BRAAVOS, GOTRegistry.tableBraavos);
		TABLES.put(City.GHISCAR, GOTRegistry.tableGhiscar);
		TABLES.put(City.LORATH, GOTRegistry.tableLorath);
		TABLES.put(City.LYS, GOTRegistry.tableLys);
		TABLES.put(City.MYR, GOTRegistry.tableMyr);
		TABLES.put(City.NORVOS, GOTRegistry.tableNorvos);
		TABLES.put(City.PENTOS, GOTRegistry.tablePentos);
		TABLES.put(City.QARTH, GOTRegistry.tableQarth);
		TABLES.put(City.QOHOR, GOTRegistry.tableQohor);
		TABLES.put(City.TYROSH, GOTRegistry.tableTyrosh);
		TABLES.put(City.VOLANTIS, GOTRegistry.tableVolantis);
	}

	public Block stoneBlock;
	public int stoneMeta;
	public Block stoneStairBlock;
	public Block stoneWallBlock;
	public int stoneWallMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block brick2CarvedBlock;
	public int brick2CarvedMeta;
	public Block pillar2Block;
	public int pillar2Meta;
	public Block woodBlock;
	public int woodMeta;
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
	public int woodBeamMeta4;
	public int woodBeamMeta8;
	public Block doorBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block gateMetalBlock;
	public Block bedBlock;
	public Block tableBlock;
	public Block cropBlock;
	public Block trapdoorBlock;
	public Block barsBlock;
	public BannerType bannerType;
	public City city;

	protected GOTStructureEssosBase(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBricks() {
		return true;
	}

	public boolean forceMonotypeWood() {
		return false;
	}

	public BannerType getBannerType() {
		return BANNERS.get(city);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(BARTENDERS.get(city), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SMITHS.get(city), world);
	}

	public GOTChestContents getChestContents() {
		return CHESTS.get(city);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMERS.get(city), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMHANDS.get(city), world);
	}

	public GOTEntityNPC getGeneral(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(CAPTAINS.get(city), world);
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(MEN.get(city), world);
	}

	public ItemStack getRandomItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.gobletCopper), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3), new ItemStack(GOTRegistry.gemsbokHorn), new ItemStack(GOTRegistry.lionFur)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer)};
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getSoldier(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SOLDIERS.get(city), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(ARCHERS.get(city), world);
	}

	public Block getTable() {
		return TABLES.get(city);
	}

	public boolean hasBlackPeople() {
		return city == City.GHISCAR;
	}

	public boolean hasNorthernTrees() {
		return NORTHERN_TREE_CITIES.contains(city);
	}

	public boolean hasSandstone() {
		return SANDSTONE_CITIES.contains(city);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (hasSandstone()) {
			if (canUseRedBricks() && random.nextInt(4) == 0) {
				gateMetalBlock = GOTRegistry.gateBronzeBars;
				stoneBlock = Blocks.sandstone;
				stoneMeta = 0;
				stoneStairBlock = Blocks.sandstone_stairs;
				stoneWallBlock = GOTRegistry.wallStoneV;
				stoneWallMeta = 4;
				brickBlock = GOTRegistry.brick3;
				brickMeta = 13;
				brickSlabBlock = GOTRegistry.slabSingle7;
				brickSlabMeta = 2;
				brickStairBlock = GOTRegistry.stairsSandstoneBrickRed;
				brickWallBlock = GOTRegistry.wallStone3;
				brickWallMeta = 4;
				pillarBlock = GOTRegistry.pillar1;
				pillarMeta = 15;
			} else {
				gateMetalBlock = GOTRegistry.gateBronzeBars;
				stoneBlock = Blocks.sandstone;
				stoneMeta = 0;
				stoneStairBlock = Blocks.sandstone_stairs;
				stoneWallBlock = GOTRegistry.wallStoneV;
				stoneWallMeta = 4;
				brickBlock = GOTRegistry.brick1;
				brickMeta = 15;
				brickSlabBlock = GOTRegistry.slabSingle4;
				brickSlabMeta = 0;
				brickStairBlock = GOTRegistry.stairsSandstoneBrick;
				brickWallBlock = GOTRegistry.wallStone1;
				brickWallMeta = 15;
				pillarBlock = GOTRegistry.pillar1;
				pillarMeta = 5;
			}
			barsBlock = GOTRegistry.bronzeBars;
			brick2Block = GOTRegistry.brick3;
			brick2CarvedBlock = GOTRegistry.brick3;
			brick2CarvedMeta = 15;
			brick2Meta = 13;
			brick2SlabBlock = GOTRegistry.slabSingle7;
			brick2SlabMeta = 2;
			brick2StairBlock = GOTRegistry.stairsSandstoneBrickRed;
			brick2WallBlock = GOTRegistry.wallStone3;
			brick2WallMeta = 4;
			pillar2Block = GOTRegistry.pillar1;
			pillar2Meta = 15;
		} else {
			gateMetalBlock = GOTRegistry.gateIronBars;
			stoneBlock = GOTRegistry.rock;
			stoneMeta = 1;
			stoneStairBlock = GOTRegistry.stairsAndesite;
			stoneWallBlock = GOTRegistry.wallStone1;
			stoneWallMeta = 2;
			brickBlock = GOTRegistry.brick1;
			brickMeta = 1;
			brickSlabBlock = GOTRegistry.slabSingle1;
			brickSlabMeta = 3;
			brickStairBlock = GOTRegistry.stairsAndesiteBrick;
			brickWallBlock = GOTRegistry.wallStone1;
			brickWallMeta = 3;
			pillarBlock = GOTRegistry.pillar1;
			pillarMeta = 6;
			barsBlock = Blocks.iron_bars;
			brick2Block = GOTRegistry.brick2;
			brick2CarvedBlock = GOTRegistry.brick1;
			brick2CarvedMeta = 5;
			brick2Meta = 11;
			brick2SlabBlock = GOTRegistry.slabSingle5;
			brick2SlabMeta = 3;
			brick2StairBlock = GOTRegistry.stairsBasaltWesterosBrick;
			brick2WallBlock = GOTRegistry.wallStone2;
			brick2WallMeta = 10;
			pillar2Block = GOTRegistry.pillar1;
			pillar2Meta = 9;
		}
		roofBlock = GOTRegistry.thatch;
		roofMeta = 1;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = GOTRegistry.stairsReed;
		if (hasNorthernTrees()) {
			if (forceMonotypeWood()) {
				woodBlock = GOTRegistry.wood2;
				woodMeta = 1;
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
				trapdoorBlock = GOTRegistry.trapdoorBeech;
			} else {
				int randomWood = random.nextInt(7);
				switch (randomWood) {
					case 0:
						woodBlock = GOTRegistry.wood2;
						woodMeta = 1;
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
						trapdoorBlock = GOTRegistry.trapdoorBeech;
						break;
					case 1:
						woodBlock = GOTRegistry.wood4;
						woodMeta = 2;
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
						trapdoorBlock = GOTRegistry.trapdoorCedar;
						break;
					case 2:
						woodBlock = GOTRegistry.wood2;
						woodMeta = 0;
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
						trapdoorBlock = GOTRegistry.trapdoorAramant;
						break;
					case 3:
						woodBlock = Blocks.log;
						woodMeta = 2;
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
						trapdoorBlock = GOTRegistry.trapdoorBirch;
						break;
					default:
						woodBlock = Blocks.log;
						woodMeta = 0;
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
						trapdoorBlock = Blocks.trapdoor;
						break;
				}
			}
		} else if (forceMonotypeWood()) {
			woodBlock = GOTRegistry.wood4;
			woodMeta = 2;
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
			trapdoorBlock = GOTRegistry.trapdoorCedar;
		} else {
			int randomWood = random.nextInt(3);
			switch (randomWood) {
				case 0:
					woodBlock = GOTRegistry.wood6;
					woodMeta = 3;
					plankBlock = GOTRegistry.planks2;
					plankMeta = 11;
					plankSlabBlock = GOTRegistry.woodSlabSingle4;
					plankSlabMeta = 3;
					plankStairBlock = GOTRegistry.stairsOlive;
					fenceBlock = GOTRegistry.fence2;
					fenceMeta = 11;
					fenceGateBlock = GOTRegistry.fenceGateOlive;
					woodBeamBlock = GOTRegistry.woodBeam6;
					woodBeamMeta = 3;
					doorBlock = GOTRegistry.doorOlive;
					trapdoorBlock = GOTRegistry.trapdoorOlive;
					break;
				case 1:
					woodBlock = GOTRegistry.wood3;
					woodMeta = 2;
					plankBlock = GOTRegistry.planks1;
					plankMeta = 14;
					plankSlabBlock = GOTRegistry.woodSlabSingle2;
					plankSlabMeta = 6;
					plankStairBlock = GOTRegistry.stairsDatePalm;
					fenceBlock = GOTRegistry.fence;
					fenceMeta = 14;
					fenceGateBlock = GOTRegistry.fenceGateDatePalm;
					woodBeamBlock = GOTRegistry.woodBeam3;
					woodBeamMeta = 2;
					doorBlock = GOTRegistry.doorDatePalm;
					trapdoorBlock = GOTRegistry.trapdoorDatePalm;
					break;
				case 2:
					woodBlock = GOTRegistry.wood8;
					woodMeta = 3;
					plankBlock = GOTRegistry.planks3;
					plankMeta = 3;
					plankSlabBlock = GOTRegistry.woodSlabSingle5;
					plankSlabMeta = 3;
					plankStairBlock = GOTRegistry.stairsPalm;
					fenceBlock = GOTRegistry.fence3;
					fenceMeta = 3;
					fenceGateBlock = GOTRegistry.fenceGatePalm;
					woodBeamBlock = GOTRegistry.woodBeam8;
					woodBeamMeta = 3;
					doorBlock = GOTRegistry.doorPalm;
					trapdoorBlock = GOTRegistry.trapdoorPalm;
					break;
				default:
					break;
			}
		}
		woodBeamMeta4 = woodBeamMeta | 4;
		woodBeamMeta8 = woodBeamMeta | 8;
		bedBlock = GOTRegistry.strawBed;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
		} else {
			int randomCrop = random.nextInt(3);
			if (randomCrop == 0 || randomCrop == 1) {
				cropBlock = Blocks.carrots;
			} else if (randomCrop == 2) {
				cropBlock = GOTRegistry.lettuceCrop;
			}
		}
		tableBlock = getTable();
		bannerType = getBannerType();
	}

	public enum City {
		BRAAVOS, GHISCAR, LORATH, VOLANTIS, TYROSH, QOHOR, QARTH, PENTOS, NORVOS, MYR, LYS
	}
}
