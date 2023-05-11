package got.client.render;

import got.client.effect.GOTEntityAlignmentBonus;
import got.client.effect.GOTEntityDeadMarshFace;
import got.client.effect.GOTEntitySwordCommandMarker;
import got.client.render.animal.*;
import got.client.render.npc.*;
import got.client.render.other.*;
import got.common.database.GOTRegistry;
import got.common.entity.animal.*;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.*;
import got.common.entity.essos.asshai.GOTEntityAsshaiMan;
import got.common.entity.essos.braavos.GOTEntityBraavosMan;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarMan;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarSlave;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarUnsullied;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.essos.ibben.GOTEntityIbbenMan;
import got.common.entity.essos.ibben.GOTEntityIbbenWarrior;
import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.GOTEntityBuGai;
import got.common.entity.essos.legendary.quest.GOTEntityDaenerysTargaryen;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import got.common.entity.essos.legendary.quest.GOTEntityMellario;
import got.common.entity.essos.legendary.trader.GOTEntityIllyrioMopatis;
import got.common.entity.essos.legendary.trader.GOTEntityMoqorro;
import got.common.entity.essos.legendary.trader.GOTEntityTychoNestoris;
import got.common.entity.essos.legendary.trader.GOTEntityXaroXhoanDaxos;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.essos.lhazar.GOTEntityLhazarMan;
import got.common.entity.essos.lorath.GOTEntityLorathMan;
import got.common.entity.essos.lys.GOTEntityLysMan;
import got.common.entity.essos.lys.GOTEntityLysSlave;
import got.common.entity.essos.mossovy.GOTEntityMarshWraith;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import got.common.entity.essos.mossovy.GOTEntityMossovyWerewolf;
import got.common.entity.essos.myr.GOTEntityMyrMan;
import got.common.entity.essos.myr.GOTEntityMyrSlave;
import got.common.entity.essos.norvos.GOTEntityNorvosMan;
import got.common.entity.essos.pentos.GOTEntityPentosMan;
import got.common.entity.essos.qarth.GOTEntityQarthMan;
import got.common.entity.essos.qarth.GOTEntityQarthWarlock;
import got.common.entity.essos.qohor.GOTEntityQohorMan;
import got.common.entity.essos.qohor.GOTEntityQohorUnsullied;
import got.common.entity.essos.tyrosh.GOTEntityTyroshMan;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSlave;
import got.common.entity.essos.volantis.GOTEntityVolantisMan;
import got.common.entity.essos.volantis.GOTEntityVolantisSlave;
import got.common.entity.essos.yiti.GOTEntityYiTiMan;
import got.common.entity.other.*;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosMan;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosShaman;
import got.common.entity.sothoryos.summer.GOTEntitySummerMan;
import got.common.entity.westeros.*;
import got.common.entity.westeros.arryn.GOTEntityArrynMan;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsAlchemist;
import got.common.entity.westeros.crownlands.GOTEntityCrownlandsMan;
import got.common.entity.westeros.dorne.GOTEntityDorneMan;
import got.common.entity.westeros.dragonstone.GOTEntityDragonstoneMan;
import got.common.entity.westeros.gift.GOTEntityGiftMan;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.entity.westeros.hillmen.GOTEntityHillmanWarrior;
import got.common.entity.westeros.ice.GOTEntityIceSpider;
import got.common.entity.westeros.ice.GOTEntityWhiteWalker;
import got.common.entity.westeros.ice.GOTEntityWight;
import got.common.entity.westeros.ice.GOTEntityWightGiant;
import got.common.entity.westeros.ironborn.GOTEntityIronbornMan;
import got.common.entity.westeros.ironborn.GOTEntityIronbornPriest;
import got.common.entity.westeros.legendary.GOTEntityCrasterWife;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.entity.westeros.north.GOTEntityNorthMan;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillman;
import got.common.entity.westeros.north.hillmen.GOTEntityNorthHillmanWarrior;
import got.common.entity.westeros.reach.GOTEntityReachMan;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsMan;
import got.common.entity.westeros.stormlands.GOTEntityStormlandsMan;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsMan;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.entity.westeros.wildling.GOTEntityWildling;
import got.common.entity.westeros.wildling.thenn.GOTEntityThenn;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennBerserker;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;

import java.util.HashMap;
import java.util.Map;

public class GOTRender {
	public static Map<Class<? extends Entity>, Render> renders = new HashMap<>();

