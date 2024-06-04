package got.common.world.spawning;

import cpw.mods.fml.common.FMLLog;
import got.common.database.GOTSpawnList;
import got.common.faction.GOTFaction;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTConquestGrid;
import got.common.world.map.GOTConquestZone;
import net.minecraft.world.World;

import java.util.*;

public class GOTBiomeSpawnList {
	private final Collection<GOTFaction> presentFactions = new ArrayList<>();
	private final List<GOTFactionContainer> factionContainers = new ArrayList<>();
	private final String biomeIdentifier;

	public GOTBiomeSpawnList(GOTBiome biome) {
		this(biome.getClass().getName());
	}

	private GOTBiomeSpawnList(String s) {
		biomeIdentifier = s;
	}

	public static GOTSpawnListContainer entry(GOTSpawnList list, int weight) {
		return new GOTSpawnListContainer(list, weight);
	}

	public void clear() {
		factionContainers.clear();
		presentFactions.clear();
	}

	private void determineFactions(World world) {
		if (presentFactions.isEmpty() && !factionContainers.isEmpty()) {
			for (GOTFactionContainer facContainer : factionContainers) {
				facContainer.determineFaction(world);
				GOTFaction fac = facContainer.getTheFaction();
				if (presentFactions.contains(fac)) {
					continue;
				}
				presentFactions.add(fac);
			}
		}
	}

	public GOTSpawnEntry.Instance getRandomSpawnEntry(Random rand, World world, int i, int k) {
		determineFactions(world);
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(i, k);
		int totalWeight = 0;
		HashMap<GOTFactionContainer, Integer> cachedFacWeights = new HashMap<>();
		HashMap<GOTFactionContainer, Float> cachedConqStrengths = new HashMap<>();
		for (GOTFactionContainer cont : factionContainers) {
			int weight;
			float conq;
			if (cont.isEmpty() || (weight = cont.getFactionWeight(conq = cont.getEffectiveConquestStrength(world, zone))) <= 0) {
				continue;
			}
			totalWeight += weight;
			cachedFacWeights.put(cont, weight);
			cachedConqStrengths.put(cont, conq);
		}
		if (totalWeight > 0) {
			GOTFactionContainer chosenFacContainer = null;
			boolean isConquestSpawn = false;
			int w = rand.nextInt(totalWeight);
			for (GOTFactionContainer cont : factionContainers) {
				int facWeight;
				if (cont.isEmpty() || !cachedFacWeights.containsKey(cont) || (w -= facWeight = cachedFacWeights.get(cont)) >= 0) {
					continue;
				}
				chosenFacContainer = cont;
				if (facWeight <= cont.getBaseWeight()) {
					break;
				}
				isConquestSpawn = rand.nextFloat() < (float) (facWeight - cont.getBaseWeight()) / facWeight;
				break;
			}
			if (chosenFacContainer != null) {
				float conq = cachedConqStrengths.get(chosenFacContainer);
				GOTSpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq);
				if (spawnList == null || spawnList.getSpawnList() == null) {
					System.out.println("WARNING NPE in " + biomeIdentifier + ", " + chosenFacContainer.getTheFaction().codeName());
					FMLLog.severe("WARNING NPE in " + biomeIdentifier + ", " + chosenFacContainer.getTheFaction().codeName());
					GOTLog.getLogger().warn("WARNING NPE in {}, {}", biomeIdentifier, chosenFacContainer.getTheFaction().codeName());
				}
				if (spawnList != null) {
					GOTSpawnEntry entry = spawnList.getSpawnList().getRandomSpawnEntry(rand);
					int chance = spawnList.getSpawnChance();
					return new GOTSpawnEntry.Instance(entry, chance, isConquestSpawn);
				}
			}
		}
		return null;
	}

	public boolean isFactionPresent(World world, GOTFaction fac) {
		determineFactions(world);
		return presentFactions.contains(fac);
	}

	public GOTFactionContainer newFactionList(int w) {
		return newFactionList(w, 1.0f);
	}

	private GOTFactionContainer newFactionList(int w, float conq) {
		GOTFactionContainer cont = new GOTFactionContainer(this, w);
		cont.setConquestSensitivity(conq);
		factionContainers.add(cont);
		return cont;
	}

	public List<GOTFactionContainer> getFactionContainers() {
		return factionContainers;
	}

	public String getBiomeIdentifier() {
		return biomeIdentifier;
	}
}