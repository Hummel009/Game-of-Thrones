package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GOTPacketFellowshipSetIcon extends GOTPacketFellowshipDo {
	public GOTPacketFellowshipSetIcon(GOTFellowshipClient fs) {
		super(fs);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipSetIcon, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipSetIcon packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getFellowship();
			if (fellowship != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				ItemStack itemstack = entityplayer.getHeldItem();
				if (itemstack != null) {
					ItemStack newStack = itemstack.copy();
					newStack.stackSize = 1;
					playerData.setFellowshipIcon(fellowship, newStack);
				} else {
					playerData.setFellowshipIcon(fellowship, null);
				}
			}
			return null;
		}
	}

}
