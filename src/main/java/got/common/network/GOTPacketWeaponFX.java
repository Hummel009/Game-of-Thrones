package got.common.network;

import java.util.Random;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTPacketWeaponFX implements IMessage {
	public Type type;
	public int entityID;

	public GOTPacketWeaponFX() {
	}

	public GOTPacketWeaponFX(Type t, Entity entity) {
		type = t;
		entityID = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = Type.values()[typeID];
		entityID = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
		data.writeInt(entityID);
	}

	public static class Handler implements IMessageHandler<GOTPacketWeaponFX, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketWeaponFX packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity != null) {
				double x = entity.posX;
				double y = entity.boundingBox.minY;
				double z = entity.posZ;
				Random rand = world.rand;
				switch (packet.type) {
				case ASSHAI:
					for (int i = 0; i < 360; i += 2) {
						float angle = (float) Math.toRadians(i);
						double dist = 1.5D;
						double d = dist * MathHelper.sin(angle);
						double d1 = dist * MathHelper.cos(angle);
						world.spawnParticle("smoke", x + d, y + 0.1D, z + d1, d * 0.2D, 0.0D, d1 * 0.2D);
					}
					break;
				case CHILLING:
					for (int i = 0; i < 40; i++) {
						double d1 = y + entity.height * 0.7F;
						float angleXZ = rand.nextFloat() * 3.1415927F * 2.0F;
						float angleY = rand.nextFloat() * 3.1415927F * 2.0F;
						float speed = MathHelper.randomFloatClamp(rand, 0.1F, 0.2F);
						double d3 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
						double d4 = MathHelper.sin(angleY) * speed;
						double d5 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
						d3 += entity.posX - entity.lastTickPosX;
						d4 += entity.posY - entity.lastTickPosY;
						d5 += entity.posZ - entity.lastTickPosZ;
						GOT.proxy.spawnParticle("chill", x, d1, z, d3, d4, d5);
					}

					break;
				case INFERNAL:
					for (int i = 0; i < 20; i++) {
						double d1 = y + entity.height * 0.7F;
						float angleXZ = rand.nextFloat() * 3.1415927F * 2.0F;
						float angleY = rand.nextFloat() * 3.1415927F * 2.0F;
						float speed = MathHelper.randomFloatClamp(rand, 0.1F, 0.15F);
						double d3 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
						double d4 = MathHelper.sin(angleY) * speed;
						double d5 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
						d4 += 0.15000000596046448D;
						d3 += entity.posX - entity.lastTickPosX;
						d4 += entity.posY - entity.lastTickPosY;
						d5 += entity.posZ - entity.lastTickPosZ;
						world.spawnParticle("flame", x, d1, z, d3, d4, d5);
					}
					break;
				}
			}
			return null;
		}
	}

	public enum Type {
		ASSHAI, INFERNAL, CHILLING
	}

}
