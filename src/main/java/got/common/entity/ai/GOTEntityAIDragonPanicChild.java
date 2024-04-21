package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIPanic;

public class GOTEntityAIDragonPanicChild extends EntityAIPanic {
	private final GOTEntityDragon dragon;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIDragonPanicChild(GOTEntityDragon dragon, double speed) {
		super(dragon, speed);
		this.dragon = dragon;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && dragon.isHatchling();
	}
}