package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketOptions implements IMessage {
	public int option;
	public boolean enable;

	public GOTPacketOptions() {
	}

	public GOTPacketOptions(int i, boolean flag) {
		option = i;
		enable = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		option = data.readByte();
		enable = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(option);
		data.writeBoolean(enable);
	}

	public static class Handler implements IMessageHandler<GOTPacketOptions, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketOptions packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
				int option = packet.option;
				boolean enable = packet.enable;
				switch (option) {
					case 0:
						GOTLevelData.getData(entityplayer).setFriendlyFire(enable);
						break;
					case 1:
						GOTLevelData.getData(entityplayer).setEnableHiredDeathMessages(enable);
						break;
					case 3:
						GOTLevelData.getData(entityplayer).setHideMapLocation(enable);
						break;
					case 4:
						GOTLevelData.getData(entityplayer).setFemRankOverride(enable);
						break;
					case 5:
						GOTLevelData.getData(entityplayer).setEnableConquestKills(enable);
						break;
					case 9:
						GOTLevelData.getData(entityplayer).setTableSwitched(enable);
						break;
					default:
						break;
				}
			}
			return null;
		}
	}

}