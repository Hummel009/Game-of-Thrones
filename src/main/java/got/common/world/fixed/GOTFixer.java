package got.common.world.fixed;

import java.util.*;

import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.*;
import got.common.entity.essos.legendary.trader.*;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.other.GOTEntityHummel009;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.*;
import got.common.world.map.*;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiCity;
import got.common.world.structure.essos.braavos.GOTStructureBraavosCity;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiVillage;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarCity;
import got.common.world.structure.essos.ibben.GOTStructureIbbenVillage;
import got.common.world.structure.essos.jogos.GOTStructureJogosVillage;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarVillage;
import got.common.world.structure.essos.lorath.GOTStructureLorathCity;
import got.common.world.structure.essos.lys.GOTStructureLysCity;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyVillage;
import got.common.world.structure.essos.myr.GOTStructureMyrCity;
import got.common.world.structure.essos.norvos.GOTStructureNorvosCity;
import got.common.world.structure.essos.pentos.GOTStructurePentosCity;
import got.common.world.structure.essos.qarth.GOTStructureQarthCity;
import got.common.world.structure.essos.qohor.GOTStructureQohorCity;
import got.common.world.structure.essos.tyrosh.GOTStructureTyroshCity;
import got.common.world.structure.essos.volantis.GOTStructureVolantisCity;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.summer.GOTStructureSummerVillage;
import got.common.world.structure.westeros.arryn.GOTStructureArrynCity;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsCity;
import got.common.world.structure.westeros.dorne.GOTStructureDorneCity;
import got.common.world.structure.westeros.dragonstone.GOTStructureDragonstoneCity;
import got.common.world.structure.westeros.gift.GOTStructureGiftVillage;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornCity;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;
import got.common.world.structure.westeros.north.hillmen.GOTStructureNorthHillmanVillage;
import got.common.world.structure.westeros.reach.GOTStructureReachCity;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsCity;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsCity;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;
import net.minecraft.world.World;

public class GOTFixer {
	public static Map<GOTWaypoint, GOTStructureBase> structures = new EnumMap<>(GOTWaypoint.class);
	public static GOTVillageGen[] f = new GOTVillageGen[69];

