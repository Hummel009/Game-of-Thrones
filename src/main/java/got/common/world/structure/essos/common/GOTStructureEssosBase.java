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
	public static Map<CityType, Class<? extends Entity>> bartenders = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> blacksmiths = new EnumMap<>(CityType.class);
	public static Map<CityType, BannerType> banners = new EnumMap<>(CityType.class);
	public static Map<CityType, GOTChestContents> chests = new EnumMap<>(CityType.class);
	public static Map<CityType, Block> tables = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> archers = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> farmers = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> farmhands = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> generals = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> men = new EnumMap<>(CityType.class);
	public static Map<CityType, Class<? extends Entity>> soldiers = new EnumMap<>(CityType.class);

	static {
		archers.put(CityType.BRAAVOS, GOTEntityBraavosSoldierArcher.class);
		archers.put(CityType.GHISCAR, GOTEntityGhiscarCorsairArcher.class);
		archers.put(CityType.LORATH, GOTEntityLorathSoldierArcher.class);
		archers.put(CityType.LYS, GOTEntityLysSoldierArcher.class);
		archers.put(CityType.MYR, GOTEntityMyrSoldierArcher.class);
		archers.put(CityType.NORVOS, GOTEntityNorvosLevymanArcher.class);
		archers.put(CityType.PENTOS, GOTEntityPentosLevymanArcher.class);
		archers.put(CityType.QARTH, GOTEntityQarthLevymanArcher.class);
		archers.put(CityType.QOHOR, GOTEntityQohorLevymanArcher.class);
		archers.put(CityType.TYROSH, GOTEntityTyroshSoldierArcher.class);
		archers.put(CityType.VOLANTIS, GOTEntityVolantisSoldierArcher.class);
		banners.put(CityType.BRAAVOS, GOTItemBanner.BannerType.BRAAVOS);
		banners.put(CityType.GHISCAR, GOTItemBanner.BannerType.GHISCAR);
		banners.put(CityType.LORATH, GOTItemBanner.BannerType.LORATH);
		banners.put(CityType.LYS, GOTItemBanner.BannerType.LYS);
		banners.put(CityType.MYR, GOTItemBanner.BannerType.MYR);
		banners.put(CityType.NORVOS, GOTItemBanner.BannerType.NORVOS);
		banners.put(CityType.PENTOS, GOTItemBanner.BannerType.PENTOS);
		banners.put(CityType.QARTH, GOTItemBanner.BannerType.QARTH);
		banners.put(CityType.QOHOR, GOTItemBanner.BannerType.QOHOR);
		banners.put(CityType.TYROSH, GOTItemBanner.BannerType.TYROSH);
		banners.put(CityType.VOLANTIS, GOTItemBanner.BannerType.VOLANTIS);
		bartenders.put(CityType.BRAAVOS, GOTEntityBraavosBartender.class);
		bartenders.put(CityType.GHISCAR, GOTEntityGhiscarBartender.class);
		bartenders.put(CityType.LORATH, GOTEntityLorathBartender.class);
		bartenders.put(CityType.LYS, GOTEntityLysBartender.class);
		bartenders.put(CityType.MYR, GOTEntityMyrBartender.class);
		bartenders.put(CityType.NORVOS, GOTEntityNorvosBartender.class);
		bartenders.put(CityType.PENTOS, GOTEntityPentosBartender.class);
		bartenders.put(CityType.QARTH, GOTEntityQarthBartender.class);
		bartenders.put(CityType.QOHOR, GOTEntityQohorBartender.class);
		bartenders.put(CityType.TYROSH, GOTEntityTyroshBartender.class);
		bartenders.put(CityType.VOLANTIS, GOTEntityVolantisBartender.class);
		blacksmiths.put(CityType.BRAAVOS, GOTEntityBraavosBlacksmith.class);
		blacksmiths.put(CityType.GHISCAR, GOTEntityGhiscarBlacksmith.class);
		blacksmiths.put(CityType.LORATH, GOTEntityLorathBlacksmith.class);
		blacksmiths.put(CityType.LYS, GOTEntityLysBlacksmith.class);
		blacksmiths.put(CityType.MYR, GOTEntityMyrBlacksmith.class);
		blacksmiths.put(CityType.NORVOS, GOTEntityNorvosBlacksmith.class);
		blacksmiths.put(CityType.PENTOS, GOTEntityPentosBlacksmith.class);
		blacksmiths.put(CityType.QARTH, GOTEntityQarthBlacksmith.class);
		blacksmiths.put(CityType.QOHOR, GOTEntityQohorBlacksmith.class);
		blacksmiths.put(CityType.TYROSH, GOTEntityTyroshBlacksmith.class);
		blacksmiths.put(CityType.VOLANTIS, GOTEntityVolantisBlacksmith.class);
		chests.put(CityType.BRAAVOS, GOTChestContents.BRAAVOS);
		chests.put(CityType.GHISCAR, GOTChestContents.GHISCAR);
		chests.put(CityType.LORATH, GOTChestContents.LORATH);
		chests.put(CityType.LYS, GOTChestContents.LYS);
		chests.put(CityType.MYR, GOTChestContents.MYR);
		chests.put(CityType.NORVOS, GOTChestContents.NORVOS);
		chests.put(CityType.PENTOS, GOTChestContents.PENTOS);
		chests.put(CityType.QARTH, GOTChestContents.QARTH);
		chests.put(CityType.QOHOR, GOTChestContents.QOHOR);
		chests.put(CityType.TYROSH, GOTChestContents.TYROSH);
		chests.put(CityType.VOLANTIS, GOTChestContents.VOLANTIS);
		farmers.put(CityType.BRAAVOS, GOTEntityBraavosFarmer.class);
		farmers.put(CityType.GHISCAR, GOTEntityGhiscarSlaver.class);
		farmers.put(CityType.LORATH, GOTEntityLorathFarmer.class);
		farmers.put(CityType.LYS, GOTEntityLysSlaver.class);
		farmers.put(CityType.MYR, GOTEntityMyrSlaver.class);
		farmers.put(CityType.NORVOS, GOTEntityNorvosFarmer.class);
		farmers.put(CityType.PENTOS, GOTEntityPentosFarmer.class);
		farmers.put(CityType.QARTH, GOTEntityQarthFarmer.class);
		farmers.put(CityType.QOHOR, GOTEntityQohorFarmer.class);
		farmers.put(CityType.TYROSH, GOTEntityTyroshSlaver.class);
		farmers.put(CityType.VOLANTIS, GOTEntityVolantisSlaver.class);
		farmhands.put(CityType.BRAAVOS, GOTEntityBraavosFarmhand.class);
		farmhands.put(CityType.GHISCAR, GOTEntityGhiscarSlave.class);
		farmhands.put(CityType.LORATH, GOTEntityLorathFarmhand.class);
		farmhands.put(CityType.LYS, GOTEntityLysSlave.class);
		farmhands.put(CityType.MYR, GOTEntityMyrSlave.class);
		farmhands.put(CityType.NORVOS, GOTEntityNorvosFarmhand.class);
		farmhands.put(CityType.PENTOS, GOTEntityPentosFarmhand.class);
		farmhands.put(CityType.QARTH, GOTEntityQarthFarmhand.class);
		farmhands.put(CityType.QOHOR, GOTEntityQohorFarmhand.class);
		farmhands.put(CityType.TYROSH, GOTEntityTyroshSlave.class);
		farmhands.put(CityType.VOLANTIS, GOTEntityVolantisSlave.class);
		generals.put(CityType.BRAAVOS, GOTEntityBraavosGeneral.class);
		generals.put(CityType.GHISCAR, GOTEntityGhiscarAdmiral.class);
		generals.put(CityType.LORATH, GOTEntityLorathGeneral.class);
		generals.put(CityType.LYS, GOTEntityLysGeneral.class);
		generals.put(CityType.MYR, GOTEntityMyrGeneral.class);
		generals.put(CityType.NORVOS, GOTEntityNorvosGuardCaptain.class);
		generals.put(CityType.PENTOS, GOTEntityPentosGuardCaptain.class);
		generals.put(CityType.QARTH, GOTEntityQarthGuardCaptain.class);
		generals.put(CityType.QOHOR, GOTEntityQohorGuardCaptain.class);
		generals.put(CityType.TYROSH, GOTEntityTyroshGeneral.class);
		generals.put(CityType.VOLANTIS, GOTEntityVolantisGeneral.class);
		men.put(CityType.BRAAVOS, GOTEntityBraavosMan.class);
		men.put(CityType.GHISCAR, GOTEntityGhiscarMan.class);
		men.put(CityType.LORATH, GOTEntityLorathMan.class);
		men.put(CityType.LYS, GOTEntityLysMan.class);
		men.put(CityType.MYR, GOTEntityMyrMan.class);
		men.put(CityType.NORVOS, GOTEntityNorvosMan.class);
		men.put(CityType.PENTOS, GOTEntityPentosMan.class);
		men.put(CityType.QARTH, GOTEntityQarthMan.class);
		men.put(CityType.QOHOR, GOTEntityQohorMan.class);
		men.put(CityType.TYROSH, GOTEntityTyroshMan.class);
		men.put(CityType.VOLANTIS, GOTEntityVolantisMan.class);
		soldiers.put(CityType.BRAAVOS, GOTEntityBraavosSoldier.class);
		soldiers.put(CityType.GHISCAR, GOTEntityGhiscarCorsair.class);
		soldiers.put(CityType.LORATH, GOTEntityLorathSoldier.class);
		soldiers.put(CityType.LYS, GOTEntityLysSoldier.class);
		soldiers.put(CityType.MYR, GOTEntityMyrSoldier.class);
		soldiers.put(CityType.NORVOS, GOTEntityNorvosLevyman.class);
		soldiers.put(CityType.PENTOS, GOTEntityPentosLevyman.class);
		soldiers.put(CityType.QARTH, GOTEntityQarthLevyman.class);
		soldiers.put(CityType.QOHOR, GOTEntityQohorLevyman.class);
		soldiers.put(CityType.TYROSH, GOTEntityTyroshSoldier.class);
		soldiers.put(CityType.VOLANTIS, GOTEntityVolantisSoldier.class);
		tables.put(CityType.BRAAVOS, GOTRegistry.tableBraavos);
		tables.put(CityType.GHISCAR, GOTRegistry.tableGhiscar);
		tables.put(CityType.LORATH, GOTRegistry.tableLorath);
		tables.put(CityType.LYS, GOTRegistry.tableLys);
		tables.put(CityType.MYR, GOTRegistry.tableMyr);
		tables.put(CityType.NORVOS, GOTRegistry.tableNorvos);
		tables.put(CityType.PENTOS, GOTRegistry.tablePentos);
		tables.put(CityType.QARTH, GOTRegistry.tableQarth);
		tables.put(CityType.QOHOR, GOTRegistry.tableQohor);
		tables.put(CityType.TYROSH, GOTRegistry.tableTyrosh);
		tables.put(CityType.VOLANTIS, GOTRegistry.tableVolantis);
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
	public CityType cityType = CityType.NULL;

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
		return banners.get(cityType);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(bartenders.get(cityType), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(blacksmiths.get(cityType), world);
	}

	public GOTChestContents getChestContents() {
		return chests.get(cityType);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmers.get(cityType), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmhands.get(cityType), world);
	}

	public GOTEntityNPC getGeneral(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(generals.get(cityType), world);
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(men.get(cityType), world);
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
		return (GOTEntityNPC) GOTReflection.newEntity(soldiers.get(cityType), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(archers.get(cityType), world);
	}

	public Block getTable() {
		return tables.get(cityType);
	}

	public boolean hasBlackPeople() {
		return cityType == CityType.GHISCAR;
	}

	public boolean hasNorthernTrees() {
		return cityType == CityType.BRAAVOS || cityType == CityType.LORATH || cityType == CityType.NORVOS || cityType == CityType.QOHOR;
	}

	public boolean hasSandstone() {
		return cityType == CityType.GHISCAR || cityType == CityType.QARTH || cityType == CityType.PENTOS;
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

	public enum CityType {
		BRAAVOS, GHISCAR, LORATH, VOLANTIS, TYROSH, QOHOR, QARTH, PENTOS, NORVOS, MYR, LYS, NULL;
	}
}
