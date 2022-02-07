package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTLevelData;
import got.common.database.GOTCapes;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketSelectCape implements IMessage {
	public GOTCapes cape;

	public GOTPacketSelectCape() {
	}

	public GOTPacketSelectCape(GOTCapes s) {
		cape = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte capeID = data.readByte();
		if (capeID == -1) {
			cape = null;
		} else {
			byte capeTypeID = data.readByte();
			if (capeTypeID < 0 || capeTypeID >= GOTCapes.CapeType.values().length) {
				FMLLog.severe("Failed to update GOT cape on server side: There is no capetype with ID " + capeTypeID);
			} else {
				GOTCapes.CapeType capeType = GOTCapes.CapeType.values()[capeTypeID];
				if (capeID < 0 || capeID >= capeType.getList().size()) {
					FMLLog.severe("Failed to update GOT cape on server side: There is no cape with ID " + capeID + " for capetype " + capeTypeID);
				} else {
					cape = capeType.getList().get(capeID);
				}
			}
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (cape == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(cape.getCapeID());
			data.writeByte(cape.getCapeType().ordinal());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketSelectCape, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSelectCape packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTCapes cape = packet.cape;
			if (cape == null || cape.canPlayerWear(entityplayer)) {
				GOTLevelData.getData(entityplayer).setCape(cape);
				GOTLevelData.sendCapeToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			} else {
				FMLLog.severe("Failed to update GOT cape on server side: Player " + entityplayer.getCommandSenderName() + " cannot wear cape " + cape.name());
			}
			return null;
		}
	}

}
