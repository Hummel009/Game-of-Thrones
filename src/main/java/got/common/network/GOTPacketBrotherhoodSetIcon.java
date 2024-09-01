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
import net.minecraft.item.ItemStack;

public class GOTPacketBrotherhoodSetIcon extends GOTPacketBrotherhoodDo {
	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodSetIcon() {
	}

	public GOTPacketBrotherhoodSetIcon(GOTBrotherhoodClient fs) {
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

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodSetIcon, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodSetIcon packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				ItemStack itemstack = entityplayer.getHeldItem();
				if (itemstack != null) {
					ItemStack newStack = itemstack.copy();
					newStack.stackSize = 1;
					playerData.setBrotherhoodIcon(brotherhood, newStack);
				} else {
					playerData.setBrotherhoodIcon(brotherhood, null);
				}
			}
			return null;
		}
	}
}