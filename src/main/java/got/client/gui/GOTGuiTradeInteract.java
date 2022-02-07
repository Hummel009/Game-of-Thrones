package got.client.gui;

import got.common.entity.other.*;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiTradeInteract extends GOTGuiNPCInteract {
	private GuiButton buttonTalk;
	private GuiButton buttonTrade;
	private GuiButton buttonExchange;
	private GuiButton buttonSmith;

	public GOTGuiTradeInteract(GOTEntityNPC entity) {
		super(entity);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			GOTPacketTraderInteract packet = new GOTPacketTraderInteract(getTheEntity().getEntityId(), button.id);
			GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
		}
	}

	@Override
	public void initGui() {
		buttonTalk = new GuiButton(0, width / 2 - 65, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.talk"));
		buttonTrade = new GuiButton(1, width / 2 + 5, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.trade"));
		buttonExchange = new GuiButton(2, width / 2 - 65, height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("got.gui.npc.exchange"));
		buttonList.add(buttonTalk);
		buttonList.add(buttonTrade);
		buttonList.add(buttonExchange);
		if (getTheEntity() instanceof GOTTradeable.Smith) {
			buttonTalk.xPosition -= 35;
			buttonTrade.xPosition -= 35;
			buttonSmith = new GuiButton(3, width / 2 + 40, height / 5 * 3, 60, 20, StatCollector.translateToLocal("got.gui.npc.smith"));
			buttonList.add(buttonSmith);
		}
	}
}
