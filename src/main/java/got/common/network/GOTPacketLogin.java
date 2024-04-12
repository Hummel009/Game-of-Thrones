package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.EnumDifficulty;

public class GOTPacketLogin implements IMessage {
	private EnumDifficulty difficulty;
	private boolean difficultyLocked;
	private boolean alignmentZones;
	private boolean feastMode;
	private boolean fellowshipCreation;
	private boolean enchanting;
	private boolean enchantingGOT;
	private boolean strictFactionTitleRequirements;
	private boolean conquestDecay;
	private int swordPortalX;
	private int swordPortalY;
	private int swordPortalZ;
	private int ftCooldownMax;
	private int ftCooldownMin;
	private int fellowshipMaxSize;
	private int customWaypointMinY;

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

	@SuppressWarnings("unused")
	public int getSwordPortalX() {
		return swordPortalX;
	}

	public void setSwordPortalX(int swordPortalX) {
		this.swordPortalX = swordPortalX;
	}

	@SuppressWarnings("unused")
	public int getSwordPortalY() {
		return swordPortalY;
	}

	public void setSwordPortalY(int swordPortalY) {
		this.swordPortalY = swordPortalY;
	}

	@SuppressWarnings("unused")
	public int getSwordPortalZ() {
		return swordPortalZ;
	}

	public void setSwordPortalZ(int swordPortalZ) {
		this.swordPortalZ = swordPortalZ;
	}

	@SuppressWarnings("unused")
	public int getFtCooldownMax() {
		return ftCooldownMax;
	}

	public void setFtCooldownMax(int ftCooldownMax) {
		this.ftCooldownMax = ftCooldownMax;
	}

	@SuppressWarnings("unused")
	public int getFtCooldownMin() {
		return ftCooldownMin;
	}

	public void setFtCooldownMin(int ftCooldownMin) {
		this.ftCooldownMin = ftCooldownMin;
	}

	@SuppressWarnings("unused")
	public EnumDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(EnumDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	@SuppressWarnings("unused")
	public boolean isDifficultyLocked() {
		return difficultyLocked;
	}

	public void setDifficultyLocked(boolean difficultyLocked) {
		this.difficultyLocked = difficultyLocked;
	}

	@SuppressWarnings("unused")
	public boolean isAlignmentZones() {
		return alignmentZones;
	}

	public void setAlignmentZones(boolean alignmentZones) {
		this.alignmentZones = alignmentZones;
	}

	@SuppressWarnings("unused")
	public boolean isFeastMode() {
		return feastMode;
	}

	public void setFeastMode(boolean feastMode) {
		this.feastMode = feastMode;
	}

	@SuppressWarnings("unused")
	public boolean isFellowshipCreation() {
		return fellowshipCreation;
	}

	public void setFellowshipCreation(boolean fellowshipCreation) {
		this.fellowshipCreation = fellowshipCreation;
	}

	@SuppressWarnings("unused")
	public int getFellowshipMaxSize() {
		return fellowshipMaxSize;
	}

	public void setFellowshipMaxSize(int fellowshipMaxSize) {
		this.fellowshipMaxSize = fellowshipMaxSize;
	}

	@SuppressWarnings("unused")
	public boolean isEnchanting() {
		return enchanting;
	}

	public void setEnchanting(boolean enchanting) {
		this.enchanting = enchanting;
	}

	@SuppressWarnings("unused")
	public boolean isEnchantingGOT() {
		return enchantingGOT;
	}

	public void setEnchantingGOT(boolean enchantingGOT) {
		this.enchantingGOT = enchantingGOT;
	}

	@SuppressWarnings("unused")
	public boolean isStrictFactionTitleRequirements() {
		return strictFactionTitleRequirements;
	}

	public void setStrictFactionTitleRequirements(boolean strictFactionTitleRequirements) {
		this.strictFactionTitleRequirements = strictFactionTitleRequirements;
	}

	@SuppressWarnings("unused")
	public int getCustomWaypointMinY() {
		return customWaypointMinY;
	}

	public void setCustomWaypointMinY(int customWaypointMinY) {
		this.customWaypointMinY = customWaypointMinY;
	}

	public static class Handler implements IMessageHandler<GOTPacketLogin, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLogin packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				GOTLevelData.destroyAllPlayerData();
			}
			GOTLevelData.setGameOfThronesPortalX(packet.swordPortalX);
			GOTLevelData.setGameOfThronesPortalY(packet.swordPortalY);
			GOTLevelData.setGameOfThronesPortalZ(packet.swordPortalZ);
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
			GOTLevelData.setClientSideThisServerFeastMode(packet.feastMode);
			GOTLevelData.setClientSideThisServerFellowshipCreation(packet.fellowshipCreation);
			GOTLevelData.setClientSideThisServerFellowshipMaxSize(packet.fellowshipMaxSize);
			GOTLevelData.setClientSideThisServerEnchanting(packet.enchanting);
			GOTLevelData.setClientSideThisServerEnchantingGOT(packet.enchantingGOT);
			GOTLevelData.setClientSideThisServerStrictFactionTitleRequirements(packet.strictFactionTitleRequirements);
			GOTLevelData.setClientSideThisServerCustomWaypointMinY(packet.customWaypointMinY);
			return null;
		}
	}
}