package got.common.item.weapon;

import java.util.List;

import got.GOT;
import got.common.database.*;
import got.common.entity.dragon.GOTEntityFlyingTameable;
import got.common.entity.other.GOTEntityNPCRideable;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class GOTItemAsshaiArchmagStaff extends GOTItemSword {
	public GOTItemAsshaiArchmagStaff() {
		super(GOTMaterial.ASSHAI);
		setCreativeTab(GOTCreativeTabs.tabStory);
		setMaxDamage(1500);
		gotWeaponDamage = 8.0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		itemstack.damageItem(2, entityplayer);
		return GOTItemAsshaiArchmagStaff.useStaff(itemstack, world, entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	public static ItemStack useStaff(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		if (!world.isRemote) {
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(64, 64, 64));
			if (!entities.isEmpty()) {
				for (Object element : entities) {
					EntityLivingBase entity = (EntityLivingBase) element;
					if (entity != user && (!(entity instanceof EntityHorse) || !((EntityHorse) entity).isTame()) && (!(entity instanceof EntityTameable) || !((EntityTameable) entity).isTamed()) && (!(entity instanceof GOTEntityNPCRideable) || !((GOTEntityNPCRideable) entity).isNPCTamed())) {
						entity.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", entity, user).setMagicDamage().setDamageBypassesArmor().setDamageAllowedInCreativeMode(), 5);
						if (GOT.canPlayerAttackEntity((EntityPlayer) user, entity, false)) {
							world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
						}
					}
				}
			}
		}
		return itemstack;
	}

	public static ItemStack wizardUseStaff(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		if (!world.isRemote) {
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(64, 64, 64));
			if (!entities.isEmpty()) {
				for (Object element : entities) {
					EntityLivingBase entity = (EntityLivingBase) element;
					if (entity != user && (!(entity instanceof EntityHorse) || !((EntityHorse) entity).isTame()) && (!(entity instanceof GOTEntityFlyingTameable) || !((GOTEntityFlyingTameable) entity).isTamed()) && (!(entity instanceof EntityTameable) || !((EntityTameable) entity).isTamed()) && (!(entity instanceof GOTEntityNPCRideable) || !((GOTEntityNPCRideable) entity).isNPCTamed())) {
						entity.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", entity, user).setMagicDamage().setDamageBypassesArmor().setDamageAllowedInCreativeMode(), 5);
						if (GOT.canNPCAttackEntity((EntityCreature) user, entity, false)) {
							world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
						}
					}
				}
			}
		}
		return itemstack;
	}
}