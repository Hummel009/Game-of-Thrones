package got.common.dispense;

import got.common.block.other.GOTBlockWildFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class GOTDispenseWildFireJar extends BehaviorDefaultDispenseItem {
	private static final IBehaviorDispenseItem DISPENSE_DEFAULT = new BehaviorDefaultDispenseItem();

	@Override
	public ItemStack dispenseStack(IBlockSource dispense, ItemStack itemstack) {
		int k;
		int j;
		int i;
		EnumFacing enumfacing = BlockDispenser.func_149937_b(dispense.getBlockMetadata());
		World world = dispense.getWorld();
		if (world.getBlock(i = dispense.getXInt() + enumfacing.getFrontOffsetX(), j = dispense.getYInt() + enumfacing.getFrontOffsetY(), k = dispense.getZInt() + enumfacing.getFrontOffsetZ()).isReplaceable(world, i, j, k)) {
			GOTBlockWildFireJar.setExplodeOnAdded(false);
			world.setBlock(i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage(), 3);
			GOTBlockWildFireJar.setExplodeOnAdded(true);
			--itemstack.stackSize;
			return itemstack;
		}
		return DISPENSE_DEFAULT.dispense(dispense, itemstack);
	}
}