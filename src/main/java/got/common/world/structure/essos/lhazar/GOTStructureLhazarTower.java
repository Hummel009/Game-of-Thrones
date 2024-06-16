package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.essos.lhazar.GOTEntityLhazarArcher;
import got.common.entity.essos.lhazar.GOTEntityLhazarWarrior;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarTower extends GOTStructureLhazarBase {
	public GOTStructureLhazarTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
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
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 1; j1 <= 16; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("lhazar_tower");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("WOOD|12", woodBlock, woodMeta | 0xC);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeChest(world, random, -2, 1, 0, GOTBlocks.chestBasket, 4, GOTChestContents.LHAZAR);
		placeSkull(world, random, 2, 2, 1);
		placeBarrel(world, random, -2, 2, -1, 4, GOTFoods.DEFAULT_DRINK);
		placeMug(world, random, 2, 2, -1, 2, GOTFoods.DEFAULT_DRINK);
		placePlate(world, random, 2, 2, 0, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placePlate(world, random, -2, 2, 1, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placeWallBanner(world, 0, 8, -3, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, 0, 8, 3, GOTItemBanner.BannerType.LHAZAR, 0);
		int warriors = 1 + random.nextInt(2);
		for (int l = 0; l < warriors; ++l) {
			GOTEntityLhazarWarrior warrior = random.nextInt(3) == 0 ? new GOTEntityLhazarArcher(world) : new GOTEntityLhazarWarrior(world);
			warrior.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(warrior, world, 0, 14, 0, 8);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass1(GOTEntityLhazarWarrior.class);
		respawner.setSpawnClass2(GOTEntityLhazarArcher.class);
		respawner.setCheckRanges(6, -20, 4, 4);
		respawner.setSpawnRanges(1, -2, 1, 8);
		placeNPCRespawner(respawner, world, 0, 14, 0);
		return true;
	}
}