package got.common.dispense;

import got.common.entity.other.GOTEntitySpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class GOTDispenseSpear extends BehaviorDefaultDispenseItem {
	@Override
	public ItemStack dispenseStack(IBlockSource dispense, ItemStack itemstack) {
		World world = dispense.getWorld();
		IPosition iposition = BlockDispenser.func_149939_a(dispense);
		EnumFacing enumfacing = BlockDispenser.func_149937_b(dispense.getBlockMetadata());
		GOTEntitySpear spear = new GOTEntitySpear(world, itemstack.copy(), iposition.getX(), iposition.getY(), iposition.getZ());
		spear.setThrowableHeading(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + 0.1f, enumfacing.getFrontOffsetZ(), 1.1f, 6.0f);
		spear.canBePickedUp = 1;
		world.spawnEntityInWorld(spear);
		itemstack.splitStack(1);
		return itemstack;
	}

	@Override
	public void playDispenseSound(IBlockSource dispense) {
		dispense.getWorld().playAuxSFX(1002, dispense.getXInt(), dispense.getYInt(), dispense.getZInt(), 0);
	}
}
