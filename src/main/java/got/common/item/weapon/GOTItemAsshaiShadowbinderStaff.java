package got.common.item.weapon;

import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTMaterial;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemAsshaiShadowbinderStaff extends GOTItemSword {
	public GOTItemAsshaiShadowbinderStaff() {
		super(GOTMaterial.RUBY);
		setMaxDamage(512);
	}

	public static void useStaff(World world, EntityLivingBase attacker) {
		attacker.swingItem();

		world.playSoundAtEntity(attacker, "mob.ghast.fireball", 2.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);

		if (!world.isRemote) {
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, attacker.boundingBox.expand(16.0, 16.0, 16.0));

			for (EntityLivingBase target : entities) {
				if (target == attacker) {
					continue;
				}
				if (target.riddenByEntity == attacker) {
					continue;
				}

				float strength = 8.0f - attacker.getDistanceToEntity(target) * 0.75f;
				if (strength < 1.0f) {
					strength = 1.0f;
				}

				float knockback = strength;
				if (knockback > 5.0f) {
					knockback = 5.0f;
				}

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;

					if (target.riddenByEntity != null && !GOT.canPlayerAttackEntity(player, (EntityLivingBase) target.riddenByEntity, false)) {
						continue;
					}
					if (!GOT.canPlayerAttackEntity(player, target, false)) {
						continue;
					}

					target.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", target, attacker).setMagicDamage().setDamageBypassesArmor(), strength * 2.0f);
					target.addVelocity(-MathHelper.sin(attacker.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback, 0.2 + 0.12 * knockback, MathHelper.cos(attacker.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback);
				} else if (attacker instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) attacker;

					if (!GOTAsshaiStaffSelector.canNpcAttackTarget(npc, target)) {
						continue;
					}

					target.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", target, attacker).setMagicDamage().setDamageBypassesArmor(), strength * 2.0f);
					target.addVelocity(-MathHelper.sin(attacker.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback, 0.2 + 0.12 * knockback, MathHelper.cos(attacker.rotationYaw * 3.1415927f / 180.0f) * 0.7f * knockback);
				}
			}

			if (attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				GOTLevelData.getData(player).addAchievement(GOTAchievement.useAsshaiShadowbinderStaff);
			}
		}
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
		itemstack.damageItem(1, entityplayer);
		useStaff(world, entityplayer);
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}