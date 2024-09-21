package got.common.entity.other.inanimate;

import com.mojang.authlib.GameProfile;
import got.common.GOTBannerProtection;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTItems;
import got.common.entity.other.utils.GOTBannerWhitelistEntry;
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

import java.util.*;

public class GOTEntityBanner extends Entity {
	public static final float REPUTATION_PROTECTION_MIN = 1.0f;
	public static final float REPUTATION_PROTECTION_MAX = 10000.0f;
	public static final int WHITELIST_MIN = 1;
	public static final int WHITELIST_MAX = 4000;

	private static final int WHITELIST_DEFAULT = 16;

	private final Set<GOTBannerProtection.Permission> defaultPermissions = EnumSet.noneOf(GOTBannerProtection.Permission.class);

	private GOTBannerWhitelistEntry[] allowedPlayers = new GOTBannerWhitelistEntry[WHITELIST_DEFAULT];
	private NBTTagCompound protectData;

	private boolean wasEverProtecting;
	private boolean playerSpecificProtection;
	private boolean structureProtection;
	private boolean selfProtection = true;
	private boolean clientside_playerHasPermission;

	private float reputationProtection = REPUTATION_PROTECTION_MIN;
	private int customRange;

	@SuppressWarnings({"WeakerAccess", "unused"})
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
		return owner != null && owner.getId() != null && entityplayer.getUniqueID().equals(owner.getId()) || !structureProtection && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && entityplayer.capabilities.isCreativeMode;
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

	private void dropAsItem(boolean drop) {
		setDead();
		if (drop) {
			entityDropItem(getBannerItem(), 0.0f);
		}
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(18, (short) 0);
	}

	public float getReputationProtection() {
		return reputationProtection;
	}

