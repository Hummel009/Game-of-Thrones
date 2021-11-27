package got.common.item.weapon;

import got.common.database.*;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.potion.*;

public class GOTItemLegendaryDagger extends GOTItemSword implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;
	public DaggerEffect effect;

	public GOTItemLegendaryDagger(GOTMaterial material) {
		this(material, DaggerEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemLegendaryDagger(GOTMaterial material, DaggerEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemLegendaryDagger(Item.ToolMaterial material) {
		this(material, DaggerEffect.NONE);
	}

	public GOTItemLegendaryDagger(Item.ToolMaterial material, DaggerEffect e) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 5.0f;
		effect = e;
		setCreativeTab(GOTCreativeTabs.tabStory);
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
			hitEntity.addPotionEffect(new PotionEffect(Potion.poison.id, 600));
		}
		if (effect == DaggerEffect.FIRE) {
			hitEntity.setFire(30);
		}
		return true;
	}

	public enum DaggerEffect {
		NONE, FIRE, POISON;
	}
}