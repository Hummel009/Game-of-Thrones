package got.common.world.biome;

import java.awt.Color;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.GOTDimension;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.essos.GOTEntityEssosBandit;
import got.common.entity.westeros.GOTEntityWesterosBandit;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.essos.*;
import got.common.world.biome.other.*;
import got.common.world.biome.sothoryos.*;
import got.common.world.biome.ulthos.*;
import got.common.world.biome.variant.*;
import got.common.world.biome.westeros.*;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.util.EnumHelper;

public abstract class GOTBiome extends BiomeGenBase {
	public static Class[][] correctCreatureTypeParams = { { EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE } };
	public static EnumCreatureType creatureType_GOTAmbient = EnumHelper.addEnum(correctCreatureTypeParams, EnumCreatureType.class, "GOTAmbient", GOTAmbientCreature.class, 45, Material.air, true, false);
	public static NoiseGeneratorPerlin biomeTerrainNoise = new NoiseGeneratorPerlin(new Random(1955L), 1);
	public static Random terrainRand = new Random();
	public static float defaultVariantChance = 0.4f;
	public static Color waterColorNorth = new Color(602979);
	public static Color waterColorSouth = new Color(4973293);
	public static int waterLimitNorth = -40000;
	public static int waterLimitSouth = 160000;
	public static int SPAWN = 600;
	public static int CONQUEST_SPAWN = 100;
	public static int[] NON_USED = { 134, 141, 149, 153 };
	public static GOTBiome alwaysWinter;
	public static GOTBiome arryn;
	public static GOTBiome arrynMountains;
	public static GOTBiome arrynMountainsFoothills;
	public static GOTBiome arrynTown;
	public static GOTBiome astapor;
	public static GOTBiome beach;
	public static GOTBiome beachGravel;
	public static GOTBiome beachRed;
	public static GOTBiome beachWhite;
	public static GOTBiome boneMountains;
	public static GOTBiome braavos;
	public static GOTBiome braavosHills;
	public static GOTBiome cannibalSands;
	public static GOTBiome cannibalSandsHills;
	public static GOTBiome coldCoast;
	public static GOTBiome crackclaw;
	public static GOTBiome crownlands;
	public static GOTBiome crownlandsForest;
	public static GOTBiome crownlandsTown;
	public static GOTBiome dorne;
	public static GOTBiome dorneDesert;
	public static GOTBiome dorneMountains;
	public static GOTBiome dorneValley;
	public static GOTBiome dothrakiHills;
	public static GOTBiome dothrakiSea;
	public static GOTBiome dragonstone;
	public static GOTBiome essos;
	public static GOTBiome essosForest;
	public static GOTBiome essosMarshes;
	public static GOTBiome essosMountains;
	public static GOTBiome farNorthSnowy;
	public static GOTBiome fireField;
	public static GOTBiome frostfangs;
	public static GOTBiome ghiscar;
	public static GOTBiome ghiscarColony;
	public static GOTBiome giftNew;
	public static GOTBiome giftOld;
	public static GOTBiome hauntedForest;
	public static GOTBiome ibben;
	public static GOTBiome ibbenBeach;
	public static GOTBiome ibbenColony;
	public static GOTBiome ibbenColonyHills;
	public static GOTBiome ibbenMountains;
	public static GOTBiome ibbenTaiga;
	public static GOTBiome ifekevronForest;
	public static GOTBiome ironborn;
	public static GOTBiome ironbornHills;
	public static GOTBiome irontree;
	public static GOTBiome island;
	public static GOTBiome isleOfFaces;
	public static GOTBiome jogosNhai;
	public static GOTBiome jogosNhaiDesert;
	public static GOTBiome jogosNhaiHills;
	public static GOTBiome kingSpears;
	public static GOTBiome lake;
	public static GOTBiome lhazar;
	public static GOTBiome lhazarHills;
	public static GOTBiome longSummer;
	public static GOTBiome lorath;
	public static GOTBiome lorathMaze;
	public static GOTBiome lys;
	public static GOTBiome massy;
	public static GOTBiome meereen;
	public static GOTBiome mercenary;
	public static GOTBiome mossovy;
	public static GOTBiome mossovyForest;
	public static GOTBiome mossovyMarshes;
	public static GOTBiome mossovyMountains;
	public static GOTBiome myr;
	public static GOTBiome naath;
	public static GOTBiome neck;
	public static GOTBiome newGhis;
	public static GOTBiome north;
	public static GOTBiome northBarrows;
	public static GOTBiome northForest;
	public static GOTBiome northHills;
	public static GOTBiome northMountains;
	public static GOTBiome northTown;
	public static GOTBiome northWild;
	public static GOTBiome norvos;
	public static GOTBiome norvosHills;
	public static GOTBiome ocean1;
	public static GOTBiome ocean2;
	public static GOTBiome ocean3;
	public static GOTBiome ocean;
	public static GOTBiome pentos;
	public static GOTBiome pentosHills;
	public static GOTBiome qarth;
	public static GOTBiome qarthColony;
	public static GOTBiome qarthDesert;
	public static GOTBiome qohor;
	public static GOTBiome qohorForest;
	public static GOTBiome rainwood;
	public static GOTBiome reach;
	public static GOTBiome reachArbor;
	public static GOTBiome reachTown;
	public static GOTBiome redMesa;
	public static GOTBiome redSea;
	public static GOTBiome river;
	public static GOTBiome riverlands;
	public static GOTBiome riverlandsHills;
	public static GOTBiome shadowLand;
	public static GOTBiome shadowMountains;
	public static GOTBiome shadowTown;
	public static GOTBiome shrykesLand;
	public static GOTBiome skagos;
	public static GOTBiome skirlingPass;
	public static GOTBiome sothoryosBushland;
	public static GOTBiome sothoryosDesert;
	public static GOTBiome sothoryosDesertCold;
	public static GOTBiome sothoryosDesertHills;
	public static GOTBiome sothoryosFrost;
	public static GOTBiome sothoryosHell;
	public static GOTBiome sothoryosJungle;
	public static GOTBiome sothoryosJungleEdge;
	public static GOTBiome sothoryosKanuka;
	public static GOTBiome sothoryosMangrove;
	public static GOTBiome sothoryosMountains;
	public static GOTBiome sothoryosSavannah;
	public static GOTBiome sothoryosTaiga;
	public static GOTBiome stepstones;
	public static GOTBiome stoneCoast;
	public static GOTBiome stormlands;
	public static GOTBiome stormlandsTown;
	public static GOTBiome summerColony;
	public static GOTBiome summerColonyMangrove;
	public static GOTBiome summerIslands;
	public static GOTBiome tarth;
	public static GOTBiome thenn;
	public static GOTBiome tropicalForest;
	public static GOTBiome tyrosh;
	public static GOTBiome ulthos;
	public static GOTBiome ulthosDesert;
	public static GOTBiome ulthosDesertCold;
	public static GOTBiome ulthosForest;
	public static GOTBiome ulthosForestEdge;
	public static GOTBiome ulthosFrost;
	public static GOTBiome ulthosMarshes;
	public static GOTBiome ulthosMarshesForest;
	public static GOTBiome ulthosMountains;
	public static GOTBiome ulthosRedForest;
	public static GOTBiome ulthosRedForestEdge;
	public static GOTBiome ulthosTaiga;
	public static GOTBiome ulthosTaigaEdge;
	public static GOTBiome valyria;
	public static GOTBiome valyriaSea;
	public static GOTBiome valyriaVolcano;
	public static GOTBiome volantis;
	public static GOTBiome volantisForest;
	public static GOTBiome volantisMarshes;
	public static GOTBiome westerlands;
	public static GOTBiome westerlandsHills;
	public static GOTBiome westerlandsTown;
	public static GOTBiome westerosForest;
	public static GOTBiome wetwood;
	public static GOTBiome whisperingWood;
	public static GOTBiome wolfswood;
	public static GOTBiome yeen;
	public static GOTBiome yiTi;
	public static GOTBiome yiTiMarshes;
	public static GOTBiome yiTiWasteland;
	public static GOTBiome yunkai;

