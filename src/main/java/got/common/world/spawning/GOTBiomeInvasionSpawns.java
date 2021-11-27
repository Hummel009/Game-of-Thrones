package got.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.database.GOTInvasions;
import got.common.world.biome.GOTBiome;

public class GOTBiomeInvasionSpawns {
	public GOTBiome theBiome;
	public Map<GOTEventSpawner.EventChance, List<GOTInvasions>> invasionsByChance = new HashMap<>();
	public List<GOTInvasions> registeredInvasions = new ArrayList<>();

	public GOTBiomeInvasionSpawns(GOTBiome biome) {
		theBiome = biome;
	}

	public void addInvasion(GOTInvasions invasion, GOTEventSpawner.EventChance chance) {
		List<GOTInvasions> chanceList = getInvasionsForChance(chance);
		if (chanceList.contains(invasion) || registeredInvasions.contains(invasion)) {
			FMLLog.warning("GOT biome %s already has invasion %s registered", theBiome.biomeName, invasion.codeName());
		} else {
			chanceList.add(invasion);
			registeredInvasions.add(invasion);
		}
	}

	public void clearInvasions() {
		invasionsByChance.clear();
		registeredInvasions.clear();
	}

	public List<GOTInvasions> getInvasionsForChance(GOTEventSpawner.EventChance chance) {
		List<GOTInvasions> chanceList = invasionsByChance.get(chance);
		if (chanceList == null) {
			chanceList = new ArrayList<>();
		}
		invasionsByChance.put(chance, chanceList);
		return chanceList;
	}
}