	public static void onInit() {
		renders.put(GOTEntityBlizzard.class, new GOTRenderBlizzard());
		renders.put(GOTEntityShryke.class, new GOTRenderFactionNPCLayered("essos/shryke"));
		renders.put(GOTEntityHillman.class, new GOTRenderFactionNPCLayered("westeros/wild"));
		renders.put(GOTEntityIbbenMan.class, new GOTRenderFactionNPCLayered("essos/ibben"));
		renders.put(GOTEntityNorthHillman.class, new GOTRenderFactionNPCLayered("westeros/wild"));
		renders.put(GOTEntityArrynMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntityAsshaiMan.class, new GOTRenderFactionNPC("essos/asshai"));
		renders.put(GOTEntityAsshaiArchmag.class, new GOTRenderFactionNPC("essos/asshai"));
		renders.put(GOTEntityBraavosMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityCrasterWife.class, new GOTRenderFactionNPC("westeros/wild"));
		renders.put(GOTEntityCrownlandsMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntityDorneMan.class, new GOTRenderFactionNPC("westeros/dorne"));
		renders.put(GOTEntityDothraki.class, new GOTRenderFactionNPC("essos/nomad"));
		renders.put(GOTEntityDragonstoneMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntityGhiscarMan.class, new GOTRenderFactionNPC("essos/ghiscar"));
		renders.put(GOTEntityGhiscarSlave.class, new GOTRenderFactionNPC("essos/slave"));
		renders.put(GOTEntityGiftMan.class, new GOTRenderFactionNPC("westeros/gift"));
		renders.put(GOTEntityGoldenMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityHillmanWarrior.class, new GOTRenderFactionNPC("westeros/wild"));
		renders.put(GOTEntityIbbenWarrior.class, new GOTRenderFactionNPC("essos/ibben"));
		renders.put(GOTEntityIronbornMan.class, new GOTRenderFactionNPC("westeros/ironborn"));
		renders.put(GOTEntityJogos.class, new GOTRenderFactionNPC("essos/jogos"));
		renders.put(GOTEntityLhazarMan.class, new GOTRenderFactionNPC("essos/nomad"));
		renders.put(GOTEntityLorathMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityLysMan.class, new GOTRenderFactionNPC("essos/violet"));
		renders.put(GOTEntityLysSlave.class, new GOTRenderFactionNPC("essos/slave"));
		renders.put(GOTEntityMyrMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityMyrSlave.class, new GOTRenderFactionNPC("essos/slave"));
		renders.put(GOTEntityNorthMan.class, new GOTRenderFactionNPC("westeros/north"));
		renders.put(GOTEntityNorthHillmanWarrior.class, new GOTRenderFactionNPC("westeros/wild"));
		renders.put(GOTEntityNorvosMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityPentosMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityQarthMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityQohorMan.class, new GOTRenderFactionNPC("essos/free"));
		renders.put(GOTEntityReachMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntityRiverlandsMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntitySothoryosMan.class, new GOTRenderFactionNPC("sothoryos/sothoryos"));
		renders.put(GOTEntityStormlandsMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntitySummerMan.class, new GOTRenderFactionNPC("sothoryos/summer"));
		renders.put(GOTEntityThenn.class, new GOTRenderFactionNPC("westeros/thenn"));
		renders.put(GOTEntityTyroshMan.class, new GOTRenderFactionNPC("essos/colored"));
		renders.put(GOTEntityTyroshSlave.class, new GOTRenderFactionNPC("essos/slave"));
		renders.put(GOTEntityVolantisMan.class, new GOTRenderFactionNPC("essos/violet"));
		renders.put(GOTEntityVolantisSlave.class, new GOTRenderFactionNPC("essos/slave"));
		renders.put(GOTEntityWesterlandsMan.class, new GOTRenderFactionNPC("westeros/westeros"));
		renders.put(GOTEntityWight.class, new GOTRenderFactionNPC("westeros/ice/wight"));
		renders.put(GOTEntityWildling.class, new GOTRenderFactionNPC("westeros/wild"));
		renders.put(GOTEntityYiTiMan.class, new GOTRenderFactionNPC("essos/yiti"));
		renders.put(GOTEntityMossovyMan.class, new GOTRenderFactionNPC("essos/mossovy"));
		renders.put(GOTEntitySepton.class, new GOTRenderFactionNPCLayered("westeros/westeros", "septon"));
		renders.put(GOTEntityRedPriest.class, new GOTRenderFactionNPCLayered("essos/asshai", "priest"));
		renders.put(GOTEntityIronbornPriest.class, new GOTRenderFactionNPCLayered("westeros/ironborn", "priest"));
		renders.put(GOTEntityIfekevron.class, new GOTRenderFactionNPCLayered("essos/ibben"));
		renders.put(GOTEntityMercenary.class, new GOTRenderFactionNPCLayered("westeros/westeros", "mercenary"));
		renders.put(GOTEntitySothoryosShaman.class, new GOTRenderFactionNPCLayered("sothoryos/sothoryos", "shaman"));
		renders.put(GOTEntityAddamMarbrand.class, new GOTRenderLegendaryNPC("addam_marbrand"));
		renders.put(GOTEntityAemonTargaryen.class, new GOTRenderLegendaryNPC("aemon"));
		renders.put(GOTEntityAeronGreyjoy.class, new GOTRenderLegendaryNPC("aeron_greyjoy"));
		renders.put(GOTEntityAmoryLorch.class, new GOTRenderLegendaryNPC("amory_lorch"));
		renders.put(GOTEntityAndersYronwood.class, new GOTRenderLegendaryNPC("anders_yronwood"));
		renders.put(GOTEntityAndrikTheUnsmilling.class, new GOTRenderLegendaryNPC("andrik_the_unsmilling"));
		renders.put(GOTEntityAnyaWaynwood.class, new GOTRenderLegendaryNPC("anya_waynwood"));
		renders.put(GOTEntityArdrianCeltigar.class, new GOTRenderLegendaryNPC("ardrian_celtigar"));
		renders.put(GOTEntityAreoHotah.class, new GOTRenderLegendaryNPC("areo_hotah", 1.2f));
		renders.put(GOTEntityArianneMartell.class, new GOTRenderLegendaryNPC("arianne_martell"));
		renders.put(GOTEntityAryaStark.class, new GOTRenderLegendaryNPC("arya_stark", 0.75f));
		renders.put(GOTEntityAuraneWaters.class, new GOTRenderLegendaryNPC("aurane_waters"));
		renders.put(GOTEntityBaelorBlacktyde.class, new GOTRenderLegendaryNPC("baelor_blacktyde"));
		renders.put(GOTEntityBarbreyDustin.class, new GOTRenderLegendaryNPC("barbrey_dustin"));
		renders.put(GOTEntityBarristanSelmy.class, new GOTRenderLegendaryNPC("barristan_selmy"));
		renders.put(GOTEntityBericDayne.class, new GOTRenderLegendaryNPC("beric_dayne"));
		renders.put(GOTEntityBlackWalderFrey.class, new GOTRenderLegendaryNPC("black_walder_frey"));
		renders.put(GOTEntityBranStark.class, new GOTRenderLegendaryNPC("bran_stark", 0.75f));
		renders.put(GOTEntityBronn.class, new GOTRenderLegendaryNPC("bronn"));
		renders.put(GOTEntityCatelynStark.class, new GOTRenderLegendaryNPC("catelyn_stark"));
		renders.put(GOTEntityCerseiLannister.class, new GOTRenderLegendaryNPC("cersei_lannister"));
		renders.put(GOTEntityClementPiper.class, new GOTRenderLegendaryNPC("clement_piper"));
		renders.put(GOTEntityCleyCerwyn.class, new GOTRenderLegendaryNPC("cley_cerwyn"));
		renders.put(GOTEntityDaarioNaharis.class, new GOTRenderLegendaryNPC("daario_naharis"));
		renders.put(GOTEntityDaenerysTargaryen.class, new GOTRenderLegendaryNPC("daenerys_targaryen"));
		renders.put(GOTEntityDagmer.class, new GOTRenderLegendaryNPC("dagmer"));
		renders.put(GOTEntityDavenLannister.class, new GOTRenderLegendaryNPC("daven_lannister"));
		renders.put(GOTEntityDoranMartell.class, new GOTRenderLegendaryNPC("doran_martell"));
		renders.put(GOTEntityDunstanDrumm.class, new GOTRenderLegendaryNPC("dunstan_drumm"));
		renders.put(GOTEntityEbrose.class, new GOTRenderLegendaryNPC("ebrose"));
		renders.put(GOTEntityEldonEstermont.class, new GOTRenderLegendaryNPC("eldon_estermont"));
		renders.put(GOTEntityEllaryaSand.class, new GOTRenderLegendaryNPC("ellarya_sand"));
		renders.put(GOTEntityErikIronmaker.class, new GOTRenderLegendaryNPC("erik_ironmaker"));
		renders.put(GOTEntityForleyPrester.class, new GOTRenderLegendaryNPC("forley_prester"));
		renders.put(GOTEntityFranklynFowler.class, new GOTRenderLegendaryNPC("franklyn_fowler"));
		renders.put(GOTEntityGarlanTyrell.class, new GOTRenderLegendaryNPC("garlan_tyrell"));
		renders.put(GOTEntityGendryBaratheon.class, new GOTRenderLegendaryNPC("gendry_baratheon"));
		renders.put(GOTEntityGeroldDayne.class, new GOTRenderLegendaryNPC("gerold_dayne"));
		renders.put(GOTEntityGeroldGrafton.class, new GOTRenderLegendaryNPC("gerold_grafton"));
		renders.put(GOTEntityGilwoodHunter.class, new GOTRenderLegendaryNPC("gilwood_hunter"));
		renders.put(GOTEntityGoroldGoodbrother.class, new GOTRenderLegendaryNPC("gorold_goodbrother"));
		renders.put(GOTEntityGreyWorm.class, new GOTRenderLegendaryNPC("grey_worm"));
		renders.put(GOTEntityGulianSwann.class, new GOTRenderLegendaryNPC("gulian_swann"));
		renders.put(GOTEntityGylbertFarwynd.class, new GOTRenderLegendaryNPC("gylbert_farwynd"));
		renders.put(GOTEntityHarmenUller.class, new GOTRenderLegendaryNPC("harmen_uller"));
		renders.put(GOTEntityHarmune.class, new GOTRenderLegendaryNPC("harmune"));
		renders.put(GOTEntityHarrasHarlaw.class, new GOTRenderLegendaryNPC("harras_harlaw"));
		renders.put(GOTEntityHarroldHardyng.class, new GOTRenderLegendaryNPC("harrold_hardyng"));
		renders.put(GOTEntityHarysSwyft.class, new GOTRenderLegendaryNPC("harys_swyft"));
		renders.put(GOTEntityHelmanTallhart.class, new GOTRenderLegendaryNPC("helman_tallhart"));
		renders.put(GOTEntityHizdahrZoLoraq.class, new GOTRenderLegendaryNPC("hizdahr_zo_loraq"));
		renders.put(GOTEntityHodor.class, new GOTRenderLegendaryNPC("hodor", 1.3f));
		renders.put(GOTEntityHortonRedfort.class, new GOTRenderLegendaryNPC("horton_redfort"));
		renders.put(GOTEntityHosterTully.class, new GOTRenderLegendaryNPC("hoster_tully"));
		renders.put(GOTEntityHummel009.class, new GOTRenderLegendaryNPC("hummel009"));
		renders.put(GOTEntityIlynPayne.class, new GOTRenderLegendaryNPC("ilyn_payne"));
		renders.put(GOTEntityJanosSlynt.class, new GOTRenderLegendaryNPC("janos_slynt"));
		renders.put(GOTEntityJaqenHghar.class, new GOTRenderLegendaryNPC("jaqen_hghar"));
		renders.put(GOTEntityJasonMallister.class, new GOTRenderLegendaryNPC("jason_mallister"));
		renders.put(GOTEntityJonConnington.class, new GOTRenderLegendaryNPC("jon_connington"));
		renders.put(GOTEntityJonosBracken.class, new GOTRenderLegendaryNPC("jonos_bracken"));
		renders.put(GOTEntityJorahMormont.class, new GOTRenderLegendaryNPC("jorah_mormont"));
		renders.put(GOTEntityKevanLannister.class, new GOTRenderLegendaryNPC("kevan_lannister"));
		renders.put(GOTEntityTugarKhan.class, new GOTRenderLegendaryNPC("tugar_khan", 1.3f));
		renders.put(GOTEntityLeoLefford.class, new GOTRenderLegendaryNPC("leo_lefford"));
		renders.put(GOTEntityLeytonHightower.class, new GOTRenderLegendaryNPC("leyton_hightower"));
		renders.put(GOTEntityLorasTyrell.class, new GOTRenderLegendaryNPC("loras_tyrell"));
		renders.put(GOTEntityLotharFrey.class, new GOTRenderLegendaryNPC("lothar_frey"));
		renders.put(GOTEntityLuwin.class, new GOTRenderLegendaryNPC("luwin"));
		renders.put(GOTEntityLyleCrakehall.class, new GOTRenderLegendaryNPC("lyle_crakehall"));
		renders.put(GOTEntityLynCorbray.class, new GOTRenderLegendaryNPC("lyn_corbray"));
		renders.put(GOTEntityLysaArryn.class, new GOTRenderLegendaryNPC("lysa_arryn"));
		renders.put(GOTEntityManfreyMartell.class, new GOTRenderLegendaryNPC("manfrey_martell"));
		renders.put(GOTEntityMargaeryTyrell.class, new GOTRenderLegendaryNPC("margaery_tyrell"));
		renders.put(GOTEntityMaronVolmark.class, new GOTRenderLegendaryNPC("maron_volmark"));
		renders.put(GOTEntityMathisRowan.class, new GOTRenderLegendaryNPC("mathis_rowan"));
		renders.put(GOTEntityMelisandra.class, new GOTRenderLegendaryNPC("melisandra"));
		renders.put(GOTEntityMellario.class, new GOTRenderLegendaryNPC("mellario"));
		renders.put(GOTEntityMerynTrant.class, new GOTRenderLegendaryNPC("meryn_trant"));
		renders.put(GOTEntityMissandei.class, new GOTRenderLegendaryNPC("missandei"));
		renders.put(GOTEntityMonfordVelaryon.class, new GOTRenderLegendaryNPC("monford_velaryon"));
		renders.put(GOTEntityMoribaldChester.class, new GOTRenderLegendaryNPC("moribald_chester"));
		renders.put(GOTEntityMullin.class, new GOTRenderLegendaryNPC("mullin"));
		renders.put(GOTEntityMyrcellaBaratheon.class, new GOTRenderLegendaryNPC("myrcella_baratheon", 0.9f));
		renders.put(GOTEntityNightKing.class, new GOTRenderLegendaryNPC("night_king", 1.1f));
		renders.put(GOTEntityOberynMartell.class, new GOTRenderLegendaryNPC("oberyn_martell"));
		renders.put(GOTEntityOlennaTyrell.class, new GOTRenderLegendaryNPC("olenna_tyrell"));
		renders.put(GOTEntityOrtonMerryweather.class, new GOTRenderLegendaryNPC("orton_merryweather"));
		renders.put(GOTEntityPaxterRedwyne.class, new GOTRenderLegendaryNPC("paxter_redwyne"));
		renders.put(GOTEntityPetyrBaelish.class, new GOTRenderLegendaryNPC("petyr_baelish"));
		renders.put(GOTEntityPodrickPayne.class, new GOTRenderLegendaryNPC("podrick_payne"));
		renders.put(GOTEntityPolliver.class, new GOTRenderLegendaryNPC("polliver"));
		renders.put(GOTEntityPycelle.class, new GOTRenderLegendaryNPC("pycelle"));
		renders.put(GOTEntityQuennRoxton.class, new GOTRenderLegendaryNPC("quenn_roxton"));
		renders.put(GOTEntityQuentenBanefort.class, new GOTRenderLegendaryNPC("quenten_banefort"));
		renders.put(GOTEntityQuentynMartell.class, new GOTRenderLegendaryNPC("quentyn_martell"));
		renders.put(GOTEntityQuentynQorgyle.class, new GOTRenderLegendaryNPC("quentyn_qorgyle"));
		renders.put(GOTEntityQyburn.class, new GOTRenderLegendaryNPC("qyburn"));
		renders.put(GOTEntityRamsayBolton.class, new GOTRenderLegendaryNPC("ramsay_bolton"));
		renders.put(GOTEntityRazdalMoEraz.class, new GOTRenderLegendaryNPC("razdal_mo_eraz"));
		renders.put(GOTEntityRenlyBaratheon.class, new GOTRenderLegendaryNPC("renly_baratheon"));
		renders.put(GOTEntityRickonStark.class, new GOTRenderLegendaryNPC("rickon_stark", 0.65f));
		renders.put(GOTEntityRobinArryn.class, new GOTRenderLegendaryNPC("robin_arryn", 0.75f));
		renders.put(GOTEntityRodrikCassel.class, new GOTRenderLegendaryNPC("rodrik_cassel"));
		renders.put(GOTEntityRodrikHarlaw.class, new GOTRenderLegendaryNPC("rodrik_harlaw"));
		renders.put(GOTEntityRodrikRyswell.class, new GOTRenderLegendaryNPC("rodrik_ryswell"));
		renders.put(GOTEntitySalladhorSaan.class, new GOTRenderLegendaryNPC("salladhor_saan"));
		renders.put(GOTEntitySansaStark.class, new GOTRenderLegendaryNPC("sansa_stark"));
		renders.put(GOTEntitySebastonFarman.class, new GOTRenderLegendaryNPC("sebaston_farman"));
		renders.put(GOTEntitySelwynTarth.class, new GOTRenderLegendaryNPC("selwyn_tarth"));
		renders.put(GOTEntitySelyseBaratheon.class, new GOTRenderLegendaryNPC("selyse_baratheon"));
		renders.put(GOTEntityShae.class, new GOTRenderLegendaryNPC("shae"));
		renders.put(GOTEntityShireenBaratheon.class, new GOTRenderLegendaryNPC("shireen_baratheon", 0.75f));
		renders.put(GOTEntityStannisBaratheon.class, new GOTRenderLegendaryNPC("stannis_baratheon"));
		renders.put(GOTEntitySymondTempleton.class, new GOTRenderLegendaryNPC("symond_templeton"));
		renders.put(GOTEntityThreeEyedRaven.class, new GOTRenderLegendaryNPC("three_eyed_raven"));
		renders.put(GOTEntityTobhoMott.class, new GOTRenderLegendaryNPC("tobho_mott"));
		renders.put(GOTEntityTommenBaratheon.class, new GOTRenderLegendaryNPC("tommen_baratheon", 0.75f));
		renders.put(GOTEntityTrystaneMartell.class, new GOTRenderLegendaryNPC("trystane_martell", 0.9f));
		renders.put(GOTEntityTychoNestoris.class, new GOTRenderLegendaryNPC("tycho_nestoris"));
		renders.put(GOTEntityTytosBlackwood.class, new GOTRenderLegendaryNPC("tytos_blackwood"));
		renders.put(GOTEntityTytosBrax.class, new GOTRenderLegendaryNPC("tytos_brax"));
		renders.put(GOTEntityTywinLannister.class, new GOTRenderLegendaryNPC("tywin_lannister"));
		renders.put(GOTEntityVargoHoat.class, new GOTRenderLegendaryNPC("vargo_hoat"));
		renders.put(GOTEntityVarys.class, new GOTRenderLegendaryNPC("varys"));
		renders.put(GOTEntityWillasTyrell.class, new GOTRenderLegendaryNPC("willas_tyrell"));
		renders.put(GOTEntityWilliamMooton.class, new GOTRenderLegendaryNPC("william_mooton"));
		renders.put(GOTEntityYgritte.class, new GOTRenderLegendaryNPC("ygritte"));
		renders.put(GOTEntityYohnRoyce.class, new GOTRenderLegendaryNPC("yohn_royce"));
		renders.put(GOTEntityYoren.class, new GOTRenderLegendaryNPC("yoren"));
		renders.put(GOTEntityYoungGriff.class, new GOTRenderLegendaryNPC("young_griff", 0.9f));
		renders.put(GOTEntityAlliserThorne.class, new GOTRenderLegendaryNPCLayered("alliser_thorne"));
		renders.put(GOTEntityBalonGreyjoy.class, new GOTRenderLegendaryNPCLayered("balon_greyjoy"));
		renders.put(GOTEntityBenedarBelmore.class, new GOTRenderLegendaryNPCLayered("benedar_belmore"));
		renders.put(GOTEntityBenjenStark.class, new GOTRenderLegendaryNPCLayered("benjen_stark"));
		renders.put(GOTEntityBericDondarrion.class, new GOTRenderLegendaryNPCLayered("beric_dondarrion"));
		renders.put(GOTEntityBrienneTarth.class, new GOTRenderLegendaryNPCLayered("brienne_tarth", 1.2f));
		renders.put(GOTEntityBryndenTully.class, new GOTRenderLegendaryNPCLayered("brynden_tully"));
		renders.put(GOTEntityBuGai.class, new GOTRenderLegendaryNPCLayered("bu_gai"));
		renders.put(GOTEntityCotterPyke.class, new GOTRenderLegendaryNPCLayered("cotter_pyke"));
		renders.put(GOTEntityCraster.class, new GOTRenderLegendaryNPCLayered("craster"));
		renders.put(GOTEntityDavosSeaworth.class, new GOTRenderLegendaryNPCLayered("davos_seaworth"));
		renders.put(GOTEntityDenysMallister.class, new GOTRenderLegendaryNPCLayered("denys_mallister"));
		renders.put(GOTEntityEdd.class, new GOTRenderLegendaryNPCLayered("edd"));
		renders.put(GOTEntityEdmureTully.class, new GOTRenderLegendaryNPCLayered("edmure_tully"));
		renders.put(GOTEntityEuronGreyjoy.class, new GOTRenderLegendaryNPCLayered("euron_greyjoy"));
		renders.put(GOTEntityHarryStrickland.class, new GOTRenderLegendaryNPCLayered("harry_strickland"));
		renders.put(GOTEntityHighSepton.class, new GOTRenderLegendaryNPCLayered("high_septon"));
		renders.put(GOTEntityHotPie.class, new GOTRenderLegendaryNPCLayered("hot_pie", 0.9f));
		renders.put(GOTEntityHowlandReed.class, new GOTRenderLegendaryNPCLayered("howland_reed"));
		renders.put(GOTEntityIllyrioMopatis.class, new GOTRenderLegendaryNPCLayered("illyrio_mopatis"));
		renders.put(GOTEntityJaimeLannister.class, new GOTRenderLegendaryNPCLayered("jaime_lannister"));
		renders.put(GOTEntityJeorMormont.class, new GOTRenderLegendaryNPCLayered("jeor_mormont"));
		renders.put(GOTEntityJoffreyBaratheon.class, new GOTRenderLegendaryNPCLayered("joffrey_baratheon", 0.9f));
		renders.put(GOTEntityJohnUmber.class, new GOTRenderLegendaryNPCLayered("john_umber", 1.2f));
		renders.put(GOTEntityJonSnow.JonSnowLife1.class, new GOTRenderLegendaryNPCLayered("jon_snow"));
		renders.put(GOTEntityJonSnow.JonSnowLife2.class, new GOTRenderLegendaryNPCLayered("jon_snow_north"));
		renders.put(GOTEntityKraznysMoNakloz.class, new GOTRenderLegendaryNPCLayered("kraznys_mo_nakloz"));
		renders.put(GOTEntityMaceTyrell.class, new GOTRenderLegendaryNPCLayered("mace_tyrell"));
		renders.put(GOTEntityMaegeMormont.class, new GOTRenderLegendaryNPCLayered("maege_mormont"));
		renders.put(GOTEntityManceRayder.class, new GOTRenderLegendaryNPCLayered("mance_rayder"));
		renders.put(GOTEntityMatthosSeaworth.class, new GOTRenderLegendaryNPCLayered("matthos_seaworth"));
		renders.put(GOTEntityMoqorro.class, new GOTRenderLegendaryNPCLayered("moqorro"));
		renders.put(GOTEntityOsha.class, new GOTRenderLegendaryNPCLayered("osha"));
		renders.put(GOTEntityRandyllTarly.class, new GOTRenderLegendaryNPCLayered("randyll_tarly"));
		renders.put(GOTEntityRickardKarstark.class, new GOTRenderLegendaryNPCLayered("rickard_karstark"));
		renders.put(GOTEntityRobbStark.class, new GOTRenderLegendaryNPCLayered("robb_stark"));
		renders.put(GOTEntityRooseBolton.class, new GOTRenderLegendaryNPCLayered("roose_bolton"));
		renders.put(GOTEntitySamwellTarly.class, new GOTRenderLegendaryNPCLayered("samwell_tarly"));
		renders.put(GOTEntitySandorClegane.class, new GOTRenderLegendaryNPCLayered("sandor_clegane", 1.2f));
		renders.put(GOTEntityThoros.class, new GOTRenderLegendaryNPCLayered("thoros"));
		renders.put(GOTEntityTormund.class, new GOTRenderLegendaryNPCLayered("tormund"));
		renders.put(GOTEntityVictarionGreyjoy.class, new GOTRenderLegendaryNPCLayered("victarion_greyjoy", true));
		renders.put(GOTEntityWalderFrey.class, new GOTRenderLegendaryNPCLayered("walder_frey"));
		renders.put(GOTEntityWymanManderly.class, new GOTRenderLegendaryNPCLayered("wyman_manderly"));
		renders.put(GOTEntityXaroXhoanDaxos.class, new GOTRenderLegendaryNPCLayered("xaro_xhoan_daxos"));
		renders.put(GOTEntityYaraGreyjoy.class, new GOTRenderLegendaryNPCLayered("yara_greyjoy"));
		renders.put(GOTEntityWesterosThief.class, new GOTRenderFactionNPC("westeros/unreliable"));
		renders.put(GOTEntityEssosThief.class, new GOTRenderFactionNPC("essos/unreliable"));
		renders.put(GOTEntityWesterosBandit.class, new GOTRenderFactionNPC("westeros/unreliable"));
		renders.put(GOTEntityEssosBandit.class, new GOTRenderFactionNPC("essos/unreliable"));
		renders.put(GOTEntityWesterosScrapTrader.class, new GOTRenderFactionNPC("westeros/unreliable"));
		renders.put(GOTEntityEssosScrapTrader.class, new GOTRenderFactionNPC("essos/unreliable"));
		renders.put(GOTEntityGhiscarUnsullied.class, new GOTRenderFactionNPCMonofolder("essos/unsullied"));
		renders.put(GOTEntityMaester.class, new GOTRenderFactionNPCLayered("westeros/westeros", "maester"));
		renders.put(GOTEntityQohorUnsullied.class, new GOTRenderFactionNPCMonofolder("essos/unsullied"));
		renders.put(GOTEntityStoneMan.class, new GOTRenderFactionNPC("essos/stone"));
		renders.put(GOTEntityThennBerserker.class, new GOTRenderFactionNPCMonofolder("westeros/thenn/male", 1.1f));
		renders.put(GOTEntityProstitute.class, new GOTRenderProstitute());
		renders.put(GOTEntityCrownlandsAlchemist.class, new GOTRenderFactionNPCLayered("westeros/westeros", "alchemist"));
		renders.put(GOTEntityQarthWarlock.class, new GOTRenderFactionNPCMonotexture("essos/pree"));
		renders.put(GOTEntityWhiteWalker.class, new GOTRenderFactionNPCMonotexture("westeros/ice/walker"));
		renders.put(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
		renders.put(GOTEntityAlignmentBonus.class, new GOTRenderAlignmentBonus());
		renders.put(GOTEntityArrowPoisoned.class, new GOTRenderArrowPoisoned());
		renders.put(GOTEntityArrowFire.class, new GOTRenderArrowFire());
		renders.put(GOTEntityBison.class, new GOTRenderBison());
		renders.put(GOTEntityBanner.class, new GOTRenderBanner());
		renders.put(GOTEntityBannerWall.class, new GOTRenderBannerWall());
		renders.put(GOTEntityBarrel.class, new GOTRenderBarrel());
		renders.put(GOTEntityBarrowWight.class, new GOTRenderBarrowWight());
		renders.put(GOTEntityBear.class, new GOTRenderBear());
		renders.put(GOTEntityBearRug.class, new GOTRenderBearRug());
		renders.put(GOTEntityBird.class, new GOTRenderBird());
		renders.put(GOTEntityBomb.class, new GOTRenderBomb());
		renders.put(GOTEntityButterfly.class, new GOTRenderButterfly());
		renders.put(GOTEntityCamel.class, new GOTRenderCamel());
		renders.put(GOTEntityCargocart.class, new GOTRenderCargocart());
		renders.put(GOTEntitySnowball.class, new RenderSnowball(Items.snowball));
		renders.put(GOTEntityConker.class, new RenderSnowball(GOTRegistry.chestnut));
		renders.put(GOTEntityCrocodile.class, new GOTRenderCrocodile());
		renders.put(GOTEntityCrossbowBolt.class, new GOTRenderCrossbowBolt());
		renders.put(GOTEntityDart.class, new GOTRenderDart());
		renders.put(GOTEntityDeer.class, new GOTRenderDeer());
		renders.put(GOTEntityDikDik.class, new GOTRenderDikDik());
		renders.put(GOTEntityDirewolf.class, new GOTRenderDirewolf());
		renders.put(GOTEntityDragon.class, new GOTRenderDragon());
		renders.put(GOTEntityElephant.class, new GOTRenderElephant());
		renders.put(GOTEntityFallingFireJar.class, new GOTRenderFallingFireJar());
		renders.put(GOTEntityFallingTreasure.class, new GOTRenderFallingCoinPile());
		renders.put(GOTEntityFirePot.class, new RenderSnowball(GOTRegistry.firePot));
		renders.put(GOTEntityFish.class, new GOTRenderFish());
		renders.put(GOTEntityFlamingo.class, new GOTRenderFlamingo());
		renders.put(GOTEntityGemsbok.class, new GOTRenderGemsbok());
		renders.put(GOTEntityGiant.class, new GOTRenderGiant("giant"));
		renders.put(GOTEntityGiraffe.class, new GOTRenderGiraffe());
		renders.put(GOTEntityGiraffeRug.class, new GOTRenderGiraffeRug());
		renders.put(GOTEntityGregorClegane.GregorCleganeAlive.class, new GOTRenderGregorClegane.Alive());
		renders.put(GOTEntityGregorClegane.GregorCleganeDead.class, new GOTRenderGregorClegane.Dead());
		renders.put(GOTEntityHorse.class, new GOTRenderHorse());
		renders.put(GOTEntityIceSpider.class, new GOTRenderIceSpider());
		renders.put(GOTEntityInvasionSpawner.class, new GOTRenderInvasionSpawner());
		renders.put(GOTEntityWhiteBison.class, new GOTRenderWhiteBison());
		renders.put(GOTEntityLancelLannister.LancelLannisterNormal.class, new GOTRenderLancelLannister.Normal());
		renders.put(GOTEntityLancelLannister.LancelLannisterReligious.class, new GOTRenderLancelLannister.Religious());
		renders.put(GOTEntityLingeringEffect.class, new GOTRenderLingeringEffect());
		renders.put(GOTEntityLingeringPotion.class, new GOTRenderLingeringPotion());
		renders.put(GOTEntityLionBase.class, new GOTRenderLion());
		renders.put(GOTEntityLionRug.class, new GOTRenderLionRug());
		renders.put(GOTEntityMammoth.class, new GOTRenderMammoth());
		renders.put(GOTEntityManticore.class, new GOTRenderManticore());
		renders.put(GOTEntityMidges.class, new GOTRenderMidges());
		renders.put(GOTEntityMossovyWerewolf.class, new GOTRenderMossovyWerewolf());
		renders.put(GOTEntityMysteryWeb.class, new RenderSnowball(GOTRegistry.mysteryWeb));
		renders.put(GOTEntityNPCRespawner.class, new GOTRenderNPCRespawner());
		renders.put(GOTEntityPebble.class, new RenderSnowball(GOTRegistry.pebble));
		renders.put(GOTEntityPlate.class, new GOTRenderPlate());
		renders.put(GOTEntityPlowcart.class, new GOTRenderPlowcart());
		renders.put(GOTEntityPortal.class, new GOTRenderPortal());
		renders.put(GOTEntityRabbit.class, new GOTRenderRabbit());
		renders.put(GOTEntityRedScorpion.class, new GOTRenderRedScorpion());
		renders.put(GOTEntityRhino.class, new GOTRenderRhino());
		renders.put(GOTEntityScorpionBig.class, new GOTRenderScorpion());
		renders.put(GOTEntitySmokeRing.class, new GOTRenderSmokeRing());
		renders.put(GOTEntitySnowBear.class, new GOTRenderSnowBear());
		renders.put(GOTEntitySpear.class, new GOTRenderSpear());
		renders.put(GOTEntitySwan.class, new GOTRenderSwan());
		renders.put(GOTEntityTermite.class, new GOTRenderTermite());
		renders.put(GOTEntityTheonGreyjoy.TheonGreyjoyNormal.class, new GOTRenderTheonGreyjoy.Normal());
		renders.put(GOTEntityTheonGreyjoy.TheonGreyjoyTormented.class, new GOTRenderTheonGreyjoy.Tormented());
		renders.put(GOTEntityThrowingAxe.class, new GOTRenderThrowingAxe());
		renders.put(GOTEntityThrownRock.class, new GOTRenderThrownRock());
		renders.put(GOTEntityThrownTermite.class, new RenderSnowball(GOTRegistry.termite));
		renders.put(GOTEntityTraderRespawn.class, new GOTRenderTraderRespawn());
		renders.put(GOTEntityTyrionLannister.class, new GOTRenderTyrionLannister());
		renders.put(GOTEntityUlthosSpider.class, new GOTRenderGiantSpider());
		renders.put(GOTEntityShadowcat.class, new GOTRenderShadowcat());
		renders.put(GOTEntityWalrus.class, new GOTRenderWalrus());
		renders.put(GOTEntityWhiteOryx.class, new GOTRenderWhiteOryx());
		renders.put(GOTEntityWightGiant.class, new GOTRenderGiant("ice"));
		renders.put(GOTEntityBoar.class, new GOTRenderBoar());
		renders.put(GOTEntityWoolyRhino.class, new GOTRenderWoolyRhino());
		renders.put(GOTEntityWyvern.class, new GOTRenderWyvern());
		renders.put(GOTEntityZebra.class, new GOTRenderZebra());
		renders.put(GOTEntityMarshWraith.class, new GOTRenderMarshWraith());
		renders.put(GOTEntityMarshWraithBall.class, new GOTRenderWraithBall());
		renders.put(GOTEntityDeadMarshFace.class, new GOTRenderDeadMarshFace());
		renders.put(GOTEntitySwordCommandMarker.class, new GOTRenderSwordCommandMarker());
	}
}
