package got.common.dispense;

import got.common.entity.other.GOTEntityFirePot;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class GOTDispenseFirePot extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new GOTEntityFirePot(world, position.getX(), position.getY(), position.getZ());
	}
}
