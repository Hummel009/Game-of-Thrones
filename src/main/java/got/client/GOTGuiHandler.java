package got.client;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.*;
import got.client.gui.*;
import got.common.*;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPCRideable;
import got.common.inventory.GOTContainerCoinExchange;
import got.common.item.other.GOTItemCoin;
import got.common.network.*;
import got.common.world.GOTWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;

public class GOTGuiHandler {
	public static RenderItem itemRenderer = new RenderItem();
	public static Set<Class<? extends Container>> coinCount_excludedContainers = new HashSet<>();
	public static Set<Class<? extends GuiContainer>> coinCount_excludedGUIs = new HashSet<>();
	public static Set<Class<? extends IInventory>> coinCount_excludedInvTypes = new HashSet<>();
	static {
		coinCount_excludedGUIs.add(GuiContainerCreative.class);
		coinCount_excludedInvTypes.add(GOTContainerCoinExchange.InventoryCoinExchangeSlot.class);
		coinCount_excludedInvTypes.add(InventoryCraftResult.class);
	}

	public int descScrollIndex = -1;

	public GOTGuiHandler() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public GuiButton getDifficultyButton(GuiOptions gui, List buttons) {
		for (Object obj : buttons) {
			GuiOptionButton button;
			if (((obj instanceof GuiOptionButton) && ((button = (GuiOptionButton) obj).returnEnumOptions() == GameSettings.Options.DIFFICULTY))) {
				return button;
			}
		}
		return null;
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		GuiScreen gui = event.gui;
		if (GOTConfig.customMainMenu && gui != null && gui.getClass() == GuiMainMenu.class) {
			event.gui = gui = new GOTGuiMainMenu();
		}
		if (gui != null && gui.getClass() == GuiDownloadTerrain.class) {
			Minecraft mc = Minecraft.getMinecraft();
			WorldProvider provider = mc.theWorld.provider;
			if (provider instanceof GOTWorldProvider) {
				event.gui = gui = new GOTGuiDownloadTerrain(mc.thePlayer.sendQueue);
			}
		}
	}

