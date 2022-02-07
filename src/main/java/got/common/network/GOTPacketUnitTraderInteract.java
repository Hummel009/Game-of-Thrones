package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.world.World;

public class GOTPacketUnitTraderInteract implements IMessage {
	private int traderID;
	private int traderAction;

	public GOTPacketUnitTraderInteract() {
	}

	public GOTPacketUnitTraderInteract(int idt, int a) {
		traderID = idt;
		traderAction = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		traderID = data.readInt();
		traderAction = data.readByte();
	}

	public void openTradeGUI(EntityPlayer entityplayer, GOTEntityNPC trader) {
		entityplayer.openGui(GOT.getInstance(), 7, entityplayer.worldObj, trader.getEntityId(), 0, 0);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(traderID);
		data.writeByte(traderAction);
	}

	public static class Handler implements IMessageHandler<GOTPacketUnitTraderInteract, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketUnitTraderInteract packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity trader = world.getEntityByID(packet.traderID);
			if (trader instanceof GOTHireableBase) {
				GOTHireableBase tradeableTrader = (GOTHireableBase) trader;
				GOTEntityNPC livingTrader = (GOTEntityNPC) trader;
				int action = packet.traderAction;
				boolean closeScreen = false;
				if (action == 0) {
					livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
					closeScreen = livingTrader.interactFirst(entityplayer);
				} else if (action == 1 && tradeableTrader.canTradeWith(entityplayer)) {
					packet.openTradeGUI(entityplayer, livingTrader);
				}
				if (closeScreen) {
					entityplayer.closeScreen();
				}
			}
			return null;
		}
	}

}
