package got.common.world.structure.essos.dothraki;

import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureDothrakiTent extends GOTStructureDothrakiBase {
	public GOTStructureDothrakiTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -6; i1 <= 6; ++i1) {
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
		for (int i1 = -6; i1 <= 6; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				if (i2 + Math.abs(k1) > 9) {
					continue;
				}
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_tent");
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TABLE", GOTBlocks.tableDothraki);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -3, 1, -2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, -2, bedBlock, 11);
		setBlockAndMetadata(world, -3, 1, 2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, 2, bedBlock, 11);
		placeWeaponRack(world, 0, 3, 5, 6, getRandomNomadWeapon(random));
		placeChest(world, random, 0, 1, 5, GOTBlocks.chestBasket, 2, GOTChestContents.DOTHRAKI);
		GOTEntityDothraki male = new GOTEntityDothraki(world);
		male.familyInfo.setMale(true);
		male.spawnRidingHorse = false;
		male.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
		GOTEntityDothraki female = new GOTEntityDothraki(world);
		female.familyInfo.setMale(false);
		female.spawnRidingHorse = false;
		female.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
		GOTEntityDothraki child = new GOTEntityDothraki(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.spawnRidingHorse = false;
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, -1, 16);
		return true;
	}
}
