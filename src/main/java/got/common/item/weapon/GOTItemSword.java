package got.common.item.weapon;

import java.util.UUID;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import got.common.item.GOTMaterialFinder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class GOTItemSword extends ItemSword implements GOTMaterialFinder {
	@SideOnly(value = Side.CLIENT)
	public IIcon glowingIcon;
	public boolean isGlowing = false;
	public float gotWeaponDamage;
	public ToolMaterial gotMaterial;
	public HitEffect effect;

	public GOTItemSword(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabCombat);
		gotWeaponDamage = material.getDamageVsEntity() + 4.0f;
		gotMaterial = material;
	}

	public GOTItemSword(Item.ToolMaterial material, HitEffect e) {
		this(material);
		effect = e;
	}

	public GOTItemSword addWeaponDamage(float f) {
		gotWeaponDamage += f;
		return this;
	}

	public float getGOTWeaponDamage() {
		return gotWeaponDamage;
	}

	public HitEffect getHitEffect() {
		return effect;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "GOT Weapon modifier", gotWeaponDamage, 0));
		return multimap;
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		itemstack.damageItem(1, user);
		if (effect == HitEffect.NONE) {
			return true;
		}
		if (effect == HitEffect.POISON) {
			applyStandardPoison(hitEntity);
		}
		if (effect == HitEffect.FIRE) {
			applyStandardFire(hitEntity);
		}
		return true;
	}

	public boolean isGlowing() {
		return isGlowing;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (getItemUseAction(itemstack) == EnumAction.none) {
			return itemstack;
		}
		return super.onItemRightClick(itemstack, world, entityplayer);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		if (isGlowing) {
			glowingIcon = iconregister.registerIcon(getIconString() + "_glowing");
		}
	}

	public GOTItemSword setIsGlowing() {
		isGlowing = true;
		return this;
	}

	public GOTItemSword setWeaponDamage(float f) {
		gotWeaponDamage = f;
		return this;
	}

	public static UUID accessWeaponDamageModifier() {
		return field_111210_e;
	}

	public static void applyStandardFire(EntityLivingBase entity) {
		EnumDifficulty difficulty = entity.worldObj.difficultySetting;
		int duration = 1 + difficulty.getDifficultyId() * 10;
		entity.setFire(duration);
	}

	public static void applyStandardPoison(EntityLivingBase entity) {
		EnumDifficulty difficulty = entity.worldObj.difficultySetting;
		int duration = 1 + difficulty.getDifficultyId() * 2;
		PotionEffect poison = new PotionEffect(Potion.poison.id, (duration + itemRand.nextInt(duration)) * 20);
		entity.addPotionEffect(poison);
	}

	public enum HitEffect {
		NONE, FIRE, POISON;
	}
}
