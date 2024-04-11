package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.database.GOTGuiId;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketTraderInteract implements IMessage {
	public int traderID;
	public int traderAction;

	public GOTPacketTraderInteract() {
	}

	public GOTPacketTraderInteract(int idt, int a) {
		traderID = idt;
		traderAction = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		traderID = data.readInt();
		traderAction = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(traderID);
		data.writeByte(traderAction);
	}

	public static class Handler implements IMessageHandler<GOTPacketTraderInteract, IMessage> {
		@Override
		@SuppressWarnings("CastConflictsWithInstanceof")
		public IMessage onMessage(GOTPacketTraderInteract packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity trader = world.getEntityByID(packet.traderID);
			if (trader instanceof GOTTradeable) {
				GOTTradeable tradeableTrader = (GOTTradeable) trader;
				GOTEntityNPC livingTrader = (GOTEntityNPC) trader;
				int action = packet.traderAction;
				boolean closeScreen = false;
				if (action == 0) {
					livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
					closeScreen = livingTrader.interactFirst(entityplayer);
				} else if (action == 1 && tradeableTrader.canTradeWith(entityplayer)) {
					entityplayer.openGui(GOT.instance, GOTGuiId.TRADE.ordinal(), world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 2 && tradeableTrader.canTradeWith(entityplayer)) {
					entityplayer.openGui(GOT.instance, GOTGuiId.COIN_EXCHANGE.ordinal(), world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 3 && tradeableTrader.canTradeWith(entityplayer) && tradeableTrader instanceof GOTTradeable.Smith) {
					entityplayer.openGui(GOT.instance, GOTGuiId.ANVIL_NPC.ordinal(), world, livingTrader.getEntityId(), 0, 0);
				}
				if (closeScreen) {
					entityplayer.closeScreen();
				}
			}
			return null;
		}
	}

}
