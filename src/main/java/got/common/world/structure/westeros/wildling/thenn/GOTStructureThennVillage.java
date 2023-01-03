package got.common.world.structure.westeros.wildling.thenn;

import java.util.Random;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.wildling.thenn.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyWell;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureThennVillage extends GOTVillageGen {
	public GOTStructureThennVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
		fixedVillageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public class Instance extends GOTVillageGen.AbstractInstance {

		public Instance(GOTStructureThennVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityThenn.class, GOTEntityThennBerserker.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityThennAxeThrower.class, GOTEntityThennArcher.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			this.addStructure(new GOTStructureMossovyWell(false), 0, -2, 0, true);
			this.addStructure(new GOTStructureThennHouse(false).setIsBlacksmith(), 0, -centreSide, 2, true);
			this.addStructure(new GOTStructureThennHouse(false), -pathEnd, 0, 1, true);
			this.addStructure(new GOTStructureThennMagnarHouse(false), pathEnd, 0, 3, true);
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					this.addStructure(new GOTStructureThennHouse(false), i1, -k1, 2);
				}
				this.addStructure(new GOTStructureThennHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					this.addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
				}
				this.addStructure(new GOTStructureHayBales(false), i1, k2, 0);
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
			return block == Blocks.snow;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}

	}

}
