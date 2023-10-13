package got.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.inventory.GOTContainerTrade;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class GOTPacketTraderInfo implements IMessage {
	public NBTTagCompound traderData;

	public GOTPacketTraderInfo() {
	}

	public GOTPacketTraderInfo(NBTTagCompound nbt) {
		traderData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			traderData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading trader data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(traderData);
		} catch (IOException e) {
			FMLLog.severe("Error writing trader data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketTraderInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketTraderInfo packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerTrade) {
				GOTContainerTrade containerTrade = (GOTContainerTrade) container;
				containerTrade.theTraderNPC.traderNPCInfo.receiveClientPacket(packet);
			}
			return null;
		}
	}

}
