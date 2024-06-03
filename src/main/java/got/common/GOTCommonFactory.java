package got.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import got.GOT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class GOTCommonFactory {
	private static GOTEventHandler eventHandler;
	private static GOTFuelHandler fuelHandler;
	private static GOTGuiHandler guiHandler;
	private static GOTTickHandlerServer tickHandlerServer;

	private GOTCommonFactory() {
	}

	public static void preInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		EventBus forgeBus = MinecraftForge.EVENT_BUS;
		EventBus terrainBus = MinecraftForge.TERRAIN_GEN_BUS;
		EventBus fmlBus = FMLCommonHandler.instance().bus();

		guiHandler = GOTGuiHandler.INSTANCE;
		NetworkRegistry.INSTANCE.registerGuiHandler(GOT.instance, guiHandler);

		eventHandler = GOTEventHandler.INSTANCE;
		fmlBus.register(eventHandler);
		forgeBus.register(eventHandler);
		terrainBus.register(eventHandler);

		fuelHandler = GOTFuelHandler.INSTANCE;
		GameRegistry.registerFuelHandler(fuelHandler);

		tickHandlerServer = GOTTickHandlerServer.INSTANCE;
		fmlBus.register(tickHandlerServer);
	}

	public static GOTEventHandler getEventHandler() {
		return eventHandler;
	}

	public static IFuelHandler getFuelHandler() {
		return fuelHandler;
	}

	public static GOTGuiHandler getGuiHandler() {
		return guiHandler;
	}

	public static GOTTickHandlerServer getTickHandlerServer() {
		return tickHandlerServer;
	}
}