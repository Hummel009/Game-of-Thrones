package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTUnitTradeEntry;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketHiredUnitCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public abstract class GOTGuiHiredNPC extends GOTGuiScreenBase {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/npc/hired.png");
	public int xSize = 200;
	public int ySize = 220;
	public int guiLeft;
	public int guiTop;
	public GOTEntityNPC theNPC;
	public int page;

	protected GOTGuiHiredNPC(GOTEntityNPC npc) {
		theNPC = npc;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
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
			int maxWidth = xSize - 12 - 4;
			GOTFaction fac = theNPC.getHiringFaction();
			String alignS = GOTAlignmentValues.formatAlignForDisplay(theNPC.hiredNPCInfo.alignmentRequiredToCommand);
			String alignReq = StatCollector.translateToLocalFormatted("got.hiredNPC.commandReq.align", alignS, fac.factionName());
			ArrayList<String> requirementLines = new ArrayList<String>(fontRendererObj.listFormattedStringToWidth(alignReq, maxWidth));
			GOTUnitTradeEntry.PledgeType pledge = theNPC.hiredNPCInfo.pledgeType;
			String pledgeReq = pledge.getCommandReqText(fac);
			if (pledgeReq != null) {
				requirementLines.addAll(fontRendererObj.listFormattedStringToWidth(pledgeReq, maxWidth));
			}
			for (String obj : requirementLines) {
				fontRendererObj.drawString(obj, x, y, 3618615);
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
		sendActionPacket(-1);
	}

	public void sendActionPacket(int action) {
		sendActionPacket(action, 0);
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
