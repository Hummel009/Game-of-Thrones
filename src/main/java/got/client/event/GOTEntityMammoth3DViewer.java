package got.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import got.common.entity.animal.GOTEntityMammoth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

public class GOTEntityMammoth3DViewer {
	private static final String[] ENTITYRENDERER_THIRDPERSONDISTANCE = {"thirdPersonDistance", "field_78490_B"};

	private final Minecraft mc = Minecraft.getMinecraft();
	private final float defaultThirdPersonDistance;

	private int noticeTicks;
	private boolean ridingDragonPrev;

	public GOTEntityMammoth3DViewer() {
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
		boolean ridingDragon = mc.thePlayer.ridingEntity instanceof GOTEntityMammoth;

		if (ridingDragon && !ridingDragonPrev) {
			setThirdPersonDistance(9);
			noticeTicks = 70;
		} else if (!ridingDragon && ridingDragonPrev) {
			setThirdPersonDistance(defaultThirdPersonDistance);
			noticeTicks = 0;
		} else if (noticeTicks > 0) {
			noticeTicks--;
		}

		ridingDragonPrev = ridingDragon;
	}
}