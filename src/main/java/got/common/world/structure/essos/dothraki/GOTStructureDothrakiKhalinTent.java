package got.common.world.structure.essos.dothraki;

import java.util.*;

import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.essos.dothraki.GOTEntityDothrakiKhalin;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureDothrakiKhalinTent extends GOTStructureDothrakiBase {
	public static Class[] stalls = { Mason.class, Brewer.class, Miner.class, Armourer.class };

	public GOTStructureDothrakiKhalinTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -14; i1 <= 14; ++i1) {
				for (int k1 = -6; k1 <= 8; ++k1) {
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
		for (int i1 = -14; i1 <= 14; ++i1) {
			for (int k1 = -6; k1 <= 8; ++k1) {
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
		loadStrScan("nomad_bazaar");
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("CARPET2", carpet2Block, carpet2Meta);
		generateStrScan(world, random, 0, 1, 0);
		this.placeSkull(world, random, -8, 2, -4);
		this.placeBarrel(world, random, 7, 2, -4, 3, GOTFoods.NOMAD_DRINK);
		this.placeBarrel(world, random, 8, 2, -4, 3, GOTFoods.NOMAD_DRINK);
		placeAnimalJar(world, -7, 2, -4, GOTRegistry.butterflyJar, 0, new GOTEntityButterfly(world));
		placeAnimalJar(world, 9, 1, 5, GOTRegistry.birdCageWood, 0, null);
		placeAnimalJar(world, 4, 3, 2, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		placeAnimalJar(world, -4, 4, 5, GOTRegistry.birdCage, 2, new GOTEntityBird(world));
		placeAnimalJar(world, -4, 5, -1, GOTRegistry.birdCage, 0, new GOTEntityBird(world));
		placeAnimalJar(world, 0, 5, 5, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
		while (stallClasses.size() > 3) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			GOTStructureBase stall0 = (GOTStructureBase) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall1 = (GOTStructureBase) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall2 = (GOTStructureBase) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -4, 1, 6, 0);
			generateSubstructure(stall1, world, random, 0, 1, 6, 0);
			generateSubstructure(stall2, world, random, 4, 1, 6, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static class Armourer extends GOTStructureBase {
		public Armourer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.anvil, 1);
			placeWeaponRack(world, -1, 2, -2, 2, new GOTStructureDothrakiKhalinTent(false).getRandomNomadWeapon(random));
			GOTEntityDothrakiKhalin trader = new GOTEntityDothrakiKhalin(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Brewer extends GOTStructureBase {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.barrel, 2);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, GOTRegistry.barrel, 2);
			this.placeMug(world, random, -1, 2, -2, 0, GOTFoods.NOMAD_DRINK);
			this.placeMug(world, random, 1, 2, -2, 0, GOTFoods.NOMAD_DRINK);
			GOTEntityDothrakiKhalin trader = new GOTEntityDothrakiKhalin(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Mason extends GOTStructureBase {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.redSandstone, 0);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.redSandstone, 0);
			setBlockAndMetadata(world, -1, 3, 1, GOTRegistry.redSandstone, 0);
			setBlockAndMetadata(world, -1, 1, 0, Blocks.sandstone, 0);
			setBlockAndMetadata(world, -1, 2, 0, Blocks.sandstone, 0);
			setBlockAndMetadata(world, 0, 1, 1, GOTRegistry.brick1, 15);
			setBlockAndMetadata(world, 0, 2, 1, GOTRegistry.slabSingle4, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.brick1, 15);
			setBlockAndMetadata(world, 1, 2, 1, GOTRegistry.slabSingle4, 0);
			placeWeaponRack(world, 1, 3, 1, 6, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityDothrakiKhalin trader = new GOTEntityDothrakiKhalin(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Miner extends GOTStructureBase {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, 0, 1, 1, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, 1, 2, 1, Blocks.lapis_ore, 0);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.lapis_ore, 0);
			placeWeaponRack(world, 0, 2, 1, 6, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityDothrakiKhalin trader = new GOTEntityDothrakiKhalin(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
