package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosTaiga extends GOTBiomeUlthosForest {
	public GOTBiomeUlthosTaiga(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		fillerBlock = Blocks.snow;
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_BLIZZARD, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosTaiga;
	}
}