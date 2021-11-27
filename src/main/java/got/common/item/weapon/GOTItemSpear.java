package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.dispense.GOTDispenseSpear;
import got.common.enchant.*;
import got.common.entity.other.GOTEntitySpear;
import got.common.item.GOTMaterialFinder;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTItemSpear extends GOTItemSword implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemSpear(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemSpear(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage -= 1.0f;
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new GOTDispenseSpear());
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}

	public int getMaxDrawTime() {
		return 20;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public float getRangedDamageMultiplier(ItemStack itemstack, Entity shooter, Entity hit) {
		float damage = getGOTWeaponDamage();
		damage = shooter instanceof EntityLivingBase && hit instanceof EntityLivingBase ? (damage += EnchantmentHelper.getEnchantmentModifierLiving((EntityLivingBase) shooter, (EntityLivingBase) hit)) : (damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED));
		return damage * 0.7f;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
		if (entityplayer.getHeldItem() != itemstack) {
			return;
		}
		int useTick = getMaxItemUseDuration(itemstack) - i;
		float charge = (float) useTick / (float) getMaxDrawTime();
		if (charge < 0.1f) {
			return;
		}
		charge = (charge * charge + charge * 2.0f) / 3.0f;
		charge = Math.min(charge, 1.0f);
		GOTEntitySpear spear = new GOTEntitySpear(world, entityplayer, itemstack.copy(), charge * 2.0f);
		if (charge >= 1.0f) {
			spear.setIsCritical(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + GOTEnchantmentHelper.calcFireAspect(itemstack) > 0) {
			spear.setFire(100);
		}
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !GOTEnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			GOTEnchantmentHelper.setProjectileEnchantment(spear, ench);
		}
		if (entityplayer.capabilities.isCreativeMode) {
			spear.canBePickedUp = 2;
		}
		world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
		if (!world.isRemote) {
			world.spawnEntityInWorld(spear);
		}
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
		}
	}
}
