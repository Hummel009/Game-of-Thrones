package got.common.world.structure.essos.common;

import java.util.*;
import java.util.Map.Entry;

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
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTStructureEssosBase extends GOTStructureBase {
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
	public Block plank2Block;
	public int plank2Meta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block gateMetalBlock;
	public Block bedBlock;
	public Block tableBlock;
	public Block cropBlock;
	public GOTItemBanner.BannerType bannerType;
	public boolean isBraavos;
	public boolean isGhiscar;
	public boolean isLorath;
	public boolean isVolantis;
	public boolean isTyrosh;
	public boolean isQohor;
	public boolean isQarth;
	public boolean isPentos;
	public boolean isNorvos;
	public boolean isMyr;
	public boolean isLys;
	public Block barsBlock;

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
		Map<Boolean, BannerType> map = new HashMap<>();
		map.put(isBraavos, GOTItemBanner.BannerType.BRAAVOS);
		map.put(isGhiscar, GOTItemBanner.BannerType.GHISCAR);
		map.put(isLorath, GOTItemBanner.BannerType.LORATH);
		map.put(isLys, GOTItemBanner.BannerType.LYS);
		map.put(isMyr, GOTItemBanner.BannerType.MYR);
		map.put(isNorvos, GOTItemBanner.BannerType.NORVOS);
		map.put(isPentos, GOTItemBanner.BannerType.PENTOS);
		map.put(isQarth, GOTItemBanner.BannerType.QARTH);
		map.put(isQohor, GOTItemBanner.BannerType.QOHOR);
		map.put(isTyrosh, GOTItemBanner.BannerType.TYROSH);
		map.put(isVolantis, GOTItemBanner.BannerType.VOLANTIS);

		for (Entry<Boolean, BannerType> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public EntityCreature getBartender(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosBartender(world));
		map.put(isGhiscar, new GOTEntityGhiscarBartender(world));
		map.put(isLorath, new GOTEntityLorathBartender(world));
		map.put(isLys, new GOTEntityLysBartender(world));
		map.put(isMyr, new GOTEntityMyrBartender(world));
		map.put(isNorvos, new GOTEntityNorvosBartender(world));
		map.put(isPentos, new GOTEntityPentosBartender(world));
		map.put(isQarth, new GOTEntityQarthBartender(world));
		map.put(isQohor, new GOTEntityQohorBartender(world));
		map.put(isTyrosh, new GOTEntityTyroshBartender(world));
		map.put(isVolantis, new GOTEntityVolantisBartender(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBlacksmith(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosBlacksmith(world));
		map.put(isGhiscar, new GOTEntityGhiscarBlacksmith(world));
		map.put(isLorath, new GOTEntityLorathBlacksmith(world));
		map.put(isLys, new GOTEntityLysBlacksmith(world));
		map.put(isMyr, new GOTEntityMyrBlacksmith(world));
		map.put(isNorvos, new GOTEntityNorvosBlacksmith(world));
		map.put(isPentos, new GOTEntityPentosBlacksmith(world));
		map.put(isQarth, new GOTEntityQarthBlacksmith(world));
		map.put(isQohor, new GOTEntityQohorBlacksmith(world));
		map.put(isTyrosh, new GOTEntityTyroshBlacksmith(world));
		map.put(isVolantis, new GOTEntityVolantisBlacksmith(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTChestContents getChestContents() {
		Map<Boolean, GOTChestContents> map = new HashMap<>();
		map.put(isBraavos, GOTChestContents.BRAAVOS);
		map.put(isGhiscar, GOTChestContents.GHISCAR);
		map.put(isLorath, GOTChestContents.LORATH);
		map.put(isLys, GOTChestContents.LYS);
		map.put(isMyr, GOTChestContents.MYR);
		map.put(isNorvos, GOTChestContents.NORVOS);
		map.put(isPentos, GOTChestContents.PENTOS);
		map.put(isQarth, GOTChestContents.QARTH);
		map.put(isQohor, GOTChestContents.QOHOR);
		map.put(isTyrosh, GOTChestContents.TYROSH);
		map.put(isVolantis, GOTChestContents.VOLANTIS);

		for (Entry<Boolean, GOTChestContents> chest : map.entrySet()) {
			if (Boolean.TRUE.equals(chest.getKey())) {
				return chest.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmer(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosFarmer(world));
		map.put(isGhiscar, new GOTEntityGhiscarSlaver(world));
		map.put(isLorath, new GOTEntityLorathFarmer(world));
		map.put(isLys, new GOTEntityLysSlaver(world));
		map.put(isMyr, new GOTEntityMyrSlaver(world));
		map.put(isNorvos, new GOTEntityNorvosFarmer(world));
		map.put(isPentos, new GOTEntityPentosFarmer(world));
		map.put(isQarth, new GOTEntityQarthFarmer(world));
		map.put(isQohor, new GOTEntityQohorFarmer(world));
		map.put(isTyrosh, new GOTEntityTyroshSlaver(world));
		map.put(isVolantis, new GOTEntityVolantisSlaver(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmhand(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosFarmhand(world));
		map.put(isGhiscar, new GOTEntityGhiscarSlave(world));
		map.put(isLorath, new GOTEntityLorathFarmhand(world));
		map.put(isLys, new GOTEntityLysSlave(world));
		map.put(isMyr, new GOTEntityMyrSlave(world));
		map.put(isNorvos, new GOTEntityNorvosFarmhand(world));
		map.put(isPentos, new GOTEntityPentosFarmhand(world));
		map.put(isQarth, new GOTEntityQarthFarmhand(world));
		map.put(isQohor, new GOTEntityQohorFarmhand(world));
		map.put(isTyrosh, new GOTEntityTyroshSlave(world));
		map.put(isVolantis, new GOTEntityVolantisSlave(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getGeneral(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosGeneral(world));
		map.put(isGhiscar, new GOTEntityGhiscarAdmiral(world));
		map.put(isLorath, new GOTEntityLorathGeneral(world));
		map.put(isLys, new GOTEntityLysGeneral(world));
		map.put(isMyr, new GOTEntityMyrGeneral(world));
		map.put(isNorvos, new GOTEntityNorvosGuardCaptain(world));
		map.put(isPentos, new GOTEntityPentosGuardCaptain(world));
		map.put(isQarth, new GOTEntityQarthGuardCaptain(world));
		map.put(isQohor, new GOTEntityQohorGuardCaptain(world));
		map.put(isTyrosh, new GOTEntityTyroshGeneral(world));
		map.put(isVolantis, new GOTEntityVolantisGeneral(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getMan(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosMan(world));
		map.put(isGhiscar, new GOTEntityGhiscarMan(world));
		map.put(isLorath, new GOTEntityLorathMan(world));
		map.put(isLys, new GOTEntityLysMan(world));
		map.put(isMyr, new GOTEntityMyrMan(world));
		map.put(isNorvos, new GOTEntityNorvosMan(world));
		map.put(isPentos, new GOTEntityPentosMan(world));
		map.put(isQarth, new GOTEntityQarthMan(world));
		map.put(isQohor, new GOTEntityQohorMan(world));
		map.put(isTyrosh, new GOTEntityTyroshMan(world));
		map.put(isVolantis, new GOTEntityVolantisMan(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public ItemStack getRandomItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.gobletCopper), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3), new ItemStack(GOTRegistry.gemsbokHorn), new ItemStack(GOTRegistry.lionFur) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer) };
		return items[random.nextInt(items.length)].copy();
	}

	public GOTEntityNPC getSoldier(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosSoldier(world));
		map.put(isGhiscar, new GOTEntityGhiscarCorsair(world));
		map.put(isLorath, new GOTEntityLorathSoldier(world));
		map.put(isLys, new GOTEntityLysSoldier(world));
		map.put(isMyr, new GOTEntityMyrSoldier(world));
		map.put(isNorvos, new GOTEntityNorvosLevyman(world));
		map.put(isPentos, new GOTEntityPentosLevyman(world));
		map.put(isQarth, new GOTEntityQarthLevyman(world));
		map.put(isQohor, new GOTEntityQohorLevyman(world));
		map.put(isTyrosh, new GOTEntityTyroshSoldier(world));
		map.put(isVolantis, new GOTEntityVolantisSoldier(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		Map<Boolean, GOTEntityNPC> map = new HashMap<>();
		map.put(isBraavos, new GOTEntityBraavosSoldierArcher(world));
		map.put(isGhiscar, new GOTEntityGhiscarCorsairArcher(world));
		map.put(isLorath, new GOTEntityLorathSoldierArcher(world));
		map.put(isLys, new GOTEntityLysSoldierArcher(world));
		map.put(isMyr, new GOTEntityMyrSoldierArcher(world));
		map.put(isNorvos, new GOTEntityNorvosLevymanArcher(world));
		map.put(isPentos, new GOTEntityPentosLevymanArcher(world));
		map.put(isQarth, new GOTEntityQarthLevymanArcher(world));
		map.put(isQohor, new GOTEntityQohorLevymanArcher(world));
		map.put(isTyrosh, new GOTEntityTyroshSoldierArcher(world));
		map.put(isVolantis, new GOTEntityVolantisSoldierArcher(world));

		for (Entry<Boolean, GOTEntityNPC> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public Block getTable() {
		Map<Boolean, Block> map = new HashMap<>();
		map.put(isBraavos, GOTRegistry.tableBraavos);
		map.put(isGhiscar, GOTRegistry.tableGhiscar);
		map.put(isLorath, GOTRegistry.tableLorath);
		map.put(isLys, GOTRegistry.tableLys);
		map.put(isMyr, GOTRegistry.tableMyr);
		map.put(isNorvos, GOTRegistry.tableNorvos);
		map.put(isPentos, GOTRegistry.tablePentos);
		map.put(isQarth, GOTRegistry.tableQarth);
		map.put(isQohor, GOTRegistry.tableQohor);
		map.put(isTyrosh, GOTRegistry.tableTyrosh);
		map.put(isVolantis, GOTRegistry.tableVolantis);

		for (Entry<Boolean, Block> npc : map.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public boolean isSandstone() {
		return isGhiscar || isQarth || isPentos;
	}

	public boolean isNorthernTrees() {
		return isBraavos || isLorath || isNorvos || isQohor;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (isSandstone()) {
			stoneBlock = Blocks.sandstone;
			stoneMeta = 0;
			stoneStairBlock = Blocks.sandstone_stairs;
			stoneWallBlock = GOTRegistry.wallStoneV;
			stoneWallMeta = 4;
			gateMetalBlock = GOTRegistry.gateBronzeBars;
			if (canUseRedBricks() && random.nextInt(4) == 0) {
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
			} else {
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
			}
		} else if (random.nextBoolean() || forceMonotypeWood()) {
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
				break;
			default:
				break;
			}
		}
		woodBeamMeta4 = woodBeamMeta | 4;
		woodBeamMeta8 = woodBeamMeta | 8;
		plank2Block = GOTRegistry.planks2;
		plank2Meta = 11;
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
}
