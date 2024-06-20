package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
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
import got.common.util.GOTReflection;
import got.common.world.feature.GOTTreeType;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public abstract class GOTStructureEssosBase extends GOTStructureBase {
	private static final Map<City, GOTItemBanner.BannerType> BANNERS = new EnumMap<>(City.class);
	private static final Map<City, Block> TABLES = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> ARCHERS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> BARTENDERS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> CAPTAINS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> FARMERS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> FARMHANDS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> MEN = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> SMITHS = new EnumMap<>(City.class);
	private static final Map<City, Class<? extends Entity>> SOLDIERS = new EnumMap<>(City.class);
	private static final Map<City, GOTChestContents> CHEST_CONTENTS = new EnumMap<>(City.class);
	private static final Map<City, Block> CHESTS = new EnumMap<>(City.class);
	private static final Set<City> NORTHERN_TREE_CITIES = EnumSet.of(City.BRAAVOS, City.LORATH, City.NORVOS, City.QOHOR);
	private static final Set<City> SANDSTONE_CITIES = EnumSet.of(City.GHISCAR, City.QARTH, City.PENTOS);

	static {
		ARCHERS.put(City.BRAAVOS, GOTEntityBraavosSoldierArcher.class);
		ARCHERS.put(City.GHISCAR, GOTEntityGhiscarSoldierArcher.class);
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
		CAPTAINS.put(City.BRAAVOS, GOTEntityBraavosCaptain.class);
		CAPTAINS.put(City.GHISCAR, GOTEntityGhiscarCaptain.class);
		CAPTAINS.put(City.LORATH, GOTEntityLorathCaptain.class);
		CAPTAINS.put(City.LYS, GOTEntityLysCaptain.class);
		CAPTAINS.put(City.MYR, GOTEntityMyrCaptain.class);
		CAPTAINS.put(City.NORVOS, GOTEntityNorvosCaptain.class);
		CAPTAINS.put(City.PENTOS, GOTEntityPentosCaptain.class);
		CAPTAINS.put(City.QARTH, GOTEntityQarthCaptain.class);
		CAPTAINS.put(City.QOHOR, GOTEntityQohorCaptain.class);
		CAPTAINS.put(City.TYROSH, GOTEntityTyroshCaptain.class);
		CAPTAINS.put(City.VOLANTIS, GOTEntityVolantisCaptain.class);
		CHESTS.put(City.BRAAVOS, GOTBlocks.chestStone);
		CHESTS.put(City.GHISCAR, GOTBlocks.chestSandstone);
		CHESTS.put(City.LORATH, GOTBlocks.chestStone);
		CHESTS.put(City.LYS, GOTBlocks.chestStone);
		CHESTS.put(City.MYR, GOTBlocks.chestStone);
		CHESTS.put(City.NORVOS, GOTBlocks.chestStone);
		CHESTS.put(City.PENTOS, GOTBlocks.chestSandstone);
		CHESTS.put(City.QARTH, GOTBlocks.chestSandstone);
		CHESTS.put(City.QOHOR, GOTBlocks.chestStone);
		CHESTS.put(City.TYROSH, GOTBlocks.chestStone);
		CHESTS.put(City.VOLANTIS, GOTBlocks.chestStone);
		CHEST_CONTENTS.put(City.BRAAVOS, GOTChestContents.BRAAVOS);
		CHEST_CONTENTS.put(City.GHISCAR, GOTChestContents.GHISCAR);
		CHEST_CONTENTS.put(City.LORATH, GOTChestContents.LORATH);
		CHEST_CONTENTS.put(City.LYS, GOTChestContents.LYS);
		CHEST_CONTENTS.put(City.MYR, GOTChestContents.MYR);
		CHEST_CONTENTS.put(City.NORVOS, GOTChestContents.NORVOS);
		CHEST_CONTENTS.put(City.PENTOS, GOTChestContents.PENTOS);
		CHEST_CONTENTS.put(City.QARTH, GOTChestContents.QARTH);
		CHEST_CONTENTS.put(City.QOHOR, GOTChestContents.QOHOR);
		CHEST_CONTENTS.put(City.TYROSH, GOTChestContents.TYROSH);
		CHEST_CONTENTS.put(City.VOLANTIS, GOTChestContents.VOLANTIS);
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
		SOLDIERS.put(City.GHISCAR, GOTEntityGhiscarSoldier.class);
		SOLDIERS.put(City.LORATH, GOTEntityLorathSoldier.class);
		SOLDIERS.put(City.LYS, GOTEntityLysSoldier.class);
		SOLDIERS.put(City.MYR, GOTEntityMyrSoldier.class);
		SOLDIERS.put(City.NORVOS, GOTEntityNorvosLevyman.class);
		SOLDIERS.put(City.PENTOS, GOTEntityPentosLevyman.class);
		SOLDIERS.put(City.QARTH, GOTEntityQarthLevyman.class);
		SOLDIERS.put(City.QOHOR, GOTEntityQohorLevyman.class);
		SOLDIERS.put(City.TYROSH, GOTEntityTyroshSoldier.class);
		SOLDIERS.put(City.VOLANTIS, GOTEntityVolantisSoldier.class);
		TABLES.put(City.BRAAVOS, GOTBlocks.tableBraavos);
		TABLES.put(City.GHISCAR, GOTBlocks.tableGhiscar);
		TABLES.put(City.LORATH, GOTBlocks.tableLorath);
		TABLES.put(City.LYS, GOTBlocks.tableLys);
		TABLES.put(City.MYR, GOTBlocks.tableMyr);
		TABLES.put(City.NORVOS, GOTBlocks.tableNorvos);
		TABLES.put(City.PENTOS, GOTBlocks.tablePentos);
		TABLES.put(City.QARTH, GOTBlocks.tableQarth);
		TABLES.put(City.QOHOR, GOTBlocks.tableQohor);
		TABLES.put(City.TYROSH, GOTBlocks.tableTyrosh);
		TABLES.put(City.VOLANTIS, GOTBlocks.tableVolantis);
	}

	protected Block stoneBlock;
	protected int stoneMeta;
	protected Block stoneStairBlock;
	protected Block stoneWallBlock;
	protected int stoneWallMeta;
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickSlabBlock;
	protected int brickSlabMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block pillarBlock;
	protected int pillarMeta;
	protected Block brick2Block;
	protected int brick2Meta;
	protected Block brick2SlabBlock;
	protected int brick2SlabMeta;
	protected Block brick2StairBlock;
	protected Block brick2WallBlock;
	protected int brick2WallMeta;
	protected Block brick2CarvedBlock;
	protected int brick2CarvedMeta;
	protected Block pillar2Block;
	protected int pillar2Meta;
	protected Block woodBlock;
	protected int woodMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block woodBeamBlock;
	protected int woodBeamMeta;
	protected int woodBeamMeta4;
	protected int woodBeamMeta8;
	protected Block doorBlock;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block gateMetalBlock;
	protected Block bedBlock;
	protected Block tableBlock;
	protected Block cropBlock;
	protected Block trapdoorBlock;
	protected Block barsBlock;
	protected GOTItemBanner.BannerType bannerType;
	protected City city;

	protected GOTStructureEssosBase(boolean flag) {
		super(flag);
	}

	protected static GOTTreeType getRandomSouthernTree(Random random) {
		ArrayList<GOTTreeType> treeList = new ArrayList<>();
		treeList.add(GOTTreeType.ACACIA);
		treeList.add(GOTTreeType.ALMOND);
		treeList.add(GOTTreeType.CEDAR);
		treeList.add(GOTTreeType.CEDAR_LARGE);
		treeList.add(GOTTreeType.CYPRESS);
		treeList.add(GOTTreeType.CYPRESS_LARGE);
		treeList.add(GOTTreeType.DATE_PALM);
		treeList.add(GOTTreeType.LEMON);
		treeList.add(GOTTreeType.LIME);
		treeList.add(GOTTreeType.OLIVE);
		treeList.add(GOTTreeType.OLIVE_LARGE);
		treeList.add(GOTTreeType.ORANGE);
		treeList.add(GOTTreeType.PALM);
		treeList.add(GOTTreeType.PLUM);
		treeList.add(GOTTreeType.KANUKA);
		return treeList.get(random.nextInt(treeList.size()));
	}

	protected static GOTTreeType getRandomStandardTree(Random random) {
		ArrayList<GOTTreeType> treeList = new ArrayList<>();
		treeList.add(GOTTreeType.OAK);
		treeList.add(GOTTreeType.OAK_TALLER);
		treeList.add(GOTTreeType.OAK_LARGE);
		treeList.add(GOTTreeType.BIRCH);
		treeList.add(GOTTreeType.BIRCH_LARGE);
		treeList.add(GOTTreeType.BIRCH_TALL);
		treeList.add(GOTTreeType.BEECH);
		treeList.add(GOTTreeType.BEECH_LARGE);
		treeList.add(GOTTreeType.APPLE);
		treeList.add(GOTTreeType.PEAR);
		treeList.add(GOTTreeType.PLUM);
		treeList.add(GOTTreeType.OLIVE);
		treeList.add(GOTTreeType.ALMOND);
		treeList.add(GOTTreeType.CHESTNUT);
		treeList.add(GOTTreeType.CHESTNUT_LARGE);
		return treeList.get(random.nextInt(treeList.size()));
	}

	protected static ItemStack getRandomItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.essosSword), new ItemStack(GOTItems.essosDagger), new ItemStack(GOTItems.essosSpear), new ItemStack(GOTItems.essosPike), new ItemStack(GOTItems.essosPolearm), new ItemStack(GOTItems.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTItems.gobletCopper), new ItemStack(GOTItems.gobletCopper), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.ceramicMug), new ItemStack(GOTItems.copperRing), new ItemStack(GOTItems.bronzeRing), new ItemStack(GOTBlocks.doubleFlower, 1, 2), new ItemStack(GOTBlocks.doubleFlower, 1, 3), new ItemStack(GOTItems.gemsbokHorn)};
		return items[random.nextInt(items.length)].copy();
	}

	protected static ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.essosSword), new ItemStack(GOTItems.essosDagger), new ItemStack(GOTItems.essosSpear), new ItemStack(GOTItems.essosPike), new ItemStack(GOTItems.essosPolearm), new ItemStack(GOTItems.essosHammer)};
		return items[random.nextInt(items.length)].copy();
	}

	private GOTItemBanner.BannerType getBannerType() {
		return BANNERS.get(city);
	}

	protected GOTEntityNPC getBartender(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(BARTENDERS.get(city), world);
	}

	protected GOTEntityNPC getBlacksmith(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SMITHS.get(city), world);
	}

	protected GOTChestContents getChestContents() {
		return CHEST_CONTENTS.get(city);
	}

	protected Block getChest() {
		return CHESTS.get(city);
	}

	protected GOTEntityNPC getFarmer(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMERS.get(city), world);
	}

	protected GOTEntityNPC getFarmhand(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(FARMHANDS.get(city), world);
	}

	protected GOTEntityNPC getGeneral(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(CAPTAINS.get(city), world);
	}

	protected GOTEntityNPC getMan(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(MEN.get(city), world);
	}

	protected GOTEntityNPC getSoldier(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(SOLDIERS.get(city), world);
	}

	protected GOTEntityNPC getSoldierArcher(World world) {
		return (GOTEntityNPC) GOTReflection.newEntity(ARCHERS.get(city), world);
	}

	private Block getTable() {
		return TABLES.get(city);
	}

	protected boolean hasDarkSkinPeople() {
		return city == City.GHISCAR;
	}

	protected boolean hasMonotypeWood() {
		return false;
	}

	protected boolean hasNorthernWood() {
		return NORTHERN_TREE_CITIES.contains(city);
	}

	protected boolean hasRedSandstone() {
		return true;
	}

	protected boolean hasSandstone() {
		return SANDSTONE_CITIES.contains(city);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		woodBeamMeta4 = woodBeamMeta | 4;
		woodBeamMeta8 = woodBeamMeta | 8;
		bedBlock = GOTBlocks.strawBed;
		tableBlock = getTable();
		bannerType = getBannerType();
		if (hasSandstone()) {
			roofBlock = GOTBlocks.thatch;
			roofMeta = 1;
			roofSlabBlock = GOTBlocks.slabSingleThatch;
			roofSlabMeta = 1;
			roofStairBlock = GOTBlocks.stairsReed;
			stoneStairBlock = Blocks.sandstone_stairs;
			stoneWallBlock = GOTBlocks.wallStoneV;
			stoneWallMeta = 4;
			gateMetalBlock = GOTBlocks.gateBronzeBars;
			stoneBlock = Blocks.sandstone;
			stoneMeta = 0;
			barsBlock = GOTBlocks.bronzeBars;
			brick2Block = GOTBlocks.brick3;
			brick2CarvedBlock = GOTBlocks.brick3;
			brick2CarvedMeta = 15;
			brick2Meta = 13;
			brick2SlabBlock = GOTBlocks.slabSingle7;
			brick2SlabMeta = 2;
			brick2StairBlock = GOTBlocks.stairsSandstoneBrickRed;
			brick2WallBlock = GOTBlocks.wallStone3;
			brick2WallMeta = 4;
			pillar2Block = GOTBlocks.pillar1;
			pillar2Meta = 15;
			if (hasRedSandstone() && random.nextInt(4) == 0) {
				brickBlock = GOTBlocks.brick3;
				brickMeta = 13;
				brickSlabBlock = GOTBlocks.slabSingle7;
				brickSlabMeta = 2;
				brickStairBlock = GOTBlocks.stairsSandstoneBrickRed;
				brickWallBlock = GOTBlocks.wallStone3;
				brickWallMeta = 4;
				pillarBlock = GOTBlocks.pillar1;
				pillarMeta = 15;
			} else {
				brickBlock = GOTBlocks.brick1;
				brickMeta = 15;
				brickSlabBlock = GOTBlocks.slabSingle4;
				brickSlabMeta = 0;
				brickStairBlock = GOTBlocks.stairsSandstoneBrick;
				brickWallBlock = GOTBlocks.wallStone1;
				brickWallMeta = 15;
				pillarBlock = GOTBlocks.pillar1;
				pillarMeta = 5;
			}
		} else {
			roofBlock = GOTBlocks.thatch;
			roofMeta = 0;
			roofSlabBlock = GOTBlocks.slabSingleThatch;
			roofSlabMeta = 0;
			roofStairBlock = GOTBlocks.stairsThatch;
			gateMetalBlock = GOTBlocks.gateIronBars;
			stoneBlock = GOTBlocks.rock;
			stoneMeta = 1;
			stoneStairBlock = GOTBlocks.stairsAndesite;
			stoneWallBlock = GOTBlocks.wallStone1;
			stoneWallMeta = 2;
			brickBlock = GOTBlocks.brick1;
			brickMeta = 1;
			brickSlabBlock = GOTBlocks.slabSingle1;
			brickSlabMeta = 3;
			brickStairBlock = GOTBlocks.stairsAndesiteBrick;
			brickWallBlock = GOTBlocks.wallStone1;
			brickWallMeta = 3;
			pillarBlock = GOTBlocks.pillar1;
			pillarMeta = 6;
			barsBlock = Blocks.iron_bars;
			brick2Block = GOTBlocks.brick2;
			brick2CarvedBlock = GOTBlocks.brick1;
			brick2CarvedMeta = 5;
			brick2Meta = 11;
			brick2SlabBlock = GOTBlocks.slabSingle5;
			brick2SlabMeta = 3;
			brick2StairBlock = GOTBlocks.stairsBasaltWesterosBrick;
			brick2WallBlock = GOTBlocks.wallStone2;
			brick2WallMeta = 10;
			pillar2Block = GOTBlocks.pillar1;
			pillar2Meta = 9;
		}
		if (hasMonotypeWood() && hasNorthernWood()) {
			woodBlock = GOTBlocks.wood2;
			woodMeta = 1;
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
			trapdoorBlock = GOTBlocks.trapdoorBeech;
		} else if (hasMonotypeWood()) {
			woodBlock = GOTBlocks.wood4;
			woodMeta = 2;
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
			trapdoorBlock = GOTBlocks.trapdoorCedar;
		} else if (hasNorthernWood()) {
			switch (random.nextInt(3)) {
				case 0:
					woodBlock = GOTBlocks.wood2;
					woodMeta = 1;
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
					trapdoorBlock = GOTBlocks.trapdoorBeech;
					break;
				case 1:
					woodBlock = GOTBlocks.wood4;
					woodMeta = 2;
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
					trapdoorBlock = GOTBlocks.trapdoorCedar;
					break;
				case 2:
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
					woodBeamBlock = GOTBlocks.woodBeamV1;
					woodBeamMeta = 0;
					doorBlock = Blocks.wooden_door;
					trapdoorBlock = Blocks.trapdoor;
					break;
			}
		} else {
			switch (random.nextInt(3)) {
				case 0:
					woodBlock = GOTBlocks.wood6;
					woodMeta = 3;
					plankBlock = GOTBlocks.planks2;
					plankMeta = 11;
					plankSlabBlock = GOTBlocks.woodSlabSingle4;
					plankSlabMeta = 3;
					plankStairBlock = GOTBlocks.stairsOlive;
					fenceBlock = GOTBlocks.fence2;
					fenceMeta = 11;
					fenceGateBlock = GOTBlocks.fenceGateOlive;
					woodBeamBlock = GOTBlocks.woodBeam6;
					woodBeamMeta = 3;
					doorBlock = GOTBlocks.doorOlive;
					trapdoorBlock = GOTBlocks.trapdoorOlive;
					break;
				case 1:
					woodBlock = GOTBlocks.wood3;
					woodMeta = 2;
					plankBlock = GOTBlocks.planks1;
					plankMeta = 14;
					plankSlabBlock = GOTBlocks.woodSlabSingle2;
					plankSlabMeta = 6;
					plankStairBlock = GOTBlocks.stairsDatePalm;
					fenceBlock = GOTBlocks.fence;
					fenceMeta = 14;
					fenceGateBlock = GOTBlocks.fenceGateDatePalm;
					woodBeamBlock = GOTBlocks.woodBeam3;
					woodBeamMeta = 2;
					doorBlock = GOTBlocks.doorDatePalm;
					trapdoorBlock = GOTBlocks.trapdoorDatePalm;
					break;
				case 2:
					woodBlock = GOTBlocks.wood8;
					woodMeta = 3;
					plankBlock = GOTBlocks.planks3;
					plankMeta = 3;
					plankSlabBlock = GOTBlocks.woodSlabSingle5;
					plankSlabMeta = 3;
					plankStairBlock = GOTBlocks.stairsPalm;
					fenceBlock = GOTBlocks.fence3;
					fenceMeta = 3;
					fenceGateBlock = GOTBlocks.fenceGatePalm;
					woodBeamBlock = GOTBlocks.woodBeam8;
					woodBeamMeta = 3;
					doorBlock = GOTBlocks.doorPalm;
					trapdoorBlock = GOTBlocks.trapdoorPalm;
					break;
			}
		}
		switch (random.nextInt(7)) {
			case 0:
			case 1:
				cropBlock = Blocks.carrots;
				break;
			case 2:
				cropBlock = GOTBlocks.lettuceCrop;
				break;
			default:
				cropBlock = Blocks.wheat;
				break;
		}
	}

	public enum City {
		BRAAVOS, GHISCAR, LORATH, VOLANTIS, TYROSH, QOHOR, QARTH, PENTOS, NORVOS, MYR, LYS
	}
}