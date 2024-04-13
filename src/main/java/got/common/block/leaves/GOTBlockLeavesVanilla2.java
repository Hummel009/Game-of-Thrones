package got.common.block.leaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class GOTBlockLeavesVanilla2 extends GOTBlockLeavesBase {
	public GOTBlockLeavesVanilla2() {
		super(true, "got:leaves_v2");
		leafNames = new String[]{"acacia", "dark_oak"};
	}

	@Override
	protected void addSpecialLeafDrops(List<ItemStack> drops, World world, int meta, int fortune) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k) & 3;
		if (meta == 0 || meta == 1) {
			return getBiomeLeafColor(world, i, j, k);
		}
		return super.colorMultiplier(world, i, j, k);
	}

	@Override
	public int damageDropped(int i) {
		return super.damageDropped(i) + 4;
	}

	@Override
	public String[] func_150125_e() {
		return BlockNewLeaf.field_150133_O;
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		return super.damageDropped(world.getBlockMetadata(i, j, k));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		int meta = i & 3;
		if (meta == 0 || meta == 1) {
			return ColorizerFoliage.getFoliageColorBasic();
		}
		return super.getRenderColor(i);
	}

	@Override
	public int getSaplingChance(int meta) {
		if (meta == 1) {
			return 12;
		}
		return super.getSaplingChance(meta);
	}
}