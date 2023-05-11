package got.client.render.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class GOTBufferedImageIcon extends TextureAtlasSprite {
	public static Set<String> loadedResources = new HashSet<>();
	public String iconName;
	public BufferedImage imageRGB;

	public GOTBufferedImageIcon(String s, BufferedImage rgb) {
		super(s);
		iconName = s;
		imageRGB = rgb;
		if (!loadedResources.contains(s)) {
			TextureManager texManager = Minecraft.getMinecraft().getTextureManager();
			ResourceLocation r = new ResourceLocation(iconName);
			ResourceLocation r1 = new ResourceLocation(r.getResourceDomain(), String.format("%s%s", r.getResourcePath(), ".png"));
			texManager.deleteTexture(r1);
			texManager.loadTexture(r1, new DynamicTexture(imageRGB));
			loadedResources.add(s);
		}
	}

	@Override
	public boolean hasCustomLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
		return true;
	}

	@Override
	public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
		BufferedImage[] imageArray = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
		imageArray[0] = imageRGB;
		loadSprite(imageArray, null, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0f);
		return false;
	}
}
