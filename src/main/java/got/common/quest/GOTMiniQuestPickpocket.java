package got.common.quest;

import got.GOT;
import got.common.GOTDate;
import got.common.GOTLevelData;
import got.common.GOTLore;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTUnitTradeEntry;
import got.common.faction.GOTReputationValues;
import got.common.item.other.GOTItemCoin;
import got.common.item.other.GOTItemLeatherHat;
import got.common.item.other.GOTItemModifierTemplate;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;

import java.util.*;

public class GOTMiniQuestPickpocket extends GOTMiniQuestCollectBase {
	private final Collection<UUID> pickpocketedEntityIDs = new HashSet<>();

	@SuppressWarnings("unused")
	public GOTMiniQuestPickpocket(GOTPlayerData pd) {
		super(pd);
	}

	private static ItemStack createPickpocketIcon() {
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 0);
		GOTItemLeatherHat.setFeatherColor(hat, 16777215);
		return hat;
	}

	private static boolean isEntityWatching(EntityLiving watcher, EntityLivingBase target) {
		Vec3 look = watcher.getLookVec();
		Vec3 watcherEyes = Vec3.createVectorHelper(watcher.posX, watcher.boundingBox.minY + watcher.getEyeHeight(), watcher.posZ);
		Vec3 targetEyes = Vec3.createVectorHelper(target.posX, target.boundingBox.minY + target.getEyeHeight(), target.posZ);
		Vec3 disp = Vec3.createVectorHelper(targetEyes.xCoord - watcherEyes.xCoord, targetEyes.yCoord - watcherEyes.yCoord, targetEyes.zCoord - watcherEyes.zCoord);
		double dot = disp.normalize().dotProduct(look.normalize());
		return dot >= MathHelper.cos(2.2689280275926285f / 2.0f) && watcher.getEntitySenses().canSee(target);
	}

	private static void spawnAngryFX(EntityLivingBase npc) {
		GOT.proxy.spawnParticle("angry", npc.posX, npc.boundingBox.minY + npc.height * 2.0f, npc.posZ, npc.motionX, Math.max(0.0, npc.motionY), npc.motionZ);
	}

	private static void spawnPickingFX(String particle, double upSpeed, EntityLivingBase npc) {
		Random rand = npc.getRNG();
		int particles = 3 + rand.nextInt(8);
		for (int p = 0; p < particles; ++p) {
			double x = npc.posX;
			double y = npc.boundingBox.minY + npc.height * 0.5f;
			double z = npc.posZ;
			float w = npc.width * 0.1f;
			float ang = rand.nextFloat() * 3.1415927f * 2.0f;
			double hSpeed = MathHelper.getRandomDoubleInRange(rand, 0.05, 0.08);
			double vx = MathHelper.cos(ang) * hSpeed;
			double vz = MathHelper.sin(ang) * hSpeed;
			double vy = MathHelper.getRandomDoubleInRange(rand, 0.1, 0.25) * upSpeed;
			GOT.proxy.spawnParticle(particle, x + MathHelper.cos(ang) * w, y, z + MathHelper.sin(ang) * w, vx, vy, vz);
		}
	}

	@Override
	public void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		GOTAchievement achievement;
		completed = true;
		dateCompleted = GOTDate.AegonCalendar.getCurrentDay();
		Random rand = npc.getRNG();
		List<ItemStack> dropItems = new ArrayList<>();
		int coins = getCoinBonus();
		if (coins != 0) {
			if (shouldRandomiseCoinReward()) {
				coins = Math.round(coins * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
				if (rand.nextInt(12) == 0) {
					coins *= MathHelper.getRandomIntegerInRange(rand, 2, 5);
				}
			}
			coins = Math.max(coins, 1);
			coinsRewarded = coins;
			int coinsRemain = coins;
			for (int l = GOTItemCoin.VALUES.length - 1; l >= 0; --l) {
				int coinValue = GOTItemCoin.VALUES[l];
				if (coinsRemain < coinValue) {
					continue;
				}
				int numCoins = coinsRemain / coinValue;
				coinsRemain -= numCoins * coinValue;
				while (numCoins > 64) {
					numCoins -= 64;
					dropItems.add(new ItemStack(GOTItems.coin, 64, l));
				}
				dropItems.add(new ItemStack(GOTItems.coin, numCoins, l));
			}
		}
		if (!rewardItemTable.isEmpty()) {
			ItemStack item = rewardItemTable.get(rand.nextInt(rewardItemTable.size()));
			dropItems.add(item.copy());
			itemsRewarded.add(item.copy());
		}
		if (canRewardVariousExtraItems()) {
			GOTLore lore;
			if (rand.nextInt(10) == 0 && questGroup != null && !questGroup.getLoreCategories().isEmpty() && (lore = GOTLore.getMultiRandomLore(questGroup.getLoreCategories(), rand, true)) != null) {
				ItemStack loreBook = lore.createLoreBook(rand);
				dropItems.add(loreBook.copy());
				itemsRewarded.add(loreBook.copy());
			}
			if (rand.nextInt(15) == 0) {
				ItemStack modItem = GOTItemModifierTemplate.getRandomCommonTemplate(rand);
				dropItems.add(modItem.copy());
				itemsRewarded.add(modItem.copy());
			}
		}
		if (!dropItems.isEmpty()) {
			boolean givePouch = canRewardVariousExtraItems() && rand.nextInt(10) == 0;
			if (givePouch) {
				ItemStack pouch = npc.createNPCPouchDrop();
				GOTEntityNPC.fillPouchFromListAndRetainUnfilled(pouch, dropItems);
				npc.entityDropItem(pouch, 0.0f);
				ItemStack pouchCopy = pouch.copy();
				pouchCopy.setTagCompound(null);
				itemsRewarded.add(pouchCopy);
			}
			npc.dropItemList(dropItems, true);
		}
		if (willHire) {
			GOTUnitTradeEntry tradeEntry = new GOTUnitTradeEntry(npc.getClass(), 0, hiringReputation);
			tradeEntry.setTask(GOTHireableInfo.Task.WARRIOR);
			npc.getHireableInfo().hireUnit(entityplayer, false, entityFaction, tradeEntry, null, npc.ridingEntity);
			wasHired = true;
		}
		if (isLegendary) {
			npc.getHireableInfo().setActive(true);
		}
		playerData.updateMiniQuest(this);
		playerData.completeMiniQuest(this);
		sendCompletedSpeech(entityplayer, npc);
		if (questGroup != null && (achievement = questGroup.getAchievement()) != null) {
			playerData.addAchievement(achievement);
		}
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getReputationBonus() * 5.0f);
	}

	@Override
	public String getObjectiveInSpeech() {
		return getCollectTarget() + " ";
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return getCollectTarget() - amountGiven + " ";
	}

	@Override
	public ItemStack getQuestIcon() {
		return createPickpocketIcon();
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.pickpocket", getCollectTarget());
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("got.miniquest.pickpocket.progress", amountGiven, getCollectTarget());
	}

	@Override
	public void handleEvent(GOTMiniQuestEvent event) {
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		return GOTPickpoketableHelper.isPickpocketed(itemstack) && entityUUID.equals(GOTPickpoketableHelper.getWanterID(itemstack));
	}

	@Override
	public boolean onInteractOther(EntityPlayer entityplayer, GOTEntityNPC npc) {
		if (entityplayer.isSneaking() && entityplayer.getHeldItem() == null && GOTEntityUtils.canBeRobbed(npc)) {
			UUID id = npc.getPersistentID();
			if (!pickpocketedEntityIDs.contains(id)) {
				boolean noticed;
				if (npc.getAttackTarget() != null) {
					entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocalFormatted("got.chat.pickpocket.inCombat")));
					return true;
				}
				if (isEntityWatching(npc, entityplayer)) {
					entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + StatCollector.translateToLocalFormatted("got.chat.pickpocket.watched")));
					return true;
				}
				Random rand = npc.getRNG();
				boolean success = rand.nextInt(3) == 0;
				boolean anyoneNoticed = noticed = rand.nextInt(success ? 3 : 4) == 0;
				if (success) {
					ItemStack picked = GOTChestContents.TREASURE.getOneItem(rand, true);
					GOTPickpoketableHelper.setPickpocketData(picked, npc.getNPCName(), entityNameFull, entityUUID);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, picked);
					entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted("got.chat.pickpocket.success", picked.stackSize, picked.getDisplayName(), npc.getNPCName())));
					npc.playSound("got:event.trade", 0.5f, 1.0f + (rand.nextFloat() - rand.nextFloat()) * 0.1f);
					npc.playSound("mob.horse.leather", 0.5f, 1.0f);
					spawnPickingFX("pickpocket", 1.0, npc);
					pickpocketedEntityIDs.add(id);
					playerData.updateMiniQuest(this);
					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.pickpocket);
				} else {
					entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + StatCollector.translateToLocalFormatted("got.chat.pickpocket.missed", npc.getNPCName())));
					npc.playSound(Blocks.wool.stepSound.getBreakSound(), 0.5f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
					spawnPickingFX("pickpocketFail", 0.4, npc);
				}
				if (noticed) {
					entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocalFormatted("got.chat.pickpocket.noticed", npc.getNPCName())));
					npc.setAttackTarget(entityplayer, true);
					npc.setRevengeTarget(entityplayer);
					spawnAngryFX(npc);
				}
				if (!noticed || rand.nextFloat() < 0.5f) {
					List<GOTEntityNPC> nearbyFriends = npc.worldObj.selectEntitiesWithinAABB(GOTEntityNPC.class, npc.boundingBox.expand(16.0, 16.0, 16.0), new EntitySelectorImpl(npc, entityplayer));
					for (GOTEntityNPC otherNPC : nearbyFriends) {
						if (otherNPC == npc) {
							continue;
						}
						boolean civilian = otherNPC.isCivilian();
						double maxRange = civilian ? 8.0 : 16.0;
						double dist = otherNPC.getDistanceToEntity(npc);
						if (dist > maxRange || otherNPC.getAttackTarget() != null || !isEntityWatching(otherNPC, entityplayer)) {
							continue;
						}
						float distFactor = 1.0f - (float) ((dist - 4.0) / (maxRange - 4.0));
						float chance = 0.5f + distFactor * 0.5f;
						if (civilian) {
							chance *= 0.25f;
						}
						if (rand.nextFloat() >= chance) {
							continue;
						}
						entityplayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + StatCollector.translateToLocalFormatted("got.chat.pickpocket.otherNoticed", otherNPC.getEntityClassName())));
						otherNPC.setAttackTarget(entityplayer, true);
						otherNPC.setRevengeTarget(entityplayer);
						spawnAngryFX(otherNPC);
						anyoneNoticed = true;
					}
				}
				if (anyoneNoticed) {
					GOTLevelData.getData(entityplayer).addReputation(entityplayer, GOTReputationValues.PICKPOCKET_PENALTY, npc.getFaction(), npc);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
	}

	@Override
	public void onPlayerTick() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		pickpocketedEntityIDs.clear();
		NBTTagList ids = nbt.getTagList("PickpocketedIDs", 8);
		for (int i = 0; i < ids.tagCount(); ++i) {
			UUID id = UUID.fromString(ids.getStringTagAt(i));
			pickpocketedEntityIDs.add(id);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList ids = new NBTTagList();
		for (UUID id : pickpocketedEntityIDs) {
			ids.appendTag(new NBTTagString(id.toString()));
		}
		nbt.setTag("PickpocketedIDs", ids);
	}

	public static class QFPickpocket<Q extends GOTMiniQuestPickpocket> extends GOTMiniQuest.QuestFactoryBase<Q> {
		protected int minTarget;
		protected int maxTarget;

		public QFPickpocket(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			Q quest = super.createQuest(npc, rand);
			quest.setCollectTarget(MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget));
			return quest;
		}

		@Override
		public Class<Q> getQuestClass() {
			return (Class<Q>) GOTMiniQuestPickpocket.class;
		}

		public QFPickpocket<Q> setPickpocketNumber(int min, int max) {
			minTarget = min;
			maxTarget = max;
			return this;
		}
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTEntityNPC npc;
		private final EntityPlayer entityplayer;

		private EntitySelectorImpl(GOTEntityNPC npc, EntityPlayer entityplayer) {
			this.npc = npc;
			this.entityplayer = entityplayer;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			GOTEntityNPC otherNPC = (GOTEntityNPC) entity;
			return otherNPC.isEntityAlive() && otherNPC.getFaction().isGoodRelation(npc.getFaction()) && otherNPC.getHireableInfo().getHiringPlayer() != entityplayer;
		}
	}
}