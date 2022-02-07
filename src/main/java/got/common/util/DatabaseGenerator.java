package got.common.util;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

import got.common.database.*;
import got.common.entity.animal.GOTEntityUlthosSpider;
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
import got.common.entity.westeros.legendary.reborn.*;
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
import got.common.world.biome.GOTBiomeDecorator.RandomStructure;
import got.common.world.biome.variant.GOTBiomeVariantList.VariantBucket;
import got.common.world.feature.GOTTreeType.WeightedTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList.*;
import got.common.world.spawning.GOTSpawnEntry;
import got.common.world.structure.other.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class DatabaseGenerator extends GOTStructureBase {
	private static Map<Class<? extends Entity>, Entity> entities = new HashMap<>();

	public DatabaseGenerator(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		entities.put(GOTEntityBandit.class, new GOTEntityBandit(world));
		entities.put(GOTEntityThief.class, new GOTEntityThief(world));
		entities.put(GOTEntityScrapTrader.class, new GOTEntityScrapTrader(world));
		entities.put(GOTEntityBanditEssos.class, new GOTEntityBanditEssos(world));
		entities.put(GOTEntityThiefEssos.class, new GOTEntityThiefEssos(world));
		entities.put(GOTEntityScrapTraderEssos.class, new GOTEntityScrapTraderEssos(world));
		entities.put(GOTEntityProstitute.class, new GOTEntityProstitute(world));
		entities.put(GOTEntityMaester.class, new GOTEntityMaester(world));
		entities.put(GOTEntitySepton.class, new GOTEntitySepton(world));
		entities.put(GOTEntityRedPriest.class, new GOTEntityRedPriest(world));
		entities.put(GOTEntityIronbornPriest.class, new GOTEntityIronbornPriest(world));
		entities.put(GOTEntityBarrowWight.class, new GOTEntityBarrowWight(world));
		entities.put(GOTEntityStoneman.class, new GOTEntityStoneman(world));
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
		entities.put(GOTEntityJonSnow.Stage1.class, new GOTEntityJonSnow.Stage1(world));
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
		entities.put(GOTEntityTheonGreyjoy.Normal.class, new GOTEntityTheonGreyjoy.Normal(world));
		entities.put(GOTEntityVictarionGreyjoy.class, new GOTEntityVictarionGreyjoy(world));
		entities.put(GOTEntityYaraGreyjoy.class, new GOTEntityYaraGreyjoy(world));
		entities.put(GOTEntityAddamMarbrand.class, new GOTEntityAddamMarbrand(world));
		entities.put(GOTEntityAmoryLorch.class, new GOTEntityAmoryLorch(world));
		entities.put(GOTEntityDavenLannister.class, new GOTEntityDavenLannister(world));
		entities.put(GOTEntityForleyPrester.class, new GOTEntityForleyPrester(world));
		entities.put(GOTEntityGregorClegane.Alive.class, new GOTEntityGregorClegane.Alive(world));
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
		entities.put(GOTEntityBericDondarrion.LifeStage1.class, new GOTEntityBericDondarrion.LifeStage1(world));
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
		entities.put(GOTEntityLancelLannister.Normal.class, new GOTEntityLancelLannister.Normal(world));
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
		String display = "null";
		for (Class mob : entities.keySet()) {
			if ("typeTradeable".equals(display) && entities.get(mob) instanceof GOTTradeable && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeUnitTradeable".equals(display) && entities.get(mob) instanceof GOTUnitTradeable && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeSmith".equals(display) && entities.get(mob) instanceof GOTTradeable.Smith && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeMercenary".equals(display) && entities.get(mob) instanceof GOTMercenary && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeFarmhand".equals(display) && entities.get(mob) instanceof GOTFarmhand && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeFarmer".equals(display) && entities.get(mob) instanceof GOTTradeable && entities.get(mob) instanceof GOTUnitTradeable && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("typeAgressive".equals(display) && entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isTargetSeeker && !(entities.get(mob) instanceof GOTTradeable) && !(entities.get(mob) instanceof GOTFarmhand) && !(entities.get(mob) instanceof GOTUnitTradeable) && !(entities.get(mob) instanceof GOTTradeable.Smith) && !(entities.get(mob) instanceof GOTMercenary)) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(mob));
				continue;
			}
			if ("unitsCommanders".equals(display) && entities.get(mob) instanceof GOTUnitTradeable && !((GOTEntityNPC) entities.get(mob)).isLegendaryNPC()) {
				GOTUnitTradeEntries entries = ((GOTUnitTradeable) entities.get(mob)).getUnits();
				for (GOTUnitTradeEntry entry : entries.tradeEntries) {
					if (entry.mountClass == null) {
						GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = [[" + GOTEntityRegistry.getEntityName(mob) + "]]");
					}
				}
			}
		}
		if ("factionUnits".equals(display)) {
			for (GOTFaction fac : GOTFaction.values()) {
				GOTLog.getLogger().info("| " + fac.factionName() + " =");
				for (Class mob : entities.keySet()) {
					if (entities.get(mob) instanceof GOTEntityNPC && ((GOTEntityNPC) entities.get(mob)).isLegendaryNPC() && ((GOTEntityNPC) entities.get(mob)).getFaction() == fac) {
						GOTLog.getLogger().info("* [[" + GOTEntityRegistry.getEntityName(mob) + "]];");
					}
				}
			}
		}
		if ("waypointBiome".equals(display)) {
			for (GOTWaypoint wp : GOTWaypoint.values()) {
				GOTLog.getLogger().info("| " + wp.getDisplayName() + " = {{ÁÄ Áèîì-Ññûëêà|" + ((GOTBiome) world.getBiomeGenForCoords(wp.xCoord, wp.zCoord)).getBiomeDisplayName() + "}}");
			}
		}
		return true;
	}

	public static void generateWikiaDatabases() throws NoSuchFieldException, IllegalAccessException {
		String display = "blocks";
		if ("chest".equals(display)) {
			for (GOTChestContents content : GOTCommander.getObjectFieldsOfType(GOTChestContents.class, GOTChestContents.class)) {
				GOTLog.getLogger().info(content + " = ");
				for (WeightedRandomChestContent con : content.items) {
					GOTLog.getLogger().info(con.theItemId.getDisplayName() + ", ");
				}
			}
		}
		if ("factionStructures".equals(display)) {
			for (GOTFaction fac : GOTFaction.values()) {
				GOTLog.getLogger().info("| " + fac.factionName() + " =");
				for (Class<? extends WorldGenerator> mob : GOTStructureRegistry.classToFactionMapping.keySet()) {
					if (GOTStructureRegistry.classToFactionMapping.get(mob) == fac) {
						GOTLog.getLogger().info("* [[" + GOTStructureRegistry.getStructureName(mob) + "]];");
					}
				}
			}
		}
		if ("capes".equals(display)) {
			for (GOTCapes cape : GOTCapes.values()) {
				GOTLog.getLogger().info("| " + cape.getCapeName() + " || " + cape.getCapeDesc() + " || [[File:Cape " + cape.name().toLowerCase() + ".png]]");
				GOTLog.getLogger().info("|-");
			}
		}
		if ("shields".equals(display)) {
			for (GOTShields shield : GOTShields.values()) {
				GOTLog.getLogger().info("| " + shield.getShieldName() + " || " + shield.getShieldDesc() + " || [[File:Shield " + shield.name().toLowerCase() + ".png]]");
				GOTLog.getLogger().info("|-");
			}
		}
		for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
			String genInfo = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name") + " || [[File:" + item.getUnlocalizedName().substring(9) + ".png|32px|link=]] ||";
			if ("foodTable".equals(display) && item instanceof ItemFood) {
				Field pf0 = ItemFood.class.getDeclaredField("saturationModifier");
				Field pf1 = ItemFood.class.getDeclaredField("healAmount");
				pf0.setAccessible(true);
				pf1.setAccessible(true);
				GOTLog.getLogger().info("| " + genInfo + "{{Bar|bread|" + new DecimalFormat("#.##").format((float) pf0.get(item) * (int) pf1.get(item) * 2) + "}} || {{Bar|food|" + (int) pf1.get(item) + "}} || " + item.getItemStackLimit());
				GOTLog.getLogger().info("|-");
				continue;
			}
			if ("armorTable".equals(display) && item instanceof ItemArmor) {
				Field pf0 = ItemArmor.class.getDeclaredField("maxDamage");
				pf0.setAccessible(true);
				GOTLog.getLogger().info("| " + genInfo + (int) pf0.get(item) + " || " + ((ItemArmor) item).damageReduceAmount + " || " + StatCollector.translateToLocal(((ItemArmor) item).getArmorMaterial().customCraftingMaterial.getUnlocalizedName() + ".name"));
				GOTLog.getLogger().info("|-");
				continue;
			}
			if ("weaponTable".equals(display) && item instanceof ItemSword) {
				Field pf0 = ItemSword.class.getDeclaredField("maxDamage");
				Field pf1 = ItemSword.class.getDeclaredField("field_150934_a");
				Field pf2 = ItemSword.class.getDeclaredField("field_150933_b");
				pf0.setAccessible(true);
				pf1.setAccessible(true);
				pf2.setAccessible(true);
				GOTLog.getLogger().info("| " + genInfo + (int) pf0.get(item) + " || " + (float) pf1.get(item) + " || " + StatCollector.translateToLocal(((ToolMaterial) pf2.get(item)).getRepairItemStack().getUnlocalizedName() + ".name"));
				GOTLog.getLogger().info("|-");
			}
		}

		for (GOTBiome biome : GOTCommander.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class)) {
			if ("biomeType".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = " + biome.type);
				continue;
			}
			if ("biomeName".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = " + biome.biomeName);
				continue;
			}
			if ("biomeVariation".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = " + biome.heightVariation);
				continue;
			}
			if ("biomeHeight".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = " + biome.heightBaseParameter);
				continue;
			}
			if ("biomeBandits".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = " + biome.banditChance);
				continue;
			}
			if ("biomeVariants".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				for (VariantBucket variant : biome.biomeVariantsSmall.variantList) {
					GOTLog.getLogger().info("* " + StatCollector.translateToLocal(variant.variant.getUnlocalizedName()));
				}
				continue;
			}
			if ("biomeInvasions".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				for (GOTInvasions inv : biome.invasionSpawns.registeredInvasions) {
					GOTLog.getLogger().info("* " + inv.invasionName());
				}
				continue;
			}
			if ("biomeStructures".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				for (RandomStructure structure : biome.decorator.randomStructures) {
					GOTLog.getLogger().info("* " + StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.getStructureNameFromClass(structure.structureGen.getClass()) + ".name") + ";");
				}
				continue;
			}
			if ("biomeTrees".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				for (WeightedTreeType tree : biome.decorator.treeTypes) {
					GOTLog.getLogger().info("* " + StatCollector.translateToLocal("got.tree." + tree.treeType.name().toLowerCase() + ".name") + ";");
				}
				continue;
			}
			if ("biomeEntities".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				List entries = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.creature));
				entries.addAll(biome.getSpawnableList(EnumCreatureType.monster));
				entries.addAll(biome.spawnableGOTAmbientList);
				for (Object entry : entries) {
					if (GOTEntityRegistry.classToNameMapping.containsKey(((SpawnListEntry) entry).entityClass)) {
						GOTLog.getLogger().info("* [[" + GOTEntityRegistry.getEntityName(((SpawnListEntry) entry).entityClass) + "]];");
					} else {
						GOTLog.getLogger().info("* " + StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(((SpawnListEntry) entry).entityClass) + ".name") + ";");
					}
				}
				continue;
			}
			if ("biomeConquestNPC".equals(display)) {
				GOTLog.getLogger().info("| " + biome.getName() + " = ");
				int i = 1;
				if (biome.npcSpawnList.factionContainers.size() > 1) {
					GOTLog.getLogger().info("Placeholder about containers");
				}
				for (FactionContainer cont : biome.npcSpawnList.factionContainers) {
					if (biome.npcSpawnList.factionContainers.size() > 1) {
						GOTLog.getLogger().info("Container ¹" + i + ":");
					}
					for (SpawnListContainer one : cont.spawnLists) {
						for (GOTSpawnEntry entry : one.spawnList.spawnList) {
							GOTLog.getLogger().info("* [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]]; ");
						}
					}
					++i;
				}
			}
		}

		for (GOTUnitTradeEntries entries : GOTCommander.getObjectFieldsOfType(GOTUnitTradeEntries.class, GOTUnitTradeEntries.class)) {
			for (GOTUnitTradeEntry entry : entries.tradeEntries) {
				if ("unitsPrice".equals(display)) {
					GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = {{Bar|coins|" + entry.initialCost * 2 + "|size=22}}");
					continue;
				}
				if ("unitsPricePledge".equals(display)) {
					GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = {{Bar|coins|" + entry.initialCost + "|size=22}}");
					continue;
				}
				if ("unitsRep".equals(display)) {
					GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = +" + entry.alignmentRequired);
					continue;
				}
				if ("unitsPledge".equals(display)) {
					GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entry.entityClass) + " = " + (entry.pledgeType == PledgeType.NONE));
					continue;
				}
				if ("unitsTable".equals(display)) {
					if (entry.mountClass == null) {
						GOTLog.getLogger().info("| [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]] || {{Bar|coins|" + entry.initialCost * 2 + "|size=22}} || +" + entry.alignmentRequired + " || " + (entry.pledgeType == PledgeType.NONE));
					} else {
						GOTLog.getLogger().info("| [[" + GOTEntityRegistry.getEntityName(entry.entityClass) + "]] (rider) || {{Bar|coins|" + entry.initialCost * 2 + "|size=22}} || +" + entry.alignmentRequired + " || " + (entry.pledgeType == PledgeType.NONE));
					}
					GOTLog.getLogger().info("|-");
				}
			}
		}

		if ("entitiesBiomes".equals(display)) {
			for (Object entityClass1 : EntityList.classToStringMapping.keySet()) {
				GOTLog.getLogger().info("| " + StatCollector.translateToLocal("entity." + EntityList.classToStringMapping.get(entityClass1) + ".name") + " = ");
				for (GOTBiome biome : GOTCommander.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class)) {
					List sus = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.creature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.monster));
					sus.addAll(biome.spawnableGOTAmbientList);
					for (Object var : sus) {
						if (((SpawnListEntry) var).entityClass.equals(entityClass1)) {
							GOTLog.getLogger().info("* " + biome.getName() + ";");
						}
					}
				}
			}
			for (Class entityClass2 : GOTEntityRegistry.classToIDMapping.keySet()) {
				GOTLog.getLogger().info("| " + GOTEntityRegistry.getEntityName(entityClass2) + " = ");
				for (GOTBiome biome : GOTCommander.getObjectFieldsOfType(GOTBiome.class, GOTBiome.class)) {
					List sus = new ArrayList(biome.getSpawnableList(EnumCreatureType.ambient));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.waterCreature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.creature));
					sus.addAll(biome.getSpawnableList(EnumCreatureType.monster));
					sus.addAll(biome.spawnableGOTAmbientList);
					for (Object var : sus) {
						if (((SpawnListEntry) var).entityClass.equals(entityClass2)) {
							GOTLog.getLogger().info("* " + biome.getName() + ";");
						}
					}
				}
			}
		}
	}
}