package got.client.gui;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHiredNPCInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class GOTGuiHiredFarmer extends GOTGuiHiredNPC {
	public GOTGuiButtonOptions buttonGuardMode;
	public GOTGuiSlider sliderGuardRange;

	public GOTGuiHiredFarmer(GOTEntityNPC npc) {
		super(npc);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button instanceof GOTGuiSlider) {
			return;
		}
		if (button.enabled) {
			this.sendActionPacket(button.id);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		String s = theNPC.hiredNPCInfo.getStatusString();
		fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 50, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonGuardMode = new GOTGuiButtonOptions(0, guiLeft + xSize / 2 - 80, guiTop + 70, 160, 20, StatCollector.translateToLocal("got.gui.farmer.mode"));
		buttonList.add(buttonGuardMode);
		buttonGuardMode.setState(theNPC.hiredNPCInfo.isGuardMode());
		sliderGuardRange = new GOTGuiSlider(1, guiLeft + xSize / 2 - 80, guiTop + 94, 160, 20, StatCollector.translateToLocal("got.gui.farmer.range"));
		buttonList.add(sliderGuardRange);
		sliderGuardRange.setMinMaxValues(GOTHiredNPCInfo.GUARD_RANGE_MIN, GOTHiredNPCInfo.GUARD_RANGE_MAX);
		sliderGuardRange.setSliderValue(theNPC.hiredNPCInfo.getGuardRange());
		sliderGuardRange.visible = theNPC.hiredNPCInfo.isGuardMode();
		buttonList.add(new GOTGuiButtonOptions(2, guiLeft + xSize / 2 - 80, guiTop + 142, 160, 20, StatCollector.translateToLocal("got.gui.farmer.openInv")));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonGuardMode.setState(theNPC.hiredNPCInfo.isGuardMode());
		sliderGuardRange.visible = theNPC.hiredNPCInfo.isGuardMode();
		if (sliderGuardRange.dragging) {
			int i = sliderGuardRange.getSliderValue();
			theNPC.hiredNPCInfo.setGuardRange(i);
			this.sendActionPacket(sliderGuardRange.id, i);
		}
	}
}
