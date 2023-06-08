package got.common.world.map;

import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipClient;
import got.common.fellowship.GOTFellowshipData;
import got.common.network.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class GOTCustomWaypoint implements GOTAbstractWaypoint {
	public String customName;
	public int mapX;
	public int mapY;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int ID;
	public List<UUID> sharedFellowshipIDs = new ArrayList<>();
	public UUID sharingPlayer;
	public String sharingPlayerName;
	public boolean sharedUnlocked;
	public boolean sharedHidden;

	public GOTCustomWaypoint(String name, int i, int j, int posX, int posY, int posZ, int id) {
		customName = name;
		mapX = i;
		mapY = j;
		xCoord = posX;
		yCoord = posY;
		zCoord = posZ;
		ID = id;
	}

	public static void createForPlayer(String name, EntityPlayer entityplayer) {
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		int cwpID = playerData.getNextCwpID();
		int i = MathHelper.floor_double(entityplayer.posX);
		int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		int mapX = GOTWaypoint.worldToMapX(i);
		int mapY = GOTWaypoint.worldToMapZ(k);
		GOTCustomWaypoint cwp = new GOTCustomWaypoint(name, mapX, mapY, i, j, k, cwpID);
		playerData.addCustomWaypoint(cwp);
		playerData.incrementNextCwpID();
	}

	public static GOTCustomWaypoint readFromNBT(NBTTagCompound nbt, GOTPlayerData pd) {
		String name = nbt.getString("Name");
		int x = nbt.getInteger("X");
		int y = nbt.getInteger("Y");
		int xCoord = nbt.getInteger("XCoord");
		int zCoord = nbt.getInteger("ZCoord");
		int yCoord = nbt.hasKey("YCoord") ? nbt.getInteger("YCoord") : -1;
		int ID = nbt.getInteger("ID");
		GOTCustomWaypoint cwp = new GOTCustomWaypoint(name, x, y, xCoord, yCoord, zCoord, ID);
		cwp.sharedFellowshipIDs.clear();
		if (nbt.hasKey("SharedFellowships")) {
			NBTTagList sharedFellowshipTags = nbt.getTagList("SharedFellowships", 8);
			for (int i = 0; i < sharedFellowshipTags.tagCount(); ++i) {
				UUID fsID = UUID.fromString(sharedFellowshipTags.getStringTagAt(i));
				cwp.sharedFellowshipIDs.add(fsID);
			}
		}
		cwp.validateFellowshipIDs(pd);
		return cwp;
	}

	public static String validateCustomName(String name) {
		if (!StringUtils.isBlank(name = StringUtils.trim(name))) {
			return name;
		}
		return null;
	}

	public void addSharedFellowship(UUID fsID) {
		if (!sharedFellowshipIDs.contains(fsID)) {
			sharedFellowshipIDs.add(fsID);
		}
	}

	public boolean canUnlockShared(EntityPlayer entityplayer) {
		if (yCoord >= 0) {
			double distSq = entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
			return distSq <= 1000000.0;
		}
		return false;
	}

	public GOTCustomWaypoint createCopyOfShared(UUID sharer) {
		GOTCustomWaypoint copy = new GOTCustomWaypoint(customName, mapX, mapY, xCoord, yCoord, zCoord, ID);
		copy.setSharingPlayerID(sharer);
		copy.sharedFellowshipIDs = new ArrayList<>(sharedFellowshipIDs);
		return copy;
	}

	public GOTPacketShareCWPClient getClientAddFellowshipPacket(UUID fsID) {
		return new GOTPacketShareCWPClient(ID, fsID, true);
	}

	public GOTPacketDeleteCWPClient getClientDeletePacket() {
		return new GOTPacketDeleteCWPClient(ID);
	}

	public GOTPacketDeleteCWPClient getClientDeletePacketShared() {
		return new GOTPacketDeleteCWPClient(ID).setSharingPlayer(sharingPlayer);
	}

	public GOTPacketCreateCWPClient getClientPacket() {
		return new GOTPacketCreateCWPClient(mapX, mapY, xCoord, yCoord, zCoord, ID, customName, sharedFellowshipIDs);
	}

	public GOTPacketCreateCWPClient getClientPacketShared() {
		return new GOTPacketCreateCWPClient(mapX, mapY, xCoord, yCoord, zCoord, ID, customName, sharedFellowshipIDs).setSharingPlayer(sharingPlayer, sharingPlayerName, sharedUnlocked, sharedHidden);
	}

	public GOTPacketShareCWPClient getClientRemoveFellowshipPacket(UUID fsID) {
		return new GOTPacketShareCWPClient(ID, fsID, false);
	}

	public GOTPacketRenameCWPClient getClientRenamePacket() {
		return new GOTPacketRenameCWPClient(ID, customName);
	}

	public GOTPacketRenameCWPClient getClientRenamePacketShared() {
		return new GOTPacketRenameCWPClient(ID, customName).setSharingPlayer(sharingPlayer);
	}

	public GOTPacketCWPSharedHideClient getClientSharedHidePacket(boolean hide) {
		return new GOTPacketCWPSharedHideClient(ID, sharingPlayer, hide);
	}

	public GOTPacketCWPSharedUnlockClient getClientSharedUnlockPacket() {
		return new GOTPacketCWPSharedUnlockClient(ID, sharingPlayer);
	}

	@Override
	public String getCodeName() {
		return customName;
	}

	@Override
	public String getDisplayName() {
		if (isShared()) {
			return StatCollector.translateToLocalFormatted("got.waypoint.shared", customName);
		}
		return StatCollector.translateToLocalFormatted("got.wp.custom", customName);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public GOTWaypoint getItself() {
		return null;
	}

	@Override
	public GOTAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
		boolean unlocked = hasPlayerUnlocked(entityplayer);
		if (isShared()) {
			return unlocked ? GOTAbstractWaypoint.WaypointLockState.SHARED_CUSTOM_UNLOCKED : GOTAbstractWaypoint.WaypointLockState.SHARED_CUSTOM_LOCKED;
		}
		return unlocked ? GOTAbstractWaypoint.WaypointLockState.CUSTOM_UNLOCKED : GOTAbstractWaypoint.WaypointLockState.CUSTOM_LOCKED;
	}

	@Override
	public String getLoreText(EntityPlayer entityplayer) {
		boolean shared;
		boolean ownShared = !isShared() && !sharedFellowshipIDs.isEmpty();
		shared = isShared() && sharingPlayerName != null;
		if (ownShared || shared) {
			int numShared = sharedFellowshipIDs.size();
			int numShown = 0;
			ArrayList<String> fsNames = new ArrayList<>();
			for (int i = 0; i < 3 && i < sharedFellowshipIDs.size(); ++i) {
				UUID fsID = sharedFellowshipIDs.get(i);
				GOTFellowshipClient fs = GOTLevelData.getData(entityplayer).getClientFellowshipByID(fsID);
				if (fs == null) {
					continue;
				}
				fsNames.add(fs.getName());
				++numShown;
			}
			StringBuilder sharedFsNames = new StringBuilder();
			for (String s : fsNames) {
				sharedFsNames.append("\n").append(s);
			}
			if (numShared > numShown) {
				int numMore = numShared - numShown;
				sharedFsNames.append("\n").append(StatCollector.translateToLocalFormatted("got.wp.custom.andMore", numMore));
			}
			if (ownShared) {
				return StatCollector.translateToLocalFormatted("got.wp.custom.info", sharedFsNames.toString());
			}
			if (shared) {
				return StatCollector.translateToLocalFormatted("got.waypoint.shared.info", sharingPlayerName, sharedFsNames.toString());
			}
		}
		return null;
	}

	public List<UUID> getPlayersInAllSharedFellowships() {
		ArrayList<UUID> allPlayers = new ArrayList<>();
		for (UUID fsID : sharedFellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null) {
				List<UUID> fsPlayers = fs.getAllPlayerUUIDs();
				for (UUID player : fsPlayers) {
					if (player.equals(sharingPlayer) || allPlayers.contains(player)) {
						continue;
					}
					allPlayers.add(player);
				}
			}
		}
		return allPlayers;
	}

	@Override
	public int getRotation() {
		return 0;
	}

	public List<UUID> getSharedFellowshipIDs() {
		return sharedFellowshipIDs;
	}

	public void setSharedFellowshipIDs(List<UUID> fsIDs) {
		sharedFellowshipIDs = fsIDs;
	}

	public UUID getSharingPlayerID() {
		return sharingPlayer;
	}

	public void setSharingPlayerID(UUID id) {
		UUID prev = sharingPlayer;
		sharingPlayer = id;
		if (MinecraftServer.getServer() != null && (prev == null || !prev.equals(sharingPlayer))) {
			sharingPlayerName = GOTPacketFellowship.getPlayerProfileWithUsername(sharingPlayer).getName();
		}
	}

	public String getSharingPlayerName() {
		return sharingPlayerName;
	}

	public void setSharingPlayerName(String s) {
		sharingPlayerName = s;
	}

	@Override
	public double getX() {
		return mapX;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public double getY() {
		return mapY;
	}

	@Override
	public int getYCoord(World world, int i, int k) {
		int j = yCoord;
		if (j < 0) {
			yCoord = GOT.getTrueTopBlock(world, i, k);
		} else if (!isSafeBlock(world, i, j, k)) {
			int j1;
			Block below = world.getBlock(i, j - 1, k);
			Block block = world.getBlock(i, j, k);
			Block above = world.getBlock(i, j + 1, k);
			boolean belowSafe = below.getMaterial().blocksMovement();
			boolean blockSafe = !block.isNormalCube(world, i, j, k);
			boolean aboveSafe = !above.isNormalCube(world, i, j + 1, k);
			boolean foundSafe = false;
			if (!belowSafe) {
				for (j1 = j - 1; j1 >= 1; --j1) {
					if (!isSafeBlock(world, i, j1, k)) {
						continue;
					}
					yCoord = j1;
					foundSafe = true;
					break;
				}
			}
			if (!foundSafe && (!blockSafe || !aboveSafe)) {
				for (j1 = aboveSafe ? j + 1 : j + 2; j1 <= world.getHeight() - 1; ++j1) {
					if (!isSafeBlock(world, i, j1, k)) {
						continue;
					}
					yCoord = j1;
					foundSafe = true;
					break;
				}
			}
			if (!foundSafe) {
				yCoord = GOT.getTrueTopBlock(world, i, k);
			}
		}
		return yCoord;
	}

	@Override
	public int getYCoordSaved() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
		if (isShared()) {
			return sharedUnlocked;
		}
		return true;
	}

	public boolean hasSharedFellowship(GOTFellowship fs) {
		return hasSharedFellowship(fs.getFellowshipID());
	}

	public boolean hasSharedFellowship(UUID fsID) {
		return sharedFellowshipIDs.contains(fsID);
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	public boolean isSafeBlock(World world, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		Block block = world.getBlock(i, j, k);
		Block above = world.getBlock(i, j + 1, k);
		if (below.getMaterial().blocksMovement() && !block.isNormalCube(world, i, j, k) && !above.isNormalCube(world, i, j + 1, k)) {
			if (block.getMaterial().isLiquid() || block.getMaterial() == Material.fire) {
				return false;
			}
			return !above.getMaterial().isLiquid() && above.getMaterial() != Material.fire;
		}
		return false;
	}

	public boolean isShared() {
		return sharingPlayer != null;
	}

	public boolean isSharedHidden() {
		return sharedHidden;
	}

	public void setSharedHidden(boolean flag) {
		sharedHidden = flag;
	}

	public boolean isSharedUnlocked() {
		return sharedUnlocked;
	}

	public void removeSharedFellowship(UUID fsID) {
		sharedFellowshipIDs.remove(fsID);
	}

	public void rename(String newName) {
		customName = newName;
	}

	public void setSharedUnlocked() {
		sharedUnlocked = true;
	}

	public void validateFellowshipIDs(GOTPlayerData ownerData) {
		UUID ownerUUID = ownerData.getPlayerUUID();
		HashSet<UUID> removeIDs = new HashSet<>();
		for (UUID fsID : sharedFellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null && fs.containsPlayer(ownerUUID)) {
				continue;
			}
			removeIDs.add(fsID);
		}
		sharedFellowshipIDs.removeAll(removeIDs);
	}

	public void writeToNBT(NBTTagCompound nbt, GOTPlayerData pd) {
		nbt.setString("Name", customName);
		nbt.setInteger("X", mapX);
		nbt.setInteger("Y", mapY);
		nbt.setInteger("XCoord", xCoord);
		nbt.setInteger("YCoord", yCoord);
		nbt.setInteger("ZCoord", zCoord);
		nbt.setInteger("ID", ID);
		validateFellowshipIDs(pd);
		if (!sharedFellowshipIDs.isEmpty()) {
			NBTTagList sharedFellowshipTags = new NBTTagList();
			for (UUID fsID : sharedFellowshipIDs) {
				NBTTagString tag = new NBTTagString(fsID.toString());
				sharedFellowshipTags.appendTag(tag);
			}
			nbt.setTag("SharedFellowships", sharedFellowshipTags);
		}
	}
}
