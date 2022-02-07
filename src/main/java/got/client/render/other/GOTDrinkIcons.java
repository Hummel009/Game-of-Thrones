package got.client.render.other;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.*;

public class GOTDrinkIcons {
	private static int maskColor = 16711935;
	private static Map<String, BufferedImage> vesselIcons = new HashMap<>();
	private static Map<Item, BufferedImage> liquidIcons = new HashMap<>();

	public static IIcon registerDrinkIcon(IIconRegister iconregister, Item item, String itemName, String vessel) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		TextureMap textureMap = (TextureMap) iconregister;
		String baseIconName = itemName.substring("got:".length());
		try {
			BufferedImage liquidIcon;
			BufferedImage vesselIcon = vesselIcons.get(vessel);
			if (vesselIcon == null) {
				ResourceLocation res = new ResourceLocation("got:textures/items/drink_" + vessel + ".png");
				vesselIcon = ImageIO.read(resourceManager.getResource(res).getInputStream());
				vesselIcons.put(vessel, vesselIcon);
			}
			if ((liquidIcon = liquidIcons.get(item)) == null) {
				ResourceLocation res = new ResourceLocation("got:textures/items/" + baseIconName + "_liquid.png");
				liquidIcon = ImageIO.read(resourceManager.getResource(res).getInputStream());
				liquidIcons.put(item, liquidIcon);
			}
			String iconName = "got:textures/items/" + baseIconName + "_" + vessel;
			int iconWidth = vesselIcon.getWidth();
			int iconHeight = vesselIcon.getHeight();
			BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
			for (int i = 0; i < iconImage.getWidth(); ++i) {
				for (int j = 0; j < iconImage.getHeight(); ++j) {
					int rgb = vesselIcon.getRGB(i, j);
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
			FMLLog.severe("Failed to load mug textures for %s", item.getUnlocalizedName());
			e.printStackTrace();
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
		}
	}

	public static IIcon registerLiquidIcon(IIconRegister iconregister, Item item, String itemName) {
		return iconregister.registerIcon(itemName + "_liquid");
	}
}
