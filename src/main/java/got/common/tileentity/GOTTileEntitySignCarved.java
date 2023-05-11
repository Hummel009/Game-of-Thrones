package got.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.other.GOTBlockSignCarved;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GOTTileEntitySignCarved extends GOTTileEntitySign {
	@Override
	@SideOnly(value = Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 1600.0;
	}

	@Override
	public int getNumLines() {
		return 8;
	}

	public IIcon getOnBlockIcon() {
		World world = getWorldObj();
		Block block = getBlockType();
		if (block instanceof GOTBlockSignCarved) {
			GOTBlockSignCarved signBlock = (GOTBlockSignCarved) block;
			int meta = getBlockMetadata();
			int i = xCoord;
			int j = yCoord;
			int k = zCoord;
			int onSide = meta;
			return signBlock.getOnBlockIcon(world, i, j, k, onSide);
		}
		return Blocks.stone.getIcon(0, 0);
	}
}
