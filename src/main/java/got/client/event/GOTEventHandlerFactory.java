package got.client.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import got.client.event.both.GOTSwingHandler;
import got.client.event.fml.GOTKeyHandler;
import got.client.event.fml.GOTViewHandler;
import got.client.event.forge.GOTGuiHandler;
import got.client.event.forge.GOTRenderPlayer;
import net.minecraftforge.common.MinecraftForge;

public class GOTEventHandlerFactory {
	private static final EventBus FML_BUS = FMLCommonHandler.instance().bus();
	private static final EventBus FORGE_BUS = MinecraftForge.EVENT_BUS;

	private static final GOTRenderPlayer RENDER_PLAYER = new GOTRenderPlayer();
	private static final GOTSwingHandler SWING_HANDLER = new GOTSwingHandler();
	private static final GOTGuiHandler GUI_HANDLER = new GOTGuiHandler();

	private GOTEventHandlerFactory() {
	}

	public static void preInit() {

	}

	public static void onInit() {
		FML_BUS.register(new GOTViewHandler());
		FML_BUS.register(new GOTKeyHandler());

		FML_BUS.register(SWING_HANDLER);
		FORGE_BUS.register(SWING_HANDLER);

		FORGE_BUS.register(RENDER_PLAYER);

		FML_BUS.register(GUI_HANDLER);
		FORGE_BUS.register(GUI_HANDLER);
	}

	public static void postInit() {

	}
}