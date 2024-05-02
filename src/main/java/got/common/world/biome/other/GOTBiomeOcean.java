package got.common.world.biome.other;

import got.GOT;
import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.entity.animal.GOTEntitySeagull;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTWorldGenSeaBlock;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeOcean extends GOTBiome {
	private static final WorldGenerator SPONGE_GEN = new GOTWorldGenSeaBlock(Blocks.sponge, 0, 24);
	private static final WorldGenerator CORAL_GEN = new GOTWorldGenSeaBlock(GOTBlocks.coralReef, 0, 64);

	public GOTBiomeOcean(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 4, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySeagull.class, 20, 4, 4));
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreSalt, 8), 4.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreSalt, 8, Blocks.sand), 0.5f, 56, 80);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreSalt, 8, GOTBlocks.whiteSand), 0.5f, 56, 80);
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}

	public static boolean isFrozen(int k) {
		return k <= -23000 || k >= 490000;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		int i1 = i + random.nextInt(16) + 8;
		int k1 = k + random.nextInt(16) + 8;
		int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
		if (j1 <= 43) {
			for (int l2 = 0; l2 < 50; ++l2) {
				int i3 = i + random.nextInt(16) + 8;
				int k3 = k + random.nextInt(16) + 8;
				int j3 = world.getTopSolidOrLiquidBlock(i3, k3);
				if (j3 <= 43 && (world.getBlock(i3, j3 - 1, k3) == Blocks.sand || world.getBlock(i3, j3 - 1, k3) == Blocks.dirt)) {
					int height = j3 + 4 + random.nextInt(4);
					for (int j2 = j3; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
						world.setBlock(i3, j2, k3, GOTBlocks.kelp);
					}
				}
			}
		}
		if (!isFrozen(k)) {
			if (random.nextInt(12) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1, k1)) < 60 || random.nextBoolean())) {
				SPONGE_GEN.generate(world, random, i1, j1, k1);
			}
			if (random.nextInt(4) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1, k1)) < 60 || random.nextBoolean())) {
				CORAL_GEN.generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.OCEAN.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.OCEAN;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}