	public static void addSpecialLocations(World world, Random random, int i, int k) {
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts1).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts2).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts3).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts4).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts5).generate(world, random, i, 0, k, 0);
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteWood) || (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Winterfell))) {
			((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions().generate(world, random, i + 50, world.getTopSolidOrLiquidBlock(i + 50, k), k);
		}
	}

	public static void affixWaypointLocations(GOTBiome biome) {
		f[0] = new GOTStructureNorthHillmanVillage(biome, 0.0f);
		f[1] = new GOTStructureMyrCity(biome, 0.0f).setIsTown();
		f[2] = new GOTStructureRuinsBig(biome, 0.0f);
		f[3] = new GOTStructureGhiscarCity(biome, 0.0f).setIsColony();
		f[4] = new GOTStructureSummerVillage(biome, 0.0f);
		f[5] = new GOTStructureGhiscarCity(biome, 0.0f).setIsTown();
		f[6] = new GOTStructureGhiscarPyramid1(biome, 0.0f);
		f[7] = new GOTStructureBraavosCity(biome, 0.0f).setIsTown();
		f[8] = new GOTStructureDothrakiVillage(biome, 0.0f).setIsBig();
		f[9] = new GOTStructureIbbenVillage(biome, 0.0f);
		f[10] = new GOTStructureFiveFortsTower(biome, 0.0f);
		f[11] = new GOTStructureYiTiCity(biome, 0.0f).setIsTown();
		f[12] = new GOTStructureVolantisCity(biome, 0.0f).setIsTown();
		f[13] = new GOTStructureTyroshCity(biome, 0.0f).setIsTown();
		f[14] = new GOTStructureJogosVillage(biome, 0.0f).setIsBig();
		f[15] = new GOTStructureLhazarVillage(biome, 0.0f).setIsTown();
		f[16] = new GOTStructureLorathCity(biome, 0.0f).setIsTown();
		f[17] = new GOTStructureLysCity(biome, 0.0f).setIsTown();
		f[18] = new GOTStructureGhiscarPyramid2(biome, 0.0f);
		f[19] = new GOTStructureNorvosCity(biome, 0.0f).setIsTown();
		f[20] = new GOTStructurePentosCity(biome, 0.0f).setIsTown();
		f[21] = new GOTStructureQarthCity(biome, 0.0f).setIsTown();
		f[22] = new GOTStructureQohorCity(biome, 0.0f).setIsTown();
		f[23] = new GOTStructureAsshaiCity(biome, 0.0f);
		f[24] = new GOTStructureMossovyVillage(biome, 0.0f);
		f[25] = new GOTStructureMossovyRampart(biome, 0.0f);
		f[26] = new GOTStructureArrynCity(biome, 0.0f).setIsCastle();
		f[27] = new GOTStructureArrynCity(biome, 0.0f).setIsTown();
		f[28] = new GOTStructureArrynCity(biome, 0.0f).setIsTown();
		f[29] = new GOTStructureCrownlandsCity(biome, 0.0f);
		f[30] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsCastle();
		f[31] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsTown();
		f[32] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsCapital();
		f[33] = new GOTStructureRedCastle(biome, 0.0f);
		f[34] = new GOTStructureDorneCity(biome, 0.0f).setIsCastle();
		f[35] = new GOTStructureDorneCity(biome, 0.0f).setIsTown();
		f[36] = new GOTStructureDragonstoneCity(biome, 0.0f).setIsCastle();
		f[37] = new GOTStructureDragonstoneCity(biome, 0.0f).setIsTown();
		f[38] = new GOTStructureHardhome(biome, 0.0f);
		f[39] = new GOTStructureGiftVillage(biome, 0.0f);
		f[40] = new GOTStructureReachCity(biome, 0.0f).setIsCastle();
		f[41] = new GOTStructureWesterlandsCity(biome, 0.0f).setIsTown();
		f[42] = new GOTStructureWesterlandsCity(biome, 0.0f);
		f[43] = new GOTStructureWesterlandsCity(biome, 0.0f).setIsCastle();
		f[44] = new GOTStructureStormlandsCity(biome, 0.0f).setIsCastle();
		f[45] = new GOTStructureStormlandsCity(biome, 0.0f).setIsTown();
		f[46] = new GOTStructureRiverlandsCity(biome, 0.0f);
		f[47] = new GOTStructureRiverlandsCity(biome, 0.0f).setIsCastle();
		f[48] = new GOTStructureRiverlandsCity(biome, 0.0f).setIsTown();
		f[49] = new GOTStructureCrossroadsInn(biome, 0.0f);
		f[50] = new GOTStructureReachCity(biome, 0.0f).setIsTown();
		f[51] = new GOTStructureReachCity(biome, 0.0f);
		f[52] = new GOTStructureReachCity(biome, 0.0f).setIsCastle();
		f[53] = new GOTStructureReachCity(biome, 0.0f).setIsTown();
		f[54] = new GOTStructureTower(biome, 0.0f);
		f[55] = new GOTStructureNorthCity(biome, 0.0f).setIsTown();
		f[56] = new GOTStructureNorthCity(biome, 0.0f).setIsSmallTown();
		f[57] = new GOTStructureNorthCity(biome, 0.0f).setIsCastle();
		f[58] = new GOTStructureNorthCity(biome, 0.0f);
		f[59] = new GOTStructureVictarionLanding(biome, 0.0f);
		f[60] = new GOTStructureIronbornCity(biome, 0.0f);
		f[61] = new GOTStructureIronbornCity(biome, 0.0f).setIsCastle();
		f[62] = new GOTStructureIronbornCity(biome, 0.0f).setIsTown();
		f[63] = new GOTStructureRuins(biome, 0.0f);
		f[64] = new GOTStructureCrasterKeep(biome, 0.0f);
		f[65] = new GOTStructureCastleBlack(biome, 0.0f);
		f[66] = new GOTStructureShadowTower(biome, 0.0f);
		f[67] = new GOTStructureEastWatch(biome, 0.0f);
		f[68] = new GOTStructureWallGate(biome, 0.0f);

		f[0].affix(GOTWaypoint.Skane, GOTWaypoint.Deepdown, GOTWaypoint.DriftwoodHall, GOTWaypoint.Kingshouse);
		f[1].affix(GOTWaypoint.Myr.info(-1, 0, 1));
		f[2].affix(GOTWaypoint.Harrenhal, GOTWaypoint.Stygai, GOTWaypoint.Ulos, GOTWaypoint.Yeen);
		f[3].affix(GOTWaypoint.BarterBeach, GOTWaypoint.Gogossos, GOTWaypoint.Gorosh, GOTWaypoint.Zamettar.info(0, -1, 2));
		f[4].affix(GOTWaypoint.Doquu, GOTWaypoint.Ebonhead, GOTWaypoint.GoldenHead, GOTWaypoint.Koj, GOTWaypoint.LastLament, GOTWaypoint.LizardHead, GOTWaypoint.LotusPoint, GOTWaypoint.Naath, GOTWaypoint.Omboru, GOTWaypoint.PearlPalace, GOTWaypoint.RedFlowerVale, GOTWaypoint.SweetLotusVale, GOTWaypoint.TallTreesTown, GOTWaypoint.Walano, GOTWaypoint.Xon);
		f[5].affix(GOTWaypoint.Astapor.info(-1, 0, 1), GOTWaypoint.Meereen.info(0, -1, 2), GOTWaypoint.NewGhis, GOTWaypoint.Yunkai.info(-1, 0, 1));
		f[6].affix(GOTWaypoint.Astapor.info(-2, 0, 0), GOTWaypoint.NewGhis.info(-2, 0, 0), GOTWaypoint.Yunkai.info(-2, 0, 0));
		f[7].affix(GOTWaypoint.Braavos.info(0, -1, 2));
		f[8].affix(GOTWaypoint.Hornoth, GOTWaypoint.Kyth, GOTWaypoint.Sathar, GOTWaypoint.Rathylar, GOTWaypoint.VaesAthjikhari, GOTWaypoint.VaesDiaf, GOTWaypoint.VaesDothrak, GOTWaypoint.VaesEfe, GOTWaypoint.VaesGorqoyi, GOTWaypoint.VaesGraddakh, GOTWaypoint.VaesJini, GOTWaypoint.VaesKhadokh, GOTWaypoint.VaesKhewo, GOTWaypoint.VaesLeisi, GOTWaypoint.VaesLeqse, GOTWaypoint.VaesMejhah);
		f[9].affix(GOTWaypoint.IbNor, GOTWaypoint.IbSar, GOTWaypoint.NewIbbish, GOTWaypoint.PortOfIbben);
		f[10].affix(GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5.info(0, 0, 3));
		f[11].affix(GOTWaypoint.Asabhad.info(-1, 0, 0), GOTWaypoint.Baoji.info(0, 1, 2), GOTWaypoint.Eijiang.info(0, 1, 2), GOTWaypoint.Jinqi.info(-1, 0, 3), GOTWaypoint.Lizhao.info(1, 0, 0), GOTWaypoint.Manjin.info(1, 0, 3), GOTWaypoint.SiQo.info(1, 0, 0), GOTWaypoint.Tiqui.info(0, -1, 0), GOTWaypoint.TraderTown.info(0, -1, 0), GOTWaypoint.Vaibei.info(0, -1, 0), GOTWaypoint.Yibin.info(-1, 0, 3), GOTWaypoint.Yin.info(0, 1, 2), GOTWaypoint.Yunnan.info(1, 0, 1), GOTWaypoint.Zabhad, GOTWaypoint.Turrani, GOTWaypoint.Vahar, GOTWaypoint.Faros, GOTWaypoint.Huiji, GOTWaypoint.LengMa, GOTWaypoint.LengYi, GOTWaypoint.LesserMoraq, GOTWaypoint.Marahai, GOTWaypoint.PortMoraq);
		f[12].affix(GOTWaypoint.Elyria, GOTWaypoint.Tolos, GOTWaypoint.LittleValyria.info(0, 1, 0), GOTWaypoint.Mantarys.info(0, -1, 2), GOTWaypoint.Selhorys.info(-1, 0, 1), GOTWaypoint.Valysar.info(-1, 0, 1), GOTWaypoint.Volantis.info(-1, 0, 1), GOTWaypoint.VolonTherys.info(0, 1, 0));
		f[13].affix(GOTWaypoint.Tyrosh);
		f[14].affix(GOTWaypoint.Hojdbaatar);
		f[15].affix(GOTWaypoint.Hesh.info(1, 0, 3), GOTWaypoint.Kosrak.info(1, 0, 1), GOTWaypoint.Lhazosh.info(-1, 0, 1));
		f[16].affix(GOTWaypoint.Lorath, GOTWaypoint.Morosh);
		f[17].affix(GOTWaypoint.Lys);
		f[18].affix(GOTWaypoint.Meereen.info(0, -2, 0));
		f[19].affix(GOTWaypoint.Norvos.info(0, -1, 2));
		f[20].affix(GOTWaypoint.Pentos.info(-1, 0, 1));
		f[21].affix(GOTWaypoint.PortYhos.info(0, 1, 0), GOTWaypoint.Qarkash.info(0, 0, 0), GOTWaypoint.Qarth.info(0, 1, 0));
		f[22].affix(GOTWaypoint.Qohor.info(0, -1, 2));
		f[23].affix(GOTWaypoint.Asshai.info(0, 0, 2));
		f[24].affix(GOTWaypoint.Kadar, GOTWaypoint.Nefer);
		f[25].affix(GOTWaypoint.EastPass, GOTWaypoint.NorthPass, GOTWaypoint.SouthPass, GOTWaypoint.WestPass);
		f[26].affix(GOTWaypoint.ColdwaterBurn, GOTWaypoint.GreyGlen, GOTWaypoint.HeartsHome, GOTWaypoint.IronOak, GOTWaypoint.LongbowHall, GOTWaypoint.Ninestars, GOTWaypoint.OldAnchor, GOTWaypoint.Pebble, GOTWaypoint.Redfort, GOTWaypoint.Runestone, GOTWaypoint.Snakewood, GOTWaypoint.Strongsong, GOTWaypoint.ThePaps, GOTWaypoint.Wickenden, GOTWaypoint.WitchIsle, GOTWaypoint.GateOfTheMoon.info(0, 1, 0));
		f[27].affix(GOTWaypoint.Sisterton);
		f[28].affix(GOTWaypoint.Gulltown);
		f[29].affix(GOTWaypoint.Briarwhite);
		f[30].affix(GOTWaypoint.Antlers, GOTWaypoint.Brownhollow, GOTWaypoint.DyreDen, GOTWaypoint.Stokeworth, GOTWaypoint.Hayford.info(-1, 0, 0), GOTWaypoint.RooksRest.info(0, -1, 0), GOTWaypoint.Rosby.info(0, -1, 0));
		f[31].affix(GOTWaypoint.Duskendale.info(-2, 0, 3));
		f[32].affix(GOTWaypoint.KingsLanding.info(1, 0, 1));
		f[33].affix(GOTWaypoint.KingsLanding.info(2, 0, 1));
		f[34].affix(GOTWaypoint.Blackmont, GOTWaypoint.GhostHill, GOTWaypoint.Godsgrace, GOTWaypoint.Hellholt, GOTWaypoint.HighHermitage, GOTWaypoint.Tor, GOTWaypoint.Vaith, GOTWaypoint.WaterGardens, GOTWaypoint.Spottswood, GOTWaypoint.Lemonwood, GOTWaypoint.Saltshore, GOTWaypoint.Sandstone, GOTWaypoint.Kingsgrave.info(-1, 0, 0), GOTWaypoint.SkyReach.info(0, 1, 0), GOTWaypoint.Starfall.info(0, -1, 0), GOTWaypoint.Wyl.info(0, -1, 0), GOTWaypoint.Yronwood.info(1, 0, 0));
		f[35].affix(GOTWaypoint.GhastonGrey, GOTWaypoint.PlankyTown, GOTWaypoint.Sunspear);
		f[36].affix(GOTWaypoint.ClawIsle, GOTWaypoint.Dragonstone, GOTWaypoint.Driftmark, GOTWaypoint.HighTide, GOTWaypoint.SharpPoint, GOTWaypoint.Stonedance, GOTWaypoint.SweetportSound);
		f[37].affix(GOTWaypoint.Hull);
		f[38].affix(GOTWaypoint.Hardhome);
		f[39].affix(GOTWaypoint.Moletown, GOTWaypoint.Queenscrown);
		f[40].affix(GOTWaypoint.Highgarden.info(0, -1, 0));
		f[41].affix(GOTWaypoint.Kayce.info(0, 0, 3), GOTWaypoint.Lannisport.info(-1, 0, 3));
		f[42].affix(GOTWaypoint.Oxcross);
		f[43].affix(GOTWaypoint.CasterlyRock.info(-1, 0, 0), GOTWaypoint.Crakehall.info(-1, 0, 0), GOTWaypoint.GoldenTooth.info(0, 1, 0), GOTWaypoint.Kayce.info(1, 0, 0), GOTWaypoint.Sarsfield.info(0, -1, 0), GOTWaypoint.Silverhill, GOTWaypoint.Wyndhall, GOTWaypoint.Plumwood, GOTWaypoint.Riverspring, GOTWaypoint.Greenfield, GOTWaypoint.Hornvale, GOTWaypoint.DeepDen, GOTWaypoint.Faircastle, GOTWaypoint.Feastfires, GOTWaypoint.CleganesKeep, GOTWaypoint.Cornfield, GOTWaypoint.Crag, GOTWaypoint.Ashemark, GOTWaypoint.Banefort);
		f[44].affix(GOTWaypoint.Blackhaven.info(-1, 0, 0), GOTWaypoint.Bronzegate.info(1, 0, 0), GOTWaypoint.DeatsHear, GOTWaypoint.EvenfallHall, GOTWaypoint.Fawntown, GOTWaypoint.Amberly, GOTWaypoint.BlackHeart, GOTWaypoint.BroadArch, GOTWaypoint.Parchments, GOTWaypoint.Poddingfield, GOTWaypoint.RainHouse, GOTWaypoint.SeaworthCastle, GOTWaypoint.Stonehelm, GOTWaypoint.StormsEnd, GOTWaypoint.TudburyHoll, GOTWaypoint.Gallowsgrey, GOTWaypoint.Grandview, GOTWaypoint.Greenstone, GOTWaypoint.HarvestHall, GOTWaypoint.HaystackHall, GOTWaypoint.Mistwood, GOTWaypoint.Felwood.info(0, 1, 0), GOTWaypoint.Nightsong.info(0, 1, 0));
		f[45].affix(GOTWaypoint.WeepingTown);
		f[46].affix(GOTWaypoint.FairMarket, GOTWaypoint.Harroway, GOTWaypoint.Pennytree, GOTWaypoint.Sevenstreams);
		f[47].affix(GOTWaypoint.Darry.info(1, 0, 0), GOTWaypoint.Maidenpool.info(1, 0, 0), GOTWaypoint.PinkmaidenCastle, GOTWaypoint.RaventreeHall, GOTWaypoint.WayfarerRest, GOTWaypoint.AcornHall, GOTWaypoint.Atranta, GOTWaypoint.Riverrun.info(1, 0, 0), GOTWaypoint.Seagard.info(0, -1, 0), GOTWaypoint.StoneHedge.info(0, 1, 0));
		f[48].affix(GOTWaypoint.Maidenpool.info(-1, 0, 3), GOTWaypoint.Saltpans, GOTWaypoint.StoneySept, GOTWaypoint.Seagard.info(0, 1, 2));
		f[49].affix(GOTWaypoint.CrossroadsInn);
		f[50].affix(GOTWaypoint.Oldtown.info(-1, 0, 3));
		f[51].affix(GOTWaypoint.Oxcross);
		f[52].affix(GOTWaypoint.Appleton.info(0, -1, 0), GOTWaypoint.Ashford.info(0, 1, 0), GOTWaypoint.Bandallon, GOTWaypoint.Goldengrove, GOTWaypoint.GrassyVale, GOTWaypoint.Greenshield, GOTWaypoint.Grimston, GOTWaypoint.Hammerhal, GOTWaypoint.RedLake, GOTWaypoint.Ring, GOTWaypoint.Southshield, GOTWaypoint.Uplands, GOTWaypoint.Holyhall, GOTWaypoint.Honeyholt, GOTWaypoint.HornHill, GOTWaypoint.IvyHall, GOTWaypoint.Longtable, GOTWaypoint.NewBarrel, GOTWaypoint.Blackcrown, GOTWaypoint.BrightwaterKeep, GOTWaypoint.CiderHall, GOTWaypoint.Coldmoat, GOTWaypoint.DarkDell, GOTWaypoint.Dunstonbury, GOTWaypoint.Bitterbridge.info(0, 1, 0), GOTWaypoint.GarnetGrove.info(-1, 0, 0), GOTWaypoint.HewettTown.info(0, -1, 0), GOTWaypoint.OldOak.info(-1, 0, 0), GOTWaypoint.SunHouse.info(0, -1, 0), GOTWaypoint.Whitegrove.info(-1, 0, 0));
		f[53].affix(GOTWaypoint.Appleton.info(0, 1, 2), GOTWaypoint.Ashford.info(0, -1, 0), GOTWaypoint.HewettTown.info(0, 1, 0), GOTWaypoint.Smithyton.info(0, 1, 2), GOTWaypoint.StarfishHarbor, GOTWaypoint.Vinetown, GOTWaypoint.Ryamsport, GOTWaypoint.Tumbleton.info(0, -1, 0));
		f[54].affix(GOTWaypoint.ThreeTowers.info(-1, -1, 1), GOTWaypoint.ThreeTowers.info(-1, 0, 1), GOTWaypoint.ThreeTowers.info(-1, 1, 1), GOTWaypoint.TowerOfGlimmering, GOTWaypoint.BaelishKeep, GOTWaypoint.HightowerLitehouse, GOTWaypoint.RamseyTower, GOTWaypoint.Standfast, GOTWaypoint.TwinsLeft.info(0, 0, 2), GOTWaypoint.TwinsRight);
		f[55].affix(GOTWaypoint.WhiteHarbour.info(0, 0, 1));
		f[56].affix(GOTWaypoint.Barrowtown.info(0, 1, 2));
		f[57].affix(GOTWaypoint.Goldgrass.info(0, 1, 0), GOTWaypoint.RisvellsCastle.info(0, 1, 0), GOTWaypoint.ServinsCastle.info(-1, 0, 0), GOTWaypoint.Winterfell.info(0, 0, 1), GOTWaypoint.BlackPool, GOTWaypoint.DeepwoodMotte, GOTWaypoint.Dreadfort, GOTWaypoint.FlintsFinger, GOTWaypoint.Highpoint, GOTWaypoint.TorhensSquare, GOTWaypoint.WidowsWatch, GOTWaypoint.Hornwood, GOTWaypoint.Ironrath, GOTWaypoint.Karhold, GOTWaypoint.LastHearth, GOTWaypoint.MoatKailin, GOTWaypoint.MormontsKeep, GOTWaypoint.OldCastle, GOTWaypoint.RamsGate, GOTWaypoint.RillwaterCrossing);
		f[58].affix(GOTWaypoint.GreywaterWatch);
		f[59].affix(GOTWaypoint.VictarionLanding);
		f[60].affix(GOTWaypoint.Pebbleton);
		f[61].affix(GOTWaypoint.Blackhaven, GOTWaypoint.Blacktyde, GOTWaypoint.CorpseLake, GOTWaypoint.CrowSpikeKeep, GOTWaypoint.Downdelving, GOTWaypoint.DrummCastle, GOTWaypoint.GreyGarden, GOTWaypoint.Hammerhorn, GOTWaypoint.HarlawHall, GOTWaypoint.HarridanHill, GOTWaypoint.IronHolt, GOTWaypoint.LonelyLight, GOTWaypoint.MyreCastle, GOTWaypoint.Orkwood, GOTWaypoint.Pyke, GOTWaypoint.Saltcliffe, GOTWaypoint.SealskinPoint, GOTWaypoint.Shatterstone, GOTWaypoint.SparrCastle, GOTWaypoint.Stonehouse, GOTWaypoint.StonetreeCastle, GOTWaypoint.Sunderly, GOTWaypoint.TawneyCastle, GOTWaypoint.TenTowers, GOTWaypoint.Volmark);
		f[62].affix(GOTWaypoint.Lordsport, GOTWaypoint.RedHaven);
		f[63].affix(GOTWaypoint.Castamere, GOTWaypoint.Goldenhill, GOTWaypoint.GreyironCastle, GOTWaypoint.HoareCastle, GOTWaypoint.HoareKeep, GOTWaypoint.HoggHall, GOTWaypoint.HollardCastle, GOTWaypoint.OldStones, GOTWaypoint.Summerhall, GOTWaypoint.TarbeckHall, GOTWaypoint.TowerOfJoy, GOTWaypoint.Whispers, GOTWaypoint.WhiteWalls);
		f[64].affix(GOTWaypoint.CrastersKeep);
		f[65].affix(GOTWaypoint.CastleBlack);
		f[66].affix(GOTWaypoint.ShadowTower);
		f[67].affix(GOTWaypoint.EastWatch);
		f[68].affix(GOTWaypoint.CastleBlack.info(0, -1, 0));

		for (int i = 0; i <= 68; i++) {
			biome.decorator.addFixedVillage(f[i]);
		}
	}

	public static void onInit() {
		structures.put(GOTWaypoint.Ashemark, new GOTFixer.AddamMarbrand());
		structures.put(GOTWaypoint.Asshai, new GOTFixer.Asshai());
		structures.put(GOTWaypoint.Astapor.shift(-1, 0), new GOTFixer.Astapor());
		structures.put(GOTWaypoint.Banefort, new GOTFixer.QuentenBanefort());
		structures.put(GOTWaypoint.Barrowtown.shift(0, 1), new GOTFixer.BarbreyDustin());
		structures.put(GOTWaypoint.Blacktyde, new GOTFixer.BaelorBlacktyde());
		structures.put(GOTWaypoint.Braavos.shift(0, -1), new GOTFixer.TychoNestoris());
		structures.put(GOTWaypoint.BrightwaterKeep, new GOTFixer.GarlanTyrell());
		structures.put(GOTWaypoint.CasterlyRock.shift(-1, 0), new GOTFixer.CasterlyRock());
		structures.put(GOTWaypoint.ClawIsle, new GOTFixer.ArdrianCeltigar());
		structures.put(GOTWaypoint.CleganesKeep, new GOTFixer.GregorClegane());
		structures.put(GOTWaypoint.Cornfield, new GOTFixer.HarysSwyft());
		structures.put(GOTWaypoint.Crakehall.shift(-1, 0), new GOTFixer.LyleCrakehall());
		structures.put(GOTWaypoint.Dragonstone, new GOTFixer.Dragonstone());
		structures.put(GOTWaypoint.Dreadfort, new GOTFixer.Dreadfort());
		structures.put(GOTWaypoint.Driftmark, new GOTFixer.Driftmark());
		structures.put(GOTWaypoint.DrummCastle, new GOTFixer.DunstanDrumm());
		structures.put(GOTWaypoint.Euron, new GOTFixer.EuronGreyjoy());
		structures.put(GOTWaypoint.EvenfallHall, new GOTFixer.SelwynTarth());
		structures.put(GOTWaypoint.Faircastle, new GOTFixer.SebastonFarman());
		structures.put(GOTWaypoint.Feastfires, new GOTFixer.ForleyPrester());
		structures.put(GOTWaypoint.GateOfTheMoon.shift(0, 1), new GOTFixer.GateOfTheMoon());
		structures.put(GOTWaypoint.GoldenTooth.shift(0, 1), new GOTFixer.LeoLefford());
		structures.put(GOTWaypoint.Goldengrove, new GOTFixer.MathisRowan());
		structures.put(GOTWaypoint.Greenshield, new GOTFixer.MoribaldChester());
		structures.put(GOTWaypoint.Greenstone, new GOTFixer.EldonEstermont());
		structures.put(GOTWaypoint.GreyGarden, new GOTFixer.HarrasHarlaw());
		structures.put(GOTWaypoint.GreywaterWatch, new GOTFixer.HowlandReed());
		structures.put(GOTWaypoint.Gulltown, new GOTFixer.GeroldGrafton());
		structures.put(GOTWaypoint.Hammerhorn, new GOTFixer.GoroldGoodbrother());
		structures.put(GOTWaypoint.HeartsHome, new GOTFixer.LynCorbray());
		structures.put(GOTWaypoint.Hellholt, new GOTFixer.HarmenUller());
		structures.put(GOTWaypoint.HighHermitage, new GOTFixer.GeroldDayne());
		structures.put(GOTWaypoint.Highgarden.shift(0, -1), new GOTFixer.Highgarden());
		structures.put(GOTWaypoint.HightowerLitehouse, new GOTFixer.LeytonHightower());
		structures.put(GOTWaypoint.Hojdbaatar, new GOTFixer.TugarKhan());
		structures.put(GOTWaypoint.HollowHill, new GOTFixer.BericDondarrion());
		structures.put(GOTWaypoint.HornHill, new GOTFixer.RandyllTarly());
		structures.put(GOTWaypoint.Hornvale, new GOTFixer.TytosBrax());
		structures.put(GOTWaypoint.IronOak, new GOTFixer.HarroldHardyng());
		structures.put(GOTWaypoint.Karhold, new GOTFixer.RickardKarstark());
		structures.put(GOTWaypoint.Lannisport.shift(-1, 0), new GOTFixer.Lannisport());
		structures.put(GOTWaypoint.LastHearth, new GOTFixer.JohnUmber());
		structures.put(GOTWaypoint.LonelyLight, new GOTFixer.GylbertFarwynd());
		structures.put(GOTWaypoint.LongbowHall, new GOTFixer.GilwoodHunter());
		structures.put(GOTWaypoint.Longtable, new GOTFixer.OrtonMerryweather());
		structures.put(GOTWaypoint.Lordsport, new GOTFixer.Dagmer());
		structures.put(GOTWaypoint.Lys, new GOTFixer.SalladhorSaan());
		structures.put(GOTWaypoint.Maidenpool.shift(1, 0), new GOTFixer.WilliamMooton());
		structures.put(GOTWaypoint.Meereen.shift(0, -1), new GOTFixer.HizdahrZoLoraq());
		structures.put(GOTWaypoint.MormontsKeep, new GOTFixer.MaegeMormont());
		structures.put(GOTWaypoint.Myr.shift(-1, 0), new GOTFixer.HarryStrickland());
		structures.put(GOTWaypoint.NaggaHill, new GOTFixer.AeronGreyjoy());
		structures.put(GOTWaypoint.NightKing, new GOTFixer.NightKing());
		structures.put(GOTWaypoint.Ninestars, new GOTFixer.SymondTempleton());
		structures.put(GOTWaypoint.Norvos.shift(0, -1), new GOTFixer.Mellario());
		structures.put(GOTWaypoint.Oldtown.shift(-1, 0), new GOTFixer.Ebrose());
		structures.put(GOTWaypoint.Pentos.shift(-1, 0), new GOTFixer.IllyrioMopatis());
		structures.put(GOTWaypoint.PinkmaidenCastle, new GOTFixer.ClementPiper());
		structures.put(GOTWaypoint.Pyke, new GOTFixer.Pyke());
		structures.put(GOTWaypoint.Qarth.shift(0, 1), new GOTFixer.XaroXhoanDaxos());
		structures.put(GOTWaypoint.RaventreeHall, new GOTFixer.TytosBlackwood());
		structures.put(GOTWaypoint.RedHaven, new GOTFixer.ErikIronmaker());
		structures.put(GOTWaypoint.Redfort, new GOTFixer.HortonRedfort());
		structures.put(GOTWaypoint.Ring, new GOTFixer.QuennRoxton());
		structures.put(GOTWaypoint.RisvellsCastle.shift(0, 1), new GOTFixer.RodrikRyswell());
		structures.put(GOTWaypoint.Riverrun.shift(1, 0), new GOTFixer.Riverrun());
		structures.put(GOTWaypoint.Runestone, new GOTFixer.YohnRoyce());
		structures.put(GOTWaypoint.Sandstone, new GOTFixer.QuentynQorgyle());
		structures.put(GOTWaypoint.Seagard.shift(0, -1), new GOTFixer.JasonMallister());
		structures.put(GOTWaypoint.ServinsCastle.shift(-1, 0), new GOTFixer.CleyCerwyn());
		structures.put(GOTWaypoint.SkyReach.shift(0, 1), new GOTFixer.FranklynFowler());
		structures.put(GOTWaypoint.Spider, new GOTFixer.Hummel009());
		structures.put(GOTWaypoint.Starfall, new GOTFixer.BericDayne());
		structures.put(GOTWaypoint.StarfishHarbor, new GOTFixer.PaxterRedwyne());
		structures.put(GOTWaypoint.StoneHedge.shift(0, 1), new GOTFixer.JonosBracken());
		structures.put(GOTWaypoint.Stonehelm, new GOTFixer.GulianSwann());
		structures.put(GOTWaypoint.StormsEnd, new GOTFixer.StormsEnd());
		structures.put(GOTWaypoint.Strongsong, new GOTFixer.BenedarBelmore());
		structures.put(GOTWaypoint.Sunspear, new GOTFixer.Sunspear());
		structures.put(GOTWaypoint.TenTowers, new GOTFixer.RodrikHarlaw());
		structures.put(GOTWaypoint.ThreeEyedRavenCave, new GOTFixer.ThreeEyedRaven());
		structures.put(GOTWaypoint.TorhensSquare, new GOTFixer.HelmanTallhart());
		structures.put(GOTWaypoint.TwinsLeft, new GOTFixer.TwinsLeft());
		structures.put(GOTWaypoint.TwinsRight, new GOTFixer.TwinsRight());
		structures.put(GOTWaypoint.Tyrosh, new GOTFixer.JonConnington());
		structures.put(GOTWaypoint.VaesEfe, new GOTFixer.DaenerysTargaryen());
		structures.put(GOTWaypoint.Volantis.shift(-1, 0), new GOTFixer.Moqorro());
		structures.put(GOTWaypoint.Volmark, new GOTFixer.MaronVolmark());
		structures.put(GOTWaypoint.WhiteHarbour, new GOTFixer.WymanManderly());
		structures.put(GOTWaypoint.WhiteWood, new GOTFixer.BenjenStark());
		structures.put(GOTWaypoint.Winterfell, new GOTFixer.Winterfell());
		structures.put(GOTWaypoint.Yin.shift(0, 1), new GOTFixer.BuGai());
		structures.put(GOTWaypoint.Yronwood.shift(1, 0), new GOTFixer.QuentynMartell());
		structures.put(GOTWaypoint.Yunkai.shift(-1, 0), new GOTFixer.Yunkai());
	}

	public static class AddamMarbrand extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
		}
	}

	public static class AeronGreyjoy extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityAeronGreyjoy(world), world, 0, 1, 2);
		}
	}

	public static class ArdrianCeltigar extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityArdrianCeltigar(world), world, 0, 1, 2);
		}
	}

	public static class Asshai extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
		}
	}

	public static class Astapor extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityKraznysMoNakloz(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityMissandei(world), world, -1, 1, -1);
			spawnLegendaryNPC(new GOTEntityGreyWorm(world), world, -1, 1, 1);
		}
	}

	public static class BaelorBlacktyde extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBaelorBlacktyde(world), world, -2, 1, -2);
		}
	}

	public static class BarbreyDustin extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBarbreyDustin(world), world, 0, 1, 3);
		}
	}

	public static class BenedarBelmore extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBenedarBelmore(world), world, 0, 1, 2);
		}
	}

	public static class BenjenStark extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBenjenStark(world), world, 0, 1, 5);
		}
	}

	public static class BericDayne extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBericDayne(world), world, -2, 1, 2);
		}
	}

	public static class BericDondarrion extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBericDondarrion.LifeStage1(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityThoros(world), world, 0, 1, 3);
		}
	}

	public static class BuGai extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
		}
	}

	public static class CasterlyRock extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityTywinLannister(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityQyburn(world), world, -2, 1, 0);
		}
	}

	public static class ClementPiper extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityClementPiper(world), world, 0, 1, 2);
		}
	}

	public static class CleyCerwyn extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
		}
	}

	public static class DaenerysTargaryen extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityDaenerysTargaryen(world), world, 0, 5, 3);
			spawnLegendaryNPC(new GOTEntityJorahMormont(world), world, 0, 5, 3);
		}
	}

	public static class Dagmer extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityDagmer(world), world, 0, 1, 3);
		}
	}

	public static class Dragonstone extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityStannisBaratheon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityDavosSeaworth(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityMelisandra(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityShireenBaratheon(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntitySelyseBaratheon(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityMatthosSeaworth(world), world, 0, 1, -2);
		}
	}

	public static class Dreadfort extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRamsayBolton(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRooseBolton(world), world, -2, 1, -2);
		}
	}

	public static class Driftmark extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMonfordVelaryon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAuraneWaters(world), world, 2, 1, 2);
		}
	}

	public static class DunstanDrumm extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityDunstanDrumm(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
		}
	}

	public static class Ebrose extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
		}
	}

	public static class EldonEstermont extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityEldonEstermont(world), world, 0, 1, 2);
		}
	}

	public static class ErikIronmaker extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityErikIronmaker(world), world, -2, 1, -2);
		}
	}

	public static class EuronGreyjoy extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			loadStrScan("euron_ship");
			generateStrScan(world, random, 16, 27, -58);
			for (int l = 0; l < 10; ++l) {
				spawnLegendaryNPC(new GOTEntityIronbornSoldier(world), world, 0, 1, 0);
			}
			spawnLegendaryNPC(new GOTEntityEuronGreyjoy(world), world, 0, 1, 0);
			return true;
		}
	}

	public static class ForleyPrester extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityForleyPrester(world), world, 0, 1, 2);
		}
	}

	public static class FranklynFowler extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityFranklynFowler(world), world, 0, 1, 2);
		}
	}

	public static class GarlanTyrell extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGarlanTyrell(world), world, 2, 1, -2);
		}
	}

	public static class GateOfTheMoon extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
		}
	}

	public static class GeroldDayne extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGeroldDayne(world), world, 2, 1, -2);
		}
	}

	public static class GeroldGrafton extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGeroldGrafton(world), world, 3, 1, 0);
		}
	}

	public static class GilwoodHunter extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGilwoodHunter(world), world, 0, 1, 2);
		}
	}

	public static class GoroldGoodbrother extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGoroldGoodbrother(world), world, 0, 1, 2);
		}
	}

	public static class GregorClegane extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGregorClegane.Alive(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityPolliver(world), world, -2, 1, 0);
		}
	}

	public static class GulianSwann extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGulianSwann(world), world, 0, 1, 2);
		}
	}

	public static class GylbertFarwynd extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityGylbertFarwynd(world), world, -2, 1, -2);
		}
	}

	public static class HarmenUller extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHarmenUller(world), world, 0, 1, 2);
		}
	}

	public static class HarrasHarlaw extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHarrasHarlaw(world), world, 0, 1, 2);
		}
	}

	public static class HarroldHardyng extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHarroldHardyng(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
		}
	}

	public static class HarryStrickland extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHarryStrickland(world), world, -1, 1, -1);
		}
	}

	public static class HarysSwyft extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHarysSwyft(world), world, 0, 1, 2);
		}
	}

	public static class HelmanTallhart extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
		}
	}

	public static class Highgarden extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
		}
	}

	public static class HizdahrZoLoraq extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, 0, 1, 1);
		}
	}

	public static class HortonRedfort extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHortonRedfort(world), world, 0, 1, 2);
		}
	}

	public static class HowlandReed extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHowlandReed(world), world, 0, 1, 5);
		}
	}

	public static class Hummel009 extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityHummel009(world), world, 0, 1, 0);
		}
	}

	public static class IllyrioMopatis extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityIllyrioMopatis(world), world, 3, 1, 0);
		}
	}

	public static class JasonMallister extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
		}
	}

	public static class JohnUmber extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityJohnUmber(world), world, 0, 1, 2);
		}
	}

	public static class JonConnington extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityJonConnington(world), world, 0, 1, -1);
			spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
		}
	}

	public static class JonosBracken extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityJonosBracken(world), world, 0, 1, 2);
		}
	}

	public static class KingsLanding extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
			spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
		}
	}

	public static class Lannisport extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityKevanLannister(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityDavenLannister(world), world, 0, 1, -5);
			spawnLegendaryNPC(new GOTEntityAmoryLorch(world), world, 5, 1, 0);
		}
	}

	public static class LeoLefford extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityLeoLefford(world), world, 2, 1, 2);
		}
	}

	public static class LeytonHightower extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityLeytonHightower(world), world, 0, 26, -5);
		}
	}

	public static class LyleCrakehall extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityLyleCrakehall(world), world, 2, 1, 2);
		}
	}

	public static class LynCorbray extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityLynCorbray(world), world, 2, 1, 2);
		}
	}

	public static class MaegeMormont extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
		}
	}

	public static class MaronVolmark extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMaronVolmark(world), world, -2, 1, -2);
		}
	}

	public static class MathisRowan extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMathisRowan(world), world, 2, 1, 2);
		}
	}

	public static class Mellario extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
		}
	}

	public static class Moqorro extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMoqorro(world), world, -1, 1, 0);
		}
	}

	public static class MoribaldChester extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityMoribaldChester(world), world, 2, 1, 0);
		}
	}

	public static class NightKing extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			loadStrScan("night_king");
			spawnLegendaryNPC(new GOTEntityNightKing(world), world, 0, 10, 0);
			generateStrScan(world, random, 0, 0, 0);
			return true;
		}
	}

	public static class OrtonMerryweather extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityOrtonMerryweather(world), world, 0, 1, 2);
		}
	}

	public static class PaxterRedwyne extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityPaxterRedwyne(world), world, 0, 1, 5);
		}
	}

	public static class Pyke extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBalonGreyjoy(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityYaraGreyjoy(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityTheonGreyjoy.Normal(world), world, -2, 1, 2);
		}
	}

	public static class QuennRoxton extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
		}
	}

	public static class QuentenBanefort extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityQuentenBanefort(world), world, 0, 1, 2);
		}
	}

	public static class QuentynMartell extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
		}
	}

	public static class QuentynQorgyle extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityQuentynQorgyle(world), world, 0, 1, 2);
		}
	}

	public static class RandyllTarly extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRandyllTarly(world), world, 0, 1, 2);
		}
	}

	public static class RickardKarstark extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
		}
	}

	public static class Riverrun extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBryndenTully(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityEdmureTully(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityHosterTully(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityRodrikCassel(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityCatelynStark(world), world, 2, 1, 0);
		}
	}

	public static class RodrikHarlaw extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRodrikHarlaw(world), world, 0, 1, 2);
		}
	}

	public static class RodrikRyswell extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
		}
	}

	public static class SalladhorSaan extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntitySalladhorSaan(world), world, 0, 1, -1);
		}
	}

	public static class SebastonFarman extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntitySebastonFarman(world), world, 0, 1, 2);
		}
	}

	public static class SelwynTarth extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntitySelwynTarth(world), world, 0, 1, 2);
		}
	}

	public static class Spawner extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(world);
			return true;
		}

		public void spawnLegendaryNPC(World world) {
		}
	}

	public static class StormsEnd extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRenlyBaratheon(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityLorasTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBrienneTarth(world), world, -2, 1, 2);
		}
	}

	public static class Sunspear extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityOberynMartell(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityDoranMartell(world), world, 0, 1, 3);
			spawnLegendaryNPC(new GOTEntityEllaryaSand(world), world, 3, 1, 3);
			spawnLegendaryNPC(new GOTEntityAreoHotah(world), world, 0, 1, -3);
			spawnLegendaryNPC(new GOTEntityTrystaneMartell(world), world, -3, 1, 0);
			spawnLegendaryNPC(new GOTEntityArianneMartell(world), world, -3, 1, 3);
			spawnLegendaryNPC(new GOTEntityManfreyMartell(world), world, -3, 1, -3);
		}
	}

	public static class SymondTempleton extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
		}
	}

	public static class ThreeEyedRaven extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 1, 0);
		}
	}

	public static class TugarKhan extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityTugarKhan(world), world, 0, 5, 3);
		}
	}

	public static class TwinsLeft extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 0, 1, -15);
			spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 0, 1, -15);
		}
	}

	public static class TwinsRight extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 0, 1, 15);
		}
	}

	public static class TychoNestoris extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityTychoNestoris(world), world, 0, 1, 1);
		}
	}

	public static class TytosBlackwood extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityTytosBlackwood(world), world, 2, 1, 0);
		}
	}

	public static class TytosBrax extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityTytosBrax(world), world, 0, 1, 2);
		}
	}

	public static class WilliamMooton extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
		}
	}

	public static class Winterfell extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityRobbStark(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityHodor(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAryaStark(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBranStark(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRickonStark(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLuwin(world), world, 0, 1, -2);
			spawnLegendaryNPC(new GOTEntityOsha(world), world, 2, 1, 0);
		}
	}

	public static class WymanManderly extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
		}
	}

	public static class XaroXhoanDaxos extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityXaroXhoanDaxos(world), world, 3, 1, 0);
		}
	}

	public static class YohnRoyce extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityYohnRoyce(world), world, 2, 1, 0);
		}
	}

	public static class Yunkai extends Spawner {

		@Override
		public void spawnLegendaryNPC(World world) {
			spawnLegendaryNPC(new GOTEntityDaarioNaharis(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityRazdalMoEraz(world), world, -1, 1, 1);
		}
	}
}