package got.common.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.*;

public class GOTSlotSeeds extends Slot {
	public World worldObj;

	public GOTSlotSeeds(IInventory inv, int i, int j, int k, World world) {
		super(inv, i, j, k);
		worldObj = world;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item instanceof IPlantable && ((IPlantable) item).getPlantType(worldObj, -1, -1, -1) == EnumPlantType.Crop;
	}
}
