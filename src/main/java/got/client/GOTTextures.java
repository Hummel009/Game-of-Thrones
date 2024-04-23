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
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GOTTextures implements IResourceManagerReloadListener {
	public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation("got:textures/map/mapOverlay.png");
	public static final ResourceLocation OSRS_TEXTURE = new ResourceLocation("got:textures/map/osrs.png");

	public static final GOTTextures INSTANCE = new GOTTextures();

	private static final Minecraft MC = Minecraft.getMinecraft();

	public static final ResourceLocation MISSING_TEXTURE = MC.getTextureManager().getDynamicTextureLocation("got.missingSkin", TextureUtil.missingTexture);

	private static final ResourceLocation PARTICLE_TEXTURES = new ResourceLocation("textures/particle/particles.png");
	private static final ResourceLocation NEW_WATER_PARTICLES = new ResourceLocation("got:textures/misc/waterParticles.png");

	private static final Map<ResourceLocation, ResourceLocation> EYES_TEXTURES = new HashMap<>();
	private static final Map<ResourceLocation, Integer> AVERAGED_PAGE_COLORS = new HashMap<>();

	private static final int NEW_WATER_U = 0;
	private static final int NEW_WATER_V = 8;
	private static final int NEW_WATER_WIDTH = 64;
	private static final int NEW_WATER_HEIGHT = 8;

	private static ResourceLocation mapTexture;
	private static ResourceLocation sepiaMapTexture;

	private GOTTextures() {
	}

	public static int computeAverageFactionPageColor(ResourceLocation texture, int u0, int v0, int u1, int v1) {
		if (!AVERAGED_PAGE_COLORS.containsKey(texture)) {
			int avgColor;
			try {
				BufferedImage pageImage = ImageIO.read(MC.getResourceManager().getResource(texture).getInputStream());
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
			AVERAGED_PAGE_COLORS.put(texture, avgColor);
			return avgColor;
		}
		return AVERAGED_PAGE_COLORS.get(texture);
	}

	public static ResourceLocation convertToSepia(BufferedImage srcImage, ResourceLocation resourceLocation) {
		int mapWidth = srcImage.getWidth();
		int mapHeight = srcImage.getHeight();
		int[] colors = srcImage.getRGB(0, 0, mapWidth, mapHeight, null, 0, mapWidth);
		for (int i = 0; i < colors.length; ++i) {
			int color = colors[i];
			if (GOTConfig.osrsMap) {
				Integer biomeID = GOTDimension.GAME_OF_THRONES.getColorsToBiomeIDs().get(color);
				if (biomeID == null) {
					color = getMapOceanColor(true);
				} else {
					GOTBiome biome = GOTDimension.GAME_OF_THRONES.getBiomeList()[biomeID];
					if (biome.getHeightBaseParameter() < 0.0f) {
						color = 6453158;
					} else if (biome.getHeightBaseParameter() > 0.8f) {
						color = 14736861;
					} else if (biome.getHeightBaseParameter() > 0.4f) {
						color = 6575407;
					} else if (biome instanceof GOTBiomeSothoryosHell) {
						color = 3290677;
					} else if (biome.getDecorator().getTreesPerChunk() > 1) {
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
							rgb = GOTColorUtil.lerpColorsI(5468426, 9279778, ratio * 2.0f);
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
							rgb = GOTColorUtil.lerpColorsI(14736861, 9005125, ratio * 1.5f);
						}
					}
					newMapImage.setRGB(i, j, rgb | 0xFF000000);
				}
			}
		}
		MC.renderEngine.loadTexture(resourceLocation, new DynamicTexture(newMapImage));
		return resourceLocation;
	}

	public static void drawMap(boolean sepia, double x0, double x1, double y0, double y1, double z, double minU, double maxU, double minV, double maxV, float alpha) {
		Tessellator tessellator = Tessellator.instance;
		MC.getTextureManager().bindTexture(getMapTexture(sepia));
		GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x0, y1, z, minU, maxV);
		tessellator.addVertexWithUV(x1, y1, z, maxU, maxV);
		tessellator.addVertexWithUV(x1, y0, z, maxU, minV);
		tessellator.addVertexWithUV(x0, y0, z, minU, minV);
		tessellator.draw();
		int mtX = 0;
		int mtY = 0;
		int mtW = 20;
		double mtMinU = (double) (mtX - mtW) / GOTGenLayerWorld.getImageWidth();
		double mtMaxU = (double) (mtX + mtW) / GOTGenLayerWorld.getImageWidth();
		double mtMinV = (double) (mtY - mtW) / GOTGenLayerWorld.getImageHeight();
		double mtMaxV = (double) (mtY + mtW) / GOTGenLayerWorld.getImageHeight();
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

	public static void drawMapCompassBottomLeft(double x, double y, double z, double scale) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		MC.getTextureManager().bindTexture(GOTGuiMap.MAP_ICONS_TEXTURE);
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

	public static void drawMapOverlay(double x0, double x1, double y0, double y1, double z) {
		Tessellator tessellator = Tessellator.instance;
		MC.getTextureManager().bindTexture(OVERLAY_TEXTURE);
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

	private static IIcon generateIconEmpty(TextureMap textureMap) {
		String iconName = "textures/blocks/GOT_EMPTY_ICON";
		int size = 16;
		BufferedImage iconImage = new BufferedImage(size, size, 2);
		for (int i = 0; i < iconImage.getWidth(); ++i) {
			for (int j = 0; j < iconImage.getHeight(); ++j) {
				iconImage.setRGB(i, j, 0);
			}
		}
		GOTBufferedImageIcon icon = new GOTBufferedImageIcon(iconName, iconImage);
		icon.setIconWidth(iconImage.getWidth());
		icon.setIconHeight(iconImage.getHeight());
		textureMap.setTextureEntry(iconName, icon);
		return icon;
	}

	public static ResourceLocation getEyesTexture(ResourceLocation skin, int[][] eyesCoords, int eyeWidth, int eyeHeight) {
		ResourceLocation eyes = EYES_TEXTURES.get(skin);
		if (eyes == null) {
			try {
				BufferedImage skinImage = ImageIO.read(MC.getResourceManager().getResource(skin).getInputStream());
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
				eyes = MC.renderEngine.getDynamicTextureLocation(skin.toString() + "_eyes_" + eyeWidth + '_' + eyeHeight, new DynamicTexture(eyesImage));
			} catch (IOException e) {
				GOTLog.getLogger().error("Failed to generate eyes skin");
				e.printStackTrace();
				eyes = MISSING_TEXTURE;
			}
			EYES_TEXTURES.put(skin, eyes);
		}
		return eyes;
	}

	public static int getMapOceanColor(boolean sepia) {
		if (GOTConfig.osrsMap) {
			return -10324058;
		}
		int ocean = GOTBiome.ocean.color;
		if (sepia) {
			return getSepia(ocean);
		}
		return ocean;
	}

	private static ResourceLocation getMapTexture(boolean sepia) {
		if (GOTConfig.osrsMap || sepia) {
			return sepiaMapTexture;
		}
		return mapTexture;
	}

	private static int getSepia(int rgb) {
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
		return sepia | alpha << 24;
	}

	private static void loadMapTextures() {
		mapTexture = new ResourceLocation("got:textures/map/map.png");
		try {
			BufferedImage mapImage = ImageIO.read(MC.getResourceManager().getResource(mapTexture).getInputStream());
			sepiaMapTexture = convertToSepia(mapImage, new ResourceLocation("got:textures/map_sepia"));
		} catch (IOException e) {
			FMLLog.severe("Failed to generate GOT sepia map");
			e.printStackTrace();
			sepiaMapTexture = mapTexture;
		}
	}

	public static void onInit() {
		TextureManager texMgr = MC.getTextureManager();
		TextureMap texMapBlocks = (TextureMap) texMgr.getTexture(TextureMap.locationBlocksTexture);
		TextureMap texMapItems = (TextureMap) texMgr.getTexture(TextureMap.locationItemsTexture);
		GOTTextures textures = GOTClientFactory.getTextures();
		textures.preTextureStitch(new TextureStitchEvent.Pre(texMapBlocks));
		textures.preTextureStitch(new TextureStitchEvent.Pre(texMapItems));
	}

	private static void replaceWaterParticles() {
		try {
			BufferedImage particles = ImageIO.read(MC.getResourcePackRepository().rprDefaultResourcePack.getInputStream(PARTICLE_TEXTURES));
			BufferedImage waterParticles = ImageIO.read(MC.getResourceManager().getResource(NEW_WATER_PARTICLES).getInputStream());
			int[] rgb = waterParticles.getRGB(0, 0, waterParticles.getWidth(), waterParticles.getHeight(), null, 0, waterParticles.getWidth());
			particles.setRGB(NEW_WATER_U, NEW_WATER_V, NEW_WATER_WIDTH, NEW_WATER_HEIGHT, rgb, 0, NEW_WATER_WIDTH);
			TextureManager textureManager = MC.getTextureManager();
			textureManager.bindTexture(PARTICLE_TEXTURES);
			ITextureObject texture = textureManager.getTexture(PARTICLE_TEXTURES);
			TextureUtil.uploadTextureImageAllocate(texture.getGlTextureId(), particles, false, false);
		} catch (IOException e) {
			FMLLog.severe("Failed to replace rain particles");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static ResourceLocation getMapTexture() {
		return mapTexture;
	}

	public static void setMapTexture(ResourceLocation mapTexture) {
		GOTTextures.mapTexture = mapTexture;
	}

	@SuppressWarnings("unused")
	public static ResourceLocation getSepiaMapTexture() {
		return sepiaMapTexture;
	}

	public static void setSepiaMapTexture(ResourceLocation sepiaMapTexture) {
		GOTTextures.sepiaMapTexture = sepiaMapTexture;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		loadMapTextures();
		replaceWaterParticles();
		EYES_TEXTURES.clear();
		AVERAGED_PAGE_COLORS.clear();
	}

	@SubscribeEvent
	@SuppressWarnings({"WeakerAccess", "MethodMayBeStatic"})
	public void preTextureStitch(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 0) {
			GOTCommonIcons.iconEmptyBlock = generateIconEmpty(map);
			GOTCommonIcons.iconStoneSnow = map.registerIcon("stone_snow");
		}
		if (map.getTextureType() == 1) {
			GOTCommonIcons.iconMeleeWeapon = map.registerIcon("got:slot_melee");
			GOTCommonIcons.iconBomb = map.registerIcon("got:slot_bomb");
		}
	}
}