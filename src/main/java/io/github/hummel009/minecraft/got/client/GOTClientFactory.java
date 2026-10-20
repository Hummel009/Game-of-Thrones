package io.github.hummel009.minecraft.got.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import io.github.hummel009.minecraft.got.client.effect.GOTEffectRenderer;
import io.github.hummel009.minecraft.got.client.gui.GOTGuiMiniquestTracker;
import io.github.hummel009.minecraft.got.client.gui.GOTGuiNotificationDisplay;
import io.github.hummel009.minecraft.got.client.render.other.GOTRenderPlayer;
import io.github.hummel009.minecraft.got.client.render.other.GOTSwingHandler;
import io.github.hummel009.minecraft.got.client.sound.GOTAmbience;
import io.github.hummel009.minecraft.got.client.sound.GOTMusic;
import io.github.hummel009.minecraft.got.common.data.GOTArmorModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class GOTClientFactory {
	private static GOTAmbience ambienceTicker;
	private static GOTArmorModels armorModels;
	private static GOTEffectRenderer effectRenderer;
	private static GOTGuiEventHandler guiEventHandler;
	private static GOTGuiMiniquestTracker miniquestTracker;
	private static GOTGuiNotificationDisplay notificationDisplay;
	private static GOTItemRendererManager itemRendererManager;
	private static GOTKeyHandler keyHandler;
	private static GOTMusic musicHandler;
	private static GOTRenderPlayer renderPlayer;
	private static GOTSwingHandler swingHandler;
	private static GOTThirdPersonViewer thirdPersonViewer;
	private static GOTTickHandlerClient tickHandlerClient;
	private static GOTTextures textures;

	private GOTClientFactory() {
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

		guiEventHandler = GOTGuiEventHandler.INSTANCE;
		fmlBus.register(guiEventHandler);
		forgeBus.register(guiEventHandler);

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

		textures = GOTTextures.INSTANCE;
		textures.onResourceManagerReload(resourceManager);
		((IReloadableResourceManager) resourceManager).registerReloadListener(textures);
		forgeBus.register(textures);

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

	public static GOTGuiEventHandler getGuiEventHandler() {
		return guiEventHandler;
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

	public static GOTTextures getTextures() {
		return textures;
	}
}