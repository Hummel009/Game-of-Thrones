package got.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import got.GOT;
import got.common.database.GOTTitle;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipData;
import got.common.network.*;
import got.common.util.GOTLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class GOTLevelData {
	public static int madePortal;
	public static int madeGameOfThronesPortal;
	public static int overworldPortalX;
	public static int overworldPortalY;
	public static int overworldPortalZ;
	public static int gameOfThronesPortalX;
	public static int gameOfThronesPortalY;
	public static int gameOfThronesPortalZ;
	public static int structuresBanned;
	public static int waypointCooldownMax;
	public static int waypointCooldownMin;
	public static boolean enableAlignmentZones;
	public static float conquestRate = 1.0f;
	public static boolean clientside_thisServer_feastMode;
	public static boolean clientside_thisServer_fellowshipCreation;
	public static int clientside_thisServer_fellowshipMaxSize;
	public static boolean clientside_thisServer_enchanting;
	public static boolean clientside_thisServer_enchantingGOT;
	public static boolean clientside_thisServer_strictFactionTitleRequirements;
	public static int clientside_thisServer_customWaypointMinY;
	public static EnumDifficulty difficulty;
	public static boolean difficultyLock;
	public static Map<UUID, GOTPlayerData> playerDataMap = new HashMap<>();
	public static Map<UUID, Optional<GOTTitle.PlayerTitle>> playerTitleOfflineCacheMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean needsSave;
	public static Random rand = new Random();

	public static boolean anyDataNeedsSave() {
		if (needsSave || GOTSpawnDamping.needsSave) {
			return true;
		}
		for (GOTPlayerData pd : playerDataMap.values()) {
			if (pd.needsSave()) {
				return true;
			}
		}
		return false;
	}

	public static void destroyAllPlayerData() {
		playerDataMap.clear();
	}

	public static boolean enableAlignmentZones() {
		return enableAlignmentZones;
	}

	public static Set<String> getBannedStructurePlayersUsernames() {
		HashSet<String> players = new HashSet<>();
		for (UUID uuid : playerDataMap.keySet()) {
			String username;
			if (getData(uuid).getStructuresBanned()) {
				GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
				if (StringUtils.isBlank(profile.getName())) {
					MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
				}
				if (!StringUtils.isBlank(username = profile.getName())) {
					players.add(username);
				}
			}
		}
		return players;
	}

	public static float getConquestRate() {
		return conquestRate;
	}

	public static void setConquestRate(float f) {
		conquestRate = f;
		markDirty();
	}

	public static GOTPlayerData getData(EntityPlayer entityplayer) {
		return getData(entityplayer.getUniqueID());
	}

	public static GOTPlayerData getData(UUID player) {
		GOTPlayerData pd = playerDataMap.get(player);
		if (pd == null) {
			pd = loadData(player);
			playerTitleOfflineCacheMap.remove(player);
			if (pd == null) {
				pd = new GOTPlayerData(player);
			}
			playerDataMap.put(player, pd);
		}
		return pd;
	}

	public static File getGOTDat() {
		return new File(getOrCreateGOTDir(), "GOT.dat");
	}

	public static File getGOTPlayerDat(UUID player) {
		File playerDir = new File(getOrCreateGOTDir(), "players");
		if (!playerDir.exists()) {
			boolean created = playerDir.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTLevelData: directory wasn't created");
			}
		}
		return new File(playerDir, player.toString() + ".dat");
	}

	public static String getHMSTime_Seconds(int secs) {
		return getHMSTime_Ticks(secs * 20);
	}

	public static String getHMSTime_Ticks(int ticks) {
		int hours = ticks / 72000;
		int minutes = ticks % 72000 / 1200;
		int seconds = ticks % 72000 % 1200 / 20;
		String sHours = StatCollector.translateToLocalFormatted("got.gui.time.hours", hours);
		String sMinutes = StatCollector.translateToLocalFormatted("got.gui.time.minutes", minutes);
		String sSeconds = StatCollector.translateToLocalFormatted("got.gui.time.seconds", seconds);
		if (hours > 0) {
			return StatCollector.translateToLocalFormatted("got.gui.time.format.hms", sHours, sMinutes, sSeconds);
		}
		if (minutes > 0) {
			return StatCollector.translateToLocalFormatted("got.gui.time.format.ms", sMinutes, sSeconds);
		}
		return StatCollector.translateToLocalFormatted("got.gui.time.format.s", sSeconds);
	}

	public static File getOrCreateGOTDir() {
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "GOT");
		if (!file.exists()) {
			boolean created = file.mkdirs();
			if (!created) {
				GOTLog.logger.info("GOTLevelData: directory wasn't created");
			}
		}
		return file;
	}

	public static GOTTitle.PlayerTitle getPlayerTitleWithOfflineCache(UUID player) {
		if (playerDataMap.containsKey(player)) {
			return playerDataMap.get(player).getPlayerTitle();
		}
		if (playerTitleOfflineCacheMap.containsKey(player)) {
			return playerTitleOfflineCacheMap.get(player).orElse(null);
		}
		GOTPlayerData pd = loadData(player);
		if (pd != null) {
			GOTTitle.PlayerTitle playerTitle = pd.getPlayerTitle();
			playerTitleOfflineCacheMap.put(player, Optional.ofNullable(playerTitle));
			return playerTitle;
		}
		return null;
	}

	public static int getWaypointCooldownMax() {
		return waypointCooldownMax;
	}

	public static int getWaypointCooldownMin() {
		return waypointCooldownMin;
	}

	public static boolean isDifficultyLocked() {
		return difficultyLock;
	}

	public static void setDifficultyLocked(boolean flag) {
		difficultyLock = flag;
		markDirty();
	}

	public static boolean isPlayerBannedForStructures(EntityPlayer entityplayer) {
		return getData(entityplayer).getStructuresBanned();
	}

	public static void load() {
		try {
			NBTTagCompound levelData = loadNBTFromFile(getGOTDat());
			File oldGOTDat = new File(DimensionManager.getCurrentSaveRootDirectory(), "GOT.dat");
			if (oldGOTDat.exists()) {
				levelData = loadNBTFromFile(oldGOTDat);
				boolean created = oldGOTDat.delete();
				if (!created) {
					GOTLog.logger.info("GOTLevelData: file wasn't deleted");
				}
				if (levelData.hasKey("PlayerData")) {
					NBTTagList playerDataTags = levelData.getTagList("PlayerData", 10);
					for (int i = 0; i < playerDataTags.tagCount(); ++i) {
						NBTTagCompound nbt = playerDataTags.getCompoundTagAt(i);
						UUID player = UUID.fromString(nbt.getString("PlayerUUID"));
						saveNBTToFile(getGOTPlayerDat(player), nbt);
					}
				}
			}
			madePortal = levelData.getInteger("MadePortal");
			madeGameOfThronesPortal = levelData.getInteger("MadeGameOfThronesPortal");
			overworldPortalX = levelData.getInteger("OverworldX");
			overworldPortalY = levelData.getInteger("OverworldY");
			overworldPortalZ = levelData.getInteger("OverworldZ");
			gameOfThronesPortalX = levelData.getInteger("GameOfThronesX");
			gameOfThronesPortalY = levelData.getInteger("GameOfThronesY");
			gameOfThronesPortalZ = levelData.getInteger("GameOfThronesZ");
			structuresBanned = levelData.getInteger("StructuresBanned");
			if (levelData.hasKey("WpCdMax")) {
				waypointCooldownMax = levelData.getInteger("WpCdMax");
			} else {
				waypointCooldownMax = 600;
			}
			if (levelData.hasKey("WpCdMin")) {
				waypointCooldownMin = levelData.getInteger("WpCdMin");
			} else {
				waypointCooldownMin = 60;
			}
			enableAlignmentZones = !levelData.hasKey("AlignmentZones") || levelData.getBoolean("AlignmentZones");
			if (levelData.hasKey("ConqRate")) {
				conquestRate = levelData.getFloat("ConqRate");
			} else {
				conquestRate = 1.0f;
			}
			if (levelData.hasKey("SavedDifficulty")) {
				int id = levelData.getInteger("SavedDifficulty");
				difficulty = EnumDifficulty.getDifficultyEnum(id);
				GOT.proxy.setClientDifficulty(difficulty);
			} else {
				difficulty = null;
			}
			difficultyLock = levelData.getBoolean("DifficultyLock");
			GOTJaqenHgharTracker.load(levelData);
			destroyAllPlayerData();
			GOTDate.loadDates(levelData);
			GOTSpawnDamping.loadAll();
			needsLoad = false;
			needsSave = true;
			save();
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT data");
			e.printStackTrace();
		}
	}

	public static GOTPlayerData loadData(UUID player) {
		try {
			NBTTagCompound nbt = loadNBTFromFile(getGOTPlayerDat(player));
			GOTPlayerData pd = new GOTPlayerData(player);
			pd.load(nbt);
			return pd;
		} catch (Exception e) {
			FMLLog.severe("Error loading GOT player data for %s", player);
			e.printStackTrace();
			return null;
		}
	}

	public static NBTTagCompound loadNBTFromFile(File file) throws IOException {
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			NBTTagCompound nbt = CompressedStreamTools.readCompressed(fis);
			fis.close();
			return nbt;
		}
		return new NBTTagCompound();
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static void markGameOfThronesPortalLocation(int i, int j, int k) {
		GOTPacketPortalPos packet = new GOTPacketPortalPos(i, j, k);
		GOTPacketHandler.networkWrapper.sendToAll(packet);
		markDirty();
	}

	public static void markOverworldPortalLocation(int i, int j, int k) {
		overworldPortalX = i;
		overworldPortalY = j;
		overworldPortalZ = k;
		markDirty();
	}

	public static void save() {
		try {
			if (needsSave) {
				File GOT_dat = getGOTDat();
				if (!GOT_dat.exists()) {
					saveNBTToFile(GOT_dat, new NBTTagCompound());
				}
				NBTTagCompound levelData = new NBTTagCompound();
				levelData.setInteger("MadePortal", madePortal);
				levelData.setInteger("MadeGameOfThronesPortal", madeGameOfThronesPortal);
				levelData.setInteger("OverworldX", overworldPortalX);
				levelData.setInteger("OverworldY", overworldPortalY);
				levelData.setInteger("OverworldZ", overworldPortalZ);
				levelData.setInteger("GameOfThronesX", gameOfThronesPortalX);
				levelData.setInteger("GameOfThronesY", gameOfThronesPortalY);
				levelData.setInteger("GameOfThronesZ", gameOfThronesPortalZ);
				levelData.setInteger("StructuresBanned", structuresBanned);
				levelData.setInteger("WpCdMax", waypointCooldownMax);
				levelData.setInteger("WpCdMin", waypointCooldownMin);
				levelData.setBoolean("AlignmentZones", enableAlignmentZones);
				levelData.setFloat("ConqRate", conquestRate);
				if (difficulty != null) {
					levelData.setInteger("SavedDifficulty", difficulty.getDifficultyId());
				}
				levelData.setBoolean("DifficultyLock", difficultyLock);
				GOTJaqenHgharTracker.save(levelData);
				GOTDate.saveDates(levelData);
				saveNBTToFile(GOT_dat, levelData);
				needsSave = false;
			}
			for (Map.Entry<UUID, GOTPlayerData> e : playerDataMap.entrySet()) {
				UUID player = e.getKey();
				GOTPlayerData pd = e.getValue();
				if (pd.needsSave()) {
					saveData(player);
				}
			}
			if (GOTSpawnDamping.needsSave) {
				GOTSpawnDamping.saveAll();
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT data");
			e.printStackTrace();
		}
	}

	public static void saveAndClearData(UUID player) {
		GOTPlayerData pd = playerDataMap.get(player);
		if (pd != null) {
			if (pd.needsSave()) {
				saveData(player);
			}
			playerTitleOfflineCacheMap.put(player, Optional.ofNullable(pd.getPlayerTitle()));
			playerDataMap.remove(player);
			return;
		}
		FMLLog.severe("Attempted to clear GOT player data for %s; no data found", player);
	}

	public static void saveAndClearUnusedPlayerData() {
		ArrayList<UUID> clearing = new ArrayList<>();
		for (UUID player : playerDataMap.keySet()) {
			boolean foundPlayer = false;
			for (WorldServer world : MinecraftServer.getServer().worldServers) {
				if (world.func_152378_a(player) != null) {
					foundPlayer = true;
					break;
				}
			}
			if (!foundPlayer) {
				clearing.add(player);
			}
		}
		for (UUID player : clearing) {
			saveAndClearData(player);
		}
	}

	public static void saveData(UUID player) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			GOTPlayerData pd = playerDataMap.get(player);
			pd.save(nbt);
			saveNBTToFile(getGOTPlayerDat(player), nbt);
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT player data for %s", player);
			e.printStackTrace();
		}
	}

	public static void saveNBTToFile(File file, NBTTagCompound nbt) throws IOException {
		CompressedStreamTools.writeCompressed(nbt, Files.newOutputStream(file.toPath()));
	}

	public static void sendAlignmentToAllPlayersInWorld(Entity entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketAlignment packet = new GOTPacketAlignment(entityplayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void sendAllAlignmentsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketAlignment packet = new GOTPacketAlignment(worldPlayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendAllCapesInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketCape packet = new GOTPacketCape(worldPlayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendAllShieldsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketShield packet = new GOTPacketShield(worldPlayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendCapeToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketCape packet = new GOTPacketCape(entityplayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) worldPlayer);
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendLoginPacket(EntityPlayerMP entityplayer) {
		GOTPacketLogin packet = new GOTPacketLogin();
		packet.swordPortalX = gameOfThronesPortalX;
		packet.swordPortalY = gameOfThronesPortalY;
		packet.swordPortalZ = gameOfThronesPortalZ;
		packet.ftCooldownMax = waypointCooldownMax;
		packet.ftCooldownMin = waypointCooldownMin;
		packet.difficulty = difficulty;
		packet.difficultyLocked = difficultyLock;
		packet.alignmentZones = enableAlignmentZones;
		packet.feastMode = GOTConfig.canAlwaysEat;
		packet.fellowshipCreation = GOTConfig.enableFellowshipCreation;
		packet.fellowshipMaxSize = GOTConfig.fellowshipMaxSize;
		packet.enchanting = GOTConfig.enchantingVanilla;
		packet.enchantingGOT = GOTConfig.enchantingGOT;
		packet.strictFactionTitleRequirements = GOTConfig.strictFactionTitleRequirements;
		packet.customWaypointMinY = GOTConfig.customWaypointMinY;
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void sendPlayerData(EntityPlayerMP entityplayer) {
		try {
			GOTPlayerData pd = getData(entityplayer);
			pd.sendPlayerData(entityplayer);
		} catch (Exception e) {
			FMLLog.severe("Failed to send player data to player " + entityplayer.getCommandSenderName());
			e.printStackTrace();
		}
	}

	public static void sendPlayerLocationsToPlayer(EntityPlayer sendPlayer, World world) {
		GOTPacketUpdatePlayerLocations packetLocations = new GOTPacketUpdatePlayerLocations();
		boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(sendPlayer.getGameProfile());
		boolean creative = sendPlayer.capabilities.isCreativeMode;
		GOTPlayerData playerData = getData(sendPlayer);
		ArrayList<GOTFellowship> fellowshipsMapShow = new ArrayList<>();
		for (UUID fsID : playerData.getFellowshipIDs()) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null && fs.getShowMapLocations()) {
				fellowshipsMapShow.add(fs);
			}
		}
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer otherPlayer : players) {
			boolean show;
			if (otherPlayer != sendPlayer) {
				show = !getData(otherPlayer).getHideMapLocation();
				if (!isOp && getData(otherPlayer).getAdminHideMap() || GOTConfig.forceMapLocations == 1) {
					show = false;
				} else if (GOTConfig.forceMapLocations == 2) {
					show = true;
				} else if (!show) {
					if (isOp && creative) {
						show = true;
					} else if (!playerData.isSiegeActive()) {
						for (GOTFellowship fs : fellowshipsMapShow) {
							if (fs.containsPlayer(otherPlayer.getUniqueID())) {
								show = true;
								break;
							}
						}
					}
				}
				if (show) {
					packetLocations.addPlayerLocation(otherPlayer.getGameProfile(), otherPlayer.posX, otherPlayer.posZ);
				}
			}
		}
		GOTPacketHandler.networkWrapper.sendTo(packetLocations, (EntityPlayerMP) sendPlayer);
	}

	public static void sendShieldToAllPlayersInWorld(Entity entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			GOTPacketShield packet = new GOTPacketShield(entityplayer.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void setEnableAlignmentZones(boolean flag) {
		enableAlignmentZones = flag;
		markDirty();
		if (!GOT.proxy.isClient()) {
			List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP entityplayer : players) {
				GOTPacketEnableAlignmentZones packet = new GOTPacketEnableAlignmentZones(enableAlignmentZones);
				GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static void setMadeGameOfThronesPortal(int i) {
		madeGameOfThronesPortal = i;
		markDirty();
	}

	public static void setMadePortal(int i) {
		madePortal = i;
		markDirty();
	}

	public static void setPlayerBannedForStructures(String username, boolean flag) {
		UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
	}

	public static void setSavedDifficulty(EnumDifficulty d) {
		difficulty = d;
		markDirty();
	}

	public static void setStructuresBanned(boolean banned) {
		if (banned) {
			structuresBanned = 1;
		} else {
			structuresBanned = 0;
		}
		markDirty();
	}

	public static void setWaypointCooldown(int max, int min) {
		max = Math.max(0, max);
		min = Math.max(0, min);
		if (min > max) {
			min = max;
		}
		waypointCooldownMax = max;
		waypointCooldownMin = min;
		markDirty();
		if (!GOT.proxy.isClient()) {
			List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP entityplayer : players) {
				GOTPacketFTCooldown packet = new GOTPacketFTCooldown(waypointCooldownMax, waypointCooldownMin);
				GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static boolean structuresBanned() {
		return structuresBanned == 1;
	}
}