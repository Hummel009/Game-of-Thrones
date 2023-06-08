package got.common.world.structure.essos.common;

import got.common.entity.animal.GOTEntityCamel;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosPasture extends GOTStructureEssosBase {
	public GOTStructureEssosPasture(boolean flag) {
		super(flag);
	}

	public static EntityAnimal getRandomAnimal(World world, Random random) {
		int animal = random.nextInt(5);
		switch (animal) {
			case 0:
				return new EntityCow(world);
			case 1:
				return new EntityPig(world);
			case 2:
				return new EntitySheep(world);
			case 3:
				return new EntityChicken(world);
			default:
				return new GOTEntityCamel(world);
		}
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -6; k1 <= 6; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 6) {
					continue;
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_pasture");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		generateStrScan(world, random, 0, 0, 0);
		int animals = 2 + random.nextInt(4);
		for (int l = 0; l < animals; ++l) {
			EntityAnimal animal = getRandomAnimal(world, random);
			spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
			assert animal != null;
			animal.detachHome();
		}
		return true;
	}
}
