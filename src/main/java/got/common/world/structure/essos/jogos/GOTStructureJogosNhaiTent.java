package got.common.world.structure.essos.jogos;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.entity.essos.jogos.GOTEntityJogosNhaiMan;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureJogosNhaiTent extends GOTStructureJogosNhaiBase {
	public GOTStructureJogosNhaiTent(boolean flag) {
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
		associateBlockAlias("TABLE", GOTBlocks.tableJogosNhai);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -3, 1, -2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, -2, bedBlock, 11);
		setBlockAndMetadata(world, -3, 1, 2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, 2, bedBlock, 11);
		placeWeaponRack(world, 0, 3, 5, 6, getRandWeaponItem(random));
		placeChest(world, random, 0, 1, 5, GOTBlocks.chestBasket, 2, GOTChestContents.JOGOS_NHAI);
		GOTEntityNPC male = new GOTEntityJogosNhaiMan(world);
		male.getFamilyInfo().setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		male.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
		GOTEntityNPC female = new GOTEntityJogosNhaiMan(world);
		female.getFamilyInfo().setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		female.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
		GOTEntityNPC child = new GOTEntityJogosNhaiMan(world);
		child.getFamilyInfo().setMale(random.nextBoolean());
		child.getFamilyInfo().setChild();
		child.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(child, world, 0, 1, -1, 16);
		return true;
	}
}