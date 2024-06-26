package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.IIcon;

public class GOTBlockFenceGate extends BlockFenceGate {
	private final Block plankBlock;
	private final int plankMeta;

	public GOTBlockFenceGate(Block block, int meta) {
		plankBlock = block;
		plankMeta = meta;
		setCreativeTab(GOTCreativeTabs.TAB_UTIL);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(soundTypeWood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return plankBlock.getIcon(i, plankMeta);
	}
}