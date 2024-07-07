package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeSothoryosMangrove extends GOTBiomeSothoryosBase implements GOTBiome.Marshes {
	public GOTBiomeSothoryosMangrove(int i, boolean major) {
		super(i, major);
		preseter.setupMangroveView();
		preseter.setupMangroveFlora();
		preseter.setupMangroveFauna();
		preseter.setupMangroveTrees();

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosMangrove;
	}
}