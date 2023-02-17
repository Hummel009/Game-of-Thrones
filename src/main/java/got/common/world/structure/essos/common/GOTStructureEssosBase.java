package got.common.world.structure.essos.common;

import java.util.*;

import got.common.database.*;
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
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTStructureEssosBase extends GOTStructureBase {
	public static Map<City, Class<? extends Entity>> bartenders = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> blacksmiths = new EnumMap<>(City.class);
	public static Map<City, BannerType> banners = new EnumMap<>(City.class);
	public static Map<City, GOTChestContents> chests = new EnumMap<>(City.class);
	public static Map<City, Block> tables = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> archers = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> farmers = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> farmhands = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> generals = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> men = new EnumMap<>(City.class);
	public static Map<City, Class<? extends Entity>> soldiers = new EnumMap<>(City.class);
	static {
		archers.put(City.BRAAVOS, GOTEntityBraavosSoldierArcher.class);
		archers.put(City.GHISCAR, GOTEntityGhiscarCorsairArcher.class);
		archers.put(City.LORATH, GOTEntityLorathSoldierArcher.class);
		archers.put(City.LYS, GOTEntityLysSoldierArcher.class);
		archers.put(City.MYR, GOTEntityMyrSoldierArcher.class);
		archers.put(City.NORVOS, GOTEntityNorvosLevymanArcher.class);
		archers.put(City.PENTOS, GOTEntityPentosLevymanArcher.class);
		archers.put(City.QARTH, GOTEntityQarthLevymanArcher.class);
		archers.put(City.QOHOR, GOTEntityQohorLevymanArcher.class);
		archers.put(City.TYROSH, GOTEntityTyroshSoldierArcher.class);
		archers.put(City.VOLANTIS, GOTEntityVolantisSoldierArcher.class);
		banners.put(City.BRAAVOS, GOTItemBanner.BannerType.BRAAVOS);
		banners.put(City.GHISCAR, GOTItemBanner.BannerType.GHISCAR);
		banners.put(City.LORATH, GOTItemBanner.BannerType.LORATH);
		banners.put(City.LYS, GOTItemBanner.BannerType.LYS);
		banners.put(City.MYR, GOTItemBanner.BannerType.MYR);
		banners.put(City.NORVOS, GOTItemBanner.BannerType.NORVOS);
		banners.put(City.PENTOS, GOTItemBanner.BannerType.PENTOS);
		banners.put(City.QARTH, GOTItemBanner.BannerType.QARTH);
		banners.put(City.QOHOR, GOTItemBanner.BannerType.QOHOR);
		banners.put(City.TYROSH, GOTItemBanner.BannerType.TYROSH);
		banners.put(City.VOLANTIS, GOTItemBanner.BannerType.VOLANTIS);
		bartenders.put(City.BRAAVOS, GOTEntityBraavosBartender.class);
		bartenders.put(City.GHISCAR, GOTEntityGhiscarBartender.class);
		bartenders.put(City.LORATH, GOTEntityLorathBartender.class);
		bartenders.put(City.LYS, GOTEntityLysBartender.class);
		bartenders.put(City.MYR, GOTEntityMyrBartender.class);
		bartenders.put(City.NORVOS, GOTEntityNorvosBartender.class);
		bartenders.put(City.PENTOS, GOTEntityPentosBartender.class);
		bartenders.put(City.QARTH, GOTEntityQarthBartender.class);
		bartenders.put(City.QOHOR, GOTEntityQohorBartender.class);
		bartenders.put(City.TYROSH, GOTEntityTyroshBartender.class);
		bartenders.put(City.VOLANTIS, GOTEntityVolantisBartender.class);
		blacksmiths.put(City.BRAAVOS, GOTEntityBraavosBlacksmith.class);
		blacksmiths.put(City.GHISCAR, GOTEntityGhiscarBlacksmith.class);
		blacksmiths.put(City.LORATH, GOTEntityLorathBlacksmith.class);
		blacksmiths.put(City.LYS, GOTEntityLysBlacksmith.class);
		blacksmiths.put(City.MYR, GOTEntityMyrBlacksmith.class);
		blacksmiths.put(City.NORVOS, GOTEntityNorvosBlacksmith.class);
		blacksmiths.put(City.PENTOS, GOTEntityPentosBlacksmith.class);
		blacksmiths.put(City.QARTH, GOTEntityQarthBlacksmith.class);
		blacksmiths.put(City.QOHOR, GOTEntityQohorBlacksmith.class);
		blacksmiths.put(City.TYROSH, GOTEntityTyroshBlacksmith.class);
		blacksmiths.put(City.VOLANTIS, GOTEntityVolantisBlacksmith.class);
		chests.put(City.BRAAVOS, GOTChestContents.BRAAVOS);
		chests.put(City.GHISCAR, GOTChestContents.GHISCAR);
		chests.put(City.LORATH, GOTChestContents.LORATH);
		chests.put(City.LYS, GOTChestContents.LYS);
		chests.put(City.MYR, GOTChestContents.MYR);
		chests.put(City.NORVOS, GOTChestContents.NORVOS);
		chests.put(City.PENTOS, GOTChestContents.PENTOS);
		chests.put(City.QARTH, GOTChestContents.QARTH);
		chests.put(City.QOHOR, GOTChestContents.QOHOR);
		chests.put(City.TYROSH, GOTChestContents.TYROSH);
		chests.put(City.VOLANTIS, GOTChestContents.VOLANTIS);
		farmers.put(City.BRAAVOS, GOTEntityBraavosFarmer.class);
		farmers.put(City.GHISCAR, GOTEntityGhiscarSlaver.class);
		farmers.put(City.LORATH, GOTEntityLorathFarmer.class);
		farmers.put(City.LYS, GOTEntityLysSlaver.class);
		farmers.put(City.MYR, GOTEntityMyrSlaver.class);
		farmers.put(City.NORVOS, GOTEntityNorvosFarmer.class);
		farmers.put(City.PENTOS, GOTEntityPentosFarmer.class);
		farmers.put(City.QARTH, GOTEntityQarthFarmer.class);
		farmers.put(City.QOHOR, GOTEntityQohorFarmer.class);
		farmers.put(City.TYROSH, GOTEntityTyroshSlaver.class);
		farmers.put(City.VOLANTIS, GOTEntityVolantisSlaver.class);
		farmhands.put(City.BRAAVOS, GOTEntityBraavosFarmhand.class);
		farmhands.put(City.GHISCAR, GOTEntityGhiscarSlave.class);
		farmhands.put(City.LORATH, GOTEntityLorathFarmhand.class);
		farmhands.put(City.LYS, GOTEntityLysSlave.class);
		farmhands.put(City.MYR, GOTEntityMyrSlave.class);
		farmhands.put(City.NORVOS, GOTEntityNorvosFarmhand.class);
		farmhands.put(City.PENTOS, GOTEntityPentosFarmhand.class);
		farmhands.put(City.QARTH, GOTEntityQarthFarmhand.class);
		farmhands.put(City.QOHOR, GOTEntityQohorFarmhand.class);
		farmhands.put(City.TYROSH, GOTEntityTyroshSlave.class);
		farmhands.put(City.VOLANTIS, GOTEntityVolantisSlave.class);
		generals.put(City.BRAAVOS, GOTEntityBraavosGeneral.class);
		generals.put(City.GHISCAR, GOTEntityGhiscarAdmiral.class);
		generals.put(City.LORATH, GOTEntityLorathGeneral.class);
		generals.put(City.LYS, GOTEntityLysGeneral.class);
		generals.put(City.MYR, GOTEntityMyrGeneral.class);
		generals.put(City.NORVOS, GOTEntityNorvosGuardCaptain.class);
		generals.put(City.PENTOS, GOTEntityPentosGuardCaptain.class);
		generals.put(City.QARTH, GOTEntityQarthGuardCaptain.class);
		generals.put(City.QOHOR, GOTEntityQohorGuardCaptain.class);
		generals.put(City.TYROSH, GOTEntityTyroshGeneral.class);
		generals.put(City.VOLANTIS, GOTEntityVolantisGeneral.class);
		men.put(City.BRAAVOS, GOTEntityBraavosMan.class);
		men.put(City.GHISCAR, GOTEntityGhiscarMan.class);
		men.put(City.LORATH, GOTEntityLorathMan.class);
		men.put(City.LYS, GOTEntityLysMan.class);
		men.put(City.MYR, GOTEntityMyrMan.class);
		men.put(City.NORVOS, GOTEntityNorvosMan.class);
		men.put(City.PENTOS, GOTEntityPentosMan.class);
		men.put(City.QARTH, GOTEntityQarthMan.class);
		men.put(City.QOHOR, GOTEntityQohorMan.class);
		men.put(City.TYROSH, GOTEntityTyroshMan.class);
		men.put(City.VOLANTIS, GOTEntityVolantisMan.class);
		soldiers.put(City.BRAAVOS, GOTEntityBraavosSoldier.class);
		soldiers.put(City.GHISCAR, GOTEntityGhiscarCorsair.class);
		soldiers.put(City.LORATH, GOTEntityLorathSoldier.class);
		soldiers.put(City.LYS, GOTEntityLysSoldier.class);
		soldiers.put(City.MYR, GOTEntityMyrSoldier.class);
		soldiers.put(City.NORVOS, GOTEntityNorvosLevyman.class);
		soldiers.put(City.PENTOS, GOTEntityPentosLevyman.class);
		soldiers.put(City.QARTH, GOTEntityQarthLevyman.class);
		soldiers.put(City.QOHOR, GOTEntityQohorLevyman.class);
		soldiers.put(City.TYROSH, GOTEntityTyroshSoldier.class);
		soldiers.put(City.VOLANTIS, GOTEntityVolantisSoldier.class);
		tables.put(City.BRAAVOS, GOTRegistry.tableBraavos);
		tables.put(City.GHISCAR, GOTRegistry.tableGhiscar);
		tables.put(City.LORATH, GOTRegistry.tableLorath);
		tables.put(City.LYS, GOTRegistry.tableLys);
		tables.put(City.MYR, GOTRegistry.tableMyr);
		tables.put(City.NORVOS, GOTRegistry.tableNorvos);
		tables.put(City.PENTOS, GOTRegistry.tablePentos);
		tables.put(City.QARTH, GOTRegistry.tableQarth);
		tables.put(City.QOHOR, GOTRegistry.tableQohor);
		tables.put(City.TYROSH, GOTRegistry.tableTyrosh);
		tables.put(City.VOLANTIS, GOTRegistry.tableVolantis);
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

	public GOTStructureEssosBase(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBricks() {
		return true;
	}

	public boolean forceMonotypeWood() {
		return false;
	}

	public BannerType getBannerType() {
		return banners.get(city);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(bartenders.get(city), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(blacksmiths.get(city), world);
	}

	public GOTChestContents getChestContents() {
		return chests.get(city);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmers.get(city), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmhands.get(city), world);
	}

	public GOTEntityNPC getGeneral(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(generals.get(city), world);
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(men.get(city), world);
	}

	public ItemStack getRandomItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.gobletCopper), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3), new ItemStack(GOTRegistry.gemsbokHorn), new ItemStack(GOTRegistry.lionFur) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer) };
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getSoldier(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(soldiers.get(city), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(archers.get(city), world);
	}

	public Block getTable() {
		return tables.get(city);
	}

	public boolean hasBlackPeople() {
		return city == City.GHISCAR;
	}

	public boolean hasNorthernTrees() {
		return city == City.BRAAVOS || city == City.LORATH || city == City.NORVOS || city == City.QOHOR;
	}

	public boolean hasSandstone() {
		return city == City.GHISCAR || city == City.QARTH || city == City.PENTOS;
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
		BRAAVOS, GHISCAR, LORATH, VOLANTIS, TYROSH, QOHOR, QARTH, PENTOS, NORVOS, MYR, LYS;
	}
}
