package got.client;

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

public class GOTThirdPersonViewer {
	public static final GOTThirdPersonViewer INSTANCE = new GOTThirdPersonViewer();

	private static final String[] ENTITYRENDERER_THIRDPERSONDISTANCE = new String[]{"thirdPersonDistance", "field_78490_B"};
	private static final Minecraft MC = Minecraft.getMinecraft();

	private boolean ridingDragonPrev;
	private boolean ridingMammothPrev;
	private float defaultThirdPersonDistance;
	private int noticeTicks;

	private GOTThirdPersonViewer() {
	}

	public void init() {
		defaultThirdPersonDistance = getThirdPersonDistance();
	}

	private float getThirdPersonDistance() {
		return ReflectionHelper.getPrivateValue(EntityRenderer.class, MC.entityRenderer, ENTITYRENDERER_THIRDPERSONDISTANCE);
	}

	private void setThirdPersonDistance(float thirdPersonDistance) {
		ReflectionHelper.setPrivateValue(EntityRenderer.class, MC.entityRenderer, thirdPersonDistance, ENTITYRENDERER_THIRDPERSONDISTANCE);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent evt) {
		if (evt.phase != TickEvent.Phase.START || MC.thePlayer == null) {
			return;
		}
		boolean ridingDragon = MC.thePlayer.ridingEntity instanceof GOTEntityDragon;
		boolean ridingMammoth = MC.thePlayer.ridingEntity instanceof GOTEntityMammoth || MC.thePlayer.ridingEntity instanceof GOTEntityElephant;
		if (ridingMammoth && !ridingMammothPrev) {
			setThirdPersonDistance(9);
		} else if (!ridingMammoth && ridingMammothPrev) {
			setThirdPersonDistance(defaultThirdPersonDistance);
		}
		ridingMammothPrev = ridingMammoth;
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
				String keyUpName = GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_DRAGON_UP.getKeyCode());
				String keyDownName = GameSettings.getKeyDisplayString(GOTKeyHandler.KEY_BINDING_DRAGON_DOWN.getKeyCode());
				MC.ingameGUI.func_110326_a(I18n.format("dragon.mountNotice", keyUpName, keyDownName), false);
			}
		}
		ridingDragonPrev = ridingDragon;
	}
}