package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosTraining extends GOTStructureEssosBase {
	public GOTStructureEssosTraining(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -7; i1 <= 7; ++i1) {
			for (int k1 = -5; k1 <= 5; ++k1) {
				for (int j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_training");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		if (hasSandstone()) {
			addBlockMetaAliasOption("GROUND", Blocks.dirt, 1);
			addBlockMetaAliasOption("GROUND", GOTBlocks.pillar1, 5);
			addBlockMetaAliasOption("GROUND", GOTBlocks.dirtPath, 0);
			addBlockMetaAliasOption("GROUND", Blocks.sand, 0);
			addBlockMetaAliasOption("GROUND", Blocks.sandstone, 0);
			addBlockMetaAliasOption("GROUND", GOTBlocks.brick1, 15);
			addBlockMetaAliasOption("GROUND", GOTBlocks.brick3, 11);
		} else {
			addBlockMetaAliasOption("GROUND", Blocks.dirt, 1);
			addBlockMetaAliasOption("GROUND", Blocks.gravel, 0);
			addBlockMetaAliasOption("GROUND", GOTBlocks.dirtPath, 0);
		}
		generateStrScan(world, random, 0, 0, 0);
		placeWeaponRack(world, -5, 2, -4, 2, getRandWeaponItem(random));
		placeWeaponRack(world, 5, 2, -4, 2, getRandWeaponItem(random));
		placeSkull(world, 0, 3, 2, 0);
		placeSkull(world, -5, 3, 0, 12);
		placeSkull(world, 5, 3, 0, 4);
		return true;
	}
}