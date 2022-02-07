package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketSetOption implements IMessage {
	private int option;

	public GOTPacketSetOption(int i) {
		option = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		option = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(option);
	}

	public static class Handler implements IMessageHandler<GOTPacketSetOption, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSetOption packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			int option = packet.option;
			boolean flag;
			switch (option) {
			case 0:
				flag = pd.getFriendlyFire();
				pd.setFriendlyFire(!flag);
				break;
			case 1:
				flag = pd.getEnableHiredDeathMessages();
				pd.setEnableHiredDeathMessages(!flag);
				break;
			case 2:
				flag = pd.getHideAlignment();
				pd.setHideAlignment(!flag);
				break;
			case 3:
				flag = pd.getHideMapLocation();
				pd.setHideMapLocation(!flag);
				break;
			case 4:
				flag = pd.getFemRankOverride();
				pd.setFemRankOverride(!flag);
				break;
			case 5:
				flag = pd.getEnableConquestKills();
				pd.setEnableConquestKills(!flag);
				break;
			case 9:
				flag = pd.getTableSwitched();
				pd.setTableSwitched(!flag);
				break;
			default:
				break;
			}
			return null;
		}
	}

}