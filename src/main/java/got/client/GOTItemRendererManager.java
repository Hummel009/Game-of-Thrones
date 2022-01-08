package got.client;

import java.lang.reflect.Field;
import java.util.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.render.other.*;
import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemAnimalJar;
import got.common.item.weapon.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class GOTItemRendererManager implements IResourceManagerReloadListener {
	public static GOTItemRendererManager INSTANCE;
	public static List<GOTRenderLargeItem> largeItemRenderers;

	static {
		largeItemRenderers = new ArrayList<>();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		largeItemRenderers.clear();
		try {
			for (Field field : GOTRegistry.class.getFields()) {
				boolean isLarge;
				if (!(field.get(null) instanceof Item)) {
					continue;
				}
				Item item = (Item) field.get(null);
				MinecraftForgeClient.registerItemRenderer(item, null);
				GOTRenderLargeItem largeItemRenderer = GOTRenderLargeItem.getRendererIfLarge(item);
				isLarge = largeItemRenderer != null;
				if (item instanceof GOTItemCrossbow) {
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderCrossbow());
				} else if (item instanceof GOTItemBow) {
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderBow(largeItemRenderer));
				} else if (item instanceof GOTItemSword && ((GOTItemSword) item).isGlowing()) {
					double d = 24.0;
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderBlade(d, largeItemRenderer));
				} else if (isLarge) {
					MinecraftForgeClient.registerItemRenderer(item, largeItemRenderer);
				}
				if (largeItemRenderer == null) {
					continue;
				}
				largeItemRenderers.add(largeItemRenderer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GOTRegistry.commandTable), new GOTRenderInvTableCommand());
		MinecraftForgeClient.registerItemRenderer(GOTRegistry.pipe, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTRegistry.commandHorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTRegistry.warhorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTRegistry.banner, new GOTRenderBannerItem());
		MinecraftForgeClient.registerItemRenderer(GOTRegistry.skullStaff, new GOTRenderSkullStaff());
		Iterator<Item> itItems = Item.itemRegistry.iterator();
		while (itItems.hasNext()) {
			Item item = itItems.next();
			if (item instanceof GOTItemAnimalJar) {
				MinecraftForgeClient.registerItemRenderer(item, new GOTRenderAnimalJar());
			}
		}
	}

	@SubscribeEvent
	public void preTextureStitch(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 1) {
			for (GOTRenderLargeItem largeRenderer : largeItemRenderers) {
				largeRenderer.registerIcons(map);
			}
		}
	}

	public static void preInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resMgr = mc.getResourceManager();
		INSTANCE = new GOTItemRendererManager();
		INSTANCE.onResourceManagerReload(resMgr);
		((IReloadableResourceManager) resMgr).registerReloadListener(INSTANCE);
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}
}
