package got.common.entity.other;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import got.common.inventory.GOTInventoryNPC;
import got.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.management.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTHiredNPCInfo {
	public static int GUARD_RANGE_DEFAULT = 8;
	public static int GUARD_RANGE_MAX = 64;
	public static int GUARD_RANGE_MIN = 1;
	public static int XP_COLOR = 16733440;
	public GOTEntityNPC theEntity;
	public GOTInventoryNPC hiredInventory;
	public GOTUnitTradeEntry.PledgeType pledgeType = GOTUnitTradeEntry.PledgeType.NONE;
	public String hiredSquadron;
	public Task hiredTask = Task.WARRIOR;
	public UUID hiringPlayerUUID;
	public boolean canMove = true;
	public boolean doneFirstUpdate = false;
	public boolean guardMode;
	public boolean inCombat;
	public boolean isActive;
	public boolean isGuiOpen;
	public boolean prevInCombat;
	public boolean resendBasicData = true;
	public boolean targetFromCommandSword;
	public boolean teleportAutomatically = true;
	public boolean wasAttackCommanded = false;
	public float alignmentRequiredToCommand;
	public int guardRange = GUARD_RANGE_DEFAULT;
	public int mobKills;
	public int xp = 0;
	public int xpLevel = 1;

	public GOTHiredNPCInfo(GOTEntityNPC npc) {
		theEntity = npc;
	}

	public void addExperience(int i) {
		xp += i;
		while (xp >= totalXPForLevel(xpLevel + 1)) {
			++xpLevel;
			markDirty();
			onLevelUp();
		}
		sendClientPacket(false);
	}

	public void commandSwordAttack(EntityLivingBase target) {
		if (target != null && GOT.canNPCAttackEntity(theEntity, target, true)) {
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

	public GOTInventoryNPC getHiredInventory() {
		return hiredInventory;
	}

	public EntityPlayer getHiringPlayer() {
		if (hiringPlayerUUID == null) {
			return null;
		}
		return theEntity.worldObj.func_152378_a(hiringPlayerUUID);
	}

	public UUID getHiringPlayerUUID() {
		return hiringPlayerUUID;
	}

	public boolean getObeyCommandSword() {
		if (hiredTask != Task.WARRIOR) {
			return false;
		}
		return !guardMode;
	}

	public boolean getObeyHornHaltReady() {
		if (hiredTask != Task.WARRIOR) {
			return false;
		}
		return !guardMode;
	}

	public boolean getObeyHornSummon() {
		if (hiredTask != Task.WARRIOR) {
			return false;
		}
		return !guardMode;
	}

	public float getProgressToNextLevel() {
		int cap = totalXPForLevel(xpLevel + 1);
		int start = totalXPForLevel(xpLevel);
		return (float) (xp - start) / (float) (cap - start);
	}

	public String getSquadron() {
		return hiredSquadron;
	}

	public String getStatusString() {
		String status = "";
		if (hiredTask == Task.WARRIOR) {
			status = inCombat ? StatCollector.translateToLocal("got.hiredNPC.status.combat") : (isHalted() ? StatCollector.translateToLocal("got.hiredNPC.status.halted") : (guardMode ? StatCollector.translateToLocal("got.hiredNPC.status.guard") : StatCollector.translateToLocal("got.hiredNPC.status.ready")));
		} else if (hiredTask == Task.FARMER) {
			status = guardMode ? StatCollector.translateToLocal("got.hiredNPC.status.farming") : StatCollector.translateToLocal("got.hiredNPC.status.following");
		}
		return StatCollector.translateToLocalFormatted("got.hiredNPC.status", status);
	}

	public Task getTask() {
		return hiredTask;
	}

	public void halt() {
		canMove = false;
		theEntity.setAttackTarget(null);
		sendClientPacket(false);
	}

	public boolean hasHiringRequirements() {
		return theEntity.getHiringFaction().isPlayableAlignmentFaction() && alignmentRequiredToCommand >= 0.0f;
	}

	public void hireUnit(EntityPlayer entityplayer, boolean setLocation, GOTFaction hiringFaction, GOTUnitTradeEntry tradeEntry, String squadron, Entity mount) {
		float alignment = tradeEntry.alignmentRequired;
		GOTUnitTradeEntry.PledgeType pledge = tradeEntry.getPledgeType();
		Task task = tradeEntry.task;
		if (setLocation) {
			theEntity.setLocationAndAngles(entityplayer.posX, entityplayer.boundingBox.minY, entityplayer.posZ, entityplayer.rotationYaw + 180.0f, 0.0f);
		}
		isActive = true;
		alignmentRequiredToCommand = alignment;
		pledgeType = pledge;
		setHiringPlayer(entityplayer);
		setTask(task);
		setSquadron(squadron);
		if (hiringFaction != null && hiringFaction.isPlayableAlignmentFaction()) {
			GOTLevelData.getData(entityplayer).getFactionData(hiringFaction).addHire();
		}
		if (mount != null) {
			mount.setLocationAndAngles(theEntity.posX, theEntity.boundingBox.minY, theEntity.posZ, theEntity.rotationYaw, 0.0f);
			if (mount instanceof GOTEntityNPC) {
				GOTEntityNPC hiredMountNPC = (GOTEntityNPC) mount;
				hiredMountNPC.hiredNPCInfo.hireUnit(entityplayer, setLocation, hiringFaction, tradeEntry, squadron, null);
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

	public boolean isHalted() {
		return !guardMode && !canMove;
	}

	public void markDirty() {
		if (!theEntity.worldObj.isRemote) {
			if (theEntity.ticksExisted > 0) {
				resendBasicData = true;
			} else {
				sendBasicDataToAllWatchers();
			}
		}
	}

	public void onDeath(DamageSource damagesource) {
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
			if (getTask() == Task.WARRIOR) {
				boolean wasEnemy = false;
				int addXP = 0;
				GOTFaction unitFaction = theEntity.getHiringFaction();
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

	public void onLevelUp() {
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
			int[] colors = { 16733440, theEntity.getFaction().getFactionColor() };
			explosionData.setIntArray("Colors", colors);
			boolean effectType = bigLvlUp;
			explosionData.setByte("Type", (byte) (effectType ? 1 : 0));
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
				GOTFaction fac = theEntity.getHiringFaction();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				boolean canCommand = true;
				if (pd.getAlignment(fac) < alignmentRequiredToCommand) {
					canCommand = false;
				}
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
			if (getTask() == Task.WARRIOR && !inCombat && shouldFollowPlayer() && theEntity.getRNG().nextInt(4000) == 0) {
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
			alignmentRequiredToCommand = data.hasKey("AlignmentRequired") ? (float) data.getInteger("AlignmentRequired") : data.getFloat("AlignReqF");
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
		hiringPlayerUUID = packet.hiringPlayer;
		setTask(packet.task);
		setSquadron(packet.squadron);
		xpLevel = packet.xpLvl;
	}

	public void receiveClientPacket(GOTPacketHiredGui packet) {
		isActive = packet.isActive;
		canMove = packet.canMove;
		teleportAutomatically = packet.teleportAutomatically;
		mobKills = packet.mobKills;
		xp = packet.xp;
		alignmentRequiredToCommand = packet.alignmentRequired;
		pledgeType = packet.pledgeType;
		inCombat = packet.inCombat;
		guardMode = packet.guardMode;
		guardRange = packet.guardRange;
	}

	public void sendBasicData(EntityPlayerMP entityplayer) {
		GOTPacketHiredInfo packet = new GOTPacketHiredInfo(theEntity.getEntityId(), hiringPlayerUUID, hiredTask, getSquadron(), xpLevel);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendBasicDataToAllWatchers() {
		int x = MathHelper.floor_double(theEntity.posX) >> 4;
		int z = MathHelper.floor_double(theEntity.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theEntity.worldObj).getPlayerManager();
		List players = theEntity.worldObj.playerEntities;
		for (Object obj : players) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
				continue;
			}
			sendBasicData(entityplayer);
		}
	}

	public void sendClientPacket(boolean shouldOpenGui) {
		if (theEntity.worldObj.isRemote || getHiringPlayer() == null) {
			return;
		}
		GOTPacketHiredGui packet = new GOTPacketHiredGui(theEntity.getEntityId(), shouldOpenGui);
		packet.isActive = isActive;
		packet.canMove = canMove;
		packet.teleportAutomatically = teleportAutomatically;
		packet.mobKills = mobKills;
		packet.xp = xp;
		packet.alignmentRequired = alignmentRequiredToCommand;
		packet.pledgeType = pledgeType;
		packet.inCombat = inCombat;
		packet.guardMode = guardMode;
		packet.guardRange = guardRange;
		GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) getHiringPlayer());
		if (shouldOpenGui) {
			isGuiOpen = true;
		}
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

	public void setGuardRange(int range) {
		guardRange = MathHelper.clamp_int(range, GUARD_RANGE_MIN, GUARD_RANGE_MAX);
		if (guardMode) {
			int i = MathHelper.floor_double(theEntity.posX);
			int j = MathHelper.floor_double(theEntity.posY);
			int k = MathHelper.floor_double(theEntity.posZ);
			theEntity.setHomeArea(i, j, k, guardRange);
		}
	}

	public void setHiringPlayer(EntityPlayer entityplayer) {
		hiringPlayerUUID = entityplayer == null ? null : entityplayer.getUniqueID();
		markDirty();
	}

	public void setSquadron(String s) {
		hiredSquadron = s;
		markDirty();
	}

	public void setTask(Task t) {
		if (t != hiredTask) {
			hiredTask = t;
			markDirty();
		}
		if (hiredTask == Task.FARMER) {
			hiredInventory = new GOTInventoryNPC("HiredInventory", theEntity, 4);
		}
	}

	public boolean shouldFollowPlayer() {
		return !guardMode && canMove;
	}

	public int totalXPForLevel(int lvl) {
		if (lvl <= 1) {
			return 0;
		}
		double d = 3.0 * (lvl - 1) * Math.pow(1.08, lvl - 2);
		return MathHelper.floor_double(d);
	}

	public boolean tryTeleportToHiringPlayer(boolean failsafe) {
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
					double d1 = j1;
					AxisAlignedBB npcBB = AxisAlignedBB.getBoundingBox(d - halfWidth, d1 + (yExtra = -theEntity.yOffset + theEntity.ySize), (d2 = k1 + 0.5) - halfWidth, d + halfWidth, d1 + yExtra + (theEntity.height), d2 + halfWidth);
					if (!world.func_147461_a(npcBB).isEmpty() || !world.getBlock(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP)) {
						continue;
					}
					if (theEntity.ridingEntity instanceof EntityLiving) {
						EntityLiving mount = (EntityLiving) theEntity.ridingEntity;
						float mHalfWidth = mount.width / 2.0f;
						float mYExtra = -mount.yOffset + mount.ySize;
						float mHeight = mount.height;
						AxisAlignedBB mountBB = AxisAlignedBB.getBoundingBox(d - mHalfWidth, d1 + mYExtra, d2 - mHalfWidth, d + mHalfWidth, d1 + mYExtra + mHeight, d2 + mHalfWidth);
						if (!world.func_147461_a(mountBB).isEmpty()) {
							continue;
						}
						mount.setLocationAndAngles(d, d1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
						mount.fallDistance = 0.0f;
						mount.getNavigator().clearPathEntity();
						mount.setAttackTarget(null);
						theEntity.fallDistance = 0.0f;
						theEntity.getNavigator().clearPathEntity();
						theEntity.setAttackTarget(null);
						return true;
					}
					theEntity.setLocationAndAngles(d, d1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
					theEntity.fallDistance = 0.0f;
					theEntity.getNavigator().clearPathEntity();
					theEntity.setAttackTarget(null);
					return true;
				}
				if (failsafe) {
					double d = i + 0.5;
					double d1 = j;
					double d2 = k + 0.5;
					if (world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
						if (theEntity.ridingEntity instanceof EntityLiving) {
							EntityLiving mount = (EntityLiving) theEntity.ridingEntity;
							mount.setLocationAndAngles(d, d1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
							mount.fallDistance = 0.0f;
							mount.getNavigator().clearPathEntity();
							mount.setAttackTarget(null);
							theEntity.fallDistance = 0.0f;
							theEntity.getNavigator().clearPathEntity();
							theEntity.setAttackTarget(null);
							return true;
						}
						theEntity.setLocationAndAngles(d, d1, d2, theEntity.rotationYaw, theEntity.rotationPitch);
						theEntity.fallDistance = 0.0f;
						theEntity.getNavigator().clearPathEntity();
						theEntity.setAttackTarget(null);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("IsActive", isActive);
		if (hiringPlayerUUID != null) {
			data.setString("HiringPlayerUUID", hiringPlayerUUID.toString());
		}
		data.setFloat("AlignReqF", alignmentRequiredToCommand);
		data.setByte("PledgeType", (byte) pledgeType.typeID);
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

	public enum Task {
		WARRIOR(true), FARMER(false), PROSTITUTE(false);

		public boolean displayXpLevel;

		Task(boolean displayLvl) {
			displayXpLevel = displayLvl;
		}

		public static Task forID(int id) {
			for (Task task : Task.values()) {
				if (task.ordinal() != id) {
					continue;
				}
				return task;
			}
			return WARRIOR;
		}
	}

}
