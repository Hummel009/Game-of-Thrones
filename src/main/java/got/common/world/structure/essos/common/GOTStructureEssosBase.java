package got.common.world.structure.essos.common;

import java.util.*;
import java.util.Map.Entry;

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

	public GOTEntityNPC getMan(World world) {
		Map<Boolean, GOTEntityNPC> theMan = new HashMap<>();
		theMan.put(isBraavos, new GOTEntityBraavosMan(world));
		theMan.put(isGhiscar, new GOTEntityGhiscarMan(world));
		theMan.put(isLorath, new GOTEntityLorathMan(world));
		theMan.put(isLys, new GOTEntityLysMan(world));
		theMan.put(isMyr, new GOTEntityMyrMan(world));
		theMan.put(isNorvos, new GOTEntityNorvosMan(world));
		theMan.put(isPentos, new GOTEntityPentosMan(world));
		theMan.put(isQarth, new GOTEntityQarthMan(world));
		theMan.put(isQohor, new GOTEntityQohorMan(world));
		theMan.put(isTyrosh, new GOTEntityTyroshMan(world));
		theMan.put(isVolantis, new GOTEntityVolantisMan(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theMan.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public EntityCreature getBartender(World world) {
		Map<Boolean, GOTEntityNPC> theBartender = new HashMap<>();
		theBartender.put(isBraavos, new GOTEntityBraavosBartender(world));
		theBartender.put(isGhiscar, new GOTEntityGhiscarBartender(world));
		theBartender.put(isLorath, new GOTEntityLorathBartender(world));
		theBartender.put(isLys, new GOTEntityLysBartender(world));
		theBartender.put(isMyr, new GOTEntityMyrBartender(world));
		theBartender.put(isNorvos, new GOTEntityNorvosBartender(world));
		theBartender.put(isPentos, new GOTEntityPentosBartender(world));
		theBartender.put(isQarth, new GOTEntityQarthBartender(world));
		theBartender.put(isQohor, new GOTEntityQohorBartender(world));
		theBartender.put(isTyrosh, new GOTEntityTyroshBartender(world));
		theBartender.put(isVolantis, new GOTEntityVolantisBartender(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theBartender.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getBlacksmith(World world) {
		Map<Boolean, GOTEntityNPC> theBlacksmith = new HashMap<>();
		theBlacksmith.put(isBraavos, new GOTEntityBraavosBlacksmith(world));
		theBlacksmith.put(isGhiscar, new GOTEntityGhiscarBlacksmith(world));
		theBlacksmith.put(isLorath, new GOTEntityLorathBlacksmith(world));
		theBlacksmith.put(isLys, new GOTEntityLysBlacksmith(world));
		theBlacksmith.put(isMyr, new GOTEntityMyrBlacksmith(world));
		theBlacksmith.put(isNorvos, new GOTEntityNorvosBlacksmith(world));
		theBlacksmith.put(isPentos, new GOTEntityPentosBlacksmith(world));
		theBlacksmith.put(isQarth, new GOTEntityQarthBlacksmith(world));
		theBlacksmith.put(isQohor, new GOTEntityQohorBlacksmith(world));
		theBlacksmith.put(isTyrosh, new GOTEntityTyroshBlacksmith(world));
		theBlacksmith.put(isVolantis, new GOTEntityVolantisBlacksmith(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theBlacksmith.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldierArcher(World world) {
		Map<Boolean, GOTEntityNPC> theSoldierArcher = new HashMap<>();
		theSoldierArcher.put(isBraavos, new GOTEntityBraavosSoldierArcher(world));
		theSoldierArcher.put(isGhiscar, new GOTEntityGhiscarCorsairArcher(world));
		theSoldierArcher.put(isLorath, new GOTEntityLorathSoldierArcher(world));
		theSoldierArcher.put(isLys, new GOTEntityLysSoldierArcher(world));
		theSoldierArcher.put(isMyr, new GOTEntityMyrSoldierArcher(world));
		theSoldierArcher.put(isNorvos, new GOTEntityNorvosLevymanArcher(world));
		theSoldierArcher.put(isPentos, new GOTEntityPentosLevymanArcher(world));
		theSoldierArcher.put(isQarth, new GOTEntityQarthLevymanArcher(world));
		theSoldierArcher.put(isQohor, new GOTEntityQohorLevymanArcher(world));
		theSoldierArcher.put(isTyrosh, new GOTEntityTyroshSoldierArcher(world));
		theSoldierArcher.put(isVolantis, new GOTEntityVolantisSoldierArcher(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theSoldierArcher.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getGeneral(World world) {
		Map<Boolean, GOTEntityNPC> theGeneral = new HashMap<>();
		theGeneral.put(isBraavos, new GOTEntityBraavosGeneral(world));
		theGeneral.put(isGhiscar, new GOTEntityGhiscarAdmiral(world));
		theGeneral.put(isLorath, new GOTEntityLorathGeneral(world));
		theGeneral.put(isLys, new GOTEntityLysGeneral(world));
		theGeneral.put(isMyr, new GOTEntityMyrGeneral(world));
		theGeneral.put(isNorvos, new GOTEntityNorvosGuardCaptain(world));
		theGeneral.put(isPentos, new GOTEntityPentosGuardCaptain(world));
		theGeneral.put(isQarth, new GOTEntityQarthGuardCaptain(world));
		theGeneral.put(isQohor, new GOTEntityQohorGuardCaptain(world));
		theGeneral.put(isTyrosh, new GOTEntityTyroshGeneral(world));
		theGeneral.put(isVolantis, new GOTEntityVolantisGeneral(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theGeneral.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getSoldier(World world) {
		Map<Boolean, GOTEntityNPC> theSoldier = new HashMap<>();
		theSoldier.put(isBraavos, new GOTEntityBraavosSoldier(world));
		theSoldier.put(isGhiscar, new GOTEntityGhiscarCorsair(world));
		theSoldier.put(isLorath, new GOTEntityLorathSoldier(world));
		theSoldier.put(isLys, new GOTEntityLysSoldier(world));
		theSoldier.put(isMyr, new GOTEntityMyrSoldier(world));
		theSoldier.put(isNorvos, new GOTEntityNorvosLevyman(world));
		theSoldier.put(isPentos, new GOTEntityPentosLevyman(world));
		theSoldier.put(isQarth, new GOTEntityQarthLevyman(world));
		theSoldier.put(isQohor, new GOTEntityQohorLevyman(world));
		theSoldier.put(isTyrosh, new GOTEntityTyroshSoldier(world));
		theSoldier.put(isVolantis, new GOTEntityVolantisSoldier(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theSoldier.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}
	
	public Block getTable() {
		Map<Boolean, Block> theTable = new HashMap<>();
		theTable.put(isBraavos, GOTRegistry.tableBraavos);
		theTable.put(isGhiscar, GOTRegistry.tableGhiscar);
		theTable.put(isLorath, GOTRegistry.tableLorath);
		theTable.put(isLys, GOTRegistry.tableLys);
		theTable.put(isMyr, GOTRegistry.tableMyr);
		theTable.put(isNorvos, GOTRegistry.tableNorvos);
		theTable.put(isPentos, GOTRegistry.tablePentos);
		theTable.put(isQarth, GOTRegistry.tableQarth);
		theTable.put(isQohor, GOTRegistry.tableQohor);
		theTable.put(isTyrosh, GOTRegistry.tableTyrosh);
		theTable.put(isVolantis, GOTRegistry.tableVolantis);

		for (Entry<Boolean, Block> npc : theTable.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}
	
	public BannerType getBannerType() {
		Map<Boolean, BannerType> theBannerType = new HashMap<>();
		theBannerType.put(isBraavos, GOTItemBanner.BannerType.BRAAVOS);
		theBannerType.put(isGhiscar, GOTItemBanner.BannerType.GHISCAR);
		theBannerType.put(isLorath, GOTItemBanner.BannerType.LORATH);
		theBannerType.put(isLys, GOTItemBanner.BannerType.LYS);
		theBannerType.put(isMyr, GOTItemBanner.BannerType.MYR);
		theBannerType.put(isNorvos, GOTItemBanner.BannerType.NORVOS);
		theBannerType.put(isPentos, GOTItemBanner.BannerType.PENTOS);
		theBannerType.put(isQarth, GOTItemBanner.BannerType.QARTH);
		theBannerType.put(isQohor, GOTItemBanner.BannerType.QOHOR);
		theBannerType.put(isTyrosh, GOTItemBanner.BannerType.TYROSH);
		theBannerType.put(isVolantis, GOTItemBanner.BannerType.VOLANTIS);

		for (Entry<Boolean, BannerType> npc : theBannerType.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmhand(World world) {
		Map<Boolean, GOTEntityNPC> theFarmhand = new HashMap<>();
	    theFarmhand.put(isBraavos, new GOTEntityBraavosFarmhand(world));
		theFarmhand.put(isGhiscar, new GOTEntityGhiscarSlave(world));
		theFarmhand.put(isLorath, new GOTEntityLorathFarmhand(world)); 
		theFarmhand.put(isLys, new GOTEntityLysSlave(world));
		theFarmhand.put(isMyr, new GOTEntityMyrSlave(world));
		theFarmhand.put(isNorvos, new GOTEntityNorvosFarmhand(world));
		theFarmhand.put(isPentos, new GOTEntityPentosFarmhand(world));
		theFarmhand.put(isQarth, new GOTEntityQarthFarmhand(world));
		theFarmhand.put(isQohor, new GOTEntityQohorFarmhand(world));
		theFarmhand.put(isTyrosh, new GOTEntityTyroshSlave(world));
		theFarmhand.put(isVolantis, new GOTEntityVolantisSlave(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theFarmhand.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public GOTEntityNPC getFarmer(World world) {
		Map<Boolean, GOTEntityNPC> theFarmer = new HashMap<>();
		theFarmer.put(isBraavos, new GOTEntityBraavosFarmer(world));
		theFarmer.put(isGhiscar, new GOTEntityGhiscarSlaver(world));
		theFarmer.put(isLorath, new GOTEntityLorathFarmer(world));
		theFarmer.put(isLys, new GOTEntityLysSlaver(world));
		theFarmer.put(isMyr, new GOTEntityMyrSlaver(world));
		theFarmer.put(isNorvos, new GOTEntityNorvosFarmer(world));
		theFarmer.put(isPentos, new GOTEntityPentosFarmer(world));
		theFarmer.put(isQarth, new GOTEntityQarthFarmer(world));
		theFarmer.put(isQohor, new GOTEntityQohorFarmer(world));
		theFarmer.put(isTyrosh, new GOTEntityTyroshSlaver(world));
		theFarmer.put(isVolantis, new GOTEntityVolantisSlaver(world));

		for (Entry<Boolean, GOTEntityNPC> npc : theFarmer.entrySet()) {
			if (Boolean.TRUE.equals(npc.getKey())) {
				return npc.getValue();
			}
		}
		return null;
	}

	public boolean canUseRedBricks() {
		return true;
	}

	public boolean forceCedarWood() {
		return false;
	}

	public ItemStack getRandomItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletGold), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.gobletCopper), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3), new ItemStack(GOTRegistry.gemsbokHorn), new ItemStack(GOTRegistry.lionFur) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		stoneBlock = Blocks.sandstone;
		stoneMeta = 0;
		stoneStairBlock = Blocks.sandstone_stairs;
		stoneWallBlock = GOTRegistry.wallStoneV;
		stoneWallMeta = 4;
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
		brick2Meta = 13;
		brick2SlabBlock = GOTRegistry.slabSingle7;
		brick2SlabMeta = 2;
		brick2StairBlock = GOTRegistry.stairsSandstoneBrickRed;
		brick2WallBlock = GOTRegistry.wallStone3;
		brick2WallMeta = 4;
		brick2CarvedBlock = GOTRegistry.brick3;
		brick2CarvedMeta = 15;
		pillar2Block = GOTRegistry.pillar1;
		pillar2Meta = 15;
		roofBlock = GOTRegistry.thatch;
		roofMeta = 1;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = GOTRegistry.stairsReed;
		if (random.nextBoolean() || forceCedarWood()) {
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
		gateMetalBlock = GOTRegistry.gateBronzeBars;
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
