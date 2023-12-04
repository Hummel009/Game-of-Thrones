package got.common.world.structure.westeros.wildling;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.entity.westeros.legendary.captain.GOTEntityManceRayder;
import got.common.entity.westeros.legendary.warrior.GOTEntityTormund;
import got.common.entity.westeros.legendary.warrior.GOTEntityYgritte;
import got.common.entity.westeros.wildling.GOTEntityWildlingChieftain;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureWildlingChieftainHouse extends GOTStructureBase {
	public Block woodBlock;
	public int woodMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block slabBlock;
	public int slabMeta;
	public Block stairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block doorBlock;
	public Block floorBlock;
	public int floorMeta;
	public boolean isHardhome;

	public GOTStructureWildlingChieftainHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 5);
		int j15;
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -5; i13 <= 5; ++i13) {
				for (int k15 = -6; k15 <= 6; ++k15) {
					j15 = getTopBlock(world, i13, k15);
					getBlock(world, i13, j15 - 1, k15);
					if (j15 < minHeight) {
						minHeight = j15;
					}
					if (j15 > maxHeight) {
						maxHeight = j15;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		woodBlock = Blocks.log;
		woodMeta = 1;
		plankBlock = Blocks.planks;
		plankMeta = 1;
		slabBlock = Blocks.wooden_slab;
		slabMeta = 1;
		stairBlock = Blocks.spruce_stairs;
		fenceBlock = Blocks.fence;
		fenceMeta = 0;
		doorBlock = GOTBlocks.doorSpruce;
		floorBlock = Blocks.stained_hardened_clay;
		floorMeta = 15;
		int k14;
		int j14;
		int i1;
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k14 = -6; k14 <= 6; ++k14) {
				for (j14 = 1; j14 <= 10; ++j14) {
					setAir(world, i1, j14, k14);
				}
				for (j14 = 0; (j14 == 0 || !isOpaque(world, i1, j14, k14)) && getY(j14) >= 0; --j14) {
					if (getBlock(world, i1, j14 + 1, k14).isOpaqueCube()) {
						setBlockAndMetadata(world, i1, j14, k14, Blocks.dirt, 0);
					} else {
						setBlockAndMetadata(world, i1, j14, k14, Blocks.grass, 0);
					}
					setGrassToDirt(world, i1, j14 - 1, k14);
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k14 = -5; k14 <= 5; ++k14) {
				setBlockAndMetadata(world, i1, 0, k14, floorBlock, floorMeta);
				if (random.nextInt(2) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k14, GOTBlocks.thatchFloor, 0);
			}
		}
		int k13;
		for (int i14 : new int[]{-4, 4}) {
			for (k13 = -4; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i14, 1, k13, woodBlock, woodMeta | 8);
				setBlockAndMetadata(world, i14, 4, k13, woodBlock, woodMeta | 8);
			}
			for (j15 = 1; j15 <= 4; ++j15) {
				setBlockAndMetadata(world, i14, j15, -5, woodBlock, woodMeta);
				setBlockAndMetadata(world, i14, j15, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, i14, j15, 5, woodBlock, woodMeta);
			}
		}
		for (int i14 : new int[]{-3, 3}) {
			for (k13 = -4; k13 <= 4; ++k13) {
				setBlockAndMetadata(world, i14, 1, k13, plankBlock, plankMeta);
			}
			for (j15 = 2; j15 <= 3; ++j15) {
				setBlockAndMetadata(world, i14, j15, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j15, -1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j15, 1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j15, 4, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, i14, 3, -3, stairBlock, 7);
			setBlockAndMetadata(world, i14, 3, -2, stairBlock, 6);
			setBlockAndMetadata(world, i14, 3, 2, stairBlock, 7);
			setBlockAndMetadata(world, i14, 3, 3, stairBlock, 6);
			for (j15 = 1; j15 <= 5; ++j15) {
				setBlockAndMetadata(world, i14, j15, 0, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, i14, 1, -5, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i14, 2, -5, stairBlock, 2);
			setBlockAndMetadata(world, i14, 3, -5, stairBlock, 6);
			setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
		}
		int[] i15 = {-2, 2};
		k14 = i15.length;
		for (j14 = 0; j14 < k14; ++j14) {
			int i14 = i15[j14];
			for (j15 = 1; j15 <= 3; ++j15) {
				setBlockAndMetadata(world, i14, j15, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, j15, -5, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
			setBlockAndMetadata(world, i14, 2, -6, Blocks.torch, 4);
			setBlockAndMetadata(world, i14, 3, -6, Blocks.skull, 2);
		}
		int j1;
		for (j1 = 1; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, -1, j1, -4, woodBlock, woodMeta);
			setBlockAndMetadata(world, 1, j1, -4, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, -1, 2, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 2, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, -1, 3, -5, stairBlock, 4);
		setBlockAndMetadata(world, -1, 4, -5, stairBlock, 1);
		setBlockAndMetadata(world, 1, 3, -5, stairBlock, 5);
		setBlockAndMetadata(world, 1, 4, -5, stairBlock, 0);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, 0, 3, -4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, -5, slabBlock, slabMeta | 8);
		int i12;
		for (i12 = -3; i12 <= 3; ++i12) {
			setBlockAndMetadata(world, i12, 4, -4, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i12, 5, -5, stairBlock, 6);
		}
		setBlockAndMetadata(world, -2, 5, -4, Blocks.skull, 3);
		setBlockAndMetadata(world, 2, 5, -4, Blocks.skull, 3);
		for (i12 = -2; i12 <= 2; ++i12) {
			setBlockAndMetadata(world, i12, 6, -5, woodBlock, woodMeta | 4);
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, 7, -5, woodBlock, woodMeta | 4);
		}
		for (j1 = 4; j1 <= 9; ++j1) {
			setBlockAndMetadata(world, 0, j1, -5, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 9, -4, stairBlock, 7);
		setBlockAndMetadata(world, 0, 6, -6, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 5, -4, Blocks.torch, 3);
		placeWallBanner(world, 0, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
		placeWallBanner(world, -2, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
		placeWallBanner(world, 2, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
		for (i12 = -3; i12 <= 3; ++i12) {
			setBlockAndMetadata(world, i12, 1, 5, woodBlock, woodMeta | 4);
			setBlockAndMetadata(world, i12, 2, 5, stairBlock, 3);
			setBlockAndMetadata(world, i12, 3, 5, stairBlock, 7);
			setBlockAndMetadata(world, i12, 4, 5, woodBlock, woodMeta | 4);
		}
		setBlockAndMetadata(world, -3, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -1, 5, 5, slabBlock, slabMeta | 8);
		setBlockAndMetadata(world, 0, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 1, 5, 5, slabBlock, slabMeta | 8);
		setBlockAndMetadata(world, 2, 5, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 5, 5, plankBlock, plankMeta);
		int j12;
		for (i12 = -2; i12 <= 2; ++i12) {
			for (j12 = 6; j12 <= 7; ++j12) {
				setBlockAndMetadata(world, i12, j12, 5, plankBlock, plankMeta);
			}
		}
		for (int i14 : new int[]{-2, 2}) {
			for (j15 = 1; j15 <= 4; ++j15) {
				setBlockAndMetadata(world, i14, j15, 4, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, i14, 5, 4, fenceBlock, fenceMeta);
		}
		int j13;
		for (j13 = 4; j13 <= 5; ++j13) {
			setBlockAndMetadata(world, -3, j13, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, j13, 4, plankBlock, plankMeta);
		}
		for (j13 = 7; j13 <= 9; ++j13) {
			setBlockAndMetadata(world, 0, j13, 5, woodBlock, woodMeta);
		}
		setBlockAndMetadata(world, 0, 9, 4, stairBlock, 6);
		setBlockAndMetadata(world, 0, 5, 4, Blocks.torch, 4);
		placeWallBanner(world, 0, 4, 5, GOTItemBanner.BannerType.WILDLING, 2);
		setBlockAndMetadata(world, -1, 4, 4, Blocks.skull, 2);
		setBlockAndMetadata(world, 1, 4, 4, Blocks.skull, 2);
		setBlockAndMetadata(world, 0, 3, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, 6, stairBlock, 7);
		setBlockAndMetadata(world, 0, 4, 6, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 5, 6, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 6, 6, stairBlock, 3);
		setBlockAndMetadata(world, -2, 5, 0, Blocks.torch, 2);
		placeWallBanner(world, -3, 3, 0, GOTItemBanner.BannerType.WILDLING, 1);
		setBlockAndMetadata(world, 2, 5, 0, Blocks.torch, 1);
		placeWallBanner(world, 3, 3, 0, GOTItemBanner.BannerType.WILDLING, 3);
		int k12;
		for (k12 = -3; k12 <= -1; ++k12) {
			setBlockAndMetadata(world, -3, 4, k12, stairBlock, 0);
			setBlockAndMetadata(world, 3, 4, k12, stairBlock, 1);
		}
		for (k12 = -4; k12 <= -1; ++k12) {
			setBlockAndMetadata(world, -3, 5, k12, stairBlock, 4);
			setBlockAndMetadata(world, 3, 5, k12, stairBlock, 5);
		}
		for (k12 = 1; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, -3, 4, k12, stairBlock, 0);
			setBlockAndMetadata(world, 3, 4, k12, stairBlock, 1);
			setBlockAndMetadata(world, -3, 5, k12, stairBlock, 4);
			setBlockAndMetadata(world, 3, 5, k12, stairBlock, 5);
		}
		for (k12 = -6; k12 <= 6; ++k12) {
			setBlockAndMetadata(world, -5, 4, k12, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, -4, 5, k12, stairBlock, 1);
			setBlockAndMetadata(world, -3, 6, k12, stairBlock, 1);
			setBlockAndMetadata(world, -2, 7, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 8, k12, stairBlock, 1);
			setBlockAndMetadata(world, -1, 9, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 10, k12, stairBlock, 1);
			setBlockAndMetadata(world, 0, 10, k12, woodBlock, woodMeta | 8);
			setBlockAndMetadata(world, 1, 10, k12, stairBlock, 0);
			setBlockAndMetadata(world, 1, 9, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 2, 8, k12, stairBlock, 0);
			setBlockAndMetadata(world, 2, 7, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 6, k12, stairBlock, 0);
			setBlockAndMetadata(world, 4, 5, k12, stairBlock, 0);
			setBlockAndMetadata(world, 5, 4, k12, slabBlock, slabMeta | 8);
		}
		for (int k15 : new int[]{-6, 6}) {
			setBlockAndMetadata(world, -4, 4, k15, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, -3, 5, k15, stairBlock, 4);
			setBlockAndMetadata(world, -2, 6, k15, stairBlock, 4);
			setBlockAndMetadata(world, -1, 7, k15, stairBlock, 4);
			setBlockAndMetadata(world, -1, 8, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 8, k15, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 7, k15, stairBlock, 5);
			setBlockAndMetadata(world, 2, 6, k15, stairBlock, 5);
			setBlockAndMetadata(world, 3, 5, k15, stairBlock, 5);
			setBlockAndMetadata(world, 4, 4, k15, slabBlock, slabMeta | 8);
		}
		setBlockAndMetadata(world, 0, 11, -6, stairBlock, 3);
		setBlockAndMetadata(world, 0, 11, -7, stairBlock, 6);
		setBlockAndMetadata(world, 0, 12, -7, stairBlock, 3);
		setBlockAndMetadata(world, 0, 11, 6, stairBlock, 2);
		setBlockAndMetadata(world, 0, 11, 7, stairBlock, 7);
		setBlockAndMetadata(world, 0, 12, 7, stairBlock, 2);
		int k1;
		for (k1 = -1; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -1, 10, k1, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 10, k1, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 11, k1, stairBlock, 1);
			setBlockAndMetadata(world, 1, 11, k1, stairBlock, 0);
		}
		setBlockAndMetadata(world, 0, 11, -1, stairBlock, 2);
		setBlockAndMetadata(world, 0, 11, 1, stairBlock, 3);
		setAir(world, 0, 10, 0);
		for (int l = 0; l <= 2; ++l) {
			j12 = 4 + l * 2;
			setBlockAndMetadata(world, -4 + l, j12, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, -4 + l, j12 + 1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, -4 + l, j12 + 2, 0, stairBlock, 1);
			setBlockAndMetadata(world, 4 - l, j12, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, 4 - l, j12 + 1, 0, woodBlock, woodMeta);
			setBlockAndMetadata(world, 4 - l, j12 + 2, 0, stairBlock, 0);
		}
		for (k1 = -4; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -2, 6, k1, stairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k1, stairBlock, 5);
		}
		for (k1 = -3; k1 <= 3; ++k1) {
			setBlockAndMetadata(world, -1, 8, k1, stairBlock, 4);
			setBlockAndMetadata(world, 1, 8, k1, stairBlock, 5);
		}
		for (int i14 : new int[]{-1, 1}) {
			setBlockAndMetadata(world, i14, 8, -5, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, -4, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, i14, 8, 5, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, -1, 7, -4, stairBlock, 4);
		setBlockAndMetadata(world, 1, 7, -4, stairBlock, 5);
		setBlockAndMetadata(world, -1, 7, 4, stairBlock, 4);
		setBlockAndMetadata(world, 1, 7, 4, stairBlock, 5);
		int j16;
		for (j16 = 0; j16 >= -5; --j16) {
			for (int i16 = -1; i16 <= 1; ++i16) {
				for (int k16 = -1; k16 <= 1; ++k16) {
					if (i16 == 0 && k16 == 0) {
						setAir(world, 0, j16, 0);
						continue;
					}
					setBlockAndMetadata(world, i16, j16, k16, Blocks.cobblestone, 0);
				}
			}
		}
		setBlockAndMetadata(world, 0, -6, 0, GOTBlocks.hearth, 0);
		setBlockAndMetadata(world, 0, -5, 0, Blocks.fire, 0);
		setBlockAndMetadata(world, 0, 0, 0, GOTBlocks.bronzeBars, 0);
		setAir(world, 0, 1, 0);
		setBlockAndMetadata(world, 0, 1, 3, GOTBlocks.strawBed, 0);
		setBlockAndMetadata(world, 0, 1, 4, GOTBlocks.strawBed, 8);
		for (j16 = 1; j16 <= 2; ++j16) {
			setBlockAndMetadata(world, -1, j16, 4, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, j16, 4, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 1, 3, GOTBlocks.tableWildling, 0);
		setBlockAndMetadata(world, -2, 1, 2, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 1, 3, Blocks.furnace, 5);
		placeChest(world, random, 2, 1, 2, 5, GOTChestContents.BEYOND_WALL);
		if (isHardhome) {
			spawnNPCAndSetHome(new GOTEntityYgritte(world), world, 1, 1, 0, 8);
			spawnNPCAndSetHome(new GOTEntityTormund(world), world, -1, 1, 0, 8);
			spawnNPCAndSetHome(new GOTEntityManceRayder(world), world, -1, 1, -1, 8);
		} else {
			spawnNPCAndSetHome(new GOTEntityWildlingChieftain(world), world, 0, 1, 0, 8);
		}
		return true;
	}

	public GOTStructureBase setIsHardhome() {
		isHardhome = true;
		return this;
	}
}
