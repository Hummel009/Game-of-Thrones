package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.util.IIcon;

public class GOTBlockFenceGate extends BlockFenceGate {
	private Block plankBlock;
	private int plankMeta;

	public GOTBlockFenceGate(Block block, int meta) {
		plankBlock = block;
		plankMeta = meta;
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(soundTypeWood);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return plankBlock.getIcon(i, plankMeta);
	}
}
