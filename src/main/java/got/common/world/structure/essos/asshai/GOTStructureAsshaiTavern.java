package got.common.world.structure.essos.asshai;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.*;
import got.common.entity.essos.asshai.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureAsshaiTavern extends GOTStructureAsshaiBase {
	public String[] tavernName;
	public String[] tavernNameSign;

	public GOTStructureAsshaiTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int oppHeight;
		int maxHeight;
		int i13;
		int step;
		int i142;
		int doorHeight;
		int j12;
		int i1;
		int k1;
		int k142;
		int beam;
		int j1;
		int i12;
		int k132;
		int k12;
		this.setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			maxHeight = 0;
			for (i12 = -9; i12 <= 13; ++i12) {
				for (k142 = -2; k142 <= 16; ++k142) {
					int j13 = getTopBlock(world, i12, k142) - 1;
					if (!isSurface(world, i12, j13, k142)) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		if (restrictions && (oppHeight = getTopBlock(world, 0, 15) - 1) > 0) {
			originY = getY(oppHeight);
		}
		for (int i15 = -7; i15 <= 11; ++i15) {
			for (k1 = 0; k1 <= 14; ++k1) {
				if ((i15 == -7 || i15 == 11) && (k1 == 0 || k1 == 14)) {
					continue;
				}
				beam = 0;
				if (i15 == -7 || i15 == 11) {
					beam = IntMath.mod(k1, 4) == 1 ? 1 : 0;
				} else if (k1 == 0 || k1 == 14) {
					beam = IntMath.mod(i15, 4) == 2 ? 1 : 0;
					maxHeight = beam;
				}
				if (beam != 0) {
					for (j12 = 4; (j12 >= 0 || !isOpaque(world, i15, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i15, j12, k1, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i15, j12 - 1, k1);
					}
					continue;
				}
				if (i15 == -7 || i15 == 11 || k1 == 0 || k1 == 14) {
					for (j12 = 0; (j12 >= 0 || !isOpaque(world, i15, j12, k1)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i15, j12, k1, rockBlock, rockMeta);
						setGrassToDirt(world, i15, j12 - 1, k1);
					}
					for (j12 = 1; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i15, j12, k1, wallBlock, wallMeta);
					}
					continue;
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i15, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i15, j12, k1, plankBlock, plankMeta);
					setGrassToDirt(world, i15, j12 - 1, k1);
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setAir(world, i15, j12, k1);
				}
			}
		}
		for (int k1421 : new int[] { 0, 14 }) {
			for (i1 = -4; i1 <= 8; ++i1) {
				if (IntMath.mod(i1, 4) != 0 || i1 == 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 2, k1421, GOTRegistry.glassPane, 0);
				setBlockAndMetadata(world, i1, 3, k1421, GOTRegistry.glassPane, 0);
			}
		}
		for (int i1421 : new int[] { -7, 11 }) {
			for (k132 = 3; k132 <= 11; ++k132) {
				if (IntMath.mod(k132, 4) != 3 || i1421 == -7 && k132 == 7) {
					continue;
				}
				setBlockAndMetadata(world, i1421, 2, k132, GOTRegistry.glassPane, 0);
				setBlockAndMetadata(world, i1421, 3, k132, GOTRegistry.glassPane, 0);
			}
		}
		setBlockAndMetadata(world, 0, 0, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 0, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, 0, doorBlock, 8);
		setBlockAndMetadata(world, 0, 0, 14, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 14, doorBlock, 3);
		setBlockAndMetadata(world, 0, 2, 14, doorBlock, 8);
		int[] i15 = { -1, 15 };
		k1 = i15.length;
		block13: for (beam = 0; beam < k1; ++beam) {
			int j14;
			i1 = 0;
			k142 = i15[beam];
			doorHeight = getTopBlock(world, i1, k142) - 1;
			if (doorHeight >= 0) {
				continue;
			}
			for (j14 = 0; (j14 == 0 || !isOpaque(world, i1, j14, k142)) && getY(j14) >= 0; --j14) {
				setBlockAndMetadata(world, i1, j14, k142, plankBlock, plankMeta);
				setGrassToDirt(world, i1, j14 - 1, k142);
			}
			++i1;
			j14 = 0;
			while (!isOpaque(world, i1, j14, k142) && getY(j14) >= 0) {
				setBlockAndMetadata(world, i1, j14, k142, plankStairBlock, 0);
				setGrassToDirt(world, i1, j14 - 1, k142);
				int j2 = j14 - 1;
				while (!isOpaque(world, i1, j2, k142) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k142, plankBlock, plankMeta);
					setGrassToDirt(world, i1, j2 - 1, k142);
					--j2;
				}
				i1++;
				if (i1 >= 15) {
					continue block13;
				}
				--j14;
			}
		}
		setBlockAndMetadata(world, -2, 3, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 4, -1, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 2, 3, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, -1, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 0, 4, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, -2, plankBlock, plankMeta);
		placeSign(world, -1, 4, -2, Blocks.wall_sign, 5, tavernNameSign);
		placeSign(world, 1, 4, -2, Blocks.wall_sign, 4, tavernNameSign);
		placeSign(world, 0, 4, -3, Blocks.wall_sign, 2, tavernNameSign);
		setBlockAndMetadata(world, -2, 3, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 4, 15, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 2, 3, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, 15, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 0, 4, 15, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, 16, plankBlock, plankMeta);
		placeSign(world, -1, 4, 16, Blocks.wall_sign, 5, tavernNameSign);
		placeSign(world, 1, 4, 16, Blocks.wall_sign, 4, tavernNameSign);
		placeSign(world, 0, 4, 17, Blocks.wall_sign, 3, tavernNameSign);
		for (int i16 = -8; i16 <= 12; ++i16) {
			for (k1 = -1; k1 <= 15; ++k1) {
				if ((i16 <= -7 || i16 >= 11) && (k1 <= 0 || k1 >= 14)) {
					continue;
				}
				beam = 0;
				if (i16 == -8 || i16 == 12) {
					beam = IntMath.mod(k1, 4) == 1 ? 1 : 0;
				} else if (k1 == -1 || k1 == 15) {
					beam = IntMath.mod(i16, 4) == 2 ? 1 : 0;
					doorHeight = beam;
				}
				if (beam != 0) {
					if (i16 == -8 || i16 == 12) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 4);
					}
					if (k1 == -1 || k1 == 15) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 8);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k1, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if (i16 == -8 || i16 == 12 || k1 == -1 || k1 == 15) {
					if (i16 == -8 || i16 == 12) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 8);
					}
					if (k1 == -1 || k1 == 15) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 4);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k1, wallBlock, wallMeta);
					}
					continue;
				}
				if ((i16 == -7 || i16 == 11) && (k1 == 1 || k1 == 13) || (i16 == -6 || i16 == 10) && (k1 == 0 || k1 == 14)) {
					if (i16 == -7 || i16 == 11) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 4);
					}
					if (k1 == 0 || k1 == 14) {
						setBlockAndMetadata(world, i16, 5, k1, woodBeamBlock, woodBeamMeta | 8);
					}
					for (j12 = 6; j12 <= 8; ++j12) {
						setBlockAndMetadata(world, i16, j12, k1, wallBlock, wallMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i16, 5, k1, plankBlock, plankMeta);
				for (j12 = 6; j12 <= 11; ++j12) {
					setAir(world, i16, j12, k1);
				}
			}
		}
		for (int k1421 : new int[] { -1, 15 }) {
			for (i1 = -4; i1 <= 8; ++i1) {
				if (IntMath.mod(i1, 4) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 7, k1421, GOTRegistry.glassPane, 0);
			}
		}
		int[] i16 = { -8, 12 };
		k1 = i16.length;
		for (beam = 0; beam < k1; ++beam) {
			i142 = i16[beam];
			for (k132 = 3; k132 <= 11; ++k132) {
				if (IntMath.mod(k132, 4) != 3) {
					continue;
				}
				setBlockAndMetadata(world, i142, 7, k132, GOTRegistry.glassPane, 0);
			}
		}
		for (int step2 = 0; step2 <= 2; ++step2) {
			for (int i17 = -9; i17 <= 13; ++i17) {
				if (i17 >= -7 + step2 && i17 <= 11 - step2) {
					setBlockAndMetadata(world, i17, 8 + step2, -2 + step2, plankStairBlock, 2);
					setBlockAndMetadata(world, i17, 8 + step2, 16 - step2, plankStairBlock, 3);
				}
				if (i17 > -7 + step2 && i17 < 11 - step2) {
					continue;
				}
				setBlockAndMetadata(world, i17, 8 + step2, 0 + step2, plankStairBlock, 2);
				setBlockAndMetadata(world, i17, 8 + step2, 14 - step2, plankStairBlock, 3);
			}
			setBlockAndMetadata(world, -7 + step2, 8 + step2, -1 + step2, plankStairBlock, 1);
			setBlockAndMetadata(world, 11 - step2, 8 + step2, -1 + step2, plankStairBlock, 0);
			setBlockAndMetadata(world, -7 + step2, 8 + step2, 15 - step2, plankStairBlock, 1);
			setBlockAndMetadata(world, 11 - step2, 8 + step2, 15 - step2, plankStairBlock, 0);
		}
		for (int i18 = -9; i18 <= 13; ++i18) {
			setBlockAndMetadata(world, i18, 11, 4, plankBlock, plankMeta);
			setBlockAndMetadata(world, i18, 12, 5, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i18, 12, 6, plankBlock, plankMeta);
			setBlockAndMetadata(world, i18, 12, 7, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i18, 13, 7, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i18, 12, 8, plankBlock, plankMeta);
			setBlockAndMetadata(world, i18, 12, 9, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i18, 11, 10, plankBlock, plankMeta);
			if (i18 >= -3 && i18 <= 7) {
				setBlockAndMetadata(world, i18, 11, 1, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i18, 11, 2, plankBlock, plankMeta);
				setBlockAndMetadata(world, i18, 11, 3, plankBlock, plankMeta);
				setBlockAndMetadata(world, i18, 11, 11, plankBlock, plankMeta);
				setBlockAndMetadata(world, i18, 11, 12, plankBlock, plankMeta);
				setBlockAndMetadata(world, i18, 11, 13, plankSlabBlock, plankSlabMeta);
			} else {
				setBlockAndMetadata(world, i18, 11, 3, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i18, 11, 11, plankSlabBlock, plankSlabMeta);
			}
			if (i18 == -4 || i18 == 8) {
				setBlockAndMetadata(world, i18, 11, 1, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i18, 11, 2, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i18, 11, 12, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, i18, 11, 13, plankSlabBlock, plankSlabMeta);
			}
			if (i18 != -9 && i18 != 13) {
				continue;
			}
			setBlockAndMetadata(world, i18, 8, 1, plankStairBlock, 7);
			setBlockAndMetadata(world, i18, 9, 2, plankStairBlock, 7);
			setBlockAndMetadata(world, i18, 10, 3, plankStairBlock, 7);
			setBlockAndMetadata(world, i18, 11, 5, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i18, 11, 9, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i18, 10, 11, plankStairBlock, 6);
			setBlockAndMetadata(world, i18, 9, 12, plankStairBlock, 6);
			setBlockAndMetadata(world, i18, 8, 13, plankStairBlock, 6);
		}
		for (int i1421 : new int[] { -8, 12 }) {
			for (k132 = 2; k132 <= 12; ++k132) {
				setBlockAndMetadata(world, i1421, 9, k132, woodBeamBlock, woodBeamMeta | 8);
			}
			for (k132 = 3; k132 <= 11; ++k132) {
				setBlockAndMetadata(world, i1421, 10, k132, wallBlock, wallMeta);
			}
			for (k132 = 5; k132 <= 9; ++k132) {
				setBlockAndMetadata(world, i1421, 11, k132, wallBlock, wallMeta);
			}
		}
		for (int i19 = 3; i19 <= 5; ++i19) {
			for (k1 = 6; k1 <= 8; ++k1) {
				for (j1 = 0; j1 <= 13; ++j1) {
					if (i19 == 4 && k1 == 7) {
						setAir(world, i19, j1, k1);
						continue;
					}
					setBlockAndMetadata(world, i19, j1, k1, brickBlock, brickMeta);
				}
			}
			setBlockAndMetadata(world, i19, 14, 6, brickStairBlock, 2);
			setBlockAndMetadata(world, i19, 14, 8, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, 3, 14, 7, brickStairBlock, 1);
		setBlockAndMetadata(world, 5, 14, 7, brickStairBlock, 0);
		setBlockAndMetadata(world, 4, 15, 7, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 16, 7, brickBlock, brickMeta);
		setBlockAndMetadata(world, 4, 17, 7, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 4, 18, 7, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 4, 0, 7, GOTRegistry.hearth, 0);
		setBlockAndMetadata(world, 4, 1, 7, Blocks.fire, 0);
		setBlockAndMetadata(world, 4, 1, 6, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 1, 8, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 3, 1, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 5, 1, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 2, 6, Blocks.furnace, 2);
		setBlockAndMetadata(world, 4, 2, 8, Blocks.furnace, 3);
		setBlockAndMetadata(world, 3, 2, 7, Blocks.furnace, 5);
		setBlockAndMetadata(world, 5, 2, 7, Blocks.furnace, 4);
		setBlockAndMetadata(world, 0, 4, 3, GOTRegistry.chandelier, 12);
		setBlockAndMetadata(world, 0, 4, 11, GOTRegistry.chandelier, 12);
		setBlockAndMetadata(world, 8, 4, 3, GOTRegistry.chandelier, 12);
		setBlockAndMetadata(world, 8, 4, 11, GOTRegistry.chandelier, 12);
		for (int k1421 : new int[] { 1, 2 }) {
			setBlockAndMetadata(world, -4, 1, k1421, plankBlock, plankMeta);
			placeMugOrPlate(world, random, -4, 2, k1421);
			setBlockAndMetadata(world, -6, 1, k1421, plankStairBlock, 0);
			setBlockAndMetadata(world, -2, 1, k1421, plankStairBlock, 1);
		}
		int[] i19 = { 1, 2, 12, 13 };
		k1 = i19.length;
		for (j1 = 0; j1 < k1; ++j1) {
			k142 = i19[j1];
			setBlockAndMetadata(world, 2, 1, k142, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 2, 2, k142);
			setBlockAndMetadata(world, 3, 1, k142, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 3, 2, k142);
			setBlockAndMetadata(world, 5, 1, k142, plankStairBlock, 1);
		}
		for (k12 = 6; k12 <= 8; ++k12) {
			setBlockAndMetadata(world, 8, 1, k12, plankBlock, plankMeta);
			placeMugOrPlate(world, random, 8, 2, k12);
			setBlockAndMetadata(world, 10, 1, k12, plankStairBlock, 1);
		}
		for (i13 = 7; i13 <= 10; ++i13) {
			setBlockAndMetadata(world, i13, 1, 1, plankStairBlock, 3);
			setBlockAndMetadata(world, i13, 1, 13, plankStairBlock, 2);
		}
		for (k12 = 2; k12 <= 4; ++k12) {
			setBlockAndMetadata(world, 10, 1, k12, plankStairBlock, 1);
		}
		for (k12 = 10; k12 <= 12; ++k12) {
			setBlockAndMetadata(world, 10, 1, k12, plankStairBlock, 1);
		}
		for (i13 = 7; i13 <= 8; ++i13) {
			int[] k15 = { 3, 4, 10, 11 };
			j1 = k15.length;
			for (k142 = 0; k142 < j1; ++k142) {
				k132 = k15[k142];
				setBlockAndMetadata(world, i13, 1, k132, plankBlock, plankMeta);
				placeMugOrPlate(world, random, i13, 2, k132);
			}
		}
		for (int j15 = 1; j15 <= 4; ++j15) {
			setBlockAndMetadata(world, -2, j15, 5, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, -2, j15, 9, woodBeamBlock, woodBeamMeta);
		}
		for (i13 = -6; i13 <= -3; ++i13) {
			setBlockAndMetadata(world, i13, 1, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, i13, 3, 5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i13, 4, 5, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i13, 1, 9, plankBlock, plankMeta);
			setBlockAndMetadata(world, i13, 3, 9, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i13, 4, 9, woodBeamBlock, woodBeamMeta | 4);
		}
		for (k12 = 6; k12 <= 8; ++k12) {
			setBlockAndMetadata(world, -2, 1, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 3, k12, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -2, 4, k12, woodBeamBlock, woodBeamMeta | 8);
		}
		setBlockAndMetadata(world, -4, 1, 5, fenceGateBlock, 0);
		this.placeBarrel(world, random, -6, 2, 5, 3, GOTFoods.WESTEROS_DRINK);
		this.placeMug(world, random, -5, 2, 5, 2, GOTFoods.WESTEROS_DRINK);
		this.placeMug(world, random, -3, 2, 5, 2, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -4, 1, 9, fenceGateBlock, 2);
		this.placeBarrel(world, random, -6, 2, 9, 2, GOTFoods.WESTEROS_DRINK);
		this.placeMug(world, random, -5, 2, 9, 0, GOTFoods.WESTEROS_DRINK);
		this.placeMug(world, random, -3, 2, 9, 0, GOTFoods.WESTEROS_DRINK);
		this.placeBarrel(world, random, -2, 2, 8, 5, GOTFoods.WESTEROS_DRINK);
		this.placeMug(world, random, -2, 2, 6, 1, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -6, 1, 6, plankStairBlock, 4);
		placePlateWithCertainty(world, random, -6, 2, 6, plateBlock, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, -6, 1, 7, Blocks.furnace, 4);
		setBlockAndMetadata(world, -6, 1, 8, Blocks.cauldron, 3);
		this.placeChest(world, random, -3, 0, 8, 5, GOTChestContents.ASSHAI);
		for (k12 = 6; k12 <= 8; ++k12) {
			setBlockAndMetadata(world, -6, 3, k12, plankStairBlock, 4);
			this.placeBarrel(world, random, -6, 4, k12, 4, GOTFoods.WESTEROS_DRINK);
		}
		setBlockAndMetadata(world, -4, 4, 7, GOTRegistry.chandelier, 12);
		for (step = 0; step <= 2; ++step) {
			setBlockAndMetadata(world, -3 - step, 1 + step, 13, plankStairBlock, 0);
			setBlockAndMetadata(world, -4 - step, 1 + step, 13, plankStairBlock, 5);
		}
		setBlockAndMetadata(world, -6, 3, 13, plankBlock, plankMeta);
		for (step = 0; step <= 1; ++step) {
			setBlockAndMetadata(world, -6, 4 + step, 12 - step, plankStairBlock, 3);
			setBlockAndMetadata(world, -6, 3 + step, 12 - step, plankStairBlock, 6);
		}
		for (i13 = -6; i13 <= -4; ++i13) {
			setAir(world, i13, 5, 13);
		}
		setAir(world, -6, 5, 12);
		for (i13 = -5; i13 <= -3; ++i13) {
			setBlockAndMetadata(world, i13, 6, 14, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i13, 6, 12, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -3, 6, 13, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -7, 6, 12, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -7, 6, 11, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 6, 11, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 7, 12, GOTRegistry.asshaiTorch, 5);
		for (i13 = -7; i13 <= -3; ++i13) {
			for (k1 = 10; k1 <= 14; ++k1) {
				if (i13 == -3 && k1 == 10) {
					continue;
				}
				if ((i13 >= -4 || k1 <= 11) && k1 <= 13) {
					setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
				}
				if (i13 < -5 && k1 > 12) {
					continue;
				}
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 4, 7, 6, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 4, 7, 8, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 3, 7, 7, Blocks.iron_bars, 0);
		setBlockAndMetadata(world, 5, 7, 7, Blocks.iron_bars, 0);
		spawnItemFrame(world, 3, 10, 7, 3, getFramedItem(random));
		for (i13 = -2; i13 <= 1; ++i13) {
			for (k1 = 5; k1 <= 9; ++k1) {
				setBlockAndMetadata(world, i13, 6, k1, Blocks.carpet, 12);
			}
		}
		for (i13 = -2; i13 <= 6; ++i13) {
			int k16;
			int i2 = IntMath.mod(i13, 4);
			if (i2 == 2) {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i13, j1, 3, woodBeamBlock, woodBeamMeta);
					for (k142 = 0; k142 <= 2; ++k142) {
						setBlockAndMetadata(world, i13, j1, k142, wallBlock, wallMeta);
					}
					setBlockAndMetadata(world, i13, j1, 11, woodBeamBlock, woodBeamMeta);
					for (k142 = 12; k142 <= 14; ++k142) {
						setBlockAndMetadata(world, i13, j1, k142, wallBlock, wallMeta);
					}
				}
				for (k16 = 0; k16 <= 3; ++k16) {
					setBlockAndMetadata(world, i13, 9, k16, woodBeamBlock, woodBeamMeta | 8);
				}
				for (k16 = 11; k16 <= 14; ++k16) {
					setBlockAndMetadata(world, i13, 9, k16, woodBeamBlock, woodBeamMeta | 8);
				}
			} else {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i13, j1, 3, wallBlock, wallMeta);
					setBlockAndMetadata(world, i13, j1, 11, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i13, 9, 3, woodBeamBlock, woodBeamMeta | 4);
				setBlockAndMetadata(world, i13, 9, 11, woodBeamBlock, woodBeamMeta | 4);
				for (k16 = 0; k16 <= 2; ++k16) {
					setBlockAndMetadata(world, i13, 9, k16, plankSlabBlock, plankSlabMeta | 8);
				}
				for (k16 = 12; k16 <= 14; ++k16) {
					setBlockAndMetadata(world, i13, 9, k16, plankSlabBlock, plankSlabMeta | 8);
				}
			}
			if (i2 == 0) {
				setBlockAndMetadata(world, i13, 6, 3, doorBlock, 3);
				setBlockAndMetadata(world, i13, 7, 3, doorBlock, 8);
				setBlockAndMetadata(world, i13, 8, 2, GOTRegistry.asshaiTorch, 4);
				setBlockAndMetadata(world, i13, 6, 11, doorBlock, 1);
				setBlockAndMetadata(world, i13, 7, 11, doorBlock, 8);
				setBlockAndMetadata(world, i13, 8, 12, GOTRegistry.asshaiTorch, 3);
			}
			if (i2 == 3) {
				setBlockAndMetadata(world, i13, 6, 1, bedBlock, 0);
				setBlockAndMetadata(world, i13, 6, 2, bedBlock, 8);
				setBlockAndMetadata(world, i13, 6, 0, Blocks.chest, 4);
				setBlockAndMetadata(world, i13, 6, 13, bedBlock, 2);
				setBlockAndMetadata(world, i13, 6, 12, bedBlock, 10);
				setBlockAndMetadata(world, i13, 6, 14, Blocks.chest, 4);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i13, 6, 2, plankStairBlock, 2);
				setBlockAndMetadata(world, i13, 6, 0, plankBlock, plankMeta);
				this.placeMug(world, random, i13, 7, 0, 2, GOTFoods.WESTEROS_DRINK);
				setBlockAndMetadata(world, i13, 6, 12, plankStairBlock, 3);
				setBlockAndMetadata(world, i13, 6, 14, plankBlock, plankMeta);
				this.placeMug(world, random, i13, 7, 14, 0, GOTFoods.WESTEROS_DRINK);
			}
			for (k16 = 1; k16 <= 3; ++k16) {
				setBlockAndMetadata(world, i13, 10, k16, wallBlock, wallMeta);
			}
			for (k16 = 11; k16 <= 13; ++k16) {
				setBlockAndMetadata(world, i13, 10, k16, wallBlock, wallMeta);
			}
		}
		for (k12 = 5; k12 <= 9; ++k12) {
			int k2 = IntMath.mod(k12, 4);
			if (k2 == 1) {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, -4, j1, k12, woodBeamBlock, woodBeamMeta);
					for (i142 = -7; i142 <= -5; ++i142) {
						setBlockAndMetadata(world, i142, j1, k12, wallBlock, wallMeta);
					}
					setBlockAndMetadata(world, 8, j1, k12, woodBeamBlock, woodBeamMeta);
					for (i142 = 9; i142 <= 11; ++i142) {
						setBlockAndMetadata(world, i142, j1, k12, wallBlock, wallMeta);
					}
				}
				for (i12 = -7; i12 <= -4; ++i12) {
					setBlockAndMetadata(world, i12, 9, k12, woodBeamBlock, woodBeamMeta | 4);
				}
				for (i12 = 8; i12 <= 11; ++i12) {
					setBlockAndMetadata(world, i12, 9, k12, woodBeamBlock, woodBeamMeta | 4);
				}
			} else {
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, -4, j1, k12, wallBlock, wallMeta);
					setBlockAndMetadata(world, 8, j1, k12, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, -4, 9, k12, woodBeamBlock, woodBeamMeta | 8);
				setBlockAndMetadata(world, 8, 9, k12, woodBeamBlock, woodBeamMeta | 8);
				for (i12 = -7; i12 <= -5; ++i12) {
					setBlockAndMetadata(world, i12, 9, k12, plankSlabBlock, plankSlabMeta | 8);
				}
				for (i12 = 9; i12 <= 11; ++i12) {
					setBlockAndMetadata(world, i12, 9, k12, plankSlabBlock, plankSlabMeta | 8);
				}
			}
			if (k2 == 3) {
				setBlockAndMetadata(world, -4, 6, k12, doorBlock, 0);
				setBlockAndMetadata(world, -4, 7, k12, doorBlock, 8);
				setBlockAndMetadata(world, -5, 8, k12, GOTRegistry.asshaiTorch, 1);
				setBlockAndMetadata(world, 8, 6, k12, doorBlock, 2);
				setBlockAndMetadata(world, 8, 7, k12, doorBlock, 8);
				setBlockAndMetadata(world, 9, 8, k12, GOTRegistry.asshaiTorch, 2);
			}
			if (k2 == 0) {
				setBlockAndMetadata(world, -6, 6, k12, bedBlock, 1);
				setBlockAndMetadata(world, -5, 6, k12, bedBlock, 9);
				setBlockAndMetadata(world, -7, 6, k12, Blocks.chest, 2);
				setBlockAndMetadata(world, 10, 6, k12, bedBlock, 3);
				setBlockAndMetadata(world, 9, 6, k12, bedBlock, 11);
				setBlockAndMetadata(world, 11, 6, k12, Blocks.chest, 2);
			}
			if (k2 == 2) {
				setBlockAndMetadata(world, -5, 6, k12, plankStairBlock, 1);
				setBlockAndMetadata(world, -7, 6, k12, plankBlock, plankMeta);
				this.placeMug(world, random, -7, 7, k12, 3, GOTFoods.WESTEROS_DRINK);
				setBlockAndMetadata(world, 9, 6, k12, plankStairBlock, 0);
				setBlockAndMetadata(world, 11, 6, k12, plankBlock, plankMeta);
				this.placeMug(world, random, 11, 7, k12, 1, GOTFoods.WESTEROS_DRINK);
			}
			for (i12 = -7; i12 <= -4; ++i12) {
				setBlockAndMetadata(world, i12, 10, k12, wallBlock, wallMeta);
				setBlockAndMetadata(world, i12, 11, k12, wallBlock, wallMeta);
			}
			for (i12 = 8; i12 <= 11; ++i12) {
				setBlockAndMetadata(world, i12, 10, k12, wallBlock, wallMeta);
				setBlockAndMetadata(world, i12, 11, k12, wallBlock, wallMeta);
			}
		}
		for (i13 = 7; i13 <= 8; ++i13) {
			for (k1 = 10; k1 <= 11; ++k1) {
				if (i13 == 7 && k1 == 10) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i13, j1, k1, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i13, 9, k1, woodBeamBlock, woodBeamMeta | (i13 == 8 ? 8 : 4));
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, 8, 6, 10, doorBlock, 2);
		setBlockAndMetadata(world, 8, 7, 10, doorBlock, 8);
		setBlockAndMetadata(world, 9, 8, 10, GOTRegistry.asshaiTorch, 2);
		setBlockAndMetadata(world, 7, 8, 13, GOTRegistry.asshaiTorch, 2);
		for (i13 = 7; i13 <= 8; ++i13) {
			for (k1 = 12; k1 <= 13; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = 9; i13 <= 11; ++i13) {
			for (k1 = 10; k1 <= 11; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = 7; i13 <= 9; ++i13) {
			for (k1 = 12; k1 <= 14; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (i13 = 9; i13 <= 11; ++i13) {
			for (k1 = 10; k1 <= 12; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 11, 6, 11, bedBlock, 0);
		setBlockAndMetadata(world, 11, 6, 12, bedBlock, 8);
		setBlockAndMetadata(world, 11, 6, 10, Blocks.chest, 5);
		setBlockAndMetadata(world, 7, 6, 13, bedBlock, 2);
		setBlockAndMetadata(world, 7, 6, 12, bedBlock, 10);
		setBlockAndMetadata(world, 7, 6, 14, Blocks.chest, 4);
		setBlockAndMetadata(world, 9, 6, 14, plankBlock, plankMeta);
		this.placeMug(world, random, 9, 7, 14, 0, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, 10, 6, 13, plankBlock, plankMeta);
		this.placeMug(world, random, 10, 7, 13, 1, GOTFoods.WESTEROS_DRINK);
		for (i13 = 7; i13 <= 8; ++i13) {
			for (k1 = 3; k1 <= 4; ++k1) {
				if (i13 == 7 && k1 == 4) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i13, j1, k1, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i13, 9, k1, woodBeamBlock, woodBeamMeta | (i13 == 8 ? 8 : 4));
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, 8, 6, 4, doorBlock, 2);
		setBlockAndMetadata(world, 8, 7, 4, doorBlock, 8);
		setBlockAndMetadata(world, 9, 8, 4, GOTRegistry.asshaiTorch, 2);
		setBlockAndMetadata(world, 7, 8, 1, GOTRegistry.asshaiTorch, 2);
		for (i13 = 7; i13 <= 8; ++i13) {
			for (k1 = 1; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = 9; i13 <= 11; ++i13) {
			for (k1 = 3; k1 <= 4; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = 7; i13 <= 9; ++i13) {
			for (k1 = 0; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (i13 = 9; i13 <= 11; ++i13) {
			for (k1 = 2; k1 <= 4; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, 11, 6, 3, bedBlock, 2);
		setBlockAndMetadata(world, 11, 6, 2, bedBlock, 10);
		setBlockAndMetadata(world, 11, 6, 4, Blocks.chest, 5);
		setBlockAndMetadata(world, 7, 6, 1, bedBlock, 0);
		setBlockAndMetadata(world, 7, 6, 2, bedBlock, 8);
		setBlockAndMetadata(world, 7, 6, 0, Blocks.chest, 4);
		setBlockAndMetadata(world, 9, 6, 0, plankBlock, plankMeta);
		this.placeMug(world, random, 9, 7, 0, 2, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, 10, 6, 1, plankBlock, plankMeta);
		this.placeMug(world, random, 10, 7, 1, 1, GOTFoods.WESTEROS_DRINK);
		for (i13 = -4; i13 <= -3; ++i13) {
			for (k1 = 3; k1 <= 4; ++k1) {
				if (i13 == -3 && k1 == 4) {
					continue;
				}
				for (j1 = 6; j1 <= 8; ++j1) {
					setBlockAndMetadata(world, i13, j1, k1, wallBlock, wallMeta);
				}
				setBlockAndMetadata(world, i13, 9, k1, woodBeamBlock, woodBeamMeta | (i13 == -4 ? 8 : 4));
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		setBlockAndMetadata(world, -4, 6, 4, doorBlock, 0);
		setBlockAndMetadata(world, -4, 7, 4, doorBlock, 8);
		setBlockAndMetadata(world, -5, 8, 4, GOTRegistry.asshaiTorch, 1);
		setBlockAndMetadata(world, -3, 8, 1, GOTRegistry.asshaiTorch, 1);
		for (i13 = -4; i13 <= -3; ++i13) {
			for (k1 = 1; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = -7; i13 <= -5; ++i13) {
			for (k1 = 3; k1 <= 4; ++k1) {
				setBlockAndMetadata(world, i13, 10, k1, wallBlock, wallMeta);
			}
		}
		for (i13 = -5; i13 <= -3; ++i13) {
			for (k1 = 0; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (i13 = -7; i13 <= -5; ++i13) {
			for (k1 = 2; k1 <= 4; ++k1) {
				setBlockAndMetadata(world, i13, 9, k1, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -7, 6, 3, bedBlock, 2);
		setBlockAndMetadata(world, -7, 6, 2, bedBlock, 10);
		setBlockAndMetadata(world, -7, 6, 4, Blocks.chest, 4);
		setBlockAndMetadata(world, -3, 6, 1, bedBlock, 0);
		setBlockAndMetadata(world, -3, 6, 2, bedBlock, 8);
		setBlockAndMetadata(world, -3, 6, 0, Blocks.chest, 5);
		setBlockAndMetadata(world, -5, 6, 0, plankBlock, plankMeta);
		this.placeMug(world, random, -5, 7, 0, 2, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -6, 6, 1, plankBlock, plankMeta);
		this.placeMug(world, random, -6, 7, 1, 3, GOTFoods.WESTEROS_DRINK);
		for (i13 = -3; i13 <= 7; ++i13) {
			for (int k1321 : new int[] { 5, 9 }) {
				setBlockAndMetadata(world, i13, 11, k1321, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -1, 11, 7, GOTRegistry.chandelier, 12);
		setBlockAndMetadata(world, 7, 11, 7, GOTRegistry.chandelier, 12);
		GOTEntityAsshaiAlchemist bartender = new GOTEntityAsshaiAlchemist(world);
		spawnNPCAndSetHome(bartender, world, -4, 1, 7, 2);
		int men = 6 + random.nextInt(7);
		for (int l = 0; l < men; ++l) {
			GOTEntityAsshaiMan asshaiMan = new GOTEntityAsshaiMan(world);
			spawnNPCAndSetHome(asshaiMan, world, 2, 1, 7, 16);
		}
		return true;
	}

	public void placeMugOrPlate(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			this.placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.WESTEROS_DRINK);
		} else {
			placePlate(world, random, i, j, k, plateBlock, GOTFoods.WESTEROS);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
		tavernName = GOTNames.getTavernName(random);
		tavernNameSign = new String[] { "", tavernName[0], tavernName[1], "" };
	}
}
