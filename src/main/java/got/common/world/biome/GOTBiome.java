package got.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.GOTDimension;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.entity.animal.*;
import got.common.entity.essos.GOTEntityEssosBandit;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.GOTEntityWesterosBandit;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.essos.*;
import got.common.world.biome.other.GOTBiomeBeach;
import got.common.world.biome.other.GOTBiomeLake;
import got.common.world.biome.other.GOTBiomeOcean;
import got.common.world.biome.other.GOTBiomeRiver;
import got.common.world.biome.sothoryos.*;
import got.common.world.biome.ulthos.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import got.common.world.biome.westeros.*;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeInvasionSpawns;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.EnumHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GOTBiome extends BiomeGenBase {
	public static Class<?>[][] correctCreatureTypeParams = {{EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE}};
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
	public static GOTBiome crownlandsForest;
	public static GOTBiome crownlands;
	public static GOTBiome crownlandsTown;
	public static GOTBiome dorne;
	public static GOTBiome dorneDesert;
	public static GOTBiome dorneMountains;
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
	public static GOTBiome massyHills;
	public static GOTBiome meereen;
	public static GOTBiome disputedLands;
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
	public static GOTBiome reach;
	public static GOTBiome reachArbor;
	public static GOTBiome reachTown;
	public static GOTBiome redMesa;
	public static GOTBiome redSea;
	public static GOTBiome river;
	public static GOTBiome riverlands;
	public static GOTBiome riverlandsForest;
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
	public static GOTBiome volantisOrangeForest;
	public static GOTBiome volantisMarshes;
	public static GOTBiome westerlands;
	public static GOTBiome westerlandsHills;
	public static GOTBiome westerlandsTown;
	public static GOTBiome wetwood;
	public static GOTBiome whisperingWood;
	public static GOTBiome wolfswood;
	public static GOTBiome yeen;
	public static GOTBiome yiTi;
	public static GOTBiome yiTiMarshes;
	public static GOTBiome yiTiWasteland;
	public static GOTBiome yunkai;
	public static GOTBiome reachHills;
	public static GOTBiome massy;
	public static GOTBiome braavosForest;
	public static GOTBiome lorathForest;
	public static GOTBiome pentosForest;
	public static GOTBiome lorathHills;
	public static GOTBiome disputedLandsForest;
	public static GOTBiome myrForest;
	public static GOTBiome qohorHills;
	public static GOTBiome jogosNhaiForest;
	public static GOTBiome ghiscarForest;
	public static GOTBiome lhazarForest;
	public static GOTBiome dothrakiSeaForest;
	public static GOTBiome summerIslandsTropicalForest;
	public static GOTBiome yiTiTropicalForest;
	public static GOTBiome norvosForest;
	public static GOTBiome arrynForest;
	public static GOTBiome volantisForest;
	public static GOTBiome westerlandsForest;
	public static GOTBiome ironbornForest;
	public static GOTBiome reachForest;
	public static GOTBiome dorneForest;
	public static GOTBiome kingswoodNorth;
	public static GOTBiome kingswoodSouth;
	public static GOTBiome tarthForest;
	public static GOTBiome stormlandsForest;

	public GOTBiomeDecorator decorator;
	public int topBlockMeta;
	public int fillerBlockMeta;
	public float heightBaseParameter;
	public boolean enablePodzol = true;
	public boolean enableRocky;
	public GOTBiomeVariantList biomeVariantsLarge = new GOTBiomeVariantList();
	public GOTBiomeVariantList biomeVariantsSmall = new GOTBiomeVariantList();
	public GOTBiomeSpawnList npcSpawnList = new GOTBiomeSpawnList(this);
	public List<SpawnListEntry> spawnableGOTAmbientList = new ArrayList<>();
	public GOTEventSpawner.EventChance banditChance;
	public Class<? extends GOTEntityNPC> banditEntityClass;
	public GOTBiomeInvasionSpawns invasionSpawns;
	public BiomeColors biomeColors = new BiomeColors(this);
	public BiomeTerrain biomeTerrain = new BiomeTerrain(this);
	public float variantChance = 0.4f;
	public GOTClimateType climateType;
	public GOTDimension biomeDimension;

	protected GOTBiome(int i, boolean major) {
		this(i, major, GOTDimension.GAME_OF_THRONES);
	}

	protected GOTBiome(int i, boolean major, GOTDimension dim) {
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
		banditChance = GOTEventSpawner.EventChance.COMMON;
		invasionSpawns = new GOTBiomeInvasionSpawns(this);
		GOTFixer.affixWaypointLocations(this);
	}

	public static void preInit() {
		beach = new GOTBiomeBeach(9, false).setBeachBlock(Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
		beachGravel = new GOTBiomeBeach(10, false).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
		beachWhite = new GOTBiomeBeach(11, false).setBeachBlock(GOTRegistry.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
		river = new GOTBiomeRiver(108, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setBiomeName("river");
		lake = new GOTBiomeLake(60, false).setTemperatureRainfall(0.8F, 0.8F).setColor(3433630).setBiomeName("lake");

		ocean = new GOTBiomeOcean(91, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x024B75).setMinMaxHeight(-1.0f, 0.3f).setBiomeName("ocean");
		ocean1 = new GOTBiomeOcean(166, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x036FAC).setMinMaxHeight(-0.7f, 0.3f).setBiomeName("ocean");
		ocean2 = new GOTBiomeOcean(167, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x026193).setMinMaxHeight(-0.8f, 0.3f).setBiomeName("ocean");
		ocean3 = new GOTBiomeOcean(46, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x025582).setMinMaxHeight(-0.9f, 0.3f).setBiomeName("ocean");
		island = new GOTBiomeOcean(55, false).setTemperatureRainfall(0.8F, 0.8F).setColor(10138963).setMinMaxHeight(0.0f, 0.3f).setBiomeName("island");
		kingSpears = new GOTBiomeOcean(59, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0xA0A0A0).setMinMaxHeight(-0.7f, 0.3f).setBiomeName("kingSpears");
		shadowLand = new GOTBiomeShadowLand(112, true).setMinMaxHeight(0.1f, 0.15f).setColor(0x8E8854).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowLand");
		shadowMountains = new GOTBiomeShadowMountains(113, true).setMinMaxHeight(2.0f, 2.0f).setColor(0x635E3B).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowMountains");
		shadowTown = new GOTBiomeShadowTown(114, true).setMinMaxHeight(0.1f, 0.15f).setColor(0xA39C68).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowTown");
		valyria = new GOTBiomeValyria(145, true).setMinMaxHeight(0.1f, 0.15f).setColor(6710111).setTemperatureRainfall(1.2F, 0.8F).setBiomeName("valyria");
		valyriaSea = new GOTBiomeValyria(146, true).setMinMaxHeight(-1.0f, 0.3f).setColor(0x0D304C).setTemperatureRainfall(0.8F, 0.8F).setBiomeName("valyriaSea");
		valyriaVolcano = new GOTBiomeValyriaVolcano(147, true).setMinMaxHeight(2.0f, 2.0f).setColor(0xA5A5A5).setTemperatureRainfall(1.2F, 0.8F).setBiomeName("valyriaVolcano");

		alwaysWinter = new GOTBiomeAlwaysWinter(1, true).setClimateType(GOTClimateType.WINTER).setColor(0xC9DAE0).setMinMaxHeight(0.1f, 0.15f).setBiomeName("alwaysWinter");
		arryn = new GOTBiomeArryn(3, true).setClimateType(GOTClimateType.NORMAL).setColor(0x8CBA5B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arryn");
		arrynForest = new GOTBiomeArrynForest(177, true).setClimateType(GOTClimateType.NORMAL).setColor(0x598440).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynForest");
		arrynMountains = new GOTBiomeArrynMountains(6, true).setClimateType(GOTClimateType.NORMAL_AZ).setColor(0xC1E0BA).setMinMaxHeight(2.0f, 2.0f).setBiomeName("arrynMountains");
		arrynMountainsFoothills = new GOTBiomeArrynMountainsFoothills(5, true).setClimateType(GOTClimateType.NORMAL).setColor(0x598454).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynMountainsFoothills");
		arrynTown = new GOTBiomeArrynTown(7, true).setClimateType(GOTClimateType.NORMAL).setColor(0x89BC5C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynTown");
		astapor = new GOTBiomeAstapor(8, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB1BA7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("astapor");
		beachRed = new GOTBiomeRedBeach(105, false).setClimateType(GOTClimateType.SUMMER).setColor(14403247).setMinMaxHeight(0.1f, 0.0f).setBiomeName("beach");
		boneMountains = new GOTBiomeBoneMountains(14, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xE5E2B3).setMinMaxHeight(2.0f, 2.0f).setBiomeName("boneMountains");
		braavos = new GOTBiomeBraavos(15, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAA9747).setMinMaxHeight(0.1f, 0.15f).setBiomeName("braavos");
		braavosForest = new GOTBiomeBraavosForest(149, true).setClimateType(GOTClimateType.SUMMER).setColor(0x686530).setMinMaxHeight(0.1f, 0.15f).setBiomeName("braavosForest");
		braavosHills = new GOTBiomeBraavos(96, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8E7E3B).setMinMaxHeight(0.1f, 1.0f).setBiomeName("braavosHills");
		cannibalSands = new GOTBiomeCannibalSands(23, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCCBC82).setMinMaxHeight(0.1f, 0.15f).setBiomeName("cannibalSands");
		cannibalSandsHills = new GOTBiomeCannibalSands(83, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A472).setMinMaxHeight(0.1f, 1.0f).setBiomeName("cannibalSandsHills");
		coldCoast = new GOTBiomeColdCoast(17, true).setClimateType(GOTClimateType.WINTER).setColor(0xC0D6CC).setMinMaxHeight(0.1f, 0.15f).setBiomeName("coldCoast");
		crownlands = new GOTBiomeCrownlands(19, true).setClimateType(GOTClimateType.NORMAL).setColor(0x81af57).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlands");
		crownlandsForest = new GOTBiomeCrownlandsForest(18, true).setClimateType(GOTClimateType.NORMAL).setColor(0x507A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlandsForest");
		crownlandsTown = new GOTBiomeCrownlandsTown(22, true).setClimateType(GOTClimateType.NORMAL).setColor(0x9DC677).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlandsTown");
		disputedLands = new GOTBiomeDisputedLands(70, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAFB25B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("disputedLands");
		disputedLandsForest = new GOTBiomeDisputedLandsForest(48, true).setClimateType(GOTClimateType.SUMMER).setColor(0x707C40).setMinMaxHeight(0.1f, 0.15f).setBiomeName("disputedLandsForest");
		dorne = new GOTBiomeDorne(24, true).setClimateType(GOTClimateType.SUMMER).setColor(0xaaa753).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorne");
		dorneDesert = new GOTBiomeDorneDesert(25, true).setClimateType(GOTClimateType.SUMMER).setColor(14074229).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorneDesert");
		dorneForest = new GOTBiomeDorneForest(100, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6B7239).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorneForest");
		dorneMountains = new GOTBiomeDorneMountains(27, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xD7E0AF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("dorneMountains");
		dothrakiHills = new GOTBiomeDothrakiSea(103, true).setClimateType(GOTClimateType.SUMMER).setColor(0x858E3B).setMinMaxHeight(0.1f, 1.0f).setBiomeName("dothrakiHills");
		dothrakiSea = new GOTBiomeDothrakiSea(30, true).setClimateType(GOTClimateType.SUMMER).setColor(10398278).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dothrakiSea");
		dothrakiSeaForest = new GOTBiomeDothrakiSeaForest(174, true).setClimateType(GOTClimateType.SUMMER).setColor(0x637531).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dothrakiSeaForest");
		dragonstone = new GOTBiomeDragonstone(31, true).setClimateType(GOTClimateType.NORMAL).setColor(0x96AF85).setMinMaxHeight(0.3f, 0.35f).setBiomeName("dragonstone");
		essos = new GOTBiomeEssos(33, true).setClimateType(GOTClimateType.SUMMER).setColor(0x92A54A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("essos");
		essosForest = new GOTBiomeEssosForest(34, true).setClimateType(GOTClimateType.SUMMER).setColor(0x617027).setMinMaxHeight(0.1f, 0.15f).setBiomeName("essosForest");
		essosMarshes = new GOTBiomeEssosMarshes(4, true).setClimateType(GOTClimateType.SUMMER).setColor(0x739655).setMinMaxHeight(0.0f, 0.1f).setBiomeName("volantisMarshes");
		essosMountains = new GOTBiomeEssosMountains(36, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xDDDDAF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("essosMountains");
		farNorthSnowy = new GOTBiomeWesterosFrost(37, true).setClimateType(GOTClimateType.WINTER).setColor(0xD7E4E5).setMinMaxHeight(0.1f, 0.15f).setBiomeName("farNorthSnowy");
		fireField = new GOTBiomeFireField(38, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC2C47B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("fireField");
		frostfangs = new GOTBiomeFrostfangs(39, true).setClimateType(GOTClimateType.WINTER).setColor(0xB8C5C6).setMinMaxHeight(2.0f, 2.0f).setBiomeName("frostfangs");
		ghiscar = new GOTBiomeGhiscar(40, true).setClimateType(GOTClimateType.SUMMER).setColor(0xADAF6B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscar");
		ghiscarColony = new GOTBiomeGhiscarColony(41, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5B8C2A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarColony");
		ghiscarForest = new GOTBiomeGhiscarForest(172, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6F7A4B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarForest");
		giftNew = new GOTBiomeGiftNew(42, true).setClimateType(GOTClimateType.COLD).setColor(0x9CC78A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("giftNew");
		giftOld = new GOTBiomeGiftOld(43, true).setClimateType(GOTClimateType.WINTER).setColor(0xB3D3A9).setMinMaxHeight(0.1f, 0.15f).setBiomeName("giftOld");
		hauntedForest = new GOTBiomeHauntedForest(44, true).setClimateType(GOTClimateType.WINTER).setColor(0x88AD8D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("hauntedForest");
		ibben = new GOTBiomeIbben(45, true).setClimateType(GOTClimateType.COLD).setColor(0x7AA04B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibben");
		ibbenColony = new GOTBiomeIbbenColony(47, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EAF56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibbenColony");
		ibbenColonyHills = new GOTBiomeIbbenColony(110, true).setClimateType(GOTClimateType.SUMMER).setColor(0x869349).setMinMaxHeight(0.1f, 1.0f).setBiomeName("ibbenColonyHills");
		ibbenMountains = new GOTBiomeIbbenMountains(165, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xafbc9f).setMinMaxHeight(2.0f, 2.0f).setBiomeName("ibbenMountains");
		ibbenTaiga = new GOTBiomeIbbenTaiga(49, true).setClimateType(GOTClimateType.COLD).setColor(0x4C6B23).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibbenTaiga");
		ifekevronForest = new GOTBiomeIfekevronForest(50, true).setClimateType(GOTClimateType.SUMMER).setColor(0x657231).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ifekevronForest");
		ironborn = new GOTBiomeIronborn(51, true).setClimateType(GOTClimateType.COLD).setColor(0x8BBA83).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ironborn");
		ironbornForest = new GOTBiomeIronbornForest(180, true).setClimateType(GOTClimateType.COLD).setColor(0x59845E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ironbornForest");
		ironbornHills = new GOTBiomeIronbornHills(53, true).setClimateType(GOTClimateType.COLD).setColor(0x779E70).setMinMaxHeight(0.1f, 1.0f).setBiomeName("ironbornHills");
		irontree = new GOTBiomeIrontree(54, true).setClimateType(GOTClimateType.COLD).setColor(0x868254).setMinMaxHeight(0.1f, 0.15f).setBiomeName("irontree");
		isleOfFaces = new GOTBiomeIsleOfFaces(56, true).setClimateType(GOTClimateType.NORMAL).setColor(0x82A556).setMinMaxHeight(0.1f, 0.15f).setBiomeName("isleOfFaces");
		jogosNhai = new GOTBiomeJogosNhai(57, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2B762).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhai");
		jogosNhaiDesert = new GOTBiomeJogosNhaiDesert(58, true).setClimateType(GOTClimateType.SUMMER).setColor(0xD6C182).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhaiDesert");
		jogosNhaiForest = new GOTBiomeJogosNhaiForest(171, true).setClimateType(GOTClimateType.SUMMER).setColor(0x727F44).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhaiForest");
		jogosNhaiHills = new GOTBiomeJogosNhai(2, true).setClimateType(GOTClimateType.SUMMER).setColor(0x979B53).setMinMaxHeight(0.1f, 1.0f).setBiomeName("jogosNhaiHills");
		kingswoodNorth = new GOTBiomeKingswoodNorth(21, true).setClimateType(GOTClimateType.NORMAL).setColor(0x587A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("kingswood");
		kingswoodSouth = new GOTBiomeKingswoodSouth(183, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5F7A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("kingswood");
		lhazar = new GOTBiomeLhazar(61, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2B758).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lhazar");
		lhazarForest = new GOTBiomeLhazarForest(173, true).setClimateType(GOTClimateType.SUMMER).setColor(0x74823F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lhazarForest");
		lhazarHills = new GOTBiomeLhazar(117, true).setClimateType(GOTClimateType.SUMMER).setColor(0x979B4C).setMinMaxHeight(0.1f, 1.0f).setBiomeName("lhazarHills");
		longSummer = new GOTBiomeLongSummer(62, true).setClimateType(GOTClimateType.SUMMER).setColor(0x95A03D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("longSummer");
		lorath = new GOTBiomeLorath(63, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA5B25B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorath");
		lorathForest = new GOTBiomeLorathForest(153, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6A7C40).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorathForest");
		lorathHills = new GOTBiomeLorath(162, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8C964E).setMinMaxHeight(0.1f, 1.0f).setBiomeName("lorathHills");
		lorathMaze = new GOTBiomeLorathMaze(93, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA8AF7B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorathMaze");
		lys = new GOTBiomeLys(65, true).setClimateType(GOTClimateType.SUMMER).setColor(0x70A564).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lys");
		massy = new GOTBiomeDragonstone(28, true).setClimateType(GOTClimateType.NORMAL).setColor(0x89AD68).setMinMaxHeight(0.1f, 0.15f).setBiomeName("massy");
		massyHills = new GOTBiomeDragonstoneHills(67, true).setClimateType(GOTClimateType.NORMAL).setColor(0x749158).setMinMaxHeight(0.1f, 1.0f).setBiomeName("massy");
		meereen = new GOTBiomeMeereen(69, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC1BA7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("meereen");
		mossovy = new GOTBiomeMossovy(71, true).setClimateType(GOTClimateType.COLD).setColor(0x92A35E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("mossovy");
		mossovyForest = new GOTBiomeMossovyForest(72, true).setClimateType(GOTClimateType.COLD).setColor(0x647A46).setMinMaxHeight(0.1f, 0.15f).setBiomeName("mossovyForest");
		mossovyMarshes = new GOTBiomeMossovyMarshes(73, true).setClimateType(GOTClimateType.COLD).setColor(0x749975).setMinMaxHeight(0.0f, 0.1f).setBiomeName("mossovyMarshes");
		mossovyMountains = new GOTBiomeMossovyMountains(75, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xD7E2B3).setMinMaxHeight(2.0f, 2.0f).setBiomeName("mossovyMountains");
		myr = new GOTBiomeMyr(76, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA0A349).setMinMaxHeight(0.1f, 0.15f).setBiomeName("myr");
		myrForest = new GOTBiomeMyrForest(168, true).setClimateType(GOTClimateType.SUMMER).setColor(0x626D31).setMinMaxHeight(0.1f, 0.15f).setBiomeName("myrForest");
		naath = new GOTBiomeSummerIslands(78, true).setClimateType(GOTClimateType.SUMMER).setColor(0x7DA33C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("naath");
		neck = new GOTBiomeNeck(79, true).setClimateType(GOTClimateType.COLD).setColor(0x6DAA75).setMinMaxHeight(0.0f, 0.1f).setBiomeName("neck");
		newGhis = new GOTBiomeNewGhis(80, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB1AF7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("newGhis");
		north = new GOTBiomeNorth(81, true).setClimateType(GOTClimateType.COLD).setColor(0x8BBA6D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("north");
		northBarrows = new GOTBiomeNorthBarrows(82, true).setClimateType(GOTClimateType.COLD).setColor(0x97C17C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northBarrows");
		northForest = new GOTBiomeNorthForest(84, true).setClimateType(GOTClimateType.COLD).setColor(0x59844E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northForest");
		northHills = new GOTBiomeNorth(85, true).setClimateType(GOTClimateType.COLD).setColor(0x779E5D).setMinMaxHeight(0.1f, 1.0f).setBiomeName("northHills");
		northMountains = new GOTBiomeNorthMountains(86, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xD3E0BA).setMinMaxHeight(2.0f, 2.0f).setBiomeName("northMountains");
		northTown = new GOTBiomeNorthTown(87, true).setClimateType(GOTClimateType.COLD).setColor(0xAAD190).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northTown");
		northWild = new GOTBiomeNorthWild(88, true).setClimateType(GOTClimateType.COLD).setColor(0xA9C69D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northWild");
		norvos = new GOTBiomeNorvos(89, true).setClimateType(GOTClimateType.SUMMER).setColor(0x7DA344).setMinMaxHeight(0.1f, 0.15f).setBiomeName("norvos");
		norvosForest = new GOTBiomeNorvosForest(176, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4A6D2E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("norvosForest");
		norvosHills = new GOTBiomeNorvos(35, true).setClimateType(GOTClimateType.SUMMER).setColor(0x678738).setMinMaxHeight(0.1f, 1.0f).setBiomeName("norvosHills");
		pentos = new GOTBiomePentos(92, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A349).setMinMaxHeight(0.1f, 0.15f).setBiomeName("pentos");
		pentosForest = new GOTBiomePentosForest(169, true).setClimateType(GOTClimateType.SUMMER).setColor(0x706F34).setMinMaxHeight(0.1f, 0.15f).setBiomeName("pentosForest");
		pentosHills = new GOTBiomePentos(98, true).setClimateType(GOTClimateType.SUMMER).setColor(0x96893F).setMinMaxHeight(0.1f, 1.0f).setBiomeName("pentosHills");
		qarth = new GOTBiomeQarth(94, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EAA4E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarth");
		qarthColony = new GOTBiomeQarthColony(52, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9DAF54).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarthColony");
		qarthDesert = new GOTBiomeQarthDesert(95, true).setClimateType(GOTClimateType.SUMMER).setColor(0xDCC175).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarthDesert");
		qohor = new GOTBiomeQohor(97, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAFB350).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qohor");
		qohorForest = new GOTBiomeQohorForest(99, true).setClimateType(GOTClimateType.SUMMER).setColor(0x737F39).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qohorForest");
		qohorHills = new GOTBiomeQohor(170, true).setClimateType(GOTClimateType.SUMMER).setColor(0x969944).setMinMaxHeight(0.1f, 1.0f).setBiomeName("qohorHills");
		reach = new GOTBiomeReach(101, true).setClimateType(GOTClimateType.SUMMER).setColor(0xa7b65a).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reach");
		reachArbor = new GOTBiomeReachArbor(102, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA7AC5A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachArbor");
		reachForest = new GOTBiomeReachForest(181, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6e8241).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachForest");
		reachHills = new GOTBiomeReach(141, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8F9B4D).setMinMaxHeight(0.1f, 1.0f).setBiomeName("reachHills");
		reachTown = new GOTBiomeReachTown(104, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC2D87B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachTown");
		redMesa = new GOTBiomeDorneMesa(106, true).setClimateType(GOTClimateType.SUMMER).setColor(0xDDD5AF).setMinMaxHeight(1.5f, 0.0f).setBiomeName("redMesa");
		redSea = new GOTBiomeRedSea(107, true).setClimateType(GOTClimateType.SUMMER).setColor(0x916c3e).setMinMaxHeight(-1.0f, 0.3f).setBiomeName("redSea");
		riverlands = new GOTBiomeRiverlands(109, true).setClimateType(GOTClimateType.NORMAL).setColor(0x82B756).setMinMaxHeight(0.1f, 0.15f).setBiomeName("riverlands");
		riverlandsForest = new GOTBiomeRiverlandsForest(111, true).setClimateType(GOTClimateType.NORMAL).setColor(0x53823D).setMinMaxHeight(0.1f, 1.0f).setBiomeName("riverlandsForest");
		shrykesLand = new GOTBiomeShrykesLand(90, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAAAE77).setMinMaxHeight(0.0f, 0.1f).setBiomeName("shrykesLand");
		skagos = new GOTBiomeSkagos(116, true).setClimateType(GOTClimateType.COLD).setColor(0x97BF85).setMinMaxHeight(0.1f, 0.15f).setBiomeName("skagos");
		sothoryosBushland = new GOTBiomeSothoryosBushland(118, true).setClimateType(GOTClimateType.SUMMER).setColor(0x998F3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosBushland");
		sothoryosDesert = new GOTBiomeSothoryosDesert(119, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCCB882).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosDesert");
		sothoryosDesertCold = new GOTBiomeSothoryosDesertCold(120, true).setClimateType(GOTClimateType.WINTER).setColor(0xDAD4AF).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosDesertCold");
		sothoryosDesertHills = new GOTBiomeSothoryosDesert(121, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB9A762).setMinMaxHeight(0.1f, 1.0f).setBiomeName("sothoryosDeserHills");
		sothoryosFrost = new GOTBiomeSothoryosFrost(122, true).setClimateType(GOTClimateType.WINTER).setColor(0xD8D8D2).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosFrost");
		sothoryosHell = new GOTBiomeSothoryosHell(123, true).setClimateType(GOTClimateType.SUMMER).setColor(0x2E421B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosHell");
		sothoryosJungle = new GOTBiomeSothoryosJungle(124, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4A6B2B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosJungle");
		sothoryosJungleEdge = new GOTBiomeSothoryosJungleEdge(125, true).setClimateType(GOTClimateType.SUMMER).setColor(0x748F3A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosJungleEdge");
		sothoryosKanuka = new GOTBiomeSothoryosForest(126, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6A8436).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosKanuka");
		sothoryosMangrove = new GOTBiomeSothoryosMangrove(127, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6E8E48).setMinMaxHeight(0.0f, 0.1f).setBiomeName("sothoryosMangrove");
		sothoryosMountains = new GOTBiomeSothoryosMountains(128, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xD8D2B1).setMinMaxHeight(2.0f, 2.0f).setBiomeName("sothoryosMountains");
		sothoryosSavannah = new GOTBiomeSothoryosSavannah(129, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8CA041).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosSavannah");
		sothoryosTaiga = new GOTBiomeSothoryosTaiga(130, true).setClimateType(GOTClimateType.WINTER).setColor(0xA3B481).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosTaiga");
		stepstones = new GOTBiomeStepstones(131, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9BA37A).setMinMaxHeight(0.0f, 0.5f).setBiomeName("stepstones");
		stoneCoast = new GOTBiomeStoneCoast(132, true).setClimateType(GOTClimateType.COLD).setColor(0x89A599).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stoneCoast");
		stormlands = new GOTBiomeStormlands(133, true).setClimateType(GOTClimateType.SUMMER).setColor(0x98AF56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlands");
		stormlandsForest = new GOTBiomeStormlandsForest(158, true).setClimateType(GOTClimateType.SUMMER).setColor(0x617A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsForest");
		stormlandsTown = new GOTBiomeStormlands(135, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB3C677).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsTown");
		summerColony = new GOTBiomeSummerColony(64, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9BAD53).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerColony");
		summerColonyMangrove = new GOTBiomeSummerColonyMangrove(66, true).setClimateType(GOTClimateType.SUMMER).setColor(0x85A361).setMinMaxHeight(0.0f, 0.1f).setBiomeName("summerColonyMangrove");
		summerIslands = new GOTBiomeSummerIslands(136, true).setClimateType(GOTClimateType.SUMMER).setColor(0x83A54A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerIslands");
		summerIslandsTropicalForest = new GOTBiomeTropicalForest(175, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4F7032).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerIslandsTropicalForest");
		tarth = new GOTBiomeTarth(137, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA4AD68).setMinMaxHeight(0.1f, 0.15f).setBiomeName("tarth");
		tarthForest = new GOTBiomeTarthForest(182, true).setClimateType(GOTClimateType.SUMMER).setColor(0x687749).setMinMaxHeight(0.1f, 0.15f).setBiomeName("tarthForest");
		thenn = new GOTBiomeThenn(138, true).setClimateType(GOTClimateType.WINTER).setColor(0xC3DDCF).setMinMaxHeight(0.1f, 0.15f).setBiomeName("thenn");
		tyrosh = new GOTBiomeTyrosh(140, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9AA5A2).setMinMaxHeight(0.1f, 0.15f).setBiomeName("tyrosh");
		ulthos = new GOTBiomeUlthos(13, true).setClimateType(GOTClimateType.SUMMER).setColor(0x648432).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthos");
		ulthosDesert = new GOTBiomeUlthosDesert(143, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCEBA84).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosDesert");
		ulthosDesertCold = new GOTBiomeUlthosDesertCold(32, true).setClimateType(GOTClimateType.WINTER).setColor(0xD8D2AD).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosDesertCold");
		ulthosForest = new GOTBiomeUlthosForest(142, true).setClimateType(GOTClimateType.SUMMER).setColor(0x284F1E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosForest");
		ulthosForestEdge = new GOTBiomeUlthosForestEdge(16, true).setClimateType(GOTClimateType.SUMMER).setColor(0x436A28).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosForestEdge");
		ulthosFrost = new GOTBiomeUlthosFrost(20, true).setClimateType(GOTClimateType.WINTER).setColor(0xCECECA).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosFrost");
		ulthosMarshes = new GOTBiomeUlthosMarshes(68, true).setClimateType(GOTClimateType.SUMMER).setColor(0x588E4D).setMinMaxHeight(0.0f, 0.1f).setBiomeName("ulthosMarshes");
		ulthosMarshesForest = new GOTBiomeUlthosMarshesForest(74, true).setClimateType(GOTClimateType.SUMMER).setColor(0x41753F).setMinMaxHeight(0.0f, 0.1f).setBiomeName("ulthosMarshesForest");
		ulthosMountains = new GOTBiomeUlthosMountains(144, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xCDD6AF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("ulthosMountains");
		ulthosRedForest = new GOTBiomeUlthosRedForest(115, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5E441C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosRedForest");
		ulthosRedForestEdge = new GOTBiomeUlthosRedForestEdge(77, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5E6526).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosRedForestEdge");
		ulthosTaiga = new GOTBiomeUlthosTaiga(26, true).setClimateType(GOTClimateType.WINTER).setColor(0x5C6B3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosTaiga");
		ulthosTaigaEdge = new GOTBiomeUlthosTaigaEdge(29, true).setClimateType(GOTClimateType.WINTER).setColor(0x818E66).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosTaigaEdge");
		volantis = new GOTBiomeVolantis(148, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A24C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantis");
		volantisForest = new GOTBiomeVolantisForest(178, true).setClimateType(GOTClimateType.SUMMER).setColor(0x727135).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantisForest");
		volantisMarshes = new GOTBiomeVolantisMarshes(151, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EA260).setMinMaxHeight(0.0f, 0.1f).setBiomeName("volantisMarshes");
		volantisOrangeForest = new GOTBiomeVolantisOrangeForest(150, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9D8840).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantisOrangeForest");
		westerlands = new GOTBiomeWesterlands(152, true).setClimateType(GOTClimateType.NORMAL).setColor(0xB9BA5E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlands");
		westerlandsForest = new GOTBiomeWesterlandsForest(179, true).setClimateType(GOTClimateType.NORMAL).setColor(0x7A8443).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlandsForest");
		westerlandsHills = new GOTBiomeWesterlandsHills(154, true).setClimateType(GOTClimateType.NORMAL).setColor(0x9E9E50).setMinMaxHeight(0.1f, 1.0f).setBiomeName("westerlandsHills");
		westerlandsTown = new GOTBiomeWesterlandsTown(155, true).setClimateType(GOTClimateType.NORMAL).setColor(0xD1D17F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlandsTown");
		wetwood = new GOTBiomeWetwood(157, true).setClimateType(GOTClimateType.COLD).setColor(0x427551).setMinMaxHeight(0.1f, 0.15f).setBiomeName("wetwood");
		wolfswood = new GOTBiomeNorthForest(159, true).setClimateType(GOTClimateType.COLD).setColor(0x5E8254).setMinMaxHeight(0.1f, 0.15f).setBiomeName("wolfswood");
		yeen = new GOTBiomeYeen(160, true).setClimateType(GOTClimateType.SUMMER).setColor(0x242F0F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yeen");
		yiTi = new GOTBiomeYiTi(161, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAAAE55).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTi");
		yiTiMarshes = new GOTBiomeYiTiMarshes(12, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8BA061).setMinMaxHeight(0.0f, 0.1f).setBiomeName("yiTiMarshes");
		yiTiTropicalForest = new GOTBiomeTropicalForest(139, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6E7A3B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTiTropicalForest");
		yiTiWasteland = new GOTBiomeYiTiWasteland(163, true).setClimateType(GOTClimateType.SUMMER).setColor(0xaaae56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTiWasteland");
		yunkai = new GOTBiomeYunkai(164, true).setClimateType(GOTClimateType.SUMMER).setColor(0xb9ba7a).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yunkai");
	}

	public static void updateWaterColor(int k) {
		int min = 0;
		int max = waterLimitSouth - waterLimitNorth;
		float latitude = (float) MathHelper.clamp_int(k - waterLimitNorth, min, max) / max;
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

	public void addBiomeF3Info(List<String> info, World world, GOTBiomeVariant variant) {
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
			if (j <= random.nextInt(5)) {
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
						--fillerDepth;
						if (fillerDepth == 0) {
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

	public Class<? extends GOTEntityNPC> getBanditEntityClass() {
		if (banditEntityClass == null) {
			return GOTEntityWesterosBandit.class;
		}
		return banditEntityClass;
	}

	@SideOnly(Side.CLIENT)
	public int getBaseFoliageColor(int i, int j, int k) {
		GOTBiomeVariant variant = ((GOTWorldChunkManager) GOT.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
		float temp = getFloatTemperature(i, j, k) + variant.tempBoost;
		float rain = rainfall + variant.rainBoost;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerFoliage.getFoliageColor(temp, rain);
	}

	@SideOnly(Side.CLIENT)
	public int getBaseGrassColor(int i, int j, int k) {
		GOTBiomeVariant variant = ((GOTWorldChunkManager) GOT.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
		float temp = getFloatTemperature(i, j, k) + variant.tempBoost;
		float rain = rainfall + variant.rainBoost;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerGrass.getGrassColor(temp, rain);
	}

	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	public GOTDimension getBiomeDimension() {
		return biomeDimension;
	}

	public String getBiomeDisplayName() {
		return StatCollector.translateToLocal("got.biome." + biomeName + ".name");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBiomeFoliageColor(int i, int j, int k) {
		return biomeColors.foliage != null ? biomeColors.foliage.getRGB() : getBaseFoliageColor(i, j, k);
	}

	@SideOnly(Side.CLIENT)
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

	public GOTBiome setClimateType(GOTClimateType type) {
		climateType = type;
		decorator.generateAgriculture = type == GOTClimateType.SUMMER;
		return this;
	}

	public void getCloudColor(Vec3 clouds) {
		if (biomeColors.clouds != null) {
			float[] colors = biomeColors.clouds.getColorComponents(null);
			clouds.xCoord *= colors[0];
			clouds.yCoord *= colors[1];
			clouds.zCoord *= colors[2];
		}
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

	public void getFogColor(Vec3 fog) {
		if (biomeColors.fog != null) {
			float[] colors = biomeColors.fog.getColorComponents(null);
			fog.xCoord *= colors[0];
			fog.yCoord *= colors[1];
			fog.zCoord *= colors[2];
		}
	}

	public float getHeightBaseParameter() {
		return heightBaseParameter;
	}

	public GOTBiomeInvasionSpawns getInvasionSpawns() {
		return invasionSpawns;
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

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float f) {
		if (biomeColors.sky != null) {
			return biomeColors.sky.getRGB();
		}
		return super.getSkyColorByTemp(f);
	}

	public List<SpawnListEntry> getSpawnableGOTAmbientList() {
		return spawnableGOTAmbientList;
	}

	@Override
	public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
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

	public void setUnreliableChance(GOTEventSpawner.EventChance c) {
		banditChance = c;
	}

	public float getVariantChance() {
		return variantChance;
	}

	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_ICE;
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

	public boolean isRiver() {
		return false;
	}

	public boolean isWateryBiome() {
		return heightBaseParameter < 0.0f;
	}

	public double modifyStoneNoiseForFiller(double stoneNoise) {
		return stoneNoise;
	}

	@Override
	public GOTBiome setBiomeName(String s) {
		return (GOTBiome) super.setBiomeName(s);
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

	public GOTBiome setMinMaxHeight(float f, float f1) {
		heightBaseParameter = f;
		f -= 2.0f;
		rootHeight = f + 0.2f;
		heightVariation = f1 / 2.0f;
		return this;
	}

	@Override
	public GOTBiome setTemperatureRainfall(float f, float f1) {
		super.setTemperatureRainfall(f, f1);
		return this;
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
		if (!(this instanceof GOTBiomeDorne)) {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 5, 1, 1));
		}
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

	public void setupStandardDomesticFauna() {
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

	public void setupStandardForestFauna() {
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

	public void setupStandardPlainsFauna() {
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

	public int spawnCountMultiplier() {
		return 1;
	}

	public interface Desert {
	}

	public interface ImmuneToFrost {
	}

	public interface ImmuneToHeat {
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
		public boolean hasCustomWater;

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

		public void setHeightStretchFactor(double d) {
			heightStretchFactor = d;
		}

		public double getXZScale() {
			return xzScale;
		}

		public void setXZScale(double d) {
			xzScale = d;
		}

		public boolean hasHeightStretchFactor() {
			return heightStretchFactor != -1.0;
		}

		public boolean hasXZScale() {
			return xzScale != -1.0;
		}

		public void resetHeightStretchFactor() {
			heightStretchFactor = -1.0;
		}

		public void resetXZScale() {
			xzScale = -1.0;
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
}