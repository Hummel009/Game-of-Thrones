package got.common.item.weapon;

import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.other.GOTEntityNPCRideable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemAsshaiArchmagStaff extends GOTItemSword {
	public GOTItemAsshaiArchmagStaff() {
		super(ToolMaterial.WOOD);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
		setMaxDamage(1500);
		gotWeaponDamage = 8.0f;
	}

	private static ItemStack useStaff(ItemStack itemstack, World world, EntityLivingBase user) {
		user.swingItem();
		if (!world.isRemote) {
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(64, 64, 64));
			for (EntityLivingBase entity : entities) {
				if (entity != user && (!(entity instanceof EntityHorse) || !((EntityHorse) entity).isTame()) && (!(entity instanceof EntityTameable) || !((EntityTameable) entity).isTamed()) && (!(entity instanceof GOTEntityNPCRideable) || !((GOTEntityNPCRideable) entity).isNPCTamed())) {
					entity.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", entity, user).setMagicDamage().setDamageBypassesArmor().setDamageAllowedInCreativeMode(), 5);
					if (GOT.canPlayerAttackEntity((EntityPlayer) user, entity, false)) {
						world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
					}
				}
			}
		}
		return itemstack;
	}

	public static void wizardUseStaff(World world, EntityLivingBase user) {
		user.swingItem();
		if (!world.isRemote) {
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, user.boundingBox.expand(64, 64, 64));
			for (EntityLivingBase entity : entities) {
				if (entity != user && (!(entity instanceof EntityHorse) || !((EntityHorse) entity).isTame()) && (!(entity instanceof GOTEntityDragon) || !((EntityTameable) entity).isTamed()) && (!(entity instanceof EntityTameable) || !((EntityTameable) entity).isTamed()) && (!(entity instanceof GOTEntityNPCRideable) || !((GOTEntityNPCRideable) entity).isNPCTamed())) {
					entity.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", entity, user).setMagicDamage().setDamageBypassesArmor().setDamageAllowedInCreativeMode(), 5);
					if (GOT.canNPCAttackEntity((EntityCreature) user, entity, false)) {
						world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
					}
				}
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
		itemstack.damageItem(2, entityplayer);
		return useStaff(itemstack, world, entityplayer);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}