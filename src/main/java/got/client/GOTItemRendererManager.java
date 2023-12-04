package got.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.render.other.*;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemAnimalJar;
import got.common.item.weapon.GOTItemBow;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Collection;

public class GOTItemRendererManager implements IResourceManagerReloadListener {
	public static GOTItemRendererManager INSTANCE;
	public static Collection<GOTRenderLargeItem> largeItemRenderers = new ArrayList<>();

	public static void preInit() {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resMgr = mc.getResourceManager();
		INSTANCE = new GOTItemRendererManager();
		INSTANCE.onResourceManagerReload(resMgr);
		((IReloadableResourceManager) resMgr).registerReloadListener(INSTANCE);
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		largeItemRenderers.clear();
		try {
			for (Item item : GOTItems.CONTENT) {
				MinecraftForgeClient.registerItemRenderer(item, null);
				GOTRenderLargeItem largeItemRenderer = GOTRenderLargeItem.getRendererIfLarge(item);
				boolean isLarge = largeItemRenderer != null;
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
				if (largeItemRenderer != null) {
					largeItemRenderers.add(largeItemRenderer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GOTBlocks.commandTable), new GOTRenderInvTableCommand());
		MinecraftForgeClient.registerItemRenderer(GOTItems.pipe, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.commandHorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.conquestHorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.banner, new GOTRenderBannerItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.skullStaff, new GOTRenderSkullStaff());
		for (Item item : (Iterable<Item>) Item.itemRegistry) {
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
}
