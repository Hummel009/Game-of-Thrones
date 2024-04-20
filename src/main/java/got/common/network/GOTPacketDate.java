package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTDate;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class GOTPacketDate implements IMessage {
	private NBTTagCompound dateData;
	private boolean doUpdate;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketDate() {
	}

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
				GOT.proxy.displayNewDate();
			}
			return null;
		}
	}
}