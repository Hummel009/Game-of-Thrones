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
	public static Map<UUID, byte[]> cachedProfileBytes = new HashMap<>();
	public List<PlayerLocationInfo> playerLocations = new ArrayList<>();

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
			GameProfile profile = player.playerProfile;
			UUID playerID = profile.getId();
			byte[] profileBytes = null;
			if (cachedProfileBytes.containsKey(playerID)) {
				profileBytes = cachedProfileBytes.get(playerID);
			} else {
				ByteBuf tempBuf = Unpooled.buffer();
				try {
					NBTTagCompound profileData = new NBTTagCompound();
					NBTUtil.func_152460_a(profileData, profile);
					new PacketBuffer(tempBuf).writeNBTTagCompoundToBuffer(profileData);
					byte[] tempBytes = tempBuf.array();
					profileBytes = Arrays.copyOf(tempBytes, tempBytes.length);
					cachedProfileBytes.put(playerID, profileBytes);
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
			data.writeDouble(player.posX);
			data.writeDouble(player.posZ);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketUpdatePlayerLocations, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketUpdatePlayerLocations packet, MessageContext context) {
			GOT.proxy.clearMapPlayerLocations();
			for (PlayerLocationInfo info : packet.playerLocations) {
				GOT.proxy.addMapPlayerLocation(info.playerProfile, info.posX, info.posZ);
			}
			return null;
		}
	}

	public static class PlayerLocationInfo {
		public GameProfile playerProfile;
		public double posX;
		public double posZ;

		public PlayerLocationInfo(GameProfile profile, double x, double z) {
			playerProfile = profile;
			posX = x;
			posZ = z;
		}
	}

}
