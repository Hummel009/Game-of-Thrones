package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class GOTPacketFellowshipDoPlayer extends GOTPacketFellowshipDo {
	public UUID subjectUuid;
	public PlayerFunction function;

	public GOTPacketFellowshipDoPlayer() {
	}

	public GOTPacketFellowshipDoPlayer(GOTFellowshipClient fs, UUID subject, PlayerFunction f) {
		super(fs);
		subjectUuid = subject;
		function = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		subjectUuid = new UUID(data.readLong(), data.readLong());
		function = PlayerFunction.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeLong(subjectUuid.getMostSignificantBits());
		data.writeLong(subjectUuid.getLeastSignificantBits());
		data.writeByte(function.ordinal());
	}

	public enum PlayerFunction {
		REMOVE, TRANSFER, OP, DEOP
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipDoPlayer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipDoPlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			String playerName = entityplayer.getCommandSenderName();
			GOTFellowship fellowship = packet.getActiveFellowship();
			UUID subjectPlayer = packet.subjectUuid;
			if (fellowship != null && subjectPlayer != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				switch (packet.function) {
					case DEOP:
						playerData.setFellowshipAdmin(fellowship, subjectPlayer, false, playerName);
						break;
					case OP:
						playerData.setFellowshipAdmin(fellowship, subjectPlayer, true, playerName);
						break;
					case REMOVE:
						playerData.removePlayerFromFellowship(fellowship, subjectPlayer, playerName);
						break;
					case TRANSFER:
						playerData.transferFellowship(fellowship, subjectPlayer, playerName);
						break;
				}
			}
			return null;
		}
	}
}