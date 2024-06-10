package got.common.entity.essos.mossovy;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.iface.GOTMercenary;
import got.common.util.GOTCrashHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityMossovyWitcher extends GOTEntityMossovyMan implements GOTMercenary {
	private final EntityAIBase rangedAttackAI = new GOTEntityAIRangedAttack(this, 1.25, 20, 40, 20.0f);
	private EntityAIBase meleeAttackAI;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMossovyWitcher(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(NPC_RANGED_ACCURACY).setBaseValue(1.0);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		npcCrossbowAttack(target);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public EntityAIBase createMossovyAttackAI() {
		meleeAttackAI = new GOTEntityAIAttackOnCollide(this, 1.4, true);
		return meleeAttackAI;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCCrossbowBolts(i);
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
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
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killMossovyWitcher;
	}

	@Override
	public float getMercAlignmentRequired() {
		return 10.0f;
	}

	@Override
	public int getMercBaseCost() {
		return GOTUnitTradeEntries.SOLDIERE;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hireableInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.mossovySword));
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.ironCrossbow));
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.mossovyBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.mossovyLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.mossovyChestplate));
		setCurrentItemOrArmor(4, null);
		return entityData;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}