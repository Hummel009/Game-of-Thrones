package got.common.item.other;

import got.common.entity.animal.GOTEntityBear;
import got.common.entity.other.inanimate.GOTEntityBearRug;
import got.common.entity.other.inanimate.GOTEntityRugBase;
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