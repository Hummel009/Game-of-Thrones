package got.common.entity.other.info;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketFamilyInfo;
import got.common.network.GOTPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.UUID;

public class GOTFamilyInfo {
	private final GOTEntityNPC theEntity;

	private Class<? extends GOTEntityNPC> marriageEntityClass;

	private UUID spouseUniqueID;
	private UUID ringGivingPlayer;
	private UUID maleParentID;
	private UUID femaleParentID;

	private String name;

	private boolean male;
	private boolean doneFirstUpdate;
	private boolean resendData = true;

	private int children;
	private int maxChildren;
	private int age;
	private int drunkTime;
	private int timeUntilDrunkSpeech;

	public GOTFamilyInfo(GOTEntityNPC npc) {
		theEntity = npc;
	}

	public boolean canMarryNPC(GOTEntityNPC npc) {
		if (npc.getClass() != theEntity.getClass() || npc.getFamilyInfo().spouseUniqueID != null || npc.getFamilyInfo().age != 0 || npc.getEquipmentInSlot(4) != null) {
			return false;
		}
		boolean lgbt = GOTConfig.lgbt || npc.getFamilyInfo().male == male;
		if (lgbt || npc == theEntity || maleParentID != null && maleParentID == npc.getFamilyInfo().maleParentID || femaleParentID != null && femaleParentID == npc.getFamilyInfo().femaleParentID) {
			return false;
		}
		ItemStack heldItem = npc.getEquipmentInSlot(0);
		return heldItem != null && heldItem.getItem() == GOTItems.goldRing;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int i) {
		age = i;
		markDirty();
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
		markDirty();
	}

	public GOTEntityNPC getParentToFollow() {
		UUID parentToFollowID = male ? maleParentID : femaleParentID;
		List<? extends Entity> list = theEntity.worldObj.getEntitiesWithinAABB(theEntity.getClass(), theEntity.boundingBox.expand(16.0, 8.0, 16.0));
		for (Entity entity : list) {
			if (!(entity instanceof GOTEntityNPC) || entity == theEntity || !entity.getUniqueID().equals(parentToFollowID)) {
				continue;
			}
			return (GOTEntityNPC) entity;
		}
		return null;
	}

	public int getRandomMaxChildren() {
		return 1 + theEntity.getRNG().nextInt(1);
	}

	public EntityPlayer getRingGivingPlayer() {
		if (ringGivingPlayer != null) {
			List<EntityPlayer> players = theEntity.worldObj.playerEntities;
			for (EntityPlayer entityplayer : players) {
				if (!entityplayer.getUniqueID().equals(ringGivingPlayer)) {
					continue;
				}
				return entityplayer;
			}
		}
		return null;
	}

	public GOTEntityNPC getSpouse() {
		if (spouseUniqueID == null) {
			return null;
		}
		List<? extends Entity> list = theEntity.worldObj.getEntitiesWithinAABB(theEntity.getClass(), theEntity.boundingBox.expand(16.0, 8.0, 16.0));
		for (Entity entity : list) {
			if (!(entity instanceof GOTEntityNPC) || entity == theEntity || !entity.getUniqueID().equals(spouseUniqueID)) {
				continue;
			}
			GOTEntityNPC npc = (GOTEntityNPC) entity;
			if (!theEntity.getUniqueID().equals(npc.getFamilyInfo().spouseUniqueID)) {
				continue;
			}
			return npc;
		}
		return null;
	}

