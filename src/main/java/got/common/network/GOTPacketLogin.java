package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.EnumDifficulty;

public class GOTPacketLogin implements IMessage {
	public int ringPortalX;
	public int ringPortalY;
	public int ringPortalZ;
	public int ftCooldownMax;
	public int ftCooldownMin;
	public EnumDifficulty difficulty;
	public boolean difficultyLocked;
	public boolean feastMode;
	public boolean fellowshipCreation;
	public boolean enchanting;
	public boolean enchantingGOT;
	public boolean strictFactionTitleRequirements;
	public boolean conquestDecay;
	public int fellowshipMaxSize;

	@Override
	public void fromBytes(ByteBuf data) {
		ringPortalX = data.readInt();
		ringPortalY = data.readInt();
		ringPortalZ = data.readInt();
		ftCooldownMax = data.readInt();
		ftCooldownMin = data.readInt();
		byte diff = data.readByte();
		difficulty = diff >= 0 ? EnumDifficulty.getDifficultyEnum(diff) : null;
		difficultyLocked = data.readBoolean();
		feastMode = data.readBoolean();
		fellowshipCreation = data.readBoolean();
		enchanting = data.readBoolean();
		enchantingGOT = data.readBoolean();
		strictFactionTitleRequirements = data.readBoolean();
		conquestDecay = data.readBoolean();
		fellowshipMaxSize = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(fellowshipMaxSize);
		data.writeInt(ringPortalX);
		data.writeInt(ringPortalY);
		data.writeInt(ringPortalZ);
		data.writeInt(ftCooldownMax);
		data.writeInt(ftCooldownMin);
		int diff = difficulty == null ? -1 : difficulty.getDifficultyId();
		data.writeByte(diff);
		data.writeBoolean(difficultyLocked);
		data.writeBoolean(feastMode);
		data.writeBoolean(fellowshipCreation);
		data.writeBoolean(enchanting);
		data.writeBoolean(enchantingGOT);
		data.writeBoolean(strictFactionTitleRequirements);
		data.writeBoolean(conquestDecay);
	}

	public static class Handler implements IMessageHandler<GOTPacketLogin, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLogin packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				GOTLevelData.destroyAllPlayerData();
			}
			GOTLevelData.gameOfThronesPortalX = packet.ringPortalX;
			GOTLevelData.gameOfThronesPortalY = packet.ringPortalY;
			GOTLevelData.gameOfThronesPortalZ = packet.ringPortalZ;
			GOTLevelData.clientside_thisServer_fellowshipMaxSize = packet.fellowshipMaxSize;
			GOTLevelData.setWaypointCooldown(packet.ftCooldownMax, packet.ftCooldownMin);
			EnumDifficulty diff = packet.difficulty;
			if (diff != null) {
				GOTLevelData.setSavedDifficulty(diff);
				GOT.proxy.setClientDifficulty(diff);
			} else {
				GOTLevelData.setSavedDifficulty(null);
			}
			GOTLevelData.setDifficultyLocked(packet.difficultyLocked);
			GOTLevelData.clientside_thisServer_feastMode = packet.feastMode;
			GOTLevelData.clientside_thisServer_fellowshipCreation = packet.fellowshipCreation;
			GOTLevelData.clientside_thisServer_enchanting = packet.enchanting;
			GOTLevelData.clientside_thisServer_enchantingGOT = packet.enchantingGOT;
			GOTLevelData.clientside_thisServer_strictFactionTitleRequirements = packet.strictFactionTitleRequirements;
			return null;
		}
	}

}
