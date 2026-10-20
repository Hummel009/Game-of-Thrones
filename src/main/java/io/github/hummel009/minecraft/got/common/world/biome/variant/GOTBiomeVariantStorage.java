package io.github.hummel009.minecraft.got.common.world.biome.variant;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.github.hummel009.minecraft.got.common.GOTDimension;
import io.github.hummel009.minecraft.got.common.network.GOTPacketBiomeVariantsUnwatch;
import io.github.hummel009.minecraft.got.common.network.GOTPacketBiomeVariantsWatch;
import io.github.hummel009.minecraft.got.common.network.GOTPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GOTBiomeVariantStorage {
	private static final Map<GOTDimension, Map<Long, byte[]>> CHUNK_VARIANT_MAP = new EnumMap<>(GOTDimension.class);
	private static final Map<GOTDimension, Map<Long, byte[]>> CHUNK_VARIANT_MAP_CLIENT = new EnumMap<>(GOTDimension.class);

	private GOTBiomeVariantStorage() {
	}

	private static long packKey(int chunkX, int chunkZ) {
		return (long) chunkX << 32 | chunkZ & 0xFFFFFFFFL;
	}

	public static void clearAllVariants(World world) {
		getDimensionChunkMap(world).clear();
		FMLLog.info("Unloading GOT biome variants in %s", GOTDimension.GAME_OF_THRONES.getDimensionName());
	}

	public static void clearChunkBiomeVariants(World world, int chunkX, int chunkZ) {
		getDimensionChunkMap(world).remove(packKey(chunkX, chunkZ));
	}

	public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
		return getChunkBiomeVariants(world, chunk.xPosition, chunk.zPosition);
	}

	private static byte[] getChunkBiomeVariants(World world, int chunkX, int chunkZ) {
		return getDimensionChunkMap(world).get(packKey(chunkX, chunkZ));
	}

	private static Map<Long, byte[]> getDimensionChunkMap(World world) {
		GOTDimension dim;
		Map<GOTDimension, Map<Long, byte[]>> sourcemap = world.isRemote ? CHUNK_VARIANT_MAP_CLIENT : CHUNK_VARIANT_MAP;
		Map<Long, byte[]> map = sourcemap.get(dim = GOTDimension.GAME_OF_THRONES);
		if (map == null) {
			map = new HashMap<>();
			sourcemap.put(dim, map);
		}
		return map;
	}

	public static int getSize(World world) {
		return getDimensionChunkMap(world).size();
	}

	public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		if (getChunkBiomeVariants(world, chunk) == null) {
			byte[] variants = data.hasKey("GOTBiomeVariants") ? data.getByteArray("GOTBiomeVariants") : new byte[256];
			setChunkBiomeVariants(world, chunk, variants);
		}
	}

	public static void performCleanup(WorldServer world) {
		Map<Long, byte[]> dimensionMap = getDimensionChunkMap(world);
		Iterator<Map.Entry<Long, byte[]>> iterator = dimensionMap.entrySet().iterator();
		while (iterator.hasNext()) {
			long key = iterator.next().getKey();
			int chunkX = (int) (key >> 32);
			int chunkZ = (int) key;
			if (world.theChunkProviderServer.chunkExists(chunkX, chunkZ)) {
				continue;
			}
			iterator.remove();
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
		setChunkBiomeVariants(world, chunk.xPosition, chunk.zPosition, variants);
	}

	public static void setChunkBiomeVariants(World world, int chunkX, int chunkZ, byte[] variants) {
		getDimensionChunkMap(world).put(packKey(chunkX, chunkZ), variants);
	}
}