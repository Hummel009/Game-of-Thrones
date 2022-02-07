package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTSquadrons;
import got.common.entity.other.*;
import got.common.inventory.GOTContainerUnitTrade;
import got.common.util.GOTLog;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.util.StringUtils;

public class GOTPacketBuyUnit implements IMessage {
	public int tradeIndex;
	public String squadron;

	public GOTPacketBuyUnit() {
	}

	public GOTPacketBuyUnit(int tr, String s) {
		tradeIndex = tr;
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		tradeIndex = data.readByte();
		int squadronLength = data.readInt();
		if (squadronLength > -1) {
			squadron = data.readBytes(squadronLength).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(tradeIndex);
		if (!StringUtils.isNullOrEmpty(squadron)) {
			byte[] squadronBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(squadronBytes.length);
			data.writeBytes(squadronBytes);
		} else {
			data.writeInt(-1);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketBuyUnit, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBuyUnit packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerUnitTrade) {
				GOTContainerUnitTrade tradeContainer = (GOTContainerUnitTrade) container;
				GOTHireableBase unitTrader = tradeContainer.theUnitTrader;
				int tradeIndex = packet.tradeIndex;
				GOTUnitTradeEntry trade = null;
				if (unitTrader instanceof GOTUnitTradeable) {
					GOTUnitTradeEntry[] tradeList = ((GOTUnitTradeable) unitTrader).getUnits().getTradeEntries();
					if (tradeIndex >= 0 && tradeIndex < tradeList.length) {
						trade = tradeList[tradeIndex];
					}
				} else if (unitTrader instanceof GOTMercenary) {
					trade = GOTMercenaryTradeEntry.createFor((GOTMercenary) unitTrader);
				}
				String squadron = packet.squadron;
				squadron = GOTSquadrons.checkAcceptableLength(squadron);
				if (trade != null) {
					trade.hireUnit(entityplayer, unitTrader, squadron);
					if (unitTrader instanceof GOTMercenary) {
						((EntityPlayer) entityplayer).closeScreen();
					}
				} else {
					GOTLog.getLogger().error("GOT: Error player " + entityplayer.getCommandSenderName() + " trying to hire unit from " + unitTrader.getNPCName() + " - trade is null or bad index!");
				}
			}
			return null;
		}
	}

}
