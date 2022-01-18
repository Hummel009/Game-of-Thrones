package got.common.item.other;

import got.common.database.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.StringUtils;

public class GOTItemArmor extends ItemArmor {
	public GOTMaterial gotMaterial;
	public String extraName;
	public String path;
	public int slot;

	public GOTItemArmor(GOTMaterial material, int slotType) {
		this(material, slotType, "");
		slot = slotType;
	}

	public GOTItemArmor(GOTMaterial material, int slotType, String s) {
		super(material.toArmorMaterial(), 0, slotType);
		gotMaterial = material;
		setCreativeTab(GOTCreativeTabs.tabCombat);
		extraName = s;
		slot = slotType;
	}

	public String getArmorName() {
		String suffix;
		String prefix = getArmorMaterial().name().substring("got".length() + 1).toLowerCase();
		suffix = armorType == 2 ? "2" : "1";
		if (!StringUtils.isNullOrEmpty(extraName)) {
			suffix = extraName;
		}
		return prefix + "_" + suffix;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		path = "got:textures/armor/";
		String armorName = getArmorName();
		StringBuilder texture = new StringBuilder(path).append(armorName);
		if (type != null) {
			texture.append("_").append(type);
		}
		return texture.append(".png").toString();
	}

	public GOTMaterial getGOTArmorMaterial() {
		return gotMaterial;
	}

	@Override
	public boolean isDamageable() {
		return gotMaterial.isDamageable();
	}
}
