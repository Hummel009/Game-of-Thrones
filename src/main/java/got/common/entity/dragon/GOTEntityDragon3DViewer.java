package got.common.entity.dragon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import got.client.GOTKeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GOTEntityDragon3DViewer {

	public static String[] ENTITYRENDERER_THIRDPERSONDISTANCE = {"thirdPersonDistance", "field_78490_B"};
	public Minecraft mc = Minecraft.getMinecraft();
	public float defaultThirdPersonDistance;
	public int noticeTicks;
	public boolean ridingDragon;
	public boolean ridingDragonPrev;

	public GOTEntityDragon3DViewer() {
		defaultThirdPersonDistance = getThirdPersonDistance();
	}

	public float getThirdPersonDistance() {
		return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, GOTEntityDragon3DViewer.ENTITYRENDERER_THIRDPERSONDISTANCE);
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent evt) {
		if (evt.phase != TickEvent.Phase.START || mc.thePlayer == null) {
			return;
		}
		ridingDragon = mc.thePlayer.ridingEntity instanceof GOTEntityDragon;

		if (ridingDragon && !ridingDragonPrev) {
			setThirdPersonDistance(6);
			noticeTicks = 70;
		} else if (!ridingDragon && ridingDragonPrev) {
			setThirdPersonDistance(defaultThirdPersonDistance);
			noticeTicks = 0;
		} else {
			if (noticeTicks > 0) {
				noticeTicks--;
			}

			if (noticeTicks == 1) {
				String keyUpName = GameSettings.getKeyDisplayString(GOTKeyHandler.keyBindingDragonUp.getKeyCode());
				String keyDownName = GameSettings.getKeyDisplayString(GOTKeyHandler.keyBindingDragonDown.getKeyCode());
				mc.ingameGUI.func_110326_a(I18n.format("dragon.mountNotice", keyUpName, keyDownName), false);
			}
		}

		ridingDragonPrev = ridingDragon;
	}

	public void setThirdPersonDistance(float thirdPersonDistance) {
		ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, thirdPersonDistance, GOTEntityDragon3DViewer.ENTITYRENDERER_THIRDPERSONDISTANCE);
	}
}