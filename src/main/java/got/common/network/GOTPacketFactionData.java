package got.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.faction.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class GOTPacketFactionData implements IMessage {
	public GOTFaction faction;
	public NBTTagCompound factionNBT;

	public GOTPacketFactionData() {
	}

	public GOTPacketFactionData(GOTFaction f, NBTTagCompound nbt) {
		faction = f;
		factionNBT = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte factionID = data.readByte();
		faction = GOTFaction.forID(factionID);
		try {
			factionNBT = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading faction data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(faction.ordinal());
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(factionNBT);
		} catch (IOException e) {
			FMLLog.severe("Error writing faction data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketFactionData, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFactionData packet, MessageContext context) {
			if (!GOT.getProxy().isSingleplayer()) {
				EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTFaction faction = packet.faction;
				if (faction != null) {
					GOTFactionData factionData = pd.getFactionData(faction);
					factionData.load(packet.factionNBT);
				}
			}
			return null;
		}
	}

}
