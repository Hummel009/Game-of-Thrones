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

public class GOTPacketBrotherhoodToggle extends GOTPacketBrotherhoodDo {
	private ToggleFunction function;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodToggle() {
	}

	public GOTPacketBrotherhoodToggle(GOTBrotherhoodClient fs, ToggleFunction f) {
		super(fs);
		function = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		function = ToggleFunction.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeByte(function.ordinal());
	}

	public enum ToggleFunction {
		PVP, HIRED_FF, MAP_SHOW

	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodToggle, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodToggle packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				if (packet.function == ToggleFunction.PVP) {
					boolean current = brotherhood.getPreventPVP();
					playerData.setBrotherhoodPreventPVP(brotherhood, !current);
				} else if (packet.function == ToggleFunction.HIRED_FF) {
					boolean current = brotherhood.getPreventHiredFriendlyFire();
					playerData.setBrotherhoodPreventHiredFF(brotherhood, !current);
				} else if (packet.function == ToggleFunction.MAP_SHOW) {
					boolean current = brotherhood.getShowMapLocations();
					playerData.setBrotherhoodShowMapLocations(brotherhood, !current);
				}
			}
			return null;
		}
	}
}