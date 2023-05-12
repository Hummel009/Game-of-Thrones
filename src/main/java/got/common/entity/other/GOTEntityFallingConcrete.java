package got.common.entity.other;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class GOTEntityFallingConcrete extends EntityFallingBlock {
	public Block fallTile;
	public int meta;

	public GOTEntityFallingConcrete(World worldIn) {
		super(worldIn);
	}

	public GOTEntityFallingConcrete(World worldIn, double x, double y, double z, Block block) {
		this(worldIn, x, y, z, block, 0);
	}

	public GOTEntityFallingConcrete(World worldIn, double x, double y, double z, Block block, int meta) {
		super(worldIn, x, y, z, block, meta);
		fallTile = block;
		this.meta = meta;
	}
}
