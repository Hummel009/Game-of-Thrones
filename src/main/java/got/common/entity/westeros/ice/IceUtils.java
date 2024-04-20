package got.common.entity.westeros.ice;

import got.common.GOTDamage;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IceUtils {
	private IceUtils() {
	}

	public static void createNewWight(EntityLivingBase entity, World world) {
		if (entity instanceof GOTEntityHumanBase) {
			GOTEntityNPC wight = new GOTEntityWight(world);
			wight.familyInfo.setAge(((GOTEntityNPC) entity).familyInfo.getAge());
			wight.copyLocationAndAnglesFrom(entity);
			wight.npcItemsInv.setMeleeWeapon(((GOTEntityNPC) entity).npcItemsInv.getMeleeWeapon());
			wight.npcItemsInv.setIdleItem(((GOTEntityNPC) entity).npcItemsInv.getMeleeWeapon());
			wight.setCurrentItemOrArmor(1, entity.getEquipmentInSlot(1));
			wight.setCurrentItemOrArmor(2, entity.getEquipmentInSlot(2));
			wight.setCurrentItemOrArmor(3, entity.getEquipmentInSlot(3));
			wight.setCurrentItemOrArmor(4, entity.getEquipmentInSlot(4));
			wight.familyInfo.setMale(((GOTEntityNPC) entity).familyInfo.male);
			world.removeEntity(entity);
			world.spawnEntityInWorld(wight);
		} else if (entity instanceof GOTEntityGiantBase) {
			GOTEntityNPC giant = new GOTEntityWightGiant(world);
			giant.copyLocationAndAnglesFrom(entity);
			world.removeEntity(entity);
			giant.onSpawnWithEgg(null);
			world.spawnEntityInWorld(giant);
		} else if (entity instanceof GOTEntitySpiderBase) {
			GOTEntityNPC spider = new GOTEntityIceSpider(world);
			spider.copyLocationAndAnglesFrom(entity);
			world.removeEntity(entity);
			spider.onSpawnWithEgg(null);
			world.spawnEntityInWorld(spider);
		}
	}

	public static boolean calculateDamage(DamageSource damageSource, boolean additionalRule) {
		Entity entity = damageSource.getEntity();
		Entity sourceOfDamage = damageSource.getSourceOfDamage();
		boolean causeDamage = false;
		if (entity instanceof EntityLivingBase && entity == damageSource.getSourceOfDamage()) {
			ItemStack itemStack = ((EntityLivingBase) entity).getHeldItem();
			if (itemStack != null) {
				Item item = itemStack.getItem();
				if (item instanceof GOTMaterialFinder && (((GOTMaterialFinder) item).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) item).getMaterial() == GOTMaterial.OBSIDIAN_TOOL)) {
					causeDamage = true;
				}
				if (item == GOTItems.crowbar) {
					causeDamage = true;
				}
			}
		}
		if (entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityAsshaiArchmag) {
			causeDamage = true;
		}
		if (sourceOfDamage instanceof GOTEntitySpear) {
			Item item = ((GOTEntityProjectileBase) sourceOfDamage).getProjectileItem().getItem();
			if (item instanceof GOTMaterialFinder && (((GOTMaterialFinder) item).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) item).getMaterial() == GOTMaterial.OBSIDIAN_TOOL)) {
				causeDamage = true;
			}
		}
		return additionalRule && damageSource.isFireDamage() || causeDamage;
	}

	public static boolean attackWithFrost(Entity entity, boolean attackEntityAsMob) {
		if (attackEntityAsMob) {
			if (entity instanceof EntityPlayerMP) {
				GOTDamage.doFrostDamage((EntityPlayerMP) entity);
			}
			return true;
		}
		return false;
	}
}