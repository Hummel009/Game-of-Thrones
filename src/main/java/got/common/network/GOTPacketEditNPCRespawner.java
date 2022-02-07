package got.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class GOTPacketEditNPCRespawner implements IMessage {
	public int spawnerID;
	public NBTTagCompound spawnerData;
	public boolean destroy;

	public GOTPacketEditNPCRespawner(GOTEntityNPCRespawner spawner) {
		spawnerID = spawner.getEntityId();
		spawnerData = new NBTTagCompound();
		spawner.writeSpawnerDataToNBT(spawnerData);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		spawnerID = data.readInt();
		try {
			spawnerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading spawner data");
			e.printStackTrace();
		}
		setDestroy(data.readBoolean());
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(spawnerID);
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(spawnerData);
		} catch (IOException e) {
			FMLLog.severe("Error writing spawner data");
			e.printStackTrace();
		}
		data.writeBoolean(isDestroy());
	}

	public static class Handler implements IMessageHandler<GOTPacketEditNPCRespawner, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEditNPCRespawner packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity sEntity = world.getEntityByID(packet.spawnerID);
			if (sEntity instanceof GOTEntityNPCRespawner) {
				GOTEntityNPCRespawner spawner = (GOTEntityNPCRespawner) sEntity;
				if (entityplayer.capabilities.isCreativeMode) {
					spawner.readSpawnerDataFromNBT(packet.spawnerData);
				}
				if (packet.isDestroy()) {
					spawner.onBreak();
				}
			}
			return null;
		}
	}

}
