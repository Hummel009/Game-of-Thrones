package got.common.dispense;

import got.common.entity.other.GOTEntityArrowFire;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class GOTDispenseArrowFire extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition iposition) {
		GOTEntityArrowFire arrow = new GOTEntityArrowFire(world, iposition.getX(), iposition.getY(), iposition.getZ());
		arrow.canBePickedUp = 1;
		return arrow;
	}
}