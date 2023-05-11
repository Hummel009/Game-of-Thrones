package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTBannerProtection;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class GOTPacketCreateCWP implements IMessage {
	public String name;

	public GOTPacketCreateCWP() {
	}

	public GOTPacketCreateCWP(String s) {
		name = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketCreateCWP, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCreateCWP packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			int numWaypoints = pd.getCustomWaypoints().size();
			if (numWaypoints <= pd.getMaxCustomWaypoints()) {
				IChatComponent[] protectionMessage = new IChatComponent[1];
				boolean protection = GOTBannerProtection.isProtected(world, entityplayer, GOTBannerProtection.forPlayer_returnMessage(entityplayer, GOTBannerProtection.Permission.FULL, protectionMessage), true);
				if (!protection) {
					String wpName = GOTCustomWaypoint.validateCustomName(packet.name);
					if (wpName != null) {
						GOTCustomWaypoint.createForPlayer(wpName, entityplayer);
					}
				} else {
					IChatComponent clientMessage = protectionMessage[0];
					GOTPacketCWPProtectionMessage packetMessage = new GOTPacketCWPProtectionMessage(clientMessage);
					GOTPacketHandler.networkWrapper.sendTo(packetMessage, entityplayer);
				}
			}
			return null;
		}
	}

}
