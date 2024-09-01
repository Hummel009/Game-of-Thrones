package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class GOTPacketBrotherhoodDoPlayer extends GOTPacketBrotherhoodDo {
	private UUID subjectUuid;
	private PlayerFunction function;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodDoPlayer() {
	}

	public GOTPacketBrotherhoodDoPlayer(GOTBrotherhoodClient fs, UUID subject, PlayerFunction f) {
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

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodDoPlayer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodDoPlayer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			String playerName = entityplayer.getCommandSenderName();
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			UUID subjectPlayer = packet.subjectUuid;
			if (brotherhood != null && subjectPlayer != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				switch (packet.function) {
					case DEOP:
						playerData.setBrotherhoodAdmin(brotherhood, subjectPlayer, false, playerName);
						break;
					case OP:
						playerData.setBrotherhoodAdmin(brotherhood, subjectPlayer, true, playerName);
						break;
					case REMOVE:
						playerData.removePlayerFromBrotherhood(brotherhood, subjectPlayer, playerName);
						break;
					case TRANSFER:
						playerData.transferBrotherhood(brotherhood, subjectPlayer, playerName);
						break;
				}
			}
			return null;
		}
	}
}