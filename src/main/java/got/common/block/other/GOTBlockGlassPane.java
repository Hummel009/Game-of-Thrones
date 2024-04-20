package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockGlassPane extends GOTBlockPane {
	public GOTBlockGlassPane() {
		super("got:glass", "got:glass_pane_top", Material.glass, false);
		setHardness(0.3f);
		setStepSound(soundTypeGlass);
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
	}

	@Override
	public boolean canPaneConnectTo(IBlockAccess world, int i, int j, int k, ForgeDirection dir) {
		return super.canPaneConnectTo(world, i, j, k, dir) || world.getBlock(i, j, k) instanceof GOTBlockGlass;
	}
}