package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockAleHorn extends GOTBlockMug {
	public GOTBlockAleHorn() {
		super(5.0f, 12.0f);
		setStepSound(Block.soundTypeStone);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.stained_hardened_clay.getIcon(i, 0);
	}
}
