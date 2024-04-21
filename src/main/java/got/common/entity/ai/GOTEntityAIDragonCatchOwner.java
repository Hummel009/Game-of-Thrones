package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIDragonCatchOwner extends EntityAIBase {
	protected final GOTEntityDragon dragon;

	protected EntityPlayer owner;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIDragonCatchOwner(GOTEntityDragon dragon) {
		this.dragon = dragon;
	}

	@Override
	public boolean shouldExecute() {
		owner = (EntityPlayer) dragon.getOwner();
		return owner != null && !owner.capabilities.isCreativeMode && dragon.riddenByEntity == null && owner.fallDistance > 4;
	}
}