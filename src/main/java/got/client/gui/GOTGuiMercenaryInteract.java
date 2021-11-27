package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;

public class GOTGuiMercenaryInteract extends GOTGuiUnitTradeInteract {
	public GOTGuiMercenaryInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketMercenaryInteract packet = new GOTPacketMercenaryInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}
}
