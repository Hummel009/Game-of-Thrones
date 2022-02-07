package got.client;

import java.util.*;

import org.lwjgl.input.Keyboard;

import com.google.common.math.IntMath;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class GOTKeyHandler {
	private static KeyBinding keyBindingMenu = new KeyBinding("Menu", 38, "Game of Thrones");
	private static KeyBinding keyBindingMapTeleport = new KeyBinding("Map Teleport", 50, "Game of Thrones");
	private static KeyBinding keyBindingFastTravel = new KeyBinding("Fast Travel", 33, "Game of Thrones");
	private static KeyBinding keyBindingAlignmentCycleLeft = new KeyBinding("Alignment Cycle Left", 203, "Game of Thrones");
	private static KeyBinding keyBindingAlignmentCycleRight = new KeyBinding("Alignment Cycle Right", 205, "Game of Thrones");
	private static KeyBinding keyBindingAlignmentGroupPrev = new KeyBinding("Alignment Group Prev", 200, "Game of Thrones");
	private static KeyBinding keyBindingAlignmentGroupNext = new KeyBinding("Alignment Group Next", 208, "Game of Thrones");
	private static KeyBinding keyBindingDragonUp = new KeyBinding("Dragon: Fly Up", Keyboard.KEY_G, "Game of Thrones");
	private static KeyBinding keyBindingDragonDown = new KeyBinding("Dragon: Fly Down", Keyboard.KEY_H, "Game of Thrones");
	private static KeyBinding keyBindingCargoCart = new KeyBinding("Enable cargocart", 19, "Game of Thrones");
	private static KeyBinding keyBindingReturn = new KeyBinding("Clear", Keyboard.KEY_RETURN, "Game of Thrones");
	private static Minecraft mc = Minecraft.getMinecraft();
	private static int alignmentChangeTick;
	private GOTPacketDragonControl dcm = new GOTPacketDragonControl();
	private SimpleNetworkWrapper network;

	public GOTKeyHandler(SimpleNetworkWrapper network) {
		this.network = network;
		ClientRegistry.registerKeyBinding(getKeyBindingMenu());
		ClientRegistry.registerKeyBinding(getKeyBindingMapTeleport());
		ClientRegistry.registerKeyBinding(getKeyBindingFastTravel());
		ClientRegistry.registerKeyBinding(getKeyBindingAlignmentCycleLeft());
		ClientRegistry.registerKeyBinding(getKeyBindingAlignmentCycleRight());
		ClientRegistry.registerKeyBinding(getKeyBindingAlignmentGroupPrev());
		ClientRegistry.registerKeyBinding(getKeyBindingAlignmentGroupNext());
		ClientRegistry.registerKeyBinding(getKeyBindingDragonUp());
		ClientRegistry.registerKeyBinding(getKeyBindingDragonDown());
		ClientRegistry.registerKeyBinding(keyBindingCargoCart);
	}

	@SubscribeEvent
	private void KeyInputEvent(InputEvent.KeyInputEvent event) {
		GOTAttackTiming.doAttackTiming();
		if (getKeyBindingMenu().getIsKeyPressed() && GOTKeyHandler.mc.currentScreen == null) {
			GOTKeyHandler.mc.thePlayer.openGui(GOT.instance, 11, GOTKeyHandler.mc.theWorld, 0, 0, 0);
		}
		GOTPlayerData pd = GOTLevelData.getData(GOTKeyHandler.mc.thePlayer);
		boolean usedAlignmentKeys = false;
		boolean skippedHelp = false;
		HashMap<GOTDimension.DimensionRegion, GOTFaction> lastViewedRegions = new HashMap<>();
		GOTDimension currentDimension = GOTDimension.getCurrentDimension(GOTKeyHandler.mc.theWorld);
		GOTFaction currentFaction = pd.getViewingFaction();
		GOTDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
		List<GOTDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
		List<GOTFaction> factionList = currentRegion.factionList;
		if (GOTKeyHandler.mc.currentScreen == null && alignmentChangeTick <= 0) {
			int i;
			if (getKeyBindingReturn().getIsKeyPressed()) {
				skippedHelp = true;
			}
			if (getKeyBindingAlignmentCycleLeft().getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				--i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (getKeyBindingAlignmentCycleRight().getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				++i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (regionList != null && currentRegion != null) {
				if (getKeyBindingAlignmentGroupPrev().getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					--i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					factionList = currentRegion.factionList;
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
				if (getKeyBindingAlignmentGroupNext().getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					factionList = currentRegion.factionList;
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
			}
		}
		if (skippedHelp && GOTTickHandlerClient.isRenderMenuPrompt()) {
			GOTTickHandlerClient.setRenderMenuPrompt(false);
			GOTPacketCheckMenuPrompt packet = new GOTPacketCheckMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (usedAlignmentKeys) {
			GOTClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			alignmentChangeTick = 2;
		}
		if (keyBindingCargoCart != null && keyBindingCargoCart.isPressed()) {
			GOTPacketHandler.networkWrapper.sendToServer(new GOTPacketCargocartControl());
		}
	}

	@SubscribeEvent
	private void MouseInputEvent(InputEvent.MouseInputEvent event) {
		GOTAttackTiming.doAttackTiming();
	}

	@SubscribeEvent
	private void onTick(ClientTickEvent evt) {
		BitSet flags = dcm.getFlags();
		flags.set(0, getKeyBindingDragonUp().getIsKeyPressed());
		flags.set(1, getKeyBindingDragonDown().getIsKeyPressed());
		if (dcm.hasChanged()) {
			network.sendToServer(dcm);
		}
	}

	public static KeyBinding getKeyBindingAlignmentCycleLeft() {
		return keyBindingAlignmentCycleLeft;
	}

	public static KeyBinding getKeyBindingAlignmentCycleRight() {
		return keyBindingAlignmentCycleRight;
	}

	public static KeyBinding getKeyBindingAlignmentGroupNext() {
		return keyBindingAlignmentGroupNext;
	}

	public static KeyBinding getKeyBindingAlignmentGroupPrev() {
		return keyBindingAlignmentGroupPrev;
	}

	public static KeyBinding getKeyBindingDragonDown() {
		return keyBindingDragonDown;
	}

	public static KeyBinding getKeyBindingDragonUp() {
		return keyBindingDragonUp;
	}

	public static KeyBinding getKeyBindingFastTravel() {
		return keyBindingFastTravel;
	}

	public static KeyBinding getKeyBindingMapTeleport() {
		return keyBindingMapTeleport;
	}

	public static KeyBinding getKeyBindingMenu() {
		return keyBindingMenu;
	}

	public static KeyBinding getKeyBindingReturn() {
		return keyBindingReturn;
	}

	public static void setKeyBindingAlignmentCycleLeft(KeyBinding keyBindingAlignmentCycleLeft) {
		GOTKeyHandler.keyBindingAlignmentCycleLeft = keyBindingAlignmentCycleLeft;
	}

	public static void setKeyBindingAlignmentCycleRight(KeyBinding keyBindingAlignmentCycleRight) {
		GOTKeyHandler.keyBindingAlignmentCycleRight = keyBindingAlignmentCycleRight;
	}

	public static void setKeyBindingAlignmentGroupNext(KeyBinding keyBindingAlignmentGroupNext) {
		GOTKeyHandler.keyBindingAlignmentGroupNext = keyBindingAlignmentGroupNext;
	}

	public static void setKeyBindingAlignmentGroupPrev(KeyBinding keyBindingAlignmentGroupPrev) {
		GOTKeyHandler.keyBindingAlignmentGroupPrev = keyBindingAlignmentGroupPrev;
	}

	public static void setKeyBindingDragonDown(KeyBinding keyBindingDragonDown) {
		GOTKeyHandler.keyBindingDragonDown = keyBindingDragonDown;
	}

	public static void setKeyBindingDragonUp(KeyBinding keyBindingDragonUp) {
		GOTKeyHandler.keyBindingDragonUp = keyBindingDragonUp;
	}

	public static void setKeyBindingFastTravel(KeyBinding keyBindingFastTravel) {
		GOTKeyHandler.keyBindingFastTravel = keyBindingFastTravel;
	}

	public static void setKeyBindingMapTeleport(KeyBinding keyBindingMapTeleport) {
		GOTKeyHandler.keyBindingMapTeleport = keyBindingMapTeleport;
	}

	public static void setKeyBindingMenu(KeyBinding keyBindingMenu) {
		GOTKeyHandler.keyBindingMenu = keyBindingMenu;
	}

	public static void setKeyBindingReturn(KeyBinding keyBindingReturn) {
		GOTKeyHandler.keyBindingReturn = keyBindingReturn;
	}

	public static void update() {
		if (alignmentChangeTick > 0) {
			--alignmentChangeTick;
		}
	}
}
