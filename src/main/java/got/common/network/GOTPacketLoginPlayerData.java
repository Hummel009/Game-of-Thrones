package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class GOTPacketLoginPlayerData implements IMessage {
	private NBTTagCompound playerData;

	@SuppressWarnings("unused")
	public GOTPacketLoginPlayerData() {
	}

	public GOTPacketLoginPlayerData(NBTTagCompound nbt) {
		playerData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			playerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading GOT login player data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(playerData);
		} catch (IOException e) {
			FMLLog.severe("Error writing GOT login player data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketLoginPlayerData, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLoginPlayerData packet, MessageContext context) {
			NBTTagCompound nbt = packet.playerData;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (!GOT.proxy.isSingleplayer()) {
				pd.load(nbt);
			}
			GOT.proxy.setWaypointModes(pd.showWaypoints(), pd.showCustomWaypoints(), pd.showHiddenSharedWaypoints());
			return null;
		}
	}
}