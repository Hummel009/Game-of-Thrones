package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosFrost extends GOTBiomeUlthosBase {
	public GOTBiomeUlthosFrost(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupPolarTrees(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_BLIZZARD, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);

		biomeAchievement = GOTAchievement.enterUlthosFrost;
		enableRiver = false;
		chanceToSpawnAnimals = 0.1f;
		roadBlock = GOTBezierType.PATH_SNOWY;
	}
}