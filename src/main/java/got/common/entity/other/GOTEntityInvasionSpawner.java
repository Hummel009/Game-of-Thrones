package got.common.entity.other;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import got.GOT;
import got.common.*;
import got.common.database.*;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemConquestHorn;
import got.common.network.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

public class GOTEntityInvasionSpawner extends Entity {
	public static int MAX_INVASION_SIZE = 10000;
	public static double INVASION_FOLLOW_RANGE = 40.0;
	public float spawnerSpin;
	public float prevSpawnerSpin;
	public int invasionSize;
	public int invasionRemaining;
	public int successiveFailedSpawns = 0;
	public int timeSincePlayerProgress = 0;
	public Map<UUID, Integer> recentPlayerContributors = new HashMap<>();
	public boolean isWarhorn = false;
	public boolean spawnsPersistent = true;
	public List<GOTFaction> bonusFactions = new ArrayList<>();

	public GOTEntityInvasionSpawner(World world) {
		super(world);
		setSize(1.5f, 1.5f);
		renderDistanceWeight = 4.0;
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	public void addPlayerKill(EntityPlayer entityplayer) {
		--invasionRemaining;
		timeSincePlayerProgress = 0;
		recentPlayerContributors.put(entityplayer.getUniqueID(), 2400);
	}

	public void announceInvasionTo(EntityPlayer entityplayer) {
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

	public boolean attemptSpawnMob(GOTEntityNPC npc) {
		for (int at = 0; at < 40; ++at) {
			int i = MathHelper.floor_double(posX) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int k = MathHelper.floor_double(posZ) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int j = MathHelper.floor_double(posY) + MathHelper.getRandomIntegerInRange(rand, -8, 4);
			if (!worldObj.getBlock(i, j - 1, k).isSideSolid(worldObj, i, j - 1, k, ForgeDirection.UP)) {
				continue;
			}
			npc.setLocationAndAngles(i + 0.5, j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
			npc.liftSpawnRestrictions = true;
			Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(npc, worldObj, ((float) npc.posX), ((float) npc.posY), ((float) npc.posZ));
			if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !npc.getCanSpawnHere())) {
				continue;
			}
			npc.liftSpawnRestrictions = false;
			npc.onSpawnWithEgg(null);
			npc.isNPCPersistent = false;
			if (spawnsPersistent) {
				npc.isNPCPersistent = true;
			}
			npc.setInvasionID(getInvasionID());
			npc.killBonusFactions.addAll(bonusFactions);
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
		if (GOTBannerProtection.isProtected(worldObj, this, GOTBannerProtection.forInvasionSpawner(this), false) || GOTEntityNPCRespawner.isSpawnBlocked(this, getInvasionType().invasionFaction)) {
			return false;
		}
		return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}

	public void endInvasion(boolean completed) {
		if (completed) {
			GOTFaction invasionFac = getInvasionType().invasionFaction;
			HashSet<EntityPlayer> achievementPlayers = new HashSet<>();
			HashSet<EntityPlayer> conqRewardPlayers = new HashSet<>();
			for (UUID player : recentPlayerContributors.keySet()) {
				GOTFaction pledged;
				EntityPlayer entityplayer = worldObj.func_152378_a(player);
				if (entityplayer == null) {
					continue;
				}
				double range = 100.0;
				if (entityplayer.dimension != dimension || (entityplayer.getDistanceSqToEntity(this) >= range * range)) {
					continue;
				}
				GOTPlayerData pd = GOTLevelData.getData(player);
				if (pd.getAlignment(invasionFac) <= 0.0f) {
					achievementPlayers.add(entityplayer);
				}
				if ((pledged = pd.getPledgeFaction()) == null || !pledged.isBadRelation(invasionFac)) {
					continue;
				}
				conqRewardPlayers.add(entityplayer);
			}
			for (EntityPlayer entityplayer : achievementPlayers) {
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.addAchievement(GOTAchievement.DEFEAT_INVASION);
			}
			if (!conqRewardPlayers.isEmpty()) {
				float boostPerPlayer = 50.0f / conqRewardPlayers.size();
				for (EntityPlayer entityplayer : conqRewardPlayers) {
					GOTPlayerData pd = GOTLevelData.getData(entityplayer);
					pd.givePureConquestBonus(entityplayer, pd.getPledgeFaction(), getInvasionType().invasionFaction, boostPerPlayer, "got.alignment.invasionDefeat", posX, posY, posZ);
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
		return (float) invasionRemaining / (float) invasionSize;
	}

	public UUID getInvasionID() {
		return getUniqueID();
	}

	public ItemStack getInvasionItem() {
		return getInvasionType().getInvasionIcon();
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

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		GOTInvasions invasionType = getInvasionType();
		if (invasionType != null) {
			return GOTItemConquestHorn.createHorn(invasionType);
		}
		return null;
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		if (entity instanceof EntityPlayer) {
			return attackEntityFrom(DamageSource.causePlayerDamage(((EntityPlayer) entity)), 0.0f);
		}
		return false;
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && entityplayer.capabilities.isCreativeMode && !bonusFactions.isEmpty()) {
			ChatComponentText message = new ChatComponentText("");
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
					HashSet<UUID> removes = new HashSet<>();
					for (Map.Entry<UUID, Integer> e : recentPlayerContributors.entrySet()) {
						UUID player = e.getKey();
						int time = e.getValue();
						e.setValue(--time);
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
			if (closePlayer != null && invasionRemaining > 0 && (worldObj.selectEntitiesWithinAABB(GOTEntityNPC.class, boundingBox.expand(nearbySearch = INVASION_FOLLOW_RANGE * 2.0, nearbySearch, nearbySearch), selectThisInvasionMobs())).size() < 16 && rand.nextInt(160) == 0) {
				int spawnAttempts = MathHelper.getRandomIntegerInRange(rand, 1, 6);
				spawnAttempts = Math.min(spawnAttempts, invasionRemaining);
				boolean spawnedAnyMobs = false;
				for (int l = 0; l < spawnAttempts; ++l) {
					GOTInvasions.InvasionSpawnEntry entry = (GOTInvasions.InvasionSpawnEntry) WeightedRandom.getRandomItem(rand, invasionType.invasionMobs);
					Class entityClass = entry.getEntityClass();
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

	public void playHorn() {
		worldObj.playSoundAtEntity(this, "got:item.horn", 4.0f, 0.65f + rand.nextFloat() * 0.1f);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		GOTInvasions type = GOTInvasions.forName(nbt.getString("InvasionType"));
		if (type == null && nbt.hasKey("Faction")) {
			String factionName = nbt.getString("Faction");
			type = GOTInvasions.forName(factionName);
		}
		if (type == null || type.invasionMobs.isEmpty()) {
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
						if (player == null) {
							continue;
						}
						short time = playerData.getShort("Time");
						recentPlayerContributors.put(player, (int) time);
						continue;
					} catch (IllegalArgumentException e) {
						FMLLog.warning("GOT: Error loading invasion recent players - %s is not a valid UUID", playerS);
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
			GOTFaction invasionFaction = getInvasionType().invasionFaction;
			for (GOTFaction faction : invasionFaction.getBonusesForKilling()) {
				if (faction.isolationist || !faction.inDefinedControlZone(worldObj, posX, posY, posZ, 50)) {
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
					if (faction.isolationist || ((dist = faction.distanceToNearestControlZoneInRange(worldObj, posX, posY, posZ, nearestRange)) < 0.0) || nearest != null && (dist >= nearestDist)) {
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

	public IEntitySelector selectThisInvasionMobs() {
		return new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				if (entity.isEntityAlive() && entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					return npc.isInvasionSpawned() && npc.getInvasionID().equals(GOTEntityInvasionSpawner.this.getInvasionID());
				}
				return false;
			}
		};
	}

	public void setInvasionType(GOTInvasions type) {
		dataWatcher.updateObject(20, ((byte) type.ordinal()));
	}

	public void setWatchingInvasion(EntityPlayerMP entityplayer, boolean overrideAlreadyWatched) {
		GOTPacketInvasionWatch pkt = new GOTPacketInvasionWatch(this, overrideAlreadyWatched);
		GOTPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
	}

	public void startInvasion() {
		this.startInvasion(null);
	}

	public void startInvasion(EntityPlayer announcePlayer) {
		this.startInvasion(announcePlayer, -1);
	}

	public void startInvasion(EntityPlayer announcePlayer, int size) {
		if (size < 0) {
			size = MathHelper.getRandomIntegerInRange(rand, 30, 70);
		}
		invasionRemaining = invasionSize = size;
		playHorn();
		double announceRange = INVASION_FOLLOW_RANGE * 2.0;
		List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(announceRange, announceRange, announceRange));
		if (announcePlayer != null && !nearbyPlayers.contains(announcePlayer)) {
			nearbyPlayers.add(announcePlayer);
		}
		for (EntityPlayer entityplayer : nearbyPlayers) {
			boolean announce;
			announce = GOTLevelData.getData(entityplayer).getAlignment(getInvasionType().invasionFaction) < 0.0f;
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

	public void updateWatchedInvasionValues() {
		if (!worldObj.isRemote) {
			dataWatcher.updateObject(21, ((short) invasionSize));
			dataWatcher.updateObject(22, ((short) invasionRemaining));
		} else {
			invasionSize = dataWatcher.getWatchableObjectShort(21);
			invasionRemaining = dataWatcher.getWatchableObjectShort(22);
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

	public static GOTEntityInvasionSpawner locateInvasionNearby(Entity seeker, UUID id) {
		World world = seeker.worldObj;
		double search = 256.0;
		List invasions = world.selectEntitiesWithinAABB(GOTEntityInvasionSpawner.class, seeker.boundingBox.expand(search, search, search), new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity e) {
				return e.getUniqueID().equals(id);
			}
		});
		if (!invasions.isEmpty()) {
			return (GOTEntityInvasionSpawner) (invasions.get(0));
		}
		return null;
	}

}