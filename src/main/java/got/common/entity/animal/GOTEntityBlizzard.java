package got.common.entity.animal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntitySnowball;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityBlizzard extends EntityCreature implements GOTBiome.ImmuneToFrost {
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	private int firingState;

	public GOTEntityBlizzard(World world) {
		super(world);
		setSize(0.6f, 1.8f);
	}

	@Override
	public void attackEntity(Entity entity, float f) {
		if (attackTime <= 0 && f < 2.0f && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
			attackTime = 20;
			attackEntityAsMob(entity);
		} else if (f < 30.0f) {
			double d0 = entity.posX - posX;
			double d1 = entity.boundingBox.minY + entity.height / 2.0F - (posY + height / 2.0F);
			double d2 = entity.posZ - posZ;
			if (attackTime == 0) {
				++firingState;
				if (firingState == 1) {
					attackTime = 60;
					setInAttackMode(true);
				} else if (firingState <= 4) {
					attackTime = 6;
				} else {
					attackTime = 80;
					firingState = 0;
					setInAttackMode(false);
				}
				if (firingState > 1) {
					float f1 = MathHelper.sqrt_float(f) * 0.5F;
					worldObj.playAuxSFXAtEntity(null, 1009, (int) posX, (int) posY, (int) posZ, 0);
					for (int i = 0; i < 1; ++i) {
						GOTEntitySnowball entitysmallfireball = new GOTEntitySnowball(worldObj, this, d0 + rand.nextGaussian() * f1, d1, d2 + rand.nextGaussian() * f1);
						entitysmallfireball.posY = posY + height / 2.0F + 0.5D;
						worldObj.spawnEntityInWorld(entitysmallfireball);
					}
				}
			}
			rotationYaw = (float) (Math.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
			hasAttacked = true;
		}
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		if (flag) {
			int j = rand.nextInt() + 2;
			for (int k = 0; k < j; ++k) {
				dropItem(GOTItems.valyrianPowder, 1);
			}
			dropItem(GOTItems.valyrianNugget, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public Entity findPlayerToAttack() {
		EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		return entityplayer != null && canEntityBeSeen(entityplayer) ? entityplayer : null;
	}

	@Override
	public float getBrightness(float f) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == GOTCrashHandler.getBiomeGenForCoords(worldObj, i, k).topBlock;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "mob.blaze.death";
	}

	@Override
	public Item getDropItem() {
		return GOTItems.valyrianPowder;
	}

	@Override
	public String getHurtSound() {
		return "mob.blaze.hit";
	}

	@Override
	public String getLivingSound() {
		return "mob.blaze.breathe";
	}

	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote) {
			--heightOffsetUpdateTime;
			if (heightOffsetUpdateTime <= 0) {
				heightOffsetUpdateTime = 100;
				heightOffset = 0.5F + (float) rand.nextGaussian() * 3.0F;
			}
			if (getEntityToAttack() != null && getEntityToAttack().posY + getEntityToAttack().getEyeHeight() > posY + getEyeHeight() + heightOffset) {
				motionY += (0.30000001192092896D - motionY) * 0.30000001192092896D;
			}
		}
		if (!onGround && motionY < 0.0D) {
			motionY *= 0.6D;
		}
		for (int i = 0; i < 2; ++i) {
			GOT.proxy.spawnParticle("chill", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
		}
		super.onLivingUpdate();
	}

	private void setInAttackMode(boolean flag) {
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 &= -2;
		}
		dataWatcher.updateObject(16, b0);
	}
}