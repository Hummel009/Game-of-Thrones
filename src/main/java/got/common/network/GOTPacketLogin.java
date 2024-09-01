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
	private boolean reputationZones;
	private boolean feastMode;
	private boolean brotherhoodCreation;
	private boolean enchanting;
	private boolean enchantingGOT;
	private boolean strictFactionTitleRequirements;
	private boolean conquestDecay;
	private int swordPortalX;
	private int swordPortalY;
	private int swordPortalZ;
	private int ftCooldownMax;
	private int ftCooldownMin;
	private int brotherhoodMaxSize;
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
		reputationZones = data.readBoolean();
		feastMode = data.readBoolean();
		brotherhoodCreation = data.readBoolean();
		brotherhoodMaxSize = data.readInt();
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
		data.writeBoolean(reputationZones);
		data.writeBoolean(feastMode);
		data.writeBoolean(brotherhoodCreation);
		data.writeInt(brotherhoodMaxSize);
		data.writeBoolean(enchanting);
		data.writeBoolean(enchantingGOT);
		data.writeBoolean(strictFactionTitleRequirements);
		data.writeBoolean(conquestDecay);
		data.writeInt(customWaypointMinY);
	}

	public void setSwordPortalX(int swordPortalX) {
		this.swordPortalX = swordPortalX;
	}

	public void setSwordPortalY(int swordPortalY) {
		this.swordPortalY = swordPortalY;
	}

	public void setSwordPortalZ(int swordPortalZ) {
		this.swordPortalZ = swordPortalZ;
	}

	public void setFtCooldownMax(int ftCooldownMax) {
		this.ftCooldownMax = ftCooldownMax;
	}

	public void setFtCooldownMin(int ftCooldownMin) {
		this.ftCooldownMin = ftCooldownMin;
	}

	public void setDifficulty(EnumDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setDifficultyLocked(boolean difficultyLocked) {
		this.difficultyLocked = difficultyLocked;
	}

	public void setReputationZones(boolean reputationZones) {
		this.reputationZones = reputationZones;
	}

	public void setFeastMode(boolean feastMode) {
		this.feastMode = feastMode;
	}

	public void setBrotherhoodCreation(boolean brotherhoodCreation) {
		this.brotherhoodCreation = brotherhoodCreation;
	}

	public void setBrotherhoodMaxSize(int brotherhoodMaxSize) {
		this.brotherhoodMaxSize = brotherhoodMaxSize;
	}

	public void setEnchanting(boolean enchanting) {
		this.enchanting = enchanting;
	}

	public void setEnchantingGOT(boolean enchantingGOT) {
		this.enchantingGOT = enchantingGOT;
	}

	public void setStrictFactionTitleRequirements(boolean strictFactionTitleRequirements) {
		this.strictFactionTitleRequirements = strictFactionTitleRequirements;
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
			GOTLevelData.setEnableReputationZones(packet.reputationZones);
			GOTLevelData.setClientSideThisServerFeastMode(packet.feastMode);
			GOTLevelData.setClientSideThisServerBrotherhoodCreation(packet.brotherhoodCreation);
			GOTLevelData.setClientSideThisServerBrotherhoodMaxSize(packet.brotherhoodMaxSize);
			GOTLevelData.setClientSideThisServerEnchanting(packet.enchanting);
			GOTLevelData.setClientSideThisServerEnchantingGOT(packet.enchantingGOT);
			GOTLevelData.setClientSideThisServerStrictFactionTitleRequirements(packet.strictFactionTitleRequirements);
			GOTLevelData.setClientSideThisServerCustomWaypointMinY(packet.customWaypointMinY);
			return null;
		}
	}
}