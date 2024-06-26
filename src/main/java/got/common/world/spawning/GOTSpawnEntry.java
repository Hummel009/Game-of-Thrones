package got.common.world.spawning;

import net.minecraft.world.biome.BiomeGenBase;

public class GOTSpawnEntry extends BiomeGenBase.SpawnListEntry {
	public GOTSpawnEntry(Class<?> c, int weight, int min, int max) {
		super(c, weight, min, max);
	}

	public static class Instance {
		private final GOTSpawnEntry spawnEntry;
		private final int spawnChance;
		private final boolean isConquestSpawn;

		public Instance(GOTSpawnEntry entry, int chance, boolean conquest) {
			spawnEntry = entry;
			spawnChance = chance;
			isConquestSpawn = conquest;
		}

		public GOTSpawnEntry getSpawnEntry() {
			return spawnEntry;
		}

		public int getSpawnChance() {
			return spawnChance;
		}

		public boolean isConquestSpawn() {
			return isConquestSpawn;
		}
	}
}