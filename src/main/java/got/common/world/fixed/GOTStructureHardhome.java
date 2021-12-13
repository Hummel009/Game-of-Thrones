package got.common.world.fixed;

import java.util.Random;

import got.common.database.*;
import got.common.entity.westeros.legendary.captain.GOTEntityManceRayder;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.item.other.GOTItemBanner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureHardhome extends GOTVillageGen {
	public GOTStructureHardhome(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, GOTLocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Home extends GOTStructureBase {
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

		public Home(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int j1;
			int j12;
			int j13;
			int i1;
			int i12;
			int j14;
			int k1;
			int k12;
			int k13;
			int k14;
			int j15;
			int j16;
			this.setOriginAndRotation(world, i, j, k, rotation, 5);
			if (restrictions) {
				int minHeight = 0;
				int maxHeight = 0;
				for (int i13 = -5; i13 <= 5; ++i13) {
					for (int k15 = -6; k15 <= 6; ++k15) {
						j12 = getTopBlock(world, i13, k15);
						Block block = getBlock(world, i13, j12 - 1, k15);
						if (block != Blocks.grass && block != Blocks.stone) {
							return false;
						}
						if (j12 < minHeight) {
							minHeight = j12;
						}
						if (j12 > maxHeight) {
							maxHeight = j12;
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
			doorBlock = GOTRegistry.doorSpruce;
			floorBlock = Blocks.stained_hardened_clay;
			floorMeta = 15;
			for (i12 = -5; i12 <= 5; ++i12) {
				for (k13 = -6; k13 <= 6; ++k13) {
					for (j1 = 1; j1 <= 10; ++j1) {
						setAir(world, i12, j1, k13);
					}
					for (j1 = 0; (j1 == 0 || !isOpaque(world, i12, j1, k13)) && getY(j1) >= 0; --j1) {
						if (getBlock(world, i12, j1 + 1, k13).isOpaqueCube()) {
							setBlockAndMetadata(world, i12, j1, k13, Blocks.dirt, 0);
						} else {
							setBlockAndMetadata(world, i12, j1, k13, Blocks.grass, 0);
						}
						setGrassToDirt(world, i12, j1 - 1, k13);
					}
				}
			}
			for (i12 = -4; i12 <= 4; ++i12) {
				for (k13 = -5; k13 <= 5; ++k13) {
					setBlockAndMetadata(world, i12, 0, k13, floorBlock, floorMeta);
					if (random.nextInt(2) != 0) {
						continue;
					}
					setBlockAndMetadata(world, i12, 1, k13, GOTRegistry.thatchFloor, 0);
				}
			}
			for (int i14 : new int[] { -4, 4 }) {
				for (k14 = -4; k14 <= 4; ++k14) {
					setBlockAndMetadata(world, i14, 1, k14, woodBlock, woodMeta | 8);
					setBlockAndMetadata(world, i14, 4, k14, woodBlock, woodMeta | 8);
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i14, j12, -5, woodBlock, woodMeta);
					setBlockAndMetadata(world, i14, j12, 0, woodBlock, woodMeta);
					setBlockAndMetadata(world, i14, j12, 5, woodBlock, woodMeta);
				}
			}
			for (int i14 : new int[] { -3, 3 }) {
				for (k14 = -4; k14 <= 4; ++k14) {
					setBlockAndMetadata(world, i14, 1, k14, plankBlock, plankMeta);
				}
				for (j12 = 2; j12 <= 3; ++j12) {
					setBlockAndMetadata(world, i14, j12, -4, plankBlock, plankMeta);
					setBlockAndMetadata(world, i14, j12, -1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i14, j12, 1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i14, j12, 4, plankBlock, plankMeta);
				}
				setBlockAndMetadata(world, i14, 3, -3, stairBlock, 7);
				setBlockAndMetadata(world, i14, 3, -2, stairBlock, 6);
				setBlockAndMetadata(world, i14, 3, 2, stairBlock, 7);
				setBlockAndMetadata(world, i14, 3, 3, stairBlock, 6);
				for (j12 = 1; j12 <= 5; ++j12) {
					setBlockAndMetadata(world, i14, j12, 0, woodBlock, woodMeta);
				}
				setBlockAndMetadata(world, i14, 1, -5, woodBlock, woodMeta | 4);
				setBlockAndMetadata(world, i14, 2, -5, stairBlock, 2);
				setBlockAndMetadata(world, i14, 3, -5, stairBlock, 6);
				setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
			}
			int[] i15 = { -2, 2 };
			k13 = i15.length;
			for (j1 = 0; j1 < k13; ++j1) {
				int i14;
				i14 = i15[j1];
				for (j12 = 1; j12 <= 3; ++j12) {
					setBlockAndMetadata(world, i14, j12, -4, plankBlock, plankMeta);
					setBlockAndMetadata(world, i14, j12, -5, woodBlock, woodMeta);
				}
				setBlockAndMetadata(world, i14, 4, -5, slabBlock, slabMeta);
				setBlockAndMetadata(world, i14, 2, -6, Blocks.torch, 4);
				setBlockAndMetadata(world, i14, 3, -6, Blocks.skull, 2);
			}
			for (j16 = 1; j16 <= 3; ++j16) {
				setBlockAndMetadata(world, -1, j16, -4, woodBlock, woodMeta);
				setBlockAndMetadata(world, 1, j16, -4, woodBlock, woodMeta);
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
			for (i1 = -3; i1 <= 3; ++i1) {
				setBlockAndMetadata(world, i1, 4, -4, woodBlock, woodMeta | 4);
				setBlockAndMetadata(world, i1, 5, -5, stairBlock, 6);
			}
			setBlockAndMetadata(world, -2, 5, -4, Blocks.skull, 3);
			setBlockAndMetadata(world, 2, 5, -4, Blocks.skull, 3);
			for (i1 = -2; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, 6, -5, woodBlock, woodMeta | 4);
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 7, -5, woodBlock, woodMeta | 4);
			}
			for (j16 = 4; j16 <= 9; ++j16) {
				setBlockAndMetadata(world, 0, j16, -5, woodBlock, woodMeta);
			}
			setBlockAndMetadata(world, 0, 9, -4, stairBlock, 7);
			setBlockAndMetadata(world, 0, 6, -6, Blocks.torch, 4);
			setBlockAndMetadata(world, 0, 5, -4, Blocks.torch, 3);
			placeWallBanner(world, 0, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
			placeWallBanner(world, -2, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
			placeWallBanner(world, 2, 5, -5, GOTItemBanner.BannerType.WILDLING, 2);
			for (i1 = -3; i1 <= 3; ++i1) {
				setBlockAndMetadata(world, i1, 1, 5, woodBlock, woodMeta | 4);
				setBlockAndMetadata(world, i1, 2, 5, stairBlock, 3);
				setBlockAndMetadata(world, i1, 3, 5, stairBlock, 7);
				setBlockAndMetadata(world, i1, 4, 5, woodBlock, woodMeta | 4);
			}
			setBlockAndMetadata(world, -3, 5, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 5, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 5, 5, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, 0, 5, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 5, 5, slabBlock, slabMeta | 8);
			setBlockAndMetadata(world, 2, 5, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 5, 5, plankBlock, plankMeta);
			for (i1 = -2; i1 <= 2; ++i1) {
				for (j14 = 6; j14 <= 7; ++j14) {
					setBlockAndMetadata(world, i1, j14, 5, plankBlock, plankMeta);
				}
			}
			for (int i14 : new int[] { -2, 2 }) {
				for (j12 = 1; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i14, j12, 4, plankBlock, plankMeta);
				}
				setBlockAndMetadata(world, i14, 5, 4, fenceBlock, fenceMeta);
			}
			for (j15 = 4; j15 <= 5; ++j15) {
				setBlockAndMetadata(world, -3, j15, 4, plankBlock, plankMeta);
				setBlockAndMetadata(world, 3, j15, 4, plankBlock, plankMeta);
			}
			for (j15 = 7; j15 <= 9; ++j15) {
				setBlockAndMetadata(world, 0, j15, 5, woodBlock, woodMeta);
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
			for (k1 = -3; k1 <= -1; ++k1) {
				setBlockAndMetadata(world, -3, 4, k1, stairBlock, 0);
				setBlockAndMetadata(world, 3, 4, k1, stairBlock, 1);
			}
			for (k1 = -4; k1 <= -1; ++k1) {
				setBlockAndMetadata(world, -3, 5, k1, stairBlock, 4);
				setBlockAndMetadata(world, 3, 5, k1, stairBlock, 5);
			}
			for (k1 = 1; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, -3, 4, k1, stairBlock, 0);
				setBlockAndMetadata(world, 3, 4, k1, stairBlock, 1);
				setBlockAndMetadata(world, -3, 5, k1, stairBlock, 4);
				setBlockAndMetadata(world, 3, 5, k1, stairBlock, 5);
			}
			for (k1 = -6; k1 <= 6; ++k1) {
				setBlockAndMetadata(world, -5, 4, k1, slabBlock, slabMeta | 8);
				setBlockAndMetadata(world, -4, 5, k1, stairBlock, 1);
				setBlockAndMetadata(world, -3, 6, k1, stairBlock, 1);
				setBlockAndMetadata(world, -2, 7, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, -2, 8, k1, stairBlock, 1);
				setBlockAndMetadata(world, -1, 9, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, -1, 10, k1, stairBlock, 1);
				setBlockAndMetadata(world, 0, 10, k1, woodBlock, woodMeta | 8);
				setBlockAndMetadata(world, 1, 10, k1, stairBlock, 0);
				setBlockAndMetadata(world, 1, 9, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, 2, 8, k1, stairBlock, 0);
				setBlockAndMetadata(world, 2, 7, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, 3, 6, k1, stairBlock, 0);
				setBlockAndMetadata(world, 4, 5, k1, stairBlock, 0);
				setBlockAndMetadata(world, 5, 4, k1, slabBlock, slabMeta | 8);
			}
			for (int k15 : new int[] { -6, 6 }) {
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
			for (k12 = -1; k12 <= 1; ++k12) {
				setBlockAndMetadata(world, -1, 10, k12, plankBlock, plankMeta);
				setBlockAndMetadata(world, 1, 10, k12, plankBlock, plankMeta);
				setBlockAndMetadata(world, -1, 11, k12, stairBlock, 1);
				setBlockAndMetadata(world, 1, 11, k12, stairBlock, 0);
			}
			setBlockAndMetadata(world, 0, 11, -1, stairBlock, 2);
			setBlockAndMetadata(world, 0, 11, 1, stairBlock, 3);
			setAir(world, 0, 10, 0);
			for (int l = 0; l <= 2; ++l) {
				j14 = 4 + l * 2;
				setBlockAndMetadata(world, -4 + l, j14, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, -4 + l, j14 + 1, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, -4 + l, j14 + 2, 0, stairBlock, 1);
				setBlockAndMetadata(world, 4 - l, j14, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, 4 - l, j14 + 1, 0, woodBlock, woodMeta);
				setBlockAndMetadata(world, 4 - l, j14 + 2, 0, stairBlock, 0);
			}
			for (k12 = -4; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, -2, 6, k12, stairBlock, 4);
				setBlockAndMetadata(world, 2, 6, k12, stairBlock, 5);
			}
			for (k12 = -3; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, -1, 8, k12, stairBlock, 4);
				setBlockAndMetadata(world, 1, 8, k12, stairBlock, 5);
			}
			for (int i14 : new int[] { -1, 1 }) {
				setBlockAndMetadata(world, i14, 8, -5, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, 8, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, 8, 4, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, 8, 5, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -1, 7, -4, stairBlock, 4);
			setBlockAndMetadata(world, 1, 7, -4, stairBlock, 5);
			setBlockAndMetadata(world, -1, 7, 4, stairBlock, 4);
			setBlockAndMetadata(world, 1, 7, 4, stairBlock, 5);
			for (j13 = 0; j13 >= -5; --j13) {
				for (int i16 = -1; i16 <= 1; ++i16) {
					for (int k16 = -1; k16 <= 1; ++k16) {
						if (i16 == 0 && k16 == 0) {
							setAir(world, i16, j13, k16);
							continue;
						}
						setBlockAndMetadata(world, i16, j13, k16, Blocks.cobblestone, 0);
					}
				}
			}
			setBlockAndMetadata(world, 0, -6, 0, GOTRegistry.hearth, 0);
			setBlockAndMetadata(world, 0, -5, 0, Blocks.fire, 0);
			setBlockAndMetadata(world, 0, 0, 0, GOTRegistry.bronzeBars, 0);
			setAir(world, 0, 1, 0);
			setBlockAndMetadata(world, 0, 1, 3, GOTRegistry.strawBed, 0);
			setBlockAndMetadata(world, 0, 1, 4, GOTRegistry.strawBed, 8);
			for (j13 = 1; j13 <= 2; ++j13) {
				setBlockAndMetadata(world, -1, j13, 4, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, 1, j13, 4, fenceBlock, fenceMeta);
			}
			setBlockAndMetadata(world, -2, 1, 3, GOTRegistry.tableWildling, 0);
			setBlockAndMetadata(world, -2, 1, 2, Blocks.crafting_table, 0);
			setBlockAndMetadata(world, 2, 1, 3, Blocks.furnace, 5);
			this.placeChest(world, random, 2, 1, 2, 5, GOTChestContents.BEYOND_WALL);
			spawnLegendaryNPC(new GOTEntityYgritte(world), world, 1, 1, 0);
			spawnLegendaryNPC(new GOTEntityTormund(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityManceRayder(world), world, -1, 1, -1);
			return true;
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureHardhome village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Home(false), 0, 0, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}
}
