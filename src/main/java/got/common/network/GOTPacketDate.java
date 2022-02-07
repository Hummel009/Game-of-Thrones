package got.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTDate;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class GOTPacketDate implements IMessage {
	public NBTTagCompound dateData;
	public boolean doUpdate;

	public GOTPacketDate(NBTTagCompound nbt, boolean flag) {
		dateData = nbt;
		doUpdate = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			dateData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading GOT date");
			e.printStackTrace();
		}
		doUpdate = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(dateData);
		} catch (IOException e) {
			FMLLog.severe("Error writing GOT date");
			e.printStackTrace();
		}
		data.writeBoolean(doUpdate);
	}

	public static class Handler implements IMessageHandler<GOTPacketDate, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketDate packet, MessageContext context) {
			GOTDate.loadDates(packet.dateData);
			if (packet.doUpdate) {
				GOT.getProxy().displayNewDate();
			}
			return null;
		}
	}

}
