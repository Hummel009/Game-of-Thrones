package got.common.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;

public class GOTEntityAIDragonWatchIdle extends EntityAILookIdle {

	public GOTEntityAIDragonWatchIdle(EntityLiving par1EntityLiving) {
		super(par1EntityLiving);
		setMutexBits(2);
	}
}
