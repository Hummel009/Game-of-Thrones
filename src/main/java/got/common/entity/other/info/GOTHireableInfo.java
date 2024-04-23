package got.common.entity.other.info;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTMountFunctions;
import got.common.entity.other.GOTNPCMount;
import got.common.entity.other.GOTUnitTradeEntry;
import got.common.faction.GOTFaction;
import got.common.inventory.GOTInventoryNPC;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketHiredGui;
import got.common.network.GOTPacketHiredInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.UUID;

public class GOTHireableInfo {
	public static final int GUARD_RANGE_MAX = 64;
	public static final int GUARD_RANGE_MIN = 1;

	private static final int GUARD_RANGE_DEFAULT = 8;

	private final GOTEntityNPC theEntity;

	private GOTUnitTradeEntry.PledgeType pledgeType = GOTUnitTradeEntry.PledgeType.NONE;
	private Task hiredTask = Task.WARRIOR;

	private GOTInventoryNPC hiredInventory;
	private String hiredSquadron;
	private UUID hiringPlayerUUID;

	private boolean isActive;
	private boolean isGuiOpen;
	private boolean doneFirstUpdate;
	private boolean guardMode;
	private boolean inCombat;
	private boolean prevInCombat;
	private boolean canMove = true;
	private boolean resendBasicData = true;
	private boolean teleportAutomatically = true;
	private boolean targetFromCommandSword;
	private boolean wasAttackCommanded;

	private float alignmentRequiredToCommand;

	private int mobKills;
	private int xp;
	private int xpLevel = 1;

	private int guardRange = GUARD_RANGE_DEFAULT;

	public GOTHireableInfo(GOTEntityNPC npc) {
		theEntity = npc;
	}

	private void addExperience(int i) {
		xp += i;
		while (xp >= totalXPForLevel(xpLevel + 1)) {
			++xpLevel;
			markDirty();
			onLevelUp();
		}
		sendClientPacket(false);
	}

	public void commandSwordAttack(EntityLivingBase target) {
		if (GOT.canNPCAttackEntity(theEntity, target, true)) {
			theEntity.getNavigator().clearPathEntity();
			theEntity.setRevengeTarget(target);
			theEntity.setAttackTarget(target);
			targetFromCommandSword = true;
		}
	}

	public void commandSwordCancel() {
		if (targetFromCommandSword) {
			theEntity.getNavigator().clearPathEntity();
			theEntity.setRevengeTarget(null);
			theEntity.setAttackTarget(null);
			targetFromCommandSword = false;
		}
	}

	public void dismissUnit(boolean isDesertion) {
		if (isDesertion) {
			getHiringPlayer().addChatMessage(new ChatComponentTranslation("got.hiredNPC.desert", theEntity.getCommandSenderName()));
		} else {
			getHiringPlayer().addChatMessage(new ChatComponentTranslation("got.hiredNPC.dismiss", theEntity.getCommandSenderName()));
		}
		if (hiredTask == Task.FARMER && hiredInventory != null) {
			hiredInventory.dropAllItems();
		}
		isActive = false;
		canMove = true;
		sendClientPacket(false);
		setHiringPlayer(null);
	}

	public int getGuardRange() {
		return guardRange;
	}

	public void setGuardRange(int range) {
		guardRange = MathHelper.clamp_int(range, GUARD_RANGE_MIN, GUARD_RANGE_MAX);
		if (guardMode) {
			int i = MathHelper.floor_double(theEntity.posX);
			int j = MathHelper.floor_double(theEntity.posY);
			int k = MathHelper.floor_double(theEntity.posZ);
			theEntity.setHomeArea(i, j, k, guardRange);
		}
	}

	public GOTInventoryNPC getHiredInventory() {
		return hiredInventory;
	}

	public EntityPlayer getHiringPlayer() {
		if (hiringPlayerUUID == null) {
			return null;
		}
		return theEntity.worldObj.func_152378_a(hiringPlayerUUID);
	}

	private void setHiringPlayer(EntityPlayer entityplayer) {
		hiringPlayerUUID = entityplayer == null ? null : entityplayer.getUniqueID();
		markDirty();
	}

	public UUID getHiringPlayerUUID() {
		return hiringPlayerUUID;
	}

