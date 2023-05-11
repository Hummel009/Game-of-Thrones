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
import net.minecraft.item.ItemStack;

public class GOTPacketFellowshipSetIcon extends GOTPacketFellowshipDo {
	public GOTPacketFellowshipSetIcon() {
	}

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
			GOTFellowship fellowship = packet.getActiveFellowship();
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
