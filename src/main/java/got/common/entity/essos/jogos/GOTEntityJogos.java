package got.common.entity.essos.jogos;

import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTNames;
import got.common.database.GOTRegistry;
import got.common.entity.ai.*;
import got.common.entity.animal.GOTEntityZebra;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTNPCMount;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestFactory;
import got.common.quest.IPickpocketable;
import got.common.world.biome.essos.GOTBiomeJogosNhai;
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

public class GOTEntityJogos extends GOTEntityHumanBase implements IPickpocketable {
	public GOTEntityJogos(World world) {
		super(world);
		canBeMarried = true;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, createJogosAttackAI());
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
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
		getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
	}

	public EntityAIBase createJogosAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public GOTMiniQuest createMiniQuest() {
		return GOTMiniQuestFactory.JOGOS.createQuest(this);
	}

	@Override
	public GOTNPCMount createMountToRide() {
		return new GOTEntityZebra(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropJogosItems(flag, i);
	}

	public void dropJogosItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.JOGOS, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiomeJogosNhai) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return GOTMiniQuestFactory.JOGOS;
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
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.JOGOS;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public float getPoisonedArrowChance() {
		return 0.5f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standart/wild/hired_soldier";
			}
			return "standart/wild/usual_friendly";
		}
		return "standart/wild/usual_hostile";
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			if (mounted) {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItemMounted());
			} else {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
			}
		} else if (mounted) {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeaponMounted());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.nomadBattleaxe));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.nomadSword));
		}
		npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(4) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.nomadSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.jogosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.jogosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.jogosChestplate));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.jogosHelmet));
		} else {
			setCurrentItemOrArmor(4, null);
		}
		return data;
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getJogosName(rand, familyInfo.isMale()));
	}
}
