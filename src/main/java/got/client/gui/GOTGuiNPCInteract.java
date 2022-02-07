package got.client.gui;

import got.common.entity.other.GOTEntityNPC;

public abstract class GOTGuiNPCInteract extends GOTGuiScreenBase {
	private GOTEntityNPC theEntity;

	public GOTGuiNPCInteract(GOTEntityNPC entity) {
		setTheEntity(entity);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		String s = getTheEntity().getCommandSenderName();
		fontRendererObj.drawString(s, (width - fontRendererObj.getStringWidth(s)) / 2, height / 5 * 3 - 20, 16777215);
		super.drawScreen(i, j, f);
	}

	public GOTEntityNPC getTheEntity() {
		return theEntity;
	}

	public void setTheEntity(GOTEntityNPC theEntity) {
		this.theEntity = theEntity;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (getTheEntity() == null || !getTheEntity().isEntityAlive() || getTheEntity().getDistanceSqToEntity(mc.thePlayer) > 100.0) {
			mc.thePlayer.closeScreen();
		}
	}
}
