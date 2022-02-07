package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTConfig;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTPacketNPCSpeech implements IMessage {
	public int entityID;
	public String speech;
	public boolean forceChatMsg;

	public GOTPacketNPCSpeech() {
	}

	public GOTPacketNPCSpeech(int id, String s, boolean forceChat) {
		entityID = id;
		speech = s;
		forceChatMsg = forceChat;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		int length = data.readInt();
		speech = data.readBytes(length).toString(Charsets.UTF_8);
		forceChatMsg = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		byte[] speechBytes = speech.getBytes(Charsets.UTF_8);
		data.writeInt(speechBytes.length);
		data.writeBytes(speechBytes);
		data.writeBoolean(forceChatMsg);
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCSpeech, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCSpeech packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (GOTConfig.immersiveSpeech) {
					GOT.getProxy().clientReceiveSpeech(npc, packet.speech);
				}
				if (!GOTConfig.immersiveSpeech || GOTConfig.immersiveSpeechChatLog || packet.forceChatMsg) {
					String name = npc.getCommandSenderName();
					String message = EnumChatFormatting.YELLOW + "<" + name + "> " + EnumChatFormatting.WHITE + packet.speech;
					entityplayer.addChatMessage(new ChatComponentText(message));
				}
			}
			return null;
		}
	}

}
