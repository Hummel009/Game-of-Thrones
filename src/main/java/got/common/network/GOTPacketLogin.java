package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.EnumDifficulty;

public class GOTPacketLogin implements IMessage {
	public int swordPortalX;
	public int swordPortalY;
	public int swordPortalZ;
	public int ftCooldownMax;
	public int ftCooldownMin;
	public EnumDifficulty difficulty;
	public boolean difficultyLocked;
	public boolean alignmentZones;
	public boolean feastMode;
	public boolean fellowshipCreation;
	public int fellowshipMaxSize;
	public boolean enchanting;
	public boolean enchantingGOT;
	public boolean strictFactionTitleRequirements;
	public boolean conquestDecay;
	public int customWaypointMinY;

	@Override
	public void fromBytes(ByteBuf data) {
		swordPortalX = data.readInt();
		swordPortalY = data.readInt();
		swordPortalZ = data.readInt();
		ftCooldownMax = data.readInt();
		ftCooldownMin = data.readInt();
		byte diff = data.readByte();
		difficulty = diff >= 0 ? EnumDifficulty.getDifficultyEnum(diff) : null;
		difficultyLocked = data.readBoolean();
		alignmentZones = data.readBoolean();
		feastMode = data.readBoolean();
		fellowshipCreation = data.readBoolean();
		fellowshipMaxSize = data.readInt();
		enchanting = data.readBoolean();
		enchantingGOT = data.readBoolean();
		strictFactionTitleRequirements = data.readBoolean();
		conquestDecay = data.readBoolean();
		customWaypointMinY = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(swordPortalX);
		data.writeInt(swordPortalY);
		data.writeInt(swordPortalZ);
		data.writeInt(ftCooldownMax);
		data.writeInt(ftCooldownMin);
		int diff = difficulty == null ? -1 : difficulty.getDifficultyId();
		data.writeByte(diff);
		data.writeBoolean(difficultyLocked);
		data.writeBoolean(alignmentZones);
		data.writeBoolean(feastMode);
		data.writeBoolean(fellowshipCreation);
		data.writeInt(fellowshipMaxSize);
		data.writeBoolean(enchanting);
		data.writeBoolean(enchantingGOT);
		data.writeBoolean(strictFactionTitleRequirements);
		data.writeBoolean(conquestDecay);
		data.writeInt(customWaypointMinY);
	}

	public static class Handler implements IMessageHandler<GOTPacketLogin, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLogin packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				GOTLevelData.destroyAllPlayerData();
			}
			GOTLevelData.gameOfThronesPortalX = packet.swordPortalX;
			GOTLevelData.gameOfThronesPortalY = packet.swordPortalY;
			GOTLevelData.gameOfThronesPortalZ = packet.swordPortalZ;
			GOTLevelData.setWaypointCooldown(packet.ftCooldownMax, packet.ftCooldownMin);
			EnumDifficulty diff = packet.difficulty;
			if (diff != null) {
				GOTLevelData.setSavedDifficulty(diff);
				GOT.proxy.setClientDifficulty(diff);
			} else {
				GOTLevelData.setSavedDifficulty(null);
			}
			GOTLevelData.setDifficultyLocked(packet.difficultyLocked);
			GOTLevelData.setEnableAlignmentZones(packet.alignmentZones);
			GOTLevelData.clientside_thisServer_feastMode = packet.feastMode;
			GOTLevelData.clientside_thisServer_fellowshipCreation = packet.fellowshipCreation;
			GOTLevelData.clientside_thisServer_fellowshipMaxSize = packet.fellowshipMaxSize;
			GOTLevelData.clientside_thisServer_enchanting = packet.enchanting;
			GOTLevelData.clientside_thisServer_enchantingGOT = packet.enchantingGOT;
			GOTLevelData.clientside_thisServer_strictFactionTitleRequirements = packet.strictFactionTitleRequirements;
			GOTLevelData.clientside_thisServer_customWaypointMinY = packet.customWaypointMinY;
			return null;
		}
	}

}
