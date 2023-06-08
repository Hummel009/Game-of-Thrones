package io.gitlab.dwarfyassassin.gotucp.client.util;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class FakeArmorStandEntity extends EntityLivingBase {
	public static final FakeArmorStandEntity INSTANCE = new FakeArmorStandEntity();

	public FakeArmorStandEntity() {
		super(FMLClientHandler.instance().getWorldClient());
	}

	public ItemStack getHeldItem() {
		return null;
	}

	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		return null;
	}

	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_) {
	}

	public ItemStack[] getLastActiveItems() {
		return null;
	}
}

