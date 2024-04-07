package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityNPCRespawner;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosTower extends GOTStructureEssosBase {
	public GOTStructureEssosTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				for (j1 = 1; j1 <= 15; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_tower");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeChest(world, random, -1, 1, -1, getChest(), 4, getChestContents());
		placeMug(world, random, -1, 2, 1, 0, GOTFoods.ESSOS_DRINK);
		placeBarrel(world, random, 1, 2, 1, 2, GOTFoods.ESSOS_DRINK);
		placeWeaponRack(world, -1, 8, 0, 5, getRandomWeapon(random));
		placeWeaponRack(world, 1, 8, 0, 7, getRandomWeapon(random));
		placeWallBanner(world, 0, 14, -3, bannerType, 2);
		placeWallBanner(world, -3, 14, 0, bannerType, 3);
		placeWallBanner(world, 0, 14, 3, bannerType, 0);
		placeWallBanner(world, 3, 14, 0, bannerType, 1);
		int warriors = 2;
		for (int l = 0; l < warriors; ++l) {
			GOTEntityNPC warrior = getSoldier(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 15, 0, 8);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClasses(getSoldier(world).getClass(), getSoldierArcher(world).getClass());
		respawner.setCheckRanges(8, -4, 20, 8);
		respawner.setSpawnRanges(2, -1, 16, 8);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}
