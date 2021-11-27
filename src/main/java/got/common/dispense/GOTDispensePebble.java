package got.common.dispense;

import got.common.entity.other.GOTEntityPebble;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class GOTDispensePebble extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new GOTEntityPebble(world, position.getX(), position.getY(), position.getZ());
	}
}
