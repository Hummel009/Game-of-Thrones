package got.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.render.other.*;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemAnimalJar;
import got.common.item.weapon.GOTItemBow;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class GOTItemRendererManager implements IResourceManagerReloadListener {
	public static final GOTItemRendererManager INSTANCE = new GOTItemRendererManager();

	private static final Collection<GOTRenderLargeItem> LARGE_ITEM_RENDERERS = new ArrayList<>();

	private GOTItemRendererManager() {
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		LARGE_ITEM_RENDERERS.clear();
		try {
			Collection<Item> items = new HashSet<>();
			items.add(Items.iron_sword);
			items.add(Items.golden_sword);
			items.add(Items.diamond_sword);
			items.add(Items.stone_sword);
			items.add(Items.wooden_sword);

			for (Item item : items) {
				VanillaRenderLargeItem largeItemRenderer = VanillaRenderLargeItem.getRendererIfLarge(item);
				if (largeItemRenderer != null) {
					MinecraftForgeClient.registerItemRenderer(item, largeItemRenderer);
				}
			}

			for (Item item : GOTItems.CONTENT) {
				MinecraftForgeClient.registerItemRenderer(item, null);
				GOTRenderLargeItem largeItemRenderer = GOTRenderLargeItem.getRendererIfLarge(item);
				boolean isLarge = largeItemRenderer != null;
				if (item instanceof GOTItemCrossbow) {
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderCrossbow());
				} else if (item instanceof GOTItemBow) {
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderBow(largeItemRenderer));
				} else if (item instanceof GOTItemSword && ((GOTItemSword) item).isGlowing()) {
					MinecraftForgeClient.registerItemRenderer(item, new GOTRenderBlade(largeItemRenderer));
				} else if (isLarge) {
					MinecraftForgeClient.registerItemRenderer(item, largeItemRenderer);
				}
				if (largeItemRenderer != null) {
					LARGE_ITEM_RENDERERS.add(largeItemRenderer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GOTBlocks.commandTable), new GOTRenderInvTableCommand());
		MinecraftForgeClient.registerItemRenderer(GOTItems.pipe, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.commandHorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.warhorn, new GOTRenderBlownItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.banner, new GOTRenderBannerItem());
		MinecraftForgeClient.registerItemRenderer(GOTItems.skullStaff, new GOTRenderSkullStaff());
		for (Item item : (Iterable<Item>) Item.itemRegistry) {
			if (item instanceof GOTItemAnimalJar) {
				MinecraftForgeClient.registerItemRenderer(item, new GOTRenderAnimalJar());
			}
		}
	}

	@SubscribeEvent
	@SuppressWarnings("MethodMayBeStatic")
	public void preTextureStitch(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 1) {
			for (GOTRenderLargeItem largeRenderer : LARGE_ITEM_RENDERERS) {
				largeRenderer.registerIcons(map);
			}
		}
	}
}