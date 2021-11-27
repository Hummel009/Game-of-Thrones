package got.common.world.structure.essos.jogos;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.jogos.GOTEntityJogos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureJogosTentLarge extends GOTStructureJogosBase {
	public GOTStructureJogosTentLarge(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -10; i1 <= 10; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -10; i1 <= 10; ++i1) {
			for (int k1 = -7; k1 <= 7; ++k1) {
				Math.abs(i1);
				Math.abs(k1);
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_tent_large");
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("CARPET2", carpet2Block, carpet2Meta);
		associateBlockAlias("TABLE", GOTRegistry.tableJogos);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -3, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -3, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, -4, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -4, 1, 5, bedBlock, 8);
		placeWeaponRack(world, 0, 3, 6, 6, getRandomNomadWeapon(random));
		this.placeChest(world, random, -4, 1, -5, GOTRegistry.chestBasket, 3, GOTChestContents.NOMAD);
		GOTEntityJogos male = new GOTEntityJogos(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityJogos female = new GOTEntityJogos(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityJogos child = new GOTEntityJogos(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}
}
