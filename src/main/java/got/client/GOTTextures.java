package got.client;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.client.gui.GOTGuiMap;
import got.client.render.other.GOTBufferedImageIcon;
import got.common.GOTConfig;
import got.common.GOTDimension;
import got.common.util.GOTColorUtil;
import got.common.util.GOTCommonIcons;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosHell;
import got.common.world.genlayer.GOTGenLayerWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GOTTextures implements IResourceManagerReloadListener {
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ResourceLocation missingTexture = mc.getTextureManager().getDynamicTextureLocation("got.missingSkin", TextureUtil.missingTexture);
	public static ResourceLocation mapTexture;
	public static ResourceLocation sepiaMapTexture;
	public static ResourceLocation overlayTexture = new ResourceLocation("got:textures/map/mapOverlay.png");
	public static ResourceLocation mapTerrain = new ResourceLocation("got:textures/map/terrain.png");
	public static ResourceLocation osrsTexture = new ResourceLocation("got:textures/map/osrs.png");
	public static int OSRS_WATER = 6453158;
	public static int OSRS_GRASS = 5468426;
	public static int OSRS_BEACH = 9279778;
	public static int OSRS_HILL = 6575407;
	public static int OSRS_MOUNTAIN = 14736861;
	public static int OSRS_MOUNTAIN_EDGE = 9005125;
	public static int OSRS_SNOW = 14215139;
	public static int OSRS_TUNDRA = 9470587;
	public static int OSRS_SAND = 13548147;
	public static int OSRS_TREE = 2775058;
	public static int OSRS_WILD = 3290677;
	public static int OSRS_PATH = 6575407;
	public static int OSRS_KINGDOM_COLOR = 16755200;
	public static ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	public static ResourceLocation newWaterParticles = new ResourceLocation("got:textures/misc/waterParticles.png");
	public static int newWaterU;
	public static int newWaterV = 8;
	public static int newWaterWidth = 64;
	public static int newWaterHeight = 8;
	public static Map<ResourceLocation, ResourceLocation> eyesTextures = new HashMap<>();
	public static Map<ResourceLocation, Integer> averagedPageColors = new HashMap<>();

	public static int computeAverageFactionPageColor(ResourceLocation texture, int u0, int v0, int u1, int v1) {
		if (!averagedPageColors.containsKey(texture)) {
			int avgColor;
			try {
				BufferedImage pageImage = ImageIO.read(mc.getResourceManager().getResource(texture).getInputStream());
				long totalR = 0L;
				long totalG = 0L;
				long totalB = 0L;
				long totalA = 0L;
				int count = 0;
				for (int u = u0; u < u1; ++u) {
					for (int v = v0; v < v1; ++v) {
						int rgb = pageImage.getRGB(u, v);
						Color color = new Color(rgb);
						totalR += color.getRed();
						totalG += color.getGreen();
						totalB += color.getBlue();
						totalA += color.getAlpha();
						++count;
					}
				}
				int avgR = (int) (totalR / count);
				int avgG = (int) (totalG / count);
				int avgB = (int) (totalB / count);
				int avgA = (int) (totalA / count);
				avgColor = new Color(avgR, avgG, avgB, avgA).getRGB();
			} catch (IOException e) {
				FMLLog.severe("Hummel009: Failed to generate average page colour");
				e.printStackTrace();
				avgColor = 0;
			}
			averagedPageColors.put(texture, avgColor);
			return avgColor;
		}
		return averagedPageColors.get(texture);
	}

	public static ResourceLocation convertToSepia(BufferedImage srcImage, ResourceLocation resourceLocation) {
		int mapWidth = srcImage.getWidth();
		int mapHeight = srcImage.getHeight();
		int[] colors = srcImage.getRGB(0, 0, mapWidth, mapHeight, null, 0, mapWidth);
		for (int i = 0; i < colors.length; ++i) {
			int color = colors[i];
			if (GOTConfig.osrsMap) {
				Integer biomeID = GOTDimension.GAME_OF_THRONES.colorsToBiomeIDs.get(color);
				if (biomeID == null) {
					color = getMapOceanColor(true);
				} else {
					GOTBiome biome = GOTDimension.GAME_OF_THRONES.biomeList[biomeID];
					if (biome.getHeightBaseParameter() < 0.0f) {
						color = 6453158;
					} else if (biome.getHeightBaseParameter() > 0.8f) {
						color = 14736861;
					} else if (biome.getHeightBaseParameter() > 0.4f) {
						color = 6575407;
					} else if (biome instanceof GOTBiomeSothoryosHell) {
						color = 3290677;
					} else if (biome.decorator.treesPerChunk > 1) {
						color = 2775058;
					} else if (biome.temperature < 0.3f) {
						if (biome.temperature < 0.2f) {
							color = 14215139;
						} else {
							color = 9470587;
						}
					} else if (biome.rainfall < 0.2f) {
						color = 13548147;
					} else {
						color = 5468426;
					}
				}
			} else {
				color = getSepia(color);
			}
			colors[i] = color;
		}
		BufferedImage newMapImage = new BufferedImage(mapWidth, mapHeight, 2);
		newMapImage.setRGB(0, 0, mapWidth, mapHeight, colors, 0, mapWidth);
		if (GOTConfig.osrsMap) {
			BufferedImage temp = newMapImage;
			newMapImage = new BufferedImage(mapWidth, mapHeight, 2);
			for (int i = 0; i < mapWidth; ++i) {
				for (int j = 0; j < mapHeight; ++j) {
					int range;
					int y1;
					int rgb1;
					int y;
					int total;
					int x1;
					int x;
					int rgb = temp.getRGB(i, j);
					if (rgb == 5468426) {
						range = 8;
						int water = 0;
						total = 0;
						for (x = -range; x < range; ++x) {
							for (y = -range; y < range; ++y) {
								x1 = i + x;
								y1 = y + j;
								if (x1 >= 0 && x1 < mapWidth && y1 >= 0 && y1 < mapHeight) {
									rgb1 = temp.getRGB(x1, y1);
									if (rgb1 == 6453158) {
										++water;
									}
									++total;
								}
							}
						}
						if (water > 0) {
							float ratio = (float) water / total;
							rgb = GOTColorUtil.lerpColors_I(5468426, 9279778, ratio * 2.0f);
						}
					} else if (rgb == 14736861) {
						range = 8;
						int edge = 0;
						total = 0;
						for (x = -range; x < range; ++x) {
							for (y = -range; y < range; ++y) {
								x1 = i + x;
								y1 = y + j;
								if (x1 >= 0 && x1 < mapWidth && y1 >= 0 && y1 < mapHeight) {
									rgb1 = temp.getRGB(x1, y1);
									if (rgb1 != 14736861) {
										++edge;
									}
									++total;
								}
							}
						}
						if (edge > 0) {
							float ratio = (float) edge / total;
							rgb = GOTColorUtil.lerpColors_I(14736861, 9005125, ratio * 1.5f);
						}
					}
					newMapImage.setRGB(i, j, rgb | 0xFF000000);
				}
			}
		}
		mc.renderEngine.loadTexture(resourceLocation, new DynamicTexture(newMapImage));
		return resourceLocation;
	}

	public static void drawMap(EntityPlayer entityplayer, boolean sepia, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV, float alpha) {
		Tessellator tessellator = Tessellator.instance;
		mc.getTextureManager().bindTexture(getMapTexture(entityplayer, sepia));
		GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x0, y1, z, minU, maxV);
		tessellator.addVertexWithUV(x1, y1, z, maxU, maxV);
		tessellator.addVertexWithUV(x1, y0, z, maxU, minV);
		tessellator.addVertexWithUV(x0, y0, z, minU, minV);
		tessellator.draw();
		if (true) {
			int mtX = 0;
			int mtY = 0;
			int mtW = 20;
			double mtMinU = (double) (mtX - mtW) / GOTGenLayerWorld.imageWidth;
			double mtMaxU = (double) (mtX + mtW) / GOTGenLayerWorld.imageWidth;
			double mtMinV = (double) (mtY - mtW) / GOTGenLayerWorld.imageHeight;
			double mtMaxV = (double) (mtY + mtW) / GOTGenLayerWorld.imageHeight;
			if (minU <= mtMaxU && maxU >= mtMinU && minV <= mtMaxV && maxV >= mtMinV) {
				GL11.glDisable(3553);
				int oceanColor = getMapOceanColor(sepia);
				mtMinU = Math.max(mtMinU, minU);
				mtMaxU = Math.min(mtMaxU, maxU);
				mtMinV = Math.max(mtMinV, minV);
				mtMaxV = Math.min(mtMaxV, maxV);
				double ratioX = (x1 - x0) / (maxU - minU);
				double ratioY = (y1 - y0) / (maxV - minV);
				double mtX0 = x0 + (mtMinU - minU) * ratioX;
				double mtX1 = x0 + (mtMaxU - minU) * ratioX;
				double mtY0 = y0 + (mtMinV - minV) * ratioY;
				double mtY1 = y0 + (mtMaxV - minV) * ratioY;
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(oceanColor);
				tessellator.addVertexWithUV(mtX0, mtY1, z, mtMinU, mtMaxV);
				tessellator.addVertexWithUV(mtX1, mtY1, z, mtMaxU, mtMaxV);
				tessellator.addVertexWithUV(mtX1, mtY0, z, mtMaxU, mtMinV);
				tessellator.addVertexWithUV(mtX0, mtY0, z, mtMinU, mtMinV);
				tessellator.draw();
				GL11.glEnable(3553);
			}
		}
	}

	public static void drawMap(EntityPlayer entityplayer, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV) {
		drawMap(entityplayer, GOTConfig.enableSepiaMap, x0, x1, y0, y1, z, minU, maxU, minV, maxV, 1.0f);
	}

	public static void drawMapCompassBottomLeft(double x, double y, double z, double scale) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GOTGuiMap.mapIconsTexture);
		int width = 32;
		int height = 32;
		double x1 = x + width * scale;
		double y0 = y - height * scale;
		int texU = 224;
		int texV = 200;
		float u0 = texU / 256.0f;
		float u1 = (texU + width) / 256.0f;
		float v0 = texV / 256.0f;
		float v1 = (texV + height) / 256.0f;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y, z, u0, v1);
		tessellator.addVertexWithUV(x1, y, z, u1, v1);
		tessellator.addVertexWithUV(x1, y0, z, u1, v0);
		tessellator.addVertexWithUV(x, y0, z, u0, v0);
		tessellator.draw();
	}

	public static void drawMapOverlay(EntityPlayer entityplayer, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV) {
		Tessellator tessellator = Tessellator.instance;
		mc.getTextureManager().bindTexture(overlayTexture);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x0, y1, z, 0.0, 1.0);
		tessellator.addVertexWithUV(x1, y1, z, 1.0, 1.0);
		tessellator.addVertexWithUV(x1, y0, z, 1.0, 0.0);
		tessellator.addVertexWithUV(x0, y0, z, 0.0, 0.0);
		tessellator.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
	}

	public static int findContrastingColor(int text, int bg) {
		Color cText = new Color(text);
		Color cBg = new Color(bg);
		float[] hsbText = Color.RGBtoHSB(cText.getRed(), cText.getGreen(), cText.getBlue(), null);
		float[] hsbBg = Color.RGBtoHSB(cBg.getRed(), cBg.getGreen(), cBg.getBlue(), null);
		float bText = hsbText[2];
		float bBg = hsbBg[2];
		float limit = 0.4f;
		if (Math.abs(bText - bBg) < limit) {
			if (bBg > 0.66f) {
				bText = bBg - limit;
			} else {
				bText = bBg + limit;
			}
		}
		return Color.HSBtoRGB(hsbText[0], hsbText[1], bText);
	}

	public static IIcon generateIconEmpty(TextureMap textureMap) {
		String iconName = "textures/blocks/GOT_EMPTY_ICON";
		int size = 16;
		BufferedImage iconImage = new BufferedImage(size, size, 2);
		for (int i = 0; i < iconImage.getWidth(); ++i) {
			for (int j = 0; j < iconImage.getHeight(); ++j) {
				int rgb = 0;
				int alpha = 0;
				iconImage.setRGB(i, j, rgb |= alpha);
			}
		}
		GOTBufferedImageIcon icon = new GOTBufferedImageIcon(iconName, iconImage);
		icon.setIconWidth(iconImage.getWidth());
		icon.setIconHeight(iconImage.getHeight());
		textureMap.setTextureEntry(iconName, icon);
		return icon;
	}

	public static ResourceLocation getEyesTexture(ResourceLocation skin, int[][] eyesCoords, int eyeWidth, int eyeHeight) {
		ResourceLocation eyes = eyesTextures.get(skin);
		if (eyes == null) {
			try {
				BufferedImage skinImage = ImageIO.read(mc.getResourceManager().getResource(skin).getInputStream());
				int skinWidth = skinImage.getWidth();
				int skinHeight = skinImage.getHeight();
				BufferedImage eyesImage = new BufferedImage(skinWidth, skinHeight, 2);
				for (int[] eyePos : eyesCoords) {
					int eyeX = eyePos[0];
					int eyeY = eyePos[1];
					for (int i = eyeX; i < eyeX + eyeWidth; ++i) {
						for (int j = eyeY; j < eyeY + eyeHeight; ++j) {
							int rgb = skinImage.getRGB(i, j);
							eyesImage.setRGB(i, j, rgb);
						}
					}
				}
				eyes = mc.renderEngine.getDynamicTextureLocation(skin.toString() + "_eyes_" + eyeWidth + "_" + eyeHeight, new DynamicTexture(eyesImage));
			} catch (IOException e) {
				GOTLog.logger.error("Failed to generate eyes skin");
				e.printStackTrace();
				eyes = missingTexture;
			}
			eyesTextures.put(skin, eyes);
		}
		return eyes;
	}

	public static int getMapOceanColor(boolean sepia) {
		if (GOTConfig.osrsMap) {
			return -10324058;
		}
		int ocean = GOTBiome.ocean.color;
		if (sepia) {
			ocean = getSepia(ocean);
		}
		return ocean;
	}

	public static ResourceLocation getMapTexture(EntityPlayer entityplayer, boolean sepia) {
		if (GOTConfig.osrsMap || sepia) {
			return sepiaMapTexture;
		}
		return mapTexture;
	}

	public static int getSepia(int rgb) {
		Color color = new Color(rgb);
		int alpha = rgb >> 24 & 0xFF;
		float[] colors = color.getColorComponents(null);
		float r = colors[0];
		float g = colors[1];
		float b = colors[2];
		float newR = r * 0.79f + g * 0.39f + b * 0.26f;
		float newG = r * 0.52f + g * 0.35f + b * 0.19f;
		float newB = r * 0.35f + g * 0.26f + b * 0.15f;
		newR = Math.min(Math.max(0.0f, newR), 1.0f);
		newG = Math.min(Math.max(0.0f, newG), 1.0f);
		newB = Math.min(Math.max(0.0f, newB), 1.0f);
		int sepia = new Color(newR, newG, newB).getRGB();
		return sepia |= alpha << 24;
	}

	public static void loadMapTextures() {
		mapTexture = new ResourceLocation("got:textures/map/map.png");
		try {
			BufferedImage mapImage = ImageIO.read(mc.getResourceManager().getResource(mapTexture).getInputStream());
			sepiaMapTexture = convertToSepia(mapImage, new ResourceLocation("got:textures/map_sepia"));
		} catch (IOException e) {
			FMLLog.severe("Failed to generate GOT sepia map");
			e.printStackTrace();
			sepiaMapTexture = mapTexture;
		}
	}

	public static void onInit() {
		IResourceManager resMgr = mc.getResourceManager();
		TextureManager texMgr = mc.getTextureManager();
		GOTTextures textures = new GOTTextures();
		textures.onResourceManagerReload(resMgr);
		((IReloadableResourceManager) resMgr).registerReloadListener(textures);
		MinecraftForge.EVENT_BUS.register(textures);
		TextureMap texMapBlocks = (TextureMap) texMgr.getTexture(TextureMap.locationBlocksTexture);
		TextureMap texMapItems = (TextureMap) texMgr.getTexture(TextureMap.locationItemsTexture);
		textures.preTextureStitch(new TextureStitchEvent.Pre(texMapBlocks));
		textures.preTextureStitch(new TextureStitchEvent.Pre(texMapItems));
	}

	public static void replaceWaterParticles() {
		try {
			BufferedImage particles = ImageIO.read(mc.getResourcePackRepository().rprDefaultResourcePack.getInputStream(particleTextures));
			BufferedImage waterParticles = ImageIO.read(mc.getResourceManager().getResource(newWaterParticles).getInputStream());
			int[] rgb = waterParticles.getRGB(0, 0, waterParticles.getWidth(), waterParticles.getHeight(), null, 0, waterParticles.getWidth());
			particles.setRGB(newWaterU, newWaterV, newWaterWidth, newWaterHeight, rgb, 0, newWaterWidth);
			TextureManager textureManager = mc.getTextureManager();
			textureManager.bindTexture(particleTextures);
			AbstractTexture texture = (AbstractTexture) textureManager.getTexture(particleTextures);
			TextureUtil.uploadTextureImageAllocate(texture.getGlTextureId(), particles, false, false);
		} catch (IOException e) {
			FMLLog.severe("Failed to replace rain particles");
			e.printStackTrace();
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		loadMapTextures();
		replaceWaterParticles();
		eyesTextures.clear();
		averagedPageColors.clear();
	}

	@SubscribeEvent
	public void preTextureStitch(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 0) {
			GOTCommonIcons.iconEmptyBlock = generateIconEmpty(map);
			GOTCommonIcons.iconStoneSnow = map.registerIcon("stone_snow");
		}
		if (map.getTextureType() == 1) {
			GOTCommonIcons.iconEmptyItem = generateIconEmpty(map);
			GOTCommonIcons.iconMeleeWeapon = map.registerIcon("got:slot_melee");
			GOTCommonIcons.iconBomb = map.registerIcon("got:slot_bomb");
		}
	}
}
