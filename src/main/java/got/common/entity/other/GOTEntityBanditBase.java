package got.common.entity.other;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIBanditFlee;
import got.common.entity.ai.GOTEntityAIBanditSteal;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBandit;
import got.common.faction.GOTFaction;
import got.common.inventory.GOTInventoryNPC;
import got.common.item.other.GOTItemMug;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class GOTEntityBanditBase extends GOTEntityHumanBase implements GOTBiome.ImmuneToHeat, GOTBiome.ImmuneToFrost {
	private static final int MAX_THEFTS = 3;

	private final GOTInventoryNPC banditInventory = new GOTInventoryNPC("BanditInventory", this, MAX_THEFTS);

	protected GOTEntityBanditBase(World world) {
		super(world);
		tasks.addTask(2, new GOTEntityAIBanditSteal(this, 1.2));
		tasks.addTask(3, new GOTEntityAIBanditFlee(this, 1.0));
		addTargetTasks(true, GOTEntityAINearestAttackableTargetBandit.class);
	}

	public static boolean canStealFromPlayerInv(EntityPlayer entityplayer) {
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			if (slot == entityplayer.inventory.currentItem || entityplayer.inventory.getStackInSlot(slot) == null) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HOSTILE;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		int coins = 10 + rand.nextInt(10) + rand.nextInt((i + 1) * 10);
		for (int l = 0; l < coins; ++l) {
			dropItem(GOTItems.coin, 1);
		}
		if (rand.nextInt(5) == 0) {
			entityDropItem(GOTItemMug.Vessel.SKULL.getEmptyVessel(), 0.0f);
		}
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return "special/bandit";
	}

	@Override
	public int getTotalArmorValue() {
		return 10;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		banditInventory.readFromNBT(nbt);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		banditInventory.writeToNBT(nbt);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
	}

	public GOTInventoryNPC getBanditInventory() {
		return banditInventory;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && !banditInventory.isEmpty()) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killThievingBandit);
		}
		if (!worldObj.isRemote) {
			banditInventory.dropAllItems();
		}
	}
}