package got.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class GOTPacketHiredInfo implements IMessage {
	private int entityID;
	private boolean isHired;
	private UUID hiringPlayer;
	private GOTHiredNPCInfo.Task task;
	private String squadron;
	private int xpLvl;

	public GOTPacketHiredInfo(int i, UUID player, GOTHiredNPCInfo.Task t, String sq, int lvl) {
		entityID = i;
		setHiringPlayer(player);
		isHired = getHiringPlayer() != null;
		setTask(t);
		setSquadron(sq);
		setXpLvl(lvl);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		isHired = data.readBoolean();
		setHiringPlayer(isHired ? new UUID(data.readLong(), data.readLong()) : null);
		setTask(GOTHiredNPCInfo.Task.forID(data.readByte()));
		short sqLength = data.readShort();
		if (sqLength > -1) {
			setSquadron(data.readBytes(sqLength).toString(Charsets.UTF_8));
		}
		setXpLvl(data.readShort());
	}

	public UUID getHiringPlayer() {
		return hiringPlayer;
	}

	public String getSquadron() {
		return squadron;
	}

	public GOTHiredNPCInfo.Task getTask() {
		return task;
	}

	public int getXpLvl() {
		return xpLvl;
	}

	public void setHiringPlayer(UUID hiringPlayer) {
		this.hiringPlayer = hiringPlayer;
	}

	public void setSquadron(String squadron) {
		this.squadron = squadron;
	}

	public void setTask(GOTHiredNPCInfo.Task task) {
		this.task = task;
	}

	public void setXpLvl(int xpLvl) {
		this.xpLvl = xpLvl;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(isHired);
		if (isHired) {
			data.writeLong(getHiringPlayer().getMostSignificantBits());
			data.writeLong(getHiringPlayer().getLeastSignificantBits());
		}
		data.writeByte(getTask().ordinal());
		if (StringUtils.isNullOrEmpty(getSquadron())) {
			data.writeShort(-1);
		} else {
			byte[] sqBytes = getSquadron().getBytes(Charsets.UTF_8);
			data.writeShort(sqBytes.length);
			data.writeBytes(sqBytes);
		}
		data.writeShort(getXpLvl());
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredInfo packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).hiredNPCInfo.receiveBasicData(packet);
			}
			return null;
		}
	}

}
