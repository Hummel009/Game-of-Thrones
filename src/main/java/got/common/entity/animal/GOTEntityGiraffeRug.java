package got.common.entity.animal;

import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGiraffeRug extends GOTEntityRugBase {
	public GOTEntityGiraffeRug(World world) {
		super(world);
		setSize(2.0f, 0.3f);
	}

	@Override
	public ItemStack getRugItem() {
		return new ItemStack(GOTItems.giraffeRug);
	}

	@Override
	public String getRugNoise() {
		return "";
	}
}
