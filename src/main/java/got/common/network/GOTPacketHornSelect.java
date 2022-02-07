package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.database.GOTRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GOTPacketHornSelect implements IMessage {
	private int hornType;

	public GOTPacketHornSelect(int h) {
		hornType = h;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		hornType = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(hornType);
	}

	public static class Handler implements IMessageHandler<GOTPacketHornSelect, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHornSelect packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if (itemstack != null && itemstack.getItem() == GOTRegistry.commandHorn && itemstack.getItemDamage() == 0) {
				itemstack.setItemDamage(packet.hornType);
			}
			return null;
		}
	}

}
