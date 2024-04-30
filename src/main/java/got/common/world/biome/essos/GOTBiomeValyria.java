package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeValyria extends GOTBiomeLongSummer {
	public GOTBiomeValyria(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeColors.setGrass(new Color(0x808080));
		biomeColors.setFoliage(new Color(0x808080));
		biomeColors.setSky(new Color(0x808080));
		biomeColors.setClouds(new Color(0x808080));
		biomeColors.setFog(new Color(0x808080));
		biomeColors.setWater(new Color(0x808080));
		biomeColors.setFoggy(true);
		decorator.setTreesPerChunk(6);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VALYRIA, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterValyria;
	}
}