	public boolean getObeyCommandSword() {
		return hiredTask == Task.WARRIOR && !guardMode;
	}

	public boolean getObeyHornHaltReady() {
		return hiredTask == Task.WARRIOR && !guardMode;
	}

	public boolean getObeyHornSummon() {
		return hiredTask == Task.WARRIOR && !guardMode;
	}

	public float getProgressToNextLevel() {
		int cap = totalXPForLevel(xpLevel + 1);
		int start = totalXPForLevel(xpLevel);
		return (float) (xp - start) / (cap - start);
	}

	public String getHiredSquadron() {
		return hiredSquadron;
	}

	public void setHiredSquadron(String s) {
		hiredSquadron = s;
		markDirty();
	}

	public String getStatusString() {
		String status = "";
		if (hiredTask == Task.WARRIOR) {
			status = inCombat ? StatCollector.translateToLocal("got.hiredNPC.status.combat") : isHalted() ? StatCollector.translateToLocal("got.hiredNPC.status.halted") : guardMode ? StatCollector.translateToLocal("got.hiredNPC.status.guard") : StatCollector.translateToLocal("got.hiredNPC.status.ready");
		} else if (hiredTask == Task.FARMER) {
			status = guardMode ? StatCollector.translateToLocal("got.hiredNPC.status.farming") : StatCollector.translateToLocal("got.hiredNPC.status.following");
		}
		return StatCollector.translateToLocalFormatted("got.hiredNPC.status", status);
	}

	private void setTask(Task t) {
		if (t != hiredTask) {
			hiredTask = t;
			markDirty();
		}
		if (hiredTask == Task.FARMER) {
			hiredInventory = new GOTInventoryNPC("HiredInventory", theEntity, 4);
		}
	}

	public void halt() {
		canMove = false;
		theEntity.setAttackTarget(null);
		sendClientPacket(false);
	}

	public boolean hasHiringRequirements() {
		return theEntity.getFaction().isPlayableAlignmentFaction() && alignmentRequiredToCommand >= 0.0f;
	}

	public void hireUnit(EntityPlayer entityplayer, boolean setLocation, GOTFaction hiringFaction, GOTUnitTradeEntry tradeEntry, String squadron, Entity mount) {
		float alignment = tradeEntry.getAlignmentRequired();
		GOTUnitTradeEntry.PledgeType pledge = tradeEntry.getPledgeType();
		Task task = tradeEntry.getTask();
		if (setLocation) {
			theEntity.setLocationAndAngles(entityplayer.posX, entityplayer.boundingBox.minY, entityplayer.posZ, entityplayer.rotationYaw + 180.0f, 0.0f);
		}
		isActive = true;
		alignmentRequiredToCommand = alignment;
		pledgeType = pledge;
		setHiringPlayer(entityplayer);
		setTask(task);
		setHiredSquadron(squadron);
		if (hiringFaction != null && hiringFaction.isPlayableAlignmentFaction()) {
			GOTLevelData.getData(entityplayer).getFactionData(hiringFaction).addHire();
		}
		if (mount != null) {
			mount.setLocationAndAngles(theEntity.posX, theEntity.boundingBox.minY, theEntity.posZ, theEntity.rotationYaw, 0.0f);
			if (mount instanceof GOTEntityNPC) {
				GOTEntityNPC hiredMountNPC = (GOTEntityNPC) mount;
				hiredMountNPC.getHireableInfo().hireUnit(entityplayer, setLocation, hiringFaction, tradeEntry, squadron, null);
			}
			theEntity.mountEntity(mount);
			if (mount instanceof GOTNPCMount && !(mount instanceof GOTEntityNPC)) {
				theEntity.setRidingHorse(true);
				GOTNPCMount hiredHorse = (GOTNPCMount) mount;
				hiredHorse.setBelongsToNPC(true);
				GOTMountFunctions.setNavigatorRangeFromNPC(hiredHorse, theEntity);
			}
		}
	}

	public boolean isGuardMode() {
		return guardMode;
	}

	public void setGuardMode(boolean flag) {
		guardMode = flag;
		if (flag) {
			int i = MathHelper.floor_double(theEntity.posX);
			int j = MathHelper.floor_double(theEntity.posY);
			int k = MathHelper.floor_double(theEntity.posZ);
			theEntity.setHomeArea(i, j, k, guardRange);
		} else {
			theEntity.detachHome();
		}
	}

