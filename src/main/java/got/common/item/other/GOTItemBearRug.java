package got.common.item.other;

import got.common.entity.animal.*;
import got.common.entity.other.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemBearRug extends GOTItemRugBase {
	public GOTItemBearRug() {
		super(GOTEntityBear.BearType.bearTypeNames());
	}

	@Override
	public GOTEntityRugBase createRug(World world, ItemStack itemstack) {
		GOTEntityBearRug rug = new GOTEntityBearRug(world);
		rug.setRugType(GOTEntityBear.BearType.forID(itemstack.getItemDamage()));
		return rug;
	}
}
