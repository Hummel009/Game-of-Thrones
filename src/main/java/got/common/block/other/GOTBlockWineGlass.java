package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockWineGlass extends GOTBlockMug {
	public GOTBlockWineGlass() {
		super(2.5f, 10.0f);
		setStepSound(Block.soundTypeGlass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.glass.getIcon(i, 0);
	}
}
