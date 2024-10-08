package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import got.common.faction.GOTFaction;
import got.common.faction.GOTReputationValues;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketHiredUnitCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("WeakerAccess")
public abstract class GOTGuiHiredNPC extends GOTGuiScreenBase {
	protected static final int Y_SIZE = 220;
	protected static final int X_SIZE = 200;

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/npc/hired.png");

	protected final GOTEntityNPC theNPC;
	protected int guiLeft;
	protected int guiTop;
	protected int page;

	protected GOTGuiHiredNPC(GOTEntityNPC npc) {
		theNPC = npc;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, X_SIZE, Y_SIZE);
		String s = theNPC.getNPCName();
		fontRendererObj.drawString(s, guiLeft + X_SIZE / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 11, 3618615);
		s = theNPC.getEntityClassName();
		fontRendererObj.drawString(s, guiLeft + X_SIZE / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 26, 3618615);
		if (page == 0 && theNPC.getHireableInfo().hasHiringRequirements()) {
			int y = guiTop + 170;
			s = StatCollector.translateToLocal("got.hiredNPC.commandReq");
			fontRendererObj.drawString(s, guiLeft + X_SIZE / 2 - fontRendererObj.getStringWidth(s) / 2, y, 3618615);
			y += fontRendererObj.FONT_HEIGHT;
			int maxWidth = X_SIZE - 12 - 4;
			GOTFaction fac = theNPC.getFaction();
			String repS = GOTReputationValues.formatRepForDisplay(theNPC.getHireableInfo().getReputationRequiredToCommand());
			String repReq = StatCollector.translateToLocalFormatted("got.hiredNPC.commandReq.rep", repS, fac.factionName());
			Collection<String> requirementLines = new ArrayList<String>(fontRendererObj.listFormattedStringToWidth(repReq, maxWidth));
			GOTUnitTradeEntry.OathType oath = theNPC.getHireableInfo().getOathType();
			String oathReq = oath.getCommandReqText(fac);
			if (oathReq != null) {
				requirementLines.addAll(fontRendererObj.listFormattedStringToWidth(oathReq, maxWidth));
			}
			for (String obj : requirementLines) {
				fontRendererObj.drawString(obj, guiLeft + X_SIZE / 2 - fontRendererObj.getStringWidth(obj) / 2, y, 3618615);
				y += fontRendererObj.FONT_HEIGHT;
			}
		}
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - X_SIZE) / 2;
		guiTop = (height - Y_SIZE) / 2;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		sendActionPacket(-1);
	}

	protected void sendActionPacket(int action) {
		sendActionPacket(action, 0);
	}

	protected void sendActionPacket(int action, int value) {
		IMessage packet = new GOTPacketHiredUnitCommand(theNPC.getEntityId(), page, action, value);
		GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (!theNPC.isEntityAlive() || theNPC.getHireableInfo().getHiringPlayer() != mc.thePlayer || theNPC.getDistanceSqToEntity(mc.thePlayer) > 64.0) {
			mc.thePlayer.closeScreen();
		}
	}
}