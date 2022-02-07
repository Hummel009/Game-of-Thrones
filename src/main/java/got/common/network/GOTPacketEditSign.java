package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.tileentity.GOTTileEntitySign;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;

public class GOTPacketEditSign implements IMessage {
	public int posX;
	public int posY;
	public int posZ;
	public String[] signText;

	public GOTPacketEditSign(GOTTileEntitySign sign) {
		posX = sign.xCoord;
		posY = sign.yCoord;
		posZ = sign.zCoord;
		signText = sign.getSignText();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
		byte lines = data.readByte();
		signText = new String[lines];
		for (int i = 0; i < signText.length; ++i) {
			short length = data.readShort();
			signText[i] = length > -1 ? data.readBytes(length).toString(Charsets.UTF_8) : "";
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
		data.writeByte(signText.length);
		for (String line : signText) {
			if (line == null) {
				data.writeShort(-1);
				continue;
			}
			byte[] lineBytes = line.getBytes(Charsets.UTF_8);
			data.writeShort(lineBytes.length);
			data.writeBytes(lineBytes);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketEditSign, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEditSign packet, MessageContext context) {
			TileEntity te;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			entityplayer.func_143004_u();
			World world = entityplayer.worldObj;
			int i = packet.posX;
			int j = packet.posY;
			int k = packet.posZ;
			String[] newText = packet.signText;
			if (world.blockExists(i, j, k) && (te = world.getTileEntity(i, j, k)) instanceof GOTTileEntitySign) {
				GOTTileEntitySign sign = (GOTTileEntitySign) te;
				if (!sign.isEditable() || sign.getEditingPlayer() != entityplayer) {
					MinecraftServer.getServer().logWarning("Player " + entityplayer.getCommandSenderName() + " just tried to change non-editable GOT sign");
					return null;
				}
				for (int l = 0; l < sign.getNumLines(); ++l) {
					String line = newText[l];
					boolean valid = true;
					if (line.length() > 15) {
						valid = false;
					} else {
						for (int c = 0; c < line.length(); ++c) {
							if (ChatAllowedCharacters.isAllowedCharacter(line.charAt(c))) {
								continue;
							}
							valid = false;
						}
					}
					if (valid) {
						continue;
					}
					newText[l] = "!?";
				}
				System.arraycopy(newText, 0, sign.getSignText(), 0, sign.getNumLines());
				sign.markDirty();
				world.markBlockForUpdate(i, j, k);
			}
			return null;
		}
	}

}
