package got.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.inventory.GOTContainerTrade;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class GOTPacketTraderInfo implements IMessage {
	private NBTTagCompound traderData;

	public GOTPacketTraderInfo(NBTTagCompound nbt) {
		setTraderData(nbt);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			setTraderData(new PacketBuffer(data).readNBTTagCompoundFromBuffer());
		} catch (IOException e) {
			FMLLog.severe("Error reading trader data");
			e.printStackTrace();
		}
	}

	public NBTTagCompound getTraderData() {
		return traderData;
	}

	public void setTraderData(NBTTagCompound traderData) {
		this.traderData = traderData;
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(getTraderData());
		} catch (IOException e) {
			FMLLog.severe("Error writing trader data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketTraderInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketTraderInfo packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerTrade) {
				GOTContainerTrade containerTrade = (GOTContainerTrade) container;
				containerTrade.getTheTraderNPC().traderNPCInfo.receiveClientPacket(packet);
			}
			return null;
		}
	}

}
