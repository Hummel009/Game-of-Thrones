package got.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.sound.GOTMusicRegion;
import got.common.GOTDimension;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.entity.animal.GOTEntityFish;
import got.common.entity.other.GOTEntityLightSkinBandit;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTAmbientCreature;
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
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeInvasionSpawns;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
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
import java.util.List;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "PublicField", "unused"})
public abstract class GOTBiome extends BiomeGenBase {
	public static final Set<GOTBiome> CONTENT = new HashSet<>();
	public static final int SPAWN = 600;
	public static final int CONQUEST_SPAWN = 100;
	public static final NoiseGeneratorPerlin BIOME_TERRAIN_NOISE = new NoiseGeneratorPerlin(new Random(1955L), 1);
	public static final Class<?>[][] CORRECT_CREATURE_TYPE_PARAMS = {{EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE}};
	public static final EnumCreatureType CREATURE_TYPE_GOT_AMBIENT = EnumHelper.addEnum(CORRECT_CREATURE_TYPE_PARAMS, EnumCreatureType.class, "GOTAmbient", GOTAmbientCreature.class, 45, Material.air, true, false);

	private static final Color WATER_COLOR_POLAR = new Color(602979);
	private static final Color WATER_COLOR_EQUATOR = new Color(4973293);

	public static GOTBiome alwaysWinter;
	public static GOTBiome arryn;
	public static GOTBiome arrynForest;
	public static GOTBiome arrynMountains;
	public static GOTBiome arrynMountainsFoothills;
	public static GOTBiome arrynTown;
	public static GOTBiome beach;
	public static GOTBiome beachGravel;
	public static GOTBiome beachWhite;
	public static GOTBiome bleedingBeach;
	public static GOTBiome bleedingSea;
	public static GOTBiome boneMountains;
	public static GOTBiome braavos;
	public static GOTBiome braavosForest;
	public static GOTBiome braavosHills;
	public static GOTBiome cannibalSands;
	public static GOTBiome cannibalSandsHills;
	public static GOTBiome coldCoast;
	public static GOTBiome crownlands;
	public static GOTBiome crownlandsForest;
	public static GOTBiome crownlandsTown;
	public static GOTBiome disputedLands;
	public static GOTBiome disputedLandsForest;
	public static GOTBiome dorne;
	public static GOTBiome dorneDesert;
	public static GOTBiome dorneForest;
	public static GOTBiome dorneMesa;
	public static GOTBiome dorneMountains;
	public static GOTBiome dothrakiSea;
	public static GOTBiome dothrakiSeaForest;
	public static GOTBiome dothrakiSeaHills;
	public static GOTBiome dragonstone;
	public static GOTBiome essos;
	public static GOTBiome essosForest;
	public static GOTBiome essosMarshes;
	public static GOTBiome essosMountains;
	public static GOTBiome frostfangs;
	public static GOTBiome ghiscar;
	public static GOTBiome ghiscarAstapor;
	public static GOTBiome ghiscarColony;
	public static GOTBiome ghiscarForest;
	public static GOTBiome ghiscarMeereen;
	public static GOTBiome ghiscarNewGhis;
	public static GOTBiome ghiscarYunkai;
	public static GOTBiome giftNew;
	public static GOTBiome giftOld;
	public static GOTBiome hauntedForest;
	public static GOTBiome ibben;
	public static GOTBiome ibbenColony;
	public static GOTBiome ibbenColonyForest;
	public static GOTBiome ibbenColonyHills;
	public static GOTBiome ibbenMountains;
	public static GOTBiome ibbenTaiga;
	public static GOTBiome ifekevronForest;
	public static GOTBiome ironIslands;
	public static GOTBiome ironIslandsForest;
	public static GOTBiome ironIslandsHills;
	public static GOTBiome island;
	public static GOTBiome isleOfFaces;
	public static GOTBiome jogosNhai;
	public static GOTBiome jogosNhaiDesert;
	public static GOTBiome jogosNhaiDesertHills;
	public static GOTBiome jogosNhaiForest;
	public static GOTBiome jogosNhaiHills;
	public static GOTBiome kingSpears;
	public static GOTBiome kingswoodNorth;
	public static GOTBiome kingswoodSouth;
	public static GOTBiome lake;
	public static GOTBiome lhazar;
	public static GOTBiome lhazarForest;
	public static GOTBiome lhazarHills;
	public static GOTBiome longSummer;
	public static GOTBiome lorath;
	public static GOTBiome lorathForest;
	public static GOTBiome lorathHills;
	public static GOTBiome lorathMaze;
	public static GOTBiome lys;
	public static GOTBiome massy;
	public static GOTBiome massyHills;
	public static GOTBiome mossovy;
	public static GOTBiome mossovyMarshes;
	public static GOTBiome mossovyMountains;
	public static GOTBiome mossovyTaiga;
	public static GOTBiome myr;
	public static GOTBiome myrForest;
	public static GOTBiome naath;
	public static GOTBiome neck;
	public static GOTBiome neckForest;
	public static GOTBiome north;
	public static GOTBiome northBarrows;
	public static GOTBiome northForest;
	public static GOTBiome northForestIrontree;
	public static GOTBiome northHills;
	public static GOTBiome northMountains;
	public static GOTBiome northTown;
	public static GOTBiome northWild;
	public static GOTBiome norvos;
	public static GOTBiome norvosForest;
	public static GOTBiome norvosHills;
	public static GOTBiome ocean1;
	public static GOTBiome ocean2;
	public static GOTBiome ocean3;
	public static GOTBiome ocean;
	public static GOTBiome pentos;
	public static GOTBiome pentosForest;
	public static GOTBiome pentosHills;
	public static GOTBiome qarth;
	public static GOTBiome qarthColony;
	public static GOTBiome qarthDesert;
	public static GOTBiome qohor;
	public static GOTBiome qohorForest;
	public static GOTBiome qohorHills;
	public static GOTBiome reach;
	public static GOTBiome reachArbor;
	public static GOTBiome reachFireField;
	public static GOTBiome reachForest;
	public static GOTBiome reachHills;
	public static GOTBiome reachTown;
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
	public static GOTBiome sothoryosForest;
	public static GOTBiome sothoryosFrost;
	public static GOTBiome sothoryosHell;
	public static GOTBiome sothoryosJungle;
	public static GOTBiome sothoryosJungleEdge;
	public static GOTBiome sothoryosMangrove;
	public static GOTBiome sothoryosMountains;
	public static GOTBiome sothoryosSavannah;
	public static GOTBiome sothoryosTaiga;
	public static GOTBiome sothoryosTaigaEdge;
	public static GOTBiome stepstones;
	public static GOTBiome stoneCoast;
	public static GOTBiome stormlands;
	public static GOTBiome stormlandsForest;
	public static GOTBiome stormlandsTarth;
	public static GOTBiome stormlandsTarthForest;
	public static GOTBiome stormlandsTown;
	public static GOTBiome summerColony;
	public static GOTBiome summerIslands;
	public static GOTBiome summerIslandsTropicalForest;
	public static GOTBiome thennLand;
	public static GOTBiome tyrosh;
	public static GOTBiome ulthosBushland;
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
	public static GOTBiome valyriaSea1;
	public static GOTBiome valyriaSea2;
	public static GOTBiome valyriaVolcano;
	public static GOTBiome volantis;
	public static GOTBiome volantisForest;
	public static GOTBiome volantisMarshes;
	public static GOTBiome volantisOrangeForest;
	public static GOTBiome westerlands;
	public static GOTBiome westerlandsForest;
	public static GOTBiome westerlandsHills;
	public static GOTBiome westerlandsTown;
	public static GOTBiome westerosFrost;
	public static GOTBiome whisperingWood;
	public static GOTBiome wolfswood;
	public static GOTBiome yeen;
	public static GOTBiome yiTi;
	public static GOTBiome yiTiMarshes;
	public static GOTBiome yiTiTropicalForest;
	public static GOTBiome yiTiBorderZone;

