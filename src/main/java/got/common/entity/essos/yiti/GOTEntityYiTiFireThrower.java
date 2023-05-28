package got.common.entity.essos.yiti;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.GOTEntityFirePot;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiFireThrower extends GOTEntityYiTiSoldier {
	public EntityAIBase rangedAttackAI = createEasterlingRangedAI();
	public EntityAIBase meleeAttackAI;

	public GOTEntityYiTiFireThrower(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		EntityArrow template = new EntityArrow(worldObj, this, target, 1.0f, 0.5f);
		GOTEntityFirePot pot = new GOTEntityFirePot(worldObj, this);
		pot.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
		pot.motionX = template.motionX;
		pot.motionY = template.motionY;
		pot.motionZ = template.motionZ;
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(pot);
	}

	public EntityAIBase createEasterlingRangedAI() {
		return new GOTEntityAIRangedAttack(this, 1.3, 20, 30, 16.0f);
	}

	@Override
	public EntityAIBase createYiTiAttackAI() {
		meleeAttackAI = super.createYiTiAttackAI();
		return meleeAttackAI;
	}

	@Override
	public double getMeleeRange() {
		EntityLivingBase target = getAttackTarget();
		if (target != null && target.isBurning()) {
			return Double.MAX_VALUE;
		}
		return super.getMeleeRange();
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		}
		if (mode == GOTEntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == GOTEntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiDagger));
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.firePot));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return data;
	}
}
