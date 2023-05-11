package got.common.dispense;

import got.common.entity.other.GOTEntityConker;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class GOTDispenseConker extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new GOTEntityConker(world, position.getX(), position.getY(), position.getZ());
	}
}
