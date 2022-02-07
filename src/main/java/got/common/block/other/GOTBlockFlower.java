package got.common.block.other;

import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class GOTBlockFlower extends BlockBush {
	public GOTBlockFlower() {
		this(Material.plants);
	}

	public GOTBlockFlower(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setHardness(0.0f);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getFlowerRenderID();
	}

	public Block setFlowerBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		return this;
	}
}
