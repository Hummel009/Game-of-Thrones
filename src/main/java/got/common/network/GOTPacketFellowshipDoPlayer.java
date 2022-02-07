package got.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class GOTPacketFellowshipDoPlayer extends GOTPacketFellowshipDo {
	private String subjectUsername;
	private PlayerFunction function;

	public GOTPacketFellowshipDoPlayer(GOTFellowshipClient fs, String name, PlayerFunction f) {
		super(fs);
		subjectUsername = name;
		function = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		subjectUsername = nameBytes.toString(Charsets.UTF_8);
		function = PlayerFunction.values()[data.readByte()];
	}

	private UUID getSubjectPlayerUUID() {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(subjectUsername);
		if (profile != null && profile.getId() != null) {
			return profile.getId();
		}
		return null;
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		byte[] nameBytes = subjectUsername.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
		data.writeByte(function.ordinal());
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipDoPlayer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipDoPlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getFellowship();
			UUID subjectPlayer = packet.getSubjectPlayerUUID();
			if (fellowship != null && subjectPlayer != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				switch (packet.function) {
				case INVITE:
					playerData.invitePlayerToFellowship(fellowship, subjectPlayer);
					break;
				case REMOVE:
					playerData.removePlayerFromFellowship(fellowship, subjectPlayer);
					break;
				case TRANSFER:
					playerData.transferFellowship(fellowship, subjectPlayer);
					break;
				case OP:
					playerData.setFellowshipAdmin(fellowship, subjectPlayer, true);
					break;
				case DEOP:
					playerData.setFellowshipAdmin(fellowship, subjectPlayer, false);
					break;
				}
			}
			return null;
		}
	}

	public enum PlayerFunction {
		INVITE, REMOVE, TRANSFER, OP, DEOP;

	}

}