	public boolean interact(EntityPlayer entityplayer) {
		if (theEntity.getHireableInfo().isActive()) {
			return false;
		}
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == GOTItems.goldRing && GOTLevelData.getData(entityplayer).getAlignment(theEntity.getFaction()) >= 50.0f && theEntity.getClass() == marriageEntityClass && age == 0 && theEntity.getEquipmentInSlot(0) == null && theEntity.getEquipmentInSlot(4) == null && spouseUniqueID == null) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}
			}
			if (!theEntity.worldObj.isRemote) {
				theEntity.setCurrentItemOrArmor(0, new ItemStack(GOTItems.goldRing));
				ringGivingPlayer = entityplayer.getUniqueID();
			}
			theEntity.setNPCPersistent(true);
			return true;
		}
		return false;
	}

	public boolean isChild() {
		return age < 0;
	}

	public boolean isDrunk() {
		return drunkTime > 0;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean flag) {
		male = flag;
		markDirty();
	}

	private void markDirty() {
		if (!theEntity.worldObj.isRemote) {
			if (theEntity.ticksExisted > 0) {
				resendData = true;
			} else {
				sendDataToAllWatchers();
			}
		}
	}

	public void onUpdate() {
		if (!theEntity.worldObj.isRemote) {
			if (!doneFirstUpdate) {
				doneFirstUpdate = true;
			}
			if (resendData) {
				sendDataToAllWatchers();
				resendData = false;
			}
			if (age < 0) {
				setAge(age + 1);
			} else if (age > 0) {
				setAge(age - 1);
			}
			if (drunkTime > 0) {
				setDrunkTime(drunkTime - 1);
			}
			if (isDrunk()) {
				theEntity.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
				if (timeUntilDrunkSpeech > 0) {
					--timeUntilDrunkSpeech;
				}
				if (theEntity.isEntityAlive() && theEntity.getAttackTarget() == null && timeUntilDrunkSpeech == 0) {
					double range = 12.0;
					List<EntityPlayer> players = theEntity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theEntity.boundingBox.expand(range, range, range));
					for (EntityPlayer entityplayer : players) {
						String speechBank;
						if (!entityplayer.isEntityAlive() || entityplayer.capabilities.isCreativeMode || (speechBank = theEntity.getSpeechBank(theEntity, entityplayer)) == null || theEntity.getRNG().nextInt(3) != 0) {
							continue;
						}
						theEntity.sendSpeechBank(entityplayer, speechBank);
					}
					timeUntilDrunkSpeech = 20 * MathHelper.getRandomIntegerInRange(theEntity.getRNG(), 5, 20);
				}
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		setAge(nbt.getInteger("NPCAge"));
		if (nbt.hasKey("NPCMale")) {
			setMale(nbt.getBoolean("NPCMale"));
		}
		if (nbt.hasKey("NPCName")) {
			setName(nbt.getString("NPCName"));
		}
		setDrunkTime(nbt.getInteger("NPCDrunkTime"));
		if (nbt.hasKey("SpouseUUIDMost") && nbt.hasKey("SpouseUUIDLeast")) {
			spouseUniqueID = new UUID(nbt.getLong("SpouseUUIDMost"), nbt.getLong("SpouseUUIDLeast"));
		}
		children = nbt.getInteger("Children");
		maxChildren = nbt.getInteger("MaxChildren");
		if (nbt.hasKey("MaleParentUUIDMost") && nbt.hasKey("MaleParentUUIDLeast")) {
			maleParentID = new UUID(nbt.getLong("MaleParentUUIDMost"), nbt.getLong("MaleParentUUIDLeast"));
		}
		if (nbt.hasKey("FemaleParentUUIDMost") && nbt.hasKey("FemaleParentUUIDLeast")) {
			femaleParentID = new UUID(nbt.getLong("FemaleParentUUIDMost"), nbt.getLong("FemaleParentUUIDLeast"));
		}
		if (nbt.hasKey("RingGivingPlayer")) {
			ringGivingPlayer = new UUID(nbt.getLong("RingGivingPlayerUUIDMost"), nbt.getLong("RingGivingPlayerUUIDLeast"));
		}
	}

	public void receiveData(GOTPacketFamilyInfo packet) {
		setAge(packet.getAge());
		setMale(packet.isMale());
		setName(packet.getName());
		if (packet.isDrunk()) {
			setDrunkTime(100000);
		} else {
			setDrunkTime(0);
		}
	}

	public void sendData(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketFamilyInfo(theEntity.getEntityId(), age, male, name, isDrunk());
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private void sendDataToAllWatchers() {
		int x = MathHelper.floor_double(theEntity.posX) >> 4;
		int z = MathHelper.floor_double(theEntity.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theEntity.worldObj).getPlayerManager();
		List<EntityPlayerMP> players = theEntity.worldObj.playerEntities;
		for (EntityPlayerMP entityplayer : players) {
			if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
				continue;
			}
			sendData(entityplayer);
		}
	}

	public void setChild() {
		setAge(-48000);
	}

	public void setMaxBreedingDelay() {
		float f = 24000;
		setAge((int) (f * (0.5f + theEntity.getRNG().nextFloat() * 0.5f)));
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("NPCAge", age);
		nbt.setBoolean("NPCMale", male);
		if (name != null) {
			nbt.setString("NPCName", name);
		}
		nbt.setInteger("NPCDrunkTime", drunkTime);
		if (spouseUniqueID != null) {
			nbt.setLong("SpouseUUIDMost", spouseUniqueID.getMostSignificantBits());
			nbt.setLong("SpouseUUIDLeast", spouseUniqueID.getLeastSignificantBits());
		}
		nbt.setInteger("Children", children);
		nbt.setInteger("MaxChildren", maxChildren);
		if (maleParentID != null) {
			nbt.setLong("MaleParentUUIDMost", maleParentID.getMostSignificantBits());
			nbt.setLong("MaleParentUUIDLeast", maleParentID.getLeastSignificantBits());
		}
		if (femaleParentID != null) {
			nbt.setLong("FemaleParentUUIDMost", femaleParentID.getMostSignificantBits());
			nbt.setLong("FemaleParentUUIDLeast", femaleParentID.getLeastSignificantBits());
		}
		if (ringGivingPlayer != null) {
			nbt.setLong("RingGivingPlayerUUIDMost", ringGivingPlayer.getMostSignificantBits());
			nbt.setLong("RingGivingPlayerUUIDLeast", ringGivingPlayer.getLeastSignificantBits());
		}
	}

	public void setDrunkTime(int i) {
		boolean prevDrunk = isDrunk();
		drunkTime = i;
		if (isDrunk() != prevDrunk) {
			markDirty();
		}
	}

	public Class<? extends Entity> getMarriageEntityClass() {
		return marriageEntityClass;
	}

	public void setMarriageEntityClass(Class<? extends GOTEntityNPC> marriageEntityClass) {
		this.marriageEntityClass = marriageEntityClass;
	}

	public UUID getSpouseUniqueID() {
		return spouseUniqueID;
	}

	public void setSpouseUniqueID(UUID spouseUniqueID) {
		this.spouseUniqueID = spouseUniqueID;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}

	public void setMaleParentID(UUID maleParentID) {
		this.maleParentID = maleParentID;
	}

	public void setFemaleParentID(UUID femaleParentID) {
		this.femaleParentID = femaleParentID;
	}
}