package got.common.world.biome.variant;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTDimension;
import got.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;

public class GOTBiomeVariantStorage {
	public static Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMap = new HashMap<>();
	public static Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMapClient = new HashMap<>();

	public static void clearAllVariants(World world) {
		GOTBiomeVariantStorage.getDimensionChunkMap(world).clear();
		FMLLog.info("Unloading GOT biome variants in %s", GOTDimension.getCurrentDimension(world).dimensionName);
	}

	public static void clearChunkBiomeVariants(World world, Chunk chunk) {
		GOTBiomeVariantStorage.clearChunkBiomeVariants(world, GOTBiomeVariantStorage.getChunkKey(chunk));
	}

	public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		GOTBiomeVariantStorage.getDimensionChunkMap(world).remove(chunk);
	}

	public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
		return GOTBiomeVariantStorage.getChunkBiomeVariants(world, GOTBiomeVariantStorage.getChunkKey(chunk));
	}

	public static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		return GOTBiomeVariantStorage.getDimensionChunkMap(world).get(chunk);
	}

	public static ChunkCoordIntPair getChunkKey(Chunk chunk) {
		return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
	}

	public static Map<ChunkCoordIntPair, byte[]> getDimensionChunkMap(World world) {
		GOTDimension dim;
		Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> sourcemap = !world.isRemote ? chunkVariantMap : chunkVariantMapClient;
		Map<ChunkCoordIntPair, byte[]> map = sourcemap.get(dim = GOTDimension.getCurrentDimension(world));
		if (map == null) {
			map = new HashMap<>();
			sourcemap.put(dim, map);
		}
		return map;
	}

	public static int getSize(World world) {
		Map<ChunkCoordIntPair, byte[]> map = GOTBiomeVariantStorage.getDimensionChunkMap(world);
		return map.size();
	}

	public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		if (GOTBiomeVariantStorage.getChunkBiomeVariants(world, chunk) == null) {
			byte[] variants = data.hasKey("GOTBiomeVariants") ? data.getByteArray("GOTBiomeVariants") : new byte[256];
			GOTBiomeVariantStorage.setChunkBiomeVariants(world, chunk, variants);
		}
	}

	public static void performCleanup(WorldServer world) {
		Map<ChunkCoordIntPair, byte[]> dimensionMap = GOTBiomeVariantStorage.getDimensionChunkMap(world);
		dimensionMap.size();
		System.nanoTime();
		ArrayList<ChunkCoordIntPair> removalChunks = new ArrayList<>();
		for (ChunkCoordIntPair chunk : dimensionMap.keySet()) {
			if (world.theChunkProviderServer.chunkExists(chunk.chunkXPos, chunk.chunkZPos)) {
				continue;
			}
			removalChunks.add(chunk);
		}
		for (ChunkCoordIntPair chunk : removalChunks) {
			dimensionMap.remove(chunk);
		}
	}

	public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		byte[] variants = GOTBiomeVariantStorage.getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			data.setByteArray("GOTBiomeVariants", variants);
		}
	}

	public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
		byte[] variants = GOTBiomeVariantStorage.getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			GOTPacketBiomeVariantsWatch packet = new GOTPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition, variants);
			GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		} else {
			String dimName = world.provider.getDimensionName();
			int posX = chunk.xPosition << 4;
			int posZ = chunk.zPosition << 4;
			String playerName = entityplayer.getCommandSenderName();
			FMLLog.severe("Could not find GOT biome variants for %s chunk at %d, %d; requested by %s", dimName, posX, posZ, playerName);
		}
	}

	public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
		GOTPacketBiomeVariantsUnwatch packet = new GOTPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
		GOTBiomeVariantStorage.setChunkBiomeVariants(world, GOTBiomeVariantStorage.getChunkKey(chunk), variants);
	}

	public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
		GOTBiomeVariantStorage.getDimensionChunkMap(world).put(chunk, variants);
	}
}
