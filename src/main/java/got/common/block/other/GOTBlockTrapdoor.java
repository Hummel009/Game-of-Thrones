package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class GOTBlockTrapdoor extends BlockTrapDoor {
	public GOTBlockTrapdoor() {
		super(Material.wood);
		setCreativeTab(GOTCreativeTabs.TAB_UTIL);
		setStepSound(soundTypeWood);
		setHardness(3.0f);
	}
}