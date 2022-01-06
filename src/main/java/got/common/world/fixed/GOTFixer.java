package got.common.world.fixed;

import java.util.Random;

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
import got.common.util.GOTCommander;
import got.common.world.biome.GOTBiome;
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
import got.common.world.structure.westeros.reach.GOTStructureReachCity;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsCity;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsCity;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;
import net.minecraft.world.World;

public class GOTFixer {
	public static GOTVillageGen f01;
	public static GOTVillageGen f02;
	public static GOTVillageGen f03;
	public static GOTVillageGen f04;
	public static GOTVillageGen f05;
	public static GOTVillageGen f06;
	public static GOTVillageGen f07;
	public static GOTVillageGen f08;
	public static GOTVillageGen f09;
	public static GOTVillageGen f10;
	public static GOTVillageGen f11;
	public static GOTVillageGen f12;
	public static GOTVillageGen f13;
	public static GOTVillageGen f14;
	public static GOTVillageGen f15;
	public static GOTVillageGen f16;
	public static GOTVillageGen f17;
	public static GOTVillageGen f18;
	public static GOTVillageGen f19;
	public static GOTVillageGen f20;
	public static GOTVillageGen f21;
	public static GOTVillageGen f22;
	public static GOTVillageGen f23;
	public static GOTVillageGen f24;
	public static GOTVillageGen f25;
	public static GOTVillageGen f26;
	public static GOTVillageGen f27;
	public static GOTVillageGen f28;
	public static GOTVillageGen f29;
	public static GOTVillageGen f30;
	public static GOTVillageGen f31;
	public static GOTVillageGen f32;
	public static GOTVillageGen f33;
	public static GOTVillageGen f34;
	public static GOTVillageGen f35;
	public static GOTVillageGen f36;
	public static GOTVillageGen f37;
	public static GOTVillageGen f38;
	public static GOTVillageGen f39;
	public static GOTVillageGen f40;
	public static GOTVillageGen f41;
	public static GOTVillageGen f42;
	public static GOTVillageGen f43;
	public static GOTVillageGen f44;
	public static GOTVillageGen f45;
	public static GOTVillageGen f46;
	public static GOTVillageGen f47;
	public static GOTVillageGen f48;
	public static GOTVillageGen f49;
	public static GOTVillageGen f50;
	public static GOTVillageGen f51;
	public static GOTVillageGen f52;
	public static GOTVillageGen f53;
	public static GOTVillageGen f54;
	public static GOTVillageGen f55;
	public static GOTVillageGen f56;
	public static GOTVillageGen f57;
	public static GOTVillageGen f58;
	public static GOTVillageGen f59;
	public static GOTVillageGen f60;
	public static GOTVillageGen f61;
	public static GOTVillageGen f62;
	public static GOTVillageGen f63;
	public static GOTVillageGen f64;
	public static GOTVillageGen f65;
	public static GOTVillageGen f66;
	public static GOTVillageGen f67;
	public static GOTVillageGen f68;
	public static GOTVillageGen f69;

