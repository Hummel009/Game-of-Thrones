package got.common.item.weapon;

import java.util.UUID;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import got.common.item.GOTMaterialFinder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class GOTItemSword extends ItemSword implements GOTMaterialFinder {
	@SideOnly(value = Side.CLIENT)
	public IIcon glowingIcon;
	public boolean isGlowing = false;
	public float gotWeaponDamage;
	public GOTMaterial gotMaterial;

	public GOTItemSword(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemSword(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabCombat);
		gotWeaponDamage = material.getDamageVsEntity() + 4.0f;
	}

	public GOTItemSword addWeaponDamage(float f) {
		gotWeaponDamage += f;
		return this;
	}

	public float getGOTWeaponDamage() {
		return gotWeaponDamage;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "GOT Weapon modifier", gotWeaponDamage, 0));
		return multimap;
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
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
}