	protected final List<SpawnListEntry> spawnableGOTAmbientList = new ArrayList<>();
	protected final GOTBiomeVariantList biomeVariants = new GOTBiomeVariantList();
	protected final GOTBiomeSpawnList npcSpawnList = new GOTBiomeSpawnList(this);
	protected final BiomeColors biomeColors = new BiomeColors(this);

	private final GOTDimension biomeDimension;

	protected GOTBiomePreseter preseter;
	protected GOTBiomeDecorator decorator;
	protected GOTBiomeGenerator generator;
	protected GOTBiomeInvasionSpawns invasionSpawns;
	protected float variantChance = 0.4f;
	protected int fillerBlockMeta;
	protected int topBlockMeta;

	protected Class<? extends GOTEntityNPC> banditEntityClass = GOTEntityLightSkinBandit.class;
	protected GOTAchievement biomeAchievement;
	protected GOTBezierType roadBlock = GOTBezierType.PATH_DIRTY;
	protected GOTBezierType wallBlock = GOTBezierType.PATH_COBBLE;
	protected GOTEventSpawner.EventChance banditChance = GOTEventSpawner.EventChance.COMMON;
	protected GOTMusicRegion.Sub biomeMusic = GOTMusicRegion.OCEAN.getSubregion(biomeName);
	protected GOTWaypoint.Region biomeWaypoints;
	protected boolean enableRiver = true;
	protected boolean enableRocky;
	protected float chanceToSpawnAnimals = 0.25f;
	protected int spawnCountMultiplier = 3;
	protected int wallTop = 90;

	private GOTClimateType climateType;
	private float heightBaseParameter;

	protected GOTBiome(int i, boolean major) {
		this(i, major, GOTDimension.GAME_OF_THRONES);
	}

