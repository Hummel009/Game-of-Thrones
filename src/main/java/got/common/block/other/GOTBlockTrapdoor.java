package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class GOTBlockTrapdoor extends BlockTrapDoor {
	public GOTBlockTrapdoor() {
		this(Material.wood);
		setStepSound(Block.soundTypeWood);
		setHardness(3.0f);
	}

	public GOTBlockTrapdoor(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabUtil);
	}
}