package got.common.world.structure.other;

import java.util.Random;

import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTStructureSmallStoneRuin extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block barsBlock;

	public GOTStructureSmallStoneRuin(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		RuinType ruinType = RuinType.getRandomType(random);
		setOriginAndRotation(world, i, j, k, rotation, ruinType.centreShift);
		int radius = ruinType.checkRadius;
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -radius; i1 <= radius; i1++) {
				for (int k1 = -radius; k1 <= radius; k1++) {
					if (!isSuitableSpawnBlock(world, i1, k1)) {
						return false;
					}
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight > 7) {
						return false;
					}
				}
			}
		}
		if (ruinType == RuinType.COLUMN) {
			brickBlock = Blocks.stonebrick;
			brickMeta = 0;
			layFoundation(world, 0, 0, 0, brickBlock, brickMeta);
			layFoundation(world, -1, 0, 0, brickBlock, brickMeta);
			layFoundation(world, 1, 0, 0, brickBlock, brickMeta);
			layFoundation(world, 0, 0, -1, brickBlock, brickMeta);
			layFoundation(world, 0, 0, 1, brickBlock, brickMeta);
			int height = 3 + random.nextInt(3);
			for (int j1 = 1; j1 <= height; j1++) {
				if (random.nextInt(3) == 0) {
					setBlockAndMetadata(world, 0, j1, 0, Blocks.stonebrick, 3);
				} else {
					setBlockAndMetadata(world, 0, j1, 0, brickBlock, brickMeta);
				}
			}
			setBlockAndMetadata(world, -1, 1, 0, Blocks.stone_brick_stairs, 1);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.stone_brick_stairs, 0);
			setBlockAndMetadata(world, 0, 1, -1, Blocks.stone_brick_stairs, 2);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.stone_brick_stairs, 3);
		} else if (ruinType == RuinType.ROOM) {
			for (int i1 = -2; i1 <= 2; i1++) {
				for (int k1 = -2; k1 <= 2; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					layFoundationRandomStoneBrick(world, random, i1, 0, k1);
					if (i2 <= 1 && k2 <= 1) {
						setBlockAndMetadata(world, i1, 0, k1, Blocks.cobblestone, 0);
					}
					if ((i2 == 2 || k2 == 2) && (i1 != 0 || k1 != -2)) {
						int height = 1 + random.nextInt(3);
						for (int j1 = 1; j1 <= height; j1++) {
							placeRandomStoneBrick(world, random, i1, j1, k1);
						}
					}
				}
			}
			if (random.nextInt(4) == 0) {
				placeChest(world, random, 0, 1, 1, GOTRegistry.chestStone, 2, GOTChestContents.TREASURE);
			}
		} else if (ruinType == RuinType.BAR_TOWER) {
			int randomBar = random.nextInt(2);
			if (randomBar == 0) {
				barsBlock = Blocks.iron_bars;
			} else if (randomBar == 1) {
				barsBlock = GOTRegistry.bronzeBars;
			}
			for (int i1 = -2; i1 <= 2; i1++) {
				for (int k1 = -2; k1 <= 2; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if (i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1) {
						layFoundationRandomStoneBrick(world, random, i1, 0, k1);
						int height = 4 + random.nextInt(3);
						for (int m = 1; m <= height; m++) {
							placeRandomStoneBrick(world, random, i1, m, k1);
						}
					}
				}
			}
			for (int j1 = 1; j1 <= 2; j1++) {
				setAir(world, 0, j1, -2);
				setBlockAndMetadata(world, 0, j1, 2, barsBlock, 0);
				setBlockAndMetadata(world, -2, j1, 0, barsBlock, 0);
				setBlockAndMetadata(world, 2, j1, 0, barsBlock, 0);
			}
			setBlockAndMetadata(world, 0, 3, -2, Blocks.stone_slab, 8);
			for (int m : new int[] { -1, 1 }) {
				int k1 = 1;
				int n = getTopBlock(world, m, k1);
				if (random.nextInt(10) == 0) {
					placeChest(world, random, m, n, k1, GOTRegistry.chestStone, 2, GOTChestContents.TREASURE);
				}
			}
		} else if (ruinType == RuinType.PIT_MINE) {
			for (int i1 = -2; i1 <= 2; i1++) {
				for (int k1 = -2; k1 <= 2; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if (i2 == 2 || k2 == 2) {
						int j1 = getTopBlock(world, i1, k1);
						j1 -= random.nextInt(3);
						for (int j2 = 1; j2 >= j1; j2--) {
							placeRandomStoneBrick(world, random, i1, j2, k1);
							setGrassToDirt(world, i1, j2 - 1, k1);
						}
					}
					if (i2 == 2 && k2 == 2) {
						int height = random.nextInt(3);
						for (int j1 = 1; j1 <= 1 + height; j1++) {
							placeRandomStoneBrick(world, random, i1, j1, k1);
							setGrassToDirt(world, i1, j1 - 1, k1);
						}
					}
				}
			}
			int pitWidth = 4 + random.nextInt(5);
			int pitHeight = 2 + random.nextInt(3);
			int pitDepth = 60 - random.nextInt(5);
			pitDepth -= originY;
			int pitBottom = pitDepth - pitHeight - 1;
			int m;
			for (m = -1; m <= 1; m++) {
				for (int k1 = -1; k1 <= 1; k1++) {
					int i2 = Math.abs(m);
					int k2 = Math.abs(k1);
					int j1 = getTopBlock(world, m, k1);
					for (int j2 = j1; j2 >= pitDepth; j2--) {
						setAir(world, m, j2, k1);
						if (i2 == 1 && k2 == 1 && random.nextInt(10) == 0) {
							setBlockAndMetadata(world, m, j2, k1, Blocks.stone_slab, 0);
						}
					}
				}
			}
			for (m = -pitWidth; m <= pitWidth; m++) {
				for (int k1 = -pitWidth; k1 <= pitWidth; k1++) {
					int i2 = Math.abs(m);
					int k2 = Math.abs(k1);
					int randomFloor = random.nextInt(5);
					switch (randomFloor) {
					case 0:
						placeRandomStoneBrick(world, random, m, pitBottom, k1);
						break;
					case 1:
						setBlockAndMetadata(world, m, pitBottom, k1, Blocks.cobblestone, 0);
						break;
					case 2:
						setBlockAndMetadata(world, m, pitBottom, k1, Blocks.stone, 0);
						break;
					case 3:
						setBlockAndMetadata(world, m, pitBottom, k1, Blocks.gravel, 0);
						break;
					case 4:
						setBlockAndMetadata(world, m, pitBottom, k1, Blocks.dirt, 0);
						break;
					default:
						break;
					}
					int j1;
					for (j1 = pitBottom + 1; j1 <= pitBottom + pitHeight; j1++) {
						setAir(world, m, j1, k1);
					}
					if (i2 == 2 && k2 == 2) {
						for (j1 = pitBottom + 1; j1 <= pitBottom + pitHeight; j1++) {
							setBlockAndMetadata(world, m, j1, k1, GOTRegistry.woodBeamV1, 0);
						}
					} else if (i2 <= 2 && k2 <= 2 && (i2 == 2 || k2 == 2)) {
						if (random.nextInt(4) != 0) {
							setBlockAndMetadata(world, m, pitBottom + pitHeight, k1, Blocks.fence, 0);
						}
					} else if (random.nextInt(60) == 0) {
						placeSkull(world, random, m, pitBottom + 1, k1);
					} else if (random.nextInt(120) == 0) {
						int chestMeta = Direction.directionToFacing[random.nextInt(4)];
						placeChest(world, random, m, pitBottom + 1, k1, chestMeta, GOTChestContents.TREASURE);
					}
				}
			}
		} else if (ruinType == RuinType.PLINTH) {
			int i1;
			for (i1 = -3; i1 <= 2; i1++) {
				for (int k1 = -3; k1 <= 2; k1++) {
					layFoundation(world, i1, 0, k1, Blocks.cobblestone, 0);
				}
			}
			for (i1 = -2; i1 <= 1; i1++) {
				for (int k1 = -2; k1 <= 1; k1++) {
					placeRandomStoneBrick(world, random, i1, 1, k1);
				}
			}
			for (int m : new int[] { -3, 2 }) {
				setBlockAndMetadata(world, m, 1, -2, Blocks.stone_brick_stairs, 2);
				setBlockAndMetadata(world, m, 1, -1, Blocks.stone_slab, 8);
				setBlockAndMetadata(world, m, 1, 0, Blocks.stone_slab, 8);
				setBlockAndMetadata(world, m, 1, 1, Blocks.stone_brick_stairs, 3);
			}
			for (int k1 : new int[] { -3, 2 }) {
				setBlockAndMetadata(world, -2, 1, k1, Blocks.stone_brick_stairs, 1);
				setBlockAndMetadata(world, -1, 1, k1, Blocks.stone_slab, 8);
				setBlockAndMetadata(world, 0, 1, k1, Blocks.stone_slab, 8);
				setBlockAndMetadata(world, 1, 1, k1, Blocks.stone_brick_stairs, 0);
			}
			for (i1 = -1; i1 <= 0; i1++) {
				for (int k1 = -1; k1 <= 0; k1++) {
					if (random.nextInt(3) == 0) {
						int height = 2 + random.nextInt(4);
						for (int j1 = 2; j1 < 2 + height; j1++) {
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.pillar2, 2);
						}
						setBlockAndMetadata(world, i1, 2 + height, k1, Blocks.stone_brick_stairs, random.nextInt(4));
					}
				}
			}
		} else if (ruinType == RuinType.RUBBLE) {
			int width = 3 + random.nextInt(5);
			int centreWidth = 2;
			int i1;
			for (i1 = -width; i1 <= width; i1++) {
				for (int k1 = -width; k1 <= width; k1++) {
					int d = i1 * i1 + k1 * k1;
					if (d < width * width) {
						int j1 = getTopBlock(world, i1, k1) - 1;
						if (isSuitableSpawnBlock(world, i1, k1)) {
							if (random.nextInt(3) == 0) {
								if (random.nextBoolean()) {
									setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
								} else {
									placeRandomStoneBrick(world, random, i1, j1, k1);
								}
								setGrassToDirt(world, i1, j1 - 1, k1);
							}
							if (d > centreWidth * centreWidth && random.nextInt(6) == 0) {
								int height = 1 + random.nextInt(4);
								for (int j2 = j1 + 1; j2 <= j1 + height; j2++) {
									setBlockAndMetadata(world, i1, j2, k1, Blocks.stone, 0);
									setGrassToDirt(world, i1, j2 - 1, k1);
								}
							}
						}
					}
				}
			}
			if (random.nextInt(6) == 0) {
				for (i1 = -1; i1 <= 1; i1++) {
					for (int k1 = -1; k1 <= 1; k1++) {
						layFoundation(world, i1, 0, k1, Blocks.double_stone_slab, 0);
						setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_slab, 0);
					}
				}
				setBlockAndMetadata(world, 0, 1, 0, Blocks.stonebrick, 3);
				placeChest(world, random, 0, 0, 0, GOTRegistry.chestStone, 2, GOTChestContents.TREASURE);
			}
		} else if (ruinType == RuinType.QUARRY) {
			int r = 9;
			int i1;
			for (i1 = -r; i1 <= r; i1++) {
				for (int k1 = -r; k1 <= r; k1++) {
					for (int i2 = r; i2 >= 1; i2--) {
						int j2 = i2 - -5;
						int d = i1 * i1 + j2 * j2 + k1 * k1;
						if (d < r * r) {
							boolean grass = !isOpaque(world, i1, i2 + 1, k1);
							setBlockAndMetadata(world, i1, i2, k1, grass ? (Block) Blocks.grass : Blocks.dirt, 0);
							setGrassToDirt(world, i1, i2 - 1, k1);
							if (i2 == 1) {
								layFoundation(world, i1, 0, k1, Blocks.dirt, 0);
							}
						}
					}
				}
			}
			r = 5;
			for (i1 = -r; i1 <= r; i1++) {
				for (int k1 = -r; k1 <= r; k1++) {
					for (int i2 = -r; i2 <= r; i2++) {
						int j2 = i2 - 1;
						int d = i1 * i1 + j2 * j2 + k1 * k1;
						if (d < r * r) {
							if (random.nextInt(4) == 0) {
								setBlockAndMetadata(world, i1, i2, k1, Blocks.cobblestone, 0);
							} else {
								setBlockAndMetadata(world, i1, i2, k1, Blocks.stone, 0);
							}
							setGrassToDirt(world, i1, i2 - 1, k1);
						}
					}
				}
			}
			r = 5;
			int r1 = 3;
			for (int m = -r; m <= r; m++) {
				for (int k1 = -r; k1 <= r; k1++) {
					for (int i2 = -r; i2 <= r; i2++) {
						int i3 = m - 2;
						int j2 = i2 - 1;
						int k2 = k1 - 2;
						int d = i3 * i3 + j2 * j2 + k2 * k2;
						if (d < r1 * r1) {
							setAir(world, m, i2, k1);
							if (getBlock(world, m, i2 - 1, k1) == Blocks.dirt) {
								setBlockAndMetadata(world, m, i2 - 1, k1, Blocks.grass, 0);
							}
						}
					}
				}
			}
			boolean rotten = random.nextBoolean();
			for (int j1 = -1; j1 <= 3; j1++) {
				if (rotten) {
					setBlockAndMetadata(world, 1, j1, 1, GOTRegistry.woodBeamRotten, 0);
				} else {
					setBlockAndMetadata(world, 1, j1, 1, GOTRegistry.woodBeamV1, 0);
				}
			}
			if (rotten) {
				setBlockAndMetadata(world, 4, 1, 3, GOTRegistry.stairsRotten, 1);
				setBlockAndMetadata(world, 4, 0, 3, GOTRegistry.stairsRotten, 4);
				setBlockAndMetadata(world, 3, 0, 3, GOTRegistry.stairsRotten, 1);
				setBlockAndMetadata(world, 3, -1, 3, GOTRegistry.stairsRotten, 4);
				setBlockAndMetadata(world, 2, -1, 3, GOTRegistry.planksRotten, 0);
				setBlockAndMetadata(world, 2, -1, 2, GOTRegistry.stairsRotten, 2);
				setBlockAndMetadata(world, 5, 2, 2, GOTRegistry.stairsRotten, 3);
			} else {
				setBlockAndMetadata(world, 4, 1, 3, Blocks.oak_stairs, 1);
				setBlockAndMetadata(world, 4, 0, 3, Blocks.oak_stairs, 4);
				setBlockAndMetadata(world, 3, 0, 3, Blocks.oak_stairs, 1);
				setBlockAndMetadata(world, 3, -1, 3, Blocks.oak_stairs, 4);
				setBlockAndMetadata(world, 2, -1, 3, Blocks.planks, 0);
				setBlockAndMetadata(world, 2, -1, 2, Blocks.oak_stairs, 2);
				setBlockAndMetadata(world, 5, 2, 2, Blocks.oak_stairs, 3);
			}
			setGrassToDirt(world, 5, 1, 2);
			for (int n = -2; n <= 2; n++) {
				for (int k1 = -2; k1 <= 2; k1++) {
					int i2 = Math.abs(n);
					int k2 = Math.abs(k1);
					if ((i2 == 2 || k2 == 2) && random.nextBoolean()) {
						if (rotten) {
							setBlockAndMetadata(world, n, 6, k1, GOTRegistry.fenceRotten, 0);
						} else {
							setBlockAndMetadata(world, n, 6, k1, Blocks.fence, 0);
						}
					}
				}
			}
		} else if (ruinType == RuinType.OBELISK) {
			int width = radius;
			int i1;
			for (i1 = -width; i1 <= width; i1++) {
				for (int k1 = -width; k1 <= width; k1++) {
					int m = getTopBlock(world, i1, k1) - 1;
					if (isSuitableSpawnBlock(world, i1, k1)) {
						if (random.nextInt(3) == 0) {
							if (random.nextBoolean()) {
								setBlockAndMetadata(world, i1, m, k1, Blocks.cobblestone, 0);
							} else {
								setBlockAndMetadata(world, i1, m, k1, Blocks.gravel, 0);
							}
							setGrassToDirt(world, i1, m - 1, k1);
						}
						if ((i1 >= 4 || k1 >= 4) && random.nextInt(6) == 0) {
							setGrassToDirt(world, i1, m, k1);
							placeRandomStoneBrick(world, random, i1, m + 1, k1);
							if (random.nextInt(3) == 0) {
								int rb = random.nextInt(3);
								switch (rb) {
								case 0:
									placeRandomStoneBrick(world, random, i1, m + 2, k1);
									break;
								case 1:
									setBlockAndMetadata(world, i1, m + 2, k1, Blocks.stone_brick_stairs, random.nextInt(4));
									break;
								case 2:
									setBlockAndMetadata(world, i1, m + 2, k1, GOTRegistry.wallStoneV, 1);
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}
			for (i1 = -1; i1 <= 1; i1++) {
				for (int k1 = -1; k1 <= 1; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					layFoundationRandomStoneBrick(world, random, i1, 0, k1);
					placeRandomStoneBrick(world, random, i1, 1, k1);
					if (i2 == 0 || k2 == 0) {
						setBlockAndMetadata(world, i1, 1, k1, Blocks.stonebrick, 3);
						placeRandomStoneBrick(world, random, i1, 2, k1);
					}
				}
			}
			setBlockAndMetadata(world, -1, 3, 0, Blocks.stone_brick_stairs, 1);
			setBlockAndMetadata(world, 1, 3, 0, Blocks.stone_brick_stairs, 0);
			setBlockAndMetadata(world, 0, 3, -1, Blocks.stone_brick_stairs, 2);
			setBlockAndMetadata(world, 0, 3, 1, Blocks.stone_brick_stairs, 3);
			int top = 4 + random.nextInt(4);
			int j1;
			for (j1 = 3; j1 <= top; j1++) {
				placeRandomStoneBrick(world, random, 0, j1, 0);
			}
			for (j1 = top + 1; j1 <= top + 2; j1++) {
				setBlockAndMetadata(world, 0, j1, 0, GOTRegistry.wallStoneV, 1);
			}
		} else if (ruinType == RuinType.WELL) {
			int wellDepth = 4 + random.nextInt(5);
			int wellBottom = -wellDepth - 1;
			boolean water = random.nextBoolean();
			int waterDepth = 2 + random.nextInt(5);
			if (waterDepth > wellDepth) {
				waterDepth = wellDepth;
			}
			for (int i1 = -2; i1 <= 1; i1++) {
				for (int k1 = -2; k1 <= 1; k1++) {
					if (i1 == -2 || i1 == 1 || k1 == -2 || k1 == 1) {
						placeRandomStoneBrick(world, random, i1, 1, k1);
						setBlockAndMetadata(world, i1, 0, k1, Blocks.double_stone_slab, 0);
						for (int j1 = -1; j1 >= wellBottom; j1--) {
							placeRandomStoneBrick(world, random, i1, j1, k1);
						}
						setGrassToDirt(world, i1, wellBottom - 1, k1);
					} else {
						for (int j1 = 1; j1 >= wellBottom; j1--) {
							if (water && j1 <= wellBottom + waterDepth) {
								setBlockAndMetadata(world, i1, j1, k1, Blocks.water, 0);
							} else {
								setAir(world, i1, j1, k1);
							}
						}
						setGrassToDirt(world, i1, wellBottom - 1, k1);
						setBlockAndMetadata(world, i1, wellBottom, k1, Blocks.stone, 0);
						setBlockAndMetadata(world, i1, wellBottom + 1, k1, Blocks.gravel, 0);
						if (random.nextBoolean()) {
							setBlockAndMetadata(world, i1, wellBottom + 2, k1, Blocks.gravel, 0);
						}
						if (random.nextInt(8) == 0) {
							int chestMeta = Direction.directionToFacing[random.nextInt(4)];
							placeChest(world, random, i1, wellBottom + 1, k1, GOTRegistry.chestStone, chestMeta, GOTChestContents.TREASURE);
						}
						if (random.nextInt(3) == 0) {
							setBlockAndMetadata(world, i1, 0, k1, GOTRegistry.fenceRotten, 0);
						}
					}
				}
			}
			setBlockAndMetadata(world, -2, 1, -2, Blocks.stone_slab, 0);
			setBlockAndMetadata(world, 1, 1, -2, Blocks.stone_slab, 0);
			setBlockAndMetadata(world, -2, 1, 1, Blocks.stone_slab, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.stone_slab, 0);
		} else if (ruinType == RuinType.TURRET) {
			int randomWood = random.nextInt(3);
			switch (randomWood) {
			case 0:
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				woodBeamBlock = GOTRegistry.woodBeamV1;
				woodBeamMeta = 0;
				break;
			case 1:
				plankBlock = Blocks.planks;
				plankMeta = 1;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 1;
				woodBeamBlock = GOTRegistry.woodBeamV1;
				woodBeamMeta = 1;
				break;
			case 2:
				plankBlock = GOTRegistry.planksRotten;
				plankMeta = 0;
				plankSlabBlock = GOTRegistry.rottenSlabSingle;
				plankSlabMeta = 0;
				woodBeamBlock = GOTRegistry.woodBeamRotten;
				woodBeamMeta = 0;
				break;
			default:
				break;
			}
			int randomBar = random.nextInt(2);
			if (randomBar == 0) {
				barsBlock = Blocks.iron_bars;
			} else if (randomBar == 1) {
				barsBlock = GOTRegistry.bronzeBars;
			}
			int i1;
			for (i1 = -4; i1 <= 4; i1++) {
				for (int m = -4; m <= 4; m++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(m);
					layFoundationRandomStoneBrick(world, random, i1, 0, m);
					placeRandomStoneBrick(world, random, i1, 1, m);
					if (i2 <= 3 && k2 <= 3) {
						if (i2 == 3 && k2 == 3) {
							placeRandomStoneBrick(world, random, i1, 2, m);
							for (int j1 = 3; j1 <= 5; j1++) {
								setBlockAndMetadata(world, i1, j1, m, woodBeamBlock, woodBeamMeta);
							}
							placeRandomStoneBrick(world, random, i1, 6, m);
						} else if (i2 == 3 || k2 == 3) {
							for (int j1 = 2; j1 <= 6; j1++) {
								if (random.nextInt(8) == 0) {
									setAir(world, i1, j1, m);
								} else {
									placeRandomStoneBrick(world, random, i1, j1, m);
								}
							}
							if (i2 <= 1 || k2 <= 1) {
								if (random.nextInt(4) == 0) {
									setAir(world, i1, 4, m);
								} else {
									setBlockAndMetadata(world, i1, 4, m, barsBlock, 0);
								}
							}
						} else {
							if (random.nextInt(4) == 0) {
								if (random.nextBoolean()) {
									setBlockAndMetadata(world, i1, 1, m, Blocks.gravel, 0);
								} else {
									setBlockAndMetadata(world, i1, 1, m, Blocks.cobblestone, 0);
								}
							} else if (random.nextInt(4) == 0) {
								setAir(world, i1, 1, m);
							} else {
								setBlockAndMetadata(world, i1, 1, m, plankBlock, plankMeta);
							}
							for (int j1 = 2; j1 <= 5; j1++) {
								setAir(world, i1, j1, m);
							}
							if (random.nextInt(5) == 0) {
								setAir(world, i1, 6, m);
							} else {
								setBlockAndMetadata(world, i1, 6, m, plankSlabBlock, plankSlabMeta);
							}
						}
					}
					if ((i2 == 4 || k2 == 4) && random.nextInt(3) != 0) {
						if (random.nextInt(3) == 0) {
							if (random.nextBoolean()) {
								setBlockAndMetadata(world, i1, 7, m, Blocks.stone_brick_stairs, random.nextInt(4));
							} else {
								setBlockAndMetadata(world, i1, 7, m, Blocks.stone_slab, 0);
							}
						} else {
							placeRandomStoneBrick(world, random, i1, 7, m);
						}
					}
				}
			}
			for (i1 = -4; i1 <= 4; i1++) {
				setBlockAndMetadata(world, i1, 2, -4, Blocks.stone_brick_stairs, 2);
				setBlockAndMetadata(world, i1, 2, 4, Blocks.stone_brick_stairs, 3);
				setBlockAndMetadata(world, i1, 6, -4, Blocks.stone_brick_stairs, 6);
				setBlockAndMetadata(world, i1, 6, 4, Blocks.stone_brick_stairs, 7);
			}
			for (int k1 = -3; k1 <= 3; k1++) {
				setBlockAndMetadata(world, -4, 2, k1, Blocks.stone_brick_stairs, 1);
				setBlockAndMetadata(world, 4, 2, k1, Blocks.stone_brick_stairs, 0);
				setBlockAndMetadata(world, -4, 6, k1, Blocks.stone_brick_stairs, 5);
				setBlockAndMetadata(world, 4, 6, k1, Blocks.stone_brick_stairs, 4);
			}
			if (random.nextInt(3) == 0) {
				setBlockAndMetadata(world, 0, 1, 2, plankBlock, plankMeta);
				placeChest(world, random, 0, 2, 2, 2, GOTChestContents.TREASURE);
			}
			if (random.nextInt(3) == 0) {
				placeRandomStoneBrick(world, random, 0, 6, 3);
				placeChest(world, random, 0, 7, 3, 2, GOTChestContents.TREASURE);
			}
		} else if (ruinType == RuinType.WALLS) {
			int length = 3 + random.nextInt(7);
			int width = 2 + random.nextInt(3);
			int height = 2 + random.nextInt(6);
			int gravelChance = 2 + random.nextInt(7);
			for (int i1 = -width; i1 <= width; i1++) {
				for (int k1 = -length; k1 <= length; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if (isSuitableSpawnBlock(world, i1, k1)) {
						int j1 = getTopBlock(world, i1, k1) - 1;
						if (i2 == width) {
							int h = height - random.nextInt(3);
							if (random.nextInt(8) == 0) {
								h = random.nextInt(3);
							}
							float factor = k2 / length;
							factor = 1.0F / (factor + 0.01F);
							factor *= 0.5F + random.nextFloat() * 0.5F;
							factor = Math.min(factor, 1.0F);
							h = (int) (h * factor);
							int top = j1 + h;
							for (int j2 = j1 + 1; j2 <= top; j2++) {
								if (random.nextInt(8) == 0) {
									setBlockAndMetadata(world, i1, j2, k1, Blocks.stone_brick_stairs, random.nextInt(8));
								} else if (random.nextInt(8) == 0) {
									setBlockAndMetadata(world, i1, j2, k1, Blocks.cobblestone, 0);
								} else {
									placeRandomStoneBrick(world, random, i1, j2, k1);
								}
								setGrassToDirt(world, i1, j2 - 1, k1);
							}
							if (k2 < length / 2 && h >= 3 && h >= height - 3 && random.nextBoolean()) {
								int h1 = top - random.nextInt(2);
								int w = random.nextInt(width * 2);
								for (int w1 = 1; w1 <= w; w1++) {
									if (i1 < 0) {
										setBlockAndMetadata(world, i1 + w1, h1, k1, Blocks.stone_slab, 8);
									} else {
										setBlockAndMetadata(world, i1 - w1, h1, k1, Blocks.stone_slab, 8);
									}
									if (random.nextInt(4) == 0) {
										break;
									}
								}
							}
						} else {
							if (random.nextInt(5) != 0) {
								if (random.nextInt(4) == 0) {
									setBlockAndMetadata(world, i1, j1, k1, Blocks.mossy_cobblestone, 0);
								} else {
									setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
								}
							}
							if (i2 == width - 1 && random.nextInt(Math.max(2, gravelChance / 2)) == 0 || random.nextInt(gravelChance) == 0) {
								int h = 1;
								if (random.nextInt(4) == 0) {
									h++;
								}
								setGrassToDirt(world, i1, j1, k1);
								for (int j2 = j1 + 1; j2 <= j1 + h; j2++) {
									setBlockAndMetadata(world, i1, j2, k1, Blocks.gravel, 0);
								}
							}
						}
					}
				}
			}
		} else if (ruinType == RuinType.SHRINE) {
			for (int i1 = -4; i1 <= 4; i1++) {
				for (int k1 = -4; k1 <= 4; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					layFoundationRandomStoneBrick(world, random, i1, 0, k1);
					if (i2 <= 3 && k2 <= 3) {
						if (i2 <= 1 && k2 <= 1) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.double_stone_slab, 0);
							setBlockAndMetadata(world, i1, 2, k1, Blocks.stone_slab, 0);
						} else if (random.nextInt(16) == 0) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.gravel, 0);
						} else if (random.nextInt(16) == 0) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.cobblestone, 0);
						} else if (random.nextInt(16) == 0) {
							setBiomeTop(world, i1, 1, k1);
						} else if (i2 <= 2 && k2 <= 2) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.double_stone_slab, 0);
						} else {
							placeRandomStoneBrick(world, random, i1, 1, k1);
						}
					}
					if (random.nextInt(5) != 0) {
						if (i1 == -4) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_brick_stairs, 1);
						} else if (i1 == 4) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_brick_stairs, 0);
						} else if (k1 == -4) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_brick_stairs, 2);
						} else if (k1 == 4) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_brick_stairs, 3);
						}
					}
					if (i2 == 3 && k2 == 3) {
						int h = 2 + random.nextInt(4);
						int top = 1 + h;
						for (int j1 = 2; j1 <= top; j1++) {
							placeRandomStoneBrick(world, random, i1, j1, k1);
							setGrassToDirt(world, i1, j1 - 1, k1);
						}
						if (h >= 4) {
							setBlockAndMetadata(world, i1 - 1, 1 + h, k1 - 1, Blocks.stone_brick_stairs, 6);
							setBlockAndMetadata(world, i1, 1 + h, k1 - 1, Blocks.stone_brick_stairs, 6);
							setBlockAndMetadata(world, i1 + 1, 1 + h, k1 - 1, Blocks.stone_brick_stairs, 6);
							setBlockAndMetadata(world, i1 + 1, 1 + h, k1, Blocks.stone_brick_stairs, 4);
							setBlockAndMetadata(world, i1 + 1, 1 + h, k1 + 1, Blocks.stone_brick_stairs, 7);
							setBlockAndMetadata(world, i1, 1 + h, k1 + 1, Blocks.stone_brick_stairs, 7);
							setBlockAndMetadata(world, i1 - 1, 1 + h, k1 + 1, Blocks.stone_brick_stairs, 7);
							setBlockAndMetadata(world, i1 - 1, 1 + h, k1, Blocks.stone_brick_stairs, 5);
						}
					}
					if (i2 == 3 && k2 <= 1 || k2 == 3 && i2 <= 1) {
						if (random.nextInt(4) != 0) {
							setBlockAndMetadata(world, i1, 2, k1, Blocks.stone_slab, 0);
							setGrassToDirt(world, i1, 1, k1);
						}
					}
				}
			}
			setBlockAndMetadata(world, 0, 2, 0, Blocks.stonebrick, 3);
			placeChest(world, random, 0, 1, 0, GOTRegistry.chestStone, 2, GOTChestContents.TREASURE);
		} else if (ruinType == RuinType.BRICK_HOUSE) {
			int width = MathHelper.getRandomIntegerInRange(random, 3, 5);
			int height = MathHelper.getRandomIntegerInRange(random, 1, 4);
			for (int i1 = -width; i1 <= width; i1++) {
				for (int k1 = -width; k1 <= width; k1++) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					layFoundation(world, i1, 0, k1, Blocks.cobblestone, 0);
					int randomFloor = random.nextInt(5);
					switch (randomFloor) {
					case 0:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.cobblestone, 0);
						break;
					case 1:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.mossy_cobblestone, 0);
						break;
					case 2:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.gravel, 0);
						break;
					case 3:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
						break;
					case 4:
						setBlockAndMetadata(world, i1, 0, k1, Blocks.brick_block, 0);
						break;
					default:
						break;
					}
					if (i2 == width || k2 == width) {
						if (random.nextInt(10) != 0) {
							for (int j1 = 1; j1 <= height; j1++) {
								if (random.nextInt(6) != 0) {
									if (random.nextInt(3) == 0) {
										if (random.nextBoolean()) {
											setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.redBrick, 0);
										} else {
											setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.redBrick, 1);
										}
									} else {
										setBlockAndMetadata(world, i1, j1, k1, Blocks.brick_block, 0);
									}
								} else {
									int randomWall = random.nextInt(7);
									switch (randomWall) {
									case 0:
										setBlockAndMetadata(world, i1, j1, k1, Blocks.double_stone_slab, 0);
										break;
									case 1:
										setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.pillar2, 3);
										break;
									case 2:
										setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.clayTile, 0);
										break;
									case 3:
										int stairDir = random.nextInt(8);
										setBlockAndMetadata(world, i1, j1, k1, Blocks.brick_stairs, stairDir);
										break;
									default:
										switch (randomWall) {
										case 4:
											int stairDir1 = random.nextInt(8);
											setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.stairsClayTile, stairDir1);
											break;
										case 5:
											setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
											break;
										case 6:
											setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.wallStoneV, 6);
											break;
										default:
											break;
										}
										break;
									}
								}
								if (random.nextInt(6) == 0) {
									break;
								}
							}
						}
					} else if (i2 == width - 1 || k2 == width - 1) {
						if (random.nextInt(3) == 0) {
							int randomWall = random.nextInt(4);
							switch (randomWall) {
							case 0:
								setBlockAndMetadata(world, i1, 1, k1, Blocks.brick_block, 0);
								break;
							case 1:
								int stairDir = random.nextInt(8);
								setBlockAndMetadata(world, i1, 1, k1, Blocks.brick_stairs, stairDir);
								break;
							case 2:
								setBlockAndMetadata(world, i1, 1, k1, GOTRegistry.planksRotten, 0);
								break;
							case 3:
								setBlockAndMetadata(world, i1, 1, k1, GOTRegistry.fenceRotten, 0);
								break;
							default:
								break;
							}
						}
					} else if (random.nextInt(8) == 0) {
						int randomWall = random.nextInt(2);
						if (randomWall == 0) {
							setBlockAndMetadata(world, i1, 1, k1, GOTRegistry.rottenSlabSingle, 0);
						} else if (randomWall == 1) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.stone_slab, 4);
						}
					}
				}
			}
		}
		return true;
	}

	public boolean isSuitableSpawnBlock(World world, int i, int k) {
		int j = getTopBlock(world, i, k);
		if (!isSurface(world, i, j - 1, k)) {
			return false;
		}
		Block above = getBlock(world, i, j, k);
		if (above.getMaterial().isLiquid()) {
			return false;
		}
		return true;
	}

	public void layFoundation(World world, int i, int j, int k, Block block, int meta) {
		for (int j1 = j; (j1 >= 0 || !isOpaque(world, i, j1, k)) && getY(j1) >= 0; j1--) {
			setBlockAndMetadata(world, i, j1, k, block, meta);
			setGrassToDirt(world, i, j1 - 1, k);
		}
	}

	public void layFoundationRandomStoneBrick(World world, Random random, int i, int j, int k) {
		for (int j1 = j; (j1 >= 0 || !isOpaque(world, i, j1, k)) && getY(j1) >= 0; j1--) {
			placeRandomStoneBrick(world, random, i, j1, k);
			setGrassToDirt(world, i, j1 - 1, k);
		}
	}

	public void placeRandomStoneBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
			} else {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
		}
	}

	public enum RuinType {
		COLUMN(0, 1), ROOM(3, 2), BAR_TOWER(3, 2), PIT_MINE(0, 2), PLINTH(0, 3), RUBBLE(0, 0), QUARRY(0, 7), OBELISK(0, 5), WELL(0, 2), TURRET(5, 4), WALLS(0, 3), SHRINE(0, 4), BRICK_HOUSE(0, 5);

		public int centreShift;
		public int checkRadius;

		RuinType(int i, int j) {
			centreShift = i;
			checkRadius = j;
		}

		public static RuinType getRandomType(Random random) {
			return values()[random.nextInt(values().length)];
		}
	}
}