	public void setReputationProtection(float f) {
		reputationProtection = MathHelper.clamp_float(f, REPUTATION_PROTECTION_MIN, REPUTATION_PROTECTION_MAX);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	private ItemStack getBannerItem() {
		ItemStack item = new ItemStack(GOTItems.banner, 1, getBannerType().getBannerID());
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

	public void setBannerType(GOTItemBanner.BannerType type) {
		setBannerTypeID(type.getBannerID());
	}

	private int getBannerTypeID() {
		return dataWatcher.getWatchableObjectShort(18);
	}

	private void setBannerTypeID(int i) {
		dataWatcher.updateObject(18, (short) i);
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

	public void setPlacingPlayer(EntityPlayer player) {
		whitelistPlayer(0, player.getGameProfile());
	}

	private int getProtectionRange() {
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
		return allowedPlayers[index].getProfile();
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
			float reputation = GOTLevelData.getData(entityplayer).getReputation(getBannerType().getFaction());
			return reputation >= reputationProtection;
		}
		return false;
	}

	private boolean isPlayerPermittedInSurvival(EntityPlayer entityplayer) {
		return new GOTBannerProtection.FilterForPlayer(entityplayer, GOTBannerProtection.Permission.FULL).ignoreCreativeMode().protects(this) == GOTBannerProtection.ProtectType.NONE;
	}

	public boolean isPlayerSpecificProtection() {
		return playerSpecificProtection;
	}

	public void setPlayerSpecificProtection(boolean flag) {
		playerSpecificProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
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
					GameProfile profile = entry.getProfile();
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
		return GOTConfig.allowSelfProtectingBanners && selfProtection;
	}

	public void setSelfProtection(boolean flag) {
		selfProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public boolean isStructureProtection() {
		return structureProtection;
	}

	public void setStructureProtection(boolean flag) {
		structureProtection = flag;
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	private boolean isValidFellowship(GOTFellowship fs) {
		GameProfile owner = getPlacingPlayer();
		return fs != null && !fs.isDisbanded() && owner != null && owner.getId() != null && fs.containsPlayer(owner.getId());
	}

	@Override
	public void onUpdate() {
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
		boolean onSolidBlock = World.doesBlockHaveSolidTopSurface(worldObj, i, j - 1, k) && boundingBox.minY == MathHelper.ceiling_double_int(boundingBox.minY);
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
		if (nbt.hasKey("ReputationProtection")) {
			setReputationProtection(nbt.getInteger("ReputationProtection"));
		} else {
			setReputationProtection(nbt.getFloat("RepProtectF"));
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
					GOTFellowshipProfile fellowshipProfile = new GOTFellowshipProfile(fsID, "");
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
		int length1 = MathHelper.clamp_int(length, WHITELIST_MIN, WHITELIST_MAX);
		if (length1 == allowedPlayers.length) {
			return;
		}
		GOTBannerWhitelistEntry[] resized = new GOTBannerWhitelistEntry[length1];
		for (int i = 0; i < length1; ++i) {
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

	private void sendBannerData(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
		GOTPacketBannerData packet = new GOTPacketBannerData(getEntityId(), openGui);
		packet.setPlayerSpecificProtection(playerSpecificProtection);
		packet.setSelfProtection(selfProtection);
		packet.setStructureProtection(structureProtection);
		packet.setCustomRange(customRange);
		packet.setReputationProtection(reputationProtection);
		packet.setWhitelistLength(getWhitelistLength());
		int maxSendIndex = sendWhitelist ? allowedPlayers.length : 1;
		String[] whitelistSlots = new String[maxSendIndex];
		int[] whitelistPerms = new int[maxSendIndex];
		for (int index = 0; index < maxSendIndex; ++index) {
			GOTBannerWhitelistEntry entry = allowedPlayers[index];
			if (entry == null) {
				whitelistSlots[index] = null;
				continue;
			}
			GameProfile profile = entry.getProfile();
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
						GOTLog.getLogger().info("Hummel009: Banner needs to be replaced at {} {} {} dim_{}", MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), dimension);
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
		packet.setWhitelistSlots(whitelistSlots);
		packet.setWhitelistPerms(whitelistPerms);
		packet.setDefaultPerms(getDefaultPermBitFlags());
		packet.setThisPlayerHasPermission(isPlayerPermittedInSurvival(entityplayer));
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
	}

	public void sendBannerToPlayer(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
		sendBannerData(entityplayer, sendWhitelist, openGui);
	}

	public void setClientside_playerHasPermissionInSurvival(boolean flag) {
		clientside_playerHasPermission = flag;
	}

	private void updateForAllWatchers(World world) {
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

	private void validateWhitelistedFellowships() {
		getPlacingPlayer();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			GameProfile profile = getWhitelistedPlayer(i);
			if (!(profile instanceof GOTFellowshipProfile) || isValidFellowship(((GOTFellowshipProfile) profile).getFellowship())) {
				continue;
			}
			allowedPlayers[i] = null;
		}
	}

	public void whitelistFellowship(int index, GOTFellowship fs, Iterable<GOTBannerProtection.Permission> perms) {
		if (isValidFellowship(fs)) {
			whitelistPlayer(index, new GOTFellowshipProfile(fs.getFellowshipID(), ""), perms);
		}
	}

	public void whitelistPlayer(int index, GameProfile profile) {
		Collection<GOTBannerProtection.Permission> defaultPerms = new ArrayList<>();
		defaultPerms.add(GOTBannerProtection.Permission.FULL);
		whitelistPlayer(index, profile, defaultPerms);
	}

	public void whitelistPlayer(int index, GameProfile profile, Iterable<GOTBannerProtection.Permission> perms) {
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

	private void writeProtectionToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("PlayerProtection", playerSpecificProtection);
		nbt.setBoolean("StructureProtection", structureProtection);
		nbt.setShort("CustomRange", (short) customRange);
		nbt.setBoolean("SelfProtection", selfProtection);
		nbt.setFloat("RepProtectF", reputationProtection);
		nbt.setInteger("WhitelistLength", allowedPlayers.length);
		NBTTagList allowedPlayersTags = new NBTTagList();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			GameProfile profile;
			GOTBannerWhitelistEntry entry = allowedPlayers[i];
			if (entry == null || (profile = entry.getProfile()) == null) {
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
				String pName = p.getCodeName();
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
				String pName = p.getCodeName();
				permTags.appendTag(new NBTTagString(pName));
			}
			nbt.setTag("DefaultPerms", permTags);
		}
	}

	public void setCustomRange(int i) {
		customRange = MathHelper.clamp_int(i, 0, 64);
		if (!worldObj.isRemote) {
			updateForAllWatchers(worldObj);
		}
	}

	public void setDefaultPermissions(Iterable<GOTBannerProtection.Permission> perms) {
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
}