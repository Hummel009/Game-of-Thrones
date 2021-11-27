package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class GOTItemSkullStaff extends GOTItemSword {
	public GOTItemSkullStaff() {
		super(GOTMaterial.ASSHAI);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.skull;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return null;
	}
}
