package got.common.world.biome.other;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTRegistry;
import got.common.entity.animal.GOTEntitySeagull;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeOcean extends GOTBiome {
	public static Random iceRand = new Random();
	public static int iceLimitSouth = -30000;
	public static int iceLimitNorth = -60000;
	public static int palmStartZ = 64000;
	public static int palmFullZ = 130000;
	public WorldGenerator spongeGen = new GOTWorldGenSeaBlock(Blocks.sponge, 0, 24);
	public WorldGenerator coralGen = new GOTWorldGenSeaBlock(GOTRegistry.coralReef, 0, 64);

	public GOTBiomeOcean(int i, boolean major) {
		super(i, major);
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 4, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySeagull.class, 20, 4, 4));
		npcSpawnList.clear();
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8), 4.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8, Blocks.sand), 0.5f, 56, 80);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreSalt, 8, GOTRegistry.whiteSand), 0.5f, 56, 80);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		if (k > -30000) {
			if (random.nextInt(12) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
				spongeGen.generate(world, random, i1, j1, k1);
			}
			if (random.nextInt(4) == 0 && ((j1 = world.getTopSolidOrLiquidBlock(i1 = i + random.nextInt(16) + 8, k1 = k + random.nextInt(16) + 8)) < 60 || random.nextBoolean())) {
				coralGen.generate(world, random, i1, j1, k1);
			}
		}
		if (k >= 64000) {
			float chance;
			chance = k >= 130000 ? 1.0f : (k - 64000) / 66000.0f;
			if (random.nextFloat() < chance && random.nextInt(6) == 0) {
				int palms = 1 + random.nextInt(2);
				if (random.nextInt(3) == 0) {
					++palms;
				}
				for (int l = 0; l < palms; ++l) {
					int j12;
					int k12;
					int i12 = i + random.nextInt(16) + 8;
					if (!world.getBlock(i12, j12 = world.getTopSolidOrLiquidBlock(i12, k12 = k + random.nextInt(16) + 8) - 1, k12).isNormalCube() || !GOTStructureBase.isSurfaceStatic(world, i12, j12, k12)) {
						continue;
					}
					Block prevBlock = world.getBlock(i12, j12, k12);
					int prevMeta = world.getBlockMetadata(i12, j12, k12);
					world.setBlock(i12, j12, k12, Blocks.dirt, 0, 2);
					WorldGenAbstractTree palmGen = GOTTreeType.PALM.create(false, random);
					if (palmGen.generate(world, random, i12, j12 + 1, k12)) {
						continue;
					}
					world.setBlock(i12, j12, k12, prevBlock, prevMeta, 2);
				}
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
		if (k > -30000) {
			return false;
		}
		int l = -60000 - k;
		if ((l *= -1) < 1) {
			return true;
		}
		iceRand.setSeed(i * 341873128712L + k * 132897987541L);
		if ((l -= Math.abs(-30000) / 2) < 0) {
			l *= -1;
			if ((l = (int) Math.sqrt(l)) < 2) {
				l = 2;
			}
			return iceRand.nextInt(l) != 0;
		}
		if ((l = (int) Math.sqrt(l)) < 2) {
			l = 2;
		}
		return iceRand.nextInt(l) == 0;
	}
}
