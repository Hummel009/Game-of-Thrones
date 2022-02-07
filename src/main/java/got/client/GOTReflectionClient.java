package got.client;

import java.lang.reflect.Method;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import got.common.util.GOTReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GOTReflectionClient {
	private static int[] colorCodes;

	private static float getCameraRoll(EntityRenderer renderer) {
		try {
			return (Float) ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, "camRoll", "field_78495_O");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0.0f;
		}
	}

	private static int[] getColorCodes(FontRenderer fontRenderer) {
		if (colorCodes == null) {
			try {
				colorCodes = (int[]) ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, fontRenderer, "colorCode", "field_78285_g");
			} catch (Exception e) {
				GOTReflection.logFailure(e);
			}
		}
		return colorCodes;
	}

	public static int getCreativeTabIndex(GuiContainerCreative gui) {
		try {
			return (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiContainerCreative.class, gui, "selectedTabIndex", "field_147058_w");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0;
		}
	}

	public static int getFormattingColor(EnumChatFormatting ecf) {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		int colorIndex = ecf.ordinal();
		return GOTReflectionClient.getColorCodes(fr)[colorIndex];
	}

	public static float getFOVModifier(EntityRenderer renderer, float tick, boolean flag) {
		try {
			Method method = GOTReflection.getPrivateMethod(EntityRenderer.class, renderer, new Class[] { Float.TYPE, Boolean.TYPE }, "getFOVModifier", "func_78481_a");
			return (Float) method.invoke(renderer, Float.valueOf(tick), flag);
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0.0f;
		}
	}

	public static int getGuiTop(GuiContainer gui) {
		try {
			return (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "guiTop", "field_147009_r");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0;
		}
	}

	public static int getGuiXSize(GuiContainer gui) {
		try {
			return (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "xSize", "field_146999_f");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0;
		}
	}

	public static float getHandFOV(EntityRenderer renderer) {
		try {
			return (Float) ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, "fovModifierHand", "field_78507_R");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0.0f;
		}
	}

	public static ItemStack getHighlightedItemStack(GuiIngame gui) {
		try {
			return (ItemStack) ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, "highlightingItemStack", "field_92016_l");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return null;
		}
	}

	public static int getHighlightedItemTicks(GuiIngame gui) {
		try {
			return (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, "remainingHighlightTicks", "field_92017_k");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return 0;
		}
	}

	public static boolean hasGuiPotionEffects(InventoryEffectRenderer gui) {
		try {
			return (Boolean) ObfuscationReflectionHelper.getPrivateValue(InventoryEffectRenderer.class, gui, "field_147045_u");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
			return false;
		}
	}

	private static void setCameraRoll(EntityRenderer renderer, float roll) {
		try {
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, roll, "camRoll", "field_78495_O");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
		}
	}

	public static void setHandFOV(EntityRenderer renderer, float fov) {
		try {
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, fov, "fovModifierHand", "field_78507_R");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
		}
	}

	public static void setHighlightedItemTicks(GuiIngame gui, int ticks) {
		try {
			ObfuscationReflectionHelper.setPrivateValue(GuiIngame.class, gui, ticks, "remainingHighlightTicks", "field_92017_k");
		} catch (Exception e) {
			GOTReflection.logFailure(e);
		}
	}

	public static void testAll(World world, Minecraft mc) {
		GOTReflectionClient.setCameraRoll(mc.entityRenderer, GOTReflectionClient.getCameraRoll(mc.entityRenderer));
		GOTReflectionClient.setHandFOV(mc.entityRenderer, GOTReflectionClient.getHandFOV(mc.entityRenderer));
		GOTReflectionClient.getColorCodes(mc.fontRenderer);
		GOTReflectionClient.setHighlightedItemTicks(mc.ingameGUI, GOTReflectionClient.getHighlightedItemTicks(mc.ingameGUI));
		GOTReflectionClient.getHighlightedItemStack(mc.ingameGUI);
	}
}
