package got.common.world.structure.westeros.gift;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.gift.GOTEntityGiftGuard;
import got.common.entity.westeros.gift.GOTEntityGiftMan;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle.Abandoned;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle.CastleBlack;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle.EastWatch;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle.ShadowTower;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureGiftSettlement extends GOTStructureBaseSettlement {
	public Type type;
	public boolean forcedType;

	public GOTStructureGiftSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 4;
		fixedSettlementChunkRadius = 4;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureGiftSettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc, type, forcedType);
	}

	public GOTStructureBaseSettlement type(Type t, int radius) {
		type = t;
		settlementChunkRadius = radius;
		fixedSettlementChunkRadius = radius;
		forcedType = true;
		return this;
	}

	public enum Type {
		ABANDONED, CASTLE_BLACK, SHADOW_TOWER, EAST_WATCH, VILLAGE
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureGiftSettlement> {
		public Type type;
		public boolean forcedType;

		public Instance(GOTStructureGiftSettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Type t, boolean b) {
			super(settlement, world, i, k, random, loc);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			switch (type) {
				case CASTLE_BLACK:
					addStructure(new GOTStructureGiftGate(false), 0, 7, 0, true);
					addStructure(new CastleBlack(false), -4, 25, 1, true);
					break;
				case SHADOW_TOWER:
					addStructure(new GOTStructureGiftGate(false), 0, 7, 0, true);
					addStructure(new ShadowTower(false), 0, 20, 0, true);
					break;
				case EAST_WATCH:
					addStructure(new EastWatch(false), 0, 50, 0, true);
					break;
				case ABANDONED:
					addStructure(new GOTStructureGiftGate(false).setIsAbandoned(), 0, 7, 0, true);
					addStructure(new Abandoned(false), 0, 20, 0, true);
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
				if (i1 <= 2 && k1 <= 2) {
					return null;
				}
				int imn = 19 + random.nextInt(3);
				if (dSq < imn * imn || k < 0 && k > -(19 + 12 + 16) && i1 <= 2 + random.nextInt(3)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(3) == 0) {
				int i = random.nextInt(3);
				switch (i) {
					case 0:
						return new GOTStructureGiftSmithy(false);
					case 1:
						return new GOTStructureGiftStables(false);
					default:
						return new GOTStructureGiftLodge(false);
				}
			}
			return new GOTStructureGiftHouse(false);
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (!forcedType) {
				type = Type.VILLAGE;
			}
		}

		public void setupVillage(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityGiftMan.class);
					spawner.setCheckRanges(40, -12, 12, 30);
					spawner.setSpawnRanges(20, -6, 6, 64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityGiftGuard.class);
					spawner.setCheckRanges(40, -12, 12, 12);
					spawner.setSpawnRanges(20, -6, 6, 64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureGiftWell(false), 0, -2, 0, true);
			int lampX = 8;
			for (int i : new int[]{-lampX, lampX}) {
				for (int k : new int[]{-lampX, lampX}) {
					addStructure(new GOTStructureGiftVillageLight(false), i, k, 0);
				}
			}
			int houses = 20;
			float frac = 1.0f / houses;
			float turn = 0.0f;
			while (turn < 1.0f) {
				int l;
				int k;
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
				if (sin < 0.0f && Math.abs(cos) <= 0.5f) {
					continue;
				}
				if (random.nextInt(3) != 0) {
					l = 19 + 3;
					if (random.nextInt(3) == 0) {
						l += 12;
					}
					i = Math.round(l * cos);
					k = Math.round(l * sin);
					addStructure(getRandomHouse(random), i, k, r);
					continue;
				}
				if (random.nextInt(4) != 0) {
					continue;
				}
				l = 19 + 5;
				if (random.nextInt(3) == 0) {
					l += 12;
				}
				i = Math.round(l * cos);
				k = Math.round(l * sin);
				addStructure(new GOTStructureHayBales(false), i, k, r);
			}
			int rPalisade = 19 + 12 + 16;
			int rSq = rPalisade * rPalisade;
			int rMax = rPalisade + 1;
			int rSqMax = rMax * rMax;
			for (int i = -rPalisade; i <= rPalisade; ++i) {
				for (int k = -rPalisade; k <= rPalisade; ++k) {
					int dSq;
					if (Math.abs(i) <= 5 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
						continue;
					}
					addStructure(new GOTStructureGiftVillagePalisade(false), i, k, 0);
				}
			}
		}
	}
}