package got.common.network;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.*;

public class GOTPacketUpdatePlayerLocations implements IMessage {
	private static final Map<UUID, byte[]> CACHED_PROFILE_BYTES = new HashMap<>();

	private final Collection<PlayerLocationInfo> playerLocations = new ArrayList<>();

	public void addPlayerLocation(GameProfile profile, double x, double z) {
		if (profile.isComplete()) {
			playerLocations.add(new PlayerLocationInfo(profile, x, z));
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		playerLocations.clear();
		int players = data.readShort();
		for (int l = 0; l < players; ++l) {
			short len = data.readShort();
			if (len < 0) {
				continue;
			}
			ByteBuf profileBytes = data.readBytes(len);
			GameProfile profile = null;
			try {
				NBTTagCompound profileData = new PacketBuffer(profileBytes).readNBTTagCompoundFromBuffer();
				profile = NBTUtil.func_152459_a(profileData);
			} catch (IOException e) {
				FMLLog.severe("Error reading player profile data");
				e.printStackTrace();
			}
			double posX = data.readDouble();
			double posZ = data.readDouble();
			if (profile == null) {
				continue;
			}
			PlayerLocationInfo playerInfo = new PlayerLocationInfo(profile, posX, posZ);
			playerLocations.add(playerInfo);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		int players = playerLocations.size();
		data.writeShort(players);
		for (PlayerLocationInfo player : playerLocations) {
			GameProfile profile = player.getPlayerProfile();
			UUID playerID = profile.getId();
			byte[] profileBytes = null;
			if (CACHED_PROFILE_BYTES.containsKey(playerID)) {
				profileBytes = CACHED_PROFILE_BYTES.get(playerID);
			} else {
				ByteBuf tempBuf = Unpooled.buffer();
				try {
					NBTTagCompound profileData = new NBTTagCompound();
					NBTUtil.func_152460_a(profileData, profile);
					new PacketBuffer(tempBuf).writeNBTTagCompoundToBuffer(profileData);
					byte[] tempBytes = tempBuf.array();
					profileBytes = Arrays.copyOf(tempBytes, tempBytes.length);
					CACHED_PROFILE_BYTES.put(playerID, profileBytes);
				} catch (IOException e) {
					FMLLog.severe("Error writing player profile data");
					e.printStackTrace();
				}
			}
			if (profileBytes == null) {
				data.writeShort(-1);
				continue;
			}
			byte[] copied = Arrays.copyOf(profileBytes, profileBytes.length);
			data.writeShort(copied.length);
			data.writeBytes(copied);
			data.writeDouble(player.getPosX());
			data.writeDouble(player.getPosZ());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketUpdatePlayerLocations, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketUpdatePlayerLocations packet, MessageContext context) {
			GOT.proxy.clearMapPlayerLocations();
			for (PlayerLocationInfo info : packet.playerLocations) {
				GOT.proxy.addMapPlayerLocation(info.getPlayerProfile(), info.getPosX(), info.getPosZ());
			}
			return null;
		}
	}

	private static class PlayerLocationInfo {
		private final GameProfile playerProfile;
		private final double posX;
		private final double posZ;

		private PlayerLocationInfo(GameProfile profile, double x, double z) {
			playerProfile = profile;
			posX = x;
			posZ = z;
		}

		private double getPosZ() {
			return posZ;
		}

		private double getPosX() {
			return posX;
		}

		private GameProfile getPlayerProfile() {
			return playerProfile;
		}
	}
}