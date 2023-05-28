/*
 * Decompiled with CFR 0.148.
 *
 * Could not onInit the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLeaves
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package got.common.world.structure.essos.mossovy;

import got.common.database.*;
import got.common.entity.essos.mossovy.GOTEntityMossovyBartender;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.westeros.GOTEntityWesterosScrapTrader;
import got.common.entity.westeros.GOTEntityWesterosThief;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyInn extends GOTStructureMossovyBase {
	public String[] tavernName;
	public String[] tavernNameSign;

	public GOTStructureMossovyInn(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 5, -2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k1 = -7; k1 <= 7; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k1 = -5; k1 <= 6; ++k1) {
				for (j1 = 1; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("mossovy_inn");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockAlias("BRICK2_STAIR", brick2StairBlock);
		associateBlockMetaAlias("BRICK2_WALL", brick2WallBlock, brick2WallMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		setBlockAliasChance("LEAF", 0.6f);
		generateStrScan(world, random, 0, 0, 0);
		placeRandomFlowerPot(world, random, -4, 2, -3);
		placeRandomFlowerPot(world, random, -3, 6, 0);
		plantFlower(world, random, -8, 6, 0);
		plantFlower(world, random, 8, 6, 0);
		plantFlower(world, random, -8, 6, 1);
		plantFlower(world, random, 8, 6, 1);
		placeChest(world, random, -5, 1, -3, 3, GOTChestContents.MOSSOVY);
		setBlockAndMetadata(world, -6, 2, -3, GOTStructureMossovyBase.getRandomPieBlock(random), 0);
		placeBarrel(world, random, -6, 2, 1, 4, GOTFoods.WESTEROS_DRINK);
		placeBarrel(world, random, -4, 2, 4, 2, GOTFoods.WESTEROS_DRINK);
		placeFoodOrDrink(world, random, 6, 2, -3);
		placeFoodOrDrink(world, random, 5, 2, -3);
		placeFoodOrDrink(world, random, 1, 2, -3);
		placeFoodOrDrink(world, random, 6, 2, -2);
		placeFoodOrDrink(world, random, 5, 2, -2);
		placeFoodOrDrink(world, random, 0, 2, 0);
		placeFoodOrDrink(world, random, -6, 2, 0);
		placeFoodOrDrink(world, random, -4, 2, 0);
		placeFoodOrDrink(world, random, -4, 2, 1);
		placeFoodOrDrink(world, random, 0, 2, 1);
		placeFoodOrDrink(world, random, -4, 2, 3);
		placeFoodOrDrink(world, random, 6, 2, 4);
		placeFoodOrDrink(world, random, 2, 2, 4);
		placeFoodOrDrink(world, random, 6, 6, -3);
		placeFoodOrDrink(world, random, 5, 6, -3);
		placeFoodOrDrink(world, random, 0, 6, -3);
		placeFoodOrDrink(world, random, -5, 6, -3);
		placeFoodOrDrink(world, random, -6, 6, -3);
		placeFoodOrDrink(world, random, 5, 6, 4);
		placeFoodOrDrink(world, random, -5, 6, 4);
		placeWeaponRack(world, -3, 7, -1, 5, getRandomBreeWeapon(random));
		placeWeaponRack(world, 3, 7, -1, 7, getRandomBreeWeapon(random));
		placeWeaponRack(world, -3, 7, 2, 5, getRandomBreeWeapon(random));
		placeWeaponRack(world, 3, 7, 2, 7, getRandomBreeWeapon(random));
		setBlockAndMetadata(world, 5, 5, 0, bedBlock, 1);
		setBlockAndMetadata(world, 6, 5, 0, bedBlock, 9);
		setBlockAndMetadata(world, -5, 5, 0, bedBlock, 3);
		setBlockAndMetadata(world, -6, 5, 0, bedBlock, 11);
		setBlockAndMetadata(world, 5, 5, 2, bedBlock, 1);
		setBlockAndMetadata(world, 6, 5, 2, bedBlock, 9);
		setBlockAndMetadata(world, -5, 5, 2, bedBlock, 3);
		setBlockAndMetadata(world, -6, 5, 2, bedBlock, 11);
		setBlockAndMetadata(world, 5, 8, -2, bedBlock, 2);
		setBlockAndMetadata(world, 5, 8, -3, bedBlock, 10);
		setBlockAndMetadata(world, -5, 8, -2, bedBlock, 2);
		setBlockAndMetadata(world, -5, 8, -3, bedBlock, 10);
		placeSign(world, -2, 4, -7, Blocks.wall_sign, 2, tavernNameSign);
		placeSign(world, -1, 4, -6, Blocks.wall_sign, 4, tavernNameSign);
		placeSign(world, -3, 4, -6, Blocks.wall_sign, 5, tavernNameSign);

		GOTEntityMossovyBartender innkeeper = new GOTEntityMossovyBartender(world);
		spawnNPCAndSetHome(innkeeper, world, -5, 1, 0, 4);
		int men = 8 + random.nextInt(6);
		for (int l = 0; l < men; ++l) {
			GOTEntityHumanBase mossovylander = new GOTEntityMossovyMan(world);
			spawnNPCAndSetHome(mossovylander, world, -2, 1, 0, 16);
		}
		spawnNPCAndSetHome(new GOTEntityWesterosThief(world), world, -2, 1, 0, 16);
		spawnNPCAndSetHome(new GOTEntityWesterosScrapTrader(world), world, -2, 1, 0, 16);
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.WESTEROS_DRINK);
			} else {
				Block[] plates = {GOTBlocks.plateBlock, GOTBlocks.ceramicPlateBlock, GOTBlocks.woodPlateBlock};
				Block plateBlock = plates[random.nextInt(plates.length)];
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.WESTEROS);
				}
			}
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = Blocks.bed;
		tavernName = GOTNames.getTavernName(random);
		tavernNameSign = new String[]{"", tavernName[0], tavernName[1], ""};
	}
}
