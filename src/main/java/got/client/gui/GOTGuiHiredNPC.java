package got.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import got.common.entity.other.*;
import got.common.faction.*;
import got.common.network.*;
import net.minecraft.util.*;

public abstract class GOTGuiHiredNPC extends GOTGuiScreenBase {
	private static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/npc/hired.png");
	private int xSize = 200;
	private int ySize = 220;
	private int guiLeft;
	private int guiTop;
	public GOTEntityNPC theNPC;
	private int page;

	public GOTGuiHiredNPC(GOTEntityNPC npc) {
		theNPC = npc;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, getxSize(), ySize);
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, getGuiLeft() + getxSize() / 2 - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 11, 3618615);
		s = theNPC.getEntityClassName();
		fontRendererObj.drawString(s, getGuiLeft() + getxSize() / 2 - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 26, 3618615);
		if (getPage() == 0 && theNPC.hiredNPCInfo.hasHiringRequirements()) {
			int x = getGuiLeft() + 6;
			int y = getGuiTop() + 170;
			s = StatCollector.translateToLocal("got.hiredNPC.commandReq");
			fontRendererObj.drawString(s, x, y, 3618615);
			y += fontRendererObj.FONT_HEIGHT;
			x += 4;
			ArrayList requirementLines = new ArrayList();
			int maxWidth = getxSize() - 12 - 4;
			GOTFaction fac = theNPC.getHiringFaction();
			String alignS = GOTAlignmentValues.formatAlignForDisplay(theNPC.hiredNPCInfo.alignmentRequiredToCommand);
			String alignReq = StatCollector.translateToLocalFormatted("got.hiredNPC.commandReq.align", alignS, fac.factionName());
			requirementLines.addAll(fontRendererObj.listFormattedStringToWidth(alignReq, maxWidth));
			GOTUnitTradeEntry.PledgeType pledge = theNPC.hiredNPCInfo.pledgeType;
			String pledgeReq = pledge.getCommandReqText(fac);
			if (pledgeReq != null) {
				requirementLines.addAll(fontRendererObj.listFormattedStringToWidth(pledgeReq, maxWidth));
			}
			for (Object obj : requirementLines) {
				String line = (String) obj;
				fontRendererObj.drawString(line, x, y, 3618615);
				y += fontRendererObj.FONT_HEIGHT;
			}
		}
		super.drawScreen(i, j, f);
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}

	public int getPage() {
		return page;
	}

	public int getxSize() {
		return xSize;
	}

	@Override
	public void initGui() {
		setGuiLeft((width - getxSize()) / 2);
		setGuiTop((height - ySize) / 2);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		this.sendActionPacket(-1);
	}

	public void sendActionPacket(int action) {
		this.sendActionPacket(action, 0);
	}

	public void sendActionPacket(int action, int value) {
		GOTPacketHiredUnitCommand packet = new GOTPacketHiredUnitCommand(theNPC.getEntityId(), getPage(), action, value);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	public void setGuiLeft(int guiLeft) {
		this.guiLeft = guiLeft;
	}

	public void setGuiTop(int guiTop) {
		this.guiTop = guiTop;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!theNPC.isEntityAlive() || theNPC.hiredNPCInfo.getHiringPlayer() != mc.thePlayer || theNPC.getDistanceSqToEntity(mc.thePlayer) > 64.0) {
			mc.thePlayer.closeScreen();
		}
	}
}
