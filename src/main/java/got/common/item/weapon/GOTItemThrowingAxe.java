package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseThrowingAxe;
import got.common.enchant.*;
import got.common.entity.other.GOTEntityThrowingAxe;
import got.common.item.GOTMaterialFinder;
import got.common.recipe.GOTRecipe;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTItemThrowingAxe extends Item implements GOTMaterialFinder {
	public ToolMaterial gotMaterial;
	public Item.ToolMaterial axeMaterial;

	public GOTItemThrowingAxe(Item.ToolMaterial material) {
		axeMaterial = material;
		setMaxStackSize(1);
		setMaxDamage(material.getMaxUses());
		setFull3D();
		setCreativeTab(GOTCreativeTabs.tabCombat);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseThrowingAxe());
		gotMaterial = material;
	}

	public Item.ToolMaterial getAxeMaterial() {
		return axeMaterial;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		if (GOTRecipe.checkItemEquals(axeMaterial.getRepairItemStack(), repairItem)) {
			return true;
		}
		return super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}

	public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
		float damage = axeMaterial.getDamageVsEntity() + 4.0f;
		damage = shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase ? (damage += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shooter, (EntityLivingBase) hit)) : (damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED));
		return damage * 0.5f;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		GOTEntityThrowingAxe axe = new GOTEntityThrowingAxe(world, entityplayer, itemstack.copy(), 2.0f);
		axe.setIsCritical(true);
		int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + GOTEnchantmentHelper.calcFireAspect(itemstack);
		if (fireAspect > 0) {
			axe.setFire(100);
		}
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			GOTEnchantmentHelper.setProjectileEnchantment(axe, ench);
		}
		if (entityplayer.capabilities.isCreativeMode) {
			axe.canBePickedUp = 2;
		}
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(axe);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		return itemstack;
	}
}
