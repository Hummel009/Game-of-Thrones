package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketHiredUnitInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

import java.util.List;

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
			IMessage packet = new GOTPacketHiredUnitInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonList.add(new GOTGuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk")));
		buttonList.add(new GOTGuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.command")));
		buttonList.add(new GOTGuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("got.gui.npc.dismiss")));
		((List<GuiButton>) buttonList).get(0).enabled = theEntity.getSpeechBank(theEntity, mc.thePlayer) != null;
	}
}