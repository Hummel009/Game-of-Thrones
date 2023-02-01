package got.common.entity.ai;

import got.common.block.other.GOTBlockBomb;
import got.common.entity.essos.yiti.GOTEntityYiTiBombardier;
import got.common.entity.other.GOTEntityBomb;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class GOTEntityAIBombardierAttack extends EntityAIBase {
	public World worldObj;
	public GOTEntityYiTiBombardier attacker;
	public EntityLivingBase entityTarget;
	public double moveSpeed;
	public PathEntity entityPathEntity;
	public int rePathDelay;

	public GOTEntityAIBombardierAttack(GOTEntityYiTiBombardier entity, double speed) {
		attacker = entity;
		worldObj = entity.worldObj;
		moveSpeed = speed;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return attacker.npcItemsInv.getBomb() != null && attacker.getAttackTarget() != null && entityTarget.isEntityAlive() && !attacker.getNavigator().noPath();
	}

	@Override
	public void resetTask() {
		entityTarget = null;
		attacker.getNavigator().clearPathEntity();
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entity = attacker.getAttackTarget();
		if (entity == null || attacker.npcItemsInv.getBomb() == null) {
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
		if (attacker.getEntitySenses().canSee(entityTarget) && --rePathDelay <= 0) {
			rePathDelay = 4 + attacker.getRNG().nextInt(7);
			attacker.getNavigator().tryMoveToEntityLiving(entityTarget, moveSpeed);
		}
		if (attacker.getDistanceToEntity(entityTarget) < 3.0) {
			GOTEntityBomb bomb = new GOTEntityBomb(worldObj, attacker.posX, attacker.posY + 1.0, attacker.posZ, attacker);
			int meta = 0;
			ItemStack bombItem = attacker.npcItemsInv.getBomb();
			if (bombItem != null && Block.getBlockFromItem(bombItem.getItem()) instanceof GOTBlockBomb) {
				meta = bombItem.getItemDamage();
			}
			bomb.setBombStrengthLevel(meta);
			bomb.setFuseFromHiredUnit();
			bomb.droppedByHiredUnit = attacker.hiredNPCInfo.isActive;
			bomb.droppedTargetingPlayer = entityTarget instanceof EntityPlayer;
			worldObj.spawnEntityInWorld(bomb);
			worldObj.playSoundAtEntity(attacker, "game.tnt.primed", 1.0f, 1.0f);
			attacker.npcItemsInv.setBomb(null);
			int bombSlot = 5;
			if (attacker.hiredReplacedInv.hasReplacedEquipment(bombSlot)) {
				attacker.hiredReplacedInv.onEquipmentChanged(bombSlot, null);
			}
			attacker.refreshCurrentAttackMode();
		}
	}
}
