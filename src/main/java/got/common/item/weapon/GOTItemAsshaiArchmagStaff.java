package got.common.item.weapon;

import got.GOT;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTMaterial;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemAsshaiArchmagStaff extends GOTItemSword {
	public GOTItemAsshaiArchmagStaff() {
		super(GOTMaterial.SAPPHIRE);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
		setMaxDamage(128);
	}

	public static void useStaff(World world, EntityLivingBase attacker) {
		attacker.swingItem();

		if (!world.isRemote) {
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, attacker.boundingBox.expand(64.0, 64.0, 64.0));

			for (EntityLivingBase target : entities) {
				if (target == attacker) {
					continue;
				}
				if (target.riddenByEntity == attacker) {
					continue;
				}
				if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
					continue;
				}

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;

					if (target.riddenByEntity != null && !GOT.canPlayerAttackEntity(player, (EntityLivingBase) target.riddenByEntity, false)) {
						continue;
					}
					if (!GOT.canPlayerAttackEntity(player, target, false)) {
						continue;
					}

					target.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", target, attacker).setMagicDamage().setDamageBypassesArmor(), 5.0f);
					world.addWeatherEffect(new EntityLightningBolt(world, target.posX, target.posY, target.posZ));
				} else if (attacker instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) attacker;

					if (!GOTAsshaiStaffSelector.canNpcAttackTarget(npc, target)) {
						continue;
					}

					target.attackEntityFrom(new EntityDamageSourceIndirect("got.staff", target, attacker).setMagicDamage().setDamageBypassesArmor(), 5.0f);
					world.addWeatherEffect(new EntityLightningBolt(world, target.posX, target.posY, target.posZ));
				}
			}

			if (attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				GOTLevelData.getData(player).addAchievement(GOTAchievement.useAsshaiArchmagStaff);
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