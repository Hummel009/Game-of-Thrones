package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiUnitTradeInteract extends GOTGuiNPCInteract {
	private GuiButton buttonTalk;
	private GuiButton buttonHire;

	public GOTGuiUnitTradeInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketUnitTraderInteract packet = new GOTPacketUnitTraderInteract(getTheEntity().getEntityId(), button.id);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonTalk = new GuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk"));
		buttonHire = new GuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.hire"));
		buttonList.add(buttonTalk);
		buttonList.add(buttonHire);
	}
}
