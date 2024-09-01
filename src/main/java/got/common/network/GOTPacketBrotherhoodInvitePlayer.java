package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import got.common.util.GOTLog;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class GOTPacketBrotherhoodInvitePlayer extends GOTPacketBrotherhoodDo {
	private String invitedUsername;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodInvitePlayer() {
	}

	public GOTPacketBrotherhoodInvitePlayer(GOTBrotherhoodClient fs, String username) {
		super(fs);
		invitedUsername = username;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		invitedUsername = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		byte[] nameBytes = invitedUsername.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodInvitePlayer, IMessage> {
		private static UUID findInvitedPlayerUUID(String invitedUsername) {
			GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(invitedUsername);
			if (profile != null && profile.getId() != null) {
				return profile.getId();
			}
			return null;
		}

		@Override
		public IMessage onMessage(GOTPacketBrotherhoodInvitePlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				int limit = GOTConfig.getBrotherhoodMaxSize(entityplayer.worldObj);
				if (limit >= 0 && brotherhood.getPlayerCount() >= limit) {
					GOTLog.getLogger().warn(String.format("Player %s tried to invite a player with username %s to brotherhood %s, but brotherhood size %d is already >= the maximum of %d", entityplayer.getCommandSenderName(), packet.invitedUsername, brotherhood.getName(), brotherhood.getPlayerCount(), limit));
				} else {
					UUID invitedPlayer = findInvitedPlayerUUID(packet.invitedUsername);
					if (invitedPlayer != null) {
						GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
						playerData.invitePlayerToBrotherhood(brotherhood, invitedPlayer, entityplayer.getCommandSenderName());
					} else {
						GOTLog.getLogger().warn(String.format("Player %s tried to invite a player with username %s to brotherhood %s, but couldn't find the invited player's UUID", entityplayer.getCommandSenderName(), packet.invitedUsername, brotherhood.getName()));
					}
				}
			}
			return null;
		}
	}
}