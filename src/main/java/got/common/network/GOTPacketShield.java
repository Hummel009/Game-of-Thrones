package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTShields;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketShield implements IMessage {
	public UUID player;
	public GOTShields shield;

	public GOTPacketShield() {
	}

	public GOTPacketShield(UUID uuid) {
		player = uuid;
		GOTPlayerData pd = GOTLevelData.getData(player);
		shield = pd.getShield();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		player = new UUID(data.readLong(), data.readLong());
		boolean hasShield = data.readBoolean();
		if (hasShield) {
			byte shieldID = data.readByte();
			byte shieldTypeID = data.readByte();
			if (shieldTypeID < 0 || shieldTypeID >= GOTShields.ShieldType.values().length) {
				FMLLog.severe("Failed to update GOT shield on client side: There is no shieldtype with ID " + shieldTypeID);
			} else {
				GOTShields.ShieldType shieldType = GOTShields.ShieldType.values()[shieldTypeID];
				if (shieldID < 0 || shieldID >= shieldType.list.size()) {
					FMLLog.severe("Failed to update GOT shield on client side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID);
				} else {
					shield = shieldType.list.get(shieldID);
				}
			}
		} else {
			shield = null;
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(player.getMostSignificantBits());
		data.writeLong(player.getLeastSignificantBits());
		boolean hasShield = shield != null;
		data.writeBoolean(hasShield);
		if (hasShield) {
			data.writeByte(shield.shieldID);
			data.writeByte(shield.shieldType.ordinal());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketShield, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketShield packet, MessageContext context) {
			GOTPlayerData pd = GOTLevelData.getData(packet.player);
			pd.setShield(packet.shield);
			return null;
		}
	}

}
