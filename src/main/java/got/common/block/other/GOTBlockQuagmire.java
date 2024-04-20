package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class GOTBlockQuagmire extends Block {
	public GOTBlockQuagmire() {
		super(Material.ground);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		entity.setInWeb();
	}
}