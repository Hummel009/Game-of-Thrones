package got.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import got.common.entity.other.*;
import got.common.faction.*;
import got.common.network.*;
import net.minecraft.util.*;

public abstract class GOTGuiHiredNPC extends GOTGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("got:gui/npc/hired.png");
	public int xSize = 200;
	public int ySize = 220;
	public int guiLeft;
	public int guiTop;
	public GOTEntityNPC theNPC;
	public int page;

	public GOTGuiHiredNPC(GOTEntityNPC npc) {
		theNPC = npc;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 3618615);
		s = theNPC.getEntityClassName();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 26, 3618615);
		if (page == 0 && theNPC.hiredNPCInfo.hasHiringRequirements()) {
			int x = guiLeft + 6;
			int y = guiTop + 170;
			s = StatCollector.translateToLocal("got.hiredNPC.commandReq");
			fontRendererObj.drawString(s, x, y, 3618615);
			y += fontRendererObj.FONT_HEIGHT;
			x += 4;
			ArrayList requirementLines = new ArrayList();
			int maxWidth = xSize - 12 - 4;
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

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
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
		GOTPacketHiredUnitCommand packet = new GOTPacketHiredUnitCommand(theNPC.getEntityId(), page, action, value);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!theNPC.isEntityAlive() || theNPC.hiredNPCInfo.getHiringPlayer() != mc.thePlayer || theNPC.getDistanceSqToEntity(mc.thePlayer) > 64.0) {
			mc.thePlayer.closeScreen();
		}
	}
}
