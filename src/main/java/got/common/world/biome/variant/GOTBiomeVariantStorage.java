package got.common.world.biome.variant;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTDimension;
import got.common.network.GOTPacketBiomeVariantsUnwatch;
import got.common.network.GOTPacketBiomeVariantsWatch;
import got.common.network.GOTPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.*;

public class GOTBiomeVariantStorage {
	private static final Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> CHUNK_VARIANT_MAP = new EnumMap<>(GOTDimension.class);
	private static final Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> CHUNK_VARIANT_MAP_CLIENT = new EnumMap<>(GOTDimension.class);

	private GOTBiomeVariantStorage() {
	}

	public static void clearAllVariants(World world) {
		getDimensionChunkMap(world).clear();
		FMLLog.info("Unloading GOT biome variants in %s", GOTDimension.GAME_OF_THRONES.getDimensionName());
	}

	public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		getDimensionChunkMap(world).remove(chunk);
	}

	public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
		return getChunkBiomeVariants(world, getChunkKey(chunk));
	}

	private static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		return getDimensionChunkMap(world).get(chunk);
	}

	private static ChunkCoordIntPair getChunkKey(Chunk chunk) {
		return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
	}

	private static Map<ChunkCoordIntPair, byte[]> getDimensionChunkMap(World world) {
		GOTDimension dim;
		Map<GOTDimension, Map<ChunkCoordIntPair, byte[]>> sourcemap = world.isRemote ? CHUNK_VARIANT_MAP_CLIENT : CHUNK_VARIANT_MAP;
		Map<ChunkCoordIntPair, byte[]> map = sourcemap.get(dim = GOTDimension.GAME_OF_THRONES);
		if (map == null) {
			map = new HashMap<>();
			sourcemap.put(dim, map);
		}
		return map;
	}

	public static int getSize(World world) {
		Map<ChunkCoordIntPair, byte[]> map = getDimensionChunkMap(world);
		return map.size();
	}

	public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		if (getChunkBiomeVariants(world, chunk) == null) {
			byte[] variants = data.hasKey("GOTBiomeVariants") ? data.getByteArray("GOTBiomeVariants") : new byte[256];
			setChunkBiomeVariants(world, chunk, variants);
		}
	}

	public static void performCleanup(WorldServer world) {
		Map<ChunkCoordIntPair, byte[]> dimensionMap = getDimensionChunkMap(world);
		System.nanoTime();
		Collection<ChunkCoordIntPair> removalChunks = new ArrayList<>();
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
		byte[] variants = getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			data.setByteArray("GOTBiomeVariants", variants);
		}
	}

	public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
		byte[] variants = getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			IMessage packet = new GOTPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition, variants);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
		} else {
			String dimName = world.provider.getDimensionName();
			int posX = chunk.xPosition << 4;
			int posZ = chunk.zPosition << 4;
			String playerName = entityplayer.getCommandSenderName();
			FMLLog.severe("Could not find GOT biome variants for %s chunk at %d, %d; requested by %s", dimName, posX, posZ, playerName);
		}
	}

	public static void sendUnwatchToPlayer(Chunk chunk, EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
		setChunkBiomeVariants(world, getChunkKey(chunk), variants);
	}

	public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
		getDimensionChunkMap(world).put(chunk, variants);
	}
}