package got.common.world.spawning;

import got.common.database.GOTSpawnList;
import got.common.faction.GOTFaction;
import got.common.world.map.GOTConquestGrid;
import got.common.world.map.GOTConquestZone;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTFactionContainer {
	private final Collection<GOTSpawnListContainer> spawnLists = new ArrayList<>();
	private final GOTBiomeSpawnList parent;
	private final int baseWeight;

	private GOTFaction theFaction;
	private float conquestSensitivity = 1.0f;

	public GOTFactionContainer(GOTBiomeSpawnList biomeList, int w) {
		parent = biomeList;
		baseWeight = w;
	}

	public void add(Collection<GOTSpawnListContainer> list) {
		spawnLists.addAll(list);
	}

	public void determineFaction(World world) {
		if (theFaction == null) {
			for (GOTSpawnListContainer cont : spawnLists) {
				GOTSpawnList list = cont.getSpawnList();
				GOTFaction fac = list.getListCommonFaction(world);
				if (theFaction == null) {
					theFaction = fac;
					continue;
				}
				if (fac == theFaction) {
					continue;
				}
				throw new IllegalArgumentException("Faction containers must include spawn lists of only one faction! Mismatched faction " + fac.codeName() + " in biome " + parent.getBiomeIdentifier());
			}
		}
	}

	public float getEffectiveConquestStrength(World world, GOTConquestZone zone) {
		if (GOTConquestGrid.conquestEnabled(world) && !zone.isEmpty()) {
			float conqStr = zone.getConquestStrength(theFaction, world);
			for (GOTFaction allyFac : theFaction.getConquestBoostRelations()) {
				if (parent.isFactionPresent(world, allyFac)) {
					continue;
				}
				conqStr += zone.getConquestStrength(allyFac, world) * 0.333f;
			}
			return conqStr;
		}
		return 0.0f;
	}

	public int getFactionWeight(float conq) {
		if (conq > 0.0f) {
			float conqFactor = conq * 0.2f * conquestSensitivity;
			return baseWeight + Math.round(conqFactor);
		}
		return baseWeight;
	}

	public GOTSpawnListContainer getRandomSpawnList(Random rand, float conq) {
		int totalWeight = 0;
		for (GOTSpawnListContainer cont : spawnLists) {
			if (!cont.canSpawnAtConquestLevel(conq)) {
				continue;
			}
			totalWeight += cont.getWeight();
		}
		if (totalWeight > 0) {
			int w = rand.nextInt(totalWeight);
			for (GOTSpawnListContainer cont : spawnLists) {
				if (!cont.canSpawnAtConquestLevel(conq) || (w -= cont.getWeight()) >= 0) {
					continue;
				}
				return cont;
			}
			return null;
		}
		return null;
	}

	public boolean isEmpty() {
		return spawnLists.isEmpty();
	}

	public Collection<GOTSpawnListContainer> getSpawnLists() {
		return spawnLists;
	}

	public int getBaseWeight() {
		return baseWeight;
	}

	public GOTFaction getTheFaction() {
		return theFaction;
	}

	@SuppressWarnings("unused")
	public float getConquestSensitivity() {
		return conquestSensitivity;
	}

	public void setConquestSensitivity(float conquestSensitivity) {
		this.conquestSensitivity = conquestSensitivity;
	}
}