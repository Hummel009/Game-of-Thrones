package got.common.item.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import got.common.enchant.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.WeightedRandom;

public class GOTItemModifierTemplate extends Item {
	public GOTItemModifierTemplate() {
		setMaxStackSize(1);
		setCreativeTab(GOTCreativeTabs.tabTools);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		GOTEnchantment mod = GOTItemModifierTemplate.getModifier(itemstack);
		if (mod != null) {
			String desc = mod.getNamedFormattedDescription(itemstack);
			list.add(desc);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String s = super.getItemStackDisplayName(itemstack);
		GOTEnchantment mod = GOTItemModifierTemplate.getModifier(itemstack);
		if (mod != null) {
			s = String.format(s, mod.getDisplayName());
		}
		return s;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.hasTemplateItem()) {
				continue;
			}
			ItemStack itemstack = new ItemStack(this);
			GOTItemModifierTemplate.setModifier(itemstack, ench);
			list.add(itemstack);
		}
	}

	public static GOTEnchantment getModifier(ItemStack itemstack) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt != null) {
			String s = nbt.getString("ScrollModifier");
			return GOTEnchantment.getEnchantmentByName(s);
		}
		return null;
	}

	public static ItemStack getRandomCommonTemplate(Random random) {
		ArrayList<GOTEnchantmentHelper.WeightedRandomEnchant> applicable = new ArrayList<>();
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.hasTemplateItem()) {
				continue;
			}
			int weight = GOTEnchantmentHelper.getSkilfulWeight(ench);
			GOTEnchantmentHelper.WeightedRandomEnchant wre = new GOTEnchantmentHelper.WeightedRandomEnchant(ench, weight);
			applicable.add(wre);
		}
		GOTEnchantmentHelper.WeightedRandomEnchant chosenWre = (GOTEnchantmentHelper.WeightedRandomEnchant) WeightedRandom.getRandomItem(random, applicable);
		GOTEnchantment chosenEnch = chosenWre.theEnchant;
		ItemStack itemstack = new ItemStack(GOTRegistry.smithScroll);
		GOTItemModifierTemplate.setModifier(itemstack, chosenEnch);
		return itemstack;
	}

	public static void setModifier(ItemStack itemstack, GOTEnchantment ench) {
		String s = ench.enchantName;
		itemstack.setTagInfo("ScrollModifier", new NBTTagString(s));
	}
}
