package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketMercenaryInteract extends GOTPacketUnitTraderInteract {
	public GOTPacketMercenaryInteract() {
	}

	public GOTPacketMercenaryInteract(int idt, int a) {
		super(idt, a);
	}

	@Override
	public void openTradeGUI(EntityPlayer entityplayer, GOTEntityNPC trader) {
		entityplayer.openGui(GOT.instance, 59, entityplayer.worldObj, trader.getEntityId(), 0, 0);
	}

	public static class Handler implements IMessageHandler<GOTPacketMercenaryInteract, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMercenaryInteract message, MessageContext ctx) {
			return new GOTPacketUnitTraderInteract.Handler().onMessage(message, ctx);
		}
	}

}
