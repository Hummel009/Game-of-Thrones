package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

import java.util.Locale;

public class GOTItemArmor extends ItemArmor {
	private final String extraName;

	public GOTItemArmor(ArmorMaterial material, int slotType) {
		this(material, slotType, "");
	}

	public GOTItemArmor(ArmorMaterial material, int slotType, String s) {
		super(material, 0, slotType);
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		extraName = s;
	}

	private String getArmorName() {
		String prefix = getArmorMaterial().name().substring("got".length() + 1).toLowerCase(Locale.ROOT);
		String suffix = armorType == 2 ? "2" : "1";
		if (!StringUtils.isNullOrEmpty(extraName)) {
			suffix = extraName;
		}
		return prefix + '_' + suffix;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
		String path = "got:textures/armor/";
		String armorName = getArmorName();
		StringBuilder texture = new StringBuilder(path).append(armorName);
		if (type != null) {
			texture.append('_').append(type);
		}
		return texture.append(".png").toString();
	}
}
