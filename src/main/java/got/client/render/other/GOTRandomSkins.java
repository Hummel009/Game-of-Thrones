package got.client.render.other;

import cpw.mods.fml.common.FMLLog;
import got.client.GOTTextures;
import got.common.entity.other.GOTRandomSkinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GOTRandomSkins implements IResourceManagerReloadListener {
	public static Random rand = new Random();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static Map<String, GOTRandomSkins> allRandomSkins = new HashMap<>();
	public String skinPath;
	public List<ResourceLocation> skins;

	public GOTRandomSkins(String path, boolean register, Object... args) {
		skinPath = path;
		handleExtraArgs(args);
		if (register) {
			((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(this);
		} else {
			loadAllRandomSkins();
		}
	}

	public static GOTRandomSkins loadSkinsList(String path) {
		GOTRandomSkins skins = allRandomSkins.get(path);
		if (skins == null) {
			skins = new GOTRandomSkins(path, true);
			allRandomSkins.put(path, skins);
		}
		return skins;
	}

	public List<ResourceLocation> getAllSkins() {
		return skins;
	}

	public ResourceLocation getRandomSkin() {
		int i = rand.nextInt(skins.size());
		return skins.get(i);
	}

	public ResourceLocation getRandomSkin(GOTRandomSkinEntity rsEntity) {
		if (skins == null || skins.isEmpty()) {
			return GOTTextures.missingTexture;
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
			} else {
				skins.add(skin);
				++skinCount;
				if (skips > 0) {
					foundAfterSkip = true;
				}
			}
		} while (true);
		if (skins.isEmpty()) {
			FMLLog.warning("Hummel009: No random skins for %s", skinPath);
		}
		if (foundAfterSkip) {
			FMLLog.warning("Hummel009: Random skins %s skipped a number. This is not good - please number your skins from 0 and upwards, with no gaps!", skinPath);
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourcemanager) {
		loadAllRandomSkins();
	}

	public static class GOTRandomSkinsCombinatorial extends GOTRandomSkins {
		public String[] skinLayers;

		public GOTRandomSkinsCombinatorial(String path, String... layers) {
			super(path, true, (Object[]) layers);
		}

		@Override
		public void handleExtraArgs(Object... args) {
			skinLayers = (String[]) args;
		}

		@Override
		public void loadAllRandomSkins() {
			skins = new ArrayList<>();
			ArrayList<BufferedImage> layeredImages = new ArrayList<>();
			ArrayList<BufferedImage> tempLayered = new ArrayList<>();
			block2:
			for (String layer : skinLayers) {
				String layerPath = skinPath + "/" + layer;
				GOTRandomSkins layerSkins = new GOTRandomSkins(layerPath, false);
				tempLayered.clear();
				for (ResourceLocation overlay : layerSkins.getAllSkins()) {
					try {
						BufferedImage overlayImage = ImageIO.read(mc.getResourceManager().getResource(overlay).getInputStream());
						if (layeredImages.isEmpty()) {
							tempLayered.add(overlayImage);
							continue;
						}
						for (BufferedImage baseImage : layeredImages) {
							int skinWidth = baseImage.getWidth();
							int skinHeight = baseImage.getHeight();
							BufferedImage newImage = new BufferedImage(skinWidth, skinHeight, 2);
							for (int i = 0; i < skinWidth; ++i) {
								for (int j = 0; j < skinHeight; ++j) {
									int opaqueTest;
									int baseRGB = baseImage.getRGB(i, j);
									int overlayRGB = overlayImage.getRGB(i, j);
									opaqueTest = -16777216;
									if ((overlayRGB & opaqueTest) == opaqueTest) {
										newImage.setRGB(i, j, overlayRGB);
									} else {
										newImage.setRGB(i, j, baseRGB);
									}
								}
							}
							tempLayered.add(newImage);
						}
					} catch (IOException e) {
						FMLLog.severe("Hummel009: Error combining skins " + skinPath + " on layer " + layer + "!");
						e.printStackTrace();
						break block2;
					}
				}
				layeredImages.clear();
				layeredImages.addAll(tempLayered);
			}
			int skinCount = 0;
			for (BufferedImage image : layeredImages) {
				ResourceLocation skin = mc.renderEngine.getDynamicTextureLocation(skinPath + "_layered/" + skinCount + ".png", new DynamicTexture(image));
				skins.add(skin);
				++skinCount;
			}
			FMLLog.info("Hummel009: Assembled combinatorial skins for " + skinPath + ": " + skins.size() + " skins");
		}
	}

}
