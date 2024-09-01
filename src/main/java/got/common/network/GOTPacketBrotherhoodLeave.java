package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketBrotherhoodLeave extends GOTPacketBrotherhoodDo {
	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodLeave() {
	}

	public GOTPacketBrotherhoodLeave(GOTBrotherhoodClient fs) {
		super(fs);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodLeave, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodLeave packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				playerData.leaveBrotherhood(brotherhood);
			}
			return null;
		}
	}
}