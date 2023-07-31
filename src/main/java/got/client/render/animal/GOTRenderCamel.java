package got.client.render.animal;

import got.client.model.GOTModelCamel;
import got.common.entity.animal.GOTEntityCamel;
import got.common.entity.other.GOTNPCMount;
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
	public static ResourceLocation camelSkin = new ResourceLocation("got:textures/entity/animal/camel/camel.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("got:textures/entity/animal/camel/saddle.png");
	public static ResourceLocation carpetBase = new ResourceLocation("got:textures/entity/animal/camel/carpet_base.png");
	public static ResourceLocation carpetOverlay = new ResourceLocation("got:textures/entity/animal/camel/carpet_overlay.png");
	public static Map<String, ResourceLocation> coloredCarpetTextures = new HashMap<>();
	public GOTModelCamel modelSaddle = new GOTModelCamel(0.5f);
	public GOTModelCamel modelCarpet = new GOTModelCamel(0.55f);

	public GOTRenderCamel() {
		super(new GOTModelCamel(), 0.5f);
		setRenderPassModel(modelSaddle);
	}

	public static ResourceLocation getColoredCarpetTexture(int carpetRGB) {
		String path = "got:camel_carpet_0x" + Integer.toHexString(carpetRGB);
		ResourceLocation res = coloredCarpetTextures.get(path);
		if (res == null) {
			try {
				Minecraft mc = Minecraft.getMinecraft();
				InputStream isBase = mc.getResourceManager().getResource(carpetBase).getInputStream();
				BufferedImage imgBase = ImageIO.read(isBase);
				InputStream isOverlay = mc.getResourceManager().getResource(carpetOverlay).getInputStream();
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
				System.out.println("Hummel009: Error generating coloured camel carpet texture");
				e.printStackTrace();
				res = carpetBase;
			}
			coloredCarpetTextures.put(path, res);
		}
		return res;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		GOTNPCMount camel = (GOTNPCMount) entity;
		return GOTRenderHorse.getLayeredMountTexture(camel, camelSkin);
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
			setRenderPassModel(modelSaddle);
			bindTexture(saddleTexture);
			return 1;
		}
		if (pass == 1 && camel.isCamelWearingCarpet()) {
			setRenderPassModel(modelCarpet);
			int color = camel.getCamelCarpetColor();
			ResourceLocation carpet = getColoredCarpetTexture(color);
			bindTexture(carpet);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
