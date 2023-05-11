package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.database.GOTShields;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketSelectShield implements IMessage {
	public GOTShields shield;

	public GOTPacketSelectShield() {
	}

	public GOTPacketSelectShield(GOTShields s) {
		shield = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte shieldID = data.readByte();
		if (shieldID == -1) {
			shield = null;
		} else {
			byte shieldTypeID = data.readByte();
			if (shieldTypeID < 0 || shieldTypeID >= GOTShields.ShieldType.values().length) {
				FMLLog.severe("Failed to update GOT shield on server side: There is no shieldtype with ID " + shieldTypeID);
			} else {
				GOTShields.ShieldType shieldType = GOTShields.ShieldType.values()[shieldTypeID];
				if (shieldID < 0 || shieldID >= shieldType.list.size()) {
					FMLLog.severe("Failed to update GOT shield on server side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID);
				} else {
					shield = shieldType.list.get(shieldID);
				}
			}
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (shield == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(shield.shieldID);
			data.writeByte(shield.shieldType.ordinal());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketSelectShield, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSelectShield packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTShields shield = packet.shield;
			if (shield == null || shield.canPlayerWear(entityplayer)) {
				GOTLevelData.getData(entityplayer).setShield(shield);
				GOTLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			} else {
				FMLLog.severe("Failed to update GOT shield on server side: Player " + entityplayer.getCommandSenderName() + " cannot wear shield " + shield.name());
			}
			return null;
		}
	}

}
