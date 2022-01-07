package got.common.world.structure.essos.asshai;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.entity.essos.GOTEntityRedPriest;
import got.common.entity.essos.asshai.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureAsshaiCity extends GOTVillageGen {
	public GOTStructureAsshaiCity(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 5;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureAsshaiCity village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addStructure(GOTStructureBase structure, int x, int z, int r, boolean force) {
			super.addStructure(structure, x, z, r, force);
		}

		@Override
		public void addVillageStructures(Random random) {
			int k1;
			int houseZ;
			boolean outerTavern = random.nextBoolean();
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityAsshaiMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityRedPriest.class, GOTEntityAsshaiAlchemist.class);
					spawner.setCheckRanges(80, -12, 12, 10);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setSpawnInterval(1);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -40, 40 }) {
				for (int k12 : new int[] { -40, 40 }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityAsshaiGuard.class);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k12, 0);
				}
			}
			this.addStructure(new GOTStructureAsshaiFort(false), 5, -20, 0, true);
			this.addStructure(new GOTStructureAsshaiTower(false), -27, 24, 0, true);
			this.addStructure(new GOTStructureAsshaiTower(false), 27, 24, 0, true);
			this.addStructure(new GOTStructureAsshaiTower(false), -27, -34, 0, true);
			this.addStructure(new GOTStructureAsshaiTower(false), 27, -34, 0, true);
			int houseX = 24;
			for (k1 = -1; k1 <= 1; ++k1) {
				houseZ = k1 * 12;
				if (k1 == 1) {
					this.addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ, 1, true);
					this.addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ, 3, true);
				}
				if (k1 == 0) {
					continue;
				}
				this.addStructure(new GOTStructureAsshaiHouse(false), houseZ, houseX, 0, true);
				this.addStructure(new GOTStructureAsshaiHouse(false), houseZ, -houseX, 2, true);
			}
			this.addStructure(new GOTStructureAsshaiLaboratory(false), 0, -26, 2, true);
			this.addStructure(new GOTStructureAsshaiTavern(false), -houseX, -5, 1, true);
			this.addStructure(new GOTStructureAsshaiOblivionAltar(false), houseX + 2, -6, 3, true);
			houseX = 54;
			for (k1 = -2; k1 <= 2; ++k1) {
				houseZ = k1 * 12;
				if (k1 <= -2 || k1 >= 1) {
					this.addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ, 3, true);
					this.addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ, 1, true);
				}
				this.addStructure(new GOTStructureAsshaiHouse(false), houseZ, houseX, 2, true);
				this.addStructure(new GOTStructureAsshaiHouse(false), houseZ, -houseX, 0, true);
			}
			houseX = 64;
			for (int k13 = -4; k13 <= 4; ++k13) {
				int houseZ2 = k13 * 12;
				boolean treepiece = IntMath.mod(k13, 2) == 1;
				if (treepiece) {
					this.addStructure(new GOTStructureAsshaiHouse(false), -houseX - 2, houseZ2, 1, true);
					this.addStructure(new GOTStructureAsshaiHouse(false), houseX + 2, houseZ2, 3, true);
				} else {
					this.addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ2, 1, true);
					this.addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ2, 3, true);
				}
				if (treepiece) {
					this.addStructure(new GOTStructureAsshaiHouse(false), houseZ2, -houseX - 2, 2, true);
				} else {
					this.addStructure(new GOTStructureAsshaiHouse(false), houseZ2, -houseX, 2, true);
				}
				if (Math.abs(k13) < 2 || outerTavern && k13 > 2) {
					continue;
				}
				if (treepiece) {
					this.addStructure(new GOTStructureAsshaiHouse(false), houseZ2, houseX + 2, 0, true);
					continue;
				}
				this.addStructure(new GOTStructureAsshaiHouse(false), houseZ2, houseX, 0, true);
			}
			if (outerTavern) {
				this.addStructure(new GOTStructureAsshaiTavern(false), 44, houseX, 0, true);
			}
			int gardenX = 42;
			int gardenZ = 48;
			this.addStructure(new GOTStructureAsshaiHouse(false), -gardenX, -gardenZ, 1, true);
			this.addStructure(new GOTStructureAsshaiHouse(false), -gardenX, gardenZ, 1, true);
			this.addStructure(new GOTStructureAsshaiHouse(false), gardenX, -gardenZ, 3, true);
			this.addStructure(new GOTStructureAsshaiHouse(false), gardenX, gardenZ, 3, true);
			int wallX;
			int l;
			for (int k11 : new int[] { 67, 75 }) {
				this.addStructure(new GOTStructureAsshaiTownBench(false), -10, k11, 1, true);
				this.addStructure(new GOTStructureAsshaiTownBench(false), 10, k11, 3, true);
			}
			this.addStructure(new GOTStructureAsshaiGatehouse(false), 0, 84, 2, true);
			this.addStructure(new GOTStructureAsshaiLampPost(false), -4, 73, 0, true);
			this.addStructure(new GOTStructureAsshaiLampPost(false), 4, 73, 0, true);
			int towerX = 78;
			int towerZ = 74;
			for (int i1 : new int[] { -towerX, towerX }) {
				this.addStructure(new GOTStructureAsshaiWatchtower(false), i1, -towerZ, 2, true);
				this.addStructure(new GOTStructureAsshaiWatchtower(false), i1, towerZ, 0, true);
			}
			int wallZ = 82;
			int wallEndX = 76;
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				this.addStructure(GOTStructureAsshaiTownWall.Left(false), -wallX, wallZ, 2, true);
				this.addStructure(GOTStructureAsshaiTownWall.Right(false), wallX, wallZ, 2, true);
			}
			this.addStructure(GOTStructureAsshaiTownWall.LeftEndShort(false), -wallEndX, wallZ, 2, true);
			this.addStructure(GOTStructureAsshaiTownWall.RightEndShort(false), wallEndX, wallZ, 2, true);
			this.addStructure(GOTStructureAsshaiTownWall.Centre(false), -wallZ, 0, 3, true);
			this.addStructure(GOTStructureAsshaiTownWall.Centre(false), wallZ, 0, 1, true);
			this.addStructure(GOTStructureAsshaiTownWall.Centre(false), 0, -wallZ, 0, true);
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				this.addStructure(GOTStructureAsshaiTownWall.Left(false), -wallZ, -wallX, 3, true);
				this.addStructure(GOTStructureAsshaiTownWall.Right(false), -wallZ, wallX, 3, true);
				this.addStructure(GOTStructureAsshaiTownWall.Left(false), wallZ, wallX, 1, true);
				this.addStructure(GOTStructureAsshaiTownWall.Right(false), wallZ, -wallX, 1, true);
				this.addStructure(GOTStructureAsshaiTownWall.Left(false), wallX, -wallZ, 0, true);
				this.addStructure(GOTStructureAsshaiTownWall.Right(false), -wallX, -wallZ, 0, true);
			}
			this.addStructure(GOTStructureAsshaiTownWall.LeftEnd(false), -wallZ, -wallEndX, 3, true);
			this.addStructure(GOTStructureAsshaiTownWall.RightEnd(false), -wallZ, wallEndX, 3, true);
			this.addStructure(GOTStructureAsshaiTownWall.LeftEnd(false), wallZ, wallEndX, 1, true);
			this.addStructure(GOTStructureAsshaiTownWall.RightEnd(false), wallZ, -wallEndX, 1, true);
			this.addStructure(GOTStructureAsshaiTownWall.LeftEndShort(false), wallEndX, -wallZ, 0, true);
			this.addStructure(GOTStructureAsshaiTownWall.RightEndShort(false), -wallEndX, -wallZ, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.TOWN && i1 <= 80 && k1 <= 80) {
				return GOTBezierType.ASSHAI;
			}
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			return villageType == VillageType.TOWN && block == Blocks.cobblestone;
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = VillageType.TOWN;
		}

	}

	public enum VillageType {
		TOWN;
	}

}
