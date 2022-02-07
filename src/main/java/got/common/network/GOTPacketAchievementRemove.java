package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketAchievementRemove implements IMessage {
	public GOTAchievement achievement;

	public GOTPacketAchievementRemove() {
	}

	public GOTPacketAchievementRemove(GOTAchievement ach) {
		achievement = ach;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte catID = data.readByte();
		short achID = data.readShort();
		GOTAchievement.Category cat = GOTAchievement.Category.values()[catID];
		achievement = GOTAchievement.achievementForCategoryAndID(cat, achID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(achievement.getCategory().ordinal());
		data.writeShort(achievement.getID());
	}

	public static class Handler implements IMessageHandler<GOTPacketAchievementRemove, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAchievementRemove packet, MessageContext context) {
			GOTAchievement achievement = packet.achievement;
			if (achievement != null && !GOT.getProxy().isSingleplayer()) {
				EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
				GOTLevelData.getData(entityplayer).removeAchievement(achievement);
			}
			return null;
		}
	}

}
