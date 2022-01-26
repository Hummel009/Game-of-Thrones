package got.common.world.structure.other;

import java.util.Random;

import got.common.database.*;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GOTStructureTentBase extends GOTStructureBase {
	public Block tentBlock;
	public int tentMeta;
	public Block fenceBlock;
	public int fenceMeta;
	public Block tableBlock;
	public GOTChestContents chestContents;
	public boolean isCaptain = false;

	public GOTStructureTentBase(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		BiomeGenBase biome;
		int k1;
		int j1;
		int i1;
		this.setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
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
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					int randomGround;
					biome = getBiome(world, i1, k1);
					if (biome instanceof GOTBiomeShadowLand) {
						randomGround = random.nextInt(3);
						switch (randomGround) {
						case 0:
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.rock, 0);
							break;
						case 1:
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.asshaiDirt, 0);
							break;
						case 2:
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.basaltGravel, 0);
							break;
						default:
							break;
						}
					} else {
						randomGround = random.nextInt(3);
						switch (randomGround) {
						case 0:
							if (j1 == 0) {
								setBiomeTop(world, i1, j1, k1);
							} else {
								setBiomeFiller(world, i1, j1, k1);
							}
							break;
						case 1:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
							break;
						case 2:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
							break;
						default:
							break;
						}
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (int k12 = -3; k12 <= 3; ++k12) {
			for (int i12 : new int[] { -2, 2 }) {
				for (int j12 = 1; j12 <= 2; ++j12) {
					setBlockAndMetadata(world, i12, j12, k12, tentBlock, tentMeta);
				}
				setGrassToDirt(world, i12, 0, k12);
			}
			setBlockAndMetadata(world, -1, 3, k12, tentBlock, tentMeta);
			setBlockAndMetadata(world, 1, 3, k12, tentBlock, tentMeta);
			setBlockAndMetadata(world, 0, 4, k12, tentBlock, tentMeta);
		}
		for (int j13 = 1; j13 <= 3; ++j13) {
			setBlockAndMetadata(world, 0, j13, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, j13, 3, fenceBlock, fenceMeta);
		}
		if (isCaptain) {
			placeBigTorch(world, -1, 1, -3);
			placeBigTorch(world, 1, 1, -3);
			placeBigTorch(world, -1, 1, 3);
			placeBigTorch(world, 1, 1, 3);
		} else {
			setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
			setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
			setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
			setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
		}
		if (random.nextBoolean()) {
			if (isCaptain) {
				setBlockAndMetadata(world, -1, 1, 0, GOTRegistry.alloyForge, 4);
				setGrassToDirt(world, -1, 0, 0);
				setBlockAndMetadata(world, -1, 1, -1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, -1, 1, 1, fenceBlock, fenceMeta);
			} else {
				setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
				setGrassToDirt(world, -1, 0, -1);
				setBlockAndMetadata(world, -1, 1, 1, tableBlock, 0);
				setGrassToDirt(world, -1, 0, 1);
			}
		} else if (isCaptain) {
			setBlockAndMetadata(world, 1, 1, 0, GOTRegistry.alloyForge, 5);
			setGrassToDirt(world, 1, 0, 0);
			setBlockAndMetadata(world, 1, 1, -1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, 1, 1, fenceBlock, fenceMeta);
		} else {
			this.placeChest(world, random, 1, 1, 0, 5, chestContents);
			setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
			setGrassToDirt(world, 1, 0, -1);
			setBlockAndMetadata(world, 1, 1, 1, tableBlock, 0);
			setGrassToDirt(world, 1, 0, 1);
		}
		return true;
	}
}
