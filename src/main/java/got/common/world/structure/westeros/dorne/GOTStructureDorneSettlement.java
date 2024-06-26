package got.common.world.structure.westeros.dorne;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.westeros.dorne.GOTEntityDorneMan;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldier;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.common.GOTStructureWesterosVillageSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureDorneSettlement extends GOTStructureBaseSettlement {
	private Type type;
	private boolean forcedType;

	public GOTStructureDorneSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 6;
		fixedSettlementChunkRadius = 6;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(world, i, k, random, loc, spawnInfos, type, forcedType);
	}

	public GOTStructureBaseSettlement type(Type t, int radius) {
		type = t;
		settlementChunkRadius = radius;
		fixedSettlementChunkRadius = radius;
		forcedType = true;
		return this;
	}

	public enum Type {
		VILLAGE, TOWN, FORT
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance {
		private final boolean forcedType;
		private Type type;

		private Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos, Type t, boolean b) {
			super(world, i, k, random, loc, spawnInfos);
			type = t;
			forcedType = b;
		}

		private static GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				if (random.nextBoolean()) {
					return new GOTStructureDorneVillageFarm.Animals(false);
				}
				return new GOTStructureDorneVillageFarm.Crops(false);
			}
			return new GOTStructureDorneVillageFarm.Tree(false);
		}

		private static GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(5) == 0) {
				int i = random.nextInt(3);
				switch (i) {
					case 0:
						return new GOTStructureDorneStables(false);
					case 1:
						return new GOTStructureDorneSmithy(false);
					case 2:
						return new GOTStructureDorneBarn(false);
				}
			}
			return new GOTStructureDorneHouseSmall(false);
		}

		private static GOTStructureDorneTownWall getWallCentre(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -5, 5);
		}

		private static GOTStructureDorneTownWall getWallLeft(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -9, 6);
		}

		private static GOTStructureDorneTownWall getWallLeftEnd(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 6, -5, 6);
		}

		private static GOTStructureDorneTownWall getWallLeftEndShort(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -5, 6);
		}

		private static GOTStructureDorneTownWall getWallRight(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 9);
		}

		private static GOTStructureDorneTownWall getWallRightEnd(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 6, -6, 5);
		}

		private static GOTStructureDorneTownWall getWallRightEndShort(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 5);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			switch (type) {
				case TOWN:
					setupTown(random);
					break;
				case FORT:
					setupCastle();
					break;
				case VILLAGE:
					setupVillage(random);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (type == Type.VILLAGE) {
				int dSq = i * i + k * k;
				int imn = 20 + random.nextInt(4);
				if (dSq < imn * imn) {
					return GOTBezierType.PATH_SANDY;
				}
				int omn = 53 - random.nextInt(4);
				int omx = 60 + random.nextInt(4);
				if (dSq > omn * omn && dSq < omx * omx || dSq < 2809 && Math.abs(i1 - k1) <= 2 + random.nextInt(4)) {
					return GOTBezierType.PATH_SANDY;
				}
			} else if (type == Type.TOWN && i1 <= 80 && k1 <= 80) {
				return GOTBezierType.PATH_SANDY;
			} else if (type == Type.FORT) {
				if (i1 <= 1 && (k >= 13 || k <= -12) && k1 <= 36) {
					return GOTBezierType.PATH_SANDY;
				}
				if (k1 <= 1 && i1 >= 12 && i1 <= 36) {
					return GOTBezierType.PATH_SANDY;
				}
				if (k >= 26 && k <= 28 && i1 <= 12) {
					return GOTBezierType.PATH_SANDY;
				}
			}
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return block == Blocks.dirt && meta == 1 || block == GOTBlocks.dirtPath && meta == 0 || block == GOTBlocks.brick1 && meta == 15 || block == GOTBlocks.brick3 && meta == 11 || block == GOTBlocks.pillar1 && meta == 5 || block == Blocks.sand && meta == 0 || block == Blocks.sandstone && meta == 0 || block == GOTBlocks.slabSingleDirt && meta == 0 || block == GOTBlocks.slabSingleDirt && meta == 1 || block == GOTBlocks.slabSingleSand && meta == 0 || block == GOTBlocks.slabSingle4 && meta == 0 || block == GOTBlocks.slabSingle7 && meta == 1 || block == GOTBlocks.slabSingle4 && meta == 7;
		}

		private void setupCastle() {
			addStructure(new StructureRespawner1(), 0, 0, 0);
			for (int i1 : new int[]{-20, 20}) {
				for (int k1 : new int[]{-20, 20}) {
					addStructure(new StructureRespawner2(), i1, k1, 0);
				}
			}
			addStructure(new GOTStructureDorneFortress(false), 0, 12, 2, true);
			addStructure(new GOTStructureDorneFortGate(false), 0, -37, 0, true);
			addStructure(new GOTStructureDorneFortWall.Right(false), -11, -37, 0, true);
			addStructure(new GOTStructureDorneFortWall.Left(false), 11, -37, 0, true);
			addStructure(new GOTStructureDorneWatchtower(false), -23, -33, 2, true);
			addStructure(new GOTStructureDorneWatchtower(false), 23, -33, 2, true);
			addStructure(new GOTStructureDorneFortGate(false), -37, 0, 3, true);
			addStructure(new GOTStructureDorneFortWall.Left(false), -37, -11, 3, true);
			addStructure(new GOTStructureDorneFortWall.Right(false), -37, 11, 3, true);
			addStructure(new GOTStructureDorneWatchtower(false), -33, -23, 1, true);
			addStructure(new GOTStructureDorneWatchtower(false), -33, 23, 1, true);
			addStructure(new GOTStructureDorneFortGate(false), 0, 37, 2, true);
			addStructure(new GOTStructureDorneFortWall.Left(false), -11, 37, 2, true);
			addStructure(new GOTStructureDorneFortWall.Right(false), 11, 37, 2, true);
			addStructure(new GOTStructureDorneWatchtower(false), -23, 33, 0, true);
			addStructure(new GOTStructureDorneWatchtower(false), 23, 33, 0, true);
			addStructure(new GOTStructureDorneFortGate(false), 37, 0, 1, true);
			addStructure(new GOTStructureDorneFortWall.Right(false), 37, -11, 1, true);
			addStructure(new GOTStructureDorneFortWall.Left(false), 37, 11, 1, true);
			addStructure(new GOTStructureDorneWatchtower(false), 33, -23, 3, true);
			addStructure(new GOTStructureDorneWatchtower(false), 33, 23, 3, true);
			addStructure(new GOTStructureDorneFortWallCorner(false), -30, -30, 3);
			addStructure(new GOTStructureDorneFortWallCorner(false), -30, 30, 2);
			addStructure(new GOTStructureDorneFortWallCorner(false), 30, 30, 1);
			addStructure(new GOTStructureDorneFortWallCorner(false), 30, -30, 2);
			addStructure(new GOTStructureDorneStables(false), -24, 2, 0);
			addStructure(new GOTStructureDorneStables(false), -24, -2, 2);
			addStructure(new GOTStructureDorneSmithy(false), 24, 1, 0);
			addStructure(new GOTStructureDorneSmithy(false), 24, -1, 2);
			addStructure(new GOTStructureDorneHouseLarge(false), -3, -25, 1);
			addStructure(new GOTStructureDorneHouseLarge(false), 3, -25, 3);
			addStructure(new GOTStructureDorneVillageFarm.Crops(false), -18, -21, 1);
			addStructure(new GOTStructureDorneVillageFarm.Crops(false), 18, -21, 3);
			addStructure(new GOTStructureDorneWell(false), -12, 27, 1);
			addStructure(new GOTStructureDorneWell(false), 12, 27, 3);
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (!forcedType) {
				if (random.nextInt(4) == 0) {
					type = Type.FORT;
				} else {
					type = Type.VILLAGE;
				}
			}
		}

		private void setupTown(Random random) {
			int l;
			int wallX;
			boolean outerTavern = random.nextBoolean();
			addStructure(new StructureRespawner3(), 0, 0, 0);
			for (int i1 : new int[]{-40, 40}) {
				int[] arrn = {-40, 40};
				for (int k1 : arrn) {
					addStructure(new StructureRespawner4(), i1, k1, 0);
				}
			}
			addStructure(new GOTStructureDorneWell(false), 0, -4, 0, true);
			int stallPos = 12;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int k2 = k1 * stallPos;
				if (random.nextInt(3) != 0) {
					addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -stallPos + 3, k2, 1, true);
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), stallPos - 3, k2, 3, true);
			}
			if (random.nextInt(3) != 0) {
				addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 0, stallPos - 3, 0, true);
			}
			if (random.nextInt(3) != 0) {
				addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 0, -stallPos + 3, 2, true);
			}
			int flowerX = 12;
			int flowerZ = 18;
			for (int i1 : new int[]{-flowerX, flowerX}) {
				addStructure(new GOTStructureDorneTownGarden(false), i1, flowerZ, 0, true);
				addStructure(new GOTStructureDorneTownGarden(false), i1, -flowerZ, 2, true);
				addStructure(new GOTStructureDorneTownGarden(false), -flowerZ, i1, 1, true);
				addStructure(new GOTStructureDorneTownGarden(false), flowerZ, i1, 3, true);
			}
			int lampZ = 21;
			for (int i1 : new int[]{-1, 1}) {
				int lampX = i1 * 6;
				addStructure(new GOTStructureDorneLampPost(false), lampX, lampZ, 0, true);
				addStructure(new GOTStructureDorneLampPost(false), lampX, -lampZ, 2, true);
				if (i1 != -1) {
					addStructure(new GOTStructureDorneLampPost(false), -lampZ, lampX, 1, true);
				}
				addStructure(new GOTStructureDorneLampPost(false), lampZ, lampX, 3, true);
			}
			int houseX = 24;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int houseZ = k1 * 12;
				if (k1 == 1) {
					addStructure(new GOTStructureDorneHouseLarge(false), -houseX, houseZ, 1, true);
					addStructure(new GOTStructureDorneHouseLarge(false), houseX, houseZ, 3, true);
				}
				if (k1 == 0) {
					continue;
				}
				addStructure(new GOTStructureDorneHouseLarge(false), houseZ, houseX, 0, true);
				addStructure(new GOTStructureDorneHouseLarge(false), houseZ, -houseX, 2, true);
			}
			addStructure(new GOTStructureDorneSmithy(false), 0, -26, 2, true);
			addStructure(new GOTStructureDorneObelisk(false), 0, 27, 0, true);
			addStructure(new GOTStructureDorneTavern(false), -houseX, -5, 1, true);
			addStructure(new GOTStructureDorneTownTrees(false), -47, -13, 2, true);
			addStructure(new GOTStructureDorneTownTrees(false), -47, 1, 0, true);
			for (int i1 : new int[]{-43, -51}) {
				addStructure(new GOTStructureDorneTownBench(false), i1, -9, 2, true);
				addStructure(new GOTStructureDorneTownBench(false), i1, -3, 0, true);
			}
			addStructure(new GOTStructureDorneBath(false), houseX + 2, -6, 3, true);
			addStructure(new GOTStructureDorneTownGarden(false), 51, -13, 2, true);
			addStructure(new GOTStructureDorneTownGarden(false), 51, 1, 0, true);
			addStructure(new GOTStructureDorneTownGarden(false), 52, -6, 3, true);
			int wellX = 22;
			int wellZ = 31;
			for (int i1 : new int[]{-wellX, wellX}) {
				addStructure(new GOTStructureDorneWell(false), i1, -wellZ, 2, true);
				addStructure(new GOTStructureDorneWell(false), i1, wellZ, 0, true);
				addStructure(new GOTStructureDorneWell(false), -wellZ, i1, 1, true);
				addStructure(new GOTStructureDorneWell(false), wellZ, i1, 3, true);
			}
			houseX = 54;
			for (int k1 = -2; k1 <= 2; ++k1) {
				int houseZ = k1 * 12;
				if (k1 == -2 || k1 >= 1) {
					addStructure(new GOTStructureDorneHouseLarge(false), -houseX, houseZ, 3, true);
					addStructure(new GOTStructureDorneHouseLarge(false), houseX, houseZ, 1, true);
				}
				addStructure(new GOTStructureDorneHouseLarge(false), houseZ, houseX, 2, true);
				addStructure(new GOTStructureDorneHouseLarge(false), houseZ, -houseX, 0, true);
			}
			int treeX = 47;
			int treeZ = 35;
			for (int i1 : new int[]{-treeX, treeX}) {
				addStructure(new GOTStructureDorneTownTrees(false), i1, -treeZ, 0, true);
				addStructure(new GOTStructureDorneTownTrees(false), i1, treeZ, 2, true);
				addStructure(new GOTStructureDorneTownTrees(false), -treeZ, i1, 3, true);
				addStructure(new GOTStructureDorneTownTrees(false), treeZ, i1, 1, true);
			}
			houseX = 64;
			int lampX = 59;
			for (int k1 = -4; k1 <= 4; ++k1) {
				int houseZ = k1 * 12;
				boolean treepiece = IntMath.mod(k1, 2) == 1;
				if (treepiece) {
					addStructure(new GOTStructureDorneVillageFarm.Tree(false), -houseX - 2, houseZ, 1, true);
					addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseX + 2, houseZ, 3, true);
				} else {
					addStructure(new GOTStructureDorneHouseLarge(false), -houseX, houseZ, 1, true);
					addStructure(new GOTStructureDorneHouseLarge(false), houseX, houseZ, 3, true);
				}
				if (treepiece) {
					addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseZ, -houseX - 2, 2, true);
				} else {
					addStructure(new GOTStructureDorneHouseLarge(false), houseZ, -houseX, 2, true);
				}
				if (Math.abs(k1) >= 2 && (!outerTavern || k1 <= 2)) {
					if (treepiece) {
						addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseZ, houseX + 2, 0, true);
					} else {
						addStructure(new GOTStructureDorneHouseLarge(false), houseZ, houseX, 0, true);
					}
				}
				addStructure(new GOTStructureDorneLampPost(false), -lampX, houseZ, 1, true);
				addStructure(new GOTStructureDorneLampPost(false), lampX, houseZ, 3, true);
				addStructure(new GOTStructureDorneLampPost(false), houseZ, lampX, 0, true);
				addStructure(new GOTStructureDorneLampPost(false), houseZ, -lampX, 2, true);
			}
			if (outerTavern) {
				addStructure(new GOTStructureDorneTavern(false), 44, houseX, 0, true);
			}
			int gardenX = 42;
			int gardenZ = 48;
			addStructure(new GOTStructureDorneVillageFarm.Tree(false), -gardenX, -gardenZ, 1, true);
			addStructure(new GOTStructureDorneVillageFarm.Tree(false), -gardenX, gardenZ, 1, true);
			addStructure(new GOTStructureDorneVillageFarm.Tree(false), gardenX, -gardenZ, 3, true);
			addStructure(new GOTStructureDorneVillageFarm.Tree(false), gardenX, gardenZ, 3, true);
			int obeliskX = 62;
			int obeliskZ = 66;
			addStructure(new GOTStructureDorneObelisk(false), -obeliskX, -obeliskZ, 1, true);
			addStructure(new GOTStructureDorneObelisk(false), -obeliskX, obeliskZ, 1, true);
			addStructure(new GOTStructureDorneObelisk(false), obeliskX, -obeliskZ, 3, true);
			addStructure(new GOTStructureDorneObelisk(false), obeliskX, obeliskZ, 3, true);
			wellX = 64;
			wellZ = 57;
			addStructure(new GOTStructureDorneWell(false), -wellX, -wellZ, 1, true);
			addStructure(new GOTStructureDorneWell(false), -wellX, wellZ, 1, true);
			addStructure(new GOTStructureDorneWell(false), wellX, -wellZ, 3, true);
			addStructure(new GOTStructureDorneWell(false), wellX, wellZ, 3, true);
			addStructure(new GOTStructureDorneWell(false), -wellZ, -wellX, 2, true);
			addStructure(new GOTStructureDorneWell(false), wellZ, -wellX, 2, true);
			addStructure(new GOTStructureDorneWell(false), -wellZ, wellX, 0, true);
			addStructure(new GOTStructureDorneWell(false), wellZ, wellX, 0, true);
			treeX = 75;
			treeZ = 61;
			addStructure(new GOTStructureDorneTownTrees(false), -treeX, -treeZ, 1, true);
			addStructure(new GOTStructureDorneTownTrees(false), -treeX, treeZ, 1, true);
			addStructure(new GOTStructureDorneTownTrees(false), treeX, -treeZ, 3, true);
			addStructure(new GOTStructureDorneTownTrees(false), treeX, treeZ, 3, true);
			addStructure(new GOTStructureDorneTownTrees(false), -treeZ, -treeX, 2, true);
			addStructure(new GOTStructureDorneTownTrees(false), treeZ, -treeX, 2, true);
			addStructure(new GOTStructureDorneTownTrees(false), -treeZ, treeX, 0, true);
			addStructure(new GOTStructureDorneTownTrees(false), treeZ, treeX, 0, true);
			addStructure(new GOTStructureDorneTownTrees(false), -14, 71, 1, true);
			addStructure(new GOTStructureDorneTownTrees(false), 14, 71, 3, true);
			for (int k1 : new int[]{67, 75}) {
				addStructure(new GOTStructureDorneTownBench(false), -10, k1, 1, true);
				addStructure(new GOTStructureDorneTownBench(false), 10, k1, 3, true);
			}
			addStructure(new GOTStructureDorneGatehouse(false), 0, 84, 2, true);
			addStructure(new GOTStructureDorneLampPost(false), -4, 73, 0, true);
			addStructure(new GOTStructureDorneLampPost(false), 4, 73, 0, true);
			int towerX = 78;
			int towerZ = 74;
			for (int i1 : new int[]{-towerX, towerX}) {
				addStructure(new GOTStructureDorneWatchtower(false), i1, -towerZ, 2, true);
				addStructure(new GOTStructureDorneWatchtower(false), i1, towerZ, 0, true);
			}
			int wallZ = 82;
			int wallEndX = 76;
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(getWallLeft(false), -wallX, wallZ, 2, true);
				addStructure(getWallRight(false), wallX, wallZ, 2, true);
			}
			addStructure(getWallLeftEndShort(false), -wallEndX, wallZ, 2, true);
			addStructure(getWallRightEndShort(false), wallEndX, wallZ, 2, true);
			addStructure(getWallCentre(false), -wallZ, 0, 3, true);
			addStructure(getWallCentre(false), wallZ, 0, 1, true);
			addStructure(getWallCentre(false), 0, -wallZ, 0, true);
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(getWallLeft(false), -wallZ, -wallX, 3, true);
				addStructure(getWallRight(false), -wallZ, wallX, 3, true);
				addStructure(getWallLeft(false), wallZ, wallX, 1, true);
				addStructure(getWallRight(false), wallZ, -wallX, 1, true);
				addStructure(getWallLeft(false), wallX, -wallZ, 0, true);
				addStructure(getWallRight(false), -wallX, -wallZ, 0, true);
			}
			addStructure(getWallLeftEnd(false), -wallZ, -wallEndX, 3, true);
			addStructure(getWallRightEnd(false), -wallZ, wallEndX, 3, true);
			addStructure(getWallLeftEnd(false), wallZ, wallEndX, 1, true);
			addStructure(getWallRightEnd(false), wallZ, -wallEndX, 1, true);
			addStructure(getWallLeftEndShort(false), wallEndX, -wallZ, 0, true);
			addStructure(getWallRightEndShort(false), -wallEndX, -wallZ, 0, true);
		}

		private void setupVillage(Random random) {
			addStructure(new GOTStructureDorneWell(false), 0, -4, 0, true);
			addStructure(new StructureRespawner5(), 0, 0, 0);
			addStructure(new StructureRespawner6(), 0, 0, 0);
			addStructure(new GOTStructureDorneHouse(false), -21, 0, 1);
			addStructure(new GOTStructureDorneHouse(false), 0, -21, 2);
			addStructure(new GOTStructureDorneHouse(false), 21, 0, 3);
			addStructure(new GOTStructureDorneTavern(false), 0, 21, 0);
			if (random.nextBoolean()) {
				if (random.nextBoolean()) {
					addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -9, -12, 1);
				}
				if (random.nextBoolean()) {
					addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 9, -12, 3);
				}
				if (random.nextBoolean()) {
					addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -9, 12, 1);
				}
				if (random.nextBoolean()) {
					addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 9, 12, 3);
				}
			}
			int houses = 20;
			float frac = 1.0f / houses;
			float turn = 0.0f;
			while (turn < 1.0f) {
				int k;
				int l;
				int i;
				float turnR = (float) Math.toRadians((turn += frac) * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turn * 8.0f;
				if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				if (random.nextBoolean()) {
					l = 61;
					i = Math.round(l * cos);
					k = Math.round(l * sin);
					addStructure(getRandomHouse(random), i, k, r);
					continue;
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				l = 65;
				i = Math.round(l * cos);
				k = Math.round(l * sin);
				addStructure(new GOTStructureHayBales(false), i, k, r);
			}
			int signPos = Math.round(50.0f * MathHelper.cos(0.7853981633974483f));
			int signDisp = Math.round(7.0f * MathHelper.cos(0.7853981633974483f));
			addStructure(new GOTStructureWesterosVillageSign(false), -signPos, -signPos + signDisp, 1);
			addStructure(new GOTStructureWesterosVillageSign(false), signPos, -signPos + signDisp, 3);
			addStructure(new GOTStructureWesterosVillageSign(false), -signPos, signPos - signDisp, 1);
			addStructure(new GOTStructureWesterosVillageSign(false), signPos, signPos - signDisp, 3);
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmX + farmSize, -farmZ, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmZ + farmSize, -farmX, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmX - farmSize, -farmZ, 3);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmZ - farmSize, -farmX, 3);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmX + farmSize, farmZ, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmX - farmSize, farmZ, 3);
			}
		}

		public void setType(Type type) {
			this.type = type;
		}

		private static class StructureRespawner1 extends GOTStructureNPCRespawner {
			private StructureRespawner1() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneSoldier.class);
				spawner.setCheckRanges(50, -12, 12, 16);
				spawner.setSpawnRanges(30, -6, 6, 40);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner2 extends GOTStructureNPCRespawner {
			private StructureRespawner2() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneSoldier.class);
				spawner.setCheckRanges(20, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 40);
				spawner.setBlockEnemySpawns(40);
			}
		}

		private static class StructureRespawner3 extends GOTStructureNPCRespawner {
			private StructureRespawner3() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneMan.class);
				spawner.setCheckRanges(80, -12, 12, 100);
				spawner.setSpawnRanges(60, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner4 extends GOTStructureNPCRespawner {
			private StructureRespawner4() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneSoldier.class);
				spawner.setCheckRanges(40, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner5 extends GOTStructureNPCRespawner {
			private StructureRespawner5() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneMan.class);
				spawner.setCheckRanges(40, -12, 12, 40);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner6 extends GOTStructureNPCRespawner {
			private StructureRespawner6() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDorneSoldier.class);
				spawner.setCheckRanges(40, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}
	}
}