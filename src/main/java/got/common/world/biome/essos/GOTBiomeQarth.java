package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.qarth.GOTStructureQarthFortress;
import got.common.world.structure.essos.qarth.GOTStructureQarthSettlement;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeQarth extends GOTBiomeQarthDesert {
	public GOTBiomeQarth(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.sand;
		topBlockMeta = 0;
		fillerBlock = Blocks.sandstone;
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.grassPerChunk = 5;
		decorator.addSettlement(new GOTStructureQarthSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureQarthFortress(false), 800);
		npcSpawnList.clear();
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.QARTH_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(8).add(c0);
		Collection<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN / 4));
		npcSpawnList.newFactionList(2).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQarth;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.QARTH;
	}
}