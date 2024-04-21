package got.common.entity.westeros.hillmen;

import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.database.GOTNames;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestFactory;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.westeros.GOTBiomeArrynMountainsFoothills;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTEntityHillman extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityHillman(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, getHillmanAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.WILD, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.WILD_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public GOTMiniQuest createMiniQuest() {
		return GOTMiniQuestFactory.HILLMEN.createQuest(this);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropHillmanItems(flag, i);
	}

	protected void dropHillmanItems(boolean flag, int i) {
		if (rand.nextInt(5) == 0) {
			dropChestContents(GOTChestContents.HILLMEN, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k);
		if (biome instanceof GOTBiomeArrynMountainsFoothills) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return GOTMiniQuestFactory.HILLMEN;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == biome.topBlock;
		}
		return false;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.HILL_TRIBES;
	}

	protected EntityAIBase getHillmanAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hireableInfo.getHiringPlayer() == entityplayer) {
				return "standard/wild/hired_soldier";
			}
			return "standard/wild/usual_friendly";
		}
		return "standard/wild/usual_hostile";
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(9);
		switch (i) {
			case 0:
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.club));
				break;
			case 2:
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.trident));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.wooden_sword));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_sword));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_axe));
				break;
			case 7:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_hoe));
				break;
			case 8:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.stoneSpear));
				break;
			default:
				break;
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return entityData;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWildName(rand, familyInfo.isMale()));
	}
}