package got.common.dispense;

import got.common.entity.other.GOTEntityCrossbowBolt;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTDispenseCrossbowBolt extends BehaviorProjectileDispense {
	public Item theBoltItem;

	public GOTDispenseCrossbowBolt(Item item) {
		theBoltItem = item;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition iposition) {
		ItemStack itemstack = new ItemStack(theBoltItem);
		GOTEntityCrossbowBolt bolt = new GOTEntityCrossbowBolt(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
		bolt.canBePickedUp = 1;
		return bolt;
	}

	@Override
	public void playDispenseSound(IBlockSource source) {
		source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "got:item.crossbow", 1.0f, 1.2f);
	}
}
