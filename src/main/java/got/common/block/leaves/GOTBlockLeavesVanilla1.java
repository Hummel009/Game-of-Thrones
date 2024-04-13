package got.common.block.leaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class GOTBlockLeavesVanilla1 extends GOTBlockLeavesBase {
	public GOTBlockLeavesVanilla1() {
		super(true, "got:leaves_v1");
		leafNames = new String[]{"oak", "spruce", "birch", "jungle"};
	}

	@Override
	protected void addSpecialLeafDrops(List<ItemStack> drops, World world, int meta, int fortune) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k) & 3;
		if (meta == 0) {
			return getBiomeLeafColor(world, i, j, k);
		}
		return super.colorMultiplier(world, i, j, k);
	}

	@Override
	public String[] func_150125_e() {
		return BlockOldLeaf.field_150131_O;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		int meta = i & 3;
		if (meta == 0) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
		return super.getRenderColor(i);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 3) {
			return 30;
		}
		return super.getSaplingChance(meta);
	}
}