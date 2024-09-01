package got.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.database.GOTTitle;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodData;
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
	private static final Map<UUID, GOTPlayerData> PLAYER_DATA_MAP = new HashMap<>();
	private static final Map<UUID, Optional<GOTTitle.PlayerTitle>> PLAYER_TITLE_OFFLINE_CACHE_MAP = new HashMap<>();

	private static int madePortal;
	private static int madeGameOfThronesPortal;
	private static int overworldPortalX;
	private static int overworldPortalY;
	private static int overworldPortalZ;
	private static int gameOfThronesPortalX;
	private static int gameOfThronesPortalY;
	private static int gameOfThronesPortalZ;
	private static boolean clientSideThisServerFeastMode;
	private static boolean clientSideThisServerBrotherhoodCreation;
	private static int clientSideThisServerBrotherhoodMaxSize;
	private static boolean clientSideThisServerEnchanting;
	private static boolean clientSideThisServerEnchantingGOT;
	private static boolean clientSideThisServerStrictFactionTitleRequirements;
	private static int clientSideThisServerCustomWaypointMinY;

	private static boolean needsLoad = true;

	private static int structuresBanned;
	private static int waypointCooldownMax;
	private static int waypointCooldownMin;
	private static boolean enableReputationZones;
	private static float conquestRate = 1.0f;
	private static EnumDifficulty difficulty;
	private static boolean difficultyLock;
	private static boolean needsSave;

	private GOTLevelData() {
	}

	public static boolean anyDataNeedsSave() {
		if (needsSave || GOTSpawnDamping.isNeedsSave()) {
			return true;
		}
		for (GOTPlayerData pd : PLAYER_DATA_MAP.values()) {
			if (pd.needsSave()) {
				return true;
			}
		}
		return false;
	}

	public static void destroyAllPlayerData() {
		PLAYER_DATA_MAP.clear();
	}

	public static Set<String> getBannedStructurePlayersUsernames() {
		Set<String> players = new HashSet<>();
		for (UUID uuid : PLAYER_DATA_MAP.keySet()) {
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
		GOTPlayerData pd = PLAYER_DATA_MAP.get(player);
		if (pd == null) {
			pd = loadData(player);
			PLAYER_TITLE_OFFLINE_CACHE_MAP.remove(player);
			if (pd == null) {
				pd = new GOTPlayerData(player);
			}
			PLAYER_DATA_MAP.put(player, pd);
		}
		return pd;
	}

	private static File getGOTDat() {
		return new File(getOrCreateGOTDir(), "GOT.dat");
	}

	private static File getGOTPlayerDat(UUID player) {
		File playerDir = new File(getOrCreateGOTDir(), "players");
		if (!playerDir.exists()) {
			boolean created = playerDir.mkdirs();
			if (!created) {
				GOTLog.getLogger().info("GOTLevelData: directory wasn't created");
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
				GOTLog.getLogger().info("GOTLevelData: directory wasn't created");
			}
		}
		return file;
	}

	public static GOTTitle.PlayerTitle getPlayerTitleWithOfflineCache(UUID player) {
		if (PLAYER_DATA_MAP.containsKey(player)) {
			return PLAYER_DATA_MAP.get(player).getPlayerTitle();
		}
		if (PLAYER_TITLE_OFFLINE_CACHE_MAP.containsKey(player)) {
			return PLAYER_TITLE_OFFLINE_CACHE_MAP.get(player).orElse(null);
		}
		GOTPlayerData pd = loadData(player);
		if (pd != null) {
			GOTTitle.PlayerTitle playerTitle = pd.getPlayerTitle();
			PLAYER_TITLE_OFFLINE_CACHE_MAP.put(player, Optional.ofNullable(playerTitle));
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
					GOTLog.getLogger().info("GOTLevelData: file wasn't deleted");
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
			enableReputationZones = !levelData.hasKey("ReputationZones") || levelData.getBoolean("ReputationZones");
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

	private static GOTPlayerData loadData(UUID player) {
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
		IMessage packet = new GOTPacketPortalPos(i, j, k);
		GOTPacketHandler.NETWORK_WRAPPER.sendToAll(packet);
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
				levelData.setBoolean("ReputationZones", enableReputationZones);
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
			for (Map.Entry<UUID, GOTPlayerData> e : PLAYER_DATA_MAP.entrySet()) {
				UUID player = e.getKey();
				GOTPlayerData pd = e.getValue();
				if (pd.needsSave()) {
					saveData(player);
				}
			}
			if (GOTSpawnDamping.isNeedsSave()) {
				GOTSpawnDamping.saveAll();
			}
		} catch (Exception e) {
			FMLLog.severe("Error saving GOT data");
			e.printStackTrace();
		}
	}

	private static void saveAndClearData(UUID player) {
		GOTPlayerData pd = PLAYER_DATA_MAP.get(player);
		if (pd != null) {
			if (pd.needsSave()) {
				saveData(player);
			}
			PLAYER_TITLE_OFFLINE_CACHE_MAP.put(player, Optional.ofNullable(pd.getPlayerTitle()));
			PLAYER_DATA_MAP.remove(player);
			return;
		}
		FMLLog.severe("Attempted to clear GOT player data for %s; no data found", player);
	}

	public static void saveAndClearUnusedPlayerData() {
		Collection<UUID> clearing = new ArrayList<>();
		for (UUID player : PLAYER_DATA_MAP.keySet()) {
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

	private static void saveData(UUID player) {
		try {
			NBTTagCompound nbt = new NBTTagCompound();
			GOTPlayerData pd = PLAYER_DATA_MAP.get(player);
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

	public static void sendReputationToAllPlayersInWorld(Entity entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketReputation(entityplayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void sendAllReputationsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketReputation(worldPlayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendAllCapesInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketCape(worldPlayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendAllShieldsInWorldToPlayer(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketShield(worldPlayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendCapeToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketCape(entityplayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) worldPlayer);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public static void sendLoginPacket(EntityPlayerMP entityplayer) {
		GOTPacketLogin packet = new GOTPacketLogin();
		packet.setSwordPortalX(gameOfThronesPortalX);
		packet.setSwordPortalY(gameOfThronesPortalY);
		packet.setSwordPortalZ(gameOfThronesPortalZ);
		packet.setFtCooldownMax(waypointCooldownMax);
		packet.setFtCooldownMin(waypointCooldownMin);
		packet.setDifficulty(difficulty);
		packet.setDifficultyLocked(difficultyLock);
		packet.setReputationZones(enableReputationZones);
		packet.setFeastMode(GOTConfig.canAlwaysEat);
		packet.setBrotherhoodCreation(GOTConfig.enableBrotherhoodCreation);
		packet.setBrotherhoodMaxSize(GOTConfig.brotherhoodMaxSize);
		packet.setEnchanting(GOTConfig.enchantingVanilla);
		packet.setEnchantingGOT(GOTConfig.enchantingGOT);
		packet.setStrictFactionTitleRequirements(GOTConfig.strictFactionTitleRequirements);
		packet.setCustomWaypointMinY(GOTConfig.customWaypointMinY);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
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
		Collection<GOTBrotherhood> brotherhoodsMapShow = new ArrayList<>();
		for (UUID fsID : playerData.getBrotherhoodIDs()) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null && fs.getShowMapLocations()) {
				brotherhoodsMapShow.add(fs);
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
						for (GOTBrotherhood fs : brotherhoodsMapShow) {
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
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packetLocations, (EntityPlayerMP) sendPlayer);
	}

	public static void sendShieldToAllPlayersInWorld(Entity entityplayer, World world) {
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer worldPlayer : players) {
			IMessage packet = new GOTPacketShield(entityplayer.getUniqueID());
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) worldPlayer);
		}
	}

	public static void setPlayerBannedForStructures(String username, boolean flag) {
		UUID uuid = UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
		getData(uuid).setStructuresBanned(flag);
	}

	public static void setSavedDifficulty(EnumDifficulty d) {
		difficulty = d;
		markDirty();
	}

	public static void setWaypointCooldown(int max, int min) {
		int min1 = min;
		int max1 = Math.max(0, max);
		min1 = Math.max(0, min1);
		if (min1 > max1) {
			min1 = max1;
		}
		waypointCooldownMax = max1;
		waypointCooldownMin = min1;
		markDirty();
		if (!GOT.proxy.isClient()) {
			List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP entityplayer : players) {
				IMessage packet = new GOTPacketFTCooldown(waypointCooldownMax, waypointCooldownMin);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
			}
		}
	}

	public static int getMadePortal() {
		return madePortal;
	}

	public static void setMadePortal(int i) {
		madePortal = i;
		markDirty();
	}

	public static int getMadeGameOfThronesPortal() {
		return madeGameOfThronesPortal;
	}

	public static void setMadeGameOfThronesPortal(int i) {
		madeGameOfThronesPortal = i;
		markDirty();
	}

	public static int getStructuresBanned() {
		return structuresBanned;
	}

	public static void setStructuresBanned(boolean banned) {
		if (banned) {
			structuresBanned = 1;
		} else {
			structuresBanned = 0;
		}
		markDirty();
	}

	public static boolean isEnableReputationZones() {
		return enableReputationZones;
	}

	public static void setEnableReputationZones(boolean flag) {
		enableReputationZones = flag;
		markDirty();
		if (!GOT.proxy.isClient()) {
			List<EntityPlayerMP> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP entityplayer : players) {
				IMessage packet = new GOTPacketEnableReputationZones(enableReputationZones);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
			}
		}
	}

	public static int getOverworldPortalX() {
		return overworldPortalX;
	}

	public static int getOverworldPortalY() {
		return overworldPortalY;
	}

	public static int getOverworldPortalZ() {
		return overworldPortalZ;
	}

	public static int getGameOfThronesPortalX() {
		return gameOfThronesPortalX;
	}

	public static void setGameOfThronesPortalX(int gameOfThronesPortalX) {
		GOTLevelData.gameOfThronesPortalX = gameOfThronesPortalX;
	}

	public static int getGameOfThronesPortalY() {
		return gameOfThronesPortalY;
	}

	public static void setGameOfThronesPortalY(int gameOfThronesPortalY) {
		GOTLevelData.gameOfThronesPortalY = gameOfThronesPortalY;
	}

	public static int getGameOfThronesPortalZ() {
		return gameOfThronesPortalZ;
	}

	public static void setGameOfThronesPortalZ(int gameOfThronesPortalZ) {
		GOTLevelData.gameOfThronesPortalZ = gameOfThronesPortalZ;
	}

	public static boolean isClientSideThisServerFeastMode() {
		return clientSideThisServerFeastMode;
	}

	public static void setClientSideThisServerFeastMode(boolean clientSideThisServerFeastMode) {
		GOTLevelData.clientSideThisServerFeastMode = clientSideThisServerFeastMode;
	}

	public static boolean isClientSideThisServerBrotherhoodCreation() {
		return clientSideThisServerBrotherhoodCreation;
	}

	public static void setClientSideThisServerBrotherhoodCreation(boolean clientSideThisServerBrotherhoodCreation) {
		GOTLevelData.clientSideThisServerBrotherhoodCreation = clientSideThisServerBrotherhoodCreation;
	}

	public static int getClientSideThisServerBrotherhoodMaxSize() {
		return clientSideThisServerBrotherhoodMaxSize;
	}

	public static void setClientSideThisServerBrotherhoodMaxSize(int clientSideThisServerBrotherhoodMaxSize) {
		GOTLevelData.clientSideThisServerBrotherhoodMaxSize = clientSideThisServerBrotherhoodMaxSize;
	}

	public static boolean isClientSideThisServerEnchanting() {
		return clientSideThisServerEnchanting;
	}

	public static void setClientSideThisServerEnchanting(boolean clientSideThisServerEnchanting) {
		GOTLevelData.clientSideThisServerEnchanting = clientSideThisServerEnchanting;
	}

	public static boolean isClientSideThisServerEnchantingGOT() {
		return clientSideThisServerEnchantingGOT;
	}

	public static void setClientSideThisServerEnchantingGOT(boolean clientSideThisServerEnchantingGOT) {
		GOTLevelData.clientSideThisServerEnchantingGOT = clientSideThisServerEnchantingGOT;
	}

	public static boolean isClientSideThisServerStrictFactionTitleRequirements() {
		return clientSideThisServerStrictFactionTitleRequirements;
	}

	public static void setClientSideThisServerStrictFactionTitleRequirements(boolean clientSideThisServerStrictFactionTitleRequirements) {
		GOTLevelData.clientSideThisServerStrictFactionTitleRequirements = clientSideThisServerStrictFactionTitleRequirements;
	}

	public static int getClientSideThisServerCustomWaypointMinY() {
		return clientSideThisServerCustomWaypointMinY;
	}

	public static void setClientSideThisServerCustomWaypointMinY(int clientSideThisServerCustomWaypointMinY) {
		GOTLevelData.clientSideThisServerCustomWaypointMinY = clientSideThisServerCustomWaypointMinY;
	}

	public static boolean isNeedsLoad() {
		return needsLoad;
	}

	public static void setNeedsLoad(boolean needsLoad) {
		GOTLevelData.needsLoad = needsLoad;
	}
}