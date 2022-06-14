package got.common.entity.essos.yiti;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import got.common.quest.*;
import got.common.world.biome.essos.GOTBiomeYiTi;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTEntityYiTiMan extends GOTEntityHumanBase implements IPickpocketable {
	public static ItemStack[] weapons = { new ItemStack(GOTRegistry.yitiDagger), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.bronzeDagger) };

	public GOTEntityYiTiMan(World world) {
		super(world);
		canBeMarried = true;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, createYiTiAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.YITI, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.YITI_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(false);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public GOTMiniQuest createMiniQuest() {
		return GOTMiniQuestFactory.YI_TI.createQuest(this);
	}

	@Override
	public GOTNPCMount createMountToRide() {
		GOTEntityHorse horse = (GOTEntityHorse) super.createMountToRide();
		horse.setMountArmor(new ItemStack(GOTRegistry.yitiHorseArmor));
		return horse;
	}

	public EntityAIBase createYiTiAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropYiTiItems(flag, i);
	}

	public void dropYiTiItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(GOTChestContents.YI_TI, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiomeYiTi) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return GOTMiniQuestFactory.YI_TI;
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
			if (j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock) {
				return true;
			}
		}
		return false;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.YI_TI;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standart/civilized/usual_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(null);
		if (familyInfo.isMale() && rand.nextInt(3000) == 0) {
			familyInfo.setName("Roobimwbj");
		}
		if (familyInfo.isMale() && rand.nextInt(3000) == 0) {
			familyInfo.setName("Mores");
		}
		if (familyInfo.isMale() && rand.nextInt(3000) == 0) {
			familyInfo.setName("12121");
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getYiTiName(rand, familyInfo.isMale()));
	}
}
