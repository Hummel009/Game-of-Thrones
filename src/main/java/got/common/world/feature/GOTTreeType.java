package got.common.world.feature;

import java.util.Random;

import got.common.database.GOTRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.feature.*;

public enum GOTTreeType {
	OAK(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			if (rand.nextInt(4) == 0) {
				return new GOTWorldGenGnarledOak(flag);
			}
			return new GOTWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_TALL(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			if (rand.nextInt(4) == 0) {
				return new GOTWorldGenGnarledOak(flag).setMinMaxHeight(6, 10);
			}
			return new GOTWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_TALLER(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 12, 16, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBigTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_GIANT(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenGiantTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), WEIRWOOD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(GOTRegistry.wood9, 2, GOTRegistry.leaves9, 2);
		}
	}), OAK_SWAMP(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenSwamp();
		}
	}), OAK_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDeadTrees(Blocks.log, 0);
		}
	}), OAK_DESERT(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDesertTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
		}
	}), OAK_SHRUB(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenShrub(Blocks.log, 0, Blocks.leaves, 0);
		}
	}), BIRCH(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			if (rand.nextInt(3) != 0) {
				return new GOTWorldGenAspen(flag).setBlocks(Blocks.log, 2, Blocks.leaves, 2).setMinMaxHeight(8, 16);
			}
			return new GOTWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, Blocks.leaves, 2);
		}
	}), BIRCH_TALL(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, Blocks.leaves, 2);
		}
	}), BIRCH_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBigTrees(flag, Blocks.log, 2, Blocks.leaves, 2);
		}
	}), BIRCH_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(Blocks.log, 2, Blocks.leaves, 2);
		}
	}), BIRCH_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDeadTrees(Blocks.log, 2);
		}
	}), SPRUCE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenTaiga2(flag);
		}
	}), ARAMANT(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 5, 9, GOTRegistry.wood2, 0, GOTRegistry.leaves2, 0);
		}
	}), SPRUCE_THIN(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenTaiga1();
		}
	}), SPRUCE_MEGA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenMegaPineTree(flag, true);
		}
	}), SPRUCE_MEGA_THIN(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenMegaPineTree(flag, false);
		}
	}), SPRUCE_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDeadTrees(Blocks.log, 1);
		}
	}), JUNGLE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenTropical(flag);
		}
	}), JUNGLE_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenTropical(flag).setExtraTrunkWidth(1);
		}
	}), JUNGLE_CLOUD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenTropical(flag).setExtraTrunkWidth(1);
		}
	}), JUNGLE_SHRUB(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenShrub(Blocks.log, 3, Blocks.leaves, 3);
		}
	}), ACACIA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenSavannaTree(flag);
		}
	}), ACACIA_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDeadTrees(Blocks.log2, 0);
		}
	}), DARK_OAK(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new WorldGenCanopyTree(flag);
		}
	}), DARK_OAK_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(Blocks.log2, 1, Blocks.leaves2, 1);
		}
	}), CATALPA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 6, 9, GOTRegistry.wood1, 1, GOTRegistry.leaves1, 1);
		}
	}), CATALPA_BOUGHS(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCatalpa(flag);
		}
	}), CATALPA_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(GOTRegistry.wood1, 1, GOTRegistry.leaves1, 1);
		}
	}), ULTHOS_OAK(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 4, 7, 0, true);
		}
	}), ULTHOS_OAK_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 12, 16, 1, true);
		}
	}), ULTHOS_OAK_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 4, 7, 0, true).setDead();
		}
	}), ULTHOS_RED_OAK(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 6, 9, 0, false).setRedOak();
		}
	}), ULTHOS_RED_OAK_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 12, 17, 1, false).setRedOak();
		}
	}), CHARRED(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCharredTrees();
		}
	}), APPLE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 4, 7, GOTRegistry.fruitWood, 0, GOTRegistry.fruitLeaves, 0);
		}
	}), PEAR(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 4, 5, GOTRegistry.fruitWood, 1, GOTRegistry.fruitLeaves, 1);
		}
	}), CHERRY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 4, 8, GOTRegistry.fruitWood, 2, GOTRegistry.fruitLeaves, 2);
		}
	}), MANGO(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			if (rand.nextInt(3) == 0) {
				return new GOTWorldGenOlive(flag).setBlocks(GOTRegistry.fruitWood, 3, GOTRegistry.fruitLeaves, 3);
			}
			return new GOTWorldGenDesertTrees(flag, GOTRegistry.fruitWood, 3, GOTRegistry.fruitLeaves, 3);
		}
	}), BEECH(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 5, 9, GOTRegistry.wood2, 1, GOTRegistry.leaves2, 1);
		}
	}), BEECH_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBigTrees(flag, GOTRegistry.wood2, 1, GOTRegistry.leaves2, 1);
		}
	}), BEECH_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(GOTRegistry.wood2, 1, GOTRegistry.leaves2, 1);
		}
	}), BEECH_DEAD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDeadTrees(GOTRegistry.wood2, 1);
		}
	}), HOLLY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenHolly(flag);
		}
	}), HOLLY_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenHolly(flag).setLarge();
		}
	}), BANANA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBanana(flag);
		}
	}), MAPLE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 4, 8, GOTRegistry.wood3, 0, GOTRegistry.leaves3, 0);
		}
	}), MAPLE_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBigTrees(flag, GOTRegistry.wood3, 0, GOTRegistry.leaves3, 0);
		}
	}), MAPLE_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(GOTRegistry.wood3, 0, GOTRegistry.leaves3, 0);
		}
	}), LARCH(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenLarch(flag);
		}
	}), DATE_PALM(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPalm(flag, GOTRegistry.wood3, 2, GOTRegistry.leaves3, 2).setMinMaxHeight(5, 8).setDates();
		}
	}), MANGROVE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenMangrove(flag);
		}
	}), CHESTNUT(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 5, 7, GOTRegistry.wood4, 0, GOTRegistry.leaves4, 0);
		}
	}), CHESTNUT_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBigTrees(flag, GOTRegistry.wood4, 0, GOTRegistry.leaves4, 0);
		}
	}), CHESTNUT_PARTY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPartyTrees(GOTRegistry.wood4, 0, GOTRegistry.leaves4, 0);
		}
	}), BAOBAB(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenBaobab(flag);
		}
	}), CEDAR(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCedar(flag);
		}
	}), CEDAR_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCedar(flag).setMinMaxHeight(15, 30);
		}
	}), FIR(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenFir(flag);
		}

	}), PINE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPine(flag);
		}
	}), IBBEN_PINE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenIbbenPine(flag);
		}
	}), FOTINIA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenFotinia(flag);
		}
	}), PINE_SHRUB(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenShrub(GOTRegistry.wood5, 0, GOTRegistry.leaves5, 0);
		}
	}), LEMON(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 6, 9, GOTRegistry.wood5, 1, GOTRegistry.leaves5, 1);
		}
	}), ORANGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 6, 9, GOTRegistry.wood5, 2, GOTRegistry.leaves5, 2);
		}
	}), LIME(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 6, 9, GOTRegistry.wood5, 3, GOTRegistry.leaves5, 3);
		}
	}), MAHOGANY(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCedar(flag).setBlocks(GOTRegistry.wood6, 0, GOTRegistry.leaves6, 0).setHangingLeaves();
		}
	}), WILLOW(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenWillow(flag);
		}
	}), WILLOW_WATER(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenWillow(flag).setNeedsWater();
		}
	}), CYPRESS(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCypress(flag);
		}
	}), CYPRESS_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenCypress(flag).setLarge();
		}
	}), OLIVE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenOlive(flag);
		}
	}), OLIVE_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenOlive(flag).setMinMaxHeight(5, 8).setExtraTrunkWidth(1);
		}
	}), ASPEN(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenAspen(flag);
		}
	}), ASPEN_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenAspen(flag).setExtraTrunkWidth(1).setMinMaxHeight(14, 25);
		}
	}), ULTHOS_GREEN_OAK(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 4, 7, 0, false).setGreenOak();
		}
	}), ULTHOS_GREEN_OAK_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 12, 16, 1, false).setGreenOak();
		}
	}), ULTHOS_GREEN_OAK_EXTREME(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenUlthosOak(flag, 25, 45, 2, false).setGreenOak();
		}
	}), ALMOND(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenAlmond(flag);
		}
	}), PLUM(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenSimpleTrees(flag, 4, 6, GOTRegistry.wood8, 0, GOTRegistry.leaves8, 0);
		}
	}), REDWOOD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenRedwood(flag);
		}
	}), REDWOOD_2(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenRedwood(flag).setExtraTrunkWidth(1);
		}
	}), REDWOOD_3(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenRedwood(flag).setTrunkWidth(1);
		}
	}), REDWOOD_4(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenRedwood(flag).setTrunkWidth(1).setExtraTrunkWidth(1);
		}
	}), REDWOOD_5(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenRedwood(flag).setTrunkWidth(2);
		}
	}), TROPICAL(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenTropical(flag).setExtraTrunkWidth(1);
		}
	}), POMEGRANATE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			if (rand.nextInt(3) == 0) {
				return new GOTWorldGenOlive(flag).setBlocks(GOTRegistry.wood8, 2, GOTRegistry.leaves8, 2);
			}
			return new GOTWorldGenDesertTrees(flag, GOTRegistry.wood8, 2, GOTRegistry.leaves8, 2);
		}
	}), PALM(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenPalm(flag, GOTRegistry.wood8, 3, GOTRegistry.leaves8, 3).setMinMaxHeight(6, 11);
		}
	}), DRAGONBLOOD(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDragonblood(flag, 3, 7, 0);
		}
	}), DRAGONBLOOD_LARGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDragonblood(flag, 6, 10, 1);
		}
	}), DRAGONBLOOD_HUGE(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenDragonblood(flag, 8, 16, 2);
		}
	}), KANUKA(new ITreeFactory() {

		@Override
		public WorldGenAbstractTree createTree(boolean flag, Random rand) {
			return new GOTWorldGenKanuka(flag);
		}
	}), NULL(null);

	public ITreeFactory treeFactory;

	GOTTreeType(ITreeFactory factory) {
		treeFactory = factory;
	}

	public WorldGenAbstractTree create(boolean flag, Random rand) {
		return treeFactory.createTree(flag, rand);
	}

	public static interface ITreeFactory {
		WorldGenAbstractTree createTree(boolean var1, Random var2);
	}

	public static class WeightedTreeType extends WeightedRandom.Item {
		public GOTTreeType treeType;

		public WeightedTreeType(GOTTreeType tree, int i) {
			super(i);
			treeType = tree;
		}
	}

}
