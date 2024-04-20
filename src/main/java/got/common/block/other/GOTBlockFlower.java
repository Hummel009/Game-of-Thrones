package got.common.block.other;

import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;

public class GOTBlockFlower extends BlockBush {
	public GOTBlockFlower() {
		this(Material.plants);
	}

	private GOTBlockFlower(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
		setHardness(0.0f);
		setStepSound(soundTypeGrass);
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getFlowerRenderID();
	}

	public Block setFlowerBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		return this;
	}
}