	private GOTBiome(int i, boolean major, GOTDimension dim) {
		super(i, false);
		biomeDimension = dim;
		if (biomeDimension.getBiomeList()[i] != null) {
			throw new IllegalArgumentException("GOT biome already exists at index " + i + " for dimension " + biomeDimension.getDimensionName() + '!');
		}
		biomeDimension.getBiomeList()[i] = this;
		if (major) {
			biomeDimension.getMajorBiomes().add(this);
		}
		waterColorMultiplier = BiomeColors.DEFAULT_WATER;
		decorator = new GOTBiomeDecorator(this);
		preseter = new GOTBiomePreseter(this);
		generator = new GOTBiomeGenerator(this);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableCaveCreatureList.clear();
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityFish.class, 10, 4, 4));
		spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
		invasionSpawns = new GOTBiomeInvasionSpawns(this);
		CONTENT.add(this);
	}

	public static void preInit() {
		ocean = new GOTBiomeOcean(0, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x024B75).setMinMaxHeight(-1.0f, 0.3f).setBiomeName("ocean");
		ocean1 = new GOTBiomeOcean(1, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x025582).setMinMaxHeight(-0.7f, 0.3f).setBiomeName("ocean");
		ocean2 = new GOTBiomeOcean(2, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x025E8C).setMinMaxHeight(-0.8f, 0.3f).setBiomeName("ocean");
		ocean3 = new GOTBiomeOcean(3, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0x036799).setMinMaxHeight(-0.9f, 0.3f).setBiomeName("ocean");

		kingSpears = new GOTBiomeOcean(4, true).setTemperatureRainfall(0.8F, 0.8F).setColor(0xA0A0A0).setMinMaxHeight(-0.7f, 0.3f).setBiomeName("kingSpears");

		island = new GOTBiomeOcean(5, false).setTemperatureRainfall(0.8F, 0.8F).setColor(10138963).setMinMaxHeight(0.0f, 0.3f).setBiomeName("island");

		beach = new GOTBiomeBeach(6, false, Blocks.sand, 0).setMinMaxHeight(0.1f, 0.0f).setColor(14404247).setTemperatureRainfall(0.8f, 0.4f).setBiomeName("beach");
		beachGravel = new GOTBiomeBeach(7, false, Blocks.gravel, 0).setMinMaxHeight(0.1f, 0.0f).setColor(9868704).setTemperatureRainfall(0.8f, 0.4f).setBiomeName("beachGravel");
		beachWhite = new GOTBiomeBeach(8, false, GOTBlocks.whiteSand, 0).setMinMaxHeight(0.1f, 0.0f).setColor(15592941).setTemperatureRainfall(0.8f, 0.4f).setBiomeName("beachWhite");
		lake = new GOTBiomeLake(9, false).setMinMaxHeight(-0.5f, 0.2f).setColor(3433630).setTemperatureRainfall(0.8f, 0.4f).setBiomeName("lake");
		river = new GOTBiomeRiver(10, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setTemperatureRainfall(0.8f, 0.4f).setBiomeName("river");

		shadowLand = new GOTBiomeShadowLand(11, true).setMinMaxHeight(0.1f, 0.15f).setColor(0x8E8854).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowLand");
		shadowMountains = new GOTBiomeShadowMountains(12, true).setMinMaxHeight(2.0f, 2.0f).setColor(0x635E3B).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowMountains");
		shadowTown = new GOTBiomeShadowTown(13, true).setMinMaxHeight(0.1f, 0.15f).setColor(0xA39C68).setTemperatureRainfall(1.0f, 0.2f).setBiomeName("shadowTown");
		valyria = new GOTBiomeValyria(14, true).setMinMaxHeight(0.1f, 0.15f).setColor(6710111).setTemperatureRainfall(1.2F, 0.8F).setBiomeName("valyria");
		valyriaSea = new GOTBiomeValyria(15, true).setMinMaxHeight(-1.0f, 0.3f).setColor(0x0D314C).setTemperatureRainfall(0.8F, 0.8F).setBiomeName("valyriaSea");
		valyriaSea1 = new GOTBiomeValyria(16, true).setMinMaxHeight(-0.9f, 0.3f).setColor(0x103B5B).setTemperatureRainfall(0.8F, 0.8F).setBiomeName("valyriaSea");
		valyriaSea2 = new GOTBiomeValyria(17, true).setMinMaxHeight(-0.8f, 0.3f).setColor(0x124468).setTemperatureRainfall(0.8F, 0.8F).setBiomeName("valyriaSea");
		valyriaVolcano = new GOTBiomeValyriaVolcano(18, true).setMinMaxHeight(2.0f, 2.0f).setColor(0xA5A5A5).setTemperatureRainfall(1.2F, 0.8F).setBiomeName("valyriaVolcano");

		bleedingBeach = new GOTBiomeBleedingBeach(19, false).setBeachBlock(Blocks.sand, 1).setClimateType(GOTClimateType.SUMMER).setColor(14403247).setMinMaxHeight(0.1f, 0.0f).setBiomeName("bleedingBeach");

		alwaysWinter = new GOTBiomeAlwaysWinter(20, true).setClimateType(GOTClimateType.WINTER).setColor(0xC9DAE0).setMinMaxHeight(0.1f, 0.15f).setBiomeName("alwaysWinter");
		arryn = new GOTBiomeArryn(21, true).setClimateType(GOTClimateType.NORMAL).setColor(0x8CBA5B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arryn");
		arrynForest = new GOTBiomeArrynForest(22, true).setClimateType(GOTClimateType.NORMAL).setColor(0x598440).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynForest");
		arrynMountains = new GOTBiomeArrynMountains(23, true).setClimateType(GOTClimateType.NORMAL_AZ).setColor(0xC1E0BA).setMinMaxHeight(2.0f, 2.0f).setBiomeName("arrynMountains");
		arrynMountainsFoothills = new GOTBiomeArrynMountainsFoothills(24, true).setClimateType(GOTClimateType.NORMAL).setColor(0x598454).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynMountainsFoothills");
		arrynTown = new GOTBiomeArrynTown(25, true).setClimateType(GOTClimateType.NORMAL).setColor(0x89BC5C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("arrynTown");
		bleedingSea = new GOTBiomeBleedingSea(26, true).setClimateType(GOTClimateType.SUMMER).setColor(0x916c3e).setMinMaxHeight(-1.0f, 0.3f).setBiomeName("bleedingSea");
		boneMountains = new GOTBiomeBoneMountains(27, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xE5E2B3).setMinMaxHeight(2.0f, 2.0f).setBiomeName("boneMountains");
		braavos = new GOTBiomeBraavos(28, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAA9747).setMinMaxHeight(0.1f, 0.15f).setBiomeName("braavos");
		braavosForest = new GOTBiomeBraavosForest(29, true).setClimateType(GOTClimateType.SUMMER).setColor(0x686530).setMinMaxHeight(0.1f, 0.15f).setBiomeName("braavosForest");
		braavosHills = new GOTBiomeBraavos(30, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8E7E3B).setMinMaxHeight(0.1f, 1.0f).setBiomeName("braavosHills");
		cannibalSands = new GOTBiomeCannibalSands(31, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCCBC82).setMinMaxHeight(0.1f, 0.15f).setBiomeName("cannibalSands");
		cannibalSandsHills = new GOTBiomeCannibalSands(32, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A472).setMinMaxHeight(0.1f, 1.0f).setBiomeName("cannibalSandsHills");
		coldCoast = new GOTBiomeColdCoast(33, true).setClimateType(GOTClimateType.WINTER).setColor(0xC0D6CC).setMinMaxHeight(0.1f, 0.15f).setBiomeName("coldCoast");
		crownlands = new GOTBiomeCrownlands(34, true).setClimateType(GOTClimateType.NORMAL).setColor(0x81af57).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlands");
		crownlandsForest = new GOTBiomeCrownlandsForest(35, true).setClimateType(GOTClimateType.NORMAL).setColor(0x507A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlandsForest");
		crownlandsTown = new GOTBiomeCrownlandsTown(36, true).setClimateType(GOTClimateType.NORMAL).setColor(0x9DC677).setMinMaxHeight(0.1f, 0.15f).setBiomeName("crownlandsTown");
		disputedLands = new GOTBiomeDisputedLands(37, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAFB25B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("disputedLands");
		disputedLandsForest = new GOTBiomeDisputedLandsForest(38, true).setClimateType(GOTClimateType.SUMMER).setColor(0x707C40).setMinMaxHeight(0.1f, 0.15f).setBiomeName("disputedLandsForest");
		dorne = new GOTBiomeDorne(39, true).setClimateType(GOTClimateType.SUMMER).setColor(0xaaa753).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorne");
		dorneDesert = new GOTBiomeDorneDesert(40, true).setClimateType(GOTClimateType.SUMMER).setColor(14074229).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorneDesert");
		dorneForest = new GOTBiomeDorneForest(41, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6B7239).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dorneForest");
		dorneMesa = new GOTBiomeDorneMesa(42, true).setClimateType(GOTClimateType.SUMMER).setColor(0xDDD5AF).setMinMaxHeight(1.5f, 0.05f).setBiomeName("dorneMesa");
		dorneMountains = new GOTBiomeDorneMountains(43, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xD7E0AF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("dorneMountains");
		dothrakiSea = new GOTBiomeDothrakiSea(44, true).setClimateType(GOTClimateType.SUMMER).setColor(10398278).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dothrakiSea");
		dothrakiSeaForest = new GOTBiomeDothrakiSeaForest(45, true).setClimateType(GOTClimateType.SUMMER).setColor(0x637531).setMinMaxHeight(0.1f, 0.15f).setBiomeName("dothrakiSeaForest");
		dothrakiSeaHills = new GOTBiomeDothrakiSea(46, true).setClimateType(GOTClimateType.SUMMER).setColor(0x858E3B).setMinMaxHeight(0.1f, 1.0f).setBiomeName("dothrakiSeaHills");
		dragonstone = new GOTBiomeDragonstone(47, true).setClimateType(GOTClimateType.NORMAL).setColor(0x96AF85).setMinMaxHeight(0.3f, 0.35f).setBiomeName("dragonstone");
		essos = new GOTBiomeEssosUnhabited(48, true).setClimateType(GOTClimateType.SUMMER).setColor(0x92A54A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("essos");
		essosForest = new GOTBiomeEssosForest(49, true).setClimateType(GOTClimateType.SUMMER).setColor(0x617027).setMinMaxHeight(0.1f, 0.15f).setBiomeName("essosForest");
		essosMarshes = new GOTBiomeEssosMarshes(50, true).setClimateType(GOTClimateType.SUMMER).setColor(0x739655).setMinMaxHeight(0.0f, 0.1f).setBiomeName("essosMarshes");
		essosMountains = new GOTBiomeEssosMountains(51, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xDDDDAF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("essosMountains");
		frostfangs = new GOTBiomeFrostfangs(52, true).setClimateType(GOTClimateType.WINTER).setColor(0xB8C5C6).setMinMaxHeight(2.0f, 2.0f).setBiomeName("frostfangs");
		ghiscar = new GOTBiomeGhiscar(53, true).setClimateType(GOTClimateType.SUMMER).setColor(0xADAF6B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscar");
		ghiscarAstapor = new GOTBiomeGhiscarAstapor(54, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB1BA7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarAstapor");
		ghiscarColony = new GOTBiomeGhiscarColony(55, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5B8C2A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarColony");
		ghiscarForest = new GOTBiomeGhiscarForest(56, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6F7A4B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarForest");
		ghiscarMeereen = new GOTBiomeGhiscarMeereen(57, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC1BA7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarMeereen");
		ghiscarNewGhis = new GOTBiomeGhiscarNewGhis(58, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB1AF7A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarNewGhis");
		ghiscarYunkai = new GOTBiomeGhiscarYunkai(59, true).setClimateType(GOTClimateType.SUMMER).setColor(0xb9ba7a).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ghiscarYunkai");
		giftOld = new GOTBiomeGiftOld(60, true).setClimateType(GOTClimateType.WINTER).setColor(0xB3D3A9).setMinMaxHeight(0.1f, 0.15f).setBiomeName("giftOld");
		giftNew = new GOTBiomeGiftNew(61, true).setClimateType(GOTClimateType.COLD).setColor(0x9CC78A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("giftNew");
		hauntedForest = new GOTBiomeHauntedForest(62, true).setClimateType(GOTClimateType.WINTER).setColor(0x88AD8D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("hauntedForest");
		ibben = new GOTBiomeIbben(63, true).setClimateType(GOTClimateType.COLD).setColor(0x7AA04B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibben");
		ibbenColony = new GOTBiomeIbbenColony(64, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EAF56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibbenColony");
		ibbenColonyForest = new GOTBiomeIbbenColonyForest(65, true).setClimateType(GOTClimateType.SUMMER).setColor(0x657A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibbenColonyForest");
		ibbenColonyHills = new GOTBiomeIbbenColony(66, true).setClimateType(GOTClimateType.SUMMER).setColor(0x869349).setMinMaxHeight(0.1f, 1.0f).setBiomeName("ibbenColonyHills");
		ibbenMountains = new GOTBiomeIbbenMountains(67, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xafbc9f).setMinMaxHeight(2.0f, 2.0f).setBiomeName("ibbenMountains");
		ibbenTaiga = new GOTBiomeIbbenTaiga(68, true).setClimateType(GOTClimateType.COLD).setColor(0x4C6B23).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ibbenTaiga");
		ifekevronForest = new GOTBiomeIfekevronForest(69, true).setClimateType(GOTClimateType.SUMMER).setColor(0x657231).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ifekevronForest");
		ironIslands = new GOTBiomeIronIslands(70, true).setClimateType(GOTClimateType.COLD).setColor(0x8BBA83).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ironIslands");
		ironIslandsForest = new GOTBiomeIronIslandsForest(71, true).setClimateType(GOTClimateType.COLD).setColor(0x59845E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ironIslandsForest");
		ironIslandsHills = new GOTBiomeIronIslands(72, true).setClimateType(GOTClimateType.COLD).setColor(0x779E70).setMinMaxHeight(0.1f, 1.0f).setBiomeName("ironIslandsHills");
		isleOfFaces = new GOTBiomeIsleOfFaces(73, true).setClimateType(GOTClimateType.NORMAL).setColor(0x82A556).setMinMaxHeight(0.1f, 0.15f).setBiomeName("isleOfFaces");
		jogosNhai = new GOTBiomeJogosNhai(74, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2B762).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhai");
		jogosNhaiDesert = new GOTBiomeJogosNhaiDesert(75, true).setClimateType(GOTClimateType.SUMMER).setColor(0xD6C182).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhaiDesert");
		jogosNhaiDesertHills = new GOTBiomeJogosNhaiDesert(76, true).setClimateType(GOTClimateType.SUMMER).setColor(0xBAA671).setMinMaxHeight(0.1f, 1.0f).setBiomeName("jogosNhaiDesertHills");
		jogosNhaiForest = new GOTBiomeJogosNhaiForest(77, true).setClimateType(GOTClimateType.SUMMER).setColor(0x727F44).setMinMaxHeight(0.1f, 0.15f).setBiomeName("jogosNhaiForest");
		jogosNhaiHills = new GOTBiomeJogosNhai(78, true).setClimateType(GOTClimateType.SUMMER).setColor(0x979B53).setMinMaxHeight(0.1f, 1.0f).setBiomeName("jogosNhaiHills");
		kingswoodNorth = new GOTBiomeKingswoodNorth(79, true).setClimateType(GOTClimateType.NORMAL).setColor(0x587A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("kingswood");
		kingswoodSouth = new GOTBiomeKingswoodSouth(80, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5F7A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("kingswood");
		lhazar = new GOTBiomeLhazar(81, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2B758).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lhazar");
		lhazarForest = new GOTBiomeLhazarForest(82, true).setClimateType(GOTClimateType.SUMMER).setColor(0x74823F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lhazarForest");
		lhazarHills = new GOTBiomeLhazar(83, true).setClimateType(GOTClimateType.SUMMER).setColor(0x979B4C).setMinMaxHeight(0.1f, 1.0f).setBiomeName("lhazarHills");
		longSummer = new GOTBiomeLongSummer(84, true).setClimateType(GOTClimateType.SUMMER).setColor(0x95A03D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("longSummer");
		longSummer = new GOTBiomeLongSummerForest(85, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5A6B29).setMinMaxHeight(0.1f, 0.15f).setBiomeName("longSummerForest");
		lorath = new GOTBiomeLorath(86, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA5B25B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorath");
		lorathForest = new GOTBiomeLorathForest(87, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6A7C40).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorathForest");
		lorathHills = new GOTBiomeLorath(88, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8C964E).setMinMaxHeight(0.1f, 1.0f).setBiomeName("lorathHills");
		lorathMaze = new GOTBiomeLorathMaze(89, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA8AF7B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lorathMaze");
		lys = new GOTBiomeLys(90, true).setClimateType(GOTClimateType.SUMMER).setColor(0x70A564).setMinMaxHeight(0.1f, 0.15f).setBiomeName("lys");
		massy = new GOTBiomeDragonstone(91, true).setClimateType(GOTClimateType.NORMAL).setColor(0x89AD68).setMinMaxHeight(0.1f, 0.15f).setBiomeName("massy");
		massyHills = new GOTBiomeDragonstone(92, true).setClimateType(GOTClimateType.NORMAL).setColor(0x749158).setMinMaxHeight(0.1f, 1.0f).setBiomeName("massyHills");
		mossovy = new GOTBiomeMossovy(93, true).setClimateType(GOTClimateType.COLD).setColor(0x92A35E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("mossovy");
		mossovyMarshes = new GOTBiomeMossovyMarshes(94, true).setClimateType(GOTClimateType.COLD).setColor(0x749975).setMinMaxHeight(0.0f, 0.1f).setBiomeName("mossovyMarshes");
		mossovyMountains = new GOTBiomeMossovyMountains(95, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xD7E2B3).setMinMaxHeight(2.0f, 2.0f).setBiomeName("mossovyMountains");
		mossovyTaiga = new GOTBiomeMossovyTaiga(96, true).setClimateType(GOTClimateType.COLD).setColor(0x647A46).setMinMaxHeight(0.1f, 0.15f).setBiomeName("mossovyTaiga");
		myr = new GOTBiomeMyr(97, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA0A349).setMinMaxHeight(0.1f, 0.15f).setBiomeName("myr");
		myrForest = new GOTBiomeMyrForest(98, true).setClimateType(GOTClimateType.SUMMER).setColor(0x626D31).setMinMaxHeight(0.1f, 0.15f).setBiomeName("myrForest");
		naath = new GOTBiomeSummerIslands(99, true).setClimateType(GOTClimateType.SUMMER).setColor(0x7DA33C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("naath");
		neck = new GOTBiomeNeck(100, true).setClimateType(GOTClimateType.COLD).setColor(0x6DAA75).setMinMaxHeight(0.0f, 0.1f).setBiomeName("neck");
		neckForest = new GOTBiomeNeckForest(101, true).setClimateType(GOTClimateType.COLD).setColor(0x427551).setMinMaxHeight(0.1f, 0.15f).setBiomeName("neckForest");
		north = new GOTBiomeNorth(102, true).setClimateType(GOTClimateType.COLD).setColor(0x8BBA6D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("north");
		northBarrows = new GOTBiomeNorthBarrows(103, true).setClimateType(GOTClimateType.COLD).setColor(0x97C17C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northBarrows");
		northForest = new GOTBiomeNorthForest(104, true).setClimateType(GOTClimateType.COLD).setColor(0x59844E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northForest");
		northForestIrontree = new GOTBiomeNorthForestIrontree(105, true).setClimateType(GOTClimateType.COLD).setColor(0x868254).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northForestIrontree");
		northHills = new GOTBiomeNorth(106, true).setClimateType(GOTClimateType.COLD).setColor(0x779E5D).setMinMaxHeight(0.1f, 1.0f).setBiomeName("northHills");
		northMountains = new GOTBiomeNorthMountains(107, true).setClimateType(GOTClimateType.COLD_AZ).setColor(0xD3E0BA).setMinMaxHeight(2.0f, 2.0f).setBiomeName("northMountains");
		northTown = new GOTBiomeNorthTown(108, true).setClimateType(GOTClimateType.COLD).setColor(0xAAD190).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northTown");
		northWild = new GOTBiomeNorthWild(109, true).setClimateType(GOTClimateType.COLD).setColor(0xA9C69D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("northWild");
		norvos = new GOTBiomeNorvos(110, true).setClimateType(GOTClimateType.SUMMER).setColor(0x7DA344).setMinMaxHeight(0.1f, 0.15f).setBiomeName("norvos");
		norvosForest = new GOTBiomeNorvosForest(111, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4A6D2E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("norvosForest");
		norvosHills = new GOTBiomeNorvos(112, true).setClimateType(GOTClimateType.SUMMER).setColor(0x678738).setMinMaxHeight(0.1f, 1.0f).setBiomeName("norvosHills");
		pentos = new GOTBiomePentos(113, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A349).setMinMaxHeight(0.1f, 0.15f).setBiomeName("pentos");
		pentosForest = new GOTBiomePentosForest(114, true).setClimateType(GOTClimateType.SUMMER).setColor(0x706F34).setMinMaxHeight(0.1f, 0.15f).setBiomeName("pentosForest");
		pentosHills = new GOTBiomePentos(115, true).setClimateType(GOTClimateType.SUMMER).setColor(0x96893F).setMinMaxHeight(0.1f, 1.0f).setBiomeName("pentosHills");
		qarth = new GOTBiomeQarth(116, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EAA4E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarth");
		qarthColony = new GOTBiomeQarthColony(117, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9DAF54).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarthColony");
		qarthDesert = new GOTBiomeQarthDesert(118, true).setClimateType(GOTClimateType.SUMMER).setColor(0xDCC175).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qarthDesert");
		qohor = new GOTBiomeQohor(119, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAFB350).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qohor");
		qohorForest = new GOTBiomeQohorForest(120, true).setClimateType(GOTClimateType.SUMMER).setColor(0x737F39).setMinMaxHeight(0.1f, 0.15f).setBiomeName("qohorForest");
		qohorHills = new GOTBiomeQohor(121, true).setClimateType(GOTClimateType.SUMMER).setColor(0x969944).setMinMaxHeight(0.1f, 1.0f).setBiomeName("qohorHills");
		reach = new GOTBiomeReach(122, true).setClimateType(GOTClimateType.SUMMER).setColor(0xa7b65a).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reach");
		reachArbor = new GOTBiomeReachArbor(123, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA7AC5A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachArbor");
		reachFireField = new GOTBiomeReachFireField(124, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC2C47B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachFireField");
		reachForest = new GOTBiomeReachForest(125, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6e8241).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachForest");
		reachHills = new GOTBiomeReach(126, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8F9B4D).setMinMaxHeight(0.1f, 1.0f).setBiomeName("reachHills");
		reachTown = new GOTBiomeReachTown(127, true).setClimateType(GOTClimateType.SUMMER).setColor(0xC2D87B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("reachTown");
		riverlands = new GOTBiomeRiverlands(128, true).setClimateType(GOTClimateType.NORMAL).setColor(0x87BC58).setMinMaxHeight(0.1f, 0.15f).setBiomeName("riverlands");
		riverlandsForest = new GOTBiomeRiverlandsForest(129, true).setClimateType(GOTClimateType.NORMAL).setColor(0x58873F).setMinMaxHeight(0.1f, 1.0f).setBiomeName("riverlandsForest");
		shrykesLand = new GOTBiomeShrykesLand(130, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAAAE77).setMinMaxHeight(0.0f, 0.1f).setBiomeName("shrykesLand");
		skagos = new GOTBiomeSkagos(131, true).setClimateType(GOTClimateType.COLD).setColor(0x97BF85).setMinMaxHeight(0.1f, 0.15f).setBiomeName("skagos");
		sothoryosBushland = new GOTBiomeSothoryosBushland(132, true).setClimateType(GOTClimateType.SUMMER).setColor(0x998F3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosBushland");
		sothoryosDesert = new GOTBiomeSothoryosDesert(133, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCCB882).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosDesert");
		sothoryosDesertCold = new GOTBiomeSothoryosDesertCold(134, true).setClimateType(GOTClimateType.WINTER).setColor(0xDAD4AF).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosDesertCold");
		sothoryosDesertHills = new GOTBiomeSothoryosDesert(135, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB9A762).setMinMaxHeight(0.1f, 1.0f).setBiomeName("sothoryosDesertHills");
		sothoryosForest = new GOTBiomeSothoryosForest(136, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6A8436).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosForest");
		sothoryosFrost = new GOTBiomeSothoryosFrost(137, true).setClimateType(GOTClimateType.WINTER).setColor(0xD8D8D2).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosFrost");
		sothoryosHell = new GOTBiomeSothoryosHell(138, true).setClimateType(GOTClimateType.SUMMER).setColor(0x2E421B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosHell");
		sothoryosJungle = new GOTBiomeSothoryosJungle(139, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4A6B2B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosJungle");
		sothoryosJungleEdge = new GOTBiomeSothoryosJungleEdge(140, true).setClimateType(GOTClimateType.SUMMER).setColor(0x748F3A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosJungleEdge");
		sothoryosMangrove = new GOTBiomeSothoryosMangrove(141, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6E8E48).setMinMaxHeight(0.0f, 0.1f).setBiomeName("sothoryosMangrove");
		sothoryosMountains = new GOTBiomeSothoryosMountains(142, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xD8D2B1).setMinMaxHeight(2.0f, 2.0f).setBiomeName("sothoryosMountains");
		sothoryosSavannah = new GOTBiomeSothoryosSavannah(143, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8CA041).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosSavannah");
		sothoryosTaiga = new GOTBiomeSothoryosTaiga(144, true).setClimateType(GOTClimateType.WINTER).setColor(0x737F5B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosTaiga");
		sothoryosTaigaEdge = new GOTBiomeSothoryosTaigaEdge(145, true).setClimateType(GOTClimateType.WINTER).setColor(0xA3B481).setMinMaxHeight(0.1f, 0.15f).setBiomeName("sothoryosTaigaEdge");
		stepstones = new GOTBiomeStepstones(146, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9BA37A).setMinMaxHeight(0.0f, 0.5f).setBiomeName("stepstones");
		stoneCoast = new GOTBiomeStoneCoast(147, true).setClimateType(GOTClimateType.COLD).setColor(0x89A599).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stoneCoast");
		stormlands = new GOTBiomeStormlands(148, true).setClimateType(GOTClimateType.SUMMER).setColor(0x96AF56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlands");
		stormlandsForest = new GOTBiomeStormlandsForest(149, true).setClimateType(GOTClimateType.SUMMER).setColor(0x617A3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsForest");
		stormlandsTarth = new GOTBiomeStormlandsTarth(150, true).setClimateType(GOTClimateType.SUMMER).setColor(0xA4AD68).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsTarth");
		stormlandsTarthForest = new GOTBiomeStormlandsForest(151, true).setClimateType(GOTClimateType.SUMMER).setColor(0x687749).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsTarthForest");
		stormlandsTown = new GOTBiomeStormlands(152, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB3C677).setMinMaxHeight(0.1f, 0.15f).setBiomeName("stormlandsTown");
		summerColony = new GOTBiomeSummerColony(153, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9BAD53).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerColony");
		summerIslands = new GOTBiomeSummerIslands(154, true).setClimateType(GOTClimateType.SUMMER).setColor(0x83A54A).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerIslands");
		summerIslandsTropicalForest = new GOTBiomeSummerIslandsTropicalForest(155, true).setClimateType(GOTClimateType.SUMMER).setColor(0x4F7032).setMinMaxHeight(0.1f, 0.15f).setBiomeName("summerIslandsTropicalForest");
		thennLand = new GOTBiomeThennLand(156, true).setClimateType(GOTClimateType.WINTER).setColor(0xC3DDCF).setMinMaxHeight(0.1f, 0.15f).setBiomeName("thennLand");
		tyrosh = new GOTBiomeTyrosh(157, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9AA5A2).setMinMaxHeight(0.1f, 0.15f).setBiomeName("tyrosh");
		ulthosBushland = new GOTBiomeUlthosBushland(158, true).setClimateType(GOTClimateType.SUMMER).setColor(0x648432).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosBushland");
		ulthosDesert = new GOTBiomeUlthosDesert(159, true).setClimateType(GOTClimateType.SUMMER).setColor(0xCEBA84).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosDesert");
		ulthosDesertCold = new GOTBiomeUlthosDesertCold(160, true).setClimateType(GOTClimateType.WINTER).setColor(0xD8D2AD).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosDesertCold");
		ulthosForest = new GOTBiomeUlthosForest(161, true).setClimateType(GOTClimateType.SUMMER).setColor(0x284F1E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosForest");
		ulthosForestEdge = new GOTBiomeUlthosForestEdge(162, true).setClimateType(GOTClimateType.SUMMER).setColor(0x436A28).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosForestEdge");
		ulthosFrost = new GOTBiomeUlthosFrost(163, true).setClimateType(GOTClimateType.WINTER).setColor(0xCECECA).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosFrost");
		ulthosMarshes = new GOTBiomeUlthosMarshes(164, true).setClimateType(GOTClimateType.SUMMER).setColor(0x588E4D).setMinMaxHeight(0.0f, 0.1f).setBiomeName("ulthosMarshes");
		ulthosMarshesForest = new GOTBiomeUlthosMarshesForest(165, true).setClimateType(GOTClimateType.SUMMER).setColor(0x41753F).setMinMaxHeight(0.0f, 0.1f).setBiomeName("ulthosMarshesForest");
		ulthosMountains = new GOTBiomeUlthosMountains(166, true).setClimateType(GOTClimateType.SUMMER_AZ).setColor(0xCDD6AF).setMinMaxHeight(2.0f, 2.0f).setBiomeName("ulthosMountains");
		ulthosRedForest = new GOTBiomeUlthosRedForest(167, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5E441C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosRedForest");
		ulthosRedForestEdge = new GOTBiomeUlthosRedForestEdge(168, true).setClimateType(GOTClimateType.SUMMER).setColor(0x5E6526).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosRedForestEdge");
		ulthosTaiga = new GOTBiomeUlthosTaiga(169, true).setClimateType(GOTClimateType.WINTER).setColor(0x5C6B3D).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosTaiga");
		ulthosTaigaEdge = new GOTBiomeUlthosTaigaEdge(170, true).setClimateType(GOTClimateType.WINTER).setColor(0x818E66).setMinMaxHeight(0.1f, 0.15f).setBiomeName("ulthosTaigaEdge");
		volantis = new GOTBiomeVolantis(171, true).setClimateType(GOTClimateType.SUMMER).setColor(0xB2A24C).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantis");
		volantisForest = new GOTBiomeVolantisForest(172, true).setClimateType(GOTClimateType.SUMMER).setColor(0x727135).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantisForest");
		volantisMarshes = new GOTBiomeVolantisMarshes(173, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9EA260).setMinMaxHeight(0.0f, 0.1f).setBiomeName("volantisMarshes");
		volantisOrangeForest = new GOTBiomeVolantisOrangeForest(174, true).setClimateType(GOTClimateType.SUMMER).setColor(0x9D8840).setMinMaxHeight(0.1f, 0.15f).setBiomeName("volantisOrangeForest");
		westerlands = new GOTBiomeWesterlands(175, true).setClimateType(GOTClimateType.NORMAL).setColor(0xB9BA5E).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlands");
		westerlandsForest = new GOTBiomeWesterlandsForest(176, true).setClimateType(GOTClimateType.NORMAL).setColor(0x7A8443).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlandsForest");
		westerlandsHills = new GOTBiomeWesterlands(177, true).setClimateType(GOTClimateType.NORMAL).setColor(0x9E9E50).setMinMaxHeight(0.1f, 1.0f).setBiomeName("westerlandsHills");
		westerlandsTown = new GOTBiomeWesterlandsTown(178, true).setClimateType(GOTClimateType.NORMAL).setColor(0xD1D17F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerlandsTown");
		westerosFrost = new GOTBiomeWesterosFrost(179, true).setClimateType(GOTClimateType.WINTER).setColor(0xD7E4E5).setMinMaxHeight(0.1f, 0.15f).setBiomeName("westerosFrost");
		yeen = new GOTBiomeYeen(180, true).setClimateType(GOTClimateType.SUMMER).setColor(0x242F0F).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yeen");
		yiTi = new GOTBiomeYiTi(181, true).setClimateType(GOTClimateType.SUMMER).setColor(0xAAAE55).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTi");
		yiTiBorderZone = new GOTBiomeYiTiBorderZone(182, true).setClimateType(GOTClimateType.SUMMER).setColor(0xaaae56).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTiBorderZone");
		yiTiMarshes = new GOTBiomeYiTiMarshes(183, true).setClimateType(GOTClimateType.SUMMER).setColor(0x8BA061).setMinMaxHeight(0.0f, 0.1f).setBiomeName("yiTiMarshes");
		yiTiTropicalForest = new GOTBiomeYiTiTropicalForest(184, true).setClimateType(GOTClimateType.SUMMER).setColor(0x6E7A3B).setMinMaxHeight(0.1f, 0.15f).setBiomeName("yiTiTropicalForest");
	}

	public static void updateWaterColor(int k) {
		float waterLimitNorth = -25000;
		float waterLimitCenter = 240000;
		float waterLimitSouth = 453000;

		try {
			for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.getBiomeList()) {
				if (biome != null && !biome.biomeColors.hasCustomWater()) {
					if (k <= waterLimitNorth) {
						biome.biomeColors.updateWater(WATER_COLOR_POLAR);
					} else if (k <= waterLimitCenter) {
						float proportion = (k - waterLimitNorth) / (waterLimitCenter - waterLimitNorth);
						Color color = mixColors(WATER_COLOR_POLAR, WATER_COLOR_EQUATOR, proportion);
						biome.biomeColors.updateWater(color);
					} else if (k <= waterLimitSouth) {
						float proportion = (k - waterLimitCenter) / (waterLimitSouth - waterLimitCenter);
						Color color = mixColors(WATER_COLOR_EQUATOR, WATER_COLOR_POLAR, proportion);
						biome.biomeColors.updateWater(color);
					} else {
						biome.biomeColors.updateWater(WATER_COLOR_POLAR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Color mixColors(Color color1, Color color2, float proportion) {
		int red = (int) (color1.getRed() * (1.0f - proportion) + color2.getRed() * proportion);
		int green = (int) (color1.getGreen() * (1.0f - proportion) + color2.getGreen() * proportion);
		int blue = (int) (color1.getBlue() * (1.0f - proportion) + color2.getBlue() * proportion);
		return new Color(red, green, blue);
	}

	public static void postInit() {
		Color baseWater = new Color(4876527);
		int baseR = baseWater.getRed();
		int baseG = baseWater.getGreen();
		int baseB = baseWater.getBlue();
		for (BiomeGenBase biome : getBiomeGenArray()) {
			if (biome == null) {
				continue;
			}
			Color water = new Color(biome.waterColorMultiplier);
			float[] rgb = water.getColorComponents(null);
			int r = (int) (baseR * rgb[0]);
			int g = (int) (baseG * rgb[1]);
			int b = (int) (baseB * rgb[2]);
			biome.waterColorMultiplier = new Color(r, g, b).getRGB();
		}
	}

	public List<SpawnListEntry> getSpawnableCreatureList() {
		return spawnableCreatureList;
	}

	public void addSpawnableCreature(Class<? extends Entity> clazz, int weight, int min, int max) {
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(clazz, weight, min, max));
	}

	public void addSpawnableAmbient(Class<? extends Entity> clazz, int weight, int min, int max) {
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(clazz, weight, min, max));
	}

	public List<SpawnListEntry> getSpawnableWaterCreatureList() {
		return spawnableWaterCreatureList;
	}

	public List<SpawnListEntry> getSpawnableCaveCreatureList() {
		return spawnableCaveCreatureList;
	}

	public List<FlowerEntry> getFlowers() {
		return flowers;
	}

	public BiomeColors getBiomeColors() {
		return biomeColors;
	}

	public void addBiomeF3Info(Collection<String> info, World world, GOTBiomeVariant variant) {
		info.add("Game of Thrones biome: " + getBiomeDisplayName() + ", ID: " + biomeID + ';');
		info.add("Variant: " + StatCollector.translateToLocal(variant.getUnlocalizedName()) + ", loaded: " + GOTBiomeVariantStorage.getSize(world));
	}

	@Override
	public boolean canSpawnLightningBolt() {
		return !getEnableSnow() && super.canSpawnLightningBolt();
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

	@SideOnly(Side.CLIENT)
	private int getBaseFoliageColor(int i, int j, int k) {
		float temp = getFloatTemperature(i, j, k);
		float rain = rainfall;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerFoliage.getFoliageColor(temp, rain);
	}

	@SideOnly(Side.CLIENT)
	private int getBaseGrassColor(int i, int j, int k) {
		float temp = getFloatTemperature(i, j, k);
		float rain = rainfall;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerGrass.getGrassColor(temp, rain);
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
		return biomeColors.getFoliage() != null ? biomeColors.getFoliage().getRGB() : getBaseFoliageColor(i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBiomeGrassColor(int i, int j, int k) {
		return biomeColors.getGrass() != null ? biomeColors.getGrass().getRGB() : getBaseGrassColor(i, j, k);
	}

	public boolean isEnableRocky() {
		return enableRocky;
	}

	public GOTBiomeVariantList getBiomeVariants() {
		return biomeVariants;
	}

	public GOTClimateType getClimateType() {
		return climateType;
	}

	protected GOTBiome setClimateType(GOTClimateType type) {
		climateType = type;
		decorator.setGenerateAgriculture(type == GOTClimateType.SUMMER);
		return this;
	}

	public void getCloudColor(Vec3 clouds) {
		if (biomeColors.getClouds() != null) {
			float[] colors = biomeColors.getClouds().getColorComponents(null);
			clouds.xCoord *= colors[0];
			clouds.yCoord *= colors[1];
			clouds.zCoord *= colors[2];
		}
	}

	public int getFillerBlockMeta() {
		return fillerBlockMeta;
	}

	public void setFillerBlockMeta(int fillerBlockMeta) {
		this.fillerBlockMeta = fillerBlockMeta;
	}

	public void getFogColor(Vec3 fog) {
		if (biomeColors.getFog() != null) {
			float[] colors = biomeColors.getFog().getColorComponents(null);
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

	public void setInvasionSpawns(GOTBiomeInvasionSpawns invasionSpawns) {
		this.invasionSpawns = invasionSpawns;
	}

	public BiomeGenBase.FlowerEntry getRandomFlower(Random random) {
		return (BiomeGenBase.FlowerEntry) WeightedRandom.getRandomItem(random, flowers);
	}

	public GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(5) == 0) {
			return new GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		if (random.nextInt(30) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.plantain, 2);
		}
		if (random.nextInt(200) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.tallGrass, 3);
		}
		if (random.nextInt(16) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.tallGrass, 1);
		}
		if (random.nextInt(10) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.tallGrass, 2);
		}
		if (random.nextInt(80) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.tallGrass, 4);
		}
		if (random.nextInt(2) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.tallGrass, 0);
		}
		if (random.nextInt(3) == 0) {
			return new GrassBlockAndMeta(GOTBlocks.clover, 0);
		}
		return new GrassBlockAndMeta(Blocks.tallgrass, 1);
	}

	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				doubleFlowerGen.func_150548_a(1);
				return doubleFlowerGen;
			case 1:
				doubleFlowerGen.func_150548_a(4);
				return doubleFlowerGen;
			case 2:
				doubleFlowerGen.func_150548_a(5);
				return doubleFlowerGen;
			default:
				return null;
		}
	}

	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}

	public static WorldGenerator getRandomWorldGenForDoubleGrass() {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		generator.func_150548_a(2);
		return generator;
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		GrassBlockAndMeta obj = getRandomGrass(random);
		return new WorldGenTallGrass(obj.getBlock(), obj.getMeta());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float f) {
		if (biomeColors.getSky() != null) {
			return biomeColors.getSky().getRGB();
		}
		return super.getSkyColorByTemp(f);
	}

	public List<SpawnListEntry> getSpawnableGOTAmbientList() {
		return spawnableGOTAmbientList;
	}

	@Override
	public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
		if (creatureType == CREATURE_TYPE_GOT_AMBIENT) {
			return spawnableGOTAmbientList;
		}
		return super.getSpawnableList(creatureType);
	}

	public int getTopBlockMeta() {
		return topBlockMeta;
	}

	public void setTopBlockMeta(int topBlockMeta) {
		this.topBlockMeta = topBlockMeta;
	}

	public WorldGenAbstractTree getTreeGen(World world, Random random, int i, int k) {
		GOTWorldChunkManager chunkManager = (GOTWorldChunkManager) world.getWorldChunkManager();
		GOTBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
		GOTTreeType tree = decorator.getRandomTreeForVariant(random, variant);
		return tree.create(false, random);
	}

	public float getVariantChance() {
		return variantChance;
	}

	public void setVariantChance(float variantChance) {
		this.variantChance = variantChance;
	}

	@Override
	public GOTBiome setBiomeName(String s) {
		return (GOTBiome) super.setBiomeName(s);
	}

	@Override
	public GOTBiome setColor(int color) {
		int color1 = color;
		color1 |= 0xFF000000;
		Integer existingBiomeID = biomeDimension.getColorsToBiomeIDs().get(color1);
		if (existingBiomeID != null) {
			throw new RuntimeException("GOT biome (ID " + biomeID + ") is duplicating the color of another GOT biome (ID " + existingBiomeID + ')');
		}
		biomeDimension.getColorsToBiomeIDs().put(color1, biomeID);
		return (GOTBiome) super.setColor(color1);
	}

	public GOTBiome setMinMaxHeight(float f, float f1) {
		float f2 = f;
		heightBaseParameter = f2;
		f2 -= 2.0f;
		rootHeight = f2 + 0.2f;
		heightVariation = f1 / 2.0f;
		return this;
	}

	@Override
	public GOTBiome setTemperatureRainfall(float f, float f1) {
		super.setTemperatureRainfall(f, f1);
		return this;
	}

	public GOTBiomeSpawnList getNPCSpawnList() {
		return npcSpawnList;
	}

	public GOTBiomeDecorator getDecorator() {
		return decorator;
	}

	public GOTMusicRegion.Sub getBiomeMusic() {
		return biomeMusic;
	}

	public GOTWaypoint.Region getBiomeWaypoints() {
		return biomeWaypoints;
	}

	public float getChanceToSpawnAnimals() {
		return chanceToSpawnAnimals;
	}

	public boolean getEnableRiver() {
		return enableRiver;
	}

	public GOTBezierType getRoadBlock() {
		return roadBlock;
	}

	public int getSpawnCountMultiplier() {
		return spawnCountMultiplier;
	}

	public GOTAchievement getBiomeAchievement() {
		return biomeAchievement;
	}

	public GOTBezierType getWallBlock() {
		return wallBlock;
	}

	public int getWallTop() {
		return wallTop;
	}

	public Class<? extends GOTEntityNPC> getBanditEntityClass() {
		return banditEntityClass;
	}

	public GOTEventSpawner.EventChance getBanditChance() {
		return banditChance;
	}

	public interface Mountains {
		void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant);
	}

	public interface Desert {
	}

	public interface Marshes {
	}

	public interface ImmuneToFrost {
	}

	public interface ImmuneToHeat {
	}

	public static class BiomeColors {
		private static final int DEFAULT_WATER = 7186907;

		private final GOTBiome biome;

		private Color grass;
		private Color foliage;
		private Color sky;
		private Color clouds;
		private Color fog;

		private boolean foggy;
		private boolean customWater;

		private BiomeColors(GOTBiome biome) {
			this.biome = biome;
		}

		public boolean hasCustomWater() {
			return customWater;
		}

		public void setWater(Color rgb) {
			biome.waterColorMultiplier = rgb.getRGB();
			if (rgb.getRGB() != DEFAULT_WATER) {
				customWater = true;
			}
		}

		public void updateWater(Color rgb) {
			biome.waterColorMultiplier = rgb.getRGB();
		}

		public Color getGrass() {
			return grass;
		}

		public void setGrass(Color grass) {
			this.grass = grass;
		}

		public Color getFoliage() {
			return foliage;
		}

		public void setFoliage(Color foliage) {
			this.foliage = foliage;
		}

		public Color getSky() {
			return sky;
		}

		public void setSky(Color sky) {
			this.sky = sky;
		}

		public Color getClouds() {
			return clouds;
		}

		public void setClouds(Color clouds) {
			this.clouds = clouds;
		}

		public Color getFog() {
			return fog;
		}

		public void setFog(Color fog) {
			this.fog = fog;
		}

		public boolean isFoggy() {
			return foggy;
		}

		public void setFoggy(boolean flag) {
			foggy = flag;
		}
	}

	public static class GrassBlockAndMeta {
		private final Block block;
		private final int meta;

		public GrassBlockAndMeta(Block b, int i) {
			block = b;
			meta = i;
		}

		public Block getBlock() {
			return block;
		}

		public int getMeta() {
			return meta;
		}
	}
}