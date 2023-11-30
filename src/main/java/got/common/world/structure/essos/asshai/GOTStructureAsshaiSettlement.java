package got.common.world.structure.essos.asshai;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.entity.essos.GOTEntityRedPriest;
import got.common.entity.essos.asshai.GOTEntityAsshaiAlchemist;
import got.common.entity.essos.asshai.GOTEntityAsshaiMan;
import got.common.entity.essos.asshai.GOTEntityAsshaiWarrior;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureAsshaiSettlement extends GOTStructureBaseSettlement {
	public GOTStructureAsshaiSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 6;
		fixedSettlementChunkRadius = 6;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureAsshaiSettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos);
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureAsshaiSettlement> {
		public Instance(GOTStructureAsshaiSettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			int k1;
			int houseZ;
			boolean outerTavern = random.nextBoolean();
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityAsshaiMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityRedPriest.class, GOTEntityAsshaiAlchemist.class);
					spawner.setCheckRanges(80, -12, 12, 10);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setSpawnInterval(1);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-40, 40}) {
				for (int k12 : new int[]{-40, 40}) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityAsshaiWarrior.class);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k12, 0);
				}
			}
			addStructure(new GOTStructureAsshaiFort(false), -5, 21, 2, true);
			addStructure(new GOTStructureAsshaiTower(false), -29, 22, 0, true);
			addStructure(new GOTStructureAsshaiTower(false), 29, 22, 0, true);
			addStructure(new GOTStructureAsshaiTower(false), -29, -36, 0, true);
			addStructure(new GOTStructureAsshaiTower(false), 29, -36, 0, true);
			int houseX = 24;
			for (k1 = -1; k1 <= 1; ++k1) {
				houseZ = k1 * 12;
				if (k1 == 1) {
					addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ, 1, true);
					addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ, 3, true);
				}
				if (k1 == 0) {
					continue;
				}
				addStructure(new GOTStructureAsshaiHouse(false), houseZ, houseX, 0, true);
				addStructure(new GOTStructureAsshaiHouse(false), houseZ, -houseX, 2, true);
			}
			addStructure(new GOTStructureAsshaiHouse(false), 0, -24, 2, true);
			addStructure(new GOTStructureAsshaiTavern(false), -houseX, -5, 1, true);
			addStructure(new GOTStructureAsshaiTavern(false), houseX, -9, 3, true);
			houseX = 54;
			for (k1 = -2; k1 <= 2; ++k1) {
				houseZ = k1 * 12;
				if (k1 <= -2 || k1 >= 1) {
					addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ, 3, true);
					addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ, 1, true);
				}
				addStructure(new GOTStructureAsshaiHouse(false), houseZ, houseX, 2, true);
				addStructure(new GOTStructureAsshaiHouse(false), houseZ, -houseX, 0, true);
			}
			houseX = 64;
			for (int k13 = -4; k13 <= 4; ++k13) {
				int houseZ2 = k13 * 12;
				boolean treepiece = IntMath.mod(k13, 2) == 1;
				if (treepiece) {
					addStructure(new GOTStructureAsshaiHouse(false), -houseX - 2, houseZ2, 1, true);
					addStructure(new GOTStructureAsshaiHouse(false), houseX + 2, houseZ2, 3, true);
				} else {
					addStructure(new GOTStructureAsshaiHouse(false), -houseX, houseZ2, 1, true);
					addStructure(new GOTStructureAsshaiHouse(false), houseX, houseZ2, 3, true);
				}
				if (treepiece) {
					addStructure(new GOTStructureAsshaiHouse(false), houseZ2, -houseX - 2, 2, true);
				} else {
					addStructure(new GOTStructureAsshaiHouse(false), houseZ2, -houseX, 2, true);
				}
				if (Math.abs(k13) < 2 || outerTavern && k13 > 2) {
					continue;
				}
				if (treepiece) {
					addStructure(new GOTStructureAsshaiHouse(false), houseZ2, houseX + 2, 0, true);
					continue;
				}
				addStructure(new GOTStructureAsshaiHouse(false), houseZ2, houseX, 0, true);
			}
			if (outerTavern) {
				addStructure(new GOTStructureAsshaiTavern(false), 44, houseX, 0, true);
			}
			int gardenX = 42;
			int gardenZ = 48;
			addStructure(new GOTStructureAsshaiHouse(false), -gardenX, -gardenZ, 1, true);
			addStructure(new GOTStructureAsshaiHouse(false), -gardenX, gardenZ, 1, true);
			addStructure(new GOTStructureAsshaiHouse(false), gardenX, -gardenZ, 3, true);
			addStructure(new GOTStructureAsshaiHouse(false), gardenX, gardenZ, 3, true);
			int wallX;
			int l;
			for (int k11 : new int[]{67, 75}) {
				addStructure(new GOTStructureAsshaiTownBench(false), -10, k11, 1, true);
				addStructure(new GOTStructureAsshaiTownBench(false), 10, k11, 3, true);
			}
			addStructure(new GOTStructureAsshaiGatehouse(false), 0, 84, 2, true);
			addStructure(new GOTStructureAsshaiLampPost(false), -4, 73, 0, true);
			addStructure(new GOTStructureAsshaiLampPost(false), 4, 73, 0, true);
			int towerX = 78;
			int towerZ = 74;
			for (int i1 : new int[]{-towerX, towerX}) {
				addStructure(new GOTStructureAsshaiWatchtower(false), i1, -towerZ, 2, true);
				addStructure(new GOTStructureAsshaiWatchtower(false), i1, towerZ, 0, true);
			}
			int wallZ = 82;
			int wallEndX = 76;
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(GOTStructureAsshaiTownWall.Left(false), -wallX, wallZ, 2, true);
				addStructure(GOTStructureAsshaiTownWall.Right(false), wallX, wallZ, 2, true);
			}
			addStructure(GOTStructureAsshaiTownWall.LeftEndShort(false), -wallEndX, wallZ, 2, true);
			addStructure(GOTStructureAsshaiTownWall.RightEndShort(false), wallEndX, wallZ, 2, true);
			addStructure(GOTStructureAsshaiTownWall.Centre(false), -wallZ, 0, 3, true);
			addStructure(GOTStructureAsshaiTownWall.Centre(false), wallZ, 0, 1, true);
			addStructure(GOTStructureAsshaiTownWall.Centre(false), 0, -wallZ, 0, true);
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(GOTStructureAsshaiTownWall.Left(false), -wallZ, -wallX, 3, true);
				addStructure(GOTStructureAsshaiTownWall.Right(false), -wallZ, wallX, 3, true);
				addStructure(GOTStructureAsshaiTownWall.Left(false), wallZ, wallX, 1, true);
				addStructure(GOTStructureAsshaiTownWall.Right(false), wallZ, -wallX, 1, true);
				addStructure(GOTStructureAsshaiTownWall.Left(false), wallX, -wallZ, 0, true);
				addStructure(GOTStructureAsshaiTownWall.Right(false), -wallX, -wallZ, 0, true);
			}
			addStructure(GOTStructureAsshaiTownWall.LeftEnd(false), -wallZ, -wallEndX, 3, true);
			addStructure(GOTStructureAsshaiTownWall.RightEnd(false), -wallZ, wallEndX, 3, true);
			addStructure(GOTStructureAsshaiTownWall.LeftEnd(false), wallZ, wallEndX, 1, true);
			addStructure(GOTStructureAsshaiTownWall.RightEnd(false), wallZ, -wallEndX, 1, true);
			addStructure(GOTStructureAsshaiTownWall.LeftEndShort(false), wallEndX, -wallZ, 0, true);
			addStructure(GOTStructureAsshaiTownWall.RightEndShort(false), -wallEndX, -wallZ, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (i1 <= 80 && k1 <= 80) {
				return GOTBezierType.TOWN_ASSHAI;
			}
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return block == GOTBlocks.asshaiDirt || block == GOTBlocks.slabSingleDirt && meta == 3 || block == GOTBlocks.basaltGravel;
		}

		@Override
		public void setupSettlementProperties(Random random) {
		}
	}
}
