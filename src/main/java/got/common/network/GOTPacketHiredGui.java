package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTPacketHiredGui implements IMessage {
	public int entityID;
	public boolean openGui;
	public boolean isActive;
	public boolean canMove;
	public boolean teleportAutomatically;
	public int mobKills;
	public int xp;
	public float alignmentRequired;
	public GOTUnitTradeEntry.PledgeType pledgeType;
	public boolean inCombat;
	public boolean guardMode;
	public int guardRange;

	public GOTPacketHiredGui(int i, boolean flag) {
		entityID = i;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		setActive(data.readBoolean());
		setCanMove(data.readBoolean());
		setTeleportAutomatically(data.readBoolean());
		setMobKills(data.readInt());
		setXp(data.readInt());
		setAlignmentRequired(data.readFloat());
		setPledgeType(GOTUnitTradeEntry.PledgeType.forID(data.readByte()));
		setInCombat(data.readBoolean());
		setGuardMode(data.readBoolean());
		setGuardRange(data.readInt());
	}

	public float getAlignmentRequired() {
		return alignmentRequired;
	}

	public int getGuardRange() {
		return guardRange;
	}

	public int getMobKills() {
		return mobKills;
	}

	public GOTUnitTradeEntry.PledgeType getPledgeType() {
		return pledgeType;
	}

	public int getXp() {
		return xp;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public boolean isGuardMode() {
		return guardMode;
	}

	public boolean isInCombat() {
		return inCombat;
	}

	public boolean isTeleportAutomatically() {
		return teleportAutomatically;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setAlignmentRequired(float alignmentRequired) {
		this.alignmentRequired = alignmentRequired;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public void setGuardMode(boolean guardMode) {
		this.guardMode = guardMode;
	}

	public void setGuardRange(int guardRange) {
		this.guardRange = guardRange;
	}

	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}

	public void setMobKills(int mobKills) {
		this.mobKills = mobKills;
	}

	public void setPledgeType(GOTUnitTradeEntry.PledgeType pledgeType) {
		this.pledgeType = pledgeType;
	}

	public void setTeleportAutomatically(boolean teleportAutomatically) {
		this.teleportAutomatically = teleportAutomatically;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(isActive());
		data.writeBoolean(isCanMove());
		data.writeBoolean(isTeleportAutomatically());
		data.writeInt(getMobKills());
		data.writeInt(getXp());
		data.writeFloat(getAlignmentRequired());
		data.writeByte(getPledgeType().typeID);
		data.writeBoolean(isInCombat());
		data.writeBoolean(isGuardMode());
		data.writeInt(getGuardRange());
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredGui, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredGui packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					npc.hiredNPCInfo.receiveClientPacket(packet);
					if (packet.openGui) {
						GOT.getProxy().openHiredNPCGui(npc);
					}
				}
			}
			return null;
		}
	}

}
