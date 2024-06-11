package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.inventory.GOTContainerBarrel;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketBrewingButton implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketBrewingButton, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrewingButton packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerBarrel) {
				((GOTContainerBarrel) container).getTheBarrel().handleBrewingButtonPress();
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useBarrel);
			}
			return null;
		}
	}
}