package got.common.world.structure.westeros.wildling;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.wildling.GOTEntityWildling;
import got.common.entity.westeros.wildling.GOTEntityWildlingArcher;
import got.common.entity.westeros.wildling.GOTEntityWildlingAxeThrower;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyWell;
import got.common.world.structure.other.GOTStructureHayBales;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.GOTVillageGen;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureWildlingVillage extends GOTVillageGen {
	public boolean isHardhome;
	public boolean isCraster;

	public GOTStructureWildlingVillage(GOTBiome biome, float f) {
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

	public GOTStructureWildlingVillage setIsCraster() {
		isCraster = true;
		fixedVillageChunkRadius = 1;
		return this;
	}

	public GOTStructureWildlingVillage setIsHardhome() {
		isHardhome = true;
		fixedVillageChunkRadius = 9;
		return this;
	}

	public enum VillageType {
		VILLAGE, HARDHOME, CRASTER;
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureWildlingVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			switch (villageType) {
				case HARDHOME:
					setupHardhome(random);
					break;
				case VILLAGE:
					setupVillage(random);
					break;
				case CRASTER:
					this.addStructure(new GOTStructureWildlingKeep(false), -7, 0, 2, true);
					this.addStructure(new GOTStructureWildlingBarn(false), 7, 6, 2, true);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			int dSq = i * i + k * k;
			int imn = 15 + random.nextInt(4);
			if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
				return GOTBezierType.PATH_DIRTY;
			}
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			return block == Blocks.snow || block == Blocks.ice;
		}

		public void setupHardhome(Random random) {
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
			for (int i1 : new int[]{-spawnerX, spawnerX}) {
				for (int k1 : new int[]{-spawnerX, spawnerX}) {
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
			this.addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), -gardenX + 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), gardenX - 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), -gardenX + 5, gardenX, 2, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), gardenX - 5, gardenX, 2, true);
		}

		public void setupVillage(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityWildling.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityWildlingAxeThrower.class, GOTEntityWildlingArcher.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			this.addStructure(new GOTStructureMossovyWell(false), 0, -2, 0, true);
			this.addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), 0, -centreSide, 2, true);
			this.addStructure(new GOTStructureWildlingHouse(false), -pathEnd, 0, 1, true);
			this.addStructure(new GOTStructureWildlingChieftainHouse(false), pathEnd, 0, 3, true);
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					this.addStructure(new GOTStructureWildlingHouse(false), i1, -k1, 2);
				}
				this.addStructure(new GOTStructureWildlingHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					this.addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
				}
				this.addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (isHardhome) {
				villageType = VillageType.HARDHOME;
			} else if (isCraster) {
				villageType = VillageType.CRASTER;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

	}
}