package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeable;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketTraderInteract;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiTradeInteract extends GOTGuiNPCInteract {
	public GOTGuiTradeInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			IMessage packet = new GOTPacketTraderInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		GuiButton buttonTalk = new GOTGuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk"));
		GuiButton buttonTrade = new GOTGuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.trade"));
		GuiButton buttonExchange = new GOTGuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("got.gui.npc.exchange"));
		buttonList.add(buttonTalk);
		buttonList.add(buttonTrade);
		buttonList.add(buttonExchange);
		if (theEntity instanceof GOTTradeable.Smith) {
			buttonTalk.xPosition -= 35;
			buttonTrade.xPosition -= 35;
			GuiButton buttonSmith = new GOTGuiButton(3, width / 2 + 40, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.smith"));
			buttonList.add(buttonSmith);
		}
	}
}
