package got.client.gui;

import got.common.entity.other.*;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiTradeInteract extends GOTGuiNPCInteract {
	public GuiButton buttonTalk;
	public GuiButton buttonTrade;
	public GuiButton buttonExchange;
	public GuiButton buttonSmith;

	public GOTGuiTradeInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketTraderInteract packet = new GOTPacketTraderInteract(theEntity.getEntityId(), button.id);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonTalk = new GOTGuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk"));
		buttonTrade = new GOTGuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.trade"));
		buttonExchange = new GOTGuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("got.gui.npc.exchange"));
		buttonList.add(buttonTalk);
		buttonList.add(buttonTrade);
		buttonList.add(buttonExchange);
		if (theEntity instanceof GOTTradeable.Smith) {
			buttonTalk.xPosition -= 35;
			buttonTrade.xPosition -= 35;
			buttonSmith = new GOTGuiButton(3, width / 2 + 40, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.smith"));
			buttonList.add(buttonSmith);
		}
	}
}
