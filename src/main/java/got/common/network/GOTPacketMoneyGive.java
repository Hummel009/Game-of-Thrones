package got.common.network;

import java.io.IOException;
import java.util.UUID;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;

public class GOTPacketMoneyGive extends GOTPacketMoney implements IMessage {
	public UUID hummel = UUID.fromString("9aee5b32-8e19-4d4b-a2d6-1318af62733d");
	public ItemStack item;

	public GOTPacketMoneyGive() {
	}

	public GOTPacketMoneyGive(ItemStack items) {
		item = items;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			item = new PacketBuffer(data).readItemStackFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error writing money data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeItemStackToBuffer(item);
		} catch (IOException e) {
			FMLLog.severe("Error reading money data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketMoneyGive, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMoneyGive packet, MessageContext context) {
			ItemStack item = packet.item;
			UUID hummel = packet.hummel;
			if (GOT.getBuy().containsKey(item)) {
				EntityPlayerMP player = context.getServerHandler().playerEntity;
				GOTPlayerData pd = GOTLevelData.getData(player);
				int cost = GOT.getBuy().get(item);
				if (hummel.equals(player.getUniqueID())) {
					player.inventory.addItemStackToInventory(item);
				} else if (pd.getBalance() >= cost && player.inventory.addItemStackToInventory(item)) {
					int balance = pd.getBalance();
					balance -= cost;
					pd.setBalance(balance);
					GOTPacketHandler.networkWrapper.sendTo(packet, player);
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.gui.money.give", item.getDisplayName())));
				} else {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.gui.money.notGive", item.getDisplayName())));
				}
			}
			return null;
		}
	}
}
