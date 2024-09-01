package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketBrotherhoodRename extends GOTPacketBrotherhoodDo {
	private String newName;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodRename() {
	}

	public GOTPacketBrotherhoodRename(GOTBrotherhoodClient fs, String name) {
		super(fs);
		newName = name;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		newName = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		byte[] nameBytes = newName.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodRename, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodRename packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				playerData.renameBrotherhood(brotherhood, packet.newName);
			}
			return null;
		}
	}
}