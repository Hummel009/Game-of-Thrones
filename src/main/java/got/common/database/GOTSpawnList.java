package got.common.database;

import got.common.entity.essos.asshai.GOTEntityAsshaiShadowbinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiSpherebinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiWarrior;
import got.common.entity.essos.braavos.GOTEntityBraavosLevyman;
import got.common.entity.essos.braavos.GOTEntityBraavosLevymanArcher;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldier;
import got.common.entity.essos.braavos.GOTEntityBraavosSoldierArcher;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.golden.GOTEntityGoldenCompanySpearman;
import got.common.entity.essos.golden.GOTEntityGoldenCompanyWarrior;
import got.common.entity.essos.ibben.GOTEntityIbbenLevyman;
import got.common.entity.essos.ibben.GOTEntityIbbenLevymanArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenSoldier;
import got.common.entity.essos.ibben.GOTEntityIbbenSoldierArcher;
import got.common.entity.essos.jogos.GOTEntityJogosNhaiArcher;
import got.common.entity.essos.jogos.GOTEntityJogosNhaiMan;
import got.common.entity.essos.lhazar.GOTEntityLhazarLevyman;
import got.common.entity.essos.lhazar.GOTEntityLhazarLevymanArcher;
import got.common.entity.essos.lhazar.GOTEntityLhazarSoldier;
import got.common.entity.essos.lhazar.GOTEntityLhazarSoldierArcher;
import got.common.entity.essos.lorath.GOTEntityLorathLevyman;
import got.common.entity.essos.lorath.GOTEntityLorathLevymanArcher;
import got.common.entity.essos.lorath.GOTEntityLorathSoldier;
import got.common.entity.essos.lorath.GOTEntityLorathSoldierArcher;
import got.common.entity.essos.lys.GOTEntityLysLevyman;
import got.common.entity.essos.lys.GOTEntityLysLevymanArcher;
import got.common.entity.essos.lys.GOTEntityLysSoldier;
import got.common.entity.essos.lys.GOTEntityLysSoldierArcher;
import got.common.entity.essos.mossovy.GOTEntityMossovyWitcher;
import got.common.entity.essos.myr.GOTEntityMyrLevyman;
import got.common.entity.essos.myr.GOTEntityMyrLevymanArcher;
import got.common.entity.essos.myr.GOTEntityMyrSoldier;
import got.common.entity.essos.myr.GOTEntityMyrSoldierArcher;
import got.common.entity.essos.norvos.GOTEntityNorvosLevyman;
import got.common.entity.essos.norvos.GOTEntityNorvosLevymanArcher;
import got.common.entity.essos.norvos.GOTEntityNorvosSoldier;
import got.common.entity.essos.norvos.GOTEntityNorvosSoldierArcher;
import got.common.entity.essos.pentos.GOTEntityPentosLevyman;
import got.common.entity.essos.pentos.GOTEntityPentosLevymanArcher;
import got.common.entity.essos.pentos.GOTEntityPentosSoldier;
import got.common.entity.essos.pentos.GOTEntityPentosSoldierArcher;
import got.common.entity.essos.qarth.GOTEntityQarthLevyman;
import got.common.entity.essos.qarth.GOTEntityQarthLevymanArcher;
import got.common.entity.essos.qarth.GOTEntityQarthSoldier;
import got.common.entity.essos.qarth.GOTEntityQarthSoldierArcher;
import got.common.entity.essos.qohor.*;
import got.common.entity.essos.tyrosh.GOTEntityTyroshLevyman;
import got.common.entity.essos.tyrosh.GOTEntityTyroshLevymanArcher;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldier;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldierArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisLevyman;
import got.common.entity.essos.volantis.GOTEntityVolantisLevymanArcher;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldier;
import got.common.entity.essos.volantis.GOTEntityVolantisSoldierArcher;
import got.common.entity.essos.yi_ti.*;
import got.common.entity.other.GOTEntityBlizzard;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityUlthosSpider;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosBlowgunner;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosWarrior;
import got.common.entity.sothoryos.summer.GOTEntitySummerSoldier;
import got.common.entity.sothoryos.summer.GOTEntitySummerSoldierArcher;
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
import got.common.entity.westeros.hillmen.GOTEntityHillmanArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanAxeThrower;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBerserker;
import got.common.entity.westeros.hillmen.GOTEntityHillmanWarrior;
import got.common.entity.westeros.ice.GOTEntityIceSpider;
import got.common.entity.westeros.ice.GOTEntityWhiteWalker;
import got.common.entity.westeros.ironborn.GOTEntityIronbornLevyman;
import got.common.entity.westeros.ironborn.GOTEntityIronbornLevymanArcher;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldierArcher;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanArcher;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanAxeThrower;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanWarrior;
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
import got.common.entity.westeros.wildling.GOTEntityWildlingArcher;
import got.common.entity.westeros.wildling.GOTEntityWildlingWarrior;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennWarrior;
import got.common.faction.GOTFaction;
import got.common.util.GOTReflection;
import got.common.world.spawning.GOTSpawnEntry;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GOTSpawnList {
	public static final GOTSpawnList ARRYN_CIVILIAN;
	public static final GOTSpawnList ARRYN_CONQUEST;
	public static final GOTSpawnList ARRYN_GUARDIAN;
	public static final GOTSpawnList ARRYN_MILITARY;
	public static final GOTSpawnList ASSHAI_MILITARY;
	public static final GOTSpawnList BRAAVOS_CONQUEST;
	public static final GOTSpawnList BRAAVOS_MILITARY;
	public static final GOTSpawnList CROWNLANDS_CIVILIAN;
	public static final GOTSpawnList CROWNLANDS_GUARDIAN;
	public static final GOTSpawnList CROWNLANDS_MILITARY;
	public static final GOTSpawnList DORNE_CONQUEST;
	public static final GOTSpawnList DORNE_MILITARY;
	public static final GOTSpawnList DRAGONSTONE_CONQUEST;
	public static final GOTSpawnList DRAGONSTONE_MILITARY;
	public static final GOTSpawnList GHISCAR_CONQUEST;
	public static final GOTSpawnList GHISCAR_HARPY;
	public static final GOTSpawnList GHISCAR_MILITARY;
	public static final GOTSpawnList GHISCAR_UNSULLIED;
	public static final GOTSpawnList GIFT_GUARDIAN;
	public static final GOTSpawnList GOLDEN_MILITARY;
	public static final GOTSpawnList HILL_TRIBES_MILITARY;
	public static final GOTSpawnList IBBEN_CONQUEST;
	public static final GOTSpawnList IBBEN_MILITARY;
	public static final GOTSpawnList IRONBORN_CONQUEST;
	public static final GOTSpawnList IRONBORN_MILITARY;
	public static final GOTSpawnList JOGOS_NHAI_MILITARY;
	public static final GOTSpawnList LHAZAR_CONQUEST;
	public static final GOTSpawnList LHAZAR_MILITARY;
	public static final GOTSpawnList LORATH_CONQUEST;
	public static final GOTSpawnList LORATH_MILITARY;
	public static final GOTSpawnList LYS_CONQUEST;
	public static final GOTSpawnList LYS_MILITARY;
	public static final GOTSpawnList MOSSOVY_MILITARY;
	public static final GOTSpawnList MYR_CONQUEST;
	public static final GOTSpawnList MYR_MILITARY;
	public static final GOTSpawnList NORTH_CIVILIAN;
	public static final GOTSpawnList NORTH_CONQUEST;
	public static final GOTSpawnList NORTH_GUARDIAN;
	public static final GOTSpawnList NORTH_HILLMEN;
	public static final GOTSpawnList NORTH_MILITARY;
	public static final GOTSpawnList NORVOS_CONQUEST;
	public static final GOTSpawnList NORVOS_MILITARY;
	public static final GOTSpawnList PENTOS_CONQUEST;
	public static final GOTSpawnList PENTOS_MILITARY;
	public static final GOTSpawnList QARTH_CONQUEST;
	public static final GOTSpawnList QARTH_MILITARY;
	public static final GOTSpawnList QOHOR_CONQUEST;
	public static final GOTSpawnList QOHOR_MILITARY;
	public static final GOTSpawnList REACH_CIVILIAN;
	public static final GOTSpawnList REACH_CONQUEST;
	public static final GOTSpawnList REACH_GUARDIAN;
	public static final GOTSpawnList REACH_MILITARY;
	public static final GOTSpawnList RIVERLANDS_CONQUEST;
	public static final GOTSpawnList RIVERLANDS_MILITARY;
	public static final GOTSpawnList SOTHORYOS_MILITARY;
	public static final GOTSpawnList STORMLANDS_CONQUEST;
	public static final GOTSpawnList STORMLANDS_MILITARY;
	public static final GOTSpawnList SUMMER_MILITARY;
	public static final GOTSpawnList TYROSH_CONQUEST;
	public static final GOTSpawnList TYROSH_MILITARY;
	public static final GOTSpawnList VOLANTIS_CONQUEST;
	public static final GOTSpawnList VOLANTIS_MILITARY;
	public static final GOTSpawnList WALKERS_BLIZZARD;
	public static final GOTSpawnList WALKERS_CONQUEST;
	public static final GOTSpawnList WALKERS_MILITARY;
	public static final GOTSpawnList WESTERLANDS_CIVILIAN;
	public static final GOTSpawnList WESTERLANDS_CONQUEST;
	public static final GOTSpawnList WESTERLANDS_GUARDIAN;
	public static final GOTSpawnList WESTERLANDS_MILITARY;
	public static final GOTSpawnList WILDING_GIANT;
	public static final GOTSpawnList WILDING_MILITARY;
	public static final GOTSpawnList WILDING_THENN;
	public static final GOTSpawnList YI_TI_CONQUEST;
	public static final GOTSpawnList YI_TI_MILITARY;
	public static final GOTSpawnList ULTHOS;

	static {
		List<GOTSpawnEntry> arrynCivilian = new ArrayList<>();
		arrynCivilian.add(new GOTSpawnEntry(GOTEntityArrynMan.class, 10, 1, 2));
		ARRYN_CIVILIAN = new GOTSpawnList(arrynCivilian);

		List<GOTSpawnEntry> arrynConquest = new ArrayList<>();
		arrynConquest.add(new GOTSpawnEntry(GOTEntityArrynSoldier.class, 10, 1, 2));
		arrynConquest.add(new GOTSpawnEntry(GOTEntityArrynSoldierArcher.class, 5, 1, 2));
		ARRYN_CONQUEST = new GOTSpawnList(arrynConquest);

		List<GOTSpawnEntry> arrynGuardian = new ArrayList<>();
		arrynGuardian.add(new GOTSpawnEntry(GOTEntityArrynGuard.class, 10, 1, 2));
		ARRYN_GUARDIAN = new GOTSpawnList(arrynGuardian);

		List<GOTSpawnEntry> arrynMilitary = new ArrayList<>();
		arrynMilitary.add(new GOTSpawnEntry(GOTEntityArrynLevyman.class, 10, 1, 2));
		arrynMilitary.add(new GOTSpawnEntry(GOTEntityArrynLevymanArcher.class, 5, 1, 2));
		ARRYN_MILITARY = new GOTSpawnList(arrynMilitary);

		List<GOTSpawnEntry> asshaiMilitary = new ArrayList<>();
		asshaiMilitary.add(new GOTSpawnEntry(GOTEntityAsshaiShadowbinder.class, 2, 1, 1));
		asshaiMilitary.add(new GOTSpawnEntry(GOTEntityAsshaiSpherebinder.class, 2, 1, 1));
		asshaiMilitary.add(new GOTSpawnEntry(GOTEntityAsshaiWarrior.class, 10, 1, 2));
		ASSHAI_MILITARY = new GOTSpawnList(asshaiMilitary);

		List<GOTSpawnEntry> braavosConquest = new ArrayList<>();
		braavosConquest.add(new GOTSpawnEntry(GOTEntityBraavosSoldier.class, 10, 1, 2));
		braavosConquest.add(new GOTSpawnEntry(GOTEntityBraavosSoldierArcher.class, 5, 1, 2));
		BRAAVOS_CONQUEST = new GOTSpawnList(braavosConquest);

		List<GOTSpawnEntry> braavosMilitary = new ArrayList<>();
		braavosMilitary.add(new GOTSpawnEntry(GOTEntityBraavosLevyman.class, 10, 1, 2));
		braavosMilitary.add(new GOTSpawnEntry(GOTEntityBraavosLevymanArcher.class, 5, 1, 2));
		BRAAVOS_MILITARY = new GOTSpawnList(braavosMilitary);

		List<GOTSpawnEntry> crownlandsCivilian = new ArrayList<>();
		crownlandsCivilian.add(new GOTSpawnEntry(GOTEntityCrownlandsMan.class, 10, 1, 2));
		CROWNLANDS_CIVILIAN = new GOTSpawnList(crownlandsCivilian);

		List<GOTSpawnEntry> crownlandsGuardian = new ArrayList<>();
		crownlandsGuardian.add(new GOTSpawnEntry(GOTEntityCrownlandsGuard.class, 10, 1, 2));
		CROWNLANDS_GUARDIAN = new GOTSpawnList(crownlandsGuardian);

		List<GOTSpawnEntry> crownlandsMilitary = new ArrayList<>();
		crownlandsMilitary.add(new GOTSpawnEntry(GOTEntityCrownlandsLevyman.class, 10, 1, 2));
		crownlandsMilitary.add(new GOTSpawnEntry(GOTEntityCrownlandsLevymanArcher.class, 5, 1, 2));
		CROWNLANDS_MILITARY = new GOTSpawnList(crownlandsMilitary);

		List<GOTSpawnEntry> dorneConquest = new ArrayList<>();
		dorneConquest.add(new GOTSpawnEntry(GOTEntityDorneSoldier.class, 10, 1, 2));
		dorneConquest.add(new GOTSpawnEntry(GOTEntityDorneSoldierArcher.class, 5, 1, 2));
		DORNE_CONQUEST = new GOTSpawnList(dorneConquest);

		List<GOTSpawnEntry> dorneMilitary = new ArrayList<>();
		dorneMilitary.add(new GOTSpawnEntry(GOTEntityDorneLevyman.class, 10, 1, 2));
		dorneMilitary.add(new GOTSpawnEntry(GOTEntityDorneLevymanArcher.class, 5, 1, 2));
		DORNE_MILITARY = new GOTSpawnList(dorneMilitary);

		List<GOTSpawnEntry> dragonstoneConquest = new ArrayList<>();
		dragonstoneConquest.add(new GOTSpawnEntry(GOTEntityDragonstoneSoldier.class, 10, 1, 2));
		dragonstoneConquest.add(new GOTSpawnEntry(GOTEntityDragonstoneSoldierArcher.class, 5, 1, 2));
		DRAGONSTONE_CONQUEST = new GOTSpawnList(dragonstoneConquest);

		List<GOTSpawnEntry> dragonstoneMilitary = new ArrayList<>();
		dragonstoneMilitary.add(new GOTSpawnEntry(GOTEntityDragonstoneLevyman.class, 10, 1, 2));
		dragonstoneMilitary.add(new GOTSpawnEntry(GOTEntityDragonstoneLevymanArcher.class, 5, 1, 2));
		DRAGONSTONE_MILITARY = new GOTSpawnList(dragonstoneMilitary);

		List<GOTSpawnEntry> ghiscarConquest = new ArrayList<>();
		ghiscarConquest.add(new GOTSpawnEntry(GOTEntityGhiscarSoldier.class, 10, 1, 2));
		ghiscarConquest.add(new GOTSpawnEntry(GOTEntityGhiscarSoldierArcher.class, 5, 1, 2));
		GHISCAR_CONQUEST = new GOTSpawnList(ghiscarConquest);

		List<GOTSpawnEntry> ghiscarHarpy = new ArrayList<>();
		ghiscarHarpy.add(new GOTSpawnEntry(GOTEntityGhiscarHarpy.class, 10, 1, 2));
		GHISCAR_HARPY = new GOTSpawnList(ghiscarHarpy);

		List<GOTSpawnEntry> ghiscarMilitary = new ArrayList<>();
		ghiscarMilitary.add(new GOTSpawnEntry(GOTEntityGhiscarLevyman.class, 10, 1, 2));
		ghiscarMilitary.add(new GOTSpawnEntry(GOTEntityGhiscarLevymanArcher.class, 5, 1, 2));
		GHISCAR_MILITARY = new GOTSpawnList(ghiscarMilitary);

		List<GOTSpawnEntry> ghiscarUnsullied = new ArrayList<>();
		ghiscarUnsullied.add(new GOTSpawnEntry(GOTEntityGhiscarUnsullied.class, 10, 1, 2));
		GHISCAR_UNSULLIED = new GOTSpawnList(ghiscarUnsullied);

		List<GOTSpawnEntry> giftGuardian = new ArrayList<>();
		giftGuardian.add(new GOTSpawnEntry(GOTEntityGiftGuard.class, 10, 1, 2));
		GIFT_GUARDIAN = new GOTSpawnList(giftGuardian);

		List<GOTSpawnEntry> goldenMilitary = new ArrayList<>();
		goldenMilitary.add(new GOTSpawnEntry(GOTEntityGoldenCompanySpearman.class, 5, 1, 2));
		goldenMilitary.add(new GOTSpawnEntry(GOTEntityGoldenCompanyWarrior.class, 10, 1, 2));
		GOLDEN_MILITARY = new GOTSpawnList(goldenMilitary);

		List<GOTSpawnEntry> hillTribesMilitary = new ArrayList<>();
		hillTribesMilitary.add(new GOTSpawnEntry(GOTEntityHillmanArcher.class, 5, 1, 2));
		hillTribesMilitary.add(new GOTSpawnEntry(GOTEntityHillmanAxeThrower.class, 3, 1, 2));
		hillTribesMilitary.add(new GOTSpawnEntry(GOTEntityHillmanBerserker.class, 3, 1, 2));
		hillTribesMilitary.add(new GOTSpawnEntry(GOTEntityHillmanWarrior.class, 10, 1, 2));
		HILL_TRIBES_MILITARY = new GOTSpawnList(hillTribesMilitary);

		List<GOTSpawnEntry> ibbenConquest = new ArrayList<>();
		ibbenConquest.add(new GOTSpawnEntry(GOTEntityIbbenSoldier.class, 10, 1, 2));
		ibbenConquest.add(new GOTSpawnEntry(GOTEntityIbbenSoldierArcher.class, 5, 1, 2));
		IBBEN_CONQUEST = new GOTSpawnList(ibbenConquest);

		List<GOTSpawnEntry> ibbenMilitary = new ArrayList<>();
		ibbenMilitary.add(new GOTSpawnEntry(GOTEntityIbbenLevyman.class, 10, 1, 2));
		ibbenMilitary.add(new GOTSpawnEntry(GOTEntityIbbenLevymanArcher.class, 5, 1, 2));
		IBBEN_MILITARY = new GOTSpawnList(ibbenMilitary);

		List<GOTSpawnEntry> ironbornConquest = new ArrayList<>();
		ironbornConquest.add(new GOTSpawnEntry(GOTEntityIronbornSoldier.class, 10, 1, 2));
		ironbornConquest.add(new GOTSpawnEntry(GOTEntityIronbornSoldierArcher.class, 5, 1, 2));
		IRONBORN_CONQUEST = new GOTSpawnList(ironbornConquest);

		List<GOTSpawnEntry> ironbornMilitary = new ArrayList<>();
		ironbornMilitary.add(new GOTSpawnEntry(GOTEntityIronbornLevyman.class, 10, 1, 2));
		ironbornMilitary.add(new GOTSpawnEntry(GOTEntityIronbornLevymanArcher.class, 5, 1, 2));
		IRONBORN_MILITARY = new GOTSpawnList(ironbornMilitary);

		List<GOTSpawnEntry> jogosNhaiMilitary = new ArrayList<>();
		jogosNhaiMilitary.add(new GOTSpawnEntry(GOTEntityJogosNhaiArcher.class, 5, 1, 2));
		jogosNhaiMilitary.add(new GOTSpawnEntry(GOTEntityJogosNhaiMan.class, 10, 1, 2));
		JOGOS_NHAI_MILITARY = new GOTSpawnList(jogosNhaiMilitary);

		List<GOTSpawnEntry> lhazarConquest = new ArrayList<>();
		lhazarConquest.add(new GOTSpawnEntry(GOTEntityLhazarSoldier.class, 10, 1, 2));
		lhazarConquest.add(new GOTSpawnEntry(GOTEntityLhazarSoldierArcher.class, 5, 1, 2));
		LHAZAR_CONQUEST = new GOTSpawnList(lhazarConquest);

		List<GOTSpawnEntry> lhazarMilitary = new ArrayList<>();
		lhazarMilitary.add(new GOTSpawnEntry(GOTEntityLhazarLevyman.class, 10, 1, 2));
		lhazarMilitary.add(new GOTSpawnEntry(GOTEntityLhazarLevymanArcher.class, 5, 1, 2));
		LHAZAR_MILITARY = new GOTSpawnList(lhazarMilitary);

		List<GOTSpawnEntry> lorathConquest = new ArrayList<>();
		lorathConquest.add(new GOTSpawnEntry(GOTEntityLorathSoldier.class, 10, 1, 2));
		lorathConquest.add(new GOTSpawnEntry(GOTEntityLorathSoldierArcher.class, 5, 1, 2));
		LORATH_CONQUEST = new GOTSpawnList(lorathConquest);

		List<GOTSpawnEntry> lorathMilitary = new ArrayList<>();
		lorathMilitary.add(new GOTSpawnEntry(GOTEntityLorathLevyman.class, 10, 1, 2));
		lorathMilitary.add(new GOTSpawnEntry(GOTEntityLorathLevymanArcher.class, 5, 1, 2));
		LORATH_MILITARY = new GOTSpawnList(lorathMilitary);

		List<GOTSpawnEntry> lysConquest = new ArrayList<>();
		lysConquest.add(new GOTSpawnEntry(GOTEntityLysSoldier.class, 10, 1, 2));
		lysConquest.add(new GOTSpawnEntry(GOTEntityLysSoldierArcher.class, 5, 1, 2));
		LYS_CONQUEST = new GOTSpawnList(lysConquest);

		List<GOTSpawnEntry> lysMilitary = new ArrayList<>();
		lysMilitary.add(new GOTSpawnEntry(GOTEntityLysLevyman.class, 10, 1, 2));
		lysMilitary.add(new GOTSpawnEntry(GOTEntityLysLevymanArcher.class, 5, 1, 2));
		LYS_MILITARY = new GOTSpawnList(lysMilitary);

		List<GOTSpawnEntry> mossovyMilitary = new ArrayList<>();
		mossovyMilitary.add(new GOTSpawnEntry(GOTEntityMossovyWitcher.class, 10, 1, 1));
		MOSSOVY_MILITARY = new GOTSpawnList(mossovyMilitary);

		List<GOTSpawnEntry> myrConquest = new ArrayList<>();
		myrConquest.add(new GOTSpawnEntry(GOTEntityMyrSoldier.class, 10, 1, 2));
		myrConquest.add(new GOTSpawnEntry(GOTEntityMyrSoldierArcher.class, 5, 1, 2));
		MYR_CONQUEST = new GOTSpawnList(myrConquest);

		List<GOTSpawnEntry> myrMilitary = new ArrayList<>();
		myrMilitary.add(new GOTSpawnEntry(GOTEntityMyrLevyman.class, 10, 1, 2));
		myrMilitary.add(new GOTSpawnEntry(GOTEntityMyrLevymanArcher.class, 5, 1, 2));
		MYR_MILITARY = new GOTSpawnList(myrMilitary);

		List<GOTSpawnEntry> northCivilian = new ArrayList<>();
		northCivilian.add(new GOTSpawnEntry(GOTEntityNorthMan.class, 10, 1, 2));
		NORTH_CIVILIAN = new GOTSpawnList(northCivilian);

		List<GOTSpawnEntry> northConquest = new ArrayList<>();
		northConquest.add(new GOTSpawnEntry(GOTEntityNorthSoldier.class, 10, 1, 2));
		northConquest.add(new GOTSpawnEntry(GOTEntityNorthSoldierArcher.class, 5, 1, 2));
		NORTH_CONQUEST = new GOTSpawnList(northConquest);

		List<GOTSpawnEntry> northGuardian = new ArrayList<>();
		northGuardian.add(new GOTSpawnEntry(GOTEntityNorthGuard.class, 10, 1, 2));
		NORTH_GUARDIAN = new GOTSpawnList(northGuardian);

		List<GOTSpawnEntry> northHillmen = new ArrayList<>();
		northHillmen.add(new GOTSpawnEntry(GOTEntityNorthHillmanArcher.class, 5, 1, 2));
		northHillmen.add(new GOTSpawnEntry(GOTEntityNorthHillmanAxeThrower.class, 3, 1, 2));
		northHillmen.add(new GOTSpawnEntry(GOTEntityNorthHillmanWarrior.class, 10, 1, 2));
		NORTH_HILLMEN = new GOTSpawnList(northHillmen);

		List<GOTSpawnEntry> northMilitary = new ArrayList<>();
		northMilitary.add(new GOTSpawnEntry(GOTEntityNorthLevyman.class, 10, 1, 2));
		northMilitary.add(new GOTSpawnEntry(GOTEntityNorthLevymanArcher.class, 5, 1, 2));
		NORTH_MILITARY = new GOTSpawnList(northMilitary);

		List<GOTSpawnEntry> norvosConquest = new ArrayList<>();
		norvosConquest.add(new GOTSpawnEntry(GOTEntityNorvosSoldier.class, 10, 1, 2));
		norvosConquest.add(new GOTSpawnEntry(GOTEntityNorvosSoldierArcher.class, 5, 1, 2));
		NORVOS_CONQUEST = new GOTSpawnList(norvosConquest);

		List<GOTSpawnEntry> norvosMilitary = new ArrayList<>();
		norvosMilitary.add(new GOTSpawnEntry(GOTEntityNorvosLevyman.class, 10, 1, 2));
		norvosMilitary.add(new GOTSpawnEntry(GOTEntityNorvosLevymanArcher.class, 5, 1, 2));
		NORVOS_MILITARY = new GOTSpawnList(norvosMilitary);

		List<GOTSpawnEntry> pentosConquest = new ArrayList<>();
		pentosConquest.add(new GOTSpawnEntry(GOTEntityPentosSoldier.class, 10, 1, 2));
		pentosConquest.add(new GOTSpawnEntry(GOTEntityPentosSoldierArcher.class, 5, 1, 2));
		PENTOS_CONQUEST = new GOTSpawnList(pentosConquest);

		List<GOTSpawnEntry> pentosMilitary = new ArrayList<>();
		pentosMilitary.add(new GOTSpawnEntry(GOTEntityPentosLevyman.class, 10, 1, 2));
		pentosMilitary.add(new GOTSpawnEntry(GOTEntityPentosLevymanArcher.class, 5, 1, 2));
		PENTOS_MILITARY = new GOTSpawnList(pentosMilitary);

		List<GOTSpawnEntry> qarthConquest = new ArrayList<>();
		qarthConquest.add(new GOTSpawnEntry(GOTEntityQarthSoldier.class, 10, 1, 2));
		qarthConquest.add(new GOTSpawnEntry(GOTEntityQarthSoldierArcher.class, 5, 1, 2));
		QARTH_CONQUEST = new GOTSpawnList(qarthConquest);

		List<GOTSpawnEntry> qarthMilitary = new ArrayList<>();
		qarthMilitary.add(new GOTSpawnEntry(GOTEntityQarthLevyman.class, 10, 1, 2));
		qarthMilitary.add(new GOTSpawnEntry(GOTEntityQarthLevymanArcher.class, 5, 1, 2));
		QARTH_MILITARY = new GOTSpawnList(qarthMilitary);

		List<GOTSpawnEntry> qohorConquest = new ArrayList<>();
		qohorConquest.add(new GOTSpawnEntry(GOTEntityQohorSoldier.class, 10, 1, 2));
		qohorConquest.add(new GOTSpawnEntry(GOTEntityQohorSoldierArcher.class, 5, 1, 2));
		QOHOR_CONQUEST = new GOTSpawnList(qohorConquest);

		List<GOTSpawnEntry> qohorMilitary = new ArrayList<>();
		qohorMilitary.add(new GOTSpawnEntry(GOTEntityQohorLevyman.class, 5, 1, 2));
		qohorMilitary.add(new GOTSpawnEntry(GOTEntityQohorLevymanArcher.class, 2, 1, 2));
		qohorMilitary.add(new GOTSpawnEntry(GOTEntityQohorUnsullied.class, 10, 1, 2));
		QOHOR_MILITARY = new GOTSpawnList(qohorMilitary);

		List<GOTSpawnEntry> reachCivilian = new ArrayList<>();
		reachCivilian.add(new GOTSpawnEntry(GOTEntityReachMan.class, 10, 1, 2));
		REACH_CIVILIAN = new GOTSpawnList(reachCivilian);

		List<GOTSpawnEntry> reachConquest = new ArrayList<>();
		reachConquest.add(new GOTSpawnEntry(GOTEntityReachSoldier.class, 10, 1, 2));
		reachConquest.add(new GOTSpawnEntry(GOTEntityReachSoldierArcher.class, 5, 1, 2));
		REACH_CONQUEST = new GOTSpawnList(reachConquest);

		List<GOTSpawnEntry> reachGuardian = new ArrayList<>();
		reachGuardian.add(new GOTSpawnEntry(GOTEntityReachGuard.class, 10, 1, 2));
		REACH_GUARDIAN = new GOTSpawnList(reachGuardian);

		List<GOTSpawnEntry> reachMilitary = new ArrayList<>();
		reachMilitary.add(new GOTSpawnEntry(GOTEntityReachLevyman.class, 10, 1, 2));
		reachMilitary.add(new GOTSpawnEntry(GOTEntityReachLevymanArcher.class, 5, 1, 2));
		REACH_MILITARY = new GOTSpawnList(reachMilitary);

		List<GOTSpawnEntry> riverlandsConquest = new ArrayList<>();
		riverlandsConquest.add(new GOTSpawnEntry(GOTEntityRiverlandsSoldier.class, 10, 1, 2));
		riverlandsConquest.add(new GOTSpawnEntry(GOTEntityRiverlandsSoldierArcher.class, 5, 1, 2));
		RIVERLANDS_CONQUEST = new GOTSpawnList(riverlandsConquest);

		List<GOTSpawnEntry> riverlandsMilitary = new ArrayList<>();
		riverlandsMilitary.add(new GOTSpawnEntry(GOTEntityRiverlandsLevyman.class, 10, 1, 2));
		riverlandsMilitary.add(new GOTSpawnEntry(GOTEntityRiverlandsLevymanArcher.class, 5, 1, 2));
		RIVERLANDS_MILITARY = new GOTSpawnList(riverlandsMilitary);

		List<GOTSpawnEntry> sothoryosMilitary = new ArrayList<>();
		sothoryosMilitary.add(new GOTSpawnEntry(GOTEntitySothoryosBlowgunner.class, 5, 1, 2));
		sothoryosMilitary.add(new GOTSpawnEntry(GOTEntitySothoryosWarrior.class, 10, 1, 2));
		SOTHORYOS_MILITARY = new GOTSpawnList(sothoryosMilitary);

		List<GOTSpawnEntry> stormlandsConquest = new ArrayList<>();
		stormlandsConquest.add(new GOTSpawnEntry(GOTEntityStormlandsSoldier.class, 10, 1, 2));
		stormlandsConquest.add(new GOTSpawnEntry(GOTEntityStormlandsSoldierArcher.class, 5, 1, 2));
		STORMLANDS_CONQUEST = new GOTSpawnList(stormlandsConquest);

		List<GOTSpawnEntry> stormlandsMilitary = new ArrayList<>();
		stormlandsMilitary.add(new GOTSpawnEntry(GOTEntityStormlandsLevyman.class, 10, 1, 2));
		stormlandsMilitary.add(new GOTSpawnEntry(GOTEntityStormlandsLevymanArcher.class, 5, 1, 2));
		STORMLANDS_MILITARY = new GOTSpawnList(stormlandsMilitary);

		List<GOTSpawnEntry> summerMilitary = new ArrayList<>();
		summerMilitary.add(new GOTSpawnEntry(GOTEntitySummerSoldier.class, 10, 1, 2));
		summerMilitary.add(new GOTSpawnEntry(GOTEntitySummerSoldierArcher.class, 5, 1, 2));
		SUMMER_MILITARY = new GOTSpawnList(summerMilitary);

		List<GOTSpawnEntry> tyroshConquest = new ArrayList<>();
		tyroshConquest.add(new GOTSpawnEntry(GOTEntityTyroshSoldier.class, 10, 1, 2));
		tyroshConquest.add(new GOTSpawnEntry(GOTEntityTyroshSoldierArcher.class, 5, 1, 2));
		TYROSH_CONQUEST = new GOTSpawnList(tyroshConquest);

		List<GOTSpawnEntry> tyroshMilitary = new ArrayList<>();
		tyroshMilitary.add(new GOTSpawnEntry(GOTEntityTyroshLevyman.class, 10, 1, 2));
		tyroshMilitary.add(new GOTSpawnEntry(GOTEntityTyroshLevymanArcher.class, 5, 1, 2));
		TYROSH_MILITARY = new GOTSpawnList(tyroshMilitary);

		List<GOTSpawnEntry> volantisConquest = new ArrayList<>();
		volantisConquest.add(new GOTSpawnEntry(GOTEntityVolantisSoldier.class, 10, 1, 2));
		volantisConquest.add(new GOTSpawnEntry(GOTEntityVolantisSoldierArcher.class, 5, 1, 2));
		VOLANTIS_CONQUEST = new GOTSpawnList(volantisConquest);

		List<GOTSpawnEntry> volantisMilitary = new ArrayList<>();
		volantisMilitary.add(new GOTSpawnEntry(GOTEntityVolantisLevyman.class, 10, 1, 2));
		volantisMilitary.add(new GOTSpawnEntry(GOTEntityVolantisLevymanArcher.class, 5, 1, 2));
		VOLANTIS_MILITARY = new GOTSpawnList(volantisMilitary);

		List<GOTSpawnEntry> walkersBlizzard = new ArrayList<>();
		walkersBlizzard.add(new GOTSpawnEntry(GOTEntityBlizzard.class, 10, 1, 2));
		WALKERS_BLIZZARD = new GOTSpawnList(walkersBlizzard);

		List<GOTSpawnEntry> walkersConquest = new ArrayList<>();
		walkersConquest.add(new GOTSpawnEntry(GOTEntityIceSpider.class, 5, 1, 2));
		walkersConquest.add(new GOTSpawnEntry(GOTEntityWhiteWalker.class, 10, 1, 2));
		WALKERS_CONQUEST = new GOTSpawnList(walkersConquest);

		List<GOTSpawnEntry> walkersMilitary = new ArrayList<>();
		walkersMilitary.add(new GOTSpawnEntry(GOTEntityWhiteWalker.class, 10, 1, 2));
		WALKERS_MILITARY = new GOTSpawnList(walkersMilitary);

		List<GOTSpawnEntry> westerlandsCivilian = new ArrayList<>();
		westerlandsCivilian.add(new GOTSpawnEntry(GOTEntityWesterlandsMan.class, 10, 1, 2));
		WESTERLANDS_CIVILIAN = new GOTSpawnList(westerlandsCivilian);

		List<GOTSpawnEntry> westerlandsConquest = new ArrayList<>();
		westerlandsConquest.add(new GOTSpawnEntry(GOTEntityWesterlandsSoldier.class, 10, 1, 2));
		westerlandsConquest.add(new GOTSpawnEntry(GOTEntityWesterlandsSoldierArcher.class, 5, 1, 2));
		WESTERLANDS_CONQUEST = new GOTSpawnList(westerlandsConquest);

		List<GOTSpawnEntry> westerlandsGuardian = new ArrayList<>();
		westerlandsGuardian.add(new GOTSpawnEntry(GOTEntityWesterlandsGuard.class, 10, 1, 2));
		WESTERLANDS_GUARDIAN = new GOTSpawnList(westerlandsGuardian);

		List<GOTSpawnEntry> westerlandsMilitary = new ArrayList<>();
		westerlandsMilitary.add(new GOTSpawnEntry(GOTEntityWesterlandsLevyman.class, 10, 1, 2));
		westerlandsMilitary.add(new GOTSpawnEntry(GOTEntityWesterlandsLevymanArcher.class, 5, 1, 2));
		WESTERLANDS_MILITARY = new GOTSpawnList(westerlandsMilitary);

		List<GOTSpawnEntry> wildingGiant = new ArrayList<>();
		wildingGiant.add(new GOTSpawnEntry(GOTEntityGiant.class, 10, 1, 1));
		WILDING_GIANT = new GOTSpawnList(wildingGiant);

		List<GOTSpawnEntry> wildingMilitary = new ArrayList<>();
		wildingMilitary.add(new GOTSpawnEntry(GOTEntityWildlingArcher.class, 5, 1, 2));
		wildingMilitary.add(new GOTSpawnEntry(GOTEntityWildlingWarrior.class, 10, 1, 2));
		WILDING_MILITARY = new GOTSpawnList(wildingMilitary);

		List<GOTSpawnEntry> wildingThenn = new ArrayList<>();
		wildingThenn.add(new GOTSpawnEntry(GOTEntityThennArcher.class, 5, 1, 2));
		wildingThenn.add(new GOTSpawnEntry(GOTEntityThennWarrior.class, 10, 1, 2));
		WILDING_THENN = new GOTSpawnList(wildingThenn);

		List<GOTSpawnEntry> yiTiConquest = new ArrayList<>();
		yiTiConquest.add(new GOTSpawnEntry(GOTEntityYiTiBombardier.class, 2, 1, 1));
		yiTiConquest.add(new GOTSpawnEntry(GOTEntityYiTiSamurai.class, 3, 1, 2));
		yiTiConquest.add(new GOTSpawnEntry(GOTEntityYiTiSamuraiFlamethrower.class, 3, 1, 1));
		yiTiConquest.add(new GOTSpawnEntry(GOTEntityYiTiSoldier.class, 10, 1, 2));
		yiTiConquest.add(new GOTSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5, 1, 1));
		YI_TI_CONQUEST = new GOTSpawnList(yiTiConquest);

		List<GOTSpawnEntry> yiTiMilitary = new ArrayList<>();
		yiTiMilitary.add(new GOTSpawnEntry(GOTEntityYiTiBombardier.class, 2, 1, 1));
		yiTiMilitary.add(new GOTSpawnEntry(GOTEntityYiTiSamurai.class, 3, 1, 2));
		yiTiMilitary.add(new GOTSpawnEntry(GOTEntityYiTiSamuraiFlamethrower.class, 3, 1, 1));
		yiTiMilitary.add(new GOTSpawnEntry(GOTEntityYiTiSoldier.class, 10, 1, 2));
		yiTiMilitary.add(new GOTSpawnEntry(GOTEntityYiTiSoldierCrossbower.class, 5, 1, 1));
		YI_TI_MILITARY = new GOTSpawnList(yiTiMilitary);

		List<GOTSpawnEntry> ulthos = new ArrayList<>();
		ulthos.add(new GOTSpawnEntry(GOTEntityUlthosSpider.class, 10, 1, 2));
		ULTHOS = new GOTSpawnList(ulthos);
	}

	private final List<GOTSpawnEntry> spawnEntries;

	private GOTFaction discoveredFaction;

	@SuppressWarnings("WeakerAccess")
	public GOTSpawnList(List<GOTSpawnEntry> entries) {
		spawnEntries = entries;
	}

	public GOTFaction getListCommonFaction(World world) {
		if (discoveredFaction != null) {
			return discoveredFaction;
		}
		GOTFaction commonFaction = null;
		for (GOTSpawnEntry entry : spawnEntries) {
			Class<? extends GOTEntityNPC> entityClass = entry.entityClass;
			if (GOTEntityNPC.class.isAssignableFrom(entityClass)) {
				try {
					GOTEntityNPC npc = (GOTEntityNPC) GOTReflection.newEntity(entityClass, world);
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
		return (GOTSpawnEntry) WeightedRandom.getRandomItem(rand, spawnEntries);
	}

	public List<GOTSpawnEntry> getSpawnEntries() {
		return spawnEntries;
	}
}