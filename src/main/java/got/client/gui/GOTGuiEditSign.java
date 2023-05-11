package got.client.gui;

import got.common.network.GOTPacketEditSign;
import got.common.network.GOTPacketHandler;
import got.common.tileentity.GOTTileEntitySign;
import got.common.tileentity.GOTTileEntitySignCarved;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GOTGuiEditSign extends GuiScreen {
	public static RenderItem itemRenderer = new RenderItem();
	public GOTTileEntitySign tileSign;
	public int updateCounter;
	public int editLine;
	public GuiButton buttonDone;

	public GOTGuiEditSign(GOTTileEntitySign sign) {
		tileSign = sign;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			tileSign.markDirty();
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		tileSign.getBlockType();
		int meta = tileSign.getBlockMetadata();
		float rotation = Direction.facingToDirection[meta] * 90.0f;
		IIcon onIcon = ((GOTTileEntitySignCarved) tileSign).getOnBlockIcon();
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("sign.edit"), width / 2, 40, 16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) width / 2, 0.0f, 50.0f);
		float f1 = 93.75f;
		GL11.glScalef(-f1, -f1, -f1);
		GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
		GL11.glDisable(2929);
		GL11.glPushMatrix();
		float iconScale = 0.5f;
		GL11.glScalef(-iconScale, -iconScale, iconScale);
		GL11.glTranslatef(0.0f, 0.5f, 0.0f);
		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		itemRenderer.renderIcon(-1, -1, onIcon, 2, 2);
		GL11.glPopMatrix();
		GL11.glEnable(2929);
		if (updateCounter / 6 % 2 == 0) {
			tileSign.lineBeingEdited = editLine;
		}
		GL11.glRotatef(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
		TileEntityRendererDispatcher.instance.renderTileEntityAt(tileSign, -0.5, -0.75, -0.5, 0.0f);
		GL11.glDisable(2896);
		tileSign.lineBeingEdited = -1;
		GL11.glPopMatrix();
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		buttonDone = new GOTGuiButton(0, width / 2 - 100, height / 4 + 120, StatCollector.translateToLocal("gui.done"));
		buttonList.add(buttonDone);
		tileSign.setEditable(false);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (i == 200) {
			--editLine;
		}
		if (i == 208 || i == 28 || i == 156) {
			++editLine;
		}
		editLine &= tileSign.getNumLines() - 1;
		if (i == 14 && tileSign.signText[editLine].length() > 0) {
			String s = tileSign.signText[editLine];
			tileSign.signText[editLine] = s.substring(0, s.length() - 1);
		}
		if (ChatAllowedCharacters.isAllowedCharacter(c) && tileSign.signText[editLine].length() < 15) {
			int n = editLine;
			tileSign.signText[n] = tileSign.signText[n] + c;
		}
		if (i == 1) {
			actionPerformed(buttonDone);
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		GOTPacketEditSign packet = new GOTPacketEditSign(tileSign);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
		tileSign.setEditable(true);
	}

	@Override
	public void updateScreen() {
		++updateCounter;
	}
}