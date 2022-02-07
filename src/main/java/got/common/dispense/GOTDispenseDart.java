package got.common.dispense;

import got.common.entity.other.GOTEntityDart;
import got.common.item.other.GOTItemDart;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTDispenseDart extends BehaviorProjectileDispense {
	private GOTItemDart theDartItem;

	public GOTDispenseDart(GOTItemDart item) {
		theDartItem = item;
	}

	@Override
	public float func_82500_b() {
		return 1.5f;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition iposition) {
		ItemStack itemstack = new ItemStack(theDartItem);
		GOTEntityDart dart = theDartItem.createDart(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
		dart.canBePickedUp = 1;
		return dart;
	}

	@Override
	public void playDispenseSound(IBlockSource source) {
		source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "got:item.dart", 1.0f, 1.2f);
	}
}
