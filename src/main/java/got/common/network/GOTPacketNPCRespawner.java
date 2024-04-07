package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.database.GOTGuiId;
import got.common.entity.other.GOTEntityNPCRespawner;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class GOTPacketNPCRespawner implements IMessage {
	public int spawnerID;
	public NBTTagCompound spawnerData;

	public GOTPacketNPCRespawner() {
	}

	public GOTPacketNPCRespawner(GOTEntityNPCRespawner spawner) {
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
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCRespawner, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCRespawner packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			Entity entity = world.getEntityByID(packet.spawnerID);
			if (entity instanceof GOTEntityNPCRespawner) {
				GOTEntityNPCRespawner spawner = (GOTEntityNPCRespawner) entity;
				spawner.readSpawnerDataFromNBT(packet.spawnerData);
				entityplayer.openGui(GOT.instance, GOTGuiId.NPC_RESPAWNER.ordinal(), world, entity.getEntityId(), 0, 0);
			}
			return null;
		}
	}

}
