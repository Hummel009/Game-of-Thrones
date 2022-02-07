package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.*;
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
					entityplayer.openGui(GOT.getInstance(), 3, world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 2 && tradeableTrader.canTradeWith(entityplayer)) {
					entityplayer.openGui(GOT.getInstance(), 35, world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 3 && tradeableTrader.canTradeWith(entityplayer) && tradeableTrader instanceof GOTTradeable.Smith) {
					entityplayer.openGui(GOT.getInstance(), 54, world, livingTrader.getEntityId(), 0, 0);
				}
				if (closeScreen) {
					entityplayer.closeScreen();
				}
			}
			return null;
		}
	}

}
