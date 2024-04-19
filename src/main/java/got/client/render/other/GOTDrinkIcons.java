package got.client.render.other;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GOTDrinkIcons {
	private static final Map<String, BufferedImage> VESSEL_ICONS = new HashMap<>();
	private static final Map<Item, BufferedImage> LIQUID_ICONS = new HashMap<>();

	private GOTDrinkIcons() {
	}

	public static IIcon registerDrinkIcon(IIconRegister iconregister, Item item, String itemName, String vessel) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		TextureMap textureMap = (TextureMap) iconregister;
		String baseIconName = itemName.substring("got:".length());
		try {
			BufferedImage vesselIcon = VESSEL_ICONS.get(vessel);
			if (vesselIcon == null) {
				ResourceLocation res = new ResourceLocation("got:textures/items/drink_" + vessel + ".png");
				vesselIcon = ImageIO.read(resourceManager.getResource(res).getInputStream());
				VESSEL_ICONS.put(vessel, vesselIcon);
			}
			BufferedImage liquidIcon = LIQUID_ICONS.get(item);
			if (liquidIcon == null) {
				ResourceLocation res = new ResourceLocation("got:textures/items/" + baseIconName + "_liquid.png");
				liquidIcon = ImageIO.read(resourceManager.getResource(res).getInputStream());
				LIQUID_ICONS.put(item, liquidIcon);
			}
			String iconName = "got:textures/items/" + baseIconName + '_' + vessel;
			int iconWidth = vesselIcon.getWidth();
			int iconHeight = vesselIcon.getHeight();
			BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
			for (int i = 0; i < iconImage.getWidth(); ++i) {
				for (int j = 0; j < iconImage.getHeight(); ++j) {
					int rgb = vesselIcon.getRGB(i, j);
					int maskColor = 16711935;
					if ((rgb & 0xFFFFFF) == maskColor) {
						rgb = liquidIcon.getRGB(i, j);
					}
					iconImage.setRGB(i, j, rgb);
				}
			}
			GOTBufferedImageIcon icon = new GOTBufferedImageIcon(iconName, iconImage);
			icon.setIconWidth(iconImage.getWidth());
			icon.setIconHeight(iconImage.getHeight());
			textureMap.setTextureEntry(iconName, icon);
			return icon;
		} catch (IOException e) {
			FMLLog.severe("Failed to onInit mug textures for %s", item.getUnlocalizedName());
			e.printStackTrace();
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
		}
	}

	public static IIcon registerLiquidIcon(IIconRegister iconregister, String itemName) {
		return iconregister.registerIcon(itemName + "_liquid");
	}
}