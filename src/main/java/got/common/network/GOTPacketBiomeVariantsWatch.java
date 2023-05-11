package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class GOTPacketBiomeVariantsWatch implements IMessage {
	public int chunkX;
	public int chunkZ;
	public byte[] variants;

	public GOTPacketBiomeVariantsWatch() {
	}

	public GOTPacketBiomeVariantsWatch(int x, int z, byte[] v) {
		chunkX = x;
		chunkZ = z;
		variants = v;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		chunkX = data.readInt();
		chunkZ = data.readInt();
		int length = data.readInt();
		variants = data.readBytes(length).array();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(chunkX);
		data.writeInt(chunkZ);
		data.writeInt(variants.length);
		data.writeBytes(variants);
	}

	public static class Handler implements IMessageHandler<GOTPacketBiomeVariantsWatch, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBiomeVariantsWatch packet, MessageContext context) {
			int chunkX;
			int chunkZ;
			World world = GOT.proxy.getClientWorld();
			if (world.blockExists((chunkX = packet.chunkX) << 4, 0, (chunkZ = packet.chunkZ) << 4)) {
				GOTBiomeVariantStorage.setChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ), packet.variants);
			} else {
				FMLLog.severe("Client received GOT biome variant data for nonexistent chunk at %d, %d", chunkX << 4, chunkZ << 4);
			}
			return null;
		}
	}

}
