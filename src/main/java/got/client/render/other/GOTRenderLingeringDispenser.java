package got.client.render.other;

import got.common.entity.other.inanimate.GOTEntityLingeringPotion;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTRenderLingeringDispenser implements IBehaviorDispenseItem {
	@Override
	public ItemStack dispense(IBlockSource block, ItemStack stack) {
		return new MyBehaviorProjectileDispense(stack).dispense(block, stack);
	}

	private static class MyBehaviorProjectileDispense extends BehaviorProjectileDispense {
		private final ItemStack stack;

		private MyBehaviorProjectileDispense(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public float func_82498_a() {
			return super.func_82498_a() * 0.5F;
		}

		@Override
		public float func_82500_b() {
			return super.func_82500_b() * 1.25F;
		}

		@Override
		public IProjectile getProjectileEntity(World world, IPosition pos) {
			return new GOTEntityLingeringPotion(world, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
		}
	}
}