package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketAchievement implements IMessage {
	public GOTAchievement achievement;
	public boolean display;

	public GOTPacketAchievement() {
	}

	public GOTPacketAchievement(GOTAchievement ach, boolean disp) {
		achievement = ach;
		display = disp;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte catID = data.readByte();
		short achID = data.readShort();
		GOTAchievement.Category cat = GOTAchievement.Category.values()[catID];
		achievement = GOTAchievement.achievementForCategoryAndID(cat, achID);
		display = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(achievement.getCategory().ordinal());
		data.writeShort(achievement.getId());
		data.writeBoolean(display);
	}

	public static class Handler implements IMessageHandler<GOTPacketAchievement, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAchievement packet, MessageContext context) {
			GOTAchievement achievement = packet.achievement;
			if (achievement != null) {
				if (!GOT.proxy.isSingleplayer()) {
					EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
					GOTLevelData.getData(entityplayer).addAchievement(achievement);
				}
				if (packet.display) {
					GOT.proxy.queueAchievement(achievement);
				}
			}
			return null;
		}
	}

}
