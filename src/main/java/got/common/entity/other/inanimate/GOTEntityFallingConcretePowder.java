package got.common.entity.other.inanimate;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class GOTEntityFallingConcretePowder extends EntityFallingBlock {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingConcretePowder(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingConcretePowder(World world, double d, double d1, double d2, Block block) {
		this(world, d, d1, d2, block, 0);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityFallingConcretePowder(World world, double d, double d1, double d2, Block block, int meta) {
		super(world, d, d1, d2, block, meta);
	}
}