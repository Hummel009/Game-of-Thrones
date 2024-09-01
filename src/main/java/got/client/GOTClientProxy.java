package got.client;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import got.client.effect.*;
import got.client.gui.*;
import got.client.render.GOTRender;
import got.client.render.other.*;
import got.common.GOTCommonProxy;
import got.common.GOTDimension;
import got.common.GOTGuiMessageTypes;
import got.common.GOTTickHandlerServer;
import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.entity.other.inanimate.GOTEntityInvasionSpawner;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.entity.other.utils.GOTInvasionStatus;
import got.common.faction.GOTReputationBonusMap;
import got.common.faction.GOTFaction;
import got.common.network.GOTPacketClientInfo;
import got.common.network.GOTPacketFellowshipAcceptInviteResult;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMenuPrompt;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.*;
import got.common.util.GOTFunctions;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTConquestZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GOTClientProxy extends GOTCommonProxy {
	public static final ResourceLocation ENCHANTMENT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	public static final ResourceLocation REPUTATION_TEXTURE = new ResourceLocation("got:textures/gui/reputation.png");
	public static final ResourceLocation PARTICLES_TEXTURE = new ResourceLocation("got:textures/misc/particles.png");
	public static final ResourceLocation PARTICLES_2_TEXTURE = new ResourceLocation("got:textures/misc/particles2.png");

	public static final int TESSELLATOR_MAX_BRIGHTNESS = 15728880;

	private static final ResourceLocation CUSTOM_POTIONS_TEXTURE = new ResourceLocation("got:textures/gui/effects.png");

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
	private int leavesRenderID;
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

	public static boolean doesClientChunkExist(World world, int i, int k) {
		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		Chunk chunk = world.getChunkProvider().provideChunk(chunkX, chunkZ);
		return !(chunk instanceof EmptyChunk);
	}

	public static int getAlphaInt(float alphaF) {
		int alphaI = (int) (alphaF * 255.0F);
		return MathHelper.clamp_int(alphaI, 4, 255);
	}

	public static void renderEnchantmentEffect() {
		Tessellator tessellator = Tessellator.instance;
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		GL11.glDepthFunc(514);
		GL11.glDisable(2896);
		texturemanager.bindTexture(ENCHANTMENT_TEXTURE);
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		float shade = 0.76F;
		GL11.glColor4f(0.5F * shade, 0.25F * shade, 0.8F * shade, 1.0F);
		GL11.glMatrixMode(5890);
		GL11.glPushMatrix();
		float scale = 0.125F;
		GL11.glScalef(scale, scale, scale);
		float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
		GL11.glTranslatef(randomShift, 0.0F, 0.0F);
		GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		randomShift = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
		GL11.glTranslatef(-randomShift, 0.0F, 0.0F);
		GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
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
		IMessage packet = new GOTPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
		GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
	}

	@Override
	public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
		GOTGuiMap.addPlayerLocationInfo(player, posX, posZ);
	}

	@Override
	public void cancelItemHighlight() {
		GOTClientFactory.getTickHandlerClient().setCancelItemHighlight(true);
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
	public void displayReputationSee(String username, Map<GOTFaction, Float> reputations) {
		GOTGuiFactions gui = new GOTGuiFactions();
		gui.setOtherPlayer(username, reputations);
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
	public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiFellowships) {
			((GOTGuiFellowships) gui).displayAcceptInvitationResult(fellowshipID, name, result);
		}
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
		GOTClientFactory.getTickHandlerClient().updateDate();
	}

	@Override
	public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
		} else {
			super.fillMugFromCauldron(world, i, j, k, side, itemstack);
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
	public int getLeavesRenderID() {
		return leavesRenderID;
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
	public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
		GOTInvasionStatus status = GOTTickHandlerClient.WATCHED_INVASION;
		if (overrideAlreadyWatched || !status.isActive()) {
			World world = getClientWorld();
			Entity e = world.getEntityByID(invasionEntityID);
			if (e instanceof GOTEntityInvasionSpawner) {
				status.setWatchedInvasion((GOTEntityInvasionSpawner) e);
			}
		}
	}

	@Override
	public boolean isClient() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}

	@Override
	public boolean isSingleplayer() {
		return Minecraft.getMinecraft().isSingleplayer();
	}

	@Override
	public void onInit() {
		GOTClientFactory.onInit();
		GOTTextures.onInit();
		GOTRender.onInit();
		for (Map.Entry<Class<? extends Entity>, Render> cl : GOTRender.RENDERS.entrySet()) {
			RenderingRegistry.registerEntityRenderingHandler(cl.getKey(), cl.getValue());
		}
		beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
		barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
		bombRenderID = RenderingRegistry.getNextAvailableRenderId();
		doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
		plateRenderID = RenderingRegistry.getNextAvailableRenderId();
		stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
		flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
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
		plantainRenderID = RenderingRegistry.getNextAvailableRenderId();
		leavesRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(plantainRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(leavesRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(beaconRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(barrelRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(bombRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(doubleTorchRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(plateRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(stalactiteRenderID, new GOTRenderBlocks(true));
		RenderingRegistry.registerBlockHandler(flowerPotRenderID, new GOTRenderBlocks(false));
		RenderingRegistry.registerBlockHandler(cloverRenderID, new GOTRenderBlocks(true));
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
		GOTClientFactory.getThirdPersonViewer().init();
	}

	@Override
	public void openHiredNPCGui(GOTEntityNPC npc) {
		Minecraft mc = Minecraft.getMinecraft();
		if (npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.WARRIOR) {
			mc.displayGuiScreen(new GOTGuiHiredWarrior(npc));
		} else if (npc.getHireableInfo().getHiredTask() == GOTHireableInfo.Task.FARMER) {
			mc.displayGuiScreen(new GOTGuiHiredFarmer(npc));
		}
	}

	@Override
	public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
		} else {
			super.placeFlowerInPot(world, i, j, k, side, itemstack);
		}
	}

	@Override
	public void postInit() {
		GOTClientFactory.postInit();
	}

	@Override
	public void preInit() {
		System.setProperty("fml.skipFirstTextureLoad", "false");
		GOTClientFactory.preInit();
	}

	@Override
	public void queueAchievement(GOTAchievement achievement) {
		GOTClientFactory.getNotificationDisplay().queueAchievement(achievement);
	}

	@Override
	public void queueConquestNotification(GOTFaction fac, float conq, boolean isCleansing) {
		GOTClientFactory.getNotificationDisplay().queueConquest(fac, conq, isCleansing);
	}

	@Override
	public void queueFellowshipNotification(IChatComponent message) {
		GOTClientFactory.getNotificationDisplay().queueFellowshipNotification(message);
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
		mc.getTextureManager().bindTexture(CUSTOM_POTIONS_TEXTURE);
		int l = potion.getStatusIconIndex();
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			screen.drawTexturedModalRect(x + 6, y + 7, l % 8 * 18, l / 8 * 18, 18, 18);
		}
	}

	@Override
	public void setClientDifficulty(EnumDifficulty difficulty) {
		Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
	}

	@Override
	public void setInPortal(EntityPlayer entityplayer) {
		if (!GOTTickHandlerClient.PLAYERS_IN_PORTALS.containsKey(entityplayer)) {
			GOTTickHandlerClient.PLAYERS_IN_PORTALS.put(entityplayer, 0);
		}
		if (Minecraft.getMinecraft().isSingleplayer() && !GOTTickHandlerServer.PLAYERS_IN_PORTALS.containsKey(entityplayer)) {
			GOTTickHandlerServer.PLAYERS_IN_PORTALS.put(entityplayer, 0);
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
		GOTClientFactory.getMiniquestTracker().setTrackedQuest(quest);
	}

	@Override
	public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
		GOTGuiMap.setShowWP(showWP);
		GOTGuiMap.setShowCWP(showCWP);
		GOTGuiMap.setShowHiddenSWP(showHiddenSWP);
	}

	@Override
	public void showBurnOverlay() {
		GOTClientFactory.getTickHandlerClient().onBurnDamage();
	}

	@Override
	public void showFrostOverlay() {
		GOTClientFactory.getTickHandlerClient().onFrostDamage();
	}

	@Override
	public void spawnReputationBonus(GOTFaction faction, float prevMainReputation, GOTReputationBonusMap factionBonusMap, String name, float conquestBonus, double posX, double posY, double posZ) {
		World world = getClientWorld();
		if (world != null) {
			GOTEntityReputationBonus entity = new GOTEntityReputationBonus(world, posX, posY, posZ, name, faction, prevMainReputation, factionBonusMap, conquestBonus);
			world.spawnEntityInWorld(entity);
		}
	}

	@Override
	public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.renderViewEntity != null && mc.theWorld != null) {
			WorldClient worldClient = mc.theWorld;
			Random rand = worldClient.rand;
			int i = mc.gameSettings.particleSetting;
			if (i == 1 && rand.nextInt(3) == 0) {
				i = 2;
			}
			if (i > 1) {
				return;
			}
			if ("angry".equals(type)) {
				GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityAngryFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("chill".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityChillFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("largeStone".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityLargeBlockFX(worldClient, d, d1, d2, d3, d4, d5, Blocks.stone, 0));
			} else if (type.startsWith("leaf")) {
				String s = type.substring("leaf".length());
				int[] texIndices = null;
				if (s.startsWith("Gold")) {
					if (rand.nextBoolean()) {
						texIndices = GOTFunctions.intRange(0, 5);
					} else {
						texIndices = GOTFunctions.intRange(8, 13);
					}
				} else if (s.startsWith("Red")) {
					if (rand.nextBoolean()) {
						texIndices = GOTFunctions.intRange(16, 21);
					} else {
						texIndices = GOTFunctions.intRange(24, 29);
					}
				} else if (s.startsWith("Mirk")) {
					if (rand.nextBoolean()) {
						texIndices = GOTFunctions.intRange(32, 37);
					} else {
						texIndices = GOTFunctions.intRange(40, 45);
					}
				} else if (s.startsWith("Green")) {
					if (rand.nextBoolean()) {
						texIndices = GOTFunctions.intRange(48, 53);
					} else {
						texIndices = GOTFunctions.intRange(56, 61);
					}
				}
				if (texIndices != null) {
					if (type.indexOf('_') > -1) {
						int age = Integer.parseInt(type.substring(type.indexOf('_') + 1));
						GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityLeafFX(worldClient, d, d1, d2, d3, d4, d5, texIndices, age));
					} else {
						GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityLeafFX(worldClient, d, d1, d2, d3, d4, d5, texIndices));
					}
				}
			} else if ("marshFlame".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityMarshFlameFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("marshLight".equals(type)) {
				GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityMarshLightFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("ulthosWater".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityRiverWaterFX(worldClient, d, d1, d2, d3, d4, d5, GOTBiome.ulthosForest.getWaterColorMultiplier()));
			} else if ("asshaiTorch".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityAsshaiTorchFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("asshaiWater".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityRiverWaterFX(worldClient, d, d1, d2, d3, d4, d5, GOTBiome.shadowLand.getWaterColorMultiplier()));
			} else if ("pickpocket".equals(type)) {
				GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityPickpocketFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("pickpocketFail".equals(type)) {
				GOTClientFactory.getEffectRenderer().addEffect(new GOTEntityPickpocketFailFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("wave".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityWaveFX(worldClient, d, d1, d2, d3, d4, d5));
			} else if ("whiteSmoke".equals(type)) {
				mc.effectRenderer.addEffect(new GOTEntityWhiteSmokeFX(worldClient, d, d1, d2, d3, d4, d5));
			}
		}
	}

	@Override
	public void testReflection(World world) {
		super.testReflection(world);
		GOTReflectionClient.testAll(Minecraft.getMinecraft());
	}

	@Override
	public void validateBannerUsername(GOTEntityBanner banner, int slot, String prevText, boolean valid) {
		Minecraft mc = Minecraft.getMinecraft();
		GuiScreen gui = mc.currentScreen;
		if (gui instanceof GOTGuiBanner) {
			GOTGuiBanner guiBanner = (GOTGuiBanner) gui;
			if (guiBanner.getTheBanner() == banner) {
				guiBanner.validateUsername(slot, prevText, valid);
			}
		}
	}
}