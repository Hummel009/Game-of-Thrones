package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiHiredInteract extends GOTGuiNPCInteract {
	public GOTGuiHiredInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 2) {
				mc.displayGuiScreen(new GOTGuiHiredDismiss(theEntity));
				return;
			}
			GOTPacketHiredUnitInteract packet = new GOTPacketHiredUnitInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonList.add(new GuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk")));
		buttonList.add(new GuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.command")));
		buttonList.add(new GuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("got.gui.npc.dismiss")));
		((GuiButton) buttonList.get(0)).enabled = theEntity.getSpeechBank(mc.thePlayer) != null;
	}
}
