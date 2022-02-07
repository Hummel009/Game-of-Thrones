package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketFamilyInfo implements IMessage {
	public int entityID;
	public int age;
	public boolean isMale;
	public String name;
	public boolean isDrunk;

	public GOTPacketFamilyInfo(int id, int a, boolean m, String s, boolean drunk) {
		entityID = id;
		setAge(a);
		setMale(m);
		setName(s);
		setDrunk(drunk);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		setAge(data.readInt());
		setMale(data.readBoolean());
		short nameLength = data.readShort();
		if (nameLength > -1) {
			setName(data.readBytes(nameLength).toString(Charsets.UTF_8));
		}
		setDrunk(data.readBoolean());
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public boolean isDrunk() {
		return isDrunk;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setDrunk(boolean isDrunk) {
		this.isDrunk = isDrunk;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(getAge());
		data.writeBoolean(isMale());
		if (getName() == null) {
			data.writeShort(-1);
		} else {
			byte[] nameBytes = getName().getBytes(Charsets.UTF_8);
			data.writeShort(nameBytes.length);
			data.writeBytes(nameBytes);
		}
		data.writeBoolean(isDrunk());
	}

	public static class Handler implements IMessageHandler<GOTPacketFamilyInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFamilyInfo packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).familyInfo.receiveData(packet);
			}
			return null;
		}
	}

}
