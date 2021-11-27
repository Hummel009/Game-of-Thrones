package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.world.EnumDifficulty;

public class GOTItemDagger extends GOTItemSword implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;
	public DaggerEffect effect;

	public GOTItemDagger(GOTMaterial material) {
		this(material, DaggerEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemDagger(GOTMaterial material, DaggerEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemDagger(Item.ToolMaterial material) {
		this(material, DaggerEffect.NONE);
	}

	public GOTItemDagger(Item.ToolMaterial material, DaggerEffect e) {
		super(material);
		gotWeaponDamage -= 3.0f;
		effect = e;
	}

	public DaggerEffect getDaggerEffect() {
		return effect;
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		itemstack.damageItem(1, user);
		if (effect == DaggerEffect.NONE) {
			return true;
		}
		if (effect == DaggerEffect.POISON) {
			GOTItemDagger.applyStandardPoison(hitEntity);
		}
		return true;
	}

	public static void applyStandardPoison(EntityLivingBase entity) {
		EnumDifficulty difficulty = entity.worldObj.difficultySetting;
		int duration = 1 + difficulty.getDifficultyId() * 2;
		PotionEffect poison = new PotionEffect(Potion.poison.id, (duration + itemRand.nextInt(duration)) * 20);
		entity.addPotionEffect(poison);
	}

	public enum DaggerEffect {
		NONE, POISON;

	}

}
