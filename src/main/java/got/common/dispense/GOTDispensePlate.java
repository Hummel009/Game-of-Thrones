package got.common.dispense;

import got.common.entity.other.GOTEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class GOTDispensePlate extends BehaviorProjectileDispense {
	private Block plateBlock;

	public GOTDispensePlate(Block block) {
		plateBlock = block;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new GOTEntityPlate(world, plateBlock, position.getX(), position.getY(), position.getZ());
	}
}
