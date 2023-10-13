package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class GOTPacketMapTp implements IMessage {
	public int xCoord;
	public int zCoord;

	public GOTPacketMapTp() {
	}

	public GOTPacketMapTp(int x, int z) {
		xCoord = x;
		zCoord = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		xCoord = data.readInt();
		zCoord = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(xCoord);
		data.writeInt(zCoord);
	}

	public static class Handler implements IMessageHandler<GOTPacketMapTp, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMapTp packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
			if (isOp) {
				int i = packet.xCoord;
				int k = packet.zCoord;
				int j = world.getTopSolidOrLiquidBlock(i, k);
				String[] args = { Integer.toString(i), Integer.toString(j), Integer.toString(k) };
				new CommandTeleport().processCommand(entityplayer, args);
			}
			return null;
		}
	}

}
