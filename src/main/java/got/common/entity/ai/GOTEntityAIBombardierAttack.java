package got.common.entity.ai;

import got.common.block.other.GOTBlockBomb;
import got.common.entity.essos.yi_ti.GOTEntityYiTiBombardier;
import got.common.entity.other.inanimate.GOTEntityBomb;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class GOTEntityAIBombardierAttack extends EntityAIBase {
	private final World worldObj;
	private final GOTEntityYiTiBombardier attacker;
	private final double moveSpeed;

	private EntityLivingBase entityTarget;
	private PathEntity entityPathEntity;
	private int rePathDelay;

	public GOTEntityAIBombardierAttack(GOTEntityYiTiBombardier entity, double speed) {
		attacker = entity;
		worldObj = entity.worldObj;
		moveSpeed = speed;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return attacker.getNpcItemsInv().getBomb() != null && attacker.getAttackTarget() != null && entityTarget.isEntityAlive() && !attacker.getNavigator().noPath();
	}

	@Override
	public void resetTask() {
		entityTarget = null;
		attacker.getNavigator().clearPathEntity();
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entity = attacker.getAttackTarget();
		if (entity == null || attacker.getNpcItemsInv().getBomb() == null) {
			return false;
		}
		entityTarget = entity;
		entityPathEntity = attacker.getNavigator().getPathToEntityLiving(entityTarget);
		return entityPathEntity != null;
	}

	@Override
	public void startExecuting() {
		attacker.getNavigator().setPath(entityPathEntity, moveSpeed);
		rePathDelay = 0;
	}

	@Override
	public void updateTask() {
		attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30.0f, 30.0f);
		if (attacker.getEntitySenses().canSee(entityTarget)) {
			--rePathDelay;
			if (rePathDelay <= 0) {
				rePathDelay = 4 + attacker.getRNG().nextInt(7);
				attacker.getNavigator().tryMoveToEntityLiving(entityTarget, moveSpeed);
			}
		}
		if (attacker.getDistanceToEntity(entityTarget) < 3.0) {
			GOTEntityBomb bomb = new GOTEntityBomb(worldObj, attacker.posX, attacker.posY + 1.0, attacker.posZ, attacker);
			int meta = 0;
			ItemStack bombItem = attacker.getNpcItemsInv().getBomb();
			if (bombItem != null && Block.getBlockFromItem(bombItem.getItem()) instanceof GOTBlockBomb) {
				meta = bombItem.getItemDamage();
			}
			bomb.setBombStrengthLevel(meta);
			bomb.setDroppedByHiredUnit(attacker.getHireableInfo().isActive());
			bomb.setDroppedTargetingPlayer(entityTarget instanceof EntityPlayer);
			worldObj.spawnEntityInWorld(bomb);
			worldObj.playSoundAtEntity(attacker, "game.tnt.primed", 1.0f, 1.0f);
			attacker.getNpcItemsInv().setBomb(null);
			int bombSlot = 5;
			if (attacker.getHiredReplacedInv().hasReplacedEquipment(bombSlot)) {
				attacker.getHiredReplacedInv().onEquipmentChanged(bombSlot, null);
			}
			attacker.refreshCurrentAttackMode();
		}
	}
}