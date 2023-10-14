package got.common.world.feature;

import got.common.database.GOTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public enum GOTTreeType {
	OAK((flag, rand) -> {
		if (rand.nextInt(4) == 0) {
			return new GOTWorldGenGnarledOak(flag);
		}
		return new GOTWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, Blocks.leaves, 0);
	}), OAK_TALL((flag, rand) -> {
		if (rand.nextInt(4) == 0) {
			return new GOTWorldGenGnarledOak(flag).setMinMaxHeight(6, 10);
		}
		return new GOTWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, Blocks.leaves, 0);
	}), OAK_TALLER((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 12, 16, Blocks.log, 0, Blocks.leaves, 0)), OAK_LARGE((flag, rand) -> new GOTWorldGenBigTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), OAK_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(Blocks.log, 0, Blocks.leaves, 0)), OAK_GIANT((flag, rand) -> new GOTWorldGenGiantTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), WEIRWOOD((flag, rand) -> new GOTWorldGenPartyTrees(GOTBlocks.wood9, 2, GOTBlocks.leaves9, 2)), OAK_SWAMP((flag, rand) -> new WorldGenSwamp()), OAK_DEAD((flag, rand) -> new GOTWorldGenDeadTrees(Blocks.log, 0)), OAK_DESERT((flag, rand) -> new GOTWorldGenDesertTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), BIRCH((flag, rand) -> {
		if (rand.nextInt(3) != 0) {
			return new GOTWorldGenAspen(flag).setBlocks(Blocks.log, 2, Blocks.leaves, 2).setMinMaxHeight(8, 16);
		}
		return new GOTWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, Blocks.leaves, 2);
	}), BIRCH_TALL((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, Blocks.leaves, 2)), BIRCH_LARGE((flag, rand) -> new GOTWorldGenBigTrees(flag, Blocks.log, 2, Blocks.leaves, 2)), BIRCH_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(Blocks.log, 2, Blocks.leaves, 2)), BIRCH_DEAD((flag, rand) -> new GOTWorldGenDeadTrees(Blocks.log, 2)), SPRUCE((flag, rand) -> new WorldGenTaiga2(flag)), ARAMANT((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 5, 9, GOTBlocks.wood2, 0, GOTBlocks.leaves2, 0)), SPRUCE_THIN((flag, rand) -> new WorldGenTaiga1()), SPRUCE_MEGA((flag, rand) -> new WorldGenMegaPineTree(flag, true)), SPRUCE_MEGA_THIN((flag, rand) -> new WorldGenMegaPineTree(flag, false)), SPRUCE_DEAD((flag, rand) -> new GOTWorldGenDeadTrees(Blocks.log, 1)), JUNGLE((flag, rand) -> new GOTWorldGenTropical(flag)), JUNGLE_LARGE((flag, rand) -> new GOTWorldGenTropical(flag).setExtraTrunkWidth(1)), JUNGLE_SHRUB((flag, rand) -> new GOTWorldGenShrub(Blocks.log, 3, Blocks.leaves, 3)), ACACIA((flag, rand) -> new WorldGenSavannaTree(flag)), ACACIA_DEAD((flag, rand) -> new GOTWorldGenDeadTrees(Blocks.log2, 0)), DARK_OAK((flag, rand) -> new WorldGenCanopyTree(flag)), DARK_OAK_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(Blocks.log2, 1, Blocks.leaves2, 1)), CATALPA((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 6, 9, GOTBlocks.wood1, 1, GOTBlocks.leaves1, 1)), CATALPA_BOUGHS((flag, rand) -> new GOTWorldGenCatalpa(flag)), CATALPA_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(GOTBlocks.wood1, 1, GOTBlocks.leaves1, 1)), ULTHOS_OAK((flag, rand) -> new GOTWorldGenUlthosOak(flag, 4, 7, 0, true)), ULTHOS_OAK_LARGE((flag, rand) -> new GOTWorldGenUlthosOak(flag, 12, 16, 1, true)), ULTHOS_OAK_DEAD((flag, rand) -> new GOTWorldGenUlthosOak(flag, 4, 7, 0, true).setDead()), ULTHOS_RED_OAK((flag, rand) -> new GOTWorldGenUlthosOak(flag, 6, 9, 0, false).setRedOak()), ULTHOS_RED_OAK_LARGE((flag, rand) -> new GOTWorldGenUlthosOak(flag, 12, 17, 1, false).setRedOak()), CHARRED((flag, rand) -> new GOTWorldGenCharredTrees()), APPLE((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 4, 7, GOTBlocks.fruitWood, 0, GOTBlocks.fruitLeaves, 0)), PEAR((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 4, 5, GOTBlocks.fruitWood, 1, GOTBlocks.fruitLeaves, 1)), CHERRY((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 4, 8, GOTBlocks.fruitWood, 2, GOTBlocks.fruitLeaves, 2)), MANGO((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new GOTWorldGenOlive(flag).setBlocks(GOTBlocks.fruitWood, 3, GOTBlocks.fruitLeaves, 3);
		}
		return new GOTWorldGenDesertTrees(flag, GOTBlocks.fruitWood, 3, GOTBlocks.fruitLeaves, 3);
	}), BEECH((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 5, 9, GOTBlocks.wood2, 1, GOTBlocks.leaves2, 1)), BEECH_LARGE((flag, rand) -> new GOTWorldGenBigTrees(flag, GOTBlocks.wood2, 1, GOTBlocks.leaves2, 1)), BEECH_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(GOTBlocks.wood2, 1, GOTBlocks.leaves2, 1)), BEECH_DEAD((flag, rand) -> new GOTWorldGenDeadTrees(GOTBlocks.wood2, 1)), HOLLY((flag, rand) -> new GOTWorldGenHolly(flag)), HOLLY_LARGE((flag, rand) -> new GOTWorldGenHolly(flag).setLarge()), BANANA((flag, rand) -> new GOTWorldGenBanana(flag)), MAPLE((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 4, 8, GOTBlocks.wood3, 0, GOTBlocks.leaves3, 0)), MAPLE_LARGE((flag, rand) -> new GOTWorldGenBigTrees(flag, GOTBlocks.wood3, 0, GOTBlocks.leaves3, 0)), MAPLE_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(GOTBlocks.wood3, 0, GOTBlocks.leaves3, 0)), LARCH((flag, rand) -> new GOTWorldGenLarch(flag)), DATE_PALM((flag, rand) -> new GOTWorldGenPalm(flag, GOTBlocks.wood3, 2, GOTBlocks.leaves3, 2).setMinMaxHeight(5, 8).setDates()), MANGROVE((flag, rand) -> new GOTWorldGenMangrove(flag)), CHESTNUT((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 5, 7, GOTBlocks.wood4, 0, GOTBlocks.leaves4, 0)), CHESTNUT_LARGE((flag, rand) -> new GOTWorldGenBigTrees(flag, GOTBlocks.wood4, 0, GOTBlocks.leaves4, 0)), CHESTNUT_PARTY((flag, rand) -> new GOTWorldGenPartyTrees(GOTBlocks.wood4, 0, GOTBlocks.leaves4, 0)), BAOBAB((flag, rand) -> new GOTWorldGenBaobab(flag)), CEDAR((flag, rand) -> new GOTWorldGenCedar(flag)), CEDAR_LARGE((flag, rand) -> new GOTWorldGenCedar(flag).setMinMaxHeight(15, 30)), FIR((flag, rand) -> new GOTWorldGenFir(flag)), PINE((flag, rand) -> new GOTWorldGenPine(flag)), IBBEN_PINE((flag, rand) -> new GOTWorldGenIbbenPine(flag)), FOTINIA((flag, rand) -> new GOTWorldGenFotinia(flag)), LEMON((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 6, 9, GOTBlocks.wood5, 1, GOTBlocks.leaves5, 1)), ORANGE((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 6, 9, GOTBlocks.wood5, 2, GOTBlocks.leaves5, 2)), LIME((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 6, 9, GOTBlocks.wood5, 3, GOTBlocks.leaves5, 3)), MAHOGANY((flag, rand) -> new GOTWorldGenCedar(flag).setBlocks(GOTBlocks.wood6, 0, GOTBlocks.leaves6, 0).setHangingLeaves()), WILLOW((flag, rand) -> new GOTWorldGenWillow(flag)), WILLOW_WATER((flag, rand) -> new GOTWorldGenWillow(flag).setNeedsWater()), CYPRESS((flag, rand) -> new GOTWorldGenCypress(flag)), CYPRESS_LARGE((flag, rand) -> new GOTWorldGenCypress(flag).setLarge()), OLIVE((flag, rand) -> new GOTWorldGenOlive(flag)), OLIVE_LARGE((flag, rand) -> new GOTWorldGenOlive(flag).setMinMaxHeight(5, 8).setExtraTrunkWidth(1)), ASPEN((flag, rand) -> new GOTWorldGenAspen(flag)), ASPEN_LARGE((flag, rand) -> new GOTWorldGenAspen(flag).setExtraTrunkWidth(1).setMinMaxHeight(14, 25)), ULTHOS_GREEN_OAK((flag, rand) -> new GOTWorldGenUlthosOak(flag, 4, 7, 0, false).setGreenOak()), ULTHOS_GREEN_OAK_LARGE((flag, rand) -> new GOTWorldGenUlthosOak(flag, 12, 16, 1, false).setGreenOak()), ALMOND((flag, rand) -> new GOTWorldGenAlmond(flag)), PLUM((flag, rand) -> new GOTWorldGenSimpleTrees(flag, 4, 6, GOTBlocks.wood8, 0, GOTBlocks.leaves8, 0)), REDWOOD((flag, rand) -> new GOTWorldGenRedwood(flag)), REDWOOD_2((flag, rand) -> new GOTWorldGenRedwood(flag).setExtraTrunkWidth(1)), REDWOOD_3((flag, rand) -> new GOTWorldGenRedwood(flag).setTrunkWidth(1)), REDWOOD_4((flag, rand) -> new GOTWorldGenRedwood(flag).setTrunkWidth(1).setExtraTrunkWidth(1)), REDWOOD_5((flag, rand) -> new GOTWorldGenRedwood(flag).setTrunkWidth(2)), POMEGRANATE((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new GOTWorldGenOlive(flag).setBlocks(GOTBlocks.wood8, 2, GOTBlocks.leaves8, 2);
		}
		return new GOTWorldGenDesertTrees(flag, GOTBlocks.wood8, 2, GOTBlocks.leaves8, 2);
	}), PALM((flag, rand) -> new GOTWorldGenPalm(flag, GOTBlocks.wood8, 3, GOTBlocks.leaves8, 3).setMinMaxHeight(6, 11)), DRAGONBLOOD((flag, rand) -> new GOTWorldGenDragonblood(flag, 3, 7, 0)), DRAGONBLOOD_LARGE((flag, rand) -> new GOTWorldGenDragonblood(flag, 6, 10, 1)), KANUKA((flag, rand) -> new GOTWorldGenKanuka(flag));

	public ITreeFactory treeFactory;

	GOTTreeType(ITreeFactory factory) {
		treeFactory = factory;
	}

	public WorldGenAbstractTree create(boolean flag, Random rand) {
		return treeFactory.createTree(flag, rand);
	}

	public interface ITreeFactory {
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