	public boolean isHalted() {
		return !guardMode && !canMove;
	}

	private void markDirty() {
		if (!theEntity.worldObj.isRemote) {
			if (theEntity.ticksExisted > 0) {
				resendBasicData = true;
			} else {
				sendBasicDataToAllWatchers();
			}
		}
	}

	public void onDeath() {
		EntityPlayer hiringPlayer;
		if (!theEntity.worldObj.isRemote && isActive && getHiringPlayer() != null && GOTLevelData.getData(hiringPlayer = getHiringPlayer()).getEnableHiredDeathMessages()) {
			hiringPlayer.addChatMessage(new ChatComponentTranslation("got.hiredNPC.death", theEntity.func_110142_aN().func_151521_b()));
		}
		if (!theEntity.worldObj.isRemote && hiredInventory != null) {
			hiredInventory.dropAllItems();
		}
	}

	public void onKillEntity(EntityLivingBase target) {
		if (!theEntity.worldObj.isRemote && isActive) {
			++mobKills;
			sendClientPacket(false);
			if (hiredTask == Task.WARRIOR) {
				boolean wasEnemy = false;
				int addXP = 0;
				GOTFaction unitFaction = theEntity.getFaction();
				if (target instanceof EntityPlayer) {
					wasEnemy = GOTLevelData.getData((EntityPlayer) target).getAlignment(unitFaction) < 0.0f;
				} else {
					GOTFaction targetFaction = GOT.getNPCFaction(target);
					if (targetFaction.isBadRelation(unitFaction) || unitFaction == GOTFaction.UNALIGNED && targetFaction != GOTFaction.UNALIGNED) {
						wasEnemy = true;
						addXP = 1;
					}
				}
				if (wasEnemy && theEntity.getRNG().nextInt(3) == 0) {
					String speechBank;
					EntityPlayer hiringPlayer = getHiringPlayer();
					if (hiringPlayer != null && theEntity.getDistanceSqToEntity(hiringPlayer) < 256.0 && (speechBank = theEntity.getSpeechBank(hiringPlayer)) != null) {
						theEntity.sendSpeechBank(hiringPlayer, speechBank);
					}
				}
				if (addXP > 0) {
					addExperience(addXP);
				}
			}
		}
	}

	private void onLevelUp() {
		float healthBoost = 2.0f;
		IAttributeInstance attrHealth = theEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		attrHealth.setBaseValue(attrHealth.getBaseValue() + healthBoost);
		theEntity.heal(healthBoost);
		EntityPlayer hirer = getHiringPlayer();
		if (hirer != null) {
			hirer.addChatMessage(new ChatComponentTranslation("got.hiredNPC.levelUp", theEntity.getCommandSenderName(), xpLevel));
		}
		boolean bigLvlUp = xpLevel % 5 == 0;
		World world = theEntity.worldObj;
		ItemStack itemstack = new ItemStack(Items.fireworks);
		NBTTagCompound itemData = new NBTTagCompound();
		NBTTagCompound fireworkData = new NBTTagCompound();
		NBTTagList explosionsList = new NBTTagList();
		int explosions = 1;
		for (int l = 0; l < explosions; ++l) {
			NBTTagCompound explosionData = new NBTTagCompound();
			explosionData.setBoolean("Flicker", true);
			explosionData.setBoolean("Trail", bigLvlUp);
			int[] colors = {16733440, theEntity.getFaction().getFactionColor()};
			explosionData.setIntArray("Colors", colors);
			explosionData.setByte("Type", (byte) (bigLvlUp ? 1 : 0));
			explosionsList.appendTag(explosionData);
		}
		fireworkData.setTag("Explosions", explosionsList);
		itemData.setTag("Fireworks", fireworkData);
		itemstack.setTagCompound(itemData);
		EntityFireworkRocket firework = new EntityFireworkRocket(world, theEntity.posX, theEntity.boundingBox.minY + theEntity.height, theEntity.posZ, itemstack);
		NBTTagCompound fireworkNBT = new NBTTagCompound();
		firework.writeEntityToNBT(fireworkNBT);
		fireworkNBT.setInteger("LifeTime", bigLvlUp ? 20 : 15);
		firework.readEntityFromNBT(fireworkNBT);
		world.spawnEntityInWorld(firework);
	}

