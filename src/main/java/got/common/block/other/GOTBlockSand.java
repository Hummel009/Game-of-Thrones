package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class GOTBlockSand extends BlockFalling {
	public GOTBlockSand() {
		super(Material.sand);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(0.5f);
		setStepSound(soundTypeSand);
	}
}
