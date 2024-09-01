package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTPacketHiredGui implements IMessage {
	private GOTUnitTradeEntry.PledgeType pledgeType;
	private boolean teleportAutomatically;
	private boolean isActive;
	private boolean canMove;
	private boolean openGui;
	private boolean inCombat;
	private boolean guardMode;
	private float reputationRequired;
	private int guardRange;
	private int entityID;
	private int mobKills;
	private int xp;

	@SuppressWarnings("unused")
	public GOTPacketHiredGui() {
	}

	public GOTPacketHiredGui(int i, boolean flag) {
		entityID = i;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		isActive = data.readBoolean();
		canMove = data.readBoolean();
		teleportAutomatically = data.readBoolean();
		mobKills = data.readInt();
		xp = data.readInt();
		reputationRequired = data.readFloat();
		pledgeType = GOTUnitTradeEntry.PledgeType.forID(data.readByte());
		inCombat = data.readBoolean();
		guardMode = data.readBoolean();
		guardRange = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(isActive);
		data.writeBoolean(canMove);
		data.writeBoolean(teleportAutomatically);
		data.writeInt(mobKills);
		data.writeInt(xp);
		data.writeFloat(reputationRequired);
		data.writeByte(pledgeType.getTypeID());
		data.writeBoolean(inCombat);
		data.writeBoolean(guardMode);
		data.writeInt(guardRange);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean isTeleportAutomatically() {
		return teleportAutomatically;
	}

	public void setTeleportAutomatically(boolean teleportAutomatically) {
		this.teleportAutomatically = teleportAutomatically;
	}

	public int getMobKills() {
		return mobKills;
	}

	public void setMobKills(int mobKills) {
		this.mobKills = mobKills;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public float getReputationRequired() {
		return reputationRequired;
	}

	public void setReputationRequired(float reputationRequired) {
		this.reputationRequired = reputationRequired;
	}

	public GOTUnitTradeEntry.PledgeType getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(GOTUnitTradeEntry.PledgeType pledgeType) {
		this.pledgeType = pledgeType;
	}

	public boolean isInCombat() {
		return inCombat;
	}

	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

	public boolean isGuardMode() {
		return guardMode;
	}

	public void setGuardMode(boolean guardMode) {
		this.guardMode = guardMode;
	}

	public int getGuardRange() {
		return guardRange;
	}

	public void setGuardRange(int guardRange) {
		this.guardRange = guardRange;
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredGui, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredGui packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.getHireableInfo().getHiringPlayer() == entityplayer) {
					npc.getHireableInfo().receiveClientPacket(packet);
					if (packet.openGui) {
						GOT.proxy.openHiredNPCGui(npc);
					}
				}
			}
			return null;
		}
	}
}