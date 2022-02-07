package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;

public class GOTGuiHiredDismiss extends GOTGuiNPCInteract {
	public GOTGuiHiredDismiss(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(new GOTGuiHiredInteract(getTheEntity()));
				return;
			}
			GOTPacketHiredUnitDismiss packet = new GOTPacketHiredUnitDismiss(getTheEntity().getEntityId(), button.id);
			GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		boolean hasRider;
		super.drawScreen(i, j, f);
		String s = StatCollector.translateToLocal("got.gui.dismiss.warning1");
		int y = height / 5 * 3;
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 16777215);
		s = StatCollector.translateToLocal("got.gui.dismiss.warning2");
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y += fontRendererObj.FONT_HEIGHT, 16777215);
		y += fontRendererObj.FONT_HEIGHT;
		Entity mount = getTheEntity().ridingEntity;
		Entity rider = getTheEntity().riddenByEntity;
		boolean hasMount = mount instanceof GOTEntityNPC && ((GOTEntityNPC) mount).hiredNPCInfo.getHiringPlayer() == mc.thePlayer;
		hasRider = rider instanceof GOTEntityNPC && ((GOTEntityNPC) rider).hiredNPCInfo.getHiringPlayer() == mc.thePlayer;
		if (hasMount) {
			s = StatCollector.translateToLocal("got.gui.dismiss.mount");
			fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
			y += fontRendererObj.FONT_HEIGHT;
		}
		if (hasRider) {
			s = StatCollector.translateToLocal("got.gui.dismiss.rider");
			fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
			y += fontRendererObj.FONT_HEIGHT;
		}
	}

	@Override
	public void initGui() {
		buttonList.add(new GOTGuiButton(0, width / 2 - 65, height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("got.gui.dismiss.dismiss")));
		buttonList.add(new GOTGuiButton(1, width / 2 + 5, height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("got.gui.dismiss.cancel")));
	}
}