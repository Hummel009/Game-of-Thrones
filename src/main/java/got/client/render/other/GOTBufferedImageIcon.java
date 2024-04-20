package got.client.render.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;

public class GOTBufferedImageIcon extends TextureAtlasSprite {
	private static final Collection<String> LOADED_RESOURCES = new HashSet<>();
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	private final BufferedImage imageRGB;

	public GOTBufferedImageIcon(String iconName, BufferedImage rgb) {
		super(iconName);
		imageRGB = rgb;
		if (!LOADED_RESOURCES.contains(iconName)) {
			TextureManager texManager = MINECRAFT.getTextureManager();
			ResourceLocation r = new ResourceLocation(iconName);
			ResourceLocation r1 = new ResourceLocation(r.getResourceDomain(), String.format("%s%s", r.getResourcePath(), ".png"));
			texManager.deleteTexture(r1);
			texManager.loadTexture(r1, new DynamicTexture(imageRGB));
			LOADED_RESOURCES.add(iconName);
		}
	}

	@Override
	public boolean hasCustomLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
		return true;
	}

	@Override
	public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
		BufferedImage[] imageArray = new BufferedImage[1 + MINECRAFT.gameSettings.mipmapLevels];
		imageArray[0] = imageRGB;
		loadSprite(imageArray, null, MINECRAFT.gameSettings.anisotropicFiltering > 1.0f);
		return false;
	}
}