package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipToggle extends GOTPacketFellowshipDo {
	private ToggleFunction function;

	public GOTPacketFellowshipToggle() {
	}

	public GOTPacketFellowshipToggle(GOTFellowshipClient fs, ToggleFunction f) {
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

	public static class Handler implements IMessageHandler<GOTPacketFellowshipToggle, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipToggle packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getFellowship();
			if (fellowship != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				boolean current;
				switch (packet.function) {
				case PVP:
					current = fellowship.getPreventPVP();
					playerData.setFellowshipPreventPVP(fellowship, !current);
					break;
				case HIRED_FF:
					current = fellowship.getPreventHiredFriendlyFire();
					playerData.setFellowshipPreventHiredFF(fellowship, !current);
					break;
				case MAP_SHOW:
					current = fellowship.getShowMapLocations();
					playerData.setFellowshipShowMapLocations(fellowship, !current);
					break;
				}
			}
			return null;
		}
	}

	public enum ToggleFunction {
		PVP, HIRED_FF, MAP_SHOW;

	}

}