	public void onSetTarget(EntityLivingBase newTarget, EntityLivingBase prevTarget) {
		if (newTarget == null || newTarget != prevTarget) {
			targetFromCommandSword = false;
			wasAttackCommanded = false;
		}
	}

	public void onUpdate() {
		if (!theEntity.worldObj.isRemote) {
			EntityPlayer entityplayer;
			if (!doneFirstUpdate) {
				doneFirstUpdate = true;
			}
			if (resendBasicData) {
				sendBasicDataToAllWatchers();
				resendBasicData = false;
			}
			if (hasHiringRequirements() && isActive && (entityplayer = getHiringPlayer()) != null) {
				GOTFaction fac = theEntity.getFaction();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				boolean canCommand = pd.getAlignment(fac) >= alignmentRequiredToCommand;
				if (!pledgeType.canAcceptPlayer(entityplayer, fac)) {
					canCommand = false;
				}
				if (!canCommand) {
					dismissUnit(true);
				}
			}
			inCombat = theEntity.getAttackTarget() != null;
			if (inCombat != prevInCombat) {
				sendClientPacket(false);
			}
			prevInCombat = inCombat;
			if (hiredTask == Task.WARRIOR && !inCombat && shouldFollowPlayer() && theEntity.getRNG().nextInt(4000) == 0) {
				String speechBank;
				EntityPlayer hiringPlayer = getHiringPlayer();
				double range = 16.0;
				if (hiringPlayer != null && theEntity.getDistanceSqToEntity(hiringPlayer) < range * range && (speechBank = theEntity.getSpeechBank(hiringPlayer)) != null) {
					theEntity.sendSpeechBank(hiringPlayer, speechBank);
				}
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag("HiredNPCInfo");
		if (data != null) {
			String savedUUID;
			if (data.hasKey("HiringPlayerName")) {
				String name = data.getString("HiringPlayerName");
				hiringPlayerUUID = UUID.fromString(PreYggdrasilConverter.func_152719_a(name));
			} else if (data.hasKey("HiringPlayerUUID") && !StringUtils.isNullOrEmpty(savedUUID = data.getString("HiringPlayerUUID"))) {
				hiringPlayerUUID = UUID.fromString(savedUUID);
			}
			isActive = data.getBoolean("IsActive");
			alignmentRequiredToCommand = data.hasKey("AlignmentRequired") ? data.getInteger("AlignmentRequired") : data.getFloat("AlignReqF");
			if (data.hasKey("PledgeType")) {
				byte pledgeID = data.getByte("PledgeType");
				pledgeType = GOTUnitTradeEntry.PledgeType.forID(pledgeID);
			}
			canMove = data.getBoolean("CanMove");
			if (data.hasKey("TeleportAutomatically")) {
				teleportAutomatically = data.getBoolean("TeleportAutomatically");
				mobKills = data.getInteger("MobKills");
				setGuardMode(data.getBoolean("GuardMode"));
				setGuardRange(data.getInteger("GuardRange"));
			}
			setTask(Task.forID(data.getInteger("Task")));
			if (data.hasKey("Xp")) {
				xp = data.getInteger("Xp");
			}
			if (data.hasKey("XpLvl")) {
				xpLevel = data.getInteger("XpLvl");
			}
			if (data.hasKey("Squadron")) {
				hiredSquadron = data.getString("Squadron");
			}
			if (hiredInventory != null) {
				hiredInventory.readFromNBT(data);
			}
		}
	}

	public void ready() {
		canMove = true;
		sendClientPacket(false);
	}

	public void receiveBasicData(GOTPacketHiredInfo packet) {
		hiringPlayerUUID = packet.getHiringPlayer();
		setTask(packet.getTask());
		setHiredSquadron(packet.getSquadron());
		xpLevel = packet.getXpLvl();
	}

	public void receiveClientPacket(GOTPacketHiredGui packet) {
		isActive = packet.isActive();
		canMove = packet.isCanMove();
		teleportAutomatically = packet.isTeleportAutomatically();
		mobKills = packet.getMobKills();
		xp = packet.getXp();
		alignmentRequiredToCommand = packet.getAlignmentRequired();
		pledgeType = packet.getPledgeType();
		inCombat = packet.isInCombat();
		guardMode = packet.isGuardMode();
		guardRange = packet.getGuardRange();
	}

	public void sendBasicData(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketHiredInfo(theEntity.getEntityId(), hiringPlayerUUID, hiredTask, hiredSquadron, xpLevel);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private void sendBasicDataToAllWatchers() {
		int x = MathHelper.floor_double(theEntity.posX) >> 4;
		int z = MathHelper.floor_double(theEntity.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theEntity.worldObj).getPlayerManager();
		List<EntityPlayer> players = theEntity.worldObj.playerEntities;
		for (EntityPlayer obj : players) {
			if (playermanager.isPlayerWatchingChunk((EntityPlayerMP) obj, x, z)) {
				sendBasicData((EntityPlayerMP) obj);
			}
		}
	}

	public void sendClientPacket(boolean shouldOpenGui) {
		if (theEntity.worldObj.isRemote || getHiringPlayer() == null) {
			return;
		}
		GOTPacketHiredGui packet = new GOTPacketHiredGui(theEntity.getEntityId(), shouldOpenGui);
		packet.setActive(isActive);
		packet.setCanMove(canMove);
		packet.setTeleportAutomatically(teleportAutomatically);
		packet.setMobKills(mobKills);
		packet.setXp(xp);
		packet.setAlignmentRequired(alignmentRequiredToCommand);
		packet.setPledgeType(pledgeType);
		packet.setInCombat(inCombat);
		packet.setGuardMode(guardMode);
		packet.setGuardRange(guardRange);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) getHiringPlayer());
		if (shouldOpenGui) {
			isGuiOpen = true;
		}
	}

	public boolean shouldFollowPlayer() {
		return !guardMode && canMove;
	}

	public static int totalXPForLevel(int lvl) {
		if (lvl <= 1) {
			return 0;
		}
		double d = 3.0 * (lvl - 1) * Math.pow(1.08, lvl - 2);
		return MathHelper.floor_double(d);
	}

	public void tryTeleportToHiringPlayer(boolean failsafe) {
		World world = theEntity.worldObj;
		if (!world.isRemote) {
			EntityPlayer entityplayer = getHiringPlayer();
			if (isActive && entityplayer != null && theEntity.riddenByEntity == null) {
				int i = MathHelper.floor_double(entityplayer.posX);
				int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
				int k = MathHelper.floor_double(entityplayer.posZ);
				float minDist = 3.0f;
				float maxDist = 6.0f;
				float extraDist = theEntity.width / 2.0f;
				if (theEntity.ridingEntity instanceof EntityLiving) {
					extraDist = Math.max(theEntity.width, theEntity.ridingEntity.width) / 2.0f;
				}
				minDist += extraDist;
				maxDist += extraDist;
				int attempts = 120;
				for (int l = 0; l < attempts; ++l) {
					float yExtra;
					double d2;
					float angle = world.rand.nextFloat() * 3.1415927f * 2.0f;
					float sin = MathHelper.sin(angle);
					float cos = MathHelper.cos(angle);
					float r = MathHelper.randomFloatClamp(world.rand, minDist, maxDist);
					int i1 = MathHelper.floor_double(i + 0.5 + cos * r);
					int k1 = MathHelper.floor_double(k + 0.5 + sin * r);
					double d = i1 + 0.5;
					float halfWidth = theEntity.width / 2.0f;
					int j1 = MathHelper.getRandomIntegerInRange(world.rand, j - 4, j + 4);
					AxisAlignedBB npcBB = AxisAlignedBB.getBoundingBox(d - halfWidth, (double) j1 + (yExtra = -theEntity.yOffset + theEntity.ySize), (d2 = k1 + 0.5) - halfWidth, d + halfWidth, (double) j1 + yExtra + theEntity.height, d2 + halfWidth);
					if (!world.func_147461_a(npcBB).isEmpty() || !world.getBlock(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP)) {
						continue;
					}
					if (theEntity.ridingEntity instanceof EntityLiving) {
						EntityLiving mount = (EntityLiving) theEntity.ridingEntity;
						float mHalfWidth = mount.width / 2.0f;
						float mYExtra = -mount.yOffset + mount.ySize;
						float mHeight = mount.height;
						AxisAlignedBB mountBB = AxisAlignedBB.getBoundingBox(d - mHalfWidth, (double) j1 + mYExtra, d2 - mHalfWidth, d + mHalfWidth, (double) j1 + mYExtra + mHeight, d2 + mHalfWidth);
						if (!world.func_147461_a(mountBB).isEmpty()) {
							continue;
						}
						mount.setLocationAndAngles(d, j1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
						mount.fallDistance = 0.0f;
						mount.getNavigator().clearPathEntity();
						mount.setAttackTarget(null);
						theEntity.fallDistance = 0.0f;
						theEntity.getNavigator().clearPathEntity();
						theEntity.setAttackTarget(null);
						return;
					}
					theEntity.setLocationAndAngles(d, j1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
					theEntity.fallDistance = 0.0f;
					theEntity.getNavigator().clearPathEntity();
					theEntity.setAttackTarget(null);
					return;
				}
				if (failsafe) {
					double d = i + 0.5;
					double d2 = k + 0.5;
					if (world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
						if (theEntity.ridingEntity instanceof EntityLiving) {
							EntityLiving mount = (EntityLiving) theEntity.ridingEntity;
							mount.setLocationAndAngles(d, j, d2, theEntity.rotationYaw, theEntity.rotationPitch);
							mount.fallDistance = 0.0f;
							mount.getNavigator().clearPathEntity();
							mount.setAttackTarget(null);
							theEntity.fallDistance = 0.0f;
							theEntity.getNavigator().clearPathEntity();
							theEntity.setAttackTarget(null);
							return;
						}
						theEntity.setLocationAndAngles(d, j, d2, theEntity.rotationYaw, theEntity.rotationPitch);
						theEntity.fallDistance = 0.0f;
						theEntity.getNavigator().clearPathEntity();
						theEntity.setAttackTarget(null);
					}
				}
			}
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("IsActive", isActive);
		if (hiringPlayerUUID != null) {
			data.setString("HiringPlayerUUID", hiringPlayerUUID.toString());
		}
		data.setFloat("AlignReqF", alignmentRequiredToCommand);
		data.setByte("PledgeType", (byte) pledgeType.getTypeID());
		data.setBoolean("CanMove", canMove);
		data.setBoolean("TeleportAutomatically", teleportAutomatically);
		data.setInteger("MobKills", mobKills);
		data.setBoolean("GuardMode", guardMode);
		data.setInteger("GuardRange", guardRange);
		data.setInteger("Task", hiredTask.ordinal());
		data.setInteger("Xp", xp);
		data.setInteger("XpLvl", xpLevel);
		if (!StringUtils.isNullOrEmpty(hiredSquadron)) {
			data.setString("Squadron", hiredSquadron);
		}
		if (hiredInventory != null) {
			hiredInventory.writeToNBT(data);
		}
		nbt.setTag("HiredNPCInfo", data);
	}

	public GOTUnitTradeEntry.PledgeType getPledgeType() {
		return pledgeType;
	}

	public Task getHiredTask() {
		return hiredTask;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isGuiOpen() {
		return isGuiOpen;
	}

	public void setGuiOpen(boolean guiOpen) {
		isGuiOpen = guiOpen;
	}

	public boolean isTeleportAutomatically() {
		return teleportAutomatically;
	}

	public void setTeleportAutomatically(boolean teleportAutomatically) {
		this.teleportAutomatically = teleportAutomatically;
	}

	public boolean isWasAttackCommanded() {
		return wasAttackCommanded;
	}

	public void setWasAttackCommanded(boolean wasAttackCommanded) {
		this.wasAttackCommanded = wasAttackCommanded;
	}

	public float getAlignmentRequiredToCommand() {
		return alignmentRequiredToCommand;
	}

	public int getMobKills() {
		return mobKills;
	}

	public int getXp() {
		return xp;
	}

	public int getXpLevel() {
		return xpLevel;
	}

	public enum Task {
		WARRIOR, FARMER, PROSTITUTE;

		public static Task forID(int id) {
			for (Task task : values()) {
				if (task.ordinal() != id) {
					continue;
				}
				return task;
			}
			return WARRIOR;
		}
	}
}