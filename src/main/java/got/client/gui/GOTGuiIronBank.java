package got.client.gui;

import got.common.*;
import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemCoin;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiIronBank extends GuiScreen {
	public static ResourceLocation BANK = new ResourceLocation("got", "gui/bank.png");
	public static int backgroundWidth = 128;
	public static int backgroundWidthHalf = 64;
	public static int backgroundHeight = 188;
	public static int backgroundHeightHalf = 94;
	int widthHalf;
	int heightHalf;
	int xOrigin;
	int yOrigin;
	public GuiButton buy1;
	public GuiButton buy2;
	public GuiButton buy4;
	public GuiButton buy8;
	public GuiButton buy56;
	public GuiButton buy392;
	public GuiButton buy11760;
	public GuiButton buy105840;
	public GuiButton sell1;
	public GuiButton sell2;
	public GuiButton sell4;
	public GuiButton sell8;
	public GuiButton sell56;
	public GuiButton sell392;
	public GuiButton sell11760;
	public GuiButton sell105840;

	public GOTGuiIronBank(EntityPlayer player) {
		this(player, player.getCommandSenderName());
	}

	public GOTGuiIronBank(EntityPlayer player, String owner) {
		widthHalf = width / 2;
		heightHalf = height / 2;
		xOrigin = widthHalf - 128;
		yOrigin = heightHalf - 94;
	}

	@Override
	public void actionPerformed(GuiButton B) {
		Minecraft.getMinecraft().thePlayer.getDisplayName();
		if (B == buy1) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 0));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy2) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 1));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy4) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 2));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy8) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 3));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy56) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 4));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy392) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 5));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy11760) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 6));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == buy105840) {
			GOTPacketMoneyGive packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, 7));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell1) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 0));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell2) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 1));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell4) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 2));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell8) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 3));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell56) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 4));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell392) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 5));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell11760) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 6));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (B == sell105840) {
			GOTPacketMoneyGet packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, 7));
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		int widthHalf = width / 2;
		int heightHalf = height / 2;
		int xOrigin = widthHalf - 128;
		int yOrigin = heightHalf - 94;
		mc.renderEngine.bindTexture(BANK);
		drawTexturedModalRect(xOrigin, yOrigin, 0, 0, 256, 188);
		GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
		String balance = StatCollector.translateToLocalFormatted("got.gui.money.balance", pd.balance);
		String instructions = StatCollector.translateToLocal("got.gui.money.press");
		drawCenteredString(fontRendererObj, balance, xOrigin + 65 + 64, yOrigin - 15, 16777215);
		drawCenteredString(fontRendererObj, instructions, xOrigin + 65 + 64, yOrigin + 200, 10066329);
		String withdraw = StatCollector.translateToLocal("got.gui.money.withdraw");
		drawCenteredString(fontRendererObj, withdraw, xOrigin + 65 + 64, yOrigin + 35, 16777215);
		String transfer = StatCollector.translateToLocal("got.gui.money.transfer");
		drawCenteredString(fontRendererObj, transfer, xOrigin + 65 + 64, yOrigin + 105, 16777215);
		int x = xOrigin + 10;
		int i = 0;
		for (int coin : GOTItemCoin.values) {
			RenderHelper.enableGUIStandardItemLighting();
			mc.renderEngine.bindTexture(BANK);
			drawTexturedModalRect(x, yOrigin + 50, 0, 188, 27, 27);
			itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.renderEngine, new ItemStack(GOTRegistry.coin, 0, i), x + 5, yOrigin + 50 + 5);
			mc.renderEngine.bindTexture(BANK);
			RenderHelper.enableGUIStandardItemLighting();
			drawTexturedModalRect(x, yOrigin + 120, 27, 188, 27, 27);
			itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.renderEngine, new ItemStack(GOTRegistry.coin, 0, i), x + 5, yOrigin + 120 + 5);
			RenderHelper.disableStandardItemLighting();
			x += 30;
			++i;
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		int widthHalf = width / 2;
		int heightHalf = height / 2;
		int xOrigin = widthHalf - 128 + 5;
		int yOrigin = heightHalf - 94;
		buy1 = new GOTGuiButtonIronBank(0, xOrigin + 4, yOrigin + 50, 28, 28, "");
		buttonList.add(buy1);
		buy2 = new GOTGuiButtonIronBank(0, xOrigin + 35, yOrigin + 50, 28, 28, "");
		buttonList.add(buy2);
		buy4 = new GOTGuiButtonIronBank(0, xOrigin + 65, yOrigin + 50, 28, 28, "");
		buttonList.add(buy4);
		buy8 = new GOTGuiButtonIronBank(0, xOrigin + 95, yOrigin + 50, 28, 28, "");
		buttonList.add(buy8);
		buy56 = new GOTGuiButtonIronBank(0, xOrigin + 125, yOrigin + 50, 28, 28, "");
		buttonList.add(buy56);
		buy392 = new GOTGuiButtonIronBank(0, xOrigin + 155, yOrigin + 50, 28, 28, "");
		buttonList.add(buy392);
		buy11760 = new GOTGuiButtonIronBank(0, xOrigin + 185, yOrigin + 50, 28, 28, "");
		buttonList.add(buy11760);
		buy105840 = new GOTGuiButtonIronBank(0, xOrigin + 215, yOrigin + 50, 28, 28, "");
		buttonList.add(buy105840);
		sell1 = new GOTGuiButtonIronBank(0, xOrigin + 4, yOrigin + 120, 28, 28, "");
		buttonList.add(sell1);
		sell2 = new GOTGuiButtonIronBank(0, xOrigin + 35, yOrigin + 120, 28, 28, "");
		buttonList.add(sell2);
		sell4 = new GOTGuiButtonIronBank(0, xOrigin + 65, yOrigin + 120, 28, 28, "");
		buttonList.add(sell4);
		sell8 = new GOTGuiButtonIronBank(0, xOrigin + 95, yOrigin + 120, 28, 28, "");
		buttonList.add(sell8);
		sell56 = new GOTGuiButtonIronBank(0, xOrigin + 125, yOrigin + 120, 28, 28, "");
		buttonList.add(sell56);
		sell392 = new GOTGuiButtonIronBank(0, xOrigin + 155, yOrigin + 120, 28, 28, "");
		buttonList.add(sell392);
		sell11760 = new GOTGuiButtonIronBank(0, xOrigin + 185, yOrigin + 120, 28, 28, "");
		buttonList.add(sell11760);
		sell105840 = new GOTGuiButtonIronBank(0, xOrigin + 215, yOrigin + 120, 28, 28, "");
		buttonList.add(sell105840);
	}
}
