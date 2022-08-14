package got.common.world.biome.other;

import java.util.Random;

import got.GOT;
import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTRegistry;
import got.common.entity.animal.GOTEntitySeagull;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTWorldGenSeaBlock;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeOcean extends GOTBiome {
	public static Random iceRand = new Random();
	public WorldGenerator spongeGen = new GOTWorldGenSeaBlock(Blocks.sponge, 0, 24);
	public WorldGenerator coralGen = new GOTWorldGenSeaBlock(GOTRegistry.coralReef, 0, 64);
	public NoiseGeneratorPerlin noiseIceGravel = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);

	public GOTBiomeOcean(int i, boolean major) {
		super(i, major);
		setupStandartPlainsFauna();
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 4, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySeagull.class, 20, 4, 4));
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8), 4.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8, Blocks.sand), 0.5f, 56, 80);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8, GOTRegistry.whiteSand), 0.5f, 56, 80);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		i1 = i + random.nextInt(16) + 8;
		k1 = k + random.nextInt(16) + 8;
		j1 = world.getTopSolidOrLiquidBlock(i1, k1);
		if (j1 <= 43) {
			for (int l2 = 0; l2 < 50; ++l2) {
				int i3 = i + random.nextInt(16) + 8;
				int k3 = k + random.nextInt(16) + 8;
				int j3 = world.getTopSolidOrLiquidBlock(i3, k3);
				if (j3 <= 43 && (world.getBlock(i3, j3 - 1, k3) == Blocks.sand || world.getBlock(i3, j3 - 1, k3) == Blocks.dirt)) {
					int height = j3 + 4 + random.nextInt(4);
					for (int j2 = j3; j2 < height && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
						world.setBlock(i3, j2, k3, GOTRegistry.kelp);
					}
				}
			}
		}
		if (!isFrozen(i, k)) {
			if (random.nextInt(12) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1, k1)) < 60 || random.nextBoolean())) {
				spongeGen.generate(world, random, i1, j1, k1);
			}
			if (random.nextInt(4) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1, k1)) < 60 || random.nextBoolean())) {
				coralGen.generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.OCEAN.getSubregion("ocean");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.OCEAN;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	public static boolean isFrozen(int i, int k) {
		return k <= -23000 || k >= 490000;
	}
}
