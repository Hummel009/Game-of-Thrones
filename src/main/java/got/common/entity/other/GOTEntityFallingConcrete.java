package got.common.entity.other;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (worldObj.getBlock((int) posX, (int) posY, (int) posZ).getMaterial() == Material.water || worldObj.getBlock((int) posX + 1, (int) posY, (int) posZ).getMaterial() == Material.water || worldObj.getBlock((int) posX - 1, (int) posY, (int) posZ).getMaterial() == Material.water || worldObj.getBlock((int) posX, (int) posY, (int) posZ + 1).getMaterial() == Material.water || worldObj.getBlock((int) posX, (int) posY, (int) posZ - 1).getMaterial() == Material.water) {
			worldObj.setBlock((int) posX, (int) posY, (int) posZ, fallTile, meta, 3);
			setDead();
		}
	}
}
