package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.feature.GOTWorldGenDoubleFlower;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeSothoryosSavannah extends GOTBiomeSothoryosBase {
	public GOTBiomeSothoryosSavannah(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.addSettlement(new GOTStructureSothoryosSettlement(this, 1.0f));

		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		GOTWorldGenDoubleFlower doubleFlowerGen = new GOTWorldGenDoubleFlower();
		if (random.nextInt(5) == 0) {
			doubleFlowerGen.setFlowerType(3);
		} else {
			doubleFlowerGen.setFlowerType(2);
		}
		return doubleFlowerGen;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosSavannah;
	}
}