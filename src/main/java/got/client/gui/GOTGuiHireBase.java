package got.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import got.GOT;
import got.client.GOTClientProxy;
import got.common.*;
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
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.*;
import got.common.faction.*;
import got.common.inventory.*;
import got.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class GOTGuiHireBase extends GuiContainer {
	public static ResourceLocation guiTexture;
	public static GOTUnitTradeEntries ARRYN = new GOTUnitTradeEntries(50.0f, new GOTUnitTradeEntry(GOTEntityBandit.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThief.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityScrapTrader.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBanditEssos.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThiefEssos.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityScrapTraderEssos.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWhore.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMaester.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySepton.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRedPriest.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornPriest.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBarrowWight.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStoneman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMercenary.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWhiteWalker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWight.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIceSpider.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWightGiant.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWildling.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWildlingArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWildlingAxeThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWildlingChieftain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGiant.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThenn.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThennArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThennAxeThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThennBerserker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThennMagnar.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityThennBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrasterWife.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGiftMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGiftBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGiftGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanCannibal.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanMercenary.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanAxeThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorthHillmanChieftain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIronbornMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityWesterlandsMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityRiverlandsMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanAxeThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanBerserker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanWarlord.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityHillmanBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityArrynMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneFishmonger.class, 0, 0f),
				new GOTUnitTradeEntry(GOTEntityDragonstoneFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDragonstoneMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityKingsguard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityCrownlandsAlchemist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityStormlandsMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityReachMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneGreengrocer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDorneMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityBraavosMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisSlave.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisSlaver.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityVolantisMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosGuardCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityPentosMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosGuardCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityNorvosMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathBartender.class, 0, 0f),
				new GOTUnitTradeEntry(GOTEntityLorathBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLorathMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorUnsullied.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorGuardCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQohorMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysSlave.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysSlaver.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLysMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrSlave.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrSlaver.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMyrMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGoldenWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGoldenSpearman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGoldenCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshSoldierArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshGeneral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshSlave.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshSlaver.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityTyroshMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarUnsullied.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarCorsair.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarCorsairArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarGladiator.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarHarpy.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarSlave.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarSlaver.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarAdmiral.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityGhiscarMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthLevymanArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthGuard.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthWarlock.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthGuardCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthFlorist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityQarthMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarWarlord.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarHunter.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityLhazarFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDothraki.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDothrakiArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDothrakiKhal.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityDothrakiKhalin.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenAxeThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenWarlord.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenMeadhost.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenBuilder.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenOrcharder.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIbbenStablemaster.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityIfekevron.class, 0, 0f),
				new GOTUnitTradeEntry(GOTEntityJogos.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityJogosArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityJogosChief.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityJogosShaman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMossovyMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMossovyWitcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMossovyBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMossovyFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityMossovyBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiLevyman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiLevymanCrossbower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiSoldier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiSoldierCrossbower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFrontier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFrontierCrossbower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiSamurai.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiBombardier.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFireThrower.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiShogune.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiHunter.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiGoldsmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityYiTiBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiShadowbinder.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiSpherebinder.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiAlchemist.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityAsshaiCaptain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosChieftain.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosBlowgunner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosShaman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySothoryosSmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerMan.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerWarrior.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerArcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerWarlord.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerBlacksmith.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerBartender.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerLumberman.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerMason.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerButcher.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerBrewer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerFishmonger.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerBaker.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerHunter.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerMiner.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerFarmhand.class, 0, 0f), new GOTUnitTradeEntry(GOTEntitySummerFarmer.class, 0, 0f), new GOTUnitTradeEntry(GOTEntityUlthosSpider.class, 0, 0f));
	public GOTHireableBase theUnitTrader;
	public GOTFaction traderFaction;
	public GOTUnitTradeEntries trades;
	public int currentTradeEntryIndex;
	public GOTEntityNPC currentDisplayedMob;
	public EntityLiving currentDisplayedMount;
	public float screenXSize;
	public float screenYSize;
	public GOTGuiUnitTradeButton buttonHire;
	public GOTGuiUnitTradeButton buttonLeftUnit;
	public GOTGuiUnitTradeButton buttonRightUnit;

	public GuiTextField squadronNameField;

	public GOTGuiHireBase(EntityPlayer entityplayer, GOTHireableBase trader, World world) {
		super(new GOTContainerUnitTrade(entityplayer, trader, world));
		xSize = 220;
		ySize = 256;
		theUnitTrader = trader;
		traderFaction = trader.getFaction();
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonLeftUnit) {
				if (currentTradeEntryIndex > 0) {
					--currentTradeEntryIndex;
				}
			} else if (button == buttonHire) {
				String squadron = squadronNameField.getText();
				GOTPacketBuyUnit packet = new GOTPacketBuyUnit(currentTradeEntryIndex, squadron);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonRightUnit && currentTradeEntryIndex < trades.tradeEntries.length - 1) {
				++currentTradeEntryIndex;
			}
		}
	}

	public GOTUnitTradeEntry currentTrade() {
		return trades.tradeEntries[currentTradeEntryIndex];
	}

	public void drawCenteredString(String s, int i, int j, int k) {
		fontRendererObj.drawString(s, i - fontRendererObj.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		boolean squadronPrompt;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (!GOT.isDevMode) {
			guiTexture = new ResourceLocation("got:textures/gui/npc/unit_trade.png");
		} else {
			guiTexture = new ResourceLocation("got:textures/gui/npc/shower.png");
		}
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (GOT.isDevMode) {
			drawMobOnGuiDev(guiLeft + 110, guiTop + 129, guiLeft + 32 - screenXSize, guiTop + 109 - 50 - screenYSize);
		} else {
			if (((GOTContainerUnitTrade) inventorySlots).alignmentRewardSlots > 0) {
				Slot slot = inventorySlots.getSlot(0);
				drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 3, guiTop + slot.yDisplayPosition - 3, xSize, 16, 22, 22);
				if (!slot.getHasStack() && GOTLevelData.getData(mc.thePlayer).getAlignment(traderFaction) < 1500.0f) {
					drawTexturedModalRect(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, xSize, 0, 16, 16);
				}
			}
			drawMobOnGui(guiLeft + 32, guiTop + 109, guiLeft + 32 - screenXSize, guiTop + 109 - 50 - screenYSize);
			squadronPrompt = StringUtils.isNullOrEmpty(squadronNameField.getText()) && !squadronNameField.isFocused();
			if (squadronPrompt) {
				String squadronMessage = StatCollector.translateToLocal("got.container.unitTrade.squadronBox");
				squadronNameField.setText(EnumChatFormatting.DARK_GRAY + squadronMessage);
			}
			squadronNameField.drawTextBox();
			if (squadronPrompt) {
				squadronNameField.setText("");
			}
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		GOTUnitTradeEntry curTrade = currentTrade();
		if (!GOT.isDevMode) {
			this.drawCenteredString(theUnitTrader.getNPCName(), 110, 11, 4210752);
			fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 30, 162, 4210752);
			this.drawCenteredString(curTrade.getUnitTradeName(), 138, 50, 4210752);
		}
		int reqX = 64;
		int reqXText = reqX + 19;
		int reqY = 65;
		int reqYTextBelow = 4;
		int reqGap = 18;
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		if (!GOT.isDevMode) {
			itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(GOTRegistry.coin), reqX, reqY);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int cost = curTrade.getCost(mc.thePlayer, theUnitTrader);
			fontRendererObj.drawString(String.valueOf(cost), reqXText, reqY + reqYTextBelow, 4210752);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
			drawTexturedModalRect(reqX, reqY += reqGap, 0, 36, 16, 16);
			float alignment = curTrade.alignmentRequired;
			String alignS = GOTAlignmentValues.formatAlignForDisplay(alignment);
			fontRendererObj.drawString(alignS, reqXText, reqY + reqYTextBelow, 4210752);
			if (curTrade.getPledgeType() != GOTUnitTradeEntry.PledgeType.NONE) {
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				mc.getTextureManager().bindTexture(GOTClientProxy.alignmentTexture);
				drawTexturedModalRect(reqX, reqY += reqGap, 0, 212, 16, 16);
				String pledge = StatCollector.translateToLocal("got.container.unitTrade.pledge");
				fontRendererObj.drawString(pledge, reqXText, reqY + reqYTextBelow, 4210752);
				int i2 = i - guiLeft - reqX;
				int j2 = j - guiTop - reqY;
				if (i2 >= 0 && i2 < 16 && j2 >= 0 && j2 < 16) {
					String pledgeDesc = curTrade.getPledgeType().getCommandReqText(traderFaction);
					drawCreativeTabHoveringText(pledgeDesc, i - guiLeft, j - guiTop);
					GL11.glDisable(2896);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
			if (((GOTContainerUnitTrade) inventorySlots).alignmentRewardSlots > 0) {
				Slot slot = inventorySlots.getSlot(0);
				boolean hasRewardCost = slot.getHasStack();
				if (hasRewardCost) {
					GL11.glEnable(2896);
					GL11.glEnable(2884);
					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(GOTRegistry.coin), 160, 100);
					GL11.glDisable(2896);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					cost = GOTSlotAlignmentReward.REWARD_COST;
					fontRendererObj.drawString(String.valueOf(cost), 179, 104, 4210752);
				} else if (!slot.getHasStack() && GOTLevelData.getData(mc.thePlayer).getAlignment(traderFaction) < 1500.0f && func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, i, j)) {
					drawCreativeTabHoveringText(StatCollector.translateToLocalFormatted("got.container.unitTrade.requiresAlignment", Float.valueOf(1500.0f)), i - guiLeft, j - guiTop);
					GL11.glDisable(2896);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
			if (curTrade.hasExtraInfo()) {
				String extraInfo = curTrade.getFormattedExtraInfo();
				boolean mouseover = i >= guiLeft + 49 && i < guiLeft + 49 + 9 && j >= guiTop + 106 && j < guiTop + 106 + 7;
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				mc.getTextureManager().bindTexture(guiTexture);
				drawTexturedModalRect(49, 106, 220, 38 + (mouseover ? 1 : 0) * 7, 9, 7);
				if (mouseover) {
					float z = zLevel;
					int stringWidth = 200;
					List desc = fontRendererObj.listFormattedStringToWidth(extraInfo, stringWidth);
					func_146283_a(desc, i - guiLeft, j - guiTop);
					GL11.glDisable(2896);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					zLevel = z;
				}
			}
		}
	}

	public void drawMobOnGui(int i, int j, float f, float f1) {
		Class entityClass = currentTrade().entityClass;
		Class mountClass = currentTrade().mountClass;
		if (currentDisplayedMob == null || currentDisplayedMob.getClass() != entityClass || mountClass == null && currentDisplayedMount != null || mountClass != null && (currentDisplayedMount == null || currentDisplayedMount.getClass() != mountClass)) {
			currentDisplayedMob = currentTrade().getOrCreateHiredNPC(mc.theWorld);
			if (mountClass != null) {
				currentDisplayedMount = currentTrade().createHiredMount(mc.theWorld);
				currentDisplayedMob.mountEntity(currentDisplayedMount);
			} else {
				currentDisplayedMount = null;
			}
		}
		float size = currentDisplayedMob.width * currentDisplayedMob.height * currentDisplayedMob.width;
		if (currentDisplayedMount != null) {
			size += currentDisplayedMount.width * currentDisplayedMount.height * currentDisplayedMount.width * 0.5f;
		}
		float scale = MathHelper.sqrt_float(MathHelper.sqrt_float(1.0f / size)) * 30.0f;
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		GL11.glTranslatef(i, j, 50.0f);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-(float) Math.atan(f1 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
		currentDisplayedMob.renderYawOffset = (float) Math.atan(f / 40.0f) * 20.0f;
		currentDisplayedMob.rotationYaw = (float) Math.atan(f / 40.0f) * 40.0f;
		currentDisplayedMob.rotationPitch = -(float) Math.atan(f1 / 40.0f) * 20.0f;
		currentDisplayedMob.rotationYawHead = currentDisplayedMob.rotationYaw;
		GL11.glTranslatef(0.0f, currentDisplayedMob.yOffset, 0.0f);
		if (currentDisplayedMount != null) {
			GL11.glTranslatef(0.0f, (float) currentDisplayedMount.getMountedYOffset(), 0.0f);
		}
		RenderManager.instance.playerViewY = 180.0f;
		RenderManager.instance.renderEntityWithPosYaw(currentDisplayedMob, 0.0, 0.0, 0.0, 0.0f, 1.0f);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		if (currentDisplayedMount != null) {
			GL11.glEnable(2903);
			GL11.glPushMatrix();
			GL11.glTranslatef(i, j, 50.0f);
			GL11.glScalef(-scale, scale, scale);
			GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
			RenderHelper.enableStandardItemLighting();
			GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-(float) Math.atan(f1 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
			currentDisplayedMount.renderYawOffset = (float) Math.atan(f / 40.0f) * 20.0f;
			currentDisplayedMount.rotationYaw = (float) Math.atan(f / 40.0f) * 40.0f;
			currentDisplayedMount.rotationPitch = -(float) Math.atan(f1 / 40.0f) * 20.0f;
			currentDisplayedMount.rotationYawHead = currentDisplayedMount.rotationYaw;
			GL11.glTranslatef(0.0f, currentDisplayedMount.yOffset, 0.0f);
			RenderManager.instance.playerViewY = 180.0f;
			RenderManager.instance.renderEntityWithPosYaw(currentDisplayedMount, 0.0, 0.0, 0.0, 0.0f, 1.0f);
			GL11.glPopMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(32826);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GL11.glDisable(3553);
			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
	}

	public void drawMobOnGuiDev(int i, int j, float f, float f1) {
		Class entityClass = currentTrade().entityClass;
		Class mountClass = currentTrade().mountClass;
		if (currentDisplayedMob == null || currentDisplayedMob.getClass() != entityClass || mountClass == null && currentDisplayedMount != null || mountClass != null && (currentDisplayedMount == null || currentDisplayedMount.getClass() != mountClass)) {
			currentDisplayedMob = currentTrade().getOrCreateHiredNPC(mc.theWorld);
			if (mountClass != null) {
				currentDisplayedMount = currentTrade().createHiredMount(mc.theWorld);
				currentDisplayedMob.mountEntity(currentDisplayedMount);
			} else {
				currentDisplayedMount = null;
			}
		}
		float size = currentDisplayedMob.width * currentDisplayedMob.height * currentDisplayedMob.width;
		float scale = MathHelper.sqrt_float(MathHelper.sqrt_float(1.0f / size)) * 30.0f * 2;
		GL11.glEnable(2903);
		GL11.glPushMatrix();
		GL11.glTranslatef(i, j, 50.0f);
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(30.0f, 1.0f, 0.0f, 0.0f);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-45.0f, 0.0f, 1.0f, 0.0f);
		RenderManager.instance.renderEntityWithPosYaw(currentDisplayedMob, 0.0, 0.0, 0.0, 0.0f, 1.0f);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		buttonLeftUnit.enabled = currentTradeEntryIndex > 0;
		buttonHire.enabled = currentTrade().hasRequiredCostAndAlignment(mc.thePlayer, theUnitTrader);
		buttonRightUnit.enabled = currentTradeEntryIndex < trades.tradeEntries.length - 1;
		super.drawScreen(i, j, f);
		screenXSize = i;
		screenYSize = j;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonLeftUnit = new GOTGuiUnitTradeButton(0, guiLeft + 90, guiTop + 144, 12, 19);
		buttonList.add(buttonLeftUnit);
		buttonLeftUnit.enabled = false;
		buttonHire = new GOTGuiUnitTradeButton(1, guiLeft + 102, guiTop + 144, 16, 19);
		buttonList.add(buttonHire);
		buttonRightUnit = new GOTGuiUnitTradeButton(2, guiLeft + 118, guiTop + 144, 12, 19);
		buttonList.add(buttonRightUnit);
		squadronNameField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 80, guiTop + 120, 160, 20);
		squadronNameField.setMaxStringLength(GOTSquadrons.SQUADRON_LENGTH_MAX);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (squadronNameField.getVisible() && squadronNameField.textboxKeyTyped(c, i)) {
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		squadronNameField.mouseClicked(i, j, k);
	}

	public void setTrades(GOTUnitTradeEntries t) {
		trades = t;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		squadronNameField.updateCursorCounter();
	}
}