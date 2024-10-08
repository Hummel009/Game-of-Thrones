package got.client;

import com.google.common.math.IntMath;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class GOTKeyHandler {
	public static final GOTKeyHandler INSTANCE = new GOTKeyHandler();

	public static final KeyBinding KEY_BINDING_MENU = new KeyBinding("Menu", 38, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_MAP_TELEPORT = new KeyBinding("Map Teleport", 50, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_FAST_TRAVEL = new KeyBinding("Fast Travel", 33, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_REPUTATION_CYCLE_LEFT = new KeyBinding("Reputation Cycle Left", 203, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_REPUTATION_CYCLE_RIGHT = new KeyBinding("Reputation Cycle Right", 205, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_REPUTATION_GROUP_PREV = new KeyBinding("Reputation Group Prev", 200, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_REPUTATION_GROUP_NEXT = new KeyBinding("Reputation Group Next", 208, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_DRAGON_UP = new KeyBinding("Dragon: Fly Up", Keyboard.KEY_G, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_DRAGON_DOWN = new KeyBinding("Dragon: Fly Down", Keyboard.KEY_H, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_DRAGON_DRACARYS = new KeyBinding("Dragon: Dracarys", Keyboard.KEY_J, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_RETURN = new KeyBinding("Clear", Keyboard.KEY_RETURN, "Game of Thrones");
	public static final KeyBinding KEY_BINDING_CARGO_CART = new KeyBinding("Enable cargocart", 19, "Game of Thrones");

	private static final Minecraft MC = Minecraft.getMinecraft();

	private static int reputationChangeTick;

	static {
		ClientRegistry.registerKeyBinding(KEY_BINDING_MENU);
		ClientRegistry.registerKeyBinding(KEY_BINDING_MAP_TELEPORT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_FAST_TRAVEL);
		ClientRegistry.registerKeyBinding(KEY_BINDING_REPUTATION_CYCLE_LEFT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_REPUTATION_CYCLE_RIGHT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_REPUTATION_GROUP_PREV);
		ClientRegistry.registerKeyBinding(KEY_BINDING_REPUTATION_GROUP_NEXT);
		ClientRegistry.registerKeyBinding(KEY_BINDING_DRAGON_UP);
		ClientRegistry.registerKeyBinding(KEY_BINDING_DRAGON_DOWN);
		ClientRegistry.registerKeyBinding(KEY_BINDING_DRAGON_DRACARYS);
		ClientRegistry.registerKeyBinding(KEY_BINDING_CARGO_CART);
	}

	private final GOTPacketDragonControl dcm = new GOTPacketDragonControl();

	public static void update() {
		if (reputationChangeTick > 0) {
			--reputationChangeTick;
		}
	}

	@SubscribeEvent
	@SuppressWarnings("MethodMayBeStatic")
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		GOTAttackTiming.doAttackTiming();
		if (KEY_BINDING_MENU.getIsKeyPressed() && MC.currentScreen == null) {
			MC.thePlayer.openGui(GOT.instance, GOTGuiId.MENU.ordinal(), MC.theWorld, 0, 0, 0);
		}
		GOTPlayerData pd = GOTLevelData.getData(MC.thePlayer);
		boolean usedReputationKeys = false;
		boolean skippedHelp = false;
		Map<GOTDimension.DimensionRegion, GOTFaction> lastViewedRegions = new EnumMap<>(GOTDimension.DimensionRegion.class);
		GOTDimension currentDimension = GOTDimension.GAME_OF_THRONES;
		GOTFaction currentFaction = pd.getViewingFaction();
		GOTDimension.DimensionRegion currentRegion = currentFaction.getFactionRegion();
		List<GOTDimension.DimensionRegion> regionList = currentDimension.getDimensionRegions();
		List<GOTFaction> factionList = currentRegion.getFactionList();
		if (MC.currentScreen == null && reputationChangeTick <= 0) {
			int i;
			if (KEY_BINDING_RETURN.getIsKeyPressed()) {
				skippedHelp = true;
			}
			if (KEY_BINDING_REPUTATION_CYCLE_LEFT.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				--i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedReputationKeys = true;
			}
			if (KEY_BINDING_REPUTATION_CYCLE_RIGHT.getIsKeyPressed()) {
				i = factionList.indexOf(currentFaction);
				++i;
				i = IntMath.mod(i, factionList.size());
				currentFaction = factionList.get(i);
				usedReputationKeys = true;
			}
			if (regionList != null) {
				if (KEY_BINDING_REPUTATION_GROUP_PREV.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					--i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedReputationKeys = true;
				}
				if (KEY_BINDING_REPUTATION_GROUP_NEXT.getIsKeyPressed()) {
					pd.setRegionLastViewedFaction(currentRegion, currentFaction);
					lastViewedRegions.put(currentRegion, currentFaction);
					i = regionList.indexOf(currentRegion);
					++i;
					i = IntMath.mod(i, regionList.size());
					currentRegion = regionList.get(i);
					currentFaction = pd.getRegionLastViewedFaction(currentRegion);
					usedReputationKeys = true;
				}
			}
		}
		if (skippedHelp && GOTTickHandlerClient.isRenderMenuPrompt()) {
			GOTTickHandlerClient.setRenderMenuPrompt(false);
			IMessage packet = new GOTPacketCheckMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
		if (usedReputationKeys) {
			GOTClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
			reputationChangeTick = 2;
		}
		if (KEY_BINDING_CARGO_CART.isPressed()) {
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(new GOTPacketCargocartControl());
		}
	}

	@SubscribeEvent
	@SuppressWarnings("MethodMayBeStatic")
	public void onMouseInput(InputEvent.MouseInputEvent event) {
		GOTAttackTiming.doAttackTiming();
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent evt) {
		BitSet flags = dcm.getFlags();
		flags.set(0, KEY_BINDING_DRAGON_UP.getIsKeyPressed());
		flags.set(1, KEY_BINDING_DRAGON_DOWN.getIsKeyPressed());
		flags.set(2, KEY_BINDING_DRAGON_DRACARYS.getIsKeyPressed());
		if (dcm.hasChanged()) {
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(dcm);
		}
	}
}