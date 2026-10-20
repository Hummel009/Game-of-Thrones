package io.github.hummel009.minecraft.got.common.world.spawning;

import cpw.mods.fml.common.FMLLog;
import io.github.hummel009.minecraft.got.common.data.GOTSpawnList;
import io.github.hummel009.minecraft.got.common.faction.GOTFaction;
import io.github.hummel009.minecraft.got.common.util.GOTLog;
import io.github.hummel009.minecraft.got.common.world.biome.GOTBiome;
import io.github.hummel009.minecraft.got.common.world.map.GOTConquestGrid;
import io.github.hummel009.minecraft.got.common.world.map.GOTConquestZone;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GOTBiomeSpawnList {
	private final Collection<GOTFaction> presentFactions = new ArrayList<>();
	private final List<GOTFactionContainer> factionContainers = new ArrayList<>();
	private final String biomeIdentifier;

	@SuppressWarnings("unused")
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
		int n = factionContainers.size();
		int[] weights = new int[n];
		float[] conqs = new float[n];
		int totalWeight = 0;
		for (int idx = 0; idx < n; ++idx) {
			GOTFactionContainer cont = factionContainers.get(idx);
			if (cont.isEmpty()) {
				weights[idx] = 0;
				conqs[idx] = 0.0f;
				continue;
			}
			float conq = cont.getEffectiveConquestStrength(world, zone);
			int weight = cont.getFactionWeight(conq);
			if (weight <= 0) {
				weights[idx] = 0;
				conqs[idx] = 0.0f;
				continue;
			}
			weights[idx] = weight;
			conqs[idx] = conq;
			totalWeight += weight;
		}
		if (totalWeight > 0) {
			int chosenIdx = -1;
			boolean isConquestSpawn = false;
			int w = rand.nextInt(totalWeight);
			for (int idx = 0; idx < n; ++idx) {
				int facWeight = weights[idx];
				if (facWeight <= 0) {
					continue;
				}
				w -= facWeight;
				if (w >= 0) {
					continue;
				}
				chosenIdx = idx;
				GOTFactionContainer cont = factionContainers.get(idx);
				if (facWeight <= cont.getBaseWeight()) {
					break;
				}
				isConquestSpawn = rand.nextFloat() < (float) (facWeight - cont.getBaseWeight()) / facWeight;
				break;
			}
			if (chosenIdx >= 0) {
				float conq = conqs[chosenIdx];
				GOTFactionContainer chosenFacContainer = factionContainers.get(chosenIdx);
				GOTSpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq);
				if (spawnList == null || spawnList.getSpawnList() == null) {
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