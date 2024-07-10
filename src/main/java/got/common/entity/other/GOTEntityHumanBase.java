package got.common.entity.other;

import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTSpeech;
import got.common.entity.ai.*;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GOTEntityHumanBase extends GOTEntityNPC {
	private final boolean canBeMarried;

	protected GOTEntityHumanBase(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, getAttackAI());
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.DEFAULT, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.DEFAULT_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));

		addTargetTasks(false);

		canBeMarried = GOTEntityUtils.canBeMarried(this);
		if (canBeMarried) {
			tasks.addTask(2, new GOTEntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
			tasks.addTask(5, new GOTEntityAINPCMarry(this, 1.3));
			tasks.addTask(6, new GOTEntityAINPCMate(this, 1.3));
			tasks.addTask(7, new GOTEntityAINPCFollowParent(this, 1.4));
			tasks.addTask(8, new GOTEntityAINPCFollowSpouse(this, 1.1));
			tasks.addTask(7, new GOTEntityAINPCFollowParent(this, 1.4));
			familyInfo.setMarriageEntityClass(getClass());
		}

		boolean canSmoke = GOTEntityUtils.canSmokeDrink(this);
		if (canSmoke) {
			tasks.addTask(6, new GOTEntityAISmoke(this, 8000));
		}
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return legendary ? GOTAchievement.killLegendaryNPC : GOTAchievement.killNPC;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(legendary ? 30.0 : 20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return GOTSpeech.getDefaultSpeech(npc, entityPlayer);
	}

	@Override
	public int getTotalArmorValue() {
		return legendary ? 15 : super.getTotalArmorValue();
	}

	protected EntityAIBase getAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
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
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k);
		if (biome instanceof GOTBiome) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public String getNPCName() {
		String name = familyInfo.getName();
		return name == null ? super.getNPCName() : name;
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
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
	public boolean interact(EntityPlayer entityplayer) {
		return canBeMarried && familyInfo.interact(entityplayer) || super.interact(entityplayer);
	}

	@Override
	public void onArtificalSpawn() {
		if ((this instanceof GOTEntityProstitute || canBeMarried && getClass() == familyInfo.getMarriageEntityClass()) && rand.nextInt(7) == 0) {
			familyInfo.setChild();
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}