package got.common.world.biome;

import java.awt.Color;
import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.client.GOTTickHandlerClient;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.*;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.other.GOTEntityBandit;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.essos.*;
import got.common.world.biome.other.*;
import got.common.world.biome.sothoryos.*;
import got.common.world.biome.ulthos.*;
import got.common.world.biome.variant.*;
import got.common.world.biome.westeros.*;
import got.common.world.feature.GOTTreeType;
import got.common.world.fixed.GOTSpawner;
import got.common.world.map.GOTBezierType;
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
	public static GOTBiome alwaysWinter;
	public static GOTBiome arborFlat;
	public static GOTBiome arryn;
	public static GOTBiome arrynFlat;
	public static GOTBiome arrynMountainsFoothills;
	public static GOTBiome arrynMountains;
	public static GOTBiome arrynTown;
	public static GOTBiome astapor;
	public static GOTBiome beach;
	public static GOTBiome beachGravel;
	public static GOTBiome beachWhite;
	public static GOTBiome blackIslands;
	public static GOTBiome blackIslandsFlat;
	public static GOTBiome boneMountains;
	public static GOTBiome braavos;
	public static GOTBiome braavosFlat;
	public static GOTBiome coldCoast;
	public static GOTBiome crackclaw;
	public static GOTBiome crownlands;
	public static GOTBiome crownlandsFlat;
	public static GOTBiome crownlandsForest;
	public static GOTBiome crownlandsTown;
	public static GOTBiome deathDesert;
	public static GOTBiome dorne;
	public static GOTBiome dorneDesert;
	public static GOTBiome dorneFlat;
	public static GOTBiome dorneMountains;
	public static GOTBiome dorneValley;
	public static GOTBiome doshKhalin;
	public static GOTBiome dothrakiSea;
	public static GOTBiome dragonstone;
	public static GOTBiome dragonstoneFlat;
	public static GOTBiome essos;
	public static GOTBiome essosForest;
	public static GOTBiome essosHills;
	public static GOTBiome essosMountains;
	public static GOTBiome farNorthSnowy;
	public static GOTBiome fireField;
	public static GOTBiome freeCities;
	public static GOTBiome frostfangs;
	public static GOTBiome ghiscar;
	public static GOTBiome ghiscarColony;
	public static GOTBiome giftNew;
	public static GOTBiome giftOld;
	public static GOTBiome hauntedForest;
	public static GOTBiome ibben;
	public static GOTBiome ibbenBeach;
	public static GOTBiome ibbenColony;
	public static GOTBiome ibbenSea;
	public static GOTBiome ibbenTaiga;
	public static GOTBiome ibbenMountains;
	public static GOTBiome ifekevronForest;
	public static GOTBiome ironborn;
	public static GOTBiome ironbornFlat;
	public static GOTBiome ironbornHills;
	public static GOTBiome irontree;
	public static GOTBiome island;
	public static GOTBiome isleOfFaces;
	public static GOTBiome jogosNhai;
	public static GOTBiome jogosNhaiDesert;
	public static GOTBiome kingSpears;
	public static GOTBiome lake;
	public static GOTBiome lhazar;
	public static GOTBiome longSummer;
	public static GOTBiome lorath;
	public static GOTBiome lorathFlat;
	public static GOTBiome lys;
	public static GOTBiome lysFlat;
	public static GOTBiome massy;
	public static GOTBiome massyFlat;
	public static GOTBiome meereen;
	public static GOTBiome mercenary;
	public static GOTBiome mossovy;
	public static GOTBiome mossovyForest;
	public static GOTBiome mossovyMarshes;
	public static GOTBiome mossovySea;
	public static GOTBiome mossovySopkas;
	public static GOTBiome mossovyFlat;
	public static GOTBiome myr;
	public static GOTBiome myrFlat;
	public static GOTBiome naath;
	public static GOTBiome neck;
	public static GOTBiome newGhis;
	public static GOTBiome north;
	public static GOTBiome northBarrows;
	public static GOTBiome northFlat;
	public static GOTBiome northForest;
	public static GOTBiome northHills;
	public static GOTBiome northMountains;
	public static GOTBiome northTown;
	public static GOTBiome northWild;
	public static GOTBiome norvos;
	public static GOTBiome norvosFlat;
	public static GOTBiome ocean;
	public static GOTBiome pentos;
	public static GOTBiome pentosFlat;
	public static GOTBiome qarth;
	public static GOTBiome qarthDesert;
	public static GOTBiome qarthFlat;
	public static GOTBiome qohor;
	public static GOTBiome qohorFlat;
	public static GOTBiome qohorForest;
	public static GOTBiome rainwood;
	public static GOTBiome reach;
	public static GOTBiome reachArbor;
	public static GOTBiome reachFlat;
	public static GOTBiome reachTown;
	public static GOTBiome redBeach;
	public static GOTBiome redMesa;
	public static GOTBiome redSea;
	public static GOTBiome river;
	public static GOTBiome riverlands;
	public static GOTBiome riverlandsFlat;
	public static GOTBiome riverlandsHills;
	public static GOTBiome sarnor;
	public static GOTBiome shadowLand;
	public static GOTBiome shadowMountains;
	public static GOTBiome shadowTown;
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
	public static GOTBiome stormlandsFlat;
	public static GOTBiome stormlandsTown;
	public static GOTBiome summerIslands;
	public static GOTBiome tarth;
	public static GOTBiome thenn;
	public static GOTBiome tropicalForest;
	public static GOTBiome tyrosh;
	public static GOTBiome tyroshFlat;
	public static GOTBiome ulos;
	public static GOTBiome ulthos;
	public static GOTBiome ulthosDesert;
	public static GOTBiome ulthosMountains;
	public static GOTBiome valyria;
	public static GOTBiome valyriaSea;
	public static GOTBiome valyriaVolcano;
	public static GOTBiome volantis;
	public static GOTBiome volantisFlat;
	public static GOTBiome volantisForest;
	public static GOTBiome volantisMarshes;
	public static GOTBiome wall;
	public static GOTBiome westerlands;
	public static GOTBiome westerlandsFlat;
	public static GOTBiome westerlandsHills;
	public static GOTBiome westerlandsTown;
	public static GOTBiome westerosForest;
	public static GOTBiome wetwood;
	public static GOTBiome whisperingWood;
	public static GOTBiome wolfswood;
	public static GOTBiome yeen;
	public static GOTBiome yiTi;
	public static GOTBiome yiTiFlat;
	public static GOTBiome yiTiWasteland;
	public static GOTBiome yunkai;
	public static GOTBiome ibbenFlat;
	public static GOTBiome giftNewFlat;
	public static GOTBiome giftOldFlat;
	public static NoiseGeneratorPerlin biomeTerrainNoise;
	public static Random terrainRand;
	public static float defaultVariantChance = 0.4f;
	public static Color waterColorNorth;
	public static Color waterColorSouth;
	public static int waterLimitNorth;
	public static int waterLimitSouth;
	public static int id = 0;
	static {
		biomeTerrainNoise = new NoiseGeneratorPerlin(new Random(1955L), 1);
		terrainRand = new Random();
		waterColorNorth = new Color(602979);
		waterColorSouth = new Color(4973293);
		waterLimitNorth = -40000;
		waterLimitSouth = 160000;
	}
	public static int SPAWN = 500;
	public static int CONQUEST_SPAWN = 100;
	public static GOTBiome lhazarFlat;
	public GOTDimension biomeDimension;
	public GOTBiomeDecorator decorator;
	public GOTSpawner spawner;
	public int topBlockMeta = 0;
	public int fillerBlockMeta = 0;
	public float temperatureBase;
	public float rainfallBase;
	public float heightBaseParameter;
	public boolean enablePodzol = true;
	public boolean enableRocky = false;
	public GOTBiomeVariantList biomeVariantsLarge = new GOTBiomeVariantList();
	public GOTBiomeVariantList biomeVariantsSmall = new GOTBiomeVariantList();
	public GOTBiomeSpawnList npcSpawnList = new GOTBiomeSpawnList(this);
	public List spawnableGOTAmbientList = new ArrayList();
	public GOTEventSpawner.EventChance banditChance;
	public Class<? extends GOTEntityBandit> banditEntityClass;
	public GOTBiomeInvasionSpawns invasionSpawns;
	public BiomeColors biomeColors = new BiomeColors(this);
	public BiomeTerrain biomeTerrain = new BiomeTerrain(this);
	public float variantChance = 0.4f;

	public boolean isAltitudeZone;
	public boolean isAlwaysWinter;
	public boolean isNeverWinter;
	public boolean isSeasonalWinter;
	public boolean isLongWinter;
	public boolean isNeverWinterAZ;

	public boolean isSeasonalWinterAZ;

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
		if (hasDomesticAnimals()) {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 5, 4, 4));
		} else {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityWildBoar.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 8, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDeer.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBison.class, 6, 4, 4));
		}
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityFish.class, 10, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRabbit.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 10, 4, 4));
		spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
		setBanditChance(GOTEventSpawner.EventChance.NEVER);
		invasionSpawns = new GOTBiomeInvasionSpawns(this);
	}

	public void addBiomeF3Info(List info, World world, GOTBiomeVariant variant, int i, int j, int k) {
		int colorRGB = color & 0xFFFFFF;
		String colorString = Integer.toHexString(colorRGB);
		while (colorString.length() < 6) {
			colorString = "0" + colorString;
		}
		info.add("Game of Thrones biome: " + getBiomeDisplayName() + ", ID: " + biomeID + ", c: #" + colorString);
		info.add("Variant: " + variant.variantName + ", loaded: " + GOTBiomeVariantStorage.getSize(world));
	}

	public void addBiomeVariant(GOTBiomeVariant v) {
		this.addBiomeVariant(v, 1.0f);
	}

	public void addBiomeVariant(GOTBiomeVariant v, float f) {
		biomeVariantsSmall.add(v, f);
	}

	public void addBiomeVariantSet(GOTBiomeVariant[] set) {
		for (GOTBiomeVariant v : set) {
			this.addBiomeVariant(v);
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
				if ((trees = Math.max(trees, variant.treeFactor * 0.5f)) >= 1.0f) {
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
				if (blocks[index] != null && blocks[index].getMaterial() == Material.air) {
					continue;
				}
				if (j != seaLevel - 1 || blocks[index] == Blocks.water) {
					break;
				}
				blocks[index] = Blocks.water;
				break;
			}
		}
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			if (j <= 0 + random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
				continue;
			}
			Block block = blocks[index];
			if (block == Blocks.air) {
				fillerDepth = -1;
				continue;
			}
			if (block != Blocks.stone) {
				continue;
			}
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
					continue;
				}
				blocks[index] = filler;
				meta[index] = fillerMeta;
				continue;
			}
			if (fillerDepth <= 0) {
				continue;
			}
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
			if (fillerDepth != 0 || fillerBlock == GOTRegistry.rock || filler != fillerBlock) {
				continue;
			}
			fillerDepth = 6 + random.nextInt(3);
			filler = Blocks.stone;
			fillerMeta = 0;
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

	public Class<? extends GOTEntityBandit> getBanditEntityClass() {
		if (banditEntityClass == null) {
			return GOTEntityBandit.class;
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
		return 1.0f;
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

	@Override
	public boolean getEnableSnow() {
		return super.getEnableSnow();
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

	public GOTBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, GOTBiomeVariant variant) {
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
		if (random.nextInt(300) == 0) {
			return new GrassBlockAndMeta(GOTRegistry.flaxPlant, 0);
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
		}
		}
		return doubleFlowerGen;
	}

	public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
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
		if (GOTTickHandlerClient.scrapTraderMisbehaveTick > 0) {
			return 0;
		}
		if (biomeColors.sky != null) {
			return biomeColors.sky.getRGB();
		}
		return super.getSkyColorByTemp(f);
	}

	public int getSnowHeight() {
		return 0;
	}

	@Override
	public List getSpawnableList(EnumCreatureType creatureType) {
		if (creatureType == creatureType_GOTAmbient) {
			return spawnableGOTAmbientList;
		}
		return super.getSpawnableList(creatureType);
	}

	public WorldGenAbstractTree getTreeGen(World world, Random random, int i, int j, int k) {
		GOTWorldChunkManager chunkManager = (GOTWorldChunkManager) world.getWorldChunkManager();
		GOTBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
		GOTTreeType tree = decorator.getRandomTreeForVariant(random, variant);
		return tree.create(false, random);
	}

	public float getTreeIncreaseChance() {
		return 0.1f;
	}

	public GOTBezierType getWallBlock() {
		return GOTBezierType.ICE;
	}

	public boolean hasDomesticAnimals() {
		return false;
	}

	public boolean hasFog() {
		return biomeColors.foggy;
	}

	public boolean hasSky() {
		return true;
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

	public void registerExoticFlowers() {
		flowers.clear();
		addFlower(GOTRegistry.essosFlower, 0, 10);
		addFlower(GOTRegistry.essosFlower, 1, 10);
		addFlower(GOTRegistry.essosFlower, 2, 5);
		addFlower(GOTRegistry.essosFlower, 3, 5);
	}

	public void registerForestFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(GOTRegistry.bluebell, 0, 5);
		addFlower(GOTRegistry.marigold, 0, 10);
	}

	public void registerJungleFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(GOTRegistry.essosFlower, 2, 20);
		addFlower(GOTRegistry.essosFlower, 3, 20);
	}

	public void registerMountainsFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(Blocks.red_flower, 1, 10);
		addFlower(GOTRegistry.bluebell, 0, 5);
	}

	public void registerPlainsFlowers() {
		flowers.clear();
		addFlower(Blocks.red_flower, 4, 3);
		addFlower(Blocks.red_flower, 5, 3);
		addFlower(Blocks.red_flower, 6, 3);
		addFlower(Blocks.red_flower, 7, 3);
		addFlower(Blocks.red_flower, 0, 20);
		addFlower(Blocks.red_flower, 3, 20);
		addFlower(Blocks.red_flower, 8, 20);
		addFlower(Blocks.yellow_flower, 0, 30);
		addFlower(GOTRegistry.bluebell, 0, 5);
		addFlower(GOTRegistry.marigold, 0, 10);
	}

	public void registerSavannaFlowers() {
		flowers.clear();
		addDefaultFlowers();
	}

	public void registerSwampFlowers() {
		flowers.clear();
		addDefaultFlowers();
	}

	public void registerTaigaFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(Blocks.red_flower, 1, 10);
		addFlower(GOTRegistry.bluebell, 0, 5);
	}

	public void registerYiTiPlainsFlowers() {
		registerPlainsFlowers();
		addFlower(GOTRegistry.marigold, 0, 10);
		addFlower(GOTRegistry.yitiFlower, 0, 10);
		addFlower(GOTRegistry.yitiFlower, 1, 10);
		addFlower(GOTRegistry.yitiFlower, 2, 10);
		addFlower(GOTRegistry.yitiFlower, 3, 10);
		addFlower(GOTRegistry.yitiFlower, 4, 10);
	}

	public void setBanditChance(GOTEventSpawner.EventChance c) {
		banditChance = c;
	}

	public void setBanditEntityClass(Class<? extends GOTEntityBandit> c) {
		banditEntityClass = c;
	}

	@Override
	public GOTBiome setBiomeName(String s) {
		return (GOTBiome) super.setBiomeName(s);
	}

	@Override
	public GOTBiome setColor(int color) {
		Integer existingBiomeID = biomeDimension.colorsToBiomeIDs.get(color |= 0xFF000000);
		if (existingBiomeID != null) {
			throw new RuntimeException("GOT biome (ID " + biomeID + ") is duplicating the color of another GOT biome (ID " + existingBiomeID + ")");
		}
		biomeDimension.colorsToBiomeIDs.put(color, biomeID);
		return (GOTBiome) super.setColor(color);
	}

	public GOTBiome setDisableSnow() {
		enableSnow = false;
		return this;
	}

	public GOTBiome setIsAlwaysWinter() {
		isAlwaysWinter = true;
		return this;
	}

	public GOTBiome setIsLongWinter() {
		isLongWinter = true;
		return this;
	}

	public GOTBiome setIsLongWinterAZ() {
		isLongWinter = true;
		isAltitudeZone = true;
		return this;
	}

	public GOTBiome setIsNeverWinter() {
		isNeverWinter = true;
		return this;
	}

	public GOTBiome setIsNeverWinterAZ() {
		isNeverWinterAZ = true;
		isAltitudeZone = true;
		return this;
	}

	public GOTBiome setIsSeasonalWinter() {
		isSeasonalWinter = true;
		return this;
	}

	public GOTBiome setIsSeasonalWinterAZ() {
		isSeasonalWinterAZ = true;
		isAltitudeZone = true;
		return this;
	}

	public GOTBiome setMinMaxHeight(float f, float f1) {
		heightBaseParameter = f;
		f -= 2.0f;
		rootHeight = f += 0.2f;
		heightVariation = f1 / 2.0f;
		return this;
	}

	@Override
	public GOTBiome setTemperatureRainfall(float f, float f1) {
		temperatureBase = f;
		rainfallBase = f1;
		super.setTemperatureRainfall(f, f1);
		return this;
	}

	public int spawnCountMultiplier() {
		return 1;
	}

	public static void performSeasonChanges() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome == null) {
				continue;
			}
			if (biome.isAlwaysWinter) {
				biome.setTemperatureRainfall(0.0F, 2.0F);
				biome.biomeColors.setGrass(0xffffff);
				biome.biomeColors.setFoggy(true);
				biome.biomeColors.setSky(4212300);
				biome.biomeColors.setFog(6188664);
				biome.biomeColors.setWater(2635588);
			}
			switch (GOTDate.AegonCalendar.getSeason()) {
			case WINTER:
				if (biome.isLongWinter || biome.isSeasonalWinter || biome.isSeasonalWinterAZ) {
					biome.setTemperatureRainfall(0.0F, 2.0F);
					biome.biomeColors.setGrass(0xffffff);
					biome.biomeColors.setSky(4212300);
					biome.biomeColors.setFog(6188664);
					biome.biomeColors.setWater(2635588);
				}
				if (biome.isNeverWinter || biome.isNeverWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 2.0F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.setSky(11653858);
				}
				break;
			case SPRING:
				if (biome.isLongWinter || biome.isSeasonalWinter || biome.isSeasonalWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.setSky(11653858);
					biome.biomeColors.resetFog();
					biome.biomeColors.setWater(2635588);
				}
				if (biome.isNeverWinter) {
					biome.setTemperatureRainfall(0.8F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
				}
				if (biome.isNeverWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
				}
				break;
			case SUMMER:
				if (biome.isLongWinter) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.setSky(11653858);
					biome.biomeColors.resetFog();
					biome.biomeColors.setWater(2635588);
				}
				if (biome.isSeasonalWinter) {
					biome.setTemperatureRainfall(0.8F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
					biome.biomeColors.resetFog();
					biome.biomeColors.resetWater();
				}
				if (biome.isSeasonalWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
					biome.biomeColors.resetFog();
					biome.biomeColors.resetWater();
				}
				if (biome.isNeverWinter) {
					biome.setTemperatureRainfall(1.2F, 0.4F);
					biome.biomeColors.setGrass(14538086);
					biome.biomeColors.setSky(15592678);
				}
				if (biome.isNeverWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.setGrass(14538086);
					biome.biomeColors.setSky(15592678);
				}
				break;
			case AUTUMN:
				if (biome.isLongWinter || biome.isSeasonalWinter || biome.isSeasonalWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 2.0F);
					biome.biomeColors.setGrass(0xd09f4d);
					biome.biomeColors.setFoggy(true);
					biome.biomeColors.setSky(11653858);
					biome.biomeColors.resetFog();
					biome.biomeColors.setWater(2635588);
				}
				if (biome.isNeverWinter) {
					biome.setTemperatureRainfall(0.8F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
				}
				if (biome.isNeverWinterAZ) {
					biome.setTemperatureRainfall(0.28F, 0.8F);
					biome.biomeColors.resetGrass();
					biome.biomeColors.resetSky();
				}
				break;
			}
		}
	}

	public static void preInit() {
		alwaysWinter = new GOTBiomeAlwaysWinter(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xBEDAE0).setBiomeName("alwaysWinter");
		arborFlat = new GOTBiomeReachFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x8CAC45).setBiomeName("reachArbor");
		arryn = new GOTBiomeArryn(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x73B23C).setBiomeName("arryn");
		arrynFlat = new GOTBiomeArrynFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x73B23D).setBiomeName("arryn");
		arrynMountainsFoothills = new GOTBiomeArrynMountainsFoothills(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.7f, 0.9f).setColor(0x587F3F).setBiomeName("arrynForest");
		arrynMountains = new GOTBiomeArrynMountains(id++, true).setIsSeasonalWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0xC5D3D6).setBiomeName("arrynMountains");
		arrynTown = new GOTBiomeArrynTown(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x73B261).setBiomeName("arrynTown");
		astapor = new GOTBiomeAstapor(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x89AA68).setBiomeName("astapor");
		beach = new GOTBiomeBeach(id++, false).setBeachBlock(Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
		beachGravel = new GOTBiomeBeach(id++, false).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
		beachWhite = new GOTBiomeBeach(id++, false).setBeachBlock(GOTRegistry.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
		blackIslands = new GOTBiomeDragonstone(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.3f, 0.35f).setColor(0x79AF43).setBiomeName("blackIslands");
		blackIslandsFlat = new GOTBiomeDragonstoneFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x79AF42).setBiomeName("blackIslands");
		boneMountains = new GOTBiomeBoneMountains(id++, true).setIsNeverWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0xE2E2E2).setBiomeName("boneMountains");
		braavos = new GOTBiomeBraavos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x969644).setBiomeName("braavos");
		braavosFlat = new GOTBiomeBraavosFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x969645).setBiomeName("braavos");
		coldCoast = new GOTBiomeColdCoast(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xAFDDCE).setBiomeName("coldCoast");
		crackclaw = new GOTBiomeCrackclaw(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x648E37).setBiomeName("crackclaw");
		crownlands = new GOTBiomeCrownlands(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x86AA40).setBiomeName("crownlands");
		crownlandsFlat = new GOTBiomeCrownlandsFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x86AA41).setBiomeName("crownlands");
		crownlandsForest = new GOTBiomeCrownlandsForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x709932).setBiomeName("crownlandsForest");
		crownlandsTown = new GOTBiomeCrownlandsTown(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x83B242).setBiomeName("crownlandsTown");
		deathDesert = new GOTBiomeCannibalSands(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(0x8d944d).setBiomeName("deathDesert");
		dorne = new GOTBiomeDorne(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9fb255).setBiomeName("dorne");
		dorneDesert = new GOTBiomeDorneDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(14074229).setBiomeName("dorneDesert");
		dorneFlat = new GOTBiomeDorneFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9FB254).setBiomeName("dorne");
		dorneMountains = new GOTBiomeDorneMountains(id++, true).setIsNeverWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0xD6C9CC).setBiomeName("dorneMountains");
		dorneValley = new GOTBiomeDorne(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9FB253).setBiomeName("dorneValley");
		doshKhalin = new GOTBiomeDoshKhalin(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9EAA64).setBiomeName("doshKhalin");
		dothrakiSea = new GOTBiomeDothrakiSea(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(10398278).setBiomeName("dothrakiSea");
		dragonstone = new GOTBiomeDragonstone(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.3f, 0.35f).setColor(0x75A83F).setBiomeName("dragonstone");
		dragonstoneFlat = new GOTBiomeDragonstoneFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x75A83E).setBiomeName("dragonstone");
		essos = new GOTBiomeEssos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x92A54A).setBiomeName("essos");
		essosForest = new GOTBiomeEssosForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x617027).setBiomeName("essosForest");
		essosHills = new GOTBiomeEssos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 1f).setColor(0x7B843A).setBiomeName("essosHills");
		essosMountains = new GOTBiomeEssosMountains(id++, true).setIsNeverWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0x808080).setBiomeName("essosMountains");
		farNorthSnowy = new GOTBiomeFarNorthSnowy(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xC9DAE0).setBiomeName("farNorthSnowy");
		fireField = new GOTBiomeFireField(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x8CA242).setBiomeName("fireField");
		frostfangs = new GOTBiomeFrostfangs(id++, true).setIsAlwaysWinter().setMinMaxHeight(2.0f, 2.0f).setColor(0xCBE9F1).setBiomeName("frostfangs");
		ghiscar = new GOTBiomeGhiscar(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA3AA68).setBiomeName("ghiscar");
		ghiscarColony = new GOTBiomeGhiscarColony(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x5B8C2A).setBiomeName("ghiscarColony");
		giftNew = new GOTBiomeGiftNew(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9CC452).setBiomeName("giftNew");
		giftOld = new GOTBiomeGiftOld(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA5C681).setBiomeName("giftOld");
		hauntedForest = new GOTBiomeHauntedForest(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA5C9AA).setBiomeName("hauntedForest");
		ibben = new GOTBiomeIbben(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x74B747).setBiomeName("ibben");
		ibbenBeach = new GOTBiomeIbbenBeach(id++, false).setBeachBlock(Blocks.sand, 0).setColor(14204248).setBiomeName("beach").setIsNeverWinter();
		ibbenColony = new GOTBiomeIbbenColony(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x8EA33A).setBiomeName("ibbenColony");
		ibbenSea = new GOTBiomeOcean(id++, true).setIsNeverWinter().setMinMaxHeight(-1.0f, 0.3f).setColor(0x02598E).setBiomeName("ocean");
		ibbenTaiga = new GOTBiomeIbbenTaiga(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x437C1F).setBiomeName("ibbenTaiga");
		ifekevronForest = new GOTBiomeIfekevronForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x657231).setBiomeName("ifekevronForest");
		ironborn = new GOTBiomeIronborn(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x86B23E).setBiomeName("ironborn");
		ironbornFlat = new GOTBiomeIronbornFlat(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x86B23D).setBiomeName("ironborn");
		ironbornHills = new GOTBiomeIronbornHills(id++, true).setIsLongWinter().setMinMaxHeight(0f, 1f).setColor(0x7F9951).setBiomeName("ironbornHills");
		irontree = new GOTBiomeIrontree(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x677F2A).setBiomeName("irontree");
		island = new GOTBiomeOcean(id++, false).setIsNeverWinter().setMinMaxHeight(0.0f, 0.3f).setColor(10138963).setBiomeName("island");
		isleOfFaces = new GOTBiomeIsleOfFaces(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x835832).setBiomeName("isleOfFaces");
		jogosNhai = new GOTBiomeJogosNhai(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xABB74D).setBiomeName("jogosNhai");
		jogosNhaiDesert = new GOTBiomeJogosNhaiDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(0xD6C182).setBiomeName("jogosNhaiDesert");
		kingSpears = new GOTBiomeNorthMountains(id++, true).setIsSeasonalWinter().setMinMaxHeight(-1.0f, 2.0f).setColor(0xA0A0A0).setBiomeName("kingSpears");
		lake = new GOTBiomeLake(id++, false).setTemperatureRainfall(0.8F, 0.8F).setColor(3433630).setBiomeName("lake");
		lhazar = new GOTBiomeLhazar(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xB5AA46).setBiomeName("lhazar");
		longSummer = new GOTBiomeLongSummer(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x95A03D).setBiomeName("longSummer");
		lorath = new GOTBiomeLorath(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xACA344).setBiomeName("lorath");
		lorathFlat = new GOTBiomeLorathFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xACA346).setBiomeName("lorath");
		lys = new GOTBiomeLys(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x70A564).setBiomeName("lys");
		lysFlat = new GOTBiomeLysFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x70A566).setBiomeName("lys");
		massy = new GOTBiomeMassy(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x73A540).setBiomeName("massy");
		massyFlat = new GOTBiomeDragonstoneFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x73A541).setBiomeName("massy");
		meereen = new GOTBiomeMeereen(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xB5AA68).setBiomeName("meereen");
		mercenary = new GOTBiomeMercenary(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x94A058).setBiomeName("mercenary");
		mossovy = new GOTBiomeMossovy(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x71843B).setBiomeName("mossovy");
		mossovyForest = new GOTBiomeMossovyForest(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x617232).setBiomeName("mossovyForest");
		mossovyMarshes = new GOTBiomeMossovyMarshes(id++, true).setIsLongWinter().setMinMaxHeight(0.0f, 0.1f).setColor(0x677F43).setBiomeName("mossovyMarshes");
		mossovySea = new GOTBiomeMossovySea(id++, true).setIsLongWinter().setMinMaxHeight(-1.0f, 0.8f).setColor(0x02517F).setBiomeName("mossovySea");
		mossovySopkas = new GOTBiomeMossovySopkas(id++, true).setIsLongWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0xADADAD).setBiomeName("mossovySopkas");
		myr = new GOTBiomeMyr(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9AA54F).setBiomeName("myr");
		myrFlat = new GOTBiomeMyrFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9AA54E).setBiomeName("myr");
		naath = new GOTBiomeNaath(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x7DA33C).setBiomeName("naath");
		neck = new GOTBiomeNeck(id++, true).setIsLongWinter().setMinMaxHeight(0.0f, 0.1f).setColor(0x578C54).setBiomeName("neck");
		newGhis = new GOTBiomeNewGhis(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xA2A86B).setBiomeName("ghiscar");
		north = new GOTBiomeNorth(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(8959044).setBiomeName("north");
		northBarrows = new GOTBiomeNorthBarrows(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x85AF44).setBiomeName("northBarrows");
		northFlat = new GOTBiomeNorthFlat(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x88B443).setBiomeName("north");
		northForest = new GOTBiomeNorthForest(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x538E2F).setBiomeName("northForest");
		northHills = new GOTBiomeWesteros(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 1f).setColor(0x74A346).setBiomeName("northHills");
		northMountains = new GOTBiomeNorthMountains(id++, true).setIsLongWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0xD8D8D8).setBiomeName("northMountains");
		northTown = new GOTBiomeNorthTown(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9DB27B).setBiomeName("northTown");
		northWild = new GOTBiomeNorthWild(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x97B26C).setBiomeName("northWild");
		norvos = new GOTBiomeNorvos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x7DA344).setBiomeName("norvos");
		norvosFlat = new GOTBiomeNorvosFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x7DA345).setBiomeName("norvos");
		ocean = new GOTBiomeOcean(id++, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-1.0f, 0.3f).setColor(153997).setBiomeName("ocean");
		pentos = new GOTBiomePentos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x93A040).setBiomeName("pentos");
		pentosFlat = new GOTBiomePentosFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x93A041).setBiomeName("pentos");
		qarth = new GOTBiomeQarth(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9EA880).setBiomeName("qarth");
		qarthDesert = new GOTBiomeQarthDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(0xDCC175).setBiomeName("qarthDesert");
		qarthFlat = new GOTBiomeQarthFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9EA882).setBiomeName("qarth");
		qohor = new GOTBiomeQohor(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA2A832).setBiomeName("qohor");
		qohorFlat = new GOTBiomeQohorFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xBAA833).setBiomeName("qohor");
		qohorForest = new GOTBiomeQohorForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xBAA832).setBiomeName("qohorForest");
		rainwood = new GOTBiomeRainwood(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x59994B).setBiomeName("rainwood");
		reach = new GOTBiomeReach(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x8CB542).setBiomeName("reach");
		reachArbor = new GOTBiomeReachArbor(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x8CAC44).setBiomeName("reachArbor");
		reachFlat = new GOTBiomeReachFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x8CB543).setBiomeName("reach");
		reachTown = new GOTBiomeReachTown(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x8CB586).setBiomeName("reachTown");
		redBeach = new GOTBiomeRedBeach(id++, false).setBeachBlock(Blocks.sand, 1).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(14403247).setBiomeName("beach");
		redMesa = new GOTBiomeDorneMesa(id++, true).setIsNeverWinter().setMinMaxHeight(1.5f, 0.0f).setColor(0xD6C9A2).setBiomeName("redMesa");
		redSea = new GOTBiomeRedSea(id++, true).setIsNeverWinter().setMinMaxHeight(-1.0f, 0.3f).setColor(0xC15B32).setBiomeName("redSea");
		river = new GOTBiomeRiver(id++, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setBiomeName("river");
		riverlands = new GOTBiomeRiverlands(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(7910726).setBiomeName("riverlands");
		riverlandsFlat = new GOTBiomeRiverlandsFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x78B547).setBiomeName("riverlands");
		riverlandsHills = new GOTBiomeRiverlandsForest(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x4C822A).setBiomeName("riverlandsHills");
		shadowLand = new GOTBiomeShadowLand(id++, true).setMinMaxHeight(0.1f, 0.15f).setTemperatureRainfall(1.0f, 0.2f).setColor(0x8E8854).setBiomeName("shadowLand");
		shadowMountains = new GOTBiomeShadowMountains(id++, true).setMinMaxHeight(2.0f, 2.0f).setTemperatureRainfall(1.0f, 0.2f).setColor(0x635E3B).setBiomeName("shadowMountains");
		shadowTown = new GOTBiomeShadowTown(id++, true).setMinMaxHeight(0.1f, 0.0f).setTemperatureRainfall(1.0f, 0.2f).setColor(0x8C8340).setBiomeName("shadowTown");
		ulos = new GOTBiomeUlos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x2B4819).setBiomeName("ulos");
		skagos = new GOTBiomeSkagos(id++, true).setIsLongWinter().setMinMaxHeight(1.0f, 1.0f).setColor(0x94AF67).setBiomeName("skagos");
		skirlingPass = new GOTBiomeSkirlingPass(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xC7E3EA).setBiomeName("skirlingPass");
		sothoryosBushland = new GOTBiomeSothoryosBushland(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x998F3D).setBiomeName("sothoryosBushland");
		sothoryosDesert = new GOTBiomeSothoryosDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(0xDBC68C).setBiomeName("sothoryosDesert");
		sothoryosDesertCold = new GOTBiomeSothoryosDesertCold(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.1f).setColor(0xDAD4AF).setBiomeName("sothoryosDesertCold");
		sothoryosDesertHills = new GOTBiomeSothoryosDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.2f).setColor(0xB9A762).setBiomeName("sothoryosDeserHills");
		sothoryosFrost = new GOTBiomeSothoryosFrost(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xD8D8D2).setBiomeName("sothoryosFrost");
		sothoryosHell = new GOTBiomeSothoryosHell(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x21301D).setBiomeName("sothoryosHell");
		sothoryosJungle = new GOTBiomeSothoryosJungle(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x4A7222).setBiomeName("sothoryosJungle");
		sothoryosJungleEdge = new GOTBiomeSothoryosJungleEdge(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x7B932C).setBiomeName("sothoryosJungleEdge");
		sothoryosKanuka = new GOTBiomeSothoryosKanuka(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x7A8C31).setBiomeName("sothoryosKanuka");
		sothoryosMangrove = new GOTBiomeSothoryosMangrove(id++, true).setIsNeverWinter().setMinMaxHeight(0.0f, 0.1f).setColor(0x737f42).setBiomeName("sothoryosMangrove");
		sothoryosMountains = new GOTBiomeSothoryosMountains(id++, true).setIsNeverWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0x665C48).setBiomeName("sothoryosMountains");
		sothoryosSavannah = new GOTBiomeSothoryosSavannah(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x88A030).setBiomeName("sothoryosSavannah");
		sothoryosTaiga = new GOTBiomeSothoryosTaiga(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA3B481).setBiomeName("sothoryosTaiga");
		stepstones = new GOTBiomeStepstones(id++, true).setIsNeverWinter().setMinMaxHeight(0.0f, 0.5f).setColor(0x9BA37A).setBiomeName("stepstones");
		stoneCoast = new GOTBiomeStoneCoast(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x727B78).setBiomeName("stoneCoast");
		stormlands = new GOTBiomeStormlands(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x75B239).setBiomeName("stormlands");
		stormlandsFlat = new GOTBiomeStormlandsFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x75B240).setBiomeName("stormlands");
		stormlandsTown = new GOTBiomeStormlandsFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x84af59).setBiomeName("stormlandsTown");
		summerIslands = new GOTBiomeSummerIslands(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x83A54A).setBiomeName("summerIslands");
		tarth = new GOTBiomeTarth(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x75B241).setBiomeName("tarth");
		thenn = new GOTBiomeThenn(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xC3DDCF).setBiomeName("thenn");
		tropicalForest = new GOTBiomeTropicalForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x007F0E).setBiomeName("tropicalForest");
		tyrosh = new GOTBiomeTyrosh(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x9AA5A2).setBiomeName("tyrosh");
		tyroshFlat = new GOTBiomeTyroshFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9AA5A4).setBiomeName("tyrosh");
		ulthos = new GOTBiomeUlthos(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x2B3F19).setBiomeName("ulthos");
		ulthosDesert = new GOTBiomeUlthosDesert(id++, true).setIsNeverWinter().setMinMaxHeight(0.2f, 0.1f).setColor(0xBFAF7C).setBiomeName("ulthosDesert");
		ulthosMountains = new GOTBiomeUlthosMountains(id++, true).setIsNeverWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0x222618).setBiomeName("ulthosMountains");
		valyria = new GOTBiomeValyria(id++, true).setMinMaxHeight(0.1f, 0.15f).setTemperatureRainfall(1.2F, 0.8F).setColor(6710111).setBiomeName("valyria");
		valyriaSea = new GOTBiomeValyriaSea(id++, true).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-1.0f, 0.3f).setColor(0x0D304C).setBiomeName("valyriaSea");
		valyriaVolcano = new GOTBiomeValyriaVolcano(id++, true).setMinMaxHeight(2.0f, 2.0f).setTemperatureRainfall(1.2F, 0.8F).setColor(0xA5A5A5).setBiomeName("valyriaVolcano");
		volantis = new GOTBiomeVolantis(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA5A344).setBiomeName("volantis");
		volantisFlat = new GOTBiomeVolantisFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xA5A343).setBiomeName("volantis");
		volantisForest = new GOTBiomeVolantisForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xCE9F32).setBiomeName("volantisForest");
		volantisMarshes = new GOTBiomeVolantisMarshes(id++, true).setIsNeverWinter().setMinMaxHeight(0.0f, 0.1f).setColor(0x89933e).setBiomeName("volantisMarshes");
		westerlands = new GOTBiomeWesterlands(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xA9B542).setBiomeName("westerlands");
		westerlandsFlat = new GOTBiomeWesterlandsFlat(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xA9B543).setBiomeName("westerlands");
		westerlandsHills = new GOTBiomeWesterlandsHills(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.0f, 1f).setColor(0x9CA548).setBiomeName("westerlandsHills");
		westerlandsTown = new GOTBiomeWesterlandsTown(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xBCC94A).setBiomeName("westerlandsTown");
		westerosForest = new GOTBiomeWesterosForest(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(5871922).setBiomeName("westerosForest");
		wetwood = new GOTBiomeWetwood(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.0f, 0.1f).setColor(0x599958).setBiomeName("wetwood");
		whisperingWood = new GOTBiomeWhisperingWood(id++, true).setIsSeasonalWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x598632).setBiomeName("whisperingWood");
		wolfswood = new GOTBiomeWolfswood(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x4A7F2A).setBiomeName("wolfswood");
		yeen = new GOTBiomeYeen(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0x240035).setBiomeName("yeen");
		yiTi = new GOTBiomeYiTi(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xBEB74D).setBiomeName("yiTi");
		yiTiFlat = new GOTBiomeYiTiFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xBEB74E).setBiomeName("yiTi");
		yiTiWasteland = new GOTBiomeYiTiWasteland(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xBEB74F).setBiomeName("yiTi");
		yunkai = new GOTBiomeYunkai(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xA3AA48).setBiomeName("yunkai");
		ibbenMountains = new GOTBiomeIbbenMountains(id++, true).setIsLongWinterAZ().setMinMaxHeight(2.0f, 2.0f).setColor(0x808081).setBiomeName("ibbenMountains");
		ibbenFlat = new GOTBiomeIbbenFlat(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x74B748).setBiomeName("ibben");
		giftNewFlat = new GOTBiomeGiftNewFlat(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x9CC453).setBiomeName("giftNew");
		giftOldFlat = new GOTBiomeGiftOldFlat(id++, true).setIsAlwaysWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0xA5C682).setBiomeName("giftOld");
		mossovyFlat = new GOTBiomeMossovyFlat(id++, true).setIsLongWinter().setMinMaxHeight(0.1f, 0.0f).setColor(0x71843C).setBiomeName("mossovy");
		lhazarFlat = new GOTBiomeLhazarFlat(id++, true).setIsNeverWinter().setMinMaxHeight(0.1f, 0.15f).setColor(0xB5AA47).setBiomeName("lhazar");
	}

	public static void updateWaterColor(int i, int j, int k) {
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
		Color water = new Color(r += northColors[0], g += northColors[1], b += northColors[2]);
		int waterRGB = water.getRGB();
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome == null || biome.biomeColors.hasCustomWater()) {
				continue;
			}
			biome.biomeColors.updateWater(waterRGB);
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

	public int getWallTop() {
		return 0;
	}
}