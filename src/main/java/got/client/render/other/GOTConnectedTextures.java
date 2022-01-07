package got.client.render.other;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.FMLLog;
import got.common.block.GOTConnectedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

public class GOTConnectedTextures {
	public static Map<String, Map<Integer, IIcon>> blockIconsMap = new HashMap<>();

	public static void createConnectedIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta, boolean includeNoBase, Map<IconElement, BufferedImage> iconElementMap) {
		String blockName = block.getConnectedName(meta);
		blockIconsMap.remove(blockName);
		Minecraft mc = Minecraft.getMinecraft();
		mc.getResourceManager();
		TextureMap textureMap = (TextureMap) iconregister;
		String baseIconName = GOTConnectedTextures.getBaseIconName(blockName);
		String modID = GOTConnectedTextures.getModID(blockName);
		BufferedImage iconElementBase = iconElementMap.get(IconElement.BASE);
		int iconWidth = iconElementBase.getWidth();
		int iconHeight = iconElementBase.getHeight();
		for (Map.Entry<IconElement, BufferedImage> entry : iconElementMap.entrySet()) {
			IconElement elemt = entry.getKey();
			BufferedImage img = entry.getValue();
			if (((elemt != IconElement.BASE) && ((img.getWidth() != iconWidth) || (img.getHeight() != iconHeight)))) {
				FMLLog.severe("GOT: All connected texture icons for " + baseIconName + " must have the same dimensions!");
				BufferedImage errored = new BufferedImage(iconWidth, iconHeight, 2);
				for (int i = 0; i < errored.getWidth(); ++i) {
					for (int j = 0; j < errored.getHeight(); ++j) {
						int rgb;
						rgb = (i + j) % 2 == 0 ? 16711680 : 0;
						errored.setRGB(i, j, 0xFF000000 | rgb);
					}
				}
				entry.setValue(errored);
			}
		}
		HashMap<Integer, IIcon> iconsMap = new HashMap<>();
		for (Map.Entry<Integer, Set<IconElement>> entry : IconElement.allCombos.entrySet()) {
			int key = entry.getKey();
			Set<IconElement> set = entry.getValue();
			List<IconElement> list = IconElement.sortIconSet(set);
			if ((includeNoBase || list.contains(IconElement.BASE))) {
				String iconName = modID + ":textures/blocks/" + baseIconName + "_" + key;
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
		blockIconsMap.put(blockName, iconsMap);
	}

	public static String getBaseIconName(String blockName) {
		String s = blockName;
		int pathIndex = s.indexOf(":");
		if (pathIndex >= 0) {
			s = s.substring(pathIndex + 1);
		}
		return s;
	}

	public static IIcon getConnectedIcon(String blockName, boolean[][] flags, boolean noBase) {
		if (!blockIconsMap.containsKey(blockName) || blockIconsMap.get(blockName).isEmpty()) {
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("");
		}
		HashSet<IconElement> set = new HashSet<>();
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
		return blockIconsMap.get(blockName).get(key);
	}

	public static IIcon getConnectedIconBlock(GOTConnectedBlock block, IBlockAccess world, int i, int j, int k, int side, boolean noBase) {
		int meta = world.getBlockMetadata(i, j, k);
		String blockName = block.getConnectedName(meta);
		boolean[][] flags = new boolean[3][3];
		for (int x = -1; x <= 1; ++x) {
			for (int y = -1; y <= 1; ++y) {
				boolean match = false;
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
		return GOTConnectedTextures.getConnectedIcon(blockName, flags, noBase);
	}

	public static Map<IconElement, BufferedImage> getConnectedIconElements(String iconName) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		String baseIconName = GOTConnectedTextures.getBaseIconName(iconName);
		String modID = GOTConnectedTextures.getModID(iconName);
		HashMap<IconElement, BufferedImage> iconElementMap = new HashMap<>();
		try {
			for (IconElement e : IconElement.values()) {
				ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + "_" + e.iconName + ".png");
				BufferedImage image = ImageIO.read(resourceManager.getResource(res).getInputStream());
				iconElementMap.put(e, image);
			}
		} catch (IOException e) {
			FMLLog.severe("Failed to load connected textures for %s", modID + ":" + baseIconName);
			e.printStackTrace();
		}
		return iconElementMap;
	}

	public static IIcon getConnectedIconItem(GOTConnectedBlock block, int meta) {
		String blockName = block.getConnectedName(meta);
		boolean[][] flags = { { false, false, false }, { false, true, false }, { false, false, false } };
		return GOTConnectedTextures.getConnectedIcon(blockName, flags, false);
	}

	public static IIcon getConnectedIconItem(GOTConnectedBlock block, int meta, boolean[][] adjacentFlags) {
		String blockName = block.getConnectedName(meta);
		return GOTConnectedTextures.getConnectedIcon(blockName, adjacentFlags, false);
	}

	public static String getModID(String blockName) {
		String s = blockName;
		int pathIndex = s.indexOf(":");
		if (pathIndex >= 0) {
			return s.substring(0, pathIndex);
		}
		return "";
	}

	public static BufferedImage getSubImageIcon(BufferedImage base, int x, int y, int width, int height) {
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
		Map<IconElement, BufferedImage> iconElementMap = GOTConnectedTextures.getConnectedIconElements(iconName);
		GOTConnectedTextures.createConnectedIcons(iconregister, block, meta, includeNoBase, iconElementMap);
	}

	public static void registerNonConnectedGateIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta) {
		GOTConnectedTextures.registerNonConnectedGateIcons(iconregister, block, meta, block.getConnectedName(meta));
	}

	public static void registerNonConnectedGateIcons(IIconRegister iconregister, GOTConnectedBlock block, int meta, String iconName) {
		Minecraft mc = Minecraft.getMinecraft();
		IResourceManager resourceManager = mc.getResourceManager();
		String baseIconName = GOTConnectedTextures.getBaseIconName(iconName);
		String modID = GOTConnectedTextures.getModID(iconName);
		HashMap<IconElement, BufferedImage> iconElementMap = new HashMap<>();
		try {
			ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + ".png");
			BufferedImage blockIconImage = ImageIO.read(resourceManager.getResource(res).getInputStream());
			int iconWidth = blockIconImage.getWidth();
			int iconHeight = blockIconImage.getHeight();
			int sideWidth = Math.max(Math.round(iconWidth / 16.0f * 3.0f), 1);
			int sideHeight = Math.max(Math.round(iconHeight / 16.0f * 3.0f), 1);
			BufferedImage emptyBase = new BufferedImage(iconWidth, iconHeight, 2);
			iconElementMap.put(IconElement.BASE, emptyBase);
			iconElementMap.put(IconElement.SIDE_LEFT, GOTConnectedTextures.getSubImageIcon(blockIconImage, 0, 0, sideWidth, iconHeight));
			iconElementMap.put(IconElement.SIDE_RIGHT, GOTConnectedTextures.getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, iconHeight));
			iconElementMap.put(IconElement.SIDE_TOP, GOTConnectedTextures.getSubImageIcon(blockIconImage, 0, 0, iconWidth, sideHeight));
			iconElementMap.put(IconElement.SIDE_BOTTOM, GOTConnectedTextures.getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, iconWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_TOPLEFT, GOTConnectedTextures.getSubImageIcon(blockIconImage, 0, 0, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_TOPRIGHT, GOTConnectedTextures.getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_BOTTOMLEFT, GOTConnectedTextures.getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, sideWidth, sideHeight));
			iconElementMap.put(IconElement.CORNER_BOTTOMRIGHT, GOTConnectedTextures.getSubImageIcon(blockIconImage, iconWidth - sideWidth, iconHeight - sideHeight, sideWidth, sideHeight));
			iconElementMap.put(IconElement.INVCORNER_TOPLEFT, iconElementMap.get(IconElement.CORNER_TOPLEFT));
			iconElementMap.put(IconElement.INVCORNER_TOPRIGHT, iconElementMap.get(IconElement.CORNER_TOPRIGHT));
			iconElementMap.put(IconElement.INVCORNER_BOTTOMLEFT, iconElementMap.get(IconElement.CORNER_BOTTOMLEFT));
			iconElementMap.put(IconElement.INVCORNER_BOTTOMRIGHT, iconElementMap.get(IconElement.CORNER_BOTTOMRIGHT));
		} catch (IOException e) {
			FMLLog.severe("Failed to load connected textures for %s", modID + ":" + baseIconName);
			e.printStackTrace();
		}
		GOTConnectedTextures.createConnectedIcons(iconregister, block, meta, false, iconElementMap);
	}

	public enum IconElement {
		BASE("base", 0), SIDE_LEFT("left", 1), SIDE_RIGHT("right", 1), SIDE_TOP("top", 1), SIDE_BOTTOM("bottom", 1), CORNER_TOPLEFT("topLeft", 2), CORNER_TOPRIGHT("topRight", 2), CORNER_BOTTOMLEFT("bottomLeft", 2), CORNER_BOTTOMRIGHT("bottomRight", 2), INVCORNER_TOPLEFT("topLeftInv", 2), INVCORNER_TOPRIGHT("topRightInv", 2), INVCORNER_BOTTOMLEFT("bottomLeftInv", 2), INVCORNER_BOTTOMRIGHT("bottomRightInv", 2);

		public static EnumSet<IconElement> allSides;
		public static EnumSet<IconElement> allCorners;
		public static EnumSet<IconElement> allInvCorners;
		public static Map<Integer, Set<IconElement>> allCombos;
		public static Comparator<IconElement> comparator;
		static {
			allSides = EnumSet.of(SIDE_LEFT, SIDE_RIGHT, SIDE_TOP, SIDE_BOTTOM);
			allCorners = EnumSet.of(CORNER_TOPLEFT, CORNER_TOPRIGHT, CORNER_BOTTOMLEFT, CORNER_BOTTOMRIGHT);
			allInvCorners = EnumSet.of(INVCORNER_TOPLEFT, INVCORNER_TOPRIGHT, INVCORNER_BOTTOMLEFT, INVCORNER_BOTTOMRIGHT);
			allCombos = new HashMap<>();
			ArrayList<Set<IconElement>> permutations = new ArrayList<>();
			boolean[] trueOrFalse = { false, true };
			for (boolean base : trueOrFalse) {
				for (boolean left : trueOrFalse) {
					for (boolean right : trueOrFalse) {
						for (boolean top : trueOrFalse) {
							for (boolean bottom : trueOrFalse) {
								for (boolean topLeft : trueOrFalse) {
									for (boolean topRight : trueOrFalse) {
										for (boolean bottomLeft : trueOrFalse) {
											for (boolean bottomRight : trueOrFalse) {
												for (boolean topLeftInv : trueOrFalse) {
													for (boolean topRightInv : trueOrFalse) {
														for (boolean bottomLeftInv : trueOrFalse) {
															for (boolean bottomRightInv : trueOrFalse) {
																boolean addBottom;
																HashSet<IconElement> set = new HashSet<>();
																if (base) {
																	set.add(BASE);
																}
																boolean addLeft = left && (!top || topLeft) && (!bottom || bottomLeft);
																boolean addRight = right && (!top || topRight) && (!bottom || bottomRight);
																boolean addTop = top && (!left || topLeft) && (!right || topRight);
																addBottom = bottom && (!left || bottomLeft) && (!right || bottomRight);
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
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			for (Set<IconElement> iconSet : permutations) {
				int key = IconElement.getIconSetKey(iconSet);
				if (!allCombos.containsKey(key)) {
					allCombos.put(key, iconSet);
				}
			}
			comparator = new Comparator<IconElement>() {

				@Override
				public int compare(IconElement e1, IconElement e2) {
					if (e1.priority == e2.priority) {
						return e1.compareTo(e2);
					}
					return Integer.compare(e1.priority, e2.priority);
				}
			};
		}
		public String iconName;
		public int bitFlag;

		public int priority;

		IconElement(String s, int i) {
			iconName = s;
			bitFlag = 1 << ordinal();
			priority = i;
		}

		public static int getIconSetKey(Set<IconElement> set) {
			int i = 0;
			for (IconElement e : IconElement.values()) {
				if (set.contains(e)) {
					i |= e.bitFlag;
				}
			}
			return i;
		}

		public static List<IconElement> sortIconSet(Set<IconElement> set) {
			ArrayList<IconElement> list = new ArrayList<>(set);
			Collections.sort(list, comparator);
			return list;
		}

	}

}
