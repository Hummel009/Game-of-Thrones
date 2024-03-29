package got.common.database;

import got.common.entity.animal.*;
import got.common.entity.essos.GOTEntityIfekevron;
import got.common.entity.essos.GOTEntityShryke;
import got.common.entity.essos.GOTEntityStoneMan;
import got.common.entity.essos.asshai.GOTEntityAsshaiShadowbinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiSpherebinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiWarrior;
import got.common.entity.essos.braavos.GOTEntityBraavosLevyman;
import got.common.entity.essos.braavos.GOTEntityBraavosLevymanArcher;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldier;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldierArcher;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.gold.GOTEntityGoldenSpearman;
import got.common.entity.essos.gold.GOTEntityGoldenWarrior;
import got.common.entity.essos.ibben.GOTEntityIbbenArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenMan;
import got.common.entity.essos.ibben.GOTEntityIbbenWarrior;
import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.jogos.GOTEntityJogosArcher;
import got.common.entity.essos.lorath.GOTEntityLorathLevyman;
import got.common.entity.essos.lorath.GOTEntityLorathLevymanArcher;
import got.common.entity.essos.lorath.GOTEntityLorathSoldier;
import got.common.entity.essos.lorath.GOTEntityLorathSoldierArcher;
import got.common.entity.essos.lys.GOTEntityLysLevyman;
import got.common.entity.essos.lys.GOTEntityLysLevymanArcher;
import got.common.entity.essos.lys.GOTEntityLysSoldier;
import got.common.entity.essos.lys.GOTEntityLysSoldierArcher;
import got.common.entity.essos.mossovy.GOTEntityMossovyWerewolf;
import got.common.entity.essos.mossovy.GOTEntityMossovyWitcher;
import got.common.entity.essos.myr.GOTEntityMyrLevyman;
import got.common.entity.essos.myr.GOTEntityMyrLevymanArcher;
import got.common.entity.essos.myr.GOTEntityMyrSoldier;
import got.common.entity.essos.myr.GOTEntityMyrSoldierArcher;
import got.common.entity.essos.norvos.GOTEntityNorvosLevyman;
import got.common.entity.essos.norvos.GOTEntityNorvosLevymanArcher;
import got.common.entity.essos.pentos.GOTEntityPentosLevyman;
import got.common.entity.essos.pentos.GOTEntityPentosLevymanArcher;
import got.common.entity.essos.qarth.GOTEntityQarthLevyman;
import got.common.entity.essos.qarth.GOTEntityQarthLevymanArcher;
import got.common.entity.essos.qohor.GOTEntityQohorLevyman;
import got.common.entity.essos.qohor.GOTEntityQohorLevymanArcher;
import got.common.entity.essos.qohor.GOTEntityQohorUnsullied;
import got.common.entity.essos.tyrosh.GOTEntityTyroshLevyman;
import got.common.entity.essos.tyrosh.GOTEntityTyroshLevymanArcher;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldier;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldierArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisLevyman;
import got.common.entity.essos.volantis.GOTEntityVolantisLevymanArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldier;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldierArcher;
import got.common.entity.essos.yiti.*;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRegistry;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosBlowgunner;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosWarrior;
import got.common.entity.sothoryos.summer.GOTEntitySummerArcher;
import got.common.entity.sothoryos.summer.GOTEntitySummerWarrior;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsGuard;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsLevyman;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsLevymanArcher;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsMan;
import got.common.entity.westeros.dorne.GOTEntityDorneLevyman;
import got.common.entity.westeros.dorne.GOTEntityDorneLevymanArcher;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldier;
import got.common.entity.westeros.dorne.GOTEntityDorneSoldierArcher;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneLevyman;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneLevymanArcher;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldier;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneSoldierArcher;
import got.common.entity.westeros.gift.GOTEntityGiftGuard;
import got.common.entity.westeros.hillmen.*;
import got.common.entity.westeros.ice.GOTEntityIceSpider;
import got.common.entity.westeros.ice.GOTEntityWhiteWalker;
import got.common.entity.westeros.ironborn.GOTEntityIronbornLevyman;
import got.common.entity.westeros.ironborn.GOTEntityIronbornLevymanArcher;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldierArcher;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsLevyman;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsLevymanArcher;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldier;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsSoldierArcher;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsLevyman;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsLevymanArcher;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldier;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsSoldierArcher;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.entity.westeros.wildling.GOTEntityWildling;
import got.common.entity.westeros.wildling.GOTEntityWildlingArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThenn;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBerserker;
import got.common.faction.GOTFaction;
import got.common.world.spawning.GOTSpawnEntry;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GOTSpawnList {
	public static GOTSpawnList ARRYN_CIVILIAN;
	public static GOTSpawnList ARRYN_CONQUEST;
	public static GOTSpawnList ARRYN_GUARDIAN;
	public static GOTSpawnList ARRYN_MILITARY;
	public static GOTSpawnList ASSHAI_MILITARY;
	public static GOTSpawnList BRAAVOS_CONQUEST;
	public static GOTSpawnList BRAAVOS_MILITARY;
	public static GOTSpawnList CROCODILE;
	public static GOTSpawnList CROWNLANDS_CIVILIAN;
	public static GOTSpawnList CROWNLANDS_GUARDIAN;
	public static GOTSpawnList CROWNLANDS_MILITARY;
	public static GOTSpawnList DESERT_SCORPION;
	public static GOTSpawnList DORNE_CONQUEST;
	public static GOTSpawnList DORNE_MILITARY;
	public static GOTSpawnList DRAGONSTONE_CONQUEST;
	public static GOTSpawnList DRAGONSTONE_MILITARY;
	public static GOTSpawnList GHISCAR_CONQUEST;
	public static GOTSpawnList GHISCAR_GUARDIAN;
	public static GOTSpawnList GHISCAR_HARPY;
	public static GOTSpawnList GHISCAR_MILITARY;
	public static GOTSpawnList GHISCAR_UNSULLIED;
	public static GOTSpawnList GIFT_GUARDIAN;
	public static GOTSpawnList GOLDEN_MILITARY;
	public static GOTSpawnList HILL_TRIBES_CIVILIAN;
	public static GOTSpawnList HILL_TRIBES_MILITARY;
	public static GOTSpawnList IBBEN_CIVILIAN;
	public static GOTSpawnList IBBEN_MILITARY;
	public static GOTSpawnList IFEKEVRON;
	public static GOTSpawnList IRONBORN_CONQUEST;
	public static GOTSpawnList IRONBORN_MILITARY;
	public static GOTSpawnList JOGOS_MILITARY;
	public static GOTSpawnList JUNGLE_SCORPION;
	public static GOTSpawnList LORATH_CONQUEST;
	public static GOTSpawnList LORATH_MILITARY;
	public static GOTSpawnList LYS_CONQUEST;
	public static GOTSpawnList LYS_MILITARY;
	public static GOTSpawnList MANTICORE;
	public static GOTSpawnList MOSSOVY_MILITARY;
	public static GOTSpawnList MOSSOVY_WEREWOLF;
	public static GOTSpawnList MYR_CONQUEST;
	public static GOTSpawnList MYR_MILITARY;
	public static GOTSpawnList NORTH_CIVILIAN;
	public static GOTSpawnList NORTH_CONQUEST;
	public static GOTSpawnList NORTH_GUARDIAN;
	public static GOTSpawnList NORTH_HILLMEN;
	public static GOTSpawnList NORTH_MILITARY;
	public static GOTSpawnList NORVOS_MILITARY;
	public static GOTSpawnList PENTOS_MILITARY;
	public static GOTSpawnList QARTH_MILITARY;
	public static GOTSpawnList QOHOR_MILITARY;
	public static GOTSpawnList REACH_CIVILIAN;
	public static GOTSpawnList REACH_CONQUEST;
	public static GOTSpawnList REACH_GUARDIAN;
	public static GOTSpawnList REACH_MILITARY;
	public static GOTSpawnList RED_SCORPION;
	public static GOTSpawnList RIVERLANDS_CONQUEST;
	public static GOTSpawnList RIVERLANDS_MILITARY;
	public static GOTSpawnList SHRYKE;
	public static GOTSpawnList SOTHORYOS_MILITARY;
	public static GOTSpawnList STORMLANDS_CONQUEST;
	public static GOTSpawnList STORMLANDS_MILITARY;
	public static GOTSpawnList SUMMER_MILITARY;
	public static GOTSpawnList TYROSH_CONQUEST;
	public static GOTSpawnList TYROSH_MILITARY;
	public static GOTSpawnList ULTHOS;
	public static GOTSpawnList VALYRIA;
	public static GOTSpawnList VOLANTIS_CONQUEST;
	public static GOTSpawnList VOLANTIS_MILITARY;
	public static GOTSpawnList WALKERS_CONQUEST;
	public static GOTSpawnList WALKERS_MILITARY;
	public static GOTSpawnList WESTERLANDS_CIVILIAN;
	public static GOTSpawnList WESTERLANDS_CONQUEST;
	public static GOTSpawnList WESTERLANDS_GUARDIAN;
	public static GOTSpawnList WESTERLANDS_MILITARY;
	public static GOTSpawnList WILDING_GIANT;
	public static GOTSpawnList WILDING_MILITARY;
	public static GOTSpawnList WILDING_THENN;
	public static GOTSpawnList WYVERN;
	public static GOTSpawnList YITI_CONQUEST;
	public static GOTSpawnList YITI_MILITARY;

	public static List<GOTSpawnEntry> ARRYN_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> ARRYN_CONQUEST_L = new ArrayList<>();
	public static Collection<GOTSpawnEntry> ARRYN_CONQUEST_l = new ArrayList<>();
	public static List<GOTSpawnEntry> ARRYN_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> ARRYN_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> ASSHAI_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> BRAAVOS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> BRAAVOS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> CROCODILE_L = new ArrayList<>();
	public static List<GOTSpawnEntry> CROWNLANDS_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> CROWNLANDS_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> CROWNLANDS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> DESERT_SCORPION_L = new ArrayList<>();
	public static List<GOTSpawnEntry> DORNE_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> DORNE_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> DRAGONSTONE_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> DRAGONSTONE_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GHISCAR_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GHISCAR_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GHISCAR_HARPY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GHISCAR_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GHISCAR_UNSULLIED_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GIFT_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> GOLDEN_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> HILL_TRIBES_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> HILL_TRIBES_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> IBBEN_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> IBBEN_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> IFEKEVRON_L = new ArrayList<>();
	public static List<GOTSpawnEntry> IRONBORN_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> IRONBORN_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> JOGOS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> JUNGLE_SCORPION_L = new ArrayList<>();
	public static List<GOTSpawnEntry> LORATH_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> LORATH_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> LYS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> LYS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> MANTICORE_L = new ArrayList<>();
	public static List<GOTSpawnEntry> MOSSOVY_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> MOSSOVY_WEREWOLF_L = new ArrayList<>();
	public static List<GOTSpawnEntry> MYR_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> MYR_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORTH_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORTH_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORTH_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORTH_HILLMEN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORTH_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> NORVOS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> PENTOS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> QARTH_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> QOHOR_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> REACH_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> REACH_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> REACH_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> REACH_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> RED_SCORPION_L = new ArrayList<>();
	public static List<GOTSpawnEntry> RIVERLANDS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> RIVERLANDS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> SHRYKE_L = new ArrayList<>();
	public static List<GOTSpawnEntry> SOTHORYOS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> STORMLANDS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> STORMLANDS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> SUMMER_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> TYROSH_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> TYROSH_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> ULTHOS_L = new ArrayList<>();
	public static List<GOTSpawnEntry> VALYRIA_L = new ArrayList<>();
	public static List<GOTSpawnEntry> VOLANTIS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> VOLANTIS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WALKERS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WALKERS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WESTERLANDS_CIVILIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WESTERLANDS_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WESTERLANDS_GUARDIAN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WESTERLANDS_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WILDING_GIANT_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WILDING_MILITARY_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WILDING_THENN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> WYVERN_L = new ArrayList<>();
	public static List<GOTSpawnEntry> YITI_CONQUEST_L = new ArrayList<>();
	public static List<GOTSpawnEntry> YITI_MILITARY_L = new ArrayList<>();

	static {
		ARRYN_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityArrynMan.class, 10, 1, 2));
		ARRYN_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityArrynSoldier.class, 10, 1, 2));
		ARRYN_CONQUEST_l.add(new GOTSpawnEntry(GOTEntityArrynSoldierArcher.class, 5, 1, 2));
		ARRYN_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityArrynGuard.class, 10, 1, 2));
		ARRYN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityArrynLevyman.class, 10, 1, 2));
		ARRYN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityArrynLevymanArcher.class, 5, 1, 2));
		ASSHAI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityAsshaiShadowbinder.class, 2, 1, 1));
		ASSHAI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityAsshaiSpherebinder.class, 5, 1, 1));
		ASSHAI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityAsshaiWarrior.class, 10, 1, 2));
		BRAAVOS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityBraavosSoldier.class, 10, 1, 2));
		BRAAVOS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityBraavosSoldierArcher.class, 5, 1, 2));
		BRAAVOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityBraavosLevyman.class, 10, 1, 2));
		BRAAVOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityBraavosLevymanArcher.class, 5, 1, 2));
		CROCODILE_L.add(new GOTSpawnEntry(GOTEntityCrocodile.class, 10, 1, 1));
		CROWNLANDS_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityCrownlandsMan.class, 10, 1, 2));
		CROWNLANDS_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityCrownlandsGuard.class, 10, 1, 2));
		CROWNLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityCrownlandsLevyman.class, 10, 1, 2));
		CROWNLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityCrownlandsLevymanArcher.class, 5, 1, 2));
		DESERT_SCORPION_L.add(new GOTSpawnEntry(GOTEntityDesertScorpion.class, 10, 1, 1));
		DORNE_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityDorneSoldier.class, 10, 1, 2));
		DORNE_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityDorneSoldierArcher.class, 5, 1, 2));
		DORNE_MILITARY_L.add(new GOTSpawnEntry(GOTEntityDorneLevyman.class, 10, 1, 2));
		DORNE_MILITARY_L.add(new GOTSpawnEntry(GOTEntityDorneLevymanArcher.class, 5, 1, 2));
		DRAGONSTONE_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityDragonstoneSoldier.class, 10, 1, 2));
		DRAGONSTONE_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityDragonstoneSoldierArcher.class, 5, 1, 2));
		DRAGONSTONE_MILITARY_L.add(new GOTSpawnEntry(GOTEntityDragonstoneLevyman.class, 10, 1, 2));
		DRAGONSTONE_MILITARY_L.add(new GOTSpawnEntry(GOTEntityDragonstoneLevymanArcher.class, 5, 1, 2));
		GHISCAR_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityGhiscarCorsair.class, 10, 1, 2));
		GHISCAR_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityGhiscarCorsairArcher.class, 5, 1, 2));
		GHISCAR_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityGhiscarGuard.class, 10, 1, 2));
		GHISCAR_HARPY_L.add(new GOTSpawnEntry(GOTEntityGhiscarHarpy.class, 10, 1, 2));
		GHISCAR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityGhiscarLevyman.class, 10, 1, 2));
		GHISCAR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityGhiscarLevymanArcher.class, 5, 1, 2));
		GHISCAR_UNSULLIED_L.add(new GOTSpawnEntry(GOTEntityGhiscarUnsullied.class, 10, 1, 2));
		GIFT_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityGiftGuard.class, 10, 1, 2));
		GOLDEN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityGoldenSpearman.class, 5, 1, 2));
		GOLDEN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityGoldenWarrior.class, 10, 1, 2));
		HILL_TRIBES_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityHillman.class, 10, 1, 2));
		HILL_TRIBES_MILITARY_L.add(new GOTSpawnEntry(GOTEntityHillmanArcher.class, 5, 1, 2));
		HILL_TRIBES_MILITARY_L.add(new GOTSpawnEntry(GOTEntityHillmanAxeThrower.class, 3, 1, 2));
		HILL_TRIBES_MILITARY_L.add(new GOTSpawnEntry(GOTEntityHillmanBerserker.class, 3, 1, 2));
		HILL_TRIBES_MILITARY_L.add(new GOTSpawnEntry(GOTEntityHillmanWarrior.class, 10, 1, 2));
		IBBEN_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityIbbenMan.class, 10, 1, 2));
		IBBEN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityIbbenArcher.class, 5, 1, 2));
		IBBEN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityIbbenWarrior.class, 10, 1, 2));
		IFEKEVRON_L.add(new GOTSpawnEntry(GOTEntityIfekevron.class, 10, 1, 2));
		IRONBORN_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityIronbornSoldier.class, 10, 1, 2));
		IRONBORN_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityIronbornSoldierArcher.class, 5, 1, 2));
		IRONBORN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityIronbornLevyman.class, 10, 1, 2));
		IRONBORN_MILITARY_L.add(new GOTSpawnEntry(GOTEntityIronbornLevymanArcher.class, 5, 1, 2));
		JOGOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityJogos.class, 10, 1, 2));
		JOGOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityJogosArcher.class, 5, 1, 2));
		JUNGLE_SCORPION_L.add(new GOTSpawnEntry(GOTEntityJungleScorpion.class, 10, 1, 1));
		LORATH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityLorathSoldier.class, 10, 1, 2));
		LORATH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityLorathSoldierArcher.class, 5, 1, 2));
		LORATH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityLorathLevyman.class, 10, 1, 2));
		LORATH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityLorathLevymanArcher.class, 5, 1, 2));
		LYS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityLysSoldier.class, 10, 1, 2));
		LYS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityLysSoldierArcher.class, 5, 1, 2));
		LYS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityLysLevyman.class, 10, 1, 2));
		LYS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityLysLevymanArcher.class, 5, 1, 2));
		MANTICORE_L.add(new GOTSpawnEntry(GOTEntityManticore.class, 10, 1, 1));
		MOSSOVY_MILITARY_L.add(new GOTSpawnEntry(GOTEntityMossovyWitcher.class, 10, 1, 1));
		MOSSOVY_WEREWOLF_L.add(new GOTSpawnEntry(GOTEntityMossovyWerewolf.class, 10, 1, 2));
		MYR_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityMyrSoldier.class, 10, 1, 2));
		MYR_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityMyrSoldierArcher.class, 5, 1, 2));
		MYR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityMyrLevyman.class, 10, 1, 2));
		MYR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityMyrLevymanArcher.class, 5, 1, 2));
		NORTH_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityNorthMan.class, 10, 1, 2));
		NORTH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityNorthSoldier.class, 10, 1, 2));
		NORTH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityNorthSoldierArcher.class, 5, 1, 2));
		NORTH_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityNorthGuard.class, 10, 1, 2));
		NORTH_HILLMEN_L.add(new GOTSpawnEntry(GOTEntityNorthHillman.class, 10, 1, 2));
		NORTH_HILLMEN_L.add(new GOTSpawnEntry(GOTEntityNorthHillmanArcher.class, 5, 1, 2));
		NORTH_HILLMEN_L.add(new GOTSpawnEntry(GOTEntityNorthHillmanCannibal.class, 5, 1, 2));
		NORTH_HILLMEN_L.add(new GOTSpawnEntry(GOTEntityNorthHillmanMercenary.class, 5, 1, 2));
		NORTH_HILLMEN_L.add(new GOTSpawnEntry(GOTEntityNorthHillmanWarrior.class, 5, 1, 2));
		NORTH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityNorthLevyman.class, 10, 1, 2));
		NORTH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityNorthLevymanArcher.class, 5, 1, 2));
		NORVOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityNorvosLevyman.class, 10, 1, 2));
		NORVOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityNorvosLevymanArcher.class, 5, 1, 2));
		PENTOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityPentosLevyman.class, 10, 1, 2));
		PENTOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityPentosLevymanArcher.class, 5, 1, 2));
		QARTH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityQarthLevyman.class, 10, 1, 2));
		QARTH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityQarthLevymanArcher.class, 5, 1, 2));
		QOHOR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityQohorLevyman.class, 5, 1, 2));
		QOHOR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityQohorLevymanArcher.class, 2, 1, 2));
		QOHOR_MILITARY_L.add(new GOTSpawnEntry(GOTEntityQohorUnsullied.class, 10, 1, 2));
		REACH_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityReachMan.class, 10, 1, 2));
		REACH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityReachSoldier.class, 10, 1, 2));
		REACH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityReachSoldierArcher.class, 5, 1, 2));
		REACH_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityReachGuard.class, 10, 1, 2));
		REACH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityReachLevyman.class, 10, 1, 2));
		REACH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityReachLevymanArcher.class, 5, 1, 2));
		RED_SCORPION_L.add(new GOTSpawnEntry(GOTEntityRedScorpion.class, 10, 1, 1));
		RIVERLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityRiverlandsSoldier.class, 10, 1, 2));
		RIVERLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityRiverlandsSoldierArcher.class, 5, 1, 2));
		RIVERLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityRiverlandsLevyman.class, 10, 1, 2));
		RIVERLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityRiverlandsLevymanArcher.class, 5, 1, 2));
		SHRYKE_L.add(new GOTSpawnEntry(GOTEntityShryke.class, 10, 1, 2));
		SOTHORYOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntitySothoryosBlowgunner.class, 5, 1, 2));
		SOTHORYOS_MILITARY_L.add(new GOTSpawnEntry(GOTEntitySothoryosWarrior.class, 10, 1, 2));
		STORMLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityStormlandsSoldier.class, 10, 1, 2));
		STORMLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityStormlandsSoldierArcher.class, 5, 1, 2));
		STORMLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityStormlandsLevyman.class, 10, 1, 2));
		STORMLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityStormlandsLevymanArcher.class, 5, 1, 2));
		SUMMER_MILITARY_L.add(new GOTSpawnEntry(GOTEntitySummerArcher.class, 5, 1, 2));
		SUMMER_MILITARY_L.add(new GOTSpawnEntry(GOTEntitySummerWarrior.class, 10, 1, 2));
		TYROSH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityTyroshSoldier.class, 10, 1, 2));
		TYROSH_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityTyroshSoldierArcher.class, 5, 1, 2));
		TYROSH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityTyroshLevyman.class, 10, 1, 2));
		TYROSH_MILITARY_L.add(new GOTSpawnEntry(GOTEntityTyroshLevymanArcher.class, 5, 1, 2));
		ULTHOS_L.add(new GOTSpawnEntry(GOTEntityUlthosSpider.class, 10, 1, 2));
		VALYRIA_L.add(new GOTSpawnEntry(GOTEntityStoneMan.class, 10, 1, 2));
		VOLANTIS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityVolantisSoldier.class, 10, 1, 2));
		VOLANTIS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityVolantisSoldierArcher.class, 5, 1, 2));
		VOLANTIS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityVolantisLevyman.class, 10, 1, 2));
		VOLANTIS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityVolantisLevymanArcher.class, 5, 1, 2));
		WALKERS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityIceSpider.class, 5, 1, 2));
		WALKERS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityWhiteWalker.class, 10, 1, 2));
		WALKERS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityWhiteWalker.class, 10, 1, 2));
		WESTERLANDS_CIVILIAN_L.add(new GOTSpawnEntry(GOTEntityWesterlandsMan.class, 10, 1, 2));
		WESTERLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityWesterlandsSoldier.class, 10, 1, 2));
		WESTERLANDS_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityWesterlandsSoldierArcher.class, 5, 1, 2));
		WESTERLANDS_GUARDIAN_L.add(new GOTSpawnEntry(GOTEntityWesterlandsGuard.class, 10, 1, 2));
		WESTERLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityWesterlandsLevyman.class, 10, 1, 2));
		WESTERLANDS_MILITARY_L.add(new GOTSpawnEntry(GOTEntityWesterlandsLevymanArcher.class, 5, 1, 2));
		WILDING_GIANT_L.add(new GOTSpawnEntry(GOTEntityGiant.class, 10, 1, 1));
		WILDING_MILITARY_L.add(new GOTSpawnEntry(GOTEntityWildling.class, 10, 1, 2));
		WILDING_MILITARY_L.add(new GOTSpawnEntry(GOTEntityWildlingArcher.class, 5, 1, 2));
		WILDING_THENN_L.add(new GOTSpawnEntry(GOTEntityThenn.class, 10, 1, 2));
		WILDING_THENN_L.add(new GOTSpawnEntry(GOTEntityThennArcher.class, 5, 1, 2));
		WILDING_THENN_L.add(new GOTSpawnEntry(GOTEntityThennBerserker.class, 10, 1, 2));
		WYVERN_L.add(new GOTSpawnEntry(GOTEntityWyvern.class, 10, 1, 1));
		YITI_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityYiTiBombardier.class, 2, 1, 1));
		YITI_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityYiTiFireThrower.class, 2, 1, 1));
		YITI_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityYiTiFrontier.class, 10, 1, 2));
		YITI_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityYiTiFrontierCrossbower.class, 5, 1, 1));
		YITI_CONQUEST_L.add(new GOTSpawnEntry(GOTEntityYiTiSamurai.class, 2, 1, 2));
		YITI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityYiTiBombardier.class, 2, 1, 1));
		YITI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityYiTiFireThrower.class, 2, 1, 1));
		YITI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityYiTiSoldier.class, 10, 1, 2));
		YITI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5, 1, 1));
		YITI_MILITARY_L.add(new GOTSpawnEntry(GOTEntityYiTiSamurai.class, 2, 1, 2));

		ARRYN_CIVILIAN = new GOTSpawnList(ARRYN_CIVILIAN_L);
		ARRYN_CONQUEST = new GOTSpawnList(ARRYN_CONQUEST_L);
		ARRYN_GUARDIAN = new GOTSpawnList(ARRYN_GUARDIAN_L);
		ARRYN_MILITARY = new GOTSpawnList(ARRYN_MILITARY_L);
		ASSHAI_MILITARY = new GOTSpawnList(ASSHAI_MILITARY_L);
		BRAAVOS_CONQUEST = new GOTSpawnList(BRAAVOS_CONQUEST_L);
		BRAAVOS_MILITARY = new GOTSpawnList(BRAAVOS_MILITARY_L);
		CROCODILE = new GOTSpawnList(CROCODILE_L);
		CROWNLANDS_CIVILIAN = new GOTSpawnList(CROWNLANDS_CIVILIAN_L);
		CROWNLANDS_GUARDIAN = new GOTSpawnList(CROWNLANDS_GUARDIAN_L);
		CROWNLANDS_MILITARY = new GOTSpawnList(CROWNLANDS_MILITARY_L);
		DESERT_SCORPION = new GOTSpawnList(DESERT_SCORPION_L);
		DORNE_CONQUEST = new GOTSpawnList(DORNE_CONQUEST_L);
		DORNE_MILITARY = new GOTSpawnList(DORNE_MILITARY_L);
		DRAGONSTONE_CONQUEST = new GOTSpawnList(DRAGONSTONE_CONQUEST_L);
		DRAGONSTONE_MILITARY = new GOTSpawnList(DRAGONSTONE_MILITARY_L);
		GHISCAR_CONQUEST = new GOTSpawnList(GHISCAR_CONQUEST_L);
		GHISCAR_GUARDIAN = new GOTSpawnList(GHISCAR_GUARDIAN_L);
		GHISCAR_HARPY = new GOTSpawnList(GHISCAR_HARPY_L);
		GHISCAR_MILITARY = new GOTSpawnList(GHISCAR_MILITARY_L);
		GHISCAR_UNSULLIED = new GOTSpawnList(GHISCAR_UNSULLIED_L);
		GIFT_GUARDIAN = new GOTSpawnList(GIFT_GUARDIAN_L);
		GOLDEN_MILITARY = new GOTSpawnList(GOLDEN_MILITARY_L);
		HILL_TRIBES_CIVILIAN = new GOTSpawnList(HILL_TRIBES_CIVILIAN_L);
		HILL_TRIBES_MILITARY = new GOTSpawnList(HILL_TRIBES_MILITARY_L);
		IBBEN_CIVILIAN = new GOTSpawnList(IBBEN_CIVILIAN_L);
		IBBEN_MILITARY = new GOTSpawnList(IBBEN_MILITARY_L);
		IFEKEVRON = new GOTSpawnList(IFEKEVRON_L);
		IRONBORN_CONQUEST = new GOTSpawnList(IRONBORN_CONQUEST_L);
		IRONBORN_MILITARY = new GOTSpawnList(IRONBORN_MILITARY_L);
		JOGOS_MILITARY = new GOTSpawnList(JOGOS_MILITARY_L);
		JUNGLE_SCORPION = new GOTSpawnList(JUNGLE_SCORPION_L);
		LORATH_CONQUEST = new GOTSpawnList(LORATH_CONQUEST_L);
		LORATH_MILITARY = new GOTSpawnList(LORATH_MILITARY_L);
		LYS_CONQUEST = new GOTSpawnList(LYS_CONQUEST_L);
		LYS_MILITARY = new GOTSpawnList(LYS_MILITARY_L);
		MANTICORE = new GOTSpawnList(MANTICORE_L);
		MOSSOVY_MILITARY = new GOTSpawnList(MOSSOVY_MILITARY_L);
		MOSSOVY_WEREWOLF = new GOTSpawnList(MOSSOVY_WEREWOLF_L);
		MYR_CONQUEST = new GOTSpawnList(MYR_CONQUEST_L);
		MYR_MILITARY = new GOTSpawnList(MYR_MILITARY_L);
		NORTH_CIVILIAN = new GOTSpawnList(NORTH_CIVILIAN_L);
		NORTH_CONQUEST = new GOTSpawnList(NORTH_CONQUEST_L);
		NORTH_GUARDIAN = new GOTSpawnList(NORTH_GUARDIAN_L);
		NORTH_HILLMEN = new GOTSpawnList(NORTH_HILLMEN_L);
		NORTH_MILITARY = new GOTSpawnList(NORTH_MILITARY_L);
		NORVOS_MILITARY = new GOTSpawnList(NORVOS_MILITARY_L);
		PENTOS_MILITARY = new GOTSpawnList(PENTOS_MILITARY_L);
		QARTH_MILITARY = new GOTSpawnList(QARTH_MILITARY_L);
		QOHOR_MILITARY = new GOTSpawnList(QOHOR_MILITARY_L);
		REACH_CIVILIAN = new GOTSpawnList(REACH_CIVILIAN_L);
		REACH_CONQUEST = new GOTSpawnList(REACH_CONQUEST_L);
		REACH_GUARDIAN = new GOTSpawnList(REACH_GUARDIAN_L);
		REACH_MILITARY = new GOTSpawnList(REACH_MILITARY_L);
		RED_SCORPION = new GOTSpawnList(RED_SCORPION_L);
		RIVERLANDS_CONQUEST = new GOTSpawnList(RIVERLANDS_CONQUEST_L);
		RIVERLANDS_MILITARY = new GOTSpawnList(RIVERLANDS_MILITARY_L);
		SHRYKE = new GOTSpawnList(SHRYKE_L);
		SOTHORYOS_MILITARY = new GOTSpawnList(SOTHORYOS_MILITARY_L);
		STORMLANDS_CONQUEST = new GOTSpawnList(STORMLANDS_CONQUEST_L);
		STORMLANDS_MILITARY = new GOTSpawnList(STORMLANDS_MILITARY_L);
		SUMMER_MILITARY = new GOTSpawnList(SUMMER_MILITARY_L);
		TYROSH_CONQUEST = new GOTSpawnList(TYROSH_CONQUEST_L);
		TYROSH_MILITARY = new GOTSpawnList(TYROSH_MILITARY_L);
		ULTHOS = new GOTSpawnList(ULTHOS_L);
		VALYRIA = new GOTSpawnList(VALYRIA_L);
		VOLANTIS_CONQUEST = new GOTSpawnList(VOLANTIS_CONQUEST_L);
		VOLANTIS_MILITARY = new GOTSpawnList(VOLANTIS_MILITARY_L);
		WALKERS_CONQUEST = new GOTSpawnList(WALKERS_CONQUEST_L);
		WALKERS_MILITARY = new GOTSpawnList(WALKERS_MILITARY_L);
		WESTERLANDS_CIVILIAN = new GOTSpawnList(WESTERLANDS_CIVILIAN_L);
		WESTERLANDS_CONQUEST = new GOTSpawnList(WESTERLANDS_CONQUEST_L);
		WESTERLANDS_GUARDIAN = new GOTSpawnList(WESTERLANDS_GUARDIAN_L);
		WESTERLANDS_MILITARY = new GOTSpawnList(WESTERLANDS_MILITARY_L);
		WILDING_GIANT = new GOTSpawnList(WILDING_GIANT_L);
		WILDING_MILITARY = new GOTSpawnList(WILDING_MILITARY_L);
		WILDING_THENN = new GOTSpawnList(WILDING_THENN_L);
		WYVERN = new GOTSpawnList(WYVERN_L);
		YITI_CONQUEST = new GOTSpawnList(YITI_CONQUEST_L);
		YITI_MILITARY = new GOTSpawnList(YITI_MILITARY_L);
	}

	public List<GOTSpawnEntry> spawnList;
	public GOTFaction discoveredFaction;

	public GOTSpawnList(List<GOTSpawnEntry> entries) {
		spawnList = entries;
	}

	public GOTFaction getListCommonFaction(World world) {
		if (discoveredFaction != null) {
			return discoveredFaction;
		}
		GOTFaction commonFaction = null;
		for (GOTSpawnEntry entry : spawnList) {
			Class<? extends GOTEntityNPC> entityClass = entry.entityClass;
			if (GOTEntityNPC.class.isAssignableFrom(entityClass)) {
				try {
					GOTEntityNPC npc = (GOTEntityNPC) GOTEntityRegistry.createEntityByClass(entityClass, world);
					GOTFaction fac = npc.getFaction();
					if (commonFaction == null) {
						commonFaction = fac;
						continue;
					}
					if (commonFaction == fac) {
						continue;
					}
					throw new IllegalArgumentException("Spawn lists must contain only one faction! Mismatched entity class: " + entityClass.getName());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			throw new IllegalArgumentException("Spawn list must contain only NPCs - invalid " + entityClass.getName());
		}
		if (commonFaction != null) {
			discoveredFaction = commonFaction;
			return discoveredFaction;
		}
		throw new IllegalArgumentException("Failed to discover faction for spawn list");
	}

	public GOTSpawnEntry getRandomSpawnEntry(Random rand) {
		return (GOTSpawnEntry) WeightedRandom.getRandomItem(rand, spawnList);
	}
}