	@SubscribeEvent
	public void postActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = event.gui;
		List buttons = event.buttonList;
		GuiButton button = event.button;
		if (gui instanceof GuiOptions && button instanceof GOTGuiButtonLock && button.enabled && mc.isSingleplayer()) {
			GOTLevelData.setSavedDifficulty(mc.gameSettings.difficulty);
			GOTLevelData.setDifficultyLocked(true);
			button.enabled = false;
			GuiButton buttonDifficulty = getDifficultyButton((GuiOptions) gui, buttons);
			if (buttonDifficulty != null) {
				buttonDifficulty.enabled = false;
			}
		}
	}

	@SubscribeEvent
	public void postInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		GuiButton buttonDifficulty;
		GuiScreen gui = event.gui;
		List buttons = event.buttonList;
		if (gui instanceof GuiOptions && (buttonDifficulty = getDifficultyButton((GuiOptions) gui, buttons)) != null) {
			GOTGuiButtonLock lock = new GOTGuiButtonLock(1000000, buttonDifficulty.xPosition + buttonDifficulty.width + 4, buttonDifficulty.yPosition);
			lock.enabled = !GOTLevelData.isDifficultyLocked();
			buttons.add(lock);
			buttonDifficulty.enabled = !GOTLevelData.isDifficultyLocked();
		}
	}

	@SubscribeEvent
	public void preDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = event.gui;
		if (gui instanceof GuiModList) {
			ModContainer mod = GOT.getModContainer();
			ModMetadata meta = mod.getMetadata();
			if (descScrollIndex == -1) {
				meta.description = GOTInfo.concatenateDescription(0);
				descScrollIndex = 0;
			}
			while (Mouse.next()) {
				int dwheel = Mouse.getEventDWheel();
				if (dwheel != 0) {
					int scroll = Integer.signum(dwheel);
					descScrollIndex -= scroll;
					descScrollIndex = MathHelper.clamp_int(descScrollIndex, 0, GOTInfo.description.length - 1);
					meta.description = GOTInfo.concatenateDescription(descScrollIndex);
				}
			}
		}
		if (gui instanceof GuiContainer && GOTConfig.displayCoinCounts) {
			mc.theWorld.theProfiler.startSection("invCoinCount");
			GuiContainer guiContainer = (GuiContainer) gui;
			Container container = guiContainer.inventorySlots;
			if (!coinCount_excludedContainers.contains(container.getClass()) && !coinCount_excludedGUIs.contains(guiContainer.getClass())) {
				int guiLeft = -1;
				int guiTop = -1;
				int guiXSize = -1;
				ArrayList<IInventory> differentInvs = new ArrayList<>();
				HashMap<IInventory, Integer> invHighestY = new HashMap<>();
				for (int i = 0; i < container.inventorySlots.size(); ++i) {
					Slot slot = container.getSlot(i);
					IInventory inv = slot.inventory;
					if (((inv != null) && !coinCount_excludedInvTypes.contains(inv.getClass()))) {
						if (!differentInvs.contains(inv)) {
							differentInvs.add(inv);
						}
						int slotY = slot.yDisplayPosition;
						if (!invHighestY.containsKey(inv)) {
							invHighestY.put(inv, slotY);
						} else {
							int highestY = invHighestY.get(inv);
							if (slotY < highestY) {
								invHighestY.put(inv, slotY);
							}
						}
					}
				}
				for (IInventory inv : differentInvs) {
					int coins = GOTItemCoin.getContainerValue(inv, true);
					if (coins > 0) {
						if (guiLeft == -1) {
							guiLeft = GOTReflectionClient.getGuiLeft(guiContainer);
							guiTop = GOTReflectionClient.getGuiTop(guiContainer);
							guiXSize = GOTReflectionClient.getGuiXSize(guiContainer);
						}
						int x = gui.width / 2 + guiXSize / 2 + 8;
						int y = invHighestY.get(inv) + guiTop;
						String sCoins = String.valueOf(coins);
						int border = 2;
						int rectX0 = x - border;
						int rectX1 = x + 16 + 2 + mc.fontRenderer.getStringWidth(sCoins) + border + 1;
						int rectY0 = y - border;
						int rectY1 = y + 16 + border;
						float a0 = 1.0f;
						float a1 = 0.1f;
						GL11.glDisable(3553);
						GL11.glDisable(3008);
						GL11.glShadeModel(7425);
						GL11.glPushMatrix();
						GL11.glTranslatef(0.0f, 0.0f, -500.0f);
						Tessellator tessellator = Tessellator.instance;
						tessellator.startDrawingQuads();
						tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a1);
						tessellator.addVertex(rectX1, rectY0, 0.0);
						tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a0);
						tessellator.addVertex(rectX0, rectY0, 0.0);
						tessellator.addVertex(rectX0, rectY1, 0.0);
						tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, a1);
						tessellator.addVertex(rectX1, rectY1, 0.0);
						tessellator.draw();
						GL11.glPopMatrix();
						GL11.glShadeModel(7424);
						GL11.glEnable(3008);
						GL11.glEnable(3553);
						GL11.glPushMatrix();
						GL11.glTranslatef(0.0f, 0.0f, 500.0f);
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(GOTRegistry.coin), x, y);
						GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
						GL11.glDisable(2896);
						mc.fontRenderer.drawString(sCoins, x + 16 + 2, y + (16 - mc.fontRenderer.FONT_HEIGHT + 2) / 2, 16777215);
						GL11.glPopMatrix();
						GL11.glDisable(2896);
						GL11.glEnable(3008);
						GL11.glEnable(3042);
						GL11.glDisable(2884);
					}
				}
				mc.theWorld.theProfiler.endSection();
			}
		}
	}

	@SubscribeEvent
	public void preInitGui(GuiScreenEvent.InitGuiEvent.Pre event) {
		GuiScreen gui = event.gui;
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		WorldClient world = mc.theWorld;
		if ((gui instanceof GuiInventory || gui instanceof GuiContainerCreative) && entityplayer != null && world != null && entityplayer.ridingEntity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entityplayer.ridingEntity).getMountInventory() != null) {
			entityplayer.closeScreen();
			GOTPacketMountOpenInv packet = new GOTPacketMountOpenInv();
			GOTPacketHandler.networkWrapper.sendToServer(packet);
			event.setCanceled(true);
		}
	}
}