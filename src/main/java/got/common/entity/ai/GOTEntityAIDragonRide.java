package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

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

	@Override
	public boolean shouldExecute() {
		rider = dragon.getRidingPlayer();
		return rider != null;
	}
}