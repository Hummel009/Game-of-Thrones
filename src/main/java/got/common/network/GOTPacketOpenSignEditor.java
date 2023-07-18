package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.tileentity.GOTTileEntitySign;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTPacketOpenSignEditor implements IMessage {
	public int posX;
	public int posY;
	public int posZ;
	public Block blockType;
	public int blockMeta;

	public GOTPacketOpenSignEditor() {
	}

	public GOTPacketOpenSignEditor(GOTTileEntitySign sign) {
		posX = sign.xCoord;
		posY = sign.yCoord;
		posZ = sign.zCoord;
		blockType = sign.getBlockType();
		blockMeta = sign.getBlockMetadata();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
		blockType = Block.getBlockById(data.readShort());
		blockMeta = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
		data.writeShort(Block.getIdFromBlock(blockType));
		data.writeByte(blockMeta);
	}

	public static class Handler implements IMessageHandler<GOTPacketOpenSignEditor, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketOpenSignEditor packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			World world = GOT.proxy.getClientWorld();
			world.setBlock(packet.posX, packet.posY, packet.posZ, packet.blockType, packet.blockMeta, 3);
			entityplayer.openGui(GOT.instance, 47, world, packet.posX, packet.posY, packet.posZ);
			return null;
		}
	}

}
