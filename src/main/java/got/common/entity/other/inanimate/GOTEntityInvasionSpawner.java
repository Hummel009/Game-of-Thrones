package got.common.entity.other.inanimate;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.GOTBannerProtection;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.entity.GOTEntityRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemWarhorn;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketInvasionWatch;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class GOTEntityInvasionSpawner extends Entity {
	private static final double INVASION_FOLLOW_RANGE = 40.0;

	private final Map<UUID, Integer> recentPlayerContributors = new HashMap<>();
	private final Collection<GOTFaction> bonusFactions = new ArrayList<>();

	private boolean isWarhorn;
	private boolean spawnsPersistent = true;

	private float spawnerSpin;
	private float prevSpawnerSpin;

	private int invasionSize;
	private int invasionRemaining;
	private int successiveFailedSpawns;
	private int timeSincePlayerProgress;

	public GOTEntityInvasionSpawner(World world) {
		super(world);
		setSize(1.5f, 1.5f);
		renderDistanceWeight = 4.0;
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	public static GOTEntityInvasionSpawner locateInvasionNearby(Entity seeker, UUID id) {
		World world = seeker.worldObj;
		double search = 256.0;
		List<GOTEntityInvasionSpawner> invasions = world.selectEntitiesWithinAABB(GOTEntityInvasionSpawner.class, seeker.boundingBox.expand(search, search, search), new EntitySelectorImpl1(id));
		if (!invasions.isEmpty()) {
			return invasions.get(0);
		}
		return null;
	}

	public void addPlayerKill(EntityPlayer entityplayer) {
		--invasionRemaining;
		timeSincePlayerProgress = 0;
		recentPlayerContributors.put(entityplayer.getUniqueID(), 2400);
	}

	private void announceInvasionTo(ICommandSender entityplayer) {
		entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.invasion.start", getInvasionType().invasionName()));
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				endInvasion(false);
			}
			return true;
		}
		return false;
	}

	private boolean attemptSpawnMob(GOTEntityNPC npc) {
		for (int at = 0; at < 40; ++at) {
			int i = MathHelper.floor_double(posX) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int k = MathHelper.floor_double(posZ) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int j = MathHelper.floor_double(posY) + MathHelper.getRandomIntegerInRange(rand, -8, 4);
			if (!worldObj.getBlock(i, j - 1, k).isSideSolid(worldObj, i, j - 1, k, ForgeDirection.UP)) {
				continue;
			}
			npc.setLocationAndAngles(i + 0.5, j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
			npc.setLiftSpawnRestrictions(true);
			Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(npc, worldObj, (float) npc.posX, (float) npc.posY, (float) npc.posZ);
			if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !npc.getCanSpawnHere())) {
				continue;
			}
			npc.setLiftSpawnRestrictions(false);
			npc.onSpawnWithEgg(null);
			npc.setPersistent(spawnsPersistent);
			npc.setInvasionID(getInvasionID());
			npc.getKillBonusFactions().addAll(bonusFactions);
			worldObj.spawnEntityInWorld(npc);
			IAttributeInstance followRangeAttrib = npc.getEntityAttribute(SharedMonsterAttributes.followRange);
			double followRange = followRangeAttrib.getBaseValue();
			followRange = Math.max(followRange, INVASION_FOLLOW_RANGE);
			followRangeAttrib.setBaseValue(followRange);
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean canInvasionSpawnHere() {
		return !GOTBannerProtection.isProtected(worldObj, this, GOTBannerProtection.forInvasionSpawner(this), false) && !GOTEntityNPCRespawner.isSpawnBlocked(this, getInvasionType().getInvasionFaction()) && worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}

	private void endInvasion(boolean completed) {
		if (completed) {
			GOTFaction invasionFac = getInvasionType().getInvasionFaction();
			Collection<EntityPlayer> achievementPlayers = new HashSet<>();
			Collection<EntityPlayer> conqRewardPlayers = new HashSet<>();
			for (UUID player : recentPlayerContributors.keySet()) {
				EntityPlayer entityplayer = worldObj.func_152378_a(player);
				if (entityplayer == null) {
					continue;
				}
				double range = 100.0;
				if (entityplayer.dimension != dimension || entityplayer.getDistanceSqToEntity(this) >= range * range) {
					continue;
				}
				GOTPlayerData pd = GOTLevelData.getData(player);
				if (pd.getReputation(invasionFac) <= 0.0f) {
					achievementPlayers.add(entityplayer);
				}
				GOTFaction oathFac = pd.getOathFaction();
				if (oathFac == null || !oathFac.isBadRelation(invasionFac)) {
					continue;
				}
				conqRewardPlayers.add(entityplayer);
			}
			for (EntityPlayer entityplayer : achievementPlayers) {
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.addAchievement(GOTAchievement.defeatInvasion);
			}
			if (!conqRewardPlayers.isEmpty()) {
				float boostPerPlayer = 50.0f / conqRewardPlayers.size();
				for (EntityPlayer entityplayer : conqRewardPlayers) {
					GOTPlayerData pd = GOTLevelData.getData(entityplayer);
					pd.givePureConquestBonus(entityplayer, pd.getOathFaction(), getInvasionType().getInvasionFaction(), boostPerPlayer, "got.reputation.invasionDefeat", posX, posY, posZ);
				}
			}
		}
		worldObj.createExplosion(this, posX, posY + height / 2.0, posZ, 0.0f, false);
		setDead();
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(20, (byte) 0);
		dataWatcher.addObject(21, (short) 0);
		dataWatcher.addObject(22, (short) 0);
	}

	public float getInvasionHealthStatus() {
		return (float) invasionRemaining / invasionSize;
	}

	private UUID getInvasionID() {
		return getUniqueID();
	}

	public int getInvasionSize() {
		return invasionSize;
	}

	public GOTInvasions getInvasionType() {
		byte i = dataWatcher.getWatchableObjectByte(20);
		GOTInvasions type = GOTInvasions.forID(i);
		if (type != null) {
			return type;
		}
		return GOTInvasions.NORTH;
	}

	public void setInvasionType(GOTInvasions type) {
		dataWatcher.updateObject(20, (byte) type.ordinal());
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		GOTInvasions invasionType = getInvasionType();
		if (invasionType != null) {
			return GOTItemWarhorn.createHorn(invasionType);
		}
		return null;
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		return entity instanceof EntityPlayer && attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity), 0.0f);
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && entityplayer.capabilities.isCreativeMode && !bonusFactions.isEmpty()) {
			IChatComponent message = new ChatComponentText("");
			for (GOTFaction f : bonusFactions) {
				if (!message.getSiblings().isEmpty()) {
					message.appendSibling(new ChatComponentText(", "));
				}
				message.appendSibling(new ChatComponentTranslation(f.factionName()));
			}
			entityplayer.addChatMessage(message);
			return true;
		}
		return false;
	}

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			endInvasion(false);
			return;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevSpawnerSpin = spawnerSpin;
		spawnerSpin += 6.0f;
		prevSpawnerSpin = MathHelper.wrapAngleTo180_float(prevSpawnerSpin);
		spawnerSpin = MathHelper.wrapAngleTo180_float(spawnerSpin);
		motionX = 0.0;
		motionY = 0.0;
		motionZ = 0.0;
		moveEntity(motionX, motionY, motionZ);
		if (!worldObj.isRemote) {
			if (invasionRemaining > 0) {
				++timeSincePlayerProgress;
				if (timeSincePlayerProgress >= 6000 && !isWarhorn && timeSincePlayerProgress % 1200 == 0) {
					++invasionRemaining;
					invasionRemaining = Math.min(invasionRemaining, invasionSize);
				}
				if (!recentPlayerContributors.isEmpty()) {
					Collection<UUID> removes = new HashSet<>();
					for (Map.Entry<UUID, Integer> e : recentPlayerContributors.entrySet()) {
						UUID player = e.getKey();
						int time = e.getValue();
						time--;
						e.setValue(time);
						if (time > 0) {
							continue;
						}
						removes.add(player);
					}
					for (UUID player : removes) {
						recentPlayerContributors.remove(player);
					}
				}
			} else {
				endInvasion(true);
			}
		}
		if (!worldObj.isRemote && GOT.canSpawnMobs(worldObj)) {
			double nearbySearch;
			GOTInvasions invasionType = getInvasionType();
			EntityPlayer closePlayer = worldObj.getClosestPlayer(posX, posY, posZ, 80.0);
			if (closePlayer != null && invasionRemaining > 0 && worldObj.selectEntitiesWithinAABB(GOTEntityNPC.class, boundingBox.expand(nearbySearch = INVASION_FOLLOW_RANGE * 2.0, nearbySearch, nearbySearch), selectThisInvasionMobs()).size() < 16 && rand.nextInt(160) == 0) {
				int spawnAttempts = MathHelper.getRandomIntegerInRange(rand, 1, 6);
				spawnAttempts = Math.min(spawnAttempts, invasionRemaining);
				boolean spawnedAnyMobs = false;
				for (int l = 0; l < spawnAttempts; ++l) {
					GOTInvasions.InvasionSpawnEntry entry = (GOTInvasions.InvasionSpawnEntry) WeightedRandom.getRandomItem(rand, invasionType.getInvasionMobs());
					Class<? extends GOTEntityNPC> entityClass = entry.getEntityClass();
					String entityName = GOTEntityRegistry.getStringFromClass(entityClass);
					GOTEntityNPC npc = (GOTEntityNPC) EntityList.createEntityByName(entityName, worldObj);
					if (!attemptSpawnMob(npc)) {
						continue;
					}
					spawnedAnyMobs = true;
				}
				if (spawnedAnyMobs) {
					successiveFailedSpawns = 0;
					playHorn();
				} else {
					++successiveFailedSpawns;
					if (successiveFailedSpawns >= 16) {
						endInvasion(false);
					}
				}
			}
		} else {
			String particle = rand.nextBoolean() ? "smoke" : "flame";
			worldObj.spawnParticle(particle, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
		}
		updateWatchedInvasionValues();
	}

	private void playHorn() {
		worldObj.playSoundAtEntity(this, "got:item.horn", 4.0f, 0.65f + rand.nextFloat() * 0.1f);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		GOTInvasions type = GOTInvasions.forName(nbt.getString("InvasionType"));
		if (type == null && nbt.hasKey("Faction")) {
			String factionName = nbt.getString("Faction");
			type = GOTInvasions.forName(factionName);
		}
		if (type == null || type.getInvasionMobs().isEmpty()) {
			setDead();
		} else {
			int i;
			setInvasionType(type);
			if (nbt.hasKey("MobsRemaining")) {
				invasionSize = invasionRemaining = nbt.getInteger("MobsRemaining");
			} else {
				invasionSize = nbt.getInteger("InvasionSize");
				invasionRemaining = nbt.hasKey("InvasionRemaining") ? nbt.getInteger("InvasionRemaining") : invasionSize;
			}
			successiveFailedSpawns = nbt.getInteger("SuccessiveFailedSpawns");
			timeSincePlayerProgress = nbt.getInteger("TimeSinceProgress");
			recentPlayerContributors.clear();
			if (nbt.hasKey("RecentPlayers")) {
				NBTTagList recentTags = nbt.getTagList("RecentPlayers", 10);
				for (i = 0; i < recentTags.tagCount(); ++i) {
					NBTTagCompound playerData = recentTags.getCompoundTagAt(i);
					String playerS = playerData.getString("Player");
					try {
						UUID player = UUID.fromString(playerS);
						short time = playerData.getShort("Time");
						recentPlayerContributors.put(player, (int) time);
					} catch (IllegalArgumentException e) {
						FMLLog.warning("Hummel009: Error loading invasion recent players - %s is not a valid UUID", playerS);
						e.printStackTrace();
					}
				}
			}
			if (nbt.hasKey("Warhorn")) {
				isWarhorn = nbt.getBoolean("Warhorn");
			}
			if (nbt.hasKey("NPCPersistent")) {
				spawnsPersistent = nbt.getBoolean("NPCPersistent");
			}
			if (nbt.hasKey("BonusFactions")) {
				NBTTagList bonusTags = nbt.getTagList("BonusFactions", 8);
				for (i = 0; i < bonusTags.tagCount(); ++i) {
					String fName = bonusTags.getStringTagAt(i);
					GOTFaction f = GOTFaction.forName(fName);
					if (f == null) {
						continue;
					}
					bonusFactions.add(f);
				}
			}
		}
	}

	public void selectAppropriateBonusFactions() {
		if (GOTFaction.controlZonesEnabled(worldObj)) {
			GOTFaction invasionFaction = getInvasionType().getInvasionFaction();
			for (GOTFaction faction : invasionFaction.getBonusesForKilling()) {
				if (!faction.inDefinedControlZone(worldObj, posX, posZ, 50)) {
					continue;
				}
				bonusFactions.add(faction);
			}
			if (bonusFactions.isEmpty()) {
				int nearestRange = 150;
				GOTFaction nearest = null;
				double nearestDist = Double.MAX_VALUE;
				for (GOTFaction faction : invasionFaction.getBonusesForKilling()) {
					double dist;
					if ((dist = faction.distanceToNearestControlZoneInRange(worldObj, posX, posZ, nearestRange)) < 0.0 || nearest != null && dist >= nearestDist) {
						continue;
					}
					nearest = faction;
					nearestDist = dist;
				}
				if (nearest != null) {
					bonusFactions.add(nearest);
				}
			}
		}
	}

	private IEntitySelector selectThisInvasionMobs() {
		return new EntitySelectorImpl2(this);
	}

	public void setWatchingInvasion(EntityPlayerMP entityplayer, boolean overrideAlreadyWatched) {
		IMessage pkt = new GOTPacketInvasionWatch(this, overrideAlreadyWatched);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(pkt, entityplayer);
	}

	public void startInvasion() {
		startInvasion(null);
	}

	public void startInvasion(EntityPlayer announcePlayer) {
		startInvasion(announcePlayer, -1);
	}

	public void startInvasion(EntityPlayer announcePlayer, int size) {
		int size1 = size;
		if (size1 < 0) {
			size1 = MathHelper.getRandomIntegerInRange(rand, 30, 70);
		}
		invasionRemaining = invasionSize = size1;
		playHorn();
		double announceRange = INVASION_FOLLOW_RANGE * 2.0;
		List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(announceRange, announceRange, announceRange));
		if (announcePlayer != null && !nearbyPlayers.contains(announcePlayer)) {
			nearbyPlayers.add(announcePlayer);
		}
		for (EntityPlayer entityplayer : nearbyPlayers) {
			boolean announce = GOTLevelData.getData(entityplayer).getReputation(getInvasionType().getInvasionFaction()) < 0.0f;
			if (entityplayer == announcePlayer) {
				announce = true;
			}
			if (!announce) {
				continue;
			}
			announceInvasionTo(entityplayer);
			setWatchingInvasion((EntityPlayerMP) entityplayer, false);
		}
	}

	private void updateWatchedInvasionValues() {
		if (worldObj.isRemote) {
			invasionSize = dataWatcher.getWatchableObjectShort(21);
			invasionRemaining = dataWatcher.getWatchableObjectShort(22);
		} else {
			dataWatcher.updateObject(21, (short) invasionSize);
			dataWatcher.updateObject(22, (short) invasionRemaining);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setString("InvasionType", getInvasionType().codeName());
		nbt.setInteger("InvasionSize", invasionSize);
		nbt.setInteger("InvasionRemaining", invasionRemaining);
		nbt.setInteger("SuccessiveFailedSpawns", successiveFailedSpawns);
		nbt.setInteger("TimeSinceProgress", timeSincePlayerProgress);
		if (!recentPlayerContributors.isEmpty()) {
			NBTTagList recentTags = new NBTTagList();
			for (Map.Entry<UUID, Integer> e : recentPlayerContributors.entrySet()) {
				UUID player = e.getKey();
				int time = e.getValue();
				NBTTagCompound playerData = new NBTTagCompound();
				playerData.setString("Player", player.toString());
				playerData.setShort("Time", (short) time);
				recentTags.appendTag(playerData);
			}
			nbt.setTag("RecentPlayers", recentTags);
		}
		nbt.setBoolean("Warhorn", isWarhorn);
		nbt.setBoolean("NPCPersistent", spawnsPersistent);
		if (!bonusFactions.isEmpty()) {
			NBTTagList bonusTags = new NBTTagList();
			for (GOTFaction f : bonusFactions) {
				String fName = f.codeName();
				bonusTags.appendTag(new NBTTagString(fName));
			}
			nbt.setTag("BonusFactions", bonusTags);
		}
	}

	public void setWarhorn(boolean warhorn) {
		isWarhorn = warhorn;
	}

	public void setSpawnsPersistent(boolean spawnsPersistent) {
		this.spawnsPersistent = spawnsPersistent;
	}

	public float getSpawnerSpin() {
		return spawnerSpin;
	}

	public float getPrevSpawnerSpin() {
		return prevSpawnerSpin;
	}

	private static class EntitySelectorImpl1 implements IEntitySelector {
		private final UUID id;

		private EntitySelectorImpl1(UUID id) {
			this.id = id;
		}

		@Override
		public boolean isEntityApplicable(Entity e) {
			return e.getUniqueID().equals(id);
		}
	}

	private static class EntitySelectorImpl2 implements IEntitySelector {
		private final GOTEntityInvasionSpawner spawner;

		private EntitySelectorImpl2(GOTEntityInvasionSpawner spawner) {
			this.spawner = spawner;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			if (entity.isEntityAlive() && entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				return npc.getInvasionID() != null && npc.getInvasionID().equals(spawner.getInvasionID());
			}
			return false;
		}
	}
}