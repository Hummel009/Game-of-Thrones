package got.client;

import java.util.*;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import got.client.effect.*;
import got.client.gui.*;
import got.client.render.GOTRender;
import got.client.render.other.*;
import got.client.sound.GOTMusic;
import got.common.*;
import got.common.controller.GOTControllerGrabble;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.dragon.GOTEntityDragon3DViewer;
import got.common.entity.other.*;
import got.common.faction.*;
import got.common.item.other.GOTItemClick;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.*;
import got.common.util.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class GOTClientProxy extends GOTCommonProxy {
	private static ResourceLocation enchantmentTexture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static ResourceLocation alignmentTexture = new ResourceLocation("got:textures/gui/alignment.png");
	private static ResourceLocation particlesTexture = new ResourceLocation("got:textures/misc/particles.png");
	private static ResourceLocation particles2Texture = new ResourceLocation("got:textures/misc/particles2.png");
	private static ResourceLocation customEffectsTexture = new ResourceLocation("got:textures/gui/effects.png");
	private static int TESSELLATOR_MAX_BRIGHTNESS = 15728880;
	private static int FONTRENDERER_ALPHA_MIN = 4;
	private static GOTEffectRenderer customEffectRenderer;
	private static GOTRenderPlayer specialPlayerRenderer = new GOTRenderPlayer();
	private static GOTSwingHandler swingHandler = new GOTSwingHandler();
	private static GOTTickHandlerClient tickHandler = new GOTTickHandlerClient();
	private static GOTGuiHandler guiHandler = new GOTGuiHandler();
	private static GOTMusic musicHandler;
	private static GOTRenderCrosshair crosshairrenderer = new GOTRenderCrosshair();
	private int beaconRenderID;
	private int barrelRenderID;
	private int bombRenderID;
	private int doubleTorchRenderID;
	private int plateRenderID;
	private int stalactiteRenderID;
	private int flowerPotRenderID;
	private int cloverRenderID;
	private int plantainRenderID;
	private int fenceRenderID;
	private int grassRenderID;
	private int fallenLeavesRenderID;
	private int commandTableRenderID;
	private int butterflyJarRenderID;
	private int unsmelteryRenderID;
	private int chestRenderID;
	private int reedsRenderID;
	private int wasteRenderID;
	private int beamRenderID;
	private int vCauldronRenderID;
	private int grapevineRenderID;
	private int thatchFloorRenderID;
	private int treasureRenderID;
	private int flowerRenderID;
	private int doublePlantRenderID;
	private int birdCageRenderID;
	private int wildFireJarRenderID;
	private int coralRenderID;
	private int doorRenderID;
	private int ropeRenderID;
	private int chainRenderID;
	private int trapdoorRenderID;
	private boolean prevleftclick;
	private Map<Integer, Long> enderlaunchtimer = new HashMap<>();

	@Override
	public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
		GOTGuiMap.addPlayerLocationInfo(player, posX, posZ);
	}

	@Override
	public void blockbreak(BreakEvent event) {
		GOTBlockPos pos = new GOTBlockPos(event.x, event.y, event.z);
		if (GOTGrappleHelper.controllerpos.containsKey(pos)) {
			GOTControllerGrabble control = GOTGrappleHelper.controllerpos.get(pos);

			control.unattach();

			GOTGrappleHelper.controllerpos.remove(pos);
		}
	}

	@Override
	public void cancelItemHighlight() {
		GOTClientProxy.getTickHandler().setCancelItemHighlight(true);
	}

	@Override
	public void clearMapPlayerLocations() {
		GOTGuiMap.clearPlayerLocations();
	}

	@Override
	public void clientReceiveSpeech(GOTEntityNPC npc, String speech) {
		GOTSpeechClient.receiveSpeech(npc, speech);
	}

	@Override
	public void displayAlignmentSee(String username, Map<GOTFaction, Float> alignments) {
		GOTGuiFactions gui = new GOTGuiFactions();
		gui.setOtherPlayer(username, alignments);
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(gui);
	}

	@Override
	public void displayBannerGui(GOTEntityBanner banner) {
		Minecraft mc = Minecraft.getMinecraft();
		GOTGuiBanner gui = new GOTGuiBanner(banner);
		mc.displayGuiScreen(gui);
	}

	@Override
	public void displayFTScreen(GOTAbstractWaypoint waypoint, int startX, int startZ) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GOTGuiFastTravel(waypoint, startX, startZ));
	}

	@Override
	public void displayMenuPrompt(GOTPacketMenuPrompt.Type type) {
		if (type == GOTPacketMenuPrompt.Type.MENU) {
			GOTTickHandlerClient.setRenderMenuPrompt(true);
		}
	}

	@Override
	public void displayMessage(GOTGuiMessageTypes message) {
		Minecraft.getMinecraft().displayGuiScreen(new GOTGuiMessage(message));
	}

	@Override
	public void displayMiniquestOffer(GOTMiniQuest quest, GOTEntityNPC npc) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GOTGuiMiniquestOffer(quest, npc));
	}

	@Override
	public void displayNewDate() {
		getTickHandler().updateDate();
	}

	@Override
	public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (!world.isRemote) {
			super.fillMugFromCauldron(world, i, j, k, side, itemstack);
		} else {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		}
	}

	@Override
	public int getBarrelRenderID() {
		return barrelRenderID;
	}

	@Override
	public int getBeaconRenderID() {
		return beaconRenderID;
	}

	@Override
	public int getBeamRenderID() {
		return beamRenderID;
	}

	@Override
	public int getBirdCageRenderID() {
		return birdCageRenderID;
	}

	@Override
	public int getBombRenderID() {
		return bombRenderID;
	}

	@Override
	public int getButterflyJarRenderID() {
		return butterflyJarRenderID;
	}

	@Override
	public int getChainRenderID() {
		return chainRenderID;
	}

	@Override
	public int getChestRenderID() {
		return chestRenderID;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public int getCloverRenderID() {
		return cloverRenderID;
	}

	@Override
	public int getCommandTableRenderID() {
		return commandTableRenderID;
	}

	@Override
	public int getCoralRenderID() {
		return coralRenderID;
	}

	@Override
	public int getDoorRenderID() {
		return doorRenderID;
	}

	@Override
	public int getDoublePlantRenderID() {
		return doublePlantRenderID;
	}

	@Override
	public int getDoubleTorchRenderID() {
		return doubleTorchRenderID;
	}

	@Override
	public int getFallenLeavesRenderID() {
		return fallenLeavesRenderID;
	}

	@Override
	public int getFenceRenderID() {
		return fenceRenderID;
	}

	@Override
	public int getFlowerPotRenderID() {
		return flowerPotRenderID;
	}

	@Override
	public int getFlowerRenderID() {
		return flowerRenderID;
	}

	@Override
	public int getGrapevineRenderID() {
		return grapevineRenderID;
	}

	@Override
	public int getGrassRenderID() {
		return grassRenderID;
	}

	@Override
	public int getPlantainRenderID() {
		return plantainRenderID;
	}

	@Override
	public int getPlateRenderID() {
		return plateRenderID;
	}

	@Override
	public void getplayermovement(GOTControllerGrabble control, int playerid) {
		Entity entity = control.entity;
		if (entity instanceof EntityPlayerSP) {
			EntityPlayerSP player = (EntityPlayerSP) entity;
			control.receivePlayerMovementMessage(player.moveStrafing, player.moveForward, player.movementInput.jump);
		}
	}

	@Override
	public int getReedsRenderID() {
		return reedsRenderID;
	}

	@Override
	public int getRopeRenderID() {
		return ropeRenderID;
	}

	@Override
	public int getStalactiteRenderID() {
		return stalactiteRenderID;
	}

	@Override
	public int getThatchFloorRenderID() {
		return thatchFloorRenderID;
	}

	@Override
	public int getTrapdoorRenderID() {
		return trapdoorRenderID;
	}

	@Override
	public int getTreasureRenderID() {
		return treasureRenderID;
	}

	@Override
	public int getUnsmelteryRenderID() {
		return unsmelteryRenderID;
	}

	@Override
	public int getVCauldronRenderID() {
		return vCauldronRenderID;
	}

	@Override
	public int getWasteRenderID() {
		return wasteRenderID;
	}

	@Override
	public int getWildFireJarRenderID() {
		return wildFireJarRenderID;
	}

	@Override
	public void handleDeath(Entity entity) {
		int id = entity.getEntityId();
		if (GOTGrappleHelper.controllers.containsKey(id)) {
			GOTControllerGrabble controller = GOTGrappleHelper.controllers.get(id);
			controller.unattach();
		}
	}

	@Override
	public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
		Entity e;
		GOTInvasionStatus status = GOTTickHandlerClient.getWatchedInvasion();
		e = getClientWorld().getEntityByID(invasionEntityID);
		if ((overrideAlreadyWatched || !status.isActive()) && e instanceof GOTEntityInvasionSpawner) {
			status.setWatchedInvasion((GOTEntityInvasionSpawner) e);
		}
	}

	@Override
	public boolean isClient() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}

	@Override
	public boolean isPaused() {
		return Minecraft.getMinecraft().isGamePaused();
	}

	@Override
	public boolean isSingleplayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}

	@Override
	public boolean isSneaking(Entity entity) {
		if (entity.equals(Minecraft.getMinecraft().thePlayer)) {
			return GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak);
		}
		return entity.isSneaking();
	}

	@Override
	public void launchplayer(EntityPlayer player) {
		if (enderlaunchtimer.containsKey(player.getEntityId())) {
			enderlaunchtimer.get(player.getEntityId());
		}
		player.worldObj.getTotalWorldTime();
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player != null && (!Minecraft.getMinecraft().isGamePaused() || !Minecraft.getMinecraft().isSingleplayer())) {
			try {
				Collection<GOTControllerGrabble> controllers = GOTGrappleHelper.controllers.values();
				for (GOTControllerGrabble controller : controllers) {
					controller.doClientTick();
				}
			} catch (ConcurrentModificationException e) {
				System.out.println("ConcurrentModificationException caught");
			}

			boolean leftclick = GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindAttack) && Minecraft.getMinecraft().currentScreen == null;
			if (prevleftclick != leftclick) {
				ItemStack stack = player.getHeldItem();
				if (stack != null) {
					Item item = stack.getItem();
					if (item instanceof GOTItemClick) {
						if (leftclick) {
							((GOTItemClick) item).onLeftClick(stack, player);
						} else {
							((GOTItemClick) item).onLeftClickRelease(stack, player);
						}
					}
				}
			}

			prevleftclick = leftclick;

			if (player.onGround && enderlaunchtimer.containsKey(player.getEntityId())) {
				long timer = player.worldObj.getTotalWorldTime() - enderlaunchtimer.get(player.getEntityId());
				if (timer > 10) {
					resetlaunchertime(player.getEntityId());
				}
			}
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		setCustomEffectRenderer(new GOTEffectRenderer(Minecraft.getMinecraft()));
		GOTTextures.onInit();
		GOTRender.onInit();
		for (Class cl : GOTRender.getRenders().keySet()) {
			RenderingRegistry.registerEntityRenderingHandler(cl, GOTRender.getRenders().get(cl));
		}
		beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
		barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
		bombRenderID = RenderingRegistry.getNextAvailableRenderId();
		doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
		plateRenderID = RenderingRegistry.getNextAvailableRenderId();
		stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
		flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
		plantainRenderID = RenderingRegistry.getNextAvailableRenderId();
		cloverRenderID = RenderingRegistry.getNextAvailableRenderId();
		fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
		grassRenderID = RenderingRegistry.getNextAvailableRenderId();
		fallenLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		commandTableRenderID = RenderingRegistry.getNextAvailableRenderId();
		butterflyJarRenderID = RenderingRegistry.getNextAvailableRenderId();
		unsmelteryRenderID = RenderingRegistry.getNextAvailableRenderId();
		chestRenderID = RenderingRegistry.getNextAvailableRenderId();
		reedsRenderID = RenderingRegistry.getNextAvailableRenderId();
		wasteRenderID = RenderingRegistry.getNextAvailableRenderId();
		beamRenderID = RenderingRegistry.getNextAvailableRenderId();
		vCauldronRenderID = RenderingRegistry.getNextAvailableRenderId();
		grapevineRenderID = RenderingRegistry.getNextAvailableRenderId();
		thatchFloorRenderID = RenderingRegistry.getNextAvailableRenderId();
		treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
		flowerRenderID = RenderingRegistry.getNextAvailableRenderId();
		doublePlantRenderID = RenderingRegistry.getNextAvailableRenderId();
		birdCageRenderID = RenderingRegistry.getNextAvailableRenderId();
		wildFireJarRenderID = RenderingRegistry.getNextAvailableRenderId();
		coralRenderID = RenderingRegistry.getNextAvailableRenderId();
		doorRenderID = RenderingRegistry.getNextAvailableRenderId();
		ropeRenderID = RenderingRegistry.getNextAvailableRenderId();
		chainRenderID = RenderingRegistry.getNextAvailableRenderId();
		trapdoorRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(beaconRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(barrelRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(bombRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(doubleTorchRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(plateRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(stalactiteRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(flowerPotRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(cloverRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(plantainRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(fenceRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(grassRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(fallenLeavesRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(commandTableRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(butterflyJarRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(unsmelteryRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(chestRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(reedsRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(wasteRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(beamRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(vCauldronRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(grapevineRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(thatchFloorRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(treasureRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(flowerRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(doublePlantRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(birdCageRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(wildFireJarRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(coralRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(doorRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(ropeRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(chainRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(trapdoorRenderID, new GOTRenderBlocks(true));
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityBeacon.class, new GOTRenderBeacon());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityPlate.class, new GOTRenderPlateFood());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntitySpawnerChest.class, new GOTRenderSpawnerChest());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityArmorStand.class, new GOTRenderArmorStand());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityMug.class, new GOTRenderMug());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityCommandTable.class, new GOTRenderCommandTable());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityAnimalJar.class, new GOTRenderAnimalJar());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityUnsmeltery.class, new GOTRenderUnsmeltery());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntitySarbacaneTrap.class, new GOTRenderDartTrap());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityChest.class, new GOTRenderChest());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityWeaponRack.class, new GOTRenderWeaponRack());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntityKebabStand.class, new GOTRenderKebabStand());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntitySignCarved.class, new GOTRenderSignCarved());
		ClientRegistry.bindTileEntitySpecialRenderer(GOTTileEntitySignCarvedValyrian.class, new GOTRenderSignCarvedValyrian());
		FMLCommonHandler.instance().bus().register(new GOTEntityDragon3DViewer());
		FMLCommonHandler.instance().bus().register(new GOTEntityMammoth3DViewer());
		FMLCommonHandler.instance().bus().register(new GOTEntityElephant3DViewer());
		FMLCommonHandler.instance().bus().register(new GOTKeyHandler(GOTPacketHandler.networkWrapper));
	}

	@Override
	public void onPostload() {
		super.onPostload();
		setMusicHandler(new GOTMusic());
		specialPlayerRenderer.postInit();
		crosshairrenderer.postInit();
		guiHandler.postInit();
		swingHandler.postInit();
	}

	@Override
	public void onPreload() {
		super.onPreload();
		System.setProperty("fml.skipFirstTextureLoad", "false");
		GOTItemRendererManager.preInit();
		GOTArmorModels.preInit();
	}

	@Override
	public void openHiredNPCGui(GOTEntityNPC npc) {
		Minecraft mc = Minecraft.getMinecraft();
		if (npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR) {
			mc.displayGuiScreen(new GOTGuiHiredWarrior(npc));
		} else if (npc.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER) {
			mc.displayGuiScreen(new GOTGuiHiredFarmer(npc));
		}
	}

	@Override
	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (!world.isRemote) {
			super.placeFlowerInPot(world, i, j, k, side, itemstack);
		} else {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		}
	}

	@Override
	public void queueAchievement(GOTAchievement achievement) {
		GOTTickHandlerClient.getNotificationDisplay().queueAchievement(achievement);
	}

	@Override
	public void queueConquestNotification(GOTFaction fac, float conq, boolean isCleansing) {
		GOTTickHandlerClient.getNotificationDisplay().queueConquest(fac, conq, isCleansing);
	}

	@Override
	public void queueFellowshipNotification(IChatComponent message) {
		GOTTickHandlerClient.getNotificationDisplay().queueFellowshipNotification(message);
	}

	@Override
	public void receiveConquestGrid(GOTFaction conqFac, List<GOTConquestZone> allZones) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiMap) {
			((GOTGuiMap) gui).receiveConquestGrid(conqFac, allZones);
		}
	}

	@Override
	public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		Potion potion = Potion.potionTypes[effect.getPotionID()];
		mc.getTextureManager().bindTexture(customEffectsTexture);
		int l = potion.getStatusIconIndex();
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			screen.drawTexturedModalRect(x + 6, y + 7, 0 + l % 8 * 18, 0 + l / 8 * 18, 18, 18);
		}
	}

	@Override
	public void resetlaunchertime(int playerid) {
		if (enderlaunchtimer.containsKey(playerid)) {
			enderlaunchtimer.put(playerid, (long) 0);
		}
	}

	@Override
	public void setClientDifficulty(EnumDifficulty difficulty) {
		Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
	}

	@Override
	public void setInPortal(EntityPlayer entityplayer) {
		if (!GOTTickHandlerClient.getPlayersInPortals().containsKey(entityplayer)) {
			GOTTickHandlerClient.getPlayersInPortals().put(entityplayer, 0);
		}
		if (Minecraft.getMinecraft().isSingleplayer() && !GOTTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
			GOTTickHandlerServer.playersInPortals.put(entityplayer, 0);
		}
	}

	@Override
	public void setMapCWPProtectionMessage(IChatComponent message) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiMap) {
			((GOTGuiMap) gui).setCWPProtectionMessage(message);
		}
	}

	@Override
	public void setMapIsOp(boolean isOp) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiMap) {
			GOTGuiMap map = (GOTGuiMap) gui;
			map.setPlayerOp(isOp);
		}
	}

	@Override
	public void setTrackedQuest(GOTMiniQuest quest) {
		GOTTickHandlerClient.getMiniquestTracker().setTrackedQuest(quest);
	}

	@Override
	public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
		GOTGuiMap.setShowWP(showWP);
		GOTGuiMap.setShowCWP(showCWP);
		GOTGuiMap.setShowHiddenSWP(showHiddenSWP);
	}

	@Override
	public void showBurnOverlay() {
		getTickHandler().onBurnDamage();
	}

	@Override
	public void showFrostOverlay() {
		getTickHandler().onFrostDamage();
	}

	@Override
	public void spawnAlignmentBonus(GOTFaction faction, float prevMainAlignment, GOTAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
		World world = getClientWorld();
		if (world != null) {
			GOTEntityAlignmentBonus entity = new GOTEntityAlignmentBonus(world, posX, posY, posZ, name, faction, prevMainAlignment, factionBonusMap, isKill, isHiredKill, conquestBonus);
			world.spawnEntityInWorld(entity);
		}
	}

	@Override
	public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.renderViewEntity != null && mc.theWorld != null) {
			WorldClient world = mc.theWorld;
			Random rand = world.rand;
			int i = mc.gameSettings.particleSetting;
			if (i == 1 && world.rand.nextInt(3) == 0) {
				i = 2;
			}
			if (i > 1) {
				return;
			}
			if ("blueFlame".equals(type)) {
				getCustomEffectRenderer().addEffect(new GOTEntityGreenFlameFX(world, d, d1, d2, d3, d4, d5));
			} else if ("chill".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityChillFX(world, d, d1, d2, d3, d4, d5));
			} else if (type.startsWith("moonGlow")) {
				GOTEntityGlowFX fx = new GOTEntityGlowFX(world, d, d1, d2, d3, d4, d5);
				int subIndex = type.indexOf("_");
				if (subIndex > -1) {
					String hex = type.substring(subIndex + 1);
					int color = Integer.parseInt(hex, 16);
					fx.setGlowColor(color);
				}
				mc.effectRenderer.addEffect(fx);
			} else if ("largeStone".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.stone, 0));
			} else if (type.startsWith("leaf")) {
				String s = type.substring("leaf".length());
				int[] texIndices = null;
				if (s.startsWith("Gold")) {
					texIndices = rand.nextBoolean() ? GOTFunctions.intRange(0, 5) : GOTFunctions.intRange(8, 13);
				} else if (s.startsWith("Red")) {
					texIndices = rand.nextBoolean() ? GOTFunctions.intRange(16, 21) : GOTFunctions.intRange(24, 29);
				} else if (s.startsWith("Mirk")) {
					texIndices = rand.nextBoolean() ? GOTFunctions.intRange(32, 37) : GOTFunctions.intRange(40, 45);
				} else if (s.startsWith("Green")) {
					texIndices = rand.nextBoolean() ? GOTFunctions.intRange(48, 53) : GOTFunctions.intRange(56, 61);
				}
				if (texIndices != null) {
					if (type.indexOf("_") > -1) {
						int age = Integer.parseInt(type.substring(type.indexOf("_") + 1));
						getCustomEffectRenderer().addEffect(new GOTEntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices, age));
					} else {
						getCustomEffectRenderer().addEffect(new GOTEntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices));
					}
				}
			} else if ("marshFlame".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityMarshFlameFX(world, d, d1, d2, d3, d4, d5));
			} else if ("marshLight".equals(type)) {
				getCustomEffectRenderer().addEffect(new GOTEntityMarshLightFX(world, d, d1, d2, d3, d4, d5));
			} else if ("asshaiWater".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, GOTBiome.shadowTown.getWaterColorMultiplier()));
			} else if ("asshaiTorch".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityAsshaiPoisonFX(world, d, d1, d2, d3, d4, d5));
			} else if ("music".equals(type)) {
				double pitch = world.rand.nextDouble();
				GOTEntityMusicFX note = new GOTEntityMusicFX(world, d, d1, d2, d3, d4, d5, pitch);
				mc.effectRenderer.addEffect(note);
			} else if ("wave".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityWaveFX(world, d, d1, d2, d3, d4, d5));
			} else if ("whiteSmoke".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityWhiteSmokeFX(world, d, d1, d2, d3, d4, d5));
			}
		}
	}

	@Override
	public void testReflection(World world) {
		GOTReflection.testAll(world);
		GOTReflectionClient.testAll(world, Minecraft.getMinecraft());
	}

	@Override
	public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
		if (!world.isRemote) {
			super.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, pouchSlot);
		} else {
			((EntityClientPlayerMP) entityplayer).sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
		}
	}

	@Override
	public void validateBannerUsername(GOTEntityBanner banner, int slot, String prevText, boolean valid) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiBanner) {
			GOTGuiBanner guiBanner = (GOTGuiBanner) gui;
			if (guiBanner.getTheBanner().equals(banner)) {
				guiBanner.validateUsername(slot, prevText, valid);
			}
		}
	}

	public static boolean doesClientChunkExist(World world, int i, int k) {
		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		Chunk chunk = world.getChunkProvider().provideChunk(chunkX, chunkZ);
		return !(chunk instanceof EmptyChunk);
	}

	public static ResourceLocation getAlignmentTexture() {
		return alignmentTexture;
	}

	public static int getAlphaInt(float alphaF) {
		int alphaI = (int) (alphaF * 255.0f);
		return MathHelper.clamp_int(alphaI, FONTRENDERER_ALPHA_MIN, 255);
	}

	public static GOTEffectRenderer getCustomEffectRenderer() {
		return customEffectRenderer;
	}

	public static ResourceLocation getEnchantmentTexture() {
		return enchantmentTexture;
	}

	public static GOTMusic getMusicHandler() {
		return musicHandler;
	}

	public static ResourceLocation getParticles2Texture() {
		return particles2Texture;
	}

	public static ResourceLocation getParticlesTexture() {
		return particlesTexture;
	}

	public static int getTesselatorMaxBrightness() {
		return TESSELLATOR_MAX_BRIGHTNESS;
	}

	public static GOTTickHandlerClient getTickHandler() {
		return tickHandler;
	}

	public static void renderEnchantmentEffect() {
		Tessellator tessellator = Tessellator.instance;
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		GL11.glDepthFunc(514);
		GL11.glDisable(2896);
		texturemanager.bindTexture(getEnchantmentTexture());
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		float shade = 0.76f;
		GL11.glColor4f(0.5f * shade, 0.25f * shade, 0.8f * shade, 1.0f);
		GL11.glMatrixMode(5890);
		GL11.glPushMatrix();
		float scale = 0.125f;
		GL11.glScalef(scale, scale, scale);
		float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
		GL11.glTranslatef(randomShift, 0.0f, 0.0f);
		GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
		ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		randomShift = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
		GL11.glTranslatef(-randomShift, 0.0f, 0.0f);
		GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
		ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
		GL11.glPopMatrix();
		GL11.glMatrixMode(5888);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glDepthFunc(515);
	}

	public static void sendClientInfoPacket(GOTFaction viewingFaction, Map<GOTDimension.DimensionRegion, GOTFaction> changedRegionMap) {
		boolean showWP = GOTGuiMap.isShowWP();
		boolean showCWP = GOTGuiMap.isShowCWP();
		boolean showHiddenSWP = GOTGuiMap.isShowHiddenSWP();
		GOTPacketClientInfo packet = new GOTPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	public static void setAlignmentTexture(ResourceLocation alignmentTexture) {
		GOTClientProxy.alignmentTexture = alignmentTexture;
	}

	public static void setCustomEffectRenderer(GOTEffectRenderer customEffectRenderer) {
		GOTClientProxy.customEffectRenderer = customEffectRenderer;
	}

	public static void setEnchantmentTexture(ResourceLocation enchantmentTexture) {
		GOTClientProxy.enchantmentTexture = enchantmentTexture;
	}

	public static void setMusicHandler(GOTMusic musicHandler) {
		GOTClientProxy.musicHandler = musicHandler;
	}

	public static void setParticles2Texture(ResourceLocation particles2Texture) {
		GOTClientProxy.particles2Texture = particles2Texture;
	}

	public static void setParticlesTexture(ResourceLocation particlesTexture) {
		GOTClientProxy.particlesTexture = particlesTexture;
	}

	public static void setTesselatorMaxBrightness(int tESSELLATOR_MAX_BRIGHTNESS) {
		TESSELLATOR_MAX_BRIGHTNESS = tESSELLATOR_MAX_BRIGHTNESS;
	}

	public static void setTickHandler(GOTTickHandlerClient tickHandler) {
		GOTClientProxy.tickHandler = tickHandler;
	}
}