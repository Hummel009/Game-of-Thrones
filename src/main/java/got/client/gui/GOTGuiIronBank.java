package got.client.gui;

import got.common.*;
import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemCoin;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiIronBank extends GuiScreen {
	private GuiButton[] button = new GuiButton[16];
	int widthHalf;
	int heightHalf;
	int xOrigin;
	int yOrigin;

	public GOTGuiIronBank() {
		widthHalf = width / 2;
		heightHalf = height / 2;
		xOrigin = widthHalf - 128;
		yOrigin = heightHalf - 94;
	}

	@Override
	public void actionPerformed(GuiButton B) {
		Minecraft.getMinecraft().thePlayer.getDisplayName();
		GOTPacketMoney packet;
		for (int i = 0; i <= 15; i++) {
			if (B == button[i]) {
				if (i <= 7) {
					packet = new GOTPacketMoneyGive(new ItemStack(GOTRegistry.coin, 1, i));
				} else {
					packet = new GOTPacketMoneyGet(new ItemStack(GOTRegistry.coin, 1, i - 8));
				}
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			}
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
		mc.renderEngine.bindTexture(new ResourceLocation("got:textures/gui/bank.png"));
		drawTexturedModalRect(xOrigin, yOrigin, 0, 0, 256, 188);
		GOTPlayerData pd = GOTLevelData.getData(mc.thePlayer);
		String balance = StatCollector.translateToLocalFormatted("got.gui.money.balance", pd.getBalance());
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
			mc.renderEngine.bindTexture(new ResourceLocation("got:textures/gui/bank.png"));
			drawTexturedModalRect(x, yOrigin + 50, 0, 188, 27, 27);
			itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.renderEngine, new ItemStack(GOTRegistry.coin, 0, i), x + 5, yOrigin + 50 + 5);
			mc.renderEngine.bindTexture(new ResourceLocation("got:textures/gui/bank.png"));
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
		int xOrigin = widthHalf - 118;
		int row1 = xOrigin;
		int row2 = xOrigin;
		for (int i = 0; i <= 15; i++) {
			if (i <= 7) {
				button[i] = new GOTGuiButtonIronBank(i, row1, heightHalf - 44);
				row1 += 30;
			} else {
				button[i] = new GOTGuiButtonIronBank(i, row2, heightHalf + 26);
				row2 += 30;
			}
			buttonList.add(button[i]);
		}
	}
}
