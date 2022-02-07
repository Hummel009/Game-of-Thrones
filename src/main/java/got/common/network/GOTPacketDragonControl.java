package got.common.network;

import java.util.BitSet;

import org.apache.logging.log4j.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.Side;
import got.common.entity.dragon.GOTEntityDragon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketDragonControl implements IMessage {
	private static Logger L = LogManager.getLogger();
	private BitSet bits;
	private int previous;

	public GOTPacketDragonControl() {
		bits = new BitSet(Byte.SIZE);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		fromInteger(buf.readUnsignedByte());
	}

	private void fromInteger(int value) {
		int index = 0;
		while (value != 0) {
			if (value % 2 != 0) {
				bits.set(index);
			}
			index++;
			value >>>= 1;
		}
	}

	public BitSet getFlags() {
		return bits;
	}

	public boolean hasChanged() {
		int current = toInteger();
		boolean changed = previous != current;
		previous = current;
		return changed;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(toInteger());
	}

	private int toInteger() {
		int value = 0;
		for (int i = 0; i < bits.length(); i++) {
			value += bits.get(i) ? 1 << i : 0;
		}
		return value;
	}

	public static class Handler implements IMessageHandler<GOTPacketDragonControl, IMessage> {

		@Override
		public IMessage onMessage(GOTPacketDragonControl message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) {
				L.warn("Recieved unexpected control message from server!");
				return null;
			}

			EntityPlayerMP player = ctx.getServerHandler().playerEntity;

			if (player.ridingEntity instanceof GOTEntityDragon) {
				GOTEntityDragon dragon = (GOTEntityDragon) player.ridingEntity;
				dragon.setControlFlags(message.getFlags());
			}

			return null;
		}
	}
}
