package got.common.world.biome.westeros;

import java.util.*;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeDorneMesa extends GOTBiome {
	public byte[] clayMeta;
	public long seed;
	public NoiseGeneratorPerlin noise1;
	public NoiseGeneratorPerlin noise2;
	public NoiseGeneratorPerlin noise3;

	public GOTBiomeDorneMesa(int i, boolean major) {
		super(i, major);
		setupDesertFauna();
		topBlock = Blocks.sand;
		topBlockMeta = 1;
		fillerBlock = Blocks.stained_hardened_clay;
		theBiomeDecorator.treesPerChunk = -1;
		theBiomeDecorator.deadBushPerChunk = 20;
		theBiomeDecorator.reedsPerChunk = 3;
		theBiomeDecorator.cactiPerChunk = 5;
		theBiomeDecorator.flowersPerChunk = 0;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		if (clayMeta == null || seed != world.getSeed()) {
			generateNoise(world.getSeed());
		}
		if (noise1 == null || noise2 == null || seed != world.getSeed()) {
			Random random1 = new Random(seed);
			noise1 = new NoiseGeneratorPerlin(random1, 4);
			noise2 = new NoiseGeneratorPerlin(random1, 1);
		}
		seed = world.getSeed();
		double d5 = 0.0D;
		int g;
		int l;
		g = i & 15;
		l = k & 15;
		Block block = Blocks.stained_hardened_clay;
		Block block2 = fillerBlock;
		int i1 = (int) (stoneNoise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		boolean flag1 = Math.cos(stoneNoise / 3.0D * Math.PI) > 0.0D;
		int j1 = -1;
		boolean flag2 = false;
		int k1 = blocks.length / 256;
		for (int l1 = 255; l1 >= 0; --l1) {
			int i2 = (l * 16 + g) * k1 + l1;
			if ((blocks[i2] == null || blocks[i2].getMaterial() == Material.air) && l1 < (int) d5) {
				blocks[i2] = Blocks.stone;
			}
			if (l1 <= 0 + random.nextInt(5)) {
				blocks[i2] = Blocks.bedrock;
			} else {
				Block block1 = blocks[i2];
				if (block1 != null && block1.getMaterial() != Material.air) {
					if (block1 == Blocks.stone) {
						byte b0;
						if (j1 == -1) {
							flag2 = false;
							if (i1 <= 0) {
								block = null;
								block2 = Blocks.stone;
							} else if (l1 >= 59 && l1 <= 64) {
								block = Blocks.stained_hardened_clay;
								block2 = fillerBlock;
							}
							if (l1 < 63 && (block == null || block.getMaterial() == Material.air)) {
								block = Blocks.water;
							}
							j1 = i1 + Math.max(0, l1 - 63);
							if (l1 >= 62) {
								if (l1 > 66 + i1) {
									b0 = 16;
									if (l1 >= 64 && l1 <= 127) {
										if (!flag1) {
											b0 = levelGenerator(i, l1, k);
										}
									} else {
										b0 = 1;
									}
									if (b0 < 16) {
										blocks[i2] = Blocks.stained_hardened_clay;
										meta[i2] = b0;
									} else {
										blocks[i2] = Blocks.hardened_clay;
									}
								} else {
									blocks[i2] = topBlock;
									meta[i2] = (byte) topBlockMeta;
									flag2 = true;
								}
							} else {
								blocks[i2] = block2;
								if (block2 == Blocks.stained_hardened_clay) {
									meta[i2] = 1;
								}
							}
						} else if (j1 > 0) {
							--j1;
							if (flag2) {
								blocks[i2] = Blocks.stained_hardened_clay;
								meta[i2] = 1;
							} else {
								b0 = levelGenerator(i, l1, k);
								if (b0 < 16) {
									blocks[i2] = Blocks.stained_hardened_clay;
									meta[i2] = b0;
								} else {
									blocks[i2] = Blocks.hardened_clay;
								}
							}
						}
					}
				} else {
					j1 = -1;
				}
			}
		}
	}

	public void generateNoise(long n) {
		clayMeta = new byte[64];
		Arrays.fill(clayMeta, (byte) 16);
		Random random = new Random(n);
		noise3 = new NoiseGeneratorPerlin(random, 1);
		int j;
		for (j = 0; j < 64; ++j) {
			j += random.nextInt(5) + 1;
			if (j < 64) {
				clayMeta[j] = 1;
			}
		}
		j = random.nextInt(4) + 2;
		int k;
		int l;
		int i1;
		int j1;
		for (k = 0; k < j; ++k) {
			l = random.nextInt(3) + 1;
			i1 = random.nextInt(64);
			for (j1 = 0; i1 + j1 < 64 && j1 < l; ++j1) {
				clayMeta[i1 + j1] = 4;
			}
		}
		k = random.nextInt(4) + 2;
		int k1;
		for (l = 0; l < k; ++l) {
			i1 = random.nextInt(3) + 2;
			j1 = random.nextInt(64);
			for (k1 = 0; j1 + k1 < 64 && k1 < i1; ++k1) {
				clayMeta[j1 + k1] = 12;
			}
		}
		l = random.nextInt(4) + 2;
		for (i1 = 0; i1 < l; ++i1) {
			j1 = random.nextInt(3) + 1;
			k1 = random.nextInt(64);
			for (int l1 = 0; k1 + l1 < 64 && l1 < j1; ++l1) {
				clayMeta[k1 + l1] = 14;
			}
		}
		i1 = random.nextInt(3) + 3;
		j1 = 0;
		for (k1 = 0; k1 < i1; ++k1) {
			byte b0 = 1;
			j1 += random.nextInt(16) + 4;
			for (int i2 = 0; j1 + i2 < 64 && i2 < b0; ++i2) {
				clayMeta[j1 + i2] = 0;
				if (j1 + i2 > 1 && random.nextBoolean()) {
					clayMeta[j1 + i2 - 1] = 8;
				}
				if (j1 + i2 < 63 && random.nextBoolean()) {
					clayMeta[j1 + i2 + 1] = 8;
				}
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorneMesa;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("halfLife");
	}

	public byte levelGenerator(int a, int b, int c) {
		int l = (int) Math.round(noise3.func_151601_a(a * 1.0D / 512.0D, a * 1.0D / 512.0D) * 2.0D);
		return clayMeta[(b + l + 64) % 64];
	}
}