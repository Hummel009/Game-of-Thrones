package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeSothoryosJungle extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosJungle(int i, boolean major) {
		super(i, major);
		topBlock = GOTBlocks.mudGrass;
		fillerBlock = GOTBlocks.mud;

		preseter.setupJungleView();
		preseter.setupJungleFlora();
		preseter.setupJungleFauna();
		preseter.setupJungleTrees();

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosJungle;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SOTHORYOS;
	}
}