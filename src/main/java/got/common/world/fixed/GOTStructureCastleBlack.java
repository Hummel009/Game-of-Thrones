package got.common.world.fixed;

import java.util.Random;

import got.common.entity.westeros.legendary.captain.GOTEntityJeorMormont;
import got.common.entity.westeros.legendary.quest.GOTEntitySamwellTarly;
import got.common.entity.westeros.legendary.reborn.GOTEntityJonSnow;
import got.common.entity.westeros.legendary.trader.GOTEntityAemonTargaryen;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle;
import net.minecraft.world.World;

public class GOTStructureCastleBlack extends GOTVillageGen {
	public GOTStructureCastleBlack(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Castle extends GOTStructureGiftCastle {
		public Castle(boolean flag) {
			super(flag);
		}

		@Override
		public int getOriginZ() {
			return 100;
		}

		@Override
		public void spawnLegendaryMobs(World world) {
			spawnLegendaryNPC(new GOTEntityJeorMormont(world), world, -3, 1, -3);
			spawnLegendaryNPC(new GOTEntityJonSnow.Stage1(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityAemonTargaryen(world), world, 3, 1, 3);
			spawnLegendaryNPC(new GOTEntityAlliserThorne(world), world, -3, 1, 3);
			spawnLegendaryNPC(new GOTEntityEdd(world), world, 3, 1, -3);
			spawnLegendaryNPC(new GOTEntitySamwellTarly(world), world, 0, 1, 3);
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureCastleBlack village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Castle(false), 0, 0, 0, true);
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
