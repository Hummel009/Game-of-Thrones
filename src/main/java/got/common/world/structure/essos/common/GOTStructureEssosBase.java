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
	public static Map<Type, Class<? extends Entity>> bartenders = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> blacksmiths = new EnumMap<>(Type.class);
	public static Map<Type, BannerType> banners = new EnumMap<>(Type.class);
	public static Map<Type, GOTChestContents> chests = new EnumMap<>(Type.class);
	public static Map<Type, Block> tables = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> archers = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> farmers = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> farmhands = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> generals = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> men = new EnumMap<>(Type.class);
	public static Map<Type, Class<? extends Entity>> soldiers = new EnumMap<>(Type.class);

	static {
		archers.put(Type.BRAAVOS, GOTEntityBraavosSoldierArcher.class);
		archers.put(Type.GHISCAR, GOTEntityGhiscarCorsairArcher.class);
		archers.put(Type.LORATH, GOTEntityLorathSoldierArcher.class);
		archers.put(Type.LYS, GOTEntityLysSoldierArcher.class);
		archers.put(Type.MYR, GOTEntityMyrSoldierArcher.class);
		archers.put(Type.NORVOS, GOTEntityNorvosLevymanArcher.class);
		archers.put(Type.PENTOS, GOTEntityPentosLevymanArcher.class);
		archers.put(Type.QARTH, GOTEntityQarthLevymanArcher.class);
		archers.put(Type.QOHOR, GOTEntityQohorLevymanArcher.class);
		archers.put(Type.TYROSH, GOTEntityTyroshSoldierArcher.class);
		archers.put(Type.VOLANTIS, GOTEntityVolantisSoldierArcher.class);
		banners.put(Type.BRAAVOS, GOTItemBanner.BannerType.BRAAVOS);
		banners.put(Type.GHISCAR, GOTItemBanner.BannerType.GHISCAR);
		banners.put(Type.LORATH, GOTItemBanner.BannerType.LORATH);
		banners.put(Type.LYS, GOTItemBanner.BannerType.LYS);
		banners.put(Type.MYR, GOTItemBanner.BannerType.MYR);
		banners.put(Type.NORVOS, GOTItemBanner.BannerType.NORVOS);
		banners.put(Type.PENTOS, GOTItemBanner.BannerType.PENTOS);
		banners.put(Type.QARTH, GOTItemBanner.BannerType.QARTH);
		banners.put(Type.QOHOR, GOTItemBanner.BannerType.QOHOR);
		banners.put(Type.TYROSH, GOTItemBanner.BannerType.TYROSH);
		banners.put(Type.VOLANTIS, GOTItemBanner.BannerType.VOLANTIS);
		bartenders.put(Type.BRAAVOS, GOTEntityBraavosBartender.class);
		bartenders.put(Type.GHISCAR, GOTEntityGhiscarBartender.class);
		bartenders.put(Type.LORATH, GOTEntityLorathBartender.class);
		bartenders.put(Type.LYS, GOTEntityLysBartender.class);
		bartenders.put(Type.MYR, GOTEntityMyrBartender.class);
		bartenders.put(Type.NORVOS, GOTEntityNorvosBartender.class);
		bartenders.put(Type.PENTOS, GOTEntityPentosBartender.class);
		bartenders.put(Type.QARTH, GOTEntityQarthBartender.class);
		bartenders.put(Type.QOHOR, GOTEntityQohorBartender.class);
		bartenders.put(Type.TYROSH, GOTEntityTyroshBartender.class);
		bartenders.put(Type.VOLANTIS, GOTEntityVolantisBartender.class);
		blacksmiths.put(Type.BRAAVOS, GOTEntityBraavosBlacksmith.class);
		blacksmiths.put(Type.GHISCAR, GOTEntityGhiscarBlacksmith.class);
		blacksmiths.put(Type.LORATH, GOTEntityLorathBlacksmith.class);
		blacksmiths.put(Type.LYS, GOTEntityLysBlacksmith.class);
		blacksmiths.put(Type.MYR, GOTEntityMyrBlacksmith.class);
		blacksmiths.put(Type.NORVOS, GOTEntityNorvosBlacksmith.class);
		blacksmiths.put(Type.PENTOS, GOTEntityPentosBlacksmith.class);
		blacksmiths.put(Type.QARTH, GOTEntityQarthBlacksmith.class);
		blacksmiths.put(Type.QOHOR, GOTEntityQohorBlacksmith.class);
		blacksmiths.put(Type.TYROSH, GOTEntityTyroshBlacksmith.class);
		blacksmiths.put(Type.VOLANTIS, GOTEntityVolantisBlacksmith.class);
		chests.put(Type.BRAAVOS, GOTChestContents.BRAAVOS);
		chests.put(Type.GHISCAR, GOTChestContents.GHISCAR);
		chests.put(Type.LORATH, GOTChestContents.LORATH);
		chests.put(Type.LYS, GOTChestContents.LYS);
		chests.put(Type.MYR, GOTChestContents.MYR);
		chests.put(Type.NORVOS, GOTChestContents.NORVOS);
		chests.put(Type.PENTOS, GOTChestContents.PENTOS);
		chests.put(Type.QARTH, GOTChestContents.QARTH);
		chests.put(Type.QOHOR, GOTChestContents.QOHOR);
		chests.put(Type.TYROSH, GOTChestContents.TYROSH);
		chests.put(Type.VOLANTIS, GOTChestContents.VOLANTIS);
		farmers.put(Type.BRAAVOS, GOTEntityBraavosFarmer.class);
		farmers.put(Type.GHISCAR, GOTEntityGhiscarSlaver.class);
		farmers.put(Type.LORATH, GOTEntityLorathFarmer.class);
		farmers.put(Type.LYS, GOTEntityLysSlaver.class);
		farmers.put(Type.MYR, GOTEntityMyrSlaver.class);
		farmers.put(Type.NORVOS, GOTEntityNorvosFarmer.class);
		farmers.put(Type.PENTOS, GOTEntityPentosFarmer.class);
		farmers.put(Type.QARTH, GOTEntityQarthFarmer.class);
		farmers.put(Type.QOHOR, GOTEntityQohorFarmer.class);
		farmers.put(Type.TYROSH, GOTEntityTyroshSlaver.class);
		farmers.put(Type.VOLANTIS, GOTEntityVolantisSlaver.class);
		farmhands.put(Type.BRAAVOS, GOTEntityBraavosFarmhand.class);
		farmhands.put(Type.GHISCAR, GOTEntityGhiscarSlave.class);
		farmhands.put(Type.LORATH, GOTEntityLorathFarmhand.class);
		farmhands.put(Type.LYS, GOTEntityLysSlave.class);
		farmhands.put(Type.MYR, GOTEntityMyrSlave.class);
		farmhands.put(Type.NORVOS, GOTEntityNorvosFarmhand.class);
		farmhands.put(Type.PENTOS, GOTEntityPentosFarmhand.class);
		farmhands.put(Type.QARTH, GOTEntityQarthFarmhand.class);
		farmhands.put(Type.QOHOR, GOTEntityQohorFarmhand.class);
		farmhands.put(Type.TYROSH, GOTEntityTyroshSlave.class);
		farmhands.put(Type.VOLANTIS, GOTEntityVolantisSlave.class);
		generals.put(Type.BRAAVOS, GOTEntityBraavosGeneral.class);
		generals.put(Type.GHISCAR, GOTEntityGhiscarAdmiral.class);
		generals.put(Type.LORATH, GOTEntityLorathGeneral.class);
		generals.put(Type.LYS, GOTEntityLysGeneral.class);
		generals.put(Type.MYR, GOTEntityMyrGeneral.class);
		generals.put(Type.NORVOS, GOTEntityNorvosGuardCaptain.class);
		generals.put(Type.PENTOS, GOTEntityPentosGuardCaptain.class);
		generals.put(Type.QARTH, GOTEntityQarthGuardCaptain.class);
		generals.put(Type.QOHOR, GOTEntityQohorGuardCaptain.class);
		generals.put(Type.TYROSH, GOTEntityTyroshGeneral.class);
		generals.put(Type.VOLANTIS, GOTEntityVolantisGeneral.class);
		men.put(Type.BRAAVOS, GOTEntityBraavosMan.class);
		men.put(Type.GHISCAR, GOTEntityGhiscarMan.class);
		men.put(Type.LORATH, GOTEntityLorathMan.class);
		men.put(Type.LYS, GOTEntityLysMan.class);
		men.put(Type.MYR, GOTEntityMyrMan.class);
		men.put(Type.NORVOS, GOTEntityNorvosMan.class);
		men.put(Type.PENTOS, GOTEntityPentosMan.class);
		men.put(Type.QARTH, GOTEntityQarthMan.class);
		men.put(Type.QOHOR, GOTEntityQohorMan.class);
		men.put(Type.TYROSH, GOTEntityTyroshMan.class);
		men.put(Type.VOLANTIS, GOTEntityVolantisMan.class);
		soldiers.put(Type.BRAAVOS, GOTEntityBraavosSoldier.class);
		soldiers.put(Type.GHISCAR, GOTEntityGhiscarCorsair.class);
		soldiers.put(Type.LORATH, GOTEntityLorathSoldier.class);
		soldiers.put(Type.LYS, GOTEntityLysSoldier.class);
		soldiers.put(Type.MYR, GOTEntityMyrSoldier.class);
		soldiers.put(Type.NORVOS, GOTEntityNorvosLevyman.class);
		soldiers.put(Type.PENTOS, GOTEntityPentosLevyman.class);
		soldiers.put(Type.QARTH, GOTEntityQarthLevyman.class);
		soldiers.put(Type.QOHOR, GOTEntityQohorLevyman.class);
		soldiers.put(Type.TYROSH, GOTEntityTyroshSoldier.class);
		soldiers.put(Type.VOLANTIS, GOTEntityVolantisSoldier.class);
		tables.put(Type.BRAAVOS, GOTRegistry.tableBraavos);
		tables.put(Type.GHISCAR, GOTRegistry.tableGhiscar);
		tables.put(Type.LORATH, GOTRegistry.tableLorath);
		tables.put(Type.LYS, GOTRegistry.tableLys);
		tables.put(Type.MYR, GOTRegistry.tableMyr);
		tables.put(Type.NORVOS, GOTRegistry.tableNorvos);
		tables.put(Type.PENTOS, GOTRegistry.tablePentos);
		tables.put(Type.QARTH, GOTRegistry.tableQarth);
		tables.put(Type.QOHOR, GOTRegistry.tableQohor);
		tables.put(Type.TYROSH, GOTRegistry.tableTyrosh);
		tables.put(Type.VOLANTIS, GOTRegistry.tableVolantis);
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
	public GOTItemBanner.BannerType bannerType;
	public Block barsBlock;

	public Type type = Type.NULL;

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
		return banners.get(type);
	}

	public GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(bartenders.get(type), world);
	}

	public GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(blacksmiths.get(type), world);
	}

	public GOTChestContents getChestContents() {
		return chests.get(type);
	}

	public GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmers.get(type), world);
	}

	public GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(farmhands.get(type), world);
	}

	public GOTEntityNPC getGeneral(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(generals.get(type), world);
	}

	public GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(men.get(type), world);
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
		return (GOTEntityNPC) GOTReflection.newEntity(soldiers.get(type), world);
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(archers.get(type), world);
	}

	public Block getTable() {
		return tables.get(type);
	}

	public boolean isNorthernTrees() {
		return type == Type.BRAAVOS || type == Type.LORATH || type == Type.NORVOS || type == Type.QOHOR;
	}

	public boolean isSandstone() {
		return type == Type.GHISCAR || type == Type.QARTH || type == Type.PENTOS;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (isSandstone()) {
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
		if (isNorthernTrees()) {
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

	public enum Type {
		BRAAVOS, GHISCAR, LORATH, VOLANTIS, TYROSH, QOHOR, QARTH, PENTOS, NORVOS, MYR, LYS, NULL;
	}
}
