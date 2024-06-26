package got.common.world.structure.essos.asshai;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.essos.asshai.GOTEntityAsshaiCaptain;
import got.common.entity.essos.asshai.GOTEntityAsshaiShadowbinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiSpherebinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiWarrior;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureAsshaiFort extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiFort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 19);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -18; i1 <= 26; ++i1) {
				for (int k1 = -18; k1 <= 20; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 16) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -17; i1 <= 25; ++i1) {
			for (int k1 = -18; k1 <= 19; ++k1) {
				for (int j1 = 1; j1 <= 16; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("asshai_fort");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("BRICK_CARVED", brickCarvedBlock, brickCarvedMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("SMOOTH", smoothBlock, smoothMeta);
		associateBlockMetaAlias("SMOOTH_SLAB", smoothSlabBlock, smoothSlabMeta);
		associateBlockMetaAlias("TILE", tileBlock, tileMeta);
		associateBlockMetaAlias("TILE_SLAB", tileSlabBlock, tileSlabMeta);
		associateBlockMetaAlias("TILE_SLAB_INV", tileSlabBlock, tileSlabMeta | 8);
		associateBlockAlias("TILE_STAIR", tileStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TRAPDOOR", GOTBlocks.trapdoorCharred);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta | 4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta | 8);
		addBlockMetaAliasOption("GROUND", rockBlock, rockMeta);
		addBlockMetaAliasOption("GROUND", GOTBlocks.asshaiDirt, 0);
		addBlockMetaAliasOption("GROUND", GOTBlocks.basaltGravel, 0);
		associateBlockAlias("GATE_IRON", gateBlock);
		associateBlockAlias("GATE_STANDARD", gateBlock);
		associateBlockMetaAlias("BARS", barsBlock, 0);
		associateBlockMetaAlias("CHANDELIER", chandelierBlock, chandelierMeta);
		associateBlockMetaAlias("TABLE", GOTBlocks.tableAsshai, 0);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, -2, 11, -15, GOTBlocks.commandTable, 0);
		placeBigTorch(world, -3, 2, -18);
		placeBigTorch(world, 3, 2, -18);
		placeBigTorch(world, 19, 2, -11);
		placeBigTorch(world, -3, 2, -10);
		placeBigTorch(world, 3, 2, -10);
		placeBigTorch(world, 2, 2, 4);
		placeBigTorch(world, 5, 2, 4);
		placeBigTorch(world, -11, 2, 12);
		placeBigTorch(world, 18, 4, -13);
		placeBigTorch(world, 10, 4, -13);
		placeBigTorch(world, 21, 4, -10);
		placeBigTorch(world, 21, 4, -2);
		placeBigTorch(world, 24, 4, 1);
		placeBigTorch(world, 20, 4, 1);
		placeBigTorch(world, -13, 4, 3);
		placeBigTorch(world, 5, 4, 9);
		placeBigTorch(world, 2, 4, 9);
		placeBigTorch(world, -13, 4, 11);
		placeBigTorch(world, 11, 4, 14);
		placeBigTorch(world, -4, 4, 14);
		placeBigTorch(world, -10, 4, 14);
		placeBigTorch(world, 20, 4, 17);
		placeBigTorch(world, 22, 10, -14);
		placeBigTorch(world, 22, 15, -14);
		placeWeaponRack(world, 9, 2, 2, 6, getRandWeaponItem(random));
		placeWeaponRack(world, 10, 2, 3, 5, getRandWeaponItem(random));
		placeWeaponRack(world, 8, 2, 3, 7, getRandWeaponItem(random));
		placeWeaponRack(world, 9, 2, 4, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 15, 2, 7, 6, getRandWeaponItem(random));
		placeWeaponRack(world, 16, 2, 8, 5, getRandWeaponItem(random));
		placeWeaponRack(world, 14, 2, 8, 7, getRandWeaponItem(random));
		placeWeaponRack(world, 15, 2, 9, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 6, 5, 10, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 1, 5, 10, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 10, 5, 12, 4, getRandWeaponItem(random));
		placeWeaponRack(world, -3, 5, 12, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 6, 6, 10, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 1, 6, 10, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 10, 6, 12, 4, getRandWeaponItem(random));
		placeWeaponRack(world, -3, 6, 12, 4, getRandWeaponItem(random));
		placeArmorStand(world, 8, 4, 10, 2, getRandArmorItems(random));
		placeArmorStand(world, -1, 4, 10, 2, getRandArmorItems(random));
		placeArmorStand(world, 7, 4, 12, 3, getRandArmorItems(random));
		placeArmorStand(world, 0, 4, 12, 1, getRandArmorItems(random));
		placeArmorStand(world, 6, 4, 13, 2, getRandArmorItems(random));
		placeArmorStand(world, 1, 4, 13, 2, getRandArmorItems(random));
		placeChest(world, random, 15, 1, 7, GOTBlocks.chestStone, 2, GOTChestContents.ASSHAI);
		placeChest(world, random, 9, 4, 11, GOTBlocks.chestStone, 3, GOTChestContents.ASSHAI);
		placeChest(world, random, -2, 4, 11, GOTBlocks.chestStone, 3, GOTChestContents.ASSHAI);
		placeChest(world, random, 12, 4, 13, GOTBlocks.chestStone, 5, GOTChestContents.ASSHAI);
		placeChest(world, random, -5, 4, 13, GOTBlocks.chestStone, 4, GOTChestContents.ASSHAI);
		placeChest(world, random, 12, 4, 17, GOTBlocks.chestStone, 5, GOTChestContents.ASSHAI);
		placeChest(world, random, -5, 4, 17, GOTBlocks.chestStone, 4, GOTChestContents.ASSHAI);
		for (int j1 = 4; j1 <= 5; ++j1) {
			for (int i1 : new int[]{-3, -1, 1}) {
				setBlockAndMetadata(world, i1, j1, 17, bedBlock, 0);
				setBlockAndMetadata(world, i1, j1, 18, bedBlock, 8);
			}
			for (int i1 : new int[]{6, 8, 10}) {
				setBlockAndMetadata(world, i1, j1, 17, bedBlock, 0);
				setBlockAndMetadata(world, i1, j1, 18, bedBlock, 8);
			}
		}
		for (int k1 = -7; k1 <= -4; ++k1) {
			for (int i1 : new int[]{-13, -11}) {
				if (random.nextBoolean()) {
					placePlate(world, random, i1, 5, k1, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
					continue;
				}
				placeMug(world, random, i1, 5, k1, random.nextInt(4), GOTFoods.DEFAULT_DRINK);
			}
		}
		GOTEntityNPC captain = new GOTEntityAsshaiCaptain(world);
		captain.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(captain, world, 0, 1, 0, 8);
		for (int l = 0; l < 5; ++l) {
			GOTEntityNPC shadowbinder = new GOTEntityAsshaiShadowbinder(world);
			shadowbinder.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(shadowbinder, world, 0, 1, 0, 32);
		}
		for (int l = 0; l < 2; ++l) {
			GOTEntityNPC spherebinder = new GOTEntityAsshaiSpherebinder(world);
			spherebinder.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(spherebinder, world, 0, 1, 0, 32);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass1(GOTEntityAsshaiWarrior.class);
		respawner.setSpawnClass2(GOTEntityAsshaiShadowbinder.class);
		respawner.setCheckRanges(32, -16, 20, 24);
		respawner.setSpawnRanges(24, -4, 8, 24);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}