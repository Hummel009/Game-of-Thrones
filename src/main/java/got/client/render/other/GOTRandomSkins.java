package got.client.render.other;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.client.GOTTextures;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GOTRandomSkins implements IResourceManagerReloadListener {
	private static Random rand = new Random();
	private static Minecraft mc = Minecraft.getMinecraft();
	private static Map<String, GOTRandomSkins> allRandomSkins = new HashMap<>();
	public String skinPath;
	public List<ResourceLocation> skins;

	private GOTRandomSkins(String path, boolean register, Object... args) {
		skinPath = path;
		handleExtraArgs(args);
		if (register) {
			((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(this);
		} else {
			loadAllRandomSkins();
		}
	}

	public ResourceLocation getRandomSkin() {
		int i = rand.nextInt(skins.size());
		return skins.get(i);
	}

	public ResourceLocation getRandomSkin(GOTRandomSkinEntity rsEntity) {
		if (skins == null || skins.isEmpty()) {
			return GOTTextures.getMissingTexture();
		}
		Entity entity = (Entity) rsEntity;
		long l = entity.getUniqueID().getLeastSignificantBits();
		long hash = skinPath.hashCode();
		l = l * 39603773L ^ l * 6583690632L ^ hash;
		l = l * hash * 2906920L + l * 65936063L;
		rand.setSeed(l);
		int i = rand.nextInt(skins.size());
		return skins.get(i);
	}

	public void handleExtraArgs(Object... args) {
	}

	public void loadAllRandomSkins() {
		skins = new ArrayList<>();
		int skinCount = 0;
		int skips = 0;
		int maxSkips = 10;
		boolean foundAfterSkip = false;
		do {
			ResourceLocation skin = new ResourceLocation(skinPath + "/" + skinCount + ".png");
			boolean noFile = false;
			try {
				if (mc.getResourceManager().getResource(skin) == null) {
					noFile = true;
				}
			} catch (Exception e) {
				noFile = true;
			}
			if (noFile) {
				skips++;
				if (skips >= maxSkips) {
					break;
				}
				++skinCount;
				continue;
			}
			skins.add(skin);
			++skinCount;
			if (skips <= 0) {
				continue;
			}
			foundAfterSkip = true;
		} while (true);
		if (skins.isEmpty()) {
			FMLLog.warning("GOT: No random skins for %s", skinPath);
		}
		if (foundAfterSkip) {
			FMLLog.warning("GOT: Random skins %s skipped a number. This is not good - please number your skins from 0 and upwards, with no gaps!", skinPath);
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadAllRandomSkins();
	}

	public static GOTRandomSkins loadSkinsList(String path) {
		GOTRandomSkins skins = allRandomSkins.get(path);
		if (skins == null) {
			skins = new GOTRandomSkins(path, true);
			allRandomSkins.put(path, skins);
		}
		return skins;
	}

}
