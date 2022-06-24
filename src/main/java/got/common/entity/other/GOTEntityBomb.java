package got.common.entity.other;

import got.GOT;
import got.common.block.other.GOTBlockBomb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntityBomb extends EntityTNTPrimed {
	public int bombFuse;
	public boolean droppedByPlayer;
	public boolean droppedByHiredUnit;
	public boolean droppedTargetingPlayer;

	public GOTEntityBomb(World world) {
		super(world);
	}

	public GOTEntityBomb(World world, double d, double d1, double d2, EntityLivingBase entity) {
		super(world, d, d1, d2, entity);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	public void explodeBomb() {
		boolean doTerrainDamage = false;
		if (droppedByPlayer) {
			doTerrainDamage = true;
		} else if (droppedByHiredUnit || droppedTargetingPlayer) {
			doTerrainDamage = GOT.canGrief(worldObj);
		}
		int meta = getBombStrengthLevel();
		int strength = GOTBlockBomb.getBombStrengthLevel(meta);
		boolean fire = GOTBlockBomb.isFireBomb(meta);
		worldObj.newExplosion(this, posX, posY, posZ, (strength + 1) * 4.0f, fire, doTerrainDamage);
	}

	public int getBombStrengthLevel() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.04;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.98;
		motionY *= 0.98;
		motionZ *= 0.98;
		if (onGround) {
			motionX *= 0.7;
			motionZ *= 0.7;
			motionY *= -0.5;
		}
		--bombFuse;
		if (bombFuse <= 0 && !worldObj.isRemote) {
			setDead();
			explodeBomb();
		} else {
			worldObj.spawnParticle("smoke", posX, posY + 0.7, posZ, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		droppedByPlayer = nbt.getBoolean("DroppedByPlayer");
		droppedByHiredUnit = nbt.getBoolean("DroppedByHiredUnit");
		droppedTargetingPlayer = nbt.getBoolean("DroppedTargetingPlayer");
		setBombStrengthLevel(nbt.getInteger("BombStrengthLevel"));
		bombFuse = nbt.getInteger("OrcBombFuse");
	}

	public void setBombStrengthLevel(int i) {
		dataWatcher.updateObject(16, (byte) i);
		bombFuse = 40 + GOTBlockBomb.getBombStrengthLevel(i) * 20;
	}

	public void setFuseFromExplosion() {
		bombFuse = worldObj.rand.nextInt(bombFuse / 4) + bombFuse / 8;
	}

	public void setFuseFromHiredUnit() {
		GOTBlockBomb.getBombStrengthLevel(getBombStrengthLevel());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("DroppedByPlayer", droppedByPlayer);
		nbt.setBoolean("DroppedByHiredUnit", droppedByHiredUnit);
		nbt.setBoolean("DroppedTargetingPlayer", droppedTargetingPlayer);
		nbt.setInteger("BombStrengthLevel", getBombStrengthLevel());
		nbt.setInteger("OrcBombFuse", bombFuse);
	}
}
