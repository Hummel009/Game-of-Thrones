package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAITargetNonTamed;

public class GOTEntityAIDragonHunt extends EntityAITargetNonTamed {

	public GOTEntityDragon dragon;

	public GOTEntityAIDragonHunt(GOTEntityDragon dragon, Class clazz, int par3, boolean par4) {
		super(dragon, clazz, par3, par4);
		this.dragon = dragon;
	}

	@Override
	public boolean shouldExecute() {
		return dragon.isAdult() && super.shouldExecute();
	}
}
