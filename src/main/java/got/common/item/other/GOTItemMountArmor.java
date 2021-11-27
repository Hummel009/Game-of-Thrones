package got.common.item.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.other.GOTNPCMount;
import got.common.util.GOTReflection;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTItemMountArmor extends Item {
	public ItemArmor.ArmorMaterial armorMaterial;
	public Mount mountType;
	public int damageReduceAmount;
	public Item templateItem;

	public GOTItemMountArmor(GOTMaterial material, Mount mount) {
		this(material.toArmorMaterial(), mount);
	}

	public GOTItemMountArmor(ItemArmor.ArmorMaterial material, Mount mount) {
		armorMaterial = material;
		damageReduceAmount = material.getDamageReductionAmount(1) + material.getDamageReductionAmount(2);
		mountType = mount;
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabCombat);
	}

	public ItemStack createTemplateItemStack(ItemStack source) {
		ItemStack template = new ItemStack(templateItem);
		template.stackSize = source.stackSize;
		template.setItemDamage(source.getItemDamage());
		if (source.getTagCompound() != null) {
			template.setTagCompound(source.getTagCompound());
		}
		return template;
	}

	public String getArmorTexture() {
		String path = null;
		if (templateItem != null) {
			int index = 0;
			if (templateItem == Items.iron_horse_armor) {
				index = 1;
			}
			if (templateItem == Items.golden_horse_armor) {
				index = 2;
			}
			if (templateItem == Items.diamond_horse_armor) {
				index = 3;
			}
			path = GOTReflection.getHorseArmorTextures()[index];
		} else {
			String mountName = mountType.textureName;
			String materialName = armorMaterial.name().toLowerCase();
			if (materialName.startsWith("got_")) {
				materialName = materialName.substring("got_".length());
			}
			path = "got:armor/mount/" + mountName + "_" + materialName + ".png";
		}
		return path;
	}

	public int getDamageReduceAmount() {
		return damageReduceAmount;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		if (templateItem != null) {
			return templateItem.getIconFromDamage(i);
		}
		return super.getIconFromDamage(i);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		if (templateItem != null) {
			return templateItem.getIconIndex(createTemplateItemStack(itemstack));
		}
		return super.getIconIndex(itemstack);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return armorMaterial.func_151685_b() == repairItem.getItem() ? true : super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (templateItem != null) {
			return templateItem.getItemStackDisplayName(createTemplateItemStack(itemstack));
		}
		return super.getItemStackDisplayName(itemstack);
	}

	public ItemArmor.ArmorMaterial getMountArmorMaterial() {
		return armorMaterial;
	}

	public boolean isValid(GOTNPCMount mount) {
		if (mount instanceof GOTEntityGiraffe || mount instanceof GOTEntityDeer || mount instanceof GOTEntityElephant || mount instanceof GOTEntityMammoth || mount instanceof GOTEntityWildBoar || mount instanceof GOTEntityCamel) {
			return false;
		}
		if (mount instanceof GOTEntityRhino || mount instanceof GOTEntityWoolyRhino) {
			return mountType == Mount.RHINO;
		}
		if (mount instanceof GOTEntityHorse) {
			return mountType == Mount.HORSE;
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		if (templateItem == null) {
			super.registerIcons(iconregister);
		}
	}

	public GOTItemMountArmor setTemplateItem(Item item) {
		templateItem = item;
		return this;
	}

	public enum Mount {
		HORSE("horse"), RHINO("rhino");

		public String textureName;

		Mount(String s) {
			textureName = s;
		}
	}

}
