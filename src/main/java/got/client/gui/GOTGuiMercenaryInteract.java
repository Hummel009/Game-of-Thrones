package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMercenaryInteract;
import net.minecraft.client.gui.GuiButton;

public class GOTGuiMercenaryInteract extends GOTGuiUnitTradeInteract {
	public GOTGuiMercenaryInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			IMessage packet = new GOTPacketMercenaryInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}
}