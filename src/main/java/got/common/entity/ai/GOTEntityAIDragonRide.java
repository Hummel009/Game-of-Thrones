package got.common.entity.ai;

import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.other.inanimate.GOTEntityDragonFireball;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

import java.util.BitSet;

public abstract class GOTEntityAIDragonRide extends EntityAIBase {
	protected final GOTEntityDragon dragon;

	protected EntityPlayer rider;

	protected GOTEntityAIDragonRide(GOTEntityDragon dragon) {
		this.dragon = dragon;
		setMutexBits(0xffffffff);
	}

	private boolean getControlFlag(int index) {
		BitSet controlFlags = dragon.getControlFlags();
		return controlFlags != null && controlFlags.get(index);
	}

	protected boolean isFlyDown() {
		return getControlFlag(1);
	}

	protected boolean isFlyUp() {
		return getControlFlag(0);
	}

	public boolean isDracarys() {
		return getControlFlag(2);
	}

	@Override
	public boolean shouldExecute() {
		rider = dragon.getRidingPlayer();
		return rider != null;
	}

	protected void dracarys() {
		GOTPlayerData playerData = GOTLevelData.getData(rider);
		if (isDracarys()) {
			if (playerData.getTimeSinceDragonFireball() >= GOTConfig.dragonFireballCooldown * 20) {
				Vec3 vecPlayer = rider.getLookVec();
				Vec3 vec3 = dragon.getLookVec();
				GOTEntityDragonFireball ball = new GOTEntityDragonFireball(dragon.worldObj, rider, vec3.xCoord, vec3.yCoord, vec3.zCoord);
				ball.setPlayer(rider);
				ball.field_92057_e = 2;
				double d8 = 4.0;
				ball.posX = rider.posX + vecPlayer.xCoord * d8;
				ball.posY = rider.posY + rider.height / 2.0f + 0.5;
				ball.posZ = rider.posZ + vecPlayer.zCoord * d8;
				ball.attackEntityFrom(DamageSource.causePlayerDamage(rider), 1.0f);
				dragon.worldObj.spawnEntityInWorld(ball);
				playerData.setTimeSinceDragonFireball(0);
			}
		}
	}
}