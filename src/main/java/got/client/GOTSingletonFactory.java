package got.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import got.client.effect.GOTEffectRenderer;
import got.client.gui.GOTGuiMiniquestTracker;
import got.client.gui.GOTGuiNotificationDisplay;
import got.client.render.other.GOTRenderPlayer;
import got.client.render.other.GOTSwingHandler;
import got.client.sound.GOTAmbience;
import got.client.sound.GOTMusic;
import got.common.database.GOTArmorModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings({"WeakerAccess", "unused"})
public class GOTSingletonFactory {
	private static GOTAmbience ambienceTicker;
	private static GOTArmorModels armorModels;
	private static GOTEffectRenderer effectRenderer;
	private static GOTGuiHandler guiHandler;
	private static GOTGuiMiniquestTracker miniquestTracker;
	private static GOTGuiNotificationDisplay notificationDisplay;
	private static GOTItemRendererManager itemRendererManager;
	private static GOTKeyHandler keyHandler;
	private static GOTMusic musicHandler;
	private static GOTRenderPlayer renderPlayer;
	private static GOTSwingHandler swingHandler;
	private static GOTThirdPersonViewer thirdPersonViewer;
	private static GOTTickHandlerClient tickHandlerClient;

	private GOTSingletonFactory() {
	}

	public static void preInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		EventBus forgeBus = MinecraftForge.EVENT_BUS;
		EventBus fmlBus = FMLCommonHandler.instance().bus();

		renderPlayer = GOTRenderPlayer.INSTANCE;
		fmlBus.register(renderPlayer);
		forgeBus.register(renderPlayer);

		swingHandler = GOTSwingHandler.INSTANCE;
		fmlBus.register(swingHandler);
		forgeBus.register(swingHandler);

		tickHandlerClient = GOTTickHandlerClient.INSTANCE;
		fmlBus.register(tickHandlerClient);
		forgeBus.register(tickHandlerClient);

		miniquestTracker = GOTGuiMiniquestTracker.INSTANCE;
		notificationDisplay = GOTGuiNotificationDisplay.INSTANCE;

		ambienceTicker = GOTAmbience.INSTANCE;
		fmlBus.register(ambienceTicker);
		forgeBus.register(ambienceTicker);

		guiHandler = GOTGuiHandler.INSTANCE;
		fmlBus.register(guiHandler);
		forgeBus.register(guiHandler);

		itemRendererManager = GOTItemRendererManager.INSTANCE;
		itemRendererManager.onResourceManagerReload(resourceManager);
		((IReloadableResourceManager) resourceManager).registerReloadListener(itemRendererManager);
		forgeBus.register(itemRendererManager);

		armorModels = GOTArmorModels.INSTANCE;
		forgeBus.register(armorModels);
	}

	public static void onInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		EventBus forgeBus = MinecraftForge.EVENT_BUS;
		EventBus fmlBus = FMLCommonHandler.instance().bus();

		effectRenderer = GOTEffectRenderer.INSTANCE;

		thirdPersonViewer = GOTThirdPersonViewer.INSTANCE;
		fmlBus.register(thirdPersonViewer);

		keyHandler = GOTKeyHandler.INSTANCE;
		fmlBus.register(keyHandler);
	}

	public static void postInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		EventBus forgeBus = MinecraftForge.EVENT_BUS;
		EventBus fmlBus = FMLCommonHandler.instance().bus();

		musicHandler = GOTMusic.INSTANCE;
		((IReloadableResourceManager) resourceManager).registerReloadListener(musicHandler);
		forgeBus.register(musicHandler);
	}

	public static GOTAmbience getAmbienceTicker() {
		return ambienceTicker;
	}

	public static GOTArmorModels getArmorModels() {
		return armorModels;
	}

	public static GOTEffectRenderer getEffectRenderer() {
		return effectRenderer;
	}

	public static GOTGuiHandler getGuiHandler() {
		return guiHandler;
	}

	public static GOTGuiMiniquestTracker getMiniquestTracker() {
		return miniquestTracker;
	}

	public static GOTGuiNotificationDisplay getNotificationDisplay() {
		return notificationDisplay;
	}

	public static GOTItemRendererManager getItemRendererManager() {
		return itemRendererManager;
	}

	public static GOTKeyHandler getKeyHandler() {
		return keyHandler;
	}

	public static GOTMusic getMusicHandler() {
		return musicHandler;
	}

	public static GOTRenderPlayer getRenderPlayer() {
		return renderPlayer;
	}

	public static GOTSwingHandler getSwingHandler() {
		return swingHandler;
	}

	public static GOTThirdPersonViewer getThirdPersonViewer() {
		return thirdPersonViewer;
	}

	public static GOTTickHandlerClient getTickHandlerClient() {
		return tickHandlerClient;
	}
}