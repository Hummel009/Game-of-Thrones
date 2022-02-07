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
	public boolean alignmentZones;
	public boolean feastMode;
	public boolean fellowshipCreation;
	public boolean enchanting;
	public boolean enchantingGOT;
	public boolean strictFactionTitleRequirements;
	public boolean conquestDecay;

	@Override
	public void fromBytes(ByteBuf data) {
		setRingPortalX(data.readInt());
		setRingPortalY(data.readInt());
		setRingPortalZ(data.readInt());
		setFtCooldownMax(data.readInt());
		setFtCooldownMin(data.readInt());
		byte diff = data.readByte();
		setDifficulty(diff >= 0 ? EnumDifficulty.getDifficultyEnum(diff) : null);
		setDifficultyLocked(data.readBoolean());
		setAlignmentZones(data.readBoolean());
		setFeastMode(data.readBoolean());
		fellowshipCreation = data.readBoolean();
		setEnchanting(data.readBoolean());
		setEnchantingGOT(data.readBoolean());
		strictFactionTitleRequirements = data.readBoolean();
		conquestDecay = data.readBoolean();
	}

	public EnumDifficulty getDifficulty() {
		return difficulty;
	}

	public int getFtCooldownMax() {
		return ftCooldownMax;
	}

	public int getFtCooldownMin() {
		return ftCooldownMin;
	}

	public int getRingPortalX() {
		return ringPortalX;
	}

	public int getRingPortalY() {
		return ringPortalY;
	}

	public int getRingPortalZ() {
		return ringPortalZ;
	}

	public boolean isAlignmentZones() {
		return alignmentZones;
	}

	public boolean isDifficultyLocked() {
		return difficultyLocked;
	}

	public boolean isEnchanting() {
		return enchanting;
	}

	public boolean isEnchantingGOT() {
		return enchantingGOT;
	}

	public boolean isFeastMode() {
		return feastMode;
	}

	public void setAlignmentZones(boolean alignmentZones) {
		this.alignmentZones = alignmentZones;
	}

	public void setDifficulty(EnumDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setDifficultyLocked(boolean difficultyLocked) {
		this.difficultyLocked = difficultyLocked;
	}

	public void setEnchanting(boolean enchanting) {
		this.enchanting = enchanting;
	}

	public void setEnchantingGOT(boolean enchantingGOT) {
		this.enchantingGOT = enchantingGOT;
	}

	public void setFeastMode(boolean feastMode) {
		this.feastMode = feastMode;
	}

	public void setFtCooldownMax(int ftCooldownMax) {
		this.ftCooldownMax = ftCooldownMax;
	}

	public void setFtCooldownMin(int ftCooldownMin) {
		this.ftCooldownMin = ftCooldownMin;
	}

	public void setRingPortalX(int ringPortalX) {
		this.ringPortalX = ringPortalX;
	}

	public void setRingPortalY(int ringPortalY) {
		this.ringPortalY = ringPortalY;
	}

	public void setRingPortalZ(int ringPortalZ) {
		this.ringPortalZ = ringPortalZ;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(getRingPortalX());
		data.writeInt(getRingPortalY());
		data.writeInt(getRingPortalZ());
		data.writeInt(getFtCooldownMax());
		data.writeInt(getFtCooldownMin());
		int diff = getDifficulty() == null ? -1 : getDifficulty().getDifficultyId();
		data.writeByte(diff);
		data.writeBoolean(isDifficultyLocked());
		data.writeBoolean(isAlignmentZones());
		data.writeBoolean(isFeastMode());
		data.writeBoolean(fellowshipCreation);
		data.writeBoolean(isEnchanting());
		data.writeBoolean(isEnchantingGOT());
		data.writeBoolean(strictFactionTitleRequirements);
		data.writeBoolean(conquestDecay);
	}

	public static class Handler implements IMessageHandler<GOTPacketLogin, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLogin packet, MessageContext context) {
			if (!GOT.getProxy().isSingleplayer()) {
				GOTLevelData.destroyAllPlayerData();
			}
			GOTLevelData.setGameOfThronesPortalX(packet.getRingPortalX());
			GOTLevelData.setGameOfThronesPortalY(packet.getRingPortalY());
			GOTLevelData.setGameOfThronesPortalZ(packet.getRingPortalZ());
			GOTLevelData.setWaypointCooldown(packet.getFtCooldownMax(), packet.getFtCooldownMin());
			EnumDifficulty diff = packet.getDifficulty();
			if (diff != null) {
				GOTLevelData.setSavedDifficulty(diff);
				GOT.getProxy().setClientDifficulty(diff);
			} else {
				GOTLevelData.setSavedDifficulty(null);
			}
			GOTLevelData.setDifficultyLocked(packet.isDifficultyLocked());
			GOTLevelData.setEnableAlignmentZones(packet.isAlignmentZones());
			GOTLevelData.setClientsideThisServerFeastMode(packet.isFeastMode());
			GOTLevelData.setClientsideThisServerFellowshipCreation(packet.fellowshipCreation);
			GOTLevelData.setClientsideThisServerEnchanting(packet.isEnchanting());
			GOTLevelData.setClientsideThisServerEnchantingGOT(packet.isEnchantingGOT());
			GOTLevelData.setClientsideThisServerStrictFactionTitleRequirements(packet.strictFactionTitleRequirements);
			return null;
		}
	}

}
