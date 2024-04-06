package got.client.event.fml;

import com.google.common.math.IntMath;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.client.GOTAttackTiming;
import got.client.GOTClientProxy;
import got.client.event.both.GOTTickHandlerClient;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GOTKeyHandler {
	public static final KeyBinding KEY_BINDING_MENU = new KeyBinding("Menu", 38, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_MAP_TELEPORT = new KeyBinding("Map Teleport", 50, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_FAST_TRAVEL = new KeyBinding("Fast Travel", 33, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_ALIGNMENT_CYCLE_LEFT = new KeyBinding("Alignment Cycle Left", 203, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_ALIGNMENT_CYCLE_RIGHT = new KeyBinding("Alignment Cycle Right", 205, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_ALIGNMENT_GROUP_PREV = new KeyBinding("Alignment Group Prev", 200, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_ALIGNMENT_GROUP_NEXT = new KeyBinding("Alignment Group Next", 208, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_DRAGON_UP = new KeyBinding("Dragon: Fly Up", Keyboard.KEY_G, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_DRAGON_DOWN = new KeyBinding("Dragon: Fly Down", Keyboard.KEY_H, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_RETURN = new KeyBinding("Clear", Keyboard.KEY_RETURN, "Game of Thrones");

	private static final KeyBinding KEY_BINDING_CARGO_CART = new KeyBinding("Enable cargocart", 19, "Game of Thrones");

	private static final GOTPacketDragonControl DRAGON_CONTROL = new GOTPacketDragonControl();
	private static final Minecraft MC = Minecraft.getMinecraft();

	private static int alignmentChangeTick;

	public GOTKeyHandler() {
		ClientRegistry.registerKeyBinding(KEY_BINDING_MENU);
		ClientRegistry.registerKeyBinding(KEY_BINDING_MAP_TELEPORT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_FAST_TRAVEL);
		ClientRegistry.registerKeyBinding(KEY_BINDING_ALIGNMENT_CYCLE_LEFT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_ALIGNMENT_CYCLE_RIGHT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_ALIGNMENT_GROUP_PREV);
		ClientRegistry.registerKeyBinding(KEY_BINDING_ALIGNMENT_GROUP_NEXT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_DRAGON_UP);
		ClientRegistry.registerKeyBinding(KEY_BINDING_DRAGON_DOWN);
		ClientRegistry.registerKeyBinding(KEY_BINDING_CARGO_CART);
	}

	public static void update() {
		if (alignmentChangeTick > 0) {
			--alignmentChangeTick;
		}
	}

	@SubscribeEvent
	public void KeyInputEvent(InputEvent.KeyInputEvent event) {
		GOTAttackTiming.doAttackTiming();
		if (KEY_BINDING_MENU.getIsKeyPressed() && MC.currentScreen == null) {
			MC.thePlayer.openGui(GOT.instance, GOTGuiID.MENU.ordinal(), MC.theWorld, 0, 0, 0);
		}
		GOTPlayerData pd = GOTLevelData.getData(MC.thePlayer);
		boolean usedAlignmentKeys = false;
		boolean skippedHelp = false;
		Map<GOTDimension.DimensionRegion, GOTFaction> lastViewedRegions = new EnumMap<>(GOTDimension.DimensionRegion.class);
		GOTDimension currentDimension = GOTDimension.getCurrentDimension(MC.theWorld);
		GOTFaction currentFaction = pd.getViewingFaction();
		GOTDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
		List<GOTDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
		List<GOTFaction> factionList = currentRegion.factionList;
		if (MC.currentScreen == null && alignmentChangeTick <= 0) {
			int i;
			if (KEY_BINDING_RETURN.getIsKeyPressed()) {
				skippedHelp = true;
			}
			if (KEY_BINDING_ALIGNMENT_CYCLE_LEFT.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				--i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (KEY_BINDING_ALIGNMENT_CYCLE_RIGHT.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				++i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedAlignmentKeys = true;
			}
			if (regionList != null) {
				if (KEY_BINDING_ALIGNMENT_GROUP_PREV.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					--i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
				if (KEY_BINDING_ALIGNMENT_GROUP_NEXT.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedAlignmentKeys = true;
				}
			}
		}
		if (skippedHelp && GOTTickHandlerClient.renderMenuPrompt) {
			GOTTickHandlerClient.renderMenuPrompt = false;
			IMessage packet = new GOTPacketCheckMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
		if (usedAlignmentKeys) {
			GOTClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			alignmentChangeTick = 2;
		}
		if (KEY_BINDING_CARGO_CART.isPressed()) {
			GOTPacketHandler.networkWrapper.sendToServer(new GOTPacketCargocartControl());
		}
	}

	@SubscribeEvent
	public void mouseInputEvent(InputEvent.MouseInputEvent event) {
		GOTAttackTiming.doAttackTiming();
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent evt) {
		BitSet flags = DRAGON_CONTROL.getFlags();
		flags.set(0, KEY_BINDING_DRAGON_UP.getIsKeyPressed());
		flags.set(1, KEY_BINDING_DRAGON_DOWN.getIsKeyPressed());
		if (DRAGON_CONTROL.hasChanged()) {
			GOTPacketHandler.networkWrapper.sendToServer(DRAGON_CONTROL);
		}
	}
}
