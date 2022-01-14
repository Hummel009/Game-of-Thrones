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
	public static GOTVillageGen[] f = new GOTVillageGen[68];

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

		f[0].affix(GOTWaypoint.Skane);
		f[0].affix(GOTWaypoint.Deepdown);
		f[0].affix(GOTWaypoint.DriftwoodHall);
		f[0].affix(GOTWaypoint.Kingshouse);
		f[1].affix(GOTWaypoint.Myr, -1, 0, 1);
		f[2].affix(GOTWaypoint.Harrenhal);
		f[2].affix(GOTWaypoint.Stygai);
		f[2].affix(GOTWaypoint.Ulos);
		f[2].affix(GOTWaypoint.Yeen);
		f[3].affix(GOTWaypoint.BarterBeach);
		f[3].affix(GOTWaypoint.Gogossos);
		f[3].affix(GOTWaypoint.Gorosh);
		f[3].affix(GOTWaypoint.Zamettar, 0, -1, 2);
		f[4].affix(GOTWaypoint.Doquu);
		f[4].affix(GOTWaypoint.Ebonhead);
		f[4].affix(GOTWaypoint.GoldenHead);
		f[4].affix(GOTWaypoint.Koj);
		f[4].affix(GOTWaypoint.LastLament);
		f[4].affix(GOTWaypoint.LizardHead);
		f[4].affix(GOTWaypoint.LotusPoint);
		f[4].affix(GOTWaypoint.Naath);
		f[4].affix(GOTWaypoint.Omboru);
		f[4].affix(GOTWaypoint.PearlPalace);
		f[4].affix(GOTWaypoint.RedFlowerVale);
		f[4].affix(GOTWaypoint.SweetLotusVale);
		f[4].affix(GOTWaypoint.TallTreesTown);
		f[4].affix(GOTWaypoint.Walano);
		f[4].affix(GOTWaypoint.Xon);
		f[5].affix(GOTWaypoint.Astapor, -1, 0, 1);
		f[5].affix(GOTWaypoint.Meereen, 0, -1, 2);
		f[5].affix(GOTWaypoint.NewGhis);
		f[5].affix(GOTWaypoint.Yunkai, -1, 0, 1);
		f[6].affix(GOTWaypoint.Astapor, -2, 0);
		f[6].affix(GOTWaypoint.NewGhis, -2, 0);
		f[6].affix(GOTWaypoint.Yunkai, -2, 0);
		f[7].affix(GOTWaypoint.Braavos, 0, -1, 2);
		f[8].affix(GOTWaypoint.Hornoth);
		f[8].affix(GOTWaypoint.Kyth);
		f[8].affix(GOTWaypoint.Rathylar);
		f[8].affix(GOTWaypoint.Sathar);
		f[8].affix(GOTWaypoint.VaesAthjikhari);
		f[8].affix(GOTWaypoint.VaesDiaf);
		f[8].affix(GOTWaypoint.VaesDothrak);
		f[8].affix(GOTWaypoint.VaesEfe);
		f[8].affix(GOTWaypoint.VaesGorqoyi);
		f[8].affix(GOTWaypoint.VaesGraddakh);
		f[8].affix(GOTWaypoint.VaesJini);
		f[8].affix(GOTWaypoint.VaesKhadokh);
		f[8].affix(GOTWaypoint.VaesKhewo);
		f[8].affix(GOTWaypoint.VaesLeisi);
		f[8].affix(GOTWaypoint.VaesLeqse);
		f[8].affix(GOTWaypoint.VaesMejhah);
		f[9].affix(GOTWaypoint.IbNor);
		f[9].affix(GOTWaypoint.IbSar);
		f[9].affix(GOTWaypoint.NewIbbish);
		f[9].affix(GOTWaypoint.PortOfIbben);
		f[10].affix(GOTWaypoint.FiveForts1);
		f[10].affix(GOTWaypoint.FiveForts2);
		f[10].affix(GOTWaypoint.FiveForts3);
		f[10].affix(GOTWaypoint.FiveForts4);
		f[10].affix(GOTWaypoint.FiveForts5, 0, 0, 3);
		f[11].affix(GOTWaypoint.Asabhad, -1, 0);
		f[11].affix(GOTWaypoint.Baoji, 0, 1, 2);
		f[11].affix(GOTWaypoint.Eijiang, 0, 1, 2);
		f[11].affix(GOTWaypoint.Faros);
		f[11].affix(GOTWaypoint.Huiji);
		f[11].affix(GOTWaypoint.Jinqi, -1, 0, 3);
		f[11].affix(GOTWaypoint.LengMa);
		f[11].affix(GOTWaypoint.LengYi);
		f[11].affix(GOTWaypoint.LesserMoraq);
		f[11].affix(GOTWaypoint.Lizhao, 1, 0);
		f[11].affix(GOTWaypoint.Manjin, 1, 0, 3);
		f[11].affix(GOTWaypoint.Marahai);
		f[11].affix(GOTWaypoint.PortMoraq);
		f[11].affix(GOTWaypoint.SiQo, 1, 0);
		f[11].affix(GOTWaypoint.Tiqui, 0, -1);
		f[11].affix(GOTWaypoint.TraderTown, 0, -1);
		f[11].affix(GOTWaypoint.Turrani);
		f[11].affix(GOTWaypoint.Vahar);
		f[11].affix(GOTWaypoint.Vaibei, 0, -1);
		f[11].affix(GOTWaypoint.Yibin, -1, 0, 3);
		f[11].affix(GOTWaypoint.Yin, 0, 1, 2);
		f[11].affix(GOTWaypoint.Yunnan, 1, 0, 1);
		f[11].affix(GOTWaypoint.Zabhad);
		f[12].affix(GOTWaypoint.Elyria);
		f[12].affix(GOTWaypoint.LittleValyria, 0, 1);
		f[12].affix(GOTWaypoint.Mantarys, 0, -1, 2);
		f[12].affix(GOTWaypoint.Selhorys, -1, 0, 1);
		f[12].affix(GOTWaypoint.Tolos);
		f[12].affix(GOTWaypoint.Valysar, -1, 0, 1);
		f[12].affix(GOTWaypoint.Volantis, -1, 0, 1);
		f[12].affix(GOTWaypoint.VolonTherys, 0, 1);
		f[13].affix(GOTWaypoint.Tyrosh);
		f[14].affix(GOTWaypoint.Hojdbaatar);
		f[15].affix(GOTWaypoint.Hesh, 1, 0, 3);
		f[15].affix(GOTWaypoint.Kosrak, 1, 0, 1);
		f[15].affix(GOTWaypoint.Lhazosh, -1, 0, 1);
		f[16].affix(GOTWaypoint.Lorath);
		f[16].affix(GOTWaypoint.Morosh);
		f[17].affix(GOTWaypoint.Lys);
		f[18].affix(GOTWaypoint.Meereen, 0, -2, 0);
		f[19].affix(GOTWaypoint.Norvos, 0, -1, 2);
		f[20].affix(GOTWaypoint.Pentos, -1, 0, 1);
		f[21].affix(GOTWaypoint.PortYhos, 0, 1);
		f[21].affix(GOTWaypoint.Qarkash, 0, 0);
		f[21].affix(GOTWaypoint.Qarth, 0, 1);
		f[22].affix(GOTWaypoint.Qohor, 0, -1, 2);
		f[23].affix(GOTWaypoint.Asshai, 2);
		f[24].affix(GOTWaypoint.Kadar);
		f[24].affix(GOTWaypoint.Nefer);
		f[25].affix(GOTWaypoint.EastPass);
		f[25].affix(GOTWaypoint.NorthPass);
		f[25].affix(GOTWaypoint.SouthPass);
		f[25].affix(GOTWaypoint.WestPass);
		f[26].affix(GOTWaypoint.ColdwaterBurn);
		f[26].affix(GOTWaypoint.GateOfTheMoon, 0, 1);
		f[26].affix(GOTWaypoint.GreyGlen);
		f[26].affix(GOTWaypoint.HeartsHome);
		f[26].affix(GOTWaypoint.IronOak);
		f[26].affix(GOTWaypoint.LongbowHall);
		f[26].affix(GOTWaypoint.Ninestars);
		f[26].affix(GOTWaypoint.OldAnchor);
		f[26].affix(GOTWaypoint.Pebble);
		f[26].affix(GOTWaypoint.Redfort);
		f[26].affix(GOTWaypoint.Runestone);
		f[26].affix(GOTWaypoint.Snakewood);
		f[26].affix(GOTWaypoint.Strongsong);
		f[26].affix(GOTWaypoint.ThePaps);
		f[26].affix(GOTWaypoint.Wickenden);
		f[26].affix(GOTWaypoint.WitchIsle);
		f[27].affix(GOTWaypoint.Sisterton);
		f[28].affix(GOTWaypoint.Gulltown);
		f[29].affix(GOTWaypoint.Briarwhite);
		f[30].affix(GOTWaypoint.Antlers);
		f[30].affix(GOTWaypoint.Brownhollow);
		f[30].affix(GOTWaypoint.DyreDen);
		f[30].affix(GOTWaypoint.Hayford, -1, 0);
		f[30].affix(GOTWaypoint.RooksRest, 0, -1);
		f[30].affix(GOTWaypoint.Rosby, 0, -1);
		f[30].affix(GOTWaypoint.Stokeworth);
		f[31].affix(GOTWaypoint.Duskendale, -2, 0, 3);
		f[32].affix(GOTWaypoint.KingsLanding, 1, 0, 1);
		f[33].affix(GOTWaypoint.KingsLanding, 2, 0, 1);
		f[34].affix(GOTWaypoint.Blackmont);
		f[34].affix(GOTWaypoint.GhostHill);
		f[34].affix(GOTWaypoint.Godsgrace);
		f[34].affix(GOTWaypoint.Hellholt);
		f[34].affix(GOTWaypoint.HighHermitage);
		f[34].affix(GOTWaypoint.Kingsgrave, -1, 0);
		f[34].affix(GOTWaypoint.Lemonwood);
		f[34].affix(GOTWaypoint.Saltshore);
		f[34].affix(GOTWaypoint.Sandstone);
		f[34].affix(GOTWaypoint.SkyReach, 0, 1);
		f[34].affix(GOTWaypoint.Spottswood);
		f[34].affix(GOTWaypoint.Starfall, 0, -1);
		f[34].affix(GOTWaypoint.Tor);
		f[34].affix(GOTWaypoint.Vaith);
		f[34].affix(GOTWaypoint.WaterGardens);
		f[34].affix(GOTWaypoint.Wyl, 0, -1);
		f[34].affix(GOTWaypoint.Yronwood, 1, 0);
		f[35].affix(GOTWaypoint.GhastonGrey);
		f[35].affix(GOTWaypoint.PlankyTown);
		f[35].affix(GOTWaypoint.Sunspear);
		f[36].affix(GOTWaypoint.ClawIsle);
		f[36].affix(GOTWaypoint.Dragonstone);
		f[36].affix(GOTWaypoint.Driftmark);
		f[36].affix(GOTWaypoint.HighTide);
		f[36].affix(GOTWaypoint.SharpPoint);
		f[36].affix(GOTWaypoint.Stonedance);
		f[36].affix(GOTWaypoint.SweetportSound);
		f[37].affix(GOTWaypoint.Hull);
		f[38].affix(GOTWaypoint.Hardhome);
		f[39].affix(GOTWaypoint.Moletown);
		f[39].affix(GOTWaypoint.Queenscrown);
		f[40].affix(GOTWaypoint.Highgarden, 0, -1);
		f[41].affix(GOTWaypoint.Kayce, 3);
		f[41].affix(GOTWaypoint.Lannisport, -1, 0, 3);
		f[42].affix(GOTWaypoint.Oxcross);
		f[43].affix(GOTWaypoint.Ashemark);
		f[43].affix(GOTWaypoint.Banefort);
		f[43].affix(GOTWaypoint.CasterlyRock, -1, 0);
		f[43].affix(GOTWaypoint.CleganesKeep);
		f[43].affix(GOTWaypoint.Cornfield);
		f[43].affix(GOTWaypoint.Crag);
		f[43].affix(GOTWaypoint.Crakehall, -1, 0);
		f[43].affix(GOTWaypoint.DeepDen);
		f[43].affix(GOTWaypoint.Faircastle);
		f[43].affix(GOTWaypoint.Feastfires);
		f[43].affix(GOTWaypoint.GoldenTooth, 0, 1);
		f[43].affix(GOTWaypoint.Greenfield);
		f[43].affix(GOTWaypoint.Hornvale);
		f[43].affix(GOTWaypoint.Kayce, 1, 0);
		f[43].affix(GOTWaypoint.Plumwood);
		f[43].affix(GOTWaypoint.Riverspring);
		f[43].affix(GOTWaypoint.Sarsfield, 0, -1);
		f[43].affix(GOTWaypoint.Silverhill);
		f[43].affix(GOTWaypoint.Wyndhall);
		f[44].affix(GOTWaypoint.Amberly);
		f[44].affix(GOTWaypoint.BlackHeart);
		f[44].affix(GOTWaypoint.Blackhaven, -1, 0);
		f[44].affix(GOTWaypoint.BroadArch);
		f[44].affix(GOTWaypoint.Bronzegate, 1, 0);
		f[44].affix(GOTWaypoint.DeatsHear);
		f[44].affix(GOTWaypoint.EvenfallHall);
		f[44].affix(GOTWaypoint.Fawntown);
		f[44].affix(GOTWaypoint.Felwood, 0, 1);
		f[44].affix(GOTWaypoint.Gallowsgrey);
		f[44].affix(GOTWaypoint.Grandview);
		f[44].affix(GOTWaypoint.Greenstone);
		f[44].affix(GOTWaypoint.HarvestHall);
		f[44].affix(GOTWaypoint.HaystackHall);
		f[44].affix(GOTWaypoint.Mistwood);
		f[44].affix(GOTWaypoint.Nightsong, 0, 1);
		f[44].affix(GOTWaypoint.Parchments);
		f[44].affix(GOTWaypoint.Poddingfield);
		f[44].affix(GOTWaypoint.RainHouse);
		f[44].affix(GOTWaypoint.SeaworthCastle);
		f[44].affix(GOTWaypoint.Stonehelm);
		f[44].affix(GOTWaypoint.StormsEnd);
		f[44].affix(GOTWaypoint.TudburyHoll);
		f[45].affix(GOTWaypoint.WeepingTown);
		f[46].affix(GOTWaypoint.FairMarket);
		f[46].affix(GOTWaypoint.Harroway);
		f[46].affix(GOTWaypoint.Pennytree);
		f[46].affix(GOTWaypoint.Sevenstreams);
		f[47].affix(GOTWaypoint.AcornHall);
		f[47].affix(GOTWaypoint.Atranta);
		f[47].affix(GOTWaypoint.Darry, 1, 0);
		f[47].affix(GOTWaypoint.Maidenpool, 1, 0);
		f[47].affix(GOTWaypoint.PinkmaidenCastle);
		f[47].affix(GOTWaypoint.RaventreeHall);
		f[47].affix(GOTWaypoint.Riverrun, 1, 0);
		f[47].affix(GOTWaypoint.Seagard, 0, -1);
		f[47].affix(GOTWaypoint.StoneHedge, 0, 1);
		f[47].affix(GOTWaypoint.WayfarerRest);
		f[48].affix(GOTWaypoint.Maidenpool, -1, 0, 3);
		f[48].affix(GOTWaypoint.Saltpans);
		f[48].affix(GOTWaypoint.Seagard, 0, 1, 2);
		f[48].affix(GOTWaypoint.StoneySept);
		f[49].affix(GOTWaypoint.CrossroadsInn);
		f[50].affix(GOTWaypoint.Oldtown, -1, 0, 3);
		f[51].affix(GOTWaypoint.Oxcross);
		f[52].affix(GOTWaypoint.Appleton, 0, -1);
		f[52].affix(GOTWaypoint.Ashford, 0, 1);
		f[52].affix(GOTWaypoint.Bandallon);
		f[52].affix(GOTWaypoint.Bitterbridge, 0, 1);
		f[52].affix(GOTWaypoint.Blackcrown);
		f[52].affix(GOTWaypoint.BrightwaterKeep);
		f[52].affix(GOTWaypoint.CiderHall);
		f[52].affix(GOTWaypoint.Coldmoat);
		f[52].affix(GOTWaypoint.DarkDell);
		f[52].affix(GOTWaypoint.Dunstonbury);
		f[52].affix(GOTWaypoint.GarnetGrove, -1, 0);
		f[52].affix(GOTWaypoint.Goldengrove);
		f[52].affix(GOTWaypoint.GrassyVale);
		f[52].affix(GOTWaypoint.Greenshield);
		f[52].affix(GOTWaypoint.Grimston);
		f[52].affix(GOTWaypoint.Hammerhal);
		f[52].affix(GOTWaypoint.HewettTown, 0, -1);
		f[52].affix(GOTWaypoint.Holyhall);
		f[52].affix(GOTWaypoint.Honeyholt);
		f[52].affix(GOTWaypoint.HornHill);
		f[52].affix(GOTWaypoint.IvyHall);
		f[52].affix(GOTWaypoint.Longtable);
		f[52].affix(GOTWaypoint.NewBarrel);
		f[52].affix(GOTWaypoint.OldOak, -1, 0);
		f[52].affix(GOTWaypoint.RedLake);
		f[52].affix(GOTWaypoint.Ring);
		f[52].affix(GOTWaypoint.Southshield);
		f[52].affix(GOTWaypoint.SunHouse, 0, -1);
		f[52].affix(GOTWaypoint.Uplands);
		f[52].affix(GOTWaypoint.Whitegrove, -1, 0);
		f[53].affix(GOTWaypoint.Appleton, 0, 1, 2);
		f[53].affix(GOTWaypoint.Ashford, 0, -1);
		f[53].affix(GOTWaypoint.HewettTown, 0, 1);
		f[53].affix(GOTWaypoint.Ryamsport);
		f[53].affix(GOTWaypoint.Smithyton, 0, 1, 2);
		f[53].affix(GOTWaypoint.StarfishHarbor);
		f[53].affix(GOTWaypoint.Tumbleton, 0, -1);
		f[53].affix(GOTWaypoint.Vinetown);
		f[54].affix(GOTWaypoint.BaelishKeep);
		f[54].affix(GOTWaypoint.HightowerLitehouse);
		f[54].affix(GOTWaypoint.RamseyTower);
		f[54].affix(GOTWaypoint.Standfast);
		f[54].affix(GOTWaypoint.ThreeTowers, -1, -1, 1);
		f[54].affix(GOTWaypoint.ThreeTowers, -1, 0, 1);
		f[54].affix(GOTWaypoint.ThreeTowers, -1, 1, 1);
		f[54].affix(GOTWaypoint.TowerOfGlimmering);
		f[54].affix(GOTWaypoint.TwinsLeft, 1, 0, 1);
		f[54].affix(GOTWaypoint.TwinsRight, -2, 0, 3);
		f[55].affix(GOTWaypoint.WhiteHarbour, 1);
		f[56].affix(GOTWaypoint.Barrowtown, 0, 1, 2);
		f[57].affix(GOTWaypoint.BlackPool);
		f[57].affix(GOTWaypoint.DeepwoodMotte);
		f[57].affix(GOTWaypoint.DeepwoodMotte);
		f[57].affix(GOTWaypoint.Dreadfort);
		f[57].affix(GOTWaypoint.FlintsFinger);
		f[57].affix(GOTWaypoint.Goldgrass, 0, 1);
		f[57].affix(GOTWaypoint.Highpoint);
		f[57].affix(GOTWaypoint.Hornwood);
		f[57].affix(GOTWaypoint.Ironrath);
		f[57].affix(GOTWaypoint.Karhold);
		f[57].affix(GOTWaypoint.LastHearth);
		f[57].affix(GOTWaypoint.MoatKailin);
		f[57].affix(GOTWaypoint.MormontsKeep);
		f[57].affix(GOTWaypoint.OldCastle);
		f[57].affix(GOTWaypoint.RamsGate);
		f[57].affix(GOTWaypoint.RillwaterCrossing);
		f[57].affix(GOTWaypoint.RisvellsCastle, 0, 1);
		f[57].affix(GOTWaypoint.ServinsCastle, -1, 0);
		f[57].affix(GOTWaypoint.TorhensSquare);
		f[57].affix(GOTWaypoint.WidowsWatch);
		f[57].affix(GOTWaypoint.Winterfell, 1);
		f[58].affix(GOTWaypoint.GreywaterWatch);
		f[59].affix(GOTWaypoint.VictarionLanding);
		f[60].affix(GOTWaypoint.Pebbleton);
		f[61].affix(GOTWaypoint.Blackhaven);
		f[61].affix(GOTWaypoint.Blacktyde);
		f[61].affix(GOTWaypoint.CorpseLake);
		f[61].affix(GOTWaypoint.CrowSpikeKeep);
		f[61].affix(GOTWaypoint.Downdelving);
		f[61].affix(GOTWaypoint.DrummCastle);
		f[61].affix(GOTWaypoint.GreyGarden);
		f[61].affix(GOTWaypoint.Hammerhorn);
		f[61].affix(GOTWaypoint.HarlawHall);
		f[61].affix(GOTWaypoint.HarridanHill);
		f[61].affix(GOTWaypoint.IronHolt);
		f[61].affix(GOTWaypoint.LonelyLight);
		f[61].affix(GOTWaypoint.MyreCastle);
		f[61].affix(GOTWaypoint.Orkwood);
		f[61].affix(GOTWaypoint.Pyke);
		f[61].affix(GOTWaypoint.Saltcliffe);
		f[61].affix(GOTWaypoint.SealskinPoint);
		f[61].affix(GOTWaypoint.Shatterstone);
		f[61].affix(GOTWaypoint.SparrCastle);
		f[61].affix(GOTWaypoint.Stonehouse);
		f[61].affix(GOTWaypoint.StonetreeCastle);
		f[61].affix(GOTWaypoint.Sunderly);
		f[61].affix(GOTWaypoint.TawneyCastle);
		f[61].affix(GOTWaypoint.TenTowers);
		f[61].affix(GOTWaypoint.Volmark);
		f[62].affix(GOTWaypoint.Lordsport);
		f[62].affix(GOTWaypoint.RedHaven);
		f[63].affix(GOTWaypoint.Castamere);
		f[63].affix(GOTWaypoint.Goldenhill);
		f[63].affix(GOTWaypoint.GreyironCastle);
		f[63].affix(GOTWaypoint.HoareCastle);
		f[63].affix(GOTWaypoint.HoareKeep);
		f[63].affix(GOTWaypoint.HoggHall);
		f[63].affix(GOTWaypoint.HollardCastle);
		f[63].affix(GOTWaypoint.OldStones);
		f[63].affix(GOTWaypoint.Summerhall);
		f[63].affix(GOTWaypoint.TarbeckHall);
		f[63].affix(GOTWaypoint.TowerOfJoy);
		f[63].affix(GOTWaypoint.Whispers);
		f[63].affix(GOTWaypoint.WhiteWalls);
		f[64].affix(GOTWaypoint.CrastersKeep);
		f[65].affix(GOTWaypoint.CastleBlack);
		f[66].affix(GOTWaypoint.ShadowTower);
		f[67].affix(GOTWaypoint.EastWatch);
		f[68].affix(GOTWaypoint.CastleBlack, 0, -1);
		
		for (int i = 0; i <= 68; i++) {
			biome.decorator.addFixedVillage(f[i]);
		}
	}

	public static void onInit() {
		structures.put(GOTWaypoint.Aboba, new GOTFixer.NightKing());
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
		structures.put(GOTWaypoint.TwinsLeft.shift(1, 0), new GOTFixer.TwinsLeft());
		structures.put(GOTWaypoint.TwinsRight.shift(-2, 0), new GOTFixer.WalderFrey());
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
			spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
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
			spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
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
			spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
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
			spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
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