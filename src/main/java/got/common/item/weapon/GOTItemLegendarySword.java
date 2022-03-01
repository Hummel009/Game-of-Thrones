package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.potion.*;

public class GOTItemLegendarySword extends GOTItemSword {
	public SwordEffect effect;

	public GOTItemLegendarySword(GOTMaterial material) {
		this(material, SwordEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemLegendarySword(GOTMaterial material, SwordEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemLegendarySword(Item.ToolMaterial material) {
		this(material, SwordEffect.NONE);
	}

	public GOTItemLegendarySword(Item.ToolMaterial material, SwordEffect e) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 9.0f;
		effect = e;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}

	public SwordEffect getDaggerEffect() {
		return effect;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		itemstack.damageItem(1, user);
		if (effect == SwordEffect.NONE) {
			return true;
		}
		if (effect == SwordEffect.POISON) {
			hitEntity.addPotionEffect(new PotionEffect(Potion.poison.id, 600));
		}
		if (effect == SwordEffect.FIRE) {
			hitEntity.setFire(30);
		}
		return true;
	}

	public enum SwordEffect {
		NONE, FIRE, POISON;
	}
}