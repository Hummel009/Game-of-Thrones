package got.common.entity.other;

import java.util.*;

import com.mojang.authlib.GameProfile;

import got.common.GOTBannerProtection;
import got.common.GOTBannerProtection.Permission;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTItems;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipClient;
import got.common.fellowship.GOTFellowshipProfile;
import got.common.item.other.GOTItemBanner;
import got.common.network.GOTPacketBannerData;
import got.common.network.GOTPacketHandler;
import got.common.util.GOTLog;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GOTEntityBanner extends Entity {
	public static float ALIGNMENT_PROTECTION_MIN = 1.0f;
	public static float ALIGNMENT_PROTECTION_MAX = 10000.0f;
	public static int WHITELIST_DEFAULT = 16;
	public static int WHITELIST_MIN = 1;
	public static int WHITELIST_MAX = 4000;
	public NBTTagCompound protectData;
	public boolean wasEverProtecting;
	public boolean playerSpecificProtection;
	public boolean structureProtection;
	public int customRange;
	public boolean selfProtection = true;
	public float alignmentProtection = ALIGNMENT_PROTECTION_MIN;
	public GOTBannerWhitelistEntry[] allowedPlayers = new GOTBannerWhitelistEntry[WHITELIST_DEFAULT];
	public Set<Permission> defaultPermissions = EnumSet.noneOf(Permission.class);
	public boolean clientside_playerHasPermission;

	public GOTEntityBanner(World world) {
		super(world);
		setSize(1.0f, 3.0f);
	}

	public void addDefaultPermission(GOTBannerProtection.Permission p) {
		if (p == GOTBannerProtection.Permission.FULL) {
			return;
		}
		defaultPermissions.add(p);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean isProtectionBanner = isProtectingTerritory();
		boolean isPlayerDamage = damagesource.getEntity() instanceof EntityPlayer;
		if (isProtectionBanner && !isPlayerDamage) {
			return false;
		}
		if (!isDead && !worldObj.isRemote) {
			if (isPlayerDamage) {
				EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
				if (GOTBannerProtection.isProtected(worldObj, this, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
					if (!isProtectionBanner || selfProtection || structureProtection && damagesource.getEntity() != damagesource.getSourceOfDamage()) {
						return false;
					}
				}
				if (isProtectionBanner && selfProtection && !canPlayerEditBanner(entityplayer)) {
					return false;
				}
			}
			setBeenAttacked();
			worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
			boolean drop = !(damagesource.getEntity() instanceof EntityPlayer) || !((EntityPlayer) damagesource.getEntity()).capabilities.isCreativeMode;
			dropAsItem(drop);
		}
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean canPlayerEditBanner(EntityPlayer entityplayer) {
		GameProfile owner = getPlacingPlayer();
		if (owner != null && owner.getId() != null && entityplayer.getUniqueID().equals(owner.getId())) {
			return true;
		}
		return !structureProtection && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && entityplayer.capabilities.isCreativeMode;
	}

	public boolean clientside_playerHasPermissionInSurvival() {
		return clientside_playerHasPermission;
	}

	public AxisAlignedBB createProtectionCube() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		int range = getProtectionRange();
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
	}

	public void dropAsItem(boolean drop) {
		setDead();
		if (drop) {
			entityDropItem(getBannerItem(), 0.0f);
		}
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(18, (short) 0);
	}

	public float getAlignmentProtection() {
		return alignmentProtection;
	}

	public ItemStack getBannerItem() {
		ItemStack item = new ItemStack(GOTItems.banner, 1, getBannerType().bannerID);
		if (wasEverProtecting && protectData == null) {
			protectData = new NBTTagCompound();
		}
		if (protectData != null) {
			writeProtectionToNBT(protectData);
			if (!structureProtection) {
				GOTItemBanner.setProtectionData(item, protectData);
			}
		}
		return item;
	}

	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.forID(getBannerTypeID());
	}

	public int getBannerTypeID() {
		return dataWatcher.getWatchableObjectShort(18);
	}

	public int getDefaultPermBitFlags() {
		return GOTBannerWhitelistEntry.static_encodePermBitFlags(defaultPermissions);
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getBannerItem();
	}

	public GOTFellowship getPlacersFellowshipByName(String fsName) {
		UUID ownerID;
		GameProfile owner = getPlacingPlayer();
		if (owner != null && (ownerID = owner.getId()) != null) {
			return GOTLevelData.getData(ownerID).getFellowshipByName(fsName);
		}
		return null;
	}

	public GameProfile getPlacingPlayer() {
		return getWhitelistedPlayer(0);
	}

	public int getProtectionRange() {
		if (!structureProtection && !GOTConfig.allowBannerProtection) {
			return 0;
		}
		if (customRange > 0) {
			return customRange;
		}
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		Block block = worldObj.getBlock(i, j - 1, k);
		int meta = worldObj.getBlockMetadata(i, j - 1, k);
		return GOTBannerProtection.getProtectionRange(block, meta);
	}

	public GameProfile getWhitelistedPlayer(int index) {
		if (allowedPlayers[index] == null) {
			return null;
		}
		return allowedPlayers[index].profile;
	}

	public GOTBannerWhitelistEntry getWhitelistEntry(int index) {
		return allowedPlayers[index];
	}

	public int getWhitelistLength() {
		return allowedPlayers.length;
	}

	public boolean hasDefaultPermission(GOTBannerProtection.Permission p) {
		return defaultPermissions.contains(p);
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && isProtectingTerritory() && canPlayerEditBanner(entityplayer)) {
			sendBannerToPlayer(entityplayer, true, true);
		}
		return true;
	}

	public boolean isPlayerAllowedByFaction(EntityPlayer entityplayer, GOTBannerProtection.Permission perm) {
		if (!playerSpecificProtection) {
			if (hasDefaultPermission(perm)) {
				return true;
			}
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(getBannerType().faction);
			return alignment >= alignmentProtection;
		}
		return false;
	}

	public boolean isPlayerPermittedInSurvival(EntityPlayer entityplayer) {
		return new GOTBannerProtection.FilterForPlayer(entityplayer, GOTBannerProtection.Permission.FULL).ignoreCreativeMode().protects(this) == GOTBannerProtection.ProtectType.NONE;
	}

	public boolean isPlayerSpecificProtection() {
		return playerSpecificProtection;
	}

	public boolean isPlayerWhitelisted(EntityPlayer entityplayer, GOTBannerProtection.Permission perm) {
		if (playerSpecificProtection) {
			if (hasDefaultPermission(perm)) {
				return true;
			}
			GameProfile playerProfile = entityplayer.getGameProfile();
			if (playerProfile != null && playerProfile.getId() != null) {
				UUID playerID = playerProfile.getId();
				for (GOTBannerWhitelistEntry entry : allowedPlayers) {
					if (entry == null) {
						continue;
					}
					GameProfile profile = entry.profile;
					boolean playerMatch = false;
					if (profile instanceof GOTFellowshipProfile) {
						Object fs;
						GOTFellowshipProfile fsPro = (GOTFellowshipProfile) profile;
						if (worldObj.isRemote) {
							fs = fsPro.getFellowshipClient();
							if (fs != null && ((GOTFellowshipClient) fs).containsPlayer(playerID)) {
								playerMatch = true;
							}
						} else {
							fs = fsPro.getFellowship();
							if (fs != null && ((GOTFellowship) fs).containsPlayer(playerID)) {
								playerMatch = true;
							}
						}
					} else if (profile.getId() != null && profile.getId().equals(playerID)) {
						playerMatch = true;
					}
					if (!playerMatch || !entry.allowsPermission(perm)) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean isProtectingTerritory() {
		return getProtectionRange() > 0;
	}

	public boolean isSelfProtection() {
		if (!GOTConfig.allowSelfProtectingBanners) {
			return false;
		}
		return selfProtection;
	}

	public boolean isStructureProtection() {
		return structureProtection;
	}

	public boolean isValidFellowship(GOTFellowship fs) {
		GameProfile owner = getPlacingPlayer();
		return fs != null && !fs.isDisbanded() && owner != null && owner.getId() != null && fs.containsPlayer(owner.getId());
	}

	@Override
	public void onUpdate() {
		boolean onSolidBlock;
		super.onUpdate();
		boolean protecting = isProtectingTerritory();
		if (!worldObj.isRemote && protecting) {
			wasEverProtecting = true;
		}
		if (!worldObj.isRemote && getPlacingPlayer() == null && playerSpecificProtection) {
			playerSpecificProtection = false;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0, posZ);
		motionZ = 0.0;
		motionX = 0.0;
		motionY = 0.0;
		moveEntity(motionX, motionY, motionZ);
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		onSolidBlock = World.doesBlockHaveSolidTopSurface(worldObj, i, j - 1, k) && boundingBox.minY == MathHelper.ceiling_double_int(boundingBox.minY);
		if (!worldObj.isRemote && !onSolidBlock) {
			dropAsItem(true);
		}
		ignoreFrustumCheck = protecting;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		setBannerTypeID(nbt.getShort("BannerType"));
		if (nbt.hasKey("PlayerProtection")) {
			readProtectionFromNBT(nbt);
			protectData = new NBTTagCompound();
			writeProtectionToNBT(protectData);
		} else if (nbt.hasKey("ProtectData")) {
			readProtectionFromNBT(nbt.getCompoundTag("ProtectData"));
		}
	}

	public void readProtectionFromNBT(NBTTagCompound nbt) {
		protectData = (NBTTagCompound) nbt.copy();
		playerSpecificProtection = nbt.getBoolean("PlayerProtection");
		structureProtection = nbt.getBoolean("StructureProtection");
		customRange = nbt.getShort("CustomRange");
		customRange = MathHelper.clamp_int(customRange, 0, 64);
		selfProtection = !nbt.hasKey("SelfProtection") || nbt.getBoolean("SelfProtection");
		if (nbt.hasKey("AlignmentProtection")) {
			setAlignmentProtection(nbt.getInteger("AlignmentProtection"));
		} else {
			setAlignmentProtection(nbt.getFloat("AlignProtectF"));
		}
		int wlength = WHITELIST_DEFAULT;
		if (nbt.hasKey("WhitelistLength")) {
			wlength = nbt.getInteger("WhitelistLength");
		}
		allowedPlayers = new GOTBannerWhitelistEntry[wlength];
		NBTTagList allowedPlayersTags = nbt.getTagList("AllowedPlayers", 10);
		for (int i = 0; i < allowedPlayersTags.tagCount(); ++i) {
			GOTBannerWhitelistEntry entry;
			NBTTagCompound playerData = allowedPlayersTags.getCompoundTagAt(i);
			int index = playerData.getInteger("Index");
			if (index < 0 || index >= wlength) {
				continue;
			}
			GameProfile profile = null;
			boolean isFellowship = playerData.getBoolean("Fellowship");
			if (isFellowship) {
				UUID fsID;
				if (playerData.hasKey("FellowshipID")) {
					String fellowshipIDString = playerData.getString("FellowshipID");
					fsID = UUID.fromString(fellowshipIDString);
					GOTFellowshipProfile fellowshipProfile = new GOTFellowshipProfile(this, fsID, "");
					if (fellowshipProfile.getFellowship() != null) {
						profile = fellowshipProfile;
					}
				}
			} else if (playerData.hasKey("Profile")) {
				NBTTagCompound profileData = playerData.getCompoundTag("Profile");
				profile = NBTUtil.func_152459_a(profileData);
			}
			if (profile == null) {
				continue;
			}
			allowedPlayers[i] = entry = new GOTBannerWhitelistEntry(profile);
			boolean savedWithPerms = playerData.getBoolean("PermsSaved");
			if (savedWithPerms) {
				if (!playerData.hasKey("Perms", 9)) {
					continue;
				}
				NBTTagList permTags = playerData.getTagList("Perms", 8);
				for (int p = 0; p < permTags.tagCount(); ++p) {
					String pName = permTags.getStringTagAt(p);
					GOTBannerProtection.Permission perm = GOTBannerProtection.Permission.forName(pName);
					if (perm == null) {
						continue;
					}
					entry.addPermission(perm);
				}
				continue;
			}
			entry.setFullPerms();
		}
		validateWhitelistedFellowships();
		defaultPermissions.clear();
		if (nbt.hasKey("DefaultPerms")) {
			NBTTagList permTags = nbt.getTagList("DefaultPerms", 8);
			for (int p = 0; p < permTags.tagCount(); ++p) {
				String pName = permTags.getStringTagAt(p);
				GOTBannerProtection.Permission perm = GOTBannerProtection.Permission.forName(pName);
				if (perm == null) {
					continue;
				}
				defaultPermissions.add(perm);
			}
		}
	}

	public void removeDefaultPermission(GOTBannerProtection.Permission p) {
		defaultPermissions.remove(p);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void resizeWhitelist(int length) {
		length = MathHelper.clamp_int(length, WHITELIST_MIN, WHITELIST_MAX);
		if (length == allowedPlayers.length) {
			return;
		}
		GOTBannerWhitelistEntry[] resized = new GOTBannerWhitelistEntry[length];
		for (int i = 0; i < length; ++i) {
			if (i >= allowedPlayers.length) {
				continue;
			}
			resized[i] = allowedPlayers[i];
		}
		allowedPlayers = resized;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void sendBannerData(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
		GOTPacketBannerData packet = new GOTPacketBannerData(getEntityId(), openGui);
		packet.playerSpecificProtection = playerSpecificProtection;
		packet.selfProtection = selfProtection;
		packet.structureProtection = structureProtection;
		packet.customRange = customRange;
		packet.alignmentProtection = alignmentProtection;
		packet.whitelistLength = getWhitelistLength();
		int maxSendIndex = sendWhitelist ? allowedPlayers.length : 1;
		String[] whitelistSlots = new String[maxSendIndex];
		int[] whitelistPerms = new int[maxSendIndex];
		for (int index = 0; index < maxSendIndex; ++index) {
			GOTBannerWhitelistEntry entry = allowedPlayers[index];
			if (entry == null) {
				whitelistSlots[index] = null;
				continue;
			}
			GameProfile profile = entry.profile;
			if (profile == null) {
				whitelistSlots[index] = null;
				continue;
			}
			if (profile instanceof GOTFellowshipProfile) {
				GOTFellowshipProfile fsProfile = (GOTFellowshipProfile) profile;
				GOTFellowship fs = fsProfile.getFellowship();
				if (isValidFellowship(fs)) {
					whitelistSlots[index] = GOTFellowshipProfile.addFellowshipCode(fs.getName());
				}
			} else {
				String username;
				if (StringUtils.isNullOrEmpty(profile.getName())) {
					MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
				}
				if (StringUtils.isNullOrEmpty(username = profile.getName())) {
					whitelistSlots[index] = null;
					if (index == 0) {
						GOTLog.logger.info("Hummel009: Banner needs to be replaced at {} {} {} dim_{}", MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), dimension);
					}
				} else {
					whitelistSlots[index] = username;
				}
			}
			if (whitelistSlots[index] == null) {
				continue;
			}
			whitelistPerms[index] = entry.encodePermBitFlags();
		}
		packet.whitelistSlots = whitelistSlots;
		packet.whitelistPerms = whitelistPerms;
		packet.defaultPerms = getDefaultPermBitFlags();
		packet.thisPlayerHasPermission = isPlayerPermittedInSurvival(entityplayer);
		GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
	}

	public void sendBannerToPlayer(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
		sendBannerData(entityplayer, sendWhitelist, openGui);
	}

	public void setAlignmentProtection(float f) {
		alignmentProtection = MathHelper.clamp_float(f, ALIGNMENT_PROTECTION_MIN, ALIGNMENT_PROTECTION_MAX);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setBannerType(GOTItemBanner.BannerType type) {
		setBannerTypeID(type.bannerID);
	}

	public void setBannerTypeID(int i) {
		dataWatcher.updateObject(18, (short) i);
	}

	public void setClientside_playerHasPermissionInSurvival(boolean flag) {
		clientside_playerHasPermission = flag;
	}

	public void setCustomRange(int i) {
		customRange = MathHelper.clamp_int(i, 0, 64);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setDefaultPermissions(Iterable<Permission> perms) {
		defaultPermissions.clear();
		for (GOTBannerProtection.Permission p : perms) {
			if (p == GOTBannerProtection.Permission.FULL) {
				continue;
			}
			defaultPermissions.add(p);
		}
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setPlacingPlayer(EntityPlayer player) {
		whitelistPlayer(0, player.getGameProfile());
	}

	public void setPlayerSpecificProtection(boolean flag) {
		playerSpecificProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setSelfProtection(boolean flag) {
		selfProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setStructureProtection(boolean flag) {
		structureProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void updateForAllWatchers(World world) {
		int x = MathHelper.floor_double(posX) >> 4;
		int z = MathHelper.floor_double(posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) worldObj).getPlayerManager();
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer obj : players) {
			if (!playermanager.isPlayerWatchingChunk((EntityPlayerMP) obj, x, z)) {
				continue;
			}
			sendBannerData(obj, false, false);
		}
	}

	public void validateWhitelistedFellowships() {
		getPlacingPlayer();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			GameProfile profile = getWhitelistedPlayer(i);
			if (!(profile instanceof GOTFellowshipProfile) || isValidFellowship(((GOTFellowshipProfile) profile).getFellowship())) {
				continue;
			}
			allowedPlayers[i] = null;
		}
	}

	public void whitelistFellowship(int index, GOTFellowship fs, Iterable<Permission> perms) {
		if (isValidFellowship(fs)) {
			whitelistPlayer(index, new GOTFellowshipProfile(this, fs.getFellowshipID(), ""), perms);
		}
	}

	public void whitelistPlayer(int index, GameProfile profile) {
		Collection<Permission> defaultPerms = new ArrayList<>();
		defaultPerms.add(GOTBannerProtection.Permission.FULL);
		whitelistPlayer(index, profile, defaultPerms);
	}

	public void whitelistPlayer(int index, GameProfile profile, Iterable<Permission> perms) {
		if (index < 0 || index >= allowedPlayers.length) {
			return;
		}
		if (profile == null) {
			allowedPlayers[index] = null;
		} else {
			GOTBannerWhitelistEntry entry = new GOTBannerWhitelistEntry(profile);
			entry.setPermissions(perms);
			allowedPlayers[index] = entry;
		}
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("BannerType", (short) getBannerTypeID());
		if (protectData == null && wasEverProtecting) {
			protectData = new NBTTagCompound();
		}
		if (protectData != null) {
			writeProtectionToNBT(protectData);
			nbt.setTag("ProtectData", protectData);
		}
	}

	public void writeProtectionToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("PlayerProtection", playerSpecificProtection);
		nbt.setBoolean("StructureProtection", structureProtection);
		nbt.setShort("CustomRange", (short) customRange);
		nbt.setBoolean("SelfProtection", selfProtection);
		nbt.setFloat("AlignProtectF", alignmentProtection);
		nbt.setInteger("WhitelistLength", allowedPlayers.length);
		NBTTagList allowedPlayersTags = new NBTTagList();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			GameProfile profile;
			GOTBannerWhitelistEntry entry = allowedPlayers[i];
			if (entry == null || (profile = entry.profile) == null) {
				continue;
			}
			NBTTagCompound playerData = new NBTTagCompound();
			playerData.setInteger("Index", i);
			boolean isFellowship = profile instanceof GOTFellowshipProfile;
			playerData.setBoolean("Fellowship", isFellowship);
			if (isFellowship) {
				GOTFellowship fs = ((GOTFellowshipProfile) profile).getFellowship();
				if (fs != null) {
					playerData.setString("FellowshipID", fs.getFellowshipID().toString());
				}
			} else {
				NBTTagCompound profileData = new NBTTagCompound();
				NBTUtil.func_152460_a(profileData, profile);
				playerData.setTag("Profile", profileData);
			}
			NBTTagList permTags = new NBTTagList();
			for (GOTBannerProtection.Permission p : entry.listPermissions()) {
				String pName = p.codeName;
				permTags.appendTag(new NBTTagString(pName));
			}
			playerData.setTag("Perms", permTags);
			playerData.setBoolean("PermsSaved", true);
			allowedPlayersTags.appendTag(playerData);
		}
		nbt.setTag("AllowedPlayers", allowedPlayersTags);
		if (!defaultPermissions.isEmpty()) {
			NBTTagList permTags = new NBTTagList();
			for (GOTBannerProtection.Permission p : defaultPermissions) {
				String pName = p.codeName;
				permTags.appendTag(new NBTTagString(pName));
			}
			nbt.setTag("DefaultPerms", permTags);
		}
	}
}
