package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTCapes;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketCape implements IMessage {
	public UUID player;
	public GOTCapes cape;

	public GOTPacketCape() {
	}

	public GOTPacketCape(UUID uuid) {
		player = uuid;
		GOTPlayerData pd = GOTLevelData.getData(player);
		cape = pd.getCape();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		player = new UUID(data.readLong(), data.readLong());
		boolean hasCape = data.readBoolean();
		if (hasCape) {
			byte capeID = data.readByte();
			byte capeTypeID = data.readByte();
			if (capeTypeID < 0 || capeTypeID >= GOTCapes.CapeType.values().length) {
				FMLLog.severe("Failed to update GOT cape on client side: There is no capetype with ID " + capeTypeID);
			} else {
				GOTCapes.CapeType capeType = GOTCapes.CapeType.values()[capeTypeID];
				if (capeID < 0 || capeID >= capeType.getCapeListSize()) {
					FMLLog.severe("Failed to update GOT cape on client side: There is no cape with ID " + capeID + " for capetype " + capeTypeID);
				} else {
					cape = capeType.getCape(capeID);
				}
			}
		} else {
			cape = null;
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(player.getMostSignificantBits());
		data.writeLong(player.getLeastSignificantBits());
		boolean hasCape = cape != null;
		data.writeBoolean(hasCape);
		if (hasCape) {
			data.writeByte(cape.getCapeID());
			data.writeByte(cape.getCapeType().ordinal());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketCape, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCape packet, MessageContext context) {
			GOTPlayerData pd = GOTLevelData.getData(packet.player);
			pd.setCape(packet.cape);
			return null;
		}
	}

}
