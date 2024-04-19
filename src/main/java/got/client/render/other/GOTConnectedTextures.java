package got.client.render.other;

import cpw.mods.fml.common.FMLLog;
import got.common.block.GOTConnectedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GOTConnectedTextures {
	private static final Map<String, Map<Integer, IIcon>> BLOCK_ICONS_MAP = new HashMap<>();

	private GOTConnectedTextures() {
	}

	private static void createConnectedIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta, boolean includeNoBase, Map<IconElement, BufferedImage> iconElementMap) {
		String blockName = block.getConnectedName(meta);
		BLOCK_ICONS_MAP.remove(blockName);
		Minecraft mc = Minecraft.getMinecraft();
		mc.getResourceManager();
		TextureMap textureMap = (TextureMap) iconregister;
		String baseIconName = getBaseIconName(blockName);
		String modID = getModID(blockName);
		BufferedImage iconElementBase = iconElementMap.get(IconElement.BASE);
		int iconWidth = iconElementBase.getWidth();
		int iconHeight = iconElementBase.getHeight();
		for (Map.Entry<IconElement, BufferedImage> entry : iconElementMap.entrySet()) {
			IconElement elemt = entry.getKey();
			BufferedImage img = entry.getValue();
			if (elemt != IconElement.BASE && (img.getWidth() != iconWidth || img.getHeight() != iconHeight)) {
				FMLLog.severe("Hummel009: All connected texture icons for " + baseIconName + " must have the same dimensions!");
				BufferedImage errored = new BufferedImage(iconWidth, iconHeight, 2);
				for (int i = 0; i < errored.getWidth(); ++i) {
					for (int j = 0; j < errored.getHeight(); ++j) {
						int rgb = (i + j) % 2 == 0 ? 16711680 : 0;
						errored.setRGB(i, j, 0xFF000000 | rgb);
					}
				}
				entry.setValue(errored);
			}
		}
		Map<Integer, IIcon> iconsMap = new HashMap<>();
		for (Map.Entry<Integer, Set<IconElement>> entry : IconElement.allCombos.entrySet()) {
			int key = entry.getKey();
			Set<IconElement> set = entry.getValue();
			List<IconElement> list = IconElement.sortIconSet(set);
			if (includeNoBase || list.contains(IconElement.BASE)) {
				String iconName = modID + ":textures/blocks/" + baseIconName + '_' + key;
				if (textureMap.getTextureExtry(iconName) != null) {
					FMLLog.severe("Icon is already registered for %s", iconName);
				} else {
					BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
					for (IconElement e : list) {
						BufferedImage baseIconImage = iconElementMap.get(e);
						for (int i = 0; i < iconImage.getWidth(); ++i) {
							for (int j = 0; j < iconImage.getHeight(); ++j) {
								int rgb = baseIconImage.getRGB(i, j);
								int alpha = rgb & 0xFF000000;
								if (alpha != 0) {
									iconImage.setRGB(i, j, rgb);
								}
							}
						}
					}
					GOTBufferedImageIcon icon = new GOTBufferedImageIcon(iconName, iconImage);
					icon.setIconWidth(iconImage.getWidth());
					icon.setIconHeight(iconImage.getHeight());
					textureMap.setTextureEntry(iconName, icon);
					iconsMap.put(key, icon);
				}
			}
		}
		BLOCK_ICONS_MAP.put(blockName, iconsMap);
	}

	private static String getBaseIconName(String blockName) {
		int pathIndex = blockName.indexOf(':');
		if (pathIndex >= 0) {
			return blockName.substring(pathIndex + 1);
		}
		return blockName;
	}

	private static IIcon getConnectedIcon(String blockName, boolean[][] flags, boolean noBase) {
		if (!BLOCK_ICONS_MAP.containsKey(blockName) || BLOCK_ICONS_MAP.get(blockName).isEmpty()) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
		}
		Set<IconElement> set = EnumSet.noneOf(IconElement.class);
		if (!noBase) {
			set.add(IconElement.BASE);
		}
		if (flags != null) {
			boolean topLeft = flags[0][0];
			boolean top = flags[1][0];
			boolean topRight = flags[2][0];
			boolean left = flags[0][1];
			boolean right = flags[2][1];
			boolean bottomLeft = flags[0][2];
			boolean bottom = flags[1][2];
			boolean bottomRight = flags[2][2];
			if (!left) {
				set.add(IconElement.SIDE_LEFT);
			}
			if (!right) {
				set.add(IconElement.SIDE_RIGHT);
			}
			if (!top) {
				set.add(IconElement.SIDE_TOP);
			}
			if (!bottom) {
				set.add(IconElement.SIDE_BOTTOM);
			}
			if (!left && !top) {
				set.add(IconElement.CORNER_TOPLEFT);
			}
			if (!right && !top) {
				set.add(IconElement.CORNER_TOPRIGHT);
			}
			if (!left && !bottom) {
				set.add(IconElement.CORNER_BOTTOMLEFT);
			}
			if (!right && !bottom) {
				set.add(IconElement.CORNER_BOTTOMRIGHT);
			}
			if (left && top && !topLeft) {
				set.add(IconElement.INVCORNER_TOPLEFT);
			}
			if (right && top && !topRight) {
				set.add(IconElement.INVCORNER_TOPRIGHT);
			}
			if (left && bottom && !bottomLeft) {
				set.add(IconElement.INVCORNER_BOTTOMLEFT);
			}
			if (right && bottom && !bottomRight) {
				set.add(IconElement.INVCORNER_BOTTOMRIGHT);
			}
		}
		int key = IconElement.getIconSetKey(set);
		return BLOCK_ICONS_MAP.get(blockName).get(key);
	}

	public static IIcon getConnectedIconBlock(GOTConnectedBlock block, IBlockAccess world, int i, int j, int k, int side, boolean noBase) {
		int meta = world.getBlockMetadata(i, j, k);
		String blockName = block.getConnectedName(meta);
		boolean[][] flags = new boolean[3][3];
		for (int x = -1; x <= 1; ++x) {
			for (int y = -1; y <= 1; ++y) {
				boolean match;
				if (x == 0 && y == 0) {
					match = true;
				} else {
					int i1 = i;
					int j1 = j;
					int k1 = k;
					switch (side) {
						case 0:
						case 1:
							i1 += x;
							k1 += y;
							break;
						case 2:
							i1 -= x;
							j1 -= y;
							break;
						case 3:
							i1 += x;
							j1 -= y;
							break;
						case 4:
							k1 += x;
							j1 -= y;
							break;
						case 5:
							k1 -= x;
							j1 -= y;
							break;
						default:
							break;
					}
					match = block.areBlocksConnected(world, i, j, k, i1, j1, k1);
				}
				flags[x + 1][y + 1] = match;
			}
		}
		return getConnectedIcon(blockName, flags, noBase);
	}

	private static Map<IconElement, BufferedImage> getConnectedIconElements(String iconName) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		String baseIconName = getBaseIconName(iconName);
		String modID = getModID(iconName);
		Map<IconElement, BufferedImage> iconElementMap = new EnumMap<>(IconElement.class);
		try {
			for (IconElement e : IconElement.values()) {
				ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + '_' + e.getIconName() + ".png");
				BufferedImage image = ImageIO.read(resourceManager.getResource(res).getInputStream());
				iconElementMap.put(e, image);
			}
		} catch (IOException e) {
			FMLLog.severe("Failed to onInit connected textures for %s", modID + ':' + baseIconName);
			e.printStackTrace();
		}
		return iconElementMap;
	}

	public static IIcon getConnectedIconItem(GOTConnectedBlock block, int meta) {
		String blockName = block.getConnectedName(meta);
		boolean[][] flags = new boolean[][]{new boolean[]{false, false, false}, new boolean[]{false, true, false}, new boolean[]{false, false, false}};
		return getConnectedIcon(blockName, flags, false);
	}

	public static IIcon getConnectedIconItem(GOTConnectedBlock block, int meta, boolean[][] adjacentFlags) {
		String blockName = block.getConnectedName(meta);
		return getConnectedIcon(blockName, adjacentFlags, false);
	}

	private static String getModID(String blockName) {
		int pathIndex = blockName.indexOf(':');
		if (pathIndex >= 0) {
			return blockName.substring(0, pathIndex);
		}
		return "";
	}

	private static BufferedImage getSubImageIcon(BufferedImage base, int x, int y, int width, int height) {
		BufferedImage subpart = base.getSubimage(x, y, width, height);
		BufferedImage img = new BufferedImage(base.getWidth(), base.getHeight(), 2);
		for (int subX = 0; subX < width; ++subX) {
			for (int subY = 0; subY < height; ++subY) {
				img.setRGB(x + subX, y + subY, subpart.getRGB(subX, subY));
			}
		}
		return img;
	}

	public static void registerConnectedIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta, boolean includeNoBase) {
		String iconName = block.getConnectedName(meta);
		Map<IconElement, BufferedImage> iconElementMap = getConnectedIconElements(iconName);
		createConnectedIcons(iconregister, block, meta, includeNoBase, iconElementMap);
	}

	public static void registerNonConnectedGateIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta) {
		registerNonConnectedGateIcons(iconregister, block, meta, block.getConnectedName(meta));
	}

	private static void registerNonConnectedGateIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta, String iconName) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		String baseIconName = getBaseIconName(iconName);
		String modID = getModID(iconName);
		Map<IconElement, BufferedImage> iconElementMap = new EnumMap<>(IconElement.class);
		try {
			ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + ".png");
			BufferedImage blockIconImage = ImageIO.read(resourceManager.getResource(res).getInputStream());
			int iconWidth = blockIconImage.getWidth();
			int iconHeight = blockIconImage.getHeight();
			int sideWidth = Math.max(Math.round(iconWidth / 16.0f * 3.0f), 1);
			int sideHeight = Math.max(Math.round(iconHeight / 16.0f * 3.0f), 1);
			BufferedImage emptyBase = new BufferedImage(iconWidth, iconHeight, 2);
			iconElementMap.put(IconElement.BASE, emptyBase);
			iconElementMap.put(IconElement.SIDE_LEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, iconHeight));
			iconElementMap.put(IconElement.SIDE_RIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, iconHeight));
			iconElementMap.put(IconElement.SIDE_TOP, getSubImageIcon(blockIconImage, 0, 0, iconWidth, sideHeight));
			iconElementMap.put(IconElement.SIDE_BOTTOM, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, iconWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_TOPLEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_TOPRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_BOTTOMLEFT, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_BOTTOMRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, iconHeight - sideHeight, sideWidth, sideHeight));
			iconElementMap.put(IconElement.INVCORNER_TOPLEFT, iconElementMap.get(IconElement.CORNER_TOPLEFT));
			iconElementMap.put(IconElement.INVCORNER_TOPRIGHT, iconElementMap.get(IconElement.CORNER_TOPRIGHT));
			iconElementMap.put(IconElement.INVCORNER_BOTTOMLEFT, iconElementMap.get(IconElement.CORNER_BOTTOMLEFT));
			iconElementMap.put(IconElement.INVCORNER_BOTTOMRIGHT, iconElementMap.get(IconElement.CORNER_BOTTOMRIGHT));
		} catch (IOException e) {
			FMLLog.severe("Failed to onInit connected textures for %s", modID + ':' + baseIconName);
			e.printStackTrace();
		}
		createConnectedIcons(iconregister, block, meta, false, iconElementMap);
	}

	private enum IconElement {
		BASE("base", 0), SIDE_LEFT("left", 1), SIDE_RIGHT("right", 1), SIDE_TOP("top", 1), SIDE_BOTTOM("bottom", 1), CORNER_TOPLEFT("top_left", 2), CORNER_TOPRIGHT("top_right", 2), CORNER_BOTTOMLEFT("bottom_left", 2), CORNER_BOTTOMRIGHT("bottom_right", 2), INVCORNER_TOPLEFT("top_left_inv", 2), INVCORNER_TOPRIGHT("top_right_inv", 2), INVCORNER_BOTTOMLEFT("bottom_left_inv", 2), INVCORNER_BOTTOMRIGHT("bottom_right_inv", 2);
		private static final Map<Integer, Set<IconElement>> allCombos;
		private static final Comparator<IconElement> comparator;

		static {
			allCombos = new HashMap<>();
			Collection<Set<IconElement>> permutations = new ArrayList<>();
			boolean[] trueOrFalse = new boolean[]{false, true};
			for (int i = 0; i < 8192; i++) {
				boolean base = trueOrFalse[i & 1];
				boolean left = trueOrFalse[i >> 1 & 1];
				boolean right = trueOrFalse[i >> 2 & 1];
				boolean top = trueOrFalse[i >> 3 & 1];
				boolean bottom = trueOrFalse[i >> 4 & 1];
				boolean topLeft = trueOrFalse[i >> 5 & 1];
				boolean topRight = trueOrFalse[i >> 6 & 1];
				boolean bottomLeft = trueOrFalse[i >> 7 & 1];
				boolean bottomRight = trueOrFalse[i >> 8 & 1];
				boolean topLeftInv = trueOrFalse[i >> 9 & 1];
				boolean topRightInv = trueOrFalse[i >> 10 & 1];
				boolean bottomLeftInv = trueOrFalse[i >> 11 & 1];
				boolean bottomRightInv = trueOrFalse[i >> 12 & 1];
				Set<IconElement> set = EnumSet.noneOf(IconElement.class);
				if (base) {
					set.add(BASE);
				}
				boolean addLeft = left && (!top || topLeft) && (!bottom || bottomLeft);
				boolean addRight = right && (!top || topRight) && (!bottom || bottomRight);
				boolean addTop = top && (!left || topLeft) && (!right || topRight);
				boolean addBottom = bottom && (!left || bottomLeft) && (!right || bottomRight);
				if (addLeft) {
					set.add(SIDE_LEFT);
				}
				if (addRight) {
					set.add(SIDE_RIGHT);
				}
				if (addTop) {
					set.add(SIDE_TOP);
				}
				if (addBottom) {
					set.add(SIDE_BOTTOM);
				}
				if (topLeft && addTop && addLeft) {
					set.add(CORNER_TOPLEFT);
				}
				if (topRight && addTop && addRight) {
					set.add(CORNER_TOPRIGHT);
				}
				if (bottomLeft && addBottom && addLeft) {
					set.add(CORNER_BOTTOMLEFT);
				}
				if (bottomRight && addBottom && addRight) {
					set.add(CORNER_BOTTOMRIGHT);
				}
				if (topLeftInv && !topLeft && !addTop && !addLeft) {
					set.add(INVCORNER_TOPLEFT);
				}
				if (topRightInv && !topRight && !addTop && !addRight) {
					set.add(INVCORNER_TOPRIGHT);
				}
				if (bottomLeftInv && !bottomLeft && !addBottom && !addLeft) {
					set.add(INVCORNER_BOTTOMLEFT);
				}
				if (bottomRightInv && !bottomRight && !addBottom && !addRight) {
					set.add(INVCORNER_BOTTOMRIGHT);
				}
				permutations.add(set);
			}
			for (Set<IconElement> iconSet : permutations) {
				int key = getIconSetKey(iconSet);
				if (!allCombos.containsKey(key)) {
					allCombos.put(key, iconSet);
				}
			}
			comparator = (e1, e2) -> {
				if (e1.priority == e2.priority) {
					return e1.compareTo(e2);
				}
				return Integer.compare(e1.priority, e2.priority);
			};
		}

		private final String iconName;
		private final int bitFlag;
		private final int priority;

		IconElement(String s, int i) {
			iconName = s;
			bitFlag = 1 << ordinal();
			priority = i;
		}

		private static int getIconSetKey(Collection<IconElement> set) {
			int i = 0;
			for (IconElement e : values()) {
				if (set.contains(e)) {
					i |= e.bitFlag;
				}
			}
			return i;
		}

		private static List<IconElement> sortIconSet(Set<IconElement> set) {
			List<IconElement> list = new ArrayList<>(set);
			list.sort(comparator);
			return list;
		}

		private String getIconName() {
			return iconName;
		}
	}
}