package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.block.other.GOTBlockIronBank;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.io.IOException;

public class GOTPacketMoneyGet extends GOTPacketMoney {
	public ItemStack item;

	public GOTPacketMoneyGet() {
	}

	public GOTPacketMoneyGet(ItemStack items) {
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

	public static class Handler implements IMessageHandler<GOTPacketMoneyGet, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMoneyGet packet, MessageContext context) {
			ItemStack item = packet.item;
			if (GOTBlockIronBank.SELL.containsKey(item)) {
				EntityPlayerMP player = context.getServerHandler().playerEntity;
				GOTPlayerData pd = GOTLevelData.getData(player);
				int cost = GOTBlockIronBank.SELL.get(item);
				int index = -1;
				for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
					if (player.inventory.mainInventory[i] == null || player.inventory.mainInventory[i].getItem() != item.getItem() || player.inventory.mainInventory[i].getItemDamage() != item.getItemDamage()) {
						continue;
					}
					index = i;
					break;
				}
				if (index >= 0) {
					--player.inventory.mainInventory[index].stackSize;
					if (player.inventory.mainInventory[index].stackSize <= 0) {
						player.inventory.mainInventory[index] = null;
					}
					int money = pd.getBalance();
					money += cost;
					pd.setBalance(money);
					GOTPacketHandler.networkWrapper.sendTo(packet, player);
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.gui.money.get", item.getDisplayName())));
				} else {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocalFormatted("got.gui.money.notGet", item.getDisplayName())));
				}
			}
			return null;
		}
	}
}
