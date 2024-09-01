package got.common.database;

import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.animal.GOTEntityWoolyRhino;
import got.common.entity.animal.GOTEntityZebra;
import got.common.entity.essos.asshai.GOTEntityAsshaiBannerBearer;
import got.common.entity.essos.asshai.GOTEntityAsshaiShadowbinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiSpherebinder;
import got.common.entity.essos.asshai.GOTEntityAsshaiWarrior;
import got.common.entity.essos.braavos.*;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.dothraki.GOTEntityDothrakiArcher;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.golden.GOTEntityGoldenCompanyBannerBearer;
import got.common.entity.essos.golden.GOTEntityGoldenCompanySpearman;
import got.common.entity.essos.golden.GOTEntityGoldenCompanyWarrior;
import got.common.entity.essos.ibben.*;
import got.common.entity.essos.jogos.GOTEntityJogosNhaiArcher;
import got.common.entity.essos.jogos.GOTEntityJogosNhaiMan;
import got.common.entity.essos.lhazar.*;
import got.common.entity.essos.lorath.*;
import got.common.entity.essos.lys.*;
import got.common.entity.essos.myr.*;
import got.common.entity.essos.norvos.*;
import got.common.entity.essos.pentos.*;
import got.common.entity.essos.qarth.*;
import got.common.entity.essos.qohor.*;
import got.common.entity.essos.tyrosh.*;
import got.common.entity.essos.volantis.*;
import got.common.entity.essos.yi_ti.*;
import got.common.entity.other.GOTEntityProstitute;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosBannerBearer;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosBlowgunner;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosFarmhand;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosWarrior;
import got.common.entity.sothoryos.summer.*;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsFarmhand;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsLevyman;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsLevymanArcher;
import got.common.entity.westeros.crownlands.GOTEntityKingsguard;
import got.common.entity.westeros.dorne.*;
import got.common.entity.westeros.dragonstone.*;
import got.common.entity.westeros.gift.GOTEntityGiftBannerBearer;
import got.common.entity.westeros.gift.GOTEntityGiftGuard;
import got.common.entity.westeros.hillmen.*;
import got.common.entity.westeros.ironborn.*;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanArcher;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanAxeThrower;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanBannerBearer;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanWarrior;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennAxeThrower;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBannerBearer;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennWarrior;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GOTUnitTradeEntries {
	public static final Collection<GOTUnitTradeEntries> CONTENT = new ArrayList<>();

	public static final int LEVYMAN = 5;
	public static final int LEVYMANA = 10;
	public static final int SOLDIER = 10;
	public static final int SOLDIERA = 15;
	public static final int SOLDIERH = 15;
	public static final int SOLDIERHA = 20;

	public static final float LEVYMAN_F = 5.0f;
	public static final float LEVYMANA_F = 5.0f;
	public static final float SOLDIER_F = 10.0f;
	public static final float SOLDIERA_F = 10.0f;
	public static final float SOLDIERH_F = 15.0f;
	public static final float SOLDIERHA_F = 15.0f;

	public static final int SLAVE = 10;
	public static final float SLAVE_F = 0.0f;

	public static final GOTUnitTradeEntries ARRYN;
	public static final GOTUnitTradeEntries ASSHAI;
	public static final GOTUnitTradeEntries BRAAVOS;
	public static final GOTUnitTradeEntries CROWNLANDS;
	public static final GOTUnitTradeEntries DORNE;
	public static final GOTUnitTradeEntries DOTHRAKI;
	public static final GOTUnitTradeEntries DRAGONSTONE;
	public static final GOTUnitTradeEntries GHISCAR;
	public static final GOTUnitTradeEntries GHISCAR_UNSULLIED;
	public static final GOTUnitTradeEntries GIFT;
	public static final GOTUnitTradeEntries GOLDEN_COMPANY;
	public static final GOTUnitTradeEntries HILLMEN;
	public static final GOTUnitTradeEntries IBBEN;
	public static final GOTUnitTradeEntries IRONBORN;
	public static final GOTUnitTradeEntries JOGOS_NHAI;
	public static final GOTUnitTradeEntries KINGSGUARD;
	public static final GOTUnitTradeEntries LHAZAR;
	public static final GOTUnitTradeEntries LORATH;
	public static final GOTUnitTradeEntries LYS;
	public static final GOTUnitTradeEntries MYR;
	public static final GOTUnitTradeEntries NORTH;
	public static final GOTUnitTradeEntries NORTH_HILLMEN;
	public static final GOTUnitTradeEntries NORVOS;
	public static final GOTUnitTradeEntries PENTOS;
	public static final GOTUnitTradeEntries QARTH;
	public static final GOTUnitTradeEntries QOHOR;
	public static final GOTUnitTradeEntries REACH;
	public static final GOTUnitTradeEntries RIVERLANDS;
	public static final GOTUnitTradeEntries SOTHORYOS;
	public static final GOTUnitTradeEntries STORMLANDS;
	public static final GOTUnitTradeEntries SUMMER;
	public static final GOTUnitTradeEntries THENN;
	public static final GOTUnitTradeEntries TYROSH;
	public static final GOTUnitTradeEntries VOLANTIS;
	public static final GOTUnitTradeEntries WESTERLANDS;
	public static final GOTUnitTradeEntries WILDLING;
	public static final GOTUnitTradeEntries YI_TI;

	public static final GOTUnitTradeEntries ARRYN_FARMER;
	public static final GOTUnitTradeEntries BRAAVOS_FARMER;
	public static final GOTUnitTradeEntries CROWNLANDS_FARMER;
	public static final GOTUnitTradeEntries DORNE_FARMER;
	public static final GOTUnitTradeEntries DRAGONSTONE_FARMER;
	public static final GOTUnitTradeEntries GHISCAR_SLAVER;
	public static final GOTUnitTradeEntries IBBEN_FARMER;
	public static final GOTUnitTradeEntries IRONBORN_FARMER;
	public static final GOTUnitTradeEntries LHAZAR_FARMER;
	public static final GOTUnitTradeEntries LORATH_FARMER;
	public static final GOTUnitTradeEntries LYS_SLAVER;
	public static final GOTUnitTradeEntries MYR_SLAVER;
	public static final GOTUnitTradeEntries NORTH_FARMER;
	public static final GOTUnitTradeEntries NORVOS_FARMER;
	public static final GOTUnitTradeEntries PENTOS_FARMER;
	public static final GOTUnitTradeEntries QARTH_FARMER;
	public static final GOTUnitTradeEntries QOHOR_FARMER;
	public static final GOTUnitTradeEntries REACH_FARMER;
	public static final GOTUnitTradeEntries RIVERLANDS_FARMER;
	public static final GOTUnitTradeEntries SOTHORYOS_FARMER;
	public static final GOTUnitTradeEntries STORMLANDS_FARMER;
	public static final GOTUnitTradeEntries SUMMER_FARMER;
	public static final GOTUnitTradeEntries TYROSH_SLAVER;
	public static final GOTUnitTradeEntries VOLANTIS_SLAVER;
	public static final GOTUnitTradeEntries WESTERLANDS_FARMER;
	public static final GOTUnitTradeEntries YI_TI_FARMER;

	public static final GOTUnitTradeEntries PROSTITUTE_KEEPER;

	static {
		List<GOTUnitTradeEntry> arryn = new ArrayList<>();
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynLevyman.class, LEVYMAN, LEVYMAN_F));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynSoldier.class, SOLDIER, SOLDIER_F));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynBannerBearer.class, SOLDIER, SOLDIER_F));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		arryn.add(new GOTUnitTradeEntry(GOTEntityArrynBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		ARRYN = new GOTUnitTradeEntries(50.0f, arryn);

		List<GOTUnitTradeEntry> braavos = new ArrayList<>();
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosLevyman.class, LEVYMAN, LEVYMAN_F));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosSoldier.class, SOLDIER, SOLDIER_F));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosBannerBearer.class, SOLDIER, SOLDIER_F));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		braavos.add(new GOTUnitTradeEntry(GOTEntityBraavosBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		BRAAVOS = new GOTUnitTradeEntries(50.0f, braavos);

		List<GOTUnitTradeEntry> dorne = new ArrayList<>();
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneLevyman.class, LEVYMAN, LEVYMAN_F));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneSoldier.class, SOLDIER, SOLDIER_F));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneBannerBearer.class, SOLDIER, SOLDIER_F));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		dorne.add(new GOTUnitTradeEntry(GOTEntityDorneBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		DORNE = new GOTUnitTradeEntries(50.0f, dorne);

		List<GOTUnitTradeEntry> dragonstone = new ArrayList<>();
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneLevyman.class, LEVYMAN, LEVYMAN_F));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldier.class, SOLDIER, SOLDIER_F));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneBannerBearer.class, SOLDIER, SOLDIER_F));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		dragonstone.add(new GOTUnitTradeEntry(GOTEntityDragonstoneBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		DRAGONSTONE = new GOTUnitTradeEntries(50.0f, dragonstone);

		List<GOTUnitTradeEntry> ghiscar = new ArrayList<>();
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarLevyman.class, LEVYMAN, LEVYMAN_F));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarSoldier.class, SOLDIER, SOLDIER_F));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarBannerBearer.class, SOLDIER, SOLDIER_F));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		ghiscar.add(new GOTUnitTradeEntry(GOTEntityGhiscarBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		GHISCAR = new GOTUnitTradeEntries(50.0f, ghiscar);

		List<GOTUnitTradeEntry> ironborn = new ArrayList<>();
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornLevyman.class, LEVYMAN, LEVYMAN_F));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornSoldier.class, SOLDIER, SOLDIER_F));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornBannerBearer.class, SOLDIER, SOLDIER_F));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		ironborn.add(new GOTUnitTradeEntry(GOTEntityIronbornBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		IRONBORN = new GOTUnitTradeEntries(50.0f, ironborn);

		List<GOTUnitTradeEntry> lorath = new ArrayList<>();
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathLevyman.class, LEVYMAN, LEVYMAN_F));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathSoldier.class, SOLDIER, SOLDIER_F));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathBannerBearer.class, SOLDIER, SOLDIER_F));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		lorath.add(new GOTUnitTradeEntry(GOTEntityLorathBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		LORATH = new GOTUnitTradeEntries(50.0f, lorath);

		List<GOTUnitTradeEntry> lys = new ArrayList<>();
		lys.add(new GOTUnitTradeEntry(GOTEntityLysLevyman.class, LEVYMAN, LEVYMAN_F));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysSoldier.class, SOLDIER, SOLDIER_F));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysBannerBearer.class, SOLDIER, SOLDIER_F));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		lys.add(new GOTUnitTradeEntry(GOTEntityLysBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		LYS = new GOTUnitTradeEntries(50.0f, lys);

		List<GOTUnitTradeEntry> myr = new ArrayList<>();
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrLevyman.class, LEVYMAN, LEVYMAN_F));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrSoldier.class, SOLDIER, SOLDIER_F));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrBannerBearer.class, SOLDIER, SOLDIER_F));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		myr.add(new GOTUnitTradeEntry(GOTEntityMyrBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		MYR = new GOTUnitTradeEntries(50.0f, myr);

		List<GOTUnitTradeEntry> north = new ArrayList<>();
		north.add(new GOTUnitTradeEntry(GOTEntityNorthLevyman.class, LEVYMAN, LEVYMAN_F));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthSoldier.class, SOLDIER, SOLDIER_F));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthBannerBearer.class, SOLDIER, SOLDIER_F));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		north.add(new GOTUnitTradeEntry(GOTEntityNorthBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		NORTH = new GOTUnitTradeEntries(50.0f, north);

		List<GOTUnitTradeEntry> norvos = new ArrayList<>();
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosLevyman.class, LEVYMAN, LEVYMAN_F));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosSoldier.class, SOLDIER, SOLDIER_F));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosBannerBearer.class, SOLDIER, SOLDIER_F));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		norvos.add(new GOTUnitTradeEntry(GOTEntityNorvosBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		NORVOS = new GOTUnitTradeEntries(50.0f, norvos);

		List<GOTUnitTradeEntry> pentos = new ArrayList<>();
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosLevyman.class, LEVYMAN, LEVYMAN_F));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosSoldier.class, SOLDIER, SOLDIER_F));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosBannerBearer.class, SOLDIER, SOLDIER_F));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		pentos.add(new GOTUnitTradeEntry(GOTEntityPentosBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		PENTOS = new GOTUnitTradeEntries(50.0f, pentos);

		List<GOTUnitTradeEntry> qarth = new ArrayList<>();
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthLevyman.class, LEVYMAN, LEVYMAN_F));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthSoldier.class, SOLDIER, SOLDIER_F));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthBannerBearer.class, SOLDIER, SOLDIER_F));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		qarth.add(new GOTUnitTradeEntry(GOTEntityQarthBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		QARTH = new GOTUnitTradeEntries(50.0f, qarth);

		List<GOTUnitTradeEntry> qohor = new ArrayList<>();
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorLevyman.class, LEVYMAN, LEVYMAN_F));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorSoldier.class, SOLDIER, SOLDIER_F));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorBannerBearer.class, SOLDIER, SOLDIER_F));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorUnsullied.class, SOLDIER * 3, SOLDIER_F * 3));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		qohor.add(new GOTUnitTradeEntry(GOTEntityQohorBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		QOHOR = new GOTUnitTradeEntries(50.0f, qohor);

		List<GOTUnitTradeEntry> reach = new ArrayList<>();
		reach.add(new GOTUnitTradeEntry(GOTEntityReachLevyman.class, LEVYMAN, LEVYMAN_F));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachSoldier.class, SOLDIER, SOLDIER_F));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachBannerBearer.class, SOLDIER, SOLDIER_F));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		reach.add(new GOTUnitTradeEntry(GOTEntityReachBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		REACH = new GOTUnitTradeEntries(50.0f, reach);

		List<GOTUnitTradeEntry> riverlands = new ArrayList<>();
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldier.class, SOLDIER, SOLDIER_F));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		riverlands.add(new GOTUnitTradeEntry(GOTEntityRiverlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		RIVERLANDS = new GOTUnitTradeEntries(50.0f, riverlands);

		List<GOTUnitTradeEntry> stormlands = new ArrayList<>();
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldier.class, SOLDIER, SOLDIER_F));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		stormlands.add(new GOTUnitTradeEntry(GOTEntityStormlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		STORMLANDS = new GOTUnitTradeEntries(50.0f, stormlands);

		List<GOTUnitTradeEntry> tyrosh = new ArrayList<>();
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshLevyman.class, LEVYMAN, LEVYMAN_F));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshSoldier.class, SOLDIER, SOLDIER_F));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshBannerBearer.class, SOLDIER, SOLDIER_F));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		tyrosh.add(new GOTUnitTradeEntry(GOTEntityTyroshBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		TYROSH = new GOTUnitTradeEntries(50.0f, tyrosh);

		List<GOTUnitTradeEntry> volantis = new ArrayList<>();
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisLevyman.class, LEVYMAN, LEVYMAN_F));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisSoldier.class, SOLDIER, SOLDIER_F));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisBannerBearer.class, SOLDIER, SOLDIER_F));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		volantis.add(new GOTUnitTradeEntry(GOTEntityVolantisBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		VOLANTIS = new GOTUnitTradeEntries(50.0f, volantis);

		List<GOTUnitTradeEntry> westerlands = new ArrayList<>();
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldier.class, SOLDIER, SOLDIER_F));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		westerlands.add(new GOTUnitTradeEntry(GOTEntityWesterlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		WESTERLANDS = new GOTUnitTradeEntries(50.0f, westerlands);

		List<GOTUnitTradeEntry> lhazar = new ArrayList<>();
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarLevyman.class, LEVYMAN, LEVYMAN_F));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarSoldier.class, SOLDIER, SOLDIER_F));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarBannerBearer.class, SOLDIER, SOLDIER_F));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		lhazar.add(new GOTUnitTradeEntry(GOTEntityLhazarBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		LHAZAR = new GOTUnitTradeEntries(50.0f, lhazar);

		List<GOTUnitTradeEntry> summer = new ArrayList<>();
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerLevyman.class, LEVYMAN, LEVYMAN_F));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerSoldier.class, SOLDIER, SOLDIER_F));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerBannerBearer.class, SOLDIER, SOLDIER_F));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		summer.add(new GOTUnitTradeEntry(GOTEntitySummerBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		SUMMER = new GOTUnitTradeEntries(50.0f, summer);

		List<GOTUnitTradeEntry> ghiscarUnsullied = new ArrayList<>();
		ghiscarUnsullied.add(new GOTUnitTradeEntry(GOTEntityGhiscarUnsullied.class, SOLDIER * 3, SOLDIER_F * 3));
		GHISCAR_UNSULLIED = new GOTUnitTradeEntries(50.0f, ghiscarUnsullied);

		List<GOTUnitTradeEntry> gift = new ArrayList<>();
		gift.add(new GOTUnitTradeEntry(GOTEntityGiftGuard.class, SOLDIER, SOLDIER_F).setPledgeExclusive());
		gift.add(new GOTUnitTradeEntry(GOTEntityGiftBannerBearer.class, SOLDIER, SOLDIER_F).setPledgeExclusive());
		GIFT = new GOTUnitTradeEntries(50.0f, gift);

		List<GOTUnitTradeEntry> crownlands = new ArrayList<>();
		crownlands.add(new GOTUnitTradeEntry(GOTEntityCrownlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		crownlands.add(new GOTUnitTradeEntry(GOTEntityCrownlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		CROWNLANDS = new GOTUnitTradeEntries(50.0f, crownlands);

		List<GOTUnitTradeEntry> kingsguard = new ArrayList<>();
		kingsguard.add(new GOTUnitTradeEntry(GOTEntityKingsguard.class, SOLDIER * 2, SOLDIER_F * 2).setPledgeExclusive());
		KINGSGUARD = new GOTUnitTradeEntries(50.0f, kingsguard);

		List<GOTUnitTradeEntry> asshai = new ArrayList<>();
		asshai.add(new GOTUnitTradeEntry(GOTEntityAsshaiWarrior.class, SOLDIER, SOLDIER_F));
		asshai.add(new GOTUnitTradeEntry(GOTEntityAsshaiSpherebinder.class, SOLDIER * 5, SOLDIER_F * 5).setPledgeExclusive());
		asshai.add(new GOTUnitTradeEntry(GOTEntityAsshaiShadowbinder.class, SOLDIER * 5, SOLDIER_F * 5).setPledgeExclusive());
		asshai.add(new GOTUnitTradeEntry(GOTEntityAsshaiBannerBearer.class, SOLDIER, SOLDIER_F));
		ASSHAI = new GOTUnitTradeEntries(50.0f, asshai);

		List<GOTUnitTradeEntry> wildling = new ArrayList<>();
		wildling.add(new GOTUnitTradeEntry(GOTEntityWildlingWarrior.class, LEVYMAN, LEVYMAN_F));
		wildling.add(new GOTUnitTradeEntry(GOTEntityWildlingArcher.class, LEVYMANA, LEVYMANA_F));
		wildling.add(new GOTUnitTradeEntry(GOTEntityWildlingAxeThrower.class, LEVYMANA, LEVYMANA_F));
		wildling.add(new GOTUnitTradeEntry(GOTEntityWildlingBannerBearer.class, LEVYMAN, LEVYMAN_F));
		wildling.add(new GOTUnitTradeEntry(GOTEntityGiant.class, LEVYMAN * 10, LEVYMAN_F * 10).setPledgeExclusive());
		WILDLING = new GOTUnitTradeEntries(50.0f, wildling);

		List<GOTUnitTradeEntry> thenn = new ArrayList<>();
		thenn.add(new GOTUnitTradeEntry(GOTEntityThennWarrior.class, LEVYMAN, LEVYMAN_F));
		thenn.add(new GOTUnitTradeEntry(GOTEntityThennArcher.class, LEVYMANA, LEVYMANA_F));
		thenn.add(new GOTUnitTradeEntry(GOTEntityThennAxeThrower.class, LEVYMANA, LEVYMANA_F));
		thenn.add(new GOTUnitTradeEntry(GOTEntityThennBannerBearer.class, LEVYMAN, LEVYMAN_F));
		thenn.add(new GOTUnitTradeEntry(GOTEntityGiant.class, LEVYMAN * 10, LEVYMAN_F * 10).setPledgeExclusive());
		THENN = new GOTUnitTradeEntries(50.0f, thenn);

		List<GOTUnitTradeEntry> hillmen = new ArrayList<>();
		hillmen.add(new GOTUnitTradeEntry(GOTEntityHillmanWarrior.class, SOLDIER, SOLDIER_F));
		hillmen.add(new GOTUnitTradeEntry(GOTEntityHillmanArcher.class, SOLDIERA, SOLDIERA_F));
		hillmen.add(new GOTUnitTradeEntry(GOTEntityHillmanAxeThrower.class, SOLDIERA, SOLDIERA_F));
		hillmen.add(new GOTUnitTradeEntry(GOTEntityHillmanBannerBearer.class, SOLDIER, SOLDIER_F));
		hillmen.add(new GOTUnitTradeEntry(GOTEntityHillmanBerserker.class, SOLDIER * 2, SOLDIER_F * 2).setPledgeExclusive());
		HILLMEN = new GOTUnitTradeEntries(50.0f, hillmen);

		List<GOTUnitTradeEntry> northHillmen = new ArrayList<>();
		northHillmen.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanWarrior.class, LEVYMAN, LEVYMAN_F));
		northHillmen.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanArcher.class, LEVYMANA, LEVYMANA_F));
		northHillmen.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanAxeThrower.class, LEVYMANA, LEVYMANA_F));
		northHillmen.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanBannerBearer.class, LEVYMAN, LEVYMAN_F));
		northHillmen.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanWarrior.class, GOTEntityWoolyRhino.class, "Rider", LEVYMAN * 5, LEVYMAN_F * 5).setPledgeExclusive());
		NORTH_HILLMEN = new GOTUnitTradeEntries(50.0f, northHillmen);

		List<GOTUnitTradeEntry> jogosNhai = new ArrayList<>();
		jogosNhai.add(new GOTUnitTradeEntry(GOTEntityJogosNhaiMan.class, SOLDIER, SOLDIER_F));
		jogosNhai.add(new GOTUnitTradeEntry(GOTEntityJogosNhaiArcher.class, SOLDIERA, SOLDIERA_F));
		jogosNhai.add(new GOTUnitTradeEntry(GOTEntityJogosNhaiMan.class, GOTEntityZebra.class, "Rider", SOLDIERH, SOLDIERH_F));
		jogosNhai.add(new GOTUnitTradeEntry(GOTEntityJogosNhaiArcher.class, GOTEntityZebra.class, "Rider", SOLDIERHA, SOLDIERHA_F));
		JOGOS_NHAI = new GOTUnitTradeEntries(50.0f, jogosNhai);

		List<GOTUnitTradeEntry> dothraki = new ArrayList<>();
		dothraki.add(new GOTUnitTradeEntry(GOTEntityDothraki.class, LEVYMAN, LEVYMAN_F));
		dothraki.add(new GOTUnitTradeEntry(GOTEntityDothrakiArcher.class, LEVYMANA, LEVYMANA_F));
		dothraki.add(new GOTUnitTradeEntry(GOTEntityDothraki.class, GOTEntityHorse.class, "Rider", SOLDIER, SOLDIER_F));
		dothraki.add(new GOTUnitTradeEntry(GOTEntityDothrakiArcher.class, GOTEntityHorse.class, "Rider", SOLDIERA, SOLDIERA_F));
		DOTHRAKI = new GOTUnitTradeEntries(50.0f, dothraki);

		List<GOTUnitTradeEntry> ibben = new ArrayList<>();
		ibben.add(new GOTUnitTradeEntry(GOTEntityIbbenLevyman.class, LEVYMAN, LEVYMAN_F));
		ibben.add(new GOTUnitTradeEntry(GOTEntityIbbenLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		ibben.add(new GOTUnitTradeEntry(GOTEntityIbbenSoldier.class, SOLDIER, SOLDIER_F));
		ibben.add(new GOTUnitTradeEntry(GOTEntityIbbenSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		ibben.add(new GOTUnitTradeEntry(GOTEntityIbbenBannerBearer.class, SOLDIER, SOLDIER_F));
		IBBEN = new GOTUnitTradeEntries(50.0f, ibben);

		List<GOTUnitTradeEntry> sothoryos = new ArrayList<>();
		sothoryos.add(new GOTUnitTradeEntry(GOTEntitySothoryosWarrior.class, SOLDIER, SOLDIER_F));
		sothoryos.add(new GOTUnitTradeEntry(GOTEntitySothoryosBlowgunner.class, SOLDIERA, SOLDIERA_F));
		sothoryos.add(new GOTUnitTradeEntry(GOTEntitySothoryosBannerBearer.class, SOLDIER, SOLDIER_F));
		SOTHORYOS = new GOTUnitTradeEntries(50.0f, sothoryos);

		List<GOTUnitTradeEntry> yiTi = new ArrayList<>();
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiLevyman.class, LEVYMAN, LEVYMAN_F));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiLevymanCrossbower.class, LEVYMANA, LEVYMANA_F));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiSoldier.class, SOLDIER, SOLDIER_F));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiSoldierCrossbower.class, SOLDIERA, SOLDIERA_F));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiSamurai.class, SOLDIER * 2, SOLDIER_F * 2).setPledgeExclusive());
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiSamuraiFlamethrower.class, SOLDIERA * 2, SOLDIERA_F * 2).setPledgeExclusive());
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiBombardier.class, SOLDIER * 5, SOLDIER_F * 5).setPledgeExclusive());
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiBannerBearer.class, SOLDIER, SOLDIER_F));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		yiTi.add(new GOTUnitTradeEntry(GOTEntityYiTiBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTItems.ironHorseArmor));
		YI_TI = new GOTUnitTradeEntries(50.0f, yiTi);

		List<GOTUnitTradeEntry> goldenCompany = new ArrayList<>();
		goldenCompany.add(new GOTUnitTradeEntry(GOTEntityGoldenCompanyWarrior.class, SOLDIER, 0.0f));
		goldenCompany.add(new GOTUnitTradeEntry(GOTEntityGoldenCompanySpearman.class, SOLDIER, 0.0f));
		goldenCompany.add(new GOTUnitTradeEntry(GOTEntityGoldenCompanyBannerBearer.class, SOLDIER, 0.0f));
		GOLDEN_COMPANY = new GOTUnitTradeEntries(0.0f, goldenCompany);

		List<GOTUnitTradeEntry> arrynFarmer = new ArrayList<>();
		arrynFarmer.add(new GOTUnitTradeEntry(GOTEntityArrynFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		ARRYN_FARMER = new GOTUnitTradeEntries(0.0f, arrynFarmer);

		List<GOTUnitTradeEntry> crownlandsFarmer = new ArrayList<>();
		crownlandsFarmer.add(new GOTUnitTradeEntry(GOTEntityCrownlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		CROWNLANDS_FARMER = new GOTUnitTradeEntries(0.0f, crownlandsFarmer);

		List<GOTUnitTradeEntry> dorneFarmer = new ArrayList<>();
		dorneFarmer.add(new GOTUnitTradeEntry(GOTEntityDorneFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		DORNE_FARMER = new GOTUnitTradeEntries(0.0f, dorneFarmer);

		List<GOTUnitTradeEntry> dragonstoneFarmer = new ArrayList<>();
		dragonstoneFarmer.add(new GOTUnitTradeEntry(GOTEntityDragonstoneFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		DRAGONSTONE_FARMER = new GOTUnitTradeEntries(0.0f, dragonstoneFarmer);

		List<GOTUnitTradeEntry> ibbenFarmer = new ArrayList<>();
		ibbenFarmer.add(new GOTUnitTradeEntry(GOTEntityIbbenFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		IBBEN_FARMER = new GOTUnitTradeEntries(0.0f, ibbenFarmer);

		List<GOTUnitTradeEntry> ironbornFarmer = new ArrayList<>();
		ironbornFarmer.add(new GOTUnitTradeEntry(GOTEntityIronbornFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		IRONBORN_FARMER = new GOTUnitTradeEntries(0.0f, ironbornFarmer);

		List<GOTUnitTradeEntry> northFarmer = new ArrayList<>();
		northFarmer.add(new GOTUnitTradeEntry(GOTEntityNorthFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		NORTH_FARMER = new GOTUnitTradeEntries(0.0f, northFarmer);

		List<GOTUnitTradeEntry> reachFarmer = new ArrayList<>();
		reachFarmer.add(new GOTUnitTradeEntry(GOTEntityReachFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		REACH_FARMER = new GOTUnitTradeEntries(0.0f, reachFarmer);

		List<GOTUnitTradeEntry> riverlandsFarmer = new ArrayList<>();
		riverlandsFarmer.add(new GOTUnitTradeEntry(GOTEntityRiverlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		RIVERLANDS_FARMER = new GOTUnitTradeEntries(0.0f, riverlandsFarmer);

		List<GOTUnitTradeEntry> sothoryosFarmer = new ArrayList<>();
		sothoryosFarmer.add(new GOTUnitTradeEntry(GOTEntitySothoryosFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		SOTHORYOS_FARMER = new GOTUnitTradeEntries(0.0f, sothoryosFarmer);

		List<GOTUnitTradeEntry> stormlandsFarmer = new ArrayList<>();
		stormlandsFarmer.add(new GOTUnitTradeEntry(GOTEntityStormlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		STORMLANDS_FARMER = new GOTUnitTradeEntries(0.0f, stormlandsFarmer);

		List<GOTUnitTradeEntry> summerFarmer = new ArrayList<>();
		summerFarmer.add(new GOTUnitTradeEntry(GOTEntitySummerFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		SUMMER_FARMER = new GOTUnitTradeEntries(0.0f, summerFarmer);

		List<GOTUnitTradeEntry> westerlandsFarmer = new ArrayList<>();
		westerlandsFarmer.add(new GOTUnitTradeEntry(GOTEntityWesterlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		WESTERLANDS_FARMER = new GOTUnitTradeEntries(0.0f, westerlandsFarmer);

		List<GOTUnitTradeEntry> lorathFarmer = new ArrayList<>();
		lorathFarmer.add(new GOTUnitTradeEntry(GOTEntityLorathFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		LORATH_FARMER = new GOTUnitTradeEntries(0.0f, lorathFarmer);

		List<GOTUnitTradeEntry> braavosFarmer = new ArrayList<>();
		braavosFarmer.add(new GOTUnitTradeEntry(GOTEntityBraavosFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		BRAAVOS_FARMER = new GOTUnitTradeEntries(0.0f, braavosFarmer);

		List<GOTUnitTradeEntry> norvosFarmer = new ArrayList<>();
		norvosFarmer.add(new GOTUnitTradeEntry(GOTEntityNorvosFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		NORVOS_FARMER = new GOTUnitTradeEntries(0.0f, norvosFarmer);

		List<GOTUnitTradeEntry> pentosFarmer = new ArrayList<>();
		pentosFarmer.add(new GOTUnitTradeEntry(GOTEntityPentosFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		PENTOS_FARMER = new GOTUnitTradeEntries(0.0f, pentosFarmer);

		List<GOTUnitTradeEntry> qohorFarmer = new ArrayList<>();
		qohorFarmer.add(new GOTUnitTradeEntry(GOTEntityQohorFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		QOHOR_FARMER = new GOTUnitTradeEntries(0.0f, qohorFarmer);

		List<GOTUnitTradeEntry> qarthFarmer = new ArrayList<>();
		qarthFarmer.add(new GOTUnitTradeEntry(GOTEntityQarthFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		QARTH_FARMER = new GOTUnitTradeEntries(0.0f, qarthFarmer);

		List<GOTUnitTradeEntry> yiTiFarmer = new ArrayList<>();
		yiTiFarmer.add(new GOTUnitTradeEntry(GOTEntityYiTiFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		YI_TI_FARMER = new GOTUnitTradeEntries(0.0f, yiTiFarmer);

		List<GOTUnitTradeEntry> lhazarFarmer = new ArrayList<>();
		lhazarFarmer.add(new GOTUnitTradeEntry(GOTEntityLhazarFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		LHAZAR_FARMER = new GOTUnitTradeEntries(0.0f, lhazarFarmer);

		List<GOTUnitTradeEntry> ghiscarSlaver = new ArrayList<>();
		ghiscarSlaver.add(new GOTUnitTradeEntry(GOTEntityGhiscarSlave.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		GHISCAR_SLAVER = new GOTUnitTradeEntries(0.0f, ghiscarSlaver);

		List<GOTUnitTradeEntry> lysSlaver = new ArrayList<>();
		lysSlaver.add(new GOTUnitTradeEntry(GOTEntityLysSlave.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		LYS_SLAVER = new GOTUnitTradeEntries(0.0f, lysSlaver);

		List<GOTUnitTradeEntry> myrSlaver = new ArrayList<>();
		myrSlaver.add(new GOTUnitTradeEntry(GOTEntityMyrSlave.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		MYR_SLAVER = new GOTUnitTradeEntries(0.0f, myrSlaver);

		List<GOTUnitTradeEntry> tyroshSlaver = new ArrayList<>();
		tyroshSlaver.add(new GOTUnitTradeEntry(GOTEntityTyroshSlave.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		TYROSH_SLAVER = new GOTUnitTradeEntries(0.0f, tyroshSlaver);

		List<GOTUnitTradeEntry> volantisSlaver = new ArrayList<>();
		volantisSlaver.add(new GOTUnitTradeEntry(GOTEntityVolantisSlave.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.FARMER));
		VOLANTIS_SLAVER = new GOTUnitTradeEntries(0.0f, volantisSlaver);

		List<GOTUnitTradeEntry> prostituteKeeper = new ArrayList<>();
		prostituteKeeper.add(new GOTUnitTradeEntry(GOTEntityProstitute.class, SLAVE, SLAVE_F).setTask(GOTHireableInfo.Task.PROSTITUTE));
		PROSTITUTE_KEEPER = new GOTUnitTradeEntries(0.0f, prostituteKeeper);
	}

	private final GOTUnitTradeEntry[] tradeEntries;

	@SuppressWarnings("WeakerAccess")
	public GOTUnitTradeEntries(float baseReputation, List<GOTUnitTradeEntry> list) {
		GOTUnitTradeEntry[] arr = new GOTUnitTradeEntry[list.size()];
		arr = list.toArray(arr);
		for (GOTUnitTradeEntry trade : tradeEntries = arr) {
			trade.setReputationRequired(trade.getReputationRequired() + baseReputation);
			if (trade.getReputationRequired() >= 0.0f) {
				continue;
			}
			throw new IllegalArgumentException("Units cannot require negative reputation!");
		}
		CONTENT.add(this);
	}

	public GOTUnitTradeEntry[] getTradeEntries() {
		return tradeEntries;
	}
}