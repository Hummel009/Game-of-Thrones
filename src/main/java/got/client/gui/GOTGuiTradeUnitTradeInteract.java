package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiTradeUnitTradeInteract extends GOTGuiTradeInteract {
	private GuiButton buttonHire;

	public GOTGuiTradeUnitTradeInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonHire) {
				GOTPacketUnitTraderInteract packet = new GOTPacketUnitTraderInteract(getTheEntity().getEntityId(), 1);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonHire = new GuiButton(-1, width / 2 - 65, height / 5 * 3 + 50, 130, 20, StatCollector.translateToLocal("got.gui.npc.hire"));
		buttonList.add(buttonHire);
	}
}
