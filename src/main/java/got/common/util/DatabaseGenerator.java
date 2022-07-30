package got.common.util;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

import got.common.block.other.*;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.*;
import got.common.entity.essos.asshai.*;
import got.common.entity.essos.braavos.*;
import got.common.entity.essos.dothraki.*;
import got.common.entity.essos.ghiscar.*;
import got.common.entity.essos.gold.*;
import got.common.entity.essos.ibben.*;
import got.common.entity.essos.jogos.*;
import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.*;
import got.common.entity.essos.legendary.trader.*;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.essos.lhazar.*;
import got.common.entity.essos.lorath.*;
import got.common.entity.essos.lys.*;
import got.common.entity.essos.mossovy.*;
import got.common.entity.essos.myr.*;
import got.common.entity.essos.norvos.*;
import got.common.entity.essos.pentos.*;
import got.common.entity.essos.qarth.*;
import got.common.entity.essos.qohor.*;
import got.common.entity.essos.tyrosh.*;
import got.common.entity.essos.volantis.*;
import got.common.entity.essos.yiti.*;
import got.common.entity.other.*;
import got.common.entity.other.GOTUnitTradeEntry.PledgeType;
import got.common.entity.sothoryos.sothoryos.*;
import got.common.entity.sothoryos.summer.*;
import got.common.entity.westeros.*;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.crownlands.*;
import got.common.entity.westeros.dorne.*;
import got.common.entity.westeros.dragonstone.*;
import got.common.entity.westeros.gift.*;
import got.common.entity.westeros.hillmen.*;
import got.common.entity.westeros.ice.*;
import got.common.entity.westeros.ironborn.*;
import got.common.entity.westeros.legendary.GOTEntityCrasterWife;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityBericDondarrion.BericDondarrionLife1;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane.GregorCleganeAlive;
import got.common.entity.westeros.legendary.reborn.GOTEntityJonSnow.JonSnowLife1;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister.LancelLannisterNormal;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy.TheonGreyjoyNormal;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.*;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTBiomeDecorator.*;
import got.common.world.biome.variant.GOTBiomeVariantList.VariantBucket;
import got.common.world.feature.GOTTreeType.WeightedTreeType;
import got.common.world.map.*;
import got.common.world.spawning.GOTBiomeSpawnList.*;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.structure.other.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.*;

public class DatabaseGenerator extends GOTStructureBase {
	public static Map<Class<? extends Entity>, Entity> entities = new HashMap<>();
	public static Map<Class<? extends Entity>, GOTWaypoint> charPoint = new HashMap<>();
	public static String display = "null";
	public static String riderLoc = "\u0432\u0435\u0440\u0448\u043D\u0438\u043A";
	public static String categoryTemplates = "\u041A\u0430\u0442\u0435\u0433\u043E\u0440\u0456\u044F:\u0428\u0430\u0431\u043B\u043E\u043D\u0438";
	public static String biomeNoNPC = "\u0423 \u0446\u044C\u043E\u043C\u0443 \u0431\u0456\u043E\u043C\u0456 \u043D\u0435\u043C\u0430\u0454 \u043D\u0456\u044F\u043A\u0438\u0445 NPC.";
	public static String biomeContainerLoc = "\u041A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440";
	public static String biomeContainerMeaning = "\u042F\u043A\u0449\u043E \u0432 \u0431\u0456\u043E\u043C\u0456 \u0432\u043A\u0430\u0437\u0430\u043D\u043E \u0434\u0435\u043A\u0456\u043B\u044C\u043A\u0430" + "\u043A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440\u0456\u0432 (\u043A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440\u0438 \u0437 \u043C\u043E\u043D\u0441\u0442\u0440\u0430\u043C\u0438 \u043D\u0435 \u0432 \u0440\u0430\u0445\u0443\u043D\u043E\u043A), \u0446\u0435 \u043E\u0437\u043D\u0430\u0447\u0430\u0454, \u0449\u043E " + "\u0442\u0435\u0440\u0438\u0442\u043E\u0440\u0456\u044E \u043C\u043E\u0436\u043D\u0430 \u0437\u0430\u0432\u043E\u044E\u0432\u0430\u0442\u0438 \u0432\u0456\u0434 \u0456\u043C\u0435\u043D\u0456 \u0434\u0435\u044F\u043A\u0438\u0445 \u0444\u0440\u0430\u043A\u0446\u0456\u0439. \u042F\u043A\u0449\u043E \u0432\u0438 " + "[[\u0424\u0440\u0430\u043A\u0446\u0456\u0457#\u041F\u0440\u0438\u0441\u044F\u0433\u0430|\u043F\u0440\u0438\u0441\u044F\u0433\u043D\u0443\u043B\u0438]] \u0444\u0440\u0430\u043A\u0446\u0456\u0457, \u0430 \u0457\u0457 \u043A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440 \u0432\u043A\u0430\u0437\u0430\u043D\u043E \u0432 " + "\u0441\u043F\u0438\u0441\u043A\u0443 \u043D\u0438\u0436\u0447\u0435, \u0437\u043D\u0430\u0447\u0438\u0442\u044C, \u043F\u0456\u0441\u043B\u044F \u0437\u0430\u0432\u043E\u044E\u0432\u0430\u043D\u043D\u044F \u0442\u0435\u0440\u0438\u0442\u043E\u0440\u0456\u0457 \u043F\u0435\u0440\u0448\u0438\u0439 \u043A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440" + "\u0441\u043F\u0430\u0432\u043D\u0430 (\u043F\u043E\u0447\u0430\u0442\u043A\u043E\u0432\u0438\u0439) \u043F\u0435\u0440\u0435\u043A\u043B\u044E\u0447\u0438\u0442\u044C\u0441\u044F \u043D\u0430 \u043A\u043E\u043D\u0442\u0435\u0439\u043D\u0435\u0440 \u0432\u0430\u0448\u043E\u0457 \u0444\u0440\u0430\u043A\u0446\u0456\u0457, \u0456 \u043D\u0430 " + "\u043E\u043A\u0443\u043F\u043E\u0432\u0430\u043D\u0456\u0439 \u0442\u0435\u0440\u0438\u0442\u043E\u0440\u0456\u0457 \u043F\u043E\u0447\u043D\u0443\u0442\u044C \u0441\u043F\u043E\u0432\u043D\u0438\u0442\u0438\u0441\u044F \u0432\u0430\u0448\u0456 \u0441\u043E\u044E\u0437\u043D\u0438\u043A\u0438.";
	public static String biomeNoVariants = "\u0426\u0435\u0439 \u0431\u0456\u043E\u043C \u043D\u0435 \u043C\u0430\u0454 \u0436\u043E\u0434\u043D\u0438\u0445 \u0432\u0430\u0440\u0456\u0430\u043D\u0442\u0456\u0432.";
	public static String biomeNoInvasions = "\u0423 \u0446\u0435\u0439 \u0431\u0456\u043E\u043C \u043D\u0435 \u0432\u0442\u043E\u0440\u0433\u0430\u044E\u0442\u044C\u0441\u044F \u043D\u0456\u044F\u043A\u0456 \u0444\u0440\u0430\u043A\u0446\u0456\u0457.";
	public static String biomeNoTrees = "\u0423 \u0446\u044C\u043E\u043C\u0443 \u0431\u0456\u043E\u043C\u0456 \u043D\u0435\u043C\u0430\u0454 \u0434\u0435\u0440\u0435\u0432.";
	public static String biomeNoAnimals = "\u0423 \u0446\u044C\u043E\u043C\u0443 \u0431\u0456\u043E\u043C\u0456 \u043D\u0435\u043C\u0430\u0454 \u0437\u0432\u0456\u0440\u0456\u0432, \u043F\u0442\u0430\u0445\u0456\u0432 \u0442\u0430 \u0440\u0438\u0431.";
	public static String biomeNoStructures = "\u0423 \u0446\u044C\u043E\u043C\u0443 \u0431\u0456\u043E\u043C\u0456 \u043D\u0435\u043C\u0430\u0454 \u0441\u0442\u0440\u0443\u043A\u0442\u0443\u0440.";
	public static String biomeLoc = "\u0431\u0456\u043E\u043C";
	public static String factionNoEnemies = "\u0426\u044F \u0444\u0440\u0430\u043A\u0446\u0456\u044F \u0432\u0437\u0430\u0433\u0430\u043B\u0456 \u043D\u0435 \u043C\u0430\u0454 \u0432\u043E\u0440\u043E\u0433\u0456\u0432.";
	public static String factionNoFriends = "\u0426\u044F \u0444\u0440\u0430\u043A\u0446\u0456\u044F \u043D\u0435 \u043C\u0430\u0454 \u0436\u043E\u0434\u043D\u0438\u0445 \u0441\u043E\u044E\u0437\u043D\u0438\u043A\u0456\u0432.";
	public static String factionNoCharacters = "\u041D\u0435 \u0456\u0441\u043D\u0443\u0454 \u043F\u0435\u0440\u0441\u043E\u043D\u0430\u0436\u0456\u0432, \u0449\u043E \u043D\u0430\u043B\u0435\u0436\u0430\u0442\u044C \u0434\u043E \u0446\u0456\u0454\u0457 \u0444\u0440\u0430\u043A\u0446\u0456\u0457.";
	public static String factionLoc = "\u0444\u0440\u0430\u043A\u0446\u0456\u044F";
	public static String factionNotViolent = "\u0426\u044F \u0444\u0440\u0430\u043A\u0446\u0456\u044F \u043D\u0435 \u0437\u0430\u043E\u0445\u043E\u0447\u0443\u0454 \u0432\u0431\u0438\u0432\u0441\u0442\u0432\u043E \u0446\u0438\u0432\u0456\u043B\u044C\u043D\u0438\u0445";
	public static String factionIsViolent = "\u0426\u044F \u0444\u0440\u0430\u043A\u0446\u0456\u044F \u0437\u0430\u043E\u0445\u043E\u0447\u0443\u0454 \u0432\u0456\u0439\u0441\u044C\u043A\u043E\u0432\u0456 \u0437\u043B\u043E\u0447\u0438\u043D\u0438";
	public static String factionNoStructures = "\u0426\u044F \u0444\u0440\u0430\u043A\u0446\u0456\u044F \u043D\u0435 \u043C\u0430\u0454 \u0436\u043E\u0434\u043D\u0438\u0445 \u0441\u0442\u0440\u0443\u043A\u0442\u0443\u0440.";
	public static String noPledge = "\u0431\u0435\u0437 \u043F\u0440\u0438\u0441\u044F\u0433\u0438";
	public static String rep = "\u0440\u0435\u043F\u0443\u0442\u0430\u0446\u0456\u0457";
	public static String yesPledge = "\u0437 \u043F\u0440\u0438\u0441\u044F\u0433\u043E\u044E";

