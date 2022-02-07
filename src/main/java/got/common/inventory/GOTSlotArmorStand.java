package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTSlotArmorStand extends Slot {
	private int armorSlot;
	private Entity armorTestEntity;

	public GOTSlotArmorStand(IInventory inv, int i, int j, int k, int a, Entity entity) {
		super(inv, i, j, k);
		armorSlot = a;
		armorTestEntity = entity;
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getBackgroundIconIndex() {
		return ItemArmor.func_94602_b(armorSlot);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return item.isValidArmor(itemstack, armorSlot, armorTestEntity);
		}
		return true;
	}
}
