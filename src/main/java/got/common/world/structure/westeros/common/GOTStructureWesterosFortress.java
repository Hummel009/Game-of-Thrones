package got.common.world.structure.westeros.common;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityMaester;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRedPriest;
import got.common.entity.other.GOTEntitySepton;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.westeros.ironborn.GOTEntityIronbornPriest;
import got.common.entity.westeros.legendary.captain.GOTEntityBarristanSelmy;
import got.common.entity.westeros.legendary.captain.GOTEntityJanosSlynt;
import got.common.entity.westeros.legendary.deco.GOTEntityMyrcellaBaratheon;
import got.common.entity.westeros.legendary.deco.GOTEntityTommenBaratheon;
import got.common.entity.westeros.legendary.quest.GOTEntityCerseiLannister;
import got.common.entity.westeros.legendary.quest.GOTEntityVarys;
import got.common.entity.westeros.legendary.trader.GOTEntityHighSepton;
import got.common.entity.westeros.legendary.trader.GOTEntityPycelle;
import got.common.entity.westeros.legendary.warrior.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureWesterosFortress extends GOTStructureWesterosBase {
	public GOTStructureWesterosFortress(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 12);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i11 = -14; i11 <= 14; i11++) {
				for (int i12 = -14; i12 <= 14; i12++) {
					int i13 = getTopBlock(world, i11, i12) - 1;
					if (!isSurface(world, i11, i13, i12)) {
						return false;
					}
					if (i13 < minHeight) {
						minHeight = i13;
					}
					if (i13 > maxHeight) {
						maxHeight = i13;
					}
					if (maxHeight - minHeight > 8) {
						return false;
					}
				}
			}
		}
		for (int i10 = -11; i10 <= 11; i10++) {
			for (int i11 = -11; i11 <= 11; i11++) {
				int i12 = Math.abs(i10);
				int k2 = Math.abs(i11);
				if (i12 >= 9 && k2 <= 5 || k2 >= 9 && i12 <= 5) {
					boolean pillar = false;
					if (i12 == 11) {
						pillar = k2 == 2 || k2 == 5;
					} else if (k2 == 11) {
						pillar = i12 == 2 || i12 == 5;
					}
					int i13;
					for (i13 = 5; (i13 >= 0 || !isOpaque(world, i10, i13, i11)) && getY(i13) >= 0; i13--) {
						if (pillar && i13 >= 1) {
							setBlockAndMetadata(world, i10, i13, i11, pillarBlock, pillarMeta);
						} else {
							setBlockAndMetadata(world, i10, i13, i11, brickBlock, brickMeta);
						}
						setGrassToDirt(world, i10, i13 - 1, i11);
					}
					setBlockAndMetadata(world, i10, 6, i11, brickBlock, brickMeta);
					for (i13 = 7; i13 <= 9; i13++) {
						setAir(world, i10, i13, i11);
					}
					if (i12 == 9 || i12 == 11 || k2 == 9 || k2 == 11) {
						setBlockAndMetadata(world, i10, 7, i11, brick2WallBlock, brick2WallMeta);
						if (i12 == 5 || k2 == 5) {
							setBlockAndMetadata(world, i10, 8, i11, Blocks.torch, 5);
						}
					}
				} else {
					int i13;
					for (i13 = 0; (i13 == 0 || !isOpaque(world, i10, i13, i11)) && getY(i13) >= 0; i13--) {
						setBlockAndMetadata(world, i10, i13, i11, brickBlock, brickMeta);
						setGrassToDirt(world, i10, i13 - 1, i11);
					}
					for (i13 = 1; i13 <= 9; i13++) {
						setAir(world, i10, i13, i11);
					}
				}
			}
		}
		for (int i11 : new int[]{-10, 10}) {
			for (int i12 : new int[]{-10, 10}) {
				for (int i14 = i11 - 4; i14 <= i11 + 4; i14++) {
					for (int k2 = i12 - 4; k2 <= i12 + 4; k2++) {
						int i15 = Math.abs(i14 - i11);
						int k3 = Math.abs(k2 - i12);
						int i16 = Math.abs(i14);
						int k4 = Math.abs(k2);
						if ((i15 != 4 || k3 < 3) && (k3 != 4 || i15 < 3)) {
							if (i15 == 4 || k3 == 4 || i15 == 3 && k3 == 3) {
								boolean pillar = false;
								if (i15 == 4) {
									pillar = k3 == 2;
								} else if (k3 == 4) {
									pillar = i15 == 2;
								}
								int i17;
								for (i17 = 5; (i17 >= 0 || !isOpaque(world, i14, i17, k2)) && getY(i17) >= 0; i17--) {
									if (pillar && i17 >= 1) {
										setBlockAndMetadata(world, i14, i17, k2, pillarBlock, pillarMeta);
									} else {
										setBlockAndMetadata(world, i14, i17, k2, brickBlock, brickMeta);
									}
									setGrassToDirt(world, i14, i17 - 1, k2);
								}
								setBlockAndMetadata(world, i14, 6, k2, brickBlock, brickMeta);
								for (i17 = 7; i17 <= 9; i17++) {
									setAir(world, i14, i17, k2);
								}
								if (i15 <= 1 || k3 <= 1) {
									setBlockAndMetadata(world, i14, 7, k2, brick2WallBlock, brick2WallMeta);
									if (i16 == 10 || k4 == 10) {
										if (i16 <= 10 && k4 <= 10) {
											setAir(world, i14, 7, k2);
										} else {
											setBlockAndMetadata(world, i14, 8, k2, Blocks.torch, 5);
										}
									}
								} else {
									setBlockAndMetadata(world, i14, 7, k2, brick2Block, brick2Meta);
									setBlockAndMetadata(world, i14, 8, k2, brick2SlabBlock, brick2SlabMeta);
								}
							} else {
								int i17;
								for (i17 = 0; (i17 == 0 || !isOpaque(world, i14, i17, k2)) && getY(i17) >= 0; i17--) {
									setBlockAndMetadata(world, i14, i17, k2, brickBlock, brickMeta);
									setGrassToDirt(world, i14, i17 - 1, k2);
								}
								for (i17 = 1; i17 <= 9; i17++) {
									setAir(world, i14, i17, k2);
								}
								setBlockAndMetadata(world, i14, 6, k2, plankBlock, plankMeta);
								if (i16 == 9 && k4 == 9 || i16 == 11 && k4 == 11) {
									setBlockAndMetadata(world, i14, 5, k2, GOTBlocks.chandelier, 2);
								}
							}
						}
					}
				}
				int i13;
				for (i13 = 1; i13 <= 8; i13++) {
					setBlockAndMetadata(world, i11, i13, i12, woodBeamBlock, woodBeamMeta);
				}
				setBlockAndMetadata(world, i11, 9, i12, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i11, 8, i12 - 1, Blocks.torch, 4);
				setBlockAndMetadata(world, i11, 8, i12 + 1, Blocks.torch, 3);
				setBlockAndMetadata(world, i11 - 1, 8, i12, Blocks.torch, 1);
				setBlockAndMetadata(world, i11 + 1, 8, i12, Blocks.torch, 2);
				if (i11 < 0) {
					for (i13 = 1; i13 <= 5; i13++) {
						setBlockAndMetadata(world, i11 + 1, i13, i12, Blocks.ladder, 4);
					}
					setBlockAndMetadata(world, i11 + 1, 6, i12, trapdoorBlock, 11);
				}
				if (i11 > 0) {
					for (i13 = 1; i13 <= 5; i13++) {
						setBlockAndMetadata(world, i11 - 1, i13, i12, Blocks.ladder, 5);
					}
					setBlockAndMetadata(world, i11 - 1, 6, i12, trapdoorBlock, 10);
				}
				if (i12 < 0) {
					for (i13 = 1; i13 <= 5; i13++) {
						setBlockAndMetadata(world, i11, i13, i12 + 1, Blocks.ladder, 3);
					}
					setBlockAndMetadata(world, i11, 6, i12 + 1, trapdoorBlock, 8);
				}
				if (i12 > 0) {
					for (i13 = 1; i13 <= 5; i13++) {
						setBlockAndMetadata(world, i11, i13, i12 - 1, Blocks.ladder, 2);
					}
					setBlockAndMetadata(world, i11, 6, i12 - 1, trapdoorBlock, 9);
				}
			}
		}
		for (int i11 : new int[]{-11, 11}) {
			int i12 = i11 + Integer.signum(i11) * -1;
			for (int i13 : new int[]{-4, 3}) {
				setBlockAndMetadata(world, i11, 2, i13, brickStairBlock, 3);
				setBlockAndMetadata(world, i11, 2, i13 + 1, brickStairBlock, 2);
				setBlockAndMetadata(world, i11, 4, i13, brickStairBlock, 7);
				setBlockAndMetadata(world, i11, 4, i13 + 1, brickStairBlock, 6);
				for (int i14 = i13; i14 <= i13 + 1; i14++) {
					setAir(world, i11, 3, i14);
					setBlockAndMetadata(world, i12, 3, i14, brickCarved, brickCarvedMeta);
				}
			}
			setBlockAndMetadata(world, i11, 2, -1, brickStairBlock, 3);
			setBlockAndMetadata(world, i11, 2, 0, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i11, 2, 1, brickStairBlock, 2);
			setBlockAndMetadata(world, i11, 4, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i11, 4, 0, brickSlabBlock, brickSlabMeta | 0x8);
			setBlockAndMetadata(world, i11, 4, 1, brickStairBlock, 6);
			for (int k2 = -1; k2 <= 1; k2++) {
				setAir(world, i11, 3, k2);
				setBlockAndMetadata(world, i12, 3, k2, brickCarved, brickCarvedMeta);
			}
		}
		for (int i11 : new int[]{-11, 11}) {
			int k2 = i11 + Integer.signum(i11) * -1;
			for (int i12 : new int[]{-4, 3}) {
				setBlockAndMetadata(world, i12, 2, i11, brickStairBlock, 0);
				setBlockAndMetadata(world, i12 + 1, 2, i11, brickStairBlock, 1);
				setBlockAndMetadata(world, i12, 4, i11, brickStairBlock, 4);
				setBlockAndMetadata(world, i12 + 1, 4, i11, brickStairBlock, 5);
				for (int i13 = i12; i13 <= i12 + 1; i13++) {
					setAir(world, i13, 3, i11);
					setBlockAndMetadata(world, i13, 3, k2, brickCarved, brickCarvedMeta);
				}
			}
			if (i11 > 0) {
				setBlockAndMetadata(world, -1, 2, i11, brickStairBlock, 0);
				setBlockAndMetadata(world, 0, 2, i11, brickSlabBlock, brickSlabMeta);
				setBlockAndMetadata(world, 1, 2, i11, brickStairBlock, 1);
				setBlockAndMetadata(world, -1, 4, i11, brickStairBlock, 4);
				setBlockAndMetadata(world, 0, 4, i11, brickSlabBlock, brickSlabMeta | 0x8);
				setBlockAndMetadata(world, 1, 4, i11, brickStairBlock, 5);
				for (int i12 = -1; i12 <= 1; i12++) {
					setAir(world, i12, 3, i11);
					setBlockAndMetadata(world, i12, 3, k2, brickCarved, brickCarvedMeta);
				}
			}
		}
		for (int i11 : new int[]{-14, 14}) {
			int k2 = i11 + Integer.signum(i11) * -1;
			for (int i12 : new int[]{-10, 10}) {
				setBlockAndMetadata(world, i12 - 1, 3, i11, brickStairBlock, 0);
				setBlockAndMetadata(world, i12, 3, i11, brick2WallBlock, brick2WallMeta);
				setBlockAndMetadata(world, i12 + 1, 3, i11, brickStairBlock, 1);
				setBlockAndMetadata(world, i12 - 1, 4, i11, brickStairBlock, 4);
				setBlockAndMetadata(world, i12, 4, i11, brick2WallBlock, brick2WallMeta);
				setBlockAndMetadata(world, i12 + 1, 4, i11, brickStairBlock, 5);
				setBlockAndMetadata(world, i12 - 1, 1, k2, brickStairBlock, 1);
				setBlockAndMetadata(world, i12, 1, k2, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12 + 1, 1, k2, brickStairBlock, 0);
				setBlockAndMetadata(world, i12, 2, k2, brickSlabBlock, brickSlabMeta);
			}
		}
		for (int i11 : new int[]{-14, 14}) {
			int i12 = i11 + Integer.signum(i11) * -1;
			for (int i13 : new int[]{-10, 10}) {
				setBlockAndMetadata(world, i11, 3, i13 - 1, brickStairBlock, 3);
				setBlockAndMetadata(world, i11, 3, i13, brick2WallBlock, brick2WallMeta);
				setBlockAndMetadata(world, i11, 3, i13 + 1, brickStairBlock, 2);
				setBlockAndMetadata(world, i11, 4, i13 - 1, brickStairBlock, 7);
				setBlockAndMetadata(world, i11, 4, i13, brick2WallBlock, brick2WallMeta);
				setBlockAndMetadata(world, i11, 4, i13 + 1, brickStairBlock, 6);
				setBlockAndMetadata(world, i12, 1, i13 - 1, brickStairBlock, 2);
				setBlockAndMetadata(world, i12, 1, i13, brickBlock, brickMeta);
				setBlockAndMetadata(world, i12, 1, i13 + 1, brickStairBlock, 3);
				setBlockAndMetadata(world, i12, 2, i13, brickSlabBlock, brickSlabMeta);
			}
		}
		for (int i11 : new int[]{-10, 10}) {
			for (int i12 : new int[]{i11 - 2, i11 + 2}) {
				placeArmorStand(world, i12, 1, -7, 0, getRandArmorItems(random));
				placeArmorStand(world, i12, 1, 7, 2, getRandArmorItems(random));
			}
			placeChest(world, random, i11, 1, -6, getChest(), 2, getChestContents(), 1);
			setAir(world, i11, 2, -6);
			spawnItemFrame(world, i11, 3, -6, 2, getRandFrameItem(random));
			placeChest(world, random, i11, 1, 6, getChest(), 3, getChestContents(), 1);
			setAir(world, i11, 2, 6);
			spawnItemFrame(world, i11, 3, 6, 0, getRandFrameItem(random));
		}
		for (int i11 : new int[]{-10, 10}) {
			for (int k2 : new int[]{i11 - 2, i11 + 2}) {
				placeArmorStand(world, -7, 1, k2, 1, getRandArmorItems(random));
				placeArmorStand(world, 7, 1, k2, 3, getRandArmorItems(random));
			}
			placeChest(world, random, -6, 1, i11, getChest(), 5, getChestContents(), 1);
			setAir(world, -6, 2, i11);
			spawnItemFrame(world, -6, 3, i11, 3, getRandFrameItem(random));
			placeChest(world, random, 6, 1, i11, getChest(), 4, getChestContents(), 1);
			setAir(world, 6, 2, i11);
			spawnItemFrame(world, 6, 3, i11, 1, getRandFrameItem(random));
		}
		for (int i9 = 1; i9 <= 4; i9++) {
			for (int i11 = -1; i11 <= 1; i11++) {
				setBlockAndMetadata(world, i11, i9, -10, gateBlock, 2);
				setAir(world, i11, i9, -9);
				setAir(world, i11, i9, -11);
			}
			setBlockAndMetadata(world, -2, i9, -9, pillarBlock, pillarMeta);
			setBlockAndMetadata(world, 2, i9, -9, pillarBlock, pillarMeta);
		}
		int i8;
		for (i8 = -1; i8 <= 1; i8++) {
			int i11 = -12;
			for (int i12 = 0; (i12 <= 0 || !isOpaque(world, i8, i12, i11)) && getY(i12) >= 0; i12--) {
				setBlockAndMetadata(world, i8, i12, i11, brickBlock, brickMeta);
				setGrassToDirt(world, i8, i12 - 1, i11);
			}
		}
		placeWallBanner(world, -2, 4, -11, bannerType, 2);
		placeWallBanner(world, 2, 4, -11, bannerType, 2);
		for (i8 = -2; i8 <= 2; i8++) {
			for (int i11 = -2; i11 <= 2; i11++) {
				setBlockAndMetadata(world, i8, 0, i11, brick2Block, brick2Meta);
			}
		}
		for (i8 = -8; i8 <= 8; i8++) {
			setBlockAndMetadata(world, i8, 0, 0, brick2Block, brick2Meta);
		}
		for (int i7 = -12; i7 <= 8; i7++) {
			setBlockAndMetadata(world, 0, 0, i7, brick2Block, brick2Meta);
		}
		setBlockAndMetadata(world, 0, 0, 0, GOTBlocks.brick4, 6);
		int i6;
		for (i6 = 1; i6 <= 4; i6++) {
			setBlockAndMetadata(world, -1, i6, -1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 1, i6, -1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, -1, i6, 1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 1, i6, 1, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, -1, 5, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, 0, 5, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, 1, 5, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 5, 0, brickStairBlock, 1);
		setBlockAndMetadata(world, 0, 5, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 5, 0, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 5, 1, brickStairBlock, 3);
		setBlockAndMetadata(world, 0, 5, 1, brickStairBlock, 3);
		setBlockAndMetadata(world, 1, 5, 1, brickStairBlock, 3);
		for (i6 = 6; i6 <= 9; i6++) {
			setBlockAndMetadata(world, 0, i6, 0, pillarBlock, pillarMeta);
		}
		setBlockAndMetadata(world, 0, 10, 0, brickCarved, brickCarvedMeta);
		setBlockAndMetadata(world, 0, 11, 0, GOTBlocks.beacon, 0);
		placeWallBanner(world, 0, 9, 0, bannerType, 0);
		placeWallBanner(world, 0, 9, 0, bannerType, 1);
		placeWallBanner(world, 0, 9, 0, bannerType, 2);
		placeWallBanner(world, 0, 9, 0, bannerType, 3);
		setBlockAndMetadata(world, 0, 4, 0, GOTBlocks.chandelier, 2);
		setBlockAndMetadata(world, -3, 3, -8, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -3, 4, -8, Blocks.torch, 5);
		setBlockAndMetadata(world, 3, 3, -8, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 3, 4, -8, Blocks.torch, 5);
		setBlockAndMetadata(world, -8, 3, -3, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -8, 4, -3, Blocks.torch, 5);
		setBlockAndMetadata(world, 8, 3, -3, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 8, 4, -3, Blocks.torch, 5);
		for (int i5 = -7; i5 <= 7; i5++) {
			int i11 = Math.abs(i5);
			if (i11 >= 2) {
				for (int i12 = -7; i12 <= -2; i12++) {
					int k2 = i12 + 9;
					int d = Math.abs(i11 - k2);
					if (d == 0 && (i11 == 2 || i11 == 7)) {
						d = 2;
					}
					if (d <= 2) {
						setBlockAndMetadata(world, i5, 0, i12, Blocks.grass, 0);
						switch (d) {
							case 0:
								setBlockAndMetadata(world, i5, 1, i12, Blocks.double_plant, 4);
								setBlockAndMetadata(world, i5, 2, i12, Blocks.double_plant, 8);
								break;
							case 1:
								setBlockAndMetadata(world, i5, 1, i12, Blocks.red_flower, 6);
								break;
							case 2:
								setBlockAndMetadata(world, i5, 1, i12, Blocks.red_flower, 4);
								break;
						}
					}
				}
			}
		}
		setBlockAndMetadata(world, -7, 0, 1, brick2Block, brick2Meta);
		for (int i4 = 0; i4 <= 2; i4++) {
			setBlockAndMetadata(world, -7, 1 + i4, 2 + i4, brickStairBlock, 2);
			for (int i11 = 1; i11 < 1 + i4; i11++) {
				setBlockAndMetadata(world, -7, i11, 2 + i4, brickBlock, brickMeta);
			}
		}
		for (int i3 = 1; i3 <= 3; i3++) {
			setBlockAndMetadata(world, -7, i3, 5, brickBlock, brickMeta);
			setBlockAndMetadata(world, -7, i3, 6, brickBlock, brickMeta);
			setBlockAndMetadata(world, -8, i3, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, -8, i3, 5, brickBlock, brickMeta);
		}
		for (int step = 0; step <= 2; step++) {
			setBlockAndMetadata(world, -8, 4 + step, 3 - step, brickStairBlock, 3);
			for (int i11 = 1; i11 < 4 + step; i11++) {
				setBlockAndMetadata(world, -8, i11, 3 - step, brickBlock, brickMeta);
			}
		}
		for (int i2 = -1; i2 <= 0; i2++) {
			setBlockAndMetadata(world, -8, 5, i2, brickStairBlock, 4);
			setBlockAndMetadata(world, -8, 6, i2, brickBlock, brickMeta);
		}
		setAir(world, -9, 7, 0);
		setAir(world, -9, 7, 1);
		setBlockAndMetadata(world, -8, 7, -1, brick2WallBlock, brick2WallMeta);
		for (int n = 6; n <= 8; n++) {
			for (int i11 = -1; i11 <= 3; i11++) {
				setBlockAndMetadata(world, n, 0, i11, brick2Block, brick2Meta);
				if (n >= 7 && i11 >= 0 && i11 <= 2) {
					setBlockAndMetadata(world, n, 4, i11, plankSlabBlock, plankSlabMeta | 0x8);
				} else {
					setBlockAndMetadata(world, n, 4, i11, plankSlabBlock, plankSlabMeta);
				}
			}
		}
		for (int j1 = 1; j1 <= 3; j1++) {
			setBlockAndMetadata(world, 6, j1, -1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 6, j1, 3, fenceBlock, fenceMeta);
		}
		for (int m = 7; m <= 8; m++) {
			setBlockAndMetadata(world, m, 3, -1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, m, 3, 3, fenceBlock, fenceMeta);
		}
		for (int k1 = 0; k1 <= 2; k1++) {
			setBlockAndMetadata(world, 6, 3, k1, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 8, 1, -1, GOTBlocks.alloyForge, 5);
		setBlockAndMetadata(world, 8, 2, -1, Blocks.furnace, 5);
		setBlockAndMetadata(world, 8, 1, 1, tableBlock, 0);
		placeChest(world, random, 8, 1, 2, getChest(), 5, getChestContents());
		setBlockAndMetadata(world, 8, 1, 3, Blocks.crafting_table, 0);
		spawnItemFrame(world, 9, 2, 1, 3, getRandFrameItem(random));
		spawnItemFrame(world, 9, 2, 2, 3, getRandFrameItem(random));
		setBlockAndMetadata(world, 6, 1, 1, Blocks.anvil, 0);
		setBlockAndMetadata(world, 8, 3, 1, Blocks.torch, 1);
		int i1;
		for (i1 = -5; i1 <= 5; i1++) {
			for (int i11 = 4; i11 <= 8; i11++) {
				int i12 = Math.abs(i1);
				if (i12 != 5 || i11 != 4) {
					if (i12 <= 4 && i11 >= 5) {
						setBlockAndMetadata(world, i1, 0, i11, plankBlock, plankMeta);
						setBlockAndMetadata(world, i1, 4, i11, brickBlock, brickMeta);
					} else {
						if (i12 == 1 || i12 == 4 || i11 == 5) {
							for (int i13 = 1; i13 <= 3; i13++) {
								setBlockAndMetadata(world, i1, i13, i11, woodBeamBlock, woodBeamMeta);
							}
						} else {
							setBlockAndMetadata(world, i1, 1, i11, brickBlock, brickMeta);
							for (int i13 = 2; i13 <= 3; i13++) {
								setBlockAndMetadata(world, i1, i13, i11, plankBlock, plankMeta);
							}
						}
						setBlockAndMetadata(world, i1, 4, i11, brickSlabBlock, brickSlabMeta);
					}
				}
			}
		}
		setBlockAndMetadata(world, 0, 0, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, 4, doorBlock, 8);
		for (i1 = -4; i1 <= 4; i1++) {
			if (IntMath.mod(i1, 2) == 0) {
				setBlockAndMetadata(world, i1, 1, 7, bedBlock, 0);
				setBlockAndMetadata(world, i1, 1, 8, bedBlock, 8);
			} else {
				placeChest(world, random, i1, 1, 8, getChest(), 2, getChestContents());
			}
		}
		placeWallBanner(world, -2, 3, 9, bannerType, 2);
		placeWallBanner(world, 2, 3, 9, bannerType, 2);
		setBlockAndMetadata(world, -4, 1, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 1, 5, plankBlock, plankMeta);
		placeBarrel(world, random, -4, 2, 5, 3, GOTFoods.DEFAULT_DRINK);
		placeMug(world, random, -3, 2, 5, 2, GOTFoods.DEFAULT_DRINK);
		setBlockAndMetadata(world, 3, 1, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, 4, 1, 5, plankBlock, plankMeta);
		placePlateWithCertainty(world, random, 3, 2, 5, plateBlock, GOTFoods.DEFAULT);
		placePlateWithCertainty(world, random, 4, 2, 5, plateBlock, GOTFoods.DEFAULT);
		setBlockAndMetadata(world, -3, 3, 6, GOTBlocks.chandelier, 2);
		setBlockAndMetadata(world, 3, 3, 6, GOTBlocks.chandelier, 2);
		setBlockAndMetadata(world, -5, 1, 2, GOTBlocks.commandTable, 0);
		if (hasMaester()) {
			GOTEntityNPC maester = new GOTEntityMaester(world);
			maester.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(maester, world, 0, 1, 0, 12);
		}
		if (hasSepton()) {
			GOTEntityNPC septon = new GOTEntitySepton(world);
			septon.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(septon, world, 0, 1, 0, 12);
		}
		if (kingdom == Kingdom.DRAGONSTONE) {
			GOTEntityNPC priest = new GOTEntityRedPriest(world);
			priest.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(priest, world, 0, 1, 0, 12);
		} else if (kingdom == Kingdom.IRONBORN) {
			GOTEntityNPC priest = new GOTEntityIronbornPriest(world);
			priest.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(priest, world, 0, 1, 0, 12);
		} else if (kingdom == Kingdom.CROWNLANDS_RED) {
			spawnLegendaryNPC(new GOTEntitySandorClegane(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityJoffreyBaratheon(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityCerseiLannister(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityJaimeLannister(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityPycelle(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityJanosSlynt(world), world, -2, 1, 0);
			spawnLegendaryNPC(new GOTEntityVarys(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityIlynPayne(world), world, 0, 1, -2);
			spawnLegendaryNPC(new GOTEntityHighSepton(world), world, -1, 1, 2);
			spawnLegendaryNPC(new GOTEntityTommenBaratheon(world), world, -2, 1, 1);
			spawnLegendaryNPC(new GOTEntityMyrcellaBaratheon(world), world, -1, 1, -2);
			spawnLegendaryNPC(new GOTEntityMerynTrant(world), world, -1, 1, 1);
			spawnLegendaryNPC(new GOTEntityBarristanSelmy(world), world, -1, 1, 0);
		} else {
			GOTEntityNPC captain = getCaptain(world);
			captain.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
			GOTEntityNPC soldier = getSoldier(world);
			soldier.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass1(getSoldier(world).getClass());
		respawner.setSpawnClass2(getSoldierArcher(world).getClass());
		respawner.setCheckRanges(20, -8, 12, 12);
		respawner.setSpawnRanges(10, 0, 8, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
	}
}