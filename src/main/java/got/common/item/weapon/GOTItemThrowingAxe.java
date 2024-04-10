package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.dispense.GOTDispenseThrowingAxe;
import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityThrowingAxe;
import got.common.item.GOTMaterialFinder;
import got.common.recipe.GOTRecipe;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTItemThrowingAxe extends Item implements GOTMaterialFinder {
	public ToolMaterial gotMaterial;
	public Item.ToolMaterial axeMaterial;

	public GOTItemThrowingAxe(Item.ToolMaterial material) {
		axeMaterial = material;
		setMaxStackSize(1);
		setMaxDamage(material.getMaxUses());
		setFull3D();
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseThrowingAxe());
		gotMaterial = material;
	}

	public Item.ToolMaterial getAxeMaterial() {
		return axeMaterial;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return GOTRecipe.checkItemEquals(axeMaterial.getRepairItemStack(), repairItem) || super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}

	public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
		float damage = axeMaterial.getDamageVsEntity() + 4.0f;
		damage = damage + (shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase ? EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shooter, (EntityLivingBase) hit) : EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED));
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
		for (GOTEnchantment ench : GOTEnchantment.CONTENT) {
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
