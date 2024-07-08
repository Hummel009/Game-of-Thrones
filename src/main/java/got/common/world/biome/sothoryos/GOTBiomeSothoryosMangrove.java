package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeSothoryosMangrove extends GOTBiomeSothoryosBase implements GOTBiome.Marshes {
	public GOTBiomeSothoryosMangrove(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.mudGrass;
		fillerBlock = GOTBlocks.mud;

		preseter.setupJungleView();
		preseter.setupJungleFlora();
		preseter.setupJungleFauna();

		variantChance = 1.0f;
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);

		spawnableWaterCreatureList.clear();

		decorator.addTree(GOTTreeType.MANGROVE, 500);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosMangrove;
	}
}