	public static void addWaypointLocations(GOTBiome biome) {
		f01 = new GOTStructureMyrCity(biome, 0.0f).setIsTown();
		f02 = new GOTStructureRuinsBig(biome, 0.0f);
		f03 = new GOTStructureGhiscarCity(biome, 0.0f).setIsColony();
		f04 = new GOTStructureSummerVillage(biome, 1.0f);
		f05 = new GOTStructureGhiscarCity(biome, 0.0f).setIsTown();
		f06 = new GOTStructureGhiscarPyramid1(biome, 0.0f);
		f07 = new GOTStructureBraavosCity(biome, 0.0f).setIsTown();
		f08 = new GOTStructureDothrakiVillage(biome, 0.0f).setIsBig();
		f09 = new GOTStructureIbbenVillage(biome, 1.0f);
		f10 = new GOTStructureFiveFortsTower(biome, 0.0f);
		f11 = new GOTStructureYiTiCity(biome, 0.0f).setIsTown();
		f12 = new GOTStructureVolantisCity(biome, 0.0f).setIsTown();
		f13 = new GOTStructureTyroshCity(biome, 0.0f).setIsTown();
		f14 = new GOTStructureJogosVillage(biome, 0.0f).setIsBig();
		f15 = new GOTStructureLhazarVillage(biome, 0.0f).setIsTown();
		f16 = new GOTStructureLorathCity(biome, 0.0f).setIsTown();
		f17 = new GOTStructureLysCity(biome, 0.0f).setIsTown();
		f18 = new GOTStructureGhiscarPyramid2(biome, 0.0f);
		f19 = new GOTStructureNorvosCity(biome, 0.0f).setIsTown();
		f20 = new GOTStructurePentosCity(biome, 0.0f).setIsTown();
		f21 = new GOTStructureQarthCity(biome, 0.0f).setIsTown();
		f22 = new GOTStructureQohorCity(biome, 0.0f).setIsTown();
		f23 = new GOTStructureAsshaiCity(biome, 0.0f);
		f24 = new GOTStructureMossovyVillage(biome, 0.0f);
		f25 = new GOTStructureMossovyRampart(biome, 0.0f);
		f26 = new GOTStructureArrynCity(biome, 0.0f).setIsCastle();
		f27 = new GOTStructureArrynCity(biome, 0.0f).setIsTown();
		f28 = new GOTStructureArrynCity(biome, 0.0f).setIsTown();
		f29 = new GOTStructureCrownlandsCity(biome, 0.0f);
		f30 = new GOTStructureCrownlandsCity(biome, 0.0f).setIsCastle();
		f31 = new GOTStructureCrownlandsCity(biome, 0.0f).setIsTown();
		f32 = new GOTStructureCrownlandsCity(biome, 0.0f).setIsCapital();
		f33 = new GOTStructureRedCastle(biome, 0.0f);
		f34 = new GOTStructureDorneCity(biome, 0.0f).setIsCastle();
		f35 = new GOTStructureDorneCity(biome, 0.0f).setIsTown();
		f36 = new GOTStructureDragonstoneCity(biome, 0.0f).setIsCastle();
		f37 = new GOTStructureDragonstoneCity(biome, 0.0f).setIsTown();
		f38 = new GOTStructureHardhome(biome, 0.0f);
		f39 = new GOTStructureNightKing(biome, 0.0f);
		f40 = new GOTStructureReachCity(biome, 0.0f).setIsCastle();
		f41 = new GOTStructureWesterlandsCity(biome, 0.0f).setIsTown();
		f42 = new GOTStructureWesterlandsCity(biome, 0.0f);
		f43 = new GOTStructureWesterlandsCity(biome, 0.0f).setIsCastle();
		f44 = new GOTStructureStormlandsCity(biome, 0.0f).setIsCastle();
		f45 = new GOTStructureStormlandsCity(biome, 0.0f).setIsTown();
		f46 = new GOTStructureRiverlandsCity(biome, 0.0f);
		f47 = new GOTStructureRiverlandsCity(biome, 0.0f).setIsCastle();
		f48 = new GOTStructureRiverlandsCity(biome, 0.0f).setIsTown();
		f49 = new GOTStructureCrossroadsInn(biome, 0.0f);
		f50 = new GOTStructureReachCity(biome, 0.0f).setIsTown();
		f51 = new GOTStructureReachCity(biome, 0.0f);
		f52 = new GOTStructureReachCity(biome, 0.0f).setIsCastle();
		f53 = new GOTStructureReachCity(biome, 0.0f).setIsTown();
		f54 = new GOTStructureTower(biome, 0.0f);
		f55 = new GOTStructureNorthCity(biome, 0.0f).setIsTown();
		f56 = new GOTStructureNorthCity(biome, 0.0f).setIsSmallTown();
		f57 = new GOTStructureNorthCity(biome, 0.0f).setIsCastle();
		f58 = new GOTStructureNorthCity(biome, 0.0f);
		f59 = new GOTStructureVictarionLanding(biome, 0.0f);
		f60 = new GOTStructureIronbornCity(biome, 0.0f);
		f61 = new GOTStructureIronbornCity(biome, 0.0f).setIsCastle();
		f62 = new GOTStructureIronbornCity(biome, 0.0f).setIsTown();
		f63 = new GOTStructureRuins(biome, 0.0f);
		f64 = new GOTStructureCrasterKeep(biome, 0.0f);
		f65 = new GOTStructureCastleBlack(biome, 0.0f);
		f66 = new GOTStructureShadowTower(biome, 0.0f);
		f67 = new GOTStructureEastWatch(biome, 0.0f);
		f68 = new GOTStructureWallGate(biome, 0.0f);
		f69 = new GOTStructureGiftVillage(biome, 0.0f);
		
		f01.affix(GOTWaypoint.Myr, -1, 0, 1);
		f02.affix(GOTWaypoint.Harrenhal);
		f02.affix(GOTWaypoint.Stygai);
		f02.affix(GOTWaypoint.Ulos);
		f02.affix(GOTWaypoint.Yeen);
		f03.affix(GOTWaypoint.BarterBeach);
		f03.affix(GOTWaypoint.Gogossos);
		f03.affix(GOTWaypoint.Gorosh);
		f03.affix(GOTWaypoint.Zamettar, 0, -1, 2);
		f04.affix(GOTWaypoint.Doquu);
		f04.affix(GOTWaypoint.Ebonhead);
		f04.affix(GOTWaypoint.GoldenHead);
		f04.affix(GOTWaypoint.Koj);
		f04.affix(GOTWaypoint.LastLament);
		f04.affix(GOTWaypoint.LizardHead);
		f04.affix(GOTWaypoint.LotusPoint);
		f04.affix(GOTWaypoint.Naath);
		f04.affix(GOTWaypoint.Omboru);
		f04.affix(GOTWaypoint.PearlPalace);
		f04.affix(GOTWaypoint.RedFlowerVale);
		f04.affix(GOTWaypoint.SweetLotusVale);
		f04.affix(GOTWaypoint.TallTreesTown);
		f04.affix(GOTWaypoint.Walano);
		f04.affix(GOTWaypoint.Xon);
		f05.affix(GOTWaypoint.Astapor, -1, 0, 1);
		f05.affix(GOTWaypoint.Meereen, 0, -1, 2);
		f05.affix(GOTWaypoint.NewGhis);
		f05.affix(GOTWaypoint.Yunkai, -1, 0, 1);
		f06.affix(GOTWaypoint.Astapor, -2, 0);
		f06.affix(GOTWaypoint.NewGhis, -2, 0);
		f06.affix(GOTWaypoint.Yunkai, -2, 0);
		f07.affix(GOTWaypoint.Braavos, 0, -1, 2);
		f08.affix(GOTWaypoint.Hornoth);
		f08.affix(GOTWaypoint.Kyth);
		f08.affix(GOTWaypoint.Rathylar);
		f08.affix(GOTWaypoint.Sathar);
		f08.affix(GOTWaypoint.VaesAthjikhari);
		f08.affix(GOTWaypoint.VaesDiaf);
		f08.affix(GOTWaypoint.VaesDothrak);
		f08.affix(GOTWaypoint.VaesEfe);
		f08.affix(GOTWaypoint.VaesGorqoyi);
		f08.affix(GOTWaypoint.VaesGraddakh);
		f08.affix(GOTWaypoint.VaesJini);
		f08.affix(GOTWaypoint.VaesKhadokh);
		f08.affix(GOTWaypoint.VaesKhewo);
		f08.affix(GOTWaypoint.VaesLeisi);
		f08.affix(GOTWaypoint.VaesLeqse);
		f08.affix(GOTWaypoint.VaesMejhah);
		f09.affix(GOTWaypoint.IbNor);
		f09.affix(GOTWaypoint.IbSar);
		f09.affix(GOTWaypoint.NewIbbish);
		f09.affix(GOTWaypoint.PortOfIbben);
		f10.affix(GOTWaypoint.FiveForts1);
		f10.affix(GOTWaypoint.FiveForts2);
		f10.affix(GOTWaypoint.FiveForts3);
		f10.affix(GOTWaypoint.FiveForts4);
		f10.affix(GOTWaypoint.FiveForts5, 0, 0, 3);
		f11.affix(GOTWaypoint.Asabhad, -1, 0);
		f11.affix(GOTWaypoint.Baoji, 0, 1, 2);
		f11.affix(GOTWaypoint.Eijiang, 0, 1, 2);
		f11.affix(GOTWaypoint.Faros);
		f11.affix(GOTWaypoint.Huiji);
		f11.affix(GOTWaypoint.Jinqi, -1, 0, 3);
		f11.affix(GOTWaypoint.LengMa);
		f11.affix(GOTWaypoint.LengYi);
		f11.affix(GOTWaypoint.LesserMoraq);
		f11.affix(GOTWaypoint.Lizhao, 1, 0);
		f11.affix(GOTWaypoint.Manjin, 1, 0, 3);
		f11.affix(GOTWaypoint.Marahai);
		f11.affix(GOTWaypoint.PortMoraq);
		f11.affix(GOTWaypoint.SiQo, 1, 0);
		f11.affix(GOTWaypoint.Tiqui, 0, -1);
		f11.affix(GOTWaypoint.TraderTown, 0, -1);
		f11.affix(GOTWaypoint.Turrani);
		f11.affix(GOTWaypoint.Vahar);
		f11.affix(GOTWaypoint.Vaibei, 0, -1);
		f11.affix(GOTWaypoint.Yibin, -1, 0, 3);
		f11.affix(GOTWaypoint.Yin, 0, 1, 2);
		f11.affix(GOTWaypoint.Yunnan, 1, 0, 1);
		f11.affix(GOTWaypoint.Zabhad);
		f12.affix(GOTWaypoint.Elyria);
		f12.affix(GOTWaypoint.LittleValyria, 0, 1);
		f12.affix(GOTWaypoint.Mantarys, 0, -1, 2);
		f12.affix(GOTWaypoint.Selhorys, -1, 0, 1);
		f12.affix(GOTWaypoint.Tolos);
		f12.affix(GOTWaypoint.Valysar, -1, 0, 1);
		f12.affix(GOTWaypoint.Volantis, -1, 0, 1);
		f12.affix(GOTWaypoint.VolonTherys, 0, 1);
		f13.affix(GOTWaypoint.Tyrosh);
		f14.affix(GOTWaypoint.Hojdbaatar);
		f15.affix(GOTWaypoint.Hesh, 1, 0, 3);
		f15.affix(GOTWaypoint.Kosrak, 1, 0, 1);
		f15.affix(GOTWaypoint.Lhazosh, -1, 0, 1);
		f16.affix(GOTWaypoint.Lorath);
		f16.affix(GOTWaypoint.Morosh);
		f17.affix(GOTWaypoint.Lys);
		f18.affix(GOTWaypoint.Meereen, 0, -2, 0);
		f19.affix(GOTWaypoint.Norvos, 0, -1, 2);
		f20.affix(GOTWaypoint.Pentos, -1, 0, 1);
		f21.affix(GOTWaypoint.PortYhos, 0, 1);
		f21.affix(GOTWaypoint.Qarkash, 0, 0);
		f21.affix(GOTWaypoint.Qarth, 0, 1);
		f22.affix(GOTWaypoint.Qohor, 0, -1, 2);
		f23.affix(GOTWaypoint.Asshai, 2);
		f24.affix(GOTWaypoint.Kadar);
		f25.affix(GOTWaypoint.EastPass);
		f25.affix(GOTWaypoint.NorthPass);
		f25.affix(GOTWaypoint.SouthPass);
		f25.affix(GOTWaypoint.WestPass);
		f26.affix(GOTWaypoint.ColdwaterBurn);
		f26.affix(GOTWaypoint.GateOfTheMoon, 0, 1);
		f26.affix(GOTWaypoint.GreyGlen);
		f26.affix(GOTWaypoint.HeartsHome);
		f26.affix(GOTWaypoint.IronOak);
		f26.affix(GOTWaypoint.LongbowHall);
		f26.affix(GOTWaypoint.Ninestars);
		f26.affix(GOTWaypoint.OldAnchor);
		f26.affix(GOTWaypoint.Pebble);
		f26.affix(GOTWaypoint.Redfort);
		f26.affix(GOTWaypoint.Runestone);
		f26.affix(GOTWaypoint.Snakewood);
		f26.affix(GOTWaypoint.Strongsong);
		f26.affix(GOTWaypoint.ThePaps);
		f26.affix(GOTWaypoint.Wickenden);
		f26.affix(GOTWaypoint.WitchIsle);
		f27.affix(GOTWaypoint.Sisterton);
		f28.affix(GOTWaypoint.Gulltown);
		f29.affix(GOTWaypoint.Briarwhite);
		f30.affix(GOTWaypoint.Antlers);
		f30.affix(GOTWaypoint.Brownhollow);
		f30.affix(GOTWaypoint.DyreDen);
		f30.affix(GOTWaypoint.Hayford, -1, 0);
		f30.affix(GOTWaypoint.RooksRest, 0, -1);
		f30.affix(GOTWaypoint.Rosby, 0, -1);
		f30.affix(GOTWaypoint.Stokeworth);
		f31.affix(GOTWaypoint.Duskendale, -2, 0, 3);
		f32.affix(GOTWaypoint.KingsLanding, 1, 0, 1);
		f33.affix(GOTWaypoint.KingsLanding, 2, 0, 1);
		f34.affix(GOTWaypoint.Blackmont);
		f34.affix(GOTWaypoint.GhostHill);
		f34.affix(GOTWaypoint.Godsgrace);
		f34.affix(GOTWaypoint.Hellholt);
		f34.affix(GOTWaypoint.HighHermitage);
		f34.affix(GOTWaypoint.Kingsgrave, -1, 0);
		f34.affix(GOTWaypoint.Lemonwood);
		f34.affix(GOTWaypoint.Saltshore);
		f34.affix(GOTWaypoint.Sandstone);
		f34.affix(GOTWaypoint.SkyReach, 0, 1);
		f34.affix(GOTWaypoint.Spottswood);
		f34.affix(GOTWaypoint.Starfall, 0, -1);
		f34.affix(GOTWaypoint.Tor);
		f34.affix(GOTWaypoint.Vaith);
		f34.affix(GOTWaypoint.WaterGardens);
		f34.affix(GOTWaypoint.Wyl, 0, -1);
		f34.affix(GOTWaypoint.Yronwood, 1, 0);
		f35.affix(GOTWaypoint.GhastonGrey);
		f35.affix(GOTWaypoint.PlankyTown);
		f35.affix(GOTWaypoint.Sunspear);
		f36.affix(GOTWaypoint.ClawIsle);
		f36.affix(GOTWaypoint.Dragonstone);
		f36.affix(GOTWaypoint.Driftmark);
		f36.affix(GOTWaypoint.HighTide);
		f36.affix(GOTWaypoint.SharpPoint);
		f36.affix(GOTWaypoint.Stonedance);
		f36.affix(GOTWaypoint.SweetportSound);
		f37.affix(GOTWaypoint.Hull);
		f38.affix(GOTWaypoint.Hardhome);
		f39.affix(GOTWaypoint.Aboba);
		f40.affix(GOTWaypoint.Highgarden, 0, -1);
		f41.affix(GOTWaypoint.Kayce, 3);
		f41.affix(GOTWaypoint.Lannisport, -1, 0, 3);
		f42.affix(GOTWaypoint.Oxcross);
		f43.affix(GOTWaypoint.Ashemark);
		f43.affix(GOTWaypoint.Banefort);
		f43.affix(GOTWaypoint.CasterlyRock, -1, 0);
		f43.affix(GOTWaypoint.CleganesKeep);
		f43.affix(GOTWaypoint.Cornfield);
		f43.affix(GOTWaypoint.Crag);
		f43.affix(GOTWaypoint.Crakehall, -1, 0);
		f43.affix(GOTWaypoint.DeepDen);
		f43.affix(GOTWaypoint.Faircastle);
		f43.affix(GOTWaypoint.Feastfires);
		f43.affix(GOTWaypoint.GoldenTooth, 0, 1);
		f43.affix(GOTWaypoint.Greenfield);
		f43.affix(GOTWaypoint.Hornvale);
		f43.affix(GOTWaypoint.Kayce, 1, 0);
		f43.affix(GOTWaypoint.Plumwood);
		f43.affix(GOTWaypoint.Riverspring);
		f43.affix(GOTWaypoint.Sarsfield, 0, -1);
		f43.affix(GOTWaypoint.Silverhill);
		f43.affix(GOTWaypoint.Wyndhall);
		f44.affix(GOTWaypoint.Amberly);
		f44.affix(GOTWaypoint.BlackHeart);
		f44.affix(GOTWaypoint.Blackhaven, -1, 0);
		f44.affix(GOTWaypoint.BroadArch);
		f44.affix(GOTWaypoint.Bronzegate, 1, 0);
		f44.affix(GOTWaypoint.DeatsHear);
		f44.affix(GOTWaypoint.EvenfallHall);
		f44.affix(GOTWaypoint.Fawntown);
		f44.affix(GOTWaypoint.Felwood, 0, 1);
		f44.affix(GOTWaypoint.Gallowsgrey);
		f44.affix(GOTWaypoint.Grandview);
		f44.affix(GOTWaypoint.Greenstone);
		f44.affix(GOTWaypoint.HarvestHall);
		f44.affix(GOTWaypoint.HaystackHall);
		f44.affix(GOTWaypoint.Mistwood);
		f44.affix(GOTWaypoint.Nightsong, 0, 1);
		f44.affix(GOTWaypoint.Parchments);
		f44.affix(GOTWaypoint.Poddingfield);
		f44.affix(GOTWaypoint.RainHouse);
		f44.affix(GOTWaypoint.SeaworthCastle);
		f44.affix(GOTWaypoint.Stonehelm);
		f44.affix(GOTWaypoint.StormsEnd);
		f44.affix(GOTWaypoint.TudburyHoll);
		f45.affix(GOTWaypoint.WeepingTown);
		f46.affix(GOTWaypoint.FairMarket);
		f46.affix(GOTWaypoint.Harroway);
		f46.affix(GOTWaypoint.Pennytree);
		f46.affix(GOTWaypoint.Sevenstreams);
		f47.affix(GOTWaypoint.AcornHall);
		f47.affix(GOTWaypoint.Atranta);
		f47.affix(GOTWaypoint.Darry, 1, 0);
		f47.affix(GOTWaypoint.Maidenpool, 1, 0);
		f47.affix(GOTWaypoint.PinkmaidenCastle);
		f47.affix(GOTWaypoint.RaventreeHall);
		f47.affix(GOTWaypoint.Riverrun, -1, 0);
		f47.affix(GOTWaypoint.Seagard, 0, -1);
		f47.affix(GOTWaypoint.StoneHedge, 0, 1);
		f47.affix(GOTWaypoint.WayfarerRest);
		f48.affix(GOTWaypoint.Maidenpool, -1, 0, 3);
		f48.affix(GOTWaypoint.Saltpans);
		f48.affix(GOTWaypoint.Seagard, 0, 1, 2);
		f48.affix(GOTWaypoint.StoneySept);
		f49.affix(GOTWaypoint.CrossroadsInn);
		f50.affix(GOTWaypoint.Oldtown, -1, 0, 3);
		f51.affix(GOTWaypoint.Oxcross);
		f52.affix(GOTWaypoint.Appleton, 0, -1);
		f52.affix(GOTWaypoint.Ashford, 0, 1);
		f52.affix(GOTWaypoint.Bandallon);
		f52.affix(GOTWaypoint.Bitterbridge, 0, 1);
		f52.affix(GOTWaypoint.Blackcrown);
		f52.affix(GOTWaypoint.BrightwaterKeep);
		f52.affix(GOTWaypoint.CiderHall);
		f52.affix(GOTWaypoint.Coldmoat);
		f52.affix(GOTWaypoint.DarkDell);
		f52.affix(GOTWaypoint.Dunstonbury);
		f52.affix(GOTWaypoint.GarnetGrove, -1, 0);
		f52.affix(GOTWaypoint.Goldengrove);
		f52.affix(GOTWaypoint.GrassyVale);
		f52.affix(GOTWaypoint.Greenshield);
		f52.affix(GOTWaypoint.Grimston);
		f52.affix(GOTWaypoint.Hammerhal);
		f52.affix(GOTWaypoint.HewettTown, 0, -1);
		f52.affix(GOTWaypoint.Holyhall);
		f52.affix(GOTWaypoint.Honeyholt);
		f52.affix(GOTWaypoint.HornHill);
		f52.affix(GOTWaypoint.IvyHall);
		f52.affix(GOTWaypoint.Longtable);
		f52.affix(GOTWaypoint.NewBarrel);
		f52.affix(GOTWaypoint.OldOak, -1, 0);
		f52.affix(GOTWaypoint.RedLake);
		f52.affix(GOTWaypoint.Ring);
		f52.affix(GOTWaypoint.Southshield);
		f52.affix(GOTWaypoint.SunHouse, 0, -1);
		f52.affix(GOTWaypoint.Uplands);
		f52.affix(GOTWaypoint.Whitegrove, -1, 0);
		f53.affix(GOTWaypoint.Appleton, 0, 1, 2);
		f53.affix(GOTWaypoint.Ashford, 0, -1);
		f53.affix(GOTWaypoint.HewettTown, 0, 1);
		f53.affix(GOTWaypoint.Ryamsport);
		f53.affix(GOTWaypoint.Smithyton, 0, 1, 2);
		f53.affix(GOTWaypoint.StarfishHarbor);
		f53.affix(GOTWaypoint.Tumbleton, 0, -1);
		f53.affix(GOTWaypoint.Vinetown);
		f54.affix(GOTWaypoint.BaelishKeep);
		f54.affix(GOTWaypoint.HightowerLitehouse);
		f54.affix(GOTWaypoint.RamseyTower);
		f54.affix(GOTWaypoint.Standfast);
		f54.affix(GOTWaypoint.ThreeTowers, -1, -1, 1);
		f54.affix(GOTWaypoint.ThreeTowers, -1, 0, 1);
		f54.affix(GOTWaypoint.ThreeTowers, -1, 1, 1);
		f54.affix(GOTWaypoint.TowerOfGlimmering);
		f54.affix(GOTWaypoint.TwinsLeft, 1, 0, 1);
		f54.affix(GOTWaypoint.TwinsRight, -2, 0, 3);
		f55.affix(GOTWaypoint.WhiteHarbour, 1);
		f56.affix(GOTWaypoint.Barrowtown, 0, 1, 2);
		f57.affix(GOTWaypoint.BlackPool);
		f57.affix(GOTWaypoint.DeepwoodMotte);
		f57.affix(GOTWaypoint.DeepwoodMotte);
		f57.affix(GOTWaypoint.Dreadfort);
		f57.affix(GOTWaypoint.FlintsFinger);
		f57.affix(GOTWaypoint.Goldgrass, 0, 1);
		f57.affix(GOTWaypoint.Highpoint);
		f57.affix(GOTWaypoint.Hornwood);
		f57.affix(GOTWaypoint.Ironrath);
		f57.affix(GOTWaypoint.Karhold);
		f57.affix(GOTWaypoint.LastHearth);
		f57.affix(GOTWaypoint.MoatKailin);
		f57.affix(GOTWaypoint.MormontsKeep);
		f57.affix(GOTWaypoint.OldCastle);
		f57.affix(GOTWaypoint.RamsGate);
		f57.affix(GOTWaypoint.RillwaterCrossing);
		f57.affix(GOTWaypoint.RisvellsCastle, 0, 1);
		f57.affix(GOTWaypoint.ServinsCastle, -1, 0);
		f57.affix(GOTWaypoint.TorhensSquare);
		f57.affix(GOTWaypoint.WidowsWatch);
		f57.affix(GOTWaypoint.Winterfell, 1);
		f58.affix(GOTWaypoint.GreywaterWatch);
		f59.affix(GOTWaypoint.VictarionLanding);
		f60.affix(GOTWaypoint.Pebbleton);
		f61.affix(GOTWaypoint.Blackhaven);
		f61.affix(GOTWaypoint.Blacktyde);
		f61.affix(GOTWaypoint.CorpseLake);
		f61.affix(GOTWaypoint.CrowSpikeKeep);
		f61.affix(GOTWaypoint.Downdelving);
		f61.affix(GOTWaypoint.DrummCastle);
		f61.affix(GOTWaypoint.GreyGarden);
		f61.affix(GOTWaypoint.Hammerhorn);
		f61.affix(GOTWaypoint.HarlawHall);
		f61.affix(GOTWaypoint.HarridanHill);
		f61.affix(GOTWaypoint.IronHolt);
		f61.affix(GOTWaypoint.LonelyLight);
		f61.affix(GOTWaypoint.MyreCastle);
		f61.affix(GOTWaypoint.Orkwood);
		f61.affix(GOTWaypoint.Pyke);
		f61.affix(GOTWaypoint.Saltcliffe);
		f61.affix(GOTWaypoint.SealskinPoint);
		f61.affix(GOTWaypoint.Shatterstone);
		f61.affix(GOTWaypoint.SparrCastle);
		f61.affix(GOTWaypoint.Stonehouse);
		f61.affix(GOTWaypoint.StonetreeCastle);
		f61.affix(GOTWaypoint.Sunderly);
		f61.affix(GOTWaypoint.TawneyCastle);
		f61.affix(GOTWaypoint.TenTowers);
		f61.affix(GOTWaypoint.Volmark);
		f62.affix(GOTWaypoint.Lordsport);
		f62.affix(GOTWaypoint.RedHaven);
		f63.affix(GOTWaypoint.Castamere);
		f63.affix(GOTWaypoint.Goldenhill);
		f63.affix(GOTWaypoint.GreyironCastle);
		f63.affix(GOTWaypoint.HoareCastle);
		f63.affix(GOTWaypoint.HoareKeep);
		f63.affix(GOTWaypoint.HoggHall);
		f63.affix(GOTWaypoint.HollardCastle);
		f63.affix(GOTWaypoint.OldStones);
		f63.affix(GOTWaypoint.Summerhall);
		f63.affix(GOTWaypoint.TarbeckHall);
		f63.affix(GOTWaypoint.TowerOfJoy);
		f63.affix(GOTWaypoint.Whispers);
		f63.affix(GOTWaypoint.WhiteWalls);
		f64.affix(GOTWaypoint.CrastersKeep);
		f65.affix(GOTWaypoint.CastleBlack);
		f66.affix(GOTWaypoint.ShadowTower);
		f67.affix(GOTWaypoint.EastWatch);
		f68.affix(GOTWaypoint.CastleBlack, 0, -1);
		f69.affix(GOTWaypoint.Moletown);
		f69.affix(GOTWaypoint.Queenscrown);
		
		for (GOTVillageGen settlement : GOTCommander.getObjectFieldsOfType(GOTFixer.class, GOTVillageGen.class)) {
			biome.decorator.affix(settlement);
		}
	}

