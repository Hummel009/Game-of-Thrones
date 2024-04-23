package got.common.item.other;

import got.common.entity.other.inanimate.GOTEntityGiraffeRug;
import got.common.entity.other.inanimate.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemGiraffeRug extends GOTItemRugBase {
	public GOTItemGiraffeRug() {
		super("giraffe");
	}

	@Override
	public GOTEntityRugBase createRug(World world, ItemStack itemstack) {
		return new GOTEntityGiraffeRug(world);
	}
}