	public GOTBiomeDecorator decorator;
	public int topBlockMeta = 0;
	public int fillerBlockMeta = 0;
	public float heightBaseParameter;
	public boolean enablePodzol = true;
	public boolean enableRocky = false;
	public GOTBiomeVariantList biomeVariantsLarge = new GOTBiomeVariantList();
	public GOTBiomeVariantList biomeVariantsSmall = new GOTBiomeVariantList();
	public GOTBiomeSpawnList npcSpawnList = new GOTBiomeSpawnList(this);
	public List spawnableGOTAmbientList = new ArrayList<>();
	public GOTEventSpawner.EventChance banditChance;
	public Class<? extends GOTEntityWesterosBandit> banditEntityClass;
	public GOTBiomeInvasionSpawns invasionSpawns;
	public BiomeColors biomeColors = new BiomeColors(this);
	public BiomeTerrain biomeTerrain = new BiomeTerrain(this);
	public float variantChance = 0.4f;
	public GOTClimateType climateType;
	public GOTDimension biomeDimension;

	public GOTBiome(int i, boolean major) {
		this(i, major, GOTDimension.GAME_OF_THRONES);
	}

	public GOTBiome(int i, boolean major, GOTDimension dim) {
		super(i, false);
		biomeDimension = dim;
		if (biomeDimension.biomeList[i] != null) {
			throw new IllegalArgumentException("GOT biome already exists at index " + i + " for dimension " + biomeDimension.dimensionName + "!");
		}
		biomeDimension.biomeList[i] = this;
		if (major) {
			biomeDimension.majorBiomes.add(this);
		}
		waterColorMultiplier = BiomeColors.DEFAULT_WATER;
		decorator = new GOTBiomeDecorator(this);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableCaveCreatureList.clear();
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityFish.class, 10, 4, 4));
		spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
		setUnreliableChance(GOTEventSpawner.EventChance.COMMON);
		invasionSpawns = new GOTBiomeInvasionSpawns(this);
		GOTFixer.affixWaypointLocations(this);
	}

	public void addBiomeF3Info(List info, World world, GOTBiomeVariant variant) {
		info.add("Game of Thrones biome: " + getBiomeDisplayName() + ", ID: " + biomeID + ";");
		info.add("Variant: " + StatCollector.translateToLocal(variant.getUnlocalizedName()) + ", loaded: " + GOTBiomeVariantStorage.getSize(world));
	}

	public void addBiomeVariant(GOTBiomeVariant v) {
		addBiomeVariant(v, 1.0f);
	}

	public void addBiomeVariant(GOTBiomeVariant v, float f) {
		biomeVariantsSmall.add(v, f);
	}

	public void addBiomeVariantSet(GOTBiomeVariant[] set) {
		for (GOTBiomeVariant v : set) {
			addBiomeVariant(v);
		}
	}

	public boolean canSpawnHostilesInDay() {
		return false;
	}

	@Override
	public boolean canSpawnLightningBolt() {
		return !getEnableSnow() && super.canSpawnLightningBolt();
	}

	public void clearBiomeVariants() {
		biomeVariantsLarge.clear();
		biomeVariantsSmall.clear();
	}

	@Override
	public BiomeGenBase createMutation() {
		return this;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		decorator.decorate(world, random, i, k);
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random random) {
		GOTTreeType tree = decorator.getRandomTree(random);
		return tree.create(false, random);
	}

	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		int seaLevel = 63;
		double stoneNoiseFiller = modifyStoneNoiseForFiller(stoneNoise);
		int fillerDepthBase = (int) (stoneNoiseFiller / 4.0 + 5.0 + random.nextDouble() * 0.25);
		int fillerDepth = -1;
		Block top = topBlock;
		byte topMeta = (byte) topBlockMeta;
		Block filler = fillerBlock;
		byte fillerMeta = (byte) fillerBlockMeta;
		if (enableRocky && height >= 90) {
			float hFactor = (height - 90) / 10.0f;
			float thresh = 1.2f - hFactor * 0.2f;
			thresh = Math.max(thresh, 0.0f);
			double d12 = biomeTerrainNoise.func_151601_a(i * 0.03, k * 0.03);
			if (d12 + biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3) > thresh) {
				if (random.nextInt(5) == 0) {
					top = Blocks.gravel;
				} else {
					top = Blocks.stone;
				}
				topMeta = 0;
				filler = Blocks.stone;
				fillerMeta = 0;
			}
		}
		if (enablePodzol) {
			boolean podzol = false;
			if (topBlock == Blocks.grass) {
				float trees = decorator.treesPerChunk + getTreeIncreaseChance();
				trees = Math.max(trees, variant.treeFactor * 0.5f);
				if (trees >= 1.0f) {
					float thresh = 0.8f;
					thresh -= trees * 0.15f;
					thresh = Math.max(thresh, 0.0f);
					double d = 0.06;
					double randNoise = biomeTerrainNoise.func_151601_a(i * d, k * d);
					if (randNoise > thresh) {
						podzol = true;
					}
				}
			}
			if (podzol) {
				terrainRand.setSeed(world.getSeed());
				terrainRand.setSeed(terrainRand.nextLong() + i * 4668095025L + k * 1387590552L ^ world.getSeed());
				float pdzRand = terrainRand.nextFloat();
				if (pdzRand < 0.35f) {
					top = Blocks.dirt;
					topMeta = 2;
				} else if (pdzRand < 0.5f) {
					top = Blocks.dirt;
					topMeta = 1;
				} else if (pdzRand < 0.51f) {
					top = Blocks.gravel;
					topMeta = 0;
				}
			}
		}
		if (variant.hasMarsh && GOTBiomeVariant.marshNoise.func_151601_a(i * 0.1, k * 0.1) > -0.1) {
			for (int j = ySize - 1; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				if (blocks[index] == null || blocks[index].getMaterial() != Material.air) {
					if (j != seaLevel - 1 || blocks[index] == Blocks.water) {
						break;
					}
					blocks[index] = Blocks.water;
					break;
				}
			}
		}
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			if (j <= 0 + random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
			} else {

				Block block = blocks[index];
				if (block == Blocks.air) {
					fillerDepth = -1;
				} else if (block == Blocks.stone) {
					if (fillerDepth == -1) {
						if (fillerDepthBase <= 0) {
							top = Blocks.air;
							topMeta = 0;
							filler = Blocks.stone;
							fillerMeta = 0;
						} else if (j >= seaLevel - 4 && j <= seaLevel + 1) {
							top = topBlock;
							topMeta = (byte) topBlockMeta;
							filler = fillerBlock;
							fillerMeta = (byte) fillerBlockMeta;
						}
						if (j < seaLevel && top == Blocks.air) {
							top = Blocks.water;
							topMeta = 0;
						}
						fillerDepth = fillerDepthBase;
						if (j >= seaLevel - 1) {
							blocks[index] = top;
							meta[index] = topMeta;
						} else {
							blocks[index] = filler;
							meta[index] = fillerMeta;
						}
					} else if (fillerDepth > 0) {
						blocks[index] = filler;
						meta[index] = fillerMeta;
						if (--fillerDepth == 0) {
							boolean sand = false;
							if (filler == Blocks.sand) {
								if (fillerMeta == 1) {
									filler = GOTRegistry.redSandstone;
								} else {
									filler = Blocks.sandstone;
								}
								fillerMeta = 0;
								sand = true;
							}
							if (filler == GOTRegistry.whiteSand) {
								filler = GOTRegistry.whiteSandstone;
								fillerMeta = 0;
								sand = true;
							}
							if (sand) {
								fillerDepth = 10 + random.nextInt(4);
							}
						}
						if (fillerDepth == 0 && fillerBlock != GOTRegistry.rock && filler == fillerBlock) {
							fillerDepth = 6 + random.nextInt(3);
							filler = Blocks.stone;
							fillerMeta = 0;
						}
					}
				}
			}

		}
		int rockDepth = (int) (stoneNoise * 6.0 + 2.0 + random.nextDouble() * 0.25);
		generateMountainTerrain(world, random, blocks, meta, i, k, xzIndex, ySize, height, rockDepth, variant);
		variant.generateVariantTerrain(world, random, blocks, meta, i, k, height, this);
	}

	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
	}

	public GOTEventSpawner.EventChance getBanditChance() {
		return banditChance;
	}

	public Class<? extends GOTEntityWesterosBandit> getBanditEntityClass() {
		if (banditEntityClass == null) {
			return GOTEntityWesterosBandit.class;
		}
		return banditEntityClass;
	}

	@SideOnly(value = Side.CLIENT)
	public int getBaseFoliageColor(int i, int j, int k) {
		GOTBiomeVariant variant = ((GOTWorldChunkManager) GOT.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
		float temp = getFloatTemperature(i, j, k) + variant.tempBoost;
		float rain = rainfall + variant.rainBoost;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerFoliage.getFoliageColor(temp, rain);
	}

	@SideOnly(value = Side.CLIENT)
	public int getBaseGrassColor(int i, int j, int k) {
		GOTBiomeVariant variant = ((GOTWorldChunkManager) GOT.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
		float temp = getFloatTemperature(i, j, k) + variant.tempBoost;
		float rain = rainfall + variant.rainBoost;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerGrass.getGrassColor(temp, rain);
	}

	@SideOnly(value = Side.CLIENT)
	public int getBaseSkyColorByTemp(int i, int j, int k) {
		return super.getSkyColorByTemp(getFloatTemperature(i, j, k));
	}

	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	public BiomeColors getBiomeColors() {
		return biomeColors;
	}

	public GOTDimension getBiomeDimension() {
		return biomeDimension;
	}

	public String getBiomeDisplayName() {
		return StatCollector.translateToLocal("got.biome." + biomeName);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getBiomeFoliageColor(int i, int j, int k) {
		return biomeColors.foliage != null ? biomeColors.foliage.getRGB() : getBaseFoliageColor(i, j, k);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getBiomeGrassColor(int i, int j, int k) {
		return biomeColors.grass != null ? biomeColors.grass.getRGB() : getBaseGrassColor(i, j, k);
	}

	public abstract MusicRegion getBiomeMusic();

	public BiomeTerrain getBiomeTerrain() {
		return biomeTerrain;
	}

	public GOTBiomeVariantList getBiomeVariantsLarge() {
		return biomeVariantsLarge;
	}

	public GOTBiomeVariantList getBiomeVariantsSmall() {
		return biomeVariantsSmall;
	}

	public Region getBiomeWaypoints() {
		return null;
	}

	public GOTBezierType.BridgeType getBridgeBlock() {
		return GOTBezierType.BridgeType.DEFAULT;
	}

	public float getChanceToSpawnAnimals() {
		return 0.2f;
	}

	public GOTClimateType getClimateType() {
		return climateType;
	}

	public Vec3 getCloudColor(Vec3 clouds) {
		if (biomeColors.clouds != null) {
			float[] colors = biomeColors.clouds.getColorComponents(null);
			clouds.xCoord *= colors[0];
			clouds.yCoord *= colors[1];
			clouds.zCoord *= colors[2];
		}
		return clouds;
	}

	public boolean getEnableRain() {
		return enableRain;
	}

	public boolean getEnableRiver() {
		return true;
	}

	public int getFillerBlockMeta() {
		return fillerBlockMeta;
	}

	public Vec3 getFogColor(Vec3 fog) {
		if (biomeColors.fog != null) {
			float[] colors = biomeColors.fog.getColorComponents(null);
			fog.xCoord *= colors[0];
			fog.yCoord *= colors[1];
			fog.zCoord *= colors[2];
		}
		return fog;
	}

	public float getHeightBaseParameter() {
		return heightBaseParameter;
	}

	public GOTBiomeInvasionSpawns getInvasionSpawns() {
		return invasionSpawns;
	}

	public String getName() {
		return StatCollector.translateToLocal("got.biome." + biomeName);
	}

	public GOTBiomeSpawnList getNpcSpawnList() {
		return npcSpawnList;
	}

	public GOTBiomeSpawnList getNPCSpawnList() {
		return npcSpawnList;
	}

	public BiomeGenBase.FlowerEntry getRandomFlower(Random random) {
		return (BiomeGenBase.FlowerEntry) WeightedRandom.getRandomItem(random, flowers);
	}

	public GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(5) == 0) {
			return new GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		if (random.nextInt(30) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.plantain, 2);
		}
		if (random.nextInt(200) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.tallGrass, 3);
		}
		if (random.nextInt(16) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.tallGrass, 1);
		}
		if (random.nextInt(10) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.tallGrass, 2);
		}
		if (random.nextInt(80) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.tallGrass, 4);
		}
		if (random.nextInt(2) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.tallGrass, 0);
		}
		if (random.nextInt(3) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.clover, 0);
		}
		return new GrassBlockAndMeta(Blocks.tallgrass, 1);
	}

	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
		int i = random.nextInt(3);
		switch (i) {
		case 0: {
			doubleFlowerGen.func_150548_a(1);
			break;
		}
		case 1: {
			doubleFlowerGen.func_150548_a(4);
			break;
		}
		case 2: {
			doubleFlowerGen.func_150548_a(5);
			break;
		}
		default:
			break;
		}
		return doubleFlowerGen;
	}

	public WorldGenerator getRandomWorldGenForDoubleGrass() {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		generator.func_150548_a(2);
		return generator;
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		GrassBlockAndMeta obj = getRandomGrass(random);
		return new WorldGenTallGrass(obj.block, obj.meta);
	}

	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float f) {
		if (biomeColors.sky != null) {
			return biomeColors.sky.getRGB();
		}
		return super.getSkyColorByTemp(f);
	}

	public int getSnowHeight() {
		return 0;
	}

	public List getSpawnableGOTAmbientList() {
		return spawnableGOTAmbientList;
	}

	@Override
	public List getSpawnableList(EnumCreatureType creatureType) {
		if (creatureType == creatureType_GOTAmbient) {
			return spawnableGOTAmbientList;
		}
		return super.getSpawnableList(creatureType);
	}

	public int getTopBlockMeta() {
		return topBlockMeta;
	}

	public WorldGenAbstractTree getTreeGen(World world, Random random, int i, int k) {
		GOTWorldChunkManager chunkManager = (GOTWorldChunkManager) world.getWorldChunkManager();
		GOTBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
		GOTTreeType tree = decorator.getRandomTreeForVariant(random, variant);
		return tree.create(false, random);
	}

	public float getTreeIncreaseChance() {
		return 0.1f;
	}

	public GOTEventSpawner.EventChance getUnreliableChance() {
		return banditChance;
	}

	public float getVariantChance() {
		return variantChance;
	}

	public GOTBezierType getWallBlock() {
		return GOTBezierType.ICE;
	}

	public int getWallTop() {
		return 0;
	}

	public boolean hasFog() {
		return biomeColors.foggy;
	}

	public boolean hasSky() {
		return true;
	}

	public boolean isEnablePodzol() {
		return enablePodzol;
	}

	public boolean isEnableRocky() {
		return enableRocky;
	}

	public boolean isRiver() {
		return false;
	}

	public boolean isWateryBiome() {
		return heightBaseParameter < 0.0f;
	}

	public double modifyStoneNoiseForFiller(double stoneNoise) {
		return stoneNoise;
	}

	public void setBanditChance(GOTEventSpawner.EventChance banditChance) {
		this.banditChance = banditChance;
	}

	public void setBanditEntityClass(Class<? extends GOTEntityWesterosBandit> banditEntityClass) {
		this.banditEntityClass = banditEntityClass;
	}

	public void setBiomeColors(BiomeColors biomeColors) {
		this.biomeColors = biomeColors;
	}

	public void setBiomeDimension(GOTDimension biomeDimension) {
		this.biomeDimension = biomeDimension;
	}

	@Override
	public GOTBiome setBiomeName(String s) {
		return (GOTBiome) super.setBiomeName(s);
	}

	public void setBiomeTerrain(BiomeTerrain biomeTerrain) {
		this.biomeTerrain = biomeTerrain;
	}

	public void setBiomeVariantsLarge(GOTBiomeVariantList biomeVariantsLarge) {
		this.biomeVariantsLarge = biomeVariantsLarge;
	}

	public void setBiomeVariantsSmall(GOTBiomeVariantList biomeVariantsSmall) {
		this.biomeVariantsSmall = biomeVariantsSmall;
	}

	public void setClimate(GOTClimateType climate) {
		climateType = climate;
	}

	public GOTBiome setClimateType(GOTClimateType type) {
		climateType = type;
		decorator.generateAgriculture = type == GOTClimateType.SUMMER;
		return this;
	}

	@Override
	public GOTBiome setColor(int color) {
		color |= 0xFF000000;
		Integer existingBiomeID = biomeDimension.colorsToBiomeIDs.get(color);
		if (existingBiomeID != null) {
			throw new RuntimeException("GOT biome (ID " + biomeID + ") is duplicating the color of another GOT biome (ID " + existingBiomeID + ")");
		}
		biomeDimension.colorsToBiomeIDs.put(color, biomeID);
		return (GOTBiome) super.setColor(color);
	}

	public void setDarkUnreliable() {
		banditEntityClass = GOTEntityEssosBandit.class;
	}

	public GOTBiome setDisableSnow() {
		enableSnow = false;
		return this;
	}

	public void setEnablePodzol(boolean enablePodzol) {
		this.enablePodzol = enablePodzol;
	}

	public void setEnableRocky(boolean enableRocky) {
		this.enableRocky = enableRocky;
	}

	public void setFillerBlockMeta(int fillerBlockMeta) {
		this.fillerBlockMeta = fillerBlockMeta;
	}

	public void setHeightBaseParameter(float heightBaseParameter) {
		this.heightBaseParameter = heightBaseParameter;
	}

	public void setInvasionSpawns(GOTBiomeInvasionSpawns invasionSpawns) {
		this.invasionSpawns = invasionSpawns;
	}

	public GOTBiome setMinMaxHeight(float f, float f1) {
		heightBaseParameter = f;
		f -= 2.0f;
		rootHeight = f += 0.2f;
		heightVariation = f1 / 2.0f;
		return this;
	}

	public void setNpcSpawnList(GOTBiomeSpawnList npcSpawnList) {
		this.npcSpawnList = npcSpawnList;
	}

	public void setSpawnableGOTAmbientList(List spawnableGOTAmbientList) {
		this.spawnableGOTAmbientList = spawnableGOTAmbientList;
	}

	@Override
	public GOTBiome setTemperatureRainfall(float f, float f1) {
		super.setTemperatureRainfall(f, f1);
		return this;
	}

	public void setTopBlockMeta(int topBlockMeta) {
		this.topBlockMeta = topBlockMeta;
	}

	public void setUnreliableChance(GOTEventSpawner.EventChance c) {
		banditChance = c;
	}

	public void setupDesertFauna() {
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityCamel.class, 100, 1, 2));
		spawnableGOTAmbientList.clear();
	}

	public void setupExoticFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 0, 10));
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 1, 10));
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 3, 20));
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 3, 20));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteOryx.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 10, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 10, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 5, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 5, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 5, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 5, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 20, 2, 3));
	}

	public void setupFrostFauna() {
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySnowBear.class, 60, 1, 1));
	}

	public void setupJungleFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.yellow_flower, 0, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 10));
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 3, 20));
		flowers.add(new FlowerEntry(GOTRegistry.essosFlower, 3, 20));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityFlamingo.class, 100, 2, 3));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 60, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 40, 2, 3));
	}

	public void setupMarshFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(GOTRegistry.deadMarshPlant, 0, 10));
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityMidges.class, 90, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySwan.class, 10, 1, 2));
	}

	public void setupStandartDomesticFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.red_flower, 4, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 5, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 6, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 7, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 3, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 8, 20));
		flowers.add(new FlowerEntry(Blocks.yellow_flower, 0, 30));
		flowers.add(new FlowerEntry(GOTRegistry.bluebell, 0, 5));
		flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 30, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 20, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 15, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 10, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 1, 2));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 5, 2, 3));
	}

	public void setupStandartForestFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.yellow_flower, 0, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 10));
		flowers.add(new FlowerEntry(GOTRegistry.bluebell, 0, 5));
		flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 20, 2, 3));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 20, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 10, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 5, 2, 3));
		if (!(this instanceof GOTBiomeYiTi)) {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 20, 1, 2));
		} else {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteBison.class, 20, 1, 2));
			flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 0, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 1, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 2, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 3, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 4, 10));
		}
	}

	public void setupStandartPlainsFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.red_flower, 4, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 5, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 6, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 7, 3));
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 3, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 8, 20));
		flowers.add(new FlowerEntry(Blocks.yellow_flower, 0, 30));
		flowers.add(new FlowerEntry(GOTRegistry.bluebell, 0, 5));
		flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 30, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 20, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 15, 2, 3));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 50, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 30, 2, 3));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGorcrow.class, 5, 2, 3));
		if (!(this instanceof GOTBiomeYiTi)) {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 10, 1, 2));
		} else {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWhiteBison.class, 10, 1, 2));
			flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 0, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 1, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 2, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 3, 10));
			flowers.add(new FlowerEntry(GOTRegistry.yitiFlower, 4, 10));
		}
	}

	public void setupTaigaFauna() {
		flowers.clear();
		flowers.add(new FlowerEntry(Blocks.yellow_flower, 0, 20));
		flowers.add(new FlowerEntry(Blocks.red_flower, 0, 10));
		flowers.add(new FlowerEntry(GOTRegistry.bluebell, 0, 5));
		flowers.add(new FlowerEntry(GOTRegistry.marigold, 0, 10));
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 30, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBoar.class, 20, 2, 3));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 15, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBear.class, 15, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWoolyRhino.class, 10, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityMammoth.class, 10, 1, 1));
		spawnableGOTAmbientList.clear();
	}

	public void setVariantChance(float variantChance) {
		this.variantChance = variantChance;
	}

	public int spawnCountMultiplier() {
		return 1;
	}

	public static void preInit() {
		braavosHills = new GOTBiomeBraavos(96, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x938448).setBiomeName("braavosHills");
		pentosHills = new GOTBiomePentos(98, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0xA0944E).setBiomeName("pentosHills");
		dothrakiHills = new GOTBiomeDothrakiSea(103, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x8A9346).setBiomeName("dothrakiHills");
		ibbenColonyHills = new GOTBiomeIbbenColony(110, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x919E5A).setBiomeName("ibbenColonyHills");
		lhazarHills = new GOTBiomeLhazar(117, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x9FA35E).setBiomeName("lhazarHills");
		alwaysWinter = new GOTBiomeAlwaysWinter(1, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xBEDAE0).setBiomeName("alwaysWinter");
		arryn = new GOTBiomeArryn(3, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x74AD45).setBiomeName("arryn");
		arrynMountainsFoothills = new GOTBiomeArrynMountainsFoothills(5, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x1E772F).setBiomeName("arrynForest");
		arrynMountains = new GOTBiomeArrynMountains(6, true).setClimateType(GOTClimateType.NORMAL_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xC1E0BA).setBiomeName("arrynMountains");
		arrynTown = new GOTBiomeArrynTown(7, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x89BC5C).setBiomeName("arrynTown");
		astapor = new GOTBiomeAstapor(8, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB1BA7A).setBiomeName("astapor");
		beach = new GOTBiomeBeach(9, false).setBeachBlock(Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
		beachGravel = new GOTBiomeBeach(10, false).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
		beachWhite = new GOTBiomeBeach(11, false).setBeachBlock(GOTRegistry.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
		boneMountains = new GOTBiomeBoneMountains(14, true).setClimateType(GOTClimateType.SUMMER_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xE5E2B3).setBiomeName("boneMountains");
		braavos = new GOTBiomeBraavos(15, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xAA9747).setBiomeName("braavos");
		coldCoast = new GOTBiomeColdCoast(17, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xAFDDCE).setBiomeName("coldCoast");
		crackclaw = new GOTBiomeCrackclaw(18, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x648E37).setBiomeName("crackclaw");
		crownlands = new GOTBiomeCrownlands(19, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x70AD45).setBiomeName("crownlands");
		crownlandsForest = new GOTBiomeCrownlandsForest(21, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x709932).setBiomeName("crownlandsForest");
		crownlandsTown = new GOTBiomeCrownlandsTown(22, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x83bc5a).setBiomeName("crownlandsTown");
		cannibalSands = new GOTBiomeCannibalSands(23, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xCCBC82).setBiomeName("deathDesert");
		dorne = new GOTBiomeDorne(24, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9fb255).setBiomeName("dorne");
		dorneDesert = new GOTBiomeDorneDesert(25, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(14074229).setBiomeName("dorneDesert");
		dorneMountains = new GOTBiomeDorneMountains(27, true).setClimateType(GOTClimateType.SUMMER_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xD7E0AF).setBiomeName("dorneMountains");
		dorneValley = new GOTBiomeDorne(28, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9FB253).setBiomeName("dorneValley");
		dothrakiSea = new GOTBiomeDothrakiSea(30, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(10398278).setBiomeName("dothrakiSea");
		dragonstone = new GOTBiomeDragonstone(31, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.3f, 0.35f).setColor(0x75A83F).setBiomeName("dragonstone");
		essos = new GOTBiomeEssos(33, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x92A54A).setBiomeName("essos");
		essosForest = new GOTBiomeEssosForest(34, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x617027).setBiomeName("essosForest");
		norvosHills = new GOTBiomeNorvos(35, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x728E47).setBiomeName("norvosHills");
		essosMountains = new GOTBiomeEssosMountains(36, true).setClimateType(GOTClimateType.SUMMER_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xDDDDAF).setBiomeName("essosMountains");
		farNorthSnowy = new GOTBiomeWesterosFrost(37, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xC9DAE0).setBiomeName("farNorthSnowy");
		fireField = new GOTBiomeFireField(38, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA3CC61).setBiomeName("fireField");
		frostfangs = new GOTBiomeFrostfangs(39, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(2.0f, 2.0f).setColor(0xCBE9F1).setBiomeName("frostfangs");
		ghiscar = new GOTBiomeGhiscar(40, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xADAF6B).setBiomeName("ghiscar");
		ghiscarColony = new GOTBiomeGhiscarColony(41, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x5B8C2A).setBiomeName("ghiscarColony");
		giftNew = new GOTBiomeGiftNew(42, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x9EC166).setBiomeName("giftNew");
		giftOld = new GOTBiomeGiftOld(43, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB9D68B).setBiomeName("giftOld");
		hauntedForest = new GOTBiomeHauntedForest(44, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA5C9AA).setBiomeName("hauntedForest");
		ibben = new GOTBiomeIbben(45, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x7AA04B).setBiomeName("ibben");
		ibbenColony = new GOTBiomeIbbenColony(47, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9EAF56).setBiomeName("ibbenColony");
		ibbenTaiga = new GOTBiomeIbbenTaiga(49, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x4C6B23).setBiomeName("ibbenTaiga");
		ifekevronForest = new GOTBiomeIfekevronForest(50, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x657231).setBiomeName("ifekevronForest");
		ironborn = new GOTBiomeIronborn(51, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x86B23E).setBiomeName("ironborn");
		ironbornHills = new GOTBiomeIronbornHills(53, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 1.0f).setColor(0x7F9951).setBiomeName("ironbornHills");
		irontree = new GOTBiomeIrontree(54, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x677F2A).setBiomeName("irontree");
		island = new GOTBiomeOcean(55, false).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.0f, 0.3f).setColor(10138963).setBiomeName("island");
		isleOfFaces = new GOTBiomeIsleOfFaces(56, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x788046).setBiomeName("isleOfFaces");
		jogosNhai = new GOTBiomeJogosNhai(57, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB2B762).setBiomeName("jogosNhai");
		jogosNhaiDesert = new GOTBiomeJogosNhaiDesert(58, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xD6C182).setBiomeName("jogosNhaiDesert");
		jogosNhaiHills = new GOTBiomeJogosNhai(2, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0x8E8D4E).setBiomeName("jogosNhaiHills");
		kingSpears = new GOTBiomeOcean(59, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-0.7f, 0.3f).setColor(0xA0A0A0).setBiomeName("kingSpears");
		lake = new GOTBiomeLake(60, false).setTemperatureRainfall(0.8F, 0.8F).setColor(3433630).setBiomeName("lake");
		lhazar = new GOTBiomeLhazar(61, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB2B758).setBiomeName("lhazar");
		longSummer = new GOTBiomeLongSummer(62, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x95A03D).setBiomeName("longSummer");
		lorath = new GOTBiomeLorath(63, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA5B25B).setBiomeName("lorath");
		lys = new GOTBiomeLys(65, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x70A564).setBiomeName("lys");
		massy = new GOTBiomeMassy(67, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 1.0f).setColor(0xAFBFAC).setBiomeName("massy");
		meereen = new GOTBiomeMeereen(69, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xC1BA7A).setBiomeName("meereen");
		mercenary = new GOTBiomeMercenary(70, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xAFB25B).setBiomeName("mercenary");
		mossovy = new GOTBiomeMossovy(71, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x92A35E).setBiomeName("mossovy");
		mossovyForest = new GOTBiomeMossovyForest(72, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x77893B).setBiomeName("mossovyForest");
		mossovyMarshes = new GOTBiomeMossovyMarshes(73, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.0f, 0.1f).setColor(0x749975).setBiomeName("mossovyMarshes");
		mossovyMountains = new GOTBiomeMossovyMountains(75, true).setClimateType(GOTClimateType.COLD_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xD7E2B3).setBiomeName("mossovyMountains");
		myr = new GOTBiomeMyr(76, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA0A349).setBiomeName("myr");
		naath = new GOTBiomeSummerIslands(78, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x7DA33C).setBiomeName("naath");
		neck = new GOTBiomeNeck(79, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.0f, 0.1f).setColor(0x599958).setBiomeName("neck");
		newGhis = new GOTBiomeNewGhis(80, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB1AF7A).setBiomeName("newGhis");
		north = new GOTBiomeNorth(81, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(8959044).setBiomeName("north");
		northBarrows = new GOTBiomeNorthBarrows(82, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x94BA53).setBiomeName("northBarrows");
		northForest = new GOTBiomeNorthForest(84, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x538E2F).setBiomeName("northForest");
		northHills = new GOTBiomeNorth(85, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 1f).setColor(0x74A346).setBiomeName("northHills");
		northMountains = new GOTBiomeNorthMountains(86, true).setClimateType(GOTClimateType.COLD_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xD3E0BA).setBiomeName("northMountains");
		northTown = new GOTBiomeNorthTown(87, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0xA4CE61).setBiomeName("northTown");
		northWild = new GOTBiomeNorthWild(88, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0xA5BF7C).setBiomeName("northWild");
		norvos = new GOTBiomeNorvos(89, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x7DA344).setBiomeName("norvos");
		ocean = new GOTBiomeOcean(91, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-1.0f, 0.3f).setColor(0x024B75).setBiomeName("ocean");
		ocean1 = new GOTBiomeOcean(166, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-0.7f, 0.3f).setColor(0x036FAC).setBiomeName("ocean");
		ocean2 = new GOTBiomeOcean(167, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-0.8f, 0.3f).setColor(0x026193).setBiomeName("ocean");
		ocean3 = new GOTBiomeOcean(46, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-0.9f, 0.3f).setColor(0x025582).setBiomeName("ocean");
		pentos = new GOTBiomePentos(92, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB2A349).setBiomeName("pentos");
		qarth = new GOTBiomeQarth(94, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9EAA4E).setBiomeName("qarth");
		qarthDesert = new GOTBiomeQarthDesert(95, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xDCC175).setBiomeName("qarthDesert");
		qohor = new GOTBiomeQohor(97, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xAFB350).setBiomeName("qohor");
		qohorForest = new GOTBiomeQohorForest(99, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9A9E37).setBiomeName("qohorForest");
		rainwood = new GOTBiomeRainwood(100, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x59994B).setBiomeName("rainwood");
		reach = new GOTBiomeReach(101, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x8BBA44).setBiomeName("reach");
		reachArbor = new GOTBiomeReachArbor(102, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x8CAC44).setBiomeName("reachArbor");
		reachTown = new GOTBiomeReachTown(104, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA2C964).setBiomeName("reachTown");
		beachRed = new GOTBiomeRedBeach(105, false).setBeachBlock(Blocks.sand, 1).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.0f).setColor(14403247).setBiomeName("beach");
		redMesa = new GOTBiomeDorneMesa(106, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(1.5f, 0.0f).setColor(0xDDD5AF).setBiomeName("redMesa");
		redSea = new GOTBiomeRedSea(107, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(-1.0f, 0.3f).setColor(0x916c3e).setBiomeName("redSea");
		river = new GOTBiomeRiver(108, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setBiomeName("river");
		riverlands = new GOTBiomeRiverlands(109, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(7910726).setBiomeName("riverlands");
		riverlandsHills = new GOTBiomeWesterosForest(111, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x4C822A).setBiomeName("riverlandsHills");
		shadowLand = new GOTBiomeShadowLand(112, true).setMinMaxHeight(0.1f, 0.15f).setTemperatureRainfall(1.0f, 0.2f).setColor(0x8E8854).setBiomeName("shadowLand");
		shadowMountains = new GOTBiomeShadowMountains(113, true).setMinMaxHeight(2.0f, 2.0f).setTemperatureRainfall(1.0f, 0.2f).setColor(0x635E3B).setBiomeName("shadowMountains");
		shadowTown = new GOTBiomeShadowTown(114, true).setMinMaxHeight(0.1f, 0.15f).setTemperatureRainfall(1.0f, 0.2f).setColor(0xA39C68).setBiomeName("shadowTown");
		skagos = new GOTBiomeSkagos(116, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x94AF67).setBiomeName("skagos");
		sothoryosBushland = new GOTBiomeSothoryosBushland(118, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x998F3D).setBiomeName("sothoryosBushland");
		sothoryosDesert = new GOTBiomeSothoryosDesert(119, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xCCB882).setBiomeName("sothoryosDesert");
		sothoryosDesertCold = new GOTBiomeSothoryosDesertCold(120, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xDAD4AF).setBiomeName("sothoryosDesertCold");
		sothoryosDesertHills = new GOTBiomeSothoryosDesert(121, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1.0f).setColor(0xB9A762).setBiomeName("sothoryosDeserHills");
		sothoryosFrost = new GOTBiomeSothoryosFrost(122, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xD8D8D2).setBiomeName("sothoryosFrost");
		sothoryosHell = new GOTBiomeSothoryosHell(123, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x2E441B).setBiomeName("sothoryosHell");
		sothoryosJungle = new GOTBiomeSothoryosJungle(124, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x4A7222).setBiomeName("sothoryosJungle");
		sothoryosJungleEdge = new GOTBiomeSothoryosJungleEdge(125, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x7B932C).setBiomeName("sothoryosJungleEdge");
		sothoryosKanuka = new GOTBiomeSothoryosForest(126, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x74842B).setBiomeName("sothoryosKanuka");
		sothoryosMangrove = new GOTBiomeSothoryosMangrove(127, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x6E8E48).setBiomeName("sothoryosMangrove");
		sothoryosMountains = new GOTBiomeSothoryosMountains(128, true).setClimateType(GOTClimateType.SUMMER_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xD8D2B1).setBiomeName("sothoryosMountains");
		sothoryosSavannah = new GOTBiomeSothoryosSavannah(129, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x8CA041).setBiomeName("sothoryosSavannah");
		sothoryosTaiga = new GOTBiomeSothoryosTaiga(130, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA3B481).setBiomeName("sothoryosTaiga");
		stepstones = new GOTBiomeStepstones(131, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.5f).setColor(0x9BA37A).setBiomeName("stepstones");
		stoneCoast = new GOTBiomeStoneCoast(132, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x879660).setBiomeName("stoneCoast");
		stormlands = new GOTBiomeStormlands(133, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x75b22f).setBiomeName("stormlands");
		stormlandsTown = new GOTBiomeStormlands(135, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x91C94C).setBiomeName("stormlandsTown");
		summerIslands = new GOTBiomeSummerIslands(136, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x83A54A).setBiomeName("summerIslands");
		tarth = new GOTBiomeTarth(137, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x75B241).setBiomeName("tarth");
		thenn = new GOTBiomeThenn(138, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xC3DDCF).setBiomeName("thenn");
		tropicalForest = new GOTBiomeTropicalForest(139, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x2E7B40).setBiomeName("tropicalForest");
		tyrosh = new GOTBiomeTyrosh(140, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9AA5A2).setBiomeName("tyrosh");
		valyria = new GOTBiomeValyria(145, true).setMinMaxHeight(0.1f, 0.15f).setTemperatureRainfall(1.2F, 0.8F).setColor(6710111).setBiomeName("valyria");
		valyriaSea = new GOTBiomeValyria(146, true).setMinMaxHeight(-1.0f, 0.3f).setTemperatureRainfall(0.8F, 0.8F).setColor(0x0D304C).setBiomeName("valyriaSea");
		valyriaVolcano = new GOTBiomeValyriaVolcano(147, true).setMinMaxHeight(2.0f, 2.0f).setTemperatureRainfall(1.2F, 0.8F).setColor(0xA5A5A5).setBiomeName("valyriaVolcano");
		volantis = new GOTBiomeVolantis(148, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xB2A24C).setBiomeName("volantis");
		volantisForest = new GOTBiomeVolantisForest(150, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xCE9F32).setBiomeName("volantisForest");
		volantisMarshes = new GOTBiomeVolantisMarshes(151, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0xA2AF69).setBiomeName("volantisMarshes");
		essosMarshes = new GOTBiomeEssosMarshes(4, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x89933E).setBiomeName("volantisMarshes");
		yiTiMarshes = new GOTBiomeYiTiMarshes(12, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x9AAA61).setBiomeName("yiTiMarshes");
		westerlands = new GOTBiomeWesterlands(152, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0xA9B542).setBiomeName("westerlands");
		westerlandsHills = new GOTBiomeWesterlandsHills(154, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 1.0f).setColor(0x9CA548).setBiomeName("westerlandsHills");
		westerlandsTown = new GOTBiomeWesterlandsTown(155, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0xbfc962).setBiomeName("westerlandsTown");
		westerosForest = new GOTBiomeWesterosForest(156, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(5871922).setBiomeName("westerosForest");
		wetwood = new GOTBiomeWetwood(157, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x578C54).setBiomeName("wetwood");
		whisperingWood = new GOTBiomeWhisperingWood(158, true).setClimateType(GOTClimateType.NORMAL).setMinMaxHeight(0.1f, 0.15f).setColor(0x598632).setBiomeName("whisperingWood");
		wolfswood = new GOTBiomeNorthForest(159, true).setClimateType(GOTClimateType.COLD).setMinMaxHeight(0.1f, 0.15f).setColor(0x4A7F2A).setBiomeName("wolfswood");
		yeen = new GOTBiomeYeen(160, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x242F0F).setBiomeName("yeen");
		yiTi = new GOTBiomeYiTi(161, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xAAAE55).setBiomeName("yiTi");
		yiTiWasteland = new GOTBiomeYiTiWasteland(163, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xaaae56).setBiomeName("yiTiWasteland");
		yunkai = new GOTBiomeYunkai(164, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xb9ba7a).setBiomeName("yunkai");
		ibbenMountains = new GOTBiomeIbbenMountains(165, true).setClimateType(GOTClimateType.COLD_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xafbc9f).setBiomeName("ibbenMountains");
		ulthosForest = new GOTBiomeUlthosForest(142, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x2E441C).setBiomeName("ulthosForest");
		ulthosDesert = new GOTBiomeUlthosDesert(143, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xCEBA84).setBiomeName("ulthosDesert");
		ulthosMountains = new GOTBiomeUlthosMountains(144, true).setClimateType(GOTClimateType.SUMMER_AZ).setMinMaxHeight(2.0f, 2.0f).setColor(0xCDD6AF).setBiomeName("ulthosMountains");
		qarthColony = new GOTBiomeQarthColony(52, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9DAF54).setBiomeName("qarthColony");
		ulthos = new GOTBiomeUlthos(13, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x648432).setBiomeName("ulthos");
		ulthosForestEdge = new GOTBiomeUlthosForestEdge(16, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x3E6526).setBiomeName("ulthosForestEdge");
		ulthosFrost = new GOTBiomeUlthosFrost(20, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xCECECA).setBiomeName("ulthosFrost");
		ulthosTaiga = new GOTBiomeUlthosTaiga(26, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0x5C6B3D).setBiomeName("ulthosTaiga");
		ulthosTaigaEdge = new GOTBiomeUlthosTaigaEdge(29, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0x818E66).setBiomeName("ulthosTaigaEdge");
		ulthosDesertCold = new GOTBiomeUlthosDesertCold(32, true).setClimateType(GOTClimateType.WINTER).setMinMaxHeight(0.1f, 0.15f).setColor(0xD8D2AD).setBiomeName("ulthosDesertCold");
		summerColony = new GOTBiomeSummerColony(64, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x9BAD53).setBiomeName("summerColony");
		summerColonyMangrove = new GOTBiomeSummerColonyMangrove(66, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x85A361).setBiomeName("summerColonyMangrove");
		ulthosMarshes = new GOTBiomeUlthosMarshes(68, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x599058).setBiomeName("ulthosMarshes");
		ulthosMarshesForest = new GOTBiomeUlthosMarshesForest(74, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0x41753F).setBiomeName("ulthosMarshesForest");
		ulthosRedForest = new GOTBiomeUlthosRedForest(115, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x5E441C).setBiomeName("ulthosRedForest");
		ulthosRedForestEdge = new GOTBiomeUlthosRedForestEdge(77, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0x5E6526).setBiomeName("ulthosRedForestEdge");
		cannibalSandsHills = new GOTBiomeCannibalSands(83, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 1f).setColor(0xA5986A).setBiomeName("cannibalSandsHills");
		shrykesLand = new GOTBiomeShrykesLand(90, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.0f, 0.1f).setColor(0xAAAE77).setBiomeName("shrykesLand");
		lorathMaze = new GOTBiomeLorathMaze(93, true).setClimateType(GOTClimateType.SUMMER).setMinMaxHeight(0.1f, 0.15f).setColor(0xA8AF7B).setBiomeName("lorathMaze");
		for (int i : NON_USED) {
			GOTDimension.GAME_OF_THRONES.biomeList[i] = GOTBiome.ocean;
		}
	}

	public static void updateWaterColor(int k) {
		int min = 0;
		int max = waterLimitSouth - waterLimitNorth;
		float latitude = (float) MathHelper.clamp_int(k - waterLimitNorth, min, max) / (float) max;
		float[] northColors = waterColorNorth.getColorComponents(null);
		float[] southColors = waterColorSouth.getColorComponents(null);
		float dR = southColors[0] - northColors[0];
		float dG = southColors[1] - northColors[1];
		float dB = southColors[2] - northColors[2];
		float r = dR * latitude;
		float g = dG * latitude;
		float b = dB * latitude;
		r += northColors[0];
		g += northColors[1];
		b += northColors[2];
		Color water = new Color(r, g, b);
		int waterRGB = water.getRGB();
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome != null && !biome.biomeColors.hasCustomWater()) {
				biome.biomeColors.updateWater(waterRGB);
			}
		}
	}

	public static class BiomeColors {
		public static int DEFAULT_WATER = 7186907;
		public GOTBiome theBiome;
		public Color grass;
		public Color foliage;
		public Color sky;
		public Color clouds;
		public Color fog;
		public boolean foggy;
		public boolean hasCustomWater = false;

		public BiomeColors(GOTBiome biome) {
			theBiome = biome;
		}

		public boolean hasCustomWater() {
			return hasCustomWater;
		}

		public void resetClouds() {
			clouds = null;
		}

		public void resetFog() {
			fog = null;
		}

		public void resetFoliage() {
			foliage = null;
		}

		public void resetGrass() {
			grass = null;
		}

		public void resetSky() {
			sky = null;
		}

		public void resetWater() {
			setWater(DEFAULT_WATER);
		}

		public void setClouds(int rgb) {
			clouds = new Color(rgb);
		}

		public void setFog(int rgb) {
			fog = new Color(rgb);
		}

		public void setFoggy(boolean flag) {
			foggy = flag;
		}

		public void setFoliage(int rgb) {
			foliage = new Color(rgb);
		}

		public void setGrass(int rgb) {
			grass = new Color(rgb);
		}

		public void setSky(int rgb) {
			sky = new Color(rgb);
		}

		public void setWater(int rgb) {
			theBiome.waterColorMultiplier = rgb;
			if (rgb != DEFAULT_WATER) {
				hasCustomWater = true;
			}
		}

		public void updateWater(int rgb) {
			theBiome.waterColorMultiplier = rgb;
		}
	}

	public static class BiomeTerrain {
		public GOTBiome theBiome;
		public double xzScale = -1.0;
		public double heightStretchFactor = -1.0;

		public BiomeTerrain(GOTBiome biome) {
			theBiome = biome;
		}

		public double getHeightStretchFactor() {
			return heightStretchFactor;
		}

		public double getXZScale() {
			return xzScale;
		}

		public boolean hasHeightStretchFactor() {
			return heightStretchFactor != -1.0;
		}

		public boolean hasXZScale() {
			return xzScale != -1.0;
		}

		public void resetHeightStretchFactor() {
			setHeightStretchFactor(-1.0);
		}

		public void resetXZScale() {
			setXZScale(-1.0);
		}

		public void setHeightStretchFactor(double d) {
			heightStretchFactor = d;
		}

		public void setXZScale(double d) {
			xzScale = d;
		}
	}

	public interface Desert {
	}

	public static class GrassBlockAndMeta {
		public Block block;
		public int meta;

		public GrassBlockAndMeta(Block b, int i) {
			block = b;
			meta = i;
		}
	}

	public interface ImmuneToFrost {
	}

	public interface ImmuneToHeat {
	}
}