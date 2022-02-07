package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIDragonCatchOwner extends EntityAIBase {

	public GOTEntityDragon dragon;
	public EntityPlayer owner;

	public GOTEntityAIDragonCatchOwner(GOTEntityDragon dragon) {
		this.dragon = dragon;
	}

	@Override
	public boolean shouldExecute() {
		owner = (EntityPlayer) dragon.getOwner();

		if (owner == null || owner.capabilities.isCreativeMode || dragon.riddenByEntity != null) {
			return false;
		}

		return owner.fallDistance > 4;
	}
}