	public DatabaseGenerator(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int y, int j, int k, int rotation) {
		charPoint.put(GOTEntityYgritte.class, GOTWaypoint.Hardhome);
		charPoint.put(GOTEntityTormund.class, GOTWaypoint.Hardhome);
		charPoint.put(GOTEntityManceRayder.class, GOTWaypoint.Hardhome);
		charPoint.put(GOTEntityCraster.class, GOTWaypoint.CrastersKeep);
		charPoint.put(GOTEntityVictarionGreyjoy.class, GOTWaypoint.VictarionLanding);
		charPoint.put(LancelLannisterNormal.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityTobhoMott.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityTyrionLannister.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityGendryBaratheon.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityBronn.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityPodrickPayne.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityPetyrBaelish.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityHotPie.class, GOTWaypoint.CrossroadsInn);
		charPoint.put(GOTEntityVargoHoat.class, GOTWaypoint.CrossroadsInn);
		charPoint.put(GOTEntitySandorClegane.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityJoffreyBaratheon.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityCerseiLannister.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityJaimeLannister.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityPycelle.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityJanosSlynt.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityVarys.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityIlynPayne.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityHighSepton.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityTommenBaratheon.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityMyrcellaBaratheon.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityMerynTrant.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityBarristanSelmy.class, GOTWaypoint.KingsLanding);
		charPoint.put(GOTEntityJeorMormont.class, GOTWaypoint.CastleBlack);
		charPoint.put(JonSnowLife1.class, GOTWaypoint.CastleBlack);
		charPoint.put(GOTEntityAemonTargaryen.class, GOTWaypoint.CastleBlack);
		charPoint.put(GOTEntityAlliserThorne.class, GOTWaypoint.CastleBlack);
		charPoint.put(GOTEntityEdd.class, GOTWaypoint.CastleBlack);
		charPoint.put(GOTEntitySamwellTarly.class, GOTWaypoint.CastleBlack);
		charPoint.put(GOTEntityCotterPyke.class, GOTWaypoint.EastWatch);
		charPoint.put(GOTEntityHarmune.class, GOTWaypoint.EastWatch);
		charPoint.put(GOTEntityDenysMallister.class, GOTWaypoint.ShadowTower);
		charPoint.put(GOTEntityMullin.class, GOTWaypoint.ShadowTower);

		entities.put(GOTEntityBison.class, new GOTEntityBison(world));
		entities.put(GOTEntityBear.class, new GOTEntityBear(world));
		entities.put(GOTEntityBird.class, new GOTEntityBird(world));
		entities.put(GOTEntityButterfly.class, new GOTEntityButterfly(world));
		entities.put(GOTEntityCamel.class, new GOTEntityCamel(world));
		entities.put(GOTEntityCrocodile.class, new GOTEntityCrocodile(world));
		entities.put(GOTEntityDeer.class, new GOTEntityDeer(world));
		entities.put(GOTEntityDesertScorpion.class, new GOTEntityDesertScorpion(world));
		entities.put(GOTEntityDikDik.class, new GOTEntityDikDik(world));
		entities.put(GOTEntityDirewolf.class, new GOTEntityDirewolf(world));
		entities.put(GOTEntityDragon.class, new GOTEntityDragon(world));
		entities.put(GOTEntityElephant.class, new GOTEntityElephant(world));
		entities.put(GOTEntityFish.class, new GOTEntityFish(world));
		entities.put(GOTEntityFlamingo.class, new GOTEntityFlamingo(world));
		entities.put(GOTEntityGemsbok.class, new GOTEntityGemsbok(world));
		entities.put(GOTEntityGiraffe.class, new GOTEntityGiraffe(world));
		entities.put(GOTEntityGorcrow.class, new GOTEntityGorcrow(world));
		entities.put(GOTEntityHorse.class, new GOTEntityHorse(world));
		entities.put(GOTEntityJungleScorpion.class, new GOTEntityJungleScorpion(world));
		entities.put(GOTEntityWhiteBison.class, new GOTEntityWhiteBison(world));
		entities.put(GOTEntityLion.class, new GOTEntityLion(world));
		entities.put(GOTEntityLioness.class, new GOTEntityLioness(world));
		entities.put(GOTEntityMammoth.class, new GOTEntityMammoth(world));
		entities.put(GOTEntityManticore.class, new GOTEntityManticore(world));
		entities.put(GOTEntityMidges.class, new GOTEntityMidges(world));
		entities.put(GOTEntityMossovyWerewolf.class, new GOTEntityMossovyWerewolf(world));
		entities.put(GOTEntityRabbit.class, new GOTEntityRabbit(world));
		entities.put(GOTEntityRedScorpion.class, new GOTEntityRedScorpion(world));
		entities.put(GOTEntityRhino.class, new GOTEntityRhino(world));
		entities.put(GOTEntitySeagull.class, new GOTEntitySeagull(world));
		entities.put(GOTEntityShadowcat.class, new GOTEntityShadowcat(world));
		entities.put(GOTEntitySnowBear.class, new GOTEntitySnowBear(world));
		entities.put(GOTEntitySwan.class, new GOTEntitySwan(world));
		entities.put(GOTEntityTermite.class, new GOTEntityTermite(world));
		entities.put(GOTEntityWalrus.class, new GOTEntityWalrus(world));
		entities.put(GOTEntityWhiteOryx.class, new GOTEntityWhiteOryx(world));
		entities.put(GOTEntityBoar.class, new GOTEntityBoar(world));
		entities.put(GOTEntityWoolyRhino.class, new GOTEntityWoolyRhino(world));
		entities.put(GOTEntityWyvern.class, new GOTEntityWyvern(world));
		entities.put(GOTEntityZebra.class, new GOTEntityZebra(world));
		entities.put(GOTEntityWesterosBandit.class, new GOTEntityWesterosBandit(world));
		entities.put(GOTEntityWesterosThief.class, new GOTEntityWesterosThief(world));
		entities.put(GOTEntityWesterosScrapTrader.class, new GOTEntityWesterosScrapTrader(world));
		entities.put(GOTEntityEssosBandit.class, new GOTEntityEssosBandit(world));
		entities.put(GOTEntityEssosThief.class, new GOTEntityEssosThief(world));
		entities.put(GOTEntityEssosScrapTrader.class, new GOTEntityEssosScrapTrader(world));
		entities.put(GOTEntityProstitute.class, new GOTEntityProstitute(world));
		entities.put(GOTEntityMaester.class, new GOTEntityMaester(world));
		entities.put(GOTEntitySepton.class, new GOTEntitySepton(world));
		entities.put(GOTEntityRedPriest.class, new GOTEntityRedPriest(world));
		entities.put(GOTEntityIronbornPriest.class, new GOTEntityIronbornPriest(world));
		entities.put(GOTEntityBarrowWight.class, new GOTEntityBarrowWight(world));
		entities.put(GOTEntityStoneMan.class, new GOTEntityStoneMan(world));
		entities.put(GOTEntityMercenary.class, new GOTEntityMercenary(world));
		entities.put(GOTEntityWhiteWalker.class, new GOTEntityWhiteWalker(world));
		entities.put(GOTEntityWight.class, new GOTEntityWight(world));
		entities.put(GOTEntityIceSpider.class, new GOTEntityIceSpider(world));
		entities.put(GOTEntityWightGiant.class, new GOTEntityWightGiant(world));
		entities.put(GOTEntityWildling.class, new GOTEntityWildling(world));
		entities.put(GOTEntityWildlingArcher.class, new GOTEntityWildlingArcher(world));
		entities.put(GOTEntityWildlingAxeThrower.class, new GOTEntityWildlingAxeThrower(world));
		entities.put(GOTEntityWildlingBannerBearer.class, new GOTEntityWildlingBannerBearer(world));
		entities.put(GOTEntityWildlingChieftain.class, new GOTEntityWildlingChieftain(world));
		entities.put(GOTEntityGiant.class, new GOTEntityGiant(world));
		entities.put(GOTEntityThenn.class, new GOTEntityThenn(world));
		entities.put(GOTEntityThennArcher.class, new GOTEntityThennArcher(world));
		entities.put(GOTEntityThennAxeThrower.class, new GOTEntityThennAxeThrower(world));
		entities.put(GOTEntityThennBannerBearer.class, new GOTEntityThennBannerBearer(world));
		entities.put(GOTEntityThennBerserker.class, new GOTEntityThennBerserker(world));
		entities.put(GOTEntityThennMagnar.class, new GOTEntityThennMagnar(world));
		entities.put(GOTEntityThennBlacksmith.class, new GOTEntityThennBlacksmith(world));
		entities.put(GOTEntityCrasterWife.class, new GOTEntityCrasterWife(world));
		entities.put(GOTEntityGiftMan.class, new GOTEntityGiftMan(world));
		entities.put(GOTEntityGiftBlacksmith.class, new GOTEntityGiftBlacksmith(world));
		entities.put(GOTEntityGiftGuard.class, new GOTEntityGiftGuard(world));
		entities.put(GOTEntityGiftBannerBearer.class, new GOTEntityGiftBannerBearer(world));
		entities.put(GOTEntityNorthMan.class, new GOTEntityNorthMan(world));
		entities.put(GOTEntityNorthLevyman.class, new GOTEntityNorthLevyman(world));
		entities.put(GOTEntityNorthLevymanArcher.class, new GOTEntityNorthLevymanArcher(world));
		entities.put(GOTEntityNorthSoldier.class, new GOTEntityNorthSoldier(world));
		entities.put(GOTEntityNorthSoldierArcher.class, new GOTEntityNorthSoldierArcher(world));
		entities.put(GOTEntityNorthGuard.class, new GOTEntityNorthGuard(world));
		entities.put(GOTEntityNorthBannerBearer.class, new GOTEntityNorthBannerBearer(world));
		entities.put(GOTEntityNorthCaptain.class, new GOTEntityNorthCaptain(world));
		entities.put(GOTEntityNorthBlacksmith.class, new GOTEntityNorthBlacksmith(world));
		entities.put(GOTEntityNorthGoldsmith.class, new GOTEntityNorthGoldsmith(world));
		entities.put(GOTEntityNorthFarmer.class, new GOTEntityNorthFarmer(world));
		entities.put(GOTEntityNorthFarmhand.class, new GOTEntityNorthFarmhand(world));
		entities.put(GOTEntityNorthBartender.class, new GOTEntityNorthBartender(world));
		entities.put(GOTEntityNorthGreengrocer.class, new GOTEntityNorthGreengrocer(world));
		entities.put(GOTEntityNorthLumberman.class, new GOTEntityNorthLumberman(world));
		entities.put(GOTEntityNorthMason.class, new GOTEntityNorthMason(world));
		entities.put(GOTEntityNorthBrewer.class, new GOTEntityNorthBrewer(world));
		entities.put(GOTEntityNorthFlorist.class, new GOTEntityNorthFlorist(world));
		entities.put(GOTEntityNorthButcher.class, new GOTEntityNorthButcher(world));
		entities.put(GOTEntityNorthFishmonger.class, new GOTEntityNorthFishmonger(world));
		entities.put(GOTEntityNorthBaker.class, new GOTEntityNorthBaker(world));
		entities.put(GOTEntityNorthHillman.class, new GOTEntityNorthHillman(world));
		entities.put(GOTEntityNorthHillmanWarrior.class, new GOTEntityNorthHillmanWarrior(world));
		entities.put(GOTEntityNorthHillmanCannibal.class, new GOTEntityNorthHillmanCannibal(world));
		entities.put(GOTEntityNorthHillmanMercenary.class, new GOTEntityNorthHillmanMercenary(world));
		entities.put(GOTEntityNorthHillmanArcher.class, new GOTEntityNorthHillmanArcher(world));
		entities.put(GOTEntityNorthHillmanAxeThrower.class, new GOTEntityNorthHillmanAxeThrower(world));
		entities.put(GOTEntityNorthHillmanBannerBearer.class, new GOTEntityNorthHillmanBannerBearer(world));
		entities.put(GOTEntityNorthHillmanChieftain.class, new GOTEntityNorthHillmanChieftain(world));
		entities.put(GOTEntityIronbornMan.class, new GOTEntityIronbornMan(world));
		entities.put(GOTEntityIronbornLevyman.class, new GOTEntityIronbornLevyman(world));
		entities.put(GOTEntityIronbornLevymanArcher.class, new GOTEntityIronbornLevymanArcher(world));
		entities.put(GOTEntityIronbornSoldier.class, new GOTEntityIronbornSoldier(world));
		entities.put(GOTEntityIronbornSoldierArcher.class, new GOTEntityIronbornSoldierArcher(world));
		entities.put(GOTEntityIronbornBannerBearer.class, new GOTEntityIronbornBannerBearer(world));
		entities.put(GOTEntityIronbornCaptain.class, new GOTEntityIronbornCaptain(world));
		entities.put(GOTEntityIronbornBaker.class, new GOTEntityIronbornBaker(world));
		entities.put(GOTEntityIronbornBartender.class, new GOTEntityIronbornBartender(world));
		entities.put(GOTEntityIronbornGoldsmith.class, new GOTEntityIronbornGoldsmith(world));
		entities.put(GOTEntityIronbornBlacksmith.class, new GOTEntityIronbornBlacksmith(world));
		entities.put(GOTEntityIronbornBrewer.class, new GOTEntityIronbornBrewer(world));
		entities.put(GOTEntityIronbornButcher.class, new GOTEntityIronbornButcher(world));
		entities.put(GOTEntityIronbornFarmer.class, new GOTEntityIronbornFarmer(world));
		entities.put(GOTEntityIronbornFarmhand.class, new GOTEntityIronbornFarmhand(world));
		entities.put(GOTEntityIronbornFishmonger.class, new GOTEntityIronbornFishmonger(world));
		entities.put(GOTEntityIronbornFlorist.class, new GOTEntityIronbornFlorist(world));
		entities.put(GOTEntityIronbornGreengrocer.class, new GOTEntityIronbornGreengrocer(world));
		entities.put(GOTEntityIronbornLumberman.class, new GOTEntityIronbornLumberman(world));
		entities.put(GOTEntityIronbornMason.class, new GOTEntityIronbornMason(world));
		entities.put(GOTEntityWesterlandsMan.class, new GOTEntityWesterlandsMan(world));
		entities.put(GOTEntityWesterlandsLevyman.class, new GOTEntityWesterlandsLevyman(world));
		entities.put(GOTEntityWesterlandsLevymanArcher.class, new GOTEntityWesterlandsLevymanArcher(world));
		entities.put(GOTEntityWesterlandsSoldier.class, new GOTEntityWesterlandsSoldier(world));
		entities.put(GOTEntityWesterlandsSoldierArcher.class, new GOTEntityWesterlandsSoldierArcher(world));
		entities.put(GOTEntityWesterlandsGuard.class, new GOTEntityWesterlandsGuard(world));
		entities.put(GOTEntityWesterlandsBannerBearer.class, new GOTEntityWesterlandsBannerBearer(world));
		entities.put(GOTEntityWesterlandsCaptain.class, new GOTEntityWesterlandsCaptain(world));
		entities.put(GOTEntityWesterlandsBaker.class, new GOTEntityWesterlandsBaker(world));
		entities.put(GOTEntityWesterlandsBartender.class, new GOTEntityWesterlandsBartender(world));
		entities.put(GOTEntityWesterlandsBlacksmith.class, new GOTEntityWesterlandsBlacksmith(world));
		entities.put(GOTEntityWesterlandsGoldsmith.class, new GOTEntityWesterlandsGoldsmith(world));
		entities.put(GOTEntityWesterlandsBrewer.class, new GOTEntityWesterlandsBrewer(world));
		entities.put(GOTEntityWesterlandsButcher.class, new GOTEntityWesterlandsButcher(world));
		entities.put(GOTEntityWesterlandsFarmer.class, new GOTEntityWesterlandsFarmer(world));
		entities.put(GOTEntityWesterlandsFarmhand.class, new GOTEntityWesterlandsFarmhand(world));
		entities.put(GOTEntityWesterlandsFishmonger.class, new GOTEntityWesterlandsFishmonger(world));
		entities.put(GOTEntityWesterlandsFlorist.class, new GOTEntityWesterlandsFlorist(world));
		entities.put(GOTEntityWesterlandsGreengrocer.class, new GOTEntityWesterlandsGreengrocer(world));
		entities.put(GOTEntityWesterlandsLumberman.class, new GOTEntityWesterlandsLumberman(world));
		entities.put(GOTEntityWesterlandsMason.class, new GOTEntityWesterlandsMason(world));
		entities.put(GOTEntityRiverlandsMan.class, new GOTEntityRiverlandsMan(world));
		entities.put(GOTEntityRiverlandsLevyman.class, new GOTEntityRiverlandsLevyman(world));
		entities.put(GOTEntityRiverlandsLevymanArcher.class, new GOTEntityRiverlandsLevymanArcher(world));
		entities.put(GOTEntityRiverlandsSoldier.class, new GOTEntityRiverlandsSoldier(world));
		entities.put(GOTEntityRiverlandsSoldierArcher.class, new GOTEntityRiverlandsSoldierArcher(world));
		entities.put(GOTEntityRiverlandsBannerBearer.class, new GOTEntityRiverlandsBannerBearer(world));
		entities.put(GOTEntityRiverlandsCaptain.class, new GOTEntityRiverlandsCaptain(world));
		entities.put(GOTEntityRiverlandsBaker.class, new GOTEntityRiverlandsBaker(world));
		entities.put(GOTEntityRiverlandsBartender.class, new GOTEntityRiverlandsBartender(world));
		entities.put(GOTEntityRiverlandsBlacksmith.class, new GOTEntityRiverlandsBlacksmith(world));
		entities.put(GOTEntityRiverlandsGoldsmith.class, new GOTEntityRiverlandsGoldsmith(world));
		entities.put(GOTEntityRiverlandsBrewer.class, new GOTEntityRiverlandsBrewer(world));
		entities.put(GOTEntityRiverlandsButcher.class, new GOTEntityRiverlandsButcher(world));
		entities.put(GOTEntityRiverlandsFarmer.class, new GOTEntityRiverlandsFarmer(world));
		entities.put(GOTEntityRiverlandsFarmhand.class, new GOTEntityRiverlandsFarmhand(world));
		entities.put(GOTEntityRiverlandsFishmonger.class, new GOTEntityRiverlandsFishmonger(world));
		entities.put(GOTEntityRiverlandsFlorist.class, new GOTEntityRiverlandsFlorist(world));
		entities.put(GOTEntityRiverlandsGreengrocer.class, new GOTEntityRiverlandsGreengrocer(world));
		entities.put(GOTEntityRiverlandsLumberman.class, new GOTEntityRiverlandsLumberman(world));
		entities.put(GOTEntityRiverlandsMason.class, new GOTEntityRiverlandsMason(world));
		entities.put(GOTEntityHillman.class, new GOTEntityHillman(world));
		entities.put(GOTEntityHillmanWarrior.class, new GOTEntityHillmanWarrior(world));
		entities.put(GOTEntityHillmanArcher.class, new GOTEntityHillmanArcher(world));
		entities.put(GOTEntityHillmanAxeThrower.class, new GOTEntityHillmanAxeThrower(world));
		entities.put(GOTEntityHillmanBerserker.class, new GOTEntityHillmanBerserker(world));
		entities.put(GOTEntityHillmanBannerBearer.class, new GOTEntityHillmanBannerBearer(world));
		entities.put(GOTEntityHillmanWarlord.class, new GOTEntityHillmanWarlord(world));
		entities.put(GOTEntityHillmanBartender.class, new GOTEntityHillmanBartender(world));
		entities.put(GOTEntityArrynMan.class, new GOTEntityArrynMan(world));
		entities.put(GOTEntityArrynLevyman.class, new GOTEntityArrynLevyman(world));
		entities.put(GOTEntityArrynLevymanArcher.class, new GOTEntityArrynLevymanArcher(world));
		entities.put(GOTEntityArrynSoldier.class, new GOTEntityArrynSoldier(world));
		entities.put(GOTEntityArrynSoldierArcher.class, new GOTEntityArrynSoldierArcher(world));
		entities.put(GOTEntityArrynGuard.class, new GOTEntityArrynGuard(world));
		entities.put(GOTEntityArrynBannerBearer.class, new GOTEntityArrynBannerBearer(world));
		entities.put(GOTEntityArrynCaptain.class, new GOTEntityArrynCaptain(world));
		entities.put(GOTEntityArrynBaker.class, new GOTEntityArrynBaker(world));
		entities.put(GOTEntityArrynBartender.class, new GOTEntityArrynBartender(world));
		entities.put(GOTEntityArrynBlacksmith.class, new GOTEntityArrynBlacksmith(world));
		entities.put(GOTEntityArrynGoldsmith.class, new GOTEntityArrynGoldsmith(world));
		entities.put(GOTEntityArrynBrewer.class, new GOTEntityArrynBrewer(world));
		entities.put(GOTEntityArrynButcher.class, new GOTEntityArrynButcher(world));
		entities.put(GOTEntityArrynFarmer.class, new GOTEntityArrynFarmer(world));
		entities.put(GOTEntityArrynFarmhand.class, new GOTEntityArrynFarmhand(world));
		entities.put(GOTEntityArrynFishmonger.class, new GOTEntityArrynFishmonger(world));
		entities.put(GOTEntityArrynFlorist.class, new GOTEntityArrynFlorist(world));
		entities.put(GOTEntityArrynGreengrocer.class, new GOTEntityArrynGreengrocer(world));
		entities.put(GOTEntityArrynLumberman.class, new GOTEntityArrynLumberman(world));
		entities.put(GOTEntityArrynMason.class, new GOTEntityArrynMason(world));
		entities.put(GOTEntityDragonstoneMan.class, new GOTEntityDragonstoneMan(world));
		entities.put(GOTEntityDragonstoneLevyman.class, new GOTEntityDragonstoneLevyman(world));
		entities.put(GOTEntityDragonstoneLevymanArcher.class, new GOTEntityDragonstoneLevymanArcher(world));
		entities.put(GOTEntityDragonstoneSoldier.class, new GOTEntityDragonstoneSoldier(world));
		entities.put(GOTEntityDragonstoneSoldierArcher.class, new GOTEntityDragonstoneSoldierArcher(world));
		entities.put(GOTEntityDragonstoneBannerBearer.class, new GOTEntityDragonstoneBannerBearer(world));
		entities.put(GOTEntityDragonstoneCaptain.class, new GOTEntityDragonstoneCaptain(world));
		entities.put(GOTEntityDragonstoneBaker.class, new GOTEntityDragonstoneBaker(world));
		entities.put(GOTEntityDragonstoneBartender.class, new GOTEntityDragonstoneBartender(world));
		entities.put(GOTEntityDragonstoneBlacksmith.class, new GOTEntityDragonstoneBlacksmith(world));
		entities.put(GOTEntityDragonstoneGoldsmith.class, new GOTEntityDragonstoneGoldsmith(world));
		entities.put(GOTEntityDragonstoneBrewer.class, new GOTEntityDragonstoneBrewer(world));
		entities.put(GOTEntityDragonstoneButcher.class, new GOTEntityDragonstoneButcher(world));
		entities.put(GOTEntityDragonstoneFarmer.class, new GOTEntityDragonstoneFarmer(world));
		entities.put(GOTEntityDragonstoneFarmhand.class, new GOTEntityDragonstoneFarmhand(world));
		entities.put(GOTEntityDragonstoneFishmonger.class, new GOTEntityDragonstoneFishmonger(world));
		entities.put(GOTEntityDragonstoneFlorist.class, new GOTEntityDragonstoneFlorist(world));
		entities.put(GOTEntityDragonstoneGreengrocer.class, new GOTEntityDragonstoneGreengrocer(world));
		entities.put(GOTEntityDragonstoneLumberman.class, new GOTEntityDragonstoneLumberman(world));
		entities.put(GOTEntityDragonstoneMason.class, new GOTEntityDragonstoneMason(world));
		entities.put(GOTEntityCrownlandsMan.class, new GOTEntityCrownlandsMan(world));
		entities.put(GOTEntityCrownlandsLevyman.class, new GOTEntityCrownlandsLevyman(world));
		entities.put(GOTEntityCrownlandsLevymanArcher.class, new GOTEntityCrownlandsLevymanArcher(world));
		entities.put(GOTEntityCrownlandsGuard.class, new GOTEntityCrownlandsGuard(world));
		entities.put(GOTEntityCrownlandsBannerBearer.class, new GOTEntityCrownlandsBannerBearer(world));
		entities.put(GOTEntityKingsguard.class, new GOTEntityKingsguard(world));
		entities.put(GOTEntityCrownlandsCaptain.class, new GOTEntityCrownlandsCaptain(world));
		entities.put(GOTEntityCrownlandsBaker.class, new GOTEntityCrownlandsBaker(world));
		entities.put(GOTEntityCrownlandsBartender.class, new GOTEntityCrownlandsBartender(world));
		entities.put(GOTEntityCrownlandsBlacksmith.class, new GOTEntityCrownlandsBlacksmith(world));
		entities.put(GOTEntityCrownlandsGoldsmith.class, new GOTEntityCrownlandsGoldsmith(world));
		entities.put(GOTEntityCrownlandsBrewer.class, new GOTEntityCrownlandsBrewer(world));
		entities.put(GOTEntityCrownlandsButcher.class, new GOTEntityCrownlandsButcher(world));
		entities.put(GOTEntityCrownlandsFarmer.class, new GOTEntityCrownlandsFarmer(world));
		entities.put(GOTEntityCrownlandsFarmhand.class, new GOTEntityCrownlandsFarmhand(world));
		entities.put(GOTEntityCrownlandsFishmonger.class, new GOTEntityCrownlandsFishmonger(world));
		entities.put(GOTEntityCrownlandsFlorist.class, new GOTEntityCrownlandsFlorist(world));
		entities.put(GOTEntityCrownlandsGreengrocer.class, new GOTEntityCrownlandsGreengrocer(world));
		entities.put(GOTEntityCrownlandsLumberman.class, new GOTEntityCrownlandsLumberman(world));
		entities.put(GOTEntityCrownlandsMason.class, new GOTEntityCrownlandsMason(world));
		entities.put(GOTEntityCrownlandsAlchemist.class, new GOTEntityCrownlandsAlchemist(world));
		entities.put(GOTEntityStormlandsMan.class, new GOTEntityStormlandsMan(world));
		entities.put(GOTEntityStormlandsLevyman.class, new GOTEntityStormlandsLevyman(world));
		entities.put(GOTEntityStormlandsLevymanArcher.class, new GOTEntityStormlandsLevymanArcher(world));
		entities.put(GOTEntityStormlandsSoldier.class, new GOTEntityStormlandsSoldier(world));
		entities.put(GOTEntityStormlandsSoldierArcher.class, new GOTEntityStormlandsSoldierArcher(world));
		entities.put(GOTEntityStormlandsBannerBearer.class, new GOTEntityStormlandsBannerBearer(world));
		entities.put(GOTEntityStormlandsCaptain.class, new GOTEntityStormlandsCaptain(world));
		entities.put(GOTEntityStormlandsBaker.class, new GOTEntityStormlandsBaker(world));
		entities.put(GOTEntityStormlandsBartender.class, new GOTEntityStormlandsBartender(world));
		entities.put(GOTEntityStormlandsBlacksmith.class, new GOTEntityStormlandsBlacksmith(world));
		entities.put(GOTEntityStormlandsGoldsmith.class, new GOTEntityStormlandsGoldsmith(world));
		entities.put(GOTEntityStormlandsBrewer.class, new GOTEntityStormlandsBrewer(world));
		entities.put(GOTEntityStormlandsButcher.class, new GOTEntityStormlandsButcher(world));
		entities.put(GOTEntityStormlandsFarmer.class, new GOTEntityStormlandsFarmer(world));
		entities.put(GOTEntityStormlandsFarmhand.class, new GOTEntityStormlandsFarmhand(world));
		entities.put(GOTEntityStormlandsFishmonger.class, new GOTEntityStormlandsFishmonger(world));
		entities.put(GOTEntityStormlandsFlorist.class, new GOTEntityStormlandsFlorist(world));
		entities.put(GOTEntityStormlandsGreengrocer.class, new GOTEntityStormlandsGreengrocer(world));
		entities.put(GOTEntityStormlandsLumberman.class, new GOTEntityStormlandsLumberman(world));
		entities.put(GOTEntityStormlandsMason.class, new GOTEntityStormlandsMason(world));
		entities.put(GOTEntityReachMan.class, new GOTEntityReachMan(world));
		entities.put(GOTEntityReachLevyman.class, new GOTEntityReachLevyman(world));
		entities.put(GOTEntityReachLevymanArcher.class, new GOTEntityReachLevymanArcher(world));
		entities.put(GOTEntityReachSoldier.class, new GOTEntityReachSoldier(world));
		entities.put(GOTEntityReachSoldierArcher.class, new GOTEntityReachSoldierArcher(world));
		entities.put(GOTEntityReachGuard.class, new GOTEntityReachGuard(world));
		entities.put(GOTEntityReachBannerBearer.class, new GOTEntityReachBannerBearer(world));
		entities.put(GOTEntityReachCaptain.class, new GOTEntityReachCaptain(world));
		entities.put(GOTEntityReachBaker.class, new GOTEntityReachBaker(world));
		entities.put(GOTEntityReachBartender.class, new GOTEntityReachBartender(world));
		entities.put(GOTEntityReachBlacksmith.class, new GOTEntityReachBlacksmith(world));
		entities.put(GOTEntityReachGoldsmith.class, new GOTEntityReachGoldsmith(world));
		entities.put(GOTEntityReachBrewer.class, new GOTEntityReachBrewer(world));
		entities.put(GOTEntityReachButcher.class, new GOTEntityReachButcher(world));
		entities.put(GOTEntityReachFarmer.class, new GOTEntityReachFarmer(world));
		entities.put(GOTEntityReachFarmhand.class, new GOTEntityReachFarmhand(world));
		entities.put(GOTEntityReachFishmonger.class, new GOTEntityReachFishmonger(world));
		entities.put(GOTEntityReachFlorist.class, new GOTEntityReachFlorist(world));
		entities.put(GOTEntityReachGreengrocer.class, new GOTEntityReachGreengrocer(world));
		entities.put(GOTEntityReachLumberman.class, new GOTEntityReachLumberman(world));
		entities.put(GOTEntityReachMason.class, new GOTEntityReachMason(world));
		entities.put(GOTEntityDorneMan.class, new GOTEntityDorneMan(world));
		entities.put(GOTEntityDorneLevyman.class, new GOTEntityDorneLevyman(world));
		entities.put(GOTEntityDorneLevymanArcher.class, new GOTEntityDorneLevymanArcher(world));
		entities.put(GOTEntityDorneSoldier.class, new GOTEntityDorneSoldier(world));
		entities.put(GOTEntityDorneSoldierArcher.class, new GOTEntityDorneSoldierArcher(world));
		entities.put(GOTEntityDorneBannerBearer.class, new GOTEntityDorneBannerBearer(world));
		entities.put(GOTEntityDorneCaptain.class, new GOTEntityDorneCaptain(world));
		entities.put(GOTEntityDorneBaker.class, new GOTEntityDorneBaker(world));
		entities.put(GOTEntityDorneBartender.class, new GOTEntityDorneBartender(world));
		entities.put(GOTEntityDorneBlacksmith.class, new GOTEntityDorneBlacksmith(world));
		entities.put(GOTEntityDorneGoldsmith.class, new GOTEntityDorneGoldsmith(world));
		entities.put(GOTEntityDorneBrewer.class, new GOTEntityDorneBrewer(world));
		entities.put(GOTEntityDorneButcher.class, new GOTEntityDorneButcher(world));
		entities.put(GOTEntityDorneFarmer.class, new GOTEntityDorneFarmer(world));
		entities.put(GOTEntityDorneFarmhand.class, new GOTEntityDorneFarmhand(world));
		entities.put(GOTEntityDorneFishmonger.class, new GOTEntityDorneFishmonger(world));
		entities.put(GOTEntityDorneFlorist.class, new GOTEntityDorneFlorist(world));
		entities.put(GOTEntityDorneGreengrocer.class, new GOTEntityDorneGreengrocer(world));
		entities.put(GOTEntityDorneLumberman.class, new GOTEntityDorneLumberman(world));
		entities.put(GOTEntityDorneMason.class, new GOTEntityDorneMason(world));
		entities.put(GOTEntityBraavosMan.class, new GOTEntityBraavosMan(world));
		entities.put(GOTEntityBraavosLevyman.class, new GOTEntityBraavosLevyman(world));
		entities.put(GOTEntityBraavosLevymanArcher.class, new GOTEntityBraavosLevymanArcher(world));
		entities.put(GOTEntityBraavosSoldier.class, new GOTEntityBraavosSoldier(world));
		entities.put(GOTEntityBraavosSoldierArcher.class, new GOTEntityBraavosSoldierArcher(world));
		entities.put(GOTEntityBraavosBannerBearer.class, new GOTEntityBraavosBannerBearer(world));
		entities.put(GOTEntityBraavosGeneral.class, new GOTEntityBraavosGeneral(world));
		entities.put(GOTEntityBraavosBaker.class, new GOTEntityBraavosBaker(world));
		entities.put(GOTEntityBraavosBartender.class, new GOTEntityBraavosBartender(world));
		entities.put(GOTEntityBraavosBlacksmith.class, new GOTEntityBraavosBlacksmith(world));
		entities.put(GOTEntityBraavosBrewer.class, new GOTEntityBraavosBrewer(world));
		entities.put(GOTEntityBraavosButcher.class, new GOTEntityBraavosButcher(world));
		entities.put(GOTEntityBraavosFarmer.class, new GOTEntityBraavosFarmer(world));
		entities.put(GOTEntityBraavosFarmhand.class, new GOTEntityBraavosFarmhand(world));
		entities.put(GOTEntityBraavosFishmonger.class, new GOTEntityBraavosFishmonger(world));
		entities.put(GOTEntityBraavosFlorist.class, new GOTEntityBraavosFlorist(world));
		entities.put(GOTEntityBraavosGoldsmith.class, new GOTEntityBraavosGoldsmith(world));
		entities.put(GOTEntityBraavosLumberman.class, new GOTEntityBraavosLumberman(world));
		entities.put(GOTEntityBraavosMason.class, new GOTEntityBraavosMason(world));
		entities.put(GOTEntityBraavosMiner.class, new GOTEntityBraavosMiner(world));
		entities.put(GOTEntityVolantisMan.class, new GOTEntityVolantisMan(world));
		entities.put(GOTEntityVolantisLevyman.class, new GOTEntityVolantisLevyman(world));
		entities.put(GOTEntityVolantisLevymanArcher.class, new GOTEntityVolantisLevymanArcher(world));
		entities.put(GOTEntityVolantisSoldier.class, new GOTEntityVolantisSoldier(world));
		entities.put(GOTEntityVolantisSoldierArcher.class, new GOTEntityVolantisSoldierArcher(world));
		entities.put(GOTEntityVolantisBannerBearer.class, new GOTEntityVolantisBannerBearer(world));
		entities.put(GOTEntityVolantisGeneral.class, new GOTEntityVolantisGeneral(world));
		entities.put(GOTEntityVolantisSlave.class, new GOTEntityVolantisSlave(world));
		entities.put(GOTEntityVolantisSlaver.class, new GOTEntityVolantisSlaver(world));
		entities.put(GOTEntityVolantisBaker.class, new GOTEntityVolantisBaker(world));
		entities.put(GOTEntityVolantisBartender.class, new GOTEntityVolantisBartender(world));
		entities.put(GOTEntityVolantisBlacksmith.class, new GOTEntityVolantisBlacksmith(world));
		entities.put(GOTEntityVolantisBrewer.class, new GOTEntityVolantisBrewer(world));
		entities.put(GOTEntityVolantisButcher.class, new GOTEntityVolantisButcher(world));
		entities.put(GOTEntityVolantisFishmonger.class, new GOTEntityVolantisFishmonger(world));
		entities.put(GOTEntityVolantisFlorist.class, new GOTEntityVolantisFlorist(world));
		entities.put(GOTEntityVolantisGoldsmith.class, new GOTEntityVolantisGoldsmith(world));
		entities.put(GOTEntityVolantisLumberman.class, new GOTEntityVolantisLumberman(world));
		entities.put(GOTEntityVolantisMason.class, new GOTEntityVolantisMason(world));
		entities.put(GOTEntityVolantisMiner.class, new GOTEntityVolantisMiner(world));
		entities.put(GOTEntityPentosMan.class, new GOTEntityPentosMan(world));
		entities.put(GOTEntityPentosLevyman.class, new GOTEntityPentosLevyman(world));
		entities.put(GOTEntityPentosLevymanArcher.class, new GOTEntityPentosLevymanArcher(world));
		entities.put(GOTEntityPentosGuard.class, new GOTEntityPentosGuard(world));
		entities.put(GOTEntityPentosBannerBearer.class, new GOTEntityPentosBannerBearer(world));
		entities.put(GOTEntityPentosGuardCaptain.class, new GOTEntityPentosGuardCaptain(world));
		entities.put(GOTEntityPentosBaker.class, new GOTEntityPentosBaker(world));
		entities.put(GOTEntityPentosBartender.class, new GOTEntityPentosBartender(world));
		entities.put(GOTEntityPentosBlacksmith.class, new GOTEntityPentosBlacksmith(world));
		entities.put(GOTEntityPentosBrewer.class, new GOTEntityPentosBrewer(world));
		entities.put(GOTEntityPentosButcher.class, new GOTEntityPentosButcher(world));
		entities.put(GOTEntityPentosFarmer.class, new GOTEntityPentosFarmer(world));
		entities.put(GOTEntityPentosFarmhand.class, new GOTEntityPentosFarmhand(world));
		entities.put(GOTEntityPentosFishmonger.class, new GOTEntityPentosFishmonger(world));
		entities.put(GOTEntityPentosFlorist.class, new GOTEntityPentosFlorist(world));
		entities.put(GOTEntityPentosGoldsmith.class, new GOTEntityPentosGoldsmith(world));
		entities.put(GOTEntityPentosLumberman.class, new GOTEntityPentosLumberman(world));
		entities.put(GOTEntityPentosMason.class, new GOTEntityPentosMason(world));
		entities.put(GOTEntityPentosMiner.class, new GOTEntityPentosMiner(world));
		entities.put(GOTEntityNorvosMan.class, new GOTEntityNorvosMan(world));
		entities.put(GOTEntityNorvosLevyman.class, new GOTEntityNorvosLevyman(world));
		entities.put(GOTEntityNorvosLevymanArcher.class, new GOTEntityNorvosLevymanArcher(world));
		entities.put(GOTEntityNorvosGuard.class, new GOTEntityNorvosGuard(world));
		entities.put(GOTEntityNorvosBannerBearer.class, new GOTEntityNorvosBannerBearer(world));
		entities.put(GOTEntityNorvosGuardCaptain.class, new GOTEntityNorvosGuardCaptain(world));
		entities.put(GOTEntityNorvosBaker.class, new GOTEntityNorvosBaker(world));
		entities.put(GOTEntityNorvosBartender.class, new GOTEntityNorvosBartender(world));
		entities.put(GOTEntityNorvosBlacksmith.class, new GOTEntityNorvosBlacksmith(world));
		entities.put(GOTEntityNorvosBrewer.class, new GOTEntityNorvosBrewer(world));
		entities.put(GOTEntityNorvosButcher.class, new GOTEntityNorvosButcher(world));
		entities.put(GOTEntityNorvosFarmer.class, new GOTEntityNorvosFarmer(world));
		entities.put(GOTEntityNorvosFarmhand.class, new GOTEntityNorvosFarmhand(world));
		entities.put(GOTEntityNorvosFishmonger.class, new GOTEntityNorvosFishmonger(world));
		entities.put(GOTEntityNorvosFlorist.class, new GOTEntityNorvosFlorist(world));
		entities.put(GOTEntityNorvosGoldsmith.class, new GOTEntityNorvosGoldsmith(world));
		entities.put(GOTEntityNorvosLumberman.class, new GOTEntityNorvosLumberman(world));
		entities.put(GOTEntityNorvosMason.class, new GOTEntityNorvosMason(world));
		entities.put(GOTEntityNorvosMiner.class, new GOTEntityNorvosMiner(world));
		entities.put(GOTEntityLorathMan.class, new GOTEntityLorathMan(world));
		entities.put(GOTEntityLorathLevyman.class, new GOTEntityLorathLevyman(world));
		entities.put(GOTEntityLorathLevymanArcher.class, new GOTEntityLorathLevymanArcher(world));
		entities.put(GOTEntityLorathSoldier.class, new GOTEntityLorathSoldier(world));
		entities.put(GOTEntityLorathSoldierArcher.class, new GOTEntityLorathSoldierArcher(world));
		entities.put(GOTEntityLorathBannerBearer.class, new GOTEntityLorathBannerBearer(world));
		entities.put(GOTEntityLorathGeneral.class, new GOTEntityLorathGeneral(world));
		entities.put(GOTEntityLorathBaker.class, new GOTEntityLorathBaker(world));
		entities.put(GOTEntityLorathBartender.class, new GOTEntityLorathBartender(world));
		entities.put(GOTEntityLorathBlacksmith.class, new GOTEntityLorathBlacksmith(world));
		entities.put(GOTEntityLorathBrewer.class, new GOTEntityLorathBrewer(world));
		entities.put(GOTEntityLorathButcher.class, new GOTEntityLorathButcher(world));
		entities.put(GOTEntityLorathFarmer.class, new GOTEntityLorathFarmer(world));
		entities.put(GOTEntityLorathFarmhand.class, new GOTEntityLorathFarmhand(world));
		entities.put(GOTEntityLorathFishmonger.class, new GOTEntityLorathFishmonger(world));
		entities.put(GOTEntityLorathFlorist.class, new GOTEntityLorathFlorist(world));
		entities.put(GOTEntityLorathGoldsmith.class, new GOTEntityLorathGoldsmith(world));
		entities.put(GOTEntityLorathLumberman.class, new GOTEntityLorathLumberman(world));
		entities.put(GOTEntityLorathMason.class, new GOTEntityLorathMason(world));
		entities.put(GOTEntityLorathMiner.class, new GOTEntityLorathMiner(world));
		entities.put(GOTEntityQohorMan.class, new GOTEntityQohorMan(world));
		entities.put(GOTEntityQohorLevyman.class, new GOTEntityQohorLevyman(world));
		entities.put(GOTEntityQohorLevymanArcher.class, new GOTEntityQohorLevymanArcher(world));
		entities.put(GOTEntityQohorGuard.class, new GOTEntityQohorGuard(world));
		entities.put(GOTEntityQohorUnsullied.class, new GOTEntityQohorUnsullied(world));
		entities.put(GOTEntityQohorBannerBearer.class, new GOTEntityQohorBannerBearer(world));
		entities.put(GOTEntityQohorGuardCaptain.class, new GOTEntityQohorGuardCaptain(world));
		entities.put(GOTEntityQohorBaker.class, new GOTEntityQohorBaker(world));
		entities.put(GOTEntityQohorBartender.class, new GOTEntityQohorBartender(world));
		entities.put(GOTEntityQohorBlacksmith.class, new GOTEntityQohorBlacksmith(world));
		entities.put(GOTEntityQohorBrewer.class, new GOTEntityQohorBrewer(world));
		entities.put(GOTEntityQohorButcher.class, new GOTEntityQohorButcher(world));
		entities.put(GOTEntityQohorFarmer.class, new GOTEntityQohorFarmer(world));
		entities.put(GOTEntityQohorFarmhand.class, new GOTEntityQohorFarmhand(world));
		entities.put(GOTEntityQohorFishmonger.class, new GOTEntityQohorFishmonger(world));
		entities.put(GOTEntityQohorFlorist.class, new GOTEntityQohorFlorist(world));
		entities.put(GOTEntityQohorGoldsmith.class, new GOTEntityQohorGoldsmith(world));
		entities.put(GOTEntityQohorLumberman.class, new GOTEntityQohorLumberman(world));
		entities.put(GOTEntityQohorMason.class, new GOTEntityQohorMason(world));
		entities.put(GOTEntityQohorMiner.class, new GOTEntityQohorMiner(world));
		entities.put(GOTEntityLysMan.class, new GOTEntityLysMan(world));
		entities.put(GOTEntityLysLevyman.class, new GOTEntityLysLevyman(world));
		entities.put(GOTEntityLysLevymanArcher.class, new GOTEntityLysLevymanArcher(world));
		entities.put(GOTEntityLysSoldier.class, new GOTEntityLysSoldier(world));
		entities.put(GOTEntityLysSoldierArcher.class, new GOTEntityLysSoldierArcher(world));
		entities.put(GOTEntityLysBannerBearer.class, new GOTEntityLysBannerBearer(world));
		entities.put(GOTEntityLysGeneral.class, new GOTEntityLysGeneral(world));
		entities.put(GOTEntityLysSlave.class, new GOTEntityLysSlave(world));
		entities.put(GOTEntityLysSlaver.class, new GOTEntityLysSlaver(world));
		entities.put(GOTEntityLysBaker.class, new GOTEntityLysBaker(world));
		entities.put(GOTEntityLysBartender.class, new GOTEntityLysBartender(world));
		entities.put(GOTEntityLysBlacksmith.class, new GOTEntityLysBlacksmith(world));
		entities.put(GOTEntityLysBrewer.class, new GOTEntityLysBrewer(world));
		entities.put(GOTEntityLysButcher.class, new GOTEntityLysButcher(world));
		entities.put(GOTEntityLysFishmonger.class, new GOTEntityLysFishmonger(world));
		entities.put(GOTEntityLysFlorist.class, new GOTEntityLysFlorist(world));
		entities.put(GOTEntityLysGoldsmith.class, new GOTEntityLysGoldsmith(world));
		entities.put(GOTEntityLysLumberman.class, new GOTEntityLysLumberman(world));
		entities.put(GOTEntityLysMason.class, new GOTEntityLysMason(world));
		entities.put(GOTEntityLysMiner.class, new GOTEntityLysMiner(world));
		entities.put(GOTEntityMyrMan.class, new GOTEntityMyrMan(world));
		entities.put(GOTEntityMyrLevyman.class, new GOTEntityMyrLevyman(world));
		entities.put(GOTEntityMyrLevymanArcher.class, new GOTEntityMyrLevymanArcher(world));
		entities.put(GOTEntityMyrSoldier.class, new GOTEntityMyrSoldier(world));
		entities.put(GOTEntityMyrSoldierArcher.class, new GOTEntityMyrSoldierArcher(world));
		entities.put(GOTEntityMyrBannerBearer.class, new GOTEntityMyrBannerBearer(world));
		entities.put(GOTEntityMyrGeneral.class, new GOTEntityMyrGeneral(world));
		entities.put(GOTEntityMyrSlave.class, new GOTEntityMyrSlave(world));
		entities.put(GOTEntityMyrSlaver.class, new GOTEntityMyrSlaver(world));
		entities.put(GOTEntityMyrBaker.class, new GOTEntityMyrBaker(world));
		entities.put(GOTEntityMyrBartender.class, new GOTEntityMyrBartender(world));
		entities.put(GOTEntityMyrBlacksmith.class, new GOTEntityMyrBlacksmith(world));
		entities.put(GOTEntityMyrBrewer.class, new GOTEntityMyrBrewer(world));
		entities.put(GOTEntityMyrButcher.class, new GOTEntityMyrButcher(world));
		entities.put(GOTEntityMyrFishmonger.class, new GOTEntityMyrFishmonger(world));
		entities.put(GOTEntityMyrFlorist.class, new GOTEntityMyrFlorist(world));
		entities.put(GOTEntityMyrGoldsmith.class, new GOTEntityMyrGoldsmith(world));
		entities.put(GOTEntityMyrLumberman.class, new GOTEntityMyrLumberman(world));
		entities.put(GOTEntityMyrMason.class, new GOTEntityMyrMason(world));
		entities.put(GOTEntityMyrMiner.class, new GOTEntityMyrMiner(world));
		entities.put(GOTEntityGoldenWarrior.class, new GOTEntityGoldenWarrior(world));
		entities.put(GOTEntityGoldenSpearman.class, new GOTEntityGoldenSpearman(world));
		entities.put(GOTEntityGoldenBannerBearer.class, new GOTEntityGoldenBannerBearer(world));
		entities.put(GOTEntityGoldenCaptain.class, new GOTEntityGoldenCaptain(world));
		entities.put(GOTEntityTyroshMan.class, new GOTEntityTyroshMan(world));
		entities.put(GOTEntityTyroshLevyman.class, new GOTEntityTyroshLevyman(world));
		entities.put(GOTEntityTyroshLevymanArcher.class, new GOTEntityTyroshLevymanArcher(world));
		entities.put(GOTEntityTyroshSoldier.class, new GOTEntityTyroshSoldier(world));
		entities.put(GOTEntityTyroshSoldierArcher.class, new GOTEntityTyroshSoldierArcher(world));
		entities.put(GOTEntityTyroshBannerBearer.class, new GOTEntityTyroshBannerBearer(world));
		entities.put(GOTEntityTyroshGeneral.class, new GOTEntityTyroshGeneral(world));
		entities.put(GOTEntityTyroshSlave.class, new GOTEntityTyroshSlave(world));
		entities.put(GOTEntityTyroshSlaver.class, new GOTEntityTyroshSlaver(world));
		entities.put(GOTEntityTyroshBaker.class, new GOTEntityTyroshBaker(world));
		entities.put(GOTEntityTyroshBartender.class, new GOTEntityTyroshBartender(world));
		entities.put(GOTEntityTyroshBlacksmith.class, new GOTEntityTyroshBlacksmith(world));
		entities.put(GOTEntityTyroshBrewer.class, new GOTEntityTyroshBrewer(world));
		entities.put(GOTEntityTyroshButcher.class, new GOTEntityTyroshButcher(world));
		entities.put(GOTEntityTyroshFishmonger.class, new GOTEntityTyroshFishmonger(world));
		entities.put(GOTEntityTyroshFlorist.class, new GOTEntityTyroshFlorist(world));
		entities.put(GOTEntityTyroshGoldsmith.class, new GOTEntityTyroshGoldsmith(world));
		entities.put(GOTEntityTyroshLumberman.class, new GOTEntityTyroshLumberman(world));
		entities.put(GOTEntityTyroshMason.class, new GOTEntityTyroshMason(world));
		entities.put(GOTEntityTyroshMiner.class, new GOTEntityTyroshMiner(world));
		entities.put(GOTEntityGhiscarMan.class, new GOTEntityGhiscarMan(world));
		entities.put(GOTEntityGhiscarLevyman.class, new GOTEntityGhiscarLevyman(world));
		entities.put(GOTEntityGhiscarLevymanArcher.class, new GOTEntityGhiscarLevymanArcher(world));
		entities.put(GOTEntityGhiscarUnsullied.class, new GOTEntityGhiscarUnsullied(world));
		entities.put(GOTEntityGhiscarCorsair.class, new GOTEntityGhiscarCorsair(world));
		entities.put(GOTEntityGhiscarCorsairArcher.class, new GOTEntityGhiscarCorsairArcher(world));
		entities.put(GOTEntityGhiscarGuard.class, new GOTEntityGhiscarGuard(world));
		entities.put(GOTEntityGhiscarGladiator.class, new GOTEntityGhiscarGladiator(world));
		entities.put(GOTEntityGhiscarHarpy.class, new GOTEntityGhiscarHarpy(world));
		entities.put(GOTEntityGhiscarBannerBearer.class, new GOTEntityGhiscarBannerBearer(world));
		entities.put(GOTEntityGhiscarSlave.class, new GOTEntityGhiscarSlave(world));
		entities.put(GOTEntityGhiscarSlaver.class, new GOTEntityGhiscarSlaver(world));
		entities.put(GOTEntityGhiscarAdmiral.class, new GOTEntityGhiscarAdmiral(world));
		entities.put(GOTEntityGhiscarBaker.class, new GOTEntityGhiscarBaker(world));
		entities.put(GOTEntityGhiscarBartender.class, new GOTEntityGhiscarBartender(world));
		entities.put(GOTEntityGhiscarBlacksmith.class, new GOTEntityGhiscarBlacksmith(world));
		entities.put(GOTEntityGhiscarBrewer.class, new GOTEntityGhiscarBrewer(world));
		entities.put(GOTEntityGhiscarButcher.class, new GOTEntityGhiscarButcher(world));
		entities.put(GOTEntityGhiscarFishmonger.class, new GOTEntityGhiscarFishmonger(world));
		entities.put(GOTEntityGhiscarFlorist.class, new GOTEntityGhiscarFlorist(world));
		entities.put(GOTEntityGhiscarGoldsmith.class, new GOTEntityGhiscarGoldsmith(world));
		entities.put(GOTEntityGhiscarLumberman.class, new GOTEntityGhiscarLumberman(world));
		entities.put(GOTEntityGhiscarMason.class, new GOTEntityGhiscarMason(world));
		entities.put(GOTEntityGhiscarMiner.class, new GOTEntityGhiscarMiner(world));
		entities.put(GOTEntityQarthMan.class, new GOTEntityQarthMan(world));
		entities.put(GOTEntityQarthLevyman.class, new GOTEntityQarthLevyman(world));
		entities.put(GOTEntityQarthLevymanArcher.class, new GOTEntityQarthLevymanArcher(world));
		entities.put(GOTEntityQarthGuard.class, new GOTEntityQarthGuard(world));
		entities.put(GOTEntityQarthBannerBearer.class, new GOTEntityQarthBannerBearer(world));
		entities.put(GOTEntityQarthWarlock.class, new GOTEntityQarthWarlock(world));
		entities.put(GOTEntityQarthGuardCaptain.class, new GOTEntityQarthGuardCaptain(world));
		entities.put(GOTEntityQarthBaker.class, new GOTEntityQarthBaker(world));
		entities.put(GOTEntityQarthBartender.class, new GOTEntityQarthBartender(world));
		entities.put(GOTEntityQarthBlacksmith.class, new GOTEntityQarthBlacksmith(world));
		entities.put(GOTEntityQarthBrewer.class, new GOTEntityQarthBrewer(world));
		entities.put(GOTEntityQarthButcher.class, new GOTEntityQarthButcher(world));
		entities.put(GOTEntityQarthFarmer.class, new GOTEntityQarthFarmer(world));
		entities.put(GOTEntityQarthFarmhand.class, new GOTEntityQarthFarmhand(world));
		entities.put(GOTEntityQarthFishmonger.class, new GOTEntityQarthFishmonger(world));
		entities.put(GOTEntityQarthFlorist.class, new GOTEntityQarthFlorist(world));
		entities.put(GOTEntityQarthGoldsmith.class, new GOTEntityQarthGoldsmith(world));
		entities.put(GOTEntityQarthLumberman.class, new GOTEntityQarthLumberman(world));
		entities.put(GOTEntityQarthMason.class, new GOTEntityQarthMason(world));
		entities.put(GOTEntityQarthMiner.class, new GOTEntityQarthMiner(world));
		entities.put(GOTEntityLhazarMan.class, new GOTEntityLhazarMan(world));
		entities.put(GOTEntityLhazarWarrior.class, new GOTEntityLhazarWarrior(world));
		entities.put(GOTEntityLhazarArcher.class, new GOTEntityLhazarArcher(world));
		entities.put(GOTEntityLhazarBannerBearer.class, new GOTEntityLhazarBannerBearer(world));
		entities.put(GOTEntityLhazarWarlord.class, new GOTEntityLhazarWarlord(world));
		entities.put(GOTEntityLhazarBlacksmith.class, new GOTEntityLhazarBlacksmith(world));
		entities.put(GOTEntityLhazarMason.class, new GOTEntityLhazarMason(world));
		entities.put(GOTEntityLhazarButcher.class, new GOTEntityLhazarButcher(world));
		entities.put(GOTEntityLhazarBrewer.class, new GOTEntityLhazarBrewer(world));
		entities.put(GOTEntityLhazarFishmonger.class, new GOTEntityLhazarFishmonger(world));
		entities.put(GOTEntityLhazarBaker.class, new GOTEntityLhazarBaker(world));
		entities.put(GOTEntityLhazarMiner.class, new GOTEntityLhazarMiner(world));
		entities.put(GOTEntityLhazarGoldsmith.class, new GOTEntityLhazarGoldsmith(world));
		entities.put(GOTEntityLhazarLumberman.class, new GOTEntityLhazarLumberman(world));
		entities.put(GOTEntityLhazarHunter.class, new GOTEntityLhazarHunter(world));
		entities.put(GOTEntityLhazarBartender.class, new GOTEntityLhazarBartender(world));
		entities.put(GOTEntityLhazarFarmer.class, new GOTEntityLhazarFarmer(world));
		entities.put(GOTEntityDothraki.class, new GOTEntityDothraki(world));
		entities.put(GOTEntityDothrakiArcher.class, new GOTEntityDothrakiArcher(world));
		entities.put(GOTEntityDothrakiKhal.class, new GOTEntityDothrakiKhal(world));
		entities.put(GOTEntityDothrakiKhalin.class, new GOTEntityDothrakiKhalin(world));
		entities.put(GOTEntityIbbenMan.class, new GOTEntityIbbenMan(world));
		entities.put(GOTEntityIbbenWarrior.class, new GOTEntityIbbenWarrior(world));
		entities.put(GOTEntityIbbenArcher.class, new GOTEntityIbbenArcher(world));
		entities.put(GOTEntityIbbenAxeThrower.class, new GOTEntityIbbenAxeThrower(world));
		entities.put(GOTEntityIbbenWarlord.class, new GOTEntityIbbenWarlord(world));
		entities.put(GOTEntityIbbenBlacksmith.class, new GOTEntityIbbenBlacksmith(world));
		entities.put(GOTEntityIbbenMeadhost.class, new GOTEntityIbbenMeadhost(world));
		entities.put(GOTEntityIbbenBannerBearer.class, new GOTEntityIbbenBannerBearer(world));
		entities.put(GOTEntityIbbenFarmhand.class, new GOTEntityIbbenFarmhand(world));
		entities.put(GOTEntityIbbenFarmer.class, new GOTEntityIbbenFarmer(world));
		entities.put(GOTEntityIbbenLumberman.class, new GOTEntityIbbenLumberman(world));
		entities.put(GOTEntityIbbenBuilder.class, new GOTEntityIbbenBuilder(world));
		entities.put(GOTEntityIbbenBrewer.class, new GOTEntityIbbenBrewer(world));
		entities.put(GOTEntityIbbenButcher.class, new GOTEntityIbbenButcher(world));
		entities.put(GOTEntityIbbenFishmonger.class, new GOTEntityIbbenFishmonger(world));
		entities.put(GOTEntityIbbenBaker.class, new GOTEntityIbbenBaker(world));
		entities.put(GOTEntityIbbenOrcharder.class, new GOTEntityIbbenOrcharder(world));
		entities.put(GOTEntityIbbenStablemaster.class, new GOTEntityIbbenStablemaster(world));
		entities.put(GOTEntityIfekevron.class, new GOTEntityIfekevron(world));
		entities.put(GOTEntityJogos.class, new GOTEntityJogos(world));
		entities.put(GOTEntityJogosArcher.class, new GOTEntityJogosArcher(world));
		entities.put(GOTEntityJogosBannerBearer.class, new GOTEntityJogosBannerBearer(world));
		entities.put(GOTEntityJogosChief.class, new GOTEntityJogosChief(world));
		entities.put(GOTEntityJogosShaman.class, new GOTEntityJogosShaman(world));
		entities.put(GOTEntityMossovyMan.class, new GOTEntityMossovyMan(world));
		entities.put(GOTEntityMossovyWitcher.class, new GOTEntityMossovyWitcher(world));
		entities.put(GOTEntityMossovyBlacksmith.class, new GOTEntityMossovyBlacksmith(world));
		entities.put(GOTEntityMossovyFarmer.class, new GOTEntityMossovyFarmer(world));
		entities.put(GOTEntityMossovyBartender.class, new GOTEntityMossovyBartender(world));
		entities.put(GOTEntityYiTiMan.class, new GOTEntityYiTiMan(world));
		entities.put(GOTEntityYiTiLevyman.class, new GOTEntityYiTiLevyman(world));
		entities.put(GOTEntityYiTiLevymanCrossbower.class, new GOTEntityYiTiLevymanCrossbower(world));
		entities.put(GOTEntityYiTiSoldier.class, new GOTEntityYiTiSoldier(world));
		entities.put(GOTEntityYiTiSoldierCrossbower.class, new GOTEntityYiTiSoldierCrossbower(world));
		entities.put(GOTEntityYiTiFrontier.class, new GOTEntityYiTiFrontier(world));
		entities.put(GOTEntityYiTiFrontierCrossbower.class, new GOTEntityYiTiFrontierCrossbower(world));
		entities.put(GOTEntityYiTiSamurai.class, new GOTEntityYiTiSamurai(world));
		entities.put(GOTEntityYiTiBombardier.class, new GOTEntityYiTiBombardier(world));
		entities.put(GOTEntityYiTiBannerBearer.class, new GOTEntityYiTiBannerBearer(world));
		entities.put(GOTEntityYiTiFireThrower.class, new GOTEntityYiTiFireThrower(world));
		entities.put(GOTEntityYiTiShogune.class, new GOTEntityYiTiShogune(world));
		entities.put(GOTEntityYiTiFarmhand.class, new GOTEntityYiTiFarmhand(world));
		entities.put(GOTEntityYiTiBlacksmith.class, new GOTEntityYiTiBlacksmith(world));
		entities.put(GOTEntityYiTiLumberman.class, new GOTEntityYiTiLumberman(world));
		entities.put(GOTEntityYiTiMason.class, new GOTEntityYiTiMason(world));
		entities.put(GOTEntityYiTiButcher.class, new GOTEntityYiTiButcher(world));
		entities.put(GOTEntityYiTiBrewer.class, new GOTEntityYiTiBrewer(world));
		entities.put(GOTEntityYiTiFishmonger.class, new GOTEntityYiTiFishmonger(world));
		entities.put(GOTEntityYiTiBaker.class, new GOTEntityYiTiBaker(world));
		entities.put(GOTEntityYiTiHunter.class, new GOTEntityYiTiHunter(world));
		entities.put(GOTEntityYiTiFarmer.class, new GOTEntityYiTiFarmer(world));
		entities.put(GOTEntityYiTiGoldsmith.class, new GOTEntityYiTiGoldsmith(world));
		entities.put(GOTEntityYiTiBartender.class, new GOTEntityYiTiBartender(world));
		entities.put(GOTEntityAsshaiMan.class, new GOTEntityAsshaiMan(world));
		entities.put(GOTEntityAsshaiWarrior.class, new GOTEntityAsshaiWarrior(world));
		entities.put(GOTEntityAsshaiBannerBearer.class, new GOTEntityAsshaiBannerBearer(world));
		entities.put(GOTEntityAsshaiShadowbinder.class, new GOTEntityAsshaiShadowbinder(world));
		entities.put(GOTEntityAsshaiSpherebinder.class, new GOTEntityAsshaiSpherebinder(world));
		entities.put(GOTEntityAsshaiAlchemist.class, new GOTEntityAsshaiAlchemist(world));
		entities.put(GOTEntityAsshaiCaptain.class, new GOTEntityAsshaiCaptain(world));
		entities.put(GOTEntitySothoryosMan.class, new GOTEntitySothoryosMan(world));
		entities.put(GOTEntitySothoryosWarrior.class, new GOTEntitySothoryosWarrior(world));
		entities.put(GOTEntitySothoryosBannerBearer.class, new GOTEntitySothoryosBannerBearer(world));
		entities.put(GOTEntitySothoryosChieftain.class, new GOTEntitySothoryosChieftain(world));
		entities.put(GOTEntitySothoryosBlowgunner.class, new GOTEntitySothoryosBlowgunner(world));
		entities.put(GOTEntitySothoryosShaman.class, new GOTEntitySothoryosShaman(world));
		entities.put(GOTEntitySothoryosFarmer.class, new GOTEntitySothoryosFarmer(world));
		entities.put(GOTEntitySothoryosFarmhand.class, new GOTEntitySothoryosFarmhand(world));
		entities.put(GOTEntitySothoryosSmith.class, new GOTEntitySothoryosSmith(world));
		entities.put(GOTEntitySummerMan.class, new GOTEntitySummerMan(world));
		entities.put(GOTEntitySummerWarrior.class, new GOTEntitySummerWarrior(world));
		entities.put(GOTEntitySummerArcher.class, new GOTEntitySummerArcher(world));
		entities.put(GOTEntitySummerBannerBearer.class, new GOTEntitySummerBannerBearer(world));
		entities.put(GOTEntitySummerWarlord.class, new GOTEntitySummerWarlord(world));
		entities.put(GOTEntitySummerBlacksmith.class, new GOTEntitySummerBlacksmith(world));
		entities.put(GOTEntitySummerBartender.class, new GOTEntitySummerBartender(world));
		entities.put(GOTEntitySummerLumberman.class, new GOTEntitySummerLumberman(world));
		entities.put(GOTEntitySummerMason.class, new GOTEntitySummerMason(world));
		entities.put(GOTEntitySummerButcher.class, new GOTEntitySummerButcher(world));
		entities.put(GOTEntitySummerBrewer.class, new GOTEntitySummerBrewer(world));
		entities.put(GOTEntitySummerFishmonger.class, new GOTEntitySummerFishmonger(world));
		entities.put(GOTEntitySummerBaker.class, new GOTEntitySummerBaker(world));
		entities.put(GOTEntitySummerHunter.class, new GOTEntitySummerHunter(world));
		entities.put(GOTEntitySummerMiner.class, new GOTEntitySummerMiner(world));
		entities.put(GOTEntitySummerFarmhand.class, new GOTEntitySummerFarmhand(world));
		entities.put(GOTEntitySummerFarmer.class, new GOTEntitySummerFarmer(world));
		entities.put(GOTEntityUlthosSpider.class, new GOTEntityUlthosSpider(world));
		entities.put(GOTEntityNightKing.class, new GOTEntityNightKing(world));
		entities.put(GOTEntityCraster.class, new GOTEntityCraster(world));
		entities.put(GOTEntityManceRayder.class, new GOTEntityManceRayder(world));
		entities.put(GOTEntityTormund.class, new GOTEntityTormund(world));
		entities.put(GOTEntityYgritte.class, new GOTEntityYgritte(world));
		entities.put(GOTEntityAemonTargaryen.class, new GOTEntityAemonTargaryen(world));
		entities.put(GOTEntityAlliserThorne.class, new GOTEntityAlliserThorne(world));
		entities.put(GOTEntityBenjenStark.class, new GOTEntityBenjenStark(world));
		entities.put(GOTEntityCotterPyke.class, new GOTEntityCotterPyke(world));
		entities.put(GOTEntityDenysMallister.class, new GOTEntityDenysMallister(world));
		entities.put(GOTEntityEdd.class, new GOTEntityEdd(world));
		entities.put(GOTEntityHarmune.class, new GOTEntityHarmune(world));
		entities.put(GOTEntityJeorMormont.class, new GOTEntityJeorMormont(world));
		entities.put(GOTEntityMullin.class, new GOTEntityMullin(world));
		entities.put(GOTEntitySamwellTarly.class, new GOTEntitySamwellTarly(world));
		entities.put(GOTEntityYoren.class, new GOTEntityYoren(world));
		entities.put(GOTEntityAryaStark.class, new GOTEntityAryaStark(world));
		entities.put(GOTEntityBarbreyDustin.class, new GOTEntityBarbreyDustin(world));
		entities.put(GOTEntityBranStark.class, new GOTEntityBranStark(world));
		entities.put(GOTEntityCatelynStark.class, new GOTEntityCatelynStark(world));
		entities.put(GOTEntityCleyCerwyn.class, new GOTEntityCleyCerwyn(world));
		entities.put(GOTEntityHelmanTallhart.class, new GOTEntityHelmanTallhart(world));
		entities.put(GOTEntityHodor.class, new GOTEntityHodor(world));
		entities.put(GOTEntityHowlandReed.class, new GOTEntityHowlandReed(world));
		entities.put(GOTEntityJohnUmber.class, new GOTEntityJohnUmber(world));
		entities.put(GOTEntityLuwin.class, new GOTEntityLuwin(world));
		entities.put(GOTEntityMaegeMormont.class, new GOTEntityMaegeMormont(world));
		entities.put(GOTEntityOsha.class, new GOTEntityOsha(world));
		entities.put(GOTEntityRamsayBolton.class, new GOTEntityRamsayBolton(world));
		entities.put(GOTEntityRickardKarstark.class, new GOTEntityRickardKarstark(world));
		entities.put(GOTEntityRickonStark.class, new GOTEntityRickonStark(world));
		entities.put(GOTEntityRobbStark.class, new GOTEntityRobbStark(world));
		entities.put(GOTEntityRodrikCassel.class, new GOTEntityRodrikCassel(world));
		entities.put(GOTEntityRodrikRyswell.class, new GOTEntityRodrikRyswell(world));
		entities.put(GOTEntityRooseBolton.class, new GOTEntityRooseBolton(world));
		entities.put(GOTEntityWymanManderly.class, new GOTEntityWymanManderly(world));
		entities.put(GOTEntityAeronGreyjoy.class, new GOTEntityAeronGreyjoy(world));
		entities.put(GOTEntityAndrikTheUnsmilling.class, new GOTEntityAndrikTheUnsmilling(world));
		entities.put(GOTEntityBaelorBlacktyde.class, new GOTEntityBaelorBlacktyde(world));
		entities.put(GOTEntityBalonGreyjoy.class, new GOTEntityBalonGreyjoy(world));
		entities.put(GOTEntityDagmer.class, new GOTEntityDagmer(world));
		entities.put(GOTEntityDunstanDrumm.class, new GOTEntityDunstanDrumm(world));
		entities.put(GOTEntityErikIronmaker.class, new GOTEntityErikIronmaker(world));
		entities.put(GOTEntityEuronGreyjoy.class, new GOTEntityEuronGreyjoy(world));
		entities.put(GOTEntityGoroldGoodbrother.class, new GOTEntityGoroldGoodbrother(world));
		entities.put(GOTEntityGylbertFarwynd.class, new GOTEntityGylbertFarwynd(world));
		entities.put(GOTEntityHarrasHarlaw.class, new GOTEntityHarrasHarlaw(world));
		entities.put(GOTEntityMaronVolmark.class, new GOTEntityMaronVolmark(world));
		entities.put(GOTEntityRodrikHarlaw.class, new GOTEntityRodrikHarlaw(world));
		entities.put(GOTEntityVictarionGreyjoy.class, new GOTEntityVictarionGreyjoy(world));
		entities.put(GOTEntityYaraGreyjoy.class, new GOTEntityYaraGreyjoy(world));
		entities.put(GOTEntityAddamMarbrand.class, new GOTEntityAddamMarbrand(world));
		entities.put(GOTEntityAmoryLorch.class, new GOTEntityAmoryLorch(world));
		entities.put(GOTEntityDavenLannister.class, new GOTEntityDavenLannister(world));
		entities.put(GOTEntityForleyPrester.class, new GOTEntityForleyPrester(world));
		entities.put(GOTEntityHarysSwyft.class, new GOTEntityHarysSwyft(world));
		entities.put(GOTEntityJaimeLannister.class, new GOTEntityJaimeLannister(world));
		entities.put(GOTEntityKevanLannister.class, new GOTEntityKevanLannister(world));
		entities.put(GOTEntityLeoLefford.class, new GOTEntityLeoLefford(world));
		entities.put(GOTEntityLyleCrakehall.class, new GOTEntityLyleCrakehall(world));
		entities.put(GOTEntityPolliver.class, new GOTEntityPolliver(world));
		entities.put(GOTEntityQuentenBanefort.class, new GOTEntityQuentenBanefort(world));
		entities.put(GOTEntityQyburn.class, new GOTEntityQyburn(world));
		entities.put(GOTEntitySebastonFarman.class, new GOTEntitySebastonFarman(world));
		entities.put(GOTEntityTyrionLannister.class, new GOTEntityTyrionLannister(world));
		entities.put(GOTEntityTytosBrax.class, new GOTEntityTytosBrax(world));
		entities.put(GOTEntityTywinLannister.class, new GOTEntityTywinLannister(world));
		entities.put(GOTEntityBlackWalderFrey.class, new GOTEntityBlackWalderFrey(world));
		entities.put(GOTEntityBryndenTully.class, new GOTEntityBryndenTully(world));
		entities.put(GOTEntityClementPiper.class, new GOTEntityClementPiper(world));
		entities.put(GOTEntityEdmureTully.class, new GOTEntityEdmureTully(world));
		entities.put(GOTEntityHosterTully.class, new GOTEntityHosterTully(world));
		entities.put(GOTEntityHotPie.class, new GOTEntityHotPie(world));
		entities.put(GOTEntityJasonMallister.class, new GOTEntityJasonMallister(world));
		entities.put(GOTEntityJonosBracken.class, new GOTEntityJonosBracken(world));
		entities.put(GOTEntityLotharFrey.class, new GOTEntityLotharFrey(world));
		entities.put(GOTEntityThoros.class, new GOTEntityThoros(world));
		entities.put(GOTEntityTytosBlackwood.class, new GOTEntityTytosBlackwood(world));
		entities.put(GOTEntityWalderFrey.class, new GOTEntityWalderFrey(world));
		entities.put(GOTEntityWilliamMooton.class, new GOTEntityWilliamMooton(world));
		entities.put(GOTEntityAnyaWaynwood.class, new GOTEntityAnyaWaynwood(world));
		entities.put(GOTEntityBenedarBelmore.class, new GOTEntityBenedarBelmore(world));
		entities.put(GOTEntityGeroldGrafton.class, new GOTEntityGeroldGrafton(world));
		entities.put(GOTEntityGilwoodHunter.class, new GOTEntityGilwoodHunter(world));
		entities.put(GOTEntityHarroldHardyng.class, new GOTEntityHarroldHardyng(world));
		entities.put(GOTEntityHortonRedfort.class, new GOTEntityHortonRedfort(world));
		entities.put(GOTEntityLynCorbray.class, new GOTEntityLynCorbray(world));
		entities.put(GOTEntityLysaArryn.class, new GOTEntityLysaArryn(world));
		entities.put(GOTEntityRobinArryn.class, new GOTEntityRobinArryn(world));
		entities.put(GOTEntitySymondTempleton.class, new GOTEntitySymondTempleton(world));
		entities.put(GOTEntityYohnRoyce.class, new GOTEntityYohnRoyce(world));
		entities.put(GOTEntityArdrianCeltigar.class, new GOTEntityArdrianCeltigar(world));
		entities.put(GOTEntityAuraneWaters.class, new GOTEntityAuraneWaters(world));
		entities.put(GOTEntityDavosSeaworth.class, new GOTEntityDavosSeaworth(world));
		entities.put(GOTEntityMatthosSeaworth.class, new GOTEntityMatthosSeaworth(world));
		entities.put(GOTEntityMelisandra.class, new GOTEntityMelisandra(world));
		entities.put(GOTEntityMonfordVelaryon.class, new GOTEntityMonfordVelaryon(world));
		entities.put(GOTEntitySelyseBaratheon.class, new GOTEntitySelyseBaratheon(world));
		entities.put(GOTEntityShireenBaratheon.class, new GOTEntityShireenBaratheon(world));
		entities.put(GOTEntityStannisBaratheon.class, new GOTEntityStannisBaratheon(world));
		entities.put(GOTEntityBarristanSelmy.class, new GOTEntityBarristanSelmy(world));
		entities.put(GOTEntityCerseiLannister.class, new GOTEntityCerseiLannister(world));
		entities.put(GOTEntityEbrose.class, new GOTEntityEbrose(world));
		entities.put(GOTEntityGendryBaratheon.class, new GOTEntityGendryBaratheon(world));
		entities.put(GOTEntityHighSepton.class, new GOTEntityHighSepton(world));
		entities.put(GOTEntityIlynPayne.class, new GOTEntityIlynPayne(world));
		entities.put(GOTEntityJanosSlynt.class, new GOTEntityJanosSlynt(world));
		entities.put(GOTEntityJoffreyBaratheon.class, new GOTEntityJoffreyBaratheon(world));
		entities.put(GOTEntityMerynTrant.class, new GOTEntityMerynTrant(world));
		entities.put(GOTEntityMyrcellaBaratheon.class, new GOTEntityMyrcellaBaratheon(world));
		entities.put(GOTEntityPetyrBaelish.class, new GOTEntityPetyrBaelish(world));
		entities.put(GOTEntityPodrickPayne.class, new GOTEntityPodrickPayne(world));
		entities.put(GOTEntityPycelle.class, new GOTEntityPycelle(world));
		entities.put(GOTEntitySandorClegane.class, new GOTEntitySandorClegane(world));
		entities.put(GOTEntitySansaStark.class, new GOTEntitySansaStark(world));
		entities.put(GOTEntityShae.class, new GOTEntityShae(world));
		entities.put(GOTEntityTobhoMott.class, new GOTEntityTobhoMott(world));
		entities.put(GOTEntityTommenBaratheon.class, new GOTEntityTommenBaratheon(world));
		entities.put(GOTEntityVarys.class, new GOTEntityVarys(world));
		entities.put(GOTEntityBrienneTarth.class, new GOTEntityBrienneTarth(world));
		entities.put(GOTEntityEldonEstermont.class, new GOTEntityEldonEstermont(world));
		entities.put(GOTEntityGulianSwann.class, new GOTEntityGulianSwann(world));
		entities.put(GOTEntityRenlyBaratheon.class, new GOTEntityRenlyBaratheon(world));
		entities.put(GOTEntitySelwynTarth.class, new GOTEntitySelwynTarth(world));
		entities.put(GOTEntityGarlanTyrell.class, new GOTEntityGarlanTyrell(world));
		entities.put(GOTEntityLeytonHightower.class, new GOTEntityLeytonHightower(world));
		entities.put(GOTEntityLorasTyrell.class, new GOTEntityLorasTyrell(world));
		entities.put(GOTEntityMaceTyrell.class, new GOTEntityMaceTyrell(world));
		entities.put(GOTEntityMargaeryTyrell.class, new GOTEntityMargaeryTyrell(world));
		entities.put(GOTEntityMathisRowan.class, new GOTEntityMathisRowan(world));
		entities.put(GOTEntityMoribaldChester.class, new GOTEntityMoribaldChester(world));
		entities.put(GOTEntityOlennaTyrell.class, new GOTEntityOlennaTyrell(world));
		entities.put(GOTEntityOrtonMerryweather.class, new GOTEntityOrtonMerryweather(world));
		entities.put(GOTEntityPaxterRedwyne.class, new GOTEntityPaxterRedwyne(world));
		entities.put(GOTEntityQuennRoxton.class, new GOTEntityQuennRoxton(world));
		entities.put(GOTEntityRandyllTarly.class, new GOTEntityRandyllTarly(world));
		entities.put(GOTEntityWillasTyrell.class, new GOTEntityWillasTyrell(world));
		entities.put(GOTEntityAndersYronwood.class, new GOTEntityAndersYronwood(world));
		entities.put(GOTEntityAreoHotah.class, new GOTEntityAreoHotah(world));
		entities.put(GOTEntityArianneMartell.class, new GOTEntityArianneMartell(world));
		entities.put(GOTEntityBericDayne.class, new GOTEntityBericDayne(world));
		entities.put(GOTEntityDoranMartell.class, new GOTEntityDoranMartell(world));
		entities.put(GOTEntityEllaryaSand.class, new GOTEntityEllaryaSand(world));
		entities.put(GOTEntityFranklynFowler.class, new GOTEntityFranklynFowler(world));
		entities.put(GOTEntityGeroldDayne.class, new GOTEntityGeroldDayne(world));
		entities.put(GOTEntityHarmenUller.class, new GOTEntityHarmenUller(world));
		entities.put(GOTEntityManfreyMartell.class, new GOTEntityManfreyMartell(world));
		entities.put(GOTEntityOberynMartell.class, new GOTEntityOberynMartell(world));
		entities.put(GOTEntityQuentynMartell.class, new GOTEntityQuentynMartell(world));
		entities.put(GOTEntityQuentynQorgyle.class, new GOTEntityQuentynQorgyle(world));
		entities.put(GOTEntityTrystaneMartell.class, new GOTEntityTrystaneMartell(world));
		entities.put(GOTEntityTychoNestoris.class, new GOTEntityTychoNestoris(world));
		entities.put(GOTEntityIllyrioMopatis.class, new GOTEntityIllyrioMopatis(world));
		entities.put(GOTEntityMellario.class, new GOTEntityMellario(world));
		entities.put(GOTEntityJaqenHghar.class, new GOTEntityJaqenHghar(world));
		entities.put(GOTEntitySalladhorSaan.class, new GOTEntitySalladhorSaan(world));
		entities.put(GOTEntityJonConnington.class, new GOTEntityJonConnington(world));
		entities.put(GOTEntityYoungGriff.class, new GOTEntityYoungGriff(world));
		entities.put(GOTEntityHizdahrZoLoraq.class, new GOTEntityHizdahrZoLoraq(world));
		entities.put(GOTEntityKraznysMoNakloz.class, new GOTEntityKraznysMoNakloz(world));
		entities.put(GOTEntityMissandei.class, new GOTEntityMissandei(world));
		entities.put(GOTEntityRazdalMoEraz.class, new GOTEntityRazdalMoEraz(world));
		entities.put(GOTEntityXaroXhoanDaxos.class, new GOTEntityXaroXhoanDaxos(world));
		entities.put(GOTEntityDaenerysTargaryen.class, new GOTEntityDaenerysTargaryen(world));
		entities.put(GOTEntityJorahMormont.class, new GOTEntityJorahMormont(world));
		entities.put(GOTEntityTugarKhan.class, new GOTEntityTugarKhan(world));
		entities.put(GOTEntityBuGai.class, new GOTEntityBuGai(world));
		entities.put(GOTEntityAsshaiArchmag.class, new GOTEntityAsshaiArchmag(world));
		entities.put(GOTEntityMoqorro.class, new GOTEntityMoqorro(world));
		entities.put(GOTEntityBronn.class, new GOTEntityBronn(world));
		entities.put(GOTEntityDaarioNaharis.class, new GOTEntityDaarioNaharis(world));
		entities.put(GOTEntityGreyWorm.class, new GOTEntityGreyWorm(world));
		entities.put(GOTEntityHarryStrickland.class, new GOTEntityHarryStrickland(world));
		entities.put(GOTEntityThreeEyedRaven.class, new GOTEntityThreeEyedRaven(world));
		entities.put(GOTEntityVargoHoat.class, new GOTEntityVargoHoat(world));
		entities.put(BericDondarrionLife1.class, new BericDondarrionLife1(world));
		entities.put(GregorCleganeAlive.class, new GregorCleganeAlive(world));
		entities.put(JonSnowLife1.class, new JonSnowLife1(world));
		entities.put(LancelLannisterNormal.class, new LancelLannisterNormal(world));
		entities.put(TheonGreyjoyNormal.class, new TheonGreyjoyNormal(world));

		/* Table-pages: capes, shields, achievements, weapons, armor, food */
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(display + ".txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		if ("units".equals(display)) {
			for (GOTUnitTradeEntries entries : GOTCommander.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
				for (GOTUnitTradeEntry entry : entries.tradeEntries) {
					if (entry.mountClass == null) {
						writer.println("| [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]] || {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost * 2 + "}} || +" + entry.alignmentRequired + " || " + (entry.pledgeType == PledgeType.NONE));
					} else {
						writer.println("| [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]] (" + riderLoc + ") || {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost * 2 + "}} || +" + entry.alignmentRequired + " || " + (entry.pledgeType == PledgeType.NONE));
					}
					writer.println("|-");
				}
			}
		} else if ("capes".equals(display)) {
			for (GOTCapes cape : GOTCapes.values()) {
				writer.println("| " + cape.getCapeName() + " || " + cape.getCapeDesc() + " || [[File:Cape " + cape.name().toLowerCase() + ".png]]");
				writer.println("|-");
			}
		} else if ("shields".equals(display)) {
			for (GOTShields shield : GOTShields.values()) {
				writer.println("| " + shield.getShieldName() + " || " + shield.getShieldDesc() + " || [[File:Shield " + shield.name().toLowerCase() + ".png]]");
				writer.println("|-");
			}
		} else if ("shields".equals(display)) {
			for (GOTShields shield : GOTShields.values()) {
				writer.println("| " + shield.getShieldName() + " || " + shield.getShieldDesc() + " || [[File:Shield " + shield.name().toLowerCase() + ".png]]");
				writer.println("|-");
			}
		} else if ("waypoints".equals(display)) {
			for (GOTWaypoint wp : GOTWaypoint.values()) {
				writer.println("| " + wp.getDisplayName() + " || " + wp.getDescription());
				writer.println("|-");
			}
		} else if ("achievements".equals(display)) {
			for (GOTAchievement ach : GOTCommander.getObjectFieldsOfType(GOTAchievement.class, GOTAchievement.class)) {
				writer.println("| " + StatCollector.translateToLocal("got.achievement." + ach.name + ".title") + "||" + StatCollector.translateToLocal("got.achievement." + ach.name + ".desc"));
				writer.println("|-");
			}
		} else if ("food".equals(display)) {
			for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
				String genInfo = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name") + " || [[File:" + item.getUnlocalizedName().substring(9) + ".png|32px|link=]] ||";
				if (item instanceof ItemFood) {
					Field pf0 = null;
					try {
						pf0 = ItemFood.class.getDeclaredField("saturationModifier");
					} catch (NoSuchFieldException | SecurityException e) {
					}
					Field pf1 = null;
					try {
						pf1 = ItemFood.class.getDeclaredField("healAmount");
					} catch (NoSuchFieldException | SecurityException e) {
					}
					pf0.setAccessible(true);
					pf1.setAccessible(true);
					try {
						writer.println("| " + genInfo + "{{Bar|bread|" + new DecimalFormat("#.##").format((float) pf0.get(item) * (int) pf1.get(item) * 2) + "}} || {{Bar|food|" + (int) pf1.get(item) + "}} || " + item.getItemStackLimit());
					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
					writer.println("|-");
				}
			}
		} else if ("armor".equals(display)) {
			for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
				String genInfo = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name") + " || [[File:" + item.getUnlocalizedName().substring(9) + ".png|32px|link=]] ||";
				if (item instanceof ItemArmor) {
					writer.println("| " + genInfo + item.getMaxDamage() + " || " + ((ItemArmor) item).damageReduceAmount + " || " + StatCollector.translateToLocal(((ItemArmor) item).getArmorMaterial().customCraftingMaterial.getUnlocalizedName() + ".name"));
					writer.println("|-");
				}
			}
		} else if ("weapon".equals(display)) {
			for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
				String genInfo = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name") + " || [[File:" + item.getUnlocalizedName().substring(9) + ".png|32px|link=]] ||";
				if (item instanceof ItemSword) {
					Field pf1 = null;
					try {
						pf1 = ItemSword.class.getDeclaredField("field_150934_a");
					} catch (NoSuchFieldException | SecurityException e) {
					}
					Field pf2 = null;
					try {
						pf2 = ItemSword.class.getDeclaredField("field_150933_b");
					} catch (NoSuchFieldException | SecurityException e) {
					}
					pf1.setAccessible(true);
					pf2.setAccessible(true);
					try {
						writer.println("| " + genInfo + item.getMaxDamage() + " || " + (float) pf1.get(item) + " || " + StatCollector.translateToLocal(((ToolMaterial) pf2.get(item)).getRepairItemStack().getUnlocalizedName() + ".name"));
					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
					writer.println("|-");
				}
			}
		} else if ("xml".equals(display)) {
			List<GOTBiome> bmlist = GOTCommander.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class);
			bmlist.remove(GOTBiome.ocean1);
			bmlist.remove(GOTBiome.ocean2);
			bmlist.remove(GOTBiome.ocean3);
			bmlist.remove(GOTBiome.beach);
			bmlist.remove(GOTBiome.beachGravel);
			bmlist.remove(GOTBiome.beachWhite);
			bmlist.remove(GOTBiome.island);
			bmlist.remove(GOTBiome.lake);
			bmlist.remove(GOTBiome.yiTiWasteland);
			bmlist.remove(GOTBiome.river);
			bmlist.remove(GOTBiome.redBeach);
			bmlist.remove(GOTBiome.newGhis);

			List<GOTFaction> fclist = new ArrayList<>(EnumSet.allOf(GOTFaction.class));
			fclist.remove(GOTFaction.HOSTILE);
			fclist.remove(GOTFaction.UNALIGNED);

			writer.println("<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.11/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.11/ http://www.mediawiki.org/xml/export-0.11.xsd\" version=\"0.11\" xml:lang=\"ru\">");
			for (Class mob : entities.keySet()) {
				String s1 = "<page><title>";
				String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u041C\u043E\u0431}}</text></revision></page>";
				writer.print(s1 + GOTEntityRegistry.getEntityName(mob) + s2);
				writer.println();
			}
			for (GOTBiome biome : bmlist) {
				boolean two = false;
				String s1 = "<page><title>";
				String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0411\u0438\u043E\u043C}}</text></revision></page>";
				for (GOTFaction fac : fclist) {
					if (fac.factionName().equals(biome.getName())) {
						two = true;
						break;
					}
				}
				if (two) {
					writer.print(s1 + biome.getName() + " (" + biomeLoc + ")" + s2);
				} else {
					writer.print(s1 + biome.getName() + s2);
				}
				writer.println();
			}
			for (GOTFaction fac : fclist) {
				boolean two = false;
				String s1 = "<page><title>";
				String s2 = "</title><revision><text>{{\u0421\u0442\u0430\u0442\u044C\u044F \u0424\u0440\u0430\u043A\u0446\u0438\u044F}}</text></revision></page>";
				for (GOTBiome biome : bmlist) {
					if (fac.factionName().equals(biome.getName())) {
						two = true;
						break;
					}
				}
				if (fac.factionRegion != null) {
					if (two) {
						writer.print(s1 + fac.factionName() + " (" + factionLoc + ")" + s2);
					} else {
						writer.print(s1 + fac.factionName() + s2);
					}
					writer.println();
				}
			}

			String begin = "</title><ns>10</ns><revision><text>&lt;includeonly&gt;{{#switch: {{{1}}}";
			String end = "}}&lt;/includeonly&gt;&lt;noinclude&gt;[[" + categoryTemplates + "]]&lt;/noinclude&gt;</text></revision></page>";

			/* BIOMES */

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-NPC");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (biome.npcSpawnList.factionContainers.isEmpty()) {
					writer.println("| " + biome.getName() + " = " + biomeNoNPC);
				} else {
					writer.println("| " + biome.getName() + " = ");
					int i = 1;
					if (biome.npcSpawnList.factionContainers.size() > 1) {
						writer.println(biomeContainerMeaning);
					}
					for (FactionContainer cont : biome.npcSpawnList.factionContainers) {
						if (biome.npcSpawnList.factionContainers.size() > 1) {
							writer.println("");
							writer.println(biomeContainerLoc + " \u2116" + i + ":");
						}
						for (SpawnListContainer one : cont.spawnLists) {
							for (GOTSpawnEntry entry : one.spawnList.spawnList) {
								writer.println("* [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]]; ");
							}
						}
						++i;
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0411\u0430\u043D\u0434\u0438\u0442\u044B");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = " + biome.banditChance);
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0412\u0430\u0440\u0438\u0430\u043D\u0442\u044B");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (biome.biomeVariantsSmall.variantList.isEmpty()) {
					writer.println("| " + biome.getName() + " = " + biomeNoVariants);
				} else {
					writer.println("| " + biome.getName() + " = ");
					for (VariantBucket variant : biome.biomeVariantsSmall.variantList) {
						writer.println("* " + StatCollector.translateToLocal(variant.variant.getUnlocalizedName()) + ";");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0412\u0442\u043E\u0440\u0436\u0435\u043D\u0438\u044F");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (biome.invasionSpawns.registeredInvasions.isEmpty()) {
					writer.println("| " + biome.getName() + " = " + biomeNoInvasions);
				} else {
					writer.println("| " + biome.getName() + " = ");
					for (GOTInvasions inv : biome.invasionSpawns.registeredInvasions) {
						writer.println("* {{\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430|" + inv.invasionName() + "}};");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0412\u044B\u0441\u043E\u0442\u0430");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = " + biome.heightBaseParameter);
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0414\u0435\u0440\u0435\u0432\u044C\u044F");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (biome.decorator.treeTypes.isEmpty()) {
					writer.println("| " + biome.getName() + " = " + biomeNoTrees);
				} else {
					writer.println("| " + biome.getName() + " = ");
					for (WeightedTreeType tree : biome.decorator.treeTypes) {
						writer.println("* " + StatCollector.translateToLocal("got.tree." + tree.treeType.name().toLowerCase() + ".name") + ";");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0416\u0438\u0432\u043E\u0442\u043D\u044B\u0435");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				List entries = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
				entries.addAll(biome.spawnableGOTAmbientList);
				if (!entries.isEmpty()) {
					writer.println("| " + biome.getName() + " = ");
					for (Object entry : entries) {
						if (GOTEntityRegistry.classToNameMapping.containsKey(((SpawnListEntry) entry).entityClass)) {
							writer.println("* [[" + GOTEntityRegistry.getEntityName(((SpawnListEntry) entry).entityClass) + "]];");
						} else {
							writer.println("* " + StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(((SpawnListEntry) entry).entityClass) + ".name") + ";");
						}
					}
				} else {
					writer.println("| " + biome.getName() + " = " + biomeNoAnimals);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0418\u0441\u043A\u043E\u043F\u0430\u0435\u043C\u044B\u0435");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = ");
				for (OreGenerant ore : biome.decorator.biomeSoils) {
					getOreInfo(ore, writer);
				}
				for (OreGenerant ore : biome.decorator.biomeOres) {
					getOreInfo(ore, writer);
				}
				for (OreGenerant ore : biome.decorator.biomeGems) {
					getOreInfo(ore, writer);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u041A\u043B\u0438\u043C\u0430\u0442");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = " + biome.climat);
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u041A\u043E\u043B\u0435\u0431\u0430\u043D\u0438\u044F");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = " + biome.heightVariation);
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u041C\u0443\u0437\u043F\u0430\u043A");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				writer.println("| " + biome.getName() + " = " + biome.biomeName);
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0421\u0441\u044B\u043B\u043A\u0430");
			writer.println(begin);
			writer.println("| #default = [[{{{1}}}]]");
			for (GOTBiome biome : bmlist) {
				for (GOTFaction fac : fclist) {
					if (fac.factionName().equals(biome.getName())) {
						writer.println("| " + biome.getName() + " | " + biome.getName() + " (" + biomeLoc + ") = [[" + biome.getName() + " (" + biomeLoc + ")|" + biome.getName() + "]]");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0421\u0442\u0440\u0443\u043A\u0442\u0443\u0440\u044B");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (biome.decorator.randomStructures.isEmpty()) {
					writer.println("| " + biome.getName() + " = " + biomeNoStructures);
				} else {
					writer.println("| " + biome.getName() + " = ");
					for (RandomStructure structure : biome.decorator.randomStructures) {
						writer.println("* " + StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.getStructureNameFromClass(structure.structureGen.getClass()) + ".name") + ";");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0411\u0438\u043E\u043C-\u0424\u043E\u0442\u043E");
			writer.println(begin);
			for (GOTBiome biome : bmlist) {
				if (((biome != GOTBiome.thenn) && (biome != GOTBiome.mercenary))) {
					writer.println("| " + biome.getName() + " = " + biome.biomeName + ".png");
				} else {
					writer.println("| " + biome.getName() + " = " + biome.biomeName + " (biome).png");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0421\u0442\u0430\u0442\u044C\u044F-\u0411\u0438\u043E\u043C");
			writer.println(begin);
			writer.println("| #default = {{{1}}}");
			for (GOTBiome biome : bmlist) {
				for (GOTFaction fac : fclist) {
					if (fac.factionName().equals(biome.getName())) {
						writer.println("| " + biome.getName() + " (" + biomeLoc + ") = " + biome.getName());
					}
				}
			}
			writer.println(end);

			/* FACTIONS */

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-NPC");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				writer.println("| " + fac.factionName() + " =");
				for (Class mob : entities.keySet()) {
					if (entities.get(mob) instanceof GOTEntityNPC && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC() && ((GOTEntityNPC) entities.get(mob)).getFaction() == fac) {
						writer.println("* [[" + GOTEntityRegistry.getEntityName(mob) + "]];");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0412\u0440\u0430\u0433\u0438");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				boolean empty = true;
				for (GOTFaction fac2 : fclist) {
					if (fac2.isBadRelation(fac) && fac2 != fac && fac != GOTFaction.HOSTILE && fac2 != GOTFaction.HOSTILE) {
						empty = false;
						break;
					}
				}
				if (!empty) {
					writer.println("| " + fac.factionName() + " =");
					int i = 0;
					for (GOTFaction fac2 : fclist) {
						if (fac2.isBadRelation(fac) && fac2 != fac && fac != GOTFaction.HOSTILE && fac2 != GOTFaction.HOSTILE) {
							if (i == 0) {
								writer.print("{{\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430|" + fac2.factionName() + "}}");
								i++;
							} else {
								writer.print(" \u2022 {{\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430|" + fac2.factionName() + "}}");
							}
						}
					}
					writer.println();
				} else {
					writer.println("| " + fac.factionName() + " = " + factionNoEnemies);
				}

			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0414\u0440\u0443\u0437\u044C\u044F");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				boolean empty = true;
				for (GOTFaction fac2 : fclist) {
					if (fac2.isGoodRelation(fac) && fac2 != fac) {
						empty = false;
						break;
					}
				}
				if (!empty) {
					writer.println("| " + fac.factionName() + " =");
					int i = 0;
					for (GOTFaction fac2 : fclist) {
						if (fac2.isGoodRelation(fac) && fac2 != fac) {
							if (i == 0) {
								writer.print("{{\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430|" + fac2.factionName() + "}}");
								i++;
							} else {
								writer.print(" \u2022 {{\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430|" + fac2.factionName() + "}}");
							}
						}
					}
					writer.println();
				} else {
					writer.println("| " + fac.factionName() + " = " + factionNoFriends);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0416\u0435\u0441\u0442\u043E\u043A\u043E\u0441\u0442\u044C");
			writer.println(begin);
			writer.println("| #default = " + factionNotViolent);
			for (GOTFaction fac : fclist) {
				if (fac.approvesWarCrimes) {
					writer.println("| " + fac.factionName() + " = " + factionIsViolent);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u041A\u043E\u0434");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				writer.println("| " + fac.factionName() + " = " + fac.codeName());
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u041F\u0435\u0440\u0441\u043E\u043D\u0430\u0436\u0438");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				boolean empty = true;
				for (Class mob : entities.keySet()) {
					if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isLegendaryNPC() && ((GOTEntityNPC) entities.get(mob)).getFaction() == fac) {
						empty = false;
						break;
					}
				}
				if (!empty) {
					writer.println("| " + fac.factionName() + " =");
					for (Class mob : entities.keySet()) {
						if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isLegendaryNPC() && ((GOTEntityNPC) entities.get(mob)).getFaction() == fac) {
							writer.println("* [[" + GOTEntityRegistry.getEntityName(mob) + "]];");
						}
					}
				} else {
					writer.println("| " + fac.factionName() + " = " + factionNoCharacters);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0420\u0435\u0433\u0438\u043E\u043D");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				if (fac.factionRegion != null) {
					writer.println("| " + fac.factionName() + " = " + fac.factionRegion.getRegionName());
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0441\u044B\u043B\u043A\u0430");
			writer.println(begin);
			writer.println("| #default = [[{{{1}}}]]");
			for (GOTFaction fac : fclist) {
				for (GOTBiome biome : bmlist) {
					if (fac.factionName().equals(biome.getName())) {
						writer.println("| " + fac.factionName() + " | " + fac.factionName() + " (" + factionLoc + ") = [[" + fac.factionName() + " (" + factionLoc + ")|" + fac.factionName() + "]]");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0424\u0440\u0430\u043A\u0446\u0438\u044F-\u0421\u0442\u0440\u0443\u043A\u0442\u0443\u0440\u044B");
			writer.println(begin);
			for (GOTFaction fac : fclist) {
				boolean empty = true;
				for (Class<? extends WorldGenerator> mob : GOTStructureRegistry.classToFactionMapping.keySet()) {
					if (GOTStructureRegistry.classToFactionMapping.get(mob) == fac) {
						empty = false;
						break;
					}
				}
				if (!empty) {
					writer.println("| " + fac.factionName() + " =");
					for (Class<? extends WorldGenerator> mob : GOTStructureRegistry.classToFactionMapping.keySet()) {
						if (GOTStructureRegistry.classToFactionMapping.get(mob) == fac) {
							writer.println("* " + GOTStructureRegistry.getStructureName(mob) + ";");
						}
					}
				} else {
					writer.println("| " + fac.factionName() + " = " + factionNoStructures);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u0421\u0442\u0430\u0442\u044C\u044F-\u0424\u0440\u0430\u043A\u0446\u0438\u044F");
			writer.println(begin);
			writer.println("| #default = {{{1}}}");
			for (GOTFaction fac : fclist) {
				for (GOTBiome biome : bmlist) {
					if (fac.factionName().equals(biome.getName())) {
						writer.println("| " + fac.factionName() + " (" + factionLoc + ") = " + fac.factionName());
					}
				}
			}
			writer.println(end);

			/* MOBS */

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-NPC");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0410\u0433\u0440\u0435\u0441\u0441\u043E\u0440");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isTargetSeeker || entities.get(mob) instanceof EntityMob || entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).getFaction() == GOTFaction.HOSTILE) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0411\u0438\u043E\u043C");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				int i = 1;
				for (GOTBiome biome : bmlist) {
					List sus = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.creature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.monster));
					sus.addAll(biome.spawnableGOTAmbientList);
					for (FactionContainer cont : biome.npcSpawnList.factionContainers) {
						for (SpawnListContainer one : cont.spawnLists) {
							sus.addAll(one.spawnList.spawnList);
						}
					}
					for (Object var : sus) {
						if (((SpawnListEntry) var).entityClass.equals(mob)) {
							if (i == 1) {
								writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = ");
							}
							i++;
							writer.println("* {{\u0411\u0414 \u0411\u0438\u043E\u043C-\u0421\u0441\u044B\u043B\u043A\u0430|" + biome.getName() + "}};");
						}
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0412\u043B\u0430\u0434\u0435\u043B\u0435\u0446");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTUnitTradeable && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
					GOTUnitTradeEntries entries = ((GOTUnitTradeable) entities.get(mob)).getUnits();
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						if (entry.mountClass == null) {
							writer.println("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = [[" + GOTEntityRegistry.getEntityName(mob) + "]]");
						}
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0415\u0437\u0434\u043E\u0432\u043E\u0439");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPCRideable) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0417\u0434\u043E\u0440\u043E\u0432\u044C\u0435");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = " + ((EntityLivingBase) entities.get(mob)).getMaxHealth());
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0417\u043D\u0430\u043C\u0435\u043D\u043E\u0441\u0435\u0446");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTBannerBearer) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0418\u043C\u044F");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = " + ((GOTEntityNPC) entities.get(mob)).getNPCName());
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041A\u043E\u043C\u0430\u043D\u0434\u0438\u0440");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTUnitTradeable) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041A\u0443\u0437\u043D\u0435\u0446");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTTradeable.Smith) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041B\u0435\u0433\u0435\u043D\u0434\u0430\u0440\u043D\u044B\u0439");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isLegendaryNPC) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041C\u0430\u0443\u043D\u0442");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTNPCMount) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041C\u043E\u0440\u043E\u0437\u043E\u0443\u0441\u0442\u043E\u0439\u0447\u0438\u0432\u043E\u0441\u0442\u044C");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isImmuneToFrost || entities.get(mob) instanceof GOTBiome.ImmuneToFrost) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041D\u0430\u0451\u043C\u043D\u0438\u043A");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTMercenary) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041D\u043E\u0447\u043D\u043E\u0439 \u0441\u043F\u0430\u0432\u043D");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).spawnsInDarkness) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041E\u0433\u043D\u0435\u0443\u043F\u043E\u0440\u043D\u043E\u0441\u0442\u044C");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob).isImmuneToFire()) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041F\u043E\u043A\u0443\u043F\u0430\u0435\u0442");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTTradeable) {
					GOTTradeEntries entries = ((GOTTradeable) entities.get(mob)).getSellPool();
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = ");
					for (GOTTradeEntry entry : entries.tradeEntries) {
						writer.println("* " + entry.tradeItem.getDisplayName() + ": {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.getCost() + "}};");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u041F\u0440\u043E\u0434\u0430\u0451\u0442");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTTradeable) {
					GOTTradeEntries entries = ((GOTTradeable) entities.get(mob)).getBuyPool();
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = ");
					for (GOTTradeEntry entry : entries.tradeEntries) {
						writer.println("* " + entry.tradeItem.getDisplayName() + ": {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.getCost() + "}};");
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0420\u0430\u0431\u043E\u0442\u043D\u0438\u043A");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTFarmhand) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0420\u0435\u043F\u0443\u0442\u0430\u0446\u0438\u044F");
			writer.println(begin);
			for (GOTUnitTradeEntries entries : GOTCommander.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
				for (GOTUnitTradeEntry entry : entries.tradeEntries) {
					writer.println("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = +" + entry.alignmentRequired);
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0421\u0432\u0430\u0434\u044C\u0431\u0430");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).canBeMarried) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0422\u043E\u0440\u0433\u043E\u0432\u0435\u0446");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTTradeable) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0423\u0441\u0442\u043E\u0439\u0447\u0438\u0432\u044B\u0439 \u043A \u0436\u0430\u0440\u0435");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTBiome.ImmuneToHeat) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = True");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0424\u043E\u0442\u043E");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = " + mob.getSimpleName().replace("GOTEntity", "") + ".png");
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0424\u0440\u0430\u043A\u0446\u0438\u044F");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = " + ((GOTEntityNPC) entities.get(mob)).getFaction().factionName());
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u0430");
			writer.println(begin);
			for (GOTUnitTradeEntries entries : GOTCommander.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
				for (GOTUnitTradeEntry entry : entries.tradeEntries) {
					writer.println("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost * 2 + "}}");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u0430\u041F\u0440\u0438\u0441\u044F\u0433\u0430");
			writer.println(begin);
			for (GOTUnitTradeEntries entries : GOTCommander.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
				for (GOTUnitTradeEntry entry : entries.tradeEntries) {
					writer.println("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost + "}}");
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0426\u0435\u043D\u043D\u043E\u0441\u0442\u044C");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTEntityNPC) {
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = " + ((GOTEntityNPC) entities.get(mob)).getAlignmentBonus());
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u042E\u043D\u0438\u0442\u044B");
			writer.println(begin);
			for (Class mob : entities.keySet()) {
				if (entities.get(mob) instanceof GOTUnitTradeable) {
					GOTUnitTradeEntries entries = ((GOTUnitTradeable) entities.get(mob)).getUnits();
					writer.println("| " + GOTEntityRegistry.getEntityName(mob) + " = ");
					for (GOTUnitTradeEntry entry : entries.tradeEntries) {
						if (entry.mountClass == null) {
							writer.println("* [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]]: {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost * 2 + "}} " + noPledge + ", {{\u0414\u0435\u043D\u044C\u0433\u0438|" + entry.initialCost + "}} " + yesPledge + "; " + entry.alignmentRequired + "+ " + rep + ";");
						}
					}
				}
			}
			writer.println(end);

			writer.print("<page><title>\u0428\u0430\u0431\u043B\u043E\u043D:\u0411\u0414 \u041C\u043E\u0431-\u0422\u043E\u0447\u043A\u0430");
			writer.println(begin);
			for (Class<? extends Entity> entity : charPoint.keySet()) {
				writer.println("| " + GOTEntityRegistry.getEntityName(entity) + " = " + charPoint.get(entity).getDisplayName());
			}
			for (GOTWaypoint wp : GOTFixer.structures.keySet()) {
				GOTStructureBase str = GOTFixer.structures.get(wp);
				str.notGen = true;
				str.generate(world, random, y, j, k);
				for (EntityCreature entity : GOTFixer.structures.get(wp).legendaryChar) {
					writer.println("| " + GOTEntityRegistry.getEntityName(entity.getClass()) + " = " + wp.getDisplayName());
				}
			}
			writer.println(end);
			writer.println("</mediawiki>");
		}
		writer.close();
		return true;
	}

	public void getOreInfo(OreGenerant ore, PrintWriter writer) {
		WorldGenMinable gen = ore.oreGen;
		Field pf1 = null;
		try {
			pf1 = WorldGenMinable.class.getDeclaredField("field_150519_a");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		pf1.setAccessible(true);
		Field pf2 = null;
		try {
			pf2 = WorldGenMinable.class.getDeclaredField("mineableBlockMeta");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		pf2.setAccessible(true);
		try {
			if ((Block) pf1.get(gen) instanceof GOTBlockOreGem || (Block) pf1.get(gen) instanceof BlockDirt || (Block) pf1.get(gen) instanceof GOTBlockRock) {
				writer.println("* " + StatCollector.translateToLocal(((Block) pf1.get(gen)).getUnlocalizedName() + "." + (int) pf2.get(gen) + ".name") + " (" + ore.oreChance + "%; Y: " + ore.minHeight + "-" + ore.maxHeight + ");");
			} else {
				writer.println("* " + StatCollector.translateToLocal(((Block) pf1.get(gen)).getUnlocalizedName() + ".name") + " (" + ore.oreChance + "%; Y: " + ore.minHeight + "-" + ore.maxHeight + ");");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public enum Database {
		XML("xml"), ACHIEVEMENTS("achievements"), ARMOR("armor"), CAPES("capes"), FOOD("food"), SHIELDS("shields"), UNITS("units"), WAYPOINTS("waypoints"), WEAPON("weapon");

		String codeName;

		Database(String string) {
			codeName = string;
		}

		public String getName() {
			return codeName;
		}

		public boolean matchesNameOrAlias(String name) {
			return codeName.equals(name);
		}

		public static Database forName(String name) {
			for (Database f : Database.values()) {
				if (f.matchesNameOrAlias(name)) {
					return f;
				}
			}
			return null;
		}

		public static List<String> getNames() {
			ArrayList<String> names = new ArrayList<>();
			for (Database f : Database.values()) {
				names.add(f.getName());
			}
			return names;
		}
	}
}