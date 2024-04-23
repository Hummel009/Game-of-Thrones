package got.client.render.animal;

import got.client.model.GOTModelCamel;
import got.common.entity.animal.GOTEntityCamel;
import got.common.entity.other.iface.GOTNPCMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GOTRenderCamel extends RenderLiving {
	private static final Map<String, ResourceLocation> COLORED_CARPET_TEXTURES = new HashMap<>();

	private static final ResourceLocation CAMEL_TEXTURE = new ResourceLocation("got:textures/entity/animal/camel/camel.png");
	private static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation("got:textures/entity/animal/camel/saddle.png");
	private static final ResourceLocation CARPET_BASE = new ResourceLocation("got:textures/entity/animal/camel/carpet_base.png");
	private static final ResourceLocation CARPET_OVERLAY = new ResourceLocation("got:textures/entity/animal/camel/carpet_overlay.png");

	private static final GOTModelCamel MODEL_SADDLE = new GOTModelCamel(0.5f);
	private static final GOTModelCamel MODEL_CARPET = new GOTModelCamel(0.55f);

	public GOTRenderCamel() {
		super(new GOTModelCamel(), 0.5f);
		setRenderPassModel(MODEL_SADDLE);
	}

	private static ResourceLocation getColoredCarpetTexture(int carpetRGB) {
		String path = "got:camel_carpet_0x" + Integer.toHexString(carpetRGB);
		ResourceLocation res = COLORED_CARPET_TEXTURES.get(path);
		if (res == null) {
			try {
				Minecraft mc = Minecraft.getMinecraft();
				InputStream isBase = mc.getResourceManager().getResource(CARPET_BASE).getInputStream();
				BufferedImage imgBase = ImageIO.read(isBase);
				InputStream isOverlay = mc.getResourceManager().getResource(CARPET_OVERLAY).getInputStream();
				BufferedImage imgOverlay = ImageIO.read(isOverlay);
				BufferedImage imgDyed = new BufferedImage(imgBase.getWidth(), imgBase.getHeight(), 2);
				int carpetR = carpetRGB >> 16 & 0xFF;
				int carpetG = carpetRGB >> 8 & 0xFF;
				int carpetB = carpetRGB & 0xFF;
				for (int i = 0; i < imgDyed.getWidth(); ++i) {
					for (int j = 0; j < imgDyed.getHeight(); ++j) {
						int argbOverlay = imgOverlay.getRGB(i, j);
						int aOverlay = argbOverlay >> 24 & 0xFF;
						if (aOverlay > 0) {
							imgDyed.setRGB(i, j, argbOverlay);
						} else {
							int argb = imgBase.getRGB(i, j);
							int a = argb >> 24 & 0xFF;
							int r = argb >> 16 & 0xFF;
							int g = argb >> 8 & 0xFF;
							int b = argb & 0xFF;
							r = r * carpetR / 255;
							g = g * carpetG / 255;
							b = b * carpetB / 255;
							int dyed = a << 24 | r << 16 | g << 8 | b;
							imgDyed.setRGB(i, j, dyed);
						}
					}
				}
				res = mc.renderEngine.getDynamicTextureLocation(path, new DynamicTexture(imgDyed));
			} catch (IOException e) {
				System.out.println("Hummel009: Error generating colored camel carpet texture.");
				e.printStackTrace();
				res = CARPET_BASE;
			}
			COLORED_CARPET_TEXTURES.put(path, res);
		}
		return res;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTNPCMount camel = (GOTNPCMount) entity;
		return GOTRenderHorse.getLayeredMountTexture(camel, CAMEL_TEXTURE);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GOTEntityCamel animal = (GOTEntityCamel) entity;
		if (animal.isChild()) {
			GL11.glScalef(0.62f, 0.62f, 0.62f);
		} else {
			GL11.glScalef(1.25f, 1.25f, 1.25f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		GOTEntityCamel camel = (GOTEntityCamel) entity;
		if (pass == 0 && camel.isMountSaddled()) {
			setRenderPassModel(MODEL_SADDLE);
			bindTexture(SADDLE_TEXTURE);
			return 1;
		}
		if (pass == 1 && camel.isCamelWearingCarpet()) {
			setRenderPassModel(MODEL_CARPET);
			int color = camel.getCamelCarpetColor();
			ResourceLocation carpet = getColoredCarpetTexture(color);
			bindTexture(carpet);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}