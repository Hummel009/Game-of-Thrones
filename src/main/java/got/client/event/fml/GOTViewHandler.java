package got.client.event.fml;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import got.common.entity.animal.GOTEntityElephant;
import got.common.entity.animal.GOTEntityMammoth;
import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GOTViewHandler {
	private static final String[] ENTITYRENDERER_THIRDPERSONDISTANCE = {"thirdPersonDistance", "field_78490_B"};

	private final Minecraft mc = Minecraft.getMinecraft();
	private final float defaultThirdPersonDistance;

	private int dragonGuideTicks;
	private boolean ridingDragonPrev;
	private boolean ridingMammothPrev;

	public GOTViewHandler() {
		defaultThirdPersonDistance = getThirdPersonDistance();
	}

	private float getThirdPersonDistance() {
		return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, ENTITYRENDERER_THIRDPERSONDISTANCE);
	}

	private void setThirdPersonDistance(float thirdPersonDistance) {
		ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, thirdPersonDistance, ENTITYRENDERER_THIRDPERSONDISTANCE);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent evt) {
		if (evt.phase != TickEvent.Phase.START || mc.thePlayer == null) {
			return;
		}

		boolean ridingDragon = mc.thePlayer.ridingEntity instanceof GOTEntityDragon;
		boolean ridingMammoth = mc.thePlayer.ridingEntity instanceof GOTEntityElephant || mc.thePlayer.ridingEntity instanceof GOTEntityMammoth;

		if (ridingMammoth && !ridingMammothPrev) {
			setThirdPersonDistance(9);
		} else if (!ridingMammoth && ridingMammothPrev) {
			setThirdPersonDistance(defaultThirdPersonDistance);
		}

		ridingMammothPrev = ridingMammoth;

		if (ridingDragon && !ridingDragonPrev) {
			setThirdPersonDistance(6);
			dragonGuideTicks = 70;
		} else if (!ridingDragon && ridingDragonPrev) {
			setThirdPersonDistance(defaultThirdPersonDistance);
			dragonGuideTicks = 0;
		} else {
			if (dragonGuideTicks > 0) {
				dragonGuideTicks--;
			}

			if (dragonGuideTicks == 1) {
				String keyUpName = GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_DRAGON_UP.getKeyCode());
				String keyDownName = GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_DRAGON_DOWN.getKeyCode());
				mc.ingameGUI.func_110326_a(I18n.format("dragon.mountNotice", keyUpName, keyDownName), false);
			}
		}

		ridingDragonPrev = ridingDragon;
	}
}