package got.common.entity.ai;

import java.util.BitSet;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public abstract class GOTEntityAIDragonRide extends EntityAIBase {

	public GOTEntityDragon dragon;
	public EntityPlayer rider;

	public GOTEntityAIDragonRide(GOTEntityDragon dragon) {
		this.dragon = dragon;
		setMutexBits(0xffffffff);
	}

	public boolean getControlFlag(int index) {
		BitSet controlFlags = dragon.getControlFlags();
		return controlFlags == null ? false : controlFlags.get(index);
	}

	public boolean isFlyDown() {
		return getControlFlag(1);
	}

	public boolean isFlyUp() {
		return getControlFlag(0);
	}

	@Override
	public boolean shouldExecute() {
		rider = dragon.getRidingPlayer();
		return rider != null;
	}
}