	public static GOTStructureBase getFixedStructure(int i, int k) {
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Ashemark)) {
			return new GOTFixer.AddamMarbrand();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.NaggaHill)) {
			return new GOTFixer.AeronGreyjoy();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Yronwood, 1, 0)) {
			return new GOTFixer.AndersYronwood();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.DrummCastle)) {
			return new GOTFixer.AndrikTheUnsmilling();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.IronOak)) {
			return new GOTFixer.AnyaWaynwood();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.ClawIsle)) {
			return new GOTFixer.ArdrianCeltigar();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Asshai)) {
			return new GOTFixer.Asshai();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Astapor, -1, 0)) {
			return new GOTFixer.Astapor();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Blacktyde)) {
			return new GOTFixer.BaelorBlacktyde();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Barrowtown, 0, 1)) {
			return new GOTFixer.BarbreyDustin();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Strongsong)) {
			return new GOTFixer.BenedarBelmore();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteWood)) {
			return new GOTFixer.BenjenStark();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Starfall)) {
			return new GOTFixer.BericDayne();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.HollowHill)) {
			return new GOTFixer.BericDondarrion();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Yin, 0, 1)) {
			return new GOTFixer.BuGai();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.CasterlyRock, -1, 0)) {
			return new GOTFixer.CasterlyRock();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.PinkmaidenCastle)) {
			return new GOTFixer.ClementPiper();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.ServinsCastle, -1, 0)) {
			return new GOTFixer.CleyCerwyn();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.VaesEfe)) {
			return new GOTFixer.DaenerysTargaryen();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Lordsport)) {
			return new GOTFixer.Dagmer();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Dragonstone)) {
			return new GOTFixer.Dragonstone();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Dreadfort)) {
			return new GOTFixer.Dreadfort();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Driftmark)) {
			return new GOTFixer.Driftmark();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.DrummCastle)) {
			return new GOTFixer.DunstanDrumm();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Oldtown, -1, 0)) {
			return new GOTFixer.Ebrose();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Greenstone)) {
			return new GOTFixer.EldonEstermont();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.RedHaven)) {
			return new GOTFixer.ErikIronmaker();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Euron)) {
			return new GOTFixer.EuronGreyjoy();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Feastfires)) {
			return new GOTFixer.ForleyPrester();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.SkyReach, 0, 1)) {
			return new GOTFixer.FranklynFowler();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.BrightwaterKeep)) {
			return new GOTFixer.GarlanTyrell();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.GateOfTheMoon, 0, 1)) {
			return new GOTFixer.GateOfTheMoon();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.HighHermitage)) {
			return new GOTFixer.GeroldDayne();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Gulltown)) {
			return new GOTFixer.GeroldGrafton();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.LongbowHall)) {
			return new GOTFixer.GilwoodHunter();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Hammerhorn)) {
			return new GOTFixer.GoroldGoodbrother();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.CleganesKeep)) {
			return new GOTFixer.GregorClegane();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Stonehelm)) {
			return new GOTFixer.GulianSwann();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.LonelyLight)) {
			return new GOTFixer.GylbertFarwynd();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Hellholt)) {
			return new GOTFixer.HarmenUller();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.GreyGarden)) {
			return new GOTFixer.HarrasHarlaw();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.IronOak)) {
			return new GOTFixer.HarroldHardyng();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Myr, -1, 0)) {
			return new GOTFixer.HarryStrickland();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Cornfield)) {
			return new GOTFixer.HarysSwyft();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.TorhensSquare)) {
			return new GOTFixer.HelmanTallhart();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Highgarden, 0, -1)) {
			return new GOTFixer.Highgarden();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Meereen, 0, -1)) {
			return new GOTFixer.HizdahrZoLoraq();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Redfort)) {
			return new GOTFixer.HortonRedfort();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.GreywaterWatch)) {
			return new GOTFixer.HowlandReed();
		}
		if (GOTFixedStructures.fixedAt(i, k, 6861, 3785)) {
			return new GOTFixer.Hummel009();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Pentos, -1, 0)) {
			return new GOTFixer.IllyrioMopatis();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Seagard, 0, -1)) {
			return new GOTFixer.JasonMallister();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.LastHearth)) {
			return new GOTFixer.JohnUmber();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Tyrosh)) {
			return new GOTFixer.JonConnington();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.StoneHedge, 0, 1)) {
			return new GOTFixer.JonosBracken();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Lannisport, -1, 0)) {
			return new GOTFixer.Lannisport();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.GoldenTooth, 0, 1)) {
			return new GOTFixer.LeoLefford();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.HightowerLitehouse)) {
			return new GOTFixer.LeytonHightower();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Crakehall, -1, 0)) {
			return new GOTFixer.LyleCrakehall();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.HeartsHome)) {
			return new GOTFixer.LynCorbray();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.MormontsKeep)) {
			return new GOTFixer.MaegeMormont();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Volmark)) {
			return new GOTFixer.MaronVolmark();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Goldengrove)) {
			return new GOTFixer.MathisRowan();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Norvos, 0, -1)) {
			return new GOTFixer.Mellario();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Volantis, -1, 0)) {
			return new GOTFixer.Moqorro();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Longtable)) {
			return new GOTFixer.OrtonMerryweather();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.StarfishHarbor)) {
			return new GOTFixer.PaxterRedwyne();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Pyke)) {
			return new GOTFixer.Pyke();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Ring)) {
			return new GOTFixer.QuennRoxton();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Banefort)) {
			return new GOTFixer.QuentenBanefort();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Yronwood, 1, 0)) {
			return new GOTFixer.QuentynMartell();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Sandstone)) {
			return new GOTFixer.QuentynQorgyle();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.HornHill)) {
			return new GOTFixer.RandyllTarly();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Karhold)) {
			return new GOTFixer.RickardKarstark();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Riverrun, -1, 0)) {
			return new GOTFixer.Riverrun();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.TenTowers)) {
			return new GOTFixer.RodrikHarlaw();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.RisvellsCastle, 0, 1)) {
			return new GOTFixer.RodrikRyswell();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Lys)) {
			return new GOTFixer.SalladhorSaan();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Faircastle)) {
			return new GOTFixer.SebastonFarman();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.EvenfallHall)) {
			return new GOTFixer.SelwynTarth();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.StormsEnd)) {
			return new GOTFixer.StormsEnd();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Sunspear)) {
			return new GOTFixer.Sunspear();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Ninestars)) {
			return new GOTFixer.SymondTempleton();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.ThreeEyedRavenCave)) {
			return new GOTFixer.ThreeEyedRaven();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Hojdbaatar)) {
			return new GOTFixer.TugarKhan();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.TwinsLeft, 1, 0)) {
			return new GOTFixer.TwinsLeft();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Braavos, 0, -1)) {
			return new GOTFixer.TychoNestoris();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.RaventreeHall)) {
			return new GOTFixer.TytosBlackwood();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Hornvale)) {
			return new GOTFixer.TytosBrax();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.TwinsRight, -2, 0)) {
			return new GOTFixer.WalderFrey();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Maidenpool, 1, 0)) {
			return new GOTFixer.WilliamMooton();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Winterfell)) {
			return new GOTFixer.Winterfell();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteHarbour)) {
			return new GOTFixer.WymanManderly();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Qarth, 0, 1)) {
			return new GOTFixer.XaroXhoanDaxos();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Runestone)) {
			return new GOTFixer.YohnRoyce();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Tyrosh)) {
			return new GOTFixer.YoungGriff();
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Yunkai, -1, 0)) {
			return new GOTFixer.Yunkai();
		}
		return null;
	}

	public static class AddamMarbrand extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class AeronGreyjoy extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAeronGreyjoy(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class AndersYronwood extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class AndrikTheUnsmilling extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class AnyaWaynwood extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class ArdrianCeltigar extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityArdrianCeltigar(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class Asshai extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
			return true;
		}
	}

	public static class Astapor extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityKraznysMoNakloz(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityMissandei(world), world, -1, 1, -1);
			spawnLegendaryNPC(new GOTEntityGreyWorm(world), world, -1, 1, 1);
			return true;
		}
	}

	public static class BaelorBlacktyde extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBaelorBlacktyde(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class BarbreyDustin extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBarbreyDustin(world), world, 0, 1, 3);
			return true;
		}
	}

	public static class BenedarBelmore extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBenedarBelmore(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class BenjenStark extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBenjenStark(world), world, 0, 1, 5);
			return true;
		}
	}

	public static class BericDayne extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBericDayne(world), world, -2, 1, 2);
			return true;
		}
	}

	public static class BericDondarrion extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBericDondarrion.LifeStage1(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityThoros(world), world, 0, 1, 3);
			return true;
		}
	}

	public static class BuGai extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
			return true;
		}
	}

	public static class CasterlyRock extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTywinLannister(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityQyburn(world), world, -2, 1, 0);
			return true;
		}
	}

	public static class ClementPiper extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityClementPiper(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class CleyCerwyn extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class DaenerysTargaryen extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDaenerysTargaryen(world), world, 0, 5, 3);
			spawnLegendaryNPC(new GOTEntityJorahMormont(world), world, 0, 5, 3);
			return true;
		}
	}

	public static class Dagmer extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDagmer(world), world, 0, 1, 3);
			return true;
		}
	}

	public static class Dragonstone extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityStannisBaratheon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityDavosSeaworth(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityMelisandra(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityShireenBaratheon(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntitySelyseBaratheon(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityMatthosSeaworth(world), world, 0, 1, -2);
			return true;
		}
	}

	public static class Dreadfort extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRamsayBolton(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRooseBolton(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class Driftmark extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMonfordVelaryon(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAuraneWaters(world), world, 2, 1, 2);
			return true;
		}
	}

	public static class DunstanDrumm extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDunstanDrumm(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class Ebrose extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			return true;
		}
	}

	public static class EldonEstermont extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityEldonEstermont(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class ErikIronmaker extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityErikIronmaker(world), world, -2, 1, -2);
			return true;
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

	public static class ForleyPrester extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityForleyPrester(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class FranklynFowler extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityFranklynFowler(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class GarlanTyrell extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGarlanTyrell(world), world, 2, 1, -2);
			return true;
		}
	}

	public static class GateOfTheMoon extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
			return true;
		}
	}

	public static class GeroldDayne extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGeroldDayne(world), world, 2, 1, -2);
			return true;
		}
	}

	public static class GeroldGrafton extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGeroldGrafton(world), world, 3, 1, 0);
			return true;
		}
	}

	public static class GilwoodHunter extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGilwoodHunter(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class GoroldGoodbrother extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGoroldGoodbrother(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class GregorClegane extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGregorClegane.Alive(world), world, 2, 1, 0);
			spawnLegendaryNPC(new GOTEntityPolliver(world), world, -2, 1, 0);
			return true;
		}
	}

	public static class GulianSwann extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGulianSwann(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class GylbertFarwynd extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityGylbertFarwynd(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class HarmenUller extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarmenUller(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class HarrasHarlaw extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarrasHarlaw(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class HarroldHardyng extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarroldHardyng(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class HarryStrickland extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarryStrickland(world), world, -1, 1, -1);
			return true;
		}
	}

	public static class HarysSwyft extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHarysSwyft(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class HelmanTallhart extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class Highgarden extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
			return true;
		}
	}

	public static class HizdahrZoLoraq extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, 0, 1, 1);
			return true;
		}
	}

	public static class HortonRedfort extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHortonRedfort(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class HowlandReed extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHowlandReed(world), world, 0, 1, 5);
			return true;
		}
	}

	public static class Hummel009 extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityHummel009(world), world, 0, 1, 0);
			return true;
		}
	}

	public static class IllyrioMopatis extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityIllyrioMopatis(world), world, 3, 1, 0);
			return true;
		}
	}

	public static class JasonMallister extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
			return true;
		}
	}

	public static class JohnUmber extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJohnUmber(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class JonConnington extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJonConnington(world), world, 0, 1, -1);
			return true;
		}
	}

	public static class JonosBracken extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityJonosBracken(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class KingsLanding extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
			spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
			return true;
		}
	}

	public static class Lannisport extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityKevanLannister(world), world, 0, 1, 5);
			spawnLegendaryNPC(new GOTEntityDavenLannister(world), world, 0, 1, -5);
			spawnLegendaryNPC(new GOTEntityAmoryLorch(world), world, 5, 1, 0);
			return true;
		}
	}

	public static class LeoLefford extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLeoLefford(world), world, 2, 1, 2);
			return true;
		}
	}

	public static class LeytonHightower extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLeytonHightower(world), world, 0, 26, -5);
			return true;
		}
	}

	public static class LyleCrakehall extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLyleCrakehall(world), world, 2, 1, 2);
			return true;
		}
	}

	public static class LynCorbray extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityLynCorbray(world), world, 2, 1, 2);
			return true;
		}
	}

	public static class MaegeMormont extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class MaronVolmark extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMaronVolmark(world), world, -2, 1, -2);
			return true;
		}
	}

	public static class MathisRowan extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMathisRowan(world), world, 2, 1, 2);
			return true;
		}
	}

	public static class Mellario extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			return true;
		}
	}

	public static class Moqorro extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityMoqorro(world), world, -1, 1, 0);
			return true;
		}
	}

	public static class OrtonMerryweather extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityOrtonMerryweather(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class PaxterRedwyne extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityPaxterRedwyne(world), world, 0, 1, 5);
			return true;
		}
	}

	public static class Pyke extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBalonGreyjoy(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityYaraGreyjoy(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityTheonGreyjoy.Normal(world), world, -2, 1, 2);
			return true;
		}
	}

	public static class QuennRoxton extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class QuentenBanefort extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentenBanefort(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class QuentynMartell extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class QuentynQorgyle extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityQuentynQorgyle(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class RandyllTarly extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRandyllTarly(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class RickardKarstark extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class Riverrun extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBryndenTully(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityEdmureTully(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityHosterTully(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityRodrikCassel(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityCatelynStark(world), world, 2, 1, 0);
			return true;
		}
	}

	public static class RodrikHarlaw extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRodrikHarlaw(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class RodrikRyswell extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class SalladhorSaan extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySalladhorSaan(world), world, 0, 1, -1);
			return true;
		}
	}

	public static class SebastonFarman extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySebastonFarman(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class SelwynTarth extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySelwynTarth(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class StormsEnd extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRenlyBaratheon(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityLorasTyrell(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBrienneTarth(world), world, -2, 1, 2);
			return true;
		}
	}

	public static class Sunspear extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityOberynMartell(world), world, 3, 1, 0);
			spawnLegendaryNPC(new GOTEntityDoranMartell(world), world, 0, 1, 3);
			spawnLegendaryNPC(new GOTEntityEllaryaSand(world), world, 3, 1, 3);
			spawnLegendaryNPC(new GOTEntityAreoHotah(world), world, 0, 1, -3);
			spawnLegendaryNPC(new GOTEntityTrystaneMartell(world), world, -3, 1, 0);
			spawnLegendaryNPC(new GOTEntityArianneMartell(world), world, -3, 1, 3);
			spawnLegendaryNPC(new GOTEntityManfreyMartell(world), world, -3, 1, -3);
			return true;
		}
	}

	public static class SymondTempleton extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class ThreeEyedRaven extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 1, 0);
			return true;
		}
	}

	public static class TugarKhan extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTugarKhan(world), world, 0, 5, 3);
			return true;
		}
	}

	public static class TwinsLeft extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 5, 1, 0);
			spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 4, 1, 0);
			return true;
		}
	}

	public static class TychoNestoris extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTychoNestoris(world), world, 0, 1, 1);
			return true;
		}
	}

	public static class TytosBlackwood extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTytosBlackwood(world), world, 2, 1, 0);
			return true;
		}
	}

	public static class TytosBrax extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityTytosBrax(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class WalderFrey extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 5, 1, 0);
			return true;
		}
	}

	public static class WilliamMooton extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			return true;
		}
	}

	public static class Winterfell extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityRobbStark(world), world, 2, 1, 2);
			spawnLegendaryNPC(new GOTEntityHodor(world), world, -2, 1, -2);
			spawnLegendaryNPC(new GOTEntityAryaStark(world), world, 2, 1, -2);
			spawnLegendaryNPC(new GOTEntityBranStark(world), world, -2, 1, 2);
			spawnLegendaryNPC(new GOTEntityRickonStark(world), world, 0, 1, 2);
			spawnLegendaryNPC(new GOTEntityLuwin(world), world, 0, 1, -2);
			spawnLegendaryNPC(new GOTEntityOsha(world), world, 2, 1, 0);
			return true;
		}
	}

	public static class WymanManderly extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
			return true;
		}
	}

	public static class XaroXhoanDaxos extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityXaroXhoanDaxos(world), world, 3, 1, 0);
			return true;
		}
	}

	public static class YohnRoyce extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityYohnRoyce(world), world, 2, 1, 0);
			return true;
		}
	}

	public static class YoungGriff extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
			return true;
		}
	}

	public static class Yunkai extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(new GOTEntityDaarioNaharis(world), world, -1, 1, 0);
			spawnLegendaryNPC(new GOTEntityRazdalMoEraz(world), world, -1, 1, 1);
			return true;
		}
	}
}