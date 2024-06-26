package got.common.world.spawning;

import got.common.database.GOTSpawnList;

public class GOTSpawnListContainer {
	private final GOTSpawnList spawnList;
	private final int weight;

	private int spawnChance;

	GOTSpawnListContainer(GOTSpawnList list, int w) {
		spawnList = list;
		weight = w;
	}

	public static boolean canSpawnAtConquestLevel(float conq) {
		return conq > -1.0f;
	}

	public int getWeight() {
		return weight;
	}

	public GOTSpawnList getSpawnList() {
		return spawnList;
	}

	public int getSpawnChance() {
		return spawnChance;
	}

	public GOTSpawnListContainer setSpawnChance(int i) {
		spawnChance = i;
		return this;
	}
}