package got.common.world.fixed;

import java.util.Random;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.wildling.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.wildling.*;
import net.minecraft.world.World;

public class GOTStructureHardhome extends GOTVillageGen {
	public GOTStructureHardhome(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureHardhome village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			int marketZ;
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityWildling.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int spawnerX = 60;
			for (int i1 : new int[] { -spawnerX, spawnerX }) {
				for (int k1 : new int[] { -spawnerX, spawnerX }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClasses(GOTEntityWildlingAxeThrower.class, GOTEntityWildlingArcher.class);
							spawner.setCheckRanges(50, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			this.addStructure(new GOTStructureWildlingChieftainHouse(false).setIsHardhome(), 0, 6, 2, true);
			int mansionX = 12;
			int mansionZ = 20;
			this.addStructure(new GOTStructureWildlingHouse(false), -mansionX, -mansionZ, 2, true);
			this.addStructure(new GOTStructureWildlingHouse(false), mansionX, -mansionZ, 2, true);
			this.addStructure(new GOTStructureWildlingHouse(false), -mansionX, mansionZ, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false), mansionX, mansionZ, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false), -mansionZ, -mansionX, 1, true);
			this.addStructure(new GOTStructureWildlingHouse(false), -mansionZ, mansionX, 1, true);
			this.addStructure(new GOTStructureWildlingHouse(false), mansionZ, -mansionX, 3, true);
			this.addStructure(new GOTStructureWildlingHouse(false), mansionZ, mansionX, 3, true);
			for (int l = 0; l <= 3; ++l) {
				int houseX = 10 + 14 * l;
				int houseZ1 = 58;
				int houseZ2 = 68;
				if (l <= 2) {
					if (l >= 1 && l <= 2) {
						if (l == 1) {
							this.addStructure(new GOTStructureWildlingHouse(false), -houseX - 7, -houseZ1, 0, true);
						}
					} else {
						this.addStructure(new GOTStructureWildlingHouse(false), -houseX, -houseZ1, 0, true);
					}
					this.addStructure(new GOTStructureWildlingHouse(false), houseX, -houseZ1, 0, true);
					if (l >= 1) {
						this.addStructure(new GOTStructureWildlingHouse(false), -houseX, houseZ1, 2, true);
						this.addStructure(new GOTStructureWildlingHouse(false), houseX, houseZ1, 2, true);
					}
					this.addStructure(new GOTStructureWildlingHouse(false), -houseZ1, -houseX, 3, true);
					this.addStructure(new GOTStructureWildlingHouse(false), -houseZ1, houseX, 3, true);
					this.addStructure(new GOTStructureWildlingHouse(false), houseZ1, -houseX, 1, true);
					this.addStructure(new GOTStructureWildlingHouse(false), houseZ1, houseX, 1, true);
				}
				this.addStructure(new GOTStructureWildlingHouse(false), -houseX, -houseZ2, 2, true);
				this.addStructure(new GOTStructureWildlingHouse(false), houseX, -houseZ2, 2, true);
				this.addStructure(new GOTStructureWildlingHouse(false), -houseX, houseZ2, 0, true);
				this.addStructure(new GOTStructureWildlingHouse(false), houseX, houseZ2, 0, true);
				this.addStructure(new GOTStructureWildlingHouse(false), -houseZ2, -houseX, 1, true);
				this.addStructure(new GOTStructureWildlingHouse(false), -houseZ2, houseX, 1, true);
				this.addStructure(new GOTStructureWildlingHouse(false), houseZ2, -houseX, 3, true);
				this.addStructure(new GOTStructureWildlingHouse(false), houseZ2, houseX, 3, true);
			}
			int gardenX = 58;
			this.addStructure(new GOTStructureWildlingHouse(false).setIsThief(), -gardenX + 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsThief(), gardenX - 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsThief(), -gardenX + 5, gardenX, 2, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsThief(), gardenX - 5, gardenX, 2, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}

	}
}