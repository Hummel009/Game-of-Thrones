package got.common.item.weapon;

import java.util.List;

import got.GOT;
import got.common.database.GOTMaterial;
import got.common.entity.dragon.GOTEntityFlyingTameable;
import got.common.entity.other.GOTEntityNPCRideable;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTItemAsshaiShadowbinderStaff extends GOTItemSword {
	public GOTItemAsshaiShadowbinderStaff() {
		super(GOTMaterial.ASSHAI);
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
		return GOTItemAsshaiShadowbinderStaff.useStaff(itemstack, world, entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	public static ItemStack useStaff(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		if (GOT.isAprilFools()) {
			world.playSoundAtEntity(user, "mob.ghast.fireball", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
			if (!world.isRemote) {
				List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(12.0, 8.0, 12.0));
				if (!entities.isEmpty()) {
					for (Object element : entities) {
						EntityLivingBase entity = (EntityLivingBase) element;
						if (entity == user || entity instanceof EntityHorse && ((EntityHorse) entity).isTame() || entity instanceof EntityTameable && ((EntityTameable) entity).isTamed() || entity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entity).isNPCTamed()) {
							continue;
						}
						float strength = 6.0f - user.getDistanceToEntity(entity) * 0.75f;
						if (strength < 1.0f) {
							strength = 1.0f;
						}
						if (user instanceof EntityPlayer) {
							entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user), 6.0f * strength);
						} else {
							entity.attackEntityFrom(DamageSource.causeMobDamage(user), 6.0f * strength);
						}
						float knockback = strength;
						if (knockback > 4.0f) {
							knockback = 4.0f;
						}
						if (GOT.canPlayerAttackEntity((EntityPlayer) user, entity, false)) {
							entity.addVelocity(-MathHelper.sin(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback, 0.2 + 0.12 * knockback, MathHelper.cos(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback);
						}
					}
				}
			}
		} else {
			world.playSoundAtEntity(user, "got:staff.amogus", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		}
		return itemstack;
	}

	public static ItemStack wizardUseStaff(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		world.playSoundAtEntity(user, "mob.ghast.fireball", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		if (!world.isRemote) {
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(12.0, 8.0, 12.0));
			if (!entities.isEmpty()) {
				for (Object element : entities) {
					EntityLivingBase entity = (EntityLivingBase) element;
					if (entity == user || entity instanceof EntityHorse && ((EntityHorse) entity).isTame() || entity instanceof GOTEntityFlyingTameable && ((GOTEntityFlyingTameable) entity).isTamed() || entity instanceof EntityTameable && ((EntityTameable) entity).isTamed() || entity instanceof GOTEntityNPCRideable && ((GOTEntityNPCRideable) entity).isNPCTamed()) {
						continue;
					}
					float strength = 6.0f - user.getDistanceToEntity(entity) * 0.75f;
					if (strength < 1.0f) {
						strength = 1.0f;
					}
					if (user instanceof EntityPlayer) {
						entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user), 6.0f * strength);
					} else {
						entity.attackEntityFrom(DamageSource.causeMobDamage(user), 6.0f * strength);
					}
					float knockback = strength;
					if (knockback > 4.0f) {
						knockback = 4.0f;
					}
					if (GOT.canNPCAttackEntity((EntityCreature) user, entity, false)) {
						entity.addVelocity(-MathHelper.sin(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback, 0.2 + 0.12 * knockback, MathHelper.cos(user.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback);
					}
				}
			}
		}
		return itemstack;
	}
}
