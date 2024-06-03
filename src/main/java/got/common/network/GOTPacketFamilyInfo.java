package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketFamilyInfo implements IMessage {
	private int age;
	private boolean isMale;
	private String name;
	private boolean isDrunk;
	private int entityID;

	@SuppressWarnings("unused")
	public GOTPacketFamilyInfo() {
	}

	public GOTPacketFamilyInfo(int id, int a, boolean m, String s, boolean drunk) {
		entityID = id;
		age = a;
		isMale = m;
		name = s;
		isDrunk = drunk;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		age = data.readInt();
		isMale = data.readBoolean();
		short nameLength = data.readShort();
		if (nameLength > -1) {
			name = data.readBytes(nameLength).toString(Charsets.UTF_8);
		}
		isDrunk = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(age);
		data.writeBoolean(isMale);
		if (name == null) {
			data.writeShort(-1);
		} else {
			byte[] nameBytes = name.getBytes(Charsets.UTF_8);
			data.writeShort(nameBytes.length);
			data.writeBytes(nameBytes);
		}
		data.writeBoolean(isDrunk);
	}

	public boolean isDrunk() {
		return isDrunk;
	}

	public String getName() {
		return name;
	}

	public boolean isMale() {
		return isMale;
	}

	public int getAge() {
		return age;
	}

	public static class Handler implements IMessageHandler<GOTPacketFamilyInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFamilyInfo packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).getFamilyInfo().receiveData(packet);
			}
			return null;
		